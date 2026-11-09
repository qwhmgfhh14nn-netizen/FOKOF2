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
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Tune
import com.example.data.local.PaymentEntity
import com.example.data.model.CategoryModel
import com.example.data.model.DesignTemplate
import com.example.data.model.DiscountCoupon
import com.example.data.model.ProTier
import com.example.data.model.SubscriptionPlan
import com.example.data.model.TemplateBundle
import com.example.data.model.WebsiteBuilderSettings
import com.example.data.model.WebsiteFeatureToggles
import com.example.data.model.WebsiteTemplate
import com.example.ui.components.AdminTemplateEditDialog
import com.example.ui.components.DeleteConfirmDialog
import com.example.ui.components.TemplateVisualCanvas
import com.example.ui.locale.TranslationStrings
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.ErrorRuby
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.SuccessEmerald

@Composable
fun AdminScreen(
    strings: TranslationStrings,
    payments: List<PaymentEntity>,
    templates: List<DesignTemplate>,
    categories: List<CategoryModel> = emptyList(),
    bundles: List<TemplateBundle>,
    subscriptionPlans: List<SubscriptionPlan> = emptyList(),
    coupons: List<DiscountCoupon>,
    tierPrices: Map<ProTier, Int>,
    onApprovePayment: (String) -> Unit,
    onRejectPayment: (String) -> Unit,
    onAddTemplate: (
        titleAr: String,
        titleEn: String,
        descriptionAr: String,
        descriptionEn: String,
        category: String,
        aspectRatio: String,
        isPremium: Boolean,
        pricingType: String,
        proTier: ProTier?,
        priceEgp: Int,
        bgStartHex: String,
        bgEndHex: String,
        isPublished: Boolean,
        imageUri: String?,
        designFileUri: String?,
        designFileName: String?
    ) -> Unit,
    onUpdateTemplate: (DesignTemplate) -> Unit,
    onDuplicateTemplate: (DesignTemplate) -> Unit,
    onDeleteTemplate: (String) -> Unit,
    onUpdatePrice: (String, Int) -> Unit,
    onTogglePublish: (String, Boolean) -> Unit,
    onAddCategory: (nameAr: String, nameEn: String, icon: String) -> Unit = { _, _, _ -> },
    onUpdateCategory: (id: String, nameAr: String, nameEn: String) -> Unit = { _, _, _ -> },
    onDeleteCategory: (id: String) -> Unit = {},
    onUpdateBundlePrice: (String, Int) -> Unit,
    onUpdateSubscriptionPrice: (String, Int) -> Unit = { _, _ -> },
    onUpdateTierPrice: (ProTier, Int) -> Unit,
    onAddCoupon: (String, Int, String) -> Unit,
    onRemoveCoupon: (String) -> Unit,
    onPreviewTemplate: (DesignTemplate) -> Unit,
    onCloseAdmin: () -> Unit,
    onLockAdmin: () -> Unit = onCloseAdmin,
    websiteTemplates: List<WebsiteTemplate> = emptyList(),
    websiteSettings: WebsiteBuilderSettings = WebsiteBuilderSettings(),
    onUpdateWebsitePrice: (String, Int) -> Unit = { _, _ -> },
    onUpdateWebsiteFeatures: (String, WebsiteFeatureToggles) -> Unit = { _, _ -> },
    onUpdateWebsiteSettings: (Int, Int, Int) -> Unit = { _, _, _ -> },
    modifier: Modifier = Modifier
) {
    var adminTab by remember { mutableStateOf(0) } // Default to Templates Management (Tab 0)
    var showTemplateDialog by remember { mutableStateOf(false) }
    var editingTemplateTarget by remember { mutableStateOf<DesignTemplate?>(null) }
    var templateToDelete by remember { mutableStateOf<DesignTemplate?>(null) }
    
    // Category management states
    var showAddCategoryDialog by remember { mutableStateOf(false) }
    var editingCategoryTarget by remember { mutableStateOf<CategoryModel?>(null) }
    var categoryToDelete by remember { mutableStateOf<CategoryModel?>(null) }
    var newCategoryNameAr by remember { mutableStateOf("") }
    var newCategoryNameEn by remember { mutableStateOf("") }
    
    var showAddCouponDialog by remember { mutableStateOf(false) }
    var newCouponCode by remember { mutableStateOf("") }
    var newCouponDiscount by remember { mutableStateOf("25") }
    var newCouponDesc by remember { mutableStateOf("") }

    var templateSearchQuery by remember { mutableStateOf("") }
    var selectedCategoryFilter by remember { mutableStateOf("ALL") }
    var statusFilter by remember { mutableStateOf("ALL") } // ALL, PUBLISHED, DRAFTS

    val pendingCount = payments.count { it.status == "PENDING" }
    val approvedCount = payments.count { it.status == "APPROVED" }

    val filteredTemplates = templates.filter { tpl ->
        val matchesSearch = tpl.titleAr.contains(templateSearchQuery, ignoreCase = true) ||
                tpl.titleEn.contains(templateSearchQuery, ignoreCase = true) ||
                tpl.descriptionAr.contains(templateSearchQuery, ignoreCase = true) ||
                tpl.category.contains(templateSearchQuery, ignoreCase = true)
        val matchesCat = if (selectedCategoryFilter == "ALL") true else tpl.category == selectedCategoryFilter
        val matchesStatus = when (statusFilter) {
            "PUBLISHED" -> tpl.isPublished
            "DRAFTS" -> !tpl.isPublished
            else -> true
        }
        matchesSearch && matchesCat && matchesStatus
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Admin Header with Exit Button & Owner Badge
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(GoldPrimary.copy(alpha = 0.2f))
                        .border(1.5.dp, GoldPrimary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.Security, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(22.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "لوحة إدارة المالك | باريا للذكاء الاصطناعي",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = GoldPrimary, fontFamily = CairoFontFamily)
                    )
                    Text(
                        text = "المهندس محمد برايا • تصريح المالك الكامل (Owner Full Control)",
                        style = MaterialTheme.typography.labelSmall.copy(color = SuccessEmerald, fontWeight = FontWeight.Bold)
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                // Lock Admin Panel
                IconButton(
                    onClick = onLockAdmin,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock", tint = GoldLight, modifier = Modifier.size(18.dp))
                }

                // Close / Back
                IconButton(
                    onClick = onCloseAdmin,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = GoldPrimary, modifier = Modifier.size(20.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Metrics Overview
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AdminMetricCard(title = "إجمالي القوالب", value = "${templates.size}", subValue = "قالب بالمكتبة", icon = Icons.Default.Folder, modifier = Modifier.weight(1f))
            AdminMetricCard(title = "طلبات معلقة", value = "$pendingCount", subValue = "تحتاج مراجعة", icon = Icons.Default.PendingActions, isAlert = pendingCount > 0, modifier = Modifier.weight(1f))
            AdminMetricCard(title = "مبيعات معتمدة", value = "$approvedCount", subValue = "معاملة ناجحة", icon = Icons.Default.MonetizationOn, modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Admin Navigation Tabs (6 Sections)
        val adminTabs = listOf(
            "إدارة القوالب والأسعار" to Icons.Default.Folder,
            "قوالب المواقع والميزات" to Icons.Default.Language,
            "إدارة الفئات" to Icons.Default.Category,
            "الباقات والاشتراكات" to Icons.Default.MonetizationOn,
            "أكواد الخصم والعروض" to Icons.Default.Discount,
            "الطلبات والمدفوعات" to Icons.AutoMirrored.Filled.ReceiptLong
        )

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(adminTabs.indices.toList()) { index ->
                val (title, icon) = adminTabs[index]
                val isSelected = adminTab == index
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, if (isSelected) GoldPrimary else GoldBorder.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .clickable { adminTab = index },
                    color = if (isSelected) GoldPrimary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = icon, contentDescription = null, tint = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // TAB CONTENT
        when (adminTab) {
            // TAB 0: TEMPLATES MANAGEMENT
            0 -> {
                Column(modifier = Modifier.weight(1f)) {
                    // Actions row: Add Template & Search
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = templateSearchQuery,
                            onValueChange = { templateSearchQuery = it },
                            placeholder = { Text("بحث بالاسم، الوصف، أو التصنيف...", fontSize = 11.sp) },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp)) },
                            modifier = Modifier.weight(1f).height(46.dp),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                editingTemplateTarget = null
                                showTemplateDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(46.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF101014), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("إضافة قالب جديد", color = Color(0xFF101014), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Status Filters: All, Published, Drafts
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        listOf(
                            "ALL" to "الكل (${templates.size})",
                            "PUBLISHED" to "منشور علناً (${templates.count { it.isPublished }})",
                            "DRAFTS" to "مسودات المالك (${templates.count { !it.isPublished }})"
                        ).forEach { (stKey, label) ->
                            val isSel = statusFilter == stKey
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .border(1.dp, if (isSel) GoldPrimary else GoldBorder.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                                    .clickable { statusFilter = stKey },
                                color = if (isSel) GoldPrimary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = label,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    fontSize = 10.sp,
                                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSel) GoldPrimary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Category Filter Pills (Dynamic from database categories)
                    val filterCategories = listOf("ALL") + (if (categories.isNotEmpty()) categories.map { it.nameAr } else listOf(
                        "مطاعم ومأكولات", "أزياء وجمال", "سيارات ومحركات", "عقارات وإنشاءات", "سوشيال ميديا", "أعمال وتسويق", "مناسبات واحتفالات"
                    ))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(filterCategories) { cat ->
                            val isSel = selectedCategoryFilter == cat
                            val label = if (cat == "ALL") "جميع الفئات" else cat
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .border(0.8.dp, if (isSel) GoldPrimary else GoldBorder.copy(alpha = 0.25f), RoundedCornerShape(6.dp))
                                    .clickable { selectedCategoryFilter = cat },
                                color = if (isSel) GoldPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = label,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    fontSize = 10.sp,
                                    color = if (isSel) GoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "عدد القوالب المطابقة: ${filteredTemplates.size} قالب",
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    if (filteredTemplates.isEmpty()) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.4f))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(imageVector = Icons.Default.Folder, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(44.dp))
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = if (templates.isEmpty()) "مكتبة القوالب فارغة حالياً" else "لا توجد قوالب مطابقة للبحث",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = GoldPrimary, fontFamily = CairoFontFamily)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = if (templates.isEmpty())
                                        "تم تفريغ القوالب الوهمية السابقة بنجاح. بصفتك المالك، يمكنك الآن رفع تصاميمك الحقيقية بالصور وملفات التصميم وتحديد أسعارها بالجنيه المصري (مجاني أو مدفوع)."
                                    else
                                        "جرب تعديل كلمة البحث أو اختيار تصنيف آخر.",
                                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                                )
                                if (templates.isEmpty()) {
                                    Spacer(modifier = Modifier.height(14.dp))
                                    Button(
                                        onClick = {
                                            editingTemplateTarget = null
                                            showTemplateDialog = true
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF101014), modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("إضافة أول تصميم حقيقي للمكتبة", color = Color(0xFF101014), fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(filteredTemplates) { tpl ->
                                AdminTemplateCard(
                                    template = tpl,
                                    strings = strings,
                                    onEdit = {
                                        editingTemplateTarget = tpl
                                        showTemplateDialog = true
                                    },
                                    onDuplicate = { onDuplicateTemplate(tpl) },
                                    onDelete = { templateToDelete = tpl },
                                    onTogglePublish = { isPub -> onTogglePublish(tpl.id, isPub) },
                                    onUpdatePrice = { newPrice -> onUpdatePrice(tpl.id, newPrice) },
                                    onPreview = { onPreviewTemplate(tpl) }
                                )
                            }
                        }
                    }
                }
            }

            // TAB 1: WEBSITES & LIMITS MANAGEMENT
            1 -> {
                WebsiteBuilderManagementTab(
                    templates = websiteTemplates,
                    settings = websiteSettings,
                    onUpdatePrice = onUpdateWebsitePrice,
                    onUpdateFeatures = onUpdateWebsiteFeatures,
                    onUpdateSettings = onUpdateWebsiteSettings,
                    modifier = Modifier.weight(1f)
                )
            }

            // TAB 2: CATEGORIES MANAGEMENT
            2 -> {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "إدارة فئات وتصنيفات القوالب:",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GoldPrimary)
                            )
                            Text(
                                text = "إضافة وتعديل وحذف فئات التصاميم في المكتبة:",
                                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                            )
                        }
                        Button(
                            onClick = {
                                newCategoryNameAr = ""
                                newCategoryNameEn = ""
                                showAddCategoryDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = Color(0xFF101014), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("إضافة فئة جديدة", color = Color(0xFF101014), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(categories) { cat ->
                            val templateCount = templates.count { it.category.equals(cat.nameAr, true) || it.category.equals(cat.nameEn, true) }
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                                border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.4f))
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
                                                .size(38.dp)
                                                .clip(CircleShape)
                                                .background(GoldPrimary.copy(alpha = 0.15f)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(imageVector = Icons.Default.Category, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(18.dp))
                                        }
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(
                                                text = cat.nameAr,
                                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontFamily = CairoFontFamily)
                                            )
                                            Text(
                                                text = "${cat.nameEn} • $templateCount قالب مسجل",
                                                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                                            )
                                        }
                                    }

                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        IconButton(
                                            onClick = { editingCategoryTarget = cat },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(Icons.Default.Edit, contentDescription = "Edit Category", tint = GoldPrimary, modifier = Modifier.size(16.dp))
                                        }
                                        IconButton(
                                            onClick = { categoryToDelete = cat },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(Icons.Default.Delete, contentDescription = "Delete Category", tint = ErrorRuby, modifier = Modifier.size(16.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // TAB 3: BUNDLES & SUBSCRIPTIONS MANAGEMENT
            3 -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Subscription Plans (Monthly, Annual, Lifetime)
                    item {
                        Text(text = "إدارة خطط الاشتراكات (الأسعار بالجنيه المصري EGP):", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GoldPrimary))
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    items(subscriptionPlans) { plan ->
                        var planPriceEdit by remember(plan.priceEgp) { mutableStateOf(plan.priceEgp.toString()) }
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if (plan.isPopular) GoldPrimary else GoldBorder.copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = plan.nameAr, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                                        if (plan.isPopular) {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Box(modifier = Modifier.clip(RoundedCornerShape(4.dp)).background(GoldPrimary).padding(horizontal = 4.dp, vertical = 1.dp)) {
                                                Text("الأكثر توفيراً", fontSize = 9.sp, color = Color(0xFF101014), fontWeight = FontWeight.Bold)
                                            }
                                        }
                                    }
                                    Text(text = "${plan.descriptionAr} • دورة الفوترة: ${plan.billingCycle}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    OutlinedTextField(
                                        value = planPriceEdit,
                                        onValueChange = { planPriceEdit = it },
                                        modifier = Modifier.width(90.dp).height(46.dp),
                                        singleLine = true,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Button(
                                        onClick = {
                                            planPriceEdit.toIntOrNull()?.let { onUpdateSubscriptionPrice(plan.id, it) }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("تحديث", color = Color(0xFF101014), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }

                    // Template Bundles (5, 10, 20, 50, 100 designs)
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "إدارة باقات القوالب المجمعة (Bundles):", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GoldPrimary))
                        Text(text = "تعديل أسعار حزم التنزيل بالجنيه المصري:", style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                    }

                    items(bundles) { bundle ->
                        var bundlePriceEdit by remember(bundle.priceEgp) { mutableStateOf(bundle.priceEgp.toString()) }
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = bundle.titleAr, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                                    Text(text = "${bundle.descriptionAr} • السعر الأصلي: ${bundle.originalPriceEgp} ج.م", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    OutlinedTextField(
                                        value = bundlePriceEdit,
                                        onValueChange = { bundlePriceEdit = it },
                                        modifier = Modifier.width(90.dp).height(46.dp),
                                        singleLine = true,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Button(
                                        onClick = {
                                            bundlePriceEdit.toIntOrNull()?.let { onUpdateBundlePrice(bundle.id, it) }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("تحديث", color = Color(0xFF101014), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }

                    // Tiers
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = "تعديل أسعار مستويات PRO الفردية (بالجنيه المصري):", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GoldPrimary))
                    }

                    items(ProTier.values()) { tier ->
                        var priceEdit by remember(tierPrices[tier]) { mutableStateOf(tierPrices[tier]?.toString() ?: tier.defaultPriceEgp.toString()) }
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(text = "فئة ${tier.titleAr} (${tier.titleEn})", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                                    Text(text = "السعر الحالي: ${tierPrices[tier] ?: tier.defaultPriceEgp} ج.م", style = MaterialTheme.typography.labelSmall, color = GoldLight)
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    OutlinedTextField(
                                        value = priceEdit,
                                        onValueChange = { priceEdit = it },
                                        modifier = Modifier.width(90.dp).height(46.dp),
                                        singleLine = true,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Button(
                                        onClick = {
                                            priceEdit.toIntOrNull()?.let { onUpdateTierPrice(tier, it) }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("حفظ", color = Color(0xFF101014), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // TAB 4: DISCOUNTS & COUPONS
            4 -> {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "إدارة العروض وأكواد الخصم:", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                        Button(
                            onClick = { showAddCouponDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("إضافة كود جديد", color = Color(0xFF101014), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(coupons) { coupon ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                                border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.5f))
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(text = coupon.code, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = GoldPrimary, fontFamily = CinzelFontFamily))
                                        Text(text = coupon.description, style = MaterialTheme.typography.bodySmall)
                                    }

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(SuccessEmerald.copy(alpha = 0.2f))
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                        ) {
                                            Text(text = "خصم ${coupon.discountPercent}%", color = SuccessEmerald, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        IconButton(onClick = { onRemoveCoupon(coupon.code) }, modifier = Modifier.size(32.dp)) {
                                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorRuby, modifier = Modifier.size(16.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // TAB 5: ORDERS & PAYMENTS REVIEW
            5 -> {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "مراجعة إيصالات الدفع اليدوي (Vodafone Cash, InstaPay, Fawry):",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(payments) { payment ->
                            PaymentReviewCard(
                                payment = payment,
                                strings = strings,
                                onApprove = { onApprovePayment(payment.id) },
                                onReject = { onRejectPayment(payment.id) }
                            )
                        }
                    }
                }
            }
        }
    }

    // Modal: Edit or Add Template
    if (showTemplateDialog) {
        AdminTemplateEditDialog(
            initialTemplate = editingTemplateTarget,
            availableCategories = categories,
            onDismiss = { showTemplateDialog = false },
            onSave = { titleAr, titleEn, descriptionAr, descriptionEn, category, aspectRatio, isPremium, pricingType, proTier, priceEgp, bgStartHex, bgEndHex, isPublished, imageUri, designFileUri, designFileName ->
                if (editingTemplateTarget == null) {
                    onAddTemplate(
                        titleAr,
                        titleEn,
                        descriptionAr,
                        descriptionEn,
                        category,
                        aspectRatio,
                        isPremium,
                        pricingType,
                        proTier,
                        priceEgp,
                        bgStartHex,
                        bgEndHex,
                        isPublished,
                        imageUri,
                        designFileUri,
                        designFileName
                    )
                } else {
                    val updated = editingTemplateTarget!!.copy(
                        titleAr = titleAr,
                        titleEn = titleEn,
                        descriptionAr = descriptionAr,
                        descriptionEn = descriptionEn,
                        category = category,
                        aspectRatio = aspectRatio,
                        isPremium = isPremium || pricingType != "FREE",
                        pricingType = pricingType,
                        proTier = proTier,
                        priceEgp = if (pricingType == "FREE") 0 else priceEgp,
                        bgStartHex = bgStartHex,
                        bgEndHex = bgEndHex,
                        isPublished = isPublished,
                        imageUri = imageUri,
                        designFileUri = designFileUri,
                        designFileName = designFileName
                    )
                    onUpdateTemplate(updated)
                }
                showTemplateDialog = false
            }
        )
    }

    // Modal: Delete Template Confirmation
    templateToDelete?.let { tpl ->
        DeleteConfirmDialog(
            templateTitle = tpl.titleAr,
            onDismiss = { templateToDelete = null },
            onConfirm = {
                onDeleteTemplate(tpl.id)
                templateToDelete = null
            }
        )
    }

    // Modal: Add Category
    if (showAddCategoryDialog) {
        AlertDialog(
            onDismissRequest = { showAddCategoryDialog = false },
            title = { Text("إضافة فئة تصاميم جديدة", color = GoldPrimary, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = newCategoryNameAr,
                        onValueChange = { newCategoryNameAr = it },
                        label = { Text("اسم الفئة بالعربية (مثال: عقارات وإنشاءات)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = newCategoryNameEn,
                        onValueChange = { newCategoryNameEn = it },
                        label = { Text("اسم الفئة بالإنجليزية (مثال: Real Estate)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newCategoryNameAr.isNotBlank()) {
                            onAddCategory(
                                newCategoryNameAr.trim(),
                                newCategoryNameEn.trim().ifBlank { newCategoryNameAr.trim() },
                                "category"
                            )
                        }
                        showAddCategoryDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                ) {
                    Text("إضافة وحفظ", color = Color(0xFF101014), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddCategoryDialog = false }) { Text("إلغاء") }
            }
        )
    }

    // Modal: Edit Category
    editingCategoryTarget?.let { cat ->
        var editNameAr by remember(cat.nameAr) { mutableStateOf(cat.nameAr) }
        var editNameEn by remember(cat.nameEn) { mutableStateOf(cat.nameEn) }
        AlertDialog(
            onDismissRequest = { editingCategoryTarget = null },
            title = { Text("تعديل اسم الفئة", color = GoldPrimary, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = editNameAr,
                        onValueChange = { editNameAr = it },
                        label = { Text("اسم الفئة بالعربية") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = editNameEn,
                        onValueChange = { editNameEn = it },
                        label = { Text("اسم الفئة بالإنجليزية") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editNameAr.isNotBlank()) {
                            onUpdateCategory(cat.id, editNameAr.trim(), editNameEn.trim().ifBlank { editNameAr.trim() })
                        }
                        editingCategoryTarget = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                ) {
                    Text("حفظ التعديل", color = Color(0xFF101014), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { editingCategoryTarget = null }) { Text("إلغاء") }
            }
        )
    }

    // Modal: Delete Category Confirmation
    categoryToDelete?.let { cat ->
        AlertDialog(
            onDismissRequest = { categoryToDelete = null },
            title = { Text("تأكيد حذف الفئة", color = ErrorRuby, fontWeight = FontWeight.Bold) },
            text = {
                Text("هل أنت متأكد من حذف فئة \"${cat.nameAr}\"؟ لن يتم حذف القوالب الموجودة ضمن هذه الفئة.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteCategory(cat.id)
                        categoryToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ErrorRuby)
                ) {
                    Text("تأكيد الحذف", color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { categoryToDelete = null }) { Text("إلغاء") }
            }
        )
    }

    // Modal: Add Coupon
    if (showAddCouponDialog) {
        AlertDialog(
            onDismissRequest = { showAddCouponDialog = false },
            title = { Text("إضافة كود خصم ترويجي جديد", color = GoldPrimary, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = newCouponCode,
                        onValueChange = { newCouponCode = it.uppercase() },
                        label = { Text("كود الخصم (مثال: EID2026)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = newCouponDiscount,
                        onValueChange = { newCouponDiscount = it },
                        label = { Text("نسبة الخصم المئوية %") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = newCouponDesc,
                        onValueChange = { newCouponDesc = it },
                        label = { Text("وصف الكود") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val disc = newCouponDiscount.toIntOrNull() ?: 20
                        if (newCouponCode.isNotBlank()) {
                            onAddCoupon(newCouponCode.trim(), disc, newCouponDesc.ifBlank { "خصم ترويجي $disc%" })
                        }
                        showAddCouponDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                ) {
                    Text("حفظ وتفعيل", color = Color(0xFF101014), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddCouponDialog = false }) { Text("إلغاء") }
            }
        )
    }
}

@Composable
fun AdminTemplateCard(
    template: DesignTemplate,
    strings: TranslationStrings,
    onEdit: () -> Unit,
    onDuplicate: () -> Unit,
    onDelete: () -> Unit,
    onTogglePublish: (Boolean) -> Unit,
    onUpdatePrice: (Int) -> Unit = {},
    onPreview: () -> Unit
) {
    var showQuickPriceDialog by remember { mutableStateOf(false) }
    var quickPriceInput by remember(template.priceEgp) { mutableStateOf(template.priceEgp.toString()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Visual Mini-Canvas Thumbnail
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .clickable { onPreview() }
                ) {
                    TemplateVisualCanvas(
                        template = template,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = template.titleAr,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontFamily = CairoFontFamily),
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (template.isPublished) SuccessEmerald.copy(alpha = 0.2f) else ErrorRuby.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = if (template.isPublished) "منشور" else "مسودة",
                                color = if (template.isPublished) SuccessEmerald else ErrorRuby,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Text(
                        text = "${template.category} • أبعاد ${template.aspectRatio}",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp)
                    )
                    if (template.descriptionAr.isNotBlank()) {
                        Text(
                            text = template.descriptionAr,
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f), fontSize = 10.sp),
                            maxLines = 1
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Clickable Price Badge to edit price on the fly
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (template.priceEgp > 0) GoldPrimary else SuccessEmerald)
                        .clickable { showQuickPriceDialog = true }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (template.priceEgp > 0) "${template.priceEgp} ج.م ✎" else "مجاناً ✎",
                        style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF101014), fontWeight = FontWeight.Bold, fontFamily = CinzelFontFamily)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action toolbar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "نشر علناً:", fontSize = 11.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Switch(
                        checked = template.isPublished,
                        onCheckedChange = onTogglePublish,
                        colors = SwitchDefaults.colors(checkedThumbColor = SuccessEmerald),
                        modifier = Modifier.size(36.dp)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(onClick = onPreview, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Visibility, contentDescription = "Preview", tint = GoldPrimary, modifier = Modifier.size(16.dp))
                    }
                    IconButton(onClick = onDuplicate, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.ContentCopy, contentDescription = "Duplicate", tint = GoldLight, modifier = Modifier.size(16.dp))
                    }
                    IconButton(onClick = onEdit, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = GoldPrimary, modifier = Modifier.size(16.dp))
                    }
                    IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorRuby, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }

    // Fast price edit dialog
    if (showQuickPriceDialog) {
        AlertDialog(
            onDismissRequest = { showQuickPriceDialog = false },
            title = { Text("تعديل سعر القالب: ${template.titleAr}", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GoldPrimary)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("أدخل السعر الجديد بالجنيه المصري (0 = مجاناً):", fontSize = 11.sp)
                    OutlinedTextField(
                        value = quickPriceInput,
                        onValueChange = { quickPriceInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val newPrice = quickPriceInput.toIntOrNull() ?: 0
                        onUpdatePrice(newPrice)
                        showQuickPriceDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("حفظ السعر", color = Color(0xFF101014), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showQuickPriceDialog = false }) { Text("إلغاء") }
            }
        )
    }
}

@Composable
fun AdminMetricCard(
    title: String,
    value: String,
    subValue: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    isAlert: Boolean = false
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isAlert) ErrorRuby else GoldBorder.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Icon(imageVector = icon, contentDescription = null, tint = if (isAlert) ErrorRuby else GoldPrimary, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = value, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = if (isAlert) ErrorRuby else GoldPrimary, fontFamily = CinzelFontFamily))
            Text(text = title, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            Text(text = subValue, style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 9.sp))
        }
    }
}

@Composable
fun PaymentReviewCard(
    payment: PaymentEntity,
    strings: TranslationStrings,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = payment.userEmail, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                    Text(text = "${payment.planName} • ${payment.dateString}", style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant))
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            when (payment.status) {
                                "APPROVED" -> SuccessEmerald.copy(alpha = 0.2f)
                                "REJECTED" -> ErrorRuby.copy(alpha = 0.2f)
                                else -> GoldPrimary.copy(alpha = 0.2f)
                            }
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = when (payment.status) {
                            "APPROVED" -> strings.statusApproved
                            "REJECTED" -> strings.statusRejected
                            else -> strings.statusPending
                        },
                        color = when (payment.status) {
                            "APPROVED" -> SuccessEmerald
                            "REJECTED" -> ErrorRuby
                            else -> GoldPrimary
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "المبلغ: ${payment.amountFormatted} • وسيلة الدفع: ${payment.paymentMethod}", style = MaterialTheme.typography.bodySmall)
            Text(text = "مرجع التحويل: ${payment.transactionRef}", style = MaterialTheme.typography.bodySmall.copy(color = GoldLight, fontWeight = FontWeight.Bold))

            if (payment.status == "PENDING") {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onApprove,
                        colors = ButtonDefaults.buttonColors(containerColor = SuccessEmerald),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.weight(1f).height(38.dp)
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = strings.approvePayment, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onReject,
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.weight(1f).height(38.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ErrorRuby)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = ErrorRuby, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = strings.rejectPayment, color = ErrorRuby, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun WebsiteBuilderManagementTab(
    templates: List<WebsiteTemplate>,
    settings: WebsiteBuilderSettings,
    onUpdatePrice: (String, Int) -> Unit,
    onUpdateFeatures: (String, WebsiteFeatureToggles) -> Unit,
    onUpdateSettings: (Int, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var freeLimitStr by remember(settings.freeWebsitesLimit) { mutableStateOf(settings.freeWebsitesLimit.toString()) }
    var extraPriceStr by remember(settings.extraWebsitePriceEgp) { mutableStateOf(settings.extraWebsitePriceEgp.toString()) }
    var customDomainPriceStr by remember(settings.customDomainPriceEgp) { mutableStateOf(settings.customDomainPriceEgp.toString()) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Global Free Limit and Builder Pricing
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldPrimary)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Tune, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(22.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "التحكم في الحد المجاني وأسعار المواقع (خاص بالمالك):",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GoldPrimary)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = freeLimitStr,
                            onValueChange = { freeLimitStr = it.filter { ch -> ch.isDigit() } },
                            label = { Text("المواقع المجانية", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp)
                        )
                        OutlinedTextField(
                            value = extraPriceStr,
                            onValueChange = { extraPriceStr = it.filter { ch -> ch.isDigit() } },
                            label = { Text("سعر الإضافي (ج.م)", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp)
                        )
                        OutlinedTextField(
                            value = customDomainPriceStr,
                            onValueChange = { customDomainPriceStr = it.filter { ch -> ch.isDigit() } },
                            label = { Text("سعر الدومين (ج.م)", fontSize = 10.sp) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp)
                        )
                    }

                    Button(
                        onClick = {
                            val freeLimit = freeLimitStr.toIntOrNull() ?: 1
                            val extraPrice = extraPriceStr.toIntOrNull() ?: 99
                            val domainPrice = customDomainPriceStr.toIntOrNull() ?: 199
                            onUpdateSettings(freeLimit, extraPrice, domainPrice)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "حفظ وتطبيق الحد المجاني والأسعار",
                            color = Color(0xFF101014),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "قوالب المواقع الـ 10 الحقيقية والتحكم في تفعيل/إيقاف الميزات:",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = GoldPrimary)
            )
        }

        items(templates) { template ->
            AdminWebsiteTemplateItemCard(
                template = template,
                onUpdatePrice = { newPrice -> onUpdatePrice(template.id, newPrice) },
                onUpdateFeatures = { toggles -> onUpdateFeatures(template.id, toggles) }
            )
        }
    }
}

@Composable
private fun AdminWebsiteTemplateItemCard(
    template: WebsiteTemplate,
    onUpdatePrice: (Int) -> Unit,
    onUpdateFeatures: (WebsiteFeatureToggles) -> Unit
) {
    var priceText by remember(template.priceEgp) { mutableStateOf(template.priceEgp.toString()) }
    var currentToggles by remember(template.featureToggles) { mutableStateOf(template.featureToggles) }
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = template.titleAr,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontFamily = CairoFontFamily)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(GoldPrimary.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = template.businessTypeNameAr,
                                fontSize = 10.sp,
                                color = GoldPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Text(
                        text = template.taglineAr,
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }

                // Price update
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = priceText,
                        onValueChange = { priceText = it.filter { ch -> ch.isDigit() } },
                        modifier = Modifier.width(75.dp).height(44.dp),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Button(
                        onClick = {
                            priceText.toIntOrNull()?.let { onUpdatePrice(it) }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(44.dp)
                    ) {
                        Text("حفظ", color = Color(0xFF101014), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Toggle Expand Button for features
            OutlinedButton(
                onClick = { isExpanded = !isExpanded },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Tune,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isExpanded) "إخفاء لوحة ميزات هذا القالب" else "التحكم في ميزات القالب وتفعيلها/إيقافها",
                    fontFamily = CairoFontFamily,
                    fontSize = 11.sp,
                    color = GoldPrimary
                )
            }

            if (isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "مفاتيح تفعيل وتعطيل الميزات للمستخدمين في هذا القالب:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldLight,
                        fontFamily = CairoFontFamily
                    )

                    FeatureToggleRow(
                        title = "الطلب المباشر عبر واتساب (WhatsApp Orders)",
                        checked = currentToggles.featureWhatsappOrders,
                        onCheckedChange = { currentToggles = currentToggles.copy(featureWhatsappOrders = it) }
                    )
                    FeatureToggleRow(
                        title = "كتالوج المنتجات والخدمات (Product Catalog)",
                        checked = currentToggles.featureProductCatalog,
                        onCheckedChange = { currentToggles = currentToggles.copy(featureProductCatalog = it) }
                    )
                    FeatureToggleRow(
                        title = "نظام الحجز والمواعيد (Booking System)",
                        checked = currentToggles.featureBookingSystem,
                        onCheckedChange = { currentToggles = currentToggles.copy(featureBookingSystem = it) }
                    )
                    FeatureToggleRow(
                        title = "الاتصال الهاتفي المباشر (Direct Calling)",
                        checked = currentToggles.featureDirectCalling,
                        onCheckedChange = { currentToggles = currentToggles.copy(featureDirectCalling = it) }
                    )
                    FeatureToggleRow(
                        title = "تقييمات وآراء العملاء (Reviews)",
                        checked = currentToggles.featureCustomerReviews,
                        onCheckedChange = { currentToggles = currentToggles.copy(featureCustomerReviews = it) }
                    )
                    FeatureToggleRow(
                        title = "موقع النشاط على الخريطة (Google Maps)",
                        checked = currentToggles.featureGoogleMaps,
                        onCheckedChange = { currentToggles = currentToggles.copy(featureGoogleMaps = it) }
                    )
                    FeatureToggleRow(
                        title = "قسم الأسئلة الشائعة (FAQ Section)",
                        checked = currentToggles.featureFaqSection,
                        onCheckedChange = { currentToggles = currentToggles.copy(featureFaqSection = it) }
                    )
                    FeatureToggleRow(
                        title = "دعم دومين خاص مخصص (Custom Domain)",
                        checked = currentToggles.featureCustomDomain,
                        onCheckedChange = { currentToggles = currentToggles.copy(featureCustomDomain = it) }
                    )

                    Button(
                        onClick = { onUpdateFeatures(currentToggles) },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                    ) {
                        Text(
                            text = "حفظ وتطبيق ميزات قالب ${template.titleAr}",
                            color = Color(0xFF101014),
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FeatureToggleRow(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontFamily = CairoFontFamily,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF101014),
                checkedTrackColor = GoldPrimary
            )
        )
    }
}

