package com.lieferin_global.webservices.responce

import com.lieferin_global.webservices.request.ImageINfo
import java.io.Serializable

class EducationalBackground : BaseRS(), Serializable {

    var id:String? = null

    var course_level:String? = null

    var school_name:String? = null

    var course_completion_year:String? = null

    var board:String? = null

    var subjects:String? = null

    var marks:String? = null

    var user_id: String? = null

    var created_at:String? = null

    var updated_at:String? = null

    var toppinsList: MutableList<ImageINfo>? = null

}