package com.lieferin_global.Fragment

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Activity.DashBoardActivity
import com.lieferin_global.Activity.DashBoardGrpceryActivity
import com.lieferin_global.Adapter.GroceryListItemHorizontal1Adapter
import com.lieferin_global.Adapter.GroceryListItemHorizontal2Adapter
import com.lieferin_global.Adapter.GroceryListItemHorizontalAdapter
import com.lieferin_global.Adapter.ReviewAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.util.*


class ProductDetailsPageFragment : AppCompatActivity(),View.OnClickListener,GroceryListItemHorizontalAdapter.CallbackDataAdapter,GroceryListItemHorizontal1Adapter.CallbackDataAdapter,GroceryListItemHorizontal2Adapter.CallbackDataAdapter,ResponseListener {

    var rootView : View? = null

    var tableReservation_back : ImageView? = null

    var ratingIV : ImageView? = null

    var shareIV : ImageView? = null

    var productName : TextView? = null

    var productSize : TextView? = null

    var productPrice : TextView? = null

    var productPriceOffer : TextView? = null

    var productImg : ImageView? =null

    var applyCoupon : TextView? = null

    var couponLimit : TextView? = null

    var savePrice : TextView? = null

    var priceTag : ImageView? = null

    var CouponApplied : TextView? = null

    var tickImg : ImageView? = null

    var savePriceLL : LinearLayout? = null

    var applyLL : LinearLayout? = null

    var featuredProductsTV : TextView? = null

    var viewMoreTV : TextView? = null

    var featuredRecyclerView : RecyclerView? = null

    var relatedProductsTV : TextView? = null

    var viewMoreRlTV : TextView? = null

    var relatedRecyclerView : RecyclerView? = null

    var oftenBoughtProductsTV : TextView? = null

    var viewMoreOBTV : TextView? = null

    var oftenBoughtRecyclerView : RecyclerView? = null

    var detailedViewMainAdapter: GroceryListItemHorizontalAdapter? = null

    var detailedViewMainAdapter1: GroceryListItemHorizontal1Adapter? = null

    var detailedViewMainAdapter2: GroceryListItemHorizontal2Adapter? = null

    var adapterDetails: MutableList<AdapterModel> = ArrayList()

    var adapterDetails1: MutableList<AdapterModel> = ArrayList()

    var adapterDetails2: MutableList<AdapterModel> = ArrayList()

    var adapterDetails3: MutableList<AdapterModel> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    internal lateinit var productModel: Product

    var adapterDetailChild: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    var minusIV : ImageView? = null

    var addItem : TextView? = null

    var plusIV : ImageView? = null

    var addToCart : TextView? = null

    var descriptionTV : TextView? = null

    var reviewTV : TextView? = null

    var viewMoreRVTV : TextView? = null

    var reviewRecyclerView : RecyclerView? = null

    var reviewAdapter: ReviewAdapter? = null

    var groceryId : String? = ""

    var offerPrice : String? = ""

    var productId : String? = ""

    var categoryId : String? = ""

    var price : String? = ""

    var productReferenceCode : String? = ""

    var groceryReferenceCode : String? = ""

    var groceryStoreName : String? = ""

    var totalPrice =0.0

