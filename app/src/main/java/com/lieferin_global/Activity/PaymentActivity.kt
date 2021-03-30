package com.lieferin_global.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.PaymentListAdapter
import com.lieferin_global.Fragment.StripePaymentGateway
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.AddRestaruantNote
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.Constant.BookingType
import com.lieferin_global.Utility.Constant.DeliveryType
import com.lieferin_global.Utility.Constant.PAYPAL_CLIENT_ID
import com.lieferin_global.Utility.Constant.PayMentType
import com.lieferin_global.Utility.Constant.quality
import com.lieferin_global.Utility.Constant.totalPrice
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.paypal.android.sdk.payments.*
import com.paypal.android.sdk.payments.PaymentActivity
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.math.BigDecimal
import java.util.*

class PaymentActivity : Activity(), View.OnClickListener, PaymentListAdapter.CallbackDataAdapter,
    ResponseListener {

    var paymentType: RecyclerView? = null

    var tableReservation_back : ImageView? = null

    var orderDescription_Type : TextView? = null

    var adapterProduct1: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    internal lateinit var productModel: Product

    var addAddressAdapter: PaymentListAdapter? = null

    var totalTVOne: TextView? = null

    var payNowTV: TextView? = null

    var total_value: String = ""

    var address_Reference = ""

    var itemValue = ""

    var dbHelper: DBHelper? = null

    var select_payment: String  = "0"

    var paymentTypeTV: String  = ""

    private val PAYPAL_REQUEST_CODE = 7777

    private val config: PayPalConfiguration = PayPalConfiguration()
        .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
        .clientId(PAYPAL_CLIENT_ID).acceptCreditCards(false);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_payment)

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

        dbHelper = DBHelper(this)

        totalTVOne = findViewById(R.id.totalTVOne) as TextView

        totalTVOne!!.typeface = fontStyle(this, "SemiBold")

        tableReservation_back = findViewById(R.id.tableReservation_back) as ImageView

        tableReservation_back!!.setOnClickListener(this)

        orderDescription_Type = findViewById(R.id.orderDescription_Type) as TextView

        orderDescription_Type!!.typeface = fontStyle(this, "Light")

        payNowTV = findViewById(R.id.payNowTV) as TextView

        payNowTV!!.typeface = fontStyle(this, "SemiBold")

        payNowTV!!.setOnClickListener(this)

        paymentType = findViewById(R.id.paymentType) as RecyclerView

        addAddressAdapter = PaymentListAdapter(this, adapterProduct1)

        totalTVOne!!.setText(Html.fromHtml("<font color='#484848'>Total: </font><font color='#7DC77D'> € 358.00</font>"))

        paymentType!!.setHasFixedSize(true)

        paymentType!!.setLayoutManager(
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        )

        addAddressAdapter!!.setCallback(this)

        paymentType!!.isNestedScrollingEnabled = false
        paymentType!!.setAdapter(addAddressAdapter!!)

        showDataProduct1()

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
        }

        orderDescription_Type!!.text = quality+" "+getString(R.string.Orders_item_pay)+" "+ totalPrice

        totalTVOne!!.text = getString(R.string.totalTV)+" " + totalPrice

        if(Constant.AppType!!.equals("0")) {

            payNowTV!!.setBackgroundResource(R.drawable.home_radius_button_orange)
        }else{

            payNowTV!!.setBackgroundResource(R.drawable.home_radius_button_green)
        }
        val intent1 = Intent(this, PayPalService::class.java)
        intent1.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        startService(intent1)
    }

    fun showDataProduct1() {

        if (adapterProduct1.size != 0) {
            adapterProduct1.clear()
        }
        productModel = Product(
            R.drawable.pay_wallet,
            "Payment Via Wallet",
            "1234 5678 9012",
            "1",
            "",
            "4.0",
            "40 Mins",
            "200 for two",
            "","","","","","","","","","",
            adapterProductList
        )
       // adapterProduct1.add(productModel)

        productModel = Product(
            R.drawable.pay_paypall,
            "Payment Via Paypal",
            "Johndeo@gmail.com",
            "1",
            "",
            "4.0",
            "40 Mins",
            "200 for two",
            "","","","","","","","","","",
            adapterProductList
        )
        adapterProduct1.add(productModel)

        productModel = Product(
            R.drawable.pay_card,
            "Payment Via Stripe",
            "**** **** **** 1234",
            "1",
            "",
            "4.0",
            "40 Mins",
            "200 for two",
            "","","","","","","","","","",
            adapterProductList
        )
        adapterProduct1.add(productModel)

        productModel = Product(
            R.drawable.pay_cod,
            "Cash On Delivery",
            "",
            "1",
            "",
            "4.0",
            "40 Mins",
            "200 for two",
            "","","","","","","","","","",
            adapterProductList
        )
       // adapterProduct1.add(productModel)

        addAddressAdapter!!.notifyDataSetChanged()
    }

    override fun onClick(view: View?) {

        when (view!!.id) {
            R.id.payNowTV -> {

                //startActivity(Intent(this, OrderCompleteActivity::class.java))

                if(select_payment.equals("1")) {

                    PayMentType = "1"
                    if(BookingType.equals("0")) {
                        if (AppType.equals("0")) {
                            Log.v("Google", "" + createWebServiceFood())
                            RequestManager.setBookingFoodOrder(this, createWebServiceFood(), this)
                        } else {
                            Log.v("Google", "" + createWebService())
                            RequestManager.setBookingGroceryOrder(this, createWebService(), this)
                        }
                    }else{
                        Log.v("Google", "" + createWebServiceTable())
                        RequestManager.setTableReservtionMenuPicked(this, createWebServiceTable(), this)
                    }
                }else if(select_payment.equals("2")){

                    PayMentType = "2"

                    var intent = Intent(this, CardDetailsActivity::class.java)

                    intent.putExtra("Total Amount", "" + total_value)

                    intent.putExtra("Address_Reference", "" + address_Reference)

                    intent.putExtra("item", "" + itemValue)

                    startActivity(intent)
                }else if(select_payment.equals("3")){

                    PayMentType = "3"

                    //processPayment()
                    var intent = Intent(this, StripePaymentGateway::class.java)

                    intent.putExtra("Total Amount", "" + total_value)

                    intent.putExtra("Address_Reference", "" + address_Reference)

                    intent.putExtra("item", "" + itemValue)

                    startActivity(intent)
                }
                else  if(select_payment.equals("0")){
                    showToast(this,"Please select payment type")
                }
            }

            R.id.tableReservation_back ->{

                finish()
            }
        }
    }

    private fun processPayment() {

        val payPalPayment = PayPalPayment(
            BigDecimal(totalPrice!!.replace("€ ","")), "EUR",
            "Purchase Goods", PayPalPayment.PAYMENT_INTENT_SALE)
        val intent = Intent(this, PaymentActivity::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment)
        startActivityForResult(intent, PAYPAL_REQUEST_CODE)
    }

    override fun setOnAddress(id: String?) {
        addAddressAdapter!!.notifyDataSetChanged()

        if(id.equals("Cash On Delivery")) {
            select_payment = "1"
            paymentTypeTV = "Cash On Delivery"
        }else if(id.equals("Payment Via Stripe")){
            select_payment = "2"
        }else{
            select_payment = "3"
            paymentTypeTV = "PayPal"
        }

    }

    fun createWebServiceFood(): JSONObject {
        val obj = JSONObject()
        val objArray = JSONArray()
        val objArray1 = JSONArray()
        val objArray2 = JSONArray()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)
        obj.put("restaurantReferenceCode", ""+dbHelper!!.getRestaurant().restaurantReferenceCode)
        obj.put("deliveryType", ""+ DeliveryType)
        obj.put("userRequestNote", ""+ AddRestaruantNote)
        //obj.put("customerAddressReferenceCode", "cdf7a4e0fc2910511d8437b213d74251")
        obj.put("customerAddressReferenceCode", ""+address_Reference)
        obj.put("bookingAmount", ""+totalPrice.replace("€ ",""))
        obj.put("totalQuantity", ""+quality)
        obj.put("paymentMode", ""+paymentTypeTV)
        obj.put("paymentType", "1")
        obj.put("distances", "10.5")

        for (i in 0 until dbHelper!!.getMenu().size) {
            val menusList = JSONObject()

            menusList.put("menuReferenceCode", "" + dbHelper!!.getMenu().get(i).token)

            menusList.put("menuUserRequest", "" + dbHelper!!.getMenu().get(i).blockName)

            menusList.put("quantity", "" + dbHelper!!.getMenu().get(i).rating)

            for (j in 0 until dbHelper!!.getMenuGroup(dbHelper!!.getMenu().get(i).id!!).size) {

                //Log.v(";;;;"," === "+dbHelper!!.getMenuGroup(dbHelper!!.getMenu().get(i).id!!).get(j).token)
                val menusList1 = JSONObject()

                menusList1.put(
                    "toppinsGroupReferenceCode",
                    "" + dbHelper!!.getMenuGroup(dbHelper!!.getMenu().get(i).id!!).get(j).token
                )
                for (k in 0 until dbHelper!!.getMenuGroupToppind(
                    dbHelper!!.getMenuGroup(
                        dbHelper!!.getMenu().get(
                            i
                        ).id!!
                    ).get(j).id!!
                ).size) {
                    val menusList2 = JSONObject()

                    menusList2.put(
                        "toppinsReferenceCode",
                        "" + dbHelper!!.getMenuGroupToppind(
                            dbHelper!!.getMenuGroup(
                                dbHelper!!.getMenu().get(
                                    i
                                ).id!!
                            ).get(j).id!!
                        ).get(k).token
                    )

                    objArray2.put(menusList2)

                    menusList1.put("toppinsList",objArray2)
                }

                objArray1.put(menusList1)

                menusList.put("toppinsGroupList",objArray1)
            }

            objArray.put(menusList)


        }

        obj.put("menusList", objArray)

        /*for (i in 0 until (responseObj as BaseRS).fetchData!!.restaurantListing!!.size) {

        }*/

        Log.v("kkkk","=="+obj)

        return obj
    }

    fun createWebServiceTable(): JSONObject {
        val obj = JSONObject()
        val objArray = JSONArray()
        val objArray1 = JSONArray()
        val objArray2 = JSONArray()
        obj.put("tableMemberReferenceCode", "" + BookingType)
        obj.put("bookingAmount", ""+totalPrice.replace("€ ",""))
        obj.put("paymentMode", ""+paymentTypeTV)
        obj.put("paymentType", "1")

        for (i in 0 until dbHelper!!.getMenu().size) {
            val menusList = JSONObject()

            menusList.put("menuReferenceCode", "" + dbHelper!!.getMenu().get(i).token)

            menusList.put("quantity", "" + dbHelper!!.getMenu().get(i).rating)

            for (j in 0 until dbHelper!!.getMenuGroup(dbHelper!!.getMenu().get(i).id!!).size) {

                //Log.v(";;;;"," === "+dbHelper!!.getMenuGroup(dbHelper!!.getMenu().get(i).id!!).get(j).token)
                val menusList1 = JSONObject()

                menusList1.put(
                    "toppinsGroupReferenceCode",
                    "" + dbHelper!!.getMenuGroup(dbHelper!!.getMenu().get(i).id!!).get(j).token
                )
                for (k in 0 until dbHelper!!.getMenuGroupToppind(
                    dbHelper!!.getMenuGroup(
                        dbHelper!!.getMenu().get(
                            i
                        ).id!!
                    ).get(j).id!!
                ).size) {
                    val menusList2 = JSONObject()

                    menusList2.put(
                        "toppinsReferenceCode",
                        "" + dbHelper!!.getMenuGroupToppind(
                            dbHelper!!.getMenuGroup(
                                dbHelper!!.getMenu().get(
                                    i
                                ).id!!
                            ).get(j).id!!
                        ).get(k).token
                    )

                    objArray2.put(menusList2)

                    menusList1.put("toppinsList",objArray2)
                }

                objArray1.put(menusList1)

                menusList.put("toppinsGroupList",objArray1)
            }

            objArray.put(menusList)


        }

        obj.put("menusList", objArray)

        /*for (i in 0 until (responseObj as BaseRS).fetchData!!.restaurantListing!!.size) {

        }*/

        Log.v("kkkk","=="+obj)

        return obj
    }

    fun createWebService(): JSONObject {
        val obj = JSONObject()
        val objArray = JSONArray()
        val objArray1 = JSONArray()
        val objArray2 = JSONArray()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)
        obj.put("groceryReferenceCode", ""+dbHelper!!.getGrecoryTotalAll().get(0).restaurantReferenceCode)
        obj.put("deliveryType", ""+ DeliveryType)
        obj.put("userRequestNote", ""+AddRestaruantNote)
        //obj.put("customerAddressReferenceCode", "cdf7a4e0fc2910511d8437b213d74251")
        obj.put("customerAddressReferenceCode", ""+address_Reference)
        obj.put("bookingAmount", ""+totalPrice.replace("€ ",""))
        obj.put("totalQuantity", ""+quality)
        obj.put("paymentMode", ""+paymentTypeTV)
        obj.put("paymentType", "1")
        obj.put("distances", "10.5")

        for (i in 0 until dbHelper!!.getGrecoryTotalPrice(dbHelper!!.getGrecoryTotalAll().get(0).restaurantReferenceCode!!).size) {
            val menusList = JSONObject()

            menusList.put("productReferenceCode", "" + dbHelper!!.getGrecoryTotalPrice(dbHelper!!.getGrecoryTotalAll().get(0).restaurantReferenceCode!!).get(i).productReferenceCode)

            menusList.put("quantity", ""+ dbHelper!!.getGrecoryTotalPrice(dbHelper!!.getGrecoryTotalAll().get(0).restaurantReferenceCode!!).get(i).quantity)


            objArray.put(menusList)


        }

        obj.put("productList", objArray)

        /*for (i in 0 until (responseObj as BaseRS).fetchData!!.restaurantListing!!.size) {

        }*/

        Log.v("kkkk","=="+obj)

        return obj
    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {

        if(responseObj != null) {
            if (requestType == Constant.MEMBER_getBookingFoodOrder_URL_RQ) {

                showToast(this,(responseObj as BaseRS).message)
                if((responseObj as BaseRS).status.equals("5102"))
                {
                    //var intent = Intent(this, OrderCompleteActivity::class.java)
                    var intent = Intent(this, OrderCompleteActivity::class.java)

                    intent.putExtra("orderId",""+(responseObj as BaseRS).orderCode)

                    startActivity(intent)
                }


            }else if (requestType == Constant.MEMBER_bookingGroceryOrder_URL_RQ) {

                showToast(this, (responseObj as BaseRS).message)

                if((responseObj as BaseRS).status.equals("5806"))
                {


                    //var intent = Intent(this, OrderCompleteActivity::class.java)
                    var intent = Intent(this, OrderCompleteActivity::class.java)

                    intent.putExtra("orderId",""+(responseObj as BaseRS).orderCode)

                    startActivity(intent)
                }

            }else if (requestType == Constant.MEMBER_tableReservtionMenuPicked_RQ) {

                showToast(this, (responseObj as BaseRS).message)

                if((responseObj as BaseRS).status.equals("5216"))
                {
                    dbHelper!!.deleteMenu()

                    dbHelper!!.deleteCategory()

                    dbHelper!!.deleteToppinsGroup()

                    dbHelper!!.deleteToppins()

                    dbHelper!!.deleteRest()


                    //var intent = Intent(this, OrderCompleteActivity::class.java)
                    var intent = Intent(this, DashBoardActivity::class.java)


                    startActivity(intent)
                }

            }
        }
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val confirmation: PaymentConfirmation = data!!.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION)
                if (confirmation != null) {
                    try {
                        val paymentDetails = confirmation.toJSONObject().toString(4)
                        if(BookingType.equals("0")) {
                            if (AppType.equals("0")) {
                                Log.v("Google", "" + createWebServiceFood())
                                RequestManager.setBookingFoodOrder(this, createWebServiceFood(), this)
                            } else {
                                Log.v("Google", "" + createWebService())
                                RequestManager.setBookingGroceryOrder(this, createWebService(), this)
                            }
                        }else{
                            Log.v("Google", "" + createWebServiceTable())
                            RequestManager.setTableReservtionMenuPicked(this, createWebServiceTable(), this)
                        }
//                        startActivity(Intent(this, PaymentDetails::class.java)
//                                .putExtra("Payment Details", paymentDetails)
//                                .putExtra("Amount", amount))
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show()
    }

}
