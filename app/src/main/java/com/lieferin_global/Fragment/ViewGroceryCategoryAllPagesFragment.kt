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
import com.lieferin_global.Adapter.ViewAllDishAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.categoryFilter
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class ViewGroceryCategoryAllPagesFragment : Fragment(), ViewAllDishAdapter.CallbackDataAdapter,
    View.OnClickListener, ResponseListener {

    var rootView: View? = null

    var viewAllRecyclerView: RecyclerView? = null

    var viewAllDishAdapter: ViewAllDishAdapter? = null

    var adapterFamous: MutableList<AdapterModel> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    var adapterProduct: MutableList<Product> = ArrayList()

    var title: String? = null

    var orderBill_Title: TextView? = null

    var tableReservation_back: ImageView? = null

    fun ViewAllPagesFragment() {}

    var callBlacklisting: CallBlacklisting? = null

    var dbHelper : DBHelper? = null

    var noCategory : TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_view_all_pages, container, false)

        dbHelper = DBHelper(activity)

        var header = rootView!!.findViewById(R.id.header) as RelativeLayout

        header!!.setOnClickListener(this)

        noCategory = rootView!!.findViewById(R.id.noCategory)

        noCategory!!.visibility = View.GONE

        noCategory!!.typeface = fontStyle(activity!!,"Light")

        val bundle = this.arguments
        if (bundle != null) {
            title = bundle.getString("Title")
        }

        tableReservation_back = rootView!!.findViewById(R.id.tableReservation_back)

        tableReservation_back!!.setOnClickListener(this)

        orderBill_Title = rootView!!.findViewById(R.id.orderBill_Title) as TextView

        orderBill_Title!!.text = title

        orderBill_Title!!.typeface = fontStyle(activity!!, "")

        orderBill_Title!!.text = "Category"

        if (orderBill_Title!!.text!!.toString().toLowerCase().contains("recommended")) {

            Log.v("kkkkkk","Success")
        }else{
         //   Log.v("kkkkkk","Success1"+title!!.length)
        }

        viewAllRecyclerView = rootView!!.findViewById(R.id.viewAllRecyclerView) as RecyclerView

        viewAllDishAdapter = ViewAllDishAdapter(activity!!, adapterFamous)

        val mLayoutManager3: RecyclerView.LayoutManager = LinearLayoutManager(
            activity!!,
            LinearLayoutManager.VERTICAL, false
        )

        viewAllRecyclerView!!.layoutManager = mLayoutManager3

        viewAllRecyclerView!!.itemAnimator!!.addDuration = 5000

        viewAllDishAdapter!!.setCallback(this)

        viewAllRecyclerView!!.adapter = viewAllDishAdapter!!

        cussin(categoryFilter!!)


        return rootView
    }

    fun cussin(string:String?) {

        val jRootArray1 = JSONArray()
        jRootArray1.put(string)

        val obj = JSONObject()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)
        /*obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")*/
        obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)
        obj.put("orderBy", "2")
        obj.put("distance","50")/*
        obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)*/
        obj.put("categoryList", jRootArray1)
        RequestManager.setFilterGroceryData(activity, obj, this);

        Log.v("llll == "+obj,"pppp"+jRootArray1)


        loadingScreen(activity)
    }

    fun showDataTrending1() {

        if (adapterFamous.size != 0) {
            adapterFamous.clear()
        }
        adapterModel = AdapterModel(
            R.drawable.recommended,
            "Dindigul Thalappakatti",
            "Quick Bites - Biryani",
            "610 m - Gandhipuram, Coimbatore",
            "Open now",
            "500 for two people (Approx)",
            "4.0",
            "https://secure.i.telegraph.co.uk/multimedia/archive/02999/restaurant_2999753b.jpg",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterProduct
        )
        adapterFamous.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.recommended,
            "Neydhal The Coast",
            "Quick Bites - Biryani",
            "610 m - Gandhipuram, Coimbatore",
            "Open now",
            "500 for two people (Approx)",
            "4.5",
            "https://secure.i.telegraph.co.uk/multimedia/archive/02999/restaurant_2999753b.jpg",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterProduct
        )
        adapterFamous.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.recommended,
            "Idly Virundhu",
            "Quick Bites - Biryani",
            "610 m - Gandhipuram, Coimbatore",
            "Open now",
            "500 for two people (Approx)",
            "3.0",
            "https://secure.i.telegraph.co.uk/multimedia/archive/02999/restaurant_2999753b.jpg",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterProduct
        )
        adapterFamous.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.recommended,
            "Vignesh Canteen",
            "Quick Bites - Biryani",
            "610 m - Gandhipuram, Coimbatore",
            "Open now",
            "500 for two people (Approx)",
            "3.1",
            "https://secure.i.telegraph.co.uk/multimedia/archive/02999/restaurant_2999753b.jpg",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "Description",
            "Description",
            "",
            0,
            0,
            0,
            adapterProduct
        )
        adapterFamous.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.recommended,
            "Night Food Coimbatore",
            "Quick Bites - Biryani",
            "610 m - Gandhipuram, Coimbatore",
            "Open now",
            "500 for two people (Approx)",
            "4.8",
            "https://secure.i.telegraph.co.uk/multimedia/archive/02999/restaurant_2999753b.jpg",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "Description",
            "",
            0,
            0,
            0,
            adapterProduct
        )
        adapterFamous.add(adapterModel)

        viewAllDishAdapter!!.notifyDataSetChanged()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }

    override fun setOnViewAll(id: String?) {
        Constant.BookingType = "0"
        val bundle = Bundle()
        bundle.putString("Title", id)
        callBlacklisting!!.fragmentChange("Detail Page", bundle)

    }

    override fun onClick(view: View?) {

        when (view!!.id) {
            R.id.tableReservation_back -> {

                callBlacklisting!!.fragmentBack("")

            }
        }

    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if (responseObj != null) {
            loadingScreenClose(activity)
            if (requestType == Constant.MEMBER_filterGroceryData_URL_RQ) {

                if ((responseObj as BaseRS).status.equals("5804")) {
                    if (adapterFamous.size != 0) {
                        adapterFamous.clear()
                    }

                    for (i in 0 until (responseObj as BaseRS).fetchData!!.groceryListing!!.size) {

                        adapterModel = AdapterModel(
                            R.drawable.recommended,
                            "" + (responseObj as BaseRS).fetchData!!.groceryListing!!.get(
                                i
                            ).groceryName,
                            "Quick Bites - " + (responseObj as BaseRS).fetchData!!.groceryListing!!.get(
                                i
                            ).street,
                            "" + (responseObj as BaseRS).fetchData!!.groceryListing!!.get(
                                i
                            ).street + " - " + (responseObj as BaseRS).fetchData!!.groceryListing!!.get(
                                i
                            ).town,
                            "Open now",
                            "€ "+(responseObj as BaseRS).fetchData!!.groceryListing!!.get(i).price+" for Minimum price",
                            "" + (responseObj as BaseRS).fetchData!!.groceryListing!!.get(
                                i
                            ).rating,
                            "https://secure.i.telegraph.co.uk/multimedia/archive/02999/restaurant_2999753b.jpg",
                            "4.4",
                            ""+ (responseObj as BaseRS).fetchData!!.groceryListing!!.get(i).groceryReferenceCode,
                            "3054 Ratings>",
                            "5 Star Given by you",
                            "1",
                            "",
                            "" + (responseObj as BaseRS).groceryPath + "/" + (responseObj as BaseRS).fetchData!!.groceryListing!!.get(
                                i
                            ).folderName,
                            "" + getImageValue1(
                                (responseObj as BaseRS).fetchData!!.groceryListing!!.get(
                                    i
                                ).groceryImages
                            ),
                            "",
                            "",
                            0,
                            0,
                            0,
                            adapterProduct
                        )

                        adapterFamous.add(adapterModel)
                    }
                    if (adapterFamous!!.size == 0) {
                        noCategory!!.visibility = View.VISIBLE
                    }else{
                        noCategory!!.visibility = View.GONE
                    }
                    viewAllDishAdapter!!.notifyDataSetChanged()
                    /*addProduct()

                    if(adapterTrending!!.size == 0 && adapterFeature!!.size == 0 && adapterFamous!!.size == 0 && allRestrant!!.size == 0)
                    {
                        noRestaurantLayout!!.visibility = View.VISIBLE
                        allRecyclerView!!.visibility = View.GONE
                    }else{
                        noRestaurantLayout!!.visibility = View.GONE

                        allRecyclerView!!.visibility = View.VISIBLE
                    }
                    adapter!!.notifyDataSetChanged()
                    allRecyclerView!!.smoothScrollToPosition(3)*/
                }

            }
        }
    }
}
