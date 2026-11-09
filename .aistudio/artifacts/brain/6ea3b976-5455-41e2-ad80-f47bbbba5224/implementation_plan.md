# Baraya Website Builder: QR Code, Mini Cart & Smart Commerce Expansion

A comprehensive feature expansion for the Baraya AI Website Builder introducing four high-impact commerce capabilities: an interactive printable QR Code generator for physical stores, a unified multi-item Mini Cart with structured WhatsApp bill checkout, live visitor and conversion analytics tracking, and an interactive online appointment and table reservation module.

## User Review & Critical Decisions

> [!IMPORTANT]
> The following recommended decisions have been set for this implementation cycle. Review and approve or request adjustments before execution:

- **Confirmed Decision 1: Full Suite Implementation**: Implementing all four synergistic commerce modules (QR Studio, WhatsApp Mini Cart, Analytics Tracker, and Appointment Booking) in one unified workflow.
- **Confirmed Decision 2: Zero Friction WhatsApp Ordering**: Mini Cart and Booking requests generate structured, itemized Arabic receipts dispatched directly to the merchant's verified WhatsApp number (`https://wa.me/...`) without requiring third-party payment gateways.
- **Confirmed Decision 3: Local First Analytics & QR Storage**: Store visitor metrics and QR custom designs locally via Room SQLite with zero external tracking dependencies to ensure user privacy and offline availability.

---

## 1. Overview & Core Concept

### What It Delivers
This expansion elevates user-created websites from static landing pages to fully functional commercial storefronts:
1. **Interactive Storefront QR Studio**: Enables business owners to generate custom-branded QR codes linking directly to their website, customized with brand colors, store logos, and ready-to-print Arabic frames for restaurant tables, shop windows, and packaging.
2. **Interactive Mini Cart & WhatsApp Bill Checkout**: Replaces single-item direct ordering with a sliding multi-item cart drawer. Customers can adjust quantities, add notes, and dispatch a clean, calculated WhatsApp order bill.
3. **Store Performance & Visitor Analytics Tracker**: Provides store owners with an in-app analytics dashboard showing daily visitors, catalog views, top-selling items, and total WhatsApp order clicks.
4. **Smart Appointment & Reservation Module**: Allows clinics, dental centers, salons, and restaurants to accept date, time, and service bookings directly via their website.

### Target Audience & Persona
- Local retail shop owners, restaurant managers, doctors, clinic administrators, and beauty salon owners in Egypt who need a modern digital storefront with zero complicated tech overhead.

---

## 2. User Experience & Visual Design

### Key User Flows

```
┌──────────────────────────────────────────────────────────────────────────┐
│                             USER JOURNEYS                                │
├──────────────────────────────┬───────────────────────────────────────────┤
│ 1. QR Code Studio            │ 2. Mini Cart & WhatsApp Ordering          │
│ • Select User Website        │ • Customer taps [+] on catalog items      │
│ • Choose Frame & Brand Color │ • Floating Cart Badge updates with total  │
│ • Preview Print Table Tent   │ • Customer opens slide-up Cart Sheet      │
│ • Download/Share QR Asset    │ • Taps "Checkout via WhatsApp" with bill  │
├──────────────────────────────┼───────────────────────────────────────────┤
│ 3. Analytics Dashboard       │ 4. Smart Appointment Booking              │
│ • View Page Impressions      │ • Customer selects Date, Time & Service   │
│ • Track WhatsApp CTA Clicks  │ • Enters Customer Name & Mobile           │
│ • See Top Clicked Products   │ • Confirms Booking dispatched to Merchant │
└──────────────────────────────┴───────────────────────────────────────────┘
```

### Visual Identity & Theme
- **Color Tokens**: Royal Gold primary (`#D4AF37`), Dark Obsidian surface (`#0F172A`), Emerald Green checkout (`#10B981`), and Crisp Slate borders (`#334155`).
- **Typography**: Cairo font family for Arabic numerals, labels, and headlines; Cinzel for high-end Latin accents.
- **Visual Micro-interactions**: Smooth sheet reveals for the Mini Cart, animated count badges on the floating cart button, and responsive SVG/Canvas rendering for QR frames.

