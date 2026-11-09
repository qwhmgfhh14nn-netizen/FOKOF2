package com.example.ui.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.InsertDriveFile
import com.example.data.model.CategoryModel
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Security
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Chat
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
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.ui.theme.ErrorRuby
import com.example.ui.theme.SuccessEmerald
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
import com.example.data.model.CanvasElement
import com.example.data.model.DesignTemplate
import com.example.data.model.ProTier
import com.example.data.model.TemplateBundle
import com.example.ui.locale.TranslationStrings
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.util.parseColor

@Composable
fun TemplatePreviewDialog(
    template: DesignTemplate,
    strings: TranslationStrings,
    isPurchased: Boolean = false,
    onDismiss: () -> Unit,
    onUseInStudio: () -> Unit,
    onBuy: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = strings.previewTemplate,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary,
                        fontFamily = CairoFontFamily
                    )
                )
                IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = GoldPrimary)
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Visual Graphic Canvas Preview
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.5.dp, GoldBorder, RoundedCornerShape(12.dp))
                ) {
                    TemplateVisualCanvas(
                        template = template,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Metadata Details
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "السعر:", style = MaterialTheme.typography.bodySmall)
                            Text(
                                text = when {
                                    isPurchased -> "مملوك لك (في مشترياتك)"
                                    template.priceEgp > 0 -> "${template.priceEgp} ${strings.currencyEgp}"
                                    else -> strings.freeBadge
                                },
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (isPurchased) Color(0xFF10B981) else GoldPrimary,
                                    fontFamily = CinzelFontFamily
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "التصنيف:", style = MaterialTheme.typography.bodySmall)
                            Text(text = template.category, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "الأبعاد والنسبة:", style = MaterialTheme.typography.bodySmall)
                            Text(text = template.aspectRatio, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "عدد الطبقات:", style = MaterialTheme.typography.bodySmall)
                            Text(text = "${template.elements.size} طبقات قابلة للتخصيص الكامل", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        },
        confirmButton = {
            if (template.priceEgp > 0 && !isPurchased) {
                Button(
                    onClick = onBuy,
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Payment, contentDescription = null, tint = Color(0xFF101014), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "شراء الآن (${template.priceEgp} ${strings.currencyEgp})",
                        color = Color(0xFF101014),
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Button(
                    onClick = onUseInStudio,
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = Color(0xFF101014), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = strings.useInStudio, color = Color(0xFF101014), fontWeight = FontWeight.Bold)
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("إلغاء") }
        }
    )
}

