package com.lieferin_global.Fragment

import android.Manifest
import android.animation.ValueAnimator
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.lieferin_global.R
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Utility.*
import com.rentaloo.CallBack.CallBlacklisting
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.textColor
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


class OrderMapDetails : Fragment() , OnMapReadyCallback,View.OnClickListener,ResponseListener {
    private val REQUEST_PHONE = 23

    var rootView : View? = null

    var orderBill_Title : TextView? = null

    var orderDescription_Type : TextView? = null

    var help : TextView? = null

    var otpTV : TextView? = null

    var orderReceived : TextView? = null

    var orderNow : TextView? = null

    var orderReceivedDescription : TextView? = null

    var foodReceived : TextView? = null

    var nextNow : TextView? = null

    var shopReceived : TextView? = null

    var laterNow : TextView? = null

    var points: MutableList<LatLng>? = null

    var bookingRef : String? =null

    var prpTime : TextView? = null

    var prepareRL : RelativeLayout? = null

    fun OrderMapDetails() {}

    private lateinit var mRandom:Random
    private lateinit var mHandler: Handler
    private lateinit var mRunnable:Runnable

    var callBlacklisting: CallBlacklisting? = null

    var mapFragment: SupportMapFragment? = null

    private var mMap: GoogleMap? = null

    var dataChange = 0

    private var marker: Marker? = null
    private  var marker1: Marker? = null
    private var handler: Handler? = null

    var timeValue: String? =null

    var timeFrom:String? = null

    var timeTo:String? = null

    var duration: String? = null

    var distance:String? = null

    var distanceValue:String? = null

    var duration1: String? = null

    var distance1:String? = null

    var distanceValue1:String? = null

    var value = 0

    var from: String? = null

    var to:String? = null

    var closeIM : ImageView? = null


    var carDisplay:Int = 0

    private val mMarkers =  HashMap<String, Marker>()
    private var polyLineList: List<LatLng>? = null
    private var polyLineList1: List<LatLng>? = null
    private var v = 0f
    private var lat = 0.0
    private  var lng:Double = 0.0

    var fromVal = 0
    var toVal:Int = 0
    var toValStatus:Int = 1

    var con = 1

    var con1:Int = 1

    var con2:Int = 1

    private var startPosition: LatLng? = null
    private  var endPosition: LatLng? = null
    private var index = 0
    private  var next:Int = 0
    private var sydney: LatLng? = null
    private val button: Button? = null
    private val destinationEditText: EditText? = null
    private val destination: String? = null
    private var polylineOptions: PolylineOptions? = null
    private  var blackPolylineOptions: PolylineOptions? = null
    private var blackPolyline: Polyline? = null
    private  var greyPolyLine: Polyline? = null

    private var polylineOptions1: PolylineOptions? = null
    private  var blackPolylineOptions1: PolylineOptions? = null
    private var blackPolyline1: Polyline? = null
    private  var greyPolyLine1: Polyline? = null

    var isTrue = true

    var driverAccepted : RelativeLayout? = null

    var pickUpText : TextView? = null

    var delayText: TextView? = null

    var storeTextOne: TextView? = null

    var storeDescriptionOne: TextView? = null

    var storeTextTwo: TextView? = null

    var storeDescriptionTwo: TextView? = null

    var call: TextView? = null

    var orderStatus: TextView? = null

    var orderStatusChange: TextView? = null

    var bannerImg: ImageView? = null

    var bookingStatus: String? ="0"

    var bookingCustomerLatitude: String? = ""

    var driverId: String? ="0"

    var driverImg : CircleImageView? =null

    var shopImg : CircleImageView? =null

    var calImage : ImageView? = null

    var driverName: TextView? = null

    var driverPhone: TextView? = null

    var driverInfo : RelativeLayout? = null

    var customerLat: String? = ""

    var restLat: String? = ""

    var foodImg :ImageView? = null

    var dbHelper : DBHelper? = null

