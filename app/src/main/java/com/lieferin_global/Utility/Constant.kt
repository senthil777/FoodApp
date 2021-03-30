package com.lieferin_global.Utility

import android.graphics.Bitmap
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import java.util.ArrayList

object Constant{

        const val FontStyleRegular = "fonts/Metropolis-Regular.otf"
        const val FontStyleBold = "fonts/Metropolis-Bold.otf"
        const val FontStyleLight = "fonts/Metropolis-Light.otf"
        const val FontStyleMedium = "fonts/Metropolis-Medium.otf"
        const val FontStyleSemiBold = "fonts/Metropolis-SemiBold.otf"

        const val PAYPAL_CLIENT_ID =
                "AZcOJQx0DnHfvxJRUKAcath1hM63Nd0lVlLMBAsfhqYMXWeONzYYROga4HCsak1fE8cgzWfPINZWhASx"

        var qualityTable = ""

        var totalPriceTable = ""

        var isTableBooking = "0"

        var isTableBookingReferenceCode = ""

        var pos = 0

        var openingTime = "0"

        var photoSelect = "0"

        var closingTime = "0"

        var posMove = 0

        var distance = ""

        var price = ""

        var latitudeAdd = ""

        var longtitudeAdd =  ""

        var quality = ""

        var totalPrice = ""

        var vegOnly = "0"

        var favStringValue = ""

        var favStringGroceryValue = ""

        var AddRestaruantNote = ""

        var StringName =""

        var StringMobileNumber =""

        var countryCode =""

        var countryCodeString =""

        var StringEmail =""

        var webServiceValue ="0"

        var StringPassword =""

        var StringConfirmPassword =""

        val menuNormal = arrayOf(
                "Manage",
                "Refresh Book Shelf",
                "Notification",
                "Send Feedback",
                "Terms & Conditions",
                "Help",
                "About Us",
                "Logout"
        )

        var adapterCategories10: ArrayList<Product> = ArrayList<Product>()

        val icon_menuNormal = intArrayOf(
                R.drawable.my_account,
                R.drawable.my_account,
                R.drawable.my_account,
                R.drawable.my_account,
                R.drawable.my_account,
                R.drawable.my_account,
                R.drawable.my_account,
                R.drawable.my_account
        )

        //var Url = "https://api.lieferin.com/"

        var Url = "https://api.lieferin.de/"

        var priceHighToLow =""

        var priceLowToHigh =""

        var ratingLowToHigh =""

        var valueString ="0"

        var priceLow = "0"

        var priceHigh = "0"

        var ratingValue = "0"

        var BookingType ="0"

        var PayMentType ="0"

        var PickUpDate ="0"

        var PickUpType ="0"

        var DeliveryType =""

        var AppType ="0"

        var AppUpdate = "0"

        var categoryFilter = "0"

        var nifNumber ="";

        var valueStringFour =0.0

        var valueStringFilter =0.0

        var customerAddressId =""

        var customerPincode =""

        var addressComplete =""

        var distanceValue =""

        var deliveryFare =""

        var valueStringMin =0

        var valueStringMax =0

        var valueStringItem =""

        var isAdminString ="1"

        var isPayType ="1"

        var AddressTXT ="Your Location"

        var photoConstant: Bitmap? = null

        var logInUrl = "api/customer/login"
        const val MEMBER_Login_URL_RQ = 1

        var customerRegister = "api/customer-mobile-insert"
        const val MEMBER_customerRegister_URL_RQ = 2

        var getCustomer = "api/customer"
        const val MEMBER_getCustomer_URL_RQ = 3

        var customerLogout = "api/logout"
        const val MEMBER_CustomerLogout_URL_RQ = 4

        var customerMobileUpdate = "api/customer-mobile-update"
        const val MEMBER_CustomerMobileUpdate_URL_RQ = 5

        var getCustomerForgotPassword = "api/customer-forgot-password"
        const val MEMBER_getCustomerForgot_URL_RQ = 6

        var getVerifyCustomerOTP = "api/verify-customer-otp"
        const val MEMBER_getVerifyCustomerOTP_URL_RQ = 7

