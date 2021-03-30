package com.lieferin_global.Activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.R
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.colorIcon
import com.lieferin_global.Utility.fontStyle
import com.lieferin_global.Utility.localgetUserInfo
import com.lieferin_global.Utility.localgetUserInfoSlash
import java.util.*

class OrderCompleteActivity : AppCompatActivity(),View.OnClickListener {

    var addAddress_Title: TextView? = null

    var paymentTitle: TextView? = null

    var paymentTitleDes: TextView? = null

    var paymentYourOrder: TextView? = null

    var paymentYourOrderNo: TextView? = null

    var paymentYourOrderSuccess: TextView? = null

    var myorderTV: TextView? = null

    private val SPLASH_DISPLAY_LENGTH = 7000

    var foodText : TextView? = null

    var dbHelper : DBHelper? = null

    var doneImg : ImageView? = null

    var smileImg : ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_order_complete)

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

        smileImg =  findViewById(R.id.smileImg) as ImageView

        doneImg =  findViewById(R.id.doneImg) as ImageView

        paymentTitle = findViewById(R.id.paymentTitle) as TextView

        paymentTitle!!.typeface = fontStyle(this,"SemiBold")

        paymentTitleDes= findViewById(R.id.paymentTitleDes) as TextView

        paymentTitleDes!!.typeface = fontStyle(this,"Light")

        paymentYourOrder = findViewById(R.id.paymentYourOrder) as TextView

        paymentYourOrder!!.typeface = fontStyle(this,"SemiBold")

        paymentYourOrderNo = findViewById(R.id.paymentYourOrderNo) as TextView

        paymentYourOrderNo!!.typeface = fontStyle(this,"SemiBold")

        paymentYourOrderSuccess = findViewById(R.id.paymentYourOrderSuccess) as TextView

        paymentYourOrderSuccess!!.typeface = fontStyle(this,"SemiBold")

        myorderTV = findViewById(R.id.myorderTV) as TextView

        myorderTV!!.typeface = fontStyle(this,"SemiBold")

        myorderTV!!.setOnClickListener(this)

        val intent = intent

        if (intent != null) {
            if (intent.getStringExtra("orderId") != null) {
                paymentYourOrderNo!!.text = ""+intent.getStringExtra("orderId")

                if(AppType.equals("0")) {

                    dbHelper!!.deleteMenu()

                    dbHelper!!.deleteCategory()

                    dbHelper!!.deleteToppinsGroup()

                    dbHelper!!.deleteToppins()

                    dbHelper!!.deleteRest()

                    smileImg!!.setColorFilter(colorIcon(this,R.color.redColor,R.drawable.smile,smileImg!!))

                    doneImg!!.setColorFilter(colorIcon(this,R.color.redColor,R.drawable.check,doneImg!!))

                    paymentYourOrderSuccess!!.setTextColor(Color.parseColor("#ec272b"))

                    myorderTV!!.setBackgroundResource(R.drawable.verify_radius_button_orange)

                }else{
                    smileImg!!.setColorFilter(colorIcon(this,R.color.colorGreen,R.drawable.smile,smileImg!!))

                    doneImg!!.setColorFilter(colorIcon(this,R.color.colorGreen,R.drawable.check,doneImg!!))

                    paymentYourOrderSuccess!!.setTextColor(Color.parseColor("#7DC77D"))

                    myorderTV!!.setBackgroundResource(R.drawable.home_radius_button_green)

                    dbHelper!!.deleteGrocery()
                }
            }
        }
    }

    override fun onClick(view: View?) {
        when(view!!.id)
        {
            R.id.myorderTV ->{
                if(AppType.equals("0")) {
                    val mIentent = Intent(this, DashBoardActivity::class.java)
                    mIentent.putExtra("page", "My Order");
                    startActivity(mIentent)
                }else{
                    val mIentent = Intent(this, DashBoardGrpceryActivity::class.java)
                    mIentent.putExtra("page", "My Order");
                    startActivity(mIentent)
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                if(AppType.equals("0")) {
                    val mIentent = Intent(this, DashBoardActivity::class.java)
                    //mIentent.putExtra("page","My Order");
                    startActivity(mIentent)
                }else{
                    val mIentent = Intent(this, DashBoardGrpceryActivity::class.java)
                    //mIentent.putExtra("page","My Order");
                    startActivity(mIentent)
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({
           /* val mIentent = Intent(this, OrderRatingActivity::class.java)
            //mIentent.putExtra("page",Constant.Location);
            startActivity(mIentent)

            finish()*/
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }

}
