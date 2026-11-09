package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        UserEntity::class,
        ProjectEntity::class,
        PaymentEntity::class,
        PurchaseEntity::class,
        TemplateEntity::class,
        CategoryEntity::class,
        BundleEntity::class,
        SubscriptionPlanEntity::class,
        UserWebsiteEntity::class,
        WebsiteTemplateEntity::class,
        WebsiteSettingsEntity::class,
        WebsiteAnalyticsEntity::class,
        WebsiteBookingEntity::class,
        PaymentMethodConfigEntity::class,
        FeatureAvailabilityEntity::class,
        DiscountCouponEntity::class
    ],
    version = 11,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun projectDao(): ProjectDao
    abstract fun paymentDao(): PaymentDao
    abstract fun purchaseDao(): PurchaseDao
    abstract fun templateDao(): TemplateDao
    abstract fun categoryDao(): CategoryDao
    abstract fun bundleDao(): BundleDao
    abstract fun subscriptionPlanDao(): SubscriptionPlanDao
    abstract fun userWebsiteDao(): UserWebsiteDao
    abstract fun websiteTemplateDao(): WebsiteTemplateDao
    abstract fun websiteSettingsDao(): WebsiteSettingsDao
    abstract fun websiteAnalyticsDao(): WebsiteAnalyticsDao
    abstract fun websiteBookingDao(): WebsiteBookingDao
    abstract fun paymentMethodConfigDao(): PaymentMethodConfigDao
    abstract fun featureAvailabilityDao(): FeatureAvailabilityDao
    abstract fun discountCouponDao(): DiscountCouponDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "baraya_ai_database"
                ).fallbackToDestructiveMigration(dropAllTables = true).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
