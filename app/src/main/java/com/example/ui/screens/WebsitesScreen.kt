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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserWebsite
import com.example.data.model.WebsiteBuilderSettings
import com.example.data.model.WebsiteTemplate
import com.example.ui.locale.TranslationStrings
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.ErrorRuby
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.SuccessEmerald
import com.example.ui.util.parseColor

@Composable
fun WebsitesScreen(
    strings: TranslationStrings,
    templates: List<WebsiteTemplate>,
    userWebsites: List<UserWebsite>,
    settings: WebsiteBuilderSettings,
    onCreateFromTemplate: (WebsiteTemplate) -> Unit,
    onEditWebsite: (UserWebsite) -> Unit,
    onDeleteWebsite: (String) -> Unit,
    onOpenQrStudio: (UserWebsite) -> Unit = {},
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("ALL") }
    var websiteToDelete by remember { mutableStateOf<UserWebsite?>(null) }

    val categories = listOf(
        "ALL" to "جميع الأنشطة",
        "RESTAURANT" to "مطاعم وكافيهات",
        "PHARMACY" to "صيدليات وعناية",
        "FASHION" to "أزياء وموضة",
        "CLINIC" to "عيادات ومراكز طبية",
        "REAL_ESTATE" to "عقارات وتطوير",
        "SALON" to "صالونات ومراكز تجميل",
        "MOTORS" to "سيارات وصيانة",
        "GROCERY" to "سوبرماركت وبقالة",
        "AGENCY" to "شركات ووكالات",
        "ACADEMY" to "أكاديميات ودورات"
    )

    val filteredTemplates = templates.filter { tpl ->
        val matchesCategory = selectedCategory == "ALL" || tpl.businessType == selectedCategory
        val matchesSearch = tpl.titleAr.contains(searchQuery, ignoreCase = true) ||
                tpl.titleEn.contains(searchQuery, ignoreCase = true) ||
                tpl.taglineAr.contains(searchQuery, ignoreCase = true) ||
                tpl.businessTypeNameAr.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesSearch
    }

    val isLimitReached = userWebsites.size >= settings.freeWebsitesLimit

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = GoldPrimary)
                }
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(
                        text = "منشئ مواقع المحلات والأنشطة",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            fontFamily = CairoFontFamily
                        )
                    )
                    Text(
                        text = "أنشئ موقعاً تجارياً متكاملاً لنشاطك مع طلبات واتساب في دقائق",
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Owner-Defined Free Limit Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isLimitReached) GoldPrimary.copy(alpha = 0.12f) else SuccessEmerald.copy(alpha = 0.12f)
            ),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (isLimitReached) GoldBorder else SuccessEmerald.copy(alpha = 0.4f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(if (isLimitReached) GoldPrimary.copy(alpha = 0.2f) else SuccessEmerald.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = null,
                            tint = if (isLimitReached) GoldPrimary else SuccessEmerald,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "حالة رصيدك: ${userWebsites.size} من أصل ${settings.freeWebsitesLimit} موقع مجاني",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isLimitReached) GoldPrimary else SuccessEmerald
                            )
                        )
                        Text(
                            text = if (isLimitReached)
                                "استهلكت حد المواقع المجانية المحدد من المالك (${settings.freeWebsitesLimit} موقع). يمكنك ترقية موقع إضافي بـ ${settings.extraWebsitePriceEgp} ج.م"
                            else
                                "يمكنك إنشاء موقع تجاري مجاناً بالكامل الآن ونشره فوراً!",
                            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("بحث عن قالب موقع (مطعم، صيدلية، عيادة، أزياء، سيارات...)", fontSize = 11.sp) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp)) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Category Filter Pills
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(categories) { (key, label) ->
                val isSel = selectedCategory == key
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, if (isSel) GoldPrimary else GoldBorder.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .clickable { selectedCategory = key },
                    color = if (isSel) GoldPrimary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = label,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        fontSize = 11.sp,
                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSel) GoldPrimary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Section 1: User's Created Websites (if any)
            if (userWebsites.isNotEmpty()) {
                item {
                    Text(
                        text = "مواقعك المنشأة (${userWebsites.size}):",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GoldPrimary)
                    )
                }

                items(userWebsites) { site ->
                    UserWebsiteCard(
                        website = site,
                        onEdit = { onEditWebsite(site) },
                        onDelete = { websiteToDelete = site },
                        onOpenQr = { onOpenQrStudio(site) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "القوالب التجارية الجاهزة (10 أنشطة حقيقية متكاملة):",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GoldPrimary)
                    )
                }
            } else {
                item {
                    Text(
                        text = "اختر قالباً لنشاطك التجاري وابدأ بتخصيصه:",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GoldPrimary)
                    )
                }
            }

            // Section 2: Templates List
            items(filteredTemplates) { tpl ->
                WebsiteTemplateCard(
                    template = tpl,
                    onSelect = { onCreateFromTemplate(tpl) }
                )
            }
        }
    }

    // Modal: Delete Website Confirmation
    websiteToDelete?.let { site ->
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { websiteToDelete = null },
            title = { Text("تأكيد حذف الموقع", color = ErrorRuby, fontWeight = FontWeight.Bold) },
            text = { Text("هل أنت متأكد من حذف موقع \"${site.siteTitle}\"؟ لا يمكن التراجع عن هذا الإجراء.") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteWebsite(site.id)
                        websiteToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorRuby)
                ) {
                    Text("حذف الموقع", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                androidx.compose.material3.TextButton(onClick = { websiteToDelete = null }) {
                    Text("إلغاء")
                }
            }
        )
    }
}

