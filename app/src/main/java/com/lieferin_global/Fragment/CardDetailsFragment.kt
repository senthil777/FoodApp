package com.lieferin_global.Fragment

import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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


class CardDetailsFragment : Fragment(),View.OnClickListener,ResponseListener {

    var rootView: View? = null

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_card_details, container, false)

        dbHelper = DBHelper(activity)

        var header = rootView!!.findViewById(R.id.header) as LinearLayout

        header!!.setOnClickListener(this)

        notificationTitle = rootView!!.findViewById(R.id.notificationTitle) as TextView

        notificationTitle!!.typeface = fontStyle(activity!!, "SemiBold")

        sendTxt = rootView!!.findViewById(R.id.sendTxt) as TextView

        sendTxt!!.typeface = fontStyle(activity!!, "SemiBold")

        sendTxt!!.setOnClickListener(this)

        back = rootView!!.findViewById(R.id.back) as ImageView

        back!!.setColorFilter(
            colorIcon(
                activity!!,
                R.color.colorBlack,
                R.drawable.abc_ic_ab_back_material,
                back!!
            ), PorterDuff.Mode.SRC_ATOP
        )

        cardMultilineWidget = rootView!!.findViewById<CardMultilineWidget>(R.id.cardInputWidget)




        back!!.setOnClickListener(this)

        cardPay = rootView!!.findViewById(R.id.cardPay) as CardView

        cardNoText = rootView!!.findViewById(R.id.cardNoText) as TextView

        nameOnCardText = rootView!!.findViewById(R.id.nameOnCardText) as TextView

        expiryDateText = rootView!!.findViewById(R.id.expiryDateText) as TextView

        ccvText = rootView!!.findViewById(R.id.ccvText) as TextView

        cardNo = rootView!!.findViewById(R.id.cardNo) as EditText

        nameOnCard = rootView!!.findViewById(R.id.nameOnCard) as EditText

        expiryDate = rootView!!.findViewById(R.id.expiryDate) as EditText

        ccv = rootView!!.findViewById(R.id.ccv) as EditText


        cardNoText!!.typeface = fontStyle(activity!!, "SemiBold")

        nameOnCardText!!.typeface = fontStyle(activity!!, "SemiBold")

        expiryDateText!!.typeface = fontStyle(activity!!, "SemiBold")

        ccvText!!.typeface = fontStyle(activity!!, "SemiBold")

        cardNo!!.typeface = fontStyle(activity!!, "")

        nameOnCard!!.typeface = fontStyle(activity!!, "")

        expiryDate!!.typeface = fontStyle(activity!!, "")

        ccv!!.typeface = fontStyle(activity!!, "")

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
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
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
            showToast(activity!!, getString(R.string.cardValidation))
        }else if(expiryDate!!.text.length != 5)
        {
            showToast(activity!!, getString(R.string.expiryDateValidation))
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
                showToast(activity!!, "Please enter valid 16 card number")
            } else if (expiryDate!!.text.toString().length != 5) {
                showToast(activity!!, "Please enter valid expiry date ")
            } else if (ccv!!.text.toString().length != 3) {
                showToast(activity!!, "Please enter valid ccv number")
            } else if (!cardMultilineWidget!!.validateCardNumber()) {
                showToast(activity!!, "Invalid card")
            } else if (!cardMultilineWidget!!.validateAllFields()) {
                showToast(activity!!, "Invalid Expiry Date")
            } else {
                val obj = JSONObject()
                obj.put("token", "" + dbHelper!!.getUserDetails().token)

                obj.put("cardNo", "" + cardNo!!.text.toString())

                obj.put("nameOnCard", "" + nameOnCard!!.text.toString())

                obj.put("expiryDate", "" + expiryDate!!.text.toString())

                obj.put("cardType", "" + cardMultilineWidget!!.card!!.brand)

                RequestManager.setInsertCardList(activity, obj, this);

                loadingScreen(activity)

                Log.v("kkkk" + obj, "33333")
            }
        }
    }


    override fun onResponseReceived(responseObj: Any?, requestType: Int) {

        if (responseObj != null) {
            loadingScreenClose(activity)
            if (requestType == Constant.MEMBER_insertCardList_URL_RQ) {

                showToast(activity!!,(responseObj as BaseRS).message)

                if((responseObj as BaseRS).status.equals("5029")) {

                    callBlacklisting!!.fragmentChange("Payment",null)

                    //startActivity(Intent(this, DashBoardActivity::class.java))

                }

            }
        }
    }
}
