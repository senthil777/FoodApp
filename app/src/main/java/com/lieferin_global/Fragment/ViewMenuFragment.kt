package com.lieferin_global.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.ItemMealsPageAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.isTableBooking
import com.lieferin_global.Utility.Constant.isTableBookingReferenceCode
import com.lieferin_global.Utility.Constant.qualityTable
import com.lieferin_global.Utility.Constant.totalPriceTable
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import org.json.JSONObject
import java.util.ArrayList

class ViewMenuFragment : Fragment(),View.OnClickListener,ResponseListener {

    var rootView : View? = null

    var orders_back : ImageView? = null

    var orders_Title : TextView? = null

    var callBlacklisting: CallBlacklisting? = null

    var viewMenuRecyclerView : RecyclerView? = null

    var tableMemberCode : String? = null

    var payNow : String? = null

    var specialMealsPageAdapter: ItemMealsPageAdapter? = null

    internal lateinit var adapterModel: AdapterModel

    internal lateinit var adapterProduct: Product

    var timeList: MutableList<String>? = ArrayList()

    internal var adapterTrending: MutableList<AdapterModel> = ArrayList<AdapterModel>()

    internal var adapterTrendingProduct: MutableList<Product> = ArrayList<Product>()

    internal var adapterTrendingProductList: MutableList<ProductList> = ArrayList<ProductList>()

    var payNowTV : TextView? = null

    var totalPayment : TextView? = null

    var totalValue : Double? = 0.0

    var totalToppinsValue = 0.0

    var dbHelper : DBHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView =inflater.inflate(R.layout.fragment_view_menu, container, false)

        dbHelper = DBHelper(activity)

        orders_Title = rootView!!.findViewById(R.id.orders_Title)

        orders_Title!!.setTypeface(fontStyle(activity!!,""))

        orders_back = rootView!!.findViewById(R.id.orders_back)

        orders_back!!.setOnClickListener(this)

        payNowTV = rootView!!.findViewById(R.id.payNowTV)

        payNowTV!!.typeface = fontStyle(activity!!,"SemiBold")

        payNowTV!!.setOnClickListener(this)

        totalPayment = rootView!!.findViewById(R.id.totalPayment)

        totalPayment!!.typeface = fontStyle(activity!!,"SemiBold")

        viewMenuRecyclerView = rootView!!.findViewById(R.id.viewMenuRecyclerView)

        specialMealsPageAdapter = ItemMealsPageAdapter(activity!!, adapterTrending)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity!!)

        viewMenuRecyclerView!!.layoutManager = mLayoutManager

        viewMenuRecyclerView!!.itemAnimator!!.addDuration = 5000

        //specialMealsPageAdapter!!.setCallback(this)

        viewMenuRecyclerView!!.adapter = specialMealsPageAdapter

        val bundle = this.arguments
        if (bundle != null) {
            tableMemberCode = bundle.getString("tableMemberCode")

            payNow = bundle.getString("pay")
        }
        if(payNow!!.equals("0"))
        {
           payNowTV!!.visibility = View.GONE
        }else{
            payNowTV!!.visibility = View.VISIBLE
        }
        timeList!!.add("10m")

        timeList!!.add("20m")

        timeList!!.add("30m")

        timeList!!.add("40m")

        timeList!!.add("50m")

        timeList!!.add("60m")

        isTableBooking = "1"
        webService()

        isTableBookingReferenceCode  = ""+tableMemberCode
        return rootView
    }
    fun webService(){

        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        obj.put("tableMemberReferenceCode", ""+ tableMemberCode)

        RequestManager.setMemberPickedMenusList(activity, obj, this);

        Log.v("kkkk"+tableMemberCode+" =","33333")

        loadingScreen(activity)
    }
    override fun onClick(view: View?) {

        when(view!!.id)
        {
            R.id.orders_back->{

                callBlacklisting!!.fragmentBack("")
            }
            R.id.payNowTV->{

                callBlacklisting!!.fragmentChange("Total Pay",null)
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if (responseObj != null) {
            loadingScreenClose(activity)
            if (requestType == Constant.MEMBER_memberPickedMenusList_URL_RQ) {
                for (i in 0 until (responseObj as BaseRS).memberPickedMenusData!!.size) {
                    totalValue = totalValue!! + (responseObj as BaseRS).memberPickedMenusData!!.get(i).menuPrice!!.toDouble()
                    if((responseObj as BaseRS).memberPickedMenusData!!.get(i).toppinsGroupJsonData != null)
                    {
                        for (j in 0 until (responseObj as BaseRS).memberPickedMenusData!!.get(i).toppinsGroupJsonData!!.toppinsGroupList!!.size) {
                            for (k in 0 until (responseObj as BaseRS).memberPickedMenusData!!.get(i).toppinsGroupJsonData!!.toppinsGroupList!!.get(j).toppinsList!!.size) {
                                totalToppinsValue = totalToppinsValue!!+(responseObj as BaseRS).memberPickedMenusData!!.get(i).toppinsGroupJsonData!!.toppinsGroupList!!.get(j).toppinsList!!.get(k).price!!.toDouble()
                                adapterProduct = Product(
                                    0,
                                    "" + (responseObj as BaseRS).memberPickedMenusData!!.get(i).toppinsGroupJsonData!!.toppinsGroupList!!.get(j).toppinsList!!.get(k).name,
                                    "" + (responseObj as BaseRS).memberPickedMenusData!!.get(i).toppinsGroupJsonData!!.toppinsGroupList!!.get(j).toppinsList!!.get(k).price,
                                    ""+ (responseObj as BaseRS).memberPickedMenusData!!.get(i).menuId,
                                    ""+ (responseObj as BaseRS).memberPickedMenusData!!.get(i).menuUserRequest,
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "","","","",
                                    adapterTrendingProductList!!
                                )
                                adapterTrendingProduct.add(adapterProduct)
                            }
                        }
                    }
                    adapterModel = AdapterModel(
                        0,
                        "" + (responseObj as BaseRS).memberPickedMenusData!!.get(i).itemName,
                        ""+ (responseObj as BaseRS).memberPickedMenusData!!.get(i).menuPrice ,
                        ""+ (responseObj as BaseRS).memberPickedMenusData!!.get(i).menuPrice,
                        ""+(responseObj as BaseRS).memberPickedMenusData!!.get(i).menuImages,
                        "Quantity : "+(responseObj as BaseRS).memberPickedMenusData!!.get(i).quantity,
                        ""+(responseObj as BaseRS).memberPickedMenusData!!.get(i).menuId,
                        ""+(responseObj as BaseRS).memberPickedMenusData!!.get(i).menuUserRequest,
                        "Order Id : #",
                        "Delivery Orders ()",
                        "3054 Ratings>",
                        "€ " ,
                        "1",
                        "",
                        "",
                        "",
                        "",
                        "",
                        0,
                        0,
                        0,
                        adapterTrendingProduct
                    )
                    adapterTrending.add(adapterModel)
                }

                specialMealsPageAdapter!!.notifyDataSetChanged()

                var total = totalValue!! + totalToppinsValue

                totalPayment!!.text = "Total : € "+total

                totalPriceTable =""+totalValue

                qualityTable =""+(responseObj as BaseRS).memberPickedMenusData!!.size
            }
        }
    }
}