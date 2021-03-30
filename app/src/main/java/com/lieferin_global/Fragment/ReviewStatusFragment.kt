package com.lieferin_global.Fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import org.jetbrains.anko.textColor
import org.json.JSONObject


class ReviewStatusFragment : Fragment(),View.OnClickListener,ResponseListener {

    var rootView:View?=null

    var orderBill_Title : TextView? = null

    var orderDescription_Type : TextView? = null

    var help : TextView? = null

    var tableReservation_back : ImageView? = null

    var orderReceived : TextView? = null

    var orderNow : TextView? = null

    var orderBoyNow : TextView? = null

    var orderReceivedDescription : TextView? = null

    var orderReceivedBoyDescription : TextView? = null

    var ratingBar : RatingBar? = null

    var ratingBoyBar : RatingBar? = null

    var ratingBarGrocery : RatingBar? = null

    var ratingBarBoyGrocery : RatingBar? = null

    var descriptionET : EditText? = null

    var descriptionBoyET : EditText? = null

    var deliveryText : TextView? = null

    var reviewString : String? = null

    var reportBoyImg : ImageView? = null

    var orderBoyReceived : TextView? = null

    var orderBoy : LinearLayout? = null

    fun ReviewStatusFragment() {}

    var dbHelper : DBHelper? = null

    var deliveryType : String? = null

    var callBlacklisting: CallBlacklisting? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView =  inflater.inflate(R.layout.fragment_review_status, container, false)

        dbHelper = DBHelper(activity!!)

        orderBill_Title = rootView!!.findViewById(R.id.orderBill_Title) as TextView

        orderBill_Title!!.typeface = fontStyle(activity!!,"")

        orderBoyReceived = rootView!!.findViewById(R.id.orderBoyReceived) as TextView

        orderBoyReceived!!.typeface = fontStyle(activity!!,"")

        orderDescription_Type = rootView!!.findViewById(R.id.orderDescription_Type) as TextView

        orderDescription_Type!!.typeface = fontStyle(activity!!,"Light")

        help = rootView!!.findViewById(R.id.help) as TextView

        help!!.typeface = fontStyle(activity!!,"Light")

        orderBoy = rootView!!.findViewById(R.id.orderBoy) as LinearLayout

        orderReceived = rootView!!.findViewById(R.id.orderReceived) as TextView

        orderReceived!!.typeface = fontStyle(activity!!,"Light")

        orderNow = rootView!!.findViewById(R.id.orderNow) as TextView

        orderNow!!.typeface = fontStyle(activity!!,"Light")

        orderBoyNow = rootView!!.findViewById(R.id.orderBoyNow) as TextView

        orderBoyNow!!.typeface = fontStyle(activity!!,"Light")

        orderReceivedDescription = rootView!!.findViewById(R.id.orderReceivedDescription) as TextView

        orderReceivedDescription!!.typeface = fontStyle(activity!!,"Light")

        orderReceivedBoyDescription = rootView!!.findViewById(R.id.orderReceivedBoyDescription) as TextView

        orderReceivedBoyDescription!!.typeface = fontStyle(activity!!,"Light")

        ratingBar= rootView!!.findViewById(R.id.RatingBar) as RatingBar

        ratingBarGrocery= rootView!!.findViewById(R.id.RatingBarGrocery) as RatingBar

        ratingBoyBar= rootView!!.findViewById(R.id.RatingBoyBar) as RatingBar

        ratingBarGrocery= rootView!!.findViewById(R.id.RatingBarGrocery) as RatingBar

        ratingBarBoyGrocery= rootView!!.findViewById(R.id.RatingBarBoyGrocery) as RatingBar

        descriptionET= rootView!!.findViewById(R.id.descriptionET) as EditText

        descriptionET!!.typeface = fontStyle(activity!!,"")

        descriptionBoyET= rootView!!.findViewById(R.id.descriptionBoyET) as EditText

        descriptionBoyET!!.typeface = fontStyle(activity!!,"")

        deliveryText = rootView!!.findViewById(R.id.deliveryText) as TextView

        deliveryText!!.typeface = fontStyle(activity!!,"SemiBold")

        deliveryText!!.setOnClickListener(this)

        val bundle = this.arguments
        if (bundle != null) {
            orderDescription_Type!!.text =
                getString(R.string.Delivered)+" " + bundle.getString("quantity") + " "+getString(R.string.item)+" " + bundle.getString("total")



            orderBill_Title!!.text = getString(R.string.order_Title)+"#" + bundle.getString("orderCode")

            reviewString= ""+ bundle.getString("RefenceCode")

            deliveryType = ""+ bundle.getString("deliveryType")

            Log.v("pppp"+reviewString,"66666666")
        }

        reportBoyImg = rootView!!.findViewById(R.id.reportBoyImg);

        reportBoyImg!!.setColorFilter(colorIcon(activity!!,R.color.colorBlack,R.drawable.delivery_scooter_white,reportBoyImg!!))


        if(deliveryType.equals("1"))
        {
           orderBoy!!.visibility = View.VISIBLE
        }else{
           orderBoy!!.visibility = View.GONE

            descriptionBoyET!!.setText("1")
        }

        if(AppType.equals("0")) {

            deliveryText!!.setBackgroundResource(R.drawable.home_radius_button_orange)

            ratingBarGrocery!!.visibility = View.GONE

            ratingBarBoyGrocery!!.visibility = View.GONE

            ratingBar!!.visibility = View.VISIBLE

            ratingBoyBar!!.visibility = View.VISIBLE

            help!!.textColor = Color.parseColor("#ec272b")

        }else{
            deliveryText!!.setBackgroundResource(R.drawable.home_radius_button_green)

            ratingBarGrocery!!.visibility = View.VISIBLE

            ratingBarBoyGrocery!!.visibility = View.VISIBLE

            ratingBar!!.visibility = View.GONE

            ratingBoyBar!!.visibility = View.GONE
            help!!.textColor = Color.parseColor("#7DC77D")
        }

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
            R.id.deliveryText->{
                if(AppType.equals("0")) {
                    wedServicePage()

                }else{
                    wedServiceGrocery()
                    loadingScreen(activity)
                }
            }
        }

    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if (responseObj != null) {
            loadingScreenClose(activity)
            if (requestType == Constant.MEMBER_ratingStore_URL_RQ) {

                if ((responseObj as BaseRS).status.equals("5033")) {

                    callBlacklisting!!.fragmentChange("My Orders",null)

                }
            }else if (requestType == Constant.MEMBER_ratingGrocery_URL_RQ) {

                if ((responseObj as BaseRS).status.equals("5038")) {

                    callBlacklisting!!.fragmentChange("My Orders",null)

                }
            }

        }
    }

    fun wedServicePage(){
        val obj = JSONObject()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)
        obj.put("bookingOrderReferenceCode", ""+reviewString)

        obj.put("rating", ""+ratingBar!!.getRating())

        obj.put("driverRating", ""+ratingBoyBar!!.getRating())
        obj.put("driverFeedback", ""+descriptionBoyET!!.text.toString())
        Log.v("Json", "Value" + obj)
        if(descriptionET!!.text.toString().equals(""))
        {
            showToast(activity!!,"Please enter description")
        }else if(descriptionBoyET!!.text.toString().equals(""))
        {
            showToast(activity!!,"Please enter description")
        }else {
            obj.put("feedback", "" + descriptionET!!.text.toString())
            RequestManager.setRatingRestaurant(activity, obj, this);
            loadingScreen(activity)
        }

    }

    fun wedServiceGrocery(){
        val obj = JSONObject()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)
        obj.put("groceryOrderReferenceCode", ""+reviewString)
        obj.put("rating", ""+ratingBarGrocery!!.getRating())
        obj.put("driverRating", ""+ratingBarBoyGrocery!!.getRating())


        if(descriptionET!!.text.toString().equals(""))
        {
            showToast(activity!!,"Please enter description")
        }else if(descriptionBoyET!!.text.toString().equals(""))
        {
            showToast(activity!!,"Please enter description")
        }else{
            obj.put("feedback", ""+descriptionET!!.text.toString())

            obj.put("driverFeedback", ""+descriptionBoyET!!.text.toString())
            RequestManager.setRatingGrocery(activity, obj, this);
        }
        Log.v("Json", "Value" + obj)

    }
}