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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.ui.components.TemplateVisualCanvas
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import com.example.data.model.CategoryModel
import com.example.data.model.DesignTemplate
import com.example.data.model.TemplateBundle
import com.example.ui.locale.TranslationStrings
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.util.parseColor

@Composable
fun TemplatesScreen(
    strings: TranslationStrings,
    templates: List<DesignTemplate>,
    categories: List<CategoryModel> = emptyList(),
    bundles: List<TemplateBundle>,
    favoriteIds: Set<String>,
    purchasedTemplateIds: Set<String>,
    onPreviewTemplate: (DesignTemplate) -> Unit,
    onUseTemplate: (DesignTemplate) -> Unit,
    onBuyTemplate: (DesignTemplate) -> Unit,
    onBuyBundle: (TemplateBundle) -> Unit,
    onToggleFavorite: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("ALL") }

    val dynamicCategories = if (categories.isNotEmpty()) {
        categories.map { it.nameAr to it.nameAr }
    } else {
        listOf(
            "مطاعم ومأكولات" to "مطاعم ومأكولات",
            "أزياء وجمال" to "أزياء وجمال",
            "سيارات ومحركات" to "سيارات ومحركات",
            "عقارات وإنشاءات" to "عقارات وإنشاءات",
            "سوشيال ميديا" to "سوشيال ميديا",
            "أعمال وتسويق" to "أعمال وتسويق",
            "مناسبات واحتفالات" to "مناسبات واحتفالات"
        )
    }

    val filterOptions = listOf(
        "ALL" to strings.filterAll,
        "FREE" to "قوالب مجانية",
        "PRO" to "قوالب مدفوعة PRO",
        "BUNDLES" to "باقات التوفير (Bundles)"
    ) + dynamicCategories

    val filtered = templates.filter { tpl ->
        val matchesSearch = tpl.titleAr.contains(searchQuery, ignoreCase = true) ||
                tpl.titleEn.contains(searchQuery, ignoreCase = true) ||
                tpl.category.contains(searchQuery, ignoreCase = true)

        val matchesFilter = when (selectedFilter) {
            "FREE" -> !tpl.isPremium || tpl.priceEgp == 0
            "PRO" -> tpl.isPremium && tpl.priceEgp > 0
            "BUNDLES" -> false
            "ALL" -> true
            else -> tpl.category.equals(selectedFilter, ignoreCase = true)
        }
        matchesSearch && matchesFilter
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = strings.templatesTitle,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary
                    )
                )
                Text(
                    text = if (templates.isEmpty())
                        "مكتبة القوالب فارغة حالياً (بانتظار رفع ونشر تصاميم المالك المعتمدة)"
                    else
                        "إجمالي القوالب المتاحة: ${templates.size} قالب (${templates.count { !it.isPremium || it.priceEgp == 0 }} مجاناً + باقات ومميز)",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text(strings.searchTemplates, fontSize = 12.sp) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = GoldPrimary)
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("templates_search_field")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Filter Pills
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filterOptions) { (key, label) ->
                val isSelected = selectedFilter == key
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = if (isSelected) GoldPrimary else GoldBorder.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable { selectedFilter = key },
                    color = if (isSelected) GoldPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = label,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurface,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Content
        if (selectedFilter == "BUNDLES") {
            // Bundles List
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "باقات قوالب التوفير بأسعار مخفضة بالجنيه المصري (EGP):",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GoldPrimary)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }
                items(bundles) { bundle ->
                    BundleCard(bundle = bundle, strings = strings, onBuy = { onBuyBundle(bundle) })
                }
            }
        } else {
            // Templates List
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // If on ALL and no search, show a quick Bundles Teaser Banner
                if (selectedFilter == "ALL" && searchQuery.isBlank()) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, GoldPrimary, RoundedCornerShape(12.dp))
                                .clickable { selectedFilter = "BUNDLES" },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp).fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(imageVector = Icons.Default.LocalOffer, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(24.dp))
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(text = "باقات التوفير الكبرى: 5 قوالب بـ 20 ج.م وحتى 100 قالب", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                                        Text(text = "وفّر حتى 60% مع باقات القوالب الشاملة للمصممين والشركات", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                                Text(text = "عرض الباقات ↗", color = GoldPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                if (selectedFilter != "BUNDLES" && filtered.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.4f))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(28.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Folder,
                                    contentDescription = null,
                                    tint = GoldPrimary,
                                    modifier = Modifier.size(48.dp)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = if (templates.isEmpty()) "مكتبة القوالب فارغة حالياً" else "لا توجد قوالب مطابقة للتصفية",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = GoldPrimary,
                                        fontFamily = CairoFontFamily
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = if (templates.isEmpty())
                                        "تم تفريغ كافة القوالب الوهمية السابقة بنجاح. سيبدأ المالك قريباً برفع تصاميم احترافية حقيقية بالصور ومصادر التصميم.\nيمكن للمالك الدخول إلى لوحة إدارة القوالب من حسابه والبدء بنشر التصاميم."
                                    else
                                        "لا توجد تصاميم منشورة حالياً تطابق معايير البحث أو هذا التصنيف.",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                    )
                                )
                            }
                        }
                    }
                }

                items(filtered) { tpl ->
                    val isPurchased = purchasedTemplateIds.contains(tpl.id)
                    TemplateDetailedCard(
                        template = tpl,
                        strings = strings,
                        isFavorite = favoriteIds.contains(tpl.id),
                        isPurchased = isPurchased,
                        onPreview = { onPreviewTemplate(tpl) },
                        onUse = { onUseTemplate(tpl) },
                        onBuy = { onBuyTemplate(tpl) },
                        onToggleFavorite = { onToggleFavorite(tpl.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun BundleCard(
    bundle: TemplateBundle,
    strings: TranslationStrings,
    onBuy: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, if (bundle.isPopular) GoldPrimary else GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = bundle.titleAr,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontFamily = CairoFontFamily)
                    )
                    if (bundle.isPopular) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(GoldPrimary)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("الأكثر طلباً", fontSize = 10.sp, color = Color(0xFF101014), fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${bundle.originalPriceEgp} ج.م",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${bundle.priceEgp} ${strings.currencyEgp}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = GoldPrimary,
                            fontWeight = FontWeight.Bold,
                            fontFamily = CinzelFontFamily
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = bundle.descriptionAr,
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onBuy,
                modifier = Modifier.fillMaxWidth().height(42.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Payment, contentDescription = null, tint = Color(0xFF101014), modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "شراء الباقة الآن (${bundle.priceEgp} ج.م)",
                    color = Color(0xFF101014),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun TemplateDetailedCard(
    template: DesignTemplate,
    strings: TranslationStrings,
    isFavorite: Boolean,
    isPurchased: Boolean,
    onPreview: () -> Unit,
    onUse: () -> Unit,
    onBuy: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, GoldBorder, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column {
            // Visual Preview Block with Rich Graphic Canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .clickable { onPreview() }
            ) {
                // Rich graphical category-specific canvas
                TemplateVisualCanvas(
                    template = template,
                    modifier = Modifier.fillMaxSize()
                )

                // Price Badge (EGP)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            when {
                                isPurchased -> Color(0xFF10B981)
                                template.priceEgp > 0 -> GoldPrimary
                                else -> Color(0xFF10B981)
                            }
                        )
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = when {
                            isPurchased -> "مملوك لك"
                            template.priceEgp > 0 -> "${template.proTier?.titleAr ?: "PRO"} • ${template.priceEgp} ${strings.currencyEgp}"
                            else -> strings.freeBadge
                        },
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFF101014),
                            fontWeight = FontWeight.Bold,
                            fontFamily = CinzelFontFamily
                        )
                    )
                }

                // Favorite Heart Button
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.4f))
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color(0xFFEF4444) else Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Details and CTAs
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = template.titleAr,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = CairoFontFamily
                            ),
                            maxLines = 1
                        )
                        Text(
                            text = "${template.category} • أبعاد ${template.aspectRatio} • ${template.elements.size} طبقات",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(text = "${template.rating}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Preview Button
                    OutlinedButton(
                        onClick = onPreview,
                        modifier = Modifier.weight(1f).height(40.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary)
                    ) {
                        Icon(imageVector = Icons.Default.Visibility, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = strings.previewTemplate, color = GoldPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    // Main Action (Use if Free or Purchased, Buy if PRO)
                    val canDirectlyUse = !template.isPremium || template.priceEgp == 0 || isPurchased
                    Button(
                        onClick = if (canDirectlyUse) onUse else onBuy,
                        modifier = Modifier.weight(1.3f).height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = if (canDirectlyUse) Icons.Default.Edit else Icons.Default.Payment,
                            contentDescription = null,
                            tint = Color(0xFF101014),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (canDirectlyUse) strings.useInStudio else "${strings.buyTemplate} (${template.priceEgp} ${strings.currencyEgp})",
                            color = Color(0xFF101014),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