@Composable
fun WebsiteTemplateCard(
    template: WebsiteTemplate,
    onSelect: () -> Unit
) {
    val themeColor = parseColor(template.primaryColorHex, GoldPrimary)
    val darkBg = parseColor(template.darkBgHex, Color(0xFF101014))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .border(1.2.dp, themeColor.copy(alpha = 0.5f), RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column {
            // Visual Header Mockup Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(themeColor.copy(alpha = 0.35f), darkBg)
                        )
                    )
                    .padding(14.dp)
            ) {
                Column(modifier = Modifier.align(Alignment.BottomStart)) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(themeColor)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = template.businessTypeNameAr,
                            fontSize = 10.sp,
                            color = Color(0xFF101014),
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = template.titleAr,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = CairoFontFamily
                        )
                    )
                    Text(
                        text = template.taglineAr,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 11.sp
                        ),
                        maxLines = 1
                    )
                }

                // Price Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (template.priceEgp == 0) SuccessEmerald else GoldPrimary)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (template.priceEgp == 0) "متاح مجاناً" else "${template.priceEgp} ج.م",
                        fontSize = 11.sp,
                        color = Color(0xFF101014),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Description and Feature Badges
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = template.descriptionAr,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Feature Highlights Pills
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (template.featureToggles.featureWhatsappOrders) {
                        WebsiteFeatureChip(text = "طلبات واتساب", color = SuccessEmerald)
                    }
                    if (template.featureToggles.featureProductCatalog) {
                        WebsiteFeatureChip(text = "كتالوج وأسعار", color = GoldPrimary)
                    }
                    if (template.featureToggles.featureBookingSystem) {
                        WebsiteFeatureChip(text = "حجز مواعيد", color = Color(0xFF42A5F5))
                    }
                    if (template.featureToggles.featureGoogleMaps) {
                        WebsiteFeatureChip(text = "خريطة الموقع", color = GoldLight)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Create Website CTA
                Button(
                    onClick = onSelect,
                    colors = ButtonDefaults.buttonColors(containerColor = themeColor),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.Language, contentDescription = null, tint = Color(0xFF101014), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "تخصيص وإنشاء موقعي من هذا القالب",
                        color = Color(0xFF101014),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun UserWebsiteCard(
    website: UserWebsite,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onOpenQr: () -> Unit = {}
) {
    val themeColor = parseColor(website.primaryColorHex, GoldPrimary)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(themeColor.copy(alpha = 0.2f))
                        .border(1.dp, themeColor, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.Language, contentDescription = null, tint = themeColor, modifier = Modifier.size(24.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = website.siteTitle,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontFamily = CairoFontFamily)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(SuccessEmerald.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("نشط ومباشر", color = SuccessEmerald, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Text(
                        text = "رابط: ${website.siteSlug}.baraya.site • ${website.items.size} أصناف مسجلة",
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        WebsiteFeatureChip("👁️ زيارات نشطة", GoldPrimary)
                        WebsiteFeatureChip("💬 طلبات واتساب", SuccessEmerald)
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onOpenQr, modifier = Modifier.size(34.dp)) {
                    Icon(imageVector = Icons.Default.QrCode2, contentDescription = "QR Code", tint = GoldPrimary, modifier = Modifier.size(18.dp))
                }
                IconButton(onClick = onEdit, modifier = Modifier.size(34.dp)) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", tint = GoldPrimary, modifier = Modifier.size(18.dp))
                }
                IconButton(onClick = onDelete, modifier = Modifier.size(34.dp)) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = ErrorRuby, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
fun WebsiteFeatureChip(text: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color.copy(alpha = 0.15f))
            .border(0.8.dp, color.copy(alpha = 0.35f), RoundedCornerShape(6.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(text = text, color = color, fontSize = 9.sp, fontWeight = FontWeight.Bold)
    }
}
