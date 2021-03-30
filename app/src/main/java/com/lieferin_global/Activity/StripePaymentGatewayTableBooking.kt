package com.lieferin_global.Activity

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.cardview.widget.CardView
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.google.gson.GsonBuilder
import com.lieferin_global.Utility.Constant.totalPriceTable
import com.paypal.android.sdk.payments.*
import com.paypal.android.sdk.payments.PaymentActivity
import com.stripe.android.ApiResultCallback
import com.stripe.android.PaymentIntentResult
import com.stripe.android.Stripe
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.StripeIntent
import com.stripe.android.view.CardInputWidget
import com.stripe.android.view.CardMultilineWidget
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.jetbrains.anko.textColor
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

class StripePaymentGatewayTableBooking : AppCompatActivity(),View.OnClickListener,ResponseListener {
    private var backendUrl = ""
    private val httpClient = OkHttpClient()
    private lateinit var publishableKey: String
    private lateinit var paymentIntentClientSecret: String
    private lateinit var stripe: Stripe

    var totalLabel : TextView? = null

    var totalData : TextView? = null

    var cardPay: CardView? = null

    var cardNoText: TextView? = null

    var nameOnCardText: TextView? = null

    var expiryDateText: TextView? = null

    var nameLabel: TextView? = null

    var nameData: TextView? = null

    var gMailLabel: TextView? = null

    var mailData: TextView? = null

    var addressLabel: TextView? = null

    var addressData: TextView? = null

    var amountLabel: TextView? = null

    var amountData: TextView? = null

    var ccvText: TextView? = null

    var deliveryFee: TextView? = null

    var deliveryFeeData: TextView? = null

    var cardNo: EditText? = null

    var nameOnCard: EditText? = null

    var expiryDate: EditText? = null

    var deliverView : View? = null

    var ccv: EditText? = null

    var sendTxt: TextView? = null

    var isDelete = true

    var isDeleteValue = true

    var notificationTitle: TextView? = null

    var back: ImageView? = null

    var dbHelper : DBHelper? = null

    var payButton : TextView? = null

    var total_value: String = ""

    var address_Reference = ""

    var itemValue = ""

    var paymentTypeMsg =""

    var paymentTypeMid =""

    var cardName = ""

    var cardNumber = ""

    var expriyDate = ""

    var CouponCode = ""

    private val PAYPAL_REQUEST_CODE = 7777

    private val config: PayPalConfiguration = PayPalConfiguration()
        .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
        .clientId(Constant.PAYPAL_CLIENT_ID).acceptCreditCards(false);

    var couponLayout : LinearLayout? = null

    var successLayout : LinearLayout? = null

    var applyCouponTextTV : TextView? = null

    var applyCouponDescriptionTV : TextView? = null

    var applyCouponTV : TextView? = null

    var cancelImg : ImageView? = null

    var couponPrice : Double? = 0.0

    var couponType : String? =""

    var totalPrice : String? =""

    var restaurantPromoId : String? = ""

    var promoValue : String? = ""

    var couponLabel : TextView? = null

    var couponData : TextView? = null

    var couponView : LinearLayout? = null

    var billRequest : TextView? = null

    var billCheck : CheckBox? = null

    var billCheckGrocery : CheckBox? = null

    var nifNumberET : EditText? = null

    var nifNumberTV : TextView? = null

    var nifNumberCheck : String? =""

    var nifNumberLabel: TextView? = null

