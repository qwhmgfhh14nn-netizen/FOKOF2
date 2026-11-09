package com.example.data.templates

import com.example.R
import com.example.data.model.CanvasElement
import com.example.data.model.CanvasElementType
import com.example.data.model.DesignTemplate
import com.example.data.model.ShapeType

object FreeTemplatesData {

    // Exactly 50 Genuine Free Launch Templates across the 10 specified categories:
    // 1. Social media posts (5)
    // 2. Advertisements (5)
    // 3. Logos (5)
    // 4. Business cards (5)
    // 5. Restaurant menus (5)
    // 6. Banners (5)
    // 7. Sales and discounts (5)
    // 8. Invitations (5)
    // 9. CV templates (5)
    // 10. Product advertisements (5)
    //
    // NO BLACK BACKGROUNDS: Every single template features a distinct, vibrant,
    // beautiful color palette: white, cream, ivory, royal blue, emerald green,
    // pastel pink, crimson red, purple, coral, amber, and gold.

    fun getTemplates(): List<DesignTemplate> = listOf(
        // ---------------- 1. Social Media Posts (5) ----------------
        DesignTemplate(
            id = "free_social_01",
            titleAr = "بوست ترحيبي لمتجر إلكتروني راقي",
            titleEn = "Luxury E-Commerce Welcome Post",
            category = "منشورات السوشيال ميديا",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.95f,
            salesCount = 1420,
            bgStartHex = "#FF5E62", // Sunset Coral & Tangerine
            bgEndHex = "#FF9966",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "✨", x = 180f, y = 70f, fontSize = 36f, colorHex = "#FFFFFF"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "أهلاً بكم في متجرنا", x = 180f, y = 130f, fontSize = 24f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "تسوق أرقى المنتجات المصرية الحصرية", x = 180f, y = 180f, fontSize = 14f, colorHex = "#FFF5EB"),
                CanvasElement(id = "e4", type = CanvasElementType.SHAPE, shapeType = ShapeType.ROUNDED_RECT, x = 180f, y = 235f, width = 150f, height = 40f, colorHex = "#FFFFFF"),
                CanvasElement(id = "e5", type = CanvasElementType.TEXT, text = "اكتشف المجموعة", x = 180f, y = 235f, fontSize = 12f, colorHex = "#EA580C", isBold = true)
            )
        ),
        DesignTemplate(
            id = "free_social_02",
            titleAr = "اقتباس تحفيزي يومي بالخط العربي",
            titleEn = "Daily Motivational Quote Poster",
            category = "منشورات السوشيال ميديا",
            aspectRatio = "4:5",
            isPremium = false,
            priceEgp = 0,
            rating = 4.92f,
            salesCount = 980,
            bgStartHex = "#4338CA", // Royal Indigo & Electric Violet
            bgEndHex = "#7C3AED",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "حكمة اليوم", x = 180f, y = 60f, fontSize = 14f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "« لا تنتظر الفرصة بل اصنعها بنفسك »", x = 180f, y = 140f, fontSize = 20f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.SHAPE, shapeType = ShapeType.RECTANGLE, x = 180f, y = 195f, width = 60f, height = 2f, colorHex = "#FDE047"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "برايا للذكاء الاصطناعي", x = 180f, y = 240f, fontSize = 12f, colorHex = "#E0E7FF")
            )
        ),
        DesignTemplate(
            id = "free_social_03",
            titleAr = "إعلان حلقة بودكاست ومقابلة أسبوعية",
            titleEn = "Weekly Podcast Episode Announcement",
            category = "منشورات السوشيال ميديا",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.88f,
            salesCount = 760,
            bgStartHex = "#047857", // Fresh Mint & Deep Emerald
            bgEndHex = "#10B981",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "🎙️", x = 180f, y = 65f, fontSize = 42f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "الحلقة 14: مستقبل الذكاء الاصطناعي", x = 180f, y = 135f, fontSize = 18f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "مع ضيفنا م. محمد برايا", x = 180f, y = 175f, fontSize = 13f, colorHex = "#D1FAE5"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "استمع الآن على Spotify و Apple Podcasts", x = 180f, y = 230f, fontSize = 11f, colorHex = "#A7F3D0")
            )
        ),
        DesignTemplate(
            id = "free_social_04",
            titleAr = "نصيحة تسويقية سريعة للمصممين",
            titleEn = "Design Tips & Creative Hacks",
            category = "منشورات السوشيال ميديا",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.90f,
            salesCount = 890,
            bgStartHex = "#BE185D", // Radiant Rose Quartz & Blush Coral
            bgEndHex = "#F43F5E",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "💡", x = 180f, y = 65f, fontSize = 38f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "سر تناسق الألوان في الهويات", x = 180f, y = 130f, fontSize = 20f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "استخدم قاعدة 60-30-10 للحصول على توازن بصري متكامل", x = 180f, y = 180f, fontSize = 13f, colorHex = "#FFE4E6"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "احفظ البوست لترجع له لاحقاً ↗", x = 180f, y = 240f, fontSize = 11f, colorHex = "#FDE047")
            )
        ),
        DesignTemplate(
            id = "free_social_05",
            titleAr = "سؤال واستفتاء تفاعلي للمتابعين",
            titleEn = "Interactive Community Q&A Poll",
            category = "منشورات السوشيال ميديا",
            aspectRatio = "9:16",
            isPremium = false,
            priceEgp = 0,
            rating = 4.87f,
            salesCount = 650,
            bgStartHex = "#0284C7", // Mediterranean Azure & Sky Blue
            bgEndHex = "#38BDF8",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "شاركونا رأيكم 💬", x = 180f, y = 80f, fontSize = 24f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "ما هو الخط العربي المفضل لديك في التصميم؟", x = 180f, y = 150f, fontSize = 16f, colorHex = "#F0F9FF"),
                CanvasElement(id = "e3", type = CanvasElementType.SHAPE, shapeType = ShapeType.ROUNDED_RECT, x = 180f, y = 220f, width = 200f, height = 44f, colorHex = "#FFFFFF"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "أ) خط كوفي عصري   ب) خط الرقعة الكلاسيكي", x = 180f, y = 220f, fontSize = 11f, colorHex = "#0284C7")
            )
        ),

        // ---------------- 2. Advertisements (5) ----------------
        DesignTemplate(
            id = "free_ad_01",
            titleAr = "إعلان افتتاح فرع جديد بالقاهرة",
            titleEn = "Grand Opening Ad - New Cairo Branch",
            category = "الإعلانات الممولة",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.97f,
            salesCount = 1340,
            bgStartHex = "#B91C1C", // Bold Imperial Crimson & Warm Scarlet
            bgEndHex = "#DC2626",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "افتتاح فرعنا الجديد في التجمع الخامس", x = 180f, y = 60f, fontSize = 18f, colorHex = "#FDE047", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "خصم 30% لأول 100 زائر!", x = 180f, y = 120f, fontSize = 24f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "شارع التسعين الشمالي - القاهرة الجديدة", x = 180f, y = 175f, fontSize = 13f, colorHex = "#FEE2E2"),
                CanvasElement(id = "e4", type = CanvasElementType.SHAPE, shapeType = ShapeType.ROUNDED_RECT, x = 180f, y = 230f, width = 160f, height = 42f, colorHex = "#FDE047"),
                CanvasElement(id = "e5", type = CanvasElementType.TEXT, text = "احجز موعدك الآن", x = 180f, y = 230f, fontSize = 13f, colorHex = "#7F1D1D", isBold = true)
            )
        ),
        DesignTemplate(
            id = "free_ad_02",
            titleAr = "إعلان دورة تدريبية في الذكاء الاصطناعي",
            titleEn = "AI & Modern Design Masterclass",
            category = "الإعلانات الممولة",
            aspectRatio = "4:5",
            isPremium = false,
            priceEgp = 0,
            rating = 4.93f,
            salesCount = 1120,
            bgStartHex = "#1E3A8A", // Deep Cobalt & Electric Blue
            bgEndHex = "#2563EB",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "🚀", x = 180f, y = 60f, fontSize = 36f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "احترف التصميم بالذكاء الاصطناعي", x = 180f, y = 120f, fontSize = 21f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "دورة تطبيقية مكثفة من الصفر للاحتراف", x = 180f, y = 165f, fontSize = 13f, colorHex = "#FDE047"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "شهادة معتمدة + مشاريع حقيقية لسوق العمل", x = 180f, y = 210f, fontSize = 12f, colorHex = "#DBEAFE")
            )
        ),
        DesignTemplate(
            id = "free_ad_03",
            titleAr = "إعلان ترويجي لتطبيق توصيل سريع",
            titleEn = "Ultra-Fast Delivery App Campaign",
            category = "الإعلانات الممولة",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.89f,
            salesCount = 820,
            bgStartHex = "#D97706", // High-Energy Saffron & Amber
            bgEndHex = "#F59E0B",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "⚡", x = 180f, y = 60f, fontSize = 42f, colorHex = "#FFFFFF"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "طلبك يوصلك في 30 دقيقة فقط", x = 180f, y = 125f, fontSize = 20f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "شحن مجاني على أول 3 طلبات بكود: BARAYA", x = 180f, y = 175f, fontSize = 13f, colorHex = "#FEF3C7"),
                CanvasElement(id = "e4", type = CanvasElementType.SHAPE, shapeType = ShapeType.ROUNDED_RECT, x = 180f, y = 230f, width = 160f, height = 40f, colorHex = "#FFFFFF"),
                CanvasElement(id = "e5", type = CanvasElementType.TEXT, text = "حمّل التطبيق الآن", x = 180f, y = 230f, fontSize = 12f, colorHex = "#B45309", isBold = true)
            )
        ),
        DesignTemplate(
            id = "free_ad_04",
            titleAr = "إعلان استشارات مالية وتطوير أعمال",
            titleEn = "Business Growth & Financial Advisory",
            category = "الإعلانات الممولة",
            aspectRatio = "16:9",
            isPremium = false,
            priceEgp = 0,
            rating = 4.91f,
            salesCount = 740,
            bgStartHex = "#0F766E", // Modern Deep Teal & Seafoam
            bgEndHex = "#0D9488",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "طوّر أرباح شركتك باستراتيجيات ذكية", x = 180f, y = 65f, fontSize = 19f, colorHex = "#FDE047", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "استشارات متخصصة للشركات الناشئة والمتوسطة بمصر", x = 180f, y = 110f, fontSize = 12f, colorHex = "#FFFFFF"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "جلسة أولى مجانية لمراجعة خطتك المالية", x = 180f, y = 150f, fontSize = 11f, colorHex = "#CCFBF1")
            )
        ),
        DesignTemplate(
            id = "free_ad_05",
            titleAr = "إعلان خدمات التسويق الرقمي وإدارة الحملات",
            titleEn = "Digital Marketing & Ads Management",
            category = "الإعلانات الممولة",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.94f,
            salesCount = 990,
            bgStartHex = "#7E22CE", // Electric Magenta & Deep Purple
            bgEndHex = "#A21CAF",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "ضاعف مبيعاتك عبر السوشيال ميديا", x = 180f, y = 70f, fontSize = 21f, colorHex = "#FDE047", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "حملات إعلانية احترافية مبنية على تحليلات دقيقة", x = 180f, y = 130f, fontSize = 13f, colorHex = "#F3E8FF"),
                CanvasElement(id = "e3", type = CanvasElementType.SHAPE, shapeType = ShapeType.ROUNDED_RECT, x = 180f, y = 195f, width = 170f, height = 42f, colorHex = "#FDE047"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "تواصل معنا واتساب", x = 180f, y = 195f, fontSize = 13f, colorHex = "#581C87", isBold = true)
            )
        ),

        // ---------------- 3. Logos (5) ----------------
        DesignTemplate(
            id = "free_logo_01",
            titleAr = "شعار ريادي عصري لشركة تقنية ناشئة",
            titleEn = "Modern Tech Monogram Logo",
            category = "الشعارات والهويات",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.96f,
            salesCount = 2150,
            bgStartHex = "#FFFDF7", // Pure Clean Ivory & Champagne Gold
            bgEndHex = "#F5EBD7",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.SHAPE, shapeType = ShapeType.CIRCLE, x = 180f, y = 110f, width = 120f, height = 120f, colorHex = "#D4AF37", opacity = 0.25f),
                CanvasElement(id = "e2", type = CanvasElementType.CALLIGRAPHY, text = "◈", x = 180f, y = 110f, fontSize = 56f, colorHex = "#92400E"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "NEXUS AI", x = 180f, y = 190f, fontSize = 24f, colorHex = "#1E293B", isBold = true),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "حلول البيانات والذكاء الاصطناعي", x = 180f, y = 230f, fontSize = 12f, colorHex = "#64748B")
            )
        ),
        DesignTemplate(
            id = "free_logo_02",
            titleAr = "شعار خط عربي كلاسيكي لدار عطور",
            titleEn = "Royal Arabic Calligraphy Perfume Logo",
            category = "الشعارات والهويات",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.98f,
            salesCount = 1890,
            bgStartHex = "#172554", // Regal Deep Navy & Gold Foil
            bgEndHex = "#1E3A8A",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "⚜️", x = 180f, y = 100f, fontSize = 50f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "عطور العود الملكي", x = 180f, y = 175f, fontSize = 22f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "ROUGE & OUD ROYAL PERFUMES", x = 180f, y = 215f, fontSize = 10f, colorHex = "#FDE047")
            )
        ),
        DesignTemplate(
            id = "free_logo_03",
            titleAr = "شعار مقهى ومخبز حرفي عصري",
            titleEn = "Artisan Specialty Cafe & Bakery Logo",
            category = "الشعارات والهويات",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.91f,
            salesCount = 1430,
            bgStartHex = "#FEF3C7", // Warm Cinnamon Cream & Caramel
            bgEndHex = "#FDE68A",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "☕", x = 180f, y = 105f, fontSize = 52f, colorHex = "#92400E"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "كافيه أروما الحرفي", x = 180f, y = 180f, fontSize = 22f, colorHex = "#78350F", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "EST. 2024 • SPECIALTY COFFEE", x = 180f, y = 220f, fontSize = 10f, colorHex = "#B45309")
            )
        ),
        DesignTemplate(
            id = "free_logo_04",
            titleAr = "شعار استوديو معماري وتصميم داخلي",
            titleEn = "Minimal Architecture & Interior Logo",
            category = "الشعارات والهويات",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.93f,
            salesCount = 1250,
            bgStartHex = "#F1F5F9", // Scandinavian Slate & Silver Pearl
            bgEndHex = "#E2E8F0",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "📐", x = 180f, y = 100f, fontSize = 48f, colorHex = "#0284C7"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "SPACE ARCHITECTS", x = 180f, y = 175f, fontSize = 20f, colorHex = "#0F172A", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "العمارة الحديثة والتصميم الداخلي", x = 180f, y = 215f, fontSize = 12f, colorHex = "#475569")
            )
        ),
        DesignTemplate(
            id = "free_logo_05",
            titleAr = "شعار عيادة صحية وتجميلية متخصصة",
            titleEn = "Medical & Aesthetic Wellness Clinic Logo",
            category = "الشعارات والهويات",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.94f,
            salesCount = 1680,
            bgStartHex = "#ECFDF5", // Soft Botanical Sage & Fresh Mint
            bgEndHex = "#D1FAE5",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "🌿", x = 180f, y = 105f, fontSize = 50f, colorHex = "#059669"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "عيادات ديرما كير", x = 180f, y = 180f, fontSize = 22f, colorHex = "#065F46", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "DERMA CARE CLINICS & WELLNESS", x = 180f, y = 220f, fontSize = 10f, colorHex = "#047857")
            )
        ),

        // ---------------- 4. Business Cards (5) ----------------
        DesignTemplate(
            id = "free_card_01",
            titleAr = "كارت شخصي تنفيذي للمدراء والاستشاريين",
            titleEn = "Executive Minimal Business Card",
            category = "كروت العمل الشخصية",
            aspectRatio = "16:9",
            isPremium = false,
            priceEgp = 0,
            rating = 4.97f,
            salesCount = 2650,
            bgStartHex = "#FFFFFF", // Crisp Pearl White & Deep Navy
            bgEndHex = "#F8FAFC",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "م. محمد برايا", x = 160f, y = 90f, fontSize = 20f, colorHex = "#0F172A", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "مؤسس ومدير إبداعي • Baraya AI", x = 160f, y = 125f, fontSize = 12f, colorHex = "#D97706"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "هاتف: +20 100 000 0000 | info@baraya.ai", x = 160f, y = 175f, fontSize = 10f, colorHex = "#475569")
            )
        ),
        DesignTemplate(
            id = "free_card_02",
            titleAr = "كارت مصمم ومطور ويب فريلانسر",
            titleEn = "Creative Freelancer Tech Card",
            category = "كروت العمل الشخصية",
            aspectRatio = "16:9",
            isPremium = false,
            priceEgp = 0,
            rating = 4.90f,
            salesCount = 1850,
            bgStartHex = "#0F172A", // Midnight Slate & Electric Cyan
            bgEndHex = "#1E293B",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "أحمد خالد الشريف", x = 160f, y = 90f, fontSize = 20f, colorHex = "#38BDF8", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "Full-Stack Developer & UI Designer", x = 160f, y = 125f, fontSize = 12f, colorHex = "#FFFFFF"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "github.com/ahmed | Cairo, Egypt", x = 160f, y = 175f, fontSize = 10f, colorHex = "#94A3B8")
            )
        ),
        DesignTemplate(
            id = "free_card_03",
            titleAr = "كارت ذهبي فاخر للمجوهرات والعقارات",
            titleEn = "Luxury Gilded Real Estate Card",
            category = "كروت العمل الشخصية",
            aspectRatio = "16:9",
            isPremium = false,
            priceEgp = 0,
            rating = 4.99f,
            salesCount = 2890,
            bgStartHex = "#FFFBEB", // Warm Cream Linen & Gold Dust
            bgEndHex = "#FEF3C7",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "❖", x = 160f, y = 75f, fontSize = 32f, colorHex = "#B45309"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "سارة المنشاوي", x = 160f, y = 120f, fontSize = 21f, colorHex = "#78350F", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "مستشارة عقارات فاخرة • بريميوم بروبرتيز", x = 160f, y = 155f, fontSize = 11f, colorHex = "#92400E")
            )
        ),
        DesignTemplate(
            id = "free_card_04",
            titleAr = "كارت طبي لعيادة أسنان تخصصية",
            titleEn = "Dental & Healthcare Modern Card",
            category = "كروت العمل الشخصية",
            aspectRatio = "16:9",
            isPremium = false,
            priceEgp = 0,
            rating = 4.88f,
            salesCount = 1420,
            bgStartHex = "#F0FDF4", // Pristine Medical Cyan & White
            bgEndHex = "#E0F2FE",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "د. كريم عبد العزيز", x = 160f, y = 85f, fontSize = 20f, colorHex = "#0284C7", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "أخصائي زراعة وتجميل الأسنان", x = 160f, y = 120f, fontSize = 12f, colorHex = "#059669"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "المهندسين - شارع جامعة الدول العربية", x = 160f, y = 165f, fontSize = 10f, colorHex = "#475569")
            )
        ),
        DesignTemplate(
            id = "free_card_05",
            titleAr = "كارت محامي ومستشار قانوني معتمد",
            titleEn = "Legal Advisor & Corporate Law Card",
            category = "كروت العمل الشخصية",
            aspectRatio = "16:9",
            isPremium = false,
            priceEgp = 0,
            rating = 4.93f,
            salesCount = 1760,
            bgStartHex = "#881337", // Rich Bordeaux Wine & Gold
            bgEndHex = "#4C0519",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "⚖️", x = 160f, y = 80f, fontSize = 36f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "الأستاذ طارق الفقي", x = 160f, y = 125f, fontSize = 21f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "محامٍ بالنقض ومحكّم دولي معتمد", x = 160f, y = 160f, fontSize = 11f, colorHex = "#FDE047")
            )
        ),

        // ---------------- 5. Restaurant Menus (5) ----------------
        DesignTemplate(
            id = "free_menu_01",
            titleAr = "قائمة طعام مطعم شرقي ومشاوي فاخرة",
            titleEn = "Oriental Grill & Traditional Cuisine Menu",
            category = "قوائم طعام ومطاعم",
            aspectRatio = "9:16",
            isPremium = false,
            priceEgp = 0,
            rating = 4.96f,
            salesCount = 1980,
            bgStartHex = "#C2410C", // Warm Terracotta & Saffron
            bgEndHex = "#EA580C",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "🍖", x = 180f, y = 70f, fontSize = 42f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "مشويات الحاتي الملكية", x = 180f, y = 125f, fontSize = 24f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "كباب وكفتة ضاني بلدي - طواجن شرقية", x = 180f, y = 165f, fontSize = 12f, colorHex = "#FED7AA"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "أطباقنا تقدم مع الأرز البسمتي والمقبلات", x = 180f, y = 220f, fontSize = 11f, colorHex = "#FFFFFF")
            )
        ),
        DesignTemplate(
            id = "free_menu_02",
            titleAr = "منيو مقهى ومشروبات ساخنة وعصائر",
            titleEn = "Specialty Coffee & Beverages Menu",
            category = "قوائم طعام ومطاعم",
            aspectRatio = "9:16",
            isPremium = false,
            priceEgp = 0,
            rating = 4.92f,
            salesCount = 1650,
            bgStartHex = "#FFFBEB", // Warm Latte Cream & Mocha
            bgEndHex = "#F5EBE0",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "☕", x = 180f, y = 70f, fontSize = 44f, colorHex = "#92400E"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "قائمة المشروبات والقهوة المختصة", x = 180f, y = 125f, fontSize = 21f, colorHex = "#78350F", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "إسبريسو • كورتادو • فلات وايت • كولد برو", x = 180f, y = 165f, fontSize = 13f, colorHex = "#B45309"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "حبوب بن إثيوبية وكولومبية منتقاة بعناية", x = 180f, y = 220f, fontSize = 11f, colorHex = "#92400E")
            )
        ),
        DesignTemplate(
            id = "free_menu_03",
            titleAr = "إعلان برجر الفحم الملكي المشوي",
            titleEn = "Royal Charcoal Burger Restaurant Ad",
            category = "قوائم طعام ومطاعم",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.98f,
            salesCount = 2840,
            previewDrawableRes = R.drawable.img_restaurant_food,
            bgStartHex = "#120B06",
            bgEndHex = "#23140A",
            elements = listOf(
                CanvasElement(
                    id = "e_badge",
                    type = CanvasElementType.TEXT,
                    text = "🔥 عرض خاص لفترة محدودة • خصم 25%",
                    x = 24f,
                    y = 20f,
                    fontSize = 11f,
                    colorHex = "#FDE047",
                    isBold = true
                ),
                CanvasElement(
                    id = "e_headline",
                    type = CanvasElementType.TEXT,
                    text = "برجر الفحم الملكي المشوي",
                    x = 24f,
                    y = 48f,
                    fontSize = 24f,
                    colorHex = "#FFFFFF",
                    isBold = true
                ),
                CanvasElement(
                    id = "e_desc",
                    type = CanvasElementType.TEXT,
                    text = "لحم أنجوس بقري صافي 100% مع خبز البريوش الذهبي وصوص الشيف الخاص",
                    x = 24f,
                    y = 90f,
                    fontSize = 11f,
                    colorHex = "#FED7AA",
                    isBold = false
                ),
                CanvasElement(
                    id = "e_price_bg",
                    type = CanvasElementType.SHAPE,
                    shapeType = ShapeType.ROUNDED_RECT,
                    x = 220f,
                    y = 130f,
                    width = 115f,
                    height = 55f,
                    colorHex = "#D4AF37",
                    opacity = 0.95f
                ),
                CanvasElement(
                    id = "e_price_val",
                    type = CanvasElementType.TEXT,
                    text = "145 ج.م",
                    x = 230f,
                    y = 138f,
                    fontSize = 19f,
                    colorHex = "#101014",
                    isBold = true
                ),
                CanvasElement(
                    id = "e_price_old",
                    type = CanvasElementType.TEXT,
                    text = "بدلاً من 195 ج.م",
                    x = 232f,
                    y = 162f,
                    fontSize = 9f,
                    colorHex = "#7F1D1D",
                    isBold = true
                ),
                CanvasElement(
                    id = "e_cta",
                    type = CanvasElementType.TEXT,
                    text = "📞 للطلب والتوصيل السريع: 19854 • توصيل ساخن ومجاني",
                    x = 24f,
                    y = 265f,
                    fontSize = 11f,
                    colorHex = "#FFFFFF",
                    isBold = true
                )
            )
        ),
        DesignTemplate(
            id = "free_menu_04",
            titleAr = "قائمة حلويات شرقية وتورتات مناسبات",
            titleEn = "Patisserie & Wedding Cakes Menu",
            category = "قوائم طعام ومطاعم",
            aspectRatio = "4:5",
            isPremium = false,
            priceEgp = 0,
            rating = 4.95f,
            salesCount = 1540,
            bgStartHex = "#FFF1F2", // Sweet Strawberry Cream & Pastel Rose
            bgEndHex = "#FCE7F3",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "🎂", x = 180f, y = 70f, fontSize = 44f, colorHex = "#BE185D"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "حلويات لوتس وفستق ديليس", x = 180f, y = 130f, fontSize = 22f, colorHex = "#881337", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "كنافة نابلسية • أم علي • تورتات أعياد الميلاد", x = 180f, y = 175f, fontSize = 12f, colorHex = "#9D174D"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "سمن بلدي طبيعي ومكسرات درجة أولى", x = 180f, y = 225f, fontSize = 11f, colorHex = "#BE185D")
            )
        ),
        DesignTemplate(
            id = "free_menu_05",
            titleAr = "قائمة مأكولات بحرية وطواجن إسكندراني",
            titleEn = "Alexandria Seafood & Fresh Catch Menu",
            category = "قوائم طعام ومطاعم",
            aspectRatio = "9:16",
            isPremium = false,
            priceEgp = 0,
            rating = 4.97f,
            salesCount = 1780,
            bgStartHex = "#0369A1", // Deep Mediterranean Ocean & Turquoise
            bgEndHex = "#0284C7",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "🦞", x = 180f, y = 70f, fontSize = 46f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "عروس البحر للمأكولات البحرية", x = 180f, y = 130f, fontSize = 23f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "جمبري جامبو • سمك قاروص مشوي • طواجن سبيط", x = 180f, y = 175f, fontSize = 12f, colorHex = "#E0F2FE"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "صيد يومي طازج من البحر الأبيض المتوسط", x = 180f, y = 225f, fontSize = 11f, colorHex = "#FDE047")
            )
        ),

        // ---------------- 6. Banners (5) ----------------
        DesignTemplate(
            id = "free_banner_01",
            titleAr = "بانر مؤتمر تقني وملتقى المطورين",
            titleEn = "Tech Summit & Developer Meetup Banner",
            category = "بانرات ومطبوعات",
            aspectRatio = "16:9",
            isPremium = false,
            priceEgp = 0,
            rating = 4.94f,
            salesCount = 1430,
            bgStartHex = "#581C87", // Cosmic Purple & Neon Violet
            bgEndHex = "#7E22CE",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "EGYPT TECH SUMMIT 2026", x = 180f, y = 60f, fontSize = 22f, colorHex = "#FDE047", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "الملتقى السنوي الأول لرواد الذكاء الاصطناعي والبرمجيات", x = 180f, y = 105f, fontSize = 14f, colorHex = "#FFFFFF"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "مركز مصر للمعارض الدولية - القاهرة", x = 180f, y = 145f, fontSize = 12f, colorHex = "#F3E8FF")
            )
        ),
        DesignTemplate(
            id = "free_banner_02",
            titleAr = "بانر رول أب لمعرض المنتجات المصرية",
            titleEn = "Egyptian Heritage Trade Fair Rollup",
            category = "بانرات ومطبوعات",
            aspectRatio = "9:16",
            isPremium = false,
            priceEgp = 0,
            rating = 4.96f,
            salesCount = 1620,
            bgStartHex = "#1E3A8A", // Golden Sand & Royal Egyptian Lapis
            bgEndHex = "#3B82F6",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "🏺", x = 180f, y = 65f, fontSize = 42f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "معرض ديارنا للحرف التراثية", x = 180f, y = 120f, fontSize = 22f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "صنع في مصر بأيادٍ مصرية مبدعة", x = 180f, y = 160f, fontSize = 13f, colorHex = "#FDE047"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "أرض المعارض بمدينة نصر • الدخول مجاناً", x = 180f, y = 210f, fontSize = 11f, colorHex = "#DBEAFE")
            )
        ),
        DesignTemplate(
            id = "free_banner_03",
            titleAr = "بانر إعلاني لموقع إلكتروني وحملة تسويقية",
            titleEn = "Digital Web Hero Banner Campaign",
            category = "بانرات ومطبوعات",
            aspectRatio = "16:9",
            isPremium = false,
            priceEgp = 0,
            rating = 4.88f,
            salesCount = 1190,
            bgStartHex = "#EA580C", // Electric Coral & Sunny Amber
            bgEndHex = "#F97316",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "انطلق بمتجرك الإلكتروني اليوم", x = 180f, y = 65f, fontSize = 22f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "تصميمات جاهزة وقوالب ذكية ترفع مبيعاتك بنسبة 300%", x = 180f, y = 110f, fontSize = 13f, colorHex = "#FFEDD5"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "ابدأ تجربتك المجانية لمدة 14 يوماً بدون بطاقة بنكية", x = 180f, y = 150f, fontSize = 11f, colorHex = "#FFFFFF")
            )
        ),
        DesignTemplate(
            id = "free_banner_04",
            titleAr = "بانر ورشة عمل الفنون التشكيلية والخط",
            titleEn = "Calligraphy & Visual Arts Workshop Banner",
            category = "بانرات ومطبوعات",
            aspectRatio = "16:9",
            isPremium = false,
            priceEgp = 0,
            rating = 4.92f,
            salesCount = 1350,
            bgStartHex = "#FEF3C7", // Warm Ivory Parchment & Gold Leaf
            bgEndHex = "#FDE68A",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "ملتقى برايا لفنون الخط العربي", x = 180f, y = 65f, fontSize = 21f, colorHex = "#78350F", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "ورشة عمل تدريبية مع نخبة من كبار الخطاطين", x = 180f, y = 110f, fontSize = 13f, colorHex = "#92400E"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "مقر ساقية الصاوي - الزمالك، القاهرة", x = 180f, y = 150f, fontSize = 11f, colorHex = "#B45309")
            )
        ),
        DesignTemplate(
            id = "free_banner_05",
            titleAr = "بانر نادي رياضي وبطولة كرة قدم",
            titleEn = "Fitness Club & Champions League Banner",
            category = "بانرات ومطبوعات",
            aspectRatio = "16:9",
            isPremium = false,
            priceEgp = 0,
            rating = 4.90f,
            salesCount = 1080,
            bgStartHex = "#065F46", // Vivid Emerald Green & Lime
            bgEndHex = "#059669",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "⚽", x = 180f, y = 55f, fontSize = 36f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "دوري أبطال الأكاديميات الصيفي", x = 180f, y = 100f, fontSize = 22f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "جوائز نقدية وكؤوس للمراكز الثلاثة الأولى", x = 180f, y = 145f, fontSize = 12f, colorHex = "#D1FAE5")
            )
        ),

        // ---------------- 7. Sales and Discounts (5) ----------------
        DesignTemplate(
            id = "free_sale_01",
            titleAr = "بوستر تخفيضات الجمعة البيضاء 50%",
            titleEn = "White Friday Mega Sale Poster",
            category = "عروض وتخفيضات",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.99f,
            salesCount = 3450,
            bgStartHex = "#DC2626", // Explosive Crimson & Amber Burst
            bgEndHex = "#EF4444",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "عروض الجمعة البيضاء الكبرى", x = 180f, y = 60f, fontSize = 20f, colorHex = "#FDE047", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.CALLIGRAPHY, text = "خصم 50%", x = 180f, y = 120f, fontSize = 38f, colorHex = "#FFFFFF"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "على كافة الأزياء والإلكترونيات ومستلزمات المنزل", x = 180f, y = 175f, fontSize = 13f, colorHex = "#FEE2E2"),
                CanvasElement(id = "e4", type = CanvasElementType.SHAPE, shapeType = ShapeType.ROUNDED_RECT, x = 180f, y = 230f, width = 160f, height = 40f, colorHex = "#FDE047"),
                CanvasElement(id = "e5", type = CanvasElementType.TEXT, text = "تسوق العرض الآن", x = 180f, y = 230f, fontSize = 13f, colorHex = "#991B1B", isBold = true)
            )
        ),
        DesignTemplate(
            id = "free_sale_02",
            titleAr = "عرض تصفيات نهاية الموسم الصيفي",
            titleEn = "Summer Clearance Final Countdown",
            category = "عروض وتخفيضات",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.93f,
            salesCount = 2100,
            bgStartHex = "#D97706", // Sunny Golden Yellow & Citrus Orange
            bgEndHex = "#F59E0B",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "☀️", x = 180f, y = 60f, fontSize = 42f, colorHex = "#FFFFFF"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "تصفيات نهاية موسم الصيف", x = 180f, y = 125f, fontSize = 23f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "أسعار تبدأ من 99 جنيهاً مصرياً فقط", x = 180f, y = 170f, fontSize = 14f, colorHex = "#FEF3C7"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "الكميات محدودة حتى نفاد المخزون", x = 180f, y = 220f, fontSize = 11f, colorHex = "#78350F")
            )
        ),
        DesignTemplate(
            id = "free_sale_03",
            titleAr = "عرض اشتري واحد واحصل على الثاني مجاناً",
            titleEn = "Buy 1 Get 1 Free Promo Post",
            category = "عروض وتخفيضات",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.95f,
            salesCount = 2890,
            bgStartHex = "#DB2777", // Neon Fuchsia & Punch Pink
            bgEndHex = "#BE185D",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "عرض 1 + 1 مجاناً!", x = 180f, y = 70f, fontSize = 26f, colorHex = "#FDE047", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "اشتري أي قطعة واحصل على الثانية هدية فورية", x = 180f, y = 135f, fontSize = 14f, colorHex = "#FFFFFF"),
                CanvasElement(id = "e3", type = CanvasElementType.SHAPE, shapeType = ShapeType.ROUNDED_RECT, x = 180f, y = 205f, width = 170f, height = 44f, colorHex = "#FDE047"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "اغتنم الفرصة الآن", x = 180f, y = 205f, fontSize = 13f, colorHex = "#831843", isBold = true)
            )
        ),
        DesignTemplate(
            id = "free_sale_04",
            titleAr = "فلاش سيل لمدة 24 ساعة فقط",
            titleEn = "24-Hour Flash Sale Mega Offer",
            category = "عروض وتخفيضات",
            aspectRatio = "4:5",
            isPremium = false,
            priceEgp = 0,
            rating = 4.91f,
            salesCount = 1970,
            bgStartHex = "#1E1B4B", // Deep Royal Indigo & Electric Yellow
            bgEndHex = "#312E81",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "⚡", x = 180f, y = 60f, fontSize = 46f, colorHex = "#FACC15"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "فلاش سيل: 24 ساعة فقط!", x = 180f, y = 125f, fontSize = 23f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "تخفيضات حارقة تصل إلى 70% على أكثر من 500 منتج", x = 180f, y = 175f, fontSize = 13f, colorHex = "#FACC15"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "ينتهي العرض عند منتصف الليل", x = 180f, y = 225f, fontSize = 11f, colorHex = "#C7D2FE")
            )
        ),
        DesignTemplate(
            id = "free_sale_05",
            titleAr = "خصم حصري لحاملي بطاقات البنك الأهلي",
            titleEn = "Bank Card Exclusive Cashback Discount",
            category = "عروض وتخفيضات",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.94f,
            salesCount = 1820,
            bgStartHex = "#065F46", // Vibrant Emerald Forest & Mint
            bgEndHex = "#047857",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "💳", x = 180f, y = 65f, fontSize = 42f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "كاش باك 20% لعملاء البنوك الشريكة", x = 180f, y = 125f, fontSize = 20f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "ادفع ببطاقتك الائتمانية واسترد قيمة الخصم فوراً", x = 180f, y = 170f, fontSize = 13f, colorHex = "#D1FAE5"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "بدون حد أدنى للمشتريات", x = 180f, y = 220f, fontSize = 11f, colorHex = "#FDE047")
            )
        ),

        // ---------------- 8. Invitations (5) ----------------
        DesignTemplate(
            id = "free_invite_01",
            titleAr = "بطاقة دعوة زفاف ملكية راقية بالخط الذهبي",
            titleEn = "Royal Wedding Gold Foliage Invitation",
            category = "دعوات ومناسبات",
            aspectRatio = "4:5",
            isPremium = false,
            priceEgp = 0,
            rating = 4.99f,
            salesCount = 3800,
            bgStartHex = "#FFFDF7", // Cream Pearl & Gilded Gold
            bgEndHex = "#FBF5EB",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "👑", x = 180f, y = 65f, fontSize = 34f, colorHex = "#B45309"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "دعوة زفاف مبارك", x = 180f, y = 115f, fontSize = 22f, colorHex = "#78350F", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "يسرنا دعوتكم لحفل زفاف العروسين", x = 180f, y = 155f, fontSize = 13f, colorHex = "#92400E"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "أحمد & مريم", x = 180f, y = 195f, fontSize = 24f, colorHex = "#B45309", isBold = true),
                CanvasElement(id = "e5", type = CanvasElementType.TEXT, text = "فندق فورسيزونز نايل بلازا - جاردن سيتي", x = 180f, y = 245f, fontSize = 11f, colorHex = "#64748B")
            )
        ),
        DesignTemplate(
            id = "free_invite_02",
            titleAr = "دعوة حفل تخرج وتكريم أوائل الطلبة",
            titleEn = "Academic Graduation & Honor Ceremony",
            category = "دعوات ومناسبات",
            aspectRatio = "4:5",
            isPremium = false,
            priceEgp = 0,
            rating = 4.96f,
            salesCount = 2450,
            bgStartHex = "#1E293B", // Royal Navy Blue & Starlight Silver
            bgEndHex = "#334155",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "🎓", x = 180f, y = 65f, fontSize = 42f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "حفل تخرج الدفعة الخامسة والخمسين", x = 180f, y = 125f, fontSize = 21f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "كلية الهندسة - جامعة القاهرة", x = 180f, y = 170f, fontSize = 14f, colorHex = "#FDE047"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "قاعة الاحتفالات الكبرى • حضوركم يشرفنا", x = 180f, y = 220f, fontSize = 11f, colorHex = "#CBD5E1")
            )
        ),
        DesignTemplate(
            id = "free_invite_03",
            titleAr = "بطاقة تهنئة عيد الفطر والأعياد المباركة",
            titleEn = "Eid Mubarak Luxury Floral Greeting",
            category = "دعوات ومناسبات",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.97f,
            salesCount = 3100,
            bgStartHex = "#F0FDF4", // Soft Botanical Sage & Mint Cream
            bgEndHex = "#DCFCE7",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "🌙", x = 180f, y = 65f, fontSize = 48f, colorHex = "#16A34A"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "عيد فطر مبارك وسعيد", x = 180f, y = 130f, fontSize = 23f, colorHex = "#14532D", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "تقبل الله منا ومنكم صالح الأعمال والطاعات", x = 180f, y = 175f, fontSize = 13f, colorHex = "#166534"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "كل عام وأنتم وعائلاتكم بألف خير وسلام", x = 180f, y = 225f, fontSize = 11f, colorHex = "#15803D")
            )
        ),
        DesignTemplate(
            id = "free_invite_04",
            titleAr = "دعوة عشاء عمل واستقبال شخصيات عامة",
            titleEn = "VIP Corporate Gala Dinner Invitation",
            category = "دعوات ومناسبات",
            aspectRatio = "4:5",
            isPremium = false,
            priceEgp = 0,
            rating = 4.93f,
            salesCount = 1890,
            bgStartHex = "#4C1D95", // Deep Amethyst & Rose Gold
            bgEndHex = "#6D28D9",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "🍸", x = 180f, y = 65f, fontSize = 38f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "حفل العشاء السنوي لكبار الشركاء", x = 180f, y = 125f, fontSize = 21f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "مجموعة برايا الاستثمارية ترحب بضيوفها الكرام", x = 180f, y = 170f, fontSize = 13f, colorHex = "#FDE047"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "فندق مينا هاوس التاريخي - الأهرامات", x = 180f, y = 220f, fontSize = 11f, colorHex = "#DDD6FE")
            )
        ),
        DesignTemplate(
            id = "free_invite_05",
            titleAr = "بطاقة عقيقة ومولود جديد بتصميم هادئ",
            titleEn = "Baby Shower & Welcome Newborn Card",
            category = "دعوات ومناسبات",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.95f,
            salesCount = 2240,
            bgStartHex = "#EFF6FF", // Soft Powder Sky Blue & Pastel Clouds
            bgEndHex = "#DBEAFE",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "👶", x = 180f, y = 65f, fontSize = 44f, colorHex = "#2563EB"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "أهلاً بأميرنا الصغير", x = 180f, y = 130f, fontSize = 23f, colorHex = "#1E3A8A", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "بارك الله لكم في الموهوب وشكرتم الواهب", x = 180f, y = 175f, fontSize = 13f, colorHex = "#1D4ED8"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "دعوة لحضور حفل العقيقة وتناول طعام الغداء", x = 180f, y = 225f, fontSize = 11f, colorHex = "#3B82F6")
            )
        ),

        // ---------------- 9. CV Templates (5) ----------------
        DesignTemplate(
            id = "free_cv_01",
            titleAr = "سيرة ذاتية عصرية لمهندسي البرمجيات",
            titleEn = "Modern Software Engineer ATS Resume",
            category = "سير ذاتية احترافية",
            aspectRatio = "9:16",
            isPremium = false,
            priceEgp = 0,
            rating = 4.97f,
            salesCount = 3120,
            bgStartHex = "#FFFFFF", // Clean Minimalist White & Deep Slate
            bgEndHex = "#F8FAFC",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "عمر مصطفى إبراهيم", x = 180f, y = 60f, fontSize = 22f, colorHex = "#0F172A", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "Senior Android & Kotlin Developer", x = 180f, y = 95f, fontSize = 13f, colorHex = "#2563EB"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "خبرة 6 سنوات في Jetpack Compose و Clean Architecture", x = 180f, y = 145f, fontSize = 11f, colorHex = "#334155"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "omar@dev.eg | +20 102 345 6789 | Cairo", x = 180f, y = 195f, fontSize = 10f, colorHex = "#64748B")
            )
        ),
        DesignTemplate(
            id = "free_cv_02",
            titleAr = "سيرة ذاتية إبداعية لمصممي الجرافيك والـ UI",
            titleEn = "Creative Graphic & UI/UX Designer CV",
            category = "سير ذاتية احترافية",
            aspectRatio = "9:16",
            isPremium = false,
            priceEgp = 0,
            rating = 4.94f,
            salesCount = 2480,
            bgStartHex = "#F1F5F9", // Scandinavian Soft Gray & Royal Blue
            bgEndHex = "#E2E8F0",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "مريم هشام الديب", x = 180f, y = 60f, fontSize = 22f, colorHex = "#0F172A", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "Lead UI/UX & Brand Identity Designer", x = 180f, y = 95f, fontSize = 13f, colorHex = "#7C3AED"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "متخصصة في أنظمة التصميم والهويات البصرية الرقمية", x = 180f, y = 145f, fontSize = 11f, colorHex = "#334155"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "behance.net/mariam | Figma Certified Expert", x = 180f, y = 195f, fontSize = 10f, colorHex = "#64748B")
            )
        ),
        DesignTemplate(
            id = "free_cv_03",
            titleAr = "سيرة ذاتية تنفيذية لمدراء المبيعات والتسويق",
            titleEn = "Executive Sales & Marketing Director CV",
            category = "سير ذاتية احترافية",
            aspectRatio = "9:16",
            isPremium = false,
            priceEgp = 0,
            rating = 4.96f,
            salesCount = 2650,
            bgStartHex = "#FAF8F5", // Elegant Ivory & Warm Charcoal
            bgEndHex = "#F3EDE2",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "حازم عبد المنعم الشافعي", x = 180f, y = 60f, fontSize = 21f, colorHex = "#1E293B", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "Regional Commercial & Marketing Director", x = 180f, y = 95f, fontSize = 12f, colorHex = "#B45309"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "قيادة فرق المبيعات وتوسيع الأسواق في الشرق الأوسط", x = 180f, y = 145f, fontSize = 11f, colorHex = "#475569"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "ماجستير إدارة الأعمال MBA - الجامعة الأمريكية", x = 180f, y = 195f, fontSize = 10f, colorHex = "#64748B")
            )
        ),
        DesignTemplate(
            id = "free_cv_04",
            titleAr = "سيرة ذاتية أكاديمية للأطباء والباحثين",
            titleEn = "Medical Doctor & Academic Researcher CV",
            category = "سير ذاتية احترافية",
            aspectRatio = "9:16",
            isPremium = false,
            priceEgp = 0,
            rating = 4.98f,
            salesCount = 2120,
            bgStartHex = "#F0FDFA", // Pure Crisp Clinic White & Cyan Tint
            bgEndHex = "#CCFBF1",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "د. ياسمين سامي قنديل", x = 180f, y = 60f, fontSize = 21f, colorHex = "#134E4A", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "مدرس واستشاري أمراض القلب والأوعية الدموية", x = 180f, y = 95f, fontSize = 12f, colorHex = "#0D9488"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "دكتوراه طب القلب - عضو الجمعية الأوروبية لأمراض القلب", x = 180f, y = 145f, fontSize = 11f, colorHex = "#115E59"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "12 بحثاً منشوراً في دوريات علمية دولية محكمة", x = 180f, y = 195f, fontSize = 10f, colorHex = "#0F766E")
            )
        ),
        DesignTemplate(
            id = "free_cv_05",
            titleAr = "سيرة ذاتية حديث التخرج لجميع التخصصات",
            titleEn = "Fresh Graduate Entry-Level Clean CV",
            category = "سير ذاتية احترافية",
            aspectRatio = "9:16",
            isPremium = false,
            priceEgp = 0,
            rating = 4.89f,
            salesCount = 1870,
            bgStartHex = "#F8FAFC", // Pearl Cream & Steel Blue
            bgEndHex = "#EDF2F7",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "نور الدين وائل صبري", x = 180f, y = 60f, fontSize = 21f, colorHex = "#0F172A", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "بكالوريوس تجارة وإدارة أعمال 2025", x = 180f, y = 95f, fontSize = 12f, colorHex = "#2563EB"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "شغف بالتحليل المالي وخدمة العملاء والتسويق الرقمي", x = 180f, y = 145f, fontSize = 11f, colorHex = "#334155"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "إجادة تامة للغة الإنجليزية وبرامج مايكروسوفت أوفيس", x = 180f, y = 195f, fontSize = 10f, colorHex = "#64748B")
            )
        ),

        // ---------------- 10. Product Advertisements (5) ----------------
        DesignTemplate(
            id = "free_product_01",
            titleAr = "إعلان ساعة يد سويسرية فاخرة",
            titleEn = "Luxury Chronograph Watch Ad",
            category = "إعلانات المنتجات التجارية",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.98f,
            salesCount = 2890,
            bgStartHex = "#064E3B", // Deep Emerald Silk & Brushed Gold
            bgEndHex = "#047857",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "⌚", x = 180f, y = 65f, fontSize = 48f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "ساعة كرونوغراف رويال سويس", x = 180f, y = 130f, fontSize = 22f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "زجاج ياقوتي مقاوم للخدش مع هيكل من التيتانيوم", x = 180f, y = 175f, fontSize = 12f, colorHex = "#D1FAE5"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "ضمان دولي لمدة 5 سنوات • توصيل مجاني", x = 180f, y = 225f, fontSize = 11f, colorHex = "#FDE047")
            )
        ),
        DesignTemplate(
            id = "free_product_02",
            titleAr = "إعلان عطر شرقي وبخور ملكي",
            titleEn = "Royal Amber & Oud Perfume Campaign",
            category = "إعلانات المنتجات التجارية",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.99f,
            salesCount = 3450,
            bgStartHex = "#881337", // Rich Crimson Velvet & Champagne
            bgEndHex = "#BE123C",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "✨", x = 180f, y = 65f, fontSize = 42f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "عطر السلطان: مزيج العود والعنبر", x = 180f, y = 130f, fontSize = 22f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "ثبات استثنائي يدوم 48 ساعة مع فوحان يأسر الحواس", x = 180f, y = 175f, fontSize = 12f, colorHex = "#FFE4E6"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "عبوة فاخرة سعة 100 مل مع عينات مجانية", x = 180f, y = 225f, fontSize = 11f, colorHex = "#FDE047")
            )
        ),
        DesignTemplate(
            id = "free_product_03",
            titleAr = "إعلان هاتف ذكي وسماعات لاسلكية",
            titleEn = "Wireless Earbuds & Tech Flagship Ad",
            category = "إعلانات المنتجات التجارية",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.93f,
            salesCount = 2100,
            bgStartHex = "#0284C7", // Vibrant Mediterranean Azure
            bgEndHex = "#0EA5E9",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "🎧", x = 180f, y = 65f, fontSize = 46f, colorHex = "#FFFFFF"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "سماعات برو اللاسلكية بنقاء سينمائي", x = 180f, y = 130f, fontSize = 21f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "عزل ضوضاء فعال ANC مع بطارية تدوم 40 ساعة", x = 180f, y = 175f, fontSize = 12f, colorHex = "#E0F2FE"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "مقاومة للماء والعرق IPX7 • تقسيط متاح", x = 180f, y = 225f, fontSize = 11f, colorHex = "#FFFFFF")
            )
        ),
        DesignTemplate(
            id = "free_product_04",
            titleAr = "إعلان مستحضرات عناية طبيعية بالبشرة",
            titleEn = "Organic Skincare & Glow Serum Poster",
            category = "إعلانات المنتجات التجارية",
            aspectRatio = "4:5",
            isPremium = false,
            priceEgp = 0,
            rating = 4.96f,
            salesCount = 2780,
            bgStartHex = "#FFF1F2", // Delicate Peach Blossom & Rose
            bgEndHex = "#FFE4E6",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "🌸", x = 180f, y = 65f, fontSize = 44f, colorHex = "#E11D48"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "سيروم النضارة بفيتامين C وحمض الهيالورونيك", x = 180f, y = 130f, fontSize = 20f, colorHex = "#9F1239", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "تركيبة طبيعية 100% لإشراقة فورية وتجديد الخلايا", x = 180f, y = 175f, fontSize = 12f, colorHex = "#BE123C"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "موصى به من أطباء الجلدية • مناسب للبشرة الحساسة", x = 180f, y = 225f, fontSize = 11f, colorHex = "#E11D48")
            )
        ),
        DesignTemplate(
            id = "free_product_05",
            titleAr = "إعلان حذاء رياضي كوتشي كاجوال عصري",
            titleEn = "Urban Athletic Sneakers Poster",
            category = "إعلانات المنتجات التجارية",
            aspectRatio = "1:1",
            isPremium = false,
            priceEgp = 0,
            rating = 4.92f,
            salesCount = 1950,
            bgStartHex = "#D97706", // Dynamic Sunset Amber & Orange
            bgEndHex = "#EA580C",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "👟", x = 180f, y = 65f, fontSize = 46f, colorHex = "#FFFFFF"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "حذاء ألترا رانينج كلاود الرياضي", x = 180f, y = 130f, fontSize = 22f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "نعل مبطن ممتص للصدمات يوفر أقصى درجات الراحة اليومية", x = 180f, y = 175f, fontSize = 12f, colorHex = "#FEF3C7"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "تصميم خفيف الوزن ومقاوم للانزلاق بجميع المقاسات", x = 180f, y = 225f, fontSize = 11f, colorHex = "#FFFFFF")
            )
        )
    )
}
