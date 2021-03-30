package com.lieferin_global.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class ProductSubListViewObj(var imageIcon: Int, var name: String?, var price: String?, var toppinsGroupId: String?, var headerSub: String?, var toppinsId: String?, var toppinsReferenceCode: String?, var menuId: String?, var typeView: String?,var toppinsGroupList: MutableList<ProductListSubView>) : Serializable,
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
        TODO("toppinsGroupList")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(imageIcon)
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeString(toppinsGroupId)
        parcel.writeString(headerSub)
        parcel.writeString(toppinsId)
        parcel.writeString(toppinsReferenceCode)
        parcel.writeString(menuId)
        parcel.writeString(typeView)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductSubListViewObj> {
        override fun createFromParcel(parcel: Parcel): ProductSubListViewObj {
            return ProductSubListViewObj(parcel)
        }

        override fun newArray(size: Int): Array<ProductSubListViewObj?> {
            return arrayOfNulls(size)
        }
    }
}