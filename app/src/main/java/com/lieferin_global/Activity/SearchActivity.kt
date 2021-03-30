package com.lieferin_global.Activity

import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.SearchAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import org.json.JSONObject
import java.util.*

class SearchActivity : AppCompatActivity(),View.OnClickListener,ResponseListener,SearchAdapter.CallbackDataAdapter {

    var restaurantList : RecyclerView? = null

    var searchEditText : EditText? = null

    var addressTextView : TextView? = null

    var mapIV : ImageView? = null

    var searchAdapter: SearchAdapter? = null

    var adapterFeature: MutableList<AdapterModel> = ArrayList()

    var adapterFeature1: MutableList<AdapterModel> = ArrayList()

    var adapterProduct: MutableList<Product> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    var searchImageView : ImageView? =null

    var dbHelper : DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        dbHelper = DBHelper(this)
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

        mapIV = findViewById(R.id.mapIV)

        mapIV!!.setColorFilter(
            colorIcon(this, R.color.colorBlack, R.drawable.abc_ic_ab_back_material, mapIV!!),
            PorterDuff.Mode.SRC_ATOP
        )

        mapIV!!.setOnClickListener(this)

        searchImageView = findViewById(R.id.searchImageView) as ImageView

        searchImageView!!.setColorFilter(
            colorIcon(this!!, R.color.colorGray, R.drawable.search, searchImageView!!),
            PorterDuff.Mode.SRC_ATOP
        )

        searchEditText = findViewById(R.id.searchEditText)

        searchEditText!!.typeface = fontStyle(this,"Light")

        addressTextView = findViewById(R.id.addressTextView)

        addressTextView!!.typeface = fontStyle(this,"")

        restaurantList = findViewById(R.id.restaurantList)

        searchAdapter = SearchAdapter(this, adapterFeature)
        restaurantList!!.setHasFixedSize(true)
        restaurantList!!.setLayoutManager(
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        )
        restaurantList!!.isNestedScrollingEnabled =
            false
        searchAdapter!!.setCallback(this)
        restaurantList!!.setAdapter(
            searchAdapter
        )

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


        //showDataFeature()

        val extras = intent.extras
        val userName: String?
        if (extras != null) {
            if(extras.getString("Address") != null) {

            addressTextView!!.text = ""+extras.getString("Address")

            }
        }

        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)
        /*obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")*/
      obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)
        obj.put("distance", "50")

        RequestManager.setDashBoardActivity(this, obj, this);

        loadingScreen(this)

    }
    fun searchValue(value : String){


        if (adapterFeature.size != 0) {
            adapterFeature.clear()
        }

        for (i in 0 until adapterFeature1.size) {
            if(adapterFeature1.get(i).categoryName!!.toLowerCase().contains(value.toLowerCase())) {
                adapterModel = AdapterModel(
                    R.drawable.img_4,
                    "" + adapterFeature1.get(i).categoryName,
                    "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNdVp58T_eVgzLEw-_dpw.730x390.jpg",
                    "Gandhipuram",
                    "Order Now",
                    ""+adapterFeature1.get(i).categoryImage,
                    ""+adapterFeature1.get(i).offer,
                    ""+adapterFeature1.get(i).parlourAddress,
                    "4.4",
                    "Very Good",
                    "3054 Ratings>",
                    "5 Star Given by you",
                    "1",
                    "",
                    ""+adapterFeature1.get(i).menuImages,
                    ""+adapterFeature1.get(i).openTime,
                    "",
                    "",
                    0,
                    0,
                    0,
                    adapterProduct
                )
                adapterFeature.add(adapterModel)
            }

        }
        searchAdapter!!.notifyDataSetChanged()

    }

    fun searchValue(){
        if (adapterFeature.size != 0) {
            adapterFeature.clear()
        }

        for (i in 0 until adapterFeature1.size) {

            adapterModel = AdapterModel(R.drawable.img_4, ""+adapterFeature1.get(i).categoryName, "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNdVp58T_eVgzLEw-_dpw.730x390.jpg", "Gandhipuram", "Order Now", ""+adapterFeature1.get(i).categoryImage, ""+adapterFeature1.get(i).offer,
                ""+adapterFeature1.get(i).parlourAddress, "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", ""+adapterFeature1.get(i).menuImages, ""+adapterFeature1.get(i).openTime, "", "", 0, 0, 0,adapterProduct)
            adapterFeature.add(adapterModel)

        }
        searchAdapter!!.notifyDataSetChanged()
    }

    fun showDataFeature() {

        if (adapterFeature.size != 0) {
            adapterFeature.clear()
        }
        adapterModel = AdapterModel(R.drawable.img_4, "Thalapakatty Restaurant", "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNdVp58T_eVgzLEw-_dpw.730x390.jpg", "Gandhipuram", "Order Now", "Delivery in 49 mins", "4.0", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)
        adapterFeature1.add(adapterModel)
        adapterModel = AdapterModel(R.drawable.img_5, "SMS Multi Cusine Restaurant", "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNdVp58T_eVgzLEw-_dpw.730x390.jpg", "Gandhipuram", "Order Now", "Delivery in 49 mins", "4.0", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)
        adapterFeature1.add(adapterModel)
        adapterModel = AdapterModel(R.drawable.img_3, "Idly Virundhu", "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNdVp58T_eVgzLEw-_dpw.730x390.jpg", "Gandhipuram", "Order Now", "Delivery in 49 mins", "4.0", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)
        adapterFeature1.add(adapterModel)
        adapterModel = AdapterModel(R.drawable.img_2, "Vignesh Canteen", "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNdVp58T_eVgzLEw-_dpw.730x390.jpg", "Gandhipuram", "Order Now", "Delivery in 49 mins", "4.0", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)
        adapterFeature1.add(adapterModel)

        searchAdapter!!.notifyDataSetChanged()
    }

    override fun onClick(view: View?) {

        when(view!!.id)
        {
            R.id.mapIV->{

                finish()
            }
        }

    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if(responseObj != null) {
            loadingScreenClose(this)
            if (requestType == Constant.MEMBER_getUserRestaurantDashBoard_URL_RQ) {
                if (adapterFeature.size != 0) {
                    adapterFeature.clear()
                }
                if ((responseObj as BaseRS).status.equals("3024")) {
                    for (i in 0 until (responseObj as BaseRS).fetchData!!.restaurantListing!!.size) {

                        adapterModel = AdapterModel(R.drawable.img_4, ""+(responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).restaurantName, "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNdVp58T_eVgzLEw-_dpw.730x390.jpg", ""+(responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).street+","+(responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).town, "Order Now", ""+(responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).distance+" KM Away", ""+(responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).rating, ""+(responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).restaurantReferenceCode, "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", ""+ (responseObj as BaseRS).restaurantPath+"/"+ (responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).folderName, ""+ getImageValue1((responseObj as BaseRS).fetchData!!.restaurantListing!!.get(i).restaurantImages), "", "", 0, 0, 0,adapterProduct)
                        adapterFeature.add(adapterModel)
                        adapterFeature1.add(adapterModel)
                    }

                    searchAdapter!!.notifyDataSetChanged()
                    }
            }
        }
    }



    override fun setOnMaterial(userId: AdapterModel?, isTrue: Boolean, id: String?, position: Int) {
        var intent = (Intent(this, DashBoardActivity::class.java))

        intent.putExtra("page","Details Page")

        intent.putExtra("Title",""+id)
        startActivity(intent)
    }
}
