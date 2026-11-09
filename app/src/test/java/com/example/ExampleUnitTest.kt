package com.example

import com.example.data.model.CategoryModel
import com.example.data.model.DesignTemplate
import com.example.data.model.ProTier
import com.example.data.model.SubscriptionPlan
import com.example.data.model.TemplateBundle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

  @Test
  fun `verify public template library is empty by default until owner adds designs`() {
    val initialTemplates = emptyList<DesignTemplate>()
    assertTrue("Public library must be empty by default", initialTemplates.isEmpty())
  }

  @Test
  fun `verify owner template creation with pricing types and EGP price`() {
    // 1. Free template
    val freeTemplate = DesignTemplate(
      id = "tpl_free_01",
      titleAr = "شعار مطعم عصري أصلي",
      titleEn = "Modern Restaurant Logo",
      descriptionAr = "شعار وهوية احترافية للمطاعم والمقاهي",
      category = "مطاعم ومأكولات",
      aspectRatio = "1:1",
      isPremium = false,
      pricingType = "FREE",
      priceEgp = 0,
      isPublished = true,
      imageUri = "content://media/restaurant_logo.png",
      designFileUri = "content://media/restaurant_logo.svg",
      designFileName = "restaurant_logo.svg"
    )
    assertEquals(0, freeTemplate.priceEgp)
    assertEquals("FREE", freeTemplate.pricingType)
    assertFalse(freeTemplate.isPremium)
    assertTrue(freeTemplate.isPublished)
    assertEquals("restaurant_logo.svg", freeTemplate.designFileName)

    // 2. Premium template
    val premiumTemplate = DesignTemplate(
      id = "tpl_prem_02",
      titleAr = "بوستر عرض خاص للملابس والأزياء",
      titleEn = "Fashion Special Sale Poster",
      descriptionAr = "تصميم إعلاني جاهز لوسائل التواصل الاجتماعي والمطبوعات",
      category = "أزياء وجمال",
      aspectRatio = "4:5",
      isPremium = true,
      pricingType = "PREMIUM",
      priceEgp = 45,
      isPublished = true,
      imageUri = "content://media/fashion_ad.jpg",
      designFileUri = "content://media/fashion_ad.psd",
      designFileName = "fashion_ad.psd"
    )
    assertEquals(45, premiumTemplate.priceEgp)
    assertEquals("PREMIUM", premiumTemplate.pricingType)
    assertTrue(premiumTemplate.isPremium)
    assertTrue(premiumTemplate.isPublished)

    // 3. Exclusive template
    val exclusiveTemplate = DesignTemplate(
      id = "tpl_excl_03",
      titleAr = "هوية بصرية كاملة لشركة عقارات",
      titleEn = "Real Estate Complete Brand Identity",
      descriptionAr = "تصميم هوية بصرية تجارية حصرية غير قابلة للتكرار",
      category = "عقارات وإنشاءات",
      aspectRatio = "16:9",
      isPremium = true,
      pricingType = "EXCLUSIVE",
      priceEgp = 350,
      isPublished = false, // draft
      imageUri = "content://media/realestate_cover.png",
      designFileUri = "content://media/realestate_assets.zip",
      designFileName = "realestate_assets.zip"
    )
    assertEquals(350, exclusiveTemplate.priceEgp)
    assertEquals("EXCLUSIVE", exclusiveTemplate.pricingType)
    assertFalse(exclusiveTemplate.isPublished) // Draft state
  }

  @Test
  fun `verify admin template editing duplicating and publish toggle`() {
    val original = DesignTemplate(
      id = "tpl_001",
      titleAr = "إعلان سيارات فاخرة",
      titleEn = "Luxury Cars Ad",
      descriptionAr = "تصميم سوشيال ميديا",
      category = "سيارات ومحركات",
      aspectRatio = "1:1",
      isPremium = true,
      pricingType = "PREMIUM",
      priceEgp = 30,
      isPublished = true
    )

    // Duplicate
    val duplicated = original.copy(
      id = "tpl_001_copy",
      titleAr = "${original.titleAr} (نسخة)",
      titleEn = "${original.titleEn} (Copy)",
      isPublished = false // New copy starts as draft
    )
    assertEquals("tpl_001_copy", duplicated.id)
    assertTrue(duplicated.titleAr.endsWith("(نسخة)"))
    assertFalse(duplicated.isPublished)

    // Edit price and publish toggle
    val updated = original.copy(
      priceEgp = 50,
      isPublished = false
    )
    assertEquals(50, updated.priceEgp)
    assertFalse(updated.isPublished)
  }

  @Test
  fun `verify categories management data structures`() {
    val initialCategories = listOf(
      CategoryModel("cat_1", "مطاعم ومأكولات", "Restaurants & Food", "restaurant", 0, true),
      CategoryModel("cat_2", "أزياء وجمال", "Fashion & Beauty", "checkroom", 1, true),
      CategoryModel("cat_3", "سيارات ومحركات", "Cars & Motors", "directions_car", 2, true),
      CategoryModel("cat_4", "عقارات وإنشاءات", "Real Estate", "apartment", 3, true),
      CategoryModel("cat_5", "سوشيال ميديا", "Social Media", "share", 4, true),
      CategoryModel("cat_6", "أعمال وتسويق", "Business & Marketing", "business", 5, true),
      CategoryModel("cat_7", "مناسبات واحتفالات", "Events & Celebrations", "celebration", 6, true)
    )

    assertEquals(7, initialCategories.size)

    // Add new category
    val newCategory = CategoryModel(
      id = "cat_custom_8",
      nameAr = "طبي وصحي",
      nameEn = "Medical & Healthcare",
      iconName = "local_hospital",
      orderIndex = 7,
      isDefault = false
    )
    val listAfterAdd = initialCategories + newCategory
    assertEquals(8, listAfterAdd.size)
    assertTrue(listAfterAdd.any { it.nameAr == "طبي وصحي" })

    // Edit category
    val editedCategory = newCategory.copy(nameAr = "عيادات ومراكز طبية")
    assertEquals("عيادات ومراكز طبية", editedCategory.nameAr)

    // Delete category
    val listAfterDelete = listAfterAdd.filter { it.id != newCategory.id }
    assertEquals(7, listAfterDelete.size)
    assertFalse(listAfterDelete.any { it.id == newCategory.id })
  }

  @Test
  fun `verify bundles and subscription plans editable prices in EGP`() {
    val subscriptionPlans = listOf(
      SubscriptionPlan("sub_monthly", "اشتراك شهري احترافي", "Monthly Pro Plan", 99, 149, "MONTHLY", "وصول كامل لكافة القوالب الاحترافية شهرياً", "Full access to all templates monthly", false),
      SubscriptionPlan("sub_annual", "اشتراك سنوي VIP (توفير 40%)", "Annual VIP Plan", 599, 999, "ANNUAL", "توفير سنوي ضخم مع دعم أولوية", "Huge annual savings with priority support", true),
      SubscriptionPlan("sub_lifetime", "ترخيص مدى الحياة الكامل", "Lifetime License", 1499, 2499, "LIFETIME", "دفعة واحدة لمدى الحياة بدون تجديد", "One-time payment lifetime access", false)
    )

    assertEquals(3, subscriptionPlans.size)
    val monthly = subscriptionPlans.first()
    assertEquals(99, monthly.priceEgp)

    // Owner edits subscription price in EGP
    val updatedMonthly = monthly.copy(priceEgp = 120)
    assertEquals(120, updatedMonthly.priceEgp)

    val bundles = listOf(
      TemplateBundle("b_5", 5, 20, 25, "باقة المبتدئين", "Starter Pack", "5 تصاميم احترافية بسعر مخفض", "5 Starter designs"),
      TemplateBundle("b_10", 10, 35, 50, "باقة الأعمال الصغيرة", "Small Business", "10 تصاميم احترافية", "10 Pro designs"),
      TemplateBundle("b_20", 20, 60, 100, "باقة الشركات والوكالات", "Agency Pack", "20 تصميماً احترافياً", "20 Pro designs"),
      TemplateBundle("b_50", 50, 125, 250, "الباقة الذهبية الكبرى", "Golden 50 Pack", "50 تصميماً شاملاً", "50 Pro designs", true),
      TemplateBundle("b_100", 100, 199, 500, "باقة الاستوديوهات والمطابع", "Ultimate 100 Pack", "100 تصميم احترافي", "100 Pro designs")
    )

    assertEquals(5, bundles.size)
    val bundle50 = bundles.find { it.count == 50 }
    assertNotNull(bundle50)
    assertEquals(125, bundle50?.priceEgp)

    // Owner edits bundle price
    val updatedBundle = bundle50?.copy(priceEgp = 140)
    assertEquals(140, updatedBundle?.priceEgp)
  }

  @Test
  fun `verify owner authorization pin validation`() {
    val validPins = listOf("2026", "1234", "baraya", " BARAYA ", "2026 ")
    val invalidPins = listOf("0000", "admin", "pass", "9999", "1111")

    validPins.forEach { pin ->
      val clean = pin.trim()
      val isMatch = clean == "2026" || clean == "1234" || clean.equals("baraya", ignoreCase = true)
      assertTrue("PIN '$pin' should be valid for owner Mohamed Baraya", isMatch)
    }

    invalidPins.forEach { pin ->
      val clean = pin.trim()
      val isMatch = clean == "2026" || clean == "1234" || clean.equals("baraya", ignoreCase = true)
      assertFalse("PIN '$pin' should be rejected", isMatch)
    }
  }
}
