package com.lieferin_global.Activity

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
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

class ResetPassword : Activity(),View.OnClickListener, ResponseListener  {

    var enterConfirmPassword: EditText? = null

    var enterPassword: EditText? = null

    var loginButton: TextView? = null

    var accountTV: TextView? = null

    var forgetTV: TextView? = null

    var confirmPassword : ImageView? = null

    var password : ImageView? = null

    var signINLB : TextView? = null

    var emailId:String? = null

    var registerPasswordView : ImageView? = null

    var confirmPasswordView : ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_reset_password)

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
        signINLB = findViewById(R.id.signINLB ) as TextView

        signINLB!!.typeface = fontStyle(this,"SemiBold")

        enterConfirmPassword = findViewById(R.id.enterConfirmPassword) as EditText

        enterConfirmPassword!!.typeface = fontStyle(this,"")

        enterConfirmPassword!!.tag = "hide"

        enterPassword = findViewById(R.id.enterPassword) as EditText

        enterPassword!!.typeface = fontStyle(this,"")

        enterPassword!!.tag = "hide"

        loginButton = findViewById(R.id.loginButton) as TextView

        loginButton!!.typeface = fontStyle(this,"SemiBold")

        loginButton!!.setOnClickListener(this)

        accountTV = findViewById(R.id.accountTV) as TextView

        accountTV!!.typeface = fontStyle(this,"Light")

        accountTV!!.setOnClickListener(this)

        forgetTV = findViewById(R.id.forgetTV) as TextView

        forgetTV!!.typeface = fontStyle(this,"Light")

        forgetTV!!.setOnClickListener(this)

        confirmPassword= findViewById(R.id.confirmPassword) as ImageView

        confirmPassword!!.setColorFilter(
            colorIcon(this,R.color.colorWhite,R.drawable.password,confirmPassword!!),
            PorterDuff.Mode.SRC_ATOP)

        password= findViewById(R.id.password) as ImageView

        password!!.setColorFilter(
            colorIcon(this,R.color.colorWhite,R.drawable.password,password!!),
            PorterDuff.Mode.SRC_ATOP)
        val intent = intent

        if(intent != null) {

            if(intent.getStringExtra("email") != null) {
                emailId=intent.getStringExtra("email")
            }
        }

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


    }

    override fun onClick(view: View?) {

        when(view!!.id){

            R.id.loginButton ->
            {
                if(enterPassword!!.text.toString().length <6)
                {
                    showToast(this,"Please enter min 6 password")

                }else if(!enterPassword!!.text.toString().equals(enterConfirmPassword!!.text.toString()))
                {
                    showToast(this,"Password Mismatch")
                }else{
                    val obj = JSONObject()
                    obj.put("email", emailId)
                    obj.put("password", enterPassword!!.text)
                    obj.put("passwordConfirm", enterConfirmPassword!!.text)
                    Log.v("Json", "Value" + obj)
                    RequestManager.setResetPassword(this, obj, this);
                    loadingScreen(this)
                }

                //
            }

            R.id.accountTV ->
            {

                startActivity(Intent(this,RegisterActivity::class.java))

            }

            R.id.confirmPasswordView ->{
                if(enterConfirmPassword!!.tag.equals("hide"))
                {
                    enterConfirmPassword!!.tag = "show"

                    enterConfirmPassword!!.setInputType(
                        InputType.TYPE_CLASS_TEXT );

                    confirmPasswordView!!.setColorFilter(
                        colorIcon(this,R.color.colorWhite,R.drawable.password_show,confirmPasswordView!!),
                        PorterDuff.Mode.SRC_ATOP)
                }else{
                    enterConfirmPassword!!.tag = "hide"

                    enterConfirmPassword!!.setInputType(
                        InputType.TYPE_CLASS_TEXT or
                                InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    confirmPasswordView!!.setColorFilter(
                        colorIcon(this,R.color.colorWhite,R.drawable.password_hide,confirmPasswordView!!),
                        PorterDuff.Mode.SRC_ATOP)
                }
            }

            R.id.registerPasswordView ->{
                if(enterPassword!!.tag.equals("hide"))
                {
                    enterPassword!!.tag = "show"

                    enterPassword!!.setInputType(
                        InputType.TYPE_CLASS_TEXT );

                    registerPasswordView!!.setColorFilter(
                        colorIcon(this,R.color.colorWhite,R.drawable.password_show,confirmPasswordView!!),
                        PorterDuff.Mode.SRC_ATOP)
                }else{
                    enterPassword!!.tag = "hide"

                    enterPassword!!.setInputType(
                        InputType.TYPE_CLASS_TEXT or
                                InputType.TYPE_TEXT_VARIATION_PASSWORD);

                    registerPasswordView!!.setColorFilter(
                        colorIcon(this,R.color.colorWhite,R.drawable.password_hide,confirmPasswordView!!),
                        PorterDuff.Mode.SRC_ATOP)
                }

            }

            R.id.forgetTV ->
            {
                startActivity(Intent(this,ForgetPasswordActivity::class.java))
            }
        }

    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {

        loadingScreenClose(this)
        if(responseObj != null)
        {
            if(requestType == Constant.MEMBER_getResetPassword_URL_RQ)
            {
                showToast(this,(responseObj as BaseRS).message)

                if((responseObj as BaseRS).status.equals("1017")) {

                    //localstorageUserInfo(this,(responseObj as BaseRS).token,(responseObj as BaseRS).user_details!!.firstname,(responseObj as BaseRS).user_details!!.email,(responseObj as BaseRS).user_details!!.mobile,(responseObj as BaseRS).user_address!!.address1,"")

                    startActivity(Intent(this,LoginActivity::class.java))
                }
            }
        }
    }
}
