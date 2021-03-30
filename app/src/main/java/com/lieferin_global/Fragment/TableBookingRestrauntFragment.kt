package com.lieferin_global.Fragment

import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.FilterPageAdapter
import com.lieferin_global.Adapter.ViewAllDishAdapter
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product

import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.AddressTXT
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONObject
import java.util.ArrayList


class TableBookingRestrauntFragment : Fragment(),FilterPageAdapter.CallbackDataAdapter,View.OnClickListener,ViewAllDishAdapter.CallbackDataAdapter,ResponseListener {

    var rootView : View? = null

    var profileImg: CircleImageView? = null

    var viewAllItems : RecyclerView? = null

    var viewAllDishAdapter: ViewAllDishAdapter? = null

    var adapterFamous: MutableList<AdapterModel> = ArrayList()

    var adapterFamousDuplicate: MutableList<AdapterModel> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    var adapterProduct: MutableList<Product> = ArrayList()

    var addressTextView: TextView? = null

    var searchEditText: EditText? = null

    var tableTv : TextView? = null

    var tableReservation_back: ImageView? = null

    var filterPageAdapter: FilterPageAdapter? = null

    var filterRecyclerView : RecyclerView? = null

    var adapterTrending: MutableList<AdapterModel> = ArrayList()

    var mapIV : ImageView? = null

    fun TableBookingRestrauntFragment() {}

