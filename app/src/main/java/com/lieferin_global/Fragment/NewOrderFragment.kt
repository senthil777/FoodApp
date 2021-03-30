package com.lieferin_global.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.OrderListAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.*
import com.lieferin_global.R
import com.lieferin_global.Utility.Constant
import com.lieferin_global.Utility.dateConversionVal
import com.lieferin_global.Utility.fontStyle
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import org.json.JSONObject
import kotlin.collections.ArrayList


class NewOrderFragment : Fragment(),View.OnClickListener,OrderListAdapter.CallbackDataAdapter,ResponseListener {

    var orderListRecyclerView: RecyclerView? = null

    var rootView: View? = null

    var orderListAdapter: OrderListAdapter? = null

    var adapterProduct: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    var adapterProductList1: ArrayList<ProductList> = ArrayList()

    var adapterProductListView: ArrayList<ProductListView> = ArrayList()

    internal lateinit var ProductSubListViewObj : ProductSubListViewObj

    internal lateinit var productModel: Product

    internal lateinit var productList: ProductList

    internal lateinit var productListView: ProductListView

    var myInt : String? =null

    fun NewOrderFragment() {}

    var callBlacklisting: CallBlacklisting? = null

    var dbHelper : DBHelper? = null

    var noOrders : TextView? = null