@Composable
fun TemplateBuyDialog(
    template: DesignTemplate,
    strings: TranslationStrings,
    onDismiss: () -> Unit,
    onConfirmBuy: (paymentMethod: String) -> Unit
) {
    val context = LocalContext.current
    var txnInput by remember { mutableStateOf("") }
    var couponInput by remember { mutableStateOf("") }
    var appliedDiscount by remember { mutableStateOf(0) }
    var receiptImageUri by remember { mutableStateOf<String?>(null) }
    var orderSubmittedForReview by remember { mutableStateOf(false) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            receiptImageUri = uri.toString()
        }
    }

    val finalPrice = (template.priceEgp * (100 - appliedDiscount) / 100).coerceAtLeast(1)

    val unavailableMethods = listOf(
        "فودافون كاش (Vodafone Cash)",
        "تطبيق إنستاباي (InstaPay)",
        "بايبال (PayPal)",
        "البطاقات البنكية (Visa & Mastercard)",
        "المحافظ الإلكترونية الذكية الأخرى"
    )

    if (orderSubmittedForReview) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SuccessEmerald, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "تم استلام طلب الشراء بنجاح",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = GoldPrimary, fontFamily = CairoFontFamily)
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "تم إرسال إشعار تحويل أورنج كاش وإيصال الدفع إلى الإدارة للمراجعة والتدقيق.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "حالة الطلب: قيد المراجعة والتدقيق (Pending Review)", fontWeight = FontWeight.Bold, color = GoldPrimary, fontSize = 12.sp)
                            Text(text = "سيتم تفعيل القالب وإتاحته فورياً في حسابك بعد مطابقة إيصال التحويل.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                    Text(
                        text = "للتأكيد السريع الفوري يمكنك مراسلتنا مباشرة:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/201206449934"))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Chat, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("واتساب الإدارة", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/+201206449934"))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0088CC)),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Chat, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("تيليجرام", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("تم", color = Color(0xFF101014), fontWeight = FontWeight.Bold)
                }
            }
        )
        return
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "إتمام شراء: ${template.titleAr}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = GoldPrimary,
                    fontFamily = CairoFontFamily
                )
            )
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Price summary
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "المبلغ المطلوب:", style = MaterialTheme.typography.bodyMedium)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (appliedDiscount > 0) {
                            Text(
                                text = "${template.priceEgp} ${strings.currencyEgp}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                        Text(
                            text = "$finalPrice ${strings.currencyEgp}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = GoldPrimary,
                                fontWeight = FontWeight.Bold,
                                fontFamily = CinzelFontFamily
                            )
                        )
                    }
                }

                // Coupon field
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = couponInput,
                        onValueChange = { couponInput = it },
                        placeholder = { Text("كود الخصم (مثل: BARAYA50)", fontSize = 11.sp) },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Button(
                        onClick = {
                            if (couponInput.trim().equals("BARAYA50", ignoreCase = true)) {
                                appliedDiscount = 50
                            } else if (couponInput.trim().equals("CAIRO20", ignoreCase = true)) {
                                appliedDiscount = 20
                            } else if (couponInput.trim().equals("ORANGE", ignoreCase = true)) {
                                appliedDiscount = 25
                            } else if (couponInput.isNotBlank()) {
                                appliedDiscount = 15
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("تطبيق", color = Color(0xFF101014), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                if (appliedDiscount > 0) {
                    Text(
                        text = "✓ تم تطبيق خصم $appliedDiscount% بنجاح!",
                        color = SuccessEmerald,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // 1. ACTIVE PAYMENT METHOD: ORANGE CASH ONLY
                Text(
                    text = "وسيلة الدفع المعتمدة والمتاحة حالياً:",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = GoldPrimary)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1408)),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFFF7900))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFFFF7900)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("O", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "أورنج كاش (Orange Cash) • الوسيلة الحصرية المفعلة",
                                    color = Color(0xFFFF7900),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    fontFamily = CairoFontFamily
                                )
                                Text(
                                    text = "التحويل متاح لحظياً من أي محفظة أورنج أو محفظة مصرية",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Transfer Number Box
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(text = "رقم تحويل أورنج كاش:", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(text = "01207164416", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = GoldPrimary, fontFamily = CinzelFontFamily)
                                }
                                OutlinedButton(
                                    onClick = {
                                        val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                        val clip = android.content.ClipData.newPlainText("Orange Cash", "01207164416")
                                        clipboard.setPrimaryClip(clip)
                                    },
                                    shape = RoundedCornerShape(6.dp),
                                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("نسخ", fontSize = 11.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Confirmation Contact Box
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(text = "جهة تأكيد الدفع والاستفسار:", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(text = "01206449934", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, fontFamily = CinzelFontFamily)
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    IconButton(
                                        onClick = {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/201206449934"))
                                            context.startActivity(intent)
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(Icons.Default.Chat, contentDescription = "WhatsApp", tint = Color(0xFF25D366))
                                    }
                                    IconButton(
                                        onClick = {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/+201206449934"))
                                            context.startActivity(intent)
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(Icons.Default.Chat, contentDescription = "Telegram", tint = Color(0xFF0088CC))
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Receipt Upload Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "إرفاق إيصال التحويل (صورة الشاشة):",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldPrimary
                                )
                                Spacer(modifier = Modifier.height(6.dp))

                                if (receiptImageUri != null) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessEmerald, modifier = Modifier.size(18.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("تم إرفاق إيصال التحويل بنجاح", fontSize = 11.sp, color = SuccessEmerald, fontWeight = FontWeight.Bold)
                                        }
                                        IconButton(onClick = { receiptImageUri = null }, modifier = Modifier.size(24.dp)) {
                                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorRuby)
                                        }
                                    }
                                } else {
                                    OutlinedButton(
                                        onClick = {
                                            photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("اختيار صورة إيصال التحويل من المعرض", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }

                // Transaction Ref / Phone input
                OutlinedTextField(
                    value = txnInput,
                    onValueChange = { txnInput = it },
                    label = { Text("رقم هاتف المحول أو كود العملية (اختياري مع الإيصال)") },
                    placeholder = { Text("مثال: 012xxxxxxxx أو Ref #984021") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp)
                )

                // 2. OTHER PAYMENT METHODS - UNAVAILABLE INITIALLY
                Text(
                    text = "طرق دفع أخرى (قيد التجهيز والتفعيل):",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                )

                unavailableMethods.forEach { method ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .border(0.8.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Block,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = method,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                                    )
                                )
                            }
                            Text(
                                text = "غير متوفر حاليًا",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = ErrorRuby.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirmBuy("أورنج كاش (Orange Cash - 01207164416)")
                    orderSubmittedForReview = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7900)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("confirm_purchase_cta")
            ) {
                Text(
                    text = "إرسال إشعار الدفع للمراجعة (${finalPrice} ج.م)",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("إلغاء") }
        }
    )
}

@Composable
fun BundleBuyDialog(
    bundle: TemplateBundle,
    strings: TranslationStrings,
    onDismiss: () -> Unit,
    onConfirmBuy: (paymentMethod: String) -> Unit
) {
    val context = LocalContext.current
    var txnInput by remember { mutableStateOf("") }
    var receiptImageUri by remember { mutableStateOf<String?>(null) }
    var orderSubmittedForReview by remember { mutableStateOf(false) }

    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            receiptImageUri = uri.toString()
        }
    }

    val unavailableMethods = listOf(
        "فودافون كاش (Vodafone Cash)",
        "تطبيق إنستاباي (InstaPay)",
        "بايبال (PayPal)",
        "البطاقات البنكية (Visa & Mastercard)",
        "المحافظ الإلكترونية الذكية الأخرى"
    )

    if (orderSubmittedForReview) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = SuccessEmerald, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "تم استلام طلب باقة ${bundle.titleAr}",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = GoldPrimary, fontFamily = CairoFontFamily)
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "تم تسجيل طلب شراء الباقة وإرسال الإشعار للإدارة للمراجعة والتدقيق.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "حالة الطلب: قيد المراجعة والتدقيق (Pending Review)", fontWeight = FontWeight.Bold, color = GoldPrimary, fontSize = 12.sp)
                            Text(text = "سيتم تفعيل وصولك لكافة قوالب الباقة فور التحقق من إيصال التحويل.", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("تم", color = Color(0xFF101014), fontWeight = FontWeight.Bold)
                }
            }
        )
        return
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "شراء ${bundle.titleAr}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = GoldPrimary,
                    fontFamily = CairoFontFamily
                )
            )
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Price summary
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "سعر الباقة الخاص:", style = MaterialTheme.typography.bodySmall)
                        Text(
                            text = "${bundle.originalPriceEgp} ${strings.currencyEgp}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                            )
                        )
                    }
                    Text(
                        text = "${bundle.priceEgp} ${strings.currencyEgp}",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = GoldPrimary,
                            fontWeight = FontWeight.Bold,
                            fontFamily = CinzelFontFamily
                        )
                    )
                }

                Text(
                    text = bundle.descriptionAr,
                    style = MaterialTheme.typography.bodySmall.copy(color = GoldLight)
                )

                // 1. ACTIVE PAYMENT METHOD: ORANGE CASH ONLY
                Text(
                    text = "وسيلة الدفع المعتمدة والمتاحة حالياً:",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = GoldPrimary)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1408)),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFFF7900))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFFFF7900)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("O", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "أورنج كاش (Orange Cash) • حصرياً",
                                    color = Color(0xFFFF7900),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    fontFamily = CairoFontFamily
                                )
                                Text(
                                    text = "حول المبلغ المطلوب وارفع الإيصال للاعتماد الفوري",
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Transfer Box
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(text = "رقم تحويل أورنج كاش:", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(text = "01207164416", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = GoldPrimary, fontFamily = CinzelFontFamily)
                                }
                                OutlinedButton(
                                    onClick = {
                                        val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                        val clip = android.content.ClipData.newPlainText("Orange Cash", "01207164416")
                                        clipboard.setPrimaryClip(clip)
                                    },
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("نسخ", fontSize = 11.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Confirmation Contact Box
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(text = "جهة تأكيد الدفع والاستفسار:", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(text = "01206449934", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, fontFamily = CinzelFontFamily)
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    IconButton(
                                        onClick = {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/201206449934"))
                                            context.startActivity(intent)
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(Icons.Default.Chat, contentDescription = "WhatsApp", tint = Color(0xFF25D366))
                                    }
                                    IconButton(
                                        onClick = {
                                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/+201206449934"))
                                            context.startActivity(intent)
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(Icons.Default.Chat, contentDescription = "Telegram", tint = Color(0xFF0088CC))
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Receipt Upload
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(text = "إرفاق صورة إيصال التحويل:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GoldPrimary)
                                Spacer(modifier = Modifier.height(6.dp))
                                if (receiptImageUri != null) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessEmerald, modifier = Modifier.size(18.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text("تم إرفاق إيصال التحويل", fontSize = 11.sp, color = SuccessEmerald, fontWeight = FontWeight.Bold)
                                        }
                                        IconButton(onClick = { receiptImageUri = null }, modifier = Modifier.size(24.dp)) {
                                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = ErrorRuby)
                                        }
                                    }
                                } else {
                                    OutlinedButton(
                                        onClick = {
                                            photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("اختيار صورة الإيصال من المعرض", fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }

                OutlinedTextField(
                    value = txnInput,
                    onValueChange = { txnInput = it },
                    label = { Text("رقم هاتف المحول أو كود العملية") },
                    placeholder = { Text("مثال: 012xxxxxxxx") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(8.dp)
                )

                // 2. UNAVAILABLE METHODS
                Text(
                    text = "طرق دفع أخرى (قيد التجهيز والتفعيل):",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                )

                unavailableMethods.forEach { method ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .border(0.8.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Block, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = method, style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)))
                            }
                            Text(text = "غير متوفر حاليًا", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = ErrorRuby.copy(alpha = 0.7f))
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirmBuy("أورنج كاش (Orange Cash - 01207164416)")
                    orderSubmittedForReview = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7900)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("confirm_bundle_purchase_cta")
            ) {
                Text(
                    text = "إرسال إشعار الدفع للمراجعة (${bundle.priceEgp} ج.م)",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("إلغاء") }
        }
    )
}

@Composable
fun AdminTemplateEditDialog(
    initialTemplate: DesignTemplate?,
    availableCategories: List<CategoryModel> = emptyList(),
    onDismiss: () -> Unit,
    onSave: (
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
    ) -> Unit
) {
    var titleAr by remember { mutableStateOf(initialTemplate?.titleAr ?: "") }
    var titleEn by remember { mutableStateOf(initialTemplate?.titleEn ?: "") }
    var descriptionAr by remember { mutableStateOf(initialTemplate?.descriptionAr ?: "") }
    var descriptionEn by remember { mutableStateOf(initialTemplate?.descriptionEn ?: "") }
    
    val defaultCategoryName = availableCategories.firstOrNull()?.nameAr ?: "مطاعم ومأكولات"
    var selectedCategory by remember { mutableStateOf(initialTemplate?.category ?: defaultCategoryName) }
    var isCustomCategory by remember { mutableStateOf(false) }
    var customCategoryText by remember { mutableStateOf("") }
    
    var selectedAspectRatio by remember { mutableStateOf(initialTemplate?.aspectRatio ?: "1:1") }
    
    var pricingType by remember {
        mutableStateOf(
            initialTemplate?.pricingType ?: if (initialTemplate?.isPremium == true) "PREMIUM" else "FREE"
        )
    }
    var priceEgpText by remember {
        mutableStateOf(
            if (initialTemplate != null && initialTemplate.priceEgp > 0) initialTemplate.priceEgp.toString() else "0"
        )
    }
    
    var isPublished by remember { mutableStateOf(initialTemplate?.isPublished ?: true) }
    var selectedTier by remember { mutableStateOf(initialTemplate?.proTier ?: ProTier.BASIC) }
    
    var uploadedImageUri by remember { mutableStateOf(initialTemplate?.imageUri) }
    var uploadedDesignFileName by remember { mutableStateOf(initialTemplate?.designFileName) }
    var uploadedDesignFileUri by remember { mutableStateOf(initialTemplate?.designFileUri) }
    
    var bgStartHex by remember { mutableStateOf(initialTemplate?.bgStartHex ?: "#0C0C10") }
    var bgEndHex by remember { mutableStateOf(initialTemplate?.bgEndHex ?: "#1D1912") }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            uploadedImageUri = uri.toString()
        }
    }

    val categoryNames = remember(availableCategories) {
        if (availableCategories.isNotEmpty()) {
            availableCategories.map { it.nameAr }
        } else {
            listOf(
                "مطاعم ومأكولات",
                "أزياء وجمال",
                "سيارات ومحركات",
                "عقارات ومقاولات",
                "سوشيال ميديا",
                "أعمال وتسويق",
                "مناسبات واحتفالات"
            )
        }
    }

    val aspectRatios = listOf("1:1", "4:5", "9:16", "16:9")

    val bgThemes = listOf(
        "ذهب وأسود" to ("#0C0C10" to "#1D1912"),
        "زمرد ملكي" to ("#081711" to "#133827"),
        "ياقوت عنابي" to ("#1A0B10" to "#2D121B"),
        "أزرق ليلي" to ("#091326" to "#16223B"),
        "عاجي فاتح" to ("#FAF8F5" to "#E8E2D5")
    )

    val effectiveCategory = if (isCustomCategory && customCategoryText.isNotBlank()) customCategoryText.trim() else selectedCategory

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (initialTemplate == null) "إضافة قالب جديد لمكتبة برايا" else "تعديل القالب: ${initialTemplate.titleAr}",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = GoldPrimary,
                    fontFamily = CairoFontFamily
                )
            )
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // 1. Upload Design Image Section (zero-permission photo picker)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "صورة التصميم أو ملف الغلاف (Photo / Banner):",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = GoldPrimary)
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        if (uploadedImageUri != null) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, GoldBorder, RoundedCornerShape(8.dp))
                                ) {
                                    AsyncImage(
                                        model = uploadedImageUri,
                                        contentDescription = "Uploaded Cover",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                                    Text("تم إرفاق صورة التصميم بنجاح", fontSize = 11.sp, color = SuccessEmerald, fontWeight = FontWeight.Bold)
                                    Text("سيتم عرضها مباشرة في المعاينة والمكتبة", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                                IconButton(onClick = { uploadedImageUri = null }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = ErrorRuby, modifier = Modifier.size(20.dp))
                                }
                            }
                        } else {
                            Button(
                                onClick = {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary.copy(alpha = 0.2f)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.fillMaxWidth().height(42.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary)
                            ) {
                                Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("رفع صورة التصميم من الجهاز", color = GoldPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // 2. Upload / Attach Design Source File
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "ملف التصميم المصدري (Supported Design Files - JSON / SVG / PSD / AI / ZIP):",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = GoldPrimary)
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        if (uploadedDesignFileName != null || uploadedDesignFileUri != null) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(imageVector = Icons.Default.InsertDriveFile, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(24.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(text = uploadedDesignFileName ?: "template_asset.json", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                                        Text(text = "ملف التصميم جاهز للتنزيل والاستخدام للمشترين", fontSize = 9.sp, color = SuccessEmerald)
                                    }
                                }
                                IconButton(onClick = {
                                    uploadedDesignFileUri = null
                                    uploadedDesignFileName = null
                                }) {
                                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete File", tint = ErrorRuby, modifier = Modifier.size(20.dp))
                                }
                            }
                        } else {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    onClick = {
                                        val randomId = (1000..9999).random()
                                        uploadedDesignFileName = "design_source_${randomId}.json"
                                        uploadedDesignFileUri = "content://baraya/templates/design_${randomId}.json"
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f).height(40.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder)
                                ) {
                                    Icon(Icons.Default.AttachFile, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("إرفاق ملف (.JSON)", color = GoldPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                                Button(
                                    onClick = {
                                        val randomId = (1000..9999).random()
                                        uploadedDesignFileName = "vector_source_${randomId}.svg"
                                        uploadedDesignFileUri = "content://baraya/vectors/vector_${randomId}.svg"
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.weight(1f).height(40.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder)
                                ) {
                                    Icon(Icons.Default.InsertDriveFile, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("إرفاق فكتور (.SVG)", color = GoldPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }

                // 3. Titles in Arabic and English
                OutlinedTextField(
                    value = titleAr,
                    onValueChange = { titleAr = it },
                    label = { Text("اسم القالب بالعربية *") },
                    placeholder = { Text("مثال: بوست عروض مطعم فاخر") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = titleEn,
                    onValueChange = { titleEn = it },
                    label = { Text("اسم القالب بالإنجليزية (English Title)") },
                    placeholder = { Text("e.g. Luxury Restaurant Offer") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )

                // 4. Descriptions in Arabic and English
                OutlinedTextField(
                    value = descriptionAr,
                    onValueChange = { descriptionAr = it },
                    label = { Text("وصف القالب بالعربية") },
                    placeholder = { Text("تصميم احترافي جاهز للطباعة والنشر...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    maxLines = 2
                )

                OutlinedTextField(
                    value = descriptionEn,
                    onValueChange = { descriptionEn = it },
                    label = { Text("وصف القالب بالإنجليزية (Description)") },
                    placeholder = { Text("Ready-to-use professional graphic design...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    maxLines = 2
                )

                // 5. Dynamic Categories & Custom Category
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "التصنيف:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                    TextButton(onClick = { isCustomCategory = !isCustomCategory }) {
                        Text(if (isCustomCategory) "اختيار من القائمة" else "+ إضافة تصنيف مخصص", fontSize = 10.sp, color = GoldPrimary)
                    }
                }

                if (isCustomCategory) {
                    OutlinedTextField(
                        value = customCategoryText,
                        onValueChange = { customCategoryText = it },
                        label = { Text("اسم التصنيف الجديد المخصص") },
                        placeholder = { Text("مثال: ستوري انستقرام أو مطبوعات طبية") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )
                } else {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(categoryNames) { cat ->
                            val isSel = selectedCategory == cat
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .border(1.dp, if (isSel) GoldPrimary else GoldBorder.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                                    .clickable { selectedCategory = cat },
                                color = if (isSel) GoldPrimary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = cat,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                                    fontSize = 11.sp,
                                    color = if (isSel) GoldPrimary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                // 6. Aspect Ratio
                Text(text = "النسبة والأبعاد:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    aspectRatios.forEach { ratio ->
                        val isSel = selectedAspectRatio == ratio
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .border(1.dp, if (isSel) GoldPrimary else GoldBorder.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                                .clickable { selectedAspectRatio = ratio },
                            color = if (isSel) GoldPrimary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = ratio,
                                modifier = Modifier.padding(vertical = 6.dp),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSel) GoldPrimary else MaterialTheme.colorScheme.onSurface,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }

                // 7. Background Color Themes
                Text(text = "نمط ألوان الخلفية:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(bgThemes) { (themeName, colors) ->
                        val isSel = bgStartHex == colors.first && bgEndHex == colors.second
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .border(1.dp, if (isSel) GoldPrimary else GoldBorder.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                                .clickable {
                                    bgStartHex = colors.first
                                    bgEndHex = colors.second
                                },
                            color = if (isSel) GoldPrimary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .background(Brush.linearGradient(listOf(Color(android.graphics.Color.parseColor(colors.first)), Color(android.graphics.Color.parseColor(colors.second)))))
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = themeName, fontSize = 10.sp, color = if (isSel) GoldPrimary else MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }

                // 8. Template Pricing: Free, Premium, Exclusive (Editable in EGP)
                Text(text = "تسعير القالب بالجنيه المصري (EGP Pricing):", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(
                        Triple("FREE", "مجاني (Free)", "0 ج.م"),
                        Triple("PREMIUM", "مدفوع (Premium)", "سعر مخصص"),
                        Triple("EXCLUSIVE", "حصري (Exclusive)", "فئة النخبة")
                    ).forEach { (typeKey, label, hint) ->
                        val isSel = pricingType == typeKey
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    width = if (isSel) 1.5.dp else 1.dp,
                                    color = if (isSel) GoldPrimary else GoldBorder.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    pricingType = typeKey
                                    when (typeKey) {
                                        "FREE" -> priceEgpText = "0"
                                        "PREMIUM" -> if (priceEgpText == "0") priceEgpText = "15"
                                        "EXCLUSIVE" -> if (priceEgpText == "0" || priceEgpText == "15") priceEgpText = "50"
                                    }
                                },
                            color = if (isSel) GoldPrimary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSel) GoldPrimary else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = hint,
                                    fontSize = 9.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                if (pricingType != "FREE") {
                    OutlinedTextField(
                        value = priceEgpText,
                        onValueChange = { priceEgpText = it },
                        label = { Text("سعر القالب بالجنيه المصري (EGP) *") },
                        placeholder = { Text("مثال: 15 أو 25 أو 50") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        leadingIcon = {
                            Text("ج.م", fontWeight = FontWeight.Bold, color = GoldPrimary, modifier = Modifier.padding(start = 12.dp))
                        }
                    )
                }

                // 9. Publish / Draft Status
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = if (isPublished) "الحالة: منشور في المتجر للجميع" else "الحالة: مسودة محفوظة للمالك فقط", style = MaterialTheme.typography.bodySmall)
                    Switch(
                        checked = isPublished,
                        onCheckedChange = { isPublished = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = SuccessEmerald)
                    )
                }

                // 10. Live Visual Preview Canvas
                Text(text = "معاينة حية للتصميم قبل النشر:", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = GoldPrimary)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, GoldBorder, RoundedCornerShape(8.dp))
                ) {
                    val isPrem = pricingType != "FREE"
                    val priceVal = if (isPrem) priceEgpText.toIntOrNull() ?: 15 else 0
                    val previewTpl = (initialTemplate ?: DesignTemplate(
                        id = "preview_temp",
                        titleAr = titleAr.ifBlank { "معاينة القالب" },
                        titleEn = titleEn.ifBlank { "Template Preview" },
                        category = effectiveCategory,
                        aspectRatio = selectedAspectRatio,
                        isPremium = isPrem,
                        pricingType = pricingType,
                        priceEgp = priceVal,
                        bgStartHex = bgStartHex,
                        bgEndHex = bgEndHex,
                        imageUri = uploadedImageUri,
                        designFileUri = uploadedDesignFileUri,
                        designFileName = uploadedDesignFileName
                    )).copy(
                        titleAr = titleAr.ifBlank { "معاينة القالب" },
                        titleEn = titleEn.ifBlank { "Template Preview" },
                        descriptionAr = descriptionAr,
                        descriptionEn = descriptionEn,
                        category = effectiveCategory,
                        aspectRatio = selectedAspectRatio,
                        isPremium = isPrem,
                        pricingType = pricingType,
                        priceEgp = priceVal,
                        bgStartHex = bgStartHex,
                        bgEndHex = bgEndHex,
                        imageUri = uploadedImageUri,
                        designFileUri = uploadedDesignFileUri,
                        designFileName = uploadedDesignFileName
                    )
                    TemplateVisualCanvas(
                        template = previewTpl,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val isPrem = pricingType != "FREE"
                    val price = if (isPrem) (priceEgpText.toIntOrNull() ?: 15) else 0
                    onSave(
                        titleAr.ifBlank { "قالب بدون عنوان" },
                        titleEn.ifBlank { "Untitled Template" },
                        descriptionAr,
                        descriptionEn,
                        effectiveCategory,
                        selectedAspectRatio,
                        isPrem,
                        pricingType,
                        if (isPrem) selectedTier else null,
                        price,
                        bgStartHex,
                        bgEndHex,
                        isPublished,
                        uploadedImageUri,
                        uploadedDesignFileUri,
                        uploadedDesignFileName
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("حفظ ونشر القالب", color = Color(0xFF101014), fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("إلغاء") }
        }
    )
}

/**
 * Security authorization modal for the owner (Mohamed Baraya).
 * Protects the admin template management panel from unauthorized users.
 */
@Composable
fun OwnerAuthDialog(
    onDismiss: () -> Unit,
    onVerify: (pin: String) -> Boolean
) {
    var pinInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Security, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "بوابة المالك الرسمية | Baraya Owner",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = GoldPrimary, fontFamily = CairoFontFamily)
                )
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "هذه اللوحة مخصصة حصرياً للمالك (المهندس محمد برايا) لإدارة القوالب، الأسعار، والمبيعات.",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "رمز المالك الافتراضي: 2026",
                    style = MaterialTheme.typography.labelSmall.copy(color = GoldLight, fontWeight = FontWeight.Bold)
                )

                OutlinedTextField(
                    value = pinInput,
                    onValueChange = {
                        pinInput = it
                        errorMessage = null
                    },
                    label = { Text("أدخل رمز مرور المالك (PIN)") },
                    placeholder = { Text("2026") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    isError = errorMessage != null
                )

                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = ErrorRuby,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (onVerify(pinInput)) {
                        onDismiss()
                    } else {
                        errorMessage = "رمز المرور غير صحيح! تصريح المالك مطلوب."
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFF101014), modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("تسجيل دخول المالك", color = Color(0xFF101014), fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("إلغاء") }
        }
    )
}

/**
 * Confirmation dialog before deleting a template.
 */
@Composable
fun DeleteConfirmDialog(
    templateTitle: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("تأكيد حذف القالب", color = ErrorRuby, fontWeight = FontWeight.Bold) },
        text = {
            Text("هل أنت متأكد من رغبتك في حذف قالب «$templateTitle» نهائياً من المكتبة وقاعدة البيانات؟ هذا الإجراء لا يمكن التراجع عنه.")
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = ErrorRuby),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("تأكيد الحذف", color = Color.White, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("إلغاء") }
        }
    )
}
