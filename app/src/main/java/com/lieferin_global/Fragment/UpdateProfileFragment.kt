package com.lieferin_global.Fragment

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
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
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Activity.DashBoardActivity
import com.lieferin_global.Activity.MapsInnerActivity
import com.lieferin_global.LocalDataBase.DBHelper

import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.AppUpdate
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
import com.lieferin_global.Adapter.CountryAdapter
import com.lieferin_global.Adapter.LanguageAdapter
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import org.json.JSONObject
import java.util.*

class UpdateProfileFragment : AppCompatActivity(),View.OnClickListener, ResponseListener,
    CountryAdapter.CallbackDataAdapter {

    var rootView : View? = null

    var total_value:String = ""

    var itemValue : String = ""

    var longtitudeValue: String? = ""

    var latitudeValue: String? = ""

    private var locationManager: LocationManager? = null

    var registerName: EditText? = null

    var mobileNumber: EditText? = null

    private val REQUEST_LOCATION = 22

    protected val REQUEST_CHECK_SETTINGS = 1000

    //var PICK_LOCATION: Int = 3

    var registerEmail: EditText? = null

    var registerPassword: EditText? = null

    var registerConfirmPassword: EditText? = null

    var registerAddress: TextView? = null

    var registerAccountTV: TextView? = null

    var registerButton: TextView? = null

    var userImage : ImageView? = null

    var mobileImg : ImageView? = null

    var emailImg : ImageView? = null

    var passwordImg : ImageView? = null

    var confirmPasswordImg : ImageView? = null

    var addressImg : ImageView? = null

    var signUPLB : TextView? = null

    var latitudeString : String? = null

    var longtitudeString : String? = null

    var cityString : String? = null

    var streetString : String? = null

    var stateString : String? = null

    var countryString : String? = null

    var countryCodeString: String? = ""

    var pinCode : String? = null

    var PICK_LOCATION:Int = 3

    var dbHelper:DBHelper? = null


    var countryCodeRL : RelativeLayout? =null

    var countryCodeTV : TextView? = null

    var adapterDetails: MutableList<AdapterModel> = ArrayList()

    var adapterDetailChild: MutableList<Product> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    var languageAdapter: CountryAdapter? = null

    var countryCode: String? = null

    var registerTick : ImageView? = null

    var mobileTick : ImageView? = null

    var emailTick : ImageView? = null

    var mobileLength = 0

    var addressTick : ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.fragment_upate_profile)



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

        signUPLB = findViewById(R.id.signUPLB ) as TextView

        signUPLB!!.typeface = fontStyle(this,"SemiBold")

        registerName = findViewById(R.id.registerName) as EditText

        registerName!!.typeface = fontStyle(this,"")

        registerName!!.setText(dbHelper!!.getUserDetails().name)

        mobileNumber = findViewById(R.id.mobileNumber) as EditText

        mobileNumber!!.typeface = fontStyle(this,"")

        val str = ""+dbHelper!!.getUserDetails().mobile
        val arrOfStr = str!!.split("-").toTypedArray()

        Log.v("mobile","=="+str)


        if(str.contains("-")) {

            mobileNumber!!.setText(arrOfStr[1].toString())
        }else{
            mobileNumber!!.setText(str)
        }

        registerEmail = findViewById(R.id.registerEmail) as EditText

        registerEmail!!.typeface = fontStyle(this,"")

        registerEmail!!.setText(dbHelper!!.getUserDetails().email)

        registerPassword = findViewById(R.id.registerPassword) as EditText

        registerPassword!!.typeface = fontStyle(this,"")

        registerConfirmPassword = findViewById(R.id.registerConfirmPassword) as EditText

        registerConfirmPassword!!.typeface = fontStyle(this,"")

        registerAddress = findViewById(R.id.registerAddress) as TextView

        registerAddress!!.setOnClickListener(this)

        registerAddress!!.typeface = fontStyle(this,"")

        registerAddress!!.setText(dbHelper!!.getUserDetails().address)

        registerAccountTV = findViewById(R.id.registerAccountTV) as TextView

        registerAccountTV!!.text = customTextColor(this,getString(R.string.register_account),22,"","SemiBold")

        registerAccountTV!!.setOnClickListener(this)

        registerButton = findViewById(R.id.registerButton) as TextView

        registerButton!!.typeface = fontStyle(this,"SemiBold")

        registerButton!!.setOnClickListener(this)

        userImage= findViewById(R.id.userImg) as ImageView

        userImage!!.setColorFilter(
            colorIcon(this,R.color.colorWhite,R.drawable.restaurant_logo,userImage!!),
            PorterDuff.Mode.SRC_ATOP)

        mobileImg= findViewById(R.id.mobileImg) as ImageView

        mobileImg!!.setColorFilter(
            colorIcon(this,R.color.colorWhite,R.drawable.smartphone,mobileImg!!),
            PorterDuff.Mode.SRC_ATOP)

        emailImg= findViewById(R.id.emailImg) as ImageView

        emailImg!!.setColorFilter(
            colorIcon(this,R.color.colorWhite,R.drawable.email,emailImg!!),
            PorterDuff.Mode.SRC_ATOP)

        passwordImg= findViewById(R.id.passwordImg) as ImageView

        passwordImg!!.setColorFilter(
            colorIcon(this,R.color.colorWhite,R.drawable.password,passwordImg!!),
            PorterDuff.Mode.SRC_ATOP)

        confirmPasswordImg= findViewById(R.id.confirmPasswordImg) as ImageView

        confirmPasswordImg!!.setColorFilter(
            colorIcon(this,R.color.colorWhite,R.drawable.password,confirmPasswordImg!!),
            PorterDuff.Mode.SRC_ATOP)

        addressImg= findViewById(R.id.addressImg) as ImageView

        addressImg!!.setColorFilter(
            colorIcon(this,R.color.colorWhite,R.drawable.location,addressImg!!),
            PorterDuff.Mode.SRC_ATOP)

        var extras = intent.extras

        var value:String =""
        if (extras != null) {
            registerAddress!!.text = extras.getString("LocationText").toString();

            latitudeString = extras.getString("latitude").toString();

            longtitudeString = extras.getString("longtitude").toString();
        }

        countryCodeTV = findViewById(R.id.countryCodeTV) as TextView

        countryCodeTV!!.typeface = fontStyle(this,"")

        countryCodeRL = findViewById(R.id.countryCodeRL) as RelativeLayout

        countryCodeRL!!.setOnClickListener(this)

        registerTick = findViewById(R.id.registerTick)

        registerTick!!.visibility = View.GONE

        mobileTick = findViewById(R.id.mobileTick)

        mobileTick!!.visibility = View.GONE

        emailTick = findViewById(R.id.emailTick)

        emailTick!!.visibility = View.GONE

        addressTick = findViewById(R.id.addressTick)



        if(registerAddress!!.text.equals(""))
        {
            addressTick!!.visibility = View.GONE
        }else{
            addressTick!!.visibility = View.VISIBLE
        }

        registerName!!.addTextChangedListener(editor)

        mobileNumber!!.addTextChangedListener(mobileEdit)

        registerEmail!!.addTextChangedListener(emailEdit)

        flagFind(arrOfStr[0])
        userName()
        mobileNumber()
        emailValidation()


    }
    private  val editor = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

            if(p0.toString().length >=3)
            {
                registerTick!!.visibility = View.VISIBLE

            }else{
                registerTick!!.visibility = View.GONE
            }

        }

        override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
        }

    }

    private  val mobileEdit = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

            if(p0.toString().length == mobileLength )
            {
                if(countryCodeTV!!.text.toString().equals("- - -"))
                {
                    mobileTick!!.visibility = View.GONE
                }else{
                    mobileTick!!.visibility = View.VISIBLE
                }

            }else{
                mobileTick!!.visibility = View.GONE
            }

        }

        override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
        }

    }

    private  val emailEdit = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

            if(isEmailValid(p0.toString()))
            {
                emailTick!!.visibility = View.VISIBLE

            }else{
                emailTick!!.visibility = View.GONE
            }

        }

        override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
        }

    }

    fun userName()
    {

        if(registerName!!.text.toString().length >=3)
        {
            registerTick!!.visibility = View.VISIBLE

        }else{
            registerTick!!.visibility = View.GONE
        }
    }

    fun mobileNumber()
    {
        if(mobileNumber!!.text.toString().length ==mobileLength)
        {
            if(countryCodeTV!!.text.toString().equals("- - -"))
            {
                mobileTick!!.visibility = View.GONE
            }else{
                mobileTick!!.visibility = View.VISIBLE
            }

        }else{
            mobileTick!!.visibility = View.GONE
        }
    }

    fun emailValidation()
    {
        if(isEmailValid(registerEmail!!.text.toString()))
        {
            emailTick!!.visibility = View.VISIBLE

        }else{
            emailTick!!.visibility = View.GONE
        }

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

        when(view!!.id){

            R.id.registerButton ->
            {
                if(registerName!!.text.toString().length <=3)
                {
                    showToast(this,"Please enter name")
                }else if(countryCodeTV!!.text.toString().equals("- - -"))
                {
                    showToast(this,"Please select country code")
                }else if (mobileNumber!!.text.toString().length <=9 && mobileNumber!!.text.toString().length >=12)
                {
                    showToast(this,"Please enter 10 digit mobile number")
                }else if(!isEmailValid(registerEmail!!.text.toString())) {
                    showToast(this, "Please enter valid email id")
                }else{

                    val obj = JSONObject()
                    obj.put("name", registerName!!.text)
                    obj.put("customerReferenceCode", localgetUserInfo("referenceCode"))
                    obj.put("mobile", countryCodeString+"-"+mobileNumber!!.text)
                    obj.put("email", registerEmail!!.text)
                    obj.put("token", dbHelper!!.getUserDetails().token)
                    Log.v("Json", "Value" + obj)
                    RequestManager.setCustomerUpdate(this, obj, this);

                }


                //startActivity(Intent(this@RegisterActivity,OtpVerificationActivity::class.java))

            }

            R.id.countryCodeRL ->{
                //showDialogAddService(this)

                val obj = JSONObject()
                RequestManager.setCountryList(this, obj, this);
                loadingScreen(this)
            }

            R.id.registerAddress ->{
                //LocationPicker()

                AppUpdate = "1"
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

   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_LOCATION) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(data, this)
                val stBuilder = StringBuilder()
                val placename = String.format("%s", place.name)
                val latitude = place.latLng.latitude.toString()
                val longitude = place.latLng.longitude.toString()
                val address = String.format("%s", place.address)

                latitudeString = latitude

                longtitudeString = longitude
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

                registerAddress!!.text = address

                val geocoder = Geocoder(this)
                try {
                    val addresses = geocoder.getFromLocation(
                        place.latLng.latitude,
                        place.latLng.longitude,
                        1
                    )

                    registerAddress!!.setText(addresses[0].getAddressLine(0))
                    cityString = addresses[0].subAdminArea

                    stateString = addresses[0].adminArea

                    countryString = addresses[0].countryName

                    streetString = addresses[0].subLocality
                    if (addresses[0].postalCode != null) {
                        pinCode = addresses[0].postalCode
                        Log.d("ZIP CODE", pinCode)
                    }


                    //String country = addresses.get(0).getAddressLine(2);
                } catch (e: IOException) {
                    e.printStackTrace()
                }catch (e: IndexOutOfBoundsException) {
                    e.printStackTrace()
                }catch (e: NullPointerException) {
                    e.printStackTrace()
                }

            }
        }
    }
