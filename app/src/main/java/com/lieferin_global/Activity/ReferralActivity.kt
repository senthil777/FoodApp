package com.lieferin_global.Activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.lieferin_global.R
import com.lieferin_global.Utility.Constant
import com.lieferin_global.Utility.fontStyle
import com.rentaloo.CallBack.CallBlacklisting

class ReferralActivity : Fragment(),View.OnClickListener {

    var tableReservation_back : ImageView? = null

    var orderBill_Title : TextView? = null

    var orderDescription_Type : TextView? = null

    var rootView : View? = null

    var callBlacklisting: CallBlacklisting? = null

    var buttonText : TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_referral, container, false)

        var header = rootView!!.findViewById(R.id.header) as RelativeLayout

        header!!.setOnClickListener(this)

        tableReservation_back = rootView!!.findViewById(R.id.tableReservation_back) as ImageView

        tableReservation_back!!.setOnClickListener(this)

        orderBill_Title = rootView!!.findViewById(R.id.orderBill_Title) as TextView

        orderBill_Title!!.typeface = fontStyle(activity!!,"")

        orderDescription_Type = rootView!!.findViewById(R.id.orderDescription_Type) as TextView

        orderDescription_Type!!.typeface = fontStyle(activity!!,"")

        buttonText = rootView!!.findViewById(R.id.buttonText) as TextView

        buttonText!!.typeface = fontStyle(activity!!,"SemiBold")

        buttonText!!.setOnClickListener(this)
        if(Constant.AppType.equals("0")) {


            buttonText!!.setBackgroundResource(R.drawable.home_radius_button_orange)
        }else{
            buttonText!!.setBackgroundResource(R.drawable.home_radius_button_green)
        }



        return rootView;
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }

    override fun onClick(view: View?) {

        when(view!!.id)
        {
            R.id.tableReservation_back->{

                callBlacklisting!!.fragmentBack("")
            }

            R.id.buttonText -> {
                buttonText!!.setOnClickListener(this)

                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type="text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.lieferin_global")
                startActivity(Intent.createChooser(shareIntent,"Share via"))
            }
        }
    }
}
