package com.lieferin_global.Fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import com.squareup.picasso.Picasso
import org.json.JSONObject


class DetailsTableBookingpageFragment : Fragment(),View.OnClickListener,ResponseListener {

    var rootView : View? = null

    var Img: String=""

    var moreInfoLayout : RelativeLayout? = null

    var bannerImg:ImageView? = null

    var back:ImageView? = null

    var dishTitle: TextView? = null

    var dishDescription: TextView? = null

    var dishTiming: TextView? = null

    var dishOpen: TextView? = null

    var dishPrice: TextView? = null

    var ratingTV: TextView? = null

    var reviewTV : TextView? = null

    var reviewDescriptionTV : TextView? = null

    var moreInfo: TextView? = null

    var addressTV : TextView? = null

    var moreInfoDescription: TextView? = null

    var tableBooking: TextView? = null

    var tableBookingDescription : TextView? = null

    var addressDesTV : TextView? = null

    var copyIV : ImageView? = null

    var copyLocationTV : TextView? = null

    var getIV : ImageView? = null

    var getDirectionTV : TextView? = null

    var tableBookingRR : RelativeLayout? = null

    var moreInfoDescriptionLayout : RelativeLayout? = null

    var descriptionInfo : TextView? =null

    fun DetailsTableBookingpageFragment() {}

    var callBlacklisting: CallBlacklisting? = null

    var title: String? = null

    var dbHelper : DBHelper? = null

    var copyLocationLL : LinearLayout? = null

