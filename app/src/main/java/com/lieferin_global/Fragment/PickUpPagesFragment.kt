package com.lieferin_global.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import org.json.JSONObject
import java.util.*


class PickUpPagesFragment : Fragment(),ViewAllDishAdapter.CallbackDataAdapter,ResponseListener {

    var rootView : View? = null

    var viewAllRecyclerView : RecyclerView? = null

    var viewAllDishAdapter: ViewAllDishAdapter? = null

    var adapterFamous: MutableList<AdapterModel> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    var adapterProduct: MutableList<Product> = ArrayList()

    var title: String? = null

    var orderBill_Title : TextView? = null

    fun ViewAllPagesFragment() {}

    var callBlacklisting: CallBlacklisting? = null

    var dbHelper : DBHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_pick_up, container, false)

        dbHelper = DBHelper(activity)

        val bundle = this.arguments
        if (bundle != null) {
            title = bundle.getString("Title")
        }

        orderBill_Title = rootView!!.findViewById(R.id.orderBill_Title) as TextView

        orderBill_Title!!.text = title

        orderBill_Title!!.typeface = fontStyle(activity!!,"")

        viewAllRecyclerView = rootView!!.findViewById(R.id.viewAllRecyclerView) as RecyclerView

        viewAllDishAdapter = ViewAllDishAdapter(activity!!,adapterFamous)

        val mLayoutManager3: RecyclerView.LayoutManager = LinearLayoutManager(activity!!,
            LinearLayoutManager.VERTICAL, false)

        viewAllRecyclerView!!.layoutManager = mLayoutManager3

        viewAllRecyclerView!!.itemAnimator!!.addDuration = 5000

        viewAllDishAdapter!!.setCallback(this)

        viewAllRecyclerView!!.adapter = viewAllDishAdapter!!

        //showDataTrending1()

        val obj = JSONObject()
        if(dbHelper!!.getUserDetails().token != null) {
            obj.put("token", "" + dbHelper!!.getUserDetails().token)
        }
        /*obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")*/
        obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)
        obj.put("distance", "50")
        Log.v("Json", "Value" + obj)
        RequestManager.setDashBoard(activity, obj, this);

        return rootView
    }

    fun showDataTrending1() {

        if (adapterFamous.size != 0) {
            adapterFamous.clear()
        }
        adapterModel = AdapterModel(R.drawable.recommended, "Dindigul Thalappakatti", "Quick Bites - Biryani", "610 m - Gandhipuram, Coimbatore", "Open now", "500 for two people (Approx)", "4.0", "https://secure.i.telegraph.co.uk/multimedia/archive/02999/restaurant_2999753b.jpg", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFamous.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Neydhal The Coast", "Quick Bites - Biryani", "610 m - Gandhipuram, Coimbatore", "Open now", "500 for two people (Approx)", "4.5", "https://secure.i.telegraph.co.uk/multimedia/archive/02999/restaurant_2999753b.jpg", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFamous.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Idly Virundhu", "Quick Bites - Biryani", "610 m - Gandhipuram, Coimbatore", "Open now", "500 for two people (Approx)", "3.0", "https://secure.i.telegraph.co.uk/multimedia/archive/02999/restaurant_2999753b.jpg", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFamous.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Vignesh Canteen", "Quick Bites - Biryani", "610 m - Gandhipuram, Coimbatore", "Open now", "500 for two people (Approx)", "3.1", "https://secure.i.telegraph.co.uk/multimedia/archive/02999/restaurant_2999753b.jpg", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFamous.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Night Food Coimbatore", "Quick Bites - Biryani", "610 m - Gandhipuram, Coimbatore", "Open now", "500 for two people (Approx)", "4.8", "https://secure.i.telegraph.co.uk/multimedia/archive/02999/restaurant_2999753b.jpg", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
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
        bundle.putString("Delivery Type", "3")

        callBlacklisting!!.fragmentChange("Detail Page",bundle)

    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if(responseObj != null) {
            //loadingScreenClose(activity)
            if (requestType == Constant.MEMBER_getUserRestaurantDashBoard_URL_RQ) {
                if((responseObj as BaseRS).status.equals("3024")) {
                    if (adapterFamous.size != 0) {
                        adapterFamous.clear()
                    }
                    for (i in 0 until (responseObj as BaseRS).fetchData!!.restaurantListing!!.size) {

                        if (!(responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).deliveryCollectionStatus!!.equals(
                                "2"
                            )
                        ) {
                            adapterModel = AdapterModel(
                                R.drawable.recommended,
                                "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).restaurantName,
                                "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).cuisineList,
                                (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).distance + " KM - " + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(
                                    i
                                ).town,
                                "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).openStatus,
                                "€ "+ addIncreasePriceHole(""+(responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).price,"2")+" for two people (Approx)",
                                "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).rating,
                                "https://secure.i.telegraph.co.uk/multimedia/archive/02999/restaurant_2999753b.jpg",
                                "4.4",
                                ""+ (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).restaurantReferenceCode,
                                "3054 Ratings>",
                                "5 Star Given by you",
                                "1",
                                "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).restaurantReferenceCode,
                                "" + (responseObj as BaseRS).restaurantPath+""+(responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).folderName,
                                ""+ getImageValue1((responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).restaurantImages),
                                "",
                                "",
                                0,
                                0,
                                0,
                                adapterProduct
                            )
                            adapterFamous.add(adapterModel)
                        }
                    }
                }
                viewAllDishAdapter!!.notifyDataSetChanged()
            }
        }
    }
}
