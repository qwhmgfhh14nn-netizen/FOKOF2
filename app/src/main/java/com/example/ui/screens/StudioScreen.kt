package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatColorFill
import androidx.compose.material.icons.filled.FormatShapes
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.TextFields
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
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.CanvasElement
import com.example.data.model.CanvasElementType
import com.example.data.model.DesignTemplate
import com.example.data.model.ShapeType
import com.example.ui.locale.TranslationStrings
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.util.parseColor
import kotlin.math.roundToInt

@Composable
fun StudioScreen(
    strings: TranslationStrings,
    canvasElements: List<CanvasElement>,
    selectedElementId: String?,
    bgStartHex: String,
    bgEndHex: String,
    canvasBgImageRes: Int? = null,
    canvasBgImageUri: String? = null,
    templates: List<DesignTemplate>,
    canvasAspectRatio: String = "1:1",
    onSelectElement: (String?) -> Unit,
    onAddText: (String) -> Unit,
    onAddShape: (ShapeType) -> Unit,
    onAddCalligraphy: (String) -> Unit,
    onAddGraphicSticker: (String) -> Unit,
    onSetCanvasBgPhoto: (Int?, String?) -> Unit = { _, _ -> },
    onUpdateElement: ((CanvasElement) -> CanvasElement) -> Unit,
    onDeleteElement: () -> Unit,
    onDuplicateElement: () -> Unit,
    onClearCanvas: () -> Unit,
    onSetCanvasAspectRatio: (String) -> Unit,
    onSetCanvasBg: (String, String) -> Unit,
    onLoadTemplate: (DesignTemplate) -> Unit,
    onSaveProject: () -> Unit,
    onShowToast: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddTextDialog by remember { mutableStateOf(false) }
    var newTextInput by remember { mutableStateOf("") }
    var showTemplatesDialog by remember { mutableStateOf(false) }
    var showCalligraphyDialog by remember { mutableStateOf(false) }
    var showImagePickerDialog by remember { mutableStateOf(false) }
    var showReplacePhotoDialog by remember { mutableStateOf(false) }
    var showResizeDialog by remember { mutableStateOf(false) }
    var showBgColorDialog by remember { mutableStateOf(false) }

    val selectedElement = canvasElements.find { it.id == selectedElementId }

    // Color Swatches
    val colorPresets = listOf(
        "#D4AF37", // Signature Gold
        "#F3E5AB", // Champagne Gold
        "#E5C158", // Bright Gold
        "#FFFFFF", // Pure White
        "#121216", // Obsidian Black
        "#10B981", // Emerald Green
        "#EF4444", // Ruby Red
        "#3B82F6", // Sapphire Blue
        "#8B5CF6", // Royal Purple
        "#C0C0C0"  // Platinum Silver
    )

    val backgroundPresets = listOf(
        Pair("#0C0C10", "#1D1912"), // Obsidian & Gold
        Pair("#14141A", "#0B0B0E"), // Pure Luxury Dark
        Pair("#251D0F", "#100D08"), // Royal Bronze
        Pair("#FFFFFF", "#F4F0E6"), // Pearl White
        Pair("#1E1012", "#0F0B0C"), // Deep Velvet Ruby
        Pair("#0A192F", "#020C1B")  // Midnight Navy
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Title & Studio Top Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = strings.studioTitle,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary,
                        fontFamily = CairoFontFamily
                    )
                )
                Text(
                    text = "${strings.studioSubtitle} • [${canvasAspectRatio}]",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            Row {
                IconButton(onClick = { showResizeDialog = true }) {
                    Icon(imageVector = Icons.Default.AspectRatio, contentDescription = "Resize", tint = GoldPrimary)
                }
                IconButton(onClick = { showBgColorDialog = true }) {
                    Icon(imageVector = Icons.Default.FormatColorFill, contentDescription = "Background", tint = GoldPrimary)
                }
                IconButton(onClick = onSaveProject) {
                    Icon(imageVector = Icons.Default.Save, contentDescription = "Save Project", tint = GoldPrimary)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Quick Tools Toolbar
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                StudioToolChip(
                    icon = Icons.Default.AddPhotoAlternate,
                    label = "تغيير الصورة",
                    onClick = { showReplacePhotoDialog = true }
                )
            }
            item {
                StudioToolChip(
                    icon = Icons.Default.TextFields,
                    label = strings.addText,
                    onClick = {
                        newTextInput = ""
                        showAddTextDialog = true
                    }
                )
            }
            item {
                StudioToolChip(
                    icon = Icons.Default.Brush,
                    label = strings.addCalligraphy,
                    onClick = { showCalligraphyDialog = true }
                )
            }
            item {
                StudioToolChip(
                    icon = Icons.Default.FormatShapes,
                    label = strings.addShape,
                    onClick = { onAddShape(ShapeType.ROUNDED_RECT) }
                )
            }
            item {
                StudioToolChip(
                    icon = Icons.Default.BookmarkBorder,
                    label = strings.templates,
                    onClick = { showTemplatesDialog = true }
                )
            }
            item {
                StudioToolChip(
                    icon = Icons.Default.CleaningServices,
                    label = strings.clearCanvas,
                    onClick = onClearCanvas
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // THE INTERACTIVE DESIGN CANVAS
        val canvasRatio = when (canvasAspectRatio) {
            "16:9" -> 16f / 9f
            "9:16" -> 9f / 16f
            "4:5" -> 4f / 5f
            else -> 1f
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(canvasRatio.coerceIn(0.7f, 1.6f))
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, GoldBorder, RoundedCornerShape(16.dp))
                .testTag("interactive_design_canvas"),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                parseColor(bgStartHex, Color(0xFF0C0C10)),
                                parseColor(bgEndHex, Color(0xFF1D1912))
                            )
                        )
                    )
                    .clickable { onSelectElement(null) }
            ) {
                // If a real photographic background is configured, render it as background layer
                if (canvasBgImageRes != null) {
                    Image(
                        painter = painterResource(id = canvasBgImageRes),
                        contentDescription = "Canvas Background Photo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    // High-contrast Canva dark vignette overlay for headline & price readability
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.55f),
                                        Color.Black.copy(alpha = 0.15f),
                                        Color.Black.copy(alpha = 0.88f)
                                    )
                                )
                            )
                    )
                }

                // Render Canvas Elements
                canvasElements.forEach { element ->
                    val isSelected = element.id == selectedElementId

                    Box(
                        modifier = Modifier
                            .offset {
                                IntOffset(
                                    element.x.roundToInt(),
                                    element.y.roundToInt()
                                )
                            }
                            .rotate(element.rotation)
                            .scale(element.scale)
                            .pointerInput(element.id) {
                                detectDragGestures { change, dragAmount ->
                                    change.consume()
                                    onSelectElement(element.id)
                                    onUpdateElement { current ->
                                        current.copy(
                                            x = (current.x + dragAmount.x).coerceIn(0f, 600f),
                                            y = (current.y + dragAmount.y).coerceIn(0f, 600f)
                                        )
                                    }
                                }
                            }
                            .clickable { onSelectElement(element.id) }
                            .then(
                                if (isSelected) {
                                    Modifier
                                        .border(
                                            width = 1.5.dp,
                                            color = GoldPrimary,
                                            shape = RoundedCornerShape(6.dp)
                                        )
                                        .padding(4.dp)
                                } else {
                                    Modifier.padding(4.dp)
                                }
                            )
                    ) {
                        RenderElement(element)
                    }
                }

                // Empty canvas prompt
                if (canvasElements.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = strings.selectElementPrompt,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(24.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Selected Element Inspector / Properties Panel
        if (selectedElement != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("element_inspector_panel"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${strings.layers}: ${selectedElement.type.name}",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = GoldPrimary
                            )
                        )

                        Row {
                            IconButton(
                                onClick = onDuplicateElement,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Duplicate", tint = GoldPrimary, modifier = Modifier.size(18.dp))
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            IconButton(
                                onClick = onDeleteElement,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(18.dp))
                            }
                        }
                    }

                    // Text Editing
                    if (selectedElement.type == CanvasElementType.TEXT || selectedElement.type == CanvasElementType.CALLIGRAPHY) {
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = selectedElement.text,
                            onValueChange = { newTxt ->
                                onUpdateElement { it.copy(text = newTxt) }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("محتوى النص (Cairo)") },
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(imageVector = Icons.Default.FormatSize, contentDescription = "Font Size", tint = GoldPrimary, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Slider(
                                value = selectedElement.fontSize,
                                onValueChange = { size ->
                                    onUpdateElement { it.copy(fontSize = size) }
                                },
                                valueRange = 12f..64f,
                                modifier = Modifier.weight(1f),
                                colors = SliderDefaults.colors(
                                    thumbColor = GoldPrimary,
                                    activeTrackColor = GoldPrimary
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${selectedElement.fontSize.toInt()}sp",
                                style = MaterialTheme.typography.bodySmall,
                                color = GoldPrimary
                            )

                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(
                                onClick = { onUpdateElement { it.copy(isBold = !it.isBold) } },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FormatBold,
                                    contentDescription = "Bold",
                                    tint = if (selectedElement.isBold) GoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Shape size & opacity slider
                    if (selectedElement.type == CanvasElementType.SHAPE) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "الحجم والتناغم", style = MaterialTheme.typography.bodySmall)
                        Slider(
                            value = selectedElement.scale,
                            onValueChange = { s -> onUpdateElement { it.copy(scale = s) } },
                            valueRange = 0.5f..2.5f,
                            colors = SliderDefaults.colors(thumbColor = GoldPrimary, activeTrackColor = GoldPrimary)
                        )
                    }

                    // Color Palette
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = strings.color, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium))
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(colorPresets) { hex ->
                            val color = parseColor(hex, Color.White)
                            val isColorSelected = selectedElement.colorHex.equals(hex, ignoreCase = true)
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .border(
                                        width = if (isColorSelected) 2.5.dp else 1.dp,
                                        color = if (isColorSelected) GoldPrimary else Color.Gray.copy(alpha = 0.4f),
                                        shape = CircleShape
                                    )
                                    .clickable { onUpdateElement { it.copy(colorHex = hex) } }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Save & Export Action Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = onSaveProject,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("studio_save_project_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldPrimary,
                    contentColor = Color(0xFF101014)
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = strings.saveProject, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = {
                    onShowToast("تم حفظ التصميم وتنزيله بجودة Ultra HD 4K!")
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .testTag("studio_export_design_button"),
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary)
            ) {
                Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = strings.exportDesign, color = GoldPrimary, fontWeight = FontWeight.Bold)
            }
        }
    }

    // Dialog 1: Add Text
    if (showAddTextDialog) {
        AlertDialog(
            onDismissRequest = { showAddTextDialog = false },
            title = { Text(text = strings.addText, color = GoldPrimary) },
            text = {
                OutlinedTextField(
                    value = newTextInput,
                    onValueChange = { newTextInput = it },
                    placeholder = { Text("اكتب نص التصميم...") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTextInput.isNotBlank()) onAddText(newTextInput) else onAddText(strings.sampleText)
                        showAddTextDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                ) {
                    Text("إضافة", color = Color(0xFF101014), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddTextDialog = false }) { Text("إلغاء") }
            }
        )
    }

    // Dialog: Replace Photographic Image (Canva-style)
    if (showReplacePhotoDialog) {
        AlertDialog(
            onDismissRequest = { showReplacePhotoDialog = false },
            title = {
                Text(
                    text = "استبدال صورة التصميم (Canva Style)",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = GoldPrimary,
                        fontWeight = FontWeight.Bold,
                        fontFamily = CairoFontFamily
                    )
                )
            },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "اختر صورة فوتوغرافية احترافية واقعية لتطبيقها كخلفية للتصميم:",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )

                    // Photo 1: Royal Charcoal Burger
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, GoldBorder, RoundedCornerShape(12.dp))
                            .clickable {
                                onSetCanvasBgPhoto(R.drawable.img_restaurant_food, null)
                                onShowToast("تم تعيين صورة برجر الفحم الملكي المشوي!")
                                showReplacePhotoDialog = false
                            },
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_restaurant_food),
                                contentDescription = "Burger",
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "🍔 برجر الفحم الملكي المشوي",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = GoldPrimary
                                    )
                                )
                                Text(
                                    text = "صورة فوتوغرافية واقعية - إعلانات مطاعم وجبات سريعة",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                        }
                    }

                    // Photo 2: Italian Wood-Fired Pizza
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, GoldBorder, RoundedCornerShape(12.dp))
                            .clickable {
                                onSetCanvasBgPhoto(R.drawable.img_restaurant_pizza, null)
                                onShowToast("تم تعيين صورة بيتزا الحطب الإيطالية!")
                                showReplacePhotoDialog = false
                            },
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_restaurant_pizza),
                                contentDescription = "Pizza",
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "🍕 بيتزا الحطب الإيطالية الأصيلة",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = GoldPrimary
                                    )
                                )
                                Text(
                                    text = "صورة فوتوغرافية واقعية - إعلانات بيتزا ومطاعم إيطالية",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                        }
                    }

                    // Photo 3: Brand Kit / Luxury Dark
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, GoldBorder, RoundedCornerShape(12.dp))
                            .clickable {
                                onSetCanvasBgPhoto(R.drawable.template_brand_kit, null)
                                onShowToast("تم تعيين خلفية الهوية البصرية الملكية!")
                                showReplacePhotoDialog = false
                            },
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.template_brand_kit),
                                contentDescription = "Brand Kit",
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "👑 هوية تجارية ملكية فاخرة",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = GoldPrimary
                                    )
                                )
                                Text(
                                    text = "خلفية أعمال وهوية بصرية فخمة",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                        }
                    }

                    // Remove Photo Option (Gradient Background)
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp))
                            .clickable {
                                onSetCanvasBgPhoto(null, null)
                                onShowToast("تمت إزالة الصورة والعودة للخلفية المتدرجة")
                                showReplacePhotoDialog = false
                            },
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CleaningServices,
                                contentDescription = "Clear Photo",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "إزالة الصورة والعودة للألوان والتدرجات",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { showReplacePhotoDialog = false }) {
                    Text("إلغاء", color = GoldPrimary)
                }
            }
        )
    }

    // Dialog 2: Image / Graphics Picker
    if (showImagePickerDialog) {
        val imageOptions = listOf(
            "🦅 شعار الصقر الملكي" to "🦅",
            "👑 التاج الذهبي" to "👑",
            "✨ بريق الإبداع والنجوم" to "✨",
            "⚡ صاعقة الطاقة والتسويق" to "⚡",
            "💎 جوهرة الفخامة" to "💎",
            "☕ رمز القهوة المختصة" to "☕",
            "🎯 رمز دقة التصويب" to "🎯",
            "🌟 نجمة التميز الذهبية" to "🌟"
        )
        AlertDialog(
            onDismissRequest = { showImagePickerDialog = false },
            title = { Text(text = strings.uploadImage, color = GoldPrimary) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "اختر رسماً أو ملصقاً فكتورياً أو أضف شعار برايا:", style = MaterialTheme.typography.bodySmall)
                    imageOptions.forEach { (label, symbol) ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, GoldBorder, RoundedCornerShape(8.dp))
                                .clickable {
                                    onAddGraphicSticker(symbol)
                                    showImagePickerDialog = false
                                },
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = symbol, fontSize = 24.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(text = label, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = { TextButton(onClick = { showImagePickerDialog = false }) { Text("إغلاق") } }
        )
    }

    // Dialog 3: Resize Design (Aspect Ratio)
    if (showResizeDialog) {
        val ratios = listOf(
            "1:1" to "مربع (انستغرام وبوستات)",
            "9:16" to "عمودي (ستوري وريلز وتيك توك)",
            "16:9" to "أفقي (يوتيوب وبانرات المواقع)",
            "4:5" to "بورتريه احترافي (إعلانات فيسبوك)"
        )
        AlertDialog(
            onDismissRequest = { showResizeDialog = false },
            title = { Text(text = strings.resizeDesign, color = GoldPrimary) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ratios.forEach { (ratio, desc) ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    width = if (canvasAspectRatio == ratio) 1.5.dp else 1.dp,
                                    color = if (canvasAspectRatio == ratio) GoldPrimary else GoldBorder,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    onSetCanvasAspectRatio(ratio)
                                    showResizeDialog = false
                                },
                            color = if (canvasAspectRatio == ratio) GoldPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(text = "$ratio - $desc", fontWeight = FontWeight.Bold, color = if (canvasAspectRatio == ratio) GoldPrimary else MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = { TextButton(onClick = { showResizeDialog = false }) { Text("إغلاق") } }
        )
    }

    // Dialog 4: Background Colors
    if (showBgColorDialog) {
        AlertDialog(
            onDismissRequest = { showBgColorDialog = false },
            title = { Text(text = "تغيير لون وخلفية التصميم", color = GoldPrimary) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    backgroundPresets.forEachIndexed { idx, (startHex, endHex) ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(parseColor(startHex, Color.Black), parseColor(endHex, Color.DarkGray))
                                    )
                                )
                                .border(1.dp, GoldBorder, RoundedCornerShape(8.dp))
                                .clickable {
                                    onSetCanvasBg(startHex, endHex)
                                    showBgColorDialog = false
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "نمط خلفية فخمة #$idx",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = { TextButton(onClick = { showBgColorDialog = false }) { Text("إغلاق") } }
        )
    }

    // Dialog 5: Calligraphy
    if (showCalligraphyDialog) {
        val calligraphyPresets = listOf(
            "الابتكار والإبداع",
            "أفكارك تتحول إلى تصاميم قبل أن تتكلم",
            "برايا للذكاء الاصطناعي",
            "فخامة وأصالة مصرية",
            "عرض حصري وخاص",
            "BARAYA AI CREATIVE"
        )
        AlertDialog(
            onDismissRequest = { showCalligraphyDialog = false },
            title = { Text(text = strings.addCalligraphy, color = GoldPrimary) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    calligraphyPresets.forEach { preset ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, GoldBorder, RoundedCornerShape(8.dp))
                                .clickable {
                                    onAddCalligraphy(preset)
                                    showCalligraphyDialog = false
                                },
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = preset,
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = GoldPrimary,
                                    fontFamily = CairoFontFamily
                                )
                            )
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = { TextButton(onClick = { showCalligraphyDialog = false }) { Text("إغلاق") } }
        )
    }

    // Dialog 6: Load Templates
    if (showTemplatesDialog) {
        AlertDialog(
            onDismissRequest = { showTemplatesDialog = false },
            title = { Text(text = strings.templates, color = GoldPrimary) },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    templates.forEach { tpl ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onLoadTemplate(tpl)
                                    showTemplatesDialog = false
                                },
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GoldBorder)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = tpl.titleAr,
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = GoldPrimary
                                        )
                                    )
                                    Text(
                                        text = "${tpl.category} • ${tpl.aspectRatio} • ${if (tpl.priceEgp > 0) "${tpl.priceEgp} ${strings.currencyEgp}" else strings.freeBadge}",
                                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = { TextButton(onClick = { showTemplatesDialog = false }) { Text("إغلاق") } }
        )
    }
}

