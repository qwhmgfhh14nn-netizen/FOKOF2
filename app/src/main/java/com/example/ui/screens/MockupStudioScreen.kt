package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.CropLandscape
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storefront
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MockupType
import com.example.ui.locale.TranslationStrings
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.util.parseColor

@Composable
fun MockupStudioScreen(
    strings: TranslationStrings,
    selectedType: MockupType,
    onSelectType: (MockupType) -> Unit,
    onBack: () -> Unit,
    onExportMockup: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }
    val context = LocalContext.current

    var brandName by remember { mutableStateOf("برجر رويال") }
    var brandTagline by remember { mutableStateOf("طعم الأصالة الملكية") }
    var brandColorHex by remember { mutableStateOf("#D4AF37") }
    var mockupBgDark by remember { mutableStateOf(true) }

    val presetColors = listOf(
        "#D4AF37" to "ذهبي",
        "#10B981" to "زمردي",
        "#EF4444" to "ياقوتي",
        "#3B82F6" to "أزرق",
        "#8B5CF6" to "بنفسجي",
        "#EC4899" to "وردي",
        "#F97316" to "برتقالي"
    )

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
                        modifier = Modifier.testTag("mockup_studio_back_btn")
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
                            text = "استوديو الموك آب الواقعي",
                            fontFamily = CairoFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "معاينة علامتك التجارية على منتجات حقيقية",
                            fontFamily = CairoFontFamily,
                            fontSize = 11.sp,
                            color = GoldLight
                        )
                    }
                }

                Button(
                    onClick = {
                        onExportMockup("تم حفظ الموك آب عالي الدقة ${selectedType.titleAr} بنجاح!")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("export_mockup_btn")
                ) {
                    Icon(Icons.Default.Download, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "تصدير عالي الدقة",
                        fontFamily = CairoFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Horizontal Ribbon for Mockup Types
        Surface(
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                items(MockupType.values()) { type ->
                    val isSelected = type == selectedType
                    val icon = getMockupIcon(type)

                    Surface(
                        color = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) GoldPrimary else GoldBorder.copy(alpha = 0.3f)
                        ),
                        modifier = Modifier
                            .clickable { onSelectType(type) }
                            .testTag("mockup_type_${type.name}")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = if (isSelected) Color.Black else GoldLight,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = type.titleAr,
                                fontFamily = CairoFontFamily,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 12.sp,
                                color = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        // Scrollable Body: Live Canvas Frame + Controls
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Realistic Canvas Simulation Card
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (mockupBgDark) Color(0xFF0F172A) else Color(0xFFF1F5F9)
                ),
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldBorder.copy(alpha = 0.4f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp)
                    .shadow(12.dp, RoundedCornerShape(20.dp))
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Custom Realistic 3D Mockup Canvas
                    MockupRenderCanvas(
                        mockupType = selectedType,
                        brandName = brandName,
                        brandTagline = brandTagline,
                        brandColorHex = brandColorHex,
                        isDark = mockupBgDark,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Overlay Branding on Mockup Center
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .background(parseColor(brandColorHex), CircleShape)
                                .border(2.dp, Color.White.copy(alpha = 0.8f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = brandName.ifBlank { "علامتك التجارية" },
                            fontFamily = CairoFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = if (selectedType == MockupType.SHOPPING_BAG || selectedType == MockupType.COFFEE_CUP || selectedType == MockupType.TSHIRT) {
                                parseColor(brandColorHex)
                            } else {
                                Color.White
                            }
                        )

                        Text(
                            text = brandTagline.ifBlank { "شعار النشاط التجاري" },
                            fontFamily = CairoFontFamily,
                            fontSize = 11.sp,
                            color = if (mockupBgDark) Color(0xFF94A3B8) else Color(0xFF475569)
                        )
                    }

                    // Floating toggle for Dark / Light Studio Scene
                    Surface(
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .clickable { mockupBgDark = !mockupBgDark }
                    ) {
                        Text(
                            text = if (mockupBgDark) "إضاءة استوديو مظلم 🌙" else "إضاءة استوديو نهاري ☀️",
                            fontFamily = CairoFontFamily,
                            fontSize = 10.sp,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Controls Card
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "تخصيص الهوية البصرية للموك آب",
                        fontFamily = CairoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = GoldPrimary
                    )

                    OutlinedTextField(
                        value = brandName,
                        onValueChange = { brandName = it },
                        label = { Text("اسم البراند / النشاط", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = brandTagline,
                        onValueChange = { brandTagline = it },
                        label = { Text("الشعار اللفظي (Slogan)", fontFamily = CairoFontFamily) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Text(
                        text = "لون الشعار والطباعة",
                        fontFamily = CairoFontFamily,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(presetColors) { (hex, name) ->
                            val isSelected = brandColorHex.equals(hex, ignoreCase = true)
                            val color = parseColor(hex)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { brandColorHex = hex }
                                    .padding(2.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(color, CircleShape)
                                        .border(
                                            width = if (isSelected) 2.5.dp else 1.dp,
                                            color = if (isSelected) Color.White else Color.Transparent,
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isSelected) {
                                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                                    }
                                }
                                Text(name, fontFamily = CairoFontFamily, fontSize = 9.sp, color = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                onExportMockup("تم تجهيز وحفظ صورة الموك آب بدقة 4K في معرض الصور!")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Download, contentDescription = null, tint = Color.Black, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("حفظ الصورة (4K)", fontFamily = CairoFontFamily, fontWeight = FontWeight.Bold, color = Color.Black)
                        }

                        OutlinedButton(
                            onClick = {
                                val shareIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(android.content.Intent.EXTRA_SUBJECT, "موك آب $brandName")
                                    putExtra(android.content.Intent.EXTRA_TEXT, "شاهد نموذج الموك آب الواقعي لعلامة $brandName الذي صممته عبر برايا AI!")
                                }
                                context.startActivity(android.content.Intent.createChooser(shareIntent, "مشاركة الموك آب"))
                            },
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("مشاركة", fontFamily = CairoFontFamily, color = GoldPrimary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MockupRenderCanvas(
    mockupType: MockupType,
    brandName: String,
    brandTagline: String,
    brandColorHex: String,
    isDark: Boolean,
    modifier: Modifier = Modifier
) {
    val brandColor = parseColor(brandColorHex)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Background studio gradient lighting
        drawRect(
            brush = Brush.radialGradient(
                colors = if (isDark) {
                    listOf(Color(0xFF1E293B), Color(0xFF020617))
                } else {
                    listOf(Color(0xFFFFFFFF), Color(0xFFCBD5E1))
                },
                center = Offset(w / 2f, h / 2f),
                radius = w * 0.7f
            )
        )

        // Drop Shadow under object
        drawOval(
            brush = Brush.radialGradient(
                colors = listOf(Color.Black.copy(alpha = 0.45f), Color.Transparent),
                center = Offset(w / 2f, h * 0.85f),
                radius = w * 0.4f
            ),
            topLeft = Offset(w * 0.15f, h * 0.78f),
            size = Size(w * 0.7f, h * 0.15f)
        )

        when (mockupType) {
            MockupType.SHOPPING_BAG -> {
                // Bag Body
                val bagLeft = w * 0.22f
                val bagTop = h * 0.26f
                val bagWidth = w * 0.56f
                val bagHeight = h * 0.52f

                drawRoundRect(
                    color = if (isDark) Color(0xFF18181B) else Color(0xFF27272A),
                    topLeft = Offset(bagLeft, bagTop),
                    size = Size(bagWidth, bagHeight),
                    cornerRadius = CornerRadius(16f, 16f)
                )

                // Gold foil borders on bag
                drawRoundRect(
                    color = brandColor.copy(alpha = 0.5f),
                    topLeft = Offset(bagLeft + 12f, bagTop + 12f),
                    size = Size(bagWidth - 24f, bagHeight - 24f),
                    cornerRadius = CornerRadius(12f, 12f),
                    style = Stroke(width = 3f)
                )

                // Handles
                val handlePath = Path().apply {
                    moveTo(w * 0.38f, bagTop)
                    cubicTo(w * 0.38f, bagTop - 70f, w * 0.62f, bagTop - 70f, w * 0.62f, bagTop)
                }
                drawPath(handlePath, color = brandColor, style = Stroke(width = 8f))
            }

            MockupType.COFFEE_CUP -> {
                // Cup Cone
                val path = Path().apply {
                    moveTo(w * 0.30f, h * 0.22f)
                    lineTo(w * 0.70f, h * 0.22f)
                    lineTo(w * 0.64f, h * 0.76f)
                    lineTo(w * 0.36f, h * 0.76f)
                    close()
                }
                drawPath(path, color = Color(0xFF1C1917))

                // Cup Lid
                drawRoundRect(
                    color = brandColor,
                    topLeft = Offset(w * 0.27f, h * 0.18f),
                    size = Size(w * 0.46f, h * 0.05f),
                    cornerRadius = CornerRadius(8f, 8f)
                )

                // Kraft Paper Cup Sleeve
                val sleevePath = Path().apply {
                    moveTo(w * 0.32f, h * 0.38f)
                    lineTo(w * 0.68f, h * 0.38f)
                    lineTo(w * 0.65f, h * 0.60f)
                    lineTo(w * 0.35f, h * 0.60f)
                    close()
                }
                drawPath(sleevePath, color = Color(0xFF78350F))
            }

            MockupType.BUSINESS_CARD -> {
                // Perspective Business Card
                drawRoundRect(
                    color = Color(0xFF09090B),
                    topLeft = Offset(w * 0.18f, h * 0.28f),
                    size = Size(w * 0.64f, h * 0.38f),
                    cornerRadius = CornerRadius(18f, 18f)
                )
                // Gold edge foil
                drawRoundRect(
                    brush = Brush.linearGradient(
                        colors = listOf(brandColor, Color(0xFFFFD700), brandColor)
                    ),
                    topLeft = Offset(w * 0.18f, h * 0.28f),
                    size = Size(w * 0.64f, h * 0.38f),
                    cornerRadius = CornerRadius(18f, 18f),
                    style = Stroke(width = 4f)
                )
            }

            MockupType.BILLBOARD -> {
                // Outdoor Billboard frame
                drawRect(
                    color = Color(0xFF334155),
                    topLeft = Offset(w * 0.12f, h * 0.20f),
                    size = Size(w * 0.76f, h * 0.45f)
                )
                // Screen inside
                drawRect(
                    color = Color(0xFF020617),
                    topLeft = Offset(w * 0.14f, h * 0.22f),
                    size = Size(w * 0.72f, h * 0.41f)
                )
                // Pillar support
                drawRect(
                    color = Color(0xFF475569),
                    topLeft = Offset(w * 0.46f, h * 0.65f),
                    size = Size(w * 0.08f, h * 0.20f)
                )
            }

            MockupType.BURGER_BOX -> {
                // Takeout Packaging Box
                drawRoundRect(
                    color = Color(0xFF1E1B18),
                    topLeft = Offset(w * 0.24f, h * 0.28f),
                    size = Size(w * 0.52f, h * 0.44f),
                    cornerRadius = CornerRadius(22f, 22f)
                )
                // Top closure tab
                drawRoundRect(
                    color = brandColor,
                    topLeft = Offset(w * 0.42f, h * 0.25f),
                    size = Size(w * 0.16f, h * 0.06f),
                    cornerRadius = CornerRadius(6f, 6f)
                )
            }

            MockupType.SMARTPHONE -> {
                // Sleek smartphone body
                drawRoundRect(
                    color = Color(0xFF0A0A0A),
                    topLeft = Offset(w * 0.28f, h * 0.14f),
                    size = Size(w * 0.44f, h * 0.68f),
                    cornerRadius = CornerRadius(32f, 32f)
                )
                // Screen
                drawRoundRect(
                    color = Color(0xFF0F172A),
                    topLeft = Offset(w * 0.30f, h * 0.16f),
                    size = Size(w * 0.40f, h * 0.64f),
                    cornerRadius = CornerRadius(24f, 24f)
                )
                // Dynamic island
                drawRoundRect(
                    color = Color.Black,
                    topLeft = Offset(w * 0.44f, h * 0.18f),
                    size = Size(w * 0.12f, h * 0.025f),
                    cornerRadius = CornerRadius(10f, 10f)
                )
            }

            MockupType.STOREFRONT -> {
                // Store Signboard
                drawRoundRect(
                    color = Color(0xFF020617),
                    topLeft = Offset(w * 0.14f, h * 0.26f),
                    size = Size(w * 0.72f, h * 0.38f),
                    cornerRadius = CornerRadius(14f, 14f)
                )
                // Signboard modern frame
                drawRoundRect(
                    color = brandColor,
                    topLeft = Offset(w * 0.14f, h * 0.26f),
                    size = Size(w * 0.72f, h * 0.38f),
                    cornerRadius = CornerRadius(14f, 14f),
                    style = Stroke(width = 6f)
                )
            }

            MockupType.TSHIRT -> {
                // T-Shirt silhouette path
                val tshirtPath = Path().apply {
                    moveTo(w * 0.36f, h * 0.22f)
                    // Neck
                    cubicTo(w * 0.44f, h * 0.26f, w * 0.56f, h * 0.26f, w * 0.64f, h * 0.22f)
                    // Right shoulder & sleeve
                    lineTo(w * 0.78f, h * 0.30f)
                    lineTo(w * 0.72f, h * 0.42f)
                    lineTo(w * 0.66f, h * 0.38f)
                    // Right side down
                    lineTo(w * 0.66f, h * 0.74f)
                    // Bottom
                    lineTo(w * 0.34f, h * 0.74f)
                    // Left side up
                    lineTo(w * 0.34f, h * 0.38f)
                    // Left sleeve
                    lineTo(w * 0.28f, h * 0.42f)
                    lineTo(w * 0.22f, h * 0.30f)
                    close()
                }
                drawPath(tshirtPath, color = Color(0xFF18181B))
                // Neck ring highlight
                drawCircle(color = brandColor.copy(alpha = 0.4f), radius = 24f, center = Offset(w / 2f, h * 0.23f), style = Stroke(width = 3f))
            }
        }
    }
}

private fun getMockupIcon(type: MockupType): ImageVector {
    return when (type) {
        MockupType.SHOPPING_BAG -> Icons.Default.ShoppingBag
        MockupType.COFFEE_CUP -> Icons.Default.LocalCafe
        MockupType.BUSINESS_CARD -> Icons.Default.Badge
        MockupType.BILLBOARD -> Icons.Default.CropLandscape
        MockupType.BURGER_BOX -> Icons.Default.LunchDining
        MockupType.SMARTPHONE -> Icons.Default.Smartphone
        MockupType.STOREFRONT -> Icons.Default.Storefront
        MockupType.TSHIRT -> Icons.Default.Checkroom
    }
}