    var productListSubView: MutableList<ProductListSubView> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_new_order, container, false)

        orderListRecyclerView = rootView!!.findViewById(R.id.orderListRecyclerView)

        orderListAdapter = OrderListAdapter(activity!!,adapterProduct)

        orderListRecyclerView!!.setHasFixedSize(true)

        dbHelper = DBHelper(activity)

        noOrders = rootView!!.findViewById(R.id.noOrders)

        noOrders!!.visibility = View.GONE

        noOrders!!.typeface = fontStyle(activity!!,"Light")

        orderListRecyclerView!!.setLayoutManager(
            LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        )

        orderListAdapter!!.setCallback(this)

        orderListRecyclerView!!.isNestedScrollingEnabled = false
        orderListRecyclerView!!.setAdapter(orderListAdapter!!)
        val bundle = this.arguments
        if (bundle != null) {
            myInt = bundle.getString("shopId")
        }

        /*if(myInt.equals("POST ORDER"))
        {
            showDataProduct1()
        }else{
            showDataProduct()
        }*/
        sample()

        webService()

        return rootView
    }

    fun webService(){

        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        RequestManager.setBookingList(activity, obj, this);

        Log.v("kkkk"+obj,"33333")

        //loadingScreen(activity)
    }
    fun showDataProduct() {

        if (adapterProduct.size != 0) {
            adapterProduct.clear()
        }
        productModel = Product(R.drawable.img_1, "Domino's Pizza", "Race Course","€ 34.00","Everyday Value Offer:[Cheese N Corn (1),Chicken Sausage (1)]","November 15 ,9:14 PM ","View Order","Track","","","","","","","","","","",adapterProductList)
        adapterProduct.add(productModel)

        orderListAdapter!!.notifyDataSetChanged()
    }

    fun showDataProduct1() {

        if (adapterProduct.size != 0) {
            adapterProduct.clear()
        }

        productModel = Product(R.drawable.img_1, "Domino's Pizza", "Race Course","€ 34.00","Everyday Value Offer:[Cheese N Corn (1),Chicken Sausage (1)]","November 15 ,9:14 PM ","Reorder\nNot Available","Rate Meal","","","","","","","","","","",adapterProductList)
        adapterProduct.add(productModel)

        productModel = Product(R.drawable.img_1, "Subway", "OMR Perungudi","€ 44.00","Everyday Value Offer:[Cheese N Corn (1),Chicken Sausage (1)]","March 11 ,1:31 AM ","Reorder","","","","","","","","","","","",adapterProductList)
        adapterProduct.add(productModel)

        orderListAdapter!!.notifyDataSetChanged()
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

    fun sample()
    {
        ProductSubListViewObj = ProductSubListViewObj(
            R.drawable.img_1,
            "" ,
            "" ,
            "",
            "",
            "","","","",productListSubView)

    }


    override fun onClick(view: View?) {

        when(view!!.id)
        {

        }

    }



    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if (adapterProduct.size != 0) {
            adapterProduct.clear()
        }
        if (responseObj != null) {
            //loadingScreenClose(activity)
            if (requestType == Constant.MEMBER_bookingFoodOrderList_URL_RQ) {

                if (adapterProductListView.size != 0) {
                    adapterProductListView.clear()
                }

                if((responseObj as BaseRS).bookingData != null) {

                    for (i in 0 until (responseObj as BaseRS).bookingData!!.size) {


                        var itemName = ""

                        for (j in 0 until (responseObj as BaseRS).bookingData!!.get(i).itemList!!.size) {

                            if ((responseObj as BaseRS).bookingData!!.get(i).itemList!!.get(j).toppinsGroupJsonData != null) {

                                productListView = ProductListView(
                                    R.drawable.img_1,
                                    "" + (responseObj as BaseRS).bookingData!!.get(i).itemList!!.get(
                                        j
                                    ).itemName,
                                    "" + (responseObj as BaseRS).bookingData!!.get(i).itemList!!.get(
                                        j
                                    ).quantity,
                                    "" + (responseObj as BaseRS).bookingData!!.get(i).itemList!!.get(
                                        j
                                    ).price,
                                    "" + (responseObj as BaseRS).bookingData!!.get(i).bookingOrderCode,
                                    "",
                                    "",
                                    "",
                                    "",
                                    (responseObj as BaseRS).bookingData!!.get(i).itemList!!.get(j).toppinsGroupJsonData!!
                                )
                                adapterProductListView.add(productListView)

                            } else {

                                productListView = ProductListView(
                                    R.drawable.img_1,
                                    "" + (responseObj as BaseRS).bookingData!!.get(i).itemList!!.get(
                                        j
                                    ).itemName,
                                    "" + (responseObj as BaseRS).bookingData!!.get(i).itemList!!.get(
                                        j
                                    ).quantity,
                                    "" + (responseObj as BaseRS).bookingData!!.get(i).itemList!!.get(
                                        j
                                    ).price,
                                    "" + (responseObj as BaseRS).bookingData!!.get(i).bookingOrderCode,
                                    "", "", "", "", ProductSubListViewObj
                                )
                                adapterProductListView.add(productListView)
                            }
                        }

                    }
                }


                for (i in 0 until (responseObj as BaseRS).bookingData!!.size) {

                    if (!(responseObj as BaseRS).bookingData!!.get(i).bookingOrderStatus.equals("6")) {

                        if (!(responseObj as BaseRS).bookingData!!.get(i).bookingOrderStatus.equals(
                                "7"
                            )
                        ) {

                            var itemName = ""

                            for (j in 0 until (responseObj as BaseRS).bookingData!!.get(i).itemList!!.size) {

                                if (itemName.equals("")) {
                                    itemName =
                                        "" + (responseObj as BaseRS).bookingData!!.get(i).itemList!!.get(
                                            j
                                        ).itemName
                                } else {
                                    itemName =
                                        itemName + "," + (responseObj as BaseRS).bookingData!!.get(i).itemList!!.get(
                                            j
                                        ).itemName
                                }

                            }
                            productModel = Product(
                                R.drawable.img_1,
                                "" + (responseObj as BaseRS).bookingData!!.get(i).restaurantName,
                                "" + (responseObj as BaseRS).bookingData!!.get(i).street + "," + (responseObj as BaseRS).bookingData!!.get(
                                    i
                                ).town,
                                "€ " + (responseObj as BaseRS).bookingData!!.get(i).totalAmount,
                                "Everyday Value Offer:[" + itemName + "]",
                                "" + dateConversionVal((responseObj as BaseRS).bookingData!!.get(i).bookingDateTime),
                                "View Order",
                                "Track",
                                "" + (responseObj as BaseRS).bookingData!!.get(i).bookingCustomerAddress,
                                "" + (responseObj as BaseRS).bookingData!!.get(i).customerFirstname,
                                "" + (responseObj as BaseRS).bookingData!!.get(i).quantity,
                                "" + (responseObj as BaseRS).bookingData!!.get(i).bookingOrderCode,
                                "" + (responseObj as BaseRS).bookingData!!.get(i).latitude,
                                "" + (responseObj as BaseRS).bookingData!!.get(i).longitude,
                                "" + (responseObj as BaseRS).bookingData!!.get(i).bookingCustomerLatitude + "," + (responseObj as BaseRS).bookingData!!.get(
                                    i
                                ).bookingCustomerLongitude,
                                "" + (responseObj as BaseRS).bookingData!!.get(i).bookingOrderReferenceCode,
                                "" + (responseObj as BaseRS).bookingData!!.get(i).bookingOrderStatus,
                                "" + (responseObj as BaseRS).bookingData!!.get(i).bookingAmount + "-" + (responseObj as BaseRS).bookingData!!.get(
                                    i
                                ).paymentMode + "-" + (responseObj as BaseRS).bookingData!!.get(i).deliveryFare+ "-" + (responseObj as BaseRS).bookingData!!.get(i).promoValue+ "-" + (responseObj as BaseRS).bookingData!!.get(i).actualBookingAmount+ "-" + (responseObj as BaseRS).bookingData!!.get(i).restaurantReferenceCode+ "-" + (responseObj as BaseRS).bookingData!!.get(i).deliveryType+ "-" + (responseObj as BaseRS).bookingData!!.get(i).restaurantId+"-"+ (responseObj as BaseRS).bookingData!!.get(i).minimumOrder+"-"+ (responseObj as BaseRS).orderSelfPickupMinimumAmount+"-"+ (responseObj as BaseRS).restaurantPath+(responseObj as BaseRS).bookingData!!.get(i).folderName+"-"+(responseObj as BaseRS).bookingData!!.get(i).restaurantImages+"-"+(responseObj as BaseRS).bookingData!!.get(i).nifNumber,
                                adapterProductList
                            )
                            adapterProduct.add(productModel)
                        }
                    }
                    if (adapterProductListView.size == 0) {
                        noOrders!!.visibility = View.VISIBLE
                    }else{
                        noOrders!!.visibility = View.GONE
                    }
                    orderListAdapter!!.notifyDataSetChanged()
                }
            }
        }
    }

    override fun setOnMaterial(id: String, adapterModels: Product?) {
        if(id!!.equals("Track"))
        {
            val bundle = Bundle()
            bundle.putString("name", adapterModels!!.name)

            bundle.putString("Address", adapterModels!!.menuId)

            bundle.putString("date", adapterModels!!.allergy1)

            bundle.putString("Title", adapterModels!!.allergy)

            bundle.putString("customerAddress", adapterModels!!.typeView)

            bundle.putString("customerName", adapterModels!!.quantity)

            bundle.putString("quantity", adapterModels!!.menuImages)

            bundle.putString("orderCode", adapterModels!!.type)

            bundle.putString("restLati", adapterModels!!.offerType)

            bundle.putString("restLong", adapterModels!!.offer)

            bundle.putString("customerLati", adapterModels!!.description)

            bundle.putString("customerLong", adapterModels!!.allergy)

            bundle.putString("totalValue", adapterModels!!.price)
            val arrOfStr =
                adapterModels!!.bookingCode!!.split("-")

            val arrOfStr1 =
                adapterModels!!.sample!!.split("-")
            bundle.putString("total", arrOfStr1[0])

            bundle.putString("paymentType", arrOfStr1[1])

            bundle.putString("deliveryFee", arrOfStr1[2])

            bundle.putString("promoAmount", arrOfStr1[3])

            bundle.putString("BookingPriceAmount", arrOfStr1[4])

            bundle.putString("bookingStatus", arrOfStr[0])

            callBlacklisting!!.fragmentChange("Track",bundle)
        }else if(id!!.equals("Rate Meal"))
        {
            val bundle = Bundle()

            bundle.putString("quantity", adapterModels!!.menuImages)
            val arrOfStr =
                adapterModels!!.bookingCode!!.split("-")

            //bundle.putString("bookingStatus", arrOfStr[0])
            bundle.putString("RefenceCode", adapterModels!!.allergy)

            bundle.putString("orderCode", adapterModels!!.type)

            bundle.putString("total", adapterModels!!.price)

            callBlacklisting!!.fragmentChange("Review Page",bundle)
        }else if(id!!.equals("View Order"))
        {
            val bundle = Bundle()
            bundle.putString("name", adapterModels!!.name)

            bundle.putString("Address", adapterModels!!.menuId)

            bundle.putString("date", adapterModels!!.allergy1)

            bundle.putString("Title", adapterModels!!.allergy)

            bundle.putString("customerAddress", adapterModels!!.typeView)

            bundle.putString("customerName", adapterModels!!.quantity)

            bundle.putString("quantity", adapterModels!!.menuImages)

            bundle.putString("orderCode", adapterModels!!.type)

            bundle.putString("restLati", adapterModels!!.offerType)

            bundle.putString("restLong", adapterModels!!.offer)

            bundle.putString("customerLati", adapterModels!!.description)

            bundle.putString("customerLong", adapterModels!!.allergy)

            bundle.putString("totalValue", adapterModels!!.price)

            val arrOfStr =
                adapterModels!!.bookingCode!!.split("-")

            bundle.putString("bookingStatus", arrOfStr[0])

            bundle.putParcelableArrayList("itemList", adapterProductListView)

            val arrOfStr1 =
                adapterModels!!.sample!!.split("-")

            bundle.putString("total", arrOfStr1[0])

            bundle.putString("paymentType", arrOfStr1[1])

            bundle.putString("deliveryFee", arrOfStr1[2])
            bundle.putString("bookingStatus", arrOfStr[0])
            bundle.putString("promoAmount", arrOfStr1[3])

            bundle.putString("BookingPriceAmount", arrOfStr1[4])

            bundle.putString("shopId", arrOfStr1[5])

            bundle.putString("deliveryType", arrOfStr1[6])

            bundle.putString("restaurantId", arrOfStr1[7])

            bundle.putString("minimumOrderId", arrOfStr1[8])

            bundle.putString("selfPickUpMinimumOrderID", arrOfStr1[9])

            bundle.putString("restaurantPath", arrOfStr1[10])

            bundle.putString("nifNumber", arrOfStr1[12])

            val arrOfStrImage =
                arrOfStr1[11].split("[")

            val arrOfStrImage1 =
                arrOfStrImage[1].split("]")

            bundle.putString("restaurantImage", arrOfStrImage1[1])

            callBlacklisting!!.fragmentChange("View Order",bundle)
        }
    }
}
