package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE LOWER(email) = LOWER(:email) LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("SELECT * FROM users ORDER BY createdAt DESC")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int
}

@Dao
interface ProjectDao {
    @Query("SELECT * FROM saved_projects ORDER BY updatedAt DESC")
    fun getAllProjects(): Flow<List<ProjectEntity>>

    @Query("SELECT * FROM saved_projects WHERE userId = :userId ORDER BY updatedAt DESC")
    fun getProjectsByUser(userId: String): Flow<List<ProjectEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: ProjectEntity)

    @Query("DELETE FROM saved_projects WHERE id = :projectId")
    suspend fun deleteProject(projectId: String)

    @Query("UPDATE saved_projects SET title = :newTitle, updatedAt = :updatedAt WHERE id = :projectId")
    suspend fun renameProject(projectId: String, newTitle: String, updatedAt: Long = System.currentTimeMillis())

    @Query("SELECT * FROM saved_projects WHERE id = :projectId LIMIT 1")
    suspend fun getProjectById(projectId: String): ProjectEntity?
}

@Dao
interface PaymentDao {
    @Query("SELECT * FROM manual_payments ORDER BY rowid DESC")
    fun getAllPayments(): Flow<List<PaymentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: PaymentEntity)

    @Query("UPDATE manual_payments SET status = :status WHERE id = :paymentId")
    suspend fun updateStatus(paymentId: String, status: String)
}

@Dao
interface PurchaseDao {
    @Query("SELECT * FROM user_purchases ORDER BY rowid DESC")
    fun getAllPurchases(): Flow<List<PurchaseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPurchase(purchase: PurchaseEntity)
}

@Dao
interface TemplateDao {
    @Query("SELECT * FROM templates WHERE isPublished = 1 ORDER BY rowid ASC")
    fun getPublishedTemplates(): Flow<List<TemplateEntity>>

