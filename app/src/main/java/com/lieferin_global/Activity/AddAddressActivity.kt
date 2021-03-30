package com.lieferin_global.Activity

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.google.android.material.textfield.TextInputLayout
import com.lieferin_global.LocalDataBase.DBHelper
import org.jetbrains.anko.hintTextColor
import org.jetbrains.anko.textColor
import org.json.JSONObject
import java.util.*

class AddAddressActivity : AppCompatActivity(),View.OnClickListener,ResponseListener {

    var filterTV : TextView? = null

    var cancel : ImageView? = null

    var address_layout_name : TextInputLayout? = null

    var addressSubDescriptionTV : TextView? = null

    var locationAddressTV : TextView? = null

    var changeText : TextView? = null

    var addressET : EditText? = null

    var landMarkET : EditText? = null

    var howToReach : EditText? = null

    var addressETGrocery : EditText? = null

    var landMarkETGrocery : EditText? = null

    var howToReachGrocery : EditText? = null

    var tagText : TextView? =null

    var continueTxt : TextView? = null

    var homeLayout : LinearLayout? = null

    var workLayout : LinearLayout? = null

    var hotelLayout : LinearLayout? = null

    var otherLayout : LinearLayout? = null

    var hotelText : TextView? = null

    var workText : TextView? = null

    var homeText : TextView? = null

    var otherText : TextView? = null

    var latitude : String? = ""

    var longtitude : String? = ""

    var homeIV : ImageView? = null

    var workIV : ImageView? = null

    var hotel : ImageView? = null

    var other : ImageView? = null

    var screen : String? = null

    var total_value:String = ""

    var itemValue : String = ""

    var grocery : LinearLayout? = null

    var food : LinearLayout? = null

    var addressText : String? = null

    var dbHelper : DBHelper? = null

    var addressType : String? = "1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
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

        address_layout_name = findViewById(R.id.address_layout_name) as TextInputLayout

        food = findViewById(R.id.food) as LinearLayout

        grocery = findViewById(R.id.grocery) as LinearLayout

        cancel = findViewById(R.id.cancel) as ImageView

        cancel!!.setOnClickListener(this)

        homeIV = findViewById(R.id.homeIV) as ImageView

        workIV = findViewById(R.id.workIV) as ImageView

        hotel = findViewById(R.id.hotel) as ImageView

        other = findViewById(R.id.other) as ImageView

        filterTV = findViewById(R.id.filterTV) as TextView

        filterTV!!.typeface = fontStyle(this,"")

        addressSubDescriptionTV = findViewById(R.id.addressSubDescriptionTV) as TextView

        addressSubDescriptionTV!!.typeface = fontStyle(this,"SemiBold")

        locationAddressTV = findViewById(R.id.locationAddressTV) as TextView

        locationAddressTV!!.typeface = fontStyle(this,"")

        changeText = findViewById(R.id.changeText) as TextView

        changeText!!.typeface = fontStyle(this,"Light")

        addressET = findViewById(R.id.addressET) as EditText

        addressET!!.typeface = fontStyle(this,"")

        landMarkET = findViewById(R.id.landMarkET) as EditText

        landMarkET!!.typeface = fontStyle(this,"")

        howToReach = findViewById(R.id.howToReach) as EditText

        howToReach!!.typeface = fontStyle(this,"")

        addressETGrocery = findViewById(R.id.addressETGrocery) as EditText

        addressETGrocery!!.typeface = fontStyle(this,"")

        landMarkETGrocery = findViewById(R.id.landMarkETGrocery) as EditText

        landMarkETGrocery!!.typeface = fontStyle(this,"")

        howToReachGrocery = findViewById(R.id.howToReachGrocery) as EditText

        howToReachGrocery!!.typeface = fontStyle(this,"")

        tagText = findViewById(R.id.tagText) as TextView

        tagText!!.typeface = fontStyle(this,"SemiBold")

        continueTxt = findViewById(R.id.continueTxt) as TextView

        continueTxt!!.typeface = fontStyle(this,"SemiBold")

        continueTxt!!.setOnClickListener(this)

        homeText = findViewById(R.id.homeText) as TextView

        homeText!!.typeface = fontStyle(this,"Light")



