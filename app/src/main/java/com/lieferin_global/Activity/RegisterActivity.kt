package com.lieferin_global.Activity

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
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.text.Editable
import android.text.InputType
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
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import org.json.JSONObject
import java.io.IOException
import java.util.*


class RegisterActivity : Activity(),View.OnClickListener,ResponseListener,CountryAdapter.CallbackDataAdapter {

    var registerName:EditText? = null

    var total_value:String = ""

    var itemValue : String = ""

    var mobileLength = 0

    var longtitudeValue: String? = ""

    var latitudeValue: String? = ""

    var countryCodeString: String? = ""

    var languageAdapter: CountryAdapter? = null

    private var locationManager: LocationManager? = null

    private val REQUEST_LOCATION = 22

    protected val REQUEST_CHECK_SETTINGS = 1000

    var mobileNumber:EditText? = null

    var registerEmail:EditText? = null

    var registerPassword:EditText? = null

    var registerConfirmPassword:EditText? = null

    var registerAddress:EditText? = null

    private val REQUEST_SMS = 23

    var registerAccountTV:TextView? = null

    var registerButton:TextView? = null

    var userImage : ImageView? = null

    var mobileImg : ImageView? = null

    var emailImg : ImageView? = null

    var passwordImg : ImageView? = null

    var confirmPasswordImg : ImageView? = null

    var addressImg : ImageView? = null

    var registerTick : ImageView? = null

    var signUPLB : TextView? = null

    var PICK_LOCATION:Int = 3

    var latitudeString : String? = null

    var longtitudeString : String? = null

    var cityString : String? = null

    var streetString : String? = null

    var stateString : String? = null

    var countryString : String? = null

    var pinCode : String? = null

    var countryCode: String? = null

    var countryCodeRL : RelativeLayout? =null

    var countryCodeTV : TextView? = null

    var adapterDetails: MutableList<AdapterModel> = ArrayList()

    var adapterDetailChild: MutableList<Product> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    var mobileTick : ImageView? = null

    var emailTick : ImageView? = null

    var passwordTick : ImageView? = null

    var confirmPasswordTick : ImageView? = null

    var addressTick : ImageView? = null

    var registerPasswordView : ImageView? = null

