package com.lieferin_global.Fragment

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.MemberOrderListAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList

import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.BookingType
import com.lieferin_global.Utility.Constant.isAdminString
import com.lieferin_global.Utility.Constant.isPayType
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import org.json.JSONObject
import java.util.ArrayList

class MemberDetailsFragment : Fragment(),View.OnClickListener,ResponseListener,MemberOrderListAdapter.CallbackDataAdapter {

    var rootView : View? = null

    var orderBill_Title : TextView? = null

    var tableReservation_back : ImageView? = null

    var tableBookingStatus : String? = null

    var tableBookingRefence : String? = null

    var orderListAdapter: MemberOrderListAdapter? = null

    var adapterProduct: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    internal lateinit var productModel: Product

    var memberListDetails: RecyclerView? = null

    var callBlacklisting: CallBlacklisting? = null

    var title:String? = null

    var dbHelper : DBHelper?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView =  inflater.inflate(R.layout.fragment_member_details, container, false)

        dbHelper = DBHelper(activity)

        tableReservation_back = rootView!!.findViewById(R.id.tableReservation_back)

        tableReservation_back!!.setOnClickListener(this)

        var header = rootView!!.findViewById(R.id.header) as RelativeLayout

        header!!.setOnClickListener(this)

        tableReservation_back!!.setColorFilter(
            colorIcon(activity!!,R.color.colorBlack,R.drawable.abc_ic_ab_back_material,tableReservation_back!!),
            PorterDuff.Mode.SRC_ATOP)

        orderBill_Title = rootView!!.findViewById(R.id.orderBill_Title)

        orderBill_Title!!.typeface = fontStyle(activity!!,"")

        val bundle = this.arguments
        if (bundle != null) {
            tableBookingRefence = bundle.getString("name")

            tableBookingStatus = bundle.getString("BookingStatus")

            title = bundle.getString("title")
        }

        memberListDetails = rootView!!.findViewById(R.id.memberListDetails)

        orderListAdapter = MemberOrderListAdapter(activity!!,adapterProduct)

        memberListDetails!!.setHasFixedSize(true)

        memberListDetails!!.setLayoutManager(
            LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        )

        orderListAdapter!!.setCallback(this)

        memberListDetails!!.isNestedScrollingEnabled = false
        memberListDetails!!.setAdapter(orderListAdapter!!)

        webService()

        return rootView
    }

    fun webService(){

        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        obj.put("tableBookingReferenceCode", ""+ tableBookingRefence)

        RequestManager.setBookingMemberDataList(activity, obj, this);

        Log.i("kkkk"+obj,"33333")

        Log.i("kkkk"+tableBookingRefence,"33333")

        //loadingScreen(activity)
    }

    override fun onClick(p0: View?) {

    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if (adapterProduct.size != 0) {
            adapterProduct.clear()
        }
        if (responseObj != null) {
            //loadingScreenClose(activity)
            if (requestType == Constant.MEMBER_tableBookingMemberData_RQ) {

                for (i in 0 until (responseObj as BaseRS).memberData!!.size) {
                    productModel = Product(R.drawable.img_1, ""+(responseObj as BaseRS).memberData!!.get(i).name, ""+(responseObj as BaseRS).memberData!!.get(i).email,""+(responseObj as BaseRS).memberData!!.get(i).mobile,""+ dateConversionVal((responseObj as BaseRS).memberData!!.get(i).created_at),""+(responseObj as BaseRS).memberData!!.get(i).payType ,"Pick Menu","Delete",""+tableBookingStatus,""+(responseObj as BaseRS).memberData!!.get(i).memberStatus,""+(responseObj as BaseRS).memberData!!.get(i).tableMemberReferenceCode,""+(responseObj as BaseRS).memberData!!.get(i).isBookingAdmin,""+(responseObj as BaseRS).memberData!!.get(i).bookingAmount,""+(responseObj as BaseRS).memberData!!.get(i).paymentMode,"","","","",adapterProductList)
                    adapterProduct.add(productModel)
                }
                orderListAdapter!!.notifyDataSetChanged()
            }else if (requestType == Constant.MEMBER_removeTableBookingMember_RQ)
            {
                showToast(activity!!,(responseObj as BaseRS).message)
                if((responseObj as BaseRS).status.equals("5225"))
                {
                 webService()
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }

    override fun setOnMaterial(id: String, adapterModels: Product?) {
        if(id!!.equals("Pick Menu"))
        {
            val bundle = Bundle()
            bundle.putString("Title", title)

            bundle.putString("tableMemberCode", adapterModels!!.menuImages)

            callBlacklisting!!.fragmentChange("Detail Page",bundle)

            BookingType = ""+adapterModels!!.menuImages

            isAdminString =""+adapterModels!!.type

            isPayType =""+adapterModels!!.allergy1

            Log.v("oooooo"+isAdminString," ==== "+isPayType)

        }else if(id!!.equals("Delete"))
        {
            val obj = JSONObject()
            obj.put("token", ""+ dbHelper!!.getUserDetails().token)

            obj.put("tableMemberReferenceCode", ""+ adapterModels!!.menuImages)

            Log.i("t "+adapterModels!!.menuImages,"==")

            RequestManager.setRemoveTableBookingMember(activity, obj, this);
        }else if(id!!.equals("Pay Now"))
        {
            val bundle = Bundle()
            bundle.putString("Title", title)

            bundle.putString("tableMemberCode", adapterModels!!.menuImages)

            bundle.putString("pay", "1")

            callBlacklisting!!.fragmentChange("View Menu",bundle)
        }else if(id!!.equals("View Menu"))
        {
            val bundle = Bundle()
            bundle.putString("Title", title)

            bundle.putString("tableMemberCode", adapterModels!!.menuImages)

            bundle.putString("pay", "0")

            callBlacklisting!!.fragmentChange("View Menu",bundle)
        }
    }

    fun webServiceDelete(){

        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        obj.put("tableBookingReferenceCode", ""+ tableBookingRefence)

        RequestManager.setBookingMemberDataList(activity, obj, this);

        Log.v("kkkk"+obj,"33333")

        //loadingScreen(activity)
    }

}