    var dbHelper : DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_product_details_page)
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

        tableReservation_back = findViewById(R.id.tableReservation_back) as ImageView

        tableReservation_back!!.setOnClickListener(this)

        tableReservation_back!!.setColorFilter(colorIcon(this,R.color.colorGreen,R.drawable.abc_ic_ab_back_material,tableReservation_back!!))

        tableReservation_back!!.setOnClickListener(this)

        ratingIV = findViewById(R.id.ratingIV) as ImageView

        ratingIV!!.setColorFilter(colorIcon(this,R.color.colorGreen,R.drawable.favorite_icon,ratingIV!!))

        ratingIV!!.setOnClickListener(this)

        shareIV = findViewById(R.id.shareIV) as ImageView

        //shareIV!!.setColorFilter(colorIcon(this,R.color.colorGreen,R.drawable.share_icon,shareIV!!))

        shareIV!!.setOnClickListener(this)

        minusIV = findViewById(R.id.minusIV) as ImageView

        minusIV!!.setColorFilter(colorIcon(this,R.color.colorGreen,R.drawable.minus,minusIV!!))

        minusIV!!.setOnClickListener(this)

        plusIV = findViewById(R.id.plusIV) as ImageView

        plusIV!!.setColorFilter(colorIcon(this,R.color.colorGreen,R.drawable.plus,plusIV!!))

        plusIV!!.setOnClickListener(this)

        addItem= findViewById(R.id.addItem) as TextView

        addItem!!.typeface = fontStyle(this,"")

        productName = findViewById(R.id.productName) as TextView

        productName!!.typeface = fontStyle(this,"SemiBold")

        productSize = findViewById(R.id.productSize) as TextView

        productSize!!.typeface = fontStyle(this,"Light")

        productPrice = findViewById(R.id.productPrice) as TextView

        productPrice!!.typeface = fontStyle(this,"SemiBold")

        productPriceOffer = findViewById(R.id.productPriceOffer) as TextView

        productPriceOffer!!.typeface = fontStyle(this,"Light")

        productImg = findViewById(R.id.productImg) as ImageView

        //Picasso.with(this).load("https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product03.jpg").into(productImg)

        applyCoupon= findViewById(R.id.applyCoupon) as TextView

        applyCoupon!!.typeface = fontStyle(this,"SemiBold")

        applyCoupon!!.setOnClickListener(this)

        couponLimit= findViewById(R.id.couponLimit) as TextView

        couponLimit!!.typeface = fontStyle(this,"Light")

        savePrice= findViewById(R.id.savePrice) as TextView

        savePrice!!.typeface = fontStyle(this,"SemiBold")

        priceTag= findViewById(R.id.priceTag) as ImageView

        priceTag!!.setColorFilter(colorIcon(this,R.color.colorRose,R.drawable.price_tag,priceTag!!))

        tickImg= findViewById(R.id.tickImg) as ImageView

        tickImg!!.setColorFilter(colorIcon(this,R.color.colorBlack,R.drawable.tick,tickImg!!))

        savePriceLL =findViewById(R.id.savePriceLL) as LinearLayout

        applyLL =findViewById(R.id.applyLL) as LinearLayout

        CouponApplied= findViewById(R.id.CouponApplied) as TextView

        CouponApplied!!.typeface = fontStyle(this,"")

        featuredProductsTV= findViewById(R.id.featuredProductsTV) as TextView

        featuredProductsTV!!.typeface = fontStyle(this,"SemiBold")

        viewMoreTV= findViewById(R.id.viewMoreTV) as TextView

        viewMoreTV!!.typeface = fontStyle(this,"SemiBold")

        relatedProductsTV= findViewById(R.id.relatedProductsTV) as TextView

        relatedProductsTV!!.typeface = fontStyle(this,"SemiBold")

        viewMoreRlTV= findViewById(R.id.viewMoreRlTV) as TextView

        viewMoreRlTV!!.typeface = fontStyle(this,"SemiBold")

        oftenBoughtProductsTV= findViewById(R.id.oftenBoughtProductsTV) as TextView

        oftenBoughtProductsTV!!.typeface = fontStyle(this,"SemiBold")

        viewMoreOBTV= findViewById(R.id.viewMoreOBTV) as TextView

        viewMoreOBTV!!.typeface = fontStyle(this,"SemiBold")

        featuredRecyclerView = findViewById(R.id.featuredRecyclerView) as RecyclerView

        relatedRecyclerView = findViewById(R.id.relatedRecyclerView) as RecyclerView

        oftenBoughtRecyclerView = findViewById(R.id.oftenBoughtRecyclerView) as RecyclerView

        detailedViewMainAdapter = GroceryListItemHorizontalAdapter(this,adapterDetails)

        featuredRecyclerView!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))

        detailedViewMainAdapter!!.setCallback(this)

        featuredRecyclerView!!.setHasFixedSize(true)

        featuredRecyclerView!!.isNestedScrollingEnabled = false

        featuredRecyclerView!!.setAdapter(detailedViewMainAdapter)

        detailedViewMainAdapter1 = GroceryListItemHorizontal1Adapter(this,adapterDetails1)

        relatedRecyclerView!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))

        relatedRecyclerView!!.setHasFixedSize(true)

        relatedRecyclerView!!.isNestedScrollingEnabled = false

        detailedViewMainAdapter1!!.setCallback(this)

        relatedRecyclerView!!.setAdapter(detailedViewMainAdapter1)

        detailedViewMainAdapter2 = GroceryListItemHorizontal2Adapter(this,adapterDetails2)

        oftenBoughtRecyclerView!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))

        oftenBoughtRecyclerView!!.setHasFixedSize(true)

        oftenBoughtRecyclerView!!.isNestedScrollingEnabled = false

        detailedViewMainAdapter2!!.setCallback(this)

        oftenBoughtRecyclerView!!.setAdapter(detailedViewMainAdapter2)

        //showDataTrending()

        //showDataTrending1()

        //showDataTrending2()

        addToCart = findViewById(R.id.addToCart) as TextView

        addToCart!!.typeface = fontStyle(this,"SemiBold")

        addToCart!!.setOnClickListener(this)

        descriptionTV = findViewById(R.id.descriptionTV) as TextView

        descriptionTV!!.typeface = fontStyle(this,"Light")

        reviewTV = findViewById(R.id.reviewTV) as TextView

        reviewTV!!.typeface = fontStyle(this,"SemiBold")

        viewMoreRVTV = findViewById(R.id.viewMoreRVTV) as TextView

        viewMoreRVTV!!.typeface = fontStyle(this,"SemiBold")

        reviewRecyclerView = findViewById(R.id.reviewRecyclerView) as RecyclerView

        reviewAdapter = ReviewAdapter(this,adapterDetails3)

        reviewRecyclerView!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))

        reviewRecyclerView!!.setHasFixedSize(true)

        reviewRecyclerView!!.isNestedScrollingEnabled = false

        //reviewAdapter!!.setCallback(this)

        reviewRecyclerView!!.setAdapter(reviewAdapter)

        //showDataTrending3()

        var getBundle: Bundle? = null
        getBundle = intent.extras
        groceryId = getBundle!!.getString("groceryId")

        productId = getBundle!!.getString("productId")

        offerPrice = getBundle!!.getString("offerPrice")

        categoryId = getBundle!!.getString("categoryId")

        groceryReferenceCode = getBundle!!.getString("groceryReferenceCode")

        groceryStoreName = getBundle!!.getString("groceryStoreName")

        Constant.DeliveryType =getBundle!!.getString("Delivery Type").toString()

        webService()
    }

    fun webService()
    {
        val obj = JSONObject()
        obj.put("groceryId", ""+groceryId)

        obj.put("productId", ""+productId)

        obj.put("categoryId", ""+categoryId)

        obj.put("offerPrice", ""+offerPrice)

        /*obj.put("groceryId", "1")

        obj.put("productId", "1")

        obj.put("categoryId", "3")

        obj.put("offerPrice", "0")*/

        Log.v("Json", "Value" + obj)
        RequestManager.setProduct(this, obj, this);

        loadingScreen(this)
    }


    override fun onClick(view: View?) {
        when(view!!.id)
        {

            R.id.tableReservation_back ->{

                finish()
            }

            R.id.ratingIV ->{

            }

            R.id.shareIV ->{

            }

            R.id.plusIV ->{

                addItem!!.text = ""+addIncrease(addItem!!.text.toString())

                if(price != null) {

                    totalPrice = addIncreasePriceHole(price!!, addItem!!.text.toString())
                }

            }

            R.id.minusIV ->{

                if(!addItem!!.text.toString().equals("1"))
                {
                    addItem!!.text = ""+addDecrease(addItem!!.text.toString())
                    if(price != null) {
                        totalPrice = addIncreasePriceHole(price!!, addItem!!.text.toString())
                    }
                }

            }

            R.id.applyCoupon ->{

                applyCoupon!!.visibility = View.GONE

                applyLL!!.visibility = View.VISIBLE

                savePriceLL!!.visibility = View.VISIBLE

                couponLimit!!.visibility = View.VISIBLE

            }

            R.id.addToCart->{

                dbHelper!!.deleteGroceryFull(productReferenceCode!!)
                dbHelper!!.addItemCategory_infoValue(groceryId!!,groceryReferenceCode!!,productName!!.text.toString(),groceryStoreName!!,productReferenceCode!!,price!!,offerPrice!!,addItem!!.text.toString(),totalPrice!!.toString())

                var intent = Intent(this, DashBoardGrpceryActivity::class.java)

                intent.putExtra("Title", "" + groceryReferenceCode)

                intent.putExtra("page", getString(R.string.Details_Page))

                intent.putExtra("Delivery Type",Constant.DeliveryType)

                startActivity(intent)
            }

        }
    }

    override fun setOnExtraItem(type: String?, id: String?) {
        for (i in 0 until adapterDetails.size) {
            if (id!!.toInt() == i) {

                adapterDetails.get(i).parlourRatingValue = "1"
            } else {
                adapterDetails.get(i).parlourRatingValue = "0"
            }
        }

        detailedViewMainAdapter!!.notifyDataSetChanged()
    }

    override fun setOnExtraItem1(type: String?, id: String?) {
        for (i in 0 until adapterDetails1.size) {
            if (id!!.toInt() == i) {

                adapterDetails1.get(i).parlourRatingValue = "1"
            } else {
                adapterDetails1.get(i).parlourRatingValue = "0"
            }
        }
        detailedViewMainAdapter1!!.notifyDataSetChanged()
    }

    override fun setOnExtraItem2(type: String?, id: String?) {
        for (i in 0 until adapterDetails2.size) {
            if (id!!.toInt() == i) {

                adapterDetails2.get(i).parlourRatingValue = "1"
            } else {
                adapterDetails2.get(i).parlourRatingValue = "0"
            }
        }
        detailedViewMainAdapter2!!.notifyDataSetChanged()
    }

    override fun setOnFavourite(isFav: String?, id: String?) {

    }

    override fun setOnProduct(isFav: String?, id: String?) {
        val arrOfStr1 =
            id!!.split("-")
        val obj = JSONObject()
        groceryId=isFav

        offerPrice="0"
        obj.put("groceryId", ""+isFav)

        obj.put("productId", ""+arrOfStr1[0])

        obj.put("categoryId", ""+arrOfStr1[1])

        obj.put("offerPrice", "0")

        Log.v("Json", "Value" + obj)

        RequestManager.setProduct(this, obj, this);

        loadingScreen(this)
    }
    fun showDataTrending() {

        if (adapterDetails.size != 0) {
            adapterDetails.clear()
        }
        adapterModel = AdapterModel(0, "Ornare sed consequat nisl eget", "€ 16.19", "3 pec", "Deal ended soon", "€ 20.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product03.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(0, "Ornare sed consequat nisl eget", "€ 11.19", "2 pec", "Deal ended soon", "€ 15.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product04.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(0, "Ornare sed consequat nisl eget", "€ 25.19", "1   pec", "Deal ended soon", "€ 30.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product05.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(0, "Ornare sed consequat nisl eget", "€ 16.19", "3 pec", "Deal ended soon", "€ 20.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product06.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(0, "Ornare sed consequat nisl eget", "€ 11.19", "2 pec", "Deal ended soon", "€ 15.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product07.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(0, "Ornare sed consequat nisl eget", "€ 25.19", "1   pec", "Deal ended soon", "€ 30.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product09.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails.add(adapterModel)
        //hotDealsAdapter!!.notifyDataSetChanged()
    }

    fun showDataTrending1() {

        if (adapterDetails1.size != 0) {
            adapterDetails1.clear()
        }
        adapterModel = AdapterModel(0, "Ornare sed consequat nisl eget", "€ 16.19", "3 pec", "Deal ended soon", "€ 20.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product03.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails1.add(adapterModel)

        adapterModel = AdapterModel(0, "Ornare sed consequat nisl eget", "€ 11.19", "2 pec", "Deal ended soon", "€ 15.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product04.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails1.add(adapterModel)

        adapterModel = AdapterModel(0, "Ornare sed consequat nisl eget", "€ 25.19", "1   pec", "Deal ended soon", "€ 30.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product05.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails1.add(adapterModel)

        adapterModel = AdapterModel(0, "Ornare sed consequat nisl eget", "€ 16.19", "3 pec", "Deal ended soon", "€ 20.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product06.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails1.add(adapterModel)

        adapterModel = AdapterModel(0, "Ornare sed consequat nisl eget", "€ 11.19", "2 pec", "Deal ended soon", "€ 15.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product07.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails1.add(adapterModel)

        adapterModel = AdapterModel(0, "Ornare sed consequat nisl eget", "€ 25.19", "1   pec", "Deal ended soon", "€ 30.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product09.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails1.add(adapterModel)
        //hotDealsAdapter!!.notifyDataSetChanged()
    }

    fun showDataTrending2() {

        if (adapterDetails2.size != 0) {
            adapterDetails2.clear()
        }
        adapterModel = AdapterModel(0, "Ornare sed consequat nisl eget", "€ 16.19", "3 pec", "Deal ended soon", "€ 20.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product03.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails2.add(adapterModel)

        adapterModel = AdapterModel(0, "Ornare sed consequat nisl eget", "€ 11.19", "2 pec", "Deal ended soon", "€ 15.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product04.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails2.add(adapterModel)

        adapterModel = AdapterModel(0, "Ornare sed consequat nisl eget", "€ 25.19", "1   pec", "Deal ended soon", "€ 30.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product05.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails2.add(adapterModel)

        adapterModel = AdapterModel(0, "Ornare sed consequat nisl eget", "€ 16.19", "3 pec", "Deal ended soon", "€ 20.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product06.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails2.add(adapterModel)

        adapterModel = AdapterModel(0, "Ornare sed consequat nisl eget", "€ 11.19", "2 pec", "Deal ended soon", "€ 15.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product07.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails2.add(adapterModel)

        adapterModel = AdapterModel(0, "Ornare sed consequat nisl eget", "€ 25.19", "1   pec", "Deal ended soon", "€ 30.19", "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product09.jpg", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails2.add(adapterModel)
        //hotDealsAdapter!!.notifyDataSetChanged()
    }

    fun showDataTrending3() {

        if (adapterDetails3.size != 0) {
            adapterDetails3.clear()
        }
        adapterModel = AdapterModel(0, "Stave Martin", "Feb 01, 2020", "5/5", "Deal ended soon", "€ 20.19", "https://www.droletechnologies.com/lieferin-html/web/grocerydelivery/images/placeholder.png", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails3.add(adapterModel)

        adapterModel = AdapterModel(0, "Mark Smith", "Jan 21, 2020", "5/5", "Deal ended soon", "€ 15.19", "https://www.droletechnologies.com/lieferin-html/web/grocerydelivery/images/placeholder.png", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails3.add(adapterModel)

        adapterModel = AdapterModel(0, "Stave Mark", "Jan 12, 2020", "5/5", "Deal ended soon", "€ 30.19", "https://www.droletechnologies.com/lieferin-html/web/grocerydelivery/images/placeholder.png", "0", "0", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
        adapterDetails3.add(adapterModel)

    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        loadingScreenClose(this)
        if(responseObj != null) {
            if (requestType == Constant.MEMBER_productView_URL_RQ) {

                if((responseObj as BaseRS).status.equals("5852"))
                {
                    productName!!.text = (responseObj as BaseRS).groceryProductData!!.productName

                    productSize!!.text = (responseObj as BaseRS).groceryProductData!!.quantity+" oz"

                    descriptionTV!!.text = (responseObj as BaseRS).groceryProductData!!.metaDescription

                    productReferenceCode = (responseObj as BaseRS).groceryProductData!!.productReferenceCode

                    price = (responseObj as BaseRS).groceryProductData!!.price

                    addItem!!.text = "1"

                    totalPrice= (responseObj as BaseRS).groceryProductData!!.price!!.toDouble()

                    if((responseObj as BaseRS).groceryProductData!!.offerPrice != null) {

                        if (!(responseObj as BaseRS).groceryProductData!!.offerPrice!!.equals("0")) {

                            productPrice!!.text =
                                "€ " + (responseObj as BaseRS).groceryProductData!!.offerPrice

                            productPriceOffer!!.text =
                                "€ " + (responseObj as BaseRS).groceryProductData!!.price

                            productPriceOffer!!.setPaintFlags(productPriceOffer!!.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

                            productPriceOffer!!.visibility = View.VISIBLE
                        } else {
                            productPrice!!.text =
                                "€ " + (responseObj as BaseRS).groceryProductData!!.price

                            productPriceOffer!!.visibility = View.GONE

                        }
                    }

                    Picasso.with(this).load((responseObj as BaseRS).groceryPath!!+"/"+(responseObj as BaseRS).groceryProductData!!.productImages).resize(450, 450)
                        .placeholder(R.drawable.item_placeholder_geocery).into(productImg)

                    if(adapterDetails1!!.size != 0)
                    {
                        adapterDetails1.clear()
                    }

                    for(i in 0 until (responseObj as BaseRS).reletedGroceryProductData!!.size)
                    {
                        adapterModel = AdapterModel(0, ""+(responseObj as BaseRS).reletedGroceryProductData!!.get(i).productName, "€ "+(responseObj as BaseRS).reletedGroceryProductData!!.get(i).price, (responseObj as BaseRS).reletedGroceryProductData!!.get(i).quantity+" pec", ""+(responseObj as BaseRS).reletedGroceryProductData!!.get(i).metaDescription, "€ 20.19", (responseObj as BaseRS).groceryPath!!+"/"+(responseObj as BaseRS).reletedGroceryProductData!!.get(i).productImages, "0", "0", ""+(responseObj as BaseRS).reletedGroceryProductData!!.get(i).groceryId, ""+(responseObj as BaseRS).reletedGroceryProductData!!.get(i).productId, ""+(responseObj as BaseRS).reletedGroceryProductData!!.get(i).categoryId, "1", "", "", "", "", "", 0, 0, 0,adapterDetailChild)
                        adapterDetails1.add(adapterModel)
                    }

                    detailedViewMainAdapter1!!.notifyDataSetChanged()
                }
            }
        }
    }

}
