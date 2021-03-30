package com.lieferin_global.Activity

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.alimuzaffar.lib.pin.PinEntryEditText
import com.alimuzaffar.lib.pin.PinEntryEditText.OnPinEnteredListener
import org.jetbrains.anko.enabled
import org.json.JSONObject
import java.util.*


class OtpVerification : Activity(),View.OnClickListener,ResponseListener {

    var verifyDetails: TextView? = null

    var verify_otp_number: TextView? = null

    var verifyTime: TextView? = null

    var verify_via_call: TextView? =null

    var verify_enter_otpBT: TextView? =null

    //var txt_pin_entry: PinEntryEditText? =null

    var verify_enter_otpTV: TextView? =null

    var customerReferenceCode:String? = null

    var eMail:String? = null

    companion object {
        var txt_pin_entry: PinEntryEditText? =null

    }

    var counter = 60

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_otp_verification)

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

        verifyDetails = findViewById(R.id.verifyDetails) as TextView

        verifyDetails!!.typeface = fontStyle(this,"SemiBold")

        verify_otp_number = findViewById(R.id.verify_otp_number) as TextView

        verify_otp_number!!.typeface = fontStyle(this,"Light")

        verify_enter_otpTV = findViewById(R.id.verify_enter_otpTV) as TextView

        verify_enter_otpTV!!.typeface = fontStyle(this,"")

        verifyTime = findViewById(R.id.verifyTime) as TextView

        verifyTime!!.typeface = fontStyle(this,"Light")

        verify_via_call = findViewById(R.id.verify_via_call) as TextView

        verify_via_call!!.typeface = fontStyle(this,"SemiBold")

        verify_enter_otpBT = findViewById(R.id.verify_enter_otpBT) as TextView

        verify_enter_otpBT!!.typeface = fontStyle(this,"SemiBold")

        verify_enter_otpBT!!.setOnClickListener(this)

        txt_pin_entry= findViewById(R.id.txt_pin_entry) as PinEntryEditText

        txt_pin_entry!!.typeface = fontStyle(this,"")

        verify_via_call!!.enabled = false

        verify_via_call!!.setOnClickListener(this)

        //txt_pin_entry!!.setText(getRandomNumberString())

        if (txt_pin_entry!!.text.toString().length == 6) {

            verify_enter_otpBT!!.setBackgroundResource(R.drawable.verify_radius_button_orange)

            verify_via_call!!.setTextColor(Color.parseColor("#e8e8e8"))

        } else {
            verify_enter_otpBT!!.setBackgroundResource(R.drawable.verify_radius_button_gray)

            verify_via_call!!.setTextColor(Color.parseColor("#e8e8e8"))
        }

        txt_pin_entry!!.setOnPinEnteredListener(OnPinEnteredListener { str ->
            if (str.toString().length == 6) {

                verify_enter_otpBT!!.setBackgroundResource(R.drawable.verify_radius_button_orange)

                verify_via_call!!.setTextColor(Color.parseColor("#e8e8e8"))

            } else {
                verify_enter_otpBT!!.setBackgroundResource(R.drawable.verify_radius_button_gray)

                verify_via_call!!.setTextColor(Color.parseColor("#e8e8e8"))
            }
        })
        val intent = intent

        if(intent != null) {
            if(intent.getStringExtra("otp") != null) {
                //txt_pin_entry!!.setText(intent.getStringExtra("otp"))
            }
            if(intent.getStringExtra("customerReferenceCode") != null) {
                customerReferenceCode=intent.getStringExtra("customerReferenceCode")
            }

            if(intent.getStringExtra("email") != null) {
                eMail=intent.getStringExtra("email")
            }
        }

        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                counter--
                if(counter.toString().length != 1) {
                    verifyTime!!.setText(java.lang.String.valueOf("00:" + counter))
                }else{
                    verifyTime!!.setText(java.lang.String.valueOf("00:0" + counter))
                }

            }

            override fun onFinish() {
                verifyTime!!.setText("00:00")
                verify_via_call!!.setTextColor(Color.parseColor("#7DC77D"))

                verify_via_call!!.enabled = true
            }
        }.start()
    }

    override fun onClick(view: View?) {


        when(view!!.id)
        {

            R.id.verify_enter_otpBT ->
            {
                val obj = JSONObject()
                obj.put("customerReferenceCode", customerReferenceCode)
                obj.put("verificationCode", txt_pin_entry!!.text)
                Log.v("Json", "Value" + obj)
                RequestManager.setCustomerVerify(this, obj, this);

                loadingScreen(this)

            }

            R.id.verify_via_call ->
            {
                val obj = JSONObject()
                obj.put("email", eMail)
                Log.v("Json", "Value" + obj)
                RequestManager.setResendOTP(this, obj, this);

                loadingScreen(this)

            }
        }
    }

    fun getRandomNumberString(): String? { // It will generate 6 digit random Number.
        // from 0 to 999999
        val rnd = Random()
        val number: Int = rnd.nextInt(999999)
        // this will convert any number sequence into 6 character.
        return String.format("%06d", number)
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

        textSuccess!!.text = "Registration confirm successfully"

        val okText = dialog.findViewById<View>(R.id.okText) as TextView

        okText!!.typeface = fontStyle(context,"")

        okText!!.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)

            finish()

            dialog.cancel()
        })

        dialog.show()

        //dialog.cancel()
    }
    override fun onResponseReceived(responseObj: Any?, requestType: Int) {

        loadingScreenClose(this)
        if(responseObj != null)
        {
            if(requestType == Constant.MEMBER_getVerifyCustomer_URL_RQ)
            {
                showToast(this,(responseObj as BaseRS).message)

                if((responseObj as BaseRS).status.equals("5010")) {

                    //localstorageUserInfo(this,(responseObj as BaseRS).token,(responseObj as BaseRS).user_details!!.firstname,(responseObj as BaseRS).user_details!!.email,(responseObj as BaseRS).user_details!!.mobile,(responseObj as BaseRS).user_address!!.address1,"")


                    successDialog(this,""+(responseObj as BaseRS).message)
                }
            }else if(requestType == Constant.MEMBER_resendOtp_URL_RQ)
            {
                showToast(this,(responseObj as BaseRS).message)

                if((responseObj as BaseRS).status.equals("5053")) {

                    //localstorageUserInfo(this,(responseObj as BaseRS).token,(responseObj as BaseRS).user_details!!.firstname,(responseObj as BaseRS).user_details!!.email,(responseObj as BaseRS).user_details!!.mobile,(responseObj as BaseRS).user_address!!.address1,"")

                    //var intent = Intent(this, LoginActivity::class.java)

                    //txt_pin_entry!!.setText((responseObj as BaseRS).verificationCode)


                    ///intent.putExtra("email",""+emailId)

                    //startActivity(intent)


                }
            }
        }
    }
}


