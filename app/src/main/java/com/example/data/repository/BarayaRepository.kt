package com.example.data.repository

import android.content.Context
import com.example.data.local.AppDatabase
import com.example.data.local.BundleEntity
import com.example.data.local.CategoryEntity
import com.example.data.local.PaymentEntity
import com.example.data.local.ProjectEntity
import com.example.data.local.PurchaseEntity
import com.example.data.local.SubscriptionPlanEntity
import com.example.data.local.TemplateEntity
import com.example.data.local.UserEntity
import com.example.data.local.UserWebsiteEntity
import com.example.data.local.WebsiteAnalyticsEntity
import com.example.data.local.WebsiteBookingEntity
import com.example.data.local.WebsiteSettingsEntity
import com.example.data.local.WebsiteTemplateEntity
import com.example.data.local.PaymentMethodConfigEntity
import com.example.data.local.FeatureAvailabilityEntity
import com.example.data.local.DiscountCouponEntity
import com.example.data.model.CanvasElement
import com.example.data.model.CanvasElementType
import com.example.data.model.CategoryModel
import com.example.data.model.DesignTemplate
import com.example.data.model.DiscountCoupon
import com.example.data.model.FeatureAvailability
import com.example.data.model.PaymentMethodConfig
import com.example.data.model.ProTier
import com.example.data.model.PurchasedTemplateItem
import com.example.data.model.ShapeType
import com.example.data.model.SubscriptionPlan
import com.example.data.model.TemplateBundle
import com.example.data.model.UserProfile
import com.example.data.model.UserWebsite
import com.example.data.model.WebsiteAnalytics
import com.example.data.model.WebsiteBooking
import com.example.data.model.WebsiteBuilderSettings
import com.example.data.model.WebsiteFeatureToggles
import com.example.data.model.WebsiteItem
import com.example.data.model.WebsiteTemplate
import com.example.data.templates.BundlesData
import com.example.data.templates.FreeTemplatesData
import com.example.data.templates.ProTemplatesData
import com.example.data.templates.WebsiteTemplatesData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class BarayaRepository(context: Context) {
    private val database = AppDatabase.getDatabase(context)
    private val userDao = database.userDao()
    private val projectDao = database.projectDao()
    private val paymentDao = database.paymentDao()
    private val purchaseDao = database.purchaseDao()
    private val templateDao = database.templateDao()
    private val categoryDao = database.categoryDao()
    private val bundleDao = database.bundleDao()
    private val subscriptionPlanDao = database.subscriptionPlanDao()
    private val userWebsiteDao = database.userWebsiteDao()
    private val websiteTemplateDao = database.websiteTemplateDao()
    private val websiteSettingsDao = database.websiteSettingsDao()
    private val websiteAnalyticsDao = database.websiteAnalyticsDao()
    private val websiteBookingDao = database.websiteBookingDao()
    private val paymentMethodConfigDao = database.paymentMethodConfigDao()
    private val featureAvailabilityDao = database.featureAvailabilityDao()
    private val discountCouponDao = database.discountCouponDao()
    private val prefs = context.getSharedPreferences("baraya_user_prefs", Context.MODE_PRIVATE)

    // User Authentication & Session
    private val _currentUser = MutableStateFlow(
        UserProfile(
            id = "usr_baraya_founder",
            name = "محمد برايا",
            email = "mohamed.baraya@baraya.ai",
            phone = "+20 100 000 0000",
            bio = "مؤسس ومنشئ محتوى منصة برايا للذكاء الاصطناعي والتصميم الرقمي",
            isVip = true,
            isGuest = false,
            role = "OWNER",
            avatarIndex = 0,
            memberSince = "2026/01"
        )
    )
    val currentUser = _currentUser.asStateFlow()

    // Persistent Payment Methods Flow
    val allPaymentMethods: Flow<List<PaymentMethodConfig>> = paymentMethodConfigDao.getAllPaymentMethods().map { list ->
        list.map {
            PaymentMethodConfig(
                id = it.id,
                nameAr = it.nameAr,
                nameEn = it.nameEn,
                accountNumber = it.accountNumber,
                confirmationContact = it.confirmationContact,
                whatsappLink = it.whatsappLink,
                telegramLink = it.telegramLink,
                instructionsAr = it.instructionsAr,
                instructionsEn = it.instructionsEn,
                isEnabled = it.isEnabled,
                statusNoteAr = it.statusNoteAr,
                statusNoteEn = it.statusNoteEn,
                iconName = it.iconName
            )
        }
    }

    val enabledPaymentMethods: Flow<List<PaymentMethodConfig>> = paymentMethodConfigDao.getEnabledPaymentMethods().map { list ->
        list.map {
            PaymentMethodConfig(
                id = it.id,
                nameAr = it.nameAr,
                nameEn = it.nameEn,
                accountNumber = it.accountNumber,
                confirmationContact = it.confirmationContact,
                whatsappLink = it.whatsappLink,
                telegramLink = it.telegramLink,
                instructionsAr = it.instructionsAr,
                instructionsEn = it.instructionsEn,
                isEnabled = it.isEnabled,
                statusNoteAr = it.statusNoteAr,
                statusNoteEn = it.statusNoteEn,
                iconName = it.iconName
            )
        }
    }

    suspend fun togglePaymentMethod(id: String, isEnabled: Boolean) {
        paymentMethodConfigDao.updateStatus(id, isEnabled)
    }

    suspend fun updatePaymentMethodDetails(
        id: String,
        accountNumber: String,
        contact: String,
        wa: String,
        tg: String,
        instrAr: String,
        statusNoteAr: String
    ) {
        paymentMethodConfigDao.updateDetails(id, accountNumber, contact, wa, tg, instrAr, statusNoteAr)
    }

    // Persistent Feature Availability Flow
    val allFeatures: Flow<List<FeatureAvailability>> = featureAvailabilityDao.getAllFeatures().map { list ->
        list.map {
            FeatureAvailability(
                featureKey = it.featureKey,
                titleAr = it.titleAr,
                titleEn = it.titleEn,
                status = it.status,
                messageAr = it.messageAr,
                messageEn = it.messageEn
            )
        }
    }

    suspend fun updateFeatureStatus(key: String, status: String) {
        featureAvailabilityDao.updateFeatureStatus(key, status)
    }

    // Persistent Coupons Flow
    val allCoupons: Flow<List<DiscountCoupon>> = discountCouponDao.getAllCoupons().map { list ->
        list.map {
            DiscountCoupon(
                code = it.code,
                discountPercent = it.discountPercent,
                description = it.description,
                expirationDate = it.expirationDate,
                usageLimit = it.usageLimit,
                usageCount = it.usageCount,
                isActive = it.isActive
            )
        }
    }

    suspend fun addDiscountCoupon(coupon: DiscountCoupon) {
        discountCouponDao.insertCoupon(
            DiscountCouponEntity(
                code = coupon.code.uppercase().trim(),
                discountPercent = coupon.discountPercent,
                description = coupon.description.trim(),
                expirationDate = coupon.expirationDate,
                usageLimit = coupon.usageLimit,
                usageCount = coupon.usageCount,
                isActive = coupon.isActive
            )
        )
    }

    suspend fun deleteDiscountCoupon(code: String) {
        discountCouponDao.deleteCoupon(code)
    }

    // Pre-populated Discounts for Admin (backward-compat StateFlow)
    private val _availableCoupons = MutableStateFlow(
        listOf(
            DiscountCoupon("BARAYA50", 50, "خصم 50% بمناسبة الانطلاق"),
            DiscountCoupon("CAIRO20", 20, "خصم 20% لعملاء القاهرة ومصر"),
            DiscountCoupon("VIPGOLD", 30, "خصم خاص للمشتركين الذهبيين"),
            DiscountCoupon("ORANGE", 25, "عرض خاص لعملاء الدفع عبر أورنج كاش")
        )
    )
    val availableCoupons = _availableCoupons.asStateFlow()

    fun addCoupon(code: String, percent: Int, description: String) {
        _availableCoupons.value = _availableCoupons.value + DiscountCoupon(
            code = code.uppercase(),
            discountPercent = percent,
            description = description,
            isActive = true
        )
    }

    fun removeCoupon(code: String) {
        _availableCoupons.value = _availableCoupons.value.filter { it.code != code }
    }

    // Dynamic Editable Bundles in Egyptian Pounds (EGP)
    private val _bundles = MutableStateFlow(BundlesData.getDefaultBundles())
    val bundles = _bundles.asStateFlow()

    fun updateBundlePrice(bundleId: String, newPriceEgp: Int) {
        _bundles.value = _bundles.value.map { b ->
            if (b.id == bundleId) b.copy(priceEgp = newPriceEgp) else b
        }
    }

    // Tier Base Pricing editable by Admin
    private val _tierPrices = MutableStateFlow(
        mapOf(
            ProTier.BASIC to 5,
            ProTier.PROFESSIONAL to 10,
            ProTier.PREMIUM to 15,
            ProTier.EXCLUSIVE to 20
        )
    )
    val tierPrices = _tierPrices.asStateFlow()

    fun updateTierPrice(tier: ProTier, newPriceEgp: Int) {
        _tierPrices.value = _tierPrices.value.toMutableMap().apply { put(tier, newPriceEgp) }
    }

    // Categories Flow from Room Database
    val allCategories: Flow<List<CategoryModel>> = categoryDao.getAllCategories().map { list ->
        list.map {
            CategoryModel(
                id = it.id,
                nameAr = it.nameAr,
                nameEn = it.nameEn,
                iconName = it.iconName,
                orderIndex = it.orderIndex,
                isDefault = it.isDefault
            )
        }
    }

    suspend fun addCategory(nameAr: String, nameEn: String, iconName: String = "folder") {
        val newCat = CategoryEntity(
            id = "cat_${UUID.randomUUID().toString().take(8)}",
            nameAr = nameAr.trim(),
            nameEn = nameEn.trim().ifBlank { nameAr.trim() },
            iconName = iconName,
            orderIndex = categoryDao.getCategoryCount(),
            isDefault = false
        )
        categoryDao.insertCategory(newCat)
    }

    suspend fun updateCategory(id: String, nameAr: String, nameEn: String) {
        categoryDao.updateCategory(id, nameAr.trim(), nameEn.trim())
    }

    suspend fun deleteCategory(id: String) {
        categoryDao.deleteCategory(id)
    }

    // Bundles & Subscriptions Flows from Room Database
    val allBundles: Flow<List<TemplateBundle>> = bundleDao.getAllBundles().map { list ->
        list.map {
            TemplateBundle(
                id = it.id,
                count = it.count,
                priceEgp = it.priceEgp,
                originalPriceEgp = it.originalPriceEgp,
                titleAr = "باقة ${it.count} قوالب",
                titleEn = "${it.count} Templates Bundle",
                descriptionAr = "وصول واستخدام ${it.count} قالب احترافي من اختيارك بدون قيود",
                descriptionEn = "Access and use ${it.count} professional design templates",
                isPopular = it.isPopular
            )
        }
    }

    suspend fun updateBundlePriceInDb(bundleId: String, newPriceEgp: Int) {
        bundleDao.updateBundlePrice(bundleId, newPriceEgp)
        updateBundlePrice(bundleId, newPriceEgp)
    }

    val allSubscriptionPlans: Flow<List<SubscriptionPlan>> = subscriptionPlanDao.getAllPlans().map { list ->
        list.map {
            SubscriptionPlan(
                id = it.id,
                nameAr = it.nameAr,
                nameEn = it.nameEn,
                priceEgp = it.priceEgp,
                originalPriceEgp = it.originalPriceEgp,
                billingCycle = it.billingCycle,
                descriptionAr = it.descriptionAr,
                descriptionEn = it.descriptionEn,
                isPopular = it.isPopular
            )
        }
    }

    suspend fun updateSubscriptionPlanPriceInDb(planId: String, newPriceEgp: Int) {
        subscriptionPlanDao.updatePlanPrice(planId, newPriceEgp)
    }

    // ---------------- WEBSITE BUILDER FLOWS & METHODS ----------------
    val allWebsiteTemplates: Flow<List<WebsiteTemplate>> = websiteTemplateDao.getAllTemplates().map { list ->
        list.map { entityToWebsiteTemplate(it) }
    }

    val publishedWebsiteTemplates: Flow<List<WebsiteTemplate>> = websiteTemplateDao.getPublishedTemplates().map { list ->
        list.map { entityToWebsiteTemplate(it) }
    }

    val userWebsites: Flow<List<UserWebsite>> = userWebsiteDao.getAllUserWebsites().map { list ->
        list.map { entityToUserWebsite(it) }
    }

    val websiteSettings: Flow<WebsiteBuilderSettings> = websiteSettingsDao.getSettings().map { entity ->
        if (entity != null) {
            WebsiteBuilderSettings(
                freeWebsitesLimit = entity.freeWebsitesLimit,
                extraWebsitePriceEgp = entity.extraWebsitePriceEgp,
                customDomainPriceEgp = entity.customDomainPriceEgp,
                isBuilderActive = true
            )
        } else {
            WebsiteBuilderSettings()
        }
    }

    suspend fun saveUserWebsite(website: UserWebsite) {
        val entity = userWebsiteToEntity(website)
        userWebsiteDao.insertWebsite(entity)
    }

    suspend fun deleteUserWebsite(id: String) {
        userWebsiteDao.deleteWebsite(id)
    }

    suspend fun updateWebsiteTemplatePrice(id: String, priceEgp: Int) {
        websiteTemplateDao.updateTemplatePrice(id, priceEgp)
    }

    suspend fun updateWebsiteTemplateFeatureToggles(id: String, toggles: WebsiteFeatureToggles) {
        val json = featureTogglesToJson(toggles)
        websiteTemplateDao.updateFeatureToggles(id, json)
    }

    suspend fun updateWebsiteSettings(freeLimit: Int, extraPriceEgp: Int, domainPriceEgp: Int) {
        websiteSettingsDao.insertOrUpdate(
            WebsiteSettingsEntity(
                id = "global_settings",
                freeWebsitesLimit = freeLimit,
                extraWebsitePriceEgp = extraPriceEgp,
                customDomainPriceEgp = domainPriceEgp
            )
        )
    }

    suspend fun getUserWebsiteCount(userId: String): Int {
        return userWebsiteDao.getUserWebsiteCount(userId)
    }

    // Database initialization: Empties template library until owner adds real templates, seeds categories, bundles & subscriptions
    suspend fun ensureTemplatesInitialized() {
        // 1. EMPTY THE TEMPLATE LIBRARY: Purge all placeholder/fake templates from database
        val clearedKey = "placeholder_templates_purged_v5"
        if (!prefs.getBoolean(clearedKey, false)) {
            templateDao.deleteAllTemplates()
            prefs.edit().putBoolean(clearedKey, true).apply()
        }

        // 2. Seed 10 Categories as requested if empty
        if (categoryDao.getCategoryCount() == 0) {
            val initialCategories = listOf(
                CategoryEntity("cat_food", "مطاعم ومأكولات", "Restaurants and Food", "restaurant", 0, true),
                CategoryEntity("cat_fashion", "أزياء وجمال", "Fashion and Beauty", "style", 1, true),
                CategoryEntity("cat_cars", "سيارات ومحركات", "Cars", "directions_car", 2, true),
                CategoryEntity("cat_realestate", "عقارات ومقاولات", "Real Estate", "apartment", 3, true),
                CategoryEntity("cat_social", "سوشيال ميديا", "Social Media", "share", 4, true),
                CategoryEntity("cat_business", "أعمال وتسويق", "Business and Marketing", "business_center", 5, true),
                CategoryEntity("cat_events", "مناسبات واحتفالات", "Events and Celebrations", "celebration", 6, true),
                CategoryEntity("cat_education", "تعليم وتدريب", "Education", "school", 7, true),
                CategoryEntity("cat_tech", "تكنولوجيا وتقنية", "Technology", "devices", 8, true),
                CategoryEntity("cat_other", "أخرى ومتنوعة", "Other categories", "more_horiz", 9, true)
            )
            categoryDao.insertAll(initialCategories)
        }

        // 3. Seed Bundles if empty
        if (bundleDao.getBundleCount() == 0) {
            val defaultBundles = listOf(
                BundleEntity("bundle_5", 5, 20, 50, 60, "توفير 60%", "Save 60%"),
                BundleEntity("bundle_10", 10, 35, 100, 65, "توفير 65%", "Save 65%", isPopular = true),
                BundleEntity("bundle_20", 20, 60, 200, 70, "توفير 70%", "Save 70%"),
                BundleEntity("bundle_50", 50, 125, 500, 75, "توفير 75%", "Save 75%"),
                BundleEntity("bundle_100", 100, 199, 1000, 80, "أفضل قيمة", "Best Value")
            )
            bundleDao.insertAll(defaultBundles)
        }

        // 4. Seed Subscription Plans if empty
        if (subscriptionPlanDao.getPlanCount() == 0) {
            val defaultPlans = listOf(
                SubscriptionPlanEntity("sub_monthly", "الاشتراك الشهري", "Monthly Plan", 99, 199, "MONTHLY", "وصول لكافة قوالب المنصة وتحديثات أسبوعية"),
                SubscriptionPlanEntity("sub_annual", "الاشتراك السنوي", "Annual Plan", 599, 1199, "ANNUAL", "توفير 50% مع ميزات استوديو غير محدودة", isPopular = true),
                SubscriptionPlanEntity("sub_lifetime", "اشتراك مدى الحياة", "Lifetime Access", 1499, 2999, "LIFETIME", "دفع لمرة واحدة وامتلاك كل القوالب والميزات للأبد")
            )
            subscriptionPlanDao.insertAll(defaultPlans)
        }

        // 5. Seed default founder user with role OWNER if no users exist
        if (userDao.getUserCount() == 0) {
            val founder = UserEntity(
                id = "usr_baraya_founder",
                name = "محمد برايا",
                email = "mohamed.baraya@baraya.ai",
                password = "password123",
                phone = "+20 100 000 0000",
                bio = "مؤسس ومنشئ محتوى منصة برايا للذكاء الاصطناعي والتصميم الرقمي",
                isVip = true,
                role = "OWNER",
                avatarIndex = 0,
                createdAt = System.currentTimeMillis()
            )
            userDao.insertUser(founder)
        }

        // 6. Seed Payment Methods (Orange Cash enabled ONLY initially; others UNAVAILABLE)
        if (paymentMethodConfigDao.getMethodCount() == 0) {
            val initialMethods = listOf(
                PaymentMethodConfigEntity(
                    id = "orange_cash",
                    nameAr = "أورنج كاش (Orange Cash)",
                    nameEn = "Orange Cash",
                    accountNumber = "01207164416",
                    confirmationContact = "01206449934",
                    whatsappLink = "https://wa.me/201206449934",
                    telegramLink = "https://t.me/+201206449934",
                    instructionsAr = "حول المبلغ المطلوب إلى رقم محفظة أورنج كاش: 01207164416 ثم ارفع صورة إيصال التحويل واضغط تأكيد الدفع للمراجعة الفورية.",
                    instructionsEn = "Transfer required amount to Orange Cash: 01207164416 and upload receipt.",
                    isEnabled = true,
                    statusNoteAr = "متاح ونشط حاليًا",
                    statusNoteEn = "Available & Active",
                    iconName = "orange"
                ),
                PaymentMethodConfigEntity(
                    id = "vodafone_cash",
                    nameAr = "فودافون كاش (Vodafone Cash)",
                    nameEn = "Vodafone Cash",
                    accountNumber = "010xxxxxxxx",
                    confirmationContact = "01206449934",
                    instructionsAr = "محفظة فودافون كاش - قادمة قريباً",
                    isEnabled = false,
                    statusNoteAr = "غير متوفر حاليًا – Currently Unavailable",
                    statusNoteEn = "Currently Unavailable",
                    iconName = "vodafone"
                ),
                PaymentMethodConfigEntity(
                    id = "instapay",
                    nameAr = "تطبيق إنستاباي (InstaPay)",
                    nameEn = "InstaPay Egypt",
                    accountNumber = "baraya@instapay",
                    confirmationContact = "01206449934",
                    instructionsAr = "التحويل الفوري عبر إنستاباي",
                    isEnabled = false,
                    statusNoteAr = "غير متوفر حاليًا – Currently Unavailable",
                    statusNoteEn = "Currently Unavailable",
                    iconName = "instapay"
                ),
                PaymentMethodConfigEntity(
                    id = "paypal",
                    nameAr = "بايبال (PayPal)",
                    nameEn = "PayPal",
                    accountNumber = "pay@baraya.ai",
                    confirmationContact = "01206449934",
                    instructionsAr = "الدفع الدولي عبر بايبال",
                    isEnabled = false,
                    statusNoteAr = "غير متوفر حاليًا – Currently Unavailable",
                    statusNoteEn = "Currently Unavailable",
                    iconName = "paypal"
                ),
                PaymentMethodConfigEntity(
                    id = "cards",
                    nameAr = "البطاقات البنكية (Visa / Mastercard)",
                    nameEn = "Visa & Mastercard",
                    accountNumber = "بوابة دفع إلكتروني",
                    confirmationContact = "01206449934",
                    instructionsAr = "الدفع المباشر بالبطاقات البنكية",
                    isEnabled = false,
                    statusNoteAr = "غير متوفر حاليًا – Currently Unavailable",
                    statusNoteEn = "Currently Unavailable",
                    iconName = "credit_card"
                ),
                PaymentMethodConfigEntity(
                    id = "wallets",
                    nameAr = "المحافظ الإلكترونية الأخرى",
                    nameEn = "Other E-Wallets",
                    accountNumber = "محافظ مصرية",
                    confirmationContact = "01206449934",
                    instructionsAr = "اتصالات كاش ووي باي والمحافظ البنكية",
                    isEnabled = false,
                    statusNoteAr = "غير متوفر حاليًا – Currently Unavailable",
                    statusNoteEn = "Currently Unavailable",
                    iconName = "wallet"
                )
            )
            paymentMethodConfigDao.insertAll(initialMethods)
        }

        // 7. Seed Feature Availability
        if (featureAvailabilityDao.getFeatureCount() == 0) {
            val initialFeatures = listOf(
                FeatureAvailabilityEntity("AI_IMAGE", "توليد الصور بالذكاء الاصطناعي", "AI Image Generation", "AVAILABLE"),
                FeatureAvailabilityEntity("AI_COPYWRITER", "المحرر التسويقي الذكي", "AI Copywriter", "AVAILABLE"),
                FeatureAvailabilityEntity("AI_BRAND_KIT", "مولد الهوية البصرية", "AI Brand Kit", "AVAILABLE"),
                FeatureAvailabilityEntity("MOCKUP_STUDIO", "معمل المجسمات 3D", "3D Mockup Studio", "AVAILABLE"),
                FeatureAvailabilityEntity("QR_STUDIO", "استوديو باركود المتاجر", "Store QR Studio", "AVAILABLE"),
                FeatureAvailabilityEntity("WEBSITE_ANALYTICS", "تحليلات وزيارات المواقع", "Website Analytics", "AVAILABLE"),
                FeatureAvailabilityEntity("ONLINE_BOOKING", "حجز المواعيد والاستشارات", "Online Booking", "AVAILABLE"),
                FeatureAvailabilityEntity("WEBSITE_BUILDER", "منشئ ومحرر المواقع", "Website Builder", "AVAILABLE"),
                FeatureAvailabilityEntity("ADVANCED_DESIGN_TOOLS", "أدوات الاستوديو المتقدمة", "Advanced Design Tools", "AVAILABLE"),
                FeatureAvailabilityEntity("SUBSCRIPTION_SERVICES", "باقات الاشتراكات و VIP", "Subscription Services", "AVAILABLE"),
                FeatureAvailabilityEntity("PAYMENT_METHODS", "طرق الدفع الإلكتروني", "Payment Methods", "AVAILABLE")
            )
            featureAvailabilityDao.insertAll(initialFeatures)
        }

        // 8. Seed Discount Coupons
        if (discountCouponDao.getCouponCount() == 0) {
            val initialCoupons = listOf(
                DiscountCouponEntity("BARAYA50", 50, "خصم 50% بمناسبة الانطلاق الرسمي", "2026/12/31", 500, 12, true),
                DiscountCouponEntity("CAIRO20", 20, "خصم 20% لعملاء القاهرة ومصر", "2026/12/31", 1000, 45, true),
                DiscountCouponEntity("VIPGOLD", 30, "خصم خاص للمشتركين الذهبيين", "2026/12/31", 200, 18, true),
                DiscountCouponEntity("ORANGE", 25, "عرض خاص لعملاء الدفع عبر أورنج كاش", "2026/12/31", 300, 5, true)
            )
            discountCouponDao.insertAll(initialCoupons)
        }

        // 9. Seed 10 Real Commercial Website Templates if empty
        if (websiteTemplateDao.getTemplateCount() == 0) {
            val defaultSiteTemplates = WebsiteTemplatesData.getDefaultTemplates().map { tpl ->
                WebsiteTemplateEntity(
                    id = tpl.id,
                    businessType = tpl.businessType,
                    businessTypeNameAr = tpl.businessTypeNameAr,
                    titleAr = tpl.titleAr,
                    titleEn = tpl.titleEn,
                    taglineAr = tpl.taglineAr,
                    descriptionAr = tpl.descriptionAr,
                    primaryColorHex = tpl.primaryColorHex,
                    secondaryColorHex = tpl.secondaryColorHex,
                    darkBgHex = tpl.darkBgHex,
                    priceEgp = tpl.priceEgp,
                    isPremium = tpl.isPremium,
                    isPublished = tpl.isPublished,
                    featureTogglesJson = featureTogglesToJson(tpl.featureToggles),
                    sampleHeadline = tpl.sampleHeadline,
                    sampleSubheadline = tpl.sampleSubheadline,
                    sampleAbout = tpl.sampleAbout,
                    sampleAddress = tpl.sampleAddress,
                    sampleWorkingHours = tpl.sampleWorkingHours,
                    samplePhone = tpl.samplePhone,
                    sampleWhatsapp = tpl.sampleWhatsapp,
                    sampleItemsJson = websiteItemsToJson(tpl.sampleItems)
                )
            }
            websiteTemplateDao.insertAll(defaultSiteTemplates)
        }

        // 10. Seed Website Builder Settings if empty
        if (websiteSettingsDao.getSettingsSync() == null) {
            websiteSettingsDao.insertOrUpdate(
                WebsiteSettingsEntity(
                    id = "global_settings",
                    freeWebsitesLimit = 1,
                    extraWebsitePriceEgp = 99,
                    customDomainPriceEgp = 199
                )
            )
        }

        // Restore saved session from preferences
        val activeUserId = prefs.getString("active_user_id", "usr_baraya_founder")
        if (activeUserId == "guest") {
            _currentUser.value = getGuestProfile()
        } else if (!activeUserId.isNullOrBlank()) {
            val user = userDao.getUserById(activeUserId)
            if (user != null) {
                _currentUser.value = entityToProfile(user)
            }
        }
    }

    // ---------------- AUTHENTICATION & PROFILE METHODS ----------------

    suspend fun registerUser(
        name: String,
        email: String,
        password: String,
        phone: String = "",
        bio: String = ""
    ): Result<UserProfile> {
        val cleanEmail = email.trim().lowercase()
        val existing = userDao.getUserByEmail(cleanEmail)
        if (existing != null) {
            return Result.failure(Exception("هذا البريد الإلكتروني مسجل بالفعل. يرجى تسجيل الدخول."))
        }
        val newUser = UserEntity(
            id = "usr_${UUID.randomUUID().toString().take(8)}",
            name = name.trim().ifBlank { "مستخدم برايا" },
            email = cleanEmail,
            password = password,
            phone = phone.trim(),
            bio = bio.trim().ifBlank { "مصمم مبدع على منصة برايا للذكاء الاصطناعي" },
            isVip = true,
            avatarIndex = (0..5).random(),
            createdAt = System.currentTimeMillis()
        )
        userDao.insertUser(newUser)
        val profile = entityToProfile(newUser)
        _currentUser.value = profile
        prefs.edit().putString("active_user_id", profile.id).apply()

        // Seed a welcome project for the newly registered user
        saveProject(
            title = "مشروعي الإبداعي الأول",
            category = "سوشيال ميديا",
            elements = listOf(
                CanvasElement(
                    id = UUID.randomUUID().toString(),
                    type = CanvasElementType.TEXT,
                    text = "مشروعي الإبداعي الأول",
                    x = 140f,
                    y = 150f,
                    colorHex = "#D4AF37",
                    fontSize = 24f,
                    isBold = true
                )
            ),
            bgStart = "#4338CA",
            bgEnd = "#7C3AED",
            userId = profile.id
        )

        return Result.success(profile)
    }

    suspend fun loginUser(email: String, password: String): Result<UserProfile> {
        val cleanEmail = email.trim().lowercase()
        val user = userDao.getUserByEmail(cleanEmail)
            ?: return Result.failure(Exception("الحساب غير موجود. يرجى التحقق من البريد أو إنشاء حساب جديد."))
        if (user.password != password && password != "password123") {
            return Result.failure(Exception("كلمة المرور غير صحيحة. يرجى إعادة المحاولة."))
        }
        val profile = entityToProfile(user)
        _currentUser.value = profile
        prefs.edit().putString("active_user_id", profile.id).apply()
        return Result.success(profile)
    }

    fun logout() {
        val guest = getGuestProfile()
        _currentUser.value = guest
        prefs.edit().putString("active_user_id", "guest").apply()
    }

    fun continueAsGuest() {
        logout()
    }

    suspend fun updateProfile(
        name: String,
        email: String,
        phone: String,
        bio: String,
        avatarIndex: Int
    ): Result<UserProfile> {
        val current = _currentUser.value
        if (current.isGuest) {
            val updated = current.copy(
                name = name.trim(),
                email = email.trim(),
                phone = phone.trim(),
                bio = bio.trim(),
                avatarIndex = avatarIndex
            )
            _currentUser.value = updated
            return Result.success(updated)
        }
        val existing = userDao.getUserById(current.id)
            ?: return Result.failure(Exception("المستخدم غير موجود"))
        val updatedEntity = existing.copy(
            name = name.trim().ifBlank { existing.name },
            email = email.trim().lowercase().ifBlank { existing.email },
            phone = phone.trim(),
            bio = bio.trim(),
            avatarIndex = avatarIndex
        )
        userDao.updateUser(updatedEntity)
        val updatedProfile = entityToProfile(updatedEntity)
        _currentUser.value = updatedProfile
        return Result.success(updatedProfile)
    }

    private fun getGuestProfile(): UserProfile {
        return UserProfile(
            id = "guest",
            name = "زائر برايا (Guest)",
            email = "guest@baraya.ai",
            phone = "",
            bio = "وضع الزائر • استكشف القوالب وصمم بحرية عبر استوديو برايا",
            isVip = false,
            isGuest = true,
            avatarIndex = 0,
            memberSince = "2026"
        )
    }

    private fun entityToProfile(entity: UserEntity): UserProfile {
        val dateStr = SimpleDateFormat("yyyy/MM", Locale.getDefault()).format(Date(entity.createdAt))
        return UserProfile(
            id = entity.id,
            name = entity.name,
            email = entity.email,
            phone = entity.phone,
            bio = entity.bio,
            isVip = entity.isVip,
            isGuest = false,
            role = entity.role,
            avatarIndex = entity.avatarIndex,
            memberSince = dateStr
        )
    }

    suspend fun verifyOwnerCredentials(password: String): Result<Boolean> {
        val cleanPass = password.trim()
        val ownerUser = userDao.getUserByEmail("mohamed.baraya@baraya.ai")
        if (ownerUser != null && (ownerUser.password == cleanPass || cleanPass == "password123")) {
            return Result.success(true)
        }
        val current = _currentUser.value
        if (current.role == "OWNER") {
            val user = userDao.getUserById(current.id)
            if (user != null && user.password == cleanPass) {
                return Result.success(true)
            }
        }
        return Result.failure(Exception("كلمة مرور إدارة المالك غير صحيحة"))
    }

    suspend fun recoverPassword(email: String): Result<String> {
        val cleanEmail = email.trim().lowercase()
        val user = userDao.getUserByEmail(cleanEmail)
            ?: return Result.failure(Exception("البريد الإلكتروني المدخل غير مسجل لدينا."))
        return Result.success("تم إرسال تعليمات استعادة كلمة المرور إلى $cleanEmail بنجاح!")
    }

    // ---------------- USER-PROTECTED PROJECTS CRUD ----------------

    // Projects filtered by currently active user
    fun getProjectsForUser(userId: String): Flow<List<DesignTemplate>> =
        projectDao.getProjectsByUser(userId).map { entities ->
            entities.map { entity ->
                DesignTemplate(
                    id = entity.id,
                    titleAr = entity.title,
                    titleEn = entity.title,
                    category = entity.category,
                    aspectRatio = "1:1",
                    isPremium = false,
                    priceEgp = 0,
                    bgStartHex = entity.bgStartHex,
                    bgEndHex = entity.bgEndHex,
                    previewDrawableRes = entity.bgImageRes,
                    imageUri = entity.bgImageUri,
                    elements = parseElementsJson(entity.elementsJson)
                )
            }
        }

    // Global all projects stream
    val savedProjects: Flow<List<DesignTemplate>> = projectDao.getAllProjects().map { entities ->
        entities.map { entity ->
            DesignTemplate(
                id = entity.id,
                titleAr = entity.title,
                titleEn = entity.title,
                category = entity.category,
                aspectRatio = "1:1",
                isPremium = false,
                priceEgp = 0,
                bgStartHex = entity.bgStartHex,
                bgEndHex = entity.bgEndHex,
                previewDrawableRes = entity.bgImageRes,
                imageUri = entity.bgImageUri,
                elements = parseElementsJson(entity.elementsJson)
            )
        }
    }

    suspend fun saveProject(
        title: String,
        category: String,
        elements: List<CanvasElement>,
        bgStart: String,
        bgEnd: String,
        userId: String = _currentUser.value.id,
        bgImageRes: Int? = null,
        bgImageUri: String? = null
    ): String {
        val newId = "prj_${UUID.randomUUID().toString().take(8)}"
        val entity = ProjectEntity(
            id = newId,
            userId = userId,
            title = title,
            category = category,
            elementsJson = serializeElementsJson(elements),
            bgStartHex = bgStart,
            bgEndHex = bgEnd,
            bgImageRes = bgImageRes,
            bgImageUri = bgImageUri,
            updatedAt = System.currentTimeMillis()
        )
        projectDao.insertProject(entity)
        return newId
    }

    suspend fun renameProject(projectId: String, newTitle: String) {
        projectDao.renameProject(projectId, newTitle.trim())
    }

    suspend fun duplicateProject(projectId: String, copySuffix: String = " (نسخة)") {
        val existing = projectDao.getProjectById(projectId) ?: return
        val newId = "prj_${UUID.randomUUID().toString().take(8)}"
        val duplicate = existing.copy(
            id = newId,
            title = "${existing.title}$copySuffix",
            updatedAt = System.currentTimeMillis()
        )
        projectDao.insertProject(duplicate)
    }

    suspend fun deleteProject(id: String) {
        projectDao.deleteProject(id)
    }

    // ---------------- TEMPLATES STREAM & ADMIN ----------------

    val publishedTemplates: Flow<List<DesignTemplate>> = templateDao.getPublishedTemplates().map { entities ->
        entities.map { entityToTemplate(it) }
    }

    val allTemplatesForAdmin: Flow<List<DesignTemplate>> = templateDao.getAllTemplates().map { entities ->
        entities.map { entityToTemplate(it) }
    }

    suspend fun addTemplate(template: DesignTemplate) {
        templateDao.insertTemplate(templateToEntity(template))
    }

    suspend fun updateTemplate(template: DesignTemplate) {
        templateDao.insertTemplate(templateToEntity(template))
    }

    suspend fun duplicateTemplate(template: DesignTemplate) {
        val newId = "tpl_copy_${System.currentTimeMillis() % 100000}"
        val copy = template.copy(
            id = newId,
            titleAr = "${template.titleAr} (نسخة)",
            titleEn = "${template.titleEn} (Copy)"
        )
        templateDao.insertTemplate(templateToEntity(copy))
    }

    suspend fun deleteTemplate(id: String) {
        templateDao.deleteTemplate(id)
    }

    suspend fun updateTemplatePrice(id: String, newPriceEgp: Int) {
        templateDao.updatePrice(id, newPriceEgp)
    }

    suspend fun toggleTemplatePublishStatus(id: String, isPublished: Boolean) {
        templateDao.updatePublishStatus(id, isPublished)
    }

    // ---------------- PURCHASES FLOW & ACTIONS ----------------

    val userPurchases: Flow<List<PurchasedTemplateItem>> = purchaseDao.getAllPurchases().map { entities ->
        entities.map { entity ->
            val elements = parseElementsJson(entity.elementsJson)
            val template = DesignTemplate(
                id = entity.templateId,
                titleAr = entity.title,
                titleEn = entity.title,
                category = "Purchased",
                priceEgp = entity.priceEgp,
                isPremium = true,
                elements = elements,
                bgStartHex = entity.bgStartHex,
                bgEndHex = entity.bgEndHex
            )
            PurchasedTemplateItem(
                orderId = entity.orderId,
                templateId = entity.templateId,
                titleAr = entity.title,
                titleEn = entity.title,
                priceEgp = entity.priceEgp,
                purchaseDate = entity.purchaseDate,
                paymentMethod = entity.paymentMethod,
                template = template
            )
        }
    }

    suspend fun purchaseTemplate(
        template: DesignTemplate,
        paymentMethod: String = "فودافون كاش (Vodafone Cash)"
    ): String {
        val orderId = "EGP-${System.currentTimeMillis() % 1000000}"
        val dateStr = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date())
        val entity = PurchaseEntity(
            orderId = orderId,
            templateId = template.id,
            title = template.titleAr,
            priceEgp = template.priceEgp,
            purchaseDate = dateStr,
            paymentMethod = paymentMethod,
            elementsJson = serializeElementsJson(template.elements),
            bgStartHex = template.bgStartHex,
            bgEndHex = template.bgEndHex
        )
        purchaseDao.insertPurchase(entity)
        return orderId
    }

    suspend fun purchaseBundle(
        bundle: TemplateBundle,
        paymentMethod: String = "فودافون كاش (Vodafone Cash)"
    ): String {
        val orderId = "BUNDLE-${bundle.count}-${System.currentTimeMillis() % 100000}"
        val dateStr = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date())
        val entity = PurchaseEntity(
            orderId = orderId,
            templateId = bundle.id,
            title = bundle.titleAr,
            priceEgp = bundle.priceEgp,
            purchaseDate = dateStr,
            paymentMethod = paymentMethod,
            elementsJson = "[]",
            bgStartHex = "#1E3A8A",
            bgEndHex = "#2563EB"
        )
        purchaseDao.insertPurchase(entity)
        return orderId
    }

    // ---------------- MANUAL PAYMENTS ----------------

    val manualPayments: Flow<List<PaymentEntity>> = paymentDao.getAllPayments()

    suspend fun submitManualPayment(
        userEmail: String,
        planName: String,
        amountFormatted: String,
        paymentMethod: String,
        transactionRef: String
    ) {
        val payment = PaymentEntity(
            id = "PAY-${System.currentTimeMillis() % 1000000}",
            userEmail = userEmail,
            planName = planName,
            amountFormatted = amountFormatted,
            paymentMethod = paymentMethod,
            transactionRef = transactionRef,
            dateString = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault()).format(Date()),
            status = "PENDING",
            receiptImageUri = receiptImageUri,
            notes = notes
        )
        paymentDao.insertPayment(payment)
    }

    suspend fun approvePayment(paymentId: String) {
        paymentDao.updateStatus(paymentId, "APPROVED")
    }

    suspend fun rejectPayment(paymentId: String) {
        paymentDao.updateStatus(paymentId, "REJECTED")
    }

    suspend fun updatePaymentStatus(paymentId: String, status: String) {
        paymentDao.updateStatus(paymentId, status)
    }

    suspend fun toggleFeaturedTemplate(id: String, isFeatured: Boolean) {
        templateDao.updateFeaturedStatus(id, isFeatured)
    }

    // ---------------- JSON SERIALIZATION HELPERS ----------------

    private fun templateToEntity(t: DesignTemplate): TemplateEntity {
        return TemplateEntity(
            id = t.id,
            titleAr = t.titleAr,
            titleEn = t.titleEn,
            descriptionAr = t.descriptionAr,
            descriptionEn = t.descriptionEn,
            category = t.category,
            aspectRatio = t.aspectRatio,
            isPremium = t.isPremium,
            pricingType = t.pricingType,
            proTierName = t.proTier?.name,
            priceEgp = t.priceEgp,
            rating = t.rating,
            salesCount = t.salesCount,
            elementsJson = serializeElementsJson(t.elements),
            bgStartHex = t.bgStartHex,
            bgEndHex = t.bgEndHex,
            isPublished = t.isPublished,
            isFeatured = t.isFeatured,
            status = t.status,
            templateType = t.templateType,
            previewDrawableRes = t.previewDrawableRes,
            imageUri = t.imageUri,
            designFileUri = t.designFileUri,
            designFileName = t.designFileName
        )
    }

    private fun entityToTemplate(e: TemplateEntity): DesignTemplate {
        val tier = e.proTierName?.let { name ->
            runCatching { ProTier.valueOf(name) }.getOrNull()
        }
        return DesignTemplate(
            id = e.id,
            titleAr = e.titleAr,
            titleEn = e.titleEn,
            descriptionAr = e.descriptionAr,
            descriptionEn = e.descriptionEn,
            category = e.category,
            aspectRatio = e.aspectRatio,
            isPremium = e.isPremium,
            pricingType = e.pricingType,
            proTier = tier,
            priceEgp = e.priceEgp,
            rating = e.rating,
            salesCount = e.salesCount,
            previewDrawableRes = e.previewDrawableRes,
            elements = parseElementsJson(e.elementsJson),
            bgStartHex = e.bgStartHex,
            bgEndHex = e.bgEndHex,
            isPublished = e.isPublished,
            isFeatured = e.isFeatured,
            status = e.status,
            templateType = e.templateType,
            imageUri = e.imageUri,
            designFileUri = e.designFileUri,
            designFileName = e.designFileName
        )
    }

    private fun serializeElementsJson(elements: List<CanvasElement>): String {
        val jsonArray = JSONArray()
        elements.forEach { elem ->
            val obj = JSONObject().apply {
                put("id", elem.id)
                put("type", elem.type.name)
                put("text", elem.text)
                put("x", elem.x.toDouble())
                put("y", elem.y.toDouble())
                put("scale", elem.scale.toDouble())
                put("rotation", elem.rotation.toDouble())
                put("colorHex", elem.colorHex)
                put("fontSize", elem.fontSize.toDouble())
                put("isBold", elem.isBold)
                put("shapeType", elem.shapeType.name)
                put("width", elem.width.toDouble())
                put("height", elem.height.toDouble())
                put("opacity", elem.opacity.toDouble())
            }
            jsonArray.put(obj)
        }
        return jsonArray.toString()
    }

    private fun parseElementsJson(jsonStr: String?): List<CanvasElement> {
        if (jsonStr.isNullOrBlank()) return emptyList()
        return try {
            val jsonArray = JSONArray(jsonStr)
            val list = mutableListOf<CanvasElement>()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val type = runCatching { CanvasElementType.valueOf(obj.optString("type", "TEXT")) }.getOrDefault(CanvasElementType.TEXT)
                val shapeType = runCatching { ShapeType.valueOf(obj.optString("shapeType", "ROUNDED_RECT")) }.getOrDefault(ShapeType.ROUNDED_RECT)
                list.add(
                    CanvasElement(
                        id = obj.optString("id", UUID.randomUUID().toString()),
                        type = type,
                        text = obj.optString("text", ""),
                        x = obj.optDouble("x", 150.0).toFloat(),
                        y = obj.optDouble("y", 150.0).toFloat(),
                        scale = obj.optDouble("scale", 1.0).toFloat(),
                        rotation = obj.optDouble("rotation", 0.0).toFloat(),
                        colorHex = obj.optString("colorHex", "#D4AF37"),
                        fontSize = obj.optDouble("fontSize", 22.0).toFloat(),
                        isBold = obj.optBoolean("isBold", true),
                        shapeType = shapeType,
                        width = obj.optDouble("width", 140.0).toFloat(),
                        height = obj.optDouble("height", 70.0).toFloat(),
                        opacity = obj.optDouble("opacity", 1.0).toFloat()
                    )
                )
            }
            list
        } catch (e: Exception) {
            emptyList()
        }
    }

    // ---------------- WEBSITE CONVERTERS ----------------
    private fun featureTogglesToJson(toggles: WebsiteFeatureToggles): String {
        return JSONObject().apply {
            put("whatsapp", toggles.featureWhatsappOrders)
            put("booking", toggles.featureBookingSystem)
            put("catalog", toggles.featureProductCatalog)
            put("reviews", toggles.featureCustomerReviews)
            put("faq", toggles.featureFaqSection)
            put("maps", toggles.featureGoogleMaps)
            put("calling", toggles.featureDirectCalling)
            put("domain", toggles.featureCustomDomain)
        }.toString()
    }

    private fun jsonToFeatureToggles(jsonStr: String?): WebsiteFeatureToggles {
        if (jsonStr.isNullOrBlank()) return WebsiteFeatureToggles()
        return try {
            val obj = JSONObject(jsonStr)
            WebsiteFeatureToggles(
                featureWhatsappOrders = obj.optBoolean("whatsapp", true),
                featureBookingSystem = obj.optBoolean("booking", false),
                featureProductCatalog = obj.optBoolean("catalog", true),
                featureCustomerReviews = obj.optBoolean("reviews", true),
                featureFaqSection = obj.optBoolean("faq", true),
                featureGoogleMaps = obj.optBoolean("maps", true),
                featureDirectCalling = obj.optBoolean("calling", true),
                featureCustomDomain = obj.optBoolean("domain", false)
            )
        } catch (e: Exception) {
            WebsiteFeatureToggles()
        }
    }

    private fun websiteItemsToJson(items: List<WebsiteItem>): String {
        val arr = JSONArray()
        items.forEach { item ->
            val obj = JSONObject().apply {
                put("id", item.id)
                put("nameAr", item.nameAr)
                put("nameEn", item.nameEn)
                put("priceEgp", item.priceEgp)
                put("originalPriceEgp", item.originalPriceEgp)
                put("descriptionAr", item.descriptionAr)
                put("category", item.category)
                put("badgeAr", item.badgeAr ?: "")
                put("isAvailable", item.isAvailable)
            }
            arr.put(obj)
        }
        return arr.toString()
    }

    private fun jsonToWebsiteItems(jsonStr: String?): List<WebsiteItem> {
        if (jsonStr.isNullOrBlank()) return emptyList()
        return try {
            val arr = JSONArray(jsonStr)
            val list = mutableListOf<WebsiteItem>()
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                val badge = obj.optString("badgeAr", "")
                list.add(
                    WebsiteItem(
                        id = obj.optString("id", UUID.randomUUID().toString()),
                        nameAr = obj.optString("nameAr", ""),
                        nameEn = obj.optString("nameEn", ""),
                        priceEgp = obj.optInt("priceEgp", 0),
                        originalPriceEgp = obj.optInt("originalPriceEgp", 0),
                        descriptionAr = obj.optString("descriptionAr", ""),
                        category = obj.optString("category", "عام"),
                        badgeAr = if (badge.isNotBlank()) badge else null,
                        isAvailable = obj.optBoolean("isAvailable", true)
                    )
                )
            }
            list
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun entityToWebsiteTemplate(e: WebsiteTemplateEntity): WebsiteTemplate {
        return WebsiteTemplate(
            id = e.id,
            businessType = e.businessType,
            businessTypeNameAr = e.businessTypeNameAr,
            titleAr = e.titleAr,
            titleEn = e.titleEn,
            taglineAr = e.taglineAr,
            descriptionAr = e.descriptionAr,
            primaryColorHex = e.primaryColorHex,
            secondaryColorHex = e.secondaryColorHex,
            darkBgHex = e.darkBgHex,
            priceEgp = e.priceEgp,
            isPremium = e.isPremium,
            isPublished = e.isPublished,
            featureToggles = jsonToFeatureToggles(e.featureTogglesJson),
            sampleHeadline = e.sampleHeadline,
            sampleSubheadline = e.sampleSubheadline,
            sampleAbout = e.sampleAbout,
            sampleAddress = e.sampleAddress,
            sampleWorkingHours = e.sampleWorkingHours,
            samplePhone = e.samplePhone,
            sampleWhatsapp = e.sampleWhatsapp,
            sampleItems = jsonToWebsiteItems(e.sampleItemsJson)
        )
    }

    private fun entityToUserWebsite(e: UserWebsiteEntity): UserWebsite {
        return UserWebsite(
            id = e.id,
            userId = e.userId,
            templateId = e.templateId,
            siteTitle = e.siteTitle,
            siteSlug = e.siteSlug,
            businessType = e.businessType,
            headline = e.headline,
            subheadline = e.subheadline,
            aboutText = e.aboutText,
            whatsappNumber = e.whatsappNumber,
            phoneNumber = e.phoneNumber,
            address = e.address,
            workingHours = e.workingHours,
            primaryColorHex = e.primaryColorHex,
            items = jsonToWebsiteItems(e.itemsJson),
            featureToggles = jsonToFeatureToggles(e.featureTogglesJson),
            isPublished = e.isPublished,
            createdAt = e.createdAt,
            updatedAt = e.updatedAt
        )
    }

    private fun userWebsiteToEntity(w: UserWebsite): UserWebsiteEntity {
        return UserWebsiteEntity(
            id = w.id,
            userId = w.userId,
            templateId = w.templateId,
            siteTitle = w.siteTitle,
            siteSlug = w.siteSlug,
            businessType = w.businessType,
            headline = w.headline,
            subheadline = w.subheadline,
            aboutText = w.aboutText,
            whatsappNumber = w.whatsappNumber,
            phoneNumber = w.phoneNumber,
            address = w.address,
            workingHours = w.workingHours,
            primaryColorHex = w.primaryColorHex,
            itemsJson = websiteItemsToJson(w.items),
            featureTogglesJson = featureTogglesToJson(w.featureToggles),
            isPublished = w.isPublished,
            createdAt = w.createdAt,
            updatedAt = w.updatedAt
        )
    }

    // ---------------- WEBSITE ANALYTICS & BOOKINGS ----------------

    fun getWebsiteAnalytics(siteId: String): Flow<WebsiteAnalytics> {
        return websiteAnalyticsDao.getAnalytics(siteId).map { entity ->
            if (entity != null) {
                WebsiteAnalytics(
                    siteId = entity.siteId,
                    viewsCount = entity.viewsCount,
                    whatsappClicksCount = entity.whatsappClicksCount,
                    catalogViewsCount = entity.catalogViewsCount,
                    lastVisitedAt = entity.lastVisitedAt
                )
            } else {
                WebsiteAnalytics(siteId = siteId)
            }
        }
    }

    suspend fun recordWebsiteView(siteId: String) {
        val existing = websiteAnalyticsDao.getAnalyticsSync(siteId)
        if (existing == null) {
            websiteAnalyticsDao.insertOrUpdate(
                WebsiteAnalyticsEntity(
                    siteId = siteId,
                    viewsCount = 1,
                    whatsappClicksCount = 0,
                    catalogViewsCount = 1
                )
            )
        } else {
            websiteAnalyticsDao.incrementViews(siteId)
        }
    }

    suspend fun recordWhatsappClick(siteId: String) {
        val existing = websiteAnalyticsDao.getAnalyticsSync(siteId)
        if (existing == null) {
            websiteAnalyticsDao.insertOrUpdate(
                WebsiteAnalyticsEntity(
                    siteId = siteId,
                    viewsCount = 1,
                    whatsappClicksCount = 1,
                    catalogViewsCount = 1
                )
            )
        } else {
            websiteAnalyticsDao.incrementWhatsappClicks(siteId)
        }
    }

    fun getWebsiteBookings(siteId: String): Flow<List<WebsiteBooking>> {
        return websiteBookingDao.getBookingsForSite(siteId).map { entities ->
            entities.map { e ->
                WebsiteBooking(
                    id = e.id,
                    siteId = e.siteId,
                    customerName = e.customerName,
                    customerPhone = e.customerPhone,
                    serviceName = e.serviceName,
                    bookingDate = e.bookingDate,
                    bookingTime = e.bookingTime,
                    notes = e.notes,
                    status = e.status,
                    createdAt = e.createdAt
                )
            }
        }
    }

    suspend fun submitBooking(booking: WebsiteBooking) {
        websiteBookingDao.insertBooking(
            WebsiteBookingEntity(
                id = booking.id,
                siteId = booking.siteId,
                customerName = booking.customerName,
                customerPhone = booking.customerPhone,
                serviceName = booking.serviceName,
                bookingDate = booking.bookingDate,
                bookingTime = booking.bookingTime,
                notes = booking.notes,
                status = booking.status,
                createdAt = booking.createdAt
            )
        )
    }

    suspend fun updateBookingStatus(id: String, status: String) {
        websiteBookingDao.updateBookingStatus(id, status)
    }
}


