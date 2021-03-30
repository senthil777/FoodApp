package com.lieferin_global.webservices.responce

import com.lieferin_global.webservices.request.ImageINfo
import java.io.Serializable

class itemDetails : Serializable{

    var id:String? = null

    var serviceBlockUploadImageId : String? = null

    var images : String? = null

    var blockName : String? = null

    var blockId : String? = null

    var name : String? = null

    var user_id:String? = null

    var experience:String? = null

    var employer_name:String? = null

    var designation:String? = null

    var department:String? = null

    var current_job:String? = null

    var created_at:String? = null

    var updated_at:String? = null

    var price:String? = null

    var dishCategoryId : String? = null

    var blockImageList: MutableList<ImageINfo>? = null

    var exploreImageList: MutableList<ImageINfo>? = null

    var restaurantName : String? = null

    var street : String? = null

    var town : String? = null

    var cuisionName : String? = null

    var distance : String? = null


}