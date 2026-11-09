package com.example.data.templates

import com.example.data.model.CanvasElement
import com.example.data.model.CanvasElementType
import com.example.data.model.DesignTemplate
import com.example.data.model.ProTier
import com.example.data.model.ShapeType

object ProTemplatesData {

    // PRO Library with individual tier pricing in Egyptian Pounds (EGP):
    // Basic: 5 EGP
    // Professional: 10 EGP
    // Premium: 15 EGP
    // Exclusive: 20 EGP
    //
    // NO BLACK BACKGROUNDS: Designed with vibrant, prestigious, colorful palettes.

    fun getTemplates(): List<DesignTemplate> = listOf(
        // ----------- BASIC TIER (5 EGP) -----------
        DesignTemplate(
            id = "pro_basic_01",
            titleAr = "شعار مونوغرام هندسي مينيمال",
            titleEn = "Geometric Minimal Monogram Logo",
            category = "الشعارات والهويات",
            aspectRatio = "1:1",
            isPremium = true,
            proTier = ProTier.BASIC,
            priceEgp = 5,
            rating = 4.95f,
            salesCount = 890,
            bgStartHex = "#FFFDF7", // Ivory Cream & Pure Gold
            bgEndHex = "#F5EBD7",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "❖", x = 180f, y = 90f, fontSize = 54f, colorHex = "#92400E"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "AURA STUDIO", x = 180f, y = 165f, fontSize = 24f, colorHex = "#1E293B", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "تصميم الهويات الرقمية المعاصرة", x = 180f, y = 210f, fontSize = 12f, colorHex = "#64748B")
            )
        ),
        DesignTemplate(
            id = "pro_basic_02",
            titleAr = "بوست سوشيال ميديا ترويجي بخصم ذهبي",
            titleEn = "Golden Glow Discount Social Post",
            category = "منشورات السوشيال ميديا",
            aspectRatio = "1:1",
            isPremium = true,
            proTier = ProTier.BASIC,
            priceEgp = 5,
            rating = 4.93f,
            salesCount = 1120,
            bgStartHex = "#EF4444", // Sunset Coral & Radiant Amber
            bgEndHex = "#F97316",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "⚡", x = 180f, y = 60f, fontSize = 38f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "عرض خاص بـ 5 ج.م فقط", x = 180f, y = 120f, fontSize = 22f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "تصميم جاهز للتعديل الفوري عبر استوديو برايا", x = 180f, y = 170f, fontSize = 13f, colorHex = "#FEF3C7"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "احصل عليه فوراً بملفات المصدر الكاملة", x = 180f, y = 220f, fontSize = 11f, colorHex = "#FFFFFF")
            )
        ),
        DesignTemplate(
            id = "pro_basic_03",
            titleAr = "كارت شخصي مودرن كيو آر كود",
            titleEn = "Modern QR Code Digital Business Card",
            category = "كروت العمل الشخصية",
            aspectRatio = "16:9",
            isPremium = true,
            proTier = ProTier.BASIC,
            priceEgp = 5,
            rating = 4.90f,
            salesCount = 780,
            bgStartHex = "#FFFFFF", // Clean Pearl White & Navy
            bgEndHex = "#E2E8F0",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "م. يوسف النجار", x = 180f, y = 50f, fontSize = 20f, colorHex = "#0F172A", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "استشاري حلول الذكاء الاصطناعي", x = 180f, y = 90f, fontSize = 12f, colorHex = "#2563EB"),
                CanvasElement(id = "e3", type = CanvasElementType.CALLIGRAPHY, text = "📲 [SCAN QR]", x = 180f, y = 135f, fontSize = 16f, colorHex = "#0F172A")
            )
        ),
        DesignTemplate(
            id = "pro_basic_04",
            titleAr = "إعلان ستوري تفاعلي عروض السفر والسياحة",
            titleEn = "Travel Agency Story & Reel Ad",
            category = "الإعلانات الممولة",
            aspectRatio = "9:16",
            isPremium = true,
            proTier = ProTier.BASIC,
            priceEgp = 5,
            rating = 4.92f,
            salesCount = 640,
            bgStartHex = "#78350F", // Warm Cinnamon & Caramel
            bgEndHex = "#92400E",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "✈️", x = 180f, y = 60f, fontSize = 42f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "سافر لشرم الشيخ بـ 1,800 ج.م فقط", x = 180f, y = 125f, fontSize = 20f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "شاملة الإقامة 4 أيام في منتجع 5 نجوم مع الانتقالات", x = 180f, y = 175f, fontSize = 12f, colorHex = "#FDE047"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "اسحب الشاشة للأعلى للحجز ↗", x = 180f, y = 230f, fontSize = 11f, colorHex = "#FEF3C7")
            )
        ),

        // ----------- PROFESSIONAL TIER (10 EGP) -----------
        DesignTemplate(
            id = "pro_prof_01",
            titleAr = "شعار الصقر الملكي الذهبي الفاخر",
            titleEn = "Royal Golden Falcon Logo",
            category = "الشعارات والهويات",
            aspectRatio = "1:1",
            isPremium = true,
            proTier = ProTier.PROFESSIONAL,
            priceEgp = 10,
            rating = 4.98f,
            salesCount = 1890,
            bgStartHex = "#1E3A8A", // Royal Deep Cobalt & Liquid Gold
            bgEndHex = "#2563EB",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.SHAPE, shapeType = ShapeType.CIRCLE, x = 180f, y = 115f, width = 140f, height = 140f, colorHex = "#FDE047", opacity = 0.25f),
                CanvasElement(id = "e2", type = CanvasElementType.CALLIGRAPHY, text = "🦅", x = 180f, y = 110f, fontSize = 52f, colorHex = "#FDE047"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "BARAYA ROYAL", x = 180f, y = 195f, fontSize = 22f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "فخامة الأصالة والإبداع المصري", x = 180f, y = 235f, fontSize = 12f, colorHex = "#FDE047")
            )
        ),
        DesignTemplate(
            id = "pro_prof_02",
            titleAr = "قائمة طعام ستيك ومشاوي فاخرة",
            titleEn = "Prime Steakhouse & Grill Dinner Menu",
            category = "قوائم طعام ومطاعم",
            aspectRatio = "9:16",
            isPremium = true,
            proTier = ProTier.PROFESSIONAL,
            priceEgp = 10,
            rating = 4.96f,
            salesCount = 920,
            bgStartHex = "#881337", // Deep Bordeaux Wine & Gold
            bgEndHex = "#9F1239",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "أطباق اللحوم والمشاوي الملكية 🥩", x = 180f, y = 55f, fontSize = 20f, colorHex = "#FDE047", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "ريب آي ستيك أنجوس 350 جم ... 420 ج.م", x = 180f, y = 115f, fontSize = 12f, colorHex = "#FFFFFF"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "طاجن كفتة بالجبنة والموزاريلا ... 210 ج.م", x = 180f, y = 160f, fontSize = 12f, colorHex = "#FFFFFF"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "كباب وكفتة ضاني مشوي ع الفحم ... 380 ج.م", x = 180f, y = 205f, fontSize = 12f, colorHex = "#FDE047")
            )
        ),
        DesignTemplate(
            id = "pro_prof_03",
            titleAr = "سيرة ذاتية ثنائية اللغة للمهندسين والمطورين",
            titleEn = "Bilingual Executive Engineering CV",
            category = "سير ذاتية احترافية",
            aspectRatio = "4:5",
            isPremium = true,
            proTier = ProTier.PROFESSIONAL,
            priceEgp = 10,
            rating = 4.94f,
            salesCount = 1430,
            bgStartHex = "#F8FAFC", // Crisp White & Sapphire Blue
            bgEndHex = "#EFF6FF",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "المهندس شريف منصور", x = 180f, y = 45f, fontSize = 21f, colorHex = "#0F172A", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "Principal Cloud Architect & AI Specialist", x = 180f, y = 80f, fontSize = 12f, colorHex = "#2563EB"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "خبير حلول Google Cloud و Kubernetes و Microservices", x = 180f, y = 125f, fontSize = 11f, colorHex = "#334155"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "sherif.mansour@gmail.com • +20 101 234 5678", x = 180f, y = 175f, fontSize = 10f, colorHex = "#64748B")
            )
        ),
        DesignTemplate(
            id = "pro_prof_04",
            titleAr = "إعلان مبيعات عقارات كمبوند راقي بالقاهرة",
            titleEn = "Luxury Real Estate Compound Ad",
            category = "الإعلانات الممولة",
            aspectRatio = "1:1",
            isPremium = true,
            proTier = ProTier.PROFESSIONAL,
            priceEgp = 10,
            rating = 4.97f,
            salesCount = 1250,
            bgStartHex = "#7C3AED", // Electric Violet & Neon Fuchsia
            bgEndHex = "#C026D3",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "امتلك فيلتك في قلب التجمع الخامس", x = 180f, y = 60f, fontSize = 21f, colorHex = "#FDE047", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "مقدم 5% فقط وتقسيط مريح على 8 سنوات بدون فوائد", x = 180f, y = 120f, fontSize = 13f, colorHex = "#FFFFFF"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "بحيرات صناعية ونادي صحي متكامل وحراسة 24/7", x = 180f, y = 170f, fontSize = 12f, colorHex = "#FDE047"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "سجل اهتمامك الآن للحصول على البروشور", x = 180f, y = 220f, fontSize = 11f, colorHex = "#F5D0FE")
            )
        ),

        // ----------- PREMIUM TIER (15 EGP) -----------
        DesignTemplate(
            id = "pro_prem_01",
            titleAr = "شهادة شكر وتقدير رسمية ذهبية معتمدة",
            titleEn = "Official Golden Award Certificate",
            category = "بانرات ومطبوعات",
            aspectRatio = "16:9",
            isPremium = true,
            proTier = ProTier.PREMIUM,
            priceEgp = 15,
            rating = 4.99f,
            salesCount = 1680,
            bgStartHex = "#FEF3C7", // Golden Amber Parchment & Regal Crest
            bgEndHex = "#FDE68A",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "شهادة تقدير وإنجاز رسمي", x = 180f, y = 50f, fontSize = 24f, colorHex = "#78350F", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "تُمنح هذه الشهادة تقديراً للإبداع والتميز الاستثنائي", x = 180f, y = 95f, fontSize = 13f, colorHex = "#92400E"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "إدارة منصة برايا للذكاء الاصطناعي • جمهورية مصر العربية", x = 180f, y = 145f, fontSize = 12f, colorHex = "#B45309")
            )
        ),
        DesignTemplate(
            id = "pro_prem_02",
            titleAr = "حزمة هوية بصرية متكاملة لعيادة تجميل",
            titleEn = "Cosmetics & Aesthetics Clinic Brand Kit",
            category = "الشعارات والهويات",
            aspectRatio = "1:1",
            isPremium = true,
            proTier = ProTier.PREMIUM,
            priceEgp = 15,
            rating = 4.97f,
            salesCount = 1110,
            bgStartHex = "#065F46", // Emerald Imperial & Gold Leaf
            bgEndHex = "#047857",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "🌸", x = 180f, y = 60f, fontSize = 44f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "د. نوران التجميل", x = 180f, y = 125f, fontSize = 24f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "Aesthetic Medicine & Laser Clinic", x = 180f, y = 170f, fontSize = 12f, colorHex = "#D1FAE5"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "الشيخ زايد - الجيزة • مصر", x = 180f, y = 215f, fontSize = 11f, colorHex = "#FDE047")
            )
        ),
        DesignTemplate(
            id = "pro_prem_03",
            titleAr = "إعلان فيديو تشويقي إطلاق منتج فاخر",
            titleEn = "Luxury Product Launch Cinematic Teaser",
            category = "إعلانات المنتجات التجارية",
            aspectRatio = "9:16",
            isPremium = true,
            proTier = ProTier.PREMIUM,
            priceEgp = 15,
            rating = 4.95f,
            salesCount = 870,
            bgStartHex = "#0284C7", // Radiant Cyan & Deep Azure
            bgEndHex = "#0369A1",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "👑", x = 180f, y = 60f, fontSize = 46f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "لحظة ولادة التحفة القادمة", x = 180f, y = 130f, fontSize = 22f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "ترقبوا الكشف الرسمي يوم الجمعة القادمة", x = 180f, y = 180f, fontSize = 13f, colorHex = "#E0F2FE"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "كن أول من يحجز نسخته المحدودة", x = 180f, y = 230f, fontSize = 11f, colorHex = "#FDE047")
            )
        ),

        // ----------- EXCLUSIVE TIER (20 EGP) -----------
        DesignTemplate(
            id = "pro_excl_01",
            titleAr = "الهوية التجارية الملكية الكاملة لمجموعة برايا",
            titleEn = "Baraya Ultra Exclusive Enterprise Identity",
            category = "الشعارات والهويات",
            aspectRatio = "1:1",
            isPremium = true,
            proTier = ProTier.EXCLUSIVE,
            priceEgp = 20,
            rating = 5.0f,
            salesCount = 2840,
            bgStartHex = "#1E3A8A", // Celestial Sapphire & Platinum Gold
            bgEndHex = "#3B82F6",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "💎", x = 180f, y = 65f, fontSize = 48f, colorHex = "#FDE047"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "مَجْمُوعَةُ بَرَايَا الحَصْرِيَّة", x = 180f, y = 135f, fontSize = 26f, colorHex = "#FFFFFF", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "THE ROYAL ENTERPRISE EDITION", x = 180f, y = 185f, fontSize = 12f, colorHex = "#FDE047"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "حقوق الاستخدام التجاري الكامل مع كافة أصول الفيكتور والخطوط", x = 180f, y = 230f, fontSize = 11f, colorHex = "#DBEAFE")
            )
        ),
        DesignTemplate(
            id = "pro_excl_02",
            titleAr = "حزمة حملة تسويقية 360 متكاملة للشركات الكبرى",
            titleEn = "360 Enterprise Marketing Campaign Kit",
            category = "الإعلانات الممولة",
            aspectRatio = "16:9",
            isPremium = true,
            proTier = ProTier.EXCLUSIVE,
            priceEgp = 20,
            rating = 4.99f,
            salesCount = 1450,
            bgStartHex = "#9333EA", // Sunset Coral & Purple Dream
            bgEndHex = "#E11D48",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.TEXT, text = "حزمة الحملات الإعلانية الحصرية 360", x = 180f, y = 50f, fontSize = 23f, colorHex = "#FDE047", isBold = true),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "تشمل تصاميم البوستات والستوريات والبانرات مع نصوص إعلانية محترفة", x = 180f, y = 95f, fontSize = 12f, colorHex = "#FFFFFF"),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "السعر الحصري: 20 ج.م فقط بدلاً من 150 ج.م", x = 180f, y = 145f, fontSize = 13f, colorHex = "#FDE047", isBold = true)
            )
        ),
        DesignTemplate(
            id = "pro_excl_03",
            titleAr = "دعوة زفاف إلكترونية تفاعلية VIP لكبار الشخصيات",
            titleEn = "VIP Interactive Royal Wedding Invitation",
            category = "دعوات ومناسبات",
            aspectRatio = "4:5",
            isPremium = true,
            proTier = ProTier.EXCLUSIVE,
            priceEgp = 20,
            rating = 5.0f,
            salesCount = 1970,
            bgStartHex = "#FDF2F8", // Blush Velvet & Metallic Gilt
            bgEndHex = "#FCE7F3",
            elements = listOf(
                CanvasElement(id = "e1", type = CanvasElementType.CALLIGRAPHY, text = "👑", x = 180f, y = 45f, fontSize = 42f, colorHex = "#B45309"),
                CanvasElement(id = "e2", type = CanvasElementType.TEXT, text = "دَعْوَةُ زَفَافٍ مَلَكِيَّةٌ خَاصَّةٌ", x = 180f, y = 100f, fontSize = 22f, colorHex = "#831843", isBold = true),
                CanvasElement(id = "e3", type = CanvasElementType.TEXT, text = "حفل زفاف سمو الأمير والأميرة", x = 180f, y = 145f, fontSize = 16f, colorHex = "#B45309"),
                CanvasElement(id = "e4", type = CanvasElementType.TEXT, text = "قصر القبة الرئاسي - القاهرة • الدعوة شخصية ولا تُفوّض", x = 180f, y = 195f, fontSize = 11f, colorHex = "#9D174D")
            )
        )
    )
}
