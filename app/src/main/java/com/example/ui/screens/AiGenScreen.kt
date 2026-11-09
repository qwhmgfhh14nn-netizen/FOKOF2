package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Tune
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.GeneratedImageItem
import com.example.ui.locale.TranslationStrings
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary

@Composable
fun AiGenScreen(
    strings: TranslationStrings,
    prompt: String,
    selectedStyle: String,
    selectedAspectRatio: String,
    isGenerating: Boolean,
    generatedImages: List<GeneratedImageItem>,
    onPromptChange: (String) -> Unit,
    onStyleChange: (String) -> Unit,
    onAspectRatioChange: (String) -> Unit,
    onEnhancePrompt: () -> Unit,
    onGenerate: () -> Unit,
    onSendToStudio: (GeneratedImageItem) -> Unit,
    onShowToast: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val styles = listOf(
        strings.styleLuxury3d,
        strings.styleCalligraphy,
        strings.styleMinimalist,
        strings.stylePhotorealistic,
        strings.styleCyberpunk,
        strings.styleWatercolor
    )

    val aspectRatios = listOf("1:1", "16:9", "9:16", "4:5")

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Title & Subtitle
        Text(
            text = strings.aiGenTitle,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = GoldPrimary
            )
        )
        Text(
            text = strings.aiGenSubtitle,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Prompt Input Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("prompt_input_card"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                OutlinedTextField(
                    value = prompt,
                    onValueChange = onPromptChange,
                    placeholder = {
                        Text(
                            text = strings.promptPlaceholder,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .testTag("ai_image_prompt_input"),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Prompt Enhancer Button
                    OutlinedButton(
                        onClick = onEnhancePrompt,
                        modifier = Modifier.testTag("enhance_prompt_button"),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "Enhance",
                            tint = GoldPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = strings.enhancePrompt,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = GoldPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                    Text(
                        text = "${prompt.length}/500",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Style Selector
        Text(
            text = "النمط والأسلوب الفني (Art Style)",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(styles) { style ->
                val isSelected = selectedStyle == style || (selectedStyle == "Luxury 3D Gold" && style == strings.styleLuxury3d)
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = if (isSelected) GoldPrimary else GoldBorder.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable { onStyleChange(style) },
                    color = if (isSelected) GoldPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = style,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurface,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Aspect Ratio Selector
        Text(
            text = strings.aspectRatio,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            aspectRatios.forEach { ratio ->
                val isSelected = selectedAspectRatio == ratio
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = if (isSelected) GoldPrimary else GoldBorder.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { onAspectRatioChange(ratio) },
                    color = if (isSelected) GoldPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = ratio,
                        modifier = Modifier.padding(vertical = 8.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurface,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Generate Action Button
        Button(
            onClick = onGenerate,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("generate_artwork_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = GoldPrimary,
                contentColor = Color(0xFF101014)
            ),
            shape = RoundedCornerShape(12.dp),
            enabled = !isGenerating
        ) {
            if (isGenerating) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color(0xFF101014),
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = strings.generatingText,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Icon(imageVector = Icons.Default.Brush, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = strings.generateButton,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Generated Artworks Gallery
        Text(
            text = "معرض التصاميم المبتكرة (Creative Gallery)",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = GoldPrimary
            )
        )
        Spacer(modifier = Modifier.height(12.dp))

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            generatedImages.forEach { item ->
                ArtworkCard(
                    item = item,
                    strings = strings,
                    onSendToStudio = { onSendToStudio(item) },
                    onDownload = { onShowToast("تم تنزيل التصميم بأعلى دقة!") }
                )
            }
        }
    }
}

@Composable
fun ArtworkCard(
    item: GeneratedImageItem,
    strings: TranslationStrings,
    onSendToStudio: () -> Unit,
    onDownload: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, GoldBorder, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column {
            // Visual Preview
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFF0F0F13))
            ) {
                when {
                    item.isLocalHeroAsset -> {
                        Image(
                            painter = painterResource(id = R.drawable.hero_baraya),
                            contentDescription = item.prompt,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    item.isBrandKitAsset -> {
                        Image(
                            painter = painterResource(id = R.drawable.template_brand_kit),
                            contentDescription = item.prompt,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    item.isLogoAsset -> {
                        Image(
                            painter = painterResource(id = R.drawable.baraya_logo),
                            contentDescription = item.prompt,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    else -> {
                        // Dynamic Procedural Artwork Canvas
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.radialGradient(
                                        listOf(GoldPrimary.copy(alpha = 0.4f), Color(0xFF101015))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = GoldPrimary,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = item.prompt,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = GoldLight,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.padding(horizontal = 20.dp),
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }

                // Aspect Ratio & Style Pill
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Black.copy(alpha = 0.75f))
                        .border(0.8.dp, GoldPrimary, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${item.style} • ${item.aspectRatio}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = GoldLight,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            // Description & Actions
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = item.prompt,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = CairoFontFamily
                    ),
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "تم التوليد: ${item.timestamp}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onSendToStudio,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.OpenInBrowser, contentDescription = null, tint = Color(0xFF101014), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = strings.sendToStudio, color = Color(0xFF101014), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }

                    OutlinedButton(
                        onClick = onDownload,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary)
                    ) {
                        Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = strings.downloadImage, color = GoldPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
