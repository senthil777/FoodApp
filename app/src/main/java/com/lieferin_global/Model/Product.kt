package com.lieferin_global.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Product(
    var imageIcon: Int, var name: String?, var menuId: String?, var price: String?, var menuReferenceCode: String?, var allergy1: String?, var categoryId: String?, var offerPrice: String?, var typeView: String?,
    var quantity: String?,
    var menuImages: String?,
    var type: String?,
    var offerType: String?,
    var offer: String?, var description: String?, var allergy: String?, var bookingCode: String?, var sample: String?, var toppinsGroupList: MutableList<ProductList>) : Serializable,Parcelable {
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
        TODO("toppinsGroupList")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(imageIcon)
        parcel.writeString(name)
        parcel.writeString(menuId)
        parcel.writeString(price)
        parcel.writeString(menuReferenceCode)
        parcel.writeString(allergy1)
        parcel.writeString(categoryId)
        parcel.writeString(offerPrice)
        parcel.writeString(typeView)
        parcel.writeString(quantity)
        parcel.writeString(menuImages)
        parcel.writeString(type)
        parcel.writeString(offerType)
        parcel.writeString(offer)
        parcel.writeString(description)
        parcel.writeString(allergy)
        parcel.writeString(bookingCode)
        parcel.writeString(sample)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}