@Composable
fun StudioToolChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, GoldBorder, RoundedCornerShape(10.dp))
            .clickable { onClick() },
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = GoldPrimary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Composable
fun RenderElement(element: CanvasElement) {
    val elementColor = parseColor(element.colorHex, GoldPrimary)

    when (element.type) {
        CanvasElementType.TEXT, CanvasElementType.CALLIGRAPHY -> {
            Text(
                text = element.text,
                fontSize = element.fontSize.sp,
                fontWeight = if (element.isBold) FontWeight.Bold else FontWeight.Normal,
                color = elementColor,
                fontFamily = CairoFontFamily
            )
        }
        CanvasElementType.SHAPE -> {
            val shapeModifier = Modifier
                .size(element.width.dp, element.height.dp)
                .background(elementColor.copy(alpha = element.opacity))

            when (element.shapeType) {
                ShapeType.CIRCLE -> Box(modifier = shapeModifier.clip(CircleShape))
                ShapeType.ROUNDED_RECT -> Box(modifier = shapeModifier.clip(RoundedCornerShape(12.dp)))
                ShapeType.RECTANGLE -> Box(modifier = shapeModifier)
                ShapeType.DIAMOND -> Box(modifier = shapeModifier.rotate(45f).clip(RoundedCornerShape(4.dp)))
                ShapeType.STAR -> Box(
                    modifier = Modifier
                        .size(element.width.dp, element.height.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "★",
                        fontSize = element.fontSize.sp,
                        color = elementColor
                    )
                }
                ShapeType.HEXAGON -> Box(modifier = shapeModifier.clip(RoundedCornerShape(8.dp)))
            }
        }
        CanvasElementType.IMAGE_BADGE -> {
            Box(
                modifier = Modifier
                    .size(element.width.dp, element.height.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, elementColor, RoundedCornerShape(8.dp))
                    .background(elementColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = element.text.ifBlank { "BARAYA" },
                    color = elementColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

