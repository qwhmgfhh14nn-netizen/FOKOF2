package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DesignTemplate
import com.example.data.model.MarketplaceAsset
import com.example.ui.locale.TranslationStrings
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.util.parseColor

@Composable
fun MarketplaceScreen(
    strings: TranslationStrings,
    assets: List<MarketplaceAsset>,
    onRemixTemplate: (DesignTemplate) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All", "Logos", "Social", "Print", "E-Commerce")

    val filteredAssets = if (selectedCategory == "All") {
        assets
    } else {
        assets.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = strings.marketplaceTitle,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = GoldPrimary
            )
        )
        Text(
            text = strings.marketplaceSubtitle,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Categories LazyRow
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(categories) { cat ->
                val isSelected = selectedCategory == cat
                val displayLabel = when (cat) {
                    "All" -> strings.catAll
                    "Logos" -> strings.catLogos
                    "Social" -> strings.catSocial
                    "Print" -> strings.catPrint
                    "E-Commerce" -> strings.catEcommerce
                    else -> cat
                }
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = if (isSelected) GoldPrimary else GoldBorder.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable { selectedCategory = cat },
                    color = if (isSelected) GoldPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = displayLabel,
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

        // Assets List
        LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            items(filteredAssets) { item ->
                MarketplaceAssetCard(
                    item = item,
                    strings = strings,
                    onRemix = { onRemixTemplate(item.template) }
                )
            }
        }
    }
}

@Composable
fun MarketplaceAssetCard(
    item: MarketplaceAsset,
    strings: TranslationStrings,
    onRemix: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, GoldBorder, RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column {
            // Visual Preview Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(parseColor(item.bgStart, Color(0xFF15151C)), parseColor(item.bgEnd, Color(0xFF221C11)))
                        )
                    )
                    .padding(14.dp)
            ) {
                // Creator Badge
                Row(
                    modifier = Modifier.align(Alignment.TopStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = "Verified Creator",
                        tint = GoldPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = item.creatorName,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = GoldLight,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                // Price Tag
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clip(RoundedCornerShape(6.dp))
                        .background(GoldPrimary.copy(alpha = 0.25f))
                        .border(0.8.dp, GoldPrimary, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = item.priceDisplay,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = GoldLight,
                            fontWeight = FontWeight.Bold,
                            fontFamily = CinzelFontFamily
                        )
                    )
                }

                // Centered Preview Abstract
                Box(
                    modifier = Modifier.align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.titleAr,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = GoldPrimary,
                            fontWeight = FontWeight.Bold,
                            fontFamily = CairoFontFamily
                        )
                    )
                }
            }

            // Details & Remix Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${item.rating}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.width(10.dp))

                    Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(text = "${item.downloadsCount}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Button(
                    onClick = onRemix,
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("remix_template_${item.id}")
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = Color(0xFF101014), modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = strings.remixInStudio, color = Color(0xFF101014), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}
