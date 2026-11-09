package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.CanvasElementType
import com.example.data.model.DesignTemplate
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.util.parseColor

/**
 * High-end graphic canvas preview for Baraya AI templates.
 * Completely eliminates black placeholder screens. Renders vibrant,
 * colorful, category-distinct layouts with high-contrast typography,
 * geometric motifs, and realistic design elements.
 */
@Composable
fun TemplateVisualCanvas(
    template: DesignTemplate,
    modifier: Modifier = Modifier
) {
    // Elegant fallback: Royal Blue gradient instead of black
    val bgStart = parseColor(template.bgStartHex, Color(0xFF1E3A8A))
    val bgEnd = parseColor(template.bgEndHex, Color(0xFF3B82F6))

    // Determine contrast based on luminance
    val startLum = calculateLuminance(bgStart)
    val endLum = calculateLuminance(bgEnd)
    val isLightBg = isColorLight(bgStart) || isColorLight(bgEnd) || ((startLum + endLum) / 2f > 0.45f)

    val primaryTextColor = if (isLightBg) Color(0xFF0F172A) else Color(0xFFFFFFFF)
    val secondaryTextColor = if (isLightBg) Color(0xFF334155) else Color(0xFFF1F5F9).copy(alpha = 0.9f)
    val goldAccent = if (isLightBg) Color(0xFFB45309) else Color(0xFFFDE047)
    val subtleContainerBg = if (isLightBg) Color(0xFF0F172A).copy(alpha = 0.06f) else Color.Black.copy(alpha = 0.22f)
    val subtleContainerBorder = if (isLightBg) Color(0xFF0F172A).copy(alpha = 0.12f) else Color.White.copy(alpha = 0.2f)

    val calligElement = template.elements.firstOrNull { it.type == CanvasElementType.CALLIGRAPHY }
    val textElements = template.elements.filter { it.type == CanvasElementType.TEXT }
    val headline = textElements.firstOrNull { it.isBold || it.fontSize >= 18f }?.text
        ?: textElements.firstOrNull()?.text
        ?: template.titleAr
    val subtitle = textElements.getOrNull(1)?.text ?: template.titleEn

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.linearGradient(listOf(bgStart, bgEnd)))
    ) {
        val hasPhoto = template.previewDrawableRes != null || !template.imageUri.isNullOrBlank()

        if (template.previewDrawableRes != null) {
            Image(
                painter = painterResource(id = template.previewDrawableRes),
                contentDescription = template.titleAr,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Black.copy(alpha = 0.55f),
                                Color.Black.copy(alpha = 0.15f),
                                Color.Black.copy(alpha = 0.88f)
                            )
                        )
                    )
            )
        } else if (!template.imageUri.isNullOrBlank()) {
            AsyncImage(
                model = template.imageUri,
                contentDescription = template.titleAr,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Black.copy(alpha = 0.55f),
                                Color.Black.copy(alpha = 0.15f),
                                Color.Black.copy(alpha = 0.88f)
                            )
                        )
                    )
            )
        }

        // Decorative background geometric drawings (only when no photograph is set)
        if (!hasPhoto) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                when (template.category) {
                "الشعارات والهويات" -> {
                    // Central radiant golden rings
                    drawCircle(
                        color = goldAccent.copy(alpha = 0.12f),
                        radius = h * 0.42f,
                        center = Offset(w / 2f, h / 2f)
                    )
                    drawCircle(
                        color = goldAccent.copy(alpha = 0.25f),
                        radius = h * 0.28f,
                        center = Offset(w / 2f, h / 2f),
                        style = Stroke(width = 1.5f)
                    )
                }
                "دعوات ومناسبات" -> {
                    // Double luxury border with corner flourishes
                    val inset = 12f
                    drawRect(
                        color = goldAccent.copy(alpha = 0.4f),
                        topLeft = Offset(inset, inset),
                        size = Size(w - (inset * 2), h - (inset * 2)),
                        style = Stroke(width = 1.5f)
                    )
                    drawRect(
                        color = goldAccent.copy(alpha = 0.2f),
                        topLeft = Offset(inset + 4f, inset + 4f),
                        size = Size(w - ((inset + 4f) * 2), h - ((inset + 4f) * 2)),
                        style = Stroke(width = 0.8f)
                    )
                }
                "كروت العمل الشخصية" -> {
                    // Metallic accent stripe
                    drawLine(
                        color = goldAccent.copy(alpha = 0.35f),
                        start = Offset(w * 0.68f, 0f),
                        end = Offset(w * 0.54f, h),
                        strokeWidth = 2f
                    )
                }
                "عروض وتخفيضات" -> {
                    // Radiant discount starburst halo
                    drawCircle(
                        color = goldAccent.copy(alpha = 0.2f),
                        radius = h * 0.35f,
                        center = Offset(w * 0.85f, h * 0.18f)
                    )
                }
                else -> {
                    // Ambient diagonal lines
                    drawLine(
                        color = goldAccent.copy(alpha = 0.15f),
                        start = Offset(0f, 0f),
                        end = Offset(w, h),
                        strokeWidth = 1f
                    )
                }
            }
        }
        }

        // Category-Specific Rich Layout
        when (template.category) {
            "منشورات السوشيال ميديا" -> {
                SocialMediaCanvasLayout(
                    template = template,
                    callig = calligElement?.text ?: "✨",
                    headline = headline,
                    subtitle = subtitle,
                    textColor = primaryTextColor,
                    subColor = secondaryTextColor,
                    gold = goldAccent,
                    isLightBg = isLightBg,
                    containerBg = subtleContainerBg,
                    containerBorder = subtleContainerBorder
                )
            }
            "الشعارات والهويات" -> {
                LogoCanvasLayout(
                    template = template,
                    callig = calligElement?.text ?: "❖",
                    headline = headline,
                    subtitle = subtitle,
                    textColor = primaryTextColor,
                    subColor = secondaryTextColor,
                    gold = goldAccent,
                    isLightBg = isLightBg
                )
            }
            "كروت العمل الشخصية" -> {
                BusinessCardCanvasLayout(
                    template = template,
                    headline = headline,
                    subtitle = subtitle,
                    textColor = primaryTextColor,
                    subColor = secondaryTextColor,
                    gold = goldAccent,
                    isLightBg = isLightBg
                )
            }
            "قوائم طعام ومطاعم" -> {
                if (hasPhoto) {
                    PhotographicRestaurantAdCanvasLayout(template = template)
                } else {
                    RestaurantMenuCanvasLayout(
                        template = template,
                        callig = calligElement?.text ?: "🍽️",
                        headline = headline,
                        subtitle = subtitle,
                        textColor = primaryTextColor,
                        subColor = secondaryTextColor,
                        gold = goldAccent,
                        isLightBg = isLightBg
                    )
                }
            }
            "عروض وتخفيضات" -> {
                SalesDiscountCanvasLayout(
                    template = template,
                    callig = calligElement?.text ?: "⚡",
                    headline = headline,
                    subtitle = subtitle,
                    textColor = primaryTextColor,
                    subColor = secondaryTextColor,
                    gold = goldAccent,
                    isLightBg = isLightBg
                )
            }
            "دعوات ومناسبات" -> {
                InvitationsCanvasLayout(
                    template = template,
                    callig = calligElement?.text ?: "👑",
                    headline = headline,
                    subtitle = subtitle,
                    textColor = primaryTextColor,
                    subColor = secondaryTextColor,
                    gold = goldAccent,
                    isLightBg = isLightBg
                )
            }
            "سير ذاتية احترافية" -> {
                CvCanvasLayout(
                    template = template,
                    headline = headline,
                    subtitle = subtitle,
                    textColor = primaryTextColor,
                    subColor = secondaryTextColor,
                    gold = goldAccent,
                    isLightBg = isLightBg,
                    containerBg = subtleContainerBg
                )
            }
            "إعلانات المنتجات التجارية" -> {
                ProductAdCanvasLayout(
                    template = template,
                    callig = calligElement?.text ?: "💎",
                    headline = headline,
                    subtitle = subtitle,
                    textColor = primaryTextColor,
                    subColor = secondaryTextColor,
                    gold = goldAccent,
                    isLightBg = isLightBg
                )
            }
            "بانرات ومطبوعات" -> {
                BannerCanvasLayout(
                    template = template,
                    callig = calligElement?.text ?: "📢",
                    headline = headline,
                    subtitle = subtitle,
                    textColor = primaryTextColor,
                    subColor = secondaryTextColor,
                    gold = goldAccent,
                    isLightBg = isLightBg
                )
            }
            else -> {
                // Default Advertisements ("الإعلانات الممولة")
                AdvertisementsCanvasLayout(
                    template = template,
                    callig = calligElement?.text ?: "🚀",
                    headline = headline,
                    subtitle = subtitle,
                    textColor = primaryTextColor,
                    subColor = secondaryTextColor,
                    gold = goldAccent,
                    isLightBg = isLightBg
                )
            }
        }
    }
}

