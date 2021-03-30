package com.lieferin_global.Activity

import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.CardListViewAdapter
import com.lieferin_global.Fragment.StripePaymentGateway
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.isTableBooking
import com.lieferin_global.Utility.Constant.qualityTable
import com.lieferin_global.Utility.Constant.totalPriceTable
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import org.json.JSONObject
import java.util.*

class CardDetailsActivity : AppCompatActivity(),View.OnClickListener, ResponseListener,CardListViewAdapter.CallbackDataAdapter {

    var rootView: View? = null

    var notificationTitle: TextView? = null

    var back: ImageView? = null

    var addCard: TextView? = null

    var addNewCard: LinearLayout? = null

    var notificationRecyclerView: RecyclerView? = null

    var sortPageAdapter: CardListViewAdapter? = null

    var adapterSort: MutableList<AdapterModel> = ArrayList()

    var adapterCategories2: MutableList<Product> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    var dbHelper:DBHelper? = null

    var selectCard: String? = "0"

    var payNowTV : TextView? = null


    var total_value: String = ""

    var address_Reference = ""

    var itemValue = ""

    var cardName = ""

    var cardNo = ""

    var expriyDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_save_card)

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

       /* var header = findViewById(R.id.header) as LinearLayout

        header!!.setOnClickListener(this)*/

        payNowTV = findViewById(R.id.payNowTV) as TextView

        payNowTV!!.typeface = fontStyle(this, "SemiBold")

        payNowTV!!.setOnClickListener(this)


        if(Constant.AppType!!.equals("0")) {

            payNowTV!!.setBackgroundResource(R.drawable.home_radius_button_orange)
        }else{

            payNowTV!!.setBackgroundResource(R.drawable.home_radius_button_green)
        }

        notificationTitle = findViewById(R.id.notificationTitle) as TextView

        notificationTitle!!.typeface = fontStyle(this, "SemiBold")

        addCard = findViewById(R.id.addCard) as TextView

        addCard!!.typeface = fontStyle(this, "")

        addNewCard = findViewById(R.id.addNewCard) as LinearLayout

        addNewCard!!.setOnClickListener(this)

        back = findViewById(R.id.back) as ImageView

        back!!.setColorFilter(
            colorIcon(
                this,
                R.color.colorBlack,
                R.drawable.abc_ic_ab_back_material,
                back!!
            ), PorterDuff.Mode.SRC_ATOP
        )

        back!!.setOnClickListener(this)

        notificationRecyclerView = findViewById(R.id.myCardRecyclerView) as RecyclerView

        sortPageAdapter = CardListViewAdapter(this, adapterSort)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        notificationRecyclerView!!.layoutManager = mLayoutManager

        notificationRecyclerView!!.itemAnimator!!.addDuration = 5000

        sortPageAdapter!!.setCallback(this)

        notificationRecyclerView!!.isNestedScrollingEnabled = false

        notificationRecyclerView!!.adapter = sortPageAdapter

        //showDataSortBy()
        webService()

        val intent = intent

        if (intent != null) {
            if (intent.getStringExtra("Total Amount") != null) {
                total_value = intent.getStringExtra("Total Amount")
            }

            if (intent.getStringExtra("Address_Reference") != null) {
                address_Reference = intent.getStringExtra("Address_Reference")
                Log.v("pincode555","===="+address_Reference)
            }

            if (intent.getStringExtra("item") != null) {
                itemValue = intent.getStringExtra("item")
            }
        }
    }

    fun webService()
    {
        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        RequestManager.setCardListActivity(this, obj, this);

        Log.v("kkkk"+obj,"33333")
    }



    fun showDataSortBy() {
        if (adapterSort.size != 0) {
            adapterSort.clear()
        }

        adapterModel = AdapterModel(
            R.drawable.kfc,
            "Master Card",
            "XXXX XXXX XXXX 1234",
            "http://www.pngall.com/wp-content/uploads/2016/07/Mastercard-Download-PNG.png",
            "Save 60 %",
            " 976",
            " 425",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterCategories2
        )
        adapterSort.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.kfc,
            "Visa",
            "XXXX XXXX XXXX 1234",
            "http://icons.iconarchive.com/icons/designbolts/credit-card-payment/256/Visa-icon.png",
            "Save 60 %",
            " 976",
            " 425",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterCategories2
        )
        adapterSort.add(adapterModel)


        sortPageAdapter!!.notifyDataSetChanged()
    }

    override fun onClick(view: View?) {

        when (view!!.id) {
            R.id.addNewCard -> {
                //callBlacklisting!!.fragmentChange("CardDetails", null)
                startActivity(Intent(this, CardEnterData::class.java))
                finish()
            }
            R.id.back->{
                finish()
            }
            R.id.payNowTV->{
                if(!selectCard!!.equals("0")) {
                    if(isTableBooking!!.equals("0")) {
                        var intent = Intent(this, StripePaymentGateway::class.java)

                        intent.putExtra("Total Amount", "" + total_value)

                        intent.putExtra("Address_Reference", "" + address_Reference)

                        intent.putExtra("item", "" + itemValue)

                        intent.putExtra("Card Name", "" + cardName)

                        intent.putExtra("Card No", "" + cardNo)

                        intent.putExtra("ExpiryDate", "" + expriyDate)

                        startActivity(intent)
                        Log.v("pincode5551","===="+address_Reference)
                    }else{
                        var intent = Intent(this, StripePaymentGatewayTableBooking::class.java)

                        intent.putExtra("Total Amount", "" + totalPriceTable)

                        intent.putExtra("Address_Reference", "" + address_Reference)

                        intent.putExtra("item", "" + qualityTable)

                        intent.putExtra("Card Name", "" + cardName)

                        intent.putExtra("Card No", "" + cardNo)

                        intent.putExtra("ExpiryDate", "" + expriyDate)

                        startActivity(intent)
                    }
                }else{
                    showToast(this,"Please select card")
                }
            }
        }

    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if (adapterSort.size != 0) {
            adapterSort.clear()
        }
        if (responseObj != null) {
            //loadingScreenClose(activity)
            if (requestType == Constant.MEMBER_CardList_URL_RQ) {

                if((responseObj as BaseRS).status.equals("5031")) {
                    for (i in 0 until (responseObj as BaseRS).cardInfo!!.size) {

                        adapterModel = AdapterModel(R.drawable.img_5, ""+(responseObj as BaseRS).cardInfo!!.get(i).cardType, ""+(responseObj as BaseRS).cardInfo!!.get(i).cardNo, ""+(responseObj as BaseRS).cardInfo!!.get(i).nameOnCard, ""+(responseObj as BaseRS).cardInfo!!.get(i).expiryDate, "", "0", "", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterCategories2)
                        adapterSort.add(adapterModel)

                    }

                    sortPageAdapter!!.notifyDataSetChanged()
                }

            }
        }
    }

    override fun setOnMaterial(userId: AdapterModel?) {

    }

    override fun setOnFavourite(userId: AdapterModel?, id: Int?) {
        selectCard = "1"
        for (i in 0 until adapterSort.size) {

            adapterSort.get(i).offer = "0"

        }
        adapterSort.get(id!!).offer = "1"

        sortPageAdapter!!.notifyDataSetChanged()

        cardName = userId!!.offerPrice!!

        cardNo = userId!!.price!!

        expriyDate = userId!!.offerPercentage!!
    }

    override fun setOnCardDelete(position: String?) {

    }
}