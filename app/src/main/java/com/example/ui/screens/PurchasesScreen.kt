package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.data.model.PurchasedTemplateItem
import com.example.ui.locale.TranslationStrings
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.SuccessEmerald

@Composable
fun PurchasesScreen(
    strings: TranslationStrings,
    purchases: List<PurchasedTemplateItem>,
    onOpenInStudio: (DesignTemplate) -> Unit,
    onDownloadFiles: (PurchasedTemplateItem) -> Unit,
    onExploreTemplates: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Page Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(GoldPrimary.copy(alpha = 0.2f))
                    .border(1.dp, GoldPrimary, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingBag,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = strings.purchasesTitle,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary
                    )
                )
                Text(
                    text = strings.purchasesSubtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (purchases.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ReceiptLong,
                        contentDescription = null,
                        tint = GoldPrimary.copy(alpha = 0.4f),
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = strings.noPurchasesYet,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onExploreTemplates,
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = strings.exploreTemplatesPrompt,
                            color = Color(0xFF101014),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(purchases) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .border(1.dp, GoldBorder, RoundedCornerShape(14.dp))
                            .testTag("purchase_card_${item.orderId}"),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            // Top Row: Order ID & Date
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = "Verified Purchase",
                                        tint = SuccessEmerald,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "${strings.orderNumber}: ${item.orderId}",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = GoldLight,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = CinzelFontFamily
                                        )
                                    )
                                }

                                Text(
                                    text = item.purchaseDate,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Middle: Title & Price
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.titleAr,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = CairoFontFamily
                                    )
                                )

                                Text(
                                    text = "${item.priceEgp} ${strings.currencyEgp}",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        color = GoldPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = CinzelFontFamily
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "${strings.paymentMethod}: ${item.paymentMethod}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            // CTAs: Open in Studio & Download
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Button(
                                    onClick = { onOpenInStudio(item.template) },
                                    modifier = Modifier.weight(1f).height(38.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = Color(0xFF101014), modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = strings.openInStudio, color = Color(0xFF101014), fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                }

                                OutlinedButton(
                                    onClick = { onDownloadFiles(item) },
                                    modifier = Modifier.weight(1f).height(38.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary)
                                ) {
                                    Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = strings.downloadFiles, color = GoldPrimary, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
