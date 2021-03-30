package com.lieferin_global.webservices.responce

import com.google.gson.annotations.SerializedName
import com.lieferin_global.Model.ProductList
import com.lieferin_global.webservices.request.DetailsINfo

open class BaseRS {
    @SerializedName("code")
    var responseCode: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("termsAndCondition")
    var termsAndCondition: ResultRes? = null

    @SerializedName("individualBooking")
    var individualBooking: ResultRes? = null

    @SerializedName("RefundPolicy")
    var RefundPolicy: ResultRes? = null

    @SerializedName("privacyPolicy")
    var privacyPolicy: ResultRes? = null

    @SerializedName("cancellationPolicy")
    var cancellationPolicy: ResultRes? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("businessProfile")
    var businessProfile: String? = null

    @SerializedName("token")
    var token: String? = null

    @SerializedName("otp")
    var otp: String? = null

    @SerializedName("orderCode")
    var orderCode: String? = null

    @SerializedName("details")
    var details: DetailsINfo? = null

    @SerializedName("userPath")
    var userPath: String? = null

    @SerializedName("user_details")
    var user_details: DetailsINfo? = null

    @SerializedName("addressDetails")
    var addressDetails: DetailsINfo? = null

    @SerializedName("fetchData")
    var fetchData: ResultRes? = null

    @SerializedName("groceryProductData")
    var groceryProductData: WorkExperience? = null

    @SerializedName("reletedGroceryProductData")
    var reletedGroceryProductData: MutableList<WorkExperience>? = null

    @SerializedName("fetchFavoriteRestaurantData")
    var fetchFavoriteRestaurantData: MutableList<ResultRes>? = null

    @SerializedName("createToppinsGroupList")
    var createToppinsGroupList: MutableList<ProductList>? = null

    @SerializedName("promoList")
    var promoList: MutableList<ResultRes>? = null

    @SerializedName("promoData")
    var promoData: ResultRes? = null

    @SerializedName("cardInfo")
    var cardInfo: MutableList<ResultRes>? = null

    @SerializedName("fetchFavoriteStoreData")
    var fetchFavoriteStoreData: MutableList<ResultRes>? = null

    @SerializedName("deliveryData")
    var deliveryData: ResultRes? = null

    @SerializedName("tableReservationData")
    var tableReservationData: MutableList<ResultRes>? = null

    @SerializedName("restaurantRatingList")
    var restaurantRatingList: MutableList<ResultRes>? = null

    @SerializedName("groceryRatingList")
    var groceryRatingList: MutableList<ResultRes>? = null

    @SerializedName("orderSelfPickupMinimumAmount")
    var orderSelfPickupMinimumAmount: String? = null

    @SerializedName("bookingData")
    var bookingData: MutableList<ResultResValue>? = null

    @SerializedName("memberPickedMenusData")
    var memberPickedMenusData: MutableList<ResultResValue>? = null

    @SerializedName("memberData")
    var memberData: MutableList<ResultRes>? = null

    @SerializedName("groupList")
    var groupList: MutableList<ResultRes>? = null

    @SerializedName("bookingConfirmData")
    var bookingConfirmData: MutableList<ResultResValue>? = null

    @SerializedName("customerAddress")
    var customerAddress: MutableList<ResultRes>? = null

    @SerializedName("categoryList")
    var categoryList: MutableList<ResultRes>? = null

    @SerializedName("faqList")
    var faqList: MutableList<ResultRes>? = null

    @SerializedName("languageList")
    var languageList: MutableList<ResultRes>? = null

    @SerializedName("cellularList")
    var cellularList: MutableList<ResultRes>? = null

    @SerializedName("businessListing")
    var businessListing: MutableList<ResultRes>? = null

    @SerializedName("businessScaleList")
    var businessScaleList: MutableList<ResultRes>? = null

    @SerializedName("countryList")
    var countryList: MutableList<ResultRes>? = null

    /*@SerializedName("restaurantData")
    var restaurantData: MutableList<WorkExperience>? = null*/

    @SerializedName("restaurantTableListing")
    var restaurantTableListing: MutableList<WorkExperience>? = null

    @SerializedName("availableSlots")
    var availableSlots: MutableList<WorkExperience>? = null

    @SerializedName("subscriptionList")
    var subscriptionList: MutableList<ResultRes>? = null

    @SerializedName("particularSubscriptionData")
    var particularSubscriptionData: ResultRes? = null

    @SerializedName("bannnerImageList")
    var bannnerImageList: MutableList<ResultRes>? = null

    @SerializedName("recommendedList")
    var recommendedList: MutableList<ResultRes>? = null

    @SerializedName("cities")
    var cities: MutableList<ResultRes>? = null

    @SerializedName("names")
    var names: MutableList<ResultRes>? = null

    @SerializedName("bookingList")
    var bookingList: MutableList<ResultRes>? = null

    @SerializedName("profile")
    var profile: ResultRes? = null

    @SerializedName("fileSource")
    var fileSource: String? = null

    @SerializedName("customerReferenceCode")
    var customerReferenceCode: String? = null

    @SerializedName("verificationCode")
    var verificationCode: String? = null

    @SerializedName("referenceCode")
    var referenceCode1: String? = null

    @SerializedName("vendorReferenceCode")
    var vendorReferenceCode: String? = null

    @SerializedName("restaurantPath")
    var restaurantPath: String? = null

    @SerializedName("cuisinePath")
    var cuisinePath: String? = null

    @SerializedName("groceryPath")
    var groceryPath: String? = null

    @SerializedName("categoryPath")
    var categoryPath: String? = null


}