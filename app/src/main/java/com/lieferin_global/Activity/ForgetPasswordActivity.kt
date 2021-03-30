package com.lieferin_global.Activity

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import org.json.JSONObject
import java.util.*

class ForgetPasswordActivity : AppCompatActivity(),View.OnClickListener,ResponseListener {

    var enterEmail: EditText? = null

    var submit: TextView? = null

    var emailImage : ImageView? = null

    var welcomeTxt : TextView? = null

    var newUserButton : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_forget_password)

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
        enterEmail = findViewById(R.id.enterEmail) as EditText

        enterEmail!!.typeface = fontStyle(this,"")

        welcomeTxt = findViewById(R.id.welcomeTxt) as TextView

        welcomeTxt!!.typeface = fontStyle(this,"SemiBold")

        newUserButton = findViewById(R.id.newUserButton) as TextView

        newUserButton!!.typeface = fontStyle(this,"Light")




        submit = findViewById(R.id.submit) as TextView

        submit!!.typeface = fontStyle(this,"SemiBold")

        submit!!.setOnClickListener(this)



        emailImage= findViewById(R.id.emailImage) as ImageView

        emailImage!!.setColorFilter(
            colorIcon(this,R.color.colorWhite,R.drawable.email,emailImage!!),
            PorterDuff.Mode.SRC_ATOP)

    }

    override fun onClick(view: View?) {

        when(view!!.id){

            R.id.submit ->
            {
                if(enterEmail!!.text.toString().equals(""))
                {
                    showToast(this,""+getString(R.string.ForgetPasswordActivity_Please_enter_email_id))

                }else{

                    val obj = JSONObject()
                    obj.put("email", enterEmail!!.text)
                    Log.v("Json", "Value" + obj)
                    RequestManager.setCustomerForgot(this, obj, this);

                    loadingScreen(this)
                }

            }


        }

    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {

        loadingScreenClose(this)
        if(responseObj != null)
        {
            if(requestType == Constant.MEMBER_getCustomerForgot_URL_RQ)
            {
                showToast(this,(responseObj as BaseRS).message)

                if((responseObj as BaseRS).status.equals("1012")) {

                    //localstorageUserInfo(this,(responseObj as BaseRS).token,(responseObj as BaseRS).user_details!!.firstname,(responseObj as BaseRS).user_details!!.email,(responseObj as BaseRS).user_details!!.mobile,(responseObj as BaseRS).user_address!!.address1,"")
                    var intent = Intent(this, OtpVerificationActivity::class.java)

                    intent.putExtra("otp",""+(responseObj as BaseRS).otp)

                    intent.putExtra("email",""+enterEmail!!.text)

                    startActivity(intent)

                    //successDialog(this,(responseObj as BaseRS).otp!!)
                }
            }
        }
    }

    fun successDialog(context: Context, msgText:String)
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
}
