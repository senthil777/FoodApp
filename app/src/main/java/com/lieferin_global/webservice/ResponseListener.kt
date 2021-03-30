package com.lieferin_global.webservice

interface ResponseListener {
    fun onResponseReceived(responseObj: Any?, requestType: Int)
}