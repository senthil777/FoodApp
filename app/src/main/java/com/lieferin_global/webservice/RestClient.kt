package com.lieferin_global.webservice

import android.app.Activity
import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import com.lieferin_global.Utility.Constant
import com.lieferin_global.Utility.showToast
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import org.json.JSONObject
import java.util.*

class RestClient(private val url: String, private val requestType: Int) {
    val basicAuth = ("Basic "
            + Base64.encodeToString(
        URL_PWD.toByteArray(),
        Base64.NO_WRAP
    ))
    //AppState appState;
    private val headers: ArrayList<NameValuePair>
    private val params: ArrayList<NameValuePair>
    fun addParam(name: String?, value: String?) {
        params.add(BasicNameValuePair(name, value))
    }

    fun addHeader(name: String?, value: String?) {
        headers.add(BasicNameValuePair(name, value))
    }

    @Throws(Exception::class)
    fun execute(
        method: RequestMethod?, activity: Context,
        fragment: Fragment
    ) {
        Log.d("", "Request params $url")
        // For debugging
        for (p in params) {
            Log.i("==========", "Setting param :" + p.name + " = " + p.value)
        }
        postData(url, activity, fragment as ResponseListener)
        /*   if (Utility.internetIsAvailable(activity)) {
            postData(url, activity, (ResponseListener) fragment);
        } else {
            Utility.hideProgressDialog(activity);
            Utility.showConnectionNotAvailable(activity);

        }*/
    }

    @Throws(Exception::class)
    fun executeJsonFragment(
        method: RequestMethod?, activity: Context,
        fragment: Fragment, jsonObject: JSONObject
    ) {
        Log.d("", "Request params $url")
        // For debugging
        for (p in params) {
            Log.i("==========", "Setting param :" + p.name + " = " + p.value)
        }
        postDataJson(url, activity, fragment as ResponseListener, jsonObject)
        Log.v("" + jsonObject, "")
        /*   if (Utility.internetIsAvailable(activity)) {
            postData(url, activity, (ResponseListener) fragment);
        } else {
            Utility.hideProgressDialog(activity);
            Utility.showConnectionNotAvailable(activity);

        }*/
    }

    @Throws(Exception::class)
    fun executeJson(
        method: RequestMethod?, activity: Context,
        fragment: Activity, jsonObject: JSONObject
    ) {
        Log.d("", "Request params $url")
        // For debugging
        for (p in params) {
            Log.i("==========", "Setting param :" + p.name + " = " + p.value)
        }
        postDataJson(url, activity, fragment as ResponseListener, jsonObject)
        Log.v("" + jsonObject, "")
        /*   if (Utility.internetIsAvailable(activity)) {
            postData(url, activity, (ResponseListener) fragment);
        } else {
            Utility.hideProgressDialog(activity);
            Utility.showConnectionNotAvailable(activity);

        }*/
    }

    fun executeJsonPut(
        method: RequestMethod?, activity: Context,
        fragment: Activity, jsonObject: JSONObject
    ) {
        Log.d("", "Request params $url")
        // For debugging
        for (p in params) {
            Log.i("==========", "Setting param :" + p.name + " = " + p.value)
        }
        postDataJsonPut(url, activity, fragment as ResponseListener, jsonObject)
        Log.v("" + jsonObject, "")
        /*   if (Utility.internetIsAvailable(activity)) {
            postData(url, activity, (ResponseListener) fragment);
        } else {
            Utility.hideProgressDialog(activity);
            Utility.showConnectionNotAvailable(activity);

        }*/
    }

    fun executeJsonGetFragment(
        method: RequestMethod?, activity: Context,
        fragment: Fragment, jsonObject: JSONObject
    ) {
        Log.d("", "Request params $url")
        // For debugging
        for (p in params) {
            Log.i("==========", "Setting param :" + p.name + " = " + p.value)
        }
        postDataJsonGet(url, activity, fragment as ResponseListener, jsonObject)
        Log.v("" + jsonObject, "URL == "+url)
        /*   if (Utility.internetIsAvailable(activity)) {
            postData(url, activity, (ResponseListener) fragment);
        } else {
            Utility.hideProgressDialog(activity);
            Utility.showConnectionNotAvailable(activity);

        }*/
    }

    fun executeJsonGet(
        method: RequestMethod?, activity: Context,
        fragment: Activity, jsonObject: JSONObject
    ) {
        Log.d("", "Request params $url")
        // For debugging
        for (p in params) {
            Log.i("==========", "Setting param :" + p.name + " = " + p.value)
        }
        postDataJsonGet(url, activity, fragment as ResponseListener, jsonObject)
        Log.v("" + jsonObject, "")
        /*   if (Utility.internetIsAvailable(activity)) {
            postData(url, activity, (ResponseListener) fragment);
        } else {
            Utility.hideProgressDialog(activity);
            Utility.showConnectionNotAvailable(activity);

        }*/
    }