*/

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if(responseObj != null)
        {
            if(requestType == Constant.MEMBER_CustomerMobileUpdate_URL_RQ)
            {
                showToast(this,(responseObj as BaseRS).message)

                if((responseObj as BaseRS).status.equals("5007")) {

                    localstorageUserInfo(this,localgetUserInfo("token"),registerName!!.text.toString(),registerEmail!!.text.toString(),countryCodeString+"-"+mobileNumber!!.text.toString(),"",localgetUserInfo("referenceCode"),localgetUserInfo("image"))

                    dbHelper!!.updateUserDetails(registerName!!.text.toString(),registerEmail!!.text.toString(),countryCodeString+"-"+mobileNumber!!.text.toString(),"",""+localgetUserInfo("token"))

                    var intent = Intent(this, DashBoardActivity::class.java)

                    intent.putExtra("page","Profile")

                    startActivity(intent)
                }

            }else if(requestType == Constant.MEMBER_countryList_URL_RQ){

                loadingScreenClose(this)
                if (adapterDetails!!.size != 0) {
                    adapterDetails!!.clear()
                }
                for(i in 0 until (responseObj as BaseRS).countryList!!.size)
                {
                    adapterModel = AdapterModel(
                        0,
                        (responseObj as BaseRS).countryList!!.get(i).countryName+" "+(responseObj as BaseRS).countryList!!.get(i).countryCode,
                        ""+(responseObj as BaseRS).countryList!!.get(i).mobileNumberLimit,
                        ""+(responseObj as BaseRS).countryList!!.get(i).countryShortCode,
                        ""+(responseObj as BaseRS).countryList!!.get(i).countryCode,
                        "3 Years",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "VTU",
                        "Rank ",
                        "10% \nOFF",
                        "Admission Open",
                        "Follow",
                        "Apply",
                        "1",
                        0,
                        0,
                        0,
                        adapterDetailChild
                    )

                    adapterDetails!!.add(adapterModel)
                }

                showDialogAddService(this)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                Log.i("", "PendrrrrringIntent unable to execute request.")

                gpsLocation()
            }
        }
    }

    fun showDialogAddService(
        context: Context?
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
        dialog.setContentView(R.layout.select_language_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val languageList = dialog.findViewById<View>(R.id.languageList) as RecyclerView

        languageAdapter = CountryAdapter(context, adapterDetails, dialog)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        languageList!!.layoutManager = mLayoutManager

        languageList!!.itemAnimator!!.addDuration = 5000

        languageAdapter!!.setCallback(this)

        languageList!!.adapter = languageAdapter

        val loginText = dialog.findViewById<View>(R.id.loginText) as TextView
        loginText!!.typeface = fontStyle(this, "")

        loginText!!.text = "Select Country Code"

        val closeIV = dialog.findViewById<View>(R.id.closeIV) as ImageView
        closeIV!!.setOnClickListener(View.OnClickListener { dialog.cancel() })

        //showLanguage()
        dialog.show()
    }

    fun showLanguage() {
        if (adapterDetails!!.size != 0) {
            adapterDetails!!.clear()
        }
        adapterModel = AdapterModel(
            0,
            "India +91",
            "",
            "",
            "",
            "3 Years",
            "",
            "",
            "",
            "",
            "",
            "VTU",
            "Rank ",
            "10% \nOFF",
            "Admission Open",
            "Follow",
            "Apply",
            "1",
            0,
            0,
            0,
            adapterDetailChild
        )
        adapterDetails!!.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "Germany +49",
            "",
            "",
            "",
            "3 Years",
            "",
            "",
            "",
            "",
            "",
            "VTU",
            "Rank ",
            "10% \nOFF",
            "Admission Open",
            "Follow",
            "Apply",
            "1",
            0,
            0,
            0,
            adapterDetailChild
        )

        adapterDetails!!.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "Portugal +351",
            "",
            "",
            "",
            "3 Years",
            "",
            "",
            "",
            "",
            "",
            "VTU",
            "Rank ",
            "10% \nOFF",
            "Admission Open",
            "Follow",
            "Apply",
            "1",
            0,
            0,
            0,
            adapterDetailChild
        )

        adapterDetails!!.add(adapterModel)
    }

    override fun setOnDetail(id: String?, position: String?, positionValue: String?) {
        Log.v("Country"+position,"=="+id)

        var countryCode =""

        var country =""

        countryCode =""+id

        mobileLength = position!!.toInt()

        country = ""+positionValue!!.toUpperCase()

        countryCodeTV!!.text = localeToEmoji(country)+" "+countryCode

        Log.v("Country"+position,"=="+countryCodeTV!!.text.toString())

        countryCodeString = countryCode

        mobileNumber()
    }

    private fun localeToEmoji(country : String): String? {

        val firstLetter = Character.codePointAt(country, 0) - 0x41 + 0x1F1E6
        val secondLetter = Character.codePointAt(country, 1) - 0x41 + 0x1F1E6
        return String(Character.toChars(firstLetter)) + String(
            Character.toChars(
                secondLetter
            )
        )
    }

    fun flagFind(phoneCode : String){
        Log.v("Country","=="+phoneCode)

        var countryCode =""

        var country =""

        if(phoneCode!!.contains("+91"))
        {
            countryCode ="+91"

            country = "IN"
            countryCodeTV!!.text = localeToEmoji(country)+" "+countryCode

            Log.v("Country","=="+countryCodeTV!!.text.toString())
        }else if(phoneCode!!.contains("+49"))
        {
            countryCode ="+49"
            country = "DE"
            countryCodeTV!!.text = localeToEmoji(country)+" "+countryCode
        }else if(phoneCode!!.contains("+351"))
        {
            countryCode ="+351"

            country = "PT"
            countryCodeTV!!.text = localeToEmoji(country)+" "+countryCode
        }

        countryCodeString = countryCode
    }
}
