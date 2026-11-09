package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DriveFileRenameOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.WorkspacePremium
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.DesignTemplate
import com.example.data.model.UserProfile
import com.example.ui.components.OwnerAuthDialog
import com.example.ui.locale.AppLanguage
import com.example.ui.locale.TranslationStrings
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.CinzelFontFamily
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.util.parseColor

@Composable
fun AccountScreen(
    strings: TranslationStrings,
    isDarkTheme: Boolean,
    currentLanguage: AppLanguage,
    currentUser: UserProfile,
    savedProjects: List<DesignTemplate>,
    templates: List<DesignTemplate>,
    favoriteIds: Set<String>,
    isOwnerAuthenticated: Boolean,
    onToggleTheme: () -> Unit,
    onToggleLanguage: () -> Unit,
    onOpenProject: (DesignTemplate) -> Unit,
    onRenameProject: (String, String) -> Unit,
    onDuplicateProject: (String) -> Unit,
    onDeleteProject: (String) -> Unit,
    onStartNewDesign: () -> Unit,
    onOpenAdmin: () -> Unit,
    onVerifyOwnerPin: (String) -> Boolean,
    onUpgradeVip: () -> Unit,
    onLogin: (email: String, password: String, onComplete: (Boolean, String) -> Unit) -> Unit,
    onRegister: (name: String, email: String, password: String, phone: String, bio: String, onComplete: (Boolean, String) -> Unit) -> Unit,
    onLogout: () -> Unit,
    onContinueAsGuest: () -> Unit,
    onUpdateProfile: (name: String, email: String, phone: String, bio: String, avatarIndex: Int, onComplete: (Boolean, String) -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) } // 0: Projects, 1: Favorites, 2: Settings
    var showOwnerAuthDialog by remember { mutableStateOf(false) }
    var showEditProfileDialog by remember { mutableStateOf(false) }
    var showAuthDialog by remember { mutableStateOf(false) }
    var showLogoutConfirmDialog by remember { mutableStateOf(false) }

    // Project Operations Dialogs State
    var renamingProject by remember { mutableStateOf<DesignTemplate?>(null) }
    var renameInputText by remember { mutableStateOf("") }
    var deletingProject by remember { mutableStateOf<DesignTemplate?>(null) }

    val favoriteTemplates = templates.filter { favoriteIds.contains(it.id) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // User Profile Header Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            border = androidx.compose.foundation.BorderStroke(1.2.dp, GoldBorder)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        // Avatar Icon with dynamic gradient and luxury ring
                        val avatarColors = listOf(
                            listOf(Color(0xFFD4AF37), Color(0xFF8B7322)),
                            listOf(Color(0xFF3B82F6), Color(0xFF1D4ED8)),
                            listOf(Color(0xFFEC4899), Color(0xFFBE185D)),
                            listOf(Color(0xFF10B981), Color(0xFF047857)),
                            listOf(Color(0xFF8B5CF6), Color(0xFF6D28D9)),
                            listOf(Color(0xFFF97316), Color(0xFFC2410C))
                        )
                        val safeAvatarIndex = (currentUser.avatarIndex).coerceIn(0, avatarColors.size - 1)
                        val currentAvatarBrush = Brush.linearGradient(avatarColors[safeAvatarIndex])

                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(currentAvatarBrush)
                                .border(1.5.dp, GoldPrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            if (currentUser.id == "usr_baraya_founder") {
                                Image(
                                    painter = painterResource(id = R.drawable.baraya_logo),
                                    contentDescription = "Avatar",
                                    modifier = Modifier.size(46.dp).clip(CircleShape)
                                )
                            } else {
                                Text(
                                    text = currentUser.name.trim().take(1).ifBlank { "B" },
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = CairoFontFamily
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = currentUser.name,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = CairoFontFamily
                                    )
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                if (currentUser.isGuest) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = strings.guestBadge,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                } else if (currentUser.isVip) {
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
                            }

                            Text(
                                text = currentUser.email,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            )

                            if (currentUser.bio.isNotBlank()) {
                                Text(
                                    text = currentUser.bio,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                        fontSize = 11.sp
                                    ),
                                    maxLines = 2
                                )
                            }
                        }
                    }

                    // Profile Actions: Edit or Login
                    if (currentUser.isGuest) {
                        Button(
                            onClick = { showAuthDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("account_login_button")
                        ) {
                            Icon(imageVector = Icons.Default.Login, contentDescription = null, tint = Color(0xFF101014), modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = strings.loginButton, color = Color(0xFF101014), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { showEditProfileDialog = true },
                                modifier = Modifier.testTag("edit_profile_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = strings.editProfile,
                                    tint = GoldPrimary
                                )
                            }
                            IconButton(
                                onClick = { showLogoutConfirmDialog = true },
                                modifier = Modifier.testTag("logout_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Logout,
                                    contentDescription = strings.logoutButton,
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }

                // Guest Banner Prompt
                if (currentUser.isGuest) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(GoldPrimary.copy(alpha = 0.12f))
                            .border(1.dp, GoldPrimary.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = strings.guestNotice,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 10.sp
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            TextButton(
                                onClick = { showAuthDialog = true },
                                modifier = Modifier.testTag("guest_register_cta")
                            ) {
                                Text(
                                    text = strings.registerButton,
                                    color = GoldPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Account Tabs Selector (0: Projects, 1: Favorites, 2: Settings)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val tabs = listOf(
                strings.savedProjectsTab to Icons.Default.Folder,
                strings.favoritesTab to Icons.Default.Favorite,
                strings.settingsTab to Icons.Default.Settings
            )
            tabs.forEachIndexed { index, (tabLabel, tabIcon) ->
                val isSelected = selectedTab == index
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = if (isSelected) GoldPrimary else GoldBorder.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable { selectedTab = index }
                        .testTag("account_tab_$index"),
                    color = if (isSelected) GoldPrimary.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = tabIcon,
                            contentDescription = null,
                            tint = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = tabLabel,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurface,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            ),
                            maxLines = 1
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tab Content
        when (selectedTab) {
            // TAB 0: User-Protected Saved Projects
            0 -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Header with New Project Button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${savedProjects.size} ${if (currentLanguage == AppLanguage.ARABIC) "مشاريع خاصة بك" else "Personal Projects"}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.SemiBold
                            )
                        )

                        Button(
                            onClick = onStartNewDesign,
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("account_new_project_btn")
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color(0xFF101014), modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = strings.startDesigning, color = Color(0xFF101014), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (savedProjects.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.FolderOpen,
                                    contentDescription = null,
                                    tint = GoldPrimary.copy(alpha = 0.5f),
                                    modifier = Modifier.size(54.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = strings.noProjectsYet,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                OutlinedButton(
                                    onClick = onStartNewDesign,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(text = strings.startDesigning, color = GoldPrimary, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(savedProjects, key = { it.id }) { project ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .border(1.dp, GoldBorder, RoundedCornerShape(12.dp)),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(44.dp)
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(
                                                        Brush.linearGradient(
                                                            listOf(
                                                                parseColor(project.bgStartHex, Color(0xFF15151C)),
                                                                parseColor(project.bgEndHex, Color(0xFF221C11))
                                                            )
                                                        )
                                                    )
                                                    .border(1.dp, GoldBorder, RoundedCornerShape(8.dp)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = project.elements.firstOrNull()?.text?.take(2) ?: "B",
                                                    color = GoldPrimary,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 14.sp
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(10.dp))

                                            Column {
                                                Text(
                                                    text = project.titleAr,
                                                    style = MaterialTheme.typography.titleSmall.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        fontFamily = CairoFontFamily
                                                    )
                                                )
                                                Text(
                                                    text = "${project.elements.size} ${if (currentLanguage == AppLanguage.ARABIC) "عناصر" else "layers"} • ${project.category}",
                                                    style = MaterialTheme.typography.bodySmall.copy(
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                        fontSize = 11.sp
                                                    )
                                                )
                                            }
                                        }

                                        // Action buttons: Open in studio, Rename, Duplicate, Delete
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            IconButton(
                                                onClick = { onOpenProject(project) },
                                                modifier = Modifier.testTag("open_project_${project.id}")
                                            ) {
                                                Icon(imageVector = Icons.Default.Edit, contentDescription = strings.openProjectAction, tint = GoldPrimary, modifier = Modifier.size(20.dp))
                                            }
                                            IconButton(
                                                onClick = {
                                                    renamingProject = project
                                                    renameInputText = project.titleAr
                                                },
                                                modifier = Modifier.testTag("rename_project_${project.id}")
                                            ) {
                                                Icon(imageVector = Icons.Default.DriveFileRenameOutline, contentDescription = strings.renameProject, tint = GoldPrimary, modifier = Modifier.size(20.dp))
                                            }
                                            IconButton(
                                                onClick = { onDuplicateProject(project.id) },
                                                modifier = Modifier.testTag("duplicate_project_${project.id}")
                                            ) {
                                                Icon(imageVector = Icons.Default.ContentCopy, contentDescription = strings.duplicateProject, tint = GoldPrimary, modifier = Modifier.size(18.dp))
                                            }
                                            IconButton(
                                                onClick = { deletingProject = project },
                                                modifier = Modifier.testTag("delete_project_${project.id}")
                                            ) {
                                                Icon(imageVector = Icons.Default.Delete, contentDescription = strings.deleteProject, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(20.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // TAB 1: Favorites
            1 -> {
                if (favoriteTemplates.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (currentLanguage == AppLanguage.ARABIC) "لم تقم بتفضيل أي قوالب بعد. اضغط على أيقونة القلب في أي قالب لحفظه هنا!" else "No favorite templates yet. Tap the heart icon on any template to bookmark it here!",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(favoriteTemplates) { template ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(1.dp, GoldBorder, RoundedCornerShape(12.dp)),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(
                                            text = if (currentLanguage == AppLanguage.ARABIC) template.titleAr else template.titleEn,
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = CairoFontFamily
                                            )
                                        )
                                        Text(
                                            text = if (template.priceEgp > 0) "${template.priceEgp} ${strings.currencyEgp}" else strings.freeBadge,
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                color = GoldPrimary,
                                                fontWeight = FontWeight.Bold
                                            )
                                        )
                                    }

                                    Button(
                                        onClick = { onOpenProject(template) },
                                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(text = strings.useInStudio, color = Color(0xFF101014), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // TAB 2: Settings & Preferences
            2 -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Language Switch Setting
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Translate, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = strings.languageSetting,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                                )
                            }

                            Button(
                                onClick = onToggleLanguage,
                                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = if (currentLanguage == AppLanguage.ARABIC) "English" else "العربية",
                                    color = Color(0xFF101014),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Theme Toggle Setting
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                                    contentDescription = null,
                                    tint = GoldPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = if (isDarkTheme) strings.darkThemeLabel else strings.lightThemeLabel,
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                                )
                            }

                            Switch(
                                checked = isDarkTheme,
                                onCheckedChange = { onToggleTheme() },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = GoldPrimary,
                                    checkedTrackColor = GoldPrimary.copy(alpha = 0.3f)
                                )
                            )
                        }
                    }

                    // Secure Admin Dashboard Portal
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.2.dp, GoldPrimary, RoundedCornerShape(12.dp))
                            .clickable {
                                if (isOwnerAuthenticated) {
                                    onOpenAdmin()
                                } else {
                                    showOwnerAuthDialog = true
                                }
                            }
                            .testTag("admin_portal_card"),
                        colors = CardDefaults.cardColors(
                            containerColor = GoldPrimary.copy(alpha = 0.1f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AdminPanelSettings,
                                    contentDescription = null,
                                    tint = GoldPrimary,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = strings.adminPortalButton,
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = GoldPrimary
                                        )
                                    )
                                    Text(
                                        text = "إدارة القوالب، الأسعار، العروض والمعاملات المالية",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }

                            Icon(
                                imageVector = Icons.Default.AdminPanelSettings,
                                contentDescription = null,
                                tint = GoldPrimary
                            )
                        }
                    }

                    // VIP Upgrade Request
                    OutlinedButton(
                        onClick = onUpgradeVip,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary)
                    ) {
                        Icon(imageVector = Icons.Default.Payment, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "إشعار تحويل يدوي / ترقية الباقة", color = GoldPrimary, fontWeight = FontWeight.Bold)
                    }

                    // Logout Button if signed in
                    if (!currentUser.isGuest) {
                        OutlinedButton(
                            onClick = { showLogoutConfirmDialog = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f))
                        ) {
                            Icon(imageVector = Icons.Default.Logout, contentDescription = null, tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = strings.logoutButton, color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    // --- DIALOG 1: Edit Profile Dialog ---
    if (showEditProfileDialog) {
        var editName by remember { mutableStateOf(currentUser.name) }
        var editEmail by remember { mutableStateOf(currentUser.email) }
        var editPhone by remember { mutableStateOf(currentUser.phone) }
        var editBio by remember { mutableStateOf(currentUser.bio) }
        var editAvatarIndex by remember { mutableStateOf(currentUser.avatarIndex) }
        var editErrorMsg by remember { mutableStateOf("") }

        val avatarColors = listOf(
            listOf(Color(0xFFD4AF37), Color(0xFF8B7322)),
            listOf(Color(0xFF3B82F6), Color(0xFF1D4ED8)),
            listOf(Color(0xFFEC4899), Color(0xFFBE185D)),
            listOf(Color(0xFF10B981), Color(0xFF047857)),
            listOf(Color(0xFF8B5CF6), Color(0xFF6D28D9)),
            listOf(Color(0xFFF97316), Color(0xFFC2410C))
        )

        AlertDialog(
            onDismissRequest = { showEditProfileDialog = false },
            title = {
                Text(
                    text = strings.editProfile,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = GoldPrimary)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (editErrorMsg.isNotBlank()) {
                        Text(text = editErrorMsg, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }

                    // Avatar Selector
                    Text(text = strings.selectAvatar, style = MaterialTheme.typography.labelSmall)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        avatarColors.forEachIndexed { idx, colors ->
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Brush.linearGradient(colors))
                                    .border(
                                        width = if (editAvatarIndex == idx) 2.5.dp else 1.dp,
                                        color = if (editAvatarIndex == idx) GoldPrimary else Color.Transparent,
                                        shape = CircleShape
                                    )
                                    .clickable { editAvatarIndex = idx },
                                contentAlignment = Alignment.Center
                            ) {
                                if (editAvatarIndex == idx) {
                                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }

                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text(strings.fullNameLabel) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = editEmail,
                        onValueChange = { editEmail = it },
                        label = { Text(strings.emailAddressLabel) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = editPhone,
                        onValueChange = { editPhone = it },
                        label = { Text(strings.phoneNumberLabel) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = editBio,
                        onValueChange = { editBio = it },
                        label = { Text(strings.bioLabel) },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onUpdateProfile(editName, editEmail, editPhone, editBio, editAvatarIndex) { success, err ->
                            if (success) {
                                showEditProfileDialog = false
                            } else {
                                editErrorMsg = err
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    modifier = Modifier.testTag("save_profile_button")
                ) {
                    Text(text = strings.saveProfileChanges, color = Color(0xFF101014), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditProfileDialog = false }) {
                    Text(text = strings.cancelAction)
                }
            }
        )
    }

    // --- DIALOG 2: Login / Register Dialog ---
    if (showAuthDialog) {
        var authTab by remember { mutableStateOf(0) } // 0: Sign In, 1: Register
        var authName by remember { mutableStateOf("") }
        var authEmail by remember { mutableStateOf("") }
        var authPassword by remember { mutableStateOf("") }
        var authPhone by remember { mutableStateOf("") }
        var authBio by remember { mutableStateOf("") }
        var authErrorMsg by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAuthDialog = false },
            title = {
                TabRow(
                    selectedTabIndex = authTab,
                    containerColor = Color.Transparent,
                    contentColor = GoldPrimary
                ) {
                    Tab(
                        selected = authTab == 0,
                        onClick = {
                            authTab = 0
                            authErrorMsg = ""
                        },
                        text = { Text(strings.loginButton, fontWeight = FontWeight.Bold) }
                    )
                    Tab(
                        selected = authTab == 1,
                        onClick = {
                            authTab = 1
                            authErrorMsg = ""
                        },
                        text = { Text(strings.registerButton, fontWeight = FontWeight.Bold) }
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    if (authErrorMsg.isNotBlank()) {
                        Text(
                            text = authErrorMsg,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }

                    if (authTab == 1) {
                        OutlinedTextField(
                            value = authName,
                            onValueChange = { authName = it },
                            label = { Text(strings.fullNameLabel) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }

                    OutlinedTextField(
                        value = authEmail,
                        onValueChange = { authEmail = it },
                        label = { Text(strings.emailAddressLabel) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = authPassword,
                        onValueChange = { authPassword = it },
                        label = { Text(strings.passwordLabel) },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    if (authTab == 1) {
                        OutlinedTextField(
                            value = authPhone,
                            onValueChange = { authPhone = it },
                            label = { Text(strings.phoneNumberLabel) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = authBio,
                            onValueChange = { authBio = it },
                            label = { Text(strings.bioLabel) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }

                    // Continue as Guest Option
                    TextButton(
                        onClick = {
                            onContinueAsGuest()
                            showAuthDialog = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = strings.continueAsGuest, color = GoldPrimary)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (authTab == 0) {
                            onLogin(authEmail, authPassword) { success, err ->
                                if (success) {
                                    showAuthDialog = false
                                } else {
                                    authErrorMsg = err
                                }
                            }
                        } else {
                            onRegister(authName, authEmail, authPassword, authPhone, authBio) { success, err ->
                                if (success) {
                                    showAuthDialog = false
                                } else {
                                    authErrorMsg = err
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    modifier = Modifier.testTag("auth_submit_button")
                ) {
                    Text(
                        text = if (authTab == 0) strings.loginButton else strings.registerButton,
                        color = Color(0xFF101014),
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showAuthDialog = false }) {
                    Text(text = strings.cancelAction)
                }
            }
        )
    }

    // --- DIALOG 3: Logout Confirmation Dialog ---
    if (showLogoutConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutConfirmDialog = false },
            title = { Text(text = strings.logoutConfirmationTitle, color = GoldPrimary, fontWeight = FontWeight.Bold) },
            text = { Text(text = strings.logoutConfirmationMessage) },
            confirmButton = {
                Button(
                    onClick = {
                        onLogout()
                        showLogoutConfirmDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(text = strings.logoutButton, color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutConfirmDialog = false }) {
                    Text(text = strings.cancelAction)
                }
            }
        )
    }

    // --- DIALOG 4: Rename Project Dialog ---
    renamingProject?.let { project ->
        AlertDialog(
            onDismissRequest = { renamingProject = null },
            title = { Text(text = strings.renameProject, color = GoldPrimary, fontWeight = FontWeight.Bold) },
            text = {
                OutlinedTextField(
                    value = renameInputText,
                    onValueChange = { renameInputText = it },
                    label = { Text(strings.newProjectTitlePrompt) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (renameInputText.isNotBlank()) {
                            onRenameProject(project.id, renameInputText)
                        }
                        renamingProject = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                ) {
                    Text(text = strings.saveProfileChanges, color = Color(0xFF101014), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { renamingProject = null }) {
                    Text(text = strings.cancelAction)
                }
            }
        )
    }

    // --- DIALOG 5: Delete Project Confirmation Dialog ---
    deletingProject?.let { project ->
        AlertDialog(
            onDismissRequest = { deletingProject = null },
            title = { Text(text = strings.confirmDeleteProjectTitle, color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold) },
            text = { Text(text = "${strings.confirmDeleteProjectMessage}\n\n«${project.titleAr}»") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteProject(project.id)
                        deletingProject = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(text = strings.deleteProject, color = Color.White, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { deletingProject = null }) {
                    Text(text = strings.cancelAction)
                }
            }
        )
    }

    // --- DIALOG 6: Owner Admin Verification Dialog ---
    if (showOwnerAuthDialog) {
        OwnerAuthDialog(
            onDismiss = { showOwnerAuthDialog = false },
            onVerify = { pin ->
                val passed = onVerifyOwnerPin(pin)
                if (passed) {
                    showOwnerAuthDialog = false
                    onOpenAdmin()
                }
                passed
            }
        )
    }
}
