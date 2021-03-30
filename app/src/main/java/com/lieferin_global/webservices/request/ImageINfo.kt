package com.lieferin_global.webservices.request

import android.os.Parcel
import android.os.Parcelable
import com.lieferin_global.webservices.responce.BaseRS
import java.io.Serializable

class ImageINfo() : BaseRS(), Serializable, Parcelable {

    var year_of_passing: String?= null

    var marks_obtained: String?= null

    var remarks: String?= null

    var categoryName: String?= null

    var education_id: String?= null

    var images :  String?= null

    var name :  String?= null

    var price : String? = null

    var menuId : String? = null

    var menuUserRequest : String? = null

    constructor(parcel: Parcel) : this() {
        year_of_passing = parcel.readString()
        marks_obtained = parcel.readString()
        remarks = parcel.readString()
        education_id = parcel.readString()
        images = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(year_of_passing)
        parcel.writeString(marks_obtained)
        parcel.writeString(remarks)
        parcel.writeString(education_id)
        parcel.writeString(images)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageINfo> {
        override fun createFromParcel(parcel: Parcel): ImageINfo {
            return ImageINfo(parcel)
        }

        override fun newArray(size: Int): Array<ImageINfo?> {
            return arrayOfNulls(size)
        }
    }


}