    var cardInputWidget : CardMultilineWidget? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stripe_payment_gateway)

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

        payButton= findViewById(R.id.payButton) as TextView

        payButton!!.typeface = fontStyle(this, "SemiBold")

        nifNumberLabel= findViewById(R.id.nifNumberLabel) as TextView

        nifNumberLabel!!.visibility = View.GONE

        nifNumberLabel!!.typeface = fontStyle(this, "SemiBold")

        Log.v("AddPrice"," === "+totalPriceTable)

        backendUrl = Constant.Url+"stripe_pay/create_payment_indent.php?amount="+ totalPriceTable.replace("€ ","")+"&receipt_email="+dbHelper!!.getUserDetails().email+"&mode=live"

        Log.v("kkkkkkk"," == "+backendUrl)


        var header = findViewById(R.id.header) as LinearLayout

        header!!.setOnClickListener(this)

        val intent = intent

        if (intent != null) {
            if (intent.getStringExtra("Total Amount") != null) {
                total_value = intent.getStringExtra("Total Amount")
            }

            if (intent.getStringExtra("Address_Reference") != null) {
                address_Reference = intent.getStringExtra("Address_Reference")

                Log.v("pincode55521","===="+address_Reference)
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

            if (intent.getStringExtra("promocode") != null) {
                CouponCode = intent.getStringExtra("promocode")
            }
        }

        couponView = findViewById(R.id.couponView) as LinearLayout

        couponView!!.visibility = View.GONE

        billRequest = findViewById(R.id.billRequest) as TextView

        billRequest!!.typeface = fontStyle(this, "")

        billCheck = findViewById(R.id.billCheck) as CheckBox

        billCheck!!.setOnClickListener(this)

        billCheckGrocery = findViewById(R.id.billCheckGrocery) as CheckBox

        billCheckGrocery!!.setOnClickListener(this)

        nifNumberET = findViewById(R.id.nifNumberET) as EditText

        nifNumberET!!.typeface = fontStyle(this, "")

        nifNumberTV = findViewById(R.id.nifNumberTV) as TextView

        nifNumberTV!!.typeface = fontStyle(this, "")

        notificationTitle = findViewById(R.id.notificationTitle) as TextView

        notificationTitle!!.typeface = fontStyle(this, "SemiBold")

        couponLabel = findViewById(R.id.couponLabel) as TextView

        couponLabel!!.typeface = fontStyle(this, "SemiBold")

        couponData = findViewById(R.id.couponData) as TextView

        couponData!!.typeface = fontStyle(this, "Light")

        nameLabel = findViewById(R.id.nameLabel) as TextView

        nameLabel!!.typeface = fontStyle(this, "SemiBold")

        nameLabel!!.setOnClickListener(this)

        nameData = findViewById(R.id.nameData) as TextView

        nameData!!.typeface = fontStyle(this, "")

        gMailLabel = findViewById(R.id.gMailLabel) as TextView

        gMailLabel!!.typeface = fontStyle(this, "SemiBold")

        deliveryFee = findViewById(R.id.DeliveryLabel) as TextView

        deliveryFee!!.typeface = fontStyle(this, "SemiBold")

        deliveryFeeData = findViewById(R.id.DeliveryData) as TextView

        deliveryFeeData!!.typeface = fontStyle(this, "")

        deliveryFeeData!!.text = Constant.deliveryFare

        totalLabel = findViewById(R.id.totalLabel) as TextView

        totalLabel!!.typeface = fontStyle(this, "SemiBold")

        totalData = findViewById(R.id.totalData) as TextView

        totalData!!.typeface = fontStyle(this, "")

        mailData = findViewById(R.id.mailData) as TextView

        mailData!!.typeface = fontStyle(this, "")

        addressLabel = findViewById(R.id.addressLabel) as TextView

        addressLabel!!.typeface = fontStyle(this, "SemiBold")

        addressData = findViewById(R.id.addressData) as TextView

        addressData!!.typeface = fontStyle(this, "")

        amountLabel = findViewById(R.id.amountLabel) as TextView

        amountLabel!!.typeface = fontStyle(this, "SemiBold")

        amountData = findViewById(R.id.amountData) as TextView

        amountData!!.typeface = fontStyle(this, "")

        addressData!!.text = Constant.addressComplete +""

        mailData!!.text = dbHelper!!.getUserDetails().email

        nameData!!.text = dbHelper!!.getUserDetails().name

        amountData!!.text = ""+Constant.totalPrice

        totalData!!.text = "€ "+ DecimalFormat("##.##").format(totalPriceTable.replace("€ ","").toDouble())

        back = findViewById(R.id.back) as ImageView

        back!!.setColorFilter(
            colorIcon(
                this,
                R.color.colorBlack,
                R.drawable.abc_ic_ab_back_material,
                back!!
            ), PorterDuff.Mode.SRC_ATOP
        )

        back!!.setOnClickListener(this)

        cardPay = findViewById(R.id.cardPay) as CardView

        cardNoText = findViewById(R.id.cardNoText) as TextView

        nameOnCardText = findViewById(R.id.nameOnCardText) as TextView

        expiryDateText = findViewById(R.id.expiryDateText) as TextView

        ccvText = findViewById(R.id.ccvText) as TextView

        cardNo = findViewById(R.id.cardNo) as EditText

        cardNo!!.setText(cardNumber)

        nameOnCard = findViewById(R.id.nameOnCard) as EditText

        nameOnCard!!.setText(cardName)

        expiryDate = findViewById(R.id.expiryDate) as EditText

        expiryDate!!.setText(expriyDate)

        ccv = findViewById(R.id.ccv) as EditText

        cardNoText!!.typeface = fontStyle(this, "SemiBold")

        nameOnCardText!!.typeface = fontStyle(this, "SemiBold")

        expiryDateText!!.typeface = fontStyle(this, "SemiBold")

        ccvText!!.typeface = fontStyle(this, "SemiBold")

        cardNo!!.typeface = fontStyle(this, "")

        nameOnCard!!.typeface = fontStyle(this, "")

        expiryDate!!.typeface = fontStyle(this, "")

        ccv!!.typeface = fontStyle(this, "")

        deliverView = findViewById(R.id.deliverView)

        if(Constant.PayMentType!!.equals("2")) {
            cardPay!!.visibility = View.VISIBLE
        }else if(Constant.PayMentType!!.equals("3")){
            cardPay!!.visibility = View.GONE
        }

        if(Constant.AppType!!.equals("0")) {
            payButton!!.setBackgroundResource(R.drawable.home_radius_button_orange)
            billCheckGrocery!!.visibility = View.GONE
            billCheck!!.visibility = View.VISIBLE
        }else{
            payButton!!.setBackgroundResource(R.drawable.home_radius_button_green)
            billCheckGrocery!!.visibility = View.VISIBLE
            billCheck!!.visibility = View.GONE
        }

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

        if(Constant.DeliveryType.equals("3"))
        {
            deliveryFee!!.visibility = View.GONE
            deliveryFeeData!!.visibility = View.GONE
            deliverView!!.visibility = View.GONE
        }else{
            deliveryFee!!.visibility = View.VISIBLE
            deliveryFeeData!!.visibility = View.VISIBLE
            deliverView!!.visibility = View.VISIBLE
        }

        if(!CouponCode!!.equals(""))
        {
            if(Constant.AppType.equals("0")) {
                webServiceCoupon()
            }else{
                webServiceGroceryCoupon()
            }
        }else{

        }
        if(Constant.nifNumber.equals("")) {
            nifNumberTV!!.visibility = View.GONE

            nifNumberET!!.visibility = View.GONE
        }else{
            nifNumberTV!!.text = Constant.nifNumber
            nifNumberTV!!.visibility = View.GONE

            nifNumberET!!.visibility = View.GONE
        }
        applyCouponTV = findViewById(R.id.applyCouponTV) as TextView

        applyCouponTV!!.typeface = fontStyle(this,"SemiBold")

        applyCouponTextTV = findViewById(R.id.applyCouponTextTV) as TextView

        applyCouponTextTV!!.typeface = fontStyle(this,"SemiBold")

        applyCouponDescriptionTV = findViewById(R.id.applyCouponDescriptionTV) as TextView

        applyCouponDescriptionTV!!.typeface = fontStyle(this,"Light")

        cancelImg = findViewById(R.id.cancelImg) as ImageView

        cancelImg!!.setOnClickListener(this)

        couponLayout = findViewById(R.id.couponLayout) as LinearLayout

        couponLayout!!.setOnClickListener(this)

        successLayout = findViewById(R.id.successLayout) as LinearLayout

        cardInputWidget = findViewById<CardMultilineWidget>(R.id.cardInputWidget)

        payButton!!.setOnClickListener {
            if(Constant.PayMentType!!.equals("2")) {

                if (totalPriceTable.replace("€ ", "").toDouble() > 0.4) {

                    if (cardNo!!.text.toString().replace(" ", "").length != 16) {
                        showToast(this, "Please enter valid 16 card number")
                    } else if (expiryDate!!.text.toString().length != 5) {
                        showToast(this, "Please enter valid expiry date ")
                    } else if (ccv!!.text.toString().length != 3) {
                        showToast(this, "Please enter valid ccv number")
                    } else {
                        cardInputWidget!!.setCardNumber(cardNo!!.text.toString().replace(" ", ""))
                        cardInputWidget!!.setExpiryDate(
                            expiryDate!!.text.toString().substring(0, 2).toInt(),
                            expiryDate!!.text.toString().substring(3).toInt()
                        )

                        cardInputWidget!!.setCvcCode(ccv!!.text.toString())
                        val params = cardInputWidget!!.paymentMethodCreateParams

                        if (params != null) {
                            backendUrl = Constant.Url+"stripe_pay/create_payment_indent.php?amount="+ totalPriceTable.replace("€ ","")+"&receipt_email="+dbHelper!!.getUserDetails().email+"&mode=live"

                            if(totalPriceTable.replace("€ ","").toDouble() > 0.4) {

                                startCheckout()

                                Log.v("kkkkkkk"," == ")
                            }else{

                                showToast(this,"Miminum order amount 0.5")

                                Log.v("kkhhkkkkk"," == ")
                            }
                            /*val confirmParams = ConfirmPaymentIntentParams
                                .createWithPaymentMethodCreateParams(
                                    params,
                                    paymentIntentClientSecret
                                )
                            stripe.confirmPayment(this, confirmParams)*/

                            //loadingScreen(this)
                        }
                    }
                } else {

                }
            }else{
                processPayment()
            }
        }

        var amountView = findViewById(R.id.amountView) as View

        amountView!!.visibility = View.GONE

        var amountLabel = findViewById(R.id.amountLabel) as TextView

        amountLabel!!.visibility = View.GONE

        var amountData = findViewById(R.id.amountData) as TextView

        amountData!!.visibility = View.GONE

        var addressData = findViewById(R.id.amountLabel) as TextView

        addressData!!.visibility = View.GONE

        var addressLabel = findViewById(R.id.addressLabel) as TextView

        addressLabel!!.visibility = View.GONE


        var addressView = findViewById(R.id.addressView) as View

        addressView!!.visibility = View.GONE

        var deliveryView = findViewById(R.id.deliveryView) as View

        deliveryView!!.visibility = View.GONE

        var DeliveryLabel = findViewById(R.id.DeliveryLabel) as TextView

        DeliveryLabel!!.visibility = View.GONE

        var DeliveryData = findViewById(R.id.DeliveryData) as TextView

        DeliveryData!!.visibility = View.GONE


    }

    private fun processPayment() {

        val payPalPayment = PayPalPayment(
            BigDecimal(totalData!!.text!!.toString().replace("€ ","")), "EUR",
            "Purchase Goods", PayPalPayment.PAYMENT_INTENT_SALE)
        val intent = Intent(this, PaymentActivity::class.java)
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config)
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment)
        startActivityForResult(intent, PAYPAL_REQUEST_CODE)

        loadingScreen(this)
    }

    fun webServiceCoupon()
    {
        val obj = JSONObject()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)
        obj.put("promocode", "" + CouponCode)
        obj.put("postcode", ""+ Constant.customerPincode)


        Log.v("mmmmmmm","==="+obj);
        RequestManager.setCheckRestaurantPromoOffer(this,obj,this)
    }

    fun webServiceGroceryCoupon()
    {
        val obj = JSONObject()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)
        obj.put("promocode", "" + CouponCode)
        obj.put("postcode", ""+ Constant.customerPincode)


        Log.v("mmmmmmm","==="+obj);
        RequestManager.setCheckGroceryPromoOffer(this,obj,this)
    }

    private fun displayAlert(activity: Activity?, title: String, message: String, restartDemo: Boolean = false) {
        if (activity == null) {
            return
        }
        runOnUiThread {
            val builder = AlertDialog.Builder(activity!!)
            builder.setTitle(title)
            builder.setMessage(message)
            if (restartDemo) {
                builder.setPositiveButton("Restart demo") { _, _ ->
                    val cardInputWidget =
                        findViewById<CardInputWidget>(R.id.cardInputWidget)
                    cardInputWidget.clear()
                    startCheckout()
                }
            }
            else {
                builder.setPositiveButton("Ok", null)
            }
            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun startCheckout() {
        val weakActivity = WeakReference<Activity>(this)
        // Create a PaymentIntent by calling the sample server's /create-payment-intent endpoint.
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val json = """
            {
                "currency":"usd",
                "items": [
                    {"id":"photo_subscription"}
                ]
            }
            """
        val body = json.toRequestBody(mediaType)
        val request = Request.Builder()
            .url(backendUrl)
            .post(body)
            .build()
        httpClient.newCall(request)
            .enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    displayAlert(weakActivity.get(), "Failed to load page", "Error: $e")
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        displayAlert(weakActivity.get(), "Failed to load page", "Error: $response")
                    } else {
                        val responseData = response.body?.string()
                        var json = JSONObject(responseData)

                        paymentIntentClientSecret = json.getString("client_secret")

                        // The response from the server includes the Stripe publishable key and
                        // PaymentIntent details.
                        // For added security, our sample app gets the publishable key from the server
                      /*  publishableKey = "pk_test_cvE7UClVGrrAYLqS0mtFS0HU00NDSTQWuu"
                        paymentIntentClientSecret = "ca_HWXHQPkfNsYY9HvUK5McQFdEmjawlA3O"

                        // Configure the SDK with your Stripe publishable key so that it can make requests to the Stripe API
                        stripe = Stripe(applicationContext, publishableKey)*/
                    }
                }
            })

        // Hook up the pay button to the card widget and stripe instance

        stripe = Stripe(applicationContext, "pk_test_cvE7UClVGrrAYLqS0mtFS0HU00NDSTQWuu")


        payButton!!.setOnClickListener {
            val cardInputWidget =
                findViewById<CardMultilineWidget>(R.id.cardInputWidget)

            if(totalPriceTable.replace("€ ","").toDouble() > 0.4) {

                if (cardNo!!.text.toString().replace(" ", "").length != 16) {
                    showToast(this, "Please enter valid 16 card number")
                } else if (expiryDate!!.text.toString().length != 5) {
                    showToast(this, "Please enter valid expiry date ")
                } else if (ccv!!.text.toString().length != 3) {
                    showToast(this, "Please enter valid ccv number")
                } else {

                    cardInputWidget.setCardNumber(cardNo!!.text.toString().replace(" ", ""))

                    cardInputWidget.setExpiryDate(
                        expiryDate!!.text.toString().substring(0, 2).toInt(),
                        expiryDate!!.text.toString().substring(3).toInt()
                    )

                    cardInputWidget.setCvcCode(ccv!!.text.toString())
                    val params = cardInputWidget.paymentMethodCreateParams

                    if (params != null) {
                        val confirmParams = ConfirmPaymentIntentParams
                            .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret)
                        stripe.confirmPayment(this, confirmParams)

                        loadingScreen(this)
                    }
                }
            }else{

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PAYPAL_REQUEST_CODE) {
            loadingScreenClose(this)
            if (resultCode == Activity.RESULT_OK) {
                val confirmation: PaymentConfirmation =
                    data!!.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION)
                if (confirmation != null) {
                    try {
                        val paymentDetails = confirmation.toJSONObject().toString(4)

                        webService("Paypal")
//                        startActivity(Intent(this, PaymentDetails::class.java)
//                                .putExtra("Payment Details", paymentDetails)
//                                .putExtra("Amount", amount))
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
            }
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show()
        } else {
            val weakActivity = WeakReference<Activity>(this)

            // Handle the result of stripe.confirmPayment
            stripe.onPaymentResult(
                requestCode,
                data,
                object : ApiResultCallback<PaymentIntentResult> {
                    override fun onSuccess(result: PaymentIntentResult) {
                        val paymentIntent = result.intent
                        val status = paymentIntent.status
                        if (status == StripeIntent.Status.Succeeded) {
                            val gson = GsonBuilder().setPrettyPrinting().create()
                            //displayAlert(weakActivity.get(), "Payment succeeded", gson.toJson(paymentIntent), restartDemo = true)


                            Log.v("kkkkkkk", " == " + gson.toJson(paymentIntent))

                            paymentTypeMsg = "" + gson.toJson(paymentIntent)

                            paymentTypeMid = "" + gson.toJson(paymentIntent.id)
                            loadingScreenClose(this@StripePaymentGatewayTableBooking)
                            webService("Srtipe")


                        } else if (status == StripeIntent.Status.RequiresPaymentMethod) {
                            displayAlert(
                                weakActivity.get(),
                                "Payment failed",
                                paymentIntent.lastPaymentError?.message ?: ""
                            )

                            showToast(this@StripePaymentGatewayTableBooking, "Payment failed")

                            loadingScreenClose(this@StripePaymentGatewayTableBooking)
                        }
                    }

                    override fun onError(e: Exception) {
                        displayAlert(weakActivity.get(), "Payment failed", e.toString())
                    }
                })
        }
    }

    override fun onClick(view: View?) {
        when(view!!.id)
        {
            R.id.back->{

                finish()
            }

            R.id.couponLayout->{
                var intent = Intent(this, AppliyTableCoupon::class.java)

                intent.putExtra("Total Amount", "" + total_value)

                intent.putExtra("Address_Reference", "" + address_Reference)

                intent.putExtra("item", "" + itemValue)

                intent.putExtra("Card Name", "" + cardName)

                intent.putExtra("Card No", "" + cardNumber)

                intent.putExtra("ExpiryDate", "" + expriyDate)

                startActivity(intent)
            }

            R.id.cancelImg->{

                couponLayout!!.visibility = View.VISIBLE

                successLayout!!.visibility = View.GONE
                totalPrice = Constant.totalPrice

                amountData!!.text = ""+totalPrice

                totalData!!.text = "€ "+ DecimalFormat("##.##").format(totalPriceTable.replace("€ ","").toDouble())


            }
        }
    }

    fun createWebServiceFood(paymentType:String): JSONObject {
        val obj = JSONObject()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)
        obj.put("tableMemberReferenceCode", ""+ Constant.isTableBookingReferenceCode)
        obj.put("totalAmount", ""+ Constant.totalPriceTable)
        obj.put("paymentMode", ""+paymentType)
        //obj.put("customerAddressReferenceCode", "cdf7a4e0fc2910511d8437b213d74251")
        obj.put("paymentType", "1")
        obj.put("paymentToken", ""+paymentTypeMid)
        obj.put("paymentObject", ""+paymentTypeMid)

        Log.v("kkkk","=="+obj)

        return obj
    }

    fun createWebServiceTable(): JSONObject {
        val obj = JSONObject()
        val objArray = JSONArray()
        val objArray1 = JSONArray()
        val objArray2 = JSONArray()
        obj.put("tableMemberReferenceCode", "" + Constant.BookingType)
        obj.put("bookingAmount", ""+ Constant.totalPrice.replace("€ ",""))
        obj.put("paymentMode", "Stripe")
        obj.put("paymentType", "1")
        obj.put("paymentToken", ""+paymentTypeMid)
        obj.put("paymentObject", ""+paymentTypeMsg)

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

    fun createWebService(): JSONObject {
        val obj = JSONObject()
        val objArray = JSONArray()
        val objArray1 = JSONArray()
        val objArray2 = JSONArray()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)
        obj.put("groceryReferenceCode", ""+dbHelper!!.getGrecoryTotalAll().get(0).restaurantReferenceCode)
        obj.put("deliveryType", ""+ Constant.DeliveryType)
        obj.put("userRequestNote", ""+ Constant.AddRestaruantNote)
        //obj.put("customerAddressReferenceCode", "cdf7a4e0fc2910511d8437b213d74251")
        obj.put("customerAddressReferenceCode", ""+address_Reference)
        obj.put("bookingAmount", ""+ Constant.totalPrice.replace("€ ",""))
        obj.put("totalQuantity", ""+ Constant.quality)
        obj.put("paymentMode", "Stripe")
        obj.put("paymentType", "1")
        obj.put("distances", "10.5")
        obj.put("paymentToken", ""+paymentTypeMid)
        obj.put("paymentObject", ""+paymentTypeMsg)

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
            if (requestType == Constant.MEMBER_customerMakePaymentForMember_URL_RQ) {

                showToast(this,(responseObj as BaseRS).message)
                if((responseObj as BaseRS).status.equals("5232"))
                {


                    //var intent = Intent(this, OrderCompleteActivity::class.java)
                    var intent = Intent(this, DashBoardActivity::class.java)


                    startActivity(intent)
                }


            }else if (requestType == Constant.MEMBER_checkRestaurantPromoOffer_URL_RQ) {
                showToast(this, (responseObj as BaseRS).message)

                if((responseObj as BaseRS).status.equals("5121"))
                {



                    showDialogReservation(this,(responseObj as BaseRS).promoData!!.promoType,(responseObj as BaseRS).promoData!!.promoValue)

                    couponLayout!!.visibility = View.GONE

                    successLayout!!.visibility = View.VISIBLE

                    applyCouponTextTV!!.text = CouponCode

                    restaurantPromoId  = ""+(responseObj as BaseRS).promoData!!.restaurantPromoId

                    couponPrice = (responseObj as BaseRS).promoData!!.promoValue!!.toDouble()

                    couponType = (responseObj as BaseRS).promoData!!.promoType!!

                    totalPrice = Constant.totalPrice

                    totalPrice = ""+couponCal(totalPrice!!.replace("€ ","").toDouble(),couponType!!,couponPrice!!)

                    amountData!!.text = ""+totalPrice

                    totalData!!.text = "€ "+ DecimalFormat("##.##").format(totalPriceTable.replace("€ ","").toDouble())

                    if(couponType!!.equals("1"))
                    {
                        promoValue  = ""+(responseObj as BaseRS).promoData!!.promoValue
                    }else{
                        promoValue  = ""+promCal(Constant.totalPrice!!.replace("€ ","").toDouble(),couponType!!,(responseObj as BaseRS).promoData!!.promoValue!!.toDouble())
                    }

                    couponView!!.visibility = View.VISIBLE

                    if((responseObj as BaseRS).promoData!!.promoType!!.equals("2"))
                    {
                        couponData!!.text =(responseObj as BaseRS).promoData!!.promoValue+" % ( -"+couponPrice!!+" )"
                    }else{
                        couponData!!.text ="€ "+(responseObj as BaseRS).promoData!!.promoValue
                    }
                }else{

                    couponView!!.visibility = View.GONE

                    showDialogReservationCancel(this,(responseObj as BaseRS).message)

                    couponLayout!!.visibility = View.VISIBLE

                    successLayout!!.visibility = View.GONE

                    couponPrice = 0.0

                    couponType = ""


                }
            }
        }
    }

    fun webService(paymentType:String)
    {
      Log.v("Google", "" + createWebServiceFood(paymentType))
      RequestManager.setCustomerMakePaymentForMember(this, createWebServiceFood(paymentType), this)
    }

    fun showDialogReservation(
        context: Context?, offerType :String?, offerValue :String?
    ) {
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
        dialog.setContentView(R.layout.offer_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val foodLog = dialog.findViewById<View>(R.id.foodLog) as ImageView

        val textInfo = dialog.findViewById<View>(R.id.textInfo) as TextView

        textInfo.typeface = fontStyle(context,"SemiBold")

        textInfo.text = "'"+CouponCode+"' Applied"

        val textPrice = dialog.findViewById<View>(R.id.textPrice) as TextView

        textPrice.typeface = fontStyle(context,"SemiBold")

        if(offerType.equals("2"))
        {
            textPrice.text = offerValue+"%"

        }else{
            textPrice.text = "€ "+offerValue
        }

        val textSaving = dialog.findViewById<View>(R.id.textSaving) as TextView

        textSaving.typeface = fontStyle(context,"Light")

        val textDescription = dialog.findViewById<View>(R.id.textDescription) as TextView

        textDescription.typeface = fontStyle(context,"")

        textDescription.text = ""+CouponCode+" and Savings every time your order "

        val textOk = dialog.findViewById<View>(R.id.textOk) as TextView

        textOk.typeface = fontStyle(context,"SemiBold")

        textOk.setOnClickListener(View.OnClickListener { dialog.cancel() })

        if(Constant.AppType!!.equals("0")) {

            foodLog.setImageResource(R.drawable.food_offer)

            textOk.textColor = Color.parseColor("#ec272b")
        }else{
            foodLog.setImageResource(R.drawable.offer_grocery)

            textOk.textColor = Color.parseColor("#7DC77D")
        }


        dialog.show()
    }


    fun showDialogReservationCancel(
        context: Context?,msg:String?
    ) {
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
        dialog.setContentView(R.layout.offer_cancel)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val offerInfo = dialog.findViewById<View>(R.id.offerInfo) as ImageView

        val textInfo = dialog.findViewById<View>(R.id.textInfo) as TextView

        textInfo.typeface = fontStyle(context,"SemiBold")

        val textPrice = dialog.findViewById<View>(R.id.textPrice) as TextView

        textPrice.typeface = fontStyle(context,"SemiBold")

        val textSaving = dialog.findViewById<View>(R.id.textSaving) as TextView

        textSaving.typeface = fontStyle(context,"Light")

        val textDescription = dialog.findViewById<View>(R.id.textDescription) as TextView

        textDescription.typeface = fontStyle(context,"")

        textDescription.text = ""+msg

        val textOk = dialog.findViewById<View>(R.id.textOk) as TextView

        textOk.typeface = fontStyle(context,"SemiBold")

        textOk.setOnClickListener(View.OnClickListener { dialog.cancel() })
        if(Constant.AppType!!.equals("0")) {

            offerInfo.setImageResource(R.drawable.offer_img_info)

            textOk.textColor = Color.parseColor("#ec272b")
        }else{
            offerInfo.setImageResource(R.drawable.grocery_coupon_info)

            textOk.textColor = Color.parseColor("#7DC77D")
        }
        dialog.show()
    }

    fun promCal(totalValue : Double,couponType:String,couponAmt : Double):Double
    {
        var total : Double? =0.0

        if(couponType.equals("2"))
        {
            var percentage = (totalValue * couponAmt) / 100

            total = percentage;

            Log.i("Total "+total,""+totalValue+" ="+percentage)
        }
        return total!!
    }
    fun couponCal(totalValue : Double,couponType:String,couponAmt : Double):Double
    {
        var total : Double? =0.0

        if(couponType.equals("1"))
        {
            total = totalValue - couponAmt;
            Log.i("Total "+total,""+totalValue+" =")
        }else{
            var percentage = (totalValue * couponAmt) / 100

            total = totalValue - percentage;

            Log.i("Total "+total,""+totalValue+" ="+percentage)
        }


        return total!!
    }

}