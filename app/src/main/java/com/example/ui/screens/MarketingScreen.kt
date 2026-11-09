package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
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
import com.example.ui.locale.TranslationStrings
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary

@Composable
fun MarketingScreen(
    strings: TranslationStrings,
    topic: String,
    audience: String,
    selectedTone: String,
    selectedToolType: String,
    result: String,
    isGenerating: Boolean,
    onTopicChange: (String) -> Unit,
    onAudienceChange: (String) -> Unit,
    onToneChange: (String) -> Unit,
    onToolTypeChange: (String) -> Unit,
    onGenerate: () -> Unit,
    onSendToStudio: () -> Unit,
    onShowToast: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val tools = listOf(
        strings.toolAdCopy,
        strings.toolSocialCaption,
        strings.toolBrandSlogan,
        strings.toolProductDesc
    )

    val tones = listOf(
        strings.toneLuxury,
        strings.toneFriendly,
        strings.toneProfessional,
        strings.toneCreative
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Title & Header
        Text(
            text = strings.marketingTitle,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = GoldPrimary
            )
        )
        Text(
            text = strings.marketingSubtitle,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Tool Type Tabs
        Text(
            text = "نوع المحتوى التسويقي",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(tools) { tool ->
                val isSelected = selectedToolType == tool || (selectedToolType == "Promotional Ads" && tool == strings.toolAdCopy)
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = if (isSelected) GoldPrimary else GoldBorder.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable { onToolTypeChange(tool) },
                    color = if (isSelected) GoldPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = tool,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurface,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Form Inputs Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                // Topic Field
                OutlinedTextField(
                    value = topic,
                    onValueChange = onTopicChange,
                    label = { Text(strings.productNameOrTopic) },
                    placeholder = { Text("مثال: عطر برايا الملكي بنفحات العود والذهب") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("marketing_topic_input"),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Audience Field
                OutlinedTextField(
                    value = audience,
                    onValueChange = onAudienceChange,
                    label = { Text(strings.targetAudience) },
                    placeholder = { Text("مثال: رجال الأعمال ومحبو الرفاهية في الخليج والشرق الأوسط") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("marketing_audience_input"),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Tone Selector
                Text(
                    text = strings.toneOfVoice,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(tones) { tone ->
                        val isToneSelected = selectedTone == tone || (selectedTone == "Luxury & Premium" && tone == strings.toneLuxury)
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    width = if (isToneSelected) 1.5.dp else 0.8.dp,
                                    color = if (isToneSelected) GoldPrimary else GoldBorder.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { onToneChange(tone) },
                            color = if (isToneSelected) GoldPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface
                        ) {
                            Text(
                                text = tone,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (isToneSelected) GoldPrimary else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = if (isToneSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Generate Action Button
        Button(
            onClick = onGenerate,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("generate_copy_button"),
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
                Text("جارٍ صياغة النص الإعلاني...", fontWeight = FontWeight.Bold)
            } else {
                Icon(imageVector = Icons.Default.Campaign, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(strings.generateCopy, fontWeight = FontWeight.Bold)
            }
        }

        // Generated Results View
        if (result.isNotBlank()) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = strings.copyResult,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = GoldPrimary
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("marketing_result_card"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = result,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 24.sp,
                            fontFamily = CairoFontFamily
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Baraya AI Copy", result)
                                clipboard.setPrimaryClip(clip)
                                onShowToast(strings.copiedSuccess)
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, tint = Color(0xFF101014), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = strings.copyToClipboard, color = Color(0xFF101014), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        OutlinedButton(
                            onClick = onSendToStudio,
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary)
                        ) {
                            Icon(imageVector = Icons.Default.OpenInBrowser, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "إرسال للمرسم", color = GoldPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
