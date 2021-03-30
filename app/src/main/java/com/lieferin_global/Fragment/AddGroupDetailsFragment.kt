package com.lieferin_global.Fragment

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.AddPersonAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList

import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class AddGroupDetailsFragment : Fragment(),View.OnClickListener,AddPersonAdapter.CallbackDataAdapter,ResponseListener {

    var rootView: View? = null

    var addIcon : ImageView? = null

    var title : String? = null

    var addLayout : LinearLayout? = null

    var orderBill_Title : TextView? = null

    var addPersonRecyclerView : RecyclerView? = null

    var adapterFeature: MutableList<AdapterModel> = ArrayList()

    var adapterProduct: ArrayList<Product> = ArrayList()

    var adapterProductIndividuals: ArrayList<Product> = ArrayList()

    var adapterProductAdd: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    internal lateinit var adapterModel: Product

    var addPersonAdapter:AddPersonAdapter? = null

    var addPersonTV : TextView? = null

    var callBlacklisting: CallBlacklisting? = null

    var titleValue: String? = null

    var dateVale: String? = null

    var countPerson: String? = null

    var restaurantReferenceCode: String? = null

    var selectTimeValue : String? = null

    var tableReservation_back : ImageView? = null

    var userCustomer:String?= "0"

    var dbHelper : DBHelper? = null

    var addGroupRelativeLayout : RelativeLayout? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_add_group_details, container, false)

        dbHelper = DBHelper(activity)

        var header = rootView!!.findViewById(R.id.header) as RelativeLayout

        header!!.setOnClickListener(this)

        var addGroupRelativeLayout = rootView!!.findViewById(R.id.addGroupRelativeLayout)as RelativeLayout

        addGroupRelativeLayout!!.setOnClickListener(this)

        tableReservation_back = rootView!!.findViewById(R.id.tableReservation_back) as ImageView

        tableReservation_back!!.setOnClickListener(this)

        addIcon = rootView!!.findViewById(R.id.addIcon)

        addLayout = rootView!!.findViewById(R.id.addLayout)

        addLayout!!.setOnClickListener(this)

        addIcon!!.setColorFilter(colorIcon(activity!!,R.color.colorWhite,R.drawable.add,addIcon!!))

        val bundle = this.arguments
        if (bundle != null) {
           // title = bundle.getString("title")

            adapterFeature= bundle.getParcelableArrayList<Product>("Product") as ArrayList<AdapterModel>

            adapterProductIndividuals= bundle.getParcelableArrayList<Product>("ProductIndividuals") as ArrayList<Product>

            titleValue = bundle.getString("Title")

            dateVale = bundle.getString("Select Date")

            countPerson = bundle.getString("person Count")

            selectTimeValue = bundle.getString("Select Time")

            restaurantReferenceCode = bundle.getString("restaurantReferenceCode")

            userCustomer = bundle.getString("userCustomer")
        }

        addPersonTV= rootView!!.findViewById(R.id.addPersonTV)

        addPersonTV!!.typeface = fontStyle(activity!!,"SemiBold")

        for(i in 0 until 1)
        {
            if(adapterFeature.size != 0) {
                for (j in 0 until adapterFeature.get(i).menusList.size) {
                    if (adapterFeature.get(i).menusList.get(j).categoryId.equals("1")) {
                        adapterModel = Product(
                            R.drawable.img_4,
                            "" + adapterFeature.get(i).menusList.get(j).name.toString(),
                            "" + adapterFeature.get(i).menusList.get(j).menuId.toString(),
                            "" + adapterFeature.get(i).menusList.get(j).price.toString(),
                            "",
                            "" + adapterFeature.get(i).menusList.get(j).allergy1.toString(),
                            " 425",
                            "" + adapterFeature.get(i).menusList.get(j).offerPrice.toString(),
                            "4.4",
                            "0",
                            "3054 Ratings>",
                            "5 Star Given by you",
                            "1",
                            "",
                            "",
                            "", "", "",
                            adapterProductList
                        )
                        adapterProduct.add(adapterModel)
                    }
                }
            }
        }

        for(j in 0 until adapterProductIndividuals.size)
        {
            if(adapterProductIndividuals.get(j).categoryId.equals("1")) {

                adapterModel = Product(
                    R.drawable.img_4,
                    "" + adapterProductIndividuals.get(j).name.toString(),
                    "" + adapterProductIndividuals.get(j).menuId.toString(),
                    "" + adapterProductIndividuals.get(j).price.toString(),
                    "",
                    "1",
                    " 425",
                    ""+adapterProductIndividuals.get(j).offerPrice.toString(),
                    "4.4",
                    "0",
                    "3054 Ratings>",
                    "5 Star Given by you",
                    "1",
                    "",
                    "",
                    "","","",
                    adapterProductList
                )
                adapterProduct.add(adapterModel)
            }
        }

        orderBill_Title = rootView!!.findViewById(R.id.orderBill_Title)

        orderBill_Title!!.typeface = fontStyle(activity!!,"")

        addPersonTV = rootView!!.findViewById(R.id.addPersonTV)

        addPersonTV!!.setOnClickListener(this)

        addPersonRecyclerView = rootView!!.findViewById(R.id.addPersonRecyclerView) as RecyclerView

        addPersonAdapter = AddPersonAdapter(activity!!, adapterProduct)
        addPersonRecyclerView!!.setHasFixedSize(true)
        addPersonRecyclerView!!.setLayoutManager(
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        )
        addPersonRecyclerView!!.isNestedScrollingEnabled = false

        addPersonAdapter!!.setCallback(this)

        addPersonRecyclerView!!.setAdapter(addPersonAdapter)

        /*val bundle = this.arguments
        if (bundle != null) {

        }
*/

        return rootView
    }

    override fun onClick(view: View?) {

        when(view!!.id)
        {
            R.id.addLayout->{
                showDialogInfo(activity!!,"Add")
            }
            R.id.tableReservation_back->{
                callBlacklisting!!.fragmentBack("")
            }
            R.id.addPersonTV->{
                webServiceTable()
            }
        }

    }

    fun webServiceTable()
    {
        val obj = JSONObject()
        val objArray = JSONArray()
        val objArray1 = JSONArray()
        val objArray2 = JSONArray()
        val objArray3 = JSONArray()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)
        obj.put("restaurantReferenceCode", ""+titleValue)
        obj.put("numberOfPeron", ""+countPerson)
        obj.put("bookingDate", ""+dateVale)

        obj.put("customerIncludeStatus ", ""+userCustomer)
        val str = selectTimeValue
        val arrOfStr =
            str!!.split(" - ")
        obj.put("startTime", ""+dateConversionTimeValue(arrOfStr[0]))
        obj.put("endTime", ""+dateConversionTimeValue(arrOfStr[1]))


        for (i in 0 until adapterFeature!!.size) {
            val menusList = JSONObject()

            for (j in 0 until adapterFeature.get(i).menusList.size) {
                val menusList = JSONObject()
                if(adapterFeature.get(i).menusList.get(j).offerPrice.equals("New Group")) {

                    if(adapterFeature.get(i).menusList.get(j).menuReferenceCode.equals(adapterFeature.get(i).categoryName)) {

                        menusList.put("name", "" + adapterFeature.get(i).menusList!!.get(j).name)

                        menusList.put("email", "" + adapterFeature.get(i).menusList!!.get(j).price)

                        menusList.put("mobile", "" + adapterFeature.get(i).menusList!!.get(j).menuId)

                        menusList.put(
                            "payType",
                            "" + adapterFeature.get(i).menusList!!.get(j).quantity
                        )
                        objArray1.put(menusList)
                    }
                }
            }

            if(adapterFeature!!.get(i).price!!.equals("")) {

                menusList.put("groupName", "" + adapterFeature!!.get(i).categoryName)

                menusList.put("groupMemberList", objArray1)

                objArray.put(menusList)
            }
        }

        if(objArray != null) {
            obj.put("newGroup", objArray)
        }
        val menusList1 = JSONObject()
        if(userCustomer!!.equals("1")) {
            menusList1.put("name", "" + localgetUserInfo("name"))

            menusList1.put("email", "" +  localgetUserInfo("email"))

            menusList1.put("mobile", "" + localgetUserInfo("mobile"))

            menusList1.put("payType", "1")

            objArray2.put(menusList1)
        }

        for (i in 0 until adapterProduct!!.size) {
            val menusList = JSONObject()

            if(adapterProduct!!.get(i).offerPrice.equals("Individual")) {

                menusList.put("name", "" + adapterProduct!!.get(i).name)

                menusList.put("email", "" + adapterProduct!!.get(i).price)

                menusList.put("mobile", "" + adapterProduct!!.get(i).menuId!!)

                menusList.put("payType", "" + adapterProduct!!.get(i).quantity)

                objArray2.put(menusList)
            }

            /*if(userCustomer!!.equals("1"))
            {
                menusList.put("name", ""+ localgetUserInfo("name"))

                menusList.put("email", ""+ localgetUserInfo("email"))

                menusList.put("mobile", ""+ localgetUserInfo("mobile"))

                menusList.put("payType", "1" )


                objArray2.put(menusList)
            }*/


        }

        if(objArray2 != null) {

            obj.put("individualList", objArray2)
        }

        for (i in 0 until adapterProduct!!.size) {
            val menusList = JSONObject()

            if(adapterProduct!!.get(i).offerPrice.equals("Existing")) {

                menusList.put("groupMemberReferenceCode", "" + adapterProduct!!.get(i).allergy1)

                menusList.put("name", "" + adapterProduct!!.get(i).name)

                menusList.put("email", "" + adapterProduct!!.get(i).price)

                menusList.put("mobile", "" + adapterProduct!!.get(i).menuId)

                menusList.put("payType", "" + adapterProduct!!.get(i).quantity)

                objArray3.put(menusList)
            }
        }

        if(objArray3 != null) {

            obj.put("existingListing", objArray3)
        }


        Log.v("Json", "Value" + obj)
        RequestManager.setTableReservationInsert(activity, obj, this)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }


    fun showDialogInfo(context: Context?,value: String?) {
        val dialog = Dialog(context!!)
        val decorView = dialog
            .window!!
            .getDecorView()
        val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
            decorView,
            PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
            PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
            PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f)
        )
        scaleDown.duration = 400
        scaleDown.start()
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.add_person_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val closeIMG = dialog.findViewById<View>(R.id.closeIMG) as ImageView

        val CancelTV = dialog.findViewById<View>(R.id.CancelTV) as TextView

        CancelTV.typeface = fontStyle(context,"SemiBold")

        val submitTV = dialog.findViewById<View>(R.id.submitTV) as TextView

        submitTV.typeface = fontStyle(context,"SemiBold")

        val tableLA = dialog.findViewById<View>(R.id.tableLA) as TextView

        tableLA.typeface = fontStyle(context,"")

        val tableLA1 = dialog.findViewById<View>(R.id.enterUserName) as EditText

        tableLA1.typeface = fontStyle(context,"")

        val tableLA2 = dialog.findViewById<View>(R.id.enterMobile) as EditText

        tableLA2.typeface = fontStyle(context,"")

        val enterEmail = dialog.findViewById<View>(R.id.enterEmail) as EditText

        enterEmail.typeface = fontStyle(context,"")

        closeIMG.setOnClickListener(View.OnClickListener {

            dialog.cancel()
        })

        CancelTV.setOnClickListener(View.OnClickListener {

            dialog.cancel()
        })

        submitTV.setOnClickListener(View.OnClickListener {

            if(tableLA1.equals(""))
            {
                showToast(activity!!,"Please enter the name")

            }else if(tableLA2.length() != 10)
            {
                showToast(activity!!,"Please enter valid mobile number")
            }else {

                showDataFeature(tableLA1.text.toString(),tableLA2.text.toString(),enterEmail.text.toString())

                dialog.cancel()
            }
        })

        dialog.show()
    }
    fun showDataFeature(name:String,mobile:String,eMail:String) {

        adapterModel = Product(R.drawable.img_4, ""+name.toString(), "", ""+eMail.toString(), ""+mobile, "1", " 425", "Tatabad,Gandhipuram", "4.4", "0", "3054 Ratings>", "5 Star Given by you", "1", "", "", "","","",adapterProductList )
        adapterProduct.add(adapterModel)

        addPersonAdapter!!.notifyDataSetChanged()
    }

    override fun setOnMaterial(id: String?,position : Int,product: Product) {

        adapterProduct.removeAt(position)

        addPersonAdapter!!.notifyDataSetChanged()
    }

    override fun setOnAdd(id: String?, position: Int,mobile: String?) {

        adapterProduct.get(position).quantity = id


       for(i in 0 until adapterFeature.size)
       {
           for(j in 0 until adapterFeature.get(i).menusList!!.size) {
               if(adapterFeature.get(i).menusList!!.get(j).menuId.equals(mobile))
                   {
                       adapterFeature.get(i).menusList!!.get(j).quantity = id
                   }
           }
       }


    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if (responseObj != null) {
            //loadingScreenClose(activity)
            if (requestType == Constant.MEMBER_tableReservationInsert_URL_RQ){

                showToast(activity!!,""+(responseObj as BaseRS).message!!)

                if ((responseObj as BaseRS).status.equals("5211")) {

                    showDialogTableInfo(activity)
                }
            }
        }
    }

    fun showDialogTableInfo(
        context: Context?
    ) {
        val dialog = Dialog(context!!)

        dialog.setCanceledOnTouchOutside(false)
        val decorView = dialog
            .window!!
            .getDecorView()
        val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
            decorView,
            PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
            PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
            PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f)
        )
        scaleDown.duration = 400
        scaleDown.start()
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.table_booking_confirm)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tableLA = dialog.findViewById<View>(R.id.successText) as TextView

        tableLA.typeface = fontStyle(context, "")

        val tableLA1 = dialog.findViewById<View>(R.id.successDescription) as TextView

        tableLA1.typeface = fontStyle(context, "")

        val tableLA2 = dialog.findViewById<View>(R.id.noTextView) as TextView

        tableLA2.typeface = fontStyle(context, "SemiBold")

        val textInfo = dialog.findViewById<View>(R.id.yesTV) as TextView

        textInfo.typeface = fontStyle(context, "SemiBold")


        tableLA2!!.setOnClickListener(View.OnClickListener {

            dialog.cancel()
        })

        textInfo!!.setOnClickListener(View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("Title", title)
            callBlacklisting!!.fragmentChange("Home", bundle)
            dialog.cancel()
        })

        dialog.show()
    }

}