    var callBlacklisting: CallBlacklisting? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_table_booking_restraunt, container, false)

        viewAllItems = rootView!!.findViewById(R.id.viewAllItems) as RecyclerView

        tableTv= rootView!!.findViewById(R.id.tableTv) as TextView

        tableTv!!.typeface = fontStyle(activity!!,"")

        viewAllDishAdapter = ViewAllDishAdapter(activity!!,adapterFamous)

        val mLayoutManager3: RecyclerView.LayoutManager = LinearLayoutManager(activity!!,
            LinearLayoutManager.VERTICAL, false)

        viewAllItems!!.layoutManager = mLayoutManager3

        viewAllItems!!.itemAnimator!!.addDuration = 5000

        viewAllDishAdapter!!.setCallback(this!!)

        viewAllItems!!.adapter = viewAllDishAdapter!!

        //showDataTrending1()

        profileImg = rootView!!.findViewById<View>(R.id.profileImg) as CircleImageView

        profileImg!!.setOnClickListener(this)

        /*if(!localgetUserInfo("image").equals("")) {

            Picasso.with(activity!!).load("" + localgetUserInfo("image")).resize(450, 450)
                .placeholder(R.drawable.place_holder).into(profileImg)
        }else{
            Picasso.with(activity!!).load(R.drawable.place_holder).resize(450, 450)
                .placeholder(R.drawable.place_holder).into(profileImg)
        }*/
        addressTextView = rootView!!.findViewById(R.id.addressTextView) as TextView

        addressTextView!!.typeface = fontStyle(activity!!,"")

        addressTextView!!.text = AddressTXT

        searchEditText = rootView!!.findViewById(R.id.searchEditText) as EditText

        searchEditText!!.typeface = fontStyle(activity!!,"Light")

        searchEditText!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

                if(p0!!.length != 0) {
                    searchValue(p0.toString())
                }else{
                    searchValue()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        mapIV = rootView!!.findViewById(R.id.mapIV) as ImageView

        mapIV!!.setColorFilter(
            colorIcon(activity!!,R.color.redColor,R.drawable.map_pin,mapIV!!),
            PorterDuff.Mode.SRC_ATOP)
        filterRecyclerView = rootView!!.findViewById(R.id.filterRecyclerView) as RecyclerView

        filterPageAdapter = FilterPageAdapter(activity!!, adapterTrending)

        val mLayoutManager2: RecyclerView.LayoutManager = LinearLayoutManager(activity!!,LinearLayoutManager.HORIZONTAL, false)

        filterRecyclerView!!.layoutManager = mLayoutManager2

        filterRecyclerView!!.itemAnimator!!.addDuration = 5000

        filterPageAdapter!!.setCallback(this)

        filterRecyclerView!!.adapter = filterPageAdapter!!

        //showDataTrending()
        val obj = JSONObject()
        //obj.put("token", ""+ localgetUserInfo("token"))
        obj.put("latitude", "")
        obj.put("longitude", "")
        obj.put("distance", "")
        Log.v("Json", "Value" + obj)
        RequestManager.setTableReservation(activity, obj, this);

        return rootView
    }

    fun searchValue(value : String){


        if (adapterFamous.size != 0) {
            adapterFamous.clear()
        }

        for (i in 0 until adapterFamousDuplicate.size) {
            if(adapterFamousDuplicate.get(i).categoryName!!.toLowerCase().contains(value.toLowerCase()) || adapterFamousDuplicate.get(i).price!!.toLowerCase().contains(value.toLowerCase())) {
                adapterModel = AdapterModel(R.drawable.img_4, ""+adapterFamousDuplicate.get(i).categoryName, ""+adapterFamousDuplicate.get(i).price, ""+adapterFamousDuplicate.get(i).offerPrice, ""+adapterFamousDuplicate.get(i).offerPercentage, ""+adapterFamousDuplicate.get(i).categoryImage, ""+adapterFamousDuplicate.get(i).offer,
                    ""+adapterFamousDuplicate.get(i).parlourAddress, ""+adapterFamousDuplicate.get(i).parlourRatingValue, ""+adapterFamousDuplicate.get(i).categoryReferenceCode, ""+adapterFamousDuplicate.get(i).parlourRatingCount, ""+adapterFamousDuplicate.get(i).parlourGivenRating, ""+adapterFamousDuplicate.get(i).categoryId, ""+adapterFamousDuplicate.get(i).shopTotRate, ""+adapterFamousDuplicate.get(i).menuImages, ""+adapterFamousDuplicate.get(i).openTime, ""+adapterFamousDuplicate.get(i).closeTime, ""+adapterFamousDuplicate.get(i).noOfEmp, 0, 0, 0,adapterProduct)

                adapterFamous.add(adapterModel)
            }

        }
        viewAllDishAdapter!!.notifyDataSetChanged()

    }

    fun searchValue(){
        if (adapterFamous.size != 0) {
            adapterFamous.clear()
        }

        for (i in 0 until adapterFamousDuplicate.size) {

            adapterModel = AdapterModel(R.drawable.img_4, ""+adapterFamousDuplicate.get(i).categoryName, ""+adapterFamousDuplicate.get(i).price, ""+adapterFamousDuplicate.get(i).offerPrice, ""+adapterFamousDuplicate.get(i).offerPercentage, ""+adapterFamousDuplicate.get(i).categoryImage, ""+adapterFamousDuplicate.get(i).offer,
                ""+adapterFamousDuplicate.get(i).parlourAddress, ""+adapterFamousDuplicate.get(i).parlourRatingValue, ""+adapterFamousDuplicate.get(i).categoryReferenceCode, ""+adapterFamousDuplicate.get(i).parlourRatingCount, ""+adapterFamousDuplicate.get(i).parlourGivenRating, ""+adapterFamousDuplicate.get(i).categoryId, ""+adapterFamousDuplicate.get(i).shopTotRate, ""+adapterFamousDuplicate.get(i).menuImages, ""+adapterFamousDuplicate.get(i).openTime, ""+adapterFamousDuplicate.get(i).closeTime, ""+adapterFamousDuplicate.get(i).noOfEmp, 0, 0, 0,adapterProduct)
            adapterFamous.add(adapterModel)

        }
        viewAllDishAdapter!!.notifyDataSetChanged()
    }

    fun showDataTrending() {

        if (adapterTrending.size != 0) {
            adapterTrending.clear()
        }
        adapterModel = AdapterModel(R.drawable.recommended, "Filter", "1", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterTrending.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Rating 4.0+", "0", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterTrending.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Fastest Delivery", "0", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterTrending.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Ratings", "2", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterTrending.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Cost", "2", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterTrending.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Culisines", "2", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterTrending.add(adapterModel)


        filterPageAdapter!!.notifyDataSetChanged()
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



    override fun setOnFilter(id: String?) {

    }

    override fun setCancelFilter(id: String?) {

    }

    override fun onClick(view: View?) {
        when(view!!.id)
        {
            R.id.tableReservation_back ->{

                callBlacklisting!!.fragmentBack("")
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }


    override fun setOnViewAll(id: String?) {

        val bundle = Bundle()
        bundle.putString("Title", id)
        callBlacklisting!!.fragmentChange("Table Details Page", bundle)
        //callBlacklisting!!.fragmentChange("Table Details Page",null)

    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if(responseObj != null) {
            //loadingScreenClose(activity)
            if (requestType == Constant.MEMBER_tableReservtionDashboard_URL_RQ) {
                if((responseObj as BaseRS).status.equals("5201")) {
                    if (adapterFamous.size != 0) {
                        adapterFamous.clear()
                    }

                        for (i in 0 until (responseObj as BaseRS).restaurantTableListing!!.size) {
                            adapterModel = AdapterModel(
                                R.drawable.recommended,
                                "" + (responseObj as BaseRS).restaurantTableListing!!.get(i).restaurantName,
                                "Quick Bites - "+(responseObj as BaseRS).restaurantTableListing!!.get(i).cuisionName,
                                ""+(responseObj as BaseRS).restaurantTableListing!!.get(i).street+","+(responseObj as BaseRS).restaurantTableListing!!.get(i).town,
                                "Open now",
                                ""+ addIncreasePriceHole(""+(responseObj as BaseRS).restaurantTableListing!!.get(i).price,"2")+" for two people (Approx)",
                                ""+(responseObj as BaseRS).restaurantTableListing!!.get(i).rating,
                                "https://secure.i.telegraph.co.uk/multimedia/archive/02999/restaurant_2999753b.jpg",
                                "4.4",
                                ""+ (responseObj as BaseRS).restaurantTableListing!!.get(i).restaurantReferenceCode,
                                "3054 Ratings>",
                                "5 Star Given by you",
                                "1",
                                "",
                                "" + (responseObj as BaseRS).restaurantPath+"/"+ (responseObj as BaseRS).restaurantTableListing!!.get(i).folderName,
                                ""+ getImageValue1((responseObj as BaseRS).restaurantTableListing!!.get(i).restaurantImages),
                                "",
                                "",
                                0,
                                0,
                                0,
                                adapterProduct
                            )
                            adapterFamous.add(adapterModel)
                            adapterFamousDuplicate.add(adapterModel)
                            viewAllDishAdapter!!.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}

