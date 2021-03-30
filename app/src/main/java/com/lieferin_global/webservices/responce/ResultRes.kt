package com.lieferin_global.webservices.responce

import android.os.Parcel
import android.os.Parcelable
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.ProductListView
import java.io.Serializable

class ResultRes() : BaseRS(), Serializable,Parcelable {
    var id: String? = null

    var nifNumber : String? = null

    var selfPickupMinimumAmount : String? = null

    var cardNo : String? = null

    var cardType : String? =null

    var orderId : String? = null

    var countryName : String? = null

    var countryCode : String? = null


    var mobileNumberLimit : String? = null

    var countryShortCode : String? = null

    var feedback : String? = null

    var profilePicture : String? = null

    var customerName : String? = null

    var deliveryFare : String? = null

    var nameOnCard : String? = null

    var expiryDate : String? = null

    var tableStatus : String? = null

    var price : String? = null

    var toppinsGroupId : String? = null

    var toppinGroupReferenceCode : String? = null

    var completeAddress : String? = null

    var floor : String? = null

    var menuId : String? = null

    var toppinType : String? = null

    var toppinsList: MutableList<ProductListView>? = null

    var isBookingAdmin : String? = null

    var bookingAmount : String? = null

    var tableMemberReferenceCode : String? = null

    var memberStatus : String? = null

    var payType : String? = null

    var paymentMode : String? = null

    var bookingDate : String? = null

    var startTime : String? = null

    var endTime : String? = null

    var reviewCount : String? = null

    var bookingInviteGroupId : String? = null

    var groupName : String? = null

    var groupReferenceCode : String? = null

    var groceryName : String? = null

    var description : String? = null

    var addressType : String? = null

    var restaurantName : String? = null

    var itemsPreparationTime : String? = null

    var restaurantReferenceCode : String? = null

    var folderName : String? = null

    var bookingOrderStatus : String? = null

    var orderOtp : String? = null

    var groceryImages : String? = null

    var groceryReferenceCode : String? = null

    var street : String? = null

    var customerAddressId : String? = null

    var latitude : String? = null

    var longitude : String? = null

    var bookingCustomerLatitude : String? = null

    var bookingOrderReferenceCode : String? = null



    var bookingCustomerLongitude : String? = null

    var customerId : String? = null

    var town : String? = null

    var totalAmount : String? =null

    var numberOfPeron : String? = null

    var bookingCustomerAddress : String? = null

    var customerFirstname : String? = null

    var quantity : String? = null

    var tableBookingReferenceCode : String? = null

    var bookingOrderCode : String? = null

    var bookingCode : String? = null

    var itemList : String? = null

    var created_at : String? = null

    var rating : String? = null

    var cuisionName : String? = null

    var boughtSubscriptionReferenceCode : String? = null

    var assignedService : String? = null

    var assignedBlock : String? = null

    var pinLocation : String? = null

    var referenceCode5 : String? =null

    var image : String? = null

    var businessName : String? = null

    var scaleId : String? = null

    var scale : String? = null

    var fullBusinessAddress : String? = null

    var checkIn : String? = null

    var checkOut : String? = null

    var myBookingReferenceCode : String? = null

    var bookingStatus : String? = null

    var email : String? = null

    var businessPartnerId : String? = null

    var city: String? = null

    var title : String? = null

    var answers : String? = null

    var specialization_name: String? = null

    var course_name: String? = null

    var name_of_degree : String? = null

    var privacy_policy : String? = null

    var image_upload : String? = null

    var university_name : String? = null

    var college_name : String? = null

    var broucher_upload : String? = null

    var college_type_id : String? = null

    var locations_id: String? = null

    var placements_id : String? = null

    var accreditations_id : String? = null

    var ownerships_id : String? = null

    var approvals_id : String? = null

    var offers_id : String? = null

    var application_charges_id: String? = null

    var service_charges_id : String? = null

    var rank_id : String? = null

    var featured : String? = null

    var top_colleges : String? = null

    var reviews_id : String? = null

    var review_type: String? = null

    var maximum_value : String? = null

    var college_information_id: String? = null

    var course_list_id : String? = null

    var course_year : String? = null

    var course_id : String? = null

    var college_id : String? = null

    var admission_id : String? = null

    var accreditation_id : String? = null

    var specialization_id: String? = null

    var course_status_id : String? = null

    var course_demand_id : String? = null

    var mode_of_studies_id : String? = null

    var mode_of_study_name : String? = null

    var name : String? = null

    var promocode : String? = null

    var promoType : String? = null

    var restaurantPromoId : String? = null

    var groceryPromoId : String? = null

    var promoValue : String? = null

    var address : String? = null

    var categoryName : String? = null

    var state : String? = null

    var openningClosingDay : String? = null

    var openingTime : String? = null

    var closingTime : String? = null

    var pincode : String? = null

    var postcode : String? = null

    var businessReferenceCode : String? = null

    var addressReferenceCode : String? = null

    var area : String? = null

    var distance : String? = null

    var restaurantImages : String? = null

    var bannerImage : String? = null

    var categoryId :String? = null

    var categoryUploadFileId : String? = null

    var uploadFiles : String? = null

    var mobile_number : String? = null


    var mobile : String? = null

    var email_id : String? = null

    var language : String? = null

    var key : String? = null

    var restaurantOpenStatus : String? = null

    var restaurantData: WorkExperience? = null

    var groceryData: WorkExperience? = null

    var groupMemberList: MutableList<WorkExperience>? = null



    var favoriteList: MutableList<WorkExperience>? = null

