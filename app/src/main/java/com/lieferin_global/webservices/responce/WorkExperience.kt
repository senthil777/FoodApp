package com.lieferin_global.webservices.responce

import android.os.Parcel
import android.os.Parcelable
import com.lieferin_global.Model.ProductSubListViewObj
import com.lieferin_global.webservices.request.ImageINfo
import java.io.Serializable

class WorkExperience() : BaseRS(), Serializable, Parcelable {

    var id:String? = null

    var menuId:String? = null

    var serviceBlockUploadImageId : String? = null

    var storeCategoryList :  MutableList<ImageINfo>? = null

    var menuReferenceCode:String? = null

    var bookingInviteGroupId : String? = null

    var groupMemberReferenceCode:String? = null

    var email:String? = null

    var mobile : String? = null

    var images : String? = null

    var categoryId:String? = null

    var offerPrice:String? = null

    var categoryReferenceCode : String? = null

    var productReferenceCode : String?=null

    var productId : String?=null

    var offerType : String?=null

    var productName : String?=null

    var brandId : String?=null

    var quantity : String?=null

    var productContent : String?=null

    var metaDescription : String?=null

    var categoryName : String?=null

    var productCode : String?=null

    var productImages : String?=null

    var groceryReferenceCode:String? = null

    var groceryName : String? = null

    var groceryImages:String? = null

    var groceryLogo : String? = null

    var description : String? = null

    var categoryImage : String? = null

     var deliveryCollectionStatus : String? = null

    var slotTime : String? = null

    var startTime : String? = null

    var endTime : String? = null

    var distance : String? = null

    var availableTable : String? = null

    var bookingDate : String? = null

    var totalPerson : String? = null

    var offerPercentage : String? = null

    var restaurantImagesList : String? = null

    var restaurantImages : String? = null

    var folderName : String? = null

    var blockName : String? = null

    var blockId : String? = null

    var name : String? = null

    var totalPrice : String? = null

    var user_id:String? = null

    var experience:String? = null

    var employer_name:String? = null

    var designation:String? = null

    var department:String? = null

    var current_job:String? = null

    var created_at:String? = null

    var updated_at:String? = null

    var price:String? = null

    var minimumOrderAmount:String? = null

    var minimumOrder:String? = null

    var website : String? = null

    var blockImageList: MutableList<ImageINfo>? = null

    var exploreImageList: MutableList<ImageINfo>? = null

    var restaurantName : String? = null

    var openStatus : String? = null

    var openingTime : String? = null

    var closingTime : String? = null

    var restaurantId : String? = null

    var groceryId : String? = null

    var restaurantReferenceCode : String? = null

    var street : String? = null

    var itemName : String? = null

    //var productName : String? = null

    var rating : String? = null

    var town : String? = null

    var cuisionName : String? = null

    var cuisineList : String? = null

    var imagePath : String? = null

    var toppinsGroupList: MutableList<EducationalBackground>? = null

    var toppinsGroupJsonData: ProductSubListViewObj? = null


    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        serviceBlockUploadImageId = parcel.readString()
        images = parcel.readString()
        blockName = parcel.readString()
        blockId = parcel.readString()
        user_id = parcel.readString()
        experience = parcel.readString()
        employer_name = parcel.readString()
        designation = parcel.readString()
        department = parcel.readString()
        current_job = parcel.readString()
        created_at = parcel.readString()
        updated_at = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(serviceBlockUploadImageId)
        parcel.writeString(images)
        parcel.writeString(blockName)
        parcel.writeString(blockId)
        parcel.writeString(user_id)
        parcel.writeString(experience)
        parcel.writeString(employer_name)
        parcel.writeString(designation)
        parcel.writeString(department)
        parcel.writeString(current_job)
        parcel.writeString(created_at)
        parcel.writeString(updated_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WorkExperience> {
        override fun createFromParcel(parcel: Parcel): WorkExperience {
            return WorkExperience(parcel)
        }

        override fun newArray(size: Int): Array<WorkExperience?> {
            return arrayOfNulls(size)
        }
    }
}