    var getDirectionLL : LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_details_table_bookingpage, container, false)

        copyLocationLL = rootView!!.findViewById(R.id.copyLocationLL) as LinearLayout

        copyLocationLL!!.setOnClickListener(this)

        getDirectionLL = rootView!!.findViewById(R.id.getDirectionLL) as LinearLayout

        getDirectionLL!!.setOnClickListener(this)


        dbHelper = DBHelper(activity!!)

        bannerImg = rootView!!.rootView!!.findViewById(R.id.bannerImg) as ImageView

        Picasso.with(activity!!).load(R.drawable.img_1).resize(450,450).into(bannerImg)

        tableBookingRR  = rootView!!.findViewById(R.id.tableBookingRR) as RelativeLayout

        tableBookingRR!!.setOnClickListener(this)

        back = rootView!!.findViewById(R.id.back) as ImageView

        back!!.setOnClickListener(this)

        back!!.setColorFilter(
            colorIcon(activity!!,R.color.colorWhite,R.drawable.abc_ic_ab_back_material,back!!),
            PorterDuff.Mode.SRC_ATOP)

        copyIV = rootView!!.findViewById(R.id.copyIV) as ImageView

        copyIV!!.setColorFilter(
            colorIcon(activity!!,R.color.redColor,R.drawable.copy,copyIV!!),
            PorterDuff.Mode.SRC_ATOP)


        getIV = rootView!!.findViewById(R.id.getIV) as ImageView

        getIV!!.setColorFilter(
            colorIcon(activity!!,R.color.redColor,R.drawable.copy,getIV!!),
            PorterDuff.Mode.SRC_ATOP)

        dishTitle = rootView!!.findViewById(R.id.dishTitle) as TextView

        dishDescription = rootView!!.findViewById(R.id.dishDescription) as TextView

        dishTiming = rootView!!.findViewById(R.id.dishTiming) as TextView

        dishOpen = rootView!!.findViewById(R.id.dishOpen) as TextView

        dishPrice = rootView!!.findViewById(R.id.dishPrice) as TextView

        ratingTV = rootView!!.findViewById(R.id.ratingTV) as TextView

        reviewTV = rootView!!.findViewById(R.id.reviewTV) as TextView

        reviewTV!!.typeface = fontStyle(activity!!,"SemiBold")

        reviewDescriptionTV = rootView!!.findViewById(R.id.reviewDescriptionTV) as TextView

        reviewDescriptionTV!!.typeface = fontStyle(activity!!,"Light")

        moreInfo = rootView!!.findViewById(R.id.moreInfo) as TextView

        moreInfo!!.typeface = fontStyle(activity!!,"")

        moreInfoDescription = rootView!!.findViewById(R.id.moreInfoDescription) as TextView

        moreInfoDescription!!.typeface = fontStyle(activity!!,"Light")

        moreInfoLayout = rootView!!.findViewById(R.id.moreInfoLayout) as RelativeLayout

        moreInfoLayout!!.setOnClickListener(this)

        tableBooking = rootView!!.findViewById(R.id.tableBooking) as TextView

        tableBooking!!.typeface = fontStyle(activity!!,"")

        tableBookingDescription = rootView!!.findViewById(R.id.tableBookingDescription) as TextView

        tableBookingDescription!!.typeface = fontStyle(activity!!,"Light")

        addressTV = rootView!!.findViewById(R.id.addressTV) as TextView

        addressTV!!.typeface = fontStyle(activity!!,"")

        addressDesTV = rootView!!.findViewById(R.id.addressDesTV) as TextView

        addressDesTV!!.typeface = fontStyle(activity!!,"")

        copyLocationTV = rootView!!.findViewById(R.id.copyLocationTV) as TextView

        copyLocationTV!!.typeface = fontStyle(activity!!,"")

        getDirectionTV = rootView!!.findViewById(R.id.getDirectionTV) as TextView

        getDirectionTV!!.typeface = fontStyle(activity!!,"")

        dishTitle!!.setTypeface(fontStyle(activity!!, ""))

        dishDescription!!.setTypeface(fontStyle(activity!!, ""))

        dishTiming!!.setTypeface(fontStyle(activity!!, ""))

        dishOpen!!.setTypeface(fontStyle(activity!!, ""))

        dishPrice!!.setTypeface(fontStyle(activity!!, ""))

        //dishPrice!!.setText("500 for two people (Approx)")

        ratingTV!!.setTypeface(fontStyle(activity!!, ""))

        ratingTV!!.setText("4.0")

        val bundle = this.arguments
        if (bundle != null) {
            title = bundle.getString("Title")
        }

        moreInfoDescriptionLayout = rootView!!.findViewById(R.id.moreInfoDescriptionLayout) as RelativeLayout

        descriptionInfo = rootView!!.findViewById(R.id.descriptionInfo) as TextView

        descriptionInfo!!.typeface = fontStyle(activity!!,"Light")

        val obj = JSONObject()
        //obj.put("token", ""+ dbHelper!!.getUserDetails().token)
        obj.put("restaurantReferenceCode", ""+title)

        Log.v("Json", "Value" + obj)
        RequestManager.setTableReservationRestarunt(activity, obj, this);


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
            R.id.tableBookingRR ->{
                val bundle = Bundle()
                bundle.putString("Title", title)
                callBlacklisting!!.fragmentChange("Table Booking",bundle)
            }

            R.id.copyLocationLL ->{
                val clipboard =
                    context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Copied Text", addressDesTV!!.text.toString())
                clipboard.setPrimaryClip(clip)
                showToast(activity!!,"Copied")
            }
            R.id.getDirectionLL ->{
                val gmmIntentUri =
                    Uri.parse("geo:0,0?q="+addressDesTV!!.text.toString())
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }

            R.id.back ->{
                callBlacklisting!!.fragmentBack("")
            }

            R.id.moreInfoLayout->{

                if(moreInfoDescriptionLayout!!.visibility == View.GONE)
                {
                    moreInfoDescriptionLayout!!.visibility = View.VISIBLE
                }else{
                    moreInfoDescriptionLayout!!.visibility = View.GONE
                }
            }
        }

    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {

        if (responseObj != null) {
            if (requestType == Constant.MEMBER_tableReservtionRestaurant_URL_RQ) {

                if((responseObj as BaseRS).tableReservationData!!.size != 0) {

                    dishTitle!!.setText("" + (responseObj as BaseRS).tableReservationData!!.get(0).restaurantName)

                    dishDescription!!.setText(
                        "Quick Bites - " + (responseObj as BaseRS).tableReservationData!!.get(
                            0
                        ).cuisionName
                    )

                    descriptionInfo!!.text = (responseObj as BaseRS).tableReservationData!!.get(0).description

                    dishTiming!!.setText("" + (responseObj as BaseRS).tableReservationData!!.get(0).town)

                    dishPrice!!.setText(
                        "" + addPriceDouble(
                            "" + (responseObj as BaseRS).tableReservationData!!.get(
                                0
                            ).price, 2
                        ).toString() + " for two people (Approx)"
                    )
                    dishOpen!!.setText("Open now")
                    ratingTV!!.setText("" + (responseObj as BaseRS).tableReservationData!!.get(0).rating)

                    var str =
                        (responseObj as BaseRS).tableReservationData!!.get(0).restaurantImages

                    Log.v("===]]'''']" + str, "====")

                    str = str!!.replace("[", "").toString()
                    str = str!!.replace("]", "").toString()
                    val arrOfStr =
                        str.split(",")

                    Log.v("===]]]" + arrOfStr[0], "====")

                    if (!arrOfStr[0].equals("")) {

                        Img =
                            (responseObj as BaseRS).restaurantPath + "" + (responseObj as BaseRS).tableReservationData!!.get(
                                0
                            ).folderName + "/" + arrOfStr[0]

                        Picasso.with(context)
                            .load(
                                (responseObj as BaseRS).restaurantPath + "" + (responseObj as BaseRS).tableReservationData!!.get(
                                    0
                                ).folderName + "/" + arrOfStr[0]
                            )
                            .resize(450, 450).transform(RoundedTransformation(16, 0))
                            .placeholder(R.drawable.restaurant_placeholder)
                            .into(bannerImg)

                        Picasso.with(activity!!).load(
                            (responseObj as BaseRS).restaurantPath + "" + (responseObj as BaseRS).tableReservationData!!.get(
                                0
                            ).folderName + "/" + arrOfStr[0]
                        ).resize(450, 450).placeholder(R.drawable.restaurant_placeholder)
                            .into(bannerImg)

                        Picasso.with(activity!!).load(
                            (responseObj as BaseRS).restaurantPath + "" + (responseObj as BaseRS).tableReservationData!!.get(
                                0
                            ).folderName + "/" + arrOfStr[0]
                        ).resize(450, 450).placeholder(R.drawable.restaurant_placeholder)
                            .into(bannerImg)
                    }

                    addressDesTV!!.text =
                        (responseObj as BaseRS).tableReservationData!!.get(0).street + " , " + (responseObj as BaseRS).tableReservationData!!.get(
                            0
                        ).town
                }
            }
        }
    }
}
