package com.lieferin_global.Activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.AllCategoryAdapter
import com.lieferin_global.Adapter.CouponListAdapter
import com.lieferin_global.Fragment.StripePaymentGateway
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
import com.lieferin_global.R
import com.lieferin_global.Utility.Constant
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.fontStyle
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import org.json.JSONObject
import java.util.ArrayList

class AppliyCoupon : AppCompatActivity(),View.OnClickListener,CouponListAdapter.CallbackDataAdapter,ResponseListener {
    var apply : TextView? = null

    var notificationTitle : TextView? = null

    var dbHelper : DBHelper? =null

    var availableTV : TextView? = null

    var applyCouponET : EditText? = null

    var back : ImageView? = null

    var cardName = ""

    var cardNumber = ""

    var expriyDate = ""

    var CouponCode = ""

    var total_value: String = ""

    var address_Reference = ""

    var itemValue = ""

    var couponListAdapter: CouponListAdapter? = null

    var adapterDetails: MutableList<AdapterModel> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    internal lateinit var productModel: Product

    var adapterDetailChild: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    var couponListRecyclerView : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appliy_coupon)

        apply = findViewById(R.id.apply) as TextView

        apply!!.setOnClickListener(this)

        dbHelper = DBHelper(this)

        apply!!.typeface = fontStyle(this,"SemiBold")

        notificationTitle = findViewById(R.id.notificationTitle) as TextView

        notificationTitle!!.typeface = fontStyle(this,"SemiBold")

        availableTV= findViewById(R.id.availableTV) as TextView

        availableTV!!.typeface = fontStyle(this,"SemiBold")

        applyCouponET = findViewById(R.id.applyCouponET) as EditText

        applyCouponET!!.addTextChangedListener(editor)

        applyCouponET!!.typeface = fontStyle(this,"SemiBold")

        back = findViewById(R.id.back) as ImageView

        back!!.setOnClickListener(this)

        val intent = intent

        if (intent != null) {
            if (intent.getStringExtra("Total Amount") != null) {
                total_value = intent.getStringExtra("Total Amount")
            }

            if (intent.getStringExtra("Address_Reference") != null) {
                address_Reference = intent.getStringExtra("Address_Reference")
            }

            if (intent.getStringExtra("item") != null) {
                itemValue = intent.getStringExtra("item")
            }

            if (intent.getStringExtra("Card Name") != null) {
                cardName = intent.getStringExtra("Card Name")
            }

            if (intent.getStringExtra("Card No") != null) {
                cardNumber = intent.getStringExtra("Card No")
            }

            if (intent.getStringExtra("ExpiryDate") != null) {
                expriyDate = intent.getStringExtra("ExpiryDate")
            }
        }

        couponListRecyclerView = findViewById(R.id.couponListRecyclerView) as RecyclerView

        couponListAdapter = CouponListAdapter(this,adapterDetails)
        couponListRecyclerView!!.setLayoutManager(
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        )
        couponListRecyclerView!!.isNestedScrollingEnabled =  false
        couponListAdapter!!.setCallback(this)

        couponListRecyclerView!!.setAdapter(couponListAdapter)

        if(AppType.equals("0")) {
            webService()
        }else{
            webServiceGrocery()
        }
    }

    fun webService(){
        val obj = JSONObject()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)


        Log.v("mmmmmmm","==="+obj);
        RequestManager.setFetchRestaurantPromocodeList(this,obj,this)
    }

    fun webServiceGrocery(){
        val obj = JSONObject()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)


        Log.v("mmmmmmm","==="+obj);
        RequestManager.setFetchGroceryPromoCode(this,obj,this)
    }
    private  val editor = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

            if(p0.toString().length ==0)
            {
                apply!!.setTextColor(Color.parseColor("#ECB687"))

            }else{
                apply!!.setTextColor(Color.parseColor("#F1821F"))
            }

        }

        override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
        }

    }
    override fun onClick(view: View?) {

        when(view!!.id)
        {
            R.id.back ->{
                var intent = Intent(this, StripePaymentGateway::class.java)

                /*intent.putExtra("latitude",""+latitude)

                intent.putExtra("longtitude",""+longtitude)*/
                startActivity(intent)

                finish()
            }
            R.id.apply ->{

                var intent = Intent(this, StripePaymentGateway::class.java)

                intent.putExtra("promocode", "" + applyCouponET!!.text.toString())

                intent.putExtra("Total Amount", "" + total_value)

                intent.putExtra("Address_Reference", "" + address_Reference)

                intent.putExtra("item", "" + itemValue)

                intent.putExtra("Card Name", "" + cardName)

                intent.putExtra("Card No", "" + cardNumber)

                intent.putExtra("ExpiryDate", "" + expriyDate)
                startActivity(intent)

                finish()
            }
        }

    }

    override fun setOnFav(id: String?) {
        var intent = Intent(this, StripePaymentGateway::class.java)

        intent.putExtra("promocode", "" + id)

        intent.putExtra("Total Amount", "" + total_value)

        intent.putExtra("Address_Reference", "" + address_Reference)

        intent.putExtra("item", "" + itemValue)

        intent.putExtra("Card Name", "" + cardName)

        intent.putExtra("Card No", "" + cardNumber)

        intent.putExtra("ExpiryDate", "" + expriyDate)
        startActivity(intent)

        finish()
    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if(responseObj != null) {
            if (requestType == Constant.MEMBER_fetchRestaurantPromocodeList_URL_RQ) {

                if ((responseObj as BaseRS).status.equals("5129")) {

                    if (adapterDetails.size != 0) {
                        adapterDetails.clear()
                    }
                    for (i in 0 until (responseObj as BaseRS).promoList!!.size) {
                        adapterModel = AdapterModel(
                            R.drawable.food,
                            "" + (responseObj as BaseRS).promoList!!.get(i).promocode,
                            "" + (responseObj as BaseRS).promoList!!.get(i).promoValue,
                            "" + (responseObj as BaseRS).promoList!!.get(i).description,
                            "" + (responseObj as BaseRS).promoList!!.get(i).promoType,
                            " 976",
                            " 425",
                            "Tatabad,Gandhipuram",
                            "",
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
                            adapterDetailChild
                        )

                        Log.v("llll" , "===")

                        adapterDetails.add(adapterModel)
                    }
                    couponListAdapter!!.notifyDataSetChanged()
                }


                } else if (requestType == Constant.MEMBER_fetchGroceryPromoCode_URL_RQ) {

                if ((responseObj as BaseRS).status.equals("5850")) {

                    if (adapterDetails.size != 0) {
                        adapterDetails.clear()
                    }
                    for (i in 0 until (responseObj as BaseRS).promoList!!.size) {
                        adapterModel = AdapterModel(
                            R.drawable.food,
                            "" + (responseObj as BaseRS).promoList!!.get(i).promocode,
                            "" + (responseObj as BaseRS).promoList!!.get(i).promoValue,
                            "" + (responseObj as BaseRS).promoList!!.get(i).description,
                            "" + (responseObj as BaseRS).promoList!!.get(i).promoType,
                            " 976",
                            " 425",
                            "Tatabad,Gandhipuram",
                            "",
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
                            adapterDetailChild
                        )

                        Log.v("llll" , "===")

                        adapterDetails.add(adapterModel)
                    }
                    couponListAdapter!!.notifyDataSetChanged()
                }


            }
        }
    }
}