    var restaurantListing: MutableList<WorkExperience>? = null

    var offerPlacedRestaurantList: MutableList<WorkExperience>? = null

    var offerPlacedStoreList: MutableList<WorkExperience>? = null

    var featureRestaurantList: MutableList<WorkExperience>? = null

    var cuisionRestaurantList: MutableList<WorkExperience>? = null

    var recommendedRestaurantList: MutableList<WorkExperience>? = null

    var groceryListing: MutableList<WorkExperience>? = null

    var groceryCategoryList: MutableList<WorkExperience>? = null

    var recommendedGroceryList: MutableList<WorkExperience>? = null

    var featureStoreList: MutableList<WorkExperience>? = null

    var educationalBackground: MutableList<EducationalBackground>? = null

    var categoryUploadFileList: MutableList<EducationPreference>? = null

    var categoryData: MutableList<AdapterModel>? = null

    var groceryCategoryData : MutableList<EducationPreference>? = null



    var amenitieList: MutableList<EducationPreference>? = null

    var serviceList: MutableList<EducationPreference>? = null

    var exploreList: MutableList<EducationPreference>? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        city = parcel.readString()
        specialization_name = parcel.readString()
        course_name = parcel.readString()
        name_of_degree = parcel.readString()
        privacy_policy = parcel.readString()
        image_upload = parcel.readString()
        university_name = parcel.readString()
        college_name = parcel.readString()
        broucher_upload = parcel.readString()
        college_type_id = parcel.readString()
        locations_id = parcel.readString()
        placements_id = parcel.readString()
        accreditations_id = parcel.readString()
        ownerships_id = parcel.readString()
        approvals_id = parcel.readString()
        offers_id = parcel.readString()
        application_charges_id = parcel.readString()
        service_charges_id = parcel.readString()
        rank_id = parcel.readString()
        featured = parcel.readString()
        top_colleges = parcel.readString()
        reviews_id = parcel.readString()
        review_type = parcel.readString()
        maximum_value = parcel.readString()
        college_information_id = parcel.readString()
        course_list_id = parcel.readString()
        course_year = parcel.readString()
        course_id = parcel.readString()
        college_id = parcel.readString()
        admission_id = parcel.readString()
        accreditation_id = parcel.readString()
        specialization_id = parcel.readString()
        course_status_id = parcel.readString()
        course_demand_id = parcel.readString()
        mode_of_studies_id = parcel.readString()
        mode_of_study_name = parcel.readString()
        name = parcel.readString()
        address = parcel.readString()
        categoryName = parcel.readString()
        state = parcel.readString()
        openningClosingDay = parcel.readString()
        openingTime = parcel.readString()
        closingTime = parcel.readString()
        pincode = parcel.readString()
        businessReferenceCode = parcel.readString()
        addressReferenceCode = parcel.readString()
        area = parcel.readString()
        distance = parcel.readString()
        folderName = parcel.readString()
        bannerImage = parcel.readString()
        categoryId = parcel.readString()
        categoryUploadFileId = parcel.readString()
        uploadFiles = parcel.readString()
        mobile_number = parcel.readString()
        email_id = parcel.readString()
        language = parcel.readString()
        key = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(city)
        parcel.writeString(specialization_name)
        parcel.writeString(course_name)
        parcel.writeString(name_of_degree)
        parcel.writeString(privacy_policy)
        parcel.writeString(image_upload)
        parcel.writeString(university_name)
        parcel.writeString(college_name)
        parcel.writeString(broucher_upload)
        parcel.writeString(college_type_id)
        parcel.writeString(locations_id)
        parcel.writeString(placements_id)
        parcel.writeString(accreditations_id)
        parcel.writeString(ownerships_id)
        parcel.writeString(approvals_id)
        parcel.writeString(offers_id)
        parcel.writeString(application_charges_id)
        parcel.writeString(service_charges_id)
        parcel.writeString(rank_id)
        parcel.writeString(featured)
        parcel.writeString(top_colleges)
        parcel.writeString(reviews_id)
        parcel.writeString(review_type)
        parcel.writeString(maximum_value)
        parcel.writeString(college_information_id)
        parcel.writeString(course_list_id)
        parcel.writeString(course_year)
        parcel.writeString(course_id)
        parcel.writeString(college_id)
        parcel.writeString(admission_id)
        parcel.writeString(accreditation_id)
        parcel.writeString(specialization_id)
        parcel.writeString(course_status_id)
        parcel.writeString(course_demand_id)
        parcel.writeString(mode_of_studies_id)
        parcel.writeString(mode_of_study_name)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(categoryName)
        parcel.writeString(state)
        parcel.writeString(openningClosingDay)
        parcel.writeString(openingTime)
        parcel.writeString(closingTime)
        parcel.writeString(pincode)
        parcel.writeString(businessReferenceCode)
        parcel.writeString(addressReferenceCode)
        parcel.writeString(area)
        parcel.writeString(distance)
        parcel.writeString(folderName)
        parcel.writeString(bannerImage)
        parcel.writeString(categoryId)
        parcel.writeString(categoryUploadFileId)
        parcel.writeString(uploadFiles)
        parcel.writeString(mobile_number)
        parcel.writeString(email_id)
        parcel.writeString(language)
        parcel.writeString(key)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultRes> {
        override fun createFromParcel(parcel: Parcel): ResultRes {
            return ResultRes(parcel)
        }

        override fun newArray(size: Int): Array<ResultRes?> {
            return arrayOfNulls(size)
        }
    }

}