        var getResetPassword = "api/customer-reset-password"
        const val MEMBER_getResetPassword_URL_RQ = 8

        var getUserRestaurantDashBoard = "api/user-restaurant-dashboard"
        const val MEMBER_getUserRestaurantDashBoard_URL_RQ = 9

        var getVerifyCustomer = "api/verify-customer-register"
        const val MEMBER_getVerifyCustomer_URL_RQ = 10

        var getRestaurantData = "api/fetch-restaurant-data"
        const val MEMBER_getRestaurantData_URL_RQ = 11

        var getBookingFoodOrder = "api/booking-food-order"
        const val MEMBER_getBookingFoodOrder_URL_RQ = 12

        var cancelFoodOrder = "api/cancel-food-order"
        const val MEMBER_cancelFoodOrder_URL_RQ = 13

        var customerAddress = "api/fetch-customer-address"
        const val MEMBER_customerAddress_URL_RQ = 14

        var customerAddressInsert = "api/customer-address-insert"
        const val MEMBER_customerAddressInsert_URL_RQ = 15

        var filterApi = "api/filter-restaurant-data"
        const val MEMBER_filterApi_URL_RQ = 16

        var tableReservtionDashboard = "api/user-table-reservtion-dashboard"
        const val MEMBER_tableReservtionDashboard_URL_RQ = 17

        var tableSlotAvailableList = "api/table-slot-available-list"
        const val MEMBER_tableSlotAvailableList_URL_RQ = 18

        var tableReservtionRestaurant = "api/table-reservtion-restaurant"
        const val MEMBER_tableReservtionRestaurant_URL_RQ = 19

        var tableFavoriteList ="api/favorite-restaurant-list"
        const val MEMBER_favoriteRestaurantList_URL_RQ = 20

        var tableFavoriteListAdd ="api/add-favorite-restaurant"
        const val MEMBER_favoriteRestaurantListAdd_URL_RQ = 21

        var tableFavoriteListRemove ="api/revoke-favorite-restaurant"
        const val MEMBER_favoriteRestaurantListRemove_URL_RQ = 22

        var deletCustomerAddress = "api/delete-customer-address"
        const val MEMBER_deletCustomerAddress_URL_RQ = 23

        var bookingFoodOrderList = "api/booking-food-order-list"
        const val MEMBER_bookingFoodOrderList_URL_RQ = 24

        var confirmFoodOrderList = "api/confirm-food-order-list"
        const val MEMBER_confirmFoodOrderList_URL_RQ = 25

        var tableReservationInsert = "api/table-reservtion-insert"
        const val MEMBER_tableReservationInsert_URL_RQ = 26

        var userGroceryDashboard = "api/user-grocery-dashboard"
        const val MEMBER_userGroceryDashboard_URL_RQ = 27

        var fetchGroceryData = "api/fetch-grocery-data"
        const val MEMBER_fetchGroceryData_URL_RQ = 28

        var filterGroceryData = "api/filter-grocery-data"
        const val MEMBER_filterGroceryData_URL_RQ = 29

        var bookingGroceryOrder = "api/booking-grocery-order"
        const val MEMBER_bookingGroceryOrder_URL_RQ = 30

        var cancelGroceryOrder = "api/cancel-grocery-order"
        const val MEMBER_cancelGroceryOrder_URL_RQ = 31

        var bookingGroceryOrderList = "api/booking-grocery-order-list"
        const val MEMBER_bookingGroceryOrderList_URL_RQ = 32

        var customerExistingGroup = "api/customer-existing-group"
        const val MEMBER_customerExistingGroup_URL_RQ = 33

        var tableBookingData = "api/table-booking-data"
        const val MEMBER_tableBookingData_RQ = 34

        var tableBookingMemberData = "api/table-booking-member-data"
        const val MEMBER_tableBookingMemberData_RQ = 35

        var tableReservtionMenuPicked = "api/table-reservtion-menu-picked"
        const val MEMBER_tableReservtionMenuPicked_RQ = 36

        var removeTableBookingMember = "api/remove-table-booking-member"
        const val MEMBER_removeTableBookingMember_RQ = 37

