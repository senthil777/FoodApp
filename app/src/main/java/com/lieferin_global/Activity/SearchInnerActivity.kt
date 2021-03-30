package com.lieferin_global.Activity

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.DetailedViewMainAdapter
import com.lieferin_global.Adapter.SearchAdapter
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductListView
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.util.*

class SearchInnerActivity : AppCompatActivity(),View.OnClickListener,ResponseListener,DetailedViewMainAdapter.CallbackDataAdapter {

    var restaurantList : RecyclerView? = null

    var searchEditText : EditText? = null

    var hotelName1 : TextView? = null

    var hotelDescription1 : TextView? = null

    var back : ImageView? = null

    var hotelIcon1 : ImageView? =null

    var searchAdapter: SearchAdapter? = null

    var adapterFeature: MutableList<AdapterModel> = ArrayList()

    var adapterFeature1: MutableList<AdapterModel> = ArrayList()

    var adapterProduct: MutableList<Product> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    var searchImageView : ImageView? =null

    var detailsRecyclerView: RecyclerView? = null

    var detailedViewMainAdapter: DetailedViewMainAdapter? = null

    var categoryData: MutableList<AdapterModel>? = ArrayList()

    var categoryData1: MutableList<AdapterModel>? = ArrayList()

    var restrauntReference : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inner_search)

        if(localgetUserInfoSlash(this,"nameKey").equals(""))
        {
            val config = resources.configuration
            val locale = Locale("en")
            Locale.setDefault(locale)
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
        }else
        {
            val config = resources.configuration
            val locale = Locale(localgetUserInfo("nameKey"))
            Locale.setDefault(locale)
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
        }

        back = findViewById(R.id.back)

        back!!.setColorFilter(
            colorIcon(this, R.color.colorBlack, R.drawable.abc_ic_ab_back_material, back!!),
            PorterDuff.Mode.SRC_ATOP
        )

        back!!.setOnClickListener(this)

        hotelIcon1 = findViewById(R.id.hotelIcon1)

        hotelDescription1 = findViewById(R.id.hotelDescription1)

        //Picasso.with(this).load(R.drawable.img_2).resize(450, 450).into(hotelIcon1)

        searchImageView = findViewById(R.id.searchImageView) as ImageView

        searchImageView!!.setColorFilter(
            colorIcon(this!!, R.color.colorGray, R.drawable.search, searchImageView!!),
            PorterDuff.Mode.SRC_ATOP
        )

        searchEditText = findViewById(R.id.searchEditText)

        searchEditText!!.typeface = fontStyle(this,"Light")

        hotelName1 = findViewById(R.id.hotelName1)

        hotelName1!!.typeface = fontStyle(this,"")

        restaurantList = findViewById(R.id.restaurantList)

        detailsRecyclerView = findViewById(R.id.detailsRecyclerView) as RecyclerView

        //mAdapterShip = HomeShipAdapter(this, topRate)

        detailedViewMainAdapter = DetailedViewMainAdapter(this, categoryData!!)
        detailsRecyclerView!!.setLayoutManager(LinearLayoutManagerWithSmoothScroller(this))

        detailedViewMainAdapter!!.setCallback(this)

        detailsRecyclerView!!.setAdapter(detailedViewMainAdapter)


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


        showDataFeature()

        val extras = intent.extras
        val userName: String?
        if (extras != null) {
            if(extras.getString("Address") != null) {

            hotelName1!!.text = ""+extras.getString("Address")

            }
        }

        var getBundle: Bundle? = null
        getBundle = intent.extras
        restrauntReference = getBundle!!.getString("restaurantReferenceCode")

        val obj = JSONObject()
        obj.put("restaurantReferenceCode", "" + restrauntReference)
        /*obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")*/
        obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)
        Log.v("Json", "Value" + obj)
        RequestManager.setRestaurantActivity(this, obj, this);

    }
    fun searchValue(value : String){


        if (categoryData!!.size != 0) {
            categoryData!!.clear()
        }

        for (i in 0 until categoryData1!!.size) {
            if(categoryData1!!.get(i).categoryName!!.toLowerCase().contains(value.toLowerCase())) {
                adapterModel = AdapterModel(
                    R.drawable.img_4,
                    "" + categoryData1!!.get(i).categoryName,
                    "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNdVp58T_eVgzLEw-_dpw.730x390.jpg",
                    "Gandhipuram",
                    "Order Now",
                    "Delivery in 49 mins",
                    "4.0",
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
                    categoryData1!!.get(i).menusList!!
                )
                if(categoryData1!!.get(i).menusList!!.size != 0) {
                    categoryData!!.add(adapterModel)
                }
            }

        }
        detailedViewMainAdapter!!.notifyDataSetChanged()

    }

    fun searchValue(){
        if (categoryData!!.size != 0) {
            categoryData!!.clear()
        }

        for (i in 0 until categoryData1!!.size) {

                adapterModel = AdapterModel(
                    R.drawable.img_4,
                    "" + categoryData1!!.get(i).categoryName,
                    "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNdVp58T_eVgzLEw-_dpw.730x390.jpg",
                    "Gandhipuram",
                    "Order Now",
                    "Delivery in 49 mins",
                    "4.0",
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
                    categoryData1!!.get(i).menusList!!
                )
            if(categoryData1!!.get(i).menusList!!.size != 0) {
                categoryData!!.add(adapterModel)
            }


        }
        detailedViewMainAdapter!!.notifyDataSetChanged()
    }

    fun showDataFeature() {

    }

    override fun onClick(view: View?) {

        when(view!!.id)
        {
            R.id.back->{

                finish()
            }
        }

    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if (responseObj != null) {
            //loadingScreenClose(this)
            if (requestType == Constant.MEMBER_getRestaurantData_URL_RQ) {

                if ((responseObj as BaseRS).status.equals("3026")) {
                   //restaurantData = (responseObj as BaseRS).fetchData!!.restaurantData!!
                    hotelName1!!.setText((responseObj as BaseRS).fetchData!!.restaurantData!!.restaurantName)

                    var str = (responseObj as BaseRS).fetchData!!.restaurantData!!.restaurantImagesList

                    Log.v("===]]'''']"+str,"====")

                    str = str!!.replace("[","").toString()
                    str = str!!.replace("]","").toString()
                    val arrOfStr =
                        str.split(",")

                    Log.v("===]]]"+arrOfStr[0],"====")

                    if(!arrOfStr[0].equals("")) {


                    }
                    Picasso.with(this)
                        .load("http://api.lieferin.com/backend/public/Images/Restaurants/"+""+(responseObj as BaseRS).fetchData!!.restaurantData!!.folderName+"/"+arrOfStr[0])
                        .resize(450, 450).transform(RoundedTransformation(16,0)).placeholder(R.drawable.restaurant_placeholder)
                        .into(hotelIcon1)

                    /* ratingTV!!.text = (responseObj as BaseRS).fetchData!!.restaurantData!!.rating

                    restaurantId = ""+(responseObj as BaseRS).fetchData!!.restaurantData!!.restaurantId

                    restToken = ""+(responseObj as BaseRS).fetchData!!.restaurantData!!.restaurantReferenceCode

                    var str = (responseObj as BaseRS).fetchData!!.restaurantData!!.restaurantImagesList

                    Log.v("===]]'''']"+str,"====")

                    str = str!!.replace("[","").toString()
                    str = str!!.replace("]","").toString()
                    val arrOfStr =
                        str.split(",")

                    Log.v("===]]]"+arrOfStr[0],"====")

                    if(!arrOfStr[0].equals("")) {


                    }
                    //Picasso.with(activity!!).load(R.drawable.img_1).resize(450, 450).into(bannerImg)

                    hotelDescription!!.setText((responseObj as BaseRS).fetchData!!.restaurantData!!.street + "," + (responseObj as BaseRS).fetchData!!.restaurantData!!.town)
*/
                    hotelName1!!.setText((responseObj as BaseRS).fetchData!!.restaurantData!!.restaurantName)

                   hotelDescription1!!.setText((responseObj as BaseRS).fetchData!!.restaurantData!!.street + "," + (responseObj as BaseRS).fetchData!!.restaurantData!!.town)

                    if (categoryData!!.size != 0) {
                        categoryData!!.clear()
                    }

                    categoryData1 = (responseObj as BaseRS).fetchData!!.categoryData!!

                    for (i in 0 until (responseObj as BaseRS).fetchData!!.categoryData!!.size) {

                        if((responseObj as BaseRS).fetchData!!.categoryData!!.get(i).menusList.size !=0) {

                            adapterModel = AdapterModel(
                                0,
                                "" + (responseObj as BaseRS).fetchData!!.categoryData!!.get(i).categoryName,
                                "https://i2.wp.com/www.vegrecipesofindia.com/wp-content/uploads/2016/08/hara-bhara-kabab-2a.jpg",
                                "0",
                                "",
                                " 976",
                                " 425",
                                "Tatabad,Gandhipuram",
                                "4.4",
                                "" + (responseObj as BaseRS).fetchData!!.categoryData!!.get(i).categoryReferenceCode,
                                "3054 Ratings>",
                                "5 Star Given by you",
                                "" + (responseObj as BaseRS).fetchData!!.categoryData!!.get(i).categoryId,
                                "",
                                "",
                                "",
                                "",
                                "",
                                0,
                                0,
                                0,
                                (responseObj as BaseRS).fetchData!!.categoryData!!.get(i).menusList!!
                            )

                            categoryData!!.add(adapterModel)
                        }

                    }

                    detailedViewMainAdapter!!.notifyDataSetChanged()

                    //filterDetailsPageAdapter!!.notifyDataSetChanged()

                    /*if (dbHelper!!.getMenu() != null) {

                        if (dbHelper!!.getMenu().size != 0) {
                            for (i in 0 until dbHelper!!.getMenu().size) {

                                priceValue = priceValue + dbHelper!!.getMenu().get(i).totalPrice!!.toDouble()
                            }

                            addPrice!!.text = customText(
                                activity!!,
                                "€ " + DecimalFormat("##.##").format(priceValue) + " Plus Taxes",
                                2 ,
                                "SemiBold",
                                ""
                            )

                            addItemTV!!.text =
                                customText(activity!!, "Added Item : " + dbHelper!!.getMenuCountValue1(""+restaurantId), 13, "SemiBold", "")

                            priceLayout!!.visibility = View.VISIBLE
                        }
                    }*/
                }
            }
        }
    }

    override fun setOnItemClose(id: String?, position: Int?, isClose: String?) {

    }

    override fun setOnDetail(id: String?, position: Int?) {

    }

    override fun setOnInfo(id: String?, position: String?) {

    }

    override fun setOnQuantity(id: String?, pos: String?) {

    }

    override fun setOnPopup(adapterModels: List<ProductListView>, position: Int?) {

    }

    override fun setOnCancel(type: String?, position: Double?, isOpen: String?, pos: String?) {

    }

    override fun setOnPriceAdd(type: String?, itemName: String?, price: String?, position: Int?) {

    }

    override fun setFinal(type: String?, position: Double?, isOpen: String?, posValue: String?) {

    }

    override fun setDelete(type: String?, position: Double?, isOpen: String?, pos: String?) {

    }
}
