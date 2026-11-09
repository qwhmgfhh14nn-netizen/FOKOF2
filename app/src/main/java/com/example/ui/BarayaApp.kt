package com.example.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AiDirectorChatDialog
import com.example.ui.components.BarayaHeader
import com.example.ui.components.BundleBuyDialog
import com.example.ui.components.TemplateBuyDialog
import com.example.ui.components.TemplatePreviewDialog
import com.example.ui.locale.AppLanguage
import com.example.ui.screens.AccountScreen
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.AiGenScreen
import com.example.ui.screens.BrandKitScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MarketingScreen
import com.example.ui.screens.MockupStudioScreen
import com.example.ui.screens.ProjectsScreen
import com.example.ui.screens.PurchasesScreen
import com.example.ui.screens.StoreQrStudioScreen
import com.example.ui.screens.StudioScreen
import com.example.ui.screens.TemplatesScreen
import com.example.ui.screens.WebsiteEditorScreen
import com.example.ui.screens.WebsitesScreen
import com.example.ui.theme.BarayaTheme
import com.example.ui.theme.GoldPrimary

@Composable
fun BarayaApp(
    viewModel: BarayaViewModel = viewModel()
) {
    val currentNav by viewModel.currentNav.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val isChatOpen by viewModel.isChatOpen.collectAsState()
    val isVipUser by viewModel.isVipUser.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    // Studio states
    val canvasElements by viewModel.canvasElements.collectAsState()
    val selectedElementId by viewModel.selectedElementId.collectAsState()
    val bgStartHex by viewModel.canvasBgStart.collectAsState()
    val bgEndHex by viewModel.canvasBgEnd.collectAsState()
    val canvasBgImageRes by viewModel.canvasBgImageRes.collectAsState()
    val canvasBgImageUri by viewModel.canvasBgImageUri.collectAsState()
    val canvasAspectRatio by viewModel.canvasAspectRatio.collectAsState()
    val savedProjects by viewModel.savedProjects.collectAsState()
    val userProjects by viewModel.userProjects.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val favoriteIds by viewModel.favoriteTemplateIds.collectAsState()

    // Purchases & Dialogs
    val userPurchases by viewModel.userPurchases.collectAsState()
    val previewTemplate by viewModel.previewTemplate.collectAsState()
    val buyingTemplate by viewModel.buyingTemplate.collectAsState()
    val buyingBundle by viewModel.buyingBundle.collectAsState()
    val publishedTemplates by viewModel.publishedTemplates.collectAsState()
    val allTemplatesForAdmin by viewModel.allTemplatesForAdmin.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val bundles by viewModel.bundles.collectAsState()
    val subscriptionPlans by viewModel.subscriptionPlans.collectAsState()
    val tierPrices by viewModel.tierPrices.collectAsState()
    val availableCoupons by viewModel.availableCoupons.collectAsState()
    val purchasedTemplateIds = remember(userPurchases) { userPurchases.map { it.templateId }.toSet() }

    // AI Generation states
    val imagePrompt by viewModel.imagePrompt.collectAsState()
    val selectedStyle by viewModel.selectedStyle.collectAsState()
    val selectedAspectRatio by viewModel.selectedAspectRatio.collectAsState()
    val isGeneratingImage by viewModel.isGeneratingImage.collectAsState()
    val generatedImages by viewModel.generatedImages.collectAsState()

    // Marketing states
    val marketingTopic by viewModel.marketingTopic.collectAsState()
    val marketingAudience by viewModel.marketingAudience.collectAsState()
    val marketingTone by viewModel.marketingTone.collectAsState()
    val marketingToolType by viewModel.marketingToolType.collectAsState()
    val marketingResult by viewModel.marketingResult.collectAsState()
    val isGeneratingCopy by viewModel.isGeneratingCopy.collectAsState()

    // Brand Kit states
    val brandKitName by viewModel.brandKitName.collectAsState()
    val brandKitIndustry by viewModel.brandKitIndustry.collectAsState()
    val brandKitResult by viewModel.brandKitResult.collectAsState()
    val isGeneratingBrandKit by viewModel.isGeneratingBrandKit.collectAsState()

    // Chat states
    val chatMessages by viewModel.chatMessages.collectAsState()
    val isSendingChat by viewModel.isSendingChat.collectAsState()

    // Admin states
    val manualPayments by viewModel.manualPayments.collectAsState()
    val isOwnerAuthenticated by viewModel.isOwnerAuthenticated.collectAsState()

    // Website Builder & Mockup states
    val allWebsiteTemplates by viewModel.allWebsiteTemplates.collectAsState()
    val publishedWebsiteTemplates by viewModel.publishedWebsiteTemplates.collectAsState()
    val userWebsites by viewModel.userWebsites.collectAsState()
    val websiteSettings by viewModel.websiteSettings.collectAsState()
    val activeWebsiteEditing by viewModel.activeWebsiteEditing.collectAsState()
    val selectedMockupType by viewModel.selectedMockupType.collectAsState()
    val qrSelectedWebsite by viewModel.qrSelectedWebsite.collectAsState()

    val strings = viewModel.strings
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearToast()
        }
    }

    val layoutDirection = if (currentLanguage == AppLanguage.ARABIC) {
        LayoutDirection.Rtl
    } else {
        LayoutDirection.Ltr
    }

    BarayaTheme(darkTheme = isDarkTheme) {
        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val isExpandedScreen = maxWidth >= 760.dp

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        BarayaHeader(
                            strings = strings,
                            isDarkTheme = isDarkTheme,
                            currentLanguage = currentLanguage,
                            onToggleTheme = { viewModel.toggleTheme() },
                            onToggleLanguage = { viewModel.toggleLanguage() },
                            onOpenChat = { viewModel.setChatOpen(true) }
                        )
                    },
                    bottomBar = {
                        if (!isExpandedScreen) {
                            BarayaFixedBottomNav(
                                strings = strings,
                                currentNav = currentNav,
                                onSelect = { viewModel.setDestination(it) },
                                modifier = Modifier.navigationBarsPadding()
                            )
                        }
                    }
                ) { innerPadding ->
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        if (isExpandedScreen) {
                            BarayaFixedNavRail(
                                strings = strings,
                                currentNav = currentNav,
                                onSelect = { viewModel.setDestination(it) }
                            )
                        }

                        Box(modifier = Modifier.weight(1f)) {
                            when (currentNav) {
                                // 1. Home – الرئيسية
                                AppNavDestination.HOME -> {
                                    HomeScreen(
                                        strings = strings,
                                        searchQuery = searchQuery,
                                        templates = publishedTemplates,
                                        favoriteIds = favoriteIds,
                                        onSearchChange = { viewModel.setSearchQuery(it) },
                                        onNavigate = { viewModel.setDestination(it) },
                                        onPreviewTemplate = { viewModel.setPreviewTemplate(it) },
                                        onUseTemplate = {
                                            viewModel.loadTemplate(it)
                                            viewModel.setDestination(AppNavDestination.STUDIO)
                                        },
                                        onBuyTemplate = { viewModel.setBuyingTemplate(it) },
                                        onToggleFavorite = { viewModel.toggleFavorite(it) }
                                    )
                                }

                                // 2. Templates – القوالب
                                AppNavDestination.TEMPLATES -> {
                                    TemplatesScreen(
                                        strings = strings,
                                        templates = publishedTemplates,
                                        categories = categories,
                                        bundles = bundles,
                                        favoriteIds = favoriteIds,
                                        purchasedTemplateIds = purchasedTemplateIds,
                                        onPreviewTemplate = { viewModel.setPreviewTemplate(it) },
                                        onUseTemplate = {
                                            viewModel.loadTemplate(it)
                                            viewModel.setDestination(AppNavDestination.STUDIO)
                                        },
                                        onBuyTemplate = { viewModel.setBuyingTemplate(it) },
                                        onBuyBundle = { viewModel.openBuyBundle(it) },
                                        onToggleFavorite = { viewModel.toggleFavorite(it) }
                                    )
                                }

                                // 3. Create Design / Studio – إنشاء تصميم
                                AppNavDestination.STUDIO -> {
                                    StudioScreen(
                                        strings = strings,
                                        canvasElements = canvasElements,
                                        selectedElementId = selectedElementId,
                                        bgStartHex = bgStartHex,
                                        bgEndHex = bgEndHex,
                                        canvasBgImageRes = canvasBgImageRes,
                                        canvasBgImageUri = canvasBgImageUri,
                                        templates = publishedTemplates,
                                        canvasAspectRatio = canvasAspectRatio,
                                        onSelectElement = { viewModel.selectElement(it) },
                                        onAddText = { viewModel.addTextElement(it) },
                                        onAddShape = { viewModel.addShapeElement(it) },
                                        onAddCalligraphy = { viewModel.addCalligraphyPreset(it) },
                                        onAddGraphicSticker = { viewModel.addGraphicSticker(it) },
                                        onSetCanvasBgPhoto = { resId, uri -> viewModel.setCanvasBgPhoto(resId, uri) },
                                        onUpdateElement = { viewModel.updateSelectedElement(it) },
                                        onDeleteElement = { viewModel.deleteSelectedElement() },
                                        onDuplicateElement = { viewModel.duplicateSelectedElement() },
                                        onClearCanvas = { viewModel.clearCanvas() },
                                        onSetCanvasAspectRatio = { viewModel.setCanvasAspectRatio(it) },
                                        onSetCanvasBg = { start, end -> viewModel.setCanvasBg(start, end) },
                                        onLoadTemplate = { viewModel.loadTemplate(it) },
                                        onSaveProject = { viewModel.saveCurrentProject() },
                                        onShowToast = { viewModel.showToast(it) }
                                    )
                                }

                                // 4. My Purchases – مشترياتي
                                AppNavDestination.PURCHASES -> {
                                    PurchasesScreen(
                                        strings = strings,
                                        purchases = userPurchases,
                                        onOpenInStudio = {
                                            viewModel.loadTemplate(it)
                                            viewModel.setDestination(AppNavDestination.STUDIO)
                                        },
                                        onDownloadFiles = {
                                            viewModel.showToast(if (currentLanguage == AppLanguage.ARABIC) "تم تنزيل ملفات التصميم المصدرية (AI, PSD, PNG)!" else "Downloaded source files (AI, PSD, PNG)!")
                                        },
                                        onExploreTemplates = {
                                            viewModel.setDestination(AppNavDestination.TEMPLATES)
                                        }
                                    )
                                }

                                // 5. My Account – حسابي
                                AppNavDestination.ACCOUNT -> {
                                    AccountScreen(
                                        strings = strings,
                                        isDarkTheme = isDarkTheme,
                                        currentLanguage = currentLanguage,
                                        currentUser = currentUser,
                                        savedProjects = userProjects,
                                        templates = publishedTemplates,
                                        favoriteIds = favoriteIds,
                                        isOwnerAuthenticated = isOwnerAuthenticated,
                                        onToggleTheme = { viewModel.toggleTheme() },
                                        onToggleLanguage = { viewModel.toggleLanguage() },
                                        onOpenProject = { viewModel.openProjectInStudio(it) },
                                        onRenameProject = { id, title -> viewModel.renameProject(id, title) },
                                        onDuplicateProject = { viewModel.duplicateProject(it) },
                                        onDeleteProject = { viewModel.deleteSavedProject(it) },
                                        onStartNewDesign = { viewModel.createNewProject() },
                                        onOpenAdmin = { viewModel.setDestination(AppNavDestination.ADMIN) },
                                        onVerifyOwnerPin = { viewModel.verifyOwnerPin(it) },
                                        onUpgradeVip = {
                                            viewModel.showToast(if (currentLanguage == AppLanguage.ARABIC) "يمكنك طلب ترقية باقة VIP عبر التحويل على فودافون كاش أو إنستاباي!" else "Upgrade to VIP via Vodafone Cash or InstaPay!")
                                        },
                                        onLogin = { email, pwd, onDone -> viewModel.login(email, pwd, onDone) },
                                        onRegister = { name, email, pwd, phone, bio, onDone -> viewModel.register(name, email, pwd, phone, bio, onDone) },
                                        onLogout = { viewModel.logout() },
                                        onContinueAsGuest = { viewModel.continueAsGuest() },
                                        onUpdateProfile = { name, email, phone, bio, avatar, onDone -> viewModel.updateProfile(name, email, phone, bio, avatar, onDone) }
                                    )
                                }

                                // 6. My Projects – مشاريعي
                                AppNavDestination.PROJECTS -> {
                                    ProjectsScreen(
                                        strings = strings,
                                        currentUser = currentUser,
                                        savedProjects = userProjects,
                                        isVipUser = currentUser.isVip,
                                        onOpenInStudio = { viewModel.openProjectInStudio(it) },
                                        onRenameProject = { id, title -> viewModel.renameProject(id, title) },
                                        onDuplicateProject = { viewModel.duplicateProject(it) },
                                        onDeleteProject = { viewModel.deleteSavedProject(it) },
                                        onStartNewDesign = { viewModel.createNewProject() },
                                        onSubmitManualPayment = { email, plan, amount, method, ref ->
                                            viewModel.submitManualPayment(email, plan, amount, method, ref)
                                        }
                                    )
                                }

                                // Admin Dashboard Portal
                                AppNavDestination.ADMIN -> {
                                    AdminScreen(
                                        strings = strings,
                                        payments = manualPayments,
                                        templates = allTemplatesForAdmin,
                                        categories = categories,
                                        bundles = bundles,
                                        subscriptionPlans = subscriptionPlans,
                                        coupons = availableCoupons,
                                        tierPrices = tierPrices,
                                        onApprovePayment = { viewModel.approvePayment(it) },
                                        onRejectPayment = { viewModel.rejectPayment(it) },
                                        onAddTemplate = { titleAr, titleEn, descriptionAr, descriptionEn, category, aspectRatio, isPremium, pricingType, proTier, priceEgp, bgStartHex, bgEndHex, isPublished, imageUri, designFileUri, designFileName ->
                                            viewModel.addTemplate(
                                                titleAr = titleAr,
                                                titleEn = titleEn,
                                                descriptionAr = descriptionAr,
                                                descriptionEn = descriptionEn,
                                                category = category,
                                                aspectRatio = aspectRatio,
                                                isPremium = isPremium,
                                                pricingType = pricingType,
                                                proTier = proTier,
                                                priceEgp = priceEgp,
                                                elements = emptyList(),
                                                bgStart = bgStartHex,
                                                bgEnd = bgEndHex,
                                                isPublished = isPublished,
                                                imageUri = imageUri,
                                                designFileUri = designFileUri,
                                                designFileName = designFileName
                                            )
                                        },
                                        onUpdateTemplate = { viewModel.updateTemplate(it) },
                                        onDuplicateTemplate = { viewModel.duplicateTemplate(it) },
                                        onDeleteTemplate = { viewModel.deleteTemplate(it) },
                                        onUpdatePrice = { id, price -> viewModel.updateTemplatePrice(id, price) },
                                        onTogglePublish = { id, isPub -> viewModel.toggleTemplatePublishStatus(id, isPub) },
                                        onAddCategory = { nameAr, nameEn, icon -> viewModel.addCategory(nameAr, nameEn, icon) },
                                        onUpdateCategory = { id, nameAr, nameEn -> viewModel.updateCategory(id, nameAr, nameEn) },
                                        onDeleteCategory = { id -> viewModel.deleteCategory(id) },
                                        onUpdateBundlePrice = { id, price -> viewModel.updateBundlePrice(id, price) },
                                        onUpdateSubscriptionPrice = { id, price -> viewModel.updateSubscriptionPrice(id, price) },
                                        onUpdateTierPrice = { tier, price -> viewModel.updateTierPrice(tier, price) },
                                        onAddCoupon = { code, disc, desc -> viewModel.addCoupon(code, disc, desc) },
                                        onRemoveCoupon = { viewModel.removeCoupon(it) },
                                        onPreviewTemplate = { viewModel.setPreviewTemplate(it) },
                                        onCloseAdmin = { viewModel.setDestination(AppNavDestination.ACCOUNT) },
                                        onLockAdmin = { viewModel.lockAdmin() },
                                        websiteTemplates = allWebsiteTemplates,
                                        websiteSettings = websiteSettings,
                                        onUpdateWebsitePrice = { id, price -> viewModel.updateWebsiteTemplatePriceInDb(id, price) },
                                        onUpdateWebsiteFeatures = { id, toggles -> viewModel.updateWebsiteFeatureTogglesInDb(id, toggles) },
                                        onUpdateWebsiteSettings = { freeLimit, extraPrice, domainPrice -> viewModel.updateWebsiteSettingsInDb(freeLimit, extraPrice, domainPrice) }
                                    )
                                }

                                // Secondary: AI Image Studio
                                AppNavDestination.AI_IMAGE -> {
                                    AiGenScreen(
                                        strings = strings,
                                        prompt = imagePrompt,
                                        selectedStyle = selectedStyle,
                                        selectedAspectRatio = selectedAspectRatio,
                                        isGenerating = isGeneratingImage,
                                        generatedImages = generatedImages,
                                        onPromptChange = { viewModel.setImagePrompt(it) },
                                        onStyleChange = { viewModel.setSelectedStyle(it) },
                                        onAspectRatioChange = { viewModel.setSelectedAspectRatio(it) },
                                        onEnhancePrompt = { viewModel.enhanceImagePrompt() },
                                        onGenerate = { viewModel.generateImage() },
                                        onSendToStudio = { viewModel.sendGeneratedImageToStudio(it) },
                                        onShowToast = { viewModel.showToast(it) }
                                    )
                                }

                                // Secondary: AI Copywriting
                                AppNavDestination.AI_MARKETING -> {
                                    MarketingScreen(
                                        strings = strings,
                                        topic = marketingTopic,
                                        audience = marketingAudience,
                                        selectedTone = marketingTone,
                                        selectedToolType = marketingToolType,
                                        result = marketingResult,
                                        isGenerating = isGeneratingCopy,
                                        onTopicChange = { viewModel.setMarketingTopic(it) },
                                        onAudienceChange = { viewModel.setMarketingAudience(it) },
                                        onToneChange = { viewModel.setMarketingTone(it) },
                                        onToolTypeChange = { viewModel.setMarketingToolType(it) },
                                        onGenerate = { viewModel.generateMarketingCopy() },
                                        onSendToStudio = { viewModel.sendMarketingTextToStudio() },
                                        onShowToast = { viewModel.showToast(it) }
                                    )
                                }

                                // Secondary: Brand Kit
                                AppNavDestination.BRAND_KIT -> {
                                    BrandKitScreen(
                                        strings = strings,
                                        brandName = brandKitName,
                                        industry = brandKitIndustry,
                                        brandKitResult = brandKitResult,
                                        isGenerating = isGeneratingBrandKit,
                                        onBrandNameChange = { viewModel.setBrandKitName(it) },
                                        onIndustryChange = { viewModel.setBrandKitIndustry(it) },
                                        onGenerate = { viewModel.generateBrandKit() },
                                        onApplyToStudio = { viewModel.applyBrandKitToStudio(it) },
                                        onShowToast = { viewModel.showToast(it) }
                                    )
                                }

                                // Websites Builder Portal
                                AppNavDestination.WEBSITES -> {
                                    WebsitesScreen(
                                        strings = strings,
                                        templates = publishedWebsiteTemplates,
                                        userWebsites = userWebsites,
                                        settings = websiteSettings,
                                        onCreateFromTemplate = { tpl -> viewModel.startNewWebsiteFromTemplate(tpl) },
                                        onEditWebsite = { site -> viewModel.editExistingWebsite(site) },
                                        onDeleteWebsite = { id -> viewModel.deleteWebsite(id) },
                                        onOpenQrStudio = { site -> viewModel.openQrStudioForWebsite(site) },
                                        onBack = { viewModel.setDestination(AppNavDestination.HOME) }
                                    )
                                }

                                // Interactive Website Editor
                                AppNavDestination.WEBSITE_EDITOR -> {
                                    activeWebsiteEditing?.let { site ->
                                        WebsiteEditorScreen(
                                            strings = strings,
                                            website = site,
                                            onSave = { updated -> viewModel.saveCurrentWebsite(updated) },
                                            onBack = { viewModel.setDestination(AppNavDestination.WEBSITES) },
                                            onOpenQrStudio = { s -> viewModel.openQrStudioForWebsite(s) },
                                            onSubmitBooking = { b -> viewModel.submitBooking(b) }
                                        )
                                    } ?: run {
                                        viewModel.setDestination(AppNavDestination.WEBSITES)
                                    }
                                }

                                // Storefront QR Code Studio
                                AppNavDestination.STORE_QR_STUDIO -> {
                                    qrSelectedWebsite?.let { site ->
                                        StoreQrStudioScreen(
                                            strings = strings,
                                            website = site,
                                            onBack = { viewModel.setDestination(AppNavDestination.WEBSITES) },
                                            onShowToast = { msg -> viewModel.showToast(msg) }
                                        )
                                    } ?: run {
                                        viewModel.setDestination(AppNavDestination.WEBSITES)
                                    }
                                }

                                // 3D Mockup Studio
                                AppNavDestination.MOCKUP_STUDIO -> {
                                    MockupStudioScreen(
                                        strings = strings,
                                        selectedType = selectedMockupType,
                                        onSelectType = { viewModel.setSelectedMockupType(it) },
                                        onBack = { viewModel.setDestination(AppNavDestination.HOME) },
                                        onExportMockup = { msg -> viewModel.showToast(msg) }
                                    )
                                }
                            }
                        }
                    }

                    // Template Preview Dialog
                    previewTemplate?.let { tpl ->
                        val isPurchased = purchasedTemplateIds.contains(tpl.id)
                        TemplatePreviewDialog(
                            template = tpl,
                            strings = strings,
                            isPurchased = isPurchased,
                            onDismiss = { viewModel.setPreviewTemplate(null) },
                            onUseInStudio = {
                                viewModel.loadTemplate(tpl)
                                viewModel.setPreviewTemplate(null)
                                viewModel.setDestination(AppNavDestination.STUDIO)
                            },
                            onBuy = {
                                viewModel.setPreviewTemplate(null)
                                viewModel.setBuyingTemplate(tpl)
                            }
                        )
                    }

                    // Template Purchase Dialog (Egyptian Pounds)
                    buyingTemplate?.let { tpl ->
                        TemplateBuyDialog(
                            template = tpl,
                            strings = strings,
                            onDismiss = { viewModel.setBuyingTemplate(null) },
                            onConfirmBuy = { method ->
                                viewModel.buyTemplate(tpl, method)
                            }
                        )
                    }

                    // Template Bundle Purchase Dialog (Egyptian Pounds)
                    buyingBundle?.let { bundle ->
                        BundleBuyDialog(
                            bundle = bundle,
                            strings = strings,
                            onDismiss = { viewModel.closeBuyBundle() },
                            onConfirmBuy = { method ->
                                viewModel.buyBundle(bundle, method)
                            }
                        )
                    }

                    // Creative Director Chat Dialog
                    if (isChatOpen) {
                        AiDirectorChatDialog(
                            strings = strings,
                            messages = chatMessages,
                            isSending = isSendingChat,
                            onSendMessage = { viewModel.sendChatMessage(it) },
                            onDismiss = { viewModel.setChatOpen(false) }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Exactly Five Fixed Bottom Navigation Buttons:
 * 1. Home – الرئيسية
 * 2. Templates – القوالب
 * 3. Create Design – إنشاء تصميم
 * 4. My Purchases – مشترياتي
 * 5. My Account – حسابي
 */
@Composable
fun BarayaFixedBottomNav(
    strings: com.example.ui.locale.TranslationStrings,
    currentNav: AppNavDestination,
    onSelect: (AppNavDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    val fiveTabs = listOf(
        AppNavDestination.HOME to (strings.navHome to Icons.Default.Home),
        AppNavDestination.TEMPLATES to (strings.navTemplates to Icons.Default.Category),
        AppNavDestination.STUDIO to (strings.navStudio to Icons.Default.Brush),
        AppNavDestination.PURCHASES to (strings.navPurchases to Icons.Default.ShoppingBag),
        AppNavDestination.ACCOUNT to (strings.navAccount to Icons.Default.Person)
    )

    NavigationBar(
        modifier = modifier.testTag("baraya_five_bottom_navigation"),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        fiveTabs.forEach { (destination, labelIcon) ->
            val isSelected = currentNav == destination
            NavigationBarItem(
                selected = isSelected,
                onClick = { onSelect(destination) },
                icon = {
                    Icon(
                        imageVector = labelIcon.second,
                        contentDescription = labelIcon.first,
                        tint = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                label = {
                    Text(
                        text = labelIcon.first,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = GoldPrimary.copy(alpha = 0.2f)
                )
            )
        }
    }
}

@Composable
fun BarayaFixedNavRail(
    strings: com.example.ui.locale.TranslationStrings,
    currentNav: AppNavDestination,
    onSelect: (AppNavDestination) -> Unit
) {
    val fiveTabs = listOf(
        AppNavDestination.HOME to (strings.navHome to Icons.Default.Home),
        AppNavDestination.TEMPLATES to (strings.navTemplates to Icons.Default.Category),
        AppNavDestination.STUDIO to (strings.navStudio to Icons.Default.Brush),
        AppNavDestination.PURCHASES to (strings.navPurchases to Icons.Default.ShoppingBag),
        AppNavDestination.ACCOUNT to (strings.navAccount to Icons.Default.Person)
    )

    NavigationRail(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxHeight()
    ) {
        fiveTabs.forEach { (destination, labelIcon) ->
            val isSelected = currentNav == destination
            NavigationRailItem(
                selected = isSelected,
                onClick = { onSelect(destination) },
                icon = {
                    Icon(
                        imageVector = labelIcon.second,
                        contentDescription = labelIcon.first,
                        tint = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                label = {
                    Text(
                        text = labelIcon.first,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) GoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1
                    )
                },
                colors = NavigationRailItemDefaults.colors(
                    indicatorColor = GoldPrimary.copy(alpha = 0.2f)
                )
            )
        }
    }
}