        var tableFavoriteStoreList ="api/favorite-store-list"
        const val MEMBER_tableFavoriteStoreList_RQ = 38

        var tableFavoriteStoreAdd ="api/add-favorite-store"
        const val MEMBER_tableFavoriteStoreAdd_URL_RQ = 39

        var confirmGroceryList = "api/confirm-grocery-order-list"
        const val MEMBER_confirmGroceryList_URL_RQ = 40

        var insertCardList = "api/store-card-info"
        const val MEMBER_insertCardList_URL_RQ = 41

        var CardList = "api/fetch-card-info"
        const val MEMBER_CardList_URL_RQ = 42

        var ratingStore = "api/restaurant-rating-store"
        const val MEMBER_ratingStore_URL_RQ = 43

        var ratingGrocery = "api/grocery-rating-store"
        const val MEMBER_ratingGrocery_URL_RQ = 44

        var ratingDriver = "api/driver-rating-store"
        const val MEMBER_ratingDriver_URL_RQ = 45

        var memberPickedMenusList = "api/fetch-table-member-picked-menus-list"
        const val MEMBER_memberPickedMenusList_URL_RQ = 46

        var customerMakePaymentForMember = "api/customer-make-payment-for-member"
        const val MEMBER_customerMakePaymentForMember_URL_RQ = 47

        var checkDeliveryLocation = "api/check-delivery-location"
        const val MEMBER_checkDeliveryLocation_URL_RQ = 48

        var checkGroceryDeliveryLocation = "api/check-grocery-delivery-location"
        const val MEMBER_checkGroceryDeliveryLocation_URL_RQ = 49

        var resendOtp = "api/resend-customer-verification-code"
        const val MEMBER_resendOtp_URL_RQ = 50

        var restaurantAvailability = "api/check-restaurant-availability"
        const val MEMBER_restaurantAvailability_URL_RQ = 51

        var groceryAvailability = "api/check-grocery-availability"
        const val MEMBER_groceryAvailability_URL_RQ = 52

        var fetchRestaurantRating = "api/fetch-restaurant-rating"
        const val MEMBER_fetchRestaurantRating_URL_RQ = 53

        var fetchGroceryRating = "api/fetch-grocery-rating"
        const val MEMBER_fetchGroceryRating_URL_RQ = 54

        var checkRestaurantPromoOffer = "api/check-restaurant-promo-offer"
        const val MEMBER_checkRestaurantPromoOffer_URL_RQ = 55

        var fetchRestaurantPromocodeList = "api/fetch-restaurant-promocode-list"
        const val MEMBER_fetchRestaurantPromocodeList_URL_RQ = 56

        var fetchMenuToppingList = "api/fetch-menu-topping-list"
        const val MEMBER_fetchMenuToppingList_URL_RQ = 57

        var countryList = "api/lieferin-service-country-list"
        const val MEMBER_countryList_URL_RQ = 58

        var productView = "api/product-view"
        const val MEMBER_productView_URL_RQ = 59

        var cardDelete = "api/delete-customer-card-info"
        const val MEMBER_cardDelete_URL_RQ = 60

        var checkGroceryPromoOffer = "api/check-grocery-promo-offer"
        const val MEMBER_checkGroceryPromoOffer_URL_RQ = 61

        var fetchGroceryPromoCode = "api/fetch-grocery-promocode-list"
        const val MEMBER_fetchGroceryPromoCode_URL_RQ = 62

        var lieferinTermsCondition = "api/lieferin-terms-condition"
        const val MEMBER_LieferinTermsCondition_URL_RQ = 63

        var lieferin_privacy_policy = "api/lieferin-privacy-policy"
        const val MEMBER_lieferin_privacy_policy_URL_RQ = 64

        var lieferinRefundPolicy = "api/lieferin-refund-policy"
        const val MEMBER_lieferinRefundPolicy_URL_RQ = 65

        var lieferinCancellationPolicy = "api/lieferin-cancellation-policy"
        const val MEMBER_lieferinCancellationPolicy_URL_RQ = 66

}
