package com.example.data.model

import java.util.UUID

enum class CanvasElementType {
    TEXT,
    SHAPE,
    CALLIGRAPHY,
    IMAGE_BADGE
}

enum class ShapeType {
    RECTANGLE,
    ROUNDED_RECT,
    CIRCLE,
    STAR,
    HEXAGON,
    DIAMOND
}

data class CanvasElement(
    val id: String = UUID.randomUUID().toString(),
    val type: CanvasElementType = CanvasElementType.TEXT,
    val text: String = "",
    val x: Float = 150f,
    val y: Float = 150f,
    val scale: Float = 1f,
    val rotation: Float = 0f,
    val colorHex: String = "#D4AF37",
    val fontSize: Float = 22f,
    val isBold: Boolean = true,
    val shapeType: ShapeType = ShapeType.ROUNDED_RECT,
    val width: Float = 140f,
    val height: Float = 70f,
    val opacity: Float = 1f
)

enum class ProTier(val titleAr: String, val titleEn: String, val defaultPriceEgp: Int) {
    BASIC("أساسي", "Basic", 5),
    PROFESSIONAL("احترافي", "Professional", 10),
    PREMIUM("بريميوم", "Premium", 15),
    EXCLUSIVE("حصري", "Exclusive", 20)
}

data class TemplateBundle(
    val id: String,
    val count: Int,
    val priceEgp: Int,
    val originalPriceEgp: Int,
    val titleAr: String,
    val titleEn: String,
    val descriptionAr: String,
    val descriptionEn: String,
    val isPopular: Boolean = false
)

data class DesignTemplate(
    val id: String,
    val titleAr: String,
    val titleEn: String,
    val descriptionAr: String = "",
    val descriptionEn: String = "",
    val category: String,
    val aspectRatio: String = "1:1",
    val isPremium: Boolean = false,
    val pricingType: String = "FREE", // "FREE", "PREMIUM", "EXCLUSIVE", "PRO"
    val proTier: ProTier? = null,
    val priceEgp: Int = 0, // In Egyptian Pounds (0 = Free)
    val rating: Float = 4.9f,
    val salesCount: Int = 240,
    val previewDrawableRes: Int? = null,
    val elements: List<CanvasElement> = emptyList(),
    val bgStartHex: String = "#101014",
    val bgEndHex: String = "#1B1812",
    val isPublished: Boolean = true,
    val isFeatured: Boolean = false,
    val status: String = "PUBLISHED", // "PUBLISHED", "DRAFT", "HIDDEN"
    val templateType: String = "FREE", // "FREE", "PREMIUM", "VIP", "PRO"
    val imageUri: String? = null,
    val designFileUri: String? = null,
    val designFileName: String? = null
)

data class CategoryModel(
    val id: String,
    val nameAr: String,
    val nameEn: String,
    val iconName: String = "folder",
    val orderIndex: Int = 0,
    val isDefault: Boolean = false
)

data class SubscriptionPlan(
    val id: String,
    val nameAr: String,
    val nameEn: String,
    val priceEgp: Int,
    val originalPriceEgp: Int,
    val billingCycle: String = "MONTHLY",
    val descriptionAr: String = "",
    val descriptionEn: String = "",
    val isPopular: Boolean = false
)

data class PurchasedTemplateItem(
    val orderId: String,
    val templateId: String,
    val titleAr: String,
    val titleEn: String,
    val priceEgp: Int,
    val purchaseDate: String,
    val paymentMethod: String,
    val template: DesignTemplate
)

data class DiscountCoupon(
    val code: String,
    val discountPercent: Int,
    val description: String,
    val expirationDate: String = "",
    val usageLimit: Int = 100,
    val usageCount: Int = 0,
    val isActive: Boolean = true
)

data class PaymentMethodConfig(
    val id: String,
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

data class FeatureAvailability(
    val featureKey: String,
    val titleAr: String,
    val titleEn: String,
    val status: String = "AVAILABLE", // "AVAILABLE", "UNAVAILABLE", "DISABLED", "COMING_SOON"
    val messageAr: String = "غير متوفر حاليًا – Currently Unavailable",
    val messageEn: String = "Currently Unavailable"
)

data class BrandIdentityResult(
    val brandName: String,
    val industry: String,
    val sloganAr: String,
    val sloganEn: String,
    val primaryColorHex: String,
    val secondaryColorHex: String,
    val accentColorHex: String,
    val darkBaseHex: String,
    val lightSurfaceHex: String,
    val fontHeading: String,
    val fontBody: String,
    val brandVoiceAr: String,
    val brandVoiceEn: String,
    val logoConceptAr: String,
    val logoConceptEn: String
)

data class MarketplaceAsset(
    val id: String,
    val titleAr: String,
    val titleEn: String,
    val creatorName: String,
    val category: String,
    val isFree: Boolean,
    val priceDisplay: String,
    val rating: Float,
    val downloadsCount: Int,
    val bgStart: String,
    val bgEnd: String,
    val template: DesignTemplate
)

data class ManualPayment(
    val id: String = UUID.randomUUID().toString(),
    val userEmail: String,
    val planName: String,
    val amountFormatted: String,
    val paymentMethod: String,
    val transactionRef: String,
    val dateString: String,
    val status: String = "PENDING", // PENDING, APPROVED, REJECTED
    val receiptImageUri: String? = null,
    val notes: String = ""
)

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val isUser: Boolean,
    val text: String,
    val timestamp: String,
    val suggestedAction: String? = null
)

data class UserProfile(
    val id: String = "guest",
    val name: String = "زائر برايا",
    val email: String = "guest@baraya.ai",
    val phone: String = "+20 100 000 0000",
    val bio: String = "مستخدم زائر - استكشف وصمم بحرية عبر استوديو برايا",
    val isVip: Boolean = false,
    val isGuest: Boolean = true,
    val role: String = "USER", // "OWNER" or "USER"
    val avatarIndex: Int = 0,
    val memberSince: String = "2026"
)

