package com.lieferin_global.webservices.request

import android.os.Parcel

import android.os.Parcelable


class SaveINfo() : Parcelable {
    var first_name: String?= null

    var middle_name: String?= null

    var last_name: String?= null

    var dob: String?= null

    var gender: String?= null

    var categories: String?= null

    var course_id: String?= null

    var specialization_id: String?= null

    var father_name: String?= null

    var father_occupation: String?= null

    var mother_name: String?= null

    var mother_occupation: String?= null

    var email_id: String?= null

    var mobile_number: String?= null

    var permanent_address: String?= null

    var temporary_address: String?= null

    var NRI: String?= null

    var stream: String?= null

    var admin_note: String?= null

    var college_id: String?= null

    var educationINfo: MutableList<EducationINfo>? = null

    var examINfo: MutableList<ExamINfo>? = null

    var uploadINfo: MutableList<UploadINfo>? = null

    constructor(parcel: Parcel) : this() {
        first_name = parcel.readString()
        middle_name = parcel.readString()
        last_name = parcel.readString()
        dob = parcel.readString()
        gender = parcel.readString()
        categories = parcel.readString()
        course_id = parcel.readString()
        specialization_id = parcel.readString()
        father_name = parcel.readString()
        father_occupation = parcel.readString()
        mother_name = parcel.readString()
        mother_occupation = parcel.readString()
        email_id = parcel.readString()
        mobile_number = parcel.readString()
        permanent_address = parcel.readString()
        temporary_address = parcel.readString()
        college_id = parcel.readString()
        NRI = parcel.readString()
        stream = parcel.readString()
        admin_note = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(first_name)
        parcel.writeString(middle_name)
        parcel.writeString(last_name)
        parcel.writeString(dob)
        parcel.writeString(gender)
        parcel.writeString(categories)
        parcel.writeString(course_id)
        parcel.writeString(specialization_id)
        parcel.writeString(father_name)
        parcel.writeString(father_occupation)
        parcel.writeString(mother_name)
        parcel.writeString(mother_occupation)
        parcel.writeString(email_id)
        parcel.writeString(mobile_number)
        parcel.writeString(permanent_address)
        parcel.writeString(temporary_address)
        parcel.writeString(NRI)
        parcel.writeString(stream)
        parcel.writeString(admin_note)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SaveINfo> {
        override fun createFromParcel(parcel: Parcel): SaveINfo {
            return SaveINfo(parcel)
        }

        override fun newArray(size: Int): Array<SaveINfo?> {
            return arrayOfNulls(size)
        }
    }
}


