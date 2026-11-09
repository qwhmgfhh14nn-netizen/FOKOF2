package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.example.data.model.ChatMessage
import com.example.ui.locale.TranslationStrings
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiDirectorChatDialog(
    strings: TranslationStrings,
    messages: List<ChatMessage>,
    isSending: Boolean,
    onSendMessage: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var textInput by remember { mutableStateOf("") }

    val quickSuggestions = listOf(
        "أفضل تناسق للألوان الذهبية الفاخرة",
        "كيف أختار الخط العربي المناسب لهويتي؟",
        "أفكار إعلانية لشهر العروض والتخفيضات",
        "نصائح لتصميم شعار مونوغرام بسيط"
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .padding(16.dp)
        ) {
            // Sheet Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(GoldPrimary.copy(alpha = 0.2f))
                            .border(1.dp, GoldPrimary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = GoldPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = strings.chatTitle,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = GoldPrimary
                            )
                        )
                        Text(
                            text = strings.chatSubtitle,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = GoldPrimary)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Suggestions LazyRow
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(quickSuggestions) { prompt ->
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, GoldBorder, RoundedCornerShape(8.dp))
                            .clickable { onSendMessage(prompt) },
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = prompt,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = GoldLight,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Chat Messages List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(messages) { msg ->
                    ChatBubble(msg = msg)
                }
            }

            // Input Bar
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = textInput,
                    onValueChange = { textInput = it },
                    placeholder = { Text(strings.chatPlaceholder, fontSize = 12.sp) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("chat_input_text_field"),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(GoldPrimary)
                        .clickable(enabled = textInput.isNotBlank() && !isSending) {
                            val toSend = textInput
                            textInput = ""
                            onSendMessage(toSend)
                        }
                        .testTag("send_chat_message_button"),
                    contentAlignment = Alignment.Center
                ) {
                    if (isSending) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color(0xFF101014),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = strings.send,
                            tint = Color(0xFF101014),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(msg: ChatMessage) {
    val isUser = msg.isUser
    val alignment = if (isUser) Alignment.End else Alignment.Start
    val bubbleColor = if (isUser) GoldPrimary else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (isUser) Color(0xFF101014) else MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment
    ) {
        Surface(
            shape = RoundedCornerShape(
                topStart = 14.dp,
                topEnd = 14.dp,
                bottomStart = if (isUser) 14.dp else 2.dp,
                bottomEnd = if (isUser) 2.dp else 14.dp
            ),
            color = bubbleColor,
            border = if (!isUser) androidx.compose.foundation.BorderStroke(1.dp, GoldBorder) else null
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = msg.text,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = textColor,
                        lineHeight = 22.sp,
                        fontFamily = CairoFontFamily
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = msg.timestamp,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = textColor.copy(alpha = 0.7f),
                        fontSize = 9.sp
                    ),
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
