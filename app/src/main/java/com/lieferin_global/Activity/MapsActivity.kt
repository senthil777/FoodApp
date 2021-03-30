package com.lieferin_global.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.Constant.latitudeAdd
import com.lieferin_global.Utility.Constant.longtitudeAdd
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
import com.lieferin_global.Utility.Constant.AddressTXT
import org.jetbrains.anko.textColor
import java.lang.reflect.InvocationTargetException
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,  View.OnClickListener,
    ResponseListener,
    GoogleMap.OnMarkerDragListener, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener,
    GoogleMap.OnCameraMoveListener,GoogleMap.OnCameraIdleListener {

    private lateinit var mMap: GoogleMap

    var addressSubDescriptionTV : TextView? = null

    var locationAddressTV : TextView? = null

    var changeText : TextView? = null

    var continueTxt : TextView? = null

    var addAddress_back : ImageView? = null

    var addressValueTV: TextView? = null

    var addressET: EditText? = null

    private val REQUEST_LOCATION = 22

    var PICK_LOCATION:Int = 3

    protected val REQUEST_CHECK_SETTINGS = 1000

    var latitudeString : Double? = 0.0

    var longtitudeString : Double? = 0.0

    private var locationManager : LocationManager? = null

    private var toolbar: Toolbar? = null

    var geocoder: Geocoder? = null
    var addresses: MutableList<Address> = java.util.ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

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

        continueTxt = findViewById(R.id.continueTxt) as TextView

        val extras = intent.extras
        val userName: String?
        if (extras != null) {

            if (extras.getString("Page") != null) {

                if(extras.getString("Page").equals("Current Location"))
                {

                    continueTxt!!.text = ""+getString(R.string.continueTxtConfirm)

                }else{
                    continueTxt!!.text = ""+getString(R.string.continueTxtConfirmProceed)
                }

                if(!extras.getString("latitude").equals(""))
                {
                    var lat =extras.getString("latitude")
                    latitudeString = lat!!.toDouble()

                    //latitudeString = 52.422271

                }
                if(!extras.getString("longitude").equals(""))
                {
                    var longtitude =extras.getString("longitude")
                    longtitudeString= longtitude!!.toDouble()

                    //longtitudeString =13.411581
                }
            }
        }
        addAddress_back = findViewById(R.id.addAddress_back) as ImageView

        geocoder = Geocoder(this, Locale.getDefault())

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        addAddress_back!!.setOnClickListener(this)

        addAddress_back!!.setColorFilter(
            colorIcon(this,R.color.colorWhite,R.drawable.abc_ic_ab_back_material,addAddress_back!!),
            PorterDuff.Mode.SRC_ATOP)


        addressValueTV = findViewById(R.id.addressValueTV) as TextView

        addressValueTV!!.typeface = fontStyle(this,"")

        addressSubDescriptionTV = findViewById(R.id.addressSubDescriptionTV) as TextView

        addressSubDescriptionTV!!.typeface = fontStyle(this,"SemiBold")

        locationAddressTV = findViewById(R.id.locationAddressTV) as TextView

        locationAddressTV!!.typeface = fontStyle(this,"")

        changeText = findViewById(R.id.changeText) as TextView

        changeText!!.typeface = fontStyle(this,"Light")

        changeText!!.setOnClickListener(this)

        continueTxt!!.typeface = fontStyle(this,"SemiBold")

        continueTxt!!.setOnClickListener(this)


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

        if(AppType!!.equals("0"))
        {
            changeText!!.textColor = Color.parseColor("#ec272b")

            continueTxt!!.setBackgroundResource(R.drawable.home_radius_button_orange)
        }else{
            changeText!!.textColor = Color.parseColor("#7DC77D")

            continueTxt!!.setBackgroundResource(R.drawable.home_radius_button_green)
        }

    }
    private fun displayLocationSettingsRequest(context: Context) {
        val googleApiClient = GoogleApiClient.Builder(context)
            .addApi(LocationServices.API).build()
        googleApiClient.connect()
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 1000
        locationRequest.fastestInterval = 10000 / 2.toLong()

        Log.i("33333", "PendingIntent unable to execute request.")
        val builder =
            LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(false)

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
    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                Log.i("", "PendrrrrringIntent unable to execute request.")

                gpsLocation()
            }
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            //thetext.text = ("" + location.longitude + ":" + location.latitude)

            //addText(getCompleteAddressString(this@DashBoardGrpceryActivity,location.latitude,location.longitude)!!)

            Constant.latitudeAdd = "" + location.latitude

            Constant.longtitudeAdd = "" + location.longitude
          try{
            /*addresses = geocoder!!.getFromLocation(
                location.latitude,
                location.longitude,
                1
            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5*/
        } catch (ex: InvocationTargetException) {

        }
            //mMap = googleMap

            // Add a marker in Sydney and move the camera
           /* longtitudeString = location.longitude

            latitudeString = location.latitude

            Log.d("End___lllll"+latitudeString, "kkkkkk"+longtitudeString);*/

            //mapView()

        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    fun mapView()
    {
        val sydney = LatLng(latitudeString!!.toDouble(),longtitudeString!!.toDouble())

        val height = 92
        val width = 92
        val b: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.google_map)
        val smallMarker: Bitmap = Bitmap.createScaledBitmap(b, width, height, false)
        val smallMarkerIcon: BitmapDescriptor = BitmapDescriptorFactory.fromBitmap(smallMarker)


        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney!!, 18F),1000,null );

        val address: String = addresses[0].getAddressLine(0)

        //addressSubDescriptionTV!!.text = "" +address

        locationAddressTV!!.setText(""+addresses[0].locality!!)
        Log.v("Address",""+address)

        if(locationManager != null) {
            locationManager!!.removeUpdates(locationListener)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(latitudeString!!,longtitudeString!!)

        /*val height = 92
        val width = 92
        val b: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.google_map)
        val smallMarker: Bitmap = Bitmap.createScaledBitmap(b, width, height, false)
        val smallMarkerIcon: BitmapDescriptor = BitmapDescriptorFactory.fromBitmap(smallMarker)


*/
        Log.d("====lllll"+longtitudeString, "kkkkkk"+latitudeString);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney!!, 16F),1000,null );
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

            R.id.changeText->{
                var intent = Intent(this, LocationActivity::class.java)
                startActivity(intent)
            }
            R.id.addressChangeTV -> {

                AddressTXT = addressSubDescriptionTV!!.text.toString()

                if(AppType.equals("0")) {
                    var intent = Intent(this, DashBoardActivity::class.java)

                    intent.putExtra("LocationText", "" + addressSubDescriptionTV!!.text.toString())

                    intent.putExtra("latitude", "" + latitudeString)

                    intent.putExtra("longtitude", "" + latitudeString)

                    startActivity(intent)

                    if(locationManager != null) {
                        locationManager!!.removeUpdates(locationListener)
                    }
                }else{
                    var intent = Intent(this, DashBoardGrpceryActivity::class.java)

                    intent.putExtra("LocationText", "" + addressSubDescriptionTV!!.text.toString())

                    intent.putExtra("latitude", "" + latitudeString)

                    intent.putExtra("longtitude", "" + latitudeString)

                    startActivity(intent)

                    if(locationManager != null) {
                        locationManager!!.removeUpdates(locationListener)
                    }
                }
            }

            R.id.continueTxt -> {
            if(continueTxt!!.text.toString().equals(""+getString(R.string.continueTxtConfirm)))
            {
                AddressTXT = locationAddressTV!!.text.toString()
                if(AppType.equals("0")) {

                    var intent = Intent(this, DashBoardActivity::class.java)

                    intent.putExtra("LocationText", "" + locationAddressTV!!.text.toString())

                    intent.putExtra("latitude", "" + latitudeString)

                    intent.putExtra("longtitude", "" + longtitudeString)

                    startActivity(intent)

                    latitudeAdd = "" + latitudeString

                    longtitudeAdd = "" + longtitudeString

                    finish()

                    if(locationManager != null) {
                        locationManager!!.removeUpdates(locationListener)
                    }
                }else{
                    var intent = Intent(this, DashBoardGrpceryActivity::class.java)

                    intent.putExtra("LocationText", "" + locationAddressTV!!.text.toString())

                    intent.putExtra("latitude", "" + latitudeString)

                    intent.putExtra("longtitude", "" + longtitudeString)

                    startActivity(intent)

                    latitudeAdd = "" + latitudeString

                    longtitudeAdd = "" + longtitudeString

                    finish()

                    if(locationManager != null) {
                        locationManager!!.removeUpdates(locationListener)
                    }
                }
            }else{
                var intent = Intent(this, AddAddressActivity::class.java)

                intent.putExtra("screen","1")

                intent.putExtra("latitude",""+latitudeString)

                intent.putExtra("longtitude",""+longtitudeString)

                startActivity(intent)

                if(locationManager != null) {
                    locationManager!!.removeUpdates(locationListener)
                }

                latitudeAdd = ""+latitudeString

                longtitudeAdd = ""+longtitudeString
            }

                /*if(addresses!!.size == 0)
                {
                    showToast(this,"Please enter address")

                }else {
                    val obj = JSONObject()
                    obj.put("token", "" + localgetUserInfo("token"))
                    obj.put("address", "" + addresses[0].getAddressLine(0))
                    obj.put("street", "" + addresses[0].locality)
                    obj.put("city", "" + addresses[0].subAdminArea)
                    obj.put("state", "" + addresses[0].adminArea)
                    obj.put("country", "" + addresses[0].countryName)
                    obj.put("postcode", "" + addresses[0].postalCode)
                    obj.put("latitude", "" + latitudeString)
                    obj.put("longitude", "" + longtitudeString)
                    RequestManager.setAddressInsert(this,obj,this)
                }
*/


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


    override fun onResponseReceived(responseObj: Any?, requestType: Int) {

        if(responseObj != null) {
            if (requestType == Constant.MEMBER_customerAddressInsert_URL_RQ) {

                if((responseObj as BaseRS).status.equals("5018"))
                {
                    showToast(this,(responseObj as BaseRS).message)

                    startActivity(Intent(this,OrderDetailsActivity::class.java))

                    if(locationManager != null) {
                        locationManager!!.removeUpdates(locationListener)
                    }
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

        /*var center = mMap!!.getCameraPosition().target;
        Log.d("====lllll"+center.latitude, "kkkkkk");*/

        if(latitudeString != 0.0) {

            //locationManager?.removeUpdates(locationListener!!)
        }

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
        if(latitudeString != 0.0) {
        var center = mMap!!.getCameraPosition().target;
        Log.d("End___lllll1"+latitudeString, "kkkkkk"+longtitudeString);

            try{

        /*addresses = geocoder!!.getFromLocation(
            center.latitude,
            center.longitude,
            1
        )*/

        latitudeString =center.latitude

        longtitudeString =center.longitude
        //addressSubDescriptionTV!!.text =addresses[0].getAddressLine(0);



            locationAddressTV!!.text = getCompleteAddressString(this@MapsActivity,center.latitude,center.longitude)

        } catch (ex: InvocationTargetException) {

        }
        }
    }


}
