package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CartItem
import com.example.data.model.UserWebsite
import com.example.data.model.WebsiteBooking
import com.example.data.model.WebsiteItem
import com.example.ui.locale.TranslationStrings
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.ErrorRuby
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.SuccessEmerald
import com.example.ui.util.parseColor
import java.util.UUID

@Composable
fun WebsiteEditorScreen(
    strings: TranslationStrings,
    website: UserWebsite,
    onSave: (UserWebsite) -> Unit,
    onBack: () -> Unit,
    onOpenQrStudio: (UserWebsite) -> Unit = {},
    onSubmitBooking: (WebsiteBooking) -> Unit = {},
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }
    val context = LocalContext.current

    // State bindings
    var siteTitle by remember(website.id) { mutableStateOf(website.siteTitle) }
    var siteSlug by remember(website.id) { mutableStateOf(website.siteSlug) }
    var headline by remember(website.id) { mutableStateOf(website.headline) }
    var subheadline by remember(website.id) { mutableStateOf(website.subheadline) }
    var aboutText by remember(website.id) { mutableStateOf(website.aboutText) }
    var whatsappNumber by remember(website.id) { mutableStateOf(website.whatsappNumber) }
    var phoneNumber by remember(website.id) { mutableStateOf(website.phoneNumber) }
    var address by remember(website.id) { mutableStateOf(website.address) }
    var workingHours by remember(website.id) { mutableStateOf(website.workingHours) }
    var primaryColorHex by remember(website.id) { mutableStateOf(website.primaryColorHex) }
    var items by remember(website.id) { mutableStateOf(website.items) }

    // Navigation tabs inside the editor: 0 = Info, 1 = Catalog/Menu, 2 = Live Preview, 3 = Export HTML
    var selectedTab by remember { mutableIntStateOf(0) }
    var showAddItemDialog by remember { mutableStateOf(false) }

    val buildUpdatedWebsite = {
        website.copy(
            siteTitle = siteTitle,
            siteSlug = siteSlug.ifBlank { "my-site" },
            headline = headline,
            subheadline = subheadline,
            aboutText = aboutText,
            whatsappNumber = whatsappNumber,
            phoneNumber = phoneNumber,
            address = address,
            workingHours = workingHours,
            primaryColorHex = primaryColorHex,
            items = items,
            updatedAt = System.currentTimeMillis()
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("website_editor_back_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = GoldPrimary
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "محرر المواقع الاحترافي",
                            fontFamily = CairoFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = siteTitle.ifBlank { "موقعي الجديد" },
                            fontFamily = CairoFontFamily,
                            fontSize = 12.sp,
                            color = GoldPrimary
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { onOpenQrStudio(buildUpdatedWebsite()) },
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary),
                        modifier = Modifier.testTag("website_editor_qr_btn")
                    ) {
                        Icon(Icons.Default.QrCode2, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "باركود QR",
                            fontFamily = CairoFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = GoldPrimary,
                            fontSize = 12.sp
                        )
                    }

                    Button(
                        onClick = {
                            val updated = buildUpdatedWebsite()
                            onSave(updated)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("website_editor_save_btn")
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "حفظ الموقع",
                            fontFamily = CairoFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        // Tabs Row
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = GoldPrimary,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = GoldPrimary
                )
            }
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Text(
                        "البيانات الأساسية",
                        fontFamily = CairoFontFamily,
                        fontSize = 12.sp,
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                    )
                },
                icon = { Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Text(
                        "المنتجات والأسعار (${items.size})",
                        fontFamily = CairoFontFamily,
                        fontSize = 12.sp,
                        fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal
                    )
                },
                icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                text = {
                    Text(
                        "معاينة حية",
                        fontFamily = CairoFontFamily,
                        fontSize = 12.sp,
                        fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal
                    )
                },
                icon = { Icon(Icons.Default.Preview, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
            Tab(
                selected = selectedTab == 3,
                onClick = { selectedTab = 3 },
                text = {
                    Text(
                        "تصدير HTML",
                        fontFamily = CairoFontFamily,
                        fontSize = 12.sp,
                        fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Normal
                    )
                },
                icon = { Icon(Icons.Default.Code, contentDescription = null, modifier = Modifier.size(18.dp)) }
            )
        }

        // Tab Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when (selectedTab) {
                0 -> GeneralInfoTab(
                    siteTitle = siteTitle,
                    onSiteTitleChange = { siteTitle = it },
                    siteSlug = siteSlug,
                    onSiteSlugChange = { siteSlug = it },
                    headline = headline,
                    onHeadlineChange = { headline = it },
                    subheadline = subheadline,
                    onSubheadlineChange = { subheadline = it },
                    aboutText = aboutText,
                    onAboutTextChange = { aboutText = it },
                    whatsappNumber = whatsappNumber,
                    onWhatsappNumberChange = { whatsappNumber = it },
                    phoneNumber = phoneNumber,
                    onPhoneNumberChange = { phoneNumber = it },
                    address = address,
                    onAddressChange = { address = it },
                    workingHours = workingHours,
                    onWorkingHoursChange = { workingHours = it },
                    primaryColorHex = primaryColorHex,
                    onPrimaryColorHexChange = { primaryColorHex = it }
                )
                1 -> CatalogItemsTab(
                    items = items,
                    onAddItem = { newItem -> items = items + newItem },
                    onDeleteItem = { id -> items = items.filter { it.id != id } },
                    primaryColorHex = primaryColorHex
                )
                2 -> LivePreviewTab(
                    website = buildUpdatedWebsite(),
                    onSubmitBooking = onSubmitBooking
                )
                3 -> ExportHtmlTab(
                    website = buildUpdatedWebsite(),
                    context = context
                )
            }
        }
    }
}

