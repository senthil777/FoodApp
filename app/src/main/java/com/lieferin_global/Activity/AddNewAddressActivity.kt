package com.lieferin_global.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PorterDuff
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.lieferin_global.Fragment.AddressFragment
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.material.tabs.TabLayout
import com.lieferin_global.LocalDataBase.DBHelper
import kotlinx.android.synthetic.main.activity_order_bill_details.view.*
import org.json.JSONObject
import java.lang.reflect.InvocationTargetException
import java.util.*
import kotlin.collections.ArrayList

class AddNewAddressActivity : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener,ResponseListener,
    GoogleMap.OnMarkerDragListener, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener,
    GoogleMap.OnCameraMoveListener,GoogleMap.OnCameraIdleListener {

    private lateinit var mMap: GoogleMap

    var addAddress_Title : TextView? = null

    var addAddress_Type : TextView? = null

    var addAddress_back : ImageView? = null

    var addressValueTV: TextView? = null

    var addressChangeTV: TextView? = null

    var addressSubDescriptionTV: TextView? = null

    var continueTxt: TextView? = null

    var addressET: EditText? = null

    var landMarkET: EditText? = null

    var tabLayout: TabLayout? = null

    var viewPager: ViewPager? = null

    private val REQUEST_LOCATION = 22

    var PICK_LOCATION:Int = 3

    protected val REQUEST_CHECK_SETTINGS = 1000

    var latitudeString : String? = null

    var longtitudeString : String? = null

    private var locationManager : LocationManager? = null

    private var locationManager1 : LocationManager? = null

    private var toolbar: Toolbar? = null

    var geocoder: Geocoder? = null
    var addresses: MutableList<Address> = java.util.ArrayList()

    var dbHelper : DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_address2)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

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

        addAddress_back = findViewById(R.id.addAddress_back) as ImageView

        geocoder = Geocoder(this, Locale.getDefault())

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        addAddress_back!!.setOnClickListener(this)

        addAddress_back!!.setColorFilter(
            colorIcon(this,R.color.colorBlack,R.drawable.abc_ic_ab_back_material,addAddress_back!!),
            PorterDuff.Mode.SRC_ATOP)

        addAddress_Title = findViewById(R.id.addAddress_Title)

        addAddress_Title!!.typeface = fontStyle(this,"")

        addAddress_Type = findViewById(R.id.addAddress_Type)

        addAddress_Type!!.typeface = fontStyle(this,"Light")

        addressValueTV = findViewById(R.id.addressValueTV) as TextView

        addressValueTV!!.typeface = fontStyle(this,"Bold")

        addressChangeTV = findViewById(R.id.addressChangeTV) as TextView

        addressChangeTV!!.typeface = fontStyle(this,"SemiBold")

        addressChangeTV!!.setOnClickListener(this)

        continueTxt = findViewById(R.id.continueTxt) as TextView

        continueTxt!!.typeface = fontStyle(this,"SemiBold")

        continueTxt!!.setOnClickListener(this)

        addressSubDescriptionTV= findViewById(R.id.addressSubDescriptionTV) as TextView

        addressSubDescriptionTV!!.typeface = fontStyle(this,"Light")

        addressET = findViewById(R.id.addressET) as EditText

        addressET!!.typeface = fontStyle(this,"")

        landMarkET = findViewById(R.id.landMarkET) as EditText

        landMarkET!!.typeface = fontStyle(this,"")


        viewPager = findViewById(R.id.addressViewpager) as ViewPager
        setupViewPager(viewPager!!);

        tabLayout = findViewById(R.id.addressTabs) as TabLayout
        tabLayout!!.setupWithViewPager(viewPager);

        val slidingTabStrip = tabLayout!!.getChildAt(0) as ViewGroup

        val tabsCount: Int = slidingTabStrip.getChildCount()

        for (j in 0 until tabsCount) {
            val vgTab = slidingTabStrip.getChildAt(j) as ViewGroup
            val tabChildsCount = vgTab.childCount
            for (i in 0 until tabChildsCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) { //Put your font in assests folder
//assign name of the font here (Must be case sensitive)
                    tabViewChild.setTypeface(
                        fontStyle(
                            this,
                            ""
                        )
                    )
                }
            }
        }

       /* val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)*/

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
        locationManager1 = getSystemService(LOCATION_SERVICE) as LocationManager?
        //getLocation()
    }


    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this@AddNewAddressActivity, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@AddNewAddressActivity, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {
            val locationGPS =
                locationManager1!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (locationGPS != null) {
                val lat = locationGPS.latitude
                val longi = locationGPS.longitude
                /*Toast.makeText(this, "Your Location: \n" +
                        "Latitude: $lat\n" +
                        "Longitude:$longi", Toast.LENGTH_SHORT).show()*/
                //showLocation.setText("Your Location: \nLatitude: $lat\nLongitude: $longitude")

                //getCompleteAddressString(this@DashBoardActivity,locationGPS.latitude,locationGPS.longitude)

                //Log.i("Location","=="+getCompleteAddressString(this@DashBoardActivity,locationGPS.latitude,locationGPS.longitude))
                val geocoder: Geocoder
                val yourAddresses: List<Address>
                geocoder = Geocoder(this, Locale.getDefault())
                yourAddresses = geocoder.getFromLocation(lat, longi, 1)

                if (yourAddresses.size > 0) {
                    val yourAddress = yourAddresses[0].getAddressLine(0)
                    val yourCity = yourAddresses[0].getAddressLine(1)
                    val yourCountry = yourAddresses[0].getAddressLine(2)
                    Constant.latitudeAdd = "" + lat

                    Constant.longtitudeAdd = "" + longi


                    //showToast(c,"dddd")

                    //addText(yourAddress)
                    Log.i("Location","=="+yourAddress)
                    Toast.makeText(this, yourAddress, Toast.LENGTH_LONG).show()
                }


            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show()
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

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {

            REQUEST_LOCATION -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                displayLocationSettingsRequest(this)
            } else {
                showToast(this, "Permission Denied")
            }
        }
    }

    @SuppressLint("ServiceCast", "MissingPermission")
    fun gpsLocation() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        locationManager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0f, locationListener)


    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            //thetext.text = ("" + location.longitude + ":" + location.latitude)

            longtitudeString = ""+location.longitude

            latitudeString = ""+location.latitude

            val sydney = LatLng(location.latitude,location.longitude)
