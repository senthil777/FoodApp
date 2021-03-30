package com.lieferin_global.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.SearchPageAdapter
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.AppType
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.places.ui.PlacePicker
import com.lieferin_global.Adapter.AddAddressAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
import com.lieferin_global.Utility.Constant.AddressTXT
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import org.jetbrains.anko.textColor
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

class LocationActivity : AppCompatActivity(), View.OnClickListener, SearchPageAdapter.CallbackDataAdapter,AddAddressAdapter.CallbackDataAdapter,ResponseListener {

    var filterTV: TextView? = null

    var cancel: ImageView? = null

    private val REQUEST_LOCATION = 22

    protected val REQUEST_CHECK_SETTINGS = 1000

    var PICK_LOCATION: Int = 3

    var searchEditText: EditText? = null

    var currentLocationText: TextView? = null

    var addAddressText: TextView? = null

    var adapterProductList: MutableList<ProductList> = ArrayList()

    private var locationManager: LocationManager? = null

    private var searchAdap: SearchPageAdapter? = null

    internal lateinit var productModel: Product

    var loca: MutableList<String> = ArrayList()

    var locationList: RecyclerView? = null

    var addressList: RecyclerView? = null

    var longtitude: String? = ""

    var latitude: String? = ""

    var longtitudeValue: String? = ""

    var latitudeValue: String? = ""

    var searchTextView: TextView? = null

    var startScreen = ""

    var adapterProduct1: MutableList<Product> = ArrayList()

    var pinLocationIcon : ImageView? = null

    var addAddressAdapter: AddAddressAdapter? =null

