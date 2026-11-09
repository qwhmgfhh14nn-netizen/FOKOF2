package com.example.data.ai

import com.example.BuildConfig
import com.example.data.model.BrandIdentityResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiAiService {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val apiKey: String = try {
        BuildConfig.GEMINI_API_KEY
    } catch (e: Exception) {
        ""
    }

    suspend fun generateCopy(
        topic: String,
        targetAudience: String,
        tone: String,
        toolType: String,
        isArabic: Boolean
    ): String = withContext(Dispatchers.IO) {
        val systemPrompt = if (isArabic) {
            "أنت خبير التسويق الرقمي وكتابة الإعلانات لمنصة برايا للذكاء الاصطناعي بإشراف محمد برايا. اكتب محتوى جذاباً، بليغاً، مقنعاً، ومصمماً للتحويلات العالية."
        } else {
            "You are the Chief Copywriter & Digital Marketing Strategist for Baraya AI by Mohamed Baraya. Write compelling, high-converting copy with clear CTA."
        }

        val prompt = if (isArabic) {
            "المطلوب: كتابة ($toolType) حول الموضوع: ($topic)، موجه إلى: ($targetAudience)، بنبرة: ($tone). قدم نصاً متكاملاً مع عبارة دعوة لاتخاذ إجراء قوية وهاشتاجات ذات صلة."
        } else {
            "Task: Generate ($toolType) for product/topic: ($topic), targeting: ($targetAudience), tone: ($tone). Include punchy hooks, value proposition, CTA, and relevant hashtags."
        }

        val remoteResult = callGeminiApi(systemPrompt, prompt)
        if (!remoteResult.isNullOrBlank()) {
            return@withContext remoteResult
        }

        // Smart procedural fallback for instant offline/keyless reliability
        fallbackMarketingCopy(topic, targetAudience, tone, toolType, isArabic)
    }

    suspend fun enhancePrompt(rawPrompt: String, isArabic: Boolean): String = withContext(Dispatchers.IO) {
        val systemPrompt = "You are a world-class prompt engineer for creative image generation and graphic design."
        val prompt = "Enhance the following creative prompt for graphic design, adding details on cinematic studio lighting, luxurious gold (#D4AF37) accents, elegant textures, composition, and high-end aesthetic:\n\"$rawPrompt\"\nKeep the enhanced prompt concise (under 50 words)."

        val remoteResult = callGeminiApi(systemPrompt, prompt)
        if (!remoteResult.isNullOrBlank()) {
            return@withContext remoteResult
        }

        if (isArabic) {
            "$rawPrompt، بلمسات ذهبية فاخرة (#D4AF37)، إضاءة استوديو سينمائية ناعمة، تفاصيل ثلاثية الأبعاد متقنة وخلفية فخمة بأعلى دقة 8K"
        } else {
            "$rawPrompt, cinematic studio lighting, polished luxury gold (#D4AF37) accents, volumetric glow, ultra-detailed 8k resolution, elegant modern composition"
        }
    }

    suspend fun chatWithDirector(
        history: List<Pair<Boolean, String>>,
        userMessage: String,
        isArabic: Boolean
    ): String = withContext(Dispatchers.IO) {
        val systemPrompt = if (isArabic) {
            "أنت المساعد الإبداعي الذكي لمنصة 'برايا للذكاء الاصطناعي - BARAYA AI' بإشراف المصمم والمهندس محمد برايا. شعارنا: 'أفكارك تتحول إلى تصاميم قبل أن تتكلم'. أنت مستشار فني خبير في التصميم الجرافيكي، تناغم الألوان، الخطوط العربية، وتصميم الهويات البصرية والشعارات. أجب بأسلوب راقٍ، ملهم، عملي، ومختصر ومباشر."
        } else {
            "You are the Baraya AI Creative Director Assistant by Mohamed Baraya. Tagline: 'Your Ideas Turn Into Designs Before You Even Speak.' You provide expert, concise, elite design advice on color harmony, Arabic/Latin typography, branding, and layouts."
        }

        val remoteResult = callGeminiApi(systemPrompt, userMessage)
        if (!remoteResult.isNullOrBlank()) {
            return@withContext remoteResult
        }

        fallbackChatResponse(userMessage, isArabic)
    }

    suspend fun generateBrandKit(
        brandName: String,
        industry: String,
        isArabic: Boolean
    ): BrandIdentityResult = withContext(Dispatchers.IO) {
        val systemPrompt = "You are an elite brand identity designer. Return a JSON object with: sloganAr, sloganEn, primaryColorHex, secondaryColorHex, accentColorHex, darkBaseHex, lightSurfaceHex, fontHeading, fontBody, brandVoiceAr, brandVoiceEn, logoConceptAr, logoConceptEn."
        val prompt = "Create a luxury brand identity for brand '$brandName' in industry '$industry'. Return JSON only."

        val remoteJson = callGeminiApi(systemPrompt, prompt)
        if (!remoteJson.isNullOrBlank()) {
            try {
                val cleaned = remoteJson.substringAfter("{").substringBeforeLast("}")
                val json = JSONObject("{$cleaned}")
                return@withContext BrandIdentityResult(
                    brandName = brandName,
                    industry = industry,
                    sloganAr = json.optString("sloganAr", "$brandName: عنوان التميز والإبداع"),
                    sloganEn = json.optString("sloganEn", "$brandName: The Pinnacle of Excellence"),
                    primaryColorHex = json.optString("primaryColorHex", "#D4AF37"),
                    secondaryColorHex = json.optString("secondaryColorHex", "#E5C158"),
                    accentColorHex = json.optString("accentColorHex", "#B89726"),
                    darkBaseHex = json.optString("darkBaseHex", "#111116"),
                    lightSurfaceHex = json.optString("lightSurfaceHex", "#FDFBF7"),
                    fontHeading = json.optString("fontHeading", "Cairo Bold / Cinzel"),
                    fontBody = json.optString("fontBody", "Cairo Regular"),
                    brandVoiceAr = json.optString("brandVoiceAr", "ملهم، فاخر، احترافي، يركز على الأصالة والحداثة"),
                    brandVoiceEn = json.optString("brandVoiceEn", "Inspiring, prestigious, modern, and trustworthy"),
                    logoConceptAr = json.optString("logoConceptAr", "شعار يدمج الحرف الأول مع عنصر رمزي من $industry بلمسات ذهبية عصرية"),
                    logoConceptEn = json.optString("logoConceptEn", "Minimalist monogram with symbolic motif of $industry embossed in gold")
                )
            } catch (e: Exception) {
                // fall back to procedural
            }
        }

        // Procedural creative kit
        BrandIdentityResult(
            brandName = brandName,
            industry = industry,
            sloganAr = "$brandName – أفق جديد من التميز والإبداع",
            sloganEn = "$brandName – Redefining Modern Excellence",
            primaryColorHex = "#D4AF37",
            secondaryColorHex = "#E5C158",
            accentColorHex = "#2A2A38",
            darkBaseHex = "#0E0E12",
            lightSurfaceHex = "#FAF8F2",
            fontHeading = "Cairo Bold & Cinzel",
            fontBody = "Cairo Regular",
            brandVoiceAr = "فخم، استثنائي، ذو حضور قوي في قطاع $industry",
            brandVoiceEn = "Sophisticated, authoritative, visionary for the $industry sector",
            logoConceptAr = "حرف متداخل مستوحى من $brandName مع لمعان ذهبي متدرج يعكس الموثوقية والرقي.",
            logoConceptEn = "Interlocking monogram of $brandName with metallic gold foil gradient over rich matte black."
        )
    }

    private fun callGeminiApi(systemPrompt: String, userPrompt: String): String? {
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") return null

        return try {
            val endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=$apiKey"
            val requestBodyJson = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", "$systemPrompt\n\n$userPrompt"))
                        })
                    })
                })
            }

            val request = Request.Builder()
                .url(endpoint)
                .post(requestBodyJson.toString().toRequestBody("application/json".toMediaType()))
                .build()

            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val body = response.body?.string() ?: return null
                    val json = JSONObject(body)
                    val candidates = json.optJSONArray("candidates") ?: return null
                    if (candidates.length() > 0) {
                        val content = candidates.getJSONObject(0).optJSONObject("content")
                        val parts = content?.optJSONArray("parts")
                        if (parts != null && parts.length() > 0) {
                            return parts.getJSONObject(0).optString("text")
                        }
                    }
                }
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun fallbackMarketingCopy(
        topic: String,
        targetAudience: String,
        tone: String,
        toolType: String,
        isArabic: Boolean
    ): String {
        return if (isArabic) {
            """
            🌟 إعلان استثنائي لـ: $topic
            
            هل تبحث عن التميز الذي يخطف الأنظار؟
            نقدم لـ ($targetAudience) تجربة لا مثيل لها تجمع بين الفخامة والابتكار المتقن.
            
            ✨ لماذا تختارنا؟
            • تصميم فريد يلهم عملائك قبل أن تتكلم.
            • جودة متناهية بلمسة احترافية تعزز مكانتك في السوق.
            • نتائج ملموسة تعكس هوية مشروعك الحقيقية.
            
            🏷️ عرض حصري لفترة محدودة: احصل على ميزتك التنافسية اليوم!
            
            📲 تواصل معنا الآن وابدأ رحلة التألق!
            
            #$topic #برايا_للذكاء_الاصطناعي #تصميم_احترافي #تسويق #ريادة_الأعمال
            """.trimIndent()
        } else {
            """
            Elevate Your Vision: $topic
            
            Designed exclusively for $targetAudience who demand perfection.
            
            Why It Stands Out:
            ✦ Iconic aesthetic with unmatched precision
            ✦ Tailored to capture attention before you even speak
            ✦ Engineered for performance, prestige, and growth
            
            Exclusive Offer: Unlock your full potential today.
            
            Take action now & redefine what is possible!
            
            #$topic #BarayaAI #LuxuryDesign #CreativeMarketing #Innovation
            """.trimIndent()
        }
    }

    private fun fallbackChatResponse(query: String, isArabic: Boolean): String {
        val q = query.lowercase()
        return if (isArabic) {
            when {
                q.contains("لون") || q.contains("الوان") ->
                    "نوصي في تصاميم برايا باعتماد قاعدة 60-30-10: 60% للأسود الداكن الفاخر (#0E0E12) لخلق العمق، 30% للرمادي الدافئ أو اللؤلؤي للنصوص والمساحات، و10% لبريق الذهب الملكي (#D4AF37) لزر الدعوة لاتخاذ الإجراء والأشكال المميزة!"
                q.contains("خط") || q.contains("فونط") ->
                    "تناغم الخطوط هو روح التصميم! ننصحك بدمج خط 'Cairo' للأوزان العريضة في العناوين والترويسات مع خط نسخ أنيق أو أوزان خفيفة للنصوص الشارحة لضمان القراءة السلسة والمظهر العصري."
                q.contains("شعار") || q.contains("لوجو") ->
                    "الشعار الناجح يتميز بثلاثة شروط: البساطة القصوى، التميز في ذاكرة العميل، وقابلية التكيف بحجم صغير وكبير. استخدم رمزاً هندسياً أو مونوغراماً ذهبياً واضح المعالم."
                else ->
                    "أهلاً بك! في برايا للذكاء الاصطناعي، نحرص على تحويل كل فكرة إلى تصميم ينبض بالحياة. يمكنك البدء باختيار قالب من المرسم أو استخدام أداة توليد الصور للحصول على الإلهام المباشر!"
            }
        } else {
            when {
                q.contains("color") ->
                    "In Baraya AI luxury layouts, we recommend the 60-30-10 rule: 60% Obsidian Black (#0E0E12) for grounding, 30% Pearl/Warm Gray for readability, and 10% Royal Gold (#D4AF37) for accent highlights and high-converting CTAs!"
                q.contains("font") || q.contains("typography") ->
                    "Typography pairing is vital: pair bold Cairo or Cinzel for hero headlines with clean geometric weights for body text to maintain visual hierarchy."
                q.contains("logo") ->
                    "An iconic logo must be simple, memorable, and scalable. A clean geometric monogram with gold foil styling always elevates brand authority."
                else ->
                    "Welcome! At Baraya AI, your ideas turn into designs before you even speak. You can customize templates in the Studio or generate custom artwork in the AI Image Studio!"
            }
        }
    }
}
