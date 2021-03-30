package com.lieferin_global.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class AdapterModel(var image: Int, var categoryName: String?, var price: String?, var offerPrice: String?, var offerPercentage: String?, var categoryImage: String?, var offer: String?, var parlourAddress: String?, var parlourRatingValue: String?, var categoryReferenceCode: String?, var parlourRatingCount: String?, var parlourGivenRating: String?, var categoryId: String?, var shopTotRate: String?, var menuImages: String?, var openTime: String?, var closeTime: String?, var noOfEmp: String?, var imageOne: Int, var imageTwo: Int, var imageThree: Int, var menusList: MutableList<Product>) : Serializable,
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        TODO("menusList")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
        parcel.writeString(categoryName)
        parcel.writeString(price)
        parcel.writeString(offerPrice)
        parcel.writeString(offerPercentage)
        parcel.writeString(categoryImage)
        parcel.writeString(offer)
        parcel.writeString(parlourAddress)
        parcel.writeString(parlourRatingValue)
        parcel.writeString(categoryReferenceCode)
        parcel.writeString(parlourRatingCount)
        parcel.writeString(parlourGivenRating)
        parcel.writeString(categoryId)
        parcel.writeString(shopTotRate)
        parcel.writeString(menuImages)
        parcel.writeString(openTime)
        parcel.writeString(closeTime)
        parcel.writeString(noOfEmp)
        parcel.writeInt(imageOne)
        parcel.writeInt(imageTwo)
        parcel.writeInt(imageThree)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AdapterModel> {
        override fun createFromParcel(parcel: Parcel): AdapterModel {
            return AdapterModel(parcel)
        }

        override fun newArray(size: Int): Array<AdapterModel?> {
            return arrayOfNulls(size)
        }
    }
}