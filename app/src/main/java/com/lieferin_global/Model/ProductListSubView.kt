package com.lieferin_global.Model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class ProductListSubView(var imageIcon: Int, var name: String?, var price: String?, var toppinsGroupId: String?, var toppinType: String?, var toppinsId: String?, var toppinsGroupReferenceCode: String?, var menuId: String?, var typeView: String?, var toppinsList: MutableList<ProductSubListView>) : Serializable,
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
        parcel.writeString(toppinType)
        parcel.writeString(toppinsId)
        parcel.writeString(toppinsGroupReferenceCode)
        parcel.writeString(menuId)
        parcel.writeString(typeView)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductListSubView> {
        override fun createFromParcel(parcel: Parcel): ProductListSubView {
            return ProductListSubView(parcel)
        }

        override fun newArray(size: Int): Array<ProductListSubView?> {
            return arrayOfNulls(size)
        }
    }
}