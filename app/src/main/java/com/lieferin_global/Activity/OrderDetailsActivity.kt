package com.lieferin_global.Activity

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.AddAddressAdapter
import com.lieferin_global.Adapter.OrderSelectAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.Constant.quality
import com.lieferin_global.Utility.Constant.totalPrice
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.lieferin_global.Adapter.CardListViewAdapter
import com.lieferin_global.Adapter.PaymentListAdapter
import com.lieferin_global.Fragment.StripePaymentGateway
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Utility.Constant.BookingType
import com.lieferin_global.Utility.Constant.addressComplete
import com.lieferin_global.Utility.Constant.customerPincode
import com.lieferin_global.Utility.SingleShotLocationProvider.requestSingleUpdate
import com.stripe.android.view.CardMultilineWidget
import org.json.JSONObject
import java.util.*

class OrderDetailsActivity : Activity(),View.OnClickListener,OrderSelectAdapter.CallbackDataAdapter,
    PaymentListAdapter.CallbackDataAdapter,AddAddressAdapter.CallbackDataAdapter,ResponseListener,CardListViewAdapter.CallbackDataAdapter {

    var order_Title : TextView? = null

    var order_Type : TextView? = null

    var selectCard: String? = "0"

    var cardName = ""

    var cardNo = ""

    var expriyDate = ""

    var isDelete = true

    var isDeleteValue = true

    var order_back : ImageView? = null

    private val REQUEST_LOCATION = 22

    protected val REQUEST_CHECK_SETTINGS = 1000

    var PICK_LOCATION: Int = 3

    private var locationManager: LocationManager? = null

    var orderOptionRecyclerView : RecyclerView? = null

    var orderAddressRecyclerView : RecyclerView? = null

    var orderAddressTV : TextView? = null

    var adapterProduct: MutableList<Product> = ArrayList()

    var adapterProduct1: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    internal lateinit var productModel: Product

    var addAddressAdapter: AddAddressAdapter? =null

    var orderSelectAdapter: PaymentListAdapter? =null

    var continueTxt: TextView? =null

    var plus_Img: ImageView? = null

    var btnDatePicker: Button? = null
    var btnTimePicker:Button? = null
    var txtDate: EditText? = null
    var txtTime:EditText? = null
    private val mYear =0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0
    private  var mHour:Int = 0
    private  var mMinute:Int = 0

    var addressReferenceCode: String = ""

    var total_value:String = ""

    var itemValue : String = ""

    var longtitudeValue: String? = ""

    var latitudeValue: String? = ""

    var dbHelper: DBHelper? = null

    var select_payment: String  = "0"

    var paymentTypeTV: String  = ""

    var selectTypeTV : TextView? = null

    var addCard: TextView? = null

    var addNewCard: LinearLayout? = null

    var notificationRecyclerView: RecyclerView? = null

    var sortPageAdapter: CardListViewAdapter? = null

    var adapterSort: MutableList<AdapterModel> = ArrayList()

    var adapterCategories2: MutableList<Product> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    var cardViewLayout : LinearLayout? = null

    var addressLayout : LinearLayout? = null

    var addressNestedScrollView : NestedScrollView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_order_details)

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

        addressLayout = findViewById(R.id.addressLayout)

        dbHelper = DBHelper(this)



        order_back = findViewById(R.id.order_back) as ImageView

        order_back!!.setOnClickListener(this)

        addressNestedScrollView = findViewById(R.id.addressNestedScrollView) as NestedScrollView

        order_back!!.setColorFilter(
            colorIcon(this,R.color.colorBlack,R.drawable.abc_ic_ab_back_material,order_back!!),
            PorterDuff.Mode.SRC_ATOP)

        plus_Img = findViewById(R.id.plus_Img) as ImageView

        plus_Img!!.setColorFilter(
            colorIcon(this,R.color.redColor,R.drawable.plus_add,plus_Img!!),
            PorterDuff.Mode.SRC_ATOP)

        order_Title = findViewById(R.id.order_Title)

        order_Title!!.typeface = fontStyle(this,"")

        order_Type = findViewById(R.id.order_Type)

        order_Type!!.typeface = fontStyle(this,"Light")

        orderAddressTV = findViewById(R.id.orderAddressTV)

        orderAddressTV!!.typeface = fontStyle(this,"Light")

        orderAddressTV!!.setOnClickListener(this)

        continueTxt = findViewById(R.id.continueTxt)

        continueTxt!!.typeface = fontStyle(this,"SemiBold")

        selectTypeTV = findViewById(R.id.selectTypeTV)

        selectTypeTV!!.typeface = fontStyle(this,"SemiBold")

        continueTxt!!.setOnClickListener(this)

        orderAddressRecyclerView = findViewById(R.id.orderAddressRecyclerView) as RecyclerView

        orderOptionRecyclerView = findViewById(R.id.orderOptionRecyclerView) as RecyclerView

        orderSelectAdapter = PaymentListAdapter(this,adapterProduct)

        orderOptionRecyclerView!!.setHasFixedSize(true)

        orderOptionRecyclerView!!.setLayoutManager(
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        )
        orderOptionRecyclerView!!.isNestedScrollingEnabled = false

        orderSelectAdapter!!.setCallback(this)

        orderOptionRecyclerView!!.setAdapter(orderSelectAdapter!!)

        addAddressAdapter = AddAddressAdapter(this,adapterProduct1)

        orderAddressRecyclerView!!.setHasFixedSize(true)

        orderAddressRecyclerView!!.setLayoutManager(
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        )
        orderAddressRecyclerView!!.isNestedScrollingEnabled = false

        addAddressAdapter!!.setCallback(this)

        orderAddressRecyclerView!!.setAdapter(addAddressAdapter!!)

        showDataProduct("","")

        //showDataProduct1()

        val intent = intent

        if(intent != null) {
            if (intent.getStringExtra("Total Amount") != null) {
                total_value = intent.getStringExtra("Total Amount")

                totalPrice = ""+total_value
            }

            if (intent.getStringExtra("Item") != null) {
                itemValue = intent.getStringExtra("Item")

                quality = ""+itemValue
            }
        }

        webService()

        if(Constant.AppType!!.equals("0")) {

            orderAddressTV!!.setTextColor(Color.parseColor("#ec272b"))
            continueTxt!!.setBackgroundResource(R.drawable.home_radius_button_orange)
            plus_Img!!.setColorFilter(
                colorIcon(this,R.color.redColor,R.drawable.plus_add,plus_Img!!),
                PorterDuff.Mode.SRC_ATOP)

        }else{
            orderAddressTV!!.setTextColor(Color.parseColor("#7DC77D"))
            continueTxt!!.setBackgroundResource(R.drawable.home_radius_button_green)

            plus_Img!!.setColorFilter(
                colorIcon(this,R.color.colorGreen,R.drawable.plus_add,plus_Img!!),
                PorterDuff.Mode.SRC_ATOP)

        }

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

        cardViewLayout = findViewById(R.id.cardViewLayout) as LinearLayout

        cardViewLayout!!.visibility = View.GONE

        if(BookingType!!.equals("0"))
        {
            orderAddressRecyclerView!!.visibility = View.VISIBLE

            orderAddressTV!!.visibility = View.VISIBLE

            addressLayout!!.visibility = View.VISIBLE
        }else{
            orderAddressRecyclerView!!.visibility = View.GONE

            orderAddressTV!!.visibility = View.GONE

            addressLayout!!.visibility = View.GONE
        }


    }
    fun webServiceCard()
    {
        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        RequestManager.setCardListActivity(this, obj, this);

        Log.v("kkkk"+obj,"33333")
    }
    fun webService(){
        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        RequestManager.setAddressDetails(this,obj,this)

        loadingScreen(this)
    }
    fun webServiceDelivery(){
        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        obj.put("restaurantReferenceCode", ""+ dbHelper!!.getRestaurant().restaurantReferenceCode)

        obj.put("customerAddressReferenceCode", ""+ addressReferenceCode)

        RequestManager.setCheckDeliveryLocation(this,obj,this)

        loadingScreen(this)

        Log.i("pp"+obj,"==")

        Log.v(dbHelper!!.getRestaurant().restaurantReferenceCode+" pp1 "+addressReferenceCode,"==")
    }

    fun webServiceGroceryDelivery(){
        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        obj.put("groceryReferenceCode", ""+ dbHelper!!.getGrecoryTotalAll().get(0).restaurantReferenceCode)

        obj.put("customerAddressReferenceCode", ""+ addressReferenceCode)

        RequestManager.setCheckGroceryDeliveryLocation(this,obj,this)

        loadingScreen(this)
    }

    fun showDataProduct(time:String,type:String) {

        if (adapterProduct.size != 0) {
            adapterProduct.clear()
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
        adapterProduct.add(productModel)

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
        adapterProduct.add(productModel)

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
        adapterProduct.add(productModel)
        orderSelectAdapter!!.notifyDataSetChanged()
    }

    fun showDataProduct1() {

        if (adapterProduct1.size != 0) {
            adapterProduct1.clear()
        }
        productModel = Product(R.drawable.delete, "Er Vijendra Rao", "#304, 305, 5th St Ext, 5th Street Extension, Gandhipuram, Tamil Nadu 641012","1","","4.0","40 Mins","200 for two","","","","","","","","","","",adapterProductList)
        adapterProduct1.add(productModel)

        productModel = Product(R.drawable.delete, "David warner", "705, 8th St Ext, 5th Street Extension, Gandhipuram, Tamil Nadu 641012","1","","4.0","40 Mins","200 for two","","","","","","","","","","",adapterProductList)
        adapterProduct1.add(productModel)

        //addAddressAdapter!!.notifyDataSetChanged()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {

            REQUEST_LOCATION -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                displayLocationSettingsRequest(this)
            } else {
                showToast(this, "Permission Denied")
            }
        }
    }
    override fun onClick(view: View?) {
        when(view!!.id)
        {
            R.id.order_back ->{

                finish()
            }

            R.id.addNewCard ->{
                showDialogInfo(this)
            }

            R.id.continueTxt ->{

                if(!addressReferenceCode.equals("")) {

                    //var intent = Intent(this, PaymentActivity::class.java)

                    //intent.putExtra("Total Amount", "" + total_value)

                    //intent.putExtra("Address_Reference", "" + addressReferenceCode)

                    //intent.putExtra("item", "" + itemValue)

                    if(AppType.equals("0")) {

                        webServiceDelivery()
                    }else{
                        webServiceGroceryDelivery()
                    }

                    //startActivity(intent)
                }else{
                    if(BookingType!!.equals("0")) {
                        showToast(this, getString(R.string.pleaseSelectAddress))
                    }else {
                        Log.v(" kk " + Constant.totalPrice, "ggggg")
                        if (select_payment.equals("2")) {
                            Constant.PayMentType = "2"
                            if (!selectCard!!.equals("0")) {
                                var intent = Intent(this, StripePaymentGateway::class.java)

                                intent.putExtra("Total Amount", "" + total_value)

                                intent.putExtra("Address_Reference", "" + addressReferenceCode)

                                intent.putExtra("item", "" + itemValue)

                                intent.putExtra("Card Name", "" + cardName)

                                intent.putExtra("Card No", "" + cardNo)

                                intent.putExtra("ExpiryDate", "" + expriyDate)

                                startActivity(intent)

                                Constant.deliveryFare = "0"
                            }
                        }else if(select_payment.equals("3")){

                            Constant.PayMentType = "3"
                            //processPayment()
                            var intent = Intent(this, StripePaymentGateway::class.java)

                            intent.putExtra("Total Amount", "" + total_value)

                            intent.putExtra("Address_Reference", "" + addressReferenceCode)

                            intent.putExtra("item", "" + itemValue)

                            startActivity(intent)

                            Constant.deliveryFare = "0"
                        }
                        else  if(select_payment.equals("0")){
                            showToast(this,"Please select payment type")
                        }
                    }
                }

            }
            R.id.orderAddressTV ->{

                Constant.AppUpdate = "0"
                val rc: Int = ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                if (rc == PackageManager.PERMISSION_GRANTED) {
                    displayLocationSettingsRequest(this)
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION
                    )
                }

                loadingScreen(this)
                //startActivity(Intent(this,MapsInnerActivity::class.java))

            }
        }
    }

    private fun displayLocationSettingsRequest(context: Context) {
        val googleApiClient = GoogleApiClient.Builder(context)
            .addApi(LocationServices.API).build()
        googleApiClient.connect()
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 10000 / 2.toLong()
        val builder =
            LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback { result ->
            val status = result.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS -> {
                    foo(this)
                    Log.i("", "All location settings are satisfied.")
                }
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    Log.i(
                        "",
                        "Location settings are not satisfied. Show the user a dialog to upgrade location settings "
                    )
                    try { // Show the dialog by calling startResolutionForResult(), and check the result
                        // in onActivityResult().
                        status.startResolutionForResult(
                            this,
                            REQUEST_CHECK_SETTINGS
                        )
                    } catch (e: IntentSender.SendIntentException) {
                        Log.i("", "PendingIntent unable to execute request.")
                    }
                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Log.i(
                    "",
                    "Location settings are inadequate, and cannot be fixed here. Dialog not created."
                )
            }
        }
    }
    @SuppressLint("ServiceCast", "MissingPermission")
    fun gpsLocation() {
        locationManager = getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager?

        locationManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0L,
            0f,
            locationListener
        )


    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            //thetext.text = ("" + location.longitude + ":" + location.latitude)


            homeScreen(location.latitude, location.longitude)

        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
    fun homeScreen(latitude: Double, longtitude: Double) {
        loadingScreenClose(this)
        finish()

        var intent = Intent(this, MapsInnerActivity::class.java)

        intent.putExtra("Page", "Add Address")
        intent.putExtra("latitude", ""+latitude)
        intent.putExtra("longitude", ""+longtitude)
        intent.putExtra("Total Amount", "" + total_value)
        intent.putExtra("item", "" + itemValue)
        startActivity(intent)



        latitudeValue = ""+latitude

        longtitudeValue= ""+longtitude

        locationManager?.removeUpdates(locationListener)


    }
    override fun setOnMaterial(id: String?) {

        if(id!!.equals(getString(R.string.Delivery)) || id!!.equals(getString(R.string.Pick_Up)) )
        {
            val c = Calendar.getInstance()
            mHour = c[Calendar.HOUR_OF_DAY]
            mMinute = c[Calendar.MINUTE]

            // Launch Time Picker Dialog
            // Launch Time Picker Dialog
            val timePickerDialog = TimePickerDialog(
                this,
                OnTimeSetListener { view, hourOfDay, minute -> showDataProduct(""+dateConversionValue(""+hourOfDay +":" + minute+""),""+id) },
                mHour,
                mMinute,
                false
            )

            timePickerDialog.setTitle("Choose Time:")

            timePickerDialog.show()
        }else if(id!!.equals(getString(R.string.Table_Booking)))
        {
            val mIentent = Intent(this, DashBoardActivity::class.java)
            mIentent.putExtra("page","Table");
            startActivity(mIentent)
        }

        orderSelectAdapter!!.notifyDataSetChanged()
    }

    override fun setOnAddress(id: String?,pincode : String?,id1: String?,pincode1 : String?,pincode2 : String?) {

        addressReferenceCode = id!!

        customerPincode = pincode!!

        addressComplete = pincode2!!

        Log.v("pincode"+customerPincode,"===="+addressReferenceCode)

        addAddressAdapter!!.notifyDataSetChanged()

        addressNestedScrollView!!.scrollTo(0,addressNestedScrollView!!.bottom)

    }

    override fun setOnDelete(id: String?) {
        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)
        obj.put("customerAddressReferenceCode", ""+ id)
        RequestManager.setAddRemove(this,obj,this)
    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if(responseObj != null) {
            if (requestType == Constant.MEMBER_customerAddress_URL_RQ) {

                loadingScreenClose(this)

                if((responseObj as BaseRS).status.equals("5016"))
                {
                    if (adapterProduct1.size != 0) {
                        adapterProduct1.clear()
                    }
                    showToast(this,(responseObj as BaseRS).message)
                    for (i in 0 until (responseObj as BaseRS).customerAddress!!.size) {

                        productModel = Product(R.drawable.delete, ""+localgetUserInfo("name"), ""+(responseObj as BaseRS).customerAddress!!.get(i).address,""+(responseObj as BaseRS).customerAddress!!.get(i).addressReferenceCode,""+(responseObj as BaseRS).customerAddress!!.get(i).postcode,""+(responseObj as BaseRS).customerAddress!!.get(i).longitude,""+(responseObj as BaseRS).customerAddress!!.get(i).latitude,""+(responseObj as BaseRS).customerAddress!!.get(i).address,""+(responseObj as BaseRS).customerAddress!!.get(i).completeAddress,""+(responseObj as BaseRS).customerAddress!!.get(i).floor,""+(responseObj as BaseRS).customerAddress!!.get(i).description,""+(responseObj as BaseRS).customerAddress!!.get(i).addressType,"","","","","","",adapterProductList)
                        adapterProduct1.add(productModel)

                    }
                    addAddressAdapter!!.notifyDataSetChanged()

                    webServiceCard()
                }

            }else if (requestType == Constant.MEMBER_deletCustomerAddress_URL_RQ) {
                if((responseObj as BaseRS).status.equals("5025"))
                {
                    showToast(this,(responseObj as BaseRS).message)
                    webService()
                }
            }else if (requestType == Constant.MEMBER_checkDeliveryLocation_URL_RQ) {
                showToast(this,(responseObj as BaseRS).message)
                loadingScreenClose(this)
                if((responseObj as BaseRS).status.equals("5047"))
                {
                    if(!Constant.DeliveryType.equals("3")) {
                        Constant.deliveryFare =
                            (responseObj as BaseRS).deliveryData!!.deliveryFare!!
                    }else{
                        Constant.deliveryFare ="0"
                    }
                    Constant.customerAddressId = (responseObj as BaseRS).deliveryData!!.customerAddressId!!

                    Constant.distanceValue = (responseObj as BaseRS).deliveryData!!.distance!!

                    if((responseObj as BaseRS).deliveryData!!.nifNumber != null) {

                        Constant.nifNumber = "" + (responseObj as BaseRS).deliveryData!!.nifNumber!!
                    }else{
                        Constant.nifNumber =""
                    }
                    nextScreen()

                    /*var intent = Intent(this, PaymentActivity::class.java)

                    //intent.putExtra("Total Amount", "" + total_value)

                    intent.putExtra("Address_Reference", "" + addressReferenceCode)

                    //intent.putExtra("item", "" + itemValue)

                    Constant.customerAddressId = (responseObj as BaseRS).deliveryData!!.customerAddressId!!

                    Constant.distanceValue = (responseObj as BaseRS).deliveryData!!.distance!!

                    startActivity(intent)*/
                }
            }else if (requestType == Constant.MEMBER_checkGroceryDeliveryLocation_URL_RQ) {
                showToast(this,(responseObj as BaseRS).message)

                loadingScreenClose(this)
                if((responseObj as BaseRS).status.equals("5826"))
                {
                    if(!Constant.DeliveryType.equals("3")) {
                        Constant.deliveryFare =
                            (responseObj as BaseRS).deliveryData!!.deliveryFare!!
                    }else{
                        Constant.deliveryFare ="0"
                    }
                    Constant.customerAddressId = (responseObj as BaseRS).deliveryData!!.customerAddressId!!

                    Constant.distanceValue = (responseObj as BaseRS).deliveryData!!.distance!!

                    if((responseObj as BaseRS).deliveryData!!.nifNumber != null) {

                        Constant.nifNumber = "" + (responseObj as BaseRS).deliveryData!!.nifNumber!!
                    }else{
                        Constant.nifNumber =""
                    }
                    nextScreen()

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
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                Log.i("", "PendrrrrringIntent unable to execute request.")
                foo(this)
            }
        }
    }

    fun foo(context: Context?) {
        // when you need location
        // if inside activity context = this;
        requestSingleUpdate(context!!,
            object : SingleShotLocationProvider.LocationCallback {
                override fun onNewLocationAvailable(location: SingleShotLocationProvider.GPSCoordinates) {
                    Log.d("Location", "my location is " + location.latitude)

                    loadingScreenClose(this@OrderDetailsActivity)

                    homeScreen(location.latitude.toDouble(), location.longitude.toDouble())
                }
            })
    }

    override fun setOnAddress(id: String?) {
        orderSelectAdapter!!.notifyDataSetChanged()
        for (i in 0 until adapterSort.size) {
            adapterSort.get(i).offer = "0"
        }
        if(id.equals("Cash On Delivery")) {
            select_payment = "1"
            paymentTypeTV = "Cash On Delivery"
            cardViewLayout!!.visibility = View.GONE
            sortPageAdapter!!.notifyDataSetChanged()
        }else if(id.equals("Debit / Credit / ATM Card")){
            select_payment = "2"
            cardViewLayout!!.visibility = View.VISIBLE
            sortPageAdapter!!.notifyDataSetChanged()

        }else{
            select_payment = "3"
            paymentTypeTV = "PayPal"
            cardViewLayout!!.visibility = View.GONE
            sortPageAdapter!!.notifyDataSetChanged()
        }


    }

    fun nextScreen(){
        //Log.v("pincode"+customerPincode,"===="+addressReferenceCode)
        if(select_payment.equals("2")){
            Constant.PayMentType = "2"
            if(!selectCard!!.equals("0")) {
                if (Constant.isTableBooking!!.equals("0")) {
                    var intent = Intent(this, StripePaymentGateway::class.java)

                    intent.putExtra("Total Amount", "" + total_value)

                    intent.putExtra("Address_Reference", "" + addressReferenceCode)

                    intent.putExtra("item", "" + itemValue)

                    intent.putExtra("Card Name", "" + cardName)

                    intent.putExtra("Card No", "" + cardNo)

                    intent.putExtra("ExpiryDate", "" + expriyDate)

                    startActivity(intent)
                }
            }
        }else if(select_payment.equals("3")){

            Constant.PayMentType = "3"
            //processPayment()
            var intent = Intent(this, StripePaymentGateway::class.java)

            intent.putExtra("Total Amount", "" + total_value)

            intent.putExtra("Address_Reference", "" + addressReferenceCode)

            intent.putExtra("item", "" + itemValue)

            startActivity(intent)
        }else if(select_payment.equals("1")){

            Constant.PayMentType = "1"
            //processPayment()
            var intent = Intent(this, StripePaymentGateway::class.java)

            intent.putExtra("Total Amount", "" + total_value)

            intent.putExtra("Address_Reference", "" + addressReferenceCode)

            intent.putExtra("item", "" + itemValue)

            startActivity(intent)
        }
        else  if(select_payment.equals("0")){
            showToast(this,"Please select payment type")
        }
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
}