// ---------------- CATEGORY LAYOUT IMPLEMENTATIONS ----------------

@Composable
private fun SocialMediaCanvasLayout(
    template: DesignTemplate,
    callig: String,
    headline: String,
    subtitle: String,
    textColor: Color,
    subColor: Color,
    gold: Color,
    isLightBg: Boolean,
    containerBg: Color,
    containerBorder: Color
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top Social Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(if (isLightBg) gold.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.25f))
                        .border(1.dp, gold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(callig, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Baraya Creative",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = textColor,
                                fontSize = 10.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = if (isLightBg) Color(0xFF0284C7) else Color(0xFF38BDF8),
                            modifier = Modifier.size(11.dp)
                        )
                    }
                    Text(
                        text = "منشور رسمي • Social Post",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = subColor,
                            fontSize = 8.sp
                        )
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(containerBg)
                    .border(0.8.dp, containerBorder, RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = template.aspectRatio,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 9.sp
                    )
                )
            }
        }

        // Center Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "« $headline »",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    fontFamily = CairoFontFamily,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = subColor,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Bottom Engagement Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(containerBg)
                .border(0.5.dp, containerBorder, RoundedCornerShape(6.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = gold, modifier = Modifier.size(13.dp))
                Icon(Icons.Default.Share, contentDescription = null, tint = textColor.copy(alpha = 0.8f), modifier = Modifier.size(13.dp))
                Icon(Icons.Default.BookmarkBorder, contentDescription = null, tint = textColor.copy(alpha = 0.8f), modifier = Modifier.size(13.dp))
            }
            Text(
                text = "اسحب للأعلى ↗",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 9.sp
                )
            )
        }
    }
}

