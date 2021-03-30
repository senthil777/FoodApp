package com.lieferin_global.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.AllCategoryAdapter
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList

import com.lieferin_global.R
import com.lieferin_global.Utility.Constant
import com.lieferin_global.Utility.fontStyle
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import org.json.JSONObject
import java.util.ArrayList


class AllCategoryFragment : Fragment(),View.OnClickListener,ResponseListener,AllCategoryAdapter.CallbackDataAdapter {

    var allCategories : RecyclerView? = null

    var rootView : View? = null

    var callBlacklisting: CallBlacklisting? = null

    var orderBill_Title: TextView? = null

    var detailedViewMainAdapter:AllCategoryAdapter? = null

    var adapterDetails: MutableList<AdapterModel> = ArrayList()

    var tableReservation_back : ImageView?= null

    var adapterDetails1: MutableList<AdapterModel> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    internal lateinit var productModel: Product

    var adapterDetailChild: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_all_category, container, false)

        orderBill_Title = rootView!!.findViewById(R.id.orderBill_Title) as TextView

        orderBill_Title!!.typeface = fontStyle(activity!!,"SemiBold")

        tableReservation_back = rootView!!.findViewById(R.id.tableReservation_back)

        tableReservation_back!!.setOnClickListener(this)


        allCategories = rootView!!.findViewById(R.id.allCategories) as RecyclerView

        detailedViewMainAdapter = AllCategoryAdapter(activity!!,adapterDetails)
        allCategories!!.setLayoutManager(GridLayoutManager(activity!!,3))

        detailedViewMainAdapter!!.setCallback(this)

        allCategories!!.setAdapter(detailedViewMainAdapter)

        //showDataTrending()

        wedServicePage()

        return rootView
    }
    fun showDataTrending() {

        if (adapterDetails.size != 0) {
            adapterDetails.clear()
        }
        adapterModel = AdapterModel(0, "Product Title", "https://www.droletechnologies.com/lieferin-html/web/grocerydelivery/images/item/1.jpg", "3 pec", "Deal ended soon", "€ 20.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product03.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(0, "Product Title", "https://www.droletechnologies.com/lieferin-html/web/grocerydelivery/images/item/2.jpg", "2 pec", "Deal ended soon", "€ 15.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product04.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(0, "Product Title", "https://www.droletechnologies.com/lieferin-html/web/grocerydelivery/images/item/3.jpg", "1   pec", "Deal ended soon", "€ 30.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product05.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(0, "Product Title", "https://www.droletechnologies.com/lieferin-html/web/grocerydelivery/images/item/4.jpg", "3 pec", "Deal ended soon", "€ 20.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product06.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(0, "Product Title", "https://www.droletechnologies.com/lieferin-html/web/grocerydelivery/images/item/5.jpg", "2 pec", "Deal ended soon", "€ 15.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product07.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(0, "Product Title", "https://www.droletechnologies.com/lieferin-html/web/grocerydelivery/images/item/6.jpg", "1   pec", "Deal ended soon", "€ 30.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product09.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(0, "Product Title", "https://www.droletechnologies.com/lieferin-html/web/grocerydelivery/images/item/7.jpg", "3 pec", "Deal ended soon", "€ 20.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product03.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(0, "Product Title", "https://www.droletechnologies.com/lieferin-html/web/grocerydelivery/images/item/8.jpg", "2 pec", "Deal ended soon", "€ 15.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product04.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(0, "Product Title", "https://www.droletechnologies.com/lieferin-html/web/grocerydelivery/images/item/9.jpg", "1   pec", "Deal ended soon", "€ 30.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product05.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(0, "Product Title", "https://www.droletechnologies.com/lieferin-html/web/grocerydelivery/images/item/10.jpg", "3 pec", "Deal ended soon", "€ 20.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product06.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(0, "Product Title", "https://www.droletechnologies.com/lieferin-html/web/grocerydelivery/images/item/11.jpg", "2 pec", "Deal ended soon", "€ 15.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product07.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(0, "Product Title", "https://www.droletechnologies.com/lieferin-html/web/grocerydelivery/images/item/12.jpg", "1   pec", "Deal ended soon", "€ 30.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product09.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails.add(adapterModel)
        //hotDealsAdapter!!.notifyDataSetChanged()
    }

    fun wedServicePage(){
        val obj = JSONObject()
//        obj.put("token", "" + localgetUserInfo("token"))
        /*obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")*/
        obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)
        obj.put("distance", "30")
        RequestManager.setUserGroceryDashboard(activity, obj, this);

        Log.v("value   "+obj,"lllll")
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
        if(responseObj != null) {
            if (requestType == Constant.MEMBER_userGroceryDashboard_URL_RQ) {
                if ((responseObj as BaseRS).status.equals("5801")) {

                    if (adapterDetails.size != 0) {
                        adapterDetails.clear()
                    }

                    for (i in 0 until (responseObj as BaseRS).fetchData!!.groceryCategoryList!!.size) {
                        adapterModel = AdapterModel(
                            R.drawable.food,
                            "" + (responseObj as BaseRS).fetchData!!.groceryCategoryList!!.get(i).name,
                            "" + (responseObj as BaseRS).categoryPath + (responseObj as BaseRS).fetchData!!.groceryCategoryList!!.get(
                                i
                            ).categoryImage,
                            "" + (responseObj as BaseRS).categoryPath + (responseObj as BaseRS).fetchData!!.groceryCategoryList!!.get(
                                i
                            ).categoryImage,
                            "Save 60 %",
                            " 976",
                            " 425",
                            "Tatabad,Gandhipuram",
                            "" + (responseObj as BaseRS).fetchData!!.groceryCategoryList!!.get(i).rating,
                            "Very Good",
                            "3054 Ratings>",
                            "5 Star Given by you",
                            "1",
                            ""+ (responseObj as BaseRS).categoryPath + (responseObj as BaseRS).fetchData!!.groceryCategoryList!!.get(
                                i
                            ).categoryImage,
                            "",
                            ""+ (responseObj as BaseRS).fetchData!!.groceryCategoryList!!.get(i).categoryReferenceCode,
                            "",
                            "",
                            0,
                            0,
                            0,
                            adapterDetailChild
                        )

                        Log.v(
                            "llll" + (responseObj as BaseRS).fetchData!!.groceryCategoryList!!.get(
                                i
                            ).categoryImage, "==="
                        )

                        adapterDetails.add(adapterModel)
                    }


                    detailedViewMainAdapter!!.notifyDataSetChanged()
                }
            }
        }
    }

    override fun setOnFav(id: String?) {

        Constant.categoryFilter = id!!

        callBlacklisting!!.fragmentChange("Reload",null)

    }
}
