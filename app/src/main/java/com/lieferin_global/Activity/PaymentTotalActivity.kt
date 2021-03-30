package com.lieferin_global.Activity

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.CardListViewAdapter
import com.lieferin_global.Adapter.PaymentListAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.AddRestaruantNote
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.Constant.BookingType
import com.lieferin_global.Utility.Constant.DeliveryType
import com.lieferin_global.Utility.Constant.PAYPAL_CLIENT_ID
import com.lieferin_global.Utility.Constant.quality
import com.lieferin_global.Utility.Constant.qualityTable
import com.lieferin_global.Utility.Constant.totalPrice
import com.lieferin_global.Utility.Constant.totalPriceTable
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.paypal.android.sdk.payments.*
import com.paypal.android.sdk.payments.PaymentActivity
import com.stripe.android.view.CardMultilineWidget
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.math.BigDecimal
import java.util.*

class PaymentTotalActivity : Activity(), View.OnClickListener, PaymentListAdapter.CallbackDataAdapter,
    ResponseListener,CardListViewAdapter.CallbackDataAdapter {

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

    internal lateinit var adapterModel: AdapterModel

    var addCard: TextView? = null

    var addNewCard: LinearLayout? = null

    var cardViewLayout: LinearLayout? = null

    var notificationRecyclerView: RecyclerView? = null

    var sortPageAdapter: CardListViewAdapter? = null

    var adapterSort: MutableList<AdapterModel> = ArrayList()

    var adapterCategories2: MutableList<Product> = ArrayList()

    var selectCard: String? = "0"

    var cardName = ""

    var cardNo = ""

    var expriyDate = ""

    var isDelete = true

    var isDeleteValue = true
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

        cardViewLayout = findViewById(R.id.cardViewLayout) as LinearLayout

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

        orderDescription_Type!!.text = qualityTable+" "+getString(R.string.Orders_item_pay)+" "+ totalPriceTable

        totalTVOne!!.text = getString(R.string.totalTV)+" " + totalPriceTable

        if(Constant.AppType!!.equals("0")) {

            payNowTV!!.setBackgroundResource(R.drawable.home_radius_button_orange)
        }else{

            payNowTV!!.setBackgroundResource(R.drawable.home_radius_button_green)
        }
        val intent1 = Intent(this, PayPalService::class.java)
        intent1.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        startService(intent1)

        addCard = findViewById(R.id.addCard) as TextView

        addCard!!.typeface = fontStyle(this, "")

        addNewCard = findViewById(R.id.addNewCard) as LinearLayout

        addNewCard!!.setOnClickListener(this)

        notificationRecyclerView = findViewById(R.id.myCardRecyclerView) as RecyclerView

        sortPageAdapter = CardListViewAdapter(this, adapterSort)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        notificationRecyclerView!!.layoutManager = mLayoutManager

        notificationRecyclerView!!.itemAnimator!!.addDuration = 5000

        sortPageAdapter!!.setCallback(this)

        notificationRecyclerView!!.isNestedScrollingEnabled = false

        notificationRecyclerView!!.adapter = sortPageAdapter

        webServiceCard()
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
            "Paypal",
            "",
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
            "Debit / Credit / ATM Card",
            "",
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
        //adapterProduct1.add(productModel)

        addAddressAdapter!!.notifyDataSetChanged()
    }

    override fun onClick(view: View?) {

        when (view!!.id) {
            R.id.payNowTV -> {

                //startActivity(Intent(this, OrderCompleteActivity::class.java))

                if(select_payment.equals("1")) {

                }else if(select_payment.equals("2") ||select_payment.equals("3")){

                    var intent = Intent(this, StripePaymentGatewayTableBooking::class.java)

                    intent.putExtra("Total Amount", "" + total_value)

                    intent.putExtra("Address_Reference", "" + address_Reference)

                    intent.putExtra("item", "" + itemValue)

                    intent.putExtra("Card Name", "" + cardName)

                    intent.putExtra("Card No", "" + cardNo)

                    intent.putExtra("ExpiryDate", "" + expriyDate)


                    startActivity(intent)

                    Constant.totalPrice = total_value
                }
                else  if(select_payment.equals("0")){
                    showToast(this,"Please select payment type")
                }
            }

            R.id.tableReservation_back ->{

                finish()
            }
            R.id.addNewCard ->{
                showDialogInfo(this)
            }
        }
    }

    fun showDialogInfo(context: Context?) {
        val dialog = Dialog(context!!)
        val decorView = dialog
            .window!!
            .getDecorView()
        val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
            decorView,
            PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
            PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
            PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f)
        )
        scaleDown.duration = 400
        scaleDown.start()
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.card_layout_details)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tableLA = dialog.findViewById<View>(R.id.titleTV) as TextView

        tableLA.typeface = fontStyle(context, "SemiBold")

        val cardMultilineWidget = dialog.findViewById<CardMultilineWidget>(R.id.cardInputWidget)

        val cardPay = dialog.findViewById(R.id.cardPay) as CardView

        val cardNoText = dialog.findViewById(R.id.cardNoText) as TextView

        val nameOnCardText = dialog.findViewById(R.id.nameOnCardText) as TextView

        val expiryDateText = dialog.findViewById(R.id.expiryDateText) as TextView

        val ccvText = dialog.findViewById(R.id.ccvText) as TextView

        val cardNo = dialog.findViewById(R.id.cardNo) as EditText

        val nameOnCard = dialog.findViewById(R.id.nameOnCard) as EditText

        val expiryDate = dialog.findViewById(R.id.expiryDate) as EditText

        val ccv = dialog.findViewById(R.id.ccv) as EditText

        val yesTV = dialog.findViewById(R.id.yesTV) as TextView

        val noTV = dialog.findViewById(R.id.noTV) as TextView

        cardNoText!!.typeface = fontStyle(this, "SemiBold")

        nameOnCardText!!.typeface = fontStyle(this, "SemiBold")

        expiryDateText!!.typeface = fontStyle(this, "SemiBold")

        ccvText!!.typeface = fontStyle(this, "SemiBold")

        cardNo!!.typeface = fontStyle(this, "")

        nameOnCard!!.typeface = fontStyle(this, "")

        expiryDate!!.typeface = fontStyle(this, "")

        ccv!!.typeface = fontStyle(this, "")

        yesTV!!.typeface = fontStyle(this,"SemiBold")

        noTV!!.typeface = fontStyle(this,"SemiBold")

        yesTV!!.setOnClickListener(View.OnClickListener {


            if(cardNo!!.text.toString().replace(" ", "").length != 16)
            {
                showToast(this, getString(R.string.cardValidation))
            }else if(expiryDate!!.text.length != 5)
            {
                showToast(this, getString(R.string.expiryDateValidation))
            }else {

                cardMultilineWidget!!.setCardNumber(cardNo!!.text.toString())
                //cardInputWidget.
                cardMultilineWidget!!.setExpiryDate(
                    expiryDate!!.text.toString().substring(0, 2).toInt(),
                    expiryDate!!.text.toString().substring(3).toInt()
                )

                cardMultilineWidget!!.setCvcCode("746")

                Log.v("Error", "======" + cardMultilineWidget!!.validateCardNumber())
                if (cardNo!!.text.toString().replace(" ", "").length != 16) {
                    showToast(this, getString(R.string.cardValidation))
                } else if (expiryDate!!.text.toString().length != 5) {
                    showToast(this, getString(R.string.expiryDateValidation))
                } else if (ccv!!.text.toString().length != 3) {
                    showToast(this, getString(R.string.cvvValidation))
                } else if (!cardMultilineWidget!!.validateCardNumber()) {
                    showToast(this, getString(R.string.invalidCard))
                } else if (!cardMultilineWidget!!.validateAllFields()) {
                    showToast(this, getString(R.string.invalidExpiryDate))
                } else {
                    val obj = JSONObject()
                    obj.put("token", "" + dbHelper!!.getUserDetails().token)

                    obj.put("cardNo", "" + cardNo!!.text.toString())

                    obj.put("nameOnCard", "" + nameOnCard!!.text.toString())

                    obj.put("expiryDate", "" + expiryDate!!.text.toString())

                    obj.put("cardType", "" + cardMultilineWidget!!.card!!.brand)

                    RequestManager.setInsertCardListActivity(this, obj, this);
                    dialog.cancel()
                    loadingScreen(this)

                    Log.v("kkkk" + obj, "33333")
                }
            }

        })
        noTV!!.setOnClickListener(View.OnClickListener {
            dialog.cancel()
        })
        expiryDate!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                cs: CharSequence,
                arg1: Int,
                arg2: Int,
                arg3: Int
            ) {
            }

            override fun beforeTextChanged(
                arg0: CharSequence,
                arg1: Int,
                before: Int,
                arg3: Int
            ) {
                if (before == 0) isDeleteValue = false else isDeleteValue = true
            }

            override fun afterTextChanged(s: Editable) {
                val source = s.toString()
                val length = source.length
                val stringBuilder = StringBuilder()
                stringBuilder.append(source)
                if (length > 0 && length % 3 == 0) {
                    if (isDeleteValue) stringBuilder.deleteCharAt(length - 1) else stringBuilder.insert(
                        length - 1,
                        "/"
                    )
                    expiryDate!!.setText(stringBuilder)
                    expiryDate!!.setSelection(expiryDate!!.getText().length)
                }
            }
        })

        cardNo!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                cs: CharSequence,
                arg1: Int,
                arg2: Int,
                arg3: Int
            ) {
            }

            override fun beforeTextChanged(
                arg0: CharSequence,
                arg1: Int,
                before: Int,
                arg3: Int
            ) {
                if (before == 0) isDelete = false else isDelete = true
            }

            override fun afterTextChanged(s: Editable) {
                val source = s.toString()
                val length = source.length
                val stringBuilder = StringBuilder()
                stringBuilder.append(source)
                if (length > 0 && length % 5 == 0) {
                    if (isDelete) stringBuilder.deleteCharAt(length - 1) else stringBuilder.insert(
                        length - 1,
                        " "
                    )
                    cardNo!!.setText(stringBuilder)
                    cardNo!!.setSelection(cardNo!!.getText().length)
                }
            }
        })

        dialog.show()
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
        cardViewLayout!!.visibility = View.GONE
        if(id.equals("Cash On Delivery")) {
            select_payment = "1"
            paymentTypeTV = "Cash On Delivery"
        }else if(id.equals("Debit / Credit / ATM Card")){
            select_payment = "2"
            Constant.PayMentType = "2"
            cardViewLayout!!.visibility = View.VISIBLE
        }else{
            select_payment = "3"
            Constant.PayMentType = "3"
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

            menusList.put("quantity", "1")

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

            menusList.put("quantity", "1")

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
    fun webServiceCard()
    {
        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        RequestManager.setCardListActivity(this, obj, this);

        Log.v("kkkk"+obj,"33333")
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
            }else if (requestType == Constant.MEMBER_CardList_URL_RQ) {
                showToast(this,(responseObj as BaseRS).message)
                if((responseObj as BaseRS).status.equals("5031")) {
                    if(adapterSort!!.size != 0)
                    {
                        adapterSort!!.clear()
                    }
                    for (i in 0 until (responseObj as BaseRS).cardInfo!!.size) {
                        adapterModel = AdapterModel(R.drawable.img_5, ""+(responseObj as BaseRS).cardInfo!!.get(i).cardType, ""+(responseObj as BaseRS).cardInfo!!.get(i).cardNo, ""+(responseObj as BaseRS).cardInfo!!.get(i).nameOnCard, ""+(responseObj as BaseRS).cardInfo!!.get(i).expiryDate, ""+(responseObj as BaseRS).cardInfo!!.get(i).id, "0", "", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterCategories2)
                        adapterSort.add(adapterModel)
                    }
                    sortPageAdapter!!.notifyDataSetChanged()
                }
            }else if (requestType == Constant.MEMBER_insertCardList_URL_RQ) {
                showToast(this, (responseObj as BaseRS).message)
                loadingScreenClose(this)
                webServiceCard()
            }else if (requestType == Constant.MEMBER_cardDelete_URL_RQ) {
                showToast(this, (responseObj as BaseRS).message)
                loadingScreenClose(this)
                webServiceCard()
                sortPageAdapter!!.notifyDataSetChanged()
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

    override fun setOnMaterial(userId: AdapterModel?) {

    }

    override fun setOnFavourite(userId: AdapterModel?, id: Int?) {
        selectCard = "1"
        for (i in 0 until adapterSort.size) {
            adapterSort.get(i).offer = "0"
        }
        adapterSort.get(id!!).offer = "1"

        sortPageAdapter!!.notifyDataSetChanged()

        cardName = userId!!.offerPrice!!

        cardNo = userId!!.price!!

        expriyDate = userId!!.offerPercentage!!
    }

    override fun setOnCardDelete(position: String?) {
        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        obj.put("cardId", ""+ position)

        RequestManager.setCardDetele(this, obj, this);

        loadingScreen(this)
    }

}