@Composable
private fun LogoCanvasLayout(
    template: DesignTemplate,
    callig: String,
    headline: String,
    subtitle: String,
    textColor: Color,
    subColor: Color,
    gold: Color,
    isLightBg: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Embellished Emblem Crest
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(gold.copy(alpha = 0.35f), gold.copy(alpha = 0.1f))
                        )
                    )
                    .border(1.5.dp, gold, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = callig,
                    fontSize = 26.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = headline.uppercase(),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (isLightBg) textColor else gold,
                    fontFamily = CinzelFontFamily,
                    fontSize = 15.sp,
                    letterSpacing = 1.5.sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = subColor,
                    fontSize = 9.sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Sub-rule tag
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.width(16.dp).height(1.dp).background(gold.copy(alpha = 0.6f)))
                Text(
                    text = " LUXURY IDENTITY ",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = gold,
                        fontSize = 7.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = CinzelFontFamily
                    )
                )
                Box(modifier = Modifier.width(16.dp).height(1.dp).background(gold.copy(alpha = 0.6f)))
            }
        }
    }
}

@Composable
private fun BusinessCardCanvasLayout(
    template: DesignTemplate,
    headline: String,
    subtitle: String,
    textColor: Color,
    subColor: Color,
    gold: Color,
    isLightBg: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left Info Area
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            // Gold Chip Graphic
            Box(
                modifier = Modifier
                    .width(26.dp)
                    .height(18.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(gold.copy(alpha = 0.4f))
                    .border(1.dp, gold, RoundedCornerShape(3.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = headline,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    fontFamily = CairoFontFamily,
                    fontSize = 13.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = gold,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Medium
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Contact micro-chips
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Phone, contentDescription = null, tint = subColor, modifier = Modifier.size(9.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("+20 100 234 5678", fontSize = 7.sp, color = subColor)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = subColor, modifier = Modifier.size(9.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("القاهرة، مصر", fontSize = 7.sp, color = subColor)
                }
            }
        }

        // Right QR & Logo Area
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.White.copy(alpha = 0.95f))
                    .border(0.5.dp, Color(0xFFE2E8F0), RoundedCornerShape(6.dp))
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.QrCode,
                    contentDescription = "QR Code",
                    tint = Color(0xFF0F172A),
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "SCAN VCARD",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = if (isLightBg) textColor else gold,
                    fontSize = 6.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = CinzelFontFamily
                )
            )
        }
    }
}