    fun executeJsonGetFragment(
        method: RequestMethod?, activity: Context,
        fragment: Activity, jsonObject: JSONObject
    ) {
        Log.d("", "Request params $url")
        // For debugging
        for (p in params) {
            Log.i("==========", "Setting param :" + p.name + " = " + p.value)
        }
        postDataJsonGet(url, activity, fragment as ResponseListener, jsonObject)
        Log.v("" + jsonObject, "")
        /*   if (Utility.internetIsAvailable(activity)) {
            postData(url, activity, (ResponseListener) fragment);
        } else {
            Utility.hideProgressDialog(activity);
            Utility.showConnectionNotAvailable(activity);

        }*/
    }

    @Throws(Exception::class)
    fun executeActivity(
        method: RequestMethod?, activity: Context,
        fragment: Activity
    ) {
        Log.d("", "Request params $url")
        // For debugging
        for (p in params) {
            Log.i("==========", "Setting param :" + p.name + " = " + p.value)
        }
        postData(url, activity, fragment as ResponseListener)
        /*   if (Utility.internetIsAvailable(activity)) {
            postData(url, activity, (ResponseListener) fragment);
        } else {
            Utility.hideProgressDialog(activity);
            Utility.showConnectionNotAvailable(activity);

        }*/
    }

    @Throws(Exception::class)
    fun executeActivity1(
        method: RequestMethod?, activity: Context,
        fragment: Activity
    ) {
        Log.d("", "Request params $url")
        // For debugging
        for (p in params) {
            Log.i("==========", "Setting param :" + p.name + " = " + p.value)
        }
        postData(url, activity, fragment as ResponseListener)
        /*   if (Utility.internetIsAvailable(activity)) {
            postData(url, activity, (ResponseListener) fragment);
        } else {
            Utility.hideProgressDialog(activity);
            Utility.showConnectionNotAvailable(activity);

        }*/
    }

    private fun postData(
        url: String, activity: Context,
        resplist: ResponseListener
    ) {
        try { //appState = (AppState) activity.getApplicationContext();
            queue = Volley.newRequestQueue(activity)
            val timeout = 60000 // 60 seconds - time out
            val postRequest: StringRequest =
                object : StringRequest(
                    Method.POST, url,
                    Response.Listener { response ->
                        // response
                        Log.d("Response", response)
                        // try {
                        val `object` = invokeParser(response, requestType)
                        if (`object` == null) { //                                Utility.hideProgressDialog(activity);
                            //                                Utility.showAlertDialog(activity, "Connection Problem. Unable to fetch the data");
                        }
                        //Log.v("URL", "--" + object.toString());
                        resplist.onResponseReceived(`object`, requestType)
                    }, Response.ErrorListener { error ->
                        // error
                        Log.d("Error.Response", "" + error.message)
                        //Utility.hideProgressDialog(activity);
                        if (error.message == null) { //   Utility.showAlertDialog(activity, "Connection Problem. Please try after some time");
                        }
                        resplist.onResponseReceived(null, requestType)
                    }) {
                    override fun getBodyContentType(): String {
                        return "application/x-www-form-urlencoded; charset=UTF-8"
                    }

                    override fun getParams(): Map<String, String> {
                        val pvalues: MutableMap<String, String> =
                            HashMap()
                        for (p in params) {
                            //pvalues["" + p.getName()] = "" + p.getValue()
                        }
                        return pvalues
                    }
                }
            // Request Time out
            postRequest.retryPolicy = DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            postRequest.retryPolicy = DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            System.setProperty("http.keepAlive", "false")
            // Default request queue
            queue!!.add(postRequest)
        } catch (e: Exception) {
        }
    }