        workText = findViewById(R.id.workText) as TextView

        workText!!.typeface = fontStyle(this,"Light")

        otherText = findViewById(R.id.otherText) as TextView

        otherText!!.typeface = fontStyle(this,"Light")

        hotelText = findViewById(R.id.hotelText) as TextView

        hotelText!!.typeface = fontStyle(this,"Light")

        homeLayout = findViewById(R.id.homeLayout) as LinearLayout

        homeLayout!!.setOnClickListener(this)

        workLayout = findViewById(R.id.workLayout) as LinearLayout

        workLayout!!.setOnClickListener(this)

        hotelLayout = findViewById(R.id.hotelLayout) as LinearLayout

        hotelLayout!!.setOnClickListener(this)

        otherLayout = findViewById(R.id.otherLayout) as LinearLayout

        otherLayout!!.setOnClickListener(this)

        val extras = intent.extras
        val userName: String?
        if (extras != null) {

            if (extras.getString("latitude") != null) {

                latitude = extras.getString("latitude")
            }
            if (extras.getString("latitude") != null) {

                screen = extras.getString("screen")
            }


            if (extras.getString("longtitude") != null) {

                longtitude = extras.getString("longtitude")

                locationAddressTV!!.text = addressLocation(this,latitude!!,longtitude!!).getAddressLine(0)
            }

            if (intent.getStringExtra("Total Amount") != null) {
                total_value = intent.getStringExtra("Total Amount")
            }

            if (intent.getStringExtra("Item") != null) {
                itemValue = intent.getStringExtra("Item")
            }
        }
        colorImage()

        backGround()

        if(AppType!!.equals("0")) {

            changeText!!.textColor = Color.parseColor("#ec272b")

            addressET!!.hintTextColor = Color.parseColor("#ec272b")

            landMarkET!!.hintTextColor = Color.parseColor("#ec272b")

            howToReach!!.hintTextColor = Color.parseColor("#ec272b")

            continueTxt!!.setBackgroundResource(R.drawable.home_radius_button_orange)

            food!!.visibility =View.VISIBLE

            grocery!!.visibility =View.GONE


        }else{
            changeText!!.textColor = Color.parseColor("#7DC77D")

            addressET!!.hintTextColor = Color.parseColor("#7DC77D")

            landMarkET!!.hintTextColor = Color.parseColor("#7DC77D")

            howToReach!!.hintTextColor = Color.parseColor("#7DC77D")

            continueTxt!!.setBackgroundResource(R.drawable.home_radius_button_green)

            food!!.visibility =View.GONE

            grocery!!.visibility =View.VISIBLE
        }

        homeText!!.setTextColor(Color.parseColor("#ffffff"))

        if(AppType!!.equals("0")) {

            homeLayout!!.setBackgroundResource(R.drawable.home_radius_red)
        }else{
            homeLayout!!.setBackgroundResource(R.drawable.home_radius_green)
        }