    var confirmPasswordView : ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_register)

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

        signUPLB = findViewById(R.id.signUPLB ) as TextView

        signUPLB!!.typeface = fontStyle(this,"SemiBold")

        registerName = findViewById(R.id.registerName) as EditText

        registerName!!.typeface = fontStyle(this,"")

        mobileNumber = findViewById(R.id.mobileNumber) as EditText

        mobileNumber!!.typeface = fontStyle(this,"")

        registerEmail = findViewById(R.id.registerEmail) as EditText

        registerEmail!!.typeface = fontStyle(this,"")

        registerPassword = findViewById(R.id.registerPassword) as EditText

        registerPassword!!.typeface = fontStyle(this,"")

        registerPassword!!.tag = "hide"

        registerConfirmPassword = findViewById(R.id.registerConfirmPassword) as EditText

        registerConfirmPassword!!.typeface = fontStyle(this,"")

        registerConfirmPassword!!.tag = "hide"

        registerAddress = findViewById(R.id.registerAddress) as EditText

        registerAddress!!.typeface = fontStyle(this,"")

        //registerAddress!!.setOnClickListener(this)

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

        countryCodeTV = findViewById(R.id.countryCodeTV) as TextView

        countryCodeTV!!.typeface = fontStyle(this,"")

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

        registerPasswordView= findViewById(R.id.registerPasswordView) as ImageView

        registerPasswordView!!.setColorFilter(
            colorIcon(this,R.color.colorWhite,R.drawable.password_hide,registerPasswordView!!),
            PorterDuff.Mode.SRC_ATOP)

        registerPasswordView!!.setOnClickListener(this)

        confirmPasswordView= findViewById(R.id.confirmPasswordView) as ImageView

        confirmPasswordView!!.setColorFilter(
            colorIcon(this,R.color.colorWhite,R.drawable.password_hide,confirmPasswordView!!),
            PorterDuff.Mode.SRC_ATOP)

        confirmPasswordView!!.setOnClickListener(this)

        addressImg= findViewById(R.id.addressImg) as ImageView

        addressImg!!.setColorFilter(
            colorIcon(this,R.color.colorWhite,R.drawable.location,addressImg!!),
            PorterDuff.Mode.SRC_ATOP)
        var extras = intent.extras

        var value:String =""
        if (extras != null) {
            registerAddress!!.setText(extras.getString("LocationText").toString());

            latitudeString = extras.getString("latitude").toString();

            longtitudeString = extras.getString("longtitude").toString();

            val geocoder = Geocoder(this)
            try {
                val addresses = geocoder.getFromLocation(
                    latitudeString!!.toDouble(),
                    longtitudeString!!.toDouble(),
                    1
                )
                registerAddress!!.setText(addresses[0].getAddressLine(0))
                cityString = addresses[0].subAdminArea

                stateString = addresses[0].adminArea

                countryString = addresses[0].countryName

                streetString = addresses[0].subLocality

                val Country_code1 = addresses[0].countryCode
                if (addresses[0].postalCode != null) {
                    pinCode = addresses[0].postalCode
                    Log.d("ZIP CODE", pinCode)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            }

        countryCodeRL = findViewById(R.id.countryCodeRL) as RelativeLayout

        countryCodeRL!!.setOnClickListener(this)

        registerName!!.setText(Constant.StringName)

        mobileNumber!!.setText(Constant.StringMobileNumber)

        registerEmail!!.setText(Constant.StringEmail)

        registerPassword!!.setText(Constant.StringPassword)

        registerConfirmPassword!!.setText(Constant.StringConfirmPassword)

        registerTick = findViewById(R.id.registerTick)

        registerTick!!.visibility = View.GONE

        if(Constant.countryCode.equals("")) {

            countryCodeTV!!.text = "- - -"
        }else{
            countryCodeTV!!.text = Constant.countryCode
        }
        countryCodeString = Constant.countryCodeString!!
        registerName!!.addTextChangedListener(editor)

        mobileNumber!!.addTextChangedListener(mobileEdit)

        registerEmail!!.addTextChangedListener(emailEdit)

        registerPassword!!.addTextChangedListener(passwordEdit)

        registerConfirmPassword!!.addTextChangedListener(confirmPasswordEdit)
        checkPermissionSms()

        mobileTick = findViewById(R.id.mobileTick)

        mobileTick!!.visibility = View.GONE

        emailTick = findViewById(R.id.emailTick)

        emailTick!!.visibility = View.GONE

        passwordTick = findViewById(R.id.passwordTick)

        passwordTick!!.visibility = View.GONE

        confirmPasswordTick = findViewById(R.id.confirmPasswordTick)

        confirmPasswordTick!!.visibility = View.GONE

        addressTick = findViewById(R.id.addressTick)

        if(registerAddress!!.text.equals(""))
        {
            addressTick!!.visibility = View.GONE
        }else{
            addressTick!!.visibility = View.GONE
        }

        userName()
        mobileNumber()
        emailValidation()
        passwordValidation()
        confirmPasswordValidation()
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

    fun passwordValidation()
    {
        if(registerPassword!!.text.toString().length >=6)
        {
            passwordTick!!.visibility = View.VISIBLE

        }else{
            passwordTick!!.visibility = View.GONE
        }


    }

    fun confirmPasswordValidation()
    {
        if(registerConfirmPassword!!.text.toString().length >=6) {
            if (registerPassword!!.text.toString()
                    .equals(registerConfirmPassword!!.text.toString())
            ) {
                confirmPasswordTick!!.visibility = View.VISIBLE

            } else {
                confirmPasswordTick!!.visibility = View.GONE
            }
        }else{
            confirmPasswordTick!!.visibility = View.GONE
        }

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

            if(p0.toString().length == mobileLength)
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

    private  val passwordEdit = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

            if(p0.toString().length >=6)
            {
                passwordTick!!.visibility = View.VISIBLE

            }else{
                passwordTick!!.visibility = View.GONE
            }
        }

        override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
        }

    }

    private  val confirmPasswordEdit = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            if(p0.toString().length >=6) {
                if (registerPassword!!.text.toString().equals(p0.toString())) {
                    confirmPasswordTick!!.visibility = View.VISIBLE

                } else {
                    confirmPasswordTick!!.visibility = View.GONE
                }
            }else{
                confirmPasswordTick!!.visibility = View.GONE
            }

        }

        override fun beforeTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) {
        }

    }
    fun checkPermissionSms() {
        val rc = ActivityCompat.checkSelfPermission(
            this@RegisterActivity,
            Manifest.permission.RECEIVE_SMS
        )
        if (rc == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(
                this@RegisterActivity,
                arrayOf(Manifest.permission.RECEIVE_SMS),
                REQUEST_SMS
            )
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

            REQUEST_SMS-> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                //displayLocationSettingsRequest(this)
            } else {
                //showToast(this, "Permission Denied")
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
                }else if(countryCodeTV!!.text.toString().equals(""))
                {
                    showToast(this,"Please select country code")
                }else if (mobileNumber!!.text.toString().length <=9 && mobileNumber!!.text.toString().length >=12)
                {
                    showToast(this,"Please enter valid mobile number")
                }else if(!isEmailValid(registerEmail!!.text.toString()))
                {
                    showToast(this,"Please enter valid email id")
                }else if(registerPassword!!.text.toString().length <6)
                {
                    showToast(this,"Please enter min 6 digit password")
                }else if(!registerPassword!!.text.toString().equals(registerConfirmPassword!!.text.toString()))
                {
                    showToast(this,"Password mismatch")
                }else{

                    val obj = JSONObject()
                    obj.put("name", registerName!!.text)
                    obj.put("mobile", countryCodeString+"-"+mobileNumber!!.text)
                    obj.put("email", registerEmail!!.text)
                    obj.put("password", registerPassword!!.text)
                    obj.put("password_confirm", registerConfirmPassword!!.text)
                    obj.put("address", registerAddress!!.text)
                    obj.put("city", cityString)
                    obj.put("country", countryString)
                    obj.put("countryCode",countryCode )
                    obj.put("state", stateString)
                    obj.put("street",streetString)
                    obj.put("pinCode", pinCode)
                    obj.put("latitude", latitudeString)
                    obj.put("longtitude", longtitudeString)
                    Log.v("Json", "Value" + obj)
                   RequestManager.setRegister(this, obj, this);
                   loadingScreen(this)
                }


                //startActivity(Intent(this@RegisterActivity,OtpVerificationActivity::class.java))

            }

            R.id.confirmPasswordView ->{
                if(registerConfirmPassword!!.tag.equals("hide"))
                {
                    registerConfirmPassword!!.tag = "show"

                    registerConfirmPassword!!.setInputType(
                        InputType.TYPE_CLASS_TEXT );

                    confirmPasswordView!!.setColorFilter(
                        colorIcon(this,R.color.colorWhite,R.drawable.password_show,confirmPasswordView!!),
                        PorterDuff.Mode.SRC_ATOP)
                }else{
                    registerConfirmPassword!!.tag = "hide"

                    registerConfirmPassword!!.setInputType(InputType.TYPE_CLASS_TEXT or
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    confirmPasswordView!!.setColorFilter(
                        colorIcon(this,R.color.colorWhite,R.drawable.password_hide,confirmPasswordView!!),
                        PorterDuff.Mode.SRC_ATOP)
                }
            }

            R.id.registerPasswordView ->{
                if(registerPassword!!.tag.equals("hide"))
                {
                    registerPassword!!.tag = "show"

                    registerPassword!!.setInputType(
                        InputType.TYPE_CLASS_TEXT );

                    registerPasswordView!!.setColorFilter(
                        colorIcon(this,R.color.colorWhite,R.drawable.password_show,registerPasswordView!!),
                        PorterDuff.Mode.SRC_ATOP)
                }else{
                    registerPassword!!.tag = "hide"

                    registerPassword!!.setInputType(InputType.TYPE_CLASS_TEXT or
                            InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    registerPasswordView!!.setColorFilter(
                        colorIcon(this,R.color.colorWhite,R.drawable.password_hide,registerPasswordView!!),
                        PorterDuff.Mode.SRC_ATOP)
                }

            }

            R.id.registerAddress ->{
                //LocationPicker()

                Constant.AppUpdate = "2"
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

                Constant.StringName = registerName!!.text.toString()

                Constant.StringMobileNumber = mobileNumber!!.text.toString()

                Constant.StringEmail = registerEmail!!.text.toString()

                Constant.StringPassword = registerPassword!!.text.toString()

                Constant.StringConfirmPassword = registerConfirmPassword!!.text.toString()

                Constant.countryCode = countryCodeTV!!.text.toString()

                Constant.countryCodeString = countryCodeString!!
            }

            R.id.countryCodeRL ->{
                   //showDialogAddService(this)
                val obj = JSONObject()
                RequestManager.setCountryList(this, obj, this);
                loadingScreen(this)
            }

            R.id.registerAccountTV ->

            {
                startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))

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

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
         if (requestCode == PICK_LOCATION) {
            if (resultCode == RESULT_OK) {
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
                }

            }
        }
    }
