package com.lieferin_global.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle
import com.lieferin_global.Utility.localgetUserInfo
import com.lieferin_global.Utility.localgetUserInfoSlash
import com.lieferin_global.webservice.RequestManager
import org.json.JSONObject
import pl.bclogic.pulsator4droid.library.PulsatorLayout
import java.util.*

class OrderRatingActivity : AppCompatActivity(),View.OnClickListener {

    var orderBill_Title : TextView? = null

    var orderDescription_Type : TextView? = null

    var help : TextView? = null

    var tableReservation_back : ImageView? = null

    var orderBill_Details : TextView? = null

    var orderBill_Time : TextView? = null

    var orderReceived : TextView? = null

    var orderNow : TextView? = null

    var orderReceivedDescription : TextView? = null

    var deliveryText : TextView? = null

    var ratingBar : RatingBar? = null

    var descriptionET : EditText? = null

    var dbHelper : DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_order_rating)
        val pulsator = findViewById(R.id.pulsator) as PulsatorLayout
        pulsator.start()

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

        orderBill_Title = findViewById(R.id.orderBill_Title) as TextView

        orderBill_Title!!.typeface = fontStyle(this,"")

        tableReservation_back = findViewById(R.id.tableReservation_back) as ImageView

        tableReservation_back!!.setOnClickListener(this)

        orderDescription_Type = findViewById(R.id.orderDescription_Type) as TextView

        orderDescription_Type!!.typeface = fontStyle(this,"Light")

        help = findViewById(R.id.help) as TextView

        help!!.typeface = fontStyle(this,"Light")

        orderBill_Details = findViewById(R.id.orderBill_Details) as TextView

        orderBill_Details!!.typeface = fontStyle(this,"")

        orderBill_Time = findViewById(R.id.orderBill_Time) as TextView

        orderBill_Time!!.typeface = fontStyle(this,"Light")

        orderReceived = findViewById(R.id.orderReceived) as TextView

        orderReceived!!.typeface = fontStyle(this,"Light")

        orderNow = findViewById(R.id.orderNow) as TextView

        orderNow!!.typeface = fontStyle(this,"Light")

        orderReceivedDescription = findViewById(R.id.orderReceivedDescription) as TextView

        orderReceivedDescription!!.typeface = fontStyle(this,"Light")

        deliveryText = findViewById(R.id.deliveryText) as TextView

        deliveryText!!.typeface = fontStyle(this,"SemiBold")

        deliveryText!!.setOnClickListener(this)


        ratingBar= findViewById(R.id.RatingBar) as RatingBar

        descriptionET= findViewById(R.id.descriptionET) as EditText

        descriptionET!!.typeface = fontStyle(this,"")
    }

    override fun onClick(view: View?) {

        when(view!!.id)
        {
            R.id.tableReservation_back->{
                finish()
            }

            R.id.deliveryText->{

                    wedServiceGrocery()

            }
        }

    }

    fun wedServiceGrocery(){
        val obj = JSONObject()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)
        obj.put("deliveryPersonReferenceCode", "")
        obj.put("rating", ""+ratingBar!!.getRating())
        obj.put("feedback", ""+descriptionET!!.text.toString())
        Log.v("Json", "Value" + obj)
        RequestManager.setRatingDriver(this, obj, this);
    }
}
