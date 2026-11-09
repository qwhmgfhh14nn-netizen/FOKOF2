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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DriveFileRenameOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Payment
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DesignTemplate
import com.example.data.model.UserProfile
import com.example.ui.locale.TranslationStrings
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.util.parseColor

@Composable
fun ProjectsScreen(
    strings: TranslationStrings,
    currentUser: UserProfile,
    savedProjects: List<DesignTemplate>,
    isVipUser: Boolean,
    onOpenInStudio: (DesignTemplate) -> Unit,
    onRenameProject: (String, String) -> Unit,
    onDuplicateProject: (String) -> Unit,
    onDeleteProject: (String) -> Unit,
    onStartNewDesign: () -> Unit,
    onSubmitManualPayment: (email: String, plan: String, amount: String, method: String, ref: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showPaymentModal by remember { mutableStateOf(false) }
    var userEmailInput by remember { mutableStateOf("") }
    var selectedMethod by remember { mutableStateOf(strings.vodafoneCash) }
    var txnRefInput by remember { mutableStateOf("") }

    // Dialog state for renaming and deleting projects
    var renamingProject by remember { mutableStateOf<DesignTemplate?>(null) }
    var renameInputText by remember { mutableStateOf("") }
    var deletingProject by remember { mutableStateOf<DesignTemplate?>(null) }

    val methods = listOf(
        strings.vodafoneCash,
        strings.instaPay,
        strings.bankTransfer,
        strings.cryptoUsdt
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Account Tier Banner & User Identity
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
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
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(GoldPrimary.copy(alpha = 0.2f))
                                .border(1.dp, GoldPrimary, RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.WorkspacePremium,
                                contentDescription = "VIP Tier",
                                tint = GoldPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = if (currentUser.isGuest) strings.guestBadge else (if (currentUser.isVip || isVipUser) strings.vipMember else strings.freeTier),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = GoldPrimary
                                    )
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "• ${currentUser.name}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }
                            Text(
                                text = if (currentUser.isGuest) strings.guestNotice else "مشاريعك المحفوظة مشفرة ومحمية في قاعدة بياناتك المحلية",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontSize = 11.sp
                                ),
                                maxLines = 1
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = { showPaymentModal = true },
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary),
                        modifier = Modifier.testTag("upgrade_vip_button")
                    ) {
                        Icon(imageVector = Icons.Default.Payment, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "ترقية الباقة", color = GoldPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Projects Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = strings.projectsTitle,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary
                    )
                )
                Text(
                    text = "${savedProjects.size} مشاريع محفوظة للعميل",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            Button(
                onClick = onStartNewDesign,
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("start_new_design_button")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color(0xFF101014), modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = strings.startDesigning, color = Color(0xFF101014), fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Projects List
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
                    Button(
                        onClick = onStartNewDesign,
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = strings.startDesigning, color = Color(0xFF101014), fontWeight = FontWeight.Bold)
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
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
                                        .size(48.dp)
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
                                        fontSize = 15.sp
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = project.titleAr,
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = CairoFontFamily
                                        )
                                    )
                                    Text(
                                        text = "${project.elements.size} عناصر وتعديلات • ${project.category}",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }

                            // Actions: Open in Studio, Rename, Duplicate, Delete
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = { onOpenInStudio(project) },
                                    modifier = Modifier.testTag("open_project_${project.id}")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = strings.openProjectAction,
                                        tint = GoldPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        renamingProject = project
                                        renameInputText = project.titleAr
                                    },
                                    modifier = Modifier.testTag("rename_project_${project.id}")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.DriveFileRenameOutline,
                                        contentDescription = strings.renameProject,
                                        tint = GoldPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                IconButton(
                                    onClick = { onDuplicateProject(project.id) },
                                    modifier = Modifier.testTag("duplicate_project_${project.id}")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = strings.duplicateProject,
                                        tint = GoldPrimary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                IconButton(
                                    onClick = { deletingProject = project },
                                    modifier = Modifier.testTag("delete_project_${project.id}")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = strings.deleteProject,
                                        tint = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Rename Project Dialog
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

    // Delete Project Confirmation Dialog
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

    // Manual Payment Submission Modal
    if (showPaymentModal) {
        AlertDialog(
            onDismissRequest = { showPaymentModal = false },
            title = { Text(text = strings.submitPayment, color = GoldPrimary) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = strings.paymentInstructions,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    OutlinedTextField(
                        value = userEmailInput,
                        onValueChange = { userEmailInput = it },
                        label = { Text("البريد الإلكتروني للعميل") },
                        placeholder = { Text(currentUser.email) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = txnRefInput,
                        onValueChange = { txnRefInput = it },
                        label = { Text(strings.transactionRef) },
                        placeholder = { Text("مثال: 9840219854") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Text(text = "اختر طريقة التحويل:", style = MaterialTheme.typography.labelSmall)
                    methods.forEach { method ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedMethod = method }
                                .padding(vertical = 4.dp)
                        ) {
                            Text(
                                text = if (selectedMethod == method) "◉ $method" else "○ $method",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = if (selectedMethod == method) GoldPrimary else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = if (selectedMethod == method) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val email = userEmailInput.ifBlank { currentUser.email }
                        val ref = txnRefInput.ifBlank { "REF-${System.currentTimeMillis() % 100000}" }
                        onSubmitManualPayment(email, "Baraya VIP Pro", "500 EGP", selectedMethod, ref)
                        showPaymentModal = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                ) {
                    Text("إرسال الإشعار", color = Color(0xFF101014), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showPaymentModal = false }) {
                    Text(text = strings.cancelAction)
                }
            }
        )
    }
}
