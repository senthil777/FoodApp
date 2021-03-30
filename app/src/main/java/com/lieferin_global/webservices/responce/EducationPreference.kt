package com.lieferin_global.webservices.responce

import com.lieferin_global.Model.Product
import java.io.Serializable

class EducationPreference : BaseRS(), Serializable {
    var id:String? = null

    var education_stream:String? = null

    var categoryName:String? = null

    var categoryImage:String? = null

    var categoryId :String? = null

    var productData: MutableList<WorkExperience>? = null

    var amenitieEnglish : String? = null

    var amenitieSomalia : String? = null

    var serviceName : String? = null

    var blockName : String? = null

    var exploreBlockId : String? = null

    var serviceReferenceCode : String? = null

    var description : String? = null

    var serviceId : String? = null

    var perDayPrice : String? = null

    var course:String? = null

    var specialization:String? = null

    var mode_of_study:String? = null

    var user_id:String? = null

    var created_at:String? = null

    var updated_at:String? = null

    var uploadFiles : String? = null

    var mobile : String? = null

    var amenitie : String? = null

    var menusList: MutableList<Product>? = null

    var exploreImageList: MutableList<WorkExperience>? = null

    var blockImageList: MutableList<WorkExperience>? = null
}