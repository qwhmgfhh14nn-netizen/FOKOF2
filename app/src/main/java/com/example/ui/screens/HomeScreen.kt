package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.DesignTemplate
import com.example.ui.AppNavDestination
import com.example.ui.components.TemplateVisualCanvas
import com.example.ui.locale.TranslationStrings
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.util.parseColor

@Composable
fun HomeScreen(
    strings: TranslationStrings,
    searchQuery: String,
    templates: List<DesignTemplate>,
    favoriteIds: Set<String>,
    onSearchChange: (String) -> Unit,
    onNavigate: (AppNavDestination) -> Unit,
    onPreviewTemplate: (DesignTemplate) -> Unit,
    onUseTemplate: (DesignTemplate) -> Unit,
    onBuyTemplate: (DesignTemplate) -> Unit,
    onToggleFavorite: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val freeTemplates = templates.filter { !it.isPremium || it.priceEgp == 0 }
    val proTemplates = templates.filter { it.isPremium && it.priceEgp > 0 }

    val popularCategories = listOf(
        strings.catLogos to Icons.Default.Brush,
        strings.catSocial to Icons.Default.Campaign,
        strings.catBranding to Icons.Default.ColorLens,
        strings.catPrint to Icons.Default.LocalOffer,
        strings.catPosters to Icons.Default.AutoAwesome
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            placeholder = {
                Text(
                    text = strings.homeSearchPlaceholder,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = GoldPrimary
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("home_search_bar"),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GoldPrimary,
                unfocusedBorderColor = GoldBorder
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Hero Featured Offers Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .border(1.2.dp, GoldBorder, RoundedCornerShape(18.dp))
                .testTag("featured_offer_banner"),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hero_baraya),
                    contentDescription = "Baraya Hero",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Dark Gradient Scrim
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f))
                            )
                        )
                )

                // Banner Overlay Copy & CTA
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(GoldPrimary)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "عرض خاص ٥٠٪",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF101014)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = strings.featuredOffersTitle,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontFamily = CairoFontFamily
                        )
                    )
                    Text(
                        text = strings.featuredOffersSubtitle,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = GoldLight,
                            fontSize = 11.sp
                        ),
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = { onNavigate(AppNavDestination.TEMPLATES) },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(
                                text = "تصفح العروض الآن",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF101014)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // AI Tools Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = strings.aiToolsTitle,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = GoldPrimary
                )
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AiToolMiniCard(
                title = "توليد الصور",
                subtitle = "ذكاء اصطناعي",
                icon = Icons.Default.AutoAwesome,
                onClick = { onNavigate(AppNavDestination.AI_IMAGE) },
                modifier = Modifier.weight(1f)
            )
            AiToolMiniCard(
                title = "كتابة الإعلانات",
                subtitle = "نصوص تسويقية",
                icon = Icons.Default.Campaign,
                onClick = { onNavigate(AppNavDestination.AI_MARKETING) },
                modifier = Modifier.weight(1f)
            )
            AiToolMiniCard(
                title = "الهوية البصرية",
                subtitle = "ألوان وشعارات",
                icon = Icons.Default.ColorLens,
                onClick = { onNavigate(AppNavDestination.BRAND_KIT) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AiToolMiniCard(
                title = "منشئ المواقع",
                subtitle = "10 قوالب للمحلات",
                icon = Icons.Default.Language,
                onClick = { onNavigate(AppNavDestination.WEBSITES) },
                modifier = Modifier.weight(1f)
            )
            AiToolMiniCard(
                title = "استوديو الموك آب",
                subtitle = "معاينة 3D واقعية",
                icon = Icons.Default.ShoppingBag,
                onClick = { onNavigate(AppNavDestination.MOCKUP_STUDIO) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        // Popular Categories
        Text(
            text = strings.popularCategoriesTitle,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = GoldPrimary
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(popularCategories) { (catTitle, icon) ->
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, GoldBorder.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                        .clickable { onNavigate(AppNavDestination.TEMPLATES) },
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = icon, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = catTitle,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // PRO Templates Section (With Egyptian Pound EGP Prices!)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.WorkspacePremium,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = strings.proTemplatesTitle,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary
                    )
                )
            }

            Text(
                text = strings.viewAll,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = GoldPrimary,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.clickable { onNavigate(AppNavDestination.TEMPLATES) }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (templates.isEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.4f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "مكتبة القوالب جاهزة لاستقبال تصاميم المالك المعتمدة",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GoldPrimary, fontFamily = CairoFontFamily)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "تم تفريغ القوالب الوهمية السابقة بنجاح. يمكن للمالك رفع التصاميم الحقيقية بالصور ومصادر التصميم وتحديد أسعارها بالجنيه المصري عبر لوحة تحكم المالك.\nيمكنك البدء بإنشاء وتعديل تصميمك الآن في الاستوديو.",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { onNavigate(AppNavDestination.STUDIO) },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = Color(0xFF101014), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("فتح استوديو التصميم والبدء من الصفر", color = Color(0xFF101014), fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            if (proTemplates.isNotEmpty()) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    items(proTemplates) { tpl ->
                        HomeTemplateCard(
                            template = tpl,
                            strings = strings,
                            isFavorite = favoriteIds.contains(tpl.id),
                            onPreview = { onPreviewTemplate(tpl) },
                            onUse = { onUseTemplate(tpl) },
                            onBuy = { onBuyTemplate(tpl) },
                            onToggleFavorite = { onToggleFavorite(tpl.id) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            if (freeTemplates.isNotEmpty()) {
                // Free Templates Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = strings.freeTemplatesTitle,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary
                        )
                    )

                    Text(
                        text = strings.viewAll,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = GoldPrimary,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.clickable { onNavigate(AppNavDestination.TEMPLATES) }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    items(freeTemplates) { tpl ->
                        HomeTemplateCard(
                            template = tpl,
                            strings = strings,
                            isFavorite = favoriteIds.contains(tpl.id),
                            onPreview = { onPreviewTemplate(tpl) },
                            onUse = { onUseTemplate(tpl) },
                            onBuy = { onUseTemplate(tpl) },
                            onToggleFavorite = { onToggleFavorite(tpl.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AiToolMiniCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(GoldPrimary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                maxLines = 1
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 9.sp
                ),
                maxLines = 1
            )
        }
    }
}

@Composable
fun HomeTemplateCard(
    template: DesignTemplate,
    strings: TranslationStrings,
    isFavorite: Boolean,
    onPreview: () -> Unit,
    onUse: () -> Unit,
    onBuy: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(220.dp)
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, GoldBorder, RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column {
            // Visual Preview Thumbnail Box with Rich Graphic Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(135.dp)
                    .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
                    .clickable { onPreview() }
            ) {
                TemplateVisualCanvas(
                    template = template,
                    modifier = Modifier.fillMaxSize()
                )

                // Price Badge (Egyptian Pounds)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(6.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (template.priceEgp > 0) GoldPrimary else Color(0xFF10B981))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = if (template.priceEgp > 0) "${template.priceEgp} ${strings.currencyEgp}" else strings.freeBadge,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFF101014),
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    )
                }

                // Favorite Heart Button
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.4f))
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color(0xFFEF4444) else Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // Info & CTAs
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = template.titleAr,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = CairoFontFamily,
                        fontSize = 12.sp
                    ),
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(text = "${template.rating}", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }

                    Text(
                        text = "${template.salesCount} مبيعة",
                        fontSize = 9.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Button(
                        onClick = if (template.priceEgp > 0) onBuy else onUse,
                        modifier = Modifier.weight(1f).height(32.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = if (template.priceEgp > 0) "شراء" else "تعديل",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF101014)
                        )
                    }

                    IconButton(
                        onClick = onPreview,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = "Preview",
                            tint = GoldPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