*/
    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if(responseObj != null)
        {
            loadingScreenClose(this)
            if(requestType == Constant.MEMBER_customerRegister_URL_RQ)
            {
                showToast(this,(responseObj as BaseRS).message)

                if((responseObj as BaseRS).status.equals("5004")) {
                    var intent = Intent(this, OtpVerification::class.java)

                    intent.putExtra("otp",""+(responseObj as BaseRS).verificationCode)

                    intent.putExtra("customerReferenceCode",""+(responseObj as BaseRS).customerReferenceCode)

                    intent.putExtra("email",""+registerEmail!!.text.toString())

                    startActivity(intent)

                    finish()
                    //successDialog(this,""+(responseObj as BaseRS).message,""+(responseObj as BaseRS).verificationCode,""+(responseObj as BaseRS).customerReferenceCode)
                }

            }else if(requestType == Constant.MEMBER_countryList_URL_RQ){
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

fun successDialog(context:Context,msgText:String)
{
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
    dialog.setContentView(R.layout.register_dialog)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    val textSuccess = dialog.findViewById<View>(R.id.textSuccess) as TextView

    textSuccess!!.typeface = fontStyle(context,"")

    textSuccess!!.text = msgText

    val okText = dialog.findViewById<View>(R.id.okText) as TextView

    okText!!.typeface = fontStyle(context,"")

   okText!!.setOnClickListener(View.OnClickListener {


   })

    dialog.show()

        //dialog.cancel()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                Log.i("", "PendrrrrringIntent unable to execute request.")

                gpsLocation()
            }
        }
    }

    override fun setOnDetail(id: String?, position: String?,positionValue: String?) {

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
}
