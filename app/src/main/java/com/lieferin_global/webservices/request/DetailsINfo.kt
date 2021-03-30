package com.lieferin_global.webservices.request

import android.os.Parcel

import android.os.Parcelable


class DetailsINfo() : Parcelable {
    var userReferenceCode : String? = null

    var id : String? = null

    var mobile : String? = null

    var email : String? = null

    var name : String? = null

    var token : String? = null

    var firstname : String? = null

    var profilePicture : String? = null

    var address1 : String? = null

    var address : String? = null
    var customerReferenceCode : String? = null

    constructor(parcel: Parcel) : this() {

        name = parcel.readString()

        firstname = parcel.readString()

        address1 = parcel.readString()

        mobile = parcel.readString()

        email = parcel.readString()

        userReferenceCode = parcel.readString()

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)

        parcel.writeString(firstname)

        parcel.writeString(userReferenceCode)

        parcel.writeString(mobile)

        parcel.writeString(email)

        parcel.writeString(address1)


    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetailsINfo> {
        override fun createFromParcel(parcel: Parcel): DetailsINfo {
            return DetailsINfo(parcel)
        }

        override fun newArray(size: Int): Array<DetailsINfo?> {
            return arrayOfNulls(size)
        }
    }
}


