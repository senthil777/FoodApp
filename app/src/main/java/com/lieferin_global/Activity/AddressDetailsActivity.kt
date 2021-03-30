package com.lieferin_global.Activity

import android.app.Activity
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.AddAddressAdapter
import com.lieferin_global.Adapter.OrderSelectAdapter
import com.lieferin_global.Adapter.PaymentListAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.Constant.customerPincode
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import org.json.JSONObject
import java.util.*

class AddressDetailsActivity : AppCompatActivity(),View.OnClickListener,PaymentListAdapter.CallbackDataAdapter,OrderSelectAdapter.CallbackDataAdapter,AddAddressAdapter.CallbackDataAdapter,ResponseListener {

    var order_Title : TextView? = null

    var order_Type : TextView? = null

    var order_back : ImageView? = null

    var orderOptionRecyclerView : RecyclerView? = null

    var orderAddressRecyclerView : RecyclerView? = null

    var orderAddressTV : TextView? = null

    var adapterProduct: MutableList<Product> = ArrayList()

    var adapterProduct1: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    internal lateinit var productModel: Product

    var addAddressAdapter: AddAddressAdapter? =null

    var orderSelectAdapter: PaymentListAdapter? =null

    var continueTxt: TextView? =null

    var plus_Img: ImageView? = null

    var btnDatePicker: Button? = null
    var btnTimePicker:Button? = null
    var txtDate: EditText? = null
    var txtTime:EditText? = null
    private val mYear =0
    private  var mMonth:Int = 0
    private  var mDay:Int = 0
    private  var mHour:Int = 0
    private  var mMinute:Int = 0

    var addressReferenceCode: String = ""

    var total_value:String = ""

    var itemValue : String = ""

    var dbHelper : DBHelper? = null

    var selectTypeTV : TextView? = null

    var cardViewLayout : LinearLayout? = null

    var addressLayout : LinearLayout? = null

    var view1 : View? = null

