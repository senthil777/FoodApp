package com.lieferin_global.Activity

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextClock
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.CategoryListPageAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductListView
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderBillDetailsActivity : Fragment(),View.OnClickListener{

    var orderBill_Title: TextView? =null

    var adapterProductListView: ArrayList<ProductListView> = ArrayList()

    internal lateinit var productModel: Product

    var adapterProductListView1: ArrayList<ProductListView> = ArrayList()

    internal lateinit var productList: ProductListView

    var tableReservation_back : ImageView? = null

    var orderDescription_Type: TextView? =null

    var help: TextView? =null

    var toAddressTitle: TextView? =null

    var fromAddressTitle: TextView? =null

    var fromAddressTitleDescription: TextView? =null

    var toAddressTitleDescription: TextView? =null

    var orderDescription: TextView? =null

    var billDetailsTV: TextView? =null

    var billTV: TextView? =null

    var itemTV: TextView? =null

    var itemPriceTV: TextView? =null

    var restaurantTV: TextView? =null

    var restaurantPriceTV: TextView? =null

    var deliveryTV: TextView? =null

    var deliveryPriceTV: TextView? =null

    var totalTV: TextView? =null

    var totalPriceTV: TextView? =null

    var reorderTV: TextView? =null

    var tickImg: ImageView? =null

    var rootView : View? = null

    var total : Double = 0.0

    var statusBooking : String = ""

    var orderCode : String = ""

    fun OrderBillDetailsActivity() {}

    var callBlacklisting: CallBlacklisting? = null

    var itemView : RecyclerView? =null

    var itemPriceTv : TextView? = null

    var itemNameTv : TextView? = null

    var totalPrice : String? =null

    var stringValue1 : String? =null

    var stringValue2 : String? =null

    var stringValue3 : String? =null

    var stringValue4 : String? =null

    var stringValue5 : String? =null

    var stringValue6 : String? =null

    var stringValue : String? =null

    var deliveryDate : String? = null

    var promoAmount : String? = null

    var discountTV : TextView? = null

    var couponPriceTV : TextView? = null

    var couponTV :TextView? = null

    var shopId : String? =""

    var couponRelativeLayout :RelativeLayout? = null

    var dbHelper : DBHelper? = null

    var deliveryType : String? =null

    var restaurantId : String? = null

    var restaurantPath : String? = null

    var minimumOrder : String? = null

    var selfPickupMinimumOrder : String? = null

    var nifRelativeLayout : RelativeLayout? = null

    var nifLabelTV : TextView? = null

    var nifTV : TextView? = null

    var groceryReferenceCode : String =""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_order_bill_details, container, false)

        dbHelper = DBHelper(activity)

        var header = rootView!!.findViewById(R.id.header) as RelativeLayout

        header!!.setOnClickListener(this)

        couponRelativeLayout = rootView!!.findViewById(R.id.couponRelativeLayout) as RelativeLayout

        couponTV = rootView!!.findViewById(R.id.couponTV) as TextView

        couponTV!!.typeface = fontStyle(activity!!,"")

        couponPriceTV = rootView!!.findViewById(R.id.couponPriceTV) as TextView

        couponPriceTV!!.typeface = fontStyle(activity!!,"")

        tickImg = rootView!!.findViewById(R.id.tickImg) as ImageView

        tickImg!!.setColorFilter(colorIcon(activity!!,R.color.colorGreen,R.drawable.tick,tickImg!!),
            PorterDuff.Mode.SRC_ATOP)

        orderBill_Title = rootView!!.findViewById(R.id.orderBill_Title) as TextView

        orderBill_Title!!.typeface = fontStyle(activity!!,"")

        discountTV = rootView!!.findViewById(R.id.discountTV) as TextView

        discountTV!!.typeface = fontStyle(activity!!,"")

        itemPriceTv = rootView!!.findViewById(R.id.itemPriceTv) as TextView

        itemPriceTv!!.typeface = fontStyle(activity!!,"SemiBold")

        itemNameTv = rootView!!.findViewById(R.id.itemNameTv) as TextView

        itemNameTv!!.typeface = fontStyle(activity!!,"")

        tableReservation_back = rootView!!.findViewById(R.id.tableReservation_back) as ImageView

        tableReservation_back!!.setOnClickListener(this)

        orderDescription_Type = rootView!!.findViewById(R.id.orderDescription_Type) as TextView

        orderDescription_Type!!.typeface = fontStyle(activity!!,"Light")

        help = rootView!!.findViewById(R.id.help) as TextView

        help!!.typeface = fontStyle(activity!!,"Light")

        help!!.setOnClickListener(this)

        fromAddressTitle = rootView!!.findViewById(R.id.fromAddressTitle) as TextView

        fromAddressTitle!!.typeface = fontStyle(activity!!,"SemiBold")

        fromAddressTitleDescription = rootView!!.findViewById(R.id.fromAddressTitleDescription) as TextView

        fromAddressTitleDescription!!.typeface = fontStyle(activity!!,"Light")

        toAddressTitleDescription = rootView!!.findViewById(R.id.toAddressTitleDescription) as TextView

        toAddressTitleDescription!!.typeface = fontStyle(activity!!,"Light")

        toAddressTitle = rootView!!.findViewById(R.id.toAddressTitle) as TextView

        toAddressTitle!!.typeface = fontStyle(activity!!,"SemiBold")

        orderDescription = rootView!!.findViewById(R.id.orderDescription) as TextView

        orderDescription!!.typeface = fontStyle(activity!!,"Light")

        billDetailsTV = rootView!!.findViewById(R.id.billDetailsTV) as TextView

        billDetailsTV!!.typeface = fontStyle(activity!!,"")

        billTV = rootView!!.findViewById(R.id.billTV) as TextView

        billTV!!.typeface = fontStyle(activity!!,"SemiBold")

        itemTV = rootView!!.findViewById(R.id.itemTV) as TextView

        itemTV!!.typeface = fontStyle(activity!!,"")

        itemPriceTV = rootView!!.findViewById(R.id.itemPriceTV) as TextView

        itemPriceTV!!.typeface = fontStyle(activity!!,"Light")

        restaurantTV = rootView!!.findViewById(R.id.restaurantTV) as TextView

        restaurantTV!!.typeface = fontStyle(activity!!,"")

        restaurantPriceTV = rootView!!.findViewById(R.id.restaurantPriceTV) as TextView

        restaurantPriceTV!!.typeface = fontStyle(activity!!,"Light")

        deliveryTV = rootView!!.findViewById(R.id.deliveryTV) as TextView

        deliveryTV!!.typeface = fontStyle(activity!!,"")

        deliveryPriceTV = rootView!!.findViewById(R.id.deliveryPriceTV) as TextView

        deliveryPriceTV!!.typeface = fontStyle(activity!!,"Light")

        totalTV = rootView!!.findViewById(R.id.totalTV) as TextView

        totalTV!!.typeface = fontStyle(activity!!,"Light")

        totalPriceTV = rootView!!.findViewById(R.id.totalPriceTV) as TextView

        totalPriceTV!!.typeface = fontStyle(activity!!,"SemiBold")

        reorderTV = rootView!!.findViewById(R.id.reorderTV) as TextView

        reorderTV!!.typeface = fontStyle(activity!!,"SemiBold")

        reorderTV!!.setOnClickListener(this)

        nifRelativeLayout = rootView!!.findViewById(R.id.nifRelativeLayout) as RelativeLayout

        nifLabelTV = rootView!!.findViewById(R.id.nifLabelTV) as TextView

        nifLabelTV!!.typeface = fontStyle(activity!!,"")

        nifTV = rootView!!.findViewById(R.id.nifTV) as TextView

        nifTV!!.typeface = fontStyle(activity!!,"")

        val bundle = this.arguments
        if (bundle != null) {
            orderDescription_Type!!.text = "Delivered "+bundle.getString("quantity")+" item "+bundle.getString("totalValue")

            adapterProductListView = bundle.getParcelableArrayList<ProductListView>("itemList")!!

            //adapterProductListView1 =adapterProductListView

            fromAddressTitle!!.text =""+bundle.getString("name")

            fromAddressTitleDescription!!.text =""+bundle.getString("Address")

            fromAddressTitle!!.text =""+bundle.getString("name")

            fromAddressTitleDescription!!.text =""+bundle.getString("Address")

            toAddressTitle!!.text =""+bundle.getString("customerName")

            toAddressTitleDescription!!.text =""+bundle.getString("customerAddress")

            orderDescription!!.text =getString(R.string.myorderDeliveredOn)+" "+bundle.getString("date")

            deliveryDate = " "+bundle.getString("date")

            restaurantId = ""+bundle.getString("restaurantId")

            orderBill_Title!!.text =getString(R.string.order_Title)+"#"+bundle.getString("orderCode")

            deliveryType = ""+bundle.getString("deliveryType")

            orderCode = ""+bundle.getString("orderCode")

            stringValue = ""+bundle.getString("quantity")

            stringValue1 = ""+bundle.getString("total")

            stringValue2 = ""+bundle.getString("restLati")

            stringValue3 = ""+bundle.getString("restLong")

            stringValue4 = ""+bundle.getString("customerLong")

            stringValue5 = ""+bundle.getString("customerLati")

            itemPriceTV!!.text = " € "+bundle.getString("BookingPriceAmount")

            itemPriceTv!!.text = getString(R.string.totalTV)+" € "+bundle.getString("total")

            statusBooking = ""+bundle.getString("bookingStatus")

            deliveryPriceTV!!.text ="€ "+bundle.getString("deliveryFee")

            totalPriceTV!!.text = "€ "+ DecimalFormat("##.##").format(bundle.getString("totalValue").toString().replace("€ ","").toDouble())

            totalTV!!.text = "Paid via "+bundle.getString("paymentType")

            promoAmount = bundle.getString("promoAmount")

            shopId = bundle.getString("shopId")

            groceryReferenceCode = bundle.getString("groceryReferenceCode").toString()

            restaurantPath = bundle.getString("restaurantPath")+"/"+bundle.getString("restaurantImage")

            minimumOrder = bundle.getString("minimumOrderId")

            selfPickupMinimumOrder = bundle.getString("selfPickUpMinimumOrderID")

            if(bundle.getString("nifNumber").equals("null"))
            {
                nifRelativeLayout!!.visibility = View.GONE
            }else{
                nifRelativeLayout!!.visibility = View.VISIBLE

                nifTV!!.text = ""+bundle.getString("nifNumber")
            }
        }

        total = itemPriceTV!!.text.toString().replace("€ ","").toDouble()+restaurantPriceTV!!.text.toString().replace("€ ","").toDouble()+deliveryPriceTV!!.text.toString().replace("€ ","").toDouble()


        if(Constant.AppType!!.equals("0")) {

            help!!.setTextColor(Color.parseColor("#ec272b"))
            reorderTV!!.setBackgroundResource(R.drawable.home_radius_button_orange)
            if(statusBooking!!.equals("1"))
            {
                orderDescription!!.text ="Order Pending"
            }else if(statusBooking!!.equals("2"))
            {
                orderDescription!!.text ="Order Confirmed"
            }else if(statusBooking!!.equals("4"))
            {
                orderDescription!!.text ="Order ready for dispatch"
            }else if(statusBooking!!.equals("5"))
            {
                orderDescription!!.text ="Order Pick Up"
            }else if(statusBooking!!.equals("6"))
            {
                orderDescription!!.text ="Order delivered on"+deliveryDate
            }else if(statusBooking!!.equals("7"))
            {
                orderDescription!!.text ="Order Cancel"
            }
        }else{
            help!!.setTextColor(Color.parseColor("#7DC77D"))
            reorderTV!!.setBackgroundResource(R.drawable.home_radius_button_green)

            if(statusBooking!!.equals("1"))
            {
                orderDescription!!.text ="Order Pending"
            }else if(statusBooking!!.equals("2"))
            {
                orderDescription!!.text ="Order Confirmed"
            }else if(statusBooking!!.equals("4"))
            {
                orderDescription!!.text ="Order ready for dispatch"
            }else if(statusBooking!!.equals("5"))
            {
                orderDescription!!.text ="Order Pick Up"
            }else if(statusBooking!!.equals("6"))
            {
                orderDescription!!.text ="Order delivered on"+deliveryDate
            }else if(statusBooking!!.equals("7"))
            {
                orderDescription!!.text ="Order Cancel"
            }
        }

        if(statusBooking!!.equals("6"))
        {
            reorderTV!!.text = ""+getString(R.string.gotoOrder_text)
        }else if(statusBooking!!.equals("7"))
        {
            reorderTV!!.text = ""+getString(R.string.gotoOrder_text)
        }else{
            reorderTV!!.text = ""+getString(R.string.Track)
        }

        if(adapterProductListView1!!.size != 0)
        {
            adapterProductListView1!!.clear()
        }
        for(i in 0 until adapterProductListView!!.size)
        {
            if(adapterProductListView!!.get(i).headerSub!!.equals(orderCode))
            {
                productList = ProductListView(
                    R.drawable.img_1,
                    "" + adapterProductListView!!.get(i).name,
                    ""  + adapterProductListView!!.get(i).price,
                    "" + adapterProductListView!!.get(i).toppinsGroupId,
                    ""+ adapterProductListView!!.get(i).headerSub,
                    ""+ adapterProductListView!!.get(i).toppinsId, ""+ adapterProductListView!!.get(i).toppinsReferenceCode,""+adapterProductListView!!.get(i).menuId,""+adapterProductListView!!.get(i).categoryId,adapterProductListView!!.get(i).toppinsGroupJsonData)
                adapterProductListView1.add(productList)

            }
        }

        itemView = rootView!!.findViewById(R.id.itemView)

        var categoriesListAdapter = CategoryListPageAdapter(activity!!,adapterProductListView1)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        itemView!!.layoutManager = mLayoutManager

        itemView!!.itemAnimator!!.addDuration = 5000

        //restaurentPageAdapter!!.setCallback(this)

        itemView!!.adapter = categoriesListAdapter

        couponPriceTV!!.text = "- € "+promoAmount

        if(promoAmount!!.equals("0"))
        {
            couponRelativeLayout!!.visibility = View.GONE
        }else{
            couponRelativeLayout!!.visibility = View.VISIBLE
        }

        return rootView
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
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
            R.id.help!! ->
            {
                callBlacklisting!!.fragmentChange("Help",null)
            }

            R.id.tableReservation_back ->{

                callBlacklisting!!.fragmentBack("")
            }
            R.id.reorderTV ->{
                if(Constant.AppType!!.equals("0")) {
                    if (reorderTV!!.text.equals(getString(R.string.gotoOrder_text))) {

                        dbHelper!!.addRestaurant_info_Data(
                            "" + restaurantId,
                            "" + fromAddressTitle!!.text.toString(),
                            "" + fromAddressTitleDescription!!.text.toString(),
                            "" + shopId!!
                        )



                        for (i in 0 until adapterProductListView1!!.size) {
                            var totalPrice = 0.0
                            for (j in 0 until adapterProductListView1!!.get(i).toppinsGroupJsonData!!.toppinsGroupList!!.size) {
                                dbHelper!!.addToppinsGroup_info_reorder(
                                    adapterProductListView1!!.get(i).toppinsGroupJsonData.toppinsGroupList!!.get(
                                        j
                                    ), shopId!!, adapterProductListView1!!.get(i).menuId!!
                                )
                                for (k in 0 until adapterProductListView1!!.get(i).toppinsGroupJsonData!!.toppinsGroupList!!.get(
                                    j
                                ).toppinsList!!.size) {

                                    totalPrice =
                                        totalPrice + adapterProductListView1!!.get(i).toppinsGroupJsonData!!.toppinsGroupList!!.get(
                                            j
                                        ).toppinsList!!.get(k).price!!.toDouble()
                                    dbHelper!!.addToppins_info_reorder(
                                        adapterProductListView1!!.get(
                                            i
                                        ).toppinsGroupJsonData!!.toppinsGroupList!!.get(j).toppinsList!!.get(
                                            k
                                        ), shopId!!, adapterProductListView1!!.get(i).menuId!!
                                    )
                                }
                            }

                            dbHelper!!.addMenuPrice(
                                adapterProductListView1!!.get(i),
                                shopId!!,
                                totalPrice!!.toString()
                            );
                        }

                        val bundle = Bundle()
                        bundle.putString("restaurantReferenceCode", "" + shopId)
                        bundle.putString("restaurantImg", "" + restaurantPath)
                        if (deliveryType!!.equals("3")) {
                            bundle.putString("minimumAmount", "" + selfPickupMinimumOrder)

                            Constant.DeliveryType = "3"
                        } else {
                            bundle.putString("minimumAmount", "" + minimumOrder)

                            Constant.DeliveryType = "" + deliveryType!!
                        }
                        callBlacklisting!!.fragmentChange("DetailItemAddPageActivity", bundle)
                    }
                    else {
                        var bundle = Bundle()

                        bundle.putString("orderCode", "" + orderCode)

                        bundle.putString("quantity", "" + stringValue)

                        bundle.putString("total", "" + stringValue1)

                        bundle.putString("restLati", "" + stringValue2)

                        bundle.putString("restLong", "" + stringValue3)

                        bundle.putString("customerLong", "" + stringValue4)

                        bundle.putString("customerLati", "" + stringValue5)
                        /* orderCode = ""+bundle.getString("orderCode")

                    stringValue = ""+bundle.getString("quantity")

                    stringValue1 = ""+bundle.getString("total")

                    stringValue2 = ""+bundle.getString("restLati")

                    stringValue3 = ""+bundle.getString("restLong")

                    stringValue4 = ""+bundle.getString("customerLong")

                    stringValue5 = ""+bundle.getString("customerLati")
*/
                        callBlacklisting!!.fragmentChange("Track", bundle)
                    }
                }else {
                    if (reorderTV!!.text.equals(getString(R.string.gotoOrder_text))) {

                        for (i in 0 until adapterProductListView1!!.size) {
                            dbHelper!!.addItemCategory_info_reOrder(adapterProductListView1!!.get(i),fromAddressTitle!!.text.toString(),groceryReferenceCode)
                        }



                        val bundle = Bundle()
                        bundle.putString("restaurantReferenceCode", "" + groceryReferenceCode)
                        bundle.putString("restaurantImg", "" + restaurantPath)
                        if (deliveryType!!.equals("3")) {
                            bundle.putString("minimumAmount", "" + selfPickupMinimumOrder)

                            Constant.DeliveryType = "3"
                        } else {
                            bundle.putString("minimumAmount", "" + minimumOrder)

                            Constant.DeliveryType = "" + deliveryType!!
                        }
                        callBlacklisting!!.fragmentChange("DetailItemAddPageActivity", bundle)
                    }
                    else {
                        var bundle = Bundle()

                        bundle.putString("orderCode", "" + orderCode)

                        bundle.putString("quantity", "" + stringValue)

                        bundle.putString("total", "" + stringValue1)

                        bundle.putString("restLati", "" + stringValue2)

                        bundle.putString("restLong", "" + stringValue3)

                        bundle.putString("customerLong", "" + stringValue4)

                        bundle.putString("customerLati", "" + stringValue5)
                        /* orderCode = ""+bundle.getString("orderCode")

                    stringValue = ""+bundle.getString("quantity")

                    stringValue1 = ""+bundle.getString("total")

                    stringValue2 = ""+bundle.getString("restLati")

                    stringValue3 = ""+bundle.getString("restLong")

                    stringValue4 = ""+bundle.getString("customerLong")

                    stringValue5 = ""+bundle.getString("customerLati")
*/
                        callBlacklisting!!.fragmentChange("Track", bundle)
                    }
                }
            }
        }
    }
}
