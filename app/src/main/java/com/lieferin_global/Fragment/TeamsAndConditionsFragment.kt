package com.lieferin_global.Fragment

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.R
import com.lieferin_global.Utility.Constant
import com.lieferin_global.Utility.fontStyle
import com.lieferin_global.Utility.getImageValue1
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import org.json.JSONObject

class TeamsAndConditionsFragment : Fragment(),ResponseListener,View.OnClickListener {

    var rootView : View? = null

    var valueTV : TextView? = null

    var valueTileTV : TextView? = null

    var pageString: String? = null

    var callBlacklisting: CallBlacklisting? = null

    var tableReservation_back : ImageView? = null

    var orderBill_Title : TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_teams_and_conditions, container, false)

        tableReservation_back = rootView!!.findViewById(R.id.tableReservation_back)

        tableReservation_back!!.setOnClickListener(this)

        val bundle = this.arguments
        if (bundle != null) {
            pageString= ""+ bundle.getString("page")
        }

        valueTV = rootView!!.findViewById(R.id.valueTV);

        orderBill_Title = rootView!!.findViewById(R.id.orderBill_Title);

        orderBill_Title!!.typeface = fontStyle(activity!!,"SemiBold")

        orderBill_Title!!.text = ""+pageString

        val obj = JSONObject()

        if(pageString!!.equals("Terms and conditions")) {
            RequestManager.setTermsCondition(activity, obj, this);
        }else if(pageString!!.equals("Payments & Refunds")) {
            RequestManager.setRefundPolicy(activity, obj, this);
        }else if(pageString!!.equals("Cancellation Policy")) {
            RequestManager.setCancellationPolicy(activity, obj, this);
        }else if(pageString!!.equals("Privacy Policy")) {
            RequestManager.setPrivacyPolicy(activity, obj, this);
        }

        return rootView;
    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if (responseObj != null) {
            //loadingScreenClose(activity)
            if (requestType == Constant.MEMBER_LieferinTermsCondition_URL_RQ) {

                valueTV!!.setText(Html.fromHtml(""+(responseObj as BaseRS).termsAndCondition!!.description))
            }else if(requestType == Constant.MEMBER_lieferinRefundPolicy_URL_RQ) {

                valueTV!!.setText(Html.fromHtml(""+(responseObj as BaseRS).RefundPolicy!!.description))
            }else if(requestType == Constant.MEMBER_lieferin_privacy_policy_URL_RQ) {

                valueTV!!.setText(Html.fromHtml(""+(responseObj as BaseRS).privacyPolicy!!.description))
            }else if(requestType == Constant.MEMBER_lieferinCancellationPolicy_URL_RQ) {

                valueTV!!.setText(Html.fromHtml(""+(responseObj as BaseRS).cancellationPolicy!!.description))
            }
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }
    override fun onClick(view: View?) {

        when(view!!.id)
        {
            R.id.tableReservation_back -> {
                callBlacklisting!!.fragmentBack("")
            }
        }
    }
}