// ---------------- TAB 0: GENERAL INFO ----------------
@Composable
private fun GeneralInfoTab(
    siteTitle: String,
    onSiteTitleChange: (String) -> Unit,
    siteSlug: String,
    onSiteSlugChange: (String) -> Unit,
    headline: String,
    onHeadlineChange: (String) -> Unit,
    subheadline: String,
    onSubheadlineChange: (String) -> Unit,
    aboutText: String,
    onAboutTextChange: (String) -> Unit,
    whatsappNumber: String,
    onWhatsappNumberChange: (String) -> Unit,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    address: String,
    onAddressChange: (String) -> Unit,
    workingHours: String,
    onWorkingHoursChange: (String) -> Unit,
    primaryColorHex: String,
    onPrimaryColorHexChange: (String) -> Unit
) {
    val presetColors = listOf(
        "#D4AF37" to "ذهبي ملكي",
        "#10B981" to "أخضر طبي",
        "#EC4899" to "وردي أنيق",
        "#0284C7" to "أزرق سماوي",
        "#F59E0B" to "عنبر دافئ",
        "#DC2626" to "أحمر فاخر",
        "#6366F1" to "بنفسجي رقمي"
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "هوية المتجر واسم النشاط",
                        fontFamily = CairoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = GoldPrimary
                    )

                    OutlinedTextField(
                        value = siteTitle,
                        onValueChange = onSiteTitleChange,
                        label = { Text("اسم النشاط التجاري / المحل", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = siteSlug,
                        onValueChange = onSiteSlugChange,
                        label = { Text("رابط الموقع الفرعي (Slug)", fontFamily = CairoFontFamily) },
                        supportingText = { Text("https://baraya.site/$siteSlug", color = GoldLight) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = headline,
                        onValueChange = onHeadlineChange,
                        label = { Text("العنوان الرئيسي الجذاب (Hero Headline)", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = subheadline,
                        onValueChange = onSubheadlineChange,
                        label = { Text("العنوان الفرعي والوصف التسويقي", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = aboutText,
                        onValueChange = onAboutTextChange,
                        label = { Text("نبذة عن المحل / النشاط (من نحن)", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        }

        // Color Selection
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Palette, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "اللون الأساسي المميز للموقع",
                            fontFamily = CairoFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(presetColors) { (hex, name) ->
                            val isSelected = primaryColorHex.equals(hex, ignoreCase = true)
                            val color = parseColor(hex)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable { onPrimaryColorHexChange(hex) }
                                    .padding(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(42.dp)
                                        .background(color, CircleShape)
                                        .border(
                                            width = if (isSelected) 3.dp else 1.dp,
                                            color = if (isSelected) Color.White else Color.Transparent,
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isSelected) {
                                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(name, fontFamily = CairoFontFamily, fontSize = 10.sp, color = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }

        // Contact & Hours
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "بيانات الاتصال وموقع النشاط",
                        fontFamily = CairoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = GoldPrimary
                    )

                    OutlinedTextField(
                        value = whatsappNumber,
                        onValueChange = onWhatsappNumberChange,
                        label = { Text("رقم واتساب للطلبات المباشرة (مع كود الدولة مثل: 2010xxxx)", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = onPhoneNumberChange,
                        label = { Text("رقم الهاتف للاتصال المباشر", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = address,
                        onValueChange = onAddressChange,
                        label = { Text("العنوان الفعلي / الفرع بالتفصيل", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = workingHours,
                        onValueChange = onWorkingHoursChange,
                        label = { Text("مواعيد العمل (مثال: يومياً من 10 ص حتى 12 م)", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        }
    }
}

// ---------------- TAB 1: CATALOG ITEMS ----------------
@Composable
private fun CatalogItemsTab(
    items: List<WebsiteItem>,
    onAddItem: (WebsiteItem) -> Unit,
    onDeleteItem: (String) -> Unit,
    primaryColorHex: String
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "قائمة المنتجات والخدمات",
                    fontFamily = CairoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "إجمالي العناصر المضافة: ${items.size}",
                    fontFamily = CairoFontFamily,
                    fontSize = 12.sp,
                    color = GoldLight
                )
            }

            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("إضافة منتج", fontFamily = CairoFontFamily, fontWeight = FontWeight.Bold, color = Color.Black)
            }
        }

        if (items.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = GoldPrimary.copy(alpha = 0.5f), modifier = Modifier.size(56.dp))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("لا توجد منتجات أو خدمات مضافة بعد", fontFamily = CairoFontFamily, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = { showDialog = true },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("أضف أول منتج الآن", fontFamily = CairoFontFamily, color = GoldPrimary)
                    }
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(items) { item ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.3f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = item.nameAr,
                                        fontFamily = CairoFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    if (!item.badgeAr.isNullOrBlank()) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            color = parseColor(primaryColorHex).copy(alpha = 0.15f),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                text = item.badgeAr,
                                                fontFamily = CairoFontFamily,
                                                fontSize = 10.sp,
                                                color = parseColor(primaryColorHex),
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }
                                if (item.descriptionAr.isNotBlank()) {
                                    Text(
                                        text = item.descriptionAr,
                                        fontFamily = CairoFontFamily,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${item.priceEgp} ج.م",
                                    fontFamily = CairoFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = GoldPrimary
                                )
                            }

                            IconButton(onClick = { onDeleteItem(item.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorRuby)
                            }
                        }
                    }
                }
            }
        }
    }

    // Add Item Dialog
    if (showDialog) {
        var nameAr by remember { mutableStateOf("") }
        var priceEgpStr by remember { mutableStateOf("") }
        var descriptionAr by remember { mutableStateOf("") }
        var category by remember { mutableStateOf("عام") }
        var badgeAr by remember { mutableStateOf("") }

        androidx.compose.ui.window.Dialog(onDismissRequest = { showDialog = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "إضافة منتج / خدمة جديدة",
                        fontFamily = CairoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = GoldPrimary
                    )

                    OutlinedTextField(
                        value = nameAr,
                        onValueChange = { nameAr = it },
                        label = { Text("اسم المنتج / الخدمة بالعربية", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = priceEgpStr,
                        onValueChange = { priceEgpStr = it.filter { ch -> ch.isDigit() } },
                        label = { Text("السعر بالجنيه المصري (EGP)", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = descriptionAr,
                        onValueChange = { descriptionAr = it },
                        label = { Text("وصف مختصر", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("التصنيف (مثال: برجر، وجبات، عطور، كشف)", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = badgeAr,
                        onValueChange = { badgeAr = it },
                        label = { Text("شارة تسويقية (مثال: الأكثر طلباً، عرض خاص)", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        OutlinedButton(
                            onClick = { showDialog = false },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("إلغاء", fontFamily = CairoFontFamily)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (nameAr.isNotBlank()) {
                                    val price = priceEgpStr.toIntOrNull() ?: 50
                                    val newItem = WebsiteItem(
                                        id = UUID.randomUUID().toString(),
                                        nameAr = nameAr,
                                        priceEgp = price,
                                        descriptionAr = descriptionAr,
                                        category = category.ifBlank { "عام" },
                                        badgeAr = badgeAr.ifBlank { null },
                                        isAvailable = true
                                    )
                                    onAddItem(newItem)
                                    showDialog = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("إضافة", fontFamily = CairoFontFamily, fontWeight = FontWeight.Bold, color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}

// ---------------- TAB 2: LIVE PREVIEW ----------------
@Composable
private fun LivePreviewTab(
    website: UserWebsite,
    onSubmitBooking: (WebsiteBooking) -> Unit
) {
    val context = LocalContext.current
    val brandColor = parseColor(website.primaryColorHex)
    var selectedCategory by remember { mutableStateOf("الكل") }

    // Mini Cart State
    var cart by remember { mutableStateOf<Map<String, CartItem>>(emptyMap()) }
    var showCartDialog by remember { mutableStateOf(false) }
    var showBookingDialog by remember { mutableStateOf(false) }

    val categories = remember(website.items) {
        listOf("الكل") + website.items.map { it.category }.distinct()
    }

    val filteredItems = remember(selectedCategory, website.items) {
        if (selectedCategory == "الكل") website.items
        else website.items.filter { it.category == selectedCategory }
    }

    val cartTotalCount = cart.values.sumOf { it.quantity }
    val cartTotalEgp = cart.values.sumOf { it.item.priceEgp * it.quantity }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF0F172A))
                .border(2.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
        ) {
            // Mobile Browser Chrome simulation
            Surface(
                color = Color(0xFF1E293B),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(8.dp).background(Color(0xFFEF4444), CircleShape))
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(modifier = Modifier.size(8.dp).background(Color(0xFFF59E0B), CircleShape))
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(modifier = Modifier.size(8.dp).background(Color(0xFF10B981), CircleShape))
                    }

                    Surface(
                        color = Color(0xFF0F172A),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.weight(1f).padding(horizontal = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Default.Language, contentDescription = null, tint = GoldLight, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "https://${website.siteSlug}.baraya.site",
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8),
                                fontFamily = CinzelFontFamily
                            )
                        }
                    }

                    Surface(
                        color = SuccessEmerald.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "مباشر",
                            color = SuccessEmerald,
                            fontSize = 10.sp,
                            fontFamily = CairoFontFamily,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            // Website Canvas Preview
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(bottom = if (cartTotalCount > 0) 70.dp else 0.dp)
            ) {
                // Header & Hero Section
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(brandColor.copy(alpha = 0.25f), Color(0xFF0F172A))
                                )
                            )
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            // Brand Badge
                            Surface(
                                color = brandColor.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(20.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, brandColor.copy(alpha = 0.4f))
                            ) {
                                Text(
                                    text = "مرحباً بكم في",
                                    fontFamily = CairoFontFamily,
                                    fontSize = 11.sp,
                                    color = brandColor,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = website.siteTitle,
                                fontFamily = CairoFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = website.headline,
                                fontFamily = CairoFontFamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = brandColor
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = website.subheadline,
                                fontFamily = CairoFontFamily,
                                fontSize = 12.sp,
                                color = Color(0xFF94A3B8),
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Action Buttons: WhatsApp & Phone & Booking
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    if (website.featureToggles.featureWhatsappOrders) {
                                        Button(
                                            onClick = {
                                                val uri = android.net.Uri.parse("https://wa.me/${website.whatsappNumber}?text=${java.net.URLEncoder.encode("مرحباً أود الاستفسار والطلب من متجركم: ${website.siteTitle}", "UTF-8")}")
                                                val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, uri)
                                                context.startActivity(intent)
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                                            shape = RoundedCornerShape(10.dp),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = "واتساب مباشر",
                                                fontFamily = CairoFontFamily,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 12.sp,
                                                color = Color.White
                                            )
                                        }
                                    }

                                    if (website.featureToggles.featureDirectCalling) {
                                        OutlinedButton(
                                            onClick = {
                                                val intent = android.content.Intent(android.content.Intent.ACTION_DIAL, android.net.Uri.parse("tel:${website.phoneNumber}"))
                                                context.startActivity(intent)
                                            },
                                            shape = RoundedCornerShape(10.dp),
                                            border = androidx.compose.foundation.BorderStroke(1.dp, brandColor),
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(Icons.Default.Call, contentDescription = null, tint = brandColor, modifier = Modifier.size(14.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = "اتصال مباشر",
                                                fontFamily = CairoFontFamily,
                                                fontSize = 12.sp,
                                                color = brandColor
                                            )
                                        }
                                    }
                                }

                                if (website.featureToggles.featureBookingSystem) {
                                    Button(
                                        onClick = { showBookingDialog = true },
                                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "حجز موعد / كشف أونلاين",
                                            fontFamily = CairoFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // About Us Section
                if (website.aboutText.isNotBlank()) {
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = "من نحن",
                                    fontFamily = CairoFontFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = brandColor
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = website.aboutText,
                                    fontFamily = CairoFontFamily,
                                    fontSize = 12.sp,
                                    color = Color(0xFFCBD5E1),
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }

                // Catalog Section Header
                if (website.featureToggles.featureProductCatalog && website.items.isNotEmpty()) {
                    item {
                        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                            Text(
                                text = "قائمة المنتجات والعروض",
                                fontFamily = CairoFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            // Category filter chips
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(categories) { cat ->
                                    val isSelected = cat == selectedCategory
                                    Surface(
                                        color = if (isSelected) brandColor else Color(0xFF1E293B),
                                        shape = RoundedCornerShape(16.dp),
                                        modifier = Modifier.clickable { selectedCategory = cat }
                                    ) {
                                        Text(
                                            text = cat,
                                            fontFamily = CairoFontFamily,
                                            fontSize = 11.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isSelected) Color.Black else Color.White,
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Catalog Items List
                    items(filteredItems) { item ->
                        val itemInCart = cart[item.id]

                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = item.nameAr,
                                            fontFamily = CairoFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = Color.White
                                        )
                                        if (!item.badgeAr.isNullOrBlank()) {
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Surface(
                                                color = brandColor.copy(alpha = 0.2f),
                                                shape = RoundedCornerShape(4.dp)
                                            ) {
                                                Text(
                                                    text = item.badgeAr,
                                                    fontFamily = CairoFontFamily,
                                                    fontSize = 9.sp,
                                                    color = brandColor,
                                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                    if (item.descriptionAr.isNotBlank()) {
                                        Text(
                                            text = item.descriptionAr,
                                            fontFamily = CairoFontFamily,
                                            fontSize = 11.sp,
                                            color = Color(0xFF94A3B8)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${item.priceEgp} ج.م",
                                        fontFamily = CairoFontFamily,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = brandColor
                                    )
                                }

                                // Cart action controls
                                if (itemInCart != null && itemInCart.quantity > 0) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .background(Color(0xFF0F172A), RoundedCornerShape(8.dp))
                                            .border(1.dp, brandColor.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                                            .padding(2.dp)
                                    ) {
                                        IconButton(
                                            onClick = {
                                                val current = cart.toMutableMap()
                                                if (itemInCart.quantity > 1) {
                                                    current[item.id] = itemInCart.copy(quantity = itemInCart.quantity - 1)
                                                } else {
                                                    current.remove(item.id)
                                                }
                                                cart = current
                                            },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(Icons.Default.Remove, contentDescription = "Decrease", tint = brandColor, modifier = Modifier.size(14.dp))
                                        }
                                        Text(
                                            text = "${itemInCart.quantity}",
                                            fontFamily = CairoFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = Color.White,
                                            modifier = Modifier.padding(horizontal = 6.dp)
                                        )
                                        IconButton(
                                            onClick = {
                                                val current = cart.toMutableMap()
                                                current[item.id] = itemInCart.copy(quantity = itemInCart.quantity + 1)
                                                cart = current
                                            },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(Icons.Default.Add, contentDescription = "Increase", tint = brandColor, modifier = Modifier.size(14.dp))
                                        }
                                    }
                                } else {
                                    Button(
                                        onClick = {
                                            val current = cart.toMutableMap()
                                            current[item.id] = CartItem(item = item, quantity = 1)
                                            cart = current
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = brandColor),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("أضف للسلة 🛒", fontFamily = CairoFontFamily, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = Color.Black)
                                    }
                                }
                            }
                        }
                    }
                }

                // Info Card: Address & Working Hours
                item {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = brandColor, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(website.address, fontFamily = CairoFontFamily, fontSize = 11.sp, color = Color(0xFFE2E8F0))
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Schedule, contentDescription = null, tint = brandColor, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(website.workingHours, fontFamily = CairoFontFamily, fontSize = 11.sp, color = Color(0xFFE2E8F0))
                            }
                        }
                    }
                }

                // Footer
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "تصميم وتطوير عبر منصة برايا AI الذكية © 2026",
                            fontFamily = CairoFontFamily,
                            fontSize = 10.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }
            }
        }

        // Floating Mini Cart Bottom Sheet Bar
        if (cartTotalCount > 0) {
            Surface(
                color = Color(0xFF1E293B),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, brandColor.copy(alpha = 0.5f)),
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = brandColor, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "السلة: $cartTotalCount عناصر",
                                fontFamily = CairoFontFamily,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Color.White
                            )
                        }
                        Text(
                            text = "الإجمالي: $cartTotalEgp ج.م",
                            fontFamily = CairoFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = brandColor
                        )
                    }

                    Button(
                        onClick = { showCartDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "إتمام الطلب عبر واتساب",
                            fontFamily = CairoFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }

    // Mini Cart Order Checkout Dialog
    if (showCartDialog) {
        var customerNotes by remember { mutableStateOf("") }

        androidx.compose.ui.window.Dialog(onDismissRequest = { showCartDialog = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF0F172A),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "فاتورة سلة المشتريات",
                            fontFamily = CairoFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = GoldPrimary
                        )
                        IconButton(onClick = { showCartDialog = false }) {
                            Icon(Icons.Default.Delete, contentDescription = "Clear", tint = ErrorRuby)
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(cart.values.toList()) { cartItem ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(0xFF1E293B), RoundedCornerShape(8.dp))
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(cartItem.item.nameAr, fontFamily = CairoFontFamily, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.White)
                                    Text("${cartItem.quantity} × ${cartItem.item.priceEgp} = ${cartItem.quantity * cartItem.item.priceEgp} ج.م", fontFamily = CairoFontFamily, fontSize = 11.sp, color = brandColor)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(
                                        onClick = {
                                            val current = cart.toMutableMap()
                                            if (cartItem.quantity > 1) {
                                                current[cartItem.item.id] = cartItem.copy(quantity = cartItem.quantity - 1)
                                            } else {
                                                current.remove(cartItem.item.id)
                                            }
                                            cart = current
                                        },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(Icons.Default.Remove, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                    }
                                    Text("${cartItem.quantity}", color = Color.White, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 4.dp))
                                    IconButton(
                                        onClick = {
                                            val current = cart.toMutableMap()
                                            current[cartItem.item.id] = cartItem.copy(quantity = cartItem.quantity + 1)
                                            cart = current
                                        },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                    }
                                }
                            }
                        }
                    }

                    OutlinedTextField(
                        value = customerNotes,
                        onValueChange = { customerNotes = it },
                        label = { Text("ملاحظات التوصيل أو تفاصيل العنوان", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Surface(
                        color = Color(0xFF1E293B),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("الإجمالي الكلي المطلوب:", fontFamily = CairoFontFamily, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
                            Text("$cartTotalEgp ج.م", fontFamily = CairoFontFamily, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = brandColor)
                        }
                    }

                    Button(
                        onClick = {
                            val itemsList = cart.values.toList()
                            val invoiceBuilder = StringBuilder()
                            invoiceBuilder.appendLine("🛍️ *طلب شراء جديد من موقع: ${website.siteTitle}*")
                            invoiceBuilder.appendLine("━━━━━━━━━━━━━━━━━━━")
                            itemsList.forEachIndexed { index, cartItem ->
                                val subtotal = cartItem.item.priceEgp * cartItem.quantity
                                invoiceBuilder.appendLine("${index + 1}. *${cartItem.item.nameAr}* (الكمية: ${cartItem.quantity}) = ${subtotal} ج.م")
                            }
                            invoiceBuilder.appendLine("━━━━━━━━━━━━━━━━━━━")
                            invoiceBuilder.appendLine("💰 *الإجمالي النهائي: ${cartTotalEgp} ج.م*")
                            if (customerNotes.isNotBlank()) {
                                invoiceBuilder.appendLine("📝 ملاحظات: $customerNotes")
                            }
                            invoiceBuilder.appendLine("⏰ التاريخ: ${java.text.SimpleDateFormat("yyyy/MM/dd hh:mm a", java.util.Locale.getDefault()).format(java.util.Date())}")

                            val uri = android.net.Uri.parse("https://wa.me/${website.whatsappNumber}?text=${java.net.URLEncoder.encode(invoiceBuilder.toString(), "UTF-8")}")
                            val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, uri)
                            context.startActivity(intent)
                            showCartDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("إرسال الطلب إلى واتساب المحل", fontFamily = CairoFontFamily, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }

    // Appointment Booking Dialog
    if (showBookingDialog) {
        var custName by remember { mutableStateOf("") }
        var custPhone by remember { mutableStateOf("") }
        var selectedService by remember { mutableStateOf(website.items.firstOrNull()?.nameAr ?: "خدمة عامة") }
        var bookDate by remember { mutableStateOf("غداً") }
        var bookTime by remember { mutableStateOf("06:00 م") }
        var bookingNotes by remember { mutableStateOf("") }

        androidx.compose.ui.window.Dialog(onDismissRequest = { showBookingDialog = false }) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF0F172A),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "📅 حجز موعد / استشارة أونلاين",
                        fontFamily = CairoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = GoldPrimary
                    )

                    OutlinedTextField(
                        value = custName,
                        onValueChange = { custName = it },
                        label = { Text("الاسم بالكامل", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )

                    OutlinedTextField(
                        value = custPhone,
                        onValueChange = { custPhone = it },
                        label = { Text("رقم الهاتف للتأكيد", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )

                    OutlinedTextField(
                        value = selectedService,
                        onValueChange = { selectedService = it },
                        label = { Text("الخدمة أو الكشف المطلوب", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = bookDate,
                            onValueChange = { bookDate = it },
                            label = { Text("تاريخ الموعد", fontFamily = CairoFontFamily) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        OutlinedTextField(
                            value = bookTime,
                            onValueChange = { bookTime = it },
                            label = { Text("الوقت المفضل", fontFamily = CairoFontFamily) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }

                    OutlinedTextField(
                        value = bookingNotes,
                        onValueChange = { bookingNotes = it },
                        label = { Text("ملاحظات إضافية", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        OutlinedButton(
                            onClick = { showBookingDialog = false },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("إلغاء", fontFamily = CairoFontFamily)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (custName.isNotBlank() && custPhone.isNotBlank()) {
                                    val booking = WebsiteBooking(
                                        siteId = website.id,
                                        customerName = custName,
                                        customerPhone = custPhone,
                                        serviceName = selectedService,
                                        bookingDate = bookDate,
                                        bookingTime = bookTime,
                                        notes = bookingNotes,
                                        status = "PENDING"
                                    )
                                    onSubmitBooking(booking)

                                    val bookingMsg = """
                                        📅 *طلب حجز موعد جديد من موقع: ${website.siteTitle}*
                                        ━━━━━━━━━━━━━━━━━━━
                                        👤 *العميل:* $custName
                                        📞 *الهاتف:* $custPhone
                                        🩺 *الخدمة المطلوبة:* $selectedService
                                        🗓️ *التاريخ المقترح:* $bookDate
                                        ⏰ *الوقت:* $bookTime
                                        📝 *الملاحظات:* $bookingNotes
                                        ━━━━━━━━━━━━━━━━━━━
                                    """.trimIndent()

                                    val uri = android.net.Uri.parse("https://wa.me/${website.whatsappNumber}?text=${java.net.URLEncoder.encode(bookingMsg, "UTF-8")}")
                                    val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, uri)
                                    context.startActivity(intent)
                                    showBookingDialog = false
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("تأكيد الحجز عبر واتساب", fontFamily = CairoFontFamily, fontWeight = FontWeight.Bold, color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}

// ---------------- TAB 3: EXPORT HTML ----------------
@Composable
private fun ExportHtmlTab(
    website: UserWebsite,
    context: Context
) {
    var isCopied by remember { mutableStateOf(false) }

    val generatedHtml = remember(website) {
        generateStandaloneHtml(website)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.3f))
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Code, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(22.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "كود الموقع المستقل (HTML5 & CSS3)",
                        fontFamily = CairoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Text(
                    text = "يمكنك نسخ كود الموقع ونشره فوراً على أي استضافة مثل Vercel، Netlify، GitHub Pages، أو رفعه على خادمك الخاص.",
                    fontFamily = CairoFontFamily,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Website HTML", generatedHtml)
                            clipboard.setPrimaryClip(clip)
                            isCopied = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.ContentCopy, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isCopied) "تم نسخ الكود!" else "نسخ الكود بالكامل",
                            fontFamily = CairoFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(android.content.Intent.EXTRA_SUBJECT, website.siteTitle)
                                putExtra(android.content.Intent.EXTRA_TEXT, "زوروا موقعنا الرسمي الجديد: https://baraya.site/${website.siteSlug}\n${website.siteTitle} - ${website.headline}")
                            }
                            context.startActivity(android.content.Intent.createChooser(shareIntent, "مشاركة رابط الموقع"))
                        },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("مشاركة الرابط", fontFamily = CairoFontFamily, color = GoldPrimary)
                    }
                }
            }
        }

        // Code preview box
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier.padding(12.dp)) {
                item {
                    Text(
                        text = generatedHtml,
                        fontFamily = CinzelFontFamily,
                        fontSize = 11.sp,
                        color = Color(0xFF93C5FD),
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}

private fun generateStandaloneHtml(site: UserWebsite): String {
    val itemsHtml = site.items.joinToString("\n") { item ->
        """
        <div class="product-card">
            <div class="product-info">
                <h3>${item.nameAr} ${if (!item.badgeAr.isNullOrBlank()) "<span class='badge'>${item.badgeAr}</span>" else ""}</h3>
                <p>${item.descriptionAr}</p>
                <div class="price">${item.priceEgp} ج.م</div>
            </div>
            <a href="https://wa.me/${site.whatsappNumber}?text=${java.net.URLEncoder.encode("مرحباً أود طلب: ${item.nameAr}", "UTF-8")}" class="order-btn" target="_blank">طلب الآن</a>
        </div>
        """.trimIndent()
    }

    return """
<!DOCTYPE html>
<html lang="ar" dir="rtl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${site.siteTitle} | ${site.headline}</title>
    <link href="https://fonts.googleapis.com/css2?family=Cairo:wght@400;600;700;800&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary: ${site.primaryColorHex};
            --bg-dark: #0f172a;
            --card-bg: #1e293b;
            --text-main: #f8fafc;
            --text-muted: #94a3b8;
        }
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Cairo', sans-serif; }
        body { background: var(--bg-dark); color: var(--text-main); line-height: 1.6; }
        header { text-align: center; padding: 60px 20px 40px; background: radial-gradient(circle at top, ${site.primaryColorHex}33, transparent 70%); }
        h1 { font-size: 2.4rem; color: #fff; margin-bottom: 8px; }
        .tagline { color: var(--primary); font-size: 1.2rem; font-weight: 600; margin-bottom: 12px; }
        .subtagline { color: var(--text-muted); max-width: 600px; margin: 0 auto 24px; font-size: 1rem; }
        .cta-group { display: flex; gap: 12px; justify-content: center; flex-wrap: wrap; }
        .btn { padding: 12px 28px; border-radius: 8px; font-weight: bold; text-decoration: none; display: inline-flex; align-items: center; }
        .btn-wa { background: #25D366; color: #fff; }
        .btn-call { border: 2px solid var(--primary); color: var(--primary); }
        .container { max-width: 1000px; margin: 0 auto; padding: 20px; }
        .section-title { font-size: 1.6rem; color: #fff; margin-bottom: 20px; border-right: 4px solid var(--primary); padding-right: 12px; }
        .about-card { background: var(--card-bg); padding: 24px; border-radius: 12px; margin-bottom: 40px; border: 1px solid rgba(255,255,255,0.08); }
        .grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 20px; margin-bottom: 40px; }
        .product-card { background: var(--card-bg); border-radius: 12px; padding: 20px; display: flex; flex-direction: column; justify-content: space-between; border: 1px solid rgba(255,255,255,0.06); }
        .product-card h3 { font-size: 1.15rem; color: #fff; margin-bottom: 6px; }
        .badge { background: ${site.primaryColorHex}22; color: var(--primary); font-size: 0.75rem; padding: 2px 8px; border-radius: 4px; margin-right: 6px; }
        .product-card p { font-size: 0.9rem; color: var(--text-muted); margin-bottom: 12px; }
        .price { font-size: 1.25rem; font-weight: bold; color: var(--primary); margin-bottom: 14px; }
        .order-btn { background: var(--primary); color: #000; text-align: center; padding: 10px; border-radius: 6px; font-weight: bold; text-decoration: none; }
        .contact-box { background: var(--card-bg); padding: 20px; border-radius: 12px; text-align: center; margin-bottom: 40px; }
        footer { text-align: center; padding: 30px; color: var(--text-muted); font-size: 0.85rem; border-top: 1px solid rgba(255,255,255,0.05); }
    </style>
</head>
<body>
    <header>
        <h1>${site.siteTitle}</h1>
        <div class="tagline">${site.headline}</div>
        <p class="subtagline">${site.subheadline}</p>
        <div class="cta-group">
            <a href="https://wa.me/${site.whatsappNumber}" class="btn btn-wa" target="_blank">اطلب عبر واتساب</a>
            <a href="tel:${site.phoneNumber}" class="btn btn-call">اتصل بنا: ${site.phoneNumber}</a>
        </div>
    </header>

    <div class="container">
        <section class="about-card">
            <h2 class="section-title">نبذة عنا</h2>
            <p>${site.aboutText}</p>
        </section>

        <section>
            <h2 class="section-title">قائمة المنتجات والخدمات</h2>
            <div class="grid">
                $itemsHtml
            </div>
        </section>

        <section class="contact-box">
            <h2 class="section-title">العنوان ومواعيد العمل</h2>
            <p>📍 ${site.address}</p>
            <p>⏰ ${site.workingHours}</p>
        </section>
    </div>

    <footer>
        <p>تم إنشاء هذا الموقع بواسطة منصة برايا AI © 2026</p>
    </footer>
</body>
</html>
    """.trimIndent()
}