    var view2 : View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_order_details)

        cardViewLayout = findViewById(R.id.cardViewLayout)

        cardViewLayout!!.visibility = View.GONE

        addressLayout = findViewById(R.id.addressLayout)

        addressLayout!!.visibility = View.GONE

        view1 = findViewById(R.id.view1)

        view1!!.visibility = View.GONE

        view2 = findViewById(R.id.view2)

        view2!!.visibility = View.GONE

        if(localgetUserInfoSlash(this,"nameKey").equals(""))
        {
            val config = resources.configuration
            val locale = Locale("en")
            Locale.setDefault(locale)
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
        }else
        {
            val config = resources.configuration
            val locale = Locale(localgetUserInfo("nameKey"))
            Locale.setDefault(locale)
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
        }

        dbHelper = DBHelper(this)

        order_back = findViewById(R.id.order_back) as ImageView

        order_back!!.setOnClickListener(this)

        order_back!!.setColorFilter(
            colorIcon(this,R.color.colorBlack,R.drawable.abc_ic_ab_back_material,order_back!!),
            PorterDuff.Mode.SRC_ATOP)

        plus_Img = findViewById(R.id.plus_Img) as ImageView


        order_Title = findViewById(R.id.order_Title)

        order_Title!!.typeface = fontStyle(this,"")

        order_Title!!.text = getString(R.string.addressText)

        order_Type = findViewById(R.id.order_Type)

        order_Type!!.typeface = fontStyle(this,"Light")

        orderAddressTV = findViewById(R.id.orderAddressTV)

        orderAddressTV!!.typeface = fontStyle(this,"Light")

        orderAddressTV!!.setOnClickListener(this)

        orderAddressTV!!.visibility = View.GONE

        continueTxt = findViewById(R.id.continueTxt)

        continueTxt!!.typeface = fontStyle(this,"SemiBold")

        continueTxt!!.setOnClickListener(this)

        continueTxt!!.visibility = View.GONE

        orderAddressRecyclerView = findViewById(R.id.orderAddressRecyclerView) as RecyclerView

        orderOptionRecyclerView = findViewById(R.id.orderOptionRecyclerView) as RecyclerView

        orderSelectAdapter = PaymentListAdapter(this,adapterProduct)

        orderOptionRecyclerView!!.setHasFixedSize(true)

        orderOptionRecyclerView!!.setLayoutManager(
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        )
        orderOptionRecyclerView!!.isNestedScrollingEnabled = false

        orderSelectAdapter!!.setCallback(this)

        orderOptionRecyclerView!!.setAdapter(orderSelectAdapter!!)

        orderOptionRecyclerView!!.visibility = View.GONE

        selectTypeTV = findViewById(R.id.selectTypeTV)

        selectTypeTV!!.visibility = View.GONE

        addAddressAdapter = AddAddressAdapter(this,adapterProduct1)

        orderAddressRecyclerView!!.setHasFixedSize(true)

        orderAddressRecyclerView!!.setLayoutManager(
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        )
        orderAddressRecyclerView!!.isNestedScrollingEnabled = false

        addAddressAdapter!!.setCallback(this)

        orderAddressRecyclerView!!.setAdapter(addAddressAdapter!!)

        showDataProduct("","")

        showDataProduct1()

        val intent = intent

        if(intent != null) {
            if (intent.getStringExtra("Total Amount") != null) {
                total_value = intent.getStringExtra("Total Amount")
            }

            if (intent.getStringExtra("Item") != null) {
                itemValue = intent.getStringExtra("Item")
            }
        }
        if(AppType.equals("0")) {

            plus_Img!!.setColorFilter(
                colorIcon(this, R.color.redColor, R.drawable.plus_add, plus_Img!!),
                PorterDuff.Mode.SRC_ATOP
            )

            orderAddressTV!!.setTextColor(Color.parseColor("#ec272b"))
        }else{
            plus_Img!!.setColorFilter(
                colorIcon(this, R.color.colorGreen, R.drawable.plus_add, plus_Img!!),
                PorterDuff.Mode.SRC_ATOP
            )

            orderAddressTV!!.setTextColor(Color.parseColor("#7DC77D"))
        }

        webService()
    }

    fun webService(){
        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        RequestManager.setAddressDetails(this,obj,this)
    }

    fun showDataProduct(time:String,type:String) {

        if (adapterProduct.size != 0) {
            adapterProduct.clear()
        }


        productModel = Product(
            R.drawable.pay_wallet,
            "Payment Via Wallet",
            "1234 5678 9012",
            "1",
            "",
            "4.0",
            "40 Mins",
            "200 for two",
            "","","","","","","","","","",
            adapterProductList
        )
        // adapterProduct1.add(productModel)

        productModel = Product(
            R.drawable.pay_paypall,
            "Payment Via Paypal",
            "Johndeo@gmail.com",
            "1",
            "",
            "4.0",
            "40 Mins",
            "200 for two",
            "","","","","","","","","","",
            adapterProductList
        )
        adapterProduct.add(productModel)

        productModel = Product(
            R.drawable.pay_card,
            "Payment Via Stripe",
            "**** **** **** 1234",
            "1",
            "",
            "4.0",
            "40 Mins",
            "200 for two",
            "","","","","","","","","","",
            adapterProductList
        )
        adapterProduct.add(productModel)

        productModel = Product(
            R.drawable.pay_cod,
            "Cash On Delivery",
            "",
            "1",
            "",
            "4.0",
            "40 Mins",
            "200 for two",
            "","","","","","","","","","",
            adapterProductList
        )
        // adapterProduct1.add(productModel)

        orderSelectAdapter!!.notifyDataSetChanged()
    }

    fun showDataProduct1() {

        if (adapterProduct1.size != 0) {
            adapterProduct1.clear()
        }
        productModel = Product(R.drawable.delete, "Er Vijendra Rao", "#304, 305, 5th St Ext, 5th Street Extension, Gandhipuram, Tamil Nadu 641012","1","","4.0","40 Mins","200 for two","","","","","","","","","","",adapterProductList)
        adapterProduct1.add(productModel)

        productModel = Product(R.drawable.delete, "David warner", "705, 8th St Ext, 5th Street Extension, Gandhipuram, Tamil Nadu 641012","1","","4.0","40 Mins","200 for two","","","","","","","","","","",adapterProductList)
        adapterProduct1.add(productModel)

        //addAddressAdapter!!.notifyDataSetChanged()
    }

    override fun onClick(view: View?) {
        when(view!!.id)
        {
            R.id.order_back ->{

                finish()
            }

            R.id.continueTxt ->{

                if(!addressReferenceCode.equals("")) {

                    var intent = Intent(this, PaymentActivity::class.java)

                    intent.putExtra("Total Amount", "" + total_value)

                    intent.putExtra("Address_Reference", "" + addressReferenceCode)

                    intent.putExtra("item", "" + itemValue)

                    startActivity(intent)
                }else{
                    showToast(this,getString(R.string.pleaseEnterAddress))
                }

            }
            R.id.orderAddressTV ->{

                startActivity(Intent(this,AddNewAddressActivity::class.java))

            }
        }
    }

    override fun setOnMaterial(id: String?) {

        if(id!!.equals("Delivery") || id!!.equals("Pick-up") )
        {
            val c = Calendar.getInstance()
            mHour = c[Calendar.HOUR_OF_DAY]
            mMinute = c[Calendar.MINUTE]

            // Launch Time Picker Dialog
            // Launch Time Picker Dialog
            val timePickerDialog = TimePickerDialog(
                this,
                OnTimeSetListener { view, hourOfDay, minute -> showDataProduct(""+dateConversionValue(""+hourOfDay +":" + minute+""),""+id) },
                mHour,
                mMinute,
                false
            )

            timePickerDialog.setTitle("Choose Time:")

            timePickerDialog.show()
        }else if(id!!.equals("Table Booking"))
        {
            val mIentent = Intent(this, DashBoardActivity::class.java)
            mIentent.putExtra("page","Table");
            startActivity(mIentent)
        }
        orderSelectAdapter!!.notifyDataSetChanged()
    }

    override fun setOnAddress(id: String?,pincode: String?,id1: String?,id2: String?,id3: String?) {

        addressReferenceCode = id!!

        customerPincode = pincode!!

        addAddressAdapter!!.notifyDataSetChanged()

    }

    override fun setOnDelete(id: String?) {
        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)
        obj.put("customerAddressReferenceCode", ""+ id)
        RequestManager.setAddRemove(this,obj,this)
    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if(responseObj != null) {
            if (requestType == Constant.MEMBER_customerAddress_URL_RQ) {

                if((responseObj as BaseRS).status.equals("5016"))
                {
                    if (adapterProduct1.size != 0) {
                        adapterProduct1.clear()
                    }

                    for (i in 0 until (responseObj as BaseRS).customerAddress!!.size) {

                        productModel = Product(R.drawable.delete, ""+localgetUserInfo("name"), ""+(responseObj as BaseRS).customerAddress!!.get(i).address,""+(responseObj as BaseRS).customerAddress!!.get(i).addressReferenceCode,""+(responseObj as BaseRS).customerAddress!!.get(i).postcode,"4.0","40 Mins","200 for two",""+(responseObj as BaseRS).customerAddress!!.get(i).completeAddress,""+(responseObj as BaseRS).customerAddress!!.get(i).floor,""+(responseObj as BaseRS).customerAddress!!.get(i).description,""+(responseObj as BaseRS).customerAddress!!.get(i).addressType,"","","","","","",adapterProductList)
                        adapterProduct1.add(productModel)

                    }
                    addAddressAdapter!!.notifyDataSetChanged()
                }

            }else if (requestType == Constant.MEMBER_deletCustomerAddress_URL_RQ) {
                if((responseObj as BaseRS).status.equals("5025"))
                {
                    showToast(this,(responseObj as BaseRS).message)
                    webService()
                }
            }
        }
    }

    override fun setOnAddress(id: String?) {

    }
}