    private fun postDataJson(
        url: String, activity: Context,
        resplist: ResponseListener, jsonObject: JSONObject
    ) {
        Log.v("jjjj", "kkkk")
        try {
            Log.v("jjjj", "kkkk")
            queue = Volley.newRequestQueue(activity)
            val timeout = 10000 // 60 seconds - time out
            val req: JsonObjectRequest =
                object : JsonObjectRequest(
                    Method.POST, url, jsonObject,
                    Response.Listener { response ->
                        Log.d(VolleyLog.TAG, response.toString())
                        // response
                        Log.d("Response", response.toString())
                        // try {
                        val `object` =
                            invokeParser(response.toString(), requestType)
                        if (`object` == null) {
                            //Utility.hideProgressDialog(activity);
                            //Utility.showAlertDialog(activity, "Connection Problem. Unable to fetch the data");
                        }
                        //Log.v("URL", "--" + object.toString());
                        resplist.onResponseReceived(`object`, requestType)
                    }, Response.ErrorListener { error ->
                        VolleyLog.d(VolleyLog.TAG, "Errobbbbr: " + error.message)
                        var message = ""
                        if (error is NetworkError) {
                            message = "Please Check Your Internet Connection"

                            //loadingScreenClose(activity)

                            showToast(activity,message)

                        } else if (error is TimeoutError) {
                            message =
                                "Connection TimeOut! Please check your internet connection."

                            //loadingScreenClose(activity)

                            showToast(activity,message)
                        }
                        if (message != "") { //Utility.toastMsg(activity, message);
                        }
                        //Utility.loadingScreenClose(activity);
                        resplist.onResponseReceived(null, requestType)
                    }) {
                    /**
                     * Passing some request headers
                     */
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers =
                            HashMap<String, String>()
                        headers["Content-Type"] = "application/json"
                        headers["apiKey"] = "xxxxxxxxxxxxxxx"
                        return headers
                    }
                }
            // Request Time out
            req.retryPolicy = DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            req.retryPolicy = DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            req.retryPolicy = object : RetryPolicy {
                override fun getCurrentTimeout(): Int {
                    return timeout
                }

                override fun getCurrentRetryCount(): Int {
                    return timeout
                }

                @Throws(VolleyError::class)
                override fun retry(error: VolleyError) {
                    VolleyLog.d(VolleyLog.TAG, "Errobbbbr: " + error.message)
                }
            }
            System.setProperty("http.keepAlive", "false")
            // Default request queue
            queue!!.add(req)
        } catch (e: Exception) {
            Log.v("jjjj", "kkkk")
        }
    }
    private fun postDataJsonGet(
        url: String, activity: Context,
        resplist: ResponseListener, jsonObject: JSONObject
    ) {
        Log.v("jjjj", "kkkk")
        try {
            Log.v("jjjj", "kkkk")
            queue = Volley.newRequestQueue(activity)
            val timeout = 10000 // 60 seconds - time out
            val req: JsonObjectRequest =
                object : JsonObjectRequest(
                    Method.GET, url, jsonObject,
                    Response.Listener { response ->
                        Log.d(VolleyLog.TAG, response.toString())
                        // response
                        Log.d("Response", response.toString())
                        // try {
                        val `object` =
                            invokeParser(response.toString(), requestType)
                        if (`object` == null) { //                                Utility.hideProgressDialog(activity);
                            //                                Utility.showAlertDialog(activity, "Connection Problem. Unable to fetch the data");
                        }
                        //Log.v("URL", "--" + object.toString());
                        resplist.onResponseReceived(`object`, requestType)
                    }, Response.ErrorListener { error ->
                        VolleyLog.d(VolleyLog.TAG, "Errobbbbr: " + error.message)
                        var message = ""
                        if (error is NetworkError) {
                            message = "Please Check Your Internet Connection"
                        } else if (error is TimeoutError) {
                            message =
                                "Connection TimeOut! Please check your internet connection."
                        }
                        if (message != "") { //Utility.toastMsg(activity, message);
                        }
                        //Utility.loadingScreenClose(activity);
                        resplist.onResponseReceived(null, requestType)
                    }) {
                    /**
                     * Passing some request headers
                     */
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers =
                            HashMap<String, String>()
                        headers["Content-Type"] = "application/json"
                        headers["apiKey"] = "xxxxxxxxxxxxxxx"
                        return headers
                    }
                }
            // Request Time out
            req.retryPolicy = DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            req.retryPolicy = DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            req.retryPolicy = object : RetryPolicy {
                override fun getCurrentTimeout(): Int {
                    return timeout
                }

                override fun getCurrentRetryCount(): Int {
                    return timeout
                }

                @Throws(VolleyError::class)
                override fun retry(error: VolleyError) {
                    VolleyLog.d(VolleyLog.TAG, "Errobbbbr: " + error.message)
                }
            }
            System.setProperty("http.keepAlive", "false")
            // Default request queue
            queue!!.add(req)
        } catch (e: Exception) {
            Log.v("jjjj", "kkkk")
        }
    }

    private fun postDataJsonPut(
        url: String, activity: Context,
        resplist: ResponseListener, jsonObject: JSONObject
    ) {
        Log.v("jjjj", "kkkk")
        try {
            Log.v("jjjj", "kkkk")
            queue = Volley.newRequestQueue(activity)
            val timeout = 10000 // 60 seconds - time out
            val req: JsonObjectRequest =
                object : JsonObjectRequest(
                    Method.PUT, url, jsonObject,
                    Response.Listener { response ->
                        Log.d(VolleyLog.TAG, response.toString())
                        // response
                        Log.d("Response", response.toString())
                        // try {
                        val `object` =
                            invokeParser(response.toString(), requestType)
                        if (`object` == null) { //                                Utility.hideProgressDialog(activity);
                            //                                Utility.showAlertDialog(activity, "Connection Problem. Unable to fetch the data");
                        }
                        //Log.v("URL", "--" + object.toString());
                        resplist.onResponseReceived(`object`, requestType)
                    }, Response.ErrorListener { error ->
                        VolleyLog.d(VolleyLog.TAG, "Errobbbbr: " + error.message)
                        var message = ""
                        if (error is NetworkError) {
                            message = "Please Check Your Internet Connection"
                        } else if (error is TimeoutError) {
                            message =
                                "Connection TimeOut! Please check your internet connection."
                        }
                        if (message != "") { //Utility.toastMsg(activity, message);
                        }
                        //Utility.loadingScreenClose(activity);
                        resplist.onResponseReceived(null, requestType)
                    }) {
                    /**
                     * Passing some request headers
                     */
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String> {
                        val headers =
                            HashMap<String, String>()
                        headers["Content-Type"] = "application/json"
                        headers["apiKey"] = "xxxxxxxxxxxxxxx"
                        return headers
                    }
                }
            // Request Time out
            req.retryPolicy = DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            req.retryPolicy = DefaultRetryPolicy(
                timeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            req.retryPolicy = object : RetryPolicy {
                override fun getCurrentTimeout(): Int {
                    return timeout
                }

                override fun getCurrentRetryCount(): Int {
                    return timeout
                }

                @Throws(VolleyError::class)
                override fun retry(error: VolleyError) {
                    VolleyLog.d(VolleyLog.TAG, "Errobbbbr: " + error.message)
                }
            }
            System.setProperty("http.keepAlive", "false")
            // Default request queue
            queue!!.add(req)
        } catch (e: Exception) {
            Log.v("jjjj", "kkkk")
        }
    }
    private fun invokeParser(response: String, req_code: Int): Any? {
        when (req_code) {

            Constant.MEMBER_Login_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_customerRegister_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_getCustomer_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_CustomerLogout_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_CustomerMobileUpdate_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_getCustomerForgot_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_getVerifyCustomerOTP_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_getResetPassword_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_getUserRestaurantDashBoard_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_getVerifyCustomer_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_getRestaurantData_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_cancelFoodOrder_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_getBookingFoodOrder_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_customerAddress_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_customerAddressInsert_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_filterApi_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_tableReservtionDashboard_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_tableSlotAvailableList_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_tableReservtionRestaurant_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_tableReservationInsert_URL_RQ ->  return Parser.getLoginResponce(response)

            Constant.MEMBER_favoriteRestaurantList_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_favoriteRestaurantListAdd_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_favoriteRestaurantListRemove_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_deletCustomerAddress_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_bookingFoodOrderList_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_confirmFoodOrderList_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_userGroceryDashboard_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_fetchGroceryData_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_filterGroceryData_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_bookingGroceryOrder_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_cancelGroceryOrder_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_bookingGroceryOrderList_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_customerExistingGroup_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_tableBookingData_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_tableBookingMemberData_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_tableReservtionMenuPicked_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_removeTableBookingMember_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_tableFavoriteStoreList_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_tableFavoriteStoreAdd_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_confirmGroceryList_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_insertCardList_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_CardList_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_ratingStore_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_ratingGrocery_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_ratingDriver_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_memberPickedMenusList_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_customerMakePaymentForMember_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_checkDeliveryLocation_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_checkGroceryDeliveryLocation_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_resendOtp_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_restaurantAvailability_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_groceryAvailability_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_fetchRestaurantRating_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_fetchGroceryRating_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_checkRestaurantPromoOffer_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_fetchRestaurantPromocodeList_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_fetchMenuToppingList_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_countryList_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_productView_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_cardDelete_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_fetchGroceryPromoCode_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_checkGroceryPromoOffer_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_LieferinTermsCondition_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_lieferinCancellationPolicy_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_lieferinRefundPolicy_URL_RQ -> return Parser.getLoginResponce(response)

            Constant.MEMBER_lieferin_privacy_policy_URL_RQ -> return Parser.getLoginResponce(response)

        }
        Log.e("", "Unknown commands")
        return null
    }

    enum class RequestMethod {
        POST, GET
    }

    companion object {
        const val URL_PWD = ""
        var timout: String? = null
        private var queue: RequestQueue? = null
    }

    init {
        headers = ArrayList()
        params = ArrayList()
    }
}