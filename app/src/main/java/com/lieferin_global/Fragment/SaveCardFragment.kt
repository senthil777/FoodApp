package com.lieferin_global.Fragment

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.CardListAdapter
import com.lieferin_global.Adapter.CardListViewAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import org.json.JSONObject
import java.util.ArrayList

class SaveCardFragment : Fragment(), View.OnClickListener,ResponseListener,CardListViewAdapter.CallbackDataAdapter {

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

    fun SaveCardFragment() {}

    var callBlacklisting: CallBlacklisting? = null

    var dbHelper:DBHelper? = null

    var payNowTV : TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_save_card, container, false)

        dbHelper = DBHelper(activity)

        var header = rootView!!.findViewById(R.id.header) as LinearLayout

        header!!.setOnClickListener(this)

        notificationTitle = rootView!!.findViewById(R.id.notificationTitle) as TextView

        notificationTitle!!.typeface = fontStyle(activity!!, "SemiBold")

        addCard = rootView!!.findViewById(R.id.addCard) as TextView

        addCard!!.typeface = fontStyle(activity!!, "")

        addNewCard = rootView!!.findViewById(R.id.addNewCard) as LinearLayout

        addNewCard!!.setOnClickListener(this)

        back = rootView!!.findViewById(R.id.back) as ImageView

        back!!.setColorFilter(
            colorIcon(
                activity!!,
                R.color.colorBlack,
                R.drawable.abc_ic_ab_back_material,
                back!!
            ), PorterDuff.Mode.SRC_ATOP
        )

        payNowTV = rootView!!.findViewById(R.id.payNowTV)

        payNowTV!!.visibility = View.GONE

        back!!.setOnClickListener(this)

        notificationRecyclerView = rootView!!.findViewById(R.id.myCardRecyclerView) as RecyclerView

        sortPageAdapter = CardListViewAdapter(activity!!, adapterSort)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        notificationRecyclerView!!.layoutManager = mLayoutManager

        notificationRecyclerView!!.itemAnimator!!.addDuration = 5000

        sortPageAdapter!!.setCallback(this)

        notificationRecyclerView!!.isNestedScrollingEnabled = false

        notificationRecyclerView!!.adapter = sortPageAdapter

        //showDataSortBy()
        webService()
        return rootView
    }

    fun webService()
    {
        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        RequestManager.setCardList(activity, obj, this);

        loadingScreen(activity)

        Log.v("kkkk"+obj,"33333")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
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
                callBlacklisting!!.fragmentChange("CardDetails", null)
            }
            R.id.back->{
                callBlacklisting!!.fragmentBack("")
            }
        }

    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if (adapterSort.size != 0) {
            adapterSort.clear()
        }
        if (responseObj != null) {
            loadingScreenClose(activity)
            if (requestType == Constant.MEMBER_CardList_URL_RQ) {

                if((responseObj as BaseRS).status.equals("5031")) {
                    for (i in 0 until (responseObj as BaseRS).cardInfo!!.size) {

                        adapterModel = AdapterModel(R.drawable.img_5, ""+(responseObj as BaseRS).cardInfo!!.get(i).cardType, ""+(responseObj as BaseRS).cardInfo!!.get(i).cardNo, ""+(responseObj as BaseRS).cardInfo!!.get(i).nameOnCard, ""+(responseObj as BaseRS).cardInfo!!.get(i).expiryDate, ""+(responseObj as BaseRS).cardInfo!!.get(i).id, "0", "", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterCategories2)
                        adapterSort.add(adapterModel)

                    }

                    sortPageAdapter!!.notifyDataSetChanged()
                }

            }else if (requestType == Constant.MEMBER_deletCustomerAddress_URL_RQ) {

                loadingScreenClose(activity)
                if((responseObj as BaseRS).status.equals("5058"))
                {
                    showToast(activity!!,(responseObj as BaseRS).message)
                    webService()
                }
            }
        }
    }

    override fun setOnMaterial(userId: AdapterModel?) {

    }

    override fun setOnFavourite(userId: AdapterModel?, id: Int?) {

    }

    override fun setOnCardDelete(position: String?) {
        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        obj.put("cardId", ""+ position)

        RequestManager.setCardDeteleFragment(activity, obj, this);

        loadingScreen(activity)

        Log.v("ii "+position," === ")
    }
}
