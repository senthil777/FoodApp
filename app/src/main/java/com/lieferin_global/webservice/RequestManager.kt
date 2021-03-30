package com.lieferin_global.webservice

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import com.lieferin_global.Utility.Constant
import org.json.JSONObject

object RequestManager {


    fun setLogin(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.logInUrl, Constant.MEMBER_Login_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setRegister(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.customerRegister, Constant.MEMBER_customerRegister_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setCustomerDetails(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.getCustomer, Constant.MEMBER_getCustomer_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setCustomerLogout(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.customerLogout, Constant.MEMBER_CustomerLogout_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setCustomerUpdate(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.customerMobileUpdate, Constant.MEMBER_CustomerMobileUpdate_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setCustomerForgot(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.getCustomerForgotPassword, Constant.MEMBER_getCustomerForgot_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setVerifyCustomerOTP(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.getVerifyCustomerOTP, Constant.MEMBER_getVerifyCustomerOTP_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setResetPassword(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.getResetPassword, Constant.MEMBER_getResetPassword_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setDashBoard(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.getUserRestaurantDashBoard, Constant.MEMBER_getUserRestaurantDashBoard_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setDashBoardActivity(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.getUserRestaurantDashBoard, Constant.MEMBER_getUserRestaurantDashBoard_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setCustomerVerify(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.getVerifyCustomer, Constant.MEMBER_getVerifyCustomer_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setRestaurant(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.getRestaurantData, Constant.MEMBER_getRestaurantData_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setRestaurantActivity(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.getRestaurantData, Constant.MEMBER_getRestaurantData_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setBookingFoodOrder(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.getBookingFoodOrder, Constant.MEMBER_getBookingFoodOrder_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setCancelFoodOrder(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.cancelFoodOrder, Constant.MEMBER_cancelFoodOrder_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setAddressDetails(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.customerAddress, Constant.MEMBER_customerAddress_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setAddressInsert(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.customerAddressInsert, Constant.MEMBER_customerAddressInsert_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setFilterActivity(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.filterApi, Constant.MEMBER_filterApi_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setFilter(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.filterApi, Constant.MEMBER_filterApi_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setTableReservation(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.tableReservtionDashboard, Constant.MEMBER_tableReservtionDashboard_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setTableReservationSlot(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.tableSlotAvailableList, Constant.MEMBER_tableSlotAvailableList_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setTableReservationRestarunt(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.tableReservtionRestaurant, Constant.MEMBER_tableReservtionRestaurant_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setFavList(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.tableFavoriteList, Constant.MEMBER_favoriteRestaurantList_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setFavListAdd(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.tableFavoriteListAdd, Constant.MEMBER_favoriteRestaurantListAdd_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setFavListRemove(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.tableFavoriteListRemove, Constant.MEMBER_favoriteRestaurantListRemove_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setAddRemove(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.deletCustomerAddress, Constant.MEMBER_deletCustomerAddress_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setBookingList(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.bookingFoodOrderList, Constant.MEMBER_bookingFoodOrderList_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setBookingGroceryOrderList(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.bookingGroceryOrderList, Constant.MEMBER_bookingGroceryOrderList_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setConfirmFoodOrderList(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.confirmFoodOrderList, Constant.MEMBER_confirmFoodOrderList_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setConfirmGroceryOrderList(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.confirmGroceryList, Constant.MEMBER_confirmGroceryList_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setTableReservationInsert(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.tableReservationInsert, Constant.MEMBER_tableReservationInsert_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setBookingDataList(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.tableBookingData, Constant.MEMBER_tableBookingData_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }


    fun setBookingMemberDataList(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.tableBookingMemberData, Constant.MEMBER_tableBookingMemberData_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }
    fun setUserGroceryDashboard(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.userGroceryDashboard, Constant.MEMBER_userGroceryDashboard_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setUserGroceryActivityDashboard(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.userGroceryDashboard, Constant.MEMBER_userGroceryDashboard_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setFetchGroceryData(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.fetchGroceryData, Constant.MEMBER_fetchGroceryData_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }
    fun setBookingGroceryOrder(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.bookingGroceryOrder, Constant.MEMBER_bookingGroceryOrder_URL_RQ)
            //fillCommonParams(client, activity);

            Log.i("Link "+Constant.Url + Constant.bookingGroceryOrder,"Value")

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setFilterGroceryData(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.filterGroceryData, Constant.MEMBER_filterGroceryData_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }
    fun setCancelGroceryOrder(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.cancelGroceryOrder, Constant.MEMBER_cancelGroceryOrder_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }
    fun setCustomerExistingGroup(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.customerExistingGroup, Constant.MEMBER_customerExistingGroup_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setTableReservtionMenuPicked(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.tableReservtionMenuPicked, Constant.MEMBER_tableReservtionMenuPicked_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }


    fun settableFavoriteStoreAdd(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.tableFavoriteStoreAdd, Constant.MEMBER_tableFavoriteStoreAdd_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setRemoveTableBookingMember(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.removeTableBookingMember, Constant.MEMBER_removeTableBookingMember_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun settableFavoriteStoreList(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.tableFavoriteStoreList, Constant.MEMBER_tableFavoriteStoreList_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setInsertCardList(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.insertCardList, Constant.MEMBER_insertCardList_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setInsertCardListActivity(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.insertCardList, Constant.MEMBER_insertCardList_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setCardList(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.CardList, Constant.MEMBER_CardList_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setCardListActivity(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.CardList, Constant.MEMBER_CardList_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setRatingRestaurant(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.ratingStore, Constant.MEMBER_ratingStore_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setRatingGrocery(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.ratingGrocery, Constant.MEMBER_ratingGrocery_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setRatingDriver(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.ratingDriver, Constant.MEMBER_ratingDriver_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setMemberPickedMenusList(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.memberPickedMenusList, Constant.MEMBER_memberPickedMenusList_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setCustomerMakePaymentForMember(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.customerMakePaymentForMember, Constant.MEMBER_customerMakePaymentForMember_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setCheckDeliveryLocation(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.checkDeliveryLocation, Constant.MEMBER_checkDeliveryLocation_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setCheckGroceryDeliveryLocation(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.checkGroceryDeliveryLocation, Constant.MEMBER_checkGroceryDeliveryLocation_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setRestaurantAvailability(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.restaurantAvailability, Constant.MEMBER_restaurantAvailability_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setGroceryAvailability(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.groceryAvailability, Constant.MEMBER_groceryAvailability_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setResendOTP(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.resendOtp, Constant.MEMBER_resendOtp_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }


    fun setFetchRestaurantRating(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.fetchRestaurantRating, Constant.MEMBER_fetchRestaurantRating_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setFetchGroceryRating(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.fetchGroceryRating, Constant.MEMBER_fetchGroceryRating_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setCheckRestaurantPromoOffer(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.checkRestaurantPromoOffer, Constant.MEMBER_checkRestaurantPromoOffer_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setFetchRestaurantPromocodeList(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.fetchRestaurantPromocodeList, Constant.MEMBER_fetchRestaurantPromocodeList_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setFetchMenuToppingList(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.fetchMenuToppingList, Constant.MEMBER_fetchMenuToppingList_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setCountryList(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.countryList, Constant.MEMBER_countryList_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setProduct(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.productView, Constant.MEMBER_productView_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setCardDetele(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.cardDelete, Constant.MEMBER_cardDelete_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setCardDeteleFragment(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.cardDelete, Constant.MEMBER_cardDelete_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setCheckGroceryPromoOffer(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.checkGroceryPromoOffer, Constant.MEMBER_checkGroceryPromoOffer_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setFetchGroceryPromoCode(activity: Context?,jsonObject: JSONObject,fragment: Activity?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.fetchGroceryPromoCode, Constant.MEMBER_fetchGroceryPromoCode_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJson(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setTermsCondition(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.lieferinTermsCondition, Constant.MEMBER_LieferinTermsCondition_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setPrivacyPolicy(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.lieferin_privacy_policy, Constant.MEMBER_lieferin_privacy_policy_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setRefundPolicy(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.lieferinRefundPolicy, Constant.MEMBER_lieferinRefundPolicy_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun setCancellationPolicy(activity: Context?,jsonObject: JSONObject,fragment: Fragment?): Boolean {
        try { // Generating Req
            val client =
                RestClient(Constant.Url + Constant.lieferinCancellationPolicy, Constant.MEMBER_lieferinCancellationPolicy_URL_RQ)
            //fillCommonParams(client, activity);

            client.executeJsonFragment(RestClient.RequestMethod.POST, activity!!, fragment!!, jsonObject)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }
}

