package com.lieferin_global.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.google.firebase.iid.FirebaseInstanceId
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import org.json.JSONObject
import java.util.*

class LoginActivity : AppCompatActivity(), View.OnClickListener, ResponseListener {

    var enterUserName: EditText? = null

    var dbHelper: DBHelper? = null

    var enterPassword: EditText? = null

    var loginButton: TextView? = null

    var accountTV: TextView? = null

    var forgetTV: TextView? = null

    var userImage: ImageView? = null

    var password: ImageView? = null

    var signINLB: TextView? = null

    var visible: LinearLayout? = null

    var telephonyManager: TelephonyManager? = null

    var homeString : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_main)

        dbHelper = DBHelper(this);

        val extras = intent.extras
        val userName: String?
        if (extras != null) {
            //showToast(this, "" + extras.getString("NotificationTitle"))
            if (extras.getString("page") != null) {

                homeString = "home"
            }
        }

        //telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        signINLB = findViewById(R.id.signINLB) as TextView

        signINLB!!.typeface = fontStyle(this, "SemiBold")

        enterUserName = findViewById(R.id.enterUserName) as EditText

        enterUserName!!.typeface = fontStyle(this, "")

        //enterUserName!!.setText("senthilyuvi55@gmail.com")

        enterPassword = findViewById(R.id.enterPassword) as EditText

        enterPassword!!.typeface = fontStyle(this, "")

        //enterPassword!!.setText("123456")

        loginButton = findViewById(R.id.loginButton) as TextView

        loginButton!!.typeface = fontStyle(this, "SemiBold")

        loginButton!!.setOnClickListener(this)

        accountTV = findViewById(R.id.accountTV) as TextView

        accountTV!!.typeface = fontStyle(this, "Light")

        accountTV!!.setOnClickListener(this)

        forgetTV = findViewById(R.id.forgetTV) as TextView

        forgetTV!!.typeface = fontStyle(this, "Light")

        forgetTV!!.setOnClickListener(this)

        userImage = findViewById(R.id.userImage) as ImageView

        userImage!!.setColorFilter(
            colorIcon(this, R.color.colorWhite, R.drawable.my_account, userImage!!),
            PorterDuff.Mode.SRC_ATOP
        )

        password = findViewById(R.id.password) as ImageView

        password!!.setColorFilter(
            colorIcon(this, R.color.colorWhite, R.drawable.password, password!!),
            PorterDuff.Mode.SRC_ATOP
        )

        visible = findViewById(R.id.visible) as LinearLayout

        visible!!.setVisibility(View.GONE)

        KeyboardVisibilityEvent.setEventListener(
            this,
            KeyboardVisibilityEventListener { isOpen ->
                // some code depending on keyboard visiblity status
                if (isOpen) {
                    visible!!.setVisibility(View.VISIBLE)
                } else {
                    visible!!.setVisibility(View.GONE)
                }
            })

        if (localgetUserInfoSlash(this, "nameKey").equals("")) {
            val config = resources.configuration
            val locale = Locale("en")
            Locale.setDefault(locale)
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
        } else {
            val config = resources.configuration
            val locale = Locale(localgetUserInfo("nameKey"))
            Locale.setDefault(locale)
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
        }

    }

    override fun onResume() {
        super.onResume()
    }

    @SuppressLint("MissingPermission", "NewApi")
    override fun onClick(view: View?) {

        when (view!!.id) {

            R.id.loginButton -> {
                if (enterUserName!!.text.toString().equals("")) {
                    showToast(this, "Please enter username")

                } else if (enterPassword!!.text.toString().equals("")) {
                    showToast(this, "Please enter password")
                } else {
                    val obj = JSONObject()
                    obj.put("email", enterUserName!!.text)
                    obj.put("password", enterPassword!!.text)

                    obj.put("fcmToken", "" + FirebaseInstanceId.getInstance().getToken())
                    obj.put("deviceId", "44141414414")
                    obj.put("deviceType", "1")
                    Log.v("Json", "Value" + obj)
                    RequestManager.setLogin(this, obj, this);
                    loadingScreen(this)
                }

                //
            }

            R.id.accountTV -> {
                Constant.StringName = ""

                Constant.StringMobileNumber = ""

                Constant.StringEmail = ""

                Constant.StringPassword = ""

                Constant.StringConfirmPassword = ""

                Constant.countryCode = ""

                Constant.countryCodeString = ""
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))

            }

            R.id.forgetTV -> {
                startActivity(Intent(this@LoginActivity, ForgetPasswordActivity::class.java))
            }
        }

    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if (responseObj != null) {
            loadingScreenClose(this)
            if (requestType == Constant.MEMBER_Login_URL_RQ) {
                showToast(this, (responseObj as BaseRS).message)

                if ((responseObj as BaseRS).status.equals("1002")) {

                    localstorageUserInfo(
                        this,
                        (responseObj as BaseRS).token,
                        (responseObj as BaseRS).details!!.name,
                        (responseObj as BaseRS).details!!.email,
                        (responseObj as BaseRS).details!!.mobile,
                        "",
                        (responseObj as BaseRS).details!!.customerReferenceCode,
                        (responseObj as BaseRS).userPath + "" + (responseObj as BaseRS).details!!.profilePicture
                    )

                    dbHelper!!.deleteUser()

                    dbHelper!!.addUser(
                        (responseObj as BaseRS).details!!,
                        "" + (responseObj as BaseRS).token!!,
                        "" + (responseObj as BaseRS).userPath,
                        ""
                    )

                    //startActivity(Intent(this,DashBoardActivity::class.java))

                    if(homeString!!.equals("")) {
                        finish()
                    }else{
                        Constant.AppType = "0"
                        if(Constant.AppType.equals("0")) {
                            startActivity(Intent(this, DashBoardActivity::class.java))
                        }else{
                            startActivity(Intent(this, DashBoardGrpceryActivity::class.java))
                        }
                        finish()
                    }
                }
            }
        }
    }
}
