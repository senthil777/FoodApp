package com.lieferin_global.Fragment

import android.os.Bundle
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
import com.lieferin_global.Adapter.DashBoardAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.HomePageModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
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


class GrocryDeliveryFragment : Fragment(),DashBoardAdapter.OnClickDashBoard,ResponseListener {

    var rootView : View? = null

    var allRecyclerView: RecyclerView? = null

    var adapterModels: MutableList<AdapterModel> = ArrayList()

    var adapterTrending: MutableList<AdapterModel> = ArrayList()

    var adapterFeature: MutableList<AdapterModel> = ArrayList()

    var adapterFamous: MutableList<AdapterModel> = ArrayList()

    var adapterFamous1: MutableList<AdapterModel> = ArrayList()

    var allRestrant:MutableList<AdapterModel> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    var adapterProduct: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    internal lateinit var productModel: Product

    var adapter:DashBoardAdapter? = null

    fun DeliveryFragment() {}

    var callBlacklisting: CallBlacklisting? = null

    var swiperefresh: SwipeRefreshLayout? = null

    val list: MutableList<HomePageModel> = ArrayList<HomePageModel>()

    var noRestaurantLayout : LinearLayout? = null

    var noRestaurant : TextView? = null

    var dbHelper : DBHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_grocery_delivery, container, false)

        dbHelper = DBHelper(activity)

        swiperefresh= rootView!!.findViewById(R.id.swiperefresh) as SwipeRefreshLayout

        allRecyclerView = rootView!!.findViewById(R.id.allRecyclerView) as RecyclerView

        //mAdapterShip = HomeShipAdapter(this, topRate)

        noRestaurantLayout = rootView!!.findViewById(R.id.noRestaurantLayout) as LinearLayout

        noRestaurant = rootView!!.findViewById(R.id.noRestaurant) as TextView

        noRestaurant!!.typeface = fontStyle(activity!!,"")

        noRestaurant!!.text = "You have to enter the delivery location to see the nearby restaurants and grocery stores"

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity!!)

        allRecyclerView!!.layoutManager = LinearLayoutManagerWithSmoothScroller(context)

        allRecyclerView!!.itemAnimator!!.addDuration = 5000

        //addProduct()



        if(dbHelper!!.getUserLocation().address != null)
        {
            noRestaurantLayout!!.visibility = View.GONE
            wedServicePage()
        }
       /* }else {

            cussin(categoryFilter)
        }*/

        //showDataCategory()

        //showDataProduct()

        //showDataTrending()

        //showDataFeature()

        //showDataFamous()

        if (Constant.valueString.equals("Filter")) {
            //allRecyclerView!!.smoothScrollToPosition(list.size-1)
            noRestaurant!!.text = "Searching Store"
        } else {
            allRecyclerView!!.smoothScrollToPosition(0)

        }

        swiperefresh!!.setOnRefreshListener(
            SwipeRefreshLayout.OnRefreshListener { wedServicePage() }
        )

        return rootView
    }


    private fun addProduct() {

        if(list!!.size != 0 )
        {
            list!!.clear()
        }

        if(adapterTrending.size != 0) {
            list.add(HomePageModel(HomePageModel.CATEGORY, adapterTrending, 1))
        }

        if(adapterFeature.size != 0) {

            list.add(HomePageModel(HomePageModel.STORE_PRODUCT, adapterFeature, 1))
        }
        if(adapterFamous1.size != 0) {
            list.add(HomePageModel(HomePageModel.STORE_PRODUCT_SUB, adapterFamous1, 1))
        }
        list.add(HomePageModel(HomePageModel.SPECIAL_OFFER, allRestrant, 1))


        //list.add(HomePageModel(HomePageModel.SPECIAL_OFFER, allRestrant,1))

        //list.add(new HomePageModel(HomePageModel.BANNER, productHorizontal, null,2));
        adapter = DashBoardAdapter(list, activity!!)
        adapter!!.SetOnItemClickListener(this)
        allRecyclerView!!.adapter = adapter
    }
    fun wedServicePage(){
        val obj = JSONObject()
        if(dbHelper!!.getUserDetails().token != null) {
            obj.put("token", "" + dbHelper!!.getUserDetails().token)
        }
        /*obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")*/
        obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)
        obj.put("distance", "30")
        RequestManager.setUserGroceryDashboard(activity, obj, this);

        Log.v("value   "+obj,""+Constant.longtitudeAdd)
    }
    fun showDataCategory() {

        if (adapterFamous1.size != 0) {
            adapterFamous1.clear()
        }
        adapterModel = AdapterModel(R.drawable.food, "Beverages", "Popularity", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFamous1.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.cleaning, "Household", "", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFamous1.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.fruit, "Fruits", "", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFamous1.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.olive, "Vegetable", "", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFamous1.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.stationary, "Stationary", "", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFamous1.add(adapterModel)

    }


    fun showDataTrending() {

        if (adapterTrending.size != 0) {
            adapterTrending.clear()
        }
        adapterModel = AdapterModel(R.drawable.gocery_3, "EASY GROCERIES", "https://cf-images.us-east-1.prod.boltdns.net/v1/static/5615998031001/d9591c0f-75ba-44cd-9bb9-135a0459a97e/65474b22-cc38-473a-9dbc-f0e7c9218fe2/1280x720/match/image.jpg", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterTrending.add(adapterModel)



        //hotDealsAdapter!!.notifyDataSetChanged()
    }

    fun showDataFamous() {

        if (adapterFamous.size != 0) {
            adapterFamous.clear()
        }
        adapterModel = AdapterModel(R.drawable.gocery_1, "CHINESE", "35 OPTIONS", "75 minutes", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFamous.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.gocery_2, "NORTH INDIAN", "10 OPTIONS", "75 minutes", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFamous.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.img_8, "PIZZA", "67 OPTIONS", "75 minutes", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFamous.add(adapterModel)
        //hotDealsAdapter!!.notifyDataSetChanged()
    }


    fun showDataFeature() {

        if (adapterFeature.size != 0) {
            adapterFeature.clear()
        }
        adapterModel = AdapterModel(R.drawable.gocery_5, "Kannan supermarket ", "https://i0.wp.com/www.pointy.com/blog/wp-content/uploads/2019/09/local-store.jpeg?fit=1050%2C700&ssl=1", "Beverages,Fruits,Vegetable,Household...", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "€ 20 Min", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.gocery_6, "Spar supermarket", "https://www.forummalls.in/wp-content/uploads/malls/shantiniketan/shop_front/spar/fs_spar_f.jpg", "Beverages,Fruits,Vegetable,Household...", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "€ 20 Min", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.gocery_5, "Kannan supermarket ", "https://www.retail-insight-network.com/wp-content/uploads/sites/20/2019/03/Coles-supermarket.jpg", "Beverages,Fruits,Vegetable,Household...", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "€ 20 Min", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.gocery_6, "Spar supermarket", "https://cdn.gobankingrates.com/wp-content/uploads/2018/06/Costco-Wholesale-warehouse-exterior-shutterstock_1088745692-848x477.jpg", "Beverages,Fruits,Vegetable,Household...", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "€ 20 Min", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.gocery_5, "Kannan supermarket ", "https://cdn.newsapi.com.au/image/v1/301570dedd253157558373f067e34ed3?width=650", "Beverages,Fruits,Vegetable,Household...", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "€ 20 Min", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.gocery_6, "Spar supermarket", "https://i0.wp.com/www.pointy.com/blog/wp-content/uploads/2019/09/local-store.jpeg?fit=1050%2C700&ssl=1", "Beverages,Fruits,Vegetable,Household...", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "€ 20 Min", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)
        //hotDealsAdapter!!.notifyDataSetChanged()
    }


    fun allRestaruntFamous() {

        if (allRestrant.size != 0) {
            allRestrant.clear()
        }
        adapterModel = AdapterModel(R.drawable.recommended, "Dindigul Thalappakatti", "Quick Bites - Biryani", "610 m - Gandhipuram, Coimbatore", "Open now", "500 for two people (Approx)", "4.0", "https://secure.i.telegraph.co.uk/multimedia/archive/02999/restaurant_2999753b.jpg", "€ 20 Min", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        allRestrant.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Neydhal The Coast", "Quick Bites - Biryani", "610 m - Gandhipuram, Coimbatore", "Open now", "500 for two people (Approx)", "4.5", "https://secure.i.telegraph.co.uk/multimedia/archive/02999/restaurant_2999753b.jpg", "€ 20 Min", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        allRestrant.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Idly Virundhu", "Quick Bites - Biryani", "610 m - Gandhipuram, Coimbatore", "Open now", "500 for two people (Approx)", "3.0", "https://secure.i.telegraph.co.uk/multimedia/archive/02999/restaurant_2999753b.jpg", "€ 20 Min", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        allRestrant.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Vignesh Canteen", "Quick Bites - Biryani", "610 m - Gandhipuram, Coimbatore", "Open now", "500 for two people (Approx)", "3.1", "https://secure.i.telegraph.co.uk/multimedia/archive/02999/restaurant_2999753b.jpg", "€ 20 Min", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        allRestrant.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Night Food Coimbatore", "Quick Bites - Biryani", "610 m - Gandhipuram, Coimbatore", "Open now", "500 for two people (Approx)", "4.8", "https://secure.i.telegraph.co.uk/multimedia/archive/02999/restaurant_2999753b.jpg", "€ 20 Min", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        allRestrant.add(adapterModel)

        //hotDealsAdapter!!.notifyDataSetChanged()
    }




    fun showDataProduct() {

        if (adapterProduct.size != 0) {
            adapterProduct.clear()
        }
        productModel = Product(R.drawable.img_1, "Kritunga supermarket", "4.4","50% OFF","Beverages,Fruits,Vegetable,Household...","4.0 KM","https://www.wbrcae.com/uploads/hannafordaugusta6-1-1400x800.jpg","€ 20 Min","0","","Gro","","","","","","","",adapterProductList)
        adapterProduct.add(productModel)

        productModel = Product(R.drawable.img_2, "Kritunga supermarket", "4.4","50% OFF","Beverages,Fruits,Vegetable,Household...","4.0 KM","https://static01.nyt.com/images/2015/07/05/dining/INSTACART/INSTACART-articleLarge.jpg?quality=90&auto=webp","€ 20 Min","0","","Gro","","","","","","","",adapterProductList)
        adapterProduct.add(productModel)

        productModel = Product(R.drawable.img_3, "Kritunga supermarket", "4.4","50% OFF","Beverages,Fruits,Vegetable,Household...","4.0 KM","https://cdn.newsapi.com.au/image/v1/301570dedd253157558373f067e34ed3?width=650","€ 20 Min","0","","Gro","","","","","","","",adapterProductList)
        adapterProduct.add(productModel)


        //hotDealsAdapter!!.notifyDataSetChanged()
    }

    override fun onItem(type: String?, id: String?, view: String?, postion:Int) {

        Log.i("Id == "+type,"View == "+view);

        if(type!!.toString().equals("View All"))
        {
            val bundle = Bundle()
            bundle.putString("Title", id)
            callBlacklisting!!.fragmentChange("Product List", bundle)

        }else if(type!!.toString().equals("View All Category"))
        {
            val bundle = Bundle()
            bundle.putString("Title", id)
            callBlacklisting!!.fragmentChange("AllCategory", bundle)
        }else if(type!!.toString().equals("VIEW ALL"))
        {
            allRestaruntFamous()
            adapter!!.notifyDataSetChanged()
            allRecyclerView!!.smoothScrollToPosition(3)
        }else if(type!!.toString().equals("Details Page")){
            val bundle = Bundle()
            bundle.putString("Title", id)
            bundle.putString("Delivery Type", "1")
            Constant.BookingType = "0"
            Log.v("id === "+id,"Value")
            callBlacklisting!!.fragmentChange("Detail Page", bundle)
        }else if (type!!.toString().equals("Add Favorite")) {

            if(view!!.equals("1")) {
                val obj = JSONObject()
                obj.put("groceryReferenceCode", "" + id)

                obj.put("token", "" + dbHelper!!.getUserDetails().token)

                obj.put("status", "" + view)

                Log.v("Json", "Value" + obj)
                RequestManager.settableFavoriteStoreAdd(activity, obj, this);
            }else{
                val obj = JSONObject()
                obj.put("groceryReferenceCode", "" + id)

                obj.put("token", "" + dbHelper!!.getUserDetails().token)

                obj.put("status", "" + view)

                Log.v("Json", "Value" + obj)
                RequestManager.settableFavoriteStoreAdd(activity, obj, this);
            }
        } else if (type!!.toString().equals("Banner")){
            val bundle = Bundle()
            bundle.putString("Title", id)
            bundle.putString("Delivery Type", "1")
            Constant.BookingType = "0"
            callBlacklisting!!.fragmentChange("Detail Page", bundle)
        }else{

            categoryFilter = ""+type
            cussin(type)
            Log.v("id === "+type,"Value")
            adapter!!.notifyDataSetChanged()
            allRecyclerView!!.smoothScrollToPosition(list.size-1)
        }

    }

    fun cussin(string:String?) {

        val jRootArray = JSONArray()

        jRootArray.put(""+Constant.priceLow)

        jRootArray.put(""+Constant.priceHigh)

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
        //obj.put("categoryList", jRootArray1)

        obj.put("hignToLow", ""+ Constant.priceHighToLow)
        obj.put("lowToHigh", ""+ Constant.priceLowToHigh)
        obj.put("ratingHighToLow", ""+ Constant.ratingLowToHigh)
        if(Constant.valueStringFour != 0.0) {
            obj.put("rating", "" + Constant.valueStringFour)
        }
        obj.put("categoryList", jRootArray1)
        if(!Constant.priceLow.equals("0") && !Constant.priceHigh.equals("0")) {
            obj.put("costPerPerson", jRootArray)
        }

        RequestManager.setFilterGroceryData(activity, obj, this);

        Log.v("llll == "+obj,"pppp"+jRootArray1)


        //loadingScreen(activity)
    }
    fun filter() {
        val jRootArray = JSONArray()


        val jRootArray1 = JSONArray()

        for (i in 0 until Constant.adapterCategories10.size) {
            jRootArray1.put(Constant.adapterCategories10.get(i).name)
        }
        Log.v(";;;"+jRootArray1,"===")

        val obj = JSONObject()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)
        obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")
        obj.put("orderBy", "2")
        obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)
        obj.put("categoryList", jRootArray1)
        RequestManager.setFilterGroceryData(activity, obj, this);

        Log.v(";;;"+obj,"===")

        //loadingScreen(activity)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {

        swiperefresh!!.setRefreshing(false);

        if(responseObj != null) {
            if (requestType == Constant.MEMBER_tableFavoriteStoreAdd_URL_RQ) {
                if ((responseObj as BaseRS).status.equals("5820")) {
                 wedServicePage()
                }else if ((responseObj as BaseRS).status.equals("5818")){
                    wedServicePage()
                }else if ((responseObj as BaseRS).status.equals("5819")){
                    wedServicePage()
                }
            }else if (requestType == Constant.MEMBER_userGroceryDashboard_URL_RQ) {
                if ((responseObj as BaseRS).status.equals("5801")) {

                    if (adapterFamous1.size != 0) {
                        adapterFamous1.clear()
                    }

                    if (adapterTrending.size != 0) {
                        adapterTrending.clear()
                    }

                    if (adapterProduct.size != 0) {
                        adapterProduct.clear()
                    }
                    if (adapterFeature.size != 0) {
                        adapterFeature.clear()
                    }
                    for (i in 0 until (responseObj as BaseRS).fetchData!!.featureStoreList!!.size) {
                        adapterModel = AdapterModel(
                            R.drawable.gocery_5,
                            "" + (responseObj as BaseRS).fetchData!!.featureStoreList!!.get(i).groceryName,
                            "" + (responseObj as BaseRS).groceryPath + (responseObj as BaseRS).fetchData!!.featureStoreList!!.get(
                                i
                            ).folderName + "/" + getImageValue1(
                                (responseObj as BaseRS).fetchData!!.featureStoreList!!.get(
                                    i
                                ).groceryImages
                            ),
                            ""+ (responseObj as BaseRS).fetchData!!.featureStoreList!!.get(i).street+","+ (responseObj as BaseRS).fetchData!!.featureStoreList!!.get(i).town,
                            "Save 60 %",
                            ""+ (responseObj as BaseRS).fetchData!!.featureStoreList!!.get(i).groceryReferenceCode,
                            " 425",
                            ""+(responseObj as BaseRS).fetchData!!.featureStoreList!!.get(i).rating,
                            ""+(responseObj as BaseRS).fetchData!!.featureStoreList!!.get(i).distance+" KM",
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
                    }

                    if (allRestrant.size != 0) {
                        allRestrant.clear()
                    }
                    Constant.favStringValue = ""
                    for (i in 0 until (responseObj as BaseRS).fetchData!!.groceryListing!!.size) {
                        /* if (Constant.favStringValue.equals("")) {
                            Constant.favStringValue =
                                "" + (responseObj as BaseRS).fetchData!!.favoriteList!!.get(i).restaurantId
                        } else {
                            Constant.favStringValue =
                                Constant.favStringValue + "," + (responseObj as BaseRS).fetchData!!.favoriteList!!.get(
                                    i
                                ).restaurantId
                        }
*/
                    }
                    Log.v("Value", "000 " + Constant.favStringValue);


                    for (i in 0 until (responseObj as BaseRS).fetchData!!.groceryListing!!.size) {
                        var categoryValue = ""
                        for (j in 0 until (responseObj as BaseRS).fetchData!!.groceryListing!!.get(i).storeCategoryList!!.size) {
                            if(categoryValue!!.equals(""))
                            {
                                categoryValue = (responseObj as BaseRS).fetchData!!.groceryListing!!.get(i).storeCategoryList!!.get(
                                            j
                                        ).categoryName!!
                            }else {
                                categoryValue =categoryValue+","+
                                    (responseObj as BaseRS).fetchData!!.groceryListing!!.get(i).storeCategoryList!!.get(
                                        j
                                    ).categoryName!!
                            }
                        }
                        if(!Constant.valueString.equals("Filter"))
                        {

                            adapterModel = AdapterModel(
                                R.drawable.recommended,
                                "" + (responseObj as BaseRS).fetchData!!.groceryListing!!.get(i).groceryName,
                                "" + categoryValue,
                                (responseObj as BaseRS).fetchData!!.groceryListing!!.get(i).distance + " KM - " + (responseObj as BaseRS).fetchData!!.groceryListing!!.get(
                                    i
                                ).town,
                                "Open now",
                                "€ " + (responseObj as BaseRS).fetchData!!.groceryListing!!.get(
                                        i
                                    ).minimumOrderAmount+ " minimum order",
                                "" + (responseObj as BaseRS).fetchData!!.groceryListing!!.get(i).rating,
                                "",
                                "4.4",
                                "" + (responseObj as BaseRS).fetchData!!.groceryListing!!.get(i).groceryReferenceCode,
                                "3054 Ratings>",
                                "5 Star Given by you",
                                "1",
                                "" + (responseObj as BaseRS).fetchData!!.groceryListing!!.get(i).groceryReferenceCode,
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
                            allRestrant.add(adapterModel)
                        } else {
                            filter()
                        }
                    }

                    Constant.favStringGroceryValue = ""
                    for (i in 0 until (responseObj as BaseRS).fetchData!!.favoriteList!!.size) {
                        if (Constant.favStringGroceryValue.equals("")) {
                            Constant.favStringGroceryValue =
                                "" + (responseObj as BaseRS).fetchData!!.favoriteList!!.get(i).groceryId
                        } else {
                            Constant.favStringGroceryValue =
                                Constant.favStringGroceryValue + "," + (responseObj as BaseRS).fetchData!!.favoriteList!!.get(
                                    i
                                ).groceryId
                        }

                    }

                    Log.v("fave","==== "+Constant.favStringGroceryValue)

                    for (i in 0 until (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.size) {
                        if (i != 0) {
                            productModel = Product(
                                R.drawable.img_1,
                                "" + (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(
                                    i
                                ).groceryName,
                                "" + (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(i).rating,
                                ""+  (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(i).groceryReferenceCode,
                                ""+ (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(i).street+","+ (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(i).town,
                                ""+ (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(i).distance+" KM",
                                "" + (responseObj as BaseRS).groceryPath + (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(
                                    i
                                ).folderName + "/" + getImageValue1(
                                    (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(
                                        i
                                    ).groceryImages
                                ),
                                ""+ (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(i).groceryId,
                                "0",
                                "" + (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(
                                    i
                                ).groceryImages,
                                "Gro",
                                ""+ (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(i).street+","+ (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(i).town,
                                "",
                                "",
                                "",
                                "","","",
                                adapterProductList
                            )
                            adapterProduct.add(productModel)
                        }
                    }
                    if((responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.size!=0) {
                        if (adapterTrending.size != 0) {
                            adapterTrending.clear()
                        }
                    }
                    for (i in 0 until (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.size) {


                        adapterModel = AdapterModel(
                            R.drawable.gocery_3,
                            "" + (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(i).groceryName,
                            "" + (responseObj as BaseRS).groceryPath + (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(
                                i
                            ).folderName + "/" + getImageValue1(
                                (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(
                                    i
                                ).groceryImages
                            ),
                            "" + (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(i).street + "," + (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(
                                i
                            ).town,
                            "" + (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(i).groceryReferenceCode,
                            " 976",
                            " 425",
                            "Tatabad,Gandhipuram",
                            "4.4",
                            ""+ (responseObj as BaseRS).fetchData!!.recommendedGroceryList!!.get(i).groceryId,
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
                    }
                    if(adapterFamous1!!.size != 0)
                    {
                        adapterFamous1!!.clear()
                    }
                    for (i in 0 until (responseObj as BaseRS).fetchData!!.groceryCategoryList!!.size) {
                        adapterModel = AdapterModel(
                            R.drawable.food,
                            "" + (responseObj as BaseRS).fetchData!!.groceryCategoryList!!.get(i).name,
                            "" + (responseObj as BaseRS).fetchData!!.groceryCategoryList!!.get(i).categoryReferenceCode,
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

                        Log.v(
                            "llll" + (responseObj as BaseRS).fetchData!!.groceryCategoryList!!.get(
                                i
                            ).categoryImage, "==="
                        )

                        adapterFamous1.add(adapterModel)
                    }
                    addProduct()

                    if(adapterTrending!!.size == 0 && adapterFeature!!.size == 0 && adapterFamous!!.size == 0 && allRestrant!!.size == 0)
                    {
                        noRestaurantLayout!!.visibility = View.VISIBLE
                        allRecyclerView!!.visibility = View.GONE
                        noRestaurant!!.text ="Sorry, we are not serving this delivery location.Hang on tight!. Soon we will be there"
                        //groceryTV!!.visibility = View.VISIBLE
                    }else{
                        noRestaurantLayout!!.visibility = View.GONE

                        allRecyclerView!!.visibility = View.VISIBLE
                    }
                    adapter!!.notifyDataSetChanged()

                }
            } else if (requestType == Constant.MEMBER_filterGroceryData_URL_RQ) {
                if ((responseObj as BaseRS).status.equals("5804")) {
                    if (allRestrant.size != 0) {
                        allRestrant.clear()
                    }
                    for (i in 0 until (responseObj as BaseRS).fetchData!!.groceryListing!!.size) {

                        var categoryValue = ""
                        if((responseObj as BaseRS).fetchData!!.groceryListing!!.get(i).storeCategoryList != null) {
                            for (j in 0 until (responseObj as BaseRS).fetchData!!.groceryListing!!.get(
                                i
                            ).storeCategoryList!!.size) {
                                if (categoryValue!!.equals("")) {
                                    categoryValue =
                                        (responseObj as BaseRS).fetchData!!.groceryListing!!.get(i).storeCategoryList!!.get(
                                            j
                                        ).categoryName!!
                                } else {
                                    categoryValue = categoryValue + "," +
                                            (responseObj as BaseRS).fetchData!!.groceryListing!!.get(
                                                i
                                            ).storeCategoryList!!.get(
                                                j
                                            ).categoryName!!
                                }
                            }
                        }
                            adapterModel = AdapterModel(
                                R.drawable.recommended,
                                "" + (responseObj as BaseRS).fetchData!!.groceryListing!!.get(i).groceryName,
                                "" + categoryValue,
                                (responseObj as BaseRS).fetchData!!.groceryListing!!.get(i).distance + " KM - " + (responseObj as BaseRS).fetchData!!.groceryListing!!.get(
                                    i
                                ).town,
                                "Open now",
                                "€ " +  (responseObj as BaseRS).fetchData!!.groceryListing!!.get(
                                        i
                                    ).minimumOrderAmount+ " for Minimum Order",
                                "" + (responseObj as BaseRS).fetchData!!.groceryListing!!.get(i).rating,
                                "",
                                "4.4",
                                "" + (responseObj as BaseRS).fetchData!!.groceryListing!!.get(i).groceryReferenceCode,
                                "3054 Ratings>",
                                "5 Star Given by you",
                                "1",
                                "" + (responseObj as BaseRS).fetchData!!.groceryListing!!.get(i).groceryReferenceCode,
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
                            allRestrant.add(adapterModel)
                        }
                    adapter!!.notifyDataSetChanged()
                    Constant.valueString = ""

                    Constant.valueStringFour =0.0

                    Constant.priceLow = "0"

                    Constant.priceHigh = "0"

                    if(Constant.adapterCategories10!!.size != 0)
                    {
                        Constant.adapterCategories10!!.clear()
                    }

                    Constant.priceHighToLow = ""
                    Constant.ratingLowToHigh = ""
                    Constant.priceLowToHigh = ""

                    allRecyclerView!!.smoothScrollToPosition(list.size-1)
                }
            }else if (requestType == Constant.MEMBER_tableFavoriteStoreAdd_URL_RQ) {
                showToast(activity!!,""+(responseObj as BaseRS).message)
                wedServicePage()
                /*if ((responseObj as BaseRS).status.equals("5821")) {

                }else if((responseObj as BaseRS).status.equals("5818")) {
                    wedServicePage()
                }*/
            }
        }

    }

}