@Composable
private fun RestaurantMenuCanvasLayout(
    template: DesignTemplate,
    callig: String,
    headline: String,
    subtitle: String,
    textColor: Color,
    subColor: Color,
    gold: Color,
    isLightBg: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(callig, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(
                        text = headline,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = textColor,
                            fontFamily = CairoFontFamily,
                            fontSize = 12.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "SPECIAL CHEF MENU • قائمة المأكولات",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = subColor,
                            fontSize = 7.sp,
                            fontFamily = CinzelFontFamily
                        )
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(gold)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text("VIP", color = if (isLightBg) Color.White else Color(0xFF101014), fontSize = 8.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Sample Gourmet Items with Price Dots
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            MenuItemRow(name = "وجبة المشاوي الملكية الخاصة", price = "180 ج.م", textColor = textColor, gold = gold)
            MenuItemRow(name = "طاجن سي فود بصلصة الكريمة والزعفران", price = "140 ج.م", textColor = textColor, gold = gold)
            MenuItemRow(name = "حلوى البقلاوة الفاخرة بالفستق الحلبي", price = "65 ج.م", textColor = textColor, gold = gold)
        }

        // Footer Note
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("★ تقييم الشيف 4.9 / 5", fontSize = 8.sp, color = gold, fontWeight = FontWeight.Bold)
            Text(subtitle, fontSize = 8.sp, color = subColor, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
private fun MenuItemRow(name: String, price: String, textColor: Color, gold: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall.copy(
                color = textColor,
                fontSize = 9.sp
            ),
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = " • • • ",
            style = MaterialTheme.typography.labelSmall.copy(
                color = textColor.copy(alpha = 0.4f),
                fontSize = 8.sp
            )
        )
        Text(
            text = price,
            style = MaterialTheme.typography.labelSmall.copy(
                color = gold,
                fontWeight = FontWeight.Bold,
                fontSize = 9.sp,
                fontFamily = CinzelFontFamily
            )
        )
    }
}

@Composable
private fun SalesDiscountCanvasLayout(
    template: DesignTemplate,
    callig: String,
    headline: String,
    subtitle: String,
    textColor: Color,
    subColor: Color,
    gold: Color,
    isLightBg: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top badge & timer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isLightBg) Color(0xFFDC2626) else Color(0xFFFDE047))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "🔥 عروض حصرية لفترة محدودة",
                        color = if (isLightBg) Color.White else Color(0xFF7F1D1D),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "LIMITED OFFER",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                        fontFamily = CinzelFontFamily,
                        fontSize = 8.sp
                    )
                )
            }

            // Main explosive discount
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = headline,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = textColor,
                            fontFamily = CairoFontFamily,
                            fontSize = 14.sp
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = subColor,
                            fontSize = 9.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Discount Circle Badge
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(gold)
                        .border(2.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "خصم",
                            color = if (isLightBg) Color.White else Color(0xFF101014),
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "50%",
                            color = if (isLightBg) Color.White else Color(0xFF101014),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = CinzelFontFamily
                        )
                    }
                }
            }

            // Bottom CTA Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "كود الخصم: BARAYA50",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 8.sp
                    )
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(gold)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "تسوق الآن ↗",
                        color = if (isLightBg) Color.White else Color(0xFF101014),
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun InvitationsCanvasLayout(
    template: DesignTemplate,
    callig: String,
    headline: String,
    subtitle: String,
    textColor: Color,
    subColor: Color,
    gold: Color,
    isLightBg: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = gold,
                    fontWeight = FontWeight.Bold,
                    fontFamily = CairoFontFamily,
                    fontSize = 10.sp
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = callig,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = headline,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    fontFamily = CairoFontFamily,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = gold,
                    fontSize = 9.sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "نتشرف بحضوركم الكريم لمشاركتنا فرحتنا",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = subColor,
                    fontSize = 8.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Composable
private fun CvCanvasLayout(
    template: DesignTemplate,
    headline: String,
    subtitle: String,
    textColor: Color,
    subColor: Color,
    gold: Color,
    isLightBg: Boolean,
    containerBg: Color
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Left Column (Sidebar profile & skill bars)
        Column(
            modifier = Modifier
                .width(55.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(8.dp))
                .background(containerBg)
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(gold.copy(alpha = 0.3f))
                    .border(1.dp, gold, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("👤", fontSize = 14.sp)
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text("المهارات", fontSize = 6.sp, color = gold, fontWeight = FontWeight.Bold)
                Box(modifier = Modifier.fillMaxWidth().height(3.dp).background(textColor.copy(alpha = 0.15f))) {
                    Box(modifier = Modifier.fillMaxWidth(0.9f).fillMaxHeight().background(gold))
                }
                Box(modifier = Modifier.fillMaxWidth().height(3.dp).background(textColor.copy(alpha = 0.15f))) {
                    Box(modifier = Modifier.fillMaxWidth(0.75f).fillMaxHeight().background(gold))
                }
            }

            Text("PDF جاهز", fontSize = 6.sp, color = textColor, fontWeight = FontWeight.Medium)
        }

        // Right Column (Details & sections)
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = headline,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = textColor,
                        fontFamily = CairoFontFamily,
                        fontSize = 12.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = gold,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Simulated CV Experience Rows
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(gold))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("الخبرات المهنية والمشاريع الإبداعية", fontSize = 8.sp, color = subColor)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(gold))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("المؤهل الأكاديمي والشهادات الدولية المعتمدة", fontSize = 8.sp, color = subColor)
                }
            }

            // Bottom bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("ATS FRIENDLY", fontSize = 7.sp, color = gold, fontWeight = FontWeight.Bold, fontFamily = CinzelFontFamily)
                Text("تصميم سيرة ذاتية احترافية", fontSize = 7.sp, color = subColor)
            }
        }
    }
}

