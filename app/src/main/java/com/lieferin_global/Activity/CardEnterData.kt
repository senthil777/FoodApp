package com.lieferin_global.Activity

import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import com.stripe.android.view.CardMultilineWidget
import org.json.JSONObject
import java.util.*

class CardEnterData : AppCompatActivity(),View.OnClickListener, ResponseListener {

    var notificationTitle: TextView? = null

    var back: ImageView? = null

    var cardPay: CardView? = null

    var cardNoText: TextView? = null

    var nameOnCardText: TextView? = null

    var expiryDateText: TextView? = null

    var ccvText: TextView? = null

    var cardNo: EditText? = null

    var nameOnCard: EditText? = null

    var expiryDate: EditText? = null

    var ccv: EditText? = null

    var sendTxt: TextView? = null

    var isDelete = true

    var isDeleteValue = true

    var dbHelper : DBHelper? = null

    fun CardDetailsFragment() {}

    var cardMultilineWidget : CardMultilineWidget? = null

    var callBlacklisting: CallBlacklisting? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_card_details)

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

        var header = findViewById(R.id.header) as LinearLayout

        header!!.setOnClickListener(this)

        notificationTitle = findViewById(R.id.notificationTitle) as TextView

        notificationTitle!!.typeface = fontStyle(this, "SemiBold")

        sendTxt = findViewById(R.id.sendTxt) as TextView

        sendTxt!!.typeface = fontStyle(this, "SemiBold")

        sendTxt!!.setOnClickListener(this)

        back = findViewById(R.id.back) as ImageView

        back!!.setColorFilter(
            colorIcon(
                this,
                R.color.colorBlack,
                R.drawable.abc_ic_ab_back_material,
                back!!
            ), PorterDuff.Mode.SRC_ATOP
        )

        cardMultilineWidget = findViewById<CardMultilineWidget>(R.id.cardInputWidget)




        back!!.setOnClickListener(this)

        cardPay = findViewById(R.id.cardPay) as CardView

        cardNoText = findViewById(R.id.cardNoText) as TextView

        nameOnCardText = findViewById(R.id.nameOnCardText) as TextView

        expiryDateText = findViewById(R.id.expiryDateText) as TextView

        ccvText = findViewById(R.id.ccvText) as TextView

        cardNo = findViewById(R.id.cardNo) as EditText

        nameOnCard = findViewById(R.id.nameOnCard) as EditText

        expiryDate = findViewById(R.id.expiryDate) as EditText

        ccv = findViewById(R.id.ccv) as EditText


        cardNoText!!.typeface = fontStyle(this, "SemiBold")

        nameOnCardText!!.typeface = fontStyle(this, "SemiBold")

        expiryDateText!!.typeface = fontStyle(this, "SemiBold")

        ccvText!!.typeface = fontStyle(this, "SemiBold")

        cardNo!!.typeface = fontStyle(this, "")

        nameOnCard!!.typeface = fontStyle(this, "")

        expiryDate!!.typeface = fontStyle(this, "")

        ccv!!.typeface = fontStyle(this, "")

        expiryDate!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                cs: CharSequence,
                arg1: Int,
                arg2: Int,
                arg3: Int
            ) {
            }

            override fun beforeTextChanged(
                arg0: CharSequence,
                arg1: Int,
                before: Int,
                arg3: Int
            ) {
                if (before == 0) isDeleteValue = false else isDeleteValue = true
            }

            override fun afterTextChanged(s: Editable) {
                val source = s.toString()
                val length = source.length
                val stringBuilder = StringBuilder()
                stringBuilder.append(source)
                if (length > 0 && length % 3 == 0) {
                    if (isDeleteValue) stringBuilder.deleteCharAt(length - 1) else stringBuilder.insert(
                        length - 1,
                        "/"
                    )
                    expiryDate!!.setText(stringBuilder)
                    expiryDate!!.setSelection(expiryDate!!.getText().length)
                }
            }
        })

        cardNo!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                cs: CharSequence,
                arg1: Int,
                arg2: Int,
                arg3: Int
            ) {
            }

            override fun beforeTextChanged(
                arg0: CharSequence,
                arg1: Int,
                before: Int,
                arg3: Int
            ) {
                if (before == 0) isDelete = false else isDelete = true
            }

            override fun afterTextChanged(s: Editable) {
                val source = s.toString()
                val length = source.length
                val stringBuilder = StringBuilder()
                stringBuilder.append(source)
                if (length > 0 && length % 5 == 0) {
                    if (isDelete) stringBuilder.deleteCharAt(length - 1) else stringBuilder.insert(
                        length - 1,
                        " "
                    )
                    cardNo!!.setText(stringBuilder)
                    cardNo!!.setSelection(cardNo!!.getText().length)
                }
            }
        })
    }



    override fun onClick(view: View?) {
        when(view!!.id)
        {
            R.id.back->{

                callBlacklisting!!.fragmentBack("")
            }

            R.id.sendTxt->{
                webService()
            }
        }
    }

    fun webService()
    {
        if(cardNo!!.text.toString().replace(" ", "").length != 16)
        {
            showToast(this, getString(R.string.cardValidation))
        }else if(expiryDate!!.text.length != 5)
        {
            showToast(this, getString(R.string.expiryDateValidation))
        }else {
            cardMultilineWidget!!.setCardNumber(cardNo!!.text.toString())

            //cardInputWidget.


            cardMultilineWidget!!.setExpiryDate(
                expiryDate!!.text.toString().substring(0, 2).toInt(),
                expiryDate!!.text.toString().substring(3).toInt()
            )

            cardMultilineWidget!!.setCvcCode("746")

            Log.v("Error", "======" + cardMultilineWidget!!.validateCardNumber())
            if (cardNo!!.text.toString().replace(" ", "").length != 16) {
                showToast(this, getString(R.string.cardValidation))
            } else if (expiryDate!!.text.toString().length != 5) {
                showToast(this, getString(R.string.expiryDateValidation))
            } else if (ccv!!.text.toString().length != 3) {
                showToast(this, getString(R.string.cvvValidation))
            } else if (!cardMultilineWidget!!.validateCardNumber()) {
                showToast(this, getString(R.string.invalidCard))
            } else if (!cardMultilineWidget!!.validateAllFields()) {
                showToast(this, getString(R.string.invalidExpiryDate))
            } else {
                val obj = JSONObject()
                obj.put("token", "" + dbHelper!!.getUserDetails().token)

                obj.put("cardNo", "" + cardNo!!.text.toString())

                obj.put("nameOnCard", "" + nameOnCard!!.text.toString())

                obj.put("expiryDate", "" + expiryDate!!.text.toString())

                obj.put("cardType", "" + cardMultilineWidget!!.card!!.brand)

                RequestManager.setInsertCardListActivity(this, obj, this);

                loadingScreen(this)

                Log.v("kkkk" + obj, "33333")
            }
        }
    }


    override fun onResponseReceived(responseObj: Any?, requestType: Int) {

        if (responseObj != null) {
            loadingScreenClose(this)
            if (requestType == Constant.MEMBER_insertCardList_URL_RQ) {

                showToast(this,(responseObj as BaseRS).message)

                if((responseObj as BaseRS).status.equals("5029")) {

                    //callBlacklisting!!.fragmentChange("Payment",null)

                    startActivity(Intent(this, CardDetailsActivity::class.java))

                    finish()

                }

            }
        }
    }
}
