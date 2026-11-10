
package com.example.data.remote

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.example.data.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.security.KeyStore
import java.util.concurrent.TimeUnit
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class Fokof2AuthApi(context: Context) {

    companion object {
        private const val BASE_URL =
            "https://fokof2-api.qwhmgfhh14nn.workers.dev"

        private const val PREFS = "fokof2_secure_session"
        private const val TOKEN_KEY = "encrypted_session"
        private const val IV_KEY = "session_iv"
        private const val KEY_ALIAS = "fokof2_session_key"
    }

    private val prefs = context.applicationContext
        .getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    private val client = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .build()

    private val jsonType =
        "application/json; charset=utf-8".toMediaType()

    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)

        val existing = keyStore.getKey(KEY_ALIAS, null)
        if (existing is SecretKey) return existing

        val generator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore"
        )

        generator.init(
            KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or
                    KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(
                    KeyProperties.ENCRYPTION_PADDING_NONE
                )
                .setRandomizedEncryptionRequired(true)
                .build()
        )

        return generator.generateKey()
    }

    private fun saveToken(token: String) {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())

        val encrypted = cipher.doFinal(token.toByteArray(Charsets.UTF_8))

        prefs.edit()
            .putString(
                TOKEN_KEY,
                android.util.Base64.encodeToString(
                    encrypted,
                    android.util.Base64.NO_WRAP
                )
            )
            .putString(
                IV_KEY,
                android.util.Base64.encodeToString(
                    cipher.iv,
                    android.util.Base64.NO_WRAP
                )
            )
            .apply()
    }

    private fun readToken(): String? {
        return try {
            val encrypted = prefs.getString(TOKEN_KEY, null) ?: return null
            val iv = prefs.getString(IV_KEY, null) ?: return null

            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(
                Cipher.DECRYPT_MODE,
                getSecretKey(),
                GCMParameterSpec(
                    128,
                    android.util.Base64.decode(iv, android.util.Base64.NO_WRAP)
                )
            )

            String(
                cipher.doFinal(
                    android.util.Base64.decode(
                        encrypted,
                        android.util.Base64.NO_WRAP
                    )
                ),
                Charsets.UTF_8
            )
        } catch (_: Exception) {
            clearToken()
            null
        }
    }

    private fun clearToken() {
        prefs.edit()
            .remove(TOKEN_KEY)
            .remove(IV_KEY)
            .apply()
    }

    private suspend fun request(
        path: String,
        method: String = "GET",
        body: JSONObject? = null,
        token: String? = null
    ): JSONObject = withContext(Dispatchers.IO) {

        val builder = Request.Builder()
            .url("$BASE_URL$path")
            .header("Accept", "application/json")

        if (token != null) {
            builder.header("Authorization", "Bearer $token")
        }

        when (method) {
            "POST" -> builder.post(
                (body ?: JSONObject()).toString().toRequestBody(jsonType)
            )
            "GET" -> builder.get()
            else -> error("Unsupported HTTP method")
        }

        client.newCall(builder.build()).execute().use { response ->
            val responseText = response.body?.string().orEmpty()

            val result = try {
                JSONObject(responseText)
            } catch (_: Exception) {
                JSONObject()
            }

            if (!response.isSuccessful || result.optBoolean("ok") == false) {
                val code = result.optString("error", "server_error")
                throw Exception(errorMessage(code))
            }

            result
        }
    }

    private fun errorMessage(code: String): String {
        return when (code) {
            "invalid_email" -> "البريد الإلكتروني غير صحيح"
            "invalid_name" -> "الاسم غير صحيح"
            "email_already_registered" -> "هذا البريد مسجل بالفعل"
            "invalid_credentials" -> "البريد أو كلمة المرور غير صحيحة"
            "password_must_be_10_to_128_characters" ->
                "كلمة المرور يجب أن تكون من 10 إلى 128 حرفًا"
            "unauthorized" -> "انتهت الجلسة، سجل الدخول مرة أخرى"
            "owner_setup_not_configured" ->
                "إعداد حساب المالك لم يكتمل بعد"
            else -> "حدث خطأ في الاتصال. حاول مرة أخرى"
        }
    }

    private fun parseUser(json: JSONObject): UserProfile {
        return UserProfile(
            id = json.optString("id"),
            name = json.optString("displayName"),
            email = json.optString("email"),
            phone = "",
            bio = "",
            isVip = false,
            isGuest = false,
            role = json.optString("role", "USER"),
            avatarIndex = 0,
            memberSince = "2026"
        )
    }

    suspend fun register(
        name: String,
        email: String,
        password: String,
        phone: String = ""
    ): UserProfile {
        val body = JSONObject().apply {
            put("name", name.trim())
            put("email", email.trim().lowercase())
            put("password", password)
            put("phone", phone.trim())
        }

        val result = request("/auth/register", "POST", body)
        val token = result.optString("token")

        if (token.isBlank()) error("لم يستلم التطبيق رمز الجلسة")

        saveToken(token)
        return parseUser(result.getJSONObject("user"))
    }

    suspend fun login(
        email: String,
        password: String
    ): UserProfile {
        val body = JSONObject().apply {
            put("email", email.trim().lowercase())
            put("password", password)
        }

        val result = request("/auth/login", "POST", body)
        val token = result.optString("token")

        if (token.isBlank()) error("لم يستلم التطبيق رمز الجلسة")

        saveToken(token)
        return parseUser(result.getJSONObject("user"))
    }

    suspend fun restoreSession(): UserProfile? {
        val token = readToken() ?: return null

        return try {
            val result = request("/auth/me", token = token)
            parseUser(result.getJSONObject("user"))
        } catch (_: Exception) {
            clearToken()
            null
        }
    }

    suspend fun logout() {
        val token = readToken()

        try {
            if (token != null) {
                request(
                    "/auth/logout",
                    method = "POST",
                    token = token
                )
            }
        } finally {
            clearToken()
        }
    }
}
