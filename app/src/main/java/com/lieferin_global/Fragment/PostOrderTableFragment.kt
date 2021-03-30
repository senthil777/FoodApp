package com.lieferin_global.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.TableOrderListAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import org.json.JSONObject
import java.util.*


class PostOrderTableFragment : Fragment(),View.OnClickListener,TableOrderListAdapter.CallbackDataAdapter,ResponseListener {

    var orderListRecyclerView: RecyclerView? = null

    var rootView: View? = null

    var orderListAdapter: TableOrderListAdapter? = null

    var adapterProduct: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    internal lateinit var productModel: Product

    var myInt : String? =null

    fun NewOrderFragment() {}

    var callBlacklisting: CallBlacklisting? = null

    var dbHelper : DBHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_new_order, container, false)

        dbHelper = DBHelper(activity)

        orderListRecyclerView = rootView!!.findViewById(R.id.orderListRecyclerView)

        orderListAdapter = TableOrderListAdapter(activity!!,adapterProduct)

        orderListRecyclerView!!.setHasFixedSize(true)

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
        webService()

        return rootView
    }

    fun webService(){

        val obj = JSONObject()
        obj.put("token", ""+dbHelper!!.getUserDetails().token)

        RequestManager.setBookingDataList(activity, obj, this);

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
            if (requestType == Constant.MEMBER_tableBookingData_RQ) {



                for (i in 0 until (responseObj as BaseRS).bookingData!!.size) {

                    if((responseObj as BaseRS).bookingData!!.get(i).tableBookingStatus!!.equals("3") || (responseObj as BaseRS).bookingData!!.get(i).tableBookingStatus!!.equals("4") || (responseObj as BaseRS).bookingData!!.get(i).tableBookingStatus!!.equals("5")) {
                        productModel = Product(
                            R.drawable.img_1,
                            "" + (responseObj as BaseRS).bookingData!!.get(i).restaurantName,
                            "" + (responseObj as BaseRS).bookingData!!.get(i).street + "," + (responseObj as BaseRS).bookingData!!.get(
                                i
                            ).town,
                            "No.of Person " + (responseObj as BaseRS).bookingData!!.get(i).numberOfPeron,
                            "" + dateConversionDateOnly((responseObj as BaseRS).bookingData!!.get(i).bookingDate),
                            "" + dateConversionTimeOnly((responseObj as BaseRS).bookingData!!.get(i).startTime) + " - " + dateConversionTimeOnly(
                                (responseObj as BaseRS).bookingData!!.get(i).endTime
                            ),
                            "View Member's",
                            "Table",
                            "" + (responseObj as BaseRS).bookingData!!.get(i).restaurantReferenceCode,
                            "" + (responseObj as BaseRS).bookingData!!.get(i).customerFirstname,
                            "" + (responseObj as BaseRS).bookingData!!.get(i).tableBookingReferenceCode,
                            "" + (responseObj as BaseRS).bookingData!!.get(i).bookingCode,
                            "" + (responseObj as BaseRS).bookingData!!.get(i).tableBookingStatus,
                            "" + (responseObj as BaseRS).bookingData!!.get(i).customerIncludeStatus,
                            "",
                            "",
                            "",
                            "",
                            adapterProductList
                        )
                        adapterProduct.add(productModel)
                    }
                }

                orderListAdapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun setOnMaterial(id: String, adapterModels: Product?) {
        if(id!!.equals("Track"))
        {
            val bundle = Bundle()
            bundle.putString("name", adapterModels!!.name)

            bundle.putString("Address", adapterModels!!.menuId)

            bundle.putString("total", adapterModels!!.price)

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

            callBlacklisting!!.fragmentChange("Track",bundle)
        }else if(id!!.equals("View Member's"))
        {
            val bundle = Bundle()
            bundle.putString("name", adapterModels!!.menuImages)

            bundle.putString("title", adapterModels!!.typeView)

            bundle.putString("BookingStatus", adapterModels!!.offerType)

            bundle.putString("BookingStatus", adapterModels!!.offerType)

            /* bundle.putString("Address", adapterModels!!.menuId)

             bundle.putString("total", adapterModels!!.price)

             bundle.putString("date", adapterModels!!.allergy1)

             bundle.putString("Title", adapterModels!!.allergy)

             bundle.putString("customerAddress", adapterModels!!.typeView)

             bundle.putString("customerName", adapterModels!!.quantity)

             bundle.putString("quantity", adapterModels!!.menuImages)

             bundle.putString("orderCode", adapterModels!!.type)

 */
            callBlacklisting!!.fragmentChange("Member Details",bundle)
        }
    }

}