    @Query("SELECT * FROM templates ORDER BY rowid DESC")
    fun getAllTemplates(): Flow<List<TemplateEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(template: TemplateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(templates: List<TemplateEntity>)

    @Query("DELETE FROM templates WHERE id = :id")
    suspend fun deleteTemplate(id: String)

    @Query("UPDATE templates SET priceEgp = :newPrice, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updatePrice(id: String, newPrice: Int, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE templates SET isPublished = :isPublished, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updatePublishStatus(id: String, isPublished: Boolean, updatedAt: Long = System.currentTimeMillis())

    @Query("UPDATE templates SET isFeatured = :isFeatured, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateFeaturedStatus(id: String, isFeatured: Boolean, updatedAt: Long = System.currentTimeMillis())

    @Query("DELETE FROM templates")
    suspend fun deleteAllTemplates()

    @Query("SELECT COUNT(*) FROM templates")
    suspend fun getTemplateCount(): Int
}

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY orderIndex ASC, rowid ASC")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<CategoryEntity>)

    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun deleteCategory(id: String)

    @Query("UPDATE categories SET nameAr = :nameAr, nameEn = :nameEn WHERE id = :id")
    suspend fun updateCategory(id: String, nameAr: String, nameEn: String)

    @Query("SELECT COUNT(*) FROM categories")
    suspend fun getCategoryCount(): Int
}

@Dao
interface BundleDao {
    @Query("SELECT * FROM template_bundles ORDER BY count ASC")
    fun getAllBundles(): Flow<List<BundleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBundle(bundle: BundleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(bundles: List<BundleEntity>)

    @Query("UPDATE template_bundles SET priceEgp = :priceEgp WHERE id = :id")
    suspend fun updateBundlePrice(id: String, priceEgp: Int)

    @Query("SELECT COUNT(*) FROM template_bundles")
    suspend fun getBundleCount(): Int
}

@Dao
interface SubscriptionPlanDao {
    @Query("SELECT * FROM subscription_plans ORDER BY priceEgp ASC")
    fun getAllPlans(): Flow<List<SubscriptionPlanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlan(plan: SubscriptionPlanEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plans: List<SubscriptionPlanEntity>)

    @Query("UPDATE subscription_plans SET priceEgp = :priceEgp WHERE id = :id")
    suspend fun updatePlanPrice(id: String, priceEgp: Int)

    @Query("SELECT COUNT(*) FROM subscription_plans")
    suspend fun getPlanCount(): Int
}

@Dao
interface UserWebsiteDao {
    @Query("SELECT * FROM user_websites ORDER BY updatedAt DESC")
    fun getAllUserWebsites(): Flow<List<UserWebsiteEntity>>

    @Query("SELECT * FROM user_websites WHERE userId = :userId ORDER BY updatedAt DESC")
    fun getUserWebsites(userId: String): Flow<List<UserWebsiteEntity>>

    @Query("SELECT * FROM user_websites WHERE id = :id")
    suspend fun getWebsiteById(id: String): UserWebsiteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebsite(website: UserWebsiteEntity)

    @Update
    suspend fun updateWebsite(website: UserWebsiteEntity)

    @Query("DELETE FROM user_websites WHERE id = :id")
    suspend fun deleteWebsite(id: String)

    @Query("SELECT COUNT(*) FROM user_websites WHERE userId = :userId")
    suspend fun getUserWebsiteCount(userId: String): Int
}

@Dao
interface WebsiteTemplateDao {
    @Query("SELECT * FROM website_templates ORDER BY businessType ASC")
    fun getAllTemplates(): Flow<List<WebsiteTemplateEntity>>

    @Query("SELECT * FROM website_templates WHERE isPublished = 1 ORDER BY businessType ASC")
    fun getPublishedTemplates(): Flow<List<WebsiteTemplateEntity>>

    @Query("SELECT * FROM website_templates WHERE id = :id")
    suspend fun getTemplateById(id: String): WebsiteTemplateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(template: WebsiteTemplateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(templates: List<WebsiteTemplateEntity>)

    @Query("UPDATE website_templates SET priceEgp = :priceEgp WHERE id = :id")
    suspend fun updateTemplatePrice(id: String, priceEgp: Int)

    @Query("UPDATE website_templates SET featureTogglesJson = :togglesJson WHERE id = :id")
    suspend fun updateFeatureToggles(id: String, togglesJson: String)

    @Query("DELETE FROM website_templates WHERE id = :id")
    suspend fun deleteTemplate(id: String)

    @Query("SELECT COUNT(*) FROM website_templates")
    suspend fun getTemplateCount(): Int
}

@Dao
interface WebsiteSettingsDao {
    @Query("SELECT * FROM website_settings WHERE id = 'global_settings'")
    fun getSettings(): Flow<WebsiteSettingsEntity?>

    @Query("SELECT * FROM website_settings WHERE id = 'global_settings'")
    suspend fun getSettingsSync(): WebsiteSettingsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(settings: WebsiteSettingsEntity)

    @Query("UPDATE website_settings SET freeWebsitesLimit = :limit WHERE id = 'global_settings'")
    suspend fun updateFreeLimit(limit: Int)

    @Query("UPDATE website_settings SET extraWebsitePriceEgp = :extraPrice, customDomainPriceEgp = :domainPrice WHERE id = 'global_settings'")
    suspend fun updatePrices(extraPrice: Int, domainPrice: Int)
}

@Dao
interface WebsiteAnalyticsDao {
    @Query("SELECT * FROM website_analytics WHERE siteId = :siteId")
    fun getAnalytics(siteId: String): Flow<WebsiteAnalyticsEntity?>

    @Query("SELECT * FROM website_analytics WHERE siteId = :siteId")
    suspend fun getAnalyticsSync(siteId: String): WebsiteAnalyticsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(analytics: WebsiteAnalyticsEntity)

    @Query("UPDATE website_analytics SET viewsCount = viewsCount + 1, lastVisitedAt = :timestamp WHERE siteId = :siteId")
    suspend fun incrementViews(siteId: String, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE website_analytics SET whatsappClicksCount = whatsappClicksCount + 1 WHERE siteId = :siteId")
    suspend fun incrementWhatsappClicks(siteId: String)

    @Query("UPDATE website_analytics SET catalogViewsCount = catalogViewsCount + 1 WHERE siteId = :siteId")
    suspend fun incrementCatalogViews(siteId: String)
}

@Dao
interface WebsiteBookingDao {
    @Query("SELECT * FROM website_bookings WHERE siteId = :siteId ORDER BY createdAt DESC")
    fun getBookingsForSite(siteId: String): Flow<List<WebsiteBookingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: WebsiteBookingEntity)

    @Query("UPDATE website_bookings SET status = :status WHERE id = :id")
    suspend fun updateBookingStatus(id: String, status: String)

    @Query("DELETE FROM website_bookings WHERE id = :id")
    suspend fun deleteBooking(id: String)
}

@Dao
interface PaymentMethodConfigDao {
    @Query("SELECT * FROM payment_method_configs ORDER BY rowid ASC")
    fun getAllPaymentMethods(): Flow<List<PaymentMethodConfigEntity>>

    @Query("SELECT * FROM payment_method_configs WHERE isEnabled = 1 ORDER BY rowid ASC")
    fun getEnabledPaymentMethods(): Flow<List<PaymentMethodConfigEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMethod(method: PaymentMethodConfigEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(methods: List<PaymentMethodConfigEntity>)

    @Query("UPDATE payment_method_configs SET isEnabled = :isEnabled WHERE id = :id")
    suspend fun updateStatus(id: String, isEnabled: Boolean)

    @Query("UPDATE payment_method_configs SET accountNumber = :accountNumber, confirmationContact = :contact, whatsappLink = :wa, telegramLink = :tg, instructionsAr = :instrAr, statusNoteAr = :statusNoteAr WHERE id = :id")
    suspend fun updateDetails(id: String, accountNumber: String, contact: String, wa: String, tg: String, instrAr: String, statusNoteAr: String)

    @Query("DELETE FROM payment_method_configs WHERE id = :id")
    suspend fun deleteMethod(id: String)

    @Query("SELECT COUNT(*) FROM payment_method_configs")
    suspend fun getMethodCount(): Int
}

@Dao
interface FeatureAvailabilityDao {
    @Query("SELECT * FROM feature_availabilities ORDER BY rowid ASC")
    fun getAllFeatures(): Flow<List<FeatureAvailabilityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeature(feature: FeatureAvailabilityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(features: List<FeatureAvailabilityEntity>)

    @Query("UPDATE feature_availabilities SET status = :status WHERE featureKey = :key")
    suspend fun updateFeatureStatus(key: String, status: String)

    @Query("SELECT COUNT(*) FROM feature_availabilities")
    suspend fun getFeatureCount(): Int
}

@Dao
interface DiscountCouponDao {
    @Query("SELECT * FROM discount_coupons ORDER BY rowid DESC")
    fun getAllCoupons(): Flow<List<DiscountCouponEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoupon(coupon: DiscountCouponEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coupons: List<DiscountCouponEntity>)

    @Query("DELETE FROM discount_coupons WHERE code = :code")
    suspend fun deleteCoupon(code: String)

    @Query("SELECT * FROM discount_coupons WHERE code = :code LIMIT 1")
    suspend fun getCouponByCode(code: String): DiscountCouponEntity?

    @Query("SELECT COUNT(*) FROM discount_coupons")
    suspend fun getCouponCount(): Int
}



