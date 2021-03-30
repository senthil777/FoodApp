package com.lieferin_global.Fragment

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.lieferin_global.Adapter.DashBoardAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.HomePageModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.adapterCategories10
import com.lieferin_global.Utility.Constant.favStringValue
import com.lieferin_global.Utility.Constant.priceHigh
import com.lieferin_global.Utility.Constant.priceHighToLow
import com.lieferin_global.Utility.Constant.priceLow
import com.lieferin_global.Utility.Constant.priceLowToHigh
import com.lieferin_global.Utility.Constant.ratingLowToHigh
import com.lieferin_global.Utility.Constant.valueStringFour
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class DeliveryFragment : Fragment(), DashBoardAdapter.OnClickDashBoard, ResponseListener,View.OnClickListener {

    var rootView: View? = null

    var allRecyclerView: RecyclerView? = null

    var adapterModels: MutableList<AdapterModel> = ArrayList()

    var adapterTrending: MutableList<AdapterModel> = ArrayList()

    var adapterFeature: MutableList<AdapterModel> = ArrayList()

    var adapterFamous: MutableList<AdapterModel> = ArrayList()

    var allRestrant: MutableList<AdapterModel> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    var adapterProduct: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    lateinit var mStrings: Array<String>

    internal lateinit var productModel: Product

    var adapter: DashBoardAdapter? = null

    var faviString = ""

    fun DeliveryFragment() {}

    var dbHelper : DBHelper? = null

    var callBlacklisting: CallBlacklisting? = null

    var noRestaurantLayout : LinearLayout? = null

    var noRestaurant : TextView? = null

    var groceryTV : TextView? = null

    var swiperefresh: SwipeRefreshLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_delivery, container, false)

        dbHelper = DBHelper(activity)

        allRecyclerView = rootView!!.findViewById(R.id.allRecyclerView) as RecyclerView

        noRestaurantLayout = rootView!!.findViewById(R.id.noRestaurantLayout) as LinearLayout

        //noRestaurantLayout!!.visibility = View.GONE

        noRestaurant = rootView!!.findViewById(R.id.noRestaurant) as TextView

        noRestaurant!!.typeface = fontStyle(activity!!,"")

        noRestaurant!!.text = "You have to enter the delivery location to see the nearby restaurants and grocery stores"


        groceryTV = rootView!!.findViewById(R.id.groceryTV) as TextView

        groceryTV!!.typeface = fontStyle(activity!!,"")

        groceryTV!!.setOnClickListener(this)

        groceryTV!!.text = Html.fromHtml("<p>You can get readymade food from grocery stores fro this location <br> <u>Click here</u></p>")

        groceryTV!!.visibility = View.GONE
        //mAdapterShip = HomeShipAdapter(this, topRate)

        swiperefresh= rootView!!.findViewById(R.id.swiperefresh) as SwipeRefreshLayout

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity!!)

        allRecyclerView!!.layoutManager = LinearLayoutManagerWithSmoothScroller(context)

        allRecyclerView!!.itemAnimator!!.addDuration = 5000

        if(dbHelper!!.getUserLocation().address != null)
        {
            noRestaurantLayout!!.visibility = View.GONE
            wedServicePage()
        }


        //showDataProduct()

        //showDataTrending()

        //showDataFeature()

        //showDataFamous()

        // allRestaruntFamous()

        //loadingScreen(activity)


        if (Constant.valueString.equals("Filter")) {
            //
            noRestaurant!!.text = "Searching Restaurant"

            groceryTV!!.visibility = View.GONE
        } else {
            allRecyclerView!!.smoothScrollToPosition(0)

        }

        swiperefresh!!.setOnRefreshListener(
            OnRefreshListener {
                Constant.valueString =""
                if(dbHelper!!.getUserLocation().address != null)
                {
                    wedServicePage()
                } else{
                    swiperefresh!!.setRefreshing(false);
                }}
        )

        return rootView
    }

    fun wedServicePage(){

        //Constant.valueString =""
        val obj = JSONObject()
        if(dbHelper!!.getUserDetails().token != null) {
            obj.put("token", "" + dbHelper!!.getUserDetails().token)
        }
       /* obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")*/
        obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)
        obj.put("distance", "50")
        Log.v("Json", "Value" + obj)
        RequestManager.setDashBoard(activity, obj, this);
    }

    fun filter() {
        val jRootArray = JSONArray()

        jRootArray.put(""+Constant.priceLow)

        jRootArray.put(""+Constant.priceHigh)

        val jRootArray1 = JSONArray()

        for (i in 0 until adapterCategories10.size) {
            jRootArray1.put(adapterCategories10.get(i).name)
        }

        webService(jRootArray, jRootArray1)


        //loadingScreen(activity)
    }

    fun cussin(string:String?) {

        val jRootArray1 = JSONArray()
        jRootArray1.put(string)


        webServiceCussin(jRootArray1,string!!)


        //loadingScreen(activity)
    }

    fun cussin1(string:String?) {

        val jRootArray1 = JSONArray()
        for (i in 0 until adapterCategories10.size) {
            jRootArray1.put(adapterCategories10.get(i).name)
        }

        webServiceCussin(jRootArray1,string!!)


        //loadingScreen(activity)
    }

    fun webService(jRootArray: JSONArray, jRootArray1: JSONArray) {
        val obj = JSONObject()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)
        /*obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")*/
        obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)
        obj.put("distance", "50")
        obj.put("hignToLow", ""+priceHighToLow)
        obj.put("lowToHigh", ""+ priceLowToHigh)
        obj.put("ratingHighToLow", ""+ ratingLowToHigh)
        if(Constant.valueStringFour != 0.0) {
            obj.put("rating", "" + Constant.valueStringFour)
        }
        obj.put("cuisine", jRootArray1)
        if(!Constant.priceLow.equals("0") && !Constant.priceHigh.equals("0")) {
            obj.put("costPerPerson", jRootArray)
        }
        Log.v("Json", "Value" + obj)
        RequestManager.setFilter(activity, obj, this);
    }

    private fun addProduct() {

        val list: MutableList<HomePageModel> = ArrayList<HomePageModel>()

        if (adapterTrending!!.size != 0) {
            list.add(HomePageModel(HomePageModel.CATEGORY, adapterTrending, 1))
        }

        if (adapterFeature!!.size != 0) {
            list.add(HomePageModel(HomePageModel.BANNER, adapterFeature, 1))
        }
        if (adapterFamous!!.size != 0) {
            list.add(HomePageModel(HomePageModel.OFFER, adapterFamous, 1))
        }
        if (allRestrant!!.size != 0) {
            list.add(HomePageModel(HomePageModel.SPECIAL_OFFER, allRestrant, 1))
        }

        //list.add(new HomePageModel(HomePageModel.BANNER, productHorizontal, null,2));
        adapter = DashBoardAdapter(list, activity!!)
        adapter!!.SetOnItemClickListener(this)
        allRecyclerView!!.adapter = adapter
    }

    fun showDataTrending() {

        if (adapterTrending.size != 0) {
            adapterTrending.clear()
        }
        adapterModel = AdapterModel(
            R.drawable.recommended,
            "",
            "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNd58T_eVgzLEw-_dpw.730x390.jpg",
            "",
            "Save 60 %",
            " 976",
            " 425",
            "Tatabad,Gandhipuram",
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
        adapterTrending.add(adapterModel)


        //hotDealsAdapter!!.notifyDataSetChanged()
    }

    fun showDataFamous() {

        if (adapterFamous.size != 0) {
            adapterFamous.clear()
        }

        adapterModel = AdapterModel(
            R.drawable.img_8,
            "VIEW ALL",
            "5 Restaurants",
            "75 minutes",
            "Save 60 %",
            "1",
            " 425",
            "Tatabad,Gandhipuram",
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
            R.drawable.img_6,
            "CHINESE",
            "35 OPTIONS",
            "75 minutes",
            "Save 60 %",
            " 1",
            " 425",
            "Tatabad,Gandhipuram",
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
            R.drawable.img_7,
            "NORTH INDIAN",
            "10 OPTIONS",
            "75 minutes",
            "Save 60 %",
            " 1",
            " 425",
            "Tatabad,Gandhipuram",
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
            R.drawable.img_8,
            "PIZZA",
            "67 OPTIONS",
            "75 minutes",
            "Save 60 %",
            "1",
            " 425",
            "Tatabad,Gandhipuram",
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
            R.drawable.img_6,
            "All Restaurants",
            "1000",
            "75 minutes",
            "Save 60 %",
            "1",
            " 425",
            "Tatabad,Gandhipuram",
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

        //hotDealsAdapter!!.notifyDataSetChanged()
    }


    fun allRestaruntFamous() {

        if (allRestrant.size != 0) {
            allRestrant.clear()
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
        allRestrant.add(adapterModel)

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
        allRestrant.add(adapterModel)

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
        allRestrant.add(adapterModel)

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
            "",
            "",
            "",
            0,
            0,
            0,
            adapterProduct
        )
        allRestrant.add(adapterModel)

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
            "",
            "",
            0,
            0,
            0,
            adapterProduct
        )
        allRestrant.add(adapterModel)

        //hotDealsAdapter!!.notifyDataSetChanged()
    }


    fun showDataFeature() {

        if (adapterFeature.size != 0) {
            adapterFeature.clear()
        }
        adapterModel = AdapterModel(
            R.drawable.img_4,
            "Thalapakatty Restaurant",
            "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNdVp58T_eVgzLEw-_dpw.730x390.jpg",
            "75 minutes",
            "Save 60 %",
            "1",
            " 425",
            "Tatabad,Gandhipuram",
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
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.img_5,
            "SMS Multi Cusine Restaurant",
            "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNdVp58T_eVgzLEw-_dpw.730x390.jpg",
            "75 minutes",
            "Save 60 %",
            "1",
            " 425",
            "Tatabad,Gandhipuram",
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
        adapterFeature.add(adapterModel)
        //hotDealsAdapter!!.notifyDataSetChanged()
    }

    fun showDataProduct() {

        if (adapterProduct.size != 0) {
            adapterProduct.clear()
        }
        productModel = Product(
            R.drawable.img_1,
            "Kritunga Restaurant",
            "4.4",
            "50% OFF",
            "South Indian, Chinese, Chat, North Indian...",
            "4.0",
            "40 Mins",
            "200 for two",
            "1",
            "",
            "",
            "",
            "",
            "","","","","",
            adapterProductList
        )
        adapterProduct.add(productModel)

        productModel = Product(
            R.drawable.img_2,
            "Kritunga Restaurant",
            "4.4",
            "50% OFF",
            "South Indian, Chinese, Chat, North Indian...",
            "4.0",
            "40 Mins",
            "200 for two",
            "1",
            "",
            "",
            "",
            "",
            "","","","","",
            adapterProductList
        )
        adapterProduct.add(productModel)

        productModel = Product(
            R.drawable.img_3,
            "Kritunga Restaurant",
            "4.4",
            "50% OFF",
            "South Indian, Chinese, Chat, North Indian...",
            "4.0",
            "40 Mins",
            "200 for two",
            "1",
            "",
            "",
            "",
            "",
            "","","","","",
            adapterProductList
        )
        adapterProduct.add(productModel)


        //hotDealsAdapter!!.notifyDataSetChanged()
    }

    override fun onItem(type: String?, id: String?, view: String?, postion: Int) {

        Log.i("Id == " + id, "View == " + type);

        if (type!!.toString().equals("View All")) {
            val bundle = Bundle()
            bundle.putString("Title", id)
            bundle.putString("View", view)
            callBlacklisting!!.fragmentChange("Product List", bundle)

        } else if (type!!.toString().equals("VIEW ALL")) {

            cussin1(type!!)
            adapter!!.notifyDataSetChanged()
            allRecyclerView!!.smoothScrollToPosition(3)
            //wedServicePage()
        } else if (type!!.toString().equals("Details Page")) {
            val bundle = Bundle()
            bundle.putString("Title", id)
            bundle.putString("Delivery Type", "1")
            Constant.BookingType = "0"
            callBlacklisting!!.fragmentChange("Detail Page", bundle)
        } else if (type!!.toString().equals("Add Favorite")) {

            if(view!!.equals("1")) {
                val obj = JSONObject()
                obj.put("restaurantReferenceCode", "" + id)

                obj.put("token", "" + dbHelper!!.getUserDetails().token)

                obj.put("status", "" + view)

                Log.v("Json", "Value" + obj)
                RequestManager.setFavListAdd(activity, obj, this);
            }else{
                val obj = JSONObject()
                obj.put("restaurantReferenceCode", "" + id)

                obj.put("token", "" + dbHelper!!.getUserDetails().token)

                obj.put("status", "" + view)

                Log.v("Json", "Value" + obj)
                RequestManager.setFavListRemove(activity, obj, this);
            }
        } else if (type!!.toString().equals("Banner")){
            val bundle = Bundle()
            bundle.putString("Title", id)
            bundle.putString("Delivery Type", "1")
            Constant.BookingType = "0"
            callBlacklisting!!.fragmentChange("Detail Page", bundle)
        }
        else{

            cussin(type)

            //adapter!!.notifyDataSetChanged()
            //allRecyclerView!!.smoothScrollToPosition(3)
        }

    }

    fun webServiceCussin(jRootArray: JSONArray?,isFull : String) {
        val obj = JSONObject()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)
        /*obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")*/
        obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)
        obj.put("distance", "50")
        if(!isFull.equals("View all")) {
            obj.put("cuisine", jRootArray)
        }
        Log.v("Json", "Value" + obj)
        RequestManager.setFilter(activity, obj, this);
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            //fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {

        swiperefresh!!.setRefreshing(false);
        if (responseObj != null) {
            //loadingScreenClose(activity)
            if (requestType == Constant.MEMBER_filterApi_URL_RQ) {

                if ((responseObj as BaseRS).status.equals("3028")) {
                    if (allRestrant.size != 0) {
                        allRestrant.clear()
                    }

                    if((responseObj as BaseRS).fetchData!!.restaurantListing!!.size == 0)
                    {
                        showToast(activity!!,"No Restaurant avaliable")
                    }else{
                        allRecyclerView!!.smoothScrollToPosition(2)
                    }

                    for (i in 0 until (responseObj as BaseRS).fetchData!!.restaurantListing!!.size) {

                            adapterModel = AdapterModel(
                                R.drawable.recommended,
                                "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).restaurantName,
                                "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).cuisineList,
                                (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).distance + " KM - " + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(
                                    i
                                ).town,
                                "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).openStatus,
                                "" + addIncreasePriceHole(
                                    "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(
                                        i
                                    ).price, "2"
                                ) + " for two people (Approx)",
                                "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).rating,
                                "" + (responseObj as BaseRS).restaurantPath + "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(
                                    i
                                ).folderName + "/" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(
                                    i
                                ).restaurantImages!!.replace("[", ""),
                                "4.4",
                                "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).restaurantReferenceCode,
                                "3054 Ratings>",
                                "5 Star Given by you",
                                "1",
                                "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).restaurantReferenceCode,
                                "" + (responseObj as BaseRS).restaurantPath + "/" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(
                                    i
                                ).folderName,
                                "" + getImageValue1(
                                    (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(
                                        i
                                    ).restaurantImages
                                ),
                                "",
                                "",
                                0,
                                0,
                                0,
                                adapterProduct
                            )
                            allRestrant.add(adapterModel)



                    }

                    valueStringFour =0.0

                    priceLow = "0"

                    priceHigh = "0"

                    if(adapterCategories10!!.size != 0)
                    {
                        adapterCategories10!!.clear()
                    }

                    Constant.priceHighToLow = ""
                    Constant.ratingLowToHigh = ""
                    Constant.priceLowToHigh = ""

                    addProduct()

                    if(adapterTrending!!.size == 0 && adapterFeature!!.size == 0 && adapterFamous!!.size == 0 && allRestrant!!.size == 0)
                    {
                        noRestaurantLayout!!.visibility = View.VISIBLE
                        allRecyclerView!!.visibility = View.GONE
                        noRestaurant!!.text ="Sorry, we are not serving this delivery location.Hang on tight!. Soon we will be there"
                        groceryTV!!.visibility = View.VISIBLE
                    }else{
                        noRestaurantLayout!!.visibility = View.GONE
                        allRecyclerView!!.visibility = View.VISIBLE
                    }
                    adapter!!.notifyDataSetChanged()
                    Constant.valueString = ""
                    allRecyclerView!!.smoothScrollToPosition(3)
                }
            }
            else if (requestType == Constant.MEMBER_getUserRestaurantDashBoard_URL_RQ) {

                if ((responseObj as BaseRS).status.equals("3025")) {
                    //showToast(activity!!,(responseObj as BaseRS).message)
                    callBlacklisting!!.fragmentChange("Login", null)
                }
                if ((responseObj as BaseRS).status.equals("3024")) {
                    if (!Constant.valueString.equals("Filter")) {
                        if (allRestrant.size != 0) {
                            allRestrant.clear()
                        }
                        favStringValue = ""
                        for (i in 0 until (responseObj as BaseRS).fetchData!!.favoriteList!!.size) {
                            if (favStringValue.equals("")) {
                                favStringValue =
                                    "" + (responseObj as BaseRS).fetchData!!.favoriteList!!.get(i).restaurantId
                            } else {
                                favStringValue =
                                    favStringValue + "," + (responseObj as BaseRS).fetchData!!.favoriteList!!.get(
                                        i
                                    ).restaurantId
                            }

                        }
                        Log.v("Value", "000 " + favStringValue);
                        for (i in 0 until (responseObj as BaseRS).fetchData!!.restaurantListing!!.size) {

                                adapterModel = AdapterModel(
                                    R.drawable.recommended,
                                    "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(
                                        i
                                    ).restaurantName,
                                    "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(
                                        i
                                    ).cuisineList,
                                    (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).distance + " KM - " + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(
                                        i
                                    ).town,
                                    "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(
                                        i
                                    ).openStatus,
                                    "€ " + addIncreasePriceHole(
                                        "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(
                                            i
                                        ).price, "2"
                                    ) + " for two people (Approx)",
                                    "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(
                                        i
                                    ).rating,
                                    "",
                                    "4.4",
                                    "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(
                                        i
                                    ).restaurantReferenceCode,
                                    "3054 Ratings>",
                                    "5 Star Given by you",
                                    "1",
                                    "" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(
                                        i
                                    ).restaurantReferenceCode,
                                    "" + (responseObj as BaseRS).restaurantPath + "/" + (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(
                                        i
                                    ).folderName,
                                    "" + getImageValue1(
                                        (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(
                                            i
                                        ).restaurantImages
                                    ),
                                    "",
                                    "",
                                    0,
                                    0,
                                    0,
                                    adapterProduct
                                )
                                allRestrant.add(adapterModel)

                        }
                    } else {

                        filter()
                    }
                    if (adapterCategories10.size != 0) {
                        //allRestrant.clear()
                    }

                    for (i in 0 until adapterCategories10.size) {

                    }


                    if (adapterTrending.size != 0) {
                        adapterTrending.clear()
                    }

                    if (adapterProduct.size != 0) {
                        adapterProduct.clear()
                    }

                    for (i in 0 until (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.size) {

                        if (i == 0) {

                                adapterModel = AdapterModel(
                                    R.drawable.recommended,
                                    "" + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).restaurantName,
                                    "" + (responseObj as BaseRS).restaurantPath + "/" + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).folderName + "/" + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).restaurantImages!!.replace("[", ""),
                                    "" + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).street + ", " + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).town,
                                    "" + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).restaurantReferenceCode,
                                    "",
                                    "" + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).distance,
                                    "" + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).price,
                                    "4.4",
                                    "" + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).restaurantId,
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
                            } else {

                                var imagePath = ""

                                if ((responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).restaurantImages == null
                                ) {
                                    imagePath = ""
                                } else {

                                    imagePath =
                                        "" + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                            i
                                        ).restaurantImages!!.replace("[", "")

                                }
                                productModel = Product(
                                    R.drawable.img_3,
                                    "" + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).restaurantName + "-" + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).restaurantReferenceCode,
                                    "" + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).rating,
                                    "",
                                    "" + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).street + ", " + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).town,
                                    "" + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).price,
                                    "" + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).distance + " KM",
                                    "" + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).restaurantId,
                                    "1",
                                    "" + (responseObj as BaseRS).restaurantPath + "/" + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).folderName + "/" + (responseObj as BaseRS).fetchData!!.recommendedRestaurantList!!.get(
                                        i
                                    ).restaurantImages!!.replace("[", ""),
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    adapterProductList
                                )

                                Log.v(
                                    "===", "===="
                                )
                                adapterProduct.add(productModel)
                            }
                            adapterTrending.add(adapterModel)

                    }
                    if (adapterFeature.size != 0) {
                        adapterFeature.clear()
                    }
                    for (i in 0 until (responseObj as BaseRS).fetchData!!.featureRestaurantList!!.size) {

                            adapterModel = AdapterModel(
                                R.drawable.img_4,
                                "" + (responseObj as BaseRS).fetchData!!.featureRestaurantList!!.get(
                                    i
                                ).restaurantName,
                                "" + (responseObj as BaseRS).fetchData!!.featureRestaurantList!!.get(
                                    i
                                ).restaurantReferenceCode,
                                "" + (responseObj as BaseRS).fetchData!!.featureRestaurantList!!.get(
                                    i
                                ).street + "," + (responseObj as BaseRS).fetchData!!.featureRestaurantList!!.get(
                                    i
                                ).town,
                                "" + getImageValue1(
                                    (responseObj as BaseRS).fetchData!!.featureRestaurantList!!.get(
                                        i
                                    ).restaurantImages
                                ),
                                "" + (responseObj as BaseRS).restaurantPath + "/" + (responseObj as BaseRS).fetchData!!.featureRestaurantList!!.get(
                                    i
                                ).folderName,
                                " 425",
                                "Tatabad,Gandhipuram",
                                "" + (responseObj as BaseRS).fetchData!!.featureRestaurantList!!.get(
                                    i
                                ).rating,
                                "" + (responseObj as BaseRS).fetchData!!.featureRestaurantList!!.get(
                                    i
                                ).distance + " KM",
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
                            adapterFeature.add(adapterModel)

                    }
                    if (adapterFamous.size != 0) {
                        adapterFamous.clear()
                    }


                    adapterModel = AdapterModel(
                        R.drawable.img_8,
                        ""+getString(R.string.DashBoard_viewAll),
                        "",
                        "75 minutes",
                        "Save 60 %",
                        "1",
                        " 425",
                        "Tatabad,Gandhipuram",
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
                    for (i in 0 until (responseObj as BaseRS).fetchData!!.cuisionRestaurantList!!.size) {

                        adapterModel = AdapterModel(
                            R.drawable.img_8,
                            "" + (responseObj as BaseRS).fetchData!!.cuisionRestaurantList!!.get(i).cuisionName,
                            ""+(responseObj as BaseRS).cuisinePath+""+ (responseObj as BaseRS).fetchData!!.cuisionRestaurantList!!.get(i).imagePath,
                            "75 minutes",
                            "Save 60 %",
                            "1",
                            " 425",
                            "Tatabad,Gandhipuram",
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
                    }
                    //adapter!!.notifyDataSetChanged()
                    if((responseObj as BaseRS).fetchData!!.restaurantListing!!.size == 0) {
                        if (adapterFamous.size != 0) {
                            adapterFamous.clear()
                        }
                    }
                    addProduct()

                    if(adapterTrending!!.size == 0 && adapterFeature!!.size == 0 && adapterFamous!!.size == 0 && allRestrant!!.size == 0)
                    {
                        noRestaurantLayout!!.visibility = View.VISIBLE
                        allRecyclerView!!.visibility = View.GONE
                        noRestaurant!!.text ="Sorry, we are not serving this delivery location.Hang on tight!. Soon we will be there"
                        groceryTV!!.visibility = View.VISIBLE
                    }else{
                        noRestaurantLayout!!.visibility = View.GONE

                        allRecyclerView!!.visibility = View.VISIBLE
                    }
                }
            }
            else if (requestType == Constant.MEMBER_favoriteRestaurantListAdd_URL_RQ) {

                if ((responseObj as BaseRS).status.equals("5029")) {

                    wedServicePage()
                }

                if ((responseObj as BaseRS).status.equals("5021")) {

                    wedServicePage()
                }

            }
            else if (requestType == Constant.MEMBER_favoriteRestaurantListRemove_URL_RQ) {

                if ((responseObj as BaseRS).status.equals("5020")) {
                    wedServicePage()
                }
            }
        }
    }

    override fun onClick(view: View?) {

        when(view!!.id)
        {
            R.id.groceryTV->{
                callBlacklisting!!.fragmentChange("Grocery",null)
            }
        }

    }
}
