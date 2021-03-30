package com.lieferin_global.webservice

import android.util.Log
import com.google.gson.Gson
import com.lieferin_global.webservices.responce.BaseRS

object Parser {
    @Synchronized
    fun getLoginResponce(response: String): BaseRS? {
        return try {
            Log.d("", response)
            val gson = Gson()
            // converting or parsing the content
            gson.fromJson<BaseRS>(response, BaseRS::class.java)
        } catch (e: IllegalStateException) {
            Log.e("", "MyResponse WS Parsing failed in Parser")
            e.printStackTrace()
            null
        } catch (e: Exception) {
            Log.e("", "MyResponse WS Parsing failed in Parser")
            e.printStackTrace()
            null
        }
    }
}