    var phoneNo: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_order_map_details, container, false)

        dbHelper = DBHelper(activity)

        points = ArrayList()

        val mapFragment = getChildFragmentManager().findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        var header = rootView!!.findViewById(R.id.header) as RelativeLayout

        header!!.setOnClickListener(this)

        calImage= rootView!!.findViewById(R.id.calImage) as ImageView

        calImage!!.setColorFilter(colorIcon(activity!!,R.color.colorGreenDrack,R.drawable.phone_call,calImage!!))

        calImage!!.setOnClickListener(this)

        foodImg = rootView!!.findViewById(R.id.foodImg) as ImageView

        driverInfo = rootView!!.findViewById(R.id.driverInfo) as RelativeLayout

        orderBill_Title = rootView!!.findViewById(R.id.orderBill_Title) as TextView

        driverAccepted = rootView!!.findViewById(R.id.driverAccepted) as RelativeLayout

        driverAccepted!!.visibility = View.GONE

        orderBill_Title!!.typeface = fontStyle(activity!!,"")

        driverImg = rootView!!.findViewById(R.id.driverImg) as CircleImageView

        shopImg = rootView!!.findViewById(R.id.shopImg) as CircleImageView

        driverName = rootView!!.findViewById(R.id.driverName) as TextView

        driverName!!.typeface = fontStyle(activity!!,"")

        driverPhone = rootView!!.findViewById(R.id.driverPhone) as TextView

        driverPhone!!.typeface = fontStyle(activity!!,"")

        orderDescription_Type = rootView!!.findViewById(R.id.orderDescription_Type) as TextView

        orderDescription_Type!!.typeface = fontStyle(activity!!,"Light")

        prepareRL = rootView!!.findViewById(R.id.prepareRL) as RelativeLayout

        val bundle = this.arguments
        if (bundle != null) {
            orderDescription_Type!!.text = getString(R.string.Delivered)+" "+bundle.getString("quantity")+" "+getString(R.string.item)+" "+bundle.getString("total")

            orderBill_Title!!.text =getString(R.string.order_Title)+"#"+bundle.getString("orderCode")

            val sydney1 = LatLng(bundle.getString("restLati")!!.toDouble(),bundle.getString("restLong")!!.toDouble())

            bookingRef = bundle.getString("customerLong")

            val arrOfStr =
                bundle.getString("customerLati").toString().split(",")

            val sydney2 = LatLng(arrOfStr[0].toDouble(),arrOfStr[1].toDouble())

            points!!.add(sydney1)

            customerLat = arrOfStr[0]+","+arrOfStr[1]
            points!!.add(sydney2)

            restLat = bundle.getString("restLati")+","+bundle.getString("restLong")
        }

        help = rootView!!.findViewById(R.id.help) as TextView

        help!!.typeface = fontStyle(activity!!,"Light")

        help!!.setOnClickListener(this)

        otpTV = rootView!!.findViewById(R.id.otpTV) as TextView

        otpTV!!.typeface = fontStyle(activity!!,"")

        orderReceived = rootView!!.findViewById(R.id.orderReceived) as TextView

        orderReceived!!.typeface = fontStyle(activity!!,"")

        orderNow = rootView!!.findViewById(R.id.orderNow) as TextView

        orderNow!!.typeface = fontStyle(activity!!,"")

        orderReceivedDescription = rootView!!.findViewById(R.id.orderReceivedDescription) as TextView

        orderReceivedDescription!!.typeface = fontStyle(activity!!,"Light")

        prpTime= rootView!!.findViewById(R.id.prpTime) as TextView

        prpTime!!.typeface = fontStyle(activity!!,"Light")

        nextNow = rootView!!.findViewById(R.id.nextNow) as TextView

        nextNow!!.typeface = fontStyle(activity!!,"")

        foodReceived = rootView!!.findViewById(R.id.foodReceived) as TextView

        foodReceived!!.typeface = fontStyle(activity!!,"")

        shopReceived = rootView!!.findViewById(R.id.shopReceived) as TextView

        shopReceived!!.typeface = fontStyle(activity!!,"")

        laterNow = rootView!!.findViewById(R.id.laterNow) as TextView

        laterNow!!.typeface = fontStyle(activity!!,"")

        if(AppType.equals("0")) {

            webService(bookingRef!!)
            otpTV!!.backgroundResource =R.drawable.home_radius_button_orange_right

            help!!.textColor = Color.parseColor("#ec272b")
        }else{
            webServiceGrocery(bookingRef!!)
            otpTV!!.backgroundResource =R.drawable.home_radius_button_green

            help!!.textColor = Color.parseColor("#7DC77D")
        }


        // Initialize the handler instance
        mHandler = Handler()

        // Set a click listener for run button

            mRunnable = Runnable {
                // Do something here
                if(AppType.equals("0")) {
                    webService(bookingRef!!)
                }else{
                    webServiceGrocery(bookingRef!!)
                }


                // Schedule the task to repeat after 1 second
                mHandler.postDelayed(
                    mRunnable, // Runnable
                    5000 // Delay in milliseconds
                )
            }

            // Schedule the task to repeat after 1 second
            mHandler.postDelayed(
                mRunnable, // Runnable
                1000 // Delay in milliseconds
            )
        handler = Handler()

        return rootView
    }

    fun webService(bookingString:String){

        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        obj.put("bookingOrderReferenceCode", ""+ bookingString)

        RequestManager.setConfirmFoodOrderList(activity, obj, this);

        Log.v("kkkk"+obj,"33333")

        //loadingScreen(activity)
    }

    fun webServiceGrocery(bookingString:String){

        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        obj.put("groceryOrderReferenceCode", ""+ bookingString)

        RequestManager.setConfirmGroceryOrderList(activity, obj, this);

        Log.v("kkkk"+obj,"33333")

        //loadingScreen(activity)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //mMap!!.setMapType(GoogleMap.MAP_TYPE_HYBRID)

        // Add a marker in Sydney and move the camera
        //val sydney = LatLng(11.0214677,76.9649095)
        val b: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.customer)
        val smallMarker: Bitmap = Bitmap.createScaledBitmap(b, 78, 78, false)
        val smallMarkerIcon: BitmapDescriptor = BitmapDescriptorFactory.fromBitmap(smallMarker)

        val b1: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.restaurant_map)
        val smallMarker1: Bitmap = Bitmap.createScaledBitmap(b1, 78, 78, false)

        val b2: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.grocery_map)
        val smallMarker2: Bitmap = Bitmap.createScaledBitmap(b2, 78, 78, false)

        mMap!!.addMarker(MarkerOptions().position(points!!.get(1)).title("").icon(smallMarkerIcon!!))

        if(AppType!!.equals("0")) {

            val smallMarkerIcon1: BitmapDescriptor =
                BitmapDescriptorFactory.fromBitmap(smallMarker1)
            mMap!!.addMarker(MarkerOptions().position(points!!.get(0)).title("Marker in Sydney").icon(smallMarkerIcon1!!))
        }else{
            val smallMarkerIcon1: BitmapDescriptor =
                BitmapDescriptorFactory.fromBitmap(smallMarker2)
            mMap!!.addMarker(MarkerOptions().position(points!!.get(0)).title("Marker in Sydney").icon(smallMarkerIcon1!!))
        }


        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(points!!.get(0)))

        val polylineOptions = PolylineOptions()

        // Setting the color of the polyline
        // Setting the color of the polyline
        polylineOptions.color(Color.RED)

        // Setting the width of the polyline
        // Setting the width of the polyline
        polylineOptions.width(3f)

        // Adding the taped point to the ArrayList
        // Adding the taped point to the ArrayList
        //points!!.add(sydney)

        // Setting points of polyline
        // Setting points of polyline
        polylineOptions.addAll(points)

        // Adding the polyline to the map
        // Adding the polyline to the map
        //googleMap.addPolyline(polylineOptions)

        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(points!!.get(0)!!, 13.5F),1000,null );


        updateRouteMap()

        loginToFirebase()
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

    override fun onResume() {
        Log.e("DEBUG", "onResume of HomeFragment")
        super.onResume()
    }

    override fun onPause() {
        Log.e("DEBUG", "OnPause of HomeFragment")
        mHandler.removeCallbacks(mRunnable)
        super.onPause()
    }
    override fun onClick(view: View?) {

        when(view!!.id)
        {
            R.id.help!! ->
            {
                callBlacklisting!!.fragmentChange("Help",null)
            }

            R.id.calImage!!->{
                checkPermissionSms(phoneNo)
            }
        }

    }
    fun checkPermissionSms(value : String) {
        val rc = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.CALL_PHONE
        )
        if (rc == PackageManager.PERMISSION_GRANTED) {
            phoneCall(value)
        } else {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.CALL_PHONE),
                REQUEST_PHONE
            )
        }
    }
    fun phoneCall(phone:String)
    {
        val callIntent =
            Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone!!.toString().replace("-","")))
        startActivity(callIntent)
    }

    private fun getURL(from : LatLng, to : LatLng) : String {
        val origin = "origin=" + from.latitude + "," + from.longitude
        val dest = "destination=" + to.latitude + "," + to.longitude
        val sensor = "sensor=false"
        val params = "$origin&$dest&$sensor"
        return "https://maps.googleapis.com/maps/api/directions/json?$params"
    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if (responseObj != null) {
            //loadingScreenClose(activity)
            if (requestType == Constant.MEMBER_confirmFoodOrderList_URL_RQ) {

                driverAccepted!!.visibility = View.GONE

                Log.v("Status"+(responseObj as BaseRS).bookingConfirmData!!.get(0).bookingOrderStatus,"Value")

                otpTV!!.text = "OTP : "+(responseObj as BaseRS).bookingConfirmData!!.get(0).orderOtp

                bookingStatus = ""+(responseObj as BaseRS).bookingConfirmData!!.get(0).bookingOrderStatus

                bookingCustomerLatitude= ""+(responseObj as BaseRS).bookingConfirmData!!.get(0).bookingCustomerLongitude+","+(responseObj as BaseRS).bookingConfirmData!!.get(0).bookingCustomerLatitude

                driverInfo!!.visibility = View.GONE

                if((responseObj as BaseRS).bookingConfirmData!!.get(0).bookingOrderStatus.equals("1")) {

                    if((responseObj as BaseRS).bookingConfirmData!!.get(0).deliveryType!!.equals("3"))
                    {
                        otpTV!!.visibility = View.GONE
                        orderReceived!!.text = "Order Pending"

                        orderReceivedDescription!!.text =
                            "We're waiting for " + (responseObj as BaseRS).bookingConfirmData!!.get(0).restaurantName + " to confirm your order"

                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE

                    }else {
                        otpTV!!.visibility = View.VISIBLE
                        orderReceived!!.text = "Order Pending"

                        orderReceivedDescription!!.text =
                            "We're waiting for " + (responseObj as BaseRS).bookingConfirmData!!.get(0).restaurantName + " to confirm your order"

                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE

                    }
                }else if((responseObj as BaseRS).bookingConfirmData!!.get(0).bookingOrderStatus.equals("4")) {

                    if((responseObj as BaseRS).bookingConfirmData!!.get(0).deliveryType!!.equals("3"))
                    {
                        otpTV!!.visibility = View.GONE
                        orderReceived!!.text = "Order ready for dispatch"

                        orderReceivedDescription!!.text =
                            "Order packed"

                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE
                    }else {
                        otpTV!!.visibility = View.VISIBLE
                        orderReceived!!.text = "Order ready for dispatch"
                        if((responseObj as BaseRS).bookingConfirmData!!.get(0).deliveryType!!.equals("2")) {
                            orderReceivedDescription!!.text =
                                "Order has been dispatched by the restaurants own delivery boy"
                        }else{
                            orderReceivedDescription!!.text =
                            "We're waiting for delivery person"
                        }

                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE
                    }


                }else if((responseObj as BaseRS).bookingConfirmData!!.get(0).bookingOrderStatus.equals("2"))
                {

                    if((responseObj as BaseRS).bookingConfirmData!!.get(0).deliveryType!!.equals("3"))
                    {
                        otpTV!!.visibility = View.GONE
                        orderReceived!!.text = "Order Confirmed"

                        if(AppType!!.equals("0")) {
                            orderReceivedDescription!!.text =
                                "Your restaurant has been confirmed your order"
                        }else{
                            "Your store has been confirmed your order"
                        }
                        foodImg!!.setImageResource(R.drawable.coffee_cup)

                        foodReceived!!.text ="Food Is Being Prepared"
                        orderNow!!.text = "ago"
                        prpTime!!.text =
                            "It will completed in " + dateConversionVal((responseObj as BaseRS).bookingConfirmData!!.get(0).bookingDateTime)
                        prepareRL!!.visibility = View.VISIBLE
                    }else {
                        otpTV!!.visibility = View.VISIBLE
                        orderReceived!!.text = "Order Confirmed"

                        if(AppType!!.equals("0")) {
                            orderReceivedDescription!!.text =
                                "Your restaurant has been confirmed your order"

                            prpTime!!.text =
                                "You have to come and fetch your order at restaurant on  " + dateConversionVal((responseObj as BaseRS).bookingConfirmData!!.get(0).bookingDateTime)

                        }else{
                            orderReceivedDescription!!.text ="Your store has been confirmed your order"

                            prpTime!!.text =
                                "You have to come and fetch your order at store on  " + dateConversionVal((responseObj as BaseRS).bookingConfirmData!!.get(0).bookingDateTime)

                        }
                        orderNow!!.text = "ago"
                        prpTime!!.text =
                            "It will completed in " + (responseObj as BaseRS).bookingConfirmData!!.get(0).itemsPreparationTime
                        prepareRL!!.visibility = View.VISIBLE

                        foodImg!!.setImageResource(R.drawable.coffee_cup)

                        foodReceived!!.text ="Food Is Being Prepared"

                        if((responseObj as BaseRS).bookingConfirmData!!.get(0).driverStatus!!.equals("1")) {

                            driverAccepted!!.visibility = View.VISIBLE

                            shopReceived!!.text = ""+(responseObj as BaseRS).bookingConfirmData!!.get(0).driverFirstname!!

                            laterNow!!.text = "Driver Accept your order"

                            if (!(responseObj as BaseRS).bookingConfirmData!!.get(0).driverProfile.equals(
                                    ""
                                )
                            ) {
                                Picasso.with(activity!!)
                                    .load((responseObj as BaseRS).bookingConfirmData!!.get(0).driverProfile).placeholder(R.drawable.place_holder)
                                    .resize(450, 450).into(shopImg)
                            } else {
                                Picasso.with(activity!!).load(R.drawable.place_holder).resize(450, 450)
                                    .into(shopImg)
                            }

                        }else{
                            driverAccepted!!.visibility = View.GONE
                        }
                    }
                }else if((responseObj as BaseRS).bookingConfirmData!!.get(0).bookingOrderStatus.equals("7"))
                {

                    if((responseObj as BaseRS).bookingConfirmData!!.get(0).deliveryType!!.equals("3"))
                    {
                        otpTV!!.visibility = View.GONE
                        orderReceived!!.text = "Order Cancel"

                        if(AppType.equals("0")) {
                            orderReceivedDescription!!.text =
                                "Your order has been cancelled by restaurant"
                        }else{
                            orderReceivedDescription!!.text =
                                "Your order has been cancelled by store"
                        }

                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE
                    }else {
                        otpTV!!.visibility = View.GONE
                        orderReceived!!.text = "Order Cancel"

                        if(AppType.equals("0")) {
                            orderReceivedDescription!!.text =
                                "Your order has been cancelled by restaurant"
                        }else{
                            orderReceivedDescription!!.text =
                                "Your order has been cancelled by store"
                        }

                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE
                    }

                }else if((responseObj as BaseRS).bookingConfirmData!!.get(0).bookingOrderStatus.equals("6"))
                {

                    if((responseObj as BaseRS).bookingConfirmData!!.get(0).deliveryType!!.equals("3"))
                    {
                        otpTV!!.visibility = View.GONE
                        orderReceived!!.text = "Order Delivered"

                        orderReceivedDescription!!.text ="Your order has been delivered"

                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE
                    }else {
                        otpTV!!.visibility = View.GONE
                        orderReceived!!.text = "Order Delivered"

                        orderReceivedDescription!!.text ="Your order has been delivered"

                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE
                    }

                }
                else if((responseObj as BaseRS).bookingConfirmData!!.get(0).bookingOrderStatus.equals("5"))
                {

                    if((responseObj as BaseRS).bookingConfirmData!!.get(0).deliveryType!!.equals("3"))
                    {
                        otpTV!!.visibility = View.GONE
                        orderReceived!!.text = "Order Pick Up"

                        orderReceivedDescription!!.text ="Order pick up by "+(responseObj as BaseRS).bookingConfirmData!!.get(0).customerFirstname!!

                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE

                        driverName!!.visibility = View.GONE

                        driverPhone!!.visibility = View.GONE
                    }else {
                        if((responseObj as BaseRS).bookingConfirmData!!.get(0).deliveryType!!.equals("2"))
                        {
                            driverInfo!!.visibility = View.GONE
                        }else{
                            driverInfo!!.visibility = View.VISIBLE
                        }
                        otpTV!!.visibility = View.VISIBLE
                        orderReceived!!.text = "Order Pick Up"

                        orderReceivedDescription!!.text = "Driver is on the way"

                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE


                        //driverInfo!!.visibility = View.VISIBLE

                        driverId =
                            "" + (responseObj as BaseRS).bookingConfirmData!!.get(0).driverFirstname + "" + (responseObj as BaseRS).bookingConfirmData!!.get(
                                0
                            ).deliveryPersonId

                        driverName!!.text =
                            "" + (responseObj as BaseRS).bookingConfirmData!!.get(0).driverFirstname

                        driverPhone!!.text =
                            "" + (responseObj as BaseRS).bookingConfirmData!!.get(0).driverMobile

                        phoneNo =  "" + (responseObj as BaseRS).bookingConfirmData!!.get(0).driverMobile

                        if (!(responseObj as BaseRS).bookingConfirmData!!.get(0).driverProfile.equals(
                                ""
                            )
                        ) {
                            Picasso.with(activity!!)
                                .load((responseObj as BaseRS).bookingConfirmData!!.get(0).driverProfile).placeholder(R.drawable.place_holder)
                                .resize(450, 450).into(driverImg)
                        } else {
                            Picasso.with(activity!!).load(R.drawable.place_holder).resize(450, 450)
                                .into(bannerImg)
                        }
                    }
                }
            }else if (requestType == Constant.MEMBER_confirmGroceryList_URL_RQ) {
                Log.v("Status"+(responseObj as BaseRS).bookingConfirmData!!.get(0).bookingOrderStatus,"Value")

                otpTV!!.text = "OTP : "+(responseObj as BaseRS).bookingConfirmData!!.get(0).orderOtp

                driverAccepted!!.visibility = View.GONE

                if((responseObj as BaseRS).bookingConfirmData!!.get(0).bookingOrderStatus.equals("1")) {


                    if((responseObj as BaseRS).bookingConfirmData!!.get(0).deliveryType!!.equals("3"))
                    {
                        otpTV!!.visibility = View.GONE
                        orderReceived!!.text = "Order Pending"

                        orderReceivedDescription!!.text =
                            "We're waiting for " + (responseObj as BaseRS).bookingConfirmData!!.get(0).groceryName + " to confirm your order"

                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE
                    }else {
                        otpTV!!.visibility = View.VISIBLE
                        orderReceived!!.text = "Order Pending"

                        orderReceivedDescription!!.text =
                            "We're waiting for " + (responseObj as BaseRS).bookingConfirmData!!.get(0).groceryName + " to confirm your order"

                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE
                    }


                }else if((responseObj as BaseRS).bookingConfirmData!!.get(0).bookingOrderStatus.equals("4")) {


                    if((responseObj as BaseRS).bookingConfirmData!!.get(0).deliveryType!!.equals("3"))
                    {
                        otpTV!!.visibility = View.GONE
                        orderReceived!!.text = "Order ready for dispatch"

                        orderReceivedDescription!!.text =
                            "Order packed"

                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE
                    }else {
                        otpTV!!.visibility = View.VISIBLE
                        orderReceived!!.text = "Order ready for dispatch"

                        if((responseObj as BaseRS).bookingConfirmData!!.get(0).deliveryType!!.equals("2")) {
                            orderReceivedDescription!!.text =
                                "Order has been dispatched by the restaurants own delivery boy"
                        }else{
                            orderReceivedDescription!!.text =
                                "We're waiting for delivery person"
                        }
                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE
                    }


                }else if((responseObj as BaseRS).bookingConfirmData!!.get(0).bookingOrderStatus.equals("2"))
                {

                    if((responseObj as BaseRS).bookingConfirmData!!.get(0).deliveryType!!.equals("3"))
                    {
                        otpTV!!.visibility = View.GONE
                        orderReceived!!.text = "Order Confirmed"

                        if(AppType!!.equals("0")) {
                            orderReceivedDescription!!.text =
                                "Your restaurant has been confirmed your order"
                            prpTime!!.text =
                                "You have to come and fetch your order at restaurant on  " + dateConversionVal((responseObj as BaseRS).bookingConfirmData!!.get(0).bookingDateTime)
                        }else{
                            orderReceivedDescription!!.text ="Your store has been confirmed your order"
                            prpTime!!.text =
                                "You have to come and fetch your order at store on  " + dateConversionVal((responseObj as BaseRS).bookingConfirmData!!.get(0).bookingDateTime)
                        }
                        orderNow!!.text = "ago"
                       /* prpTime!!.text =
                            "You have to come and fetch your order at restaurant on  " + dateConversionVal((responseObj as BaseRS).bookingConfirmData!!.get(0).bookingDateTime)
          */              prepareRL!!.visibility = View.VISIBLE
                    }else {
                        otpTV!!.visibility = View.VISIBLE
                        orderReceived!!.text = "Order Confirmed"

                        if(AppType!!.equals("0")) {
                            orderReceivedDescription!!.text =
                                "Your restaurant has been confirmed your order"
                            foodReceived!!.text ="Order Is Packing"
                        }else{

                            foodReceived!!.text ="Order Is Packing"
                            orderReceivedDescription!!.text ="Your store has been confirmed your order"
                        }
                        orderNow!!.text = "ago"
                        prpTime!!.text =
                            "It will completed in " + (responseObj as BaseRS).bookingConfirmData!!.get(0).itemsPackingTime
                        prepareRL!!.visibility = View.VISIBLE
                        foodImg!!.setImageResource(R.drawable.shopping_bag)
                        foodReceived!!.text ="Order Is Packing"
                    }
                }else if((responseObj as BaseRS).bookingConfirmData!!.get(0).bookingOrderStatus.equals("7"))
                {

                    if((responseObj as BaseRS).bookingConfirmData!!.get(0).deliveryType!!.equals("3"))
                    {
                        otpTV!!.visibility = View.GONE
                        orderReceived!!.text = "Order Cancel"

                        if(AppType.equals("0")) {
                            orderReceivedDescription!!.text =
                                "Your order has been cancelled by restaurant"
                        }else{
                            orderReceivedDescription!!.text =
                                "Your order has been cancelled by store"
                        }

                        orderNow!!.text = "Now"



                        prepareRL!!.visibility = View.GONE
                    }else {
                        otpTV!!.visibility = View.GONE
                        orderReceived!!.text = "Order Cancel"

                        if(AppType.equals("0")) {
                            orderReceivedDescription!!.text =
                                "Your order has been cancelled by restaurant"
                        }else{
                            orderReceivedDescription!!.text =
                                "Your order has been cancelled by store"
                        }

                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE
                    }

                }else if((responseObj as BaseRS).bookingConfirmData!!.get(0).bookingOrderStatus.equals("6"))
                {

                    if((responseObj as BaseRS).bookingConfirmData!!.get(0).deliveryType!!.equals("3"))
                    {
                        otpTV!!.visibility = View.GONE

                        orderReceived!!.text = "Order Delivered"

                        orderReceivedDescription!!.text ="Your order has been delivered"

                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE

                    }else {
                        otpTV!!.visibility = View.GONE

                        orderReceived!!.text = "Order Delivered"

                        orderReceivedDescription!!.text ="Your order has been delivered"

                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE
                    }

                }
                else if((responseObj as BaseRS).bookingConfirmData!!.get(0).bookingOrderStatus.equals("5"))
                {

                    if((responseObj as BaseRS).bookingConfirmData!!.get(0).deliveryType!!.equals("3"))
                    {
                        otpTV!!.visibility = View.GONE
                        orderReceived!!.text = "Order Pick Up"

                        orderReceivedDescription!!.text ="Order pick up"+(responseObj as BaseRS).bookingConfirmData!!.get(0).customerFirstname!!

                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE
                    }else {
                        otpTV!!.visibility = View.VISIBLE
                        orderReceived!!.text = "Order Pick Up"

                        orderReceivedDescription!!.text ="Driver is on the way"

                        orderNow!!.text = "Now"

                        prepareRL!!.visibility = View.GONE
                    }

                }
            }
        }
    }

    private fun loginToFirebase() {
        val email = getString(R.string.firebase_email)
        val password = getString(R.string.firebase_password)
        // Authenticate with Firebase and subscribe to updates
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            email, password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                subscribeToUpdates()
                Log.d("MainActivity.TAG", "firebase auth success")
            } else {
                Log.d("MainActivity.TAG", "firebase auth failed")
            }
        }
    }

    private fun subscribeToUpdates() {
        val ref = FirebaseDatabase.getInstance().getReference(""+driverId)
        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(
                dataSnapshot: DataSnapshot,
                previousChildName: String?
            ) {
                setMarker(dataSnapshot)
            }

            override fun onChildChanged(
                dataSnapshot: DataSnapshot,
                previousChildName: String?
            ) {
                dataChange = 1
                setMarker(dataSnapshot)
            }

            override fun onChildMoved(
                dataSnapshot: DataSnapshot,
                previousChildName: String?
            ) {
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
            override fun onCancelled(error: DatabaseError) {
                Log.d("MapsActivity2.TAG", "Failed to read value.", error.toException())
            }
        })
    }

    private fun setMarker(dataSnapshot: DataSnapshot) {
        // When a location update is received, put or update
        // its value in mMarkers, which contains all the markers
        // for locations received, so that we can build the
        // boundaries required to show them all on the map at once
        val key = dataSnapshot.key
        val value =
                dataSnapshot.value as HashMap<String, Any>?
        val lat = value!!["latitude"].toString().toDouble()
        val lng = value!!["longitude"].toString().toDouble()
        timeFrom = timeTo
        timeTo = getDate(value["time"].toString().toLong())
        Log.v("nnnfndn", "nn" + value["latitude"].toString())
        if (timeFrom != null) {
            difference()
        } else {
            /*mMap!!.addMarker(
                MarkerOptions()
                    .position(LatLng(lat, lng))
            )*/
        }
        to = "$lat,$lng"
        if (con1 == 1) {
            from = "$lat,$lng"
            sydney = LatLng(lat, lng)
        }
        con1 = 0
        // sydney = new LatLng(lat, lng);
        val location = LatLng(lat, lng)
        if (!mMarkers.containsKey(key)) {
            // mMarkers.put(key, mMap.addMarker(new MarkerOptions().title(key).position(location)));
        } else {
            //mMarkers.get(key).setPosition(location);
        }
        val builder = LatLngBounds.Builder()
        for (marker in mMarkers.values) {
            builder.include(marker.position)
        }
        // mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 300));

        //handler.removeCallbacks(mStatusChecker);
        Log.v("MapsActivity2.TAG", "http")
        //        if(mMap != null) {
//            mMap.clear();
//        }
        udateRoute()

        //udateRoute1();
    }

    private fun getDate(time: Long): String? {
        val cal = Calendar.getInstance(Locale.ENGLISH)
        cal.timeInMillis = time
        return DateFormat.format("HH:mm:ss", cal).toString()
    }

    private fun difference() {
        val simpleDateFormat = SimpleDateFormat("HH:mm:ss")
        var date1: Date? = null
        var date2: Date? = null
        try {
            date1 = simpleDateFormat.parse(timeFrom)
            date2 = simpleDateFormat.parse(timeTo)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val difference = date2!!.time - date1!!.time
        val days = (difference / (1000 * 60 * 60 * 24)).toInt()
        var hours = ((difference - 1000 * 60 * 60 * 24 * days) / (1000 * 60 * 60)).toInt()
        val min =
            (difference - 1000 * 60 * 60 * 24 * days - 1000 * 60 * 60 * hours).toInt() / (1000 * 60)
        hours = if (hours < 0) -hours else hours
        Log.i(
            "======= Hours" + date2.time + "--" + date1.time,
            " :: $difference"
        )
    }

    fun udateRoute() {
        var requestUrl: String? = null
        try {
            requestUrl =
                "https://maps.googleapis.com/maps/api/directions/json?origin=$from&destination=$to&key="+getString(R.string.GCM_Api_Main)
            isTrue = false
            con = 1
            Log.d("MapsActivity2.TAG", requestUrl)
            from = to
            val jsonObjectRequest =
                JsonObjectRequest(
                    Request.Method.GET,
                    requestUrl, null as String?,
                    Response.Listener { response ->
                        Log.d("MapsActivity2.TAG", response.toString() + "")
                        try {
                            val jsonArray = response.getJSONArray("routes")
                            for (i in 0 until jsonArray.length()) {
                                val route = jsonArray.getJSONObject(i)
                                val poly1 = route.getJSONArray("legs")
                                val poly2 = poly1.getJSONObject(0)
                                val poly3 = poly2.getJSONObject("duration")
                                duration = poly3.getString("text")
                                val poly4 = poly2.getJSONObject("distance")
                                distanceValue = poly4.getString("text")
//                                mapTitlefrom.setText("   " + poly2.getString("start_address"))
//                                mapTitleto.setText("   " + poly2.getString("end_address"))
                                if (distanceValue!!.contains("k")) {
                                    distanceValue = distanceValue!!.replace(" k", "")
                                }
                                val poly = route.getJSONObject("overview_polyline")
                                val polyline = poly.getString("points")
                                if (polyLineList != null) {
                                    polyLineList.orEmpty()
                                }
                                polyLineList = decodePoly(polyline)
                                Log.d(
                                    "MapsActivity2.TAG",
                                    polyLineList.toString() + "otgototj"
                                )
                            }
                            //Adjusting bounds
                            val builder = LatLngBounds.Builder()
                            for (latLng in polyLineList!!) {
                                builder.include(latLng)
                            }
                            val bounds = builder.build()
                            val mCameraUpdate =
                                CameraUpdateFactory.newLatLngBounds(bounds, 2)
                            mMap!!.animateCamera(mCameraUpdate)
                            polylineOptions = PolylineOptions()
                            polylineOptions!!.width(4f)
                            polylineOptions!!.color(Color.GREEN)
                            polylineOptions!!.startCap(SquareCap())
                            polylineOptions!!.endCap(SquareCap())
                            polylineOptions!!.jointType(JointType.ROUND)
                            polylineOptions!!.addAll(polyLineList)
                            greyPolyLine = mMap!!.addPolyline(polylineOptions)
                            blackPolylineOptions = PolylineOptions()
                            blackPolylineOptions!!.width(4f)
                            polylineOptions!!.color(Color.BLUE)
                            blackPolylineOptions!!.startCap(SquareCap())
                            blackPolylineOptions!!.endCap(SquareCap())
                            blackPolylineOptions!!.jointType(JointType.ROUND)
                            blackPolyline = mMap!!.addPolyline(blackPolylineOptions)

                            //                                    if(toVal ==0) {
                            //                                        mMap.addMarker(new MarkerOptions()
                            //                                                .position(polyLineList.get(0)).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_car", 23, 48))));
                            //
                            //                                    }
                            //                                    toVal = 1;
                            val polylineAnimator = ValueAnimator.ofInt(0, 100)
                            polylineAnimator.duration = 1000
                            polylineAnimator.interpolator = LinearInterpolator()
                            polylineAnimator.addUpdateListener { valueAnimator ->
                                val points: List<LatLng> =
                                    greyPolyLine!!.getPoints()
                                Log.v(
                                    "kfkdjfkf",
                                    "knhfdgkhfjghfj   == $points"
                                )
                                val percentValue =
                                    valueAnimator.animatedValue as Int
                                val size = points.size
                                val newPoints = (size * (percentValue / 100.0f)).toInt()
                                Log.v(
                                    "88888 ==$size ==$newPoints",
                                    "fj   == $percentValue"
                                )
                                val p =
                                    points.subList(0, newPoints)
                                blackPolyline!!.setPoints(p)
                            }
                            polylineAnimator.start()

                            //.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_car", 23, 48))));

                            //                                    marker = mMap.addMarker(new MarkerOptions().position(polyLineList.get(0))
                            //                                            .flat(true)
                            //                                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_car", 23, 48))));
                            index = -1
                            next = 1
                            handler!!.postDelayed(mStatusChecker, 3000)
                            carDisplay = 0
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    },
                    Response.ErrorListener { error ->
                        Log.d(
                            "MapsActivity2.TAG",
                            error.toString() + ""
                        )
                    })
            val requestQueue = Volley.newRequestQueue(activity!!)
            requestQueue.add(jsonObjectRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun updateRouteMap() {
        var requestUrl: String? = null
        try {
            requestUrl =
                "https://maps.googleapis.com/maps/api/directions/json?origin=$customerLat&destination=$restLat&key="+getString(R.string.GCM_Api_Main)

            val jsonObjectRequest =
                JsonObjectRequest(
                    Request.Method.GET,
                    requestUrl, null as String?,
                    Response.Listener { response ->

                        Log.d("MapsActivity2.TAG", response.toString() + "")
                        try {
                            val jsonArray = response.getJSONArray("routes")
                            for (i in 0 until jsonArray.length()) {
                                val route = jsonArray.getJSONObject(i)
                                val poly1 = route.getJSONArray("legs")
                                val poly2 = poly1.getJSONObject(0)
                                val poly3 = poly2.getJSONObject("duration")
                                duration1 = poly3.getString("text")
                                val poly4 = poly2.getJSONObject("distance")
                                distanceValue1 = poly4.getString("text")
//                                mapTitlefrom.setText("   " + poly2.getString("start_address"))
//                                mapTitleto.setText("   " + poly2.getString("end_address"))
                                if (distanceValue1!!.contains("k")) {
                                    distanceValue1 = distanceValue1!!.replace(" k", "")
                                }
                                val poly = route.getJSONObject("overview_polyline")
                                val polyline = poly.getString("points")
                                if (polyLineList1 != null) {
                                    polyLineList1.orEmpty()
                                }
                                polyLineList1 = decodePoly1(polyline)
                                Log.d(
                                    "MapsActivity2.TAG",
                                    polyLineList1.toString() + "otgototj"
                                )
                            }
                            //Adjusting bounds
                            val builder = LatLngBounds.Builder()
                            for (latLng in polyLineList1!!) {
                                builder.include(latLng)
                            }
                            val bounds = builder.build()
                            val mCameraUpdate =
                                CameraUpdateFactory.newLatLngBounds(bounds, 2)
                            mMap!!.animateCamera(mCameraUpdate)
                            polylineOptions1 = PolylineOptions()
                            polylineOptions1!!.width(5f)
                            polylineOptions1!!.color(Color.BLUE)
                            polylineOptions1!!.startCap(SquareCap())
                            polylineOptions1!!.endCap(SquareCap())
                            polylineOptions1!!.jointType(JointType.ROUND)
                            polylineOptions1!!.addAll(polyLineList1)
                            greyPolyLine1 = mMap!!.addPolyline(polylineOptions1)
                            blackPolylineOptions1 = PolylineOptions()
                            blackPolylineOptions1!!.width(5f)
                            //polylineOptions!!.color(Color.GREEN)
                            blackPolylineOptions1!!.startCap(SquareCap())
                            blackPolylineOptions1!!.endCap(SquareCap())
                            blackPolylineOptions1!!.jointType(JointType.ROUND)
                            blackPolyline1 = mMap!!.addPolyline(blackPolylineOptions1)

                            //                                    if(toVal ==0) {
                            //                                        mMap.addMarker(new MarkerOptions()
                            //                                                .position(polyLineList.get(0)).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_car", 23, 48))));
                            //
                            //                                    }
                            //                                    toVal = 1;
                            /*val polylineAnimator = ValueAnimator.ofInt(0, 100)
                            polylineAnimator.duration = 1000
                            polylineAnimator.interpolator = LinearInterpolator()
                            polylineAnimator.addUpdateListener { valueAnimator ->
                                val points: List<LatLng> =
                                    greyPolyLine!!.getPoints()
                                Log.v(
                                    "kfkdjfkf",
                                    "knhfdgkhfjghfj   == $points"
                                )
                                val percentValue =
                                    valueAnimator.animatedValue as Int
                                val size = points.size
                                val newPoints = (size * (percentValue / 100.0f)).toInt()
                                Log.v(
                                    "88888 ==$size ==$newPoints",
                                    "fj   == $percentValue"
                                )
                                val p =
                                    points.subList(0, newPoints)
                                blackPolyline!!.setPoints(p)
                            }
                            polylineAnimator.start()
*/
                            //.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_car", 23, 48))));

                            //                                    marker = mMap.addMarker(new MarkerOptions().position(polyLineList.get(0))
                            //                                            .flat(true)
                            //                                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_car", 23, 48))));
                   /*         index = -1
                            next = 1
                            handler!!.postDelayed(mStatusChecker, 3000)
                            carDisplay = 0*/
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    },
                    Response.ErrorListener { error ->
                        Log.d(
                            "MapsActivity2.TAG",
                            error.toString() + ""
                        )
                    })
            val requestQueue = Volley.newRequestQueue(activity!!)
            requestQueue.add(jsonObjectRequest)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    var mStatusChecker: Runnable = object : Runnable {
        override fun run() {
            try {
                hhh()
            } catch (e: java.lang.Exception) {
                Log.e("MapsActivity2.TAG", e.message)
            }
            handler!!.postDelayed(this, 3000)
        }
    }

    fun hhh() {
        if (index < polyLineList!!.size - 1) {
            index++
            next = index + 1
        }
        if (index < polyLineList!!.size - 1) {
            startPosition = polyLineList!![index]
            endPosition = polyLineList!![next]
        }
        //infoLayout.setVisibility(View.VISIBLE);
        if (endPosition != null) {
            if (carDisplay == 0) {
                if (marker != null) {
                    marker!!.remove()
                }
                marker = mMap!!.addMarker(
                    MarkerOptions().position(sydney!!).flat(true)
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("ic_car", 48, 96)))
                )
            }
        }
        carDisplay = 1
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.duration = 3000
        Log.v("f.,f,lfdn$v", "yes")
        //animationDuration = 2+Integer.parseInt(duration)*1000;
        valueAnimator.interpolator = LinearInterpolator()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            valueAnimator.setCurrentFraction(0.0056666667.toFloat())
        }

        //handler.removeCallbacks(mStatusChecker1);
        valueAnimator.addUpdateListener { valueAnimator ->
            if (next < polyLineList!!.size) {
                con = 1
                con2 = 1
                toValStatus = 0
                if (endPosition != null) {
                    //Log.v("f.,f,lfdn" + v, "super");
                    //v = v +(float)0.0056666667;
                    v = valueAnimator.animatedFraction
                    //Log.v("kfkdjfkfhdgjf", "knhfdgkhfjghfj == " + v);
                    lng = v * endPosition!!.longitude + (1 - v) * startPosition!!.longitude
                    lat = v * endPosition!!.latitude + (1 - v) * startPosition!!.latitude
                    val newPos = LatLng(lat, lng)
                    marker!!.setPosition(newPos)
                    marker!!.setAnchor(0.5f, 0.5f)
                    marker!!.setRotation(getBearing(startPosition!!, newPos))
                    mMap!!.moveCamera(
                        CameraUpdateFactory
                            .newCameraPosition(
                                CameraPosition.Builder()
                                    .target(newPos)
                                    .zoom(16.5f)
                                    .build()
                            )
                    )
                } else {
                    //handler.postDelayed(mStatusChecker2, 1500);
                }
            } else {
                updateMap(valueAnimator)
            }
            Log.v("f.,f,lfdn$v", "super")
        }
        valueAnimator.start()
        //handler.postDelayed(this, 5000);
    }

    fun updateMap(valueAnimator: ValueAnimator) {

        //Log.v("htt == ==", "fj   == 3" );
        if (con == 1) {
            handler!!.removeCallbacks(mStatusChecker)
            valueAnimator.cancel()
            con = 0
            Log.v("httk == ==", "fj   == ")
            //handler.postDelayed(mStatusChecker1, 1500);
            valueAnimator.end()
            con = 0
        }
    }

    private fun decodePoly(encoded: String): List<LatLng>? {
        val poly: MutableList<LatLng> = java.util.ArrayList()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val p = LatLng(
                lat.toDouble() / 1E5,
                lng.toDouble() / 1E5
            )
            poly.add(p)
        }
        return poly
    }


    private fun decodePoly1(encoded: String): List<LatLng>? {
        val poly: MutableList<LatLng> = java.util.ArrayList()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val p = LatLng(
                lat.toDouble() / 1E5,
                lng.toDouble() / 1E5
            )
            poly.add(p)
        }
        return poly
    }

    private fun getBearing(begin: LatLng, end: LatLng): Float {
        val lat = Math.abs(begin.latitude - end.latitude)
        val lng = Math.abs(begin.longitude - end.longitude)
        if (begin.latitude < end.latitude && begin.longitude < end.longitude) return Math.toDegrees(
            Math.atan(lng / lat)
        )
            .toFloat() else if (begin.latitude >= end.latitude && begin.longitude < end.longitude) return (90 - Math.toDegrees(
            Math.atan(lng / lat)
        ) + 90).toFloat() else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude) return (Math.toDegrees(
            Math.atan(lng / lat)
        ) + 180).toFloat() else if (begin.latitude < end.latitude && begin.longitude >= end.longitude) return (90 - Math.toDegrees(
            Math.atan(lng / lat)
        ) + 270).toFloat()
        return (-1).toFloat()
    }

    fun resizeMapIcons(iconName: String?, width: Int, height: Int): Bitmap? {
        val imageBitmap = BitmapFactory.decodeResource(
            resources,
            resources.getIdentifier(iconName, "drawable", activity!!.packageName)
        )
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false)
    }

}
