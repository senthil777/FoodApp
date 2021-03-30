package com.lieferin_global.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Activity.LoginActivity
import com.lieferin_global.Adapter.FavoriteAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import org.json.JSONObject
import java.util.ArrayList

class FavoriteFragment : Fragment(),View.OnClickListener,ResponseListener,FavoriteAdapter.CallbackDataAdapter {

    var favoriteRecyclerView: RecyclerView? = null

    var tableReservation_back : ImageView? =null

    var orderBill_Title: TextView? = null

    var categoryItemRecyclerView: FavoriteAdapter? = null

    var adapterFeature: MutableList<AdapterModel> = ArrayList()

    var adapterProduct: MutableList<Product> = ArrayList()

    internal lateinit var adapterModel: AdapterModel
    var rootView : View? = null

    var callBlacklisting: CallBlacklisting? = null

    var dbHelper : DBHelper? =null

    var noFavorite : TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_favorite, container, false)

        dbHelper = DBHelper(activity)

        var header = rootView!!.findViewById(R.id.header) as RelativeLayout

        header!!.setOnClickListener(this)

        tableReservation_back = rootView!!.findViewById(R.id.tableReservation_back)

        tableReservation_back!!.setOnClickListener(this)

        orderBill_Title = rootView!!.findViewById(R.id.orderBill_Title) as TextView

        orderBill_Title!!.typeface = fontStyle(activity!!,"SemiBold")

        favoriteRecyclerView = rootView!!.findViewById(R.id.favoriteRecyclerView) as RecyclerView

        categoryItemRecyclerView = FavoriteAdapter(activity!!, adapterFeature)
        favoriteRecyclerView!!.setHasFixedSize(true)
        favoriteRecyclerView!!.setLayoutManager(
            LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        )
        favoriteRecyclerView!!.isNestedScrollingEnabled =
            false

        categoryItemRecyclerView!!.setCallback(this)
        favoriteRecyclerView!!.setAdapter(
            categoryItemRecyclerView
        )
        noFavorite = rootView!!.findViewById(R.id.noFavorite)

        noFavorite!!.visibility = View.GONE

        noFavorite!!.typeface = fontStyle(activity!!,"Light")
        showDataFeature()

        if(AppType.equals("0")) {
            webService()
        }else{
            webServiceGrocery()
        }
        return rootView
    }

    fun webService(){

        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        RequestManager.setFavList(activity, obj, this);

        loadingScreen(activity)
    }
    fun webServiceGrocery(){

        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        RequestManager.settableFavoriteStoreList(activity, obj, this);

        loadingScreen(activity)
    }

    fun showDataFeature() {

        if (adapterFeature.size != 0) {
            adapterFeature.clear()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }

    override fun onClick(view: View?) {
        when(view!!.id)
        {
            R.id.tableReservation_back->{

                callBlacklisting!!.fragmentBack("")
            }
        }
    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {

        loadingScreenClose(activity)
        if(responseObj != null) {
            if (requestType == Constant.MEMBER_favoriteRestaurantList_URL_RQ) {
                if(dbHelper!!.getUserDetails().token == null)
                {
                    showToast(activity!!, "Please Login")
                }else {
                    showToast(activity!!, (responseObj as BaseRS).message)
                }
                if((responseObj as BaseRS).status.equals("5023"))
                {
                    if (adapterFeature.size != 0) {
                        adapterFeature.clear()
                    }

                    for (i in 0 until (responseObj as BaseRS).fetchFavoriteRestaurantData!!.size) {

                        adapterModel = AdapterModel(R.drawable.img_5, ""+(responseObj as BaseRS).fetchFavoriteRestaurantData!!.get(i).restaurantName, ""+(responseObj as BaseRS).fetchFavoriteRestaurantData!!.get(i).street, ""+(responseObj as BaseRS).fetchFavoriteRestaurantData!!.get(i).street+","+(responseObj as BaseRS).fetchFavoriteRestaurantData!!.get(i).town, ""+(responseObj as BaseRS).fetchFavoriteRestaurantData!!.get(i).rating, (responseObj as BaseRS).restaurantPath+""+(responseObj as BaseRS).fetchFavoriteRestaurantData!!.get(i).folderName, ""+(responseObj as BaseRS).fetchFavoriteRestaurantData!!.get(i).restaurantImages, ""+(responseObj as BaseRS).fetchFavoriteRestaurantData!!.get(i).restaurantReferenceCode, "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
                        adapterFeature.add(adapterModel)

                    }

                    if (adapterFeature.size == 0) {
                        noFavorite!!.visibility = View.VISIBLE
                        if(AppType!!.equals("0"))
                        {
                            noFavorite!!.text = "you haven't wishlist your restaurant yet"
                        }else{
                            noFavorite!!.text = "you haven't wishlist your store yet"
                        }
                    }else{
                        noFavorite!!.visibility = View.GONE
                    }
                    categoryItemRecyclerView!!.notifyDataSetChanged()
                }

            }else if(requestType == Constant.MEMBER_favoriteRestaurantListRemove_URL_RQ)
            {
                showToast(activity!!,(responseObj as BaseRS).message)
                if((responseObj as BaseRS).status.equals("5020"))
                {
                    webService()
                }
            }else if(requestType == Constant.MEMBER_tableFavoriteStoreAdd_URL_RQ)
            {
                showToast(activity!!,(responseObj as BaseRS).message)

                    webServiceGrocery()

            }else if(requestType == Constant.MEMBER_tableFavoriteStoreList_RQ){
                if((responseObj as BaseRS).status.equals("5823"))
                {
                    if (adapterFeature.size != 0) {
                        adapterFeature.clear()
                    }

                    for (i in 0 until (responseObj as BaseRS).fetchFavoriteStoreData!!.size) {

                        adapterModel = AdapterModel(R.drawable.img_5, ""+(responseObj as BaseRS).fetchFavoriteStoreData!!.get(i).groceryName, ""+(responseObj as BaseRS).fetchFavoriteStoreData!!.get(i).street, ""+(responseObj as BaseRS).fetchFavoriteStoreData!!.get(i).street+","+(responseObj as BaseRS).fetchFavoriteStoreData!!.get(i).town, ""+(responseObj as BaseRS).fetchFavoriteStoreData!!.get(i).rating, (responseObj as BaseRS).groceryPath+""+(responseObj as BaseRS).fetchFavoriteStoreData!!.get(i).folderName, ""+(responseObj as BaseRS).fetchFavoriteStoreData!!.get(i).groceryImages, ""+(responseObj as BaseRS).fetchFavoriteStoreData!!.get(i).groceryReferenceCode, "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
                        adapterFeature.add(adapterModel)

                    }

                    if (adapterFeature.size == 0) {
                        noFavorite!!.visibility = View.VISIBLE
                        if(AppType!!.equals("0"))
                        {
                            noFavorite!!.text = "you haven't wishlist your restaurant yet"
                        }else{
                            noFavorite!!.text = "you haven't wishlist your store yet"
                        }
                    }else{
                        noFavorite!!.visibility = View.GONE
                    }
                    categoryItemRecyclerView!!.notifyDataSetChanged()
                }

            }
        }
    }

    override fun setOnMaterial(userId: AdapterModel?, isTrue: Boolean, id: String?, position: Int) {
        if(AppType!!.equals("0")) {
            val obj = JSONObject()
            obj.put("restaurantReferenceCode", "" + id)

            obj.put("token", "" + dbHelper!!.getUserDetails().token)

            obj.put("status", "2")

            Log.v("Json", "Value" + obj)
            RequestManager.setFavListRemove(activity, obj, this);
        }else{
            val obj = JSONObject()
            obj.put("groceryReferenceCode", "" + id)

            obj.put("token", "" + dbHelper!!.getUserDetails().token)

            obj.put("status", "2")

            Log.v("Json", "Value" + obj)
            RequestManager.settableFavoriteStoreAdd(activity, obj, this);
        }
    }

    override fun setOnMaterialPage(id: String?) {
        Constant.BookingType = "0"
        val bundle = Bundle()
        bundle.putString("Title", id)
        callBlacklisting!!.fragmentChange("Detail Page", bundle)
    }

}