        homeIV!!.setColorFilter(
            colorIcon(
                this,
                R.color.colorWhite,
                R.drawable.address_home,
                homeIV!!
            ),
            PorterDuff.Mode.SRC_ATOP
        )
    }

    fun colorImage()
    {
        if(AppType!!.equals("0")) {
            homeIV!!.setColorFilter(
                colorIcon(
                    this,
                    R.color.redColor,
                    R.drawable.address_home,
                    homeIV!!
                ),
                PorterDuff.Mode.SRC_ATOP
            )

            workIV!!.setColorFilter(
                colorIcon(
                    this,
                    R.color.redColor,
                    R.drawable.address_work,
                    workIV!!
                ),
                PorterDuff.Mode.SRC_ATOP
            )

            hotel!!.setColorFilter(
                colorIcon(
                    this,
                    R.color.redColor,
                    R.drawable.address_hotel,
                    hotel!!
                ),
                PorterDuff.Mode.SRC_ATOP
            )

            other!!.setColorFilter(
                colorIcon(
                    this,
                    R.color.redColor,
                    R.drawable.address_other,
                    other!!
                ),
                PorterDuff.Mode.SRC_ATOP
            )
        }else{
            homeIV!!.setColorFilter(
                colorIcon(
                    this,
                    R.color.colorGreen,
                    R.drawable.address_home,
                    homeIV!!
                ),
                PorterDuff.Mode.SRC_ATOP
            )

            workIV!!.setColorFilter(
                colorIcon(
                    this,
                    R.color.colorGreen,
                    R.drawable.address_work,
                    workIV!!
                ),
                PorterDuff.Mode.SRC_ATOP
            )

            hotel!!.setColorFilter(
                colorIcon(
                    this,
                    R.color.colorGreen,
                    R.drawable.address_hotel,
                    hotel!!
                ),
                PorterDuff.Mode.SRC_ATOP
            )

            other!!.setColorFilter(
                colorIcon(
                    this,
                    R.color.colorGreen,
                    R.drawable.address_other,
                    other!!
                ),
                PorterDuff.Mode.SRC_ATOP
            )
        }
    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {

        if(responseObj != null) {
            if (requestType == Constant.MEMBER_customerAddressInsert_URL_RQ) {

                if((responseObj as BaseRS).status.equals("5018"))
                {
                    showToast(this,(responseObj as BaseRS).message)


                    if(screen.equals("1")) {

                        if(AppType!!.equals("0")) {
                            var intent = Intent(this, DashBoardActivity::class.java)

                            intent.putExtra(
                                "LocationText",
                                "" + locationAddressTV!!.text.toString()
                            )

                            Constant.AddressTXT = ""+locationAddressTV!!.text.toString()

                            Constant.latitudeAdd = latitude!!

                            Constant.longtitudeAdd = longtitude!!

                            /*intent.putExtra("latitude",""+latitude)

                        intent.putExtra("longtitude",""+longtitude)*/
                            startActivity(intent)
                        }else{
                            var intent = Intent(this, DashBoardGrpceryActivity::class.java)

                            intent.putExtra(
                                "LocationText",
                                "" + locationAddressTV!!.text.toString()
                            )

                            Constant.AddressTXT = ""+locationAddressTV!!.text.toString()

                            Constant.latitudeAdd = latitude!!

                            Constant.longtitudeAdd = longtitude!!
                            /*intent.putExtra("latitude",""+latitude)

                        intent.putExtra("longtitude",""+longtitude)*/
                            startActivity(intent)
                        }

                    }else{
                        var intent = Intent(this, OrderDetailsActivity::class.java)

                        intent.putExtra("LocationText", "" + locationAddressTV!!.text.toString())
/*
                        intent.putExtra("Total Amount", "" + total_value)
                        intent.putExtra("item", "" + itemValue)*/
                        startActivity(intent)
                    }


                }
            }
        }

    }
    fun backGround(){

        if(AppType!!.equals("0")) {

            homeText!!.setTextColor(Color.parseColor("#ec272b"))

            workText!!.setTextColor(Color.parseColor("#ec272b"))

            hotelText!!.setTextColor(Color.parseColor("#ec272b"))

            otherText!!.setTextColor(Color.parseColor("#ec272b"))

            homeLayout!!.setBackgroundResource(R.drawable.home_radius_border_red)

            workLayout!!.setBackgroundResource(R.drawable.home_radius_border_red)

            hotelLayout!!.setBackgroundResource(R.drawable.home_radius_border_red)

            otherLayout!!.setBackgroundResource(R.drawable.home_radius_border_red)
        }else{
            homeText!!.setTextColor(Color.parseColor("#7DC77D"))

            workText!!.setTextColor(Color.parseColor("#7DC77D"))

            hotelText!!.setTextColor(Color.parseColor("#7DC77D"))

            otherText!!.setTextColor(Color.parseColor("#7DC77D"))

            homeLayout!!.setBackgroundResource(R.drawable.home_radius_border_green)

            workLayout!!.setBackgroundResource(R.drawable.home_radius_border_green)

            hotelLayout!!.setBackgroundResource(R.drawable.home_radius_border_green)

            otherLayout!!.setBackgroundResource(R.drawable.home_radius_border_green)
        }



    }

    override fun onClick(view: View?) {

        when(view!!.id)
        {

            R.id.cancel ->{
                finish()
            }
            R.id.continueTxt->{

                if(AppType.equals("0"))
                {
                    addressText = ""+addressET!!.text.toString()
                }else{
                    addressText = ""+addressETGrocery!!.text.toString()
                }

                if(addressText.toString().equals(""))
                {
                    showToast(this,getString(R.string.pleaseEnterAddress))

                }else {
                    val obj = JSONObject()
                    obj.put("token", "" + dbHelper!!.getUserDetails().token)
                    obj.put("address", "" + addressLocation(this,latitude!!,longtitude!!).getAddressLine(0))
                    obj.put("street", "" + addressLocation(this,latitude!!,longtitude!!).locality)
                    obj.put("city", "" + addressLocation(this,latitude!!,longtitude!!).subAdminArea)
                    obj.put("state", "" + addressLocation(this,latitude!!,longtitude!!).adminArea)
                    obj.put("country", "" + addressLocation(this,latitude!!,longtitude!!).countryName)
                    obj.put("postcode", "" + addressLocation(this,latitude!!,longtitude!!).postalCode)
                    obj.put("latitude", "" + latitude)
                    obj.put("longitude", "" + longtitude)
                    if(AppType.equals("0")) {
                        obj.put("completeAddress", "" + addressET!!.text.toString())
                        obj.put("floor", "" + landMarkET!!.text.toString())
                        obj.put("description", "" + howToReach!!.text.toString())
                        obj.put("addressType", "" + addressType)
                    }else{
                        obj.put("completeAddress", "" + addressETGrocery!!.text.toString())
                        obj.put("floor", "" + landMarkETGrocery!!.text.toString())
                        obj.put("description", "" + howToReachGrocery!!.text.toString())
                        obj.put("addressType", "" + addressType)
                    }
                    Log.v("mmmmmmm","==="+obj);
                    RequestManager.setAddressInsert(this,obj,this)
                }
            }
            R.id.homeLayout->{

                backGround()

                colorImage()

                homeIV!!.setColorFilter(
                    colorIcon(
                        this,
                        R.color.colorWhite,
                        R.drawable.address_home,
                        homeIV!!
                    ),
                    PorterDuff.Mode.SRC_ATOP
                )
                homeText!!.setTextColor(Color.parseColor("#ffffff"))

                if(AppType!!.equals("0")) {

                    homeLayout!!.setBackgroundResource(R.drawable.home_radius_red)
                }else{
                    homeLayout!!.setBackgroundResource(R.drawable.home_radius_green)
                }
                addressType = "1"
            }
            R.id.workLayout->{

                backGround()

                colorImage()

                workIV!!.setColorFilter(
                    colorIcon(
                        this,
                        R.color.colorWhite,
                        R.drawable.address_work,
                        workIV!!
                    ),
                    PorterDuff.Mode.SRC_ATOP
                )
                workText!!.setTextColor(Color.parseColor("#ffffff"))

                if(AppType!!.equals("0")) {

                    workLayout!!.setBackgroundResource(R.drawable.home_radius_red)
                }else{
                    workLayout!!.setBackgroundResource(R.drawable.home_radius_green)
                }

                addressType = "2"
            }
            R.id.otherLayout->{
                backGround()
                colorImage()

                other!!.setColorFilter(
                    colorIcon(
                        this,
                        R.color.colorWhite,
                        R.drawable.address_other,
                        other!!
                    ),
                    PorterDuff.Mode.SRC_ATOP
                )
                otherText!!.setTextColor(Color.parseColor("#ffffff"))

                if(AppType!!.equals("0")) {

                    otherLayout!!.setBackgroundResource(R.drawable.home_radius_red)
                }else{
                    otherLayout!!.setBackgroundResource(R.drawable.home_radius_green)
                }

                addressType = "2"
            }
            R.id.hotelLayout->{
                backGround()
                colorImage()

                hotel!!.setColorFilter(
                    colorIcon(
                        this,
                        R.color.colorWhite,
                        R.drawable.address_hotel,
                        hotel!!
                    ),
                    PorterDuff.Mode.SRC_ATOP
                )
                hotelText!!.setTextColor(Color.parseColor("#ffffff"))

                if(AppType!!.equals("0")) {

                    hotelLayout!!.setBackgroundResource(R.drawable.home_radius_red)
                }else{
                    hotelLayout!!.setBackgroundResource(R.drawable.home_radius_green)
                }
            }
        }
    }
}
