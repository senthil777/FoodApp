package com.lieferin_global.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lieferin_global.R
import com.lieferin_global.Utility.Constant
import com.lieferin_global.Utility.fontStyle
import com.lieferin_global.Utility.localgetUserInfo
import com.lieferin_global.Utility.localgetUserInfoSlash
import pl.bclogic.pulsator4droid.library.PulsatorLayout
import java.util.*


class SplashScreen : AppCompatActivity() {

    private val SPLASH_DISPLAY_LENGTH = 5000

    var foodText : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_splash_screen)

        foodText = findViewById(R.id.foodText) as TextView

        foodText!!.typeface = fontStyle(this,"SemiBold")

        val pulsator = findViewById(R.id.pulsator) as PulsatorLayout

        pulsator.start()

//        Constant.latitudeAdd = "52.422271"
//
//        Constant.longtitudeAdd = "13.411581"

        //localstorageValue(this,"")

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
    }
    override fun onStart() {
        super.onStart()
        Handler().postDelayed({

            Log.v("name "+localgetUserInfo("name"),"==")

            if(localgetUserInfo("name").equals("")) {
                finish()
                val mIentent = Intent(this@SplashScreen, InfoActivity::class.java)
                //mIentent.putExtra("page",Constant.Location);
                startActivity(mIentent)
            }else if(localgetUserInfo("name").equals("Logout")) {
                finish()

                val mIentent = Intent(this@SplashScreen, DashBoardActivity::class.java)
                //mIentent.putExtra("page",Constant.Location);
                startActivity(mIentent)
            }else{
                finish()
                val mIentent = Intent(this@SplashScreen, DashBoardActivity::class.java)
                //mIentent.putExtra("page",Constant.Location);
                startActivity(mIentent)
            }
/*
            val mIentent = Intent(this@SplashScreen, InfoActivity::class.java)
            //mIentent.putExtra("page",Constant.Location);
            startActivity(mIentent)*/
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}
