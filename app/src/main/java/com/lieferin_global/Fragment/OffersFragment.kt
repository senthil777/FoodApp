package com.lieferin_global.Fragment

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
import com.lieferin_global.Adapter.OfferAdapter
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

class OffersFragment : Fragment(),View.OnClickListener,ResponseListener,OfferAdapter.CallbackDataAdapter {

    var favoriteRecyclerView: RecyclerView? = null

    var orderBill_Title: TextView? = null

    var categoryItemRecyclerView: OfferAdapter? = null

    var adapterFeature: MutableList<AdapterModel> = ArrayList()

    var adapterProduct: MutableList<Product> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    var callBlacklisting: CallBlacklisting? = null

    var rootView : View? = null

    var tableReservation_back : ImageView? =null

    var dbHelper : DBHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_offer, container, false)

        dbHelper = DBHelper(activity)

        var header = rootView!!.findViewById(R.id.header) as RelativeLayout

        header!!.setOnClickListener(this)

        orderBill_Title = rootView!!.findViewById(R.id.orderBill_Title) as TextView

        orderBill_Title!!.typeface = fontStyle(activity!!,"SemiBold")

        tableReservation_back = rootView!!.findViewById(R.id.tableReservation_back) as ImageView

        tableReservation_back!!.setOnClickListener(this)

        favoriteRecyclerView = rootView!!.findViewById(R.id.favoriteRecyclerView) as RecyclerView

        categoryItemRecyclerView = OfferAdapter(activity!!, adapterFeature)
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

        //showDataFeature()
        if(AppType.equals("0")) {
            wedServicePage()
        }else{
            wedService()
        }
        return rootView
    }

    fun wedServicePage(){
        val obj = JSONObject()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)
        /*obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")*/
        obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)
        obj.put("distance", "50")
        Log.v("Json", "Value" + obj)
        RequestManager.setDashBoard(activity, obj, this);
    }
    fun wedService(){
        val obj = JSONObject()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)
        /*obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")*/
        obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)
        obj.put("distance", "30")
        Log.v("Json", "Value" + obj)
        RequestManager.setUserGroceryDashboard(activity, obj, this);
    }


    fun showDataFeature() {

        if (adapterFeature.size != 0) {
            adapterFeature.clear()
        }
        adapterModel = AdapterModel(R.drawable.img_4, "Thalapakatty Restaurant", "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNdVp58T_eVgzLEw-_dpw.730x390.jpg", "75 minutes", "Save 60 %", "1", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.img_5, "SMS Multi Cusine Restaurant", "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNdVp58T_eVgzLEw-_dpw.730x390.jpg", "75 minutes", "Save 60 %", "1", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)
        categoryItemRecyclerView!!.notifyDataSetChanged()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }


    override fun onClick(view: View?) {

        when(view!!.id)
        {
            R.id.tableReservation_back ->{
                callBlacklisting!!.fragmentBack("")
            }
        }

    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if (responseObj != null) {
            //loadingScreenClose(activity)
            if (requestType == Constant.MEMBER_getUserRestaurantDashBoard_URL_RQ) {

                if ((responseObj as BaseRS).status.equals("3024")) {

                    for (i in 0 until (responseObj as BaseRS).fetchData!!.offerPlacedRestaurantList!!.size) {

                        adapterModel = AdapterModel(
                            R.drawable.recommended,
                            "" + (responseObj as BaseRS).fetchData!!.offerPlacedRestaurantList!!.get(i).restaurantName,
                            "" + (responseObj as BaseRS).fetchData!!.offerPlacedRestaurantList!!.get(i).street+","+ (responseObj as BaseRS).fetchData!!.offerPlacedRestaurantList!!.get(i).town,
                            (responseObj as BaseRS).fetchData!!.offerPlacedRestaurantList!!.get(i).distance + " KM",
                            "Open now",
                            "",
                            "" + (responseObj as BaseRS).fetchData!!.offerPlacedRestaurantList!!.get(i).rating,
                            "" + (responseObj as BaseRS).restaurantPath + "" + (responseObj as BaseRS).fetchData!!.offerPlacedRestaurantList!!.get(
                                i
                            ).folderName + "/" + (responseObj as BaseRS).fetchData!!.offerPlacedRestaurantList!!.get(
                                i
                            ).restaurantImages!!.replace("[", ""),
                            ""+ (responseObj as BaseRS).fetchData!!.offerPlacedRestaurantList!!.get(i).offerPercentage,
                            "" + (responseObj as BaseRS).fetchData!!.offerPlacedRestaurantList!!.get(i).restaurantReferenceCode,
                            "3054 Ratings>",
                            "5 Star Given by you",
                            "1",
                            "" + (responseObj as BaseRS).fetchData!!.offerPlacedRestaurantList!!.get(i).restaurantReferenceCode,
                            ""+ (responseObj as BaseRS).restaurantPath+"/"+ (responseObj as BaseRS).fetchData!!.offerPlacedRestaurantList!!.get(i).folderName,
                            ""+ getImageValue1((responseObj as BaseRS).fetchData!!.offerPlacedRestaurantList!!.get(i).restaurantImages),
                            "",
                            "",
                            0,
                            0,
                            0,
                            adapterProduct
                        )
                        adapterFeature.add(adapterModel)
                        categoryItemRecyclerView!!.notifyDataSetChanged()

                    }

                }
            }else if (requestType == Constant.MEMBER_userGroceryDashboard_URL_RQ) {

                if ((responseObj as BaseRS).status.equals("5801")) {

                    for (i in 0 until (responseObj as BaseRS).fetchData!!.offerPlacedStoreList!!.size) {

                        adapterModel = AdapterModel(
                            R.drawable.recommended,
                            "" + (responseObj as BaseRS).fetchData!!.offerPlacedStoreList!!.get(i).groceryName,
                            "" + (responseObj as BaseRS).fetchData!!.offerPlacedStoreList!!.get(i).street+","+ (responseObj as BaseRS).fetchData!!.offerPlacedStoreList!!.get(i).town,
                            (responseObj as BaseRS).fetchData!!.offerPlacedStoreList!!.get(i).distance + " KM",
                            "Open now",
                            "",
                            "" + (responseObj as BaseRS).fetchData!!.offerPlacedStoreList!!.get(i).rating,
                            "" + (responseObj as BaseRS).groceryPath + "" + (responseObj as BaseRS).fetchData!!.offerPlacedStoreList!!.get(
                                i
                            ).folderName + "/" + (responseObj as BaseRS).fetchData!!.offerPlacedStoreList!!.get(
                                i
                            ).groceryImages!!.replace("[", ""),
                            ""+ (responseObj as BaseRS).fetchData!!.offerPlacedStoreList!!.get(i).offerPercentage,
                            "" + (responseObj as BaseRS).fetchData!!.offerPlacedStoreList!!.get(i).groceryReferenceCode,
                            "3054 Ratings>",
                            "5 Star Given by you",
                            "1",
                            "" + (responseObj as BaseRS).fetchData!!.offerPlacedStoreList!!.get(i).groceryReferenceCode,
                            ""+ (responseObj as BaseRS).groceryPath+"/"+ (responseObj as BaseRS).fetchData!!.offerPlacedStoreList!!.get(i).folderName,
                            ""+ getImageValue1((responseObj as BaseRS).fetchData!!.offerPlacedStoreList!!.get(i).groceryImages),
                            "",
                            "",
                            0,
                            0,
                            0,
                            adapterProduct
                        )
                        adapterFeature.add(adapterModel)
                        categoryItemRecyclerView!!.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun setOnMaterial(userId: AdapterModel?, isTrue: Boolean, id: String?, position: Int) {
        val bundle = Bundle()
        bundle.putString("Title", id)
        bundle.putString("Delivery Type", "1")
        Constant.BookingType = "0"
        callBlacklisting!!.fragmentChange("Detail Page", bundle)

    }

}