    var dbHelper : DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_location)

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

        filterTV = findViewById(R.id.filterTV) as TextView

        filterTV!!.typeface = fontStyle(this, "SemiBold")

        cancel = findViewById(R.id.cancel) as ImageView

        cancel!!.setOnClickListener(this)

        pinLocationIcon = findViewById(R.id.pinLocationIcon)

        currentLocationText = findViewById(R.id.currentLocationText) as TextView

        currentLocationText!!.typeface = fontStyle(this, "")

        currentLocationText!!.setOnClickListener(this)

        addAddressText = findViewById(R.id.addAddressText) as TextView

        addAddressText!!.typeface = fontStyle(this, "")

        addAddressText!!.setOnClickListener(this)

        searchTextView = findViewById(R.id.searchTextView) as TextView

        searchTextView!!.setOnClickListener(this)

        searchTextView!!.typeface = fontStyle(this,"")

        searchEditText = findViewById(R.id.searchEditText) as EditText

        searchEditText!!.typeface = fontStyle(this, "Light")

        locationList = findViewById<View>(R.id.locationList) as RecyclerView


        searchAdap = SearchPageAdapter(this, loca as ArrayList<String>)

        locationList!!.setLayoutManager(
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        )

        locationList!!.setAdapter(searchAdap)

        searchAdap!!.setCallback(this)


        searchEditText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.toString().length >= 0) {
                    voll(charSequence.toString(), this@LocationActivity)
                    locationList!!.visibility = View.VISIBLE
                    if (loca == null) {
                    } else {
                        locationList!!.visibility = View.VISIBLE
                        //pickerPoint.setVisibility(View.GONE);
                    }
                    searchAdap!!.notifyDataSetChanged()
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        if(AppType!!.equals("0"))
        {
            currentLocationText!!.textColor = Color.parseColor("#ec272b")

            addAddressText!!.textColor = Color.parseColor("#ec272b")

            pinLocationIcon!!.setColorFilter(
                colorIcon(
                    this,
                    R.color.redColor,
                    R.drawable.location_pin,
                    pinLocationIcon!!
                ),
                PorterDuff.Mode.SRC_ATOP
            )
        }else{
            currentLocationText!!.textColor = Color.parseColor("#7DC77D")

            addAddressText!!.textColor = Color.parseColor("#7DC77D")

            pinLocationIcon!!.setColorFilter(
                colorIcon(
                    this,
                    R.color.colorGreen,
                    R.drawable.location_pin,
                    pinLocationIcon!!
                ),
                PorterDuff.Mode.SRC_ATOP
            )
        }

        addressList = findViewById(R.id.addressList) as RecyclerView

        addAddressAdapter = AddAddressAdapter(this,adapterProduct1)

        addressList!!.setHasFixedSize(true)

        addressList!!.setLayoutManager(
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        )
        addressList!!.isNestedScrollingEnabled = false

        addAddressAdapter!!.setCallback(this)

        addressList!!.setAdapter(addAddressAdapter!!)
        webService()
    }
    fun webService(){
        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        RequestManager.setAddressDetails(this,obj,this)

        loadingScreen(this)
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
                loadingScreenClose(this)
                showToast(this, "Permission Denied")
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

                    gpsLocation()
                    Log.i("location", "All location settings are satisfied.")
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
        loadingScreen(this)
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

        var intent = Intent(this, MapsActivity::class.java)

        intent.putExtra("Page", ""+startScreen)
        intent.putExtra("latitude", ""+latitude)
        intent.putExtra("longitude", ""+longtitude)
        startActivity(intent)



        latitudeValue = ""+latitude

        longtitudeValue= ""+longtitude

        locationManager?.removeUpdates(locationListener)


    }


    override fun onClick(view: View?) {

        when (view!!.id) {
            R.id.cancel -> {

                finish()
            }

            R.id.searchTextView -> {
                startScreen = "Current Location"
                searchTextView!!.visibility = View.GONE
                searchEditText!!.visibility = View.VISIBLE

                searchEditText!!.requestFocus()

                var checkFocus = searchEditText!!.requestFocus();

                Log.i("CheckFocus", "" + checkFocus);
                if (checkFocus == true) {
                    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                }


            }

            R.id.currentLocationText -> {

                //LocationPicker()

                /*var intent = Intent(this, MapsActivity::class.java)

                intent.putExtra("Page","Current Location")
                startActivity(intent)*/

                startScreen = "Current Location"

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
            }

            R.id.addAddressText -> {

                startScreen = "Add Address"

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

                //loadingScreen(this)
            }
        }
    }

    fun LocationPicker() {
        try {
            val intentBuilder = PlacePicker.IntentBuilder()
            val intent = intentBuilder.build(this)
            startActivityForResult(intent, PICK_LOCATION)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_LOCATION) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(data, this)
                val stBuilder = StringBuilder()
                val placename = String.format("%s", place.name)
                val latitude = place.latLng.latitude.toString()
                val longitude = place.latLng.longitude.toString()
                val address = String.format("%s", place.address)

                /*latitudeString = latitude

                longtitudeString = longitude*/
                stBuilder.append("Name: ")
                stBuilder.append(placename)
                stBuilder.append("\n")
                stBuilder.append("Latitude: ")
                stBuilder.append(latitude)
                stBuilder.append("\n")
                stBuilder.append("Logitude: ")
                stBuilder.append(longitude)
                stBuilder.append("\n")
                stBuilder.append("Address: ")
                stBuilder.append(address)

                //registerAddress!!.text = address

                val geocoder = Geocoder(this)
                try {
                    val addresses = geocoder.getFromLocation(
                        place.latLng.latitude,
                        place.latLng.longitude,
                        1
                    )
                    /* registerAddress!!.setText(addresses[0].getAddressLine(0))
                     cityString = addresses[0].subAdminArea

                     stateString = addresses[0].adminArea

                     countryString = addresses[0].countryName

                     streetString = addresses[0].subLocality
                     if (addresses[0].postalCode != null) {
                         pinCode = addresses[0].postalCode
                         Log.d("ZIP CODE", pinCode)
                     }

 */
                    //String country = addresses.get(0).getAddressLine(2);
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        } else if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                Log.i("", "PendrrrrringIntent unable to execute request.")

                gpsLocation()
            }
        }
    }

    fun vollAddress(input: String, context: Context?) {
        val queue = Volley.newRequestQueue(context)
        val myArray = arrayOfNulls<String>(1)
        val sr: StringRequest = object : StringRequest(
            Method.GET,
            "https://maps.googleapis.com/maps/api/geocode/json?address=" + input + "&sensor=false&key="+getString(R.string.GCM_Api_Main),
            Response.Listener { response ->
                Log.v("HttpClient", "https://maps.googleapis.com/maps/api/geocode/json?address="+input+"&sensor=false&key="+getString(R.string.GCM_Api_Main))
                Log.e("HttpClient", "success! response: $response")
                try { // Create a JSON object hierarchy from the results
                    val jsonObj = JSONObject(response)
                    val predsJsonArray = jsonObj.getJSONArray("results")
                    // Extract the Place descriptions from the results
                    if (loca != null) {
                        if (loca.size != 0) {
                            loca.clear()
                        }
                    }
                    for (i in 0 until predsJsonArray.length()) {
                        println(
                            predsJsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject(
                                "location"
                            ).getString("lat")
                        );
                        println("============================================================")
                        println(
                            predsJsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject(
                                "location"
                            ).getString("lng")
                        );
                        println("============================================================")
                        ;

                        longtitude =
                            "" + predsJsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject(
                                "location"
                            ).getString("lng")

                        latitude =
                            "" + predsJsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject(
                                "location"
                            ).getString("lat")

                        var intent = Intent(this, MapsActivity::class.java)

                        intent.putExtra("Page", ""+startScreen)
                        intent.putExtra("latitude", ""+latitude)
                        intent.putExtra("longitude", ""+longtitude)
                        startActivity(intent)
                        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()!!.getWindowToken(), 0)
                        finish()

                    }
                } catch (e: JSONException) {
                    //Log.e(LOG_TAG!!, "Cannot process JSON results", e)
                }
            },
            Response.ErrorListener { error -> Log.e("HttpClient", "error: $error") }) {
            override fun getParams(): Map<String, String> {
                return HashMap()
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Content-Type"] = "application/x-www-form-urlencoded"
                return params
            }
        }
        queue.add(sr)
    }

    fun voll(input: String, context: Context?) {
        val queue = Volley.newRequestQueue(context)
        val myArray = arrayOfNulls<String>(1)
        val sr: StringRequest = object : StringRequest(
            Method.GET,
            "https://maps.googleapis.com/maps/api/place/autocomplete/json?key="+getString(R.string.GCM_Api_Main)+"&input=$input",
            Response.Listener { response ->
                Log.v("HttpClient", "https://maps.googleapis.com/maps/api/geocode/json?address="+input+"&sensor=false&key="+getString(R.string.GCM_Api_Main))
                Log.e("HttpClient", "success! response: $response")
                try { // Create a JSON object hierarchy from the results
                    val jsonObj = JSONObject(response)
                    val predsJsonArray = jsonObj.getJSONArray("predictions")
                    // Extract the Place descriptions from the results
                    if (loca != null) {
                        if (loca.size != 0) {
                            loca.clear()
                        }
                    }
                    for (i in 0 until predsJsonArray.length()) {
                        println(predsJsonArray.getJSONObject(i).getString("description"))
                        println("============================================================")
                        loca.add(predsJsonArray.getJSONObject(i).getString("description"))
                        searchAdap!!.notifyDataSetChanged()
                        //myArrayValue = myArrayValue + "-" + predsJsonArray.getJSONObject(i).getString("description")

                        Log.v("Enter", "jj" + loca[0]);
                    }
                } catch (e: JSONException) {
                    //Log.e(LOG_TAG!!, "Cannot process JSON results", e)
                }
            },
            Response.ErrorListener { error -> Log.e("HttpClient", "error: $error") }) {
            override fun getParams(): Map<String, String> {
                return HashMap()
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Content-Type"] = "application/x-www-form-urlencoded"
                return params
            }
        }
        queue.add(sr)
    }


    override fun setOnMaterial(price: String?) {
        vollAddress(price!!, this)
        //searchEdit!!.setText(price)
        //startActivity(Intent(this, DashBoardActivity::class.java))
    }

    override fun setOnAddress(id: String?, pincode: String?,longtitude: String?,latitude : String?,address : String?) {
        addAddressAdapter!!.notifyDataSetChanged()

        AddressTXT = ""+address

        if(AppType.equals("0")) {
            var intent = Intent(this, DashBoardActivity::class.java)

            intent.putExtra("LocationText", "" + address)

            intent.putExtra("latitude", "" + latitude)

            intent.putExtra("longtitude", "" + longtitude)

            startActivity(intent)

            Constant.latitudeAdd = "" + latitude

            Constant.longtitudeAdd = "" + longtitude
        }else{
            var intent = Intent(this, DashBoardGrpceryActivity::class.java)

            intent.putExtra("LocationText", "" + address)

            intent.putExtra("latitude", "" + latitude)

            intent.putExtra("longtitude", "" + longtitude)

            startActivity(intent)

            Constant.latitudeAdd = "" + latitude

            Constant.longtitudeAdd = "" + longtitude
        }
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
                }

            }else if (requestType == Constant.MEMBER_deletCustomerAddress_URL_RQ) {
                if((responseObj as BaseRS).status.equals("5025"))
                {
                    showToast(this,(responseObj as BaseRS).message)
                    webService()
                }
            }
        }
    }
}
