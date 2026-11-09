package com.example.data.templates

import com.example.data.model.TemplateBundle

object BundlesData {

    // Official Template Bundles with Egyptian Pound Pricing (EGP / ج.م):
    // 5 templates: 20 EGP (save 5 EGP)
    // 10 templates: 35 EGP (save 15 EGP)
    // 20 templates: 60 EGP (save 40 EGP)
    // 50 templates: 125 EGP (save 125 EGP)
    // 100 templates: 199 EGP (save 301 EGP)

    fun getDefaultBundles(): List<TemplateBundle> = listOf(
        TemplateBundle(
            id = "bundle_5",
            count = 5,
            priceEgp = 20,
            originalPriceEgp = 25,
            titleAr = "باقة الانطلاق (5 قوالب)",
            titleEn = "Starter Bundle (5 Templates)",
            descriptionAr = "وفّر 5 ج.م • مثالية للشركات الناشئة والمشاريع الصغيرة",
            descriptionEn = "Save 5 EGP • Ideal for startups and small stores"
        ),
        TemplateBundle(
            id = "bundle_10",
            count = 10,
            priceEgp = 35,
            originalPriceEgp = 50,
            titleAr = "باقة النمو (10 قوالب)",
            titleEn = "Growth Bundle (10 Templates)",
            descriptionAr = "وفّر 15 ج.م • باقة شاملة للمتاجر وصناع المحتوى",
            descriptionEn = "Save 15 EGP • Popular for online shops and creators",
            isPopular = true
        ),
        TemplateBundle(
            id = "bundle_20",
            count = 20,
            priceEgp = 60,
            originalPriceEgp = 100,
            titleAr = "باقة الأعمال الاحترافية (20 قالب)",
            titleEn = "Business Pro Bundle (20 Templates)",
            descriptionAr = "وفّر 40 ج.م • مخصصة لوكالات التسويق ومصممي الفريلانس",
            descriptionEn = "Save 40 EGP • Perfect for agencies and freelance designers"
        ),
        TemplateBundle(
            id = "bundle_50",
            count = 50,
            priceEgp = 125,
            originalPriceEgp = 250,
            titleAr = "باقة الوكالات المتقدمة (50 قالب)",
            titleEn = "Agency Mega Bundle (50 Templates)",
            descriptionAr = "وفّر 125 ج.م (خصم 50%) • تغطية شاملة لكافة تصنيفات التصميم",
            descriptionEn = "Save 125 EGP (50% OFF) • Full access across all categories"
        ),
        TemplateBundle(
            id = "bundle_100",
            count = 100,
            priceEgp = 199,
            originalPriceEgp = 500,
            titleAr = "الترخيص الشامل الملكي (100 قالب)",
            titleEn = "Royal Unlimited Bundle (100 Templates)",
            descriptionAr = "وفّر 301 ج.م (خصم 60%) • وصول كامل لمكتبة برايا الاحترافية مدى الحياة",
            descriptionEn = "Save 301 EGP (60% OFF) • Complete lifetime access to Baraya PRO",
            isPopular = true
        )
    )
}
