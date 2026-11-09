package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FormatColorFill
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BrandIdentityResult
import com.example.ui.locale.TranslationStrings
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.util.parseColor

@Composable
fun BrandKitScreen(
    strings: TranslationStrings,
    brandName: String,
    industry: String,
    brandKitResult: BrandIdentityResult?,
    isGenerating: Boolean,
    onBrandNameChange: (String) -> Unit,
    onIndustryChange: (String) -> Unit,
    onGenerate: () -> Unit,
    onApplyToStudio: (BrandIdentityResult) -> Unit,
    onShowToast: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = strings.brandKitTitle,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = GoldPrimary
            )
        )
        Text(
            text = strings.brandKitSubtitle,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Input Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                OutlinedTextField(
                    value = brandName,
                    onValueChange = onBrandNameChange,
                    label = { Text(strings.brandNameLabel) },
                    placeholder = { Text("مثال: شركة برايا العقارية") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("brand_name_input"),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = industry,
                    onValueChange = onIndustryChange,
                    label = { Text(strings.industryLabel) },
                    placeholder = { Text("مثال: التطوير العقاري والاستثمار الفاخر") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("brand_industry_input"),
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Generate Action Button
        Button(
            onClick = onGenerate,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("generate_brand_kit_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = GoldPrimary,
                contentColor = Color(0xFF101014)
            ),
            shape = RoundedCornerShape(10.dp),
            enabled = !isGenerating
        ) {
            if (isGenerating) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    color = Color(0xFF101014),
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("جارٍ هندسة الهوية البصرية...", fontWeight = FontWeight.Bold)
            } else {
                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(strings.generateBrandKit, fontWeight = FontWeight.Bold)
            }
        }

        // Generated Brand Kit Display
        if (brandKitResult != null) {
            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("brand_kit_result_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Brand Title & Slogan
                    Text(
                        text = brandKitResult.brandName,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            fontFamily = CairoFontFamily
                        )
                    )
                    Text(
                        text = "« ${brandKitResult.sloganAr} »",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = GoldLight,
                            fontWeight = FontWeight.Medium
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // 1. Color Palette
                    Text(
                        text = strings.colorPalette,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val palette = listOf(
                        "الرئيسي" to brandKitResult.primaryColorHex,
                        "الثانوي" to brandKitResult.secondaryColorHex,
                        "التمييز" to brandKitResult.accentColorHex,
                        "الأساس" to brandKitResult.darkBaseHex,
                        "الخلفية" to brandKitResult.lightSurfaceHex
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        palette.forEach { (label, hex) ->
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(parseColor(hex, GoldPrimary))
                                        .border(1.dp, GoldBorder, RoundedCornerShape(8.dp))
                                        .clickable {
                                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                            val clip = ClipData.newPlainText("Color Hex", hex)
                                            clipboard.setPrimaryClip(clip)
                                            onShowToast("تم نسخ $hex")
                                        }
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = label, style = MaterialTheme.typography.labelSmall, fontSize = 9.sp)
                                Text(text = hex, style = MaterialTheme.typography.labelSmall, fontSize = 8.sp, color = GoldLight)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 2. Typography
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.TextFields, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = strings.typographyPairing,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "• خط العناوين: ${brandKitResult.fontHeading}\n• خط المتن والشروحات: ${brandKitResult.fontBody}",
                        style = MaterialTheme.typography.bodySmall.copy(lineHeight = 20.sp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // 3. Logo Concept Blueprint
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Brush, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = strings.logoConcept,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = brandKitResult.logoConceptAr,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 20.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Apply to Studio Button
                    Button(
                        onClick = { onApplyToStudio(brandKitResult) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("apply_brand_kit_to_studio_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.OpenInBrowser, contentDescription = null, tint = Color(0xFF101014), modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "تطبيق الهوية في مرسم التصميم", color = Color(0xFF101014), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