@Composable
private fun ProductAdCanvasLayout(
    template: DesignTemplate,
    callig: String,
    headline: String,
    subtitle: String,
    textColor: Color,
    subColor: Color,
    gold: Color,
    isLightBg: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top product header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(gold)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text("منتج تجاري حصري", color = if (isLightBg) Color.White else Color(0xFF101014), fontSize = 8.sp, fontWeight = FontWeight.Bold)
            }

            Text(callig, fontSize = 20.sp)
        }

        // Center Product Features
        Column {
            Text(
                text = headline,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    fontFamily = CairoFontFamily,
                    fontSize = 13.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = subColor,
                    fontSize = 9.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = gold, modifier = Modifier.size(10.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("ضمان أصلي 100%", fontSize = 7.sp, color = subColor)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = gold, modifier = Modifier.size(10.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("شحن لجميع المحافظات", fontSize = 7.sp, color = subColor)
                }
            }
        }

        // Bottom CTA & Price Tag
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "سعر العرض: متوفر فوراً",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 9.sp
                )
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(gold)
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "اطلب الآن ↗",
                    color = if (isLightBg) Color.White else Color(0xFF101014),
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun BannerCanvasLayout(
    template: DesignTemplate,
    callig: String,
    headline: String,
    subtitle: String,
    textColor: Color,
    subColor: Color,
    gold: Color,
    isLightBg: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "BANNER & PRINT • بانر إعلاني ومطبوعات",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = gold,
                    fontWeight = FontWeight.Bold,
                    fontSize = 8.sp,
                    fontFamily = CinzelFontFamily
                )
            )
            Text(callig, fontSize = 16.sp)
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = headline,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    fontFamily = CairoFontFamily,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = subColor,
                    fontSize = 9.sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("📍 فندق الماسة - القاهرة", fontSize = 8.sp, color = subColor)
            Text("🗓️ حجز مسبق متاح", fontSize = 8.sp, color = gold, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun AdvertisementsCanvasLayout(
    template: DesignTemplate,
    callig: String,
    headline: String,
    subtitle: String,
    textColor: Color,
    subColor: Color,
    gold: Color,
    isLightBg: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(gold.copy(alpha = 0.25f))
                    .border(0.8.dp, gold, RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "📢 إعلان ممول • SPONSORED",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = textColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 8.sp
                    )
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(5) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = gold,
                        modifier = Modifier.size(10.dp)
                    )
                }
                Spacer(modifier = Modifier.width(3.dp))
                Text("4.9", fontSize = 8.sp, color = textColor, fontWeight = FontWeight.Bold)
            }
        }

        // Center Headline
        Column {
            Text(
                text = headline,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = textColor,
                    fontFamily = CairoFontFamily,
                    fontSize = 14.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = subColor,
                    fontSize = 9.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Bottom CTA Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "استشارات وحملات تسويقية",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = subColor,
                    fontSize = 8.sp
                )
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(gold)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "سجّل الآن ↗",
                    color = if (isLightBg) Color.White else Color(0xFF101014),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun PhotographicRestaurantAdCanvasLayout(
    template: DesignTemplate
) {
    val badgeText = template.elements.firstOrNull { it.id == "e_badge" }?.text ?: "🔥 عرض خاص لفترة محدودة • خصم 25%"
    val headlineText = template.elements.firstOrNull { it.id == "e_headline" }?.text ?: template.titleAr
    val descText = template.elements.firstOrNull { it.id == "e_desc" }?.text ?: "لحم أنجوس بقري صافي 100% مع خبز البريوش وصوص الشيف"
    val priceVal = template.elements.firstOrNull { it.id == "e_price_val" }?.text ?: "145 ج.م"
    val priceOld = template.elements.firstOrNull { it.id == "e_price_old" }?.text ?: "بدلاً من 195 ج.م"
    val ctaText = template.elements.firstOrNull { it.id == "e_cta" }?.text ?: "📞 للطلب والتوصيل: 19854 • خدمة 24 ساعة"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Top row: Offer Badge & Rating
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFFD4AF37))
                    .border(1.dp, Color(0xFFFDE047), RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = badgeText,
                    color = Color(0xFF101014),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = CairoFontFamily
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.Black.copy(alpha = 0.65f))
                    .border(0.8.dp, Color(0xFFD4AF37), RoundedCornerShape(6.dp))
                    .padding(horizontal = 6.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "★ 4.9",
                    color = Color(0xFFFDE047),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Center / Floating Price Tag and Typography
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                Text(
                    text = headlineText,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        fontFamily = CairoFontFamily,
                        fontSize = 16.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = descText,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFFFED7AA),
                        fontSize = 9.sp,
                        fontFamily = CairoFontFamily
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Standout Canva Price Tag
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFD4AF37))
                    .border(1.5.dp, Color.White, RoundedCornerShape(10.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = priceVal,
                        color = Color(0xFF101014),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = CinzelFontFamily
                    )
                    Text(
                        text = priceOld,
                        color = Color(0xFF991B1B),
                        fontSize = 7.sp,
                        fontWeight = FontWeight.Bold,
                        style = androidx.compose.ui.text.TextStyle(
                            textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                        )
                    )
                }
            }
        }

        // Bottom CTA Hotline Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Black.copy(alpha = 0.8f))
                .border(1.dp, Color(0xFFD4AF37).copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = ctaText,
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = CairoFontFamily
                )
                Text(
                    text = "⚡ خدمة سريعة",
                    color = Color(0xFFFDE047),
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Utility to calculate luminance of color to pick high-contrast text and elements.
 */
private fun calculateLuminance(color: Color): Float {
    return (0.299f * color.red) + (0.587f * color.green) + (0.114f * color.blue)
}

private fun isColorLight(color: Color): Boolean {
    return calculateLuminance(color) > 0.45f
}
