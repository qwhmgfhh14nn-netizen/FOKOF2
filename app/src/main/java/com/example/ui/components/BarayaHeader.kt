package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.locale.AppLanguage
import com.example.ui.locale.TranslationStrings
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary

@Composable
fun BarayaHeader(
    strings: TranslationStrings,
    isDarkTheme: Boolean,
    currentLanguage: AppLanguage,
    onToggleTheme: () -> Unit,
    onToggleLanguage: () -> Unit,
    onOpenChat: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Brand Monogram & Title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.testTag("brand_header_container")
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF1E1A14), Color(0xFF121216))
                                )
                            )
                            .border(1.5.dp, GoldPrimary, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baraya_logo),
                            contentDescription = "Baraya AI Logo",
                            modifier = Modifier.size(40.dp).clip(RoundedCornerShape(10.dp))
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = strings.appName,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = GoldPrimary
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(GoldPrimary.copy(alpha = 0.2f))
                                    .border(0.8.dp, GoldPrimary, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.WorkspacePremium,
                                        contentDescription = "VIP",
                                        tint = GoldPrimary,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    Text(
                                        text = "VIP",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldPrimary,
                                        fontFamily = CinzelFontFamily
                                    )
                                }
                            }
                        }

                        Text(
                            text = strings.founderCredit,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                // Action Controls
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // AI Assistant Sparkle Button
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(GoldPrimary, Color(0xFFE5C158))
                                )
                            )
                            .clickable { onOpenChat() }
                            .testTag("ai_assistant_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "AI Assistant",
                            tint = Color(0xFF101014),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Language Switcher
                    IconButton(
                        onClick = onToggleLanguage,
                        modifier = Modifier
                            .size(38.dp)
                            .testTag("toggle_language_button")
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, GoldBorder, RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (currentLanguage == AppLanguage.ARABIC) "EN" else "عربي",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = GoldPrimary
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    // Dark / Light Theme Toggle
                    IconButton(
                        onClick = onToggleTheme,
                        modifier = Modifier
                            .size(38.dp)
                            .testTag("toggle_theme_button")
                    ) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Theme Toggle",
                            tint = GoldPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Tagline Banner
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "« ${strings.tagline} »",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.sp,
                    color = GoldLight.copy(alpha = 0.85f),
                    fontWeight = FontWeight.Medium
                ),
                maxLines = 1
            )
        }
    }
}