/*
            val height = 92
            val width = 92
            val b: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.google_map)
            val smallMarker: Bitmap = Bitmap.createScaledBitmap(b, width, height, false)
            val smallMarkerIcon: BitmapDescriptor = BitmapDescriptorFactory.fromBitmap(smallMarker)

            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney")).setIcon(
                smallMarkerIcon)*/
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney!!, 14F),1000,null );
            val address: String = getCompleteAddressString(this@AddNewAddressActivity,location.latitude,location.longitude)!!

            addressSubDescriptionTV!!.text = "" +address

            addressET!!.setText(""+getCompleteAddressString(this@AddNewAddressActivity,location.latitude,location.longitude)!!)



      try{

            /*addresses = geocoder!!.getFromLocation(
                location.latitude,
                location.longitude,
                1
            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            //mMap = googleMap

            // Add a marker in Sydney and move the camera


            Log.v("Address",""+address)*/
} catch (ex: InvocationTargetException) {

}
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(11.0214677,76.9649095)

        val height = 92
        val width = 92
        val b: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.google_map)
        val smallMarker: Bitmap = Bitmap.createScaledBitmap(b, width, height, false)
        val smallMarkerIcon: BitmapDescriptor = BitmapDescriptorFactory.fromBitmap(smallMarker)

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney!!, 14F),1000,null );

        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);

        mMap.setOnMarkerDragListener(this)

        mMap.setOnCameraMoveListener(this)

        mMap.setOnCameraIdleListener(this)

        /*mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney").draggable(true)).setIcon(
            smallMarkerIcon)
*/
        /*mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragEnd(p0: Marker?) {
                if (p0 != null) {
                    val id: String = p0.tag as String
                    //MapViewPlugin.annotationDragEnd(id, p0.position)
                }
            }

            override fun onMarkerDragStart(p0: Marker?) {
                if (p0 != null) {
                    val id: String = p0.tag as String
                    //MapViewPlugin.annotationDragStart(id, p0.position)
                }
            }

            override fun onMarkerDrag(p0: Marker?) {
                if (p0 != null) {
                    val id: String = p0.tag as String
                    //MapViewPlugin.annotationDrag(id, p0.position)

                    Log.v("lllll", "''''"+String.format("Drag from %f:%f"))
                }
            }
        })

*/
    }

    override fun onClick(view: View?) {

        when(view!!.id)
        {

            R.id.addAddress_back->{
                finish()
            }
            R.id.addressChangeTV -> {

                startActivity(Intent(this,AddressSearch::class.java))
            }

            R.id.continueTxt -> {

                if(addresses!!.size == 0)
                {
                    showToast(this,getString(R.string.pleaseEnterAddress))

                }else {
                    val obj = JSONObject()
                    obj.put("token", "" + dbHelper!!.getUserDetails().token)
                    obj.put("address", "" + addresses[0].getAddressLine(0))
                    obj.put("street", "" + addresses[0].locality)
                    obj.put("city", "" + addresses[0].subAdminArea)
                    obj.put("state", "" + addresses[0].adminArea)
                    obj.put("country", "" + addresses[0].countryName)
                    obj.put("postcode", "" + addresses[0].postalCode)
                    obj.put("latitude", "" + latitudeString)
                    obj.put("longitude", "" + longtitudeString)
                    obj.put("completeAddress", "" + addressET!!.text.toString())
                    obj.put("floor", "" + landMarkET!!.text.toString())
                    obj.put("description", "")
                    obj.put("addressType", "1" )
                    RequestManager.setAddressInsert(this,obj,this)
                }



                //startActivity(Intent(this,OrderDetailsActivity::class.java))
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


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(AddressFragment(), getString(R.string.homeText))

        adapter.addFragment(AddressFragment(), getString(R.string.workText))

        adapter.addFragment(AddressFragment(), getString(R.string.otherText))

        viewPager.adapter = adapter
    }

    
    internal class ViewPagerAdapter(manager: FragmentManager?) :
        FragmentPagerAdapter(manager!!) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {

        if(responseObj != null) {
            if (requestType == Constant.MEMBER_customerAddressInsert_URL_RQ) {

                if((responseObj as BaseRS).status.equals("5018"))
                {
                    showToast(this,(responseObj as BaseRS).message)

                    startActivity(Intent(this,OrderDetailsActivity::class.java))
                }
            }
        }

    }

    override fun onMarkerDragEnd(marker: Marker?) {
        var position = marker!!.position

        Log.d("lllll", String.format("Drag from %f:%f",
            position.latitude,
            position.longitude));
    }

    override fun onMarkerDragStart(marker: Marker?) {

        var position = marker!!.position

    Log.d("lllll", String.format("Drag from %f:%f",
            position.latitude,
            position.longitude));
    }

    override fun onMarkerDrag(marker: Marker?) {

        var position = marker!!.position

        Log.d("lllll", String.format("Drag from %f:%f",
            position.latitude,
            position.longitude));

    }

    override fun onMapClick(marker: LatLng?) {

        //var position = marker!!.latitude
        getCompleteAddressString(this@AddNewAddressActivity,marker!!.latitude,marker!!.longitude)!!
    try{
        /*addresses = geocoder!!.getFromLocation(
            marker!!.latitude,
            marker!!.longitude,
            1
        )

        Log.d("lllll",
            ""+addresses[0].getAddressLine(0));*/

    } catch (ex: InvocationTargetException) {

    }
    }

    override fun onMapLongClick(marker: LatLng?) {
        Log.d("lllll", String.format("Drag from %f:%f",
            marker!!.latitude,
            marker!!.longitude));
    }

    override fun onCameraMove() {

        var center = mMap!!.getCameraPosition().target;
        Log.d("====lllll"+center.latitude, "kkkkkk");

        /*addresses = geocoder!!.getFromLocation(
            center.latitude,
            center.longitude,
            1
        )*/
        /*addressSubDescriptionTV!!.text =addresses[0].getAddressLine(0);

        addressValueTV!!.text =addresses[0].subLocality;
*/


    }

    /*override fun onCameraMoveCanceled() {


    }*/

    override fun onCameraIdle() {
        var center = mMap!!.getCameraPosition().target;
        Log.d("End___lllll"+center.latitude, "kkkkkk");
        addressSubDescriptionTV!!.text = getCompleteAddressString(this@AddNewAddressActivity,center!!.latitude,center!!.longitude)!!

        addressValueTV!!.text =getCompleteAddressLocation(this@AddNewAddressActivity,center!!.latitude,center!!.longitude)!!
        try {

        /*addresses = geocoder!!.getFromLocation(
           center.latitude,
           center.longitude,
           1
       )*/
        //addressSubDescriptionTV!!.text =addresses[0].getAddressLine(0);

        addressValueTV!!.text =getCompleteAddressLocation(this@AddNewAddressActivity,center!!.latitude,center!!.longitude)!!
        } catch (ex: InvocationTargetException) {

        }
    }


}
