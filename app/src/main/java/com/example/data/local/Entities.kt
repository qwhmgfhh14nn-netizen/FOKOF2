package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val password: String,
    val phone: String = "",
    val bio: String = "",
    val isVip: Boolean = true,
    val avatarIndex: Int = 0,
    val role: String = "USER", // "OWNER" or "USER"
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_projects")
data class ProjectEntity(
    @PrimaryKey val id: String,
    val userId: String = "guest",
    val title: String,
    val category: String,
    val elementsJson: String,
    val bgStartHex: String,
    val bgEndHex: String,
    val bgImageRes: Int? = null,
    val bgImageUri: String? = null,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "manual_payments")
data class PaymentEntity(
    @PrimaryKey val id: String,
    val userEmail: String,
    val planName: String,
    val amountFormatted: String,
    val paymentMethod: String,
    val transactionRef: String,
    val dateString: String,
    val status: String,
    val receiptImageUri: String? = null,
    val notes: String = ""
)

@Entity(tableName = "user_purchases")
data class PurchaseEntity(
    @PrimaryKey val orderId: String,
    val templateId: String,
    val title: String,
    val priceEgp: Int,
    val purchaseDate: String,
    val paymentMethod: String,
    val elementsJson: String,
    val bgStartHex: String,
    val bgEndHex: String
)

@Entity(tableName = "templates")
data class TemplateEntity(
    @PrimaryKey val id: String,
    val titleAr: String,
    val titleEn: String,
    val descriptionAr: String = "",
    val descriptionEn: String = "",
    val category: String,
    val aspectRatio: String = "1:1",
    val isPremium: Boolean = false,
    val pricingType: String = "FREE", // "FREE", "PREMIUM", "EXCLUSIVE", "PRO"
    val proTierName: String? = null,
    val priceEgp: Int = 0,
    val rating: Float = 4.9f,
    val salesCount: Int = 100,
    val elementsJson: String,
    val bgStartHex: String,
    val bgEndHex: String,
    val isPublished: Boolean = true,
    val isFeatured: Boolean = false,
    val status: String = "PUBLISHED", // "PUBLISHED", "DRAFT", "HIDDEN"
    val templateType: String = "FREE", // "FREE", "PREMIUM", "VIP", "PRO"
    val previewDrawableRes: Int? = null,
    val imageUri: String? = null,
    val designFileUri: String? = null,
    val designFileName: String? = null,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val nameAr: String,
    val nameEn: String,
    val iconName: String = "folder",
    val orderIndex: Int = 0,
    val isDefault: Boolean = false
)

@Entity(tableName = "template_bundles")
data class BundleEntity(
    @PrimaryKey val id: String,
    val count: Int,
    val priceEgp: Int,
    val originalPriceEgp: Int,
    val discountPercent: Int,
    val badgeAr: String = "",
    val badgeEn: String = "",
    val isPopular: Boolean = false
)

@Entity(tableName = "subscription_plans")
data class SubscriptionPlanEntity(
    @PrimaryKey val id: String,
    val nameAr: String,
    val nameEn: String,
    val priceEgp: Int,
    val originalPriceEgp: Int,
    val billingCycle: String = "MONTHLY", // "MONTHLY", "ANNUAL", "LIFETIME"
    val descriptionAr: String = "",
    val descriptionEn: String = "",
    val isPopular: Boolean = false
)

@Entity(tableName = "user_websites")
data class UserWebsiteEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val templateId: String,
    val siteTitle: String,
    val siteSlug: String,
    val businessType: String,
    val headline: String,
    val subheadline: String,
    val aboutText: String,
    val whatsappNumber: String,
    val phoneNumber: String,
    val address: String,
    val workingHours: String,
    val primaryColorHex: String,
    val itemsJson: String,
    val featureTogglesJson: String,
    val isPublished: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "website_templates")
data class WebsiteTemplateEntity(
    @PrimaryKey val id: String,
    val businessType: String,
    val businessTypeNameAr: String,
    val titleAr: String,
    val titleEn: String,
    val taglineAr: String,
    val descriptionAr: String,
    val primaryColorHex: String,
    val secondaryColorHex: String,
    val darkBgHex: String,
    val priceEgp: Int = 0,
    val isPremium: Boolean = false,
    val isPublished: Boolean = true,
    val featureTogglesJson: String,
    val sampleHeadline: String,
    val sampleSubheadline: String,
    val sampleAbout: String,
    val sampleAddress: String,
    val sampleWorkingHours: String,
    val samplePhone: String,
    val sampleWhatsapp: String,
    val sampleItemsJson: String
)

@Entity(tableName = "website_settings")
data class WebsiteSettingsEntity(
    @PrimaryKey val id: String = "global_settings",
    val freeWebsitesLimit: Int = 1,
    val extraWebsitePriceEgp: Int = 99,
    val customDomainPriceEgp: Int = 199
)

@Entity(tableName = "website_analytics")
data class WebsiteAnalyticsEntity(
    @PrimaryKey val siteId: String,
    val viewsCount: Int = 0,
    val whatsappClicksCount: Int = 0,
    val catalogViewsCount: Int = 0,
    val lastVisitedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "website_bookings")
data class WebsiteBookingEntity(
    @PrimaryKey val id: String,
    val siteId: String,
    val customerName: String,
    val customerPhone: String,
    val serviceName: String,
    val bookingDate: String,
    val bookingTime: String,
    val notes: String = "",
    val status: String = "PENDING",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "payment_method_configs")
data class PaymentMethodConfigEntity(
    @PrimaryKey val id: String,
    val nameAr: String,
    val nameEn: String,
    val accountNumber: String = "",
    val confirmationContact: String = "",
    val whatsappLink: String = "",
    val telegramLink: String = "",
    val instructionsAr: String = "",
    val instructionsEn: String = "",
    val isEnabled: Boolean = false,
    val statusNoteAr: String = "غير متوفر حاليًا",
    val statusNoteEn: String = "Currently Unavailable",
    val iconName: String = "payment"
)

@Entity(tableName = "feature_availabilities")
data class FeatureAvailabilityEntity(
    @PrimaryKey val featureKey: String,
    val titleAr: String,
    val titleEn: String,
    val status: String = "AVAILABLE", // "AVAILABLE", "UNAVAILABLE", "DISABLED", "COMING_SOON"
    val messageAr: String = "غير متوفر حاليًا – ميزة تحت التحديث والتطوير",
    val messageEn: String = "Currently Unavailable"
)

@Entity(tableName = "discount_coupons")
data class DiscountCouponEntity(
    @PrimaryKey val code: String,
    val discountPercent: Int,
    val description: String,
    val expirationDate: String = "",
    val usageLimit: Int = 100,
    val usageCount: Int = 0,
    val isActive: Boolean = true
)