---

## 3. Key Product Decisions & Trade-Offs

### Decision 1: In-App Procedural QR Code Generation
- **Chosen Approach**: Use a lightweight, high-performance Canvas rendering algorithm based on standard QR matrix encoding with zero external web API calls.
- **Why**: Ensures instantaneous rendering, 100% offline generation, and ability to overlay custom store logos and colored circular dot styles.

### Decision 2: Multi-Item WhatsApp Checkout Formatting
- **Chosen Approach**: Generate a clean, formatted text invoice containing itemized names, unit prices, quantities, subtotal in EGP, delivery notes, and timestamp.
- **Why**: WhatsApp is the undisputed dominant commercial communication channel in Egypt and the Middle East; direct structured WhatsApp messaging eliminates abandoned carts and complex payment setups.

---

## 4. Technical Architecture & Data Strategy

### Component & System Diagram

```
┌─────────────────────────────────────────────────────────────────────────┐
│                           BARAYA ARCHITECTURE                           │
└────────────────────────────────────┬────────────────────────────────────┘
                                     │
           ┌─────────────────────────┴────────────────────────┐
           ▼                                                  ▼
┌─────────────────────────┐                        ┌───────────────────────┐
│     DATA LAYER (Room)   │                        │   VIEWMODEL & STATE   │
├─────────────────────────┤                        ├───────────────────────┤
│ • UserWebsiteEntity     │                        │ • BarayaViewModel     │
│ • WebsiteAnalyticsEntity│                        │ • cartItemsFlow       │
│ • WebsiteBookingEntity  │                        │ • analyticsSummaryFlow│
│ • WebsiteTemplateEntity │                        │ • activeQrSiteFlow    │
└──────────┬──────────────┘                        └──────────┬────────────┘
           │                                                  │
           └─────────────────────────┬────────────────────────┘
                                     │
           ┌─────────────────────────┼────────────────────────┐
           ▼                         ▼                        ▼
┌─────────────────────┐   ┌────────────────────┐   ┌──────────────────────┐
│  WebsitesScreen.kt  │   │ WebsiteEditorScreen│   │ StoreQrStudioScreen  │
├─────────────────────┤   ├────────────────────┤   ├──────────────────────┤
│ • Sites list        │   │ • General Info     │   │ • Canvas QR Frame    │
│ • Analytics Cards   │   │ • Catalog & Prices │   │ • Color & Logo Pick  │
│ • Booking Inquiries │   │ • Mini Cart Preview│   │ • Export & Print PNG │
└─────────────────────┘   └────────────────────┘   └──────────────────────┘
```

### Proposed Data Models
1. **`CartItem`**: `id`, `websiteItemId`, `nameAr`, `priceEgp`, `quantity`, `notes`.
2. **`WebsiteAnalytics`**: `siteId`, `viewsCount`, `whatsappClicksCount`, `catalogViewsCount`, `lastVisitedAt`.
3. **`WebsiteBooking`**: `id`, `siteId`, `customerName`, `customerPhone`, `serviceName`, `bookingDate`, `bookingTime`, `notes`, `status`.

---

## Verification Plan

### Automated Compilation & Linting
- Verify seamless Kotlin compilation via `compile_applet`.
- Verify database schemas and DAO methods with `@Database(version = 10)` fallback migration rules.

### Functional CUJs to Verify
1. **QR Studio**: Generate QR code for created website -> customize color -> test share intent.
2. **Mini Cart**: Add 3 catalog items in website preview -> review quantities -> generate WhatsApp checkout URL with itemized EGP total.
3. **Booking**: Submit appointment inquiry -> verify prompt dispatch to merchant's WhatsApp.
4. **Analytics**: Increment view count on website preview -> verify counter displayed in the merchant portal.
