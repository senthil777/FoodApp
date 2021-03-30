package com.lieferin_global.Activity

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.CalenderListPageAdapter
import com.lieferin_global.Adapter.DetailedItemAddViewAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONObject
import java.text.DecimalFormat
import java.util.*


class DetailGroceryItemAddPageActivity : Activity(),View.OnClickListener,DetailedItemAddViewAdapter.CallbackDataAdapter,CalenderListPageAdapter.CallbackDataAdapter,ResponseListener {

    var details_item_add_back: ImageView? = null

    var details_item_add_search: ImageView? = null

    var details_item_add_favorite: ImageView? = null

    var details_item_add_hotelIcon: CircleImageView? = null

    var details_item_add_hotelName: TextView? = null

    var details_item_add_hotelDescription: TextView? = null

    var details_item_add_RecyclerView: RecyclerView? = null

    var detailItemAddPageActivity: DetailedItemAddViewAdapter? =null

    var adapterProduct: MutableList<Product> = ArrayList()

    var adapterProductDelete: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    internal lateinit var productModel: Product

    var detailsDescriptionTV: TextView? = null

    var applyCouponTV: TextView? = null

    var applyCouponET: EditText? = null

    var billDetailsTV: TextView? = null

    var itemTotalTV: TextView? = null

    var itemTotalPrice: TextView? = null

    var totalPrice : Double? = 0.0

    var totalPriceValue : Double? = 0.0

    var restaurantChargesTV: TextView? = null

    var restaurantPriceTV: TextView? =null

    var deliveryFeeTV: TextView? = null

    var deliveryFeePriceTV: TextView? = null

    var toPayTV: TextView? = null

    var toPayPriceTV: TextView? = null

    var detailsDescriptionIV: ImageView? = null

    var applyCouponIV: ImageView? = null

    var arrowIV: ImageView? = null

    var priceLayout : RelativeLayout? =null

    var addItemTV :TextView? = null

    var addPrice :TextView? = null

    var addToBasket : TextView? =null

    var dbHelper: DBHelper? = null

    var total_Id : String? =null

    var continueTxt : TextView? = null

    var storeName : String? = null

    var detailsDescriptionET : EditText? = null


    var asapLL : LinearLayout? = null

    var laterLL : LinearLayout? = null

    var asapIV : ImageView? = null

    var laterIV : ImageView? = null

    var asapTv : TextView? = null

    var laterTv : TextView? = null
    var mAdapter1: CalenderListPageAdapter? = null

    internal lateinit var adapterModel: AdapterModel

    var dateVale =""

    var selectTime = ""

    var selectDate : TextView? = null

    var adapterModels1: MutableList<AdapterModel> = ArrayList()

    var adapterModelsProduct: MutableList<Product> = ArrayList()

    var calLay : LinearLayout? = null

    var deliveryTypeLL : LinearLayout? = null

    var pickUpTime : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_detail_item_add_page)

        dbHelper = DBHelper(this)

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

        Constant.PickUpType = "0"

        val intent = intent

        if(intent != null) {
            if (intent.getStringExtra("id") != null) {
                total_Id = intent.getStringExtra("id")
            }
        }

        var getBundle: Bundle? = null
        getBundle = intent.extras
        val name = getBundle!!.getString("restaurantImg")

        storeName = getBundle!!.getString("restaurantReferenceCode")


        for (i in 0 until dbHelper!!.getMenu().size) {

            if(!dbHelper!!.getMenu().get(i).restaurantId!!.equals(name))
            {

                productModel = Product(
                    0,
                    ""+dbHelper!!.getMenu().get(i).restaurantId!!,
                    "",
                    "1",
                    "",
                    "4.0",
                    "",
                    "",
                    "","","","","","","","","","",

                    adapterProductList
                )
                adapterProductDelete.add(productModel)


            }
        }
/*
        for (i in 0 until adapterProductDelete.size) {
            dbHelper!!.deleteCategory(adapterProductDelete.get(i).name!!)

            dbHelper!!.deleteToppinsFull(adapterProductDelete.get(i).name!!)

            dbHelper!!.deleteToppinsGroupFull(adapterProductDelete.get(i).name!!)

            dbHelper!!.deleteRestaurant(adapterProductDelete.get(i).name!!)

            dbHelper!!.deleteMenuValueReat(adapterProductDelete.get(i).name!!)
        }*/

        details_item_add_hotelIcon = findViewById(R.id.details_item_add_hotelIcon) as CircleImageView
        if(!name.equals("")) {
            Picasso.with(this).load(name).resize(450, 450)
                .placeholder(R.drawable.restaurant_placeholder).into(details_item_add_hotelIcon)
        }else{
            Picasso.with(this).load(R.drawable.restaurant_placeholder).resize(450, 450)
                .into(details_item_add_hotelIcon)
        }
        details_item_add_back = findViewById(R.id.order_back) as ImageView

        details_item_add_back!!.setColorFilter(
            colorIcon(this,R.color.colorBlack,R.drawable.abc_ic_ab_back_material,details_item_add_back!!),
            PorterDuff.Mode.SRC_ATOP)

        details_item_add_back!!.setOnClickListener(this)

        details_item_add_favorite = findViewById(R.id.details_item_add_favorite) as ImageView

        details_item_add_favorite!!.setColorFilter(
            colorIcon(this,R.color.colorBlack,R.drawable.favorite,details_item_add_favorite!!),
            PorterDuff.Mode.SRC_ATOP)

        details_item_add_search = findViewById(R.id.details_item_add_search) as ImageView

        details_item_add_search!!.setColorFilter(
            colorIcon(this,R.color.colorBlack,R.drawable.search,details_item_add_search!!),
            PorterDuff.Mode.SRC_ATOP)

        details_item_add_hotelName = findViewById(R.id.order_Title) as TextView

        details_item_add_hotelName!!.typeface = fontStyle(this,"SemiBold")


        details_item_add_hotelDescription = findViewById(R.id.order_Type) as TextView

        details_item_add_hotelDescription!!.typeface = fontStyle(this,"Light")

        details_item_add_RecyclerView = findViewById(R.id.details_item_add_RecyclerView) as RecyclerView

        detailItemAddPageActivity = DetailedItemAddViewAdapter(this,adapterProduct)

        details_item_add_RecyclerView!!.setHasFixedSize(true)

        details_item_add_RecyclerView!!.setLayoutManager(
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        )
        details_item_add_RecyclerView!!.isNestedScrollingEnabled = false

        detailItemAddPageActivity!!.setCallback(this)

        details_item_add_RecyclerView!!.setAdapter(detailItemAddPageActivity!!)

        detailsDescriptionTV = findViewById(R.id.detailsDescriptionTV) as TextView

        detailsDescriptionTV!!.typeface = fontStyle(this,"Light")

        applyCouponTV = findViewById(R.id.applyCouponTV) as TextView

        applyCouponTV!!.typeface = fontStyle(this,"Light")

        applyCouponTV!!.setOnClickListener(this)

        applyCouponET = findViewById(R.id.applyCouponET) as EditText

        applyCouponET!!.typeface = fontStyle(this,"Light")

        applyCouponET!!.visibility = View.GONE

        billDetailsTV = findViewById(R.id.billDetailsTV) as TextView

        billDetailsTV!!.typeface = fontStyle(this,"SemiBold")

        itemTotalTV = findViewById(R.id.itemTotalTV) as TextView

        itemTotalTV!!.typeface = fontStyle(this,"Light")

        itemTotalPrice = findViewById(R.id.itemTotalPrice) as TextView

        itemTotalPrice!!.typeface = fontStyle(this,"Light")

        restaurantChargesTV = findViewById(R.id.restaurantChargesTV) as TextView

        restaurantChargesTV!!.typeface = fontStyle(this,"Light")

        restaurantPriceTV = findViewById(R.id.restaurantPriceTV) as TextView

        restaurantPriceTV!!.typeface = fontStyle(this,"Light")

        deliveryFeeTV = findViewById(R.id.deliveryFeeTV) as TextView

        deliveryFeeTV!!.typeface = fontStyle(this,"Light")

        deliveryFeePriceTV = findViewById(R.id.deliveryFeePriceTV) as TextView

        deliveryFeePriceTV!!.typeface = fontStyle(this,"Light")

        toPayTV = findViewById(R.id.toPayTV) as TextView

        toPayTV!!.typeface = fontStyle(this,"SemiBold")

        toPayPriceTV = findViewById(R.id.toPayPriceTV) as TextView

        toPayPriceTV!!.typeface = fontStyle(this,"Light")

        continueTxt = findViewById(R.id.continueTxt) as TextView

        continueTxt!!.typeface = fontStyle(this,"SemiBold")

        continueTxt!!.setOnClickListener(this)

        detailsDescriptionET = findViewById(R.id.detailsDescriptionET) as EditText

        detailsDescriptionET!!.typeface = fontStyle(this,"")

        detailsDescriptionET!!.hint = "Any store request? We will try our best to convey it"

        detailsDescriptionIV = findViewById(R.id.detailsDescriptionIV) as ImageView

        detailsDescriptionIV!!.setColorFilter(
            colorIcon(this,R.color.colorBlack,R.drawable.notepad,detailsDescriptionIV!!),
            PorterDuff.Mode.SRC_ATOP)

        applyCouponIV = findViewById(R.id.applyCouponIV) as ImageView

        applyCouponIV!!.setColorFilter(
            colorIcon(this,R.color.colorBlack,R.drawable.discount,applyCouponIV!!),
            PorterDuff.Mode.SRC_ATOP)

        arrowIV = findViewById(R.id.arrowIV) as ImageView

        arrowIV!!.setColorFilter(
            colorIcon(this,R.color.colorBlack,R.drawable.arrow,arrowIV!!),
            PorterDuff.Mode.SRC_ATOP)

        priceLayout = findViewById(R.id.priceLayout) as RelativeLayout

        priceLayout!!.setOnClickListener(this)

        addItemTV = findViewById(R.id.addItemTV) as TextView

        addItemTV!!.typeface = fontStyle(this,"Light")

        addPrice = findViewById(R.id.addPrice) as TextView

        addPrice!!.text = customText(this,"€ 125.5 Plus Taxes",7,"SemiBold","")

        addToBasket = findViewById(R.id.addToBasket) as TextView

        addToBasket!!.setOnClickListener(this)

        addToBasket!!.typeface = fontStyle(this,"Light")

        showDataProduct()
        if(AppType!!.equals("0")) {

            details_item_add_hotelName!!.text = dbHelper!!.getRestaurant().restaurantName

            continueTxt!!.setBackgroundResource(R.drawable.home_radius_button_orange)
        }else{
            details_item_add_hotelName!!.text = ""+dbHelper!!.getGrecoryTotalPrice(storeName!!).get(0).name

            continueTxt!!.setBackgroundResource(R.drawable.home_radius_button_green)
        }

        addItemTV!!.text = getString(R.string.Added_Item)+adapterProduct.size

        deliveryTypeLL = findViewById(R.id.deliveryTypeLL) as LinearLayout

        asapLL = findViewById(R.id.asapLL) as LinearLayout

        asapLL!!.setOnClickListener(this)

        calLay = findViewById(R.id.calLay) as LinearLayout

        laterLL = findViewById(R.id.laterLL) as LinearLayout

        laterLL!!.setOnClickListener(this)

        asapTv = findViewById(R.id.asapTv) as TextView

        asapTv!!.setTypeface(fontStyle(this,"Light"))

        laterTv = findViewById(R.id.laterTv) as TextView

        laterTv!!.setTypeface(fontStyle(this,"Light"))

        laterIV = findViewById(R.id.laterIV) as ImageView

        asapIV = findViewById(R.id.asapIV) as ImageView

        pickUpTime = findViewById(R.id.pickUpTime) as TextView

        pickUpTime!!.setTypeface(fontStyle(this,"Light"))


        if(Constant.DeliveryType!!.equals("3"))
        {
            deliveryTypeLL!!.visibility = View.VISIBLE
        }else{
            deliveryTypeLL!!.visibility = View.GONE
        }
        Log.v("kkk"+ Constant.DeliveryType,"===")


        selectDate = findViewById(R.id.selectDate) as TextView

        selectDate!!.typeface = fontStyle(this,"")

        val calendar =
            Calendar.getInstance(TimeZone.getDefault())
        calendar[Calendar.YEAR]
        calendar.add(Calendar.DAY_OF_MONTH, 0)
        var dateValue = calendar[Calendar.MONTH] + 1
        dateVale =
            "" + calendar[Calendar.YEAR] + "-" + dateValue + "-" + calendar[Calendar.DAY_OF_MONTH]

        val calenderViewList = findViewById(R.id.dateRecyclerView) as RecyclerView
        mAdapter1 = CalenderListPageAdapter(this, adapterModels1)


        calenderViewList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        calenderViewList.itemAnimator!!.addDuration = 5000
        mAdapter1!!.setCallback(this)
        calenderViewList.adapter = mAdapter1

        calenderViewList.isNestedScrollingEnabled = false
        showDate()
    }
    fun showDate() {
        if (adapterModels1 != null) {
            adapterModels1.clear()
        }
        for (i in 0..10) {
            val calendar =
                Calendar.getInstance(TimeZone.getDefault())
            calendar[Calendar.YEAR]
            calendar.add(Calendar.DAY_OF_MONTH, i)
            val date = calendar[Calendar.DAY_OF_MONTH]

            val monthValue = calendar[Calendar.MONTH]
            val month = calendar.getDisplayName(
                Calendar.MONTH,
                Calendar.LONG,
                Locale.getDefault()
            )
            val day = calendar.getDisplayName(
                Calendar.DAY_OF_WEEK,
                Calendar.LONG,
                Locale.getDefault()
            )
            val year = calendar[Calendar.YEAR]
            var month_num = calendar[Calendar.MONTH]
            month_num = month_num + 1
            var dateValue: String
            dateValue = if (date < 9) {
                "0$date"
            } else {
                "" + date
            }
            if (i == 0) {
                //select_date = "$year-$month_num-$date"
            }
            adapterModel = AdapterModel(
                0,
                ""+month,
                "" + dateValue,
                ""+day,
                "" + year,
                "" + month_num,
                "Purple Beauty Park",
                "Tatabad,Gandhipuram",
                "4.4",
                "Very Good",
                "3054 Ratings>",
                "5 Star Given by you",
                "1",
                "",
                "",
                "",
                "",
                "", 0, 0, 0, adapterModelsProduct
            )

            adapterModels1.add(adapterModel)
        }
        mAdapter1!!.notifyDataSetChanged()
    }

    fun showDataProduct() {

        if (adapterProduct.size != 0) {
            adapterProduct.clear()
        }

        toPayPriceTV!!.text ="€ 00.00"

        itemTotalPrice!!.text ="€ 00.00"

        for (i in 0 until dbHelper!!.getGrecoryTotalPrice(storeName!!).size) {

            var addVal:String = ""

            var addPrice:Double = 0.0

            for(j in 0 until dbHelper!!.getGrecoryTotalPrice(storeName!!).size)
            {
                addPrice = addPrice + dbHelper!!.getGrecoryTotalPrice(storeName!!).get(i).price!!.toDouble()
                /*if(addVal!!.equals("")) {
                    addVal = dbHelper!!.getMenu_toppins(dbHelper!!.getMenu().get(i).id!!).get(j).blockName!!
                }else{
                    addVal = addVal +" , "+ dbHelper!!.getMenu_toppins(dbHelper!!.getMenu().get(i).id!!).get(j).blockName!!
                }*/
            }

            productModel = Product(
                R.drawable.img_1,
                ""+dbHelper!!.getGrecoryTotalPrice(storeName!!).get(i).productName,
                ""+dbHelper!!.getGrecoryTotalPrice(storeName!!).get(i).price,
                ""+dbHelper!!.getGrecoryTotalPrice(storeName!!).get(i).quantity,
                ""+dbHelper!!.getGrecoryTotalPrice(storeName!!).get(i).productReferenceCode,
                "4.0",
                addVal,
                ""+addPrice,
                "1","","","","","","","","","",

                adapterProductList
            )
            adapterProduct.add(productModel)

            totalPrice = 0.0

            for(i in 0 until dbHelper!!.getGrecoryTotalPrice(storeName!!).size) {

                totalPrice = totalPrice!! + addIncreasePriceHole(dbHelper!!.getGrecoryTotalPrice(storeName!!).get(i).price!!,""+dbHelper!!.getGrecoryTotalPrice(storeName!!).get(i).quantity!!)

                Log.v("Price ="+dbHelper!!.getGrecoryTotalPrice(storeName!!).get(i).totalPrice!!,"Quality ="+dbHelper!!.getGrecoryTotalPrice(storeName!!).get(i).quantity!!)
            }

            Log.v("Total ="+totalPrice,"Quality =")

            //totalPrice = totalPrice!! + addIncreasePrice(dbHelper!!.getMenu().get(i).price!!,""+addPrice)

            itemTotalPrice!!.text = "€ "+DecimalFormat("##.##").format(totalPrice!!)

            totalPriceValue = totalPrice!! + restaurantPriceTV!!.text.toString().replace("€ ","").toDouble() + deliveryFeePriceTV!!.text.toString().replace("€ ","").toDouble()

            //totalPriceValue = addIncreasePrice(""+totalPriceValue!!,deliveryFeePriceTV!!.text.toString().replace("€ ",""))

            //toPayPriceTV!!.text = "€ "+DecimalFormat("##.##").format(totalPriceValue!!.toDouble())

            toPayPriceTV!!.text = "€ "+DecimalFormat("##.##").format(totalPrice!!.toDouble())
        }

        addPrice!!.text = customText(this,"€ "+DecimalFormat("##.##").format(totalPriceValue!!.toDouble())+" "+getString(R.string.Plus_Taxes),7,"SemiBold","")

        detailItemAddPageActivity!!.notifyDataSetChanged()
    }

    override fun onClick(view: View?) {
        when(view!!.id)
        {
            R.id.asapLL ->{
                asapIV!!.setColorFilter(colorIcon(this,R.color.colorWhite,R.drawable.asap,asapIV!!))
                laterIV!!.setColorFilter(colorIcon(this,R.color.colorBlack,R.drawable.later,laterIV!!))
                asapLL!!.setBackgroundResource(R.drawable.backgound_light_corner)
                laterLL!!.setBackgroundResource(R.drawable.home_cal_black_corner)
                asapTv!!.setTextColor(Color.parseColor("#ffffff"))
                laterTv!!.setTextColor(Color.parseColor("#484848"))
                calLay!!.visibility = View.GONE

                Constant.PickUpType = "0"
            }
            R.id.laterLL ->{
                asapIV!!.setColorFilter(colorIcon(this,R.color.colorBlack,R.drawable.asap,asapIV!!))
                laterIV!!.setColorFilter(colorIcon(this,R.color.colorWhite,R.drawable.later,laterIV!!))
                asapLL!!.setBackgroundResource(R.drawable.home_cal_black_corner)
                laterLL!!.setBackgroundResource(R.drawable.backgound_light_corner)
                asapTv!!.setTextColor(Color.parseColor("#484848"))
                laterTv!!.setTextColor(Color.parseColor("#ffffff"))
                calLay!!.visibility = View.VISIBLE
                Constant.PickUpType = "1"
            }

            R.id.continueTxt -> {
                //startActivity(Intent(this,OrderDetailsActivity::class.java))
                if (dbHelper!!.getUserDetails().token == null) {
                    val mIentent = Intent(this, LoginActivity::class.java)
                    //mIentent.putExtra("page",Constant.Location);
                    startActivity(mIentent)
                } else {
                    Constant.AddRestaruantNote = "" + detailsDescriptionET!!.text.toString()
                    if (dbHelper!!.getGrecoryTotalPrice(storeName!!).size != 0) {

                        var intent = Intent(this, OrderDetailsActivity::class.java)

                        intent.putExtra("Total Amount", "" + toPayPriceTV!!.text.toString())

                        intent.putExtra("Item", "" + adapterProduct.size)

                        if (Constant.PickUpType.equals("1")) {
                            if (!pickUpTime!!.text.equals("")) {
                                startActivity(intent)
                            } else {
                                showToast(this, "Please select data and time")
                            }

                        } else {
                            startActivity(intent)
                        }
                    } else {
                        showToast(this, "" + getString(R.string.Please_select_item))
                    }
                }
            }

            R.id.order_back ->{

                var intent = Intent(this, DashBoardGrpceryActivity::class.java)

                intent.putExtra("Title", "" + storeName)

                intent.putExtra("page", ""+getString(R.string.Details_Page))

                startActivity(intent)

                finish()
            }
            R.id.applyCouponTV ->{
                applyCouponTV!!.visibility = View.GONE

                applyCouponET!!.isEnabled = true

                applyCouponET!!.visibility = View.VISIBLE

                applyCouponET!!.requestFocus();
            }
        }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                var intent = Intent(this, DashBoardGrpceryActivity::class.java)

                intent.putExtra("Title", "" + storeName)

                intent.putExtra("page", ""+getString(R.string.Details_Page))

                intent.putExtra("Delivery Type", ""+Constant.DeliveryType)

                startActivity(intent)

                finish()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
    override fun setOnMaterial(userId: Product?, isTrue: Boolean, id: String?, position: Int) {

        Log.v("hhhh","=="+id);



        dbHelper!!.updateGroceryDetailsOne(userId!!,id!!,""+position!!)


        totalPrice = 0.0

        for(i in 0 until dbHelper!!.getGrecoryTotalPrice(storeName!!).size) {

            totalPrice = totalPrice!! + + addIncreasePriceHole(dbHelper!!.getGrecoryTotalPrice(storeName!!).get(i).price!!,""+dbHelper!!.getGrecoryTotalPrice(storeName!!).get(i).quantity!!)
        }


        itemTotalPrice!!.text = "€ "+DecimalFormat("##.##").format(totalPrice!!)

        totalPriceValue = totalPrice!! + restaurantPriceTV!!.text.toString().replace("€ ","").toDouble() + deliveryFeePriceTV!!.text.toString().replace("€ ","").toDouble()

        toPayPriceTV!!.text = "€ "+DecimalFormat("##.##").format(totalPrice!!)

        addPrice!!.text = customText(this,"€ "+DecimalFormat("##.##").format(totalPriceValue!!)+" "+getString(R.string.Plus_Taxes),7,"SemiBold","")

    }

    override fun setOnAddText(addText: String?) {
        dbHelper!!.deleteGroceryFull(addText!!)

        showDataProduct()
    }

    override fun setOnEdit(addText: String?) {

    }

    fun webservice(time:String,dateString:String){

        var day ="0"

        if(dateString!!.equals("Sun"))
        {
            day = "1"
        }else if(dateString!!.equals("Mon"))
        {
            day = "2"
        }else if(dateString!!.equals("Tus"))
        {
            day = "3"
        }else if(dateString!!.equals("Wed"))
        {
            day = "4"
        }else if(dateString!!.equals("Thu"))
        {
            day = "5"
        }else if(dateString!!.equals("Fri"))
        {
            day = "6"
        }else if(dateString!!.equals("Sat"))
        {
            day = "7"
        }
        val obj = JSONObject()
        obj.put("groceryReferenceCode", ""+dbHelper!!.getGrecoryTotalAll().get(0).restaurantReferenceCode)
        obj.put("bookingTime", ""+time)
        obj.put("dayId",""+day)
        RequestManager.setGroceryAvailability(this,obj,this)
        loadingScreen(this)
    }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun setOnDetails(userId: String?) {
        Log.v("Date == "+userId,"Value"+dateConversionTimeDay(userId))

        dateVale = userId!!

        mAdapter1!!.notifyDataSetChanged()

        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            val sdf = SimpleDateFormat("HH")
            val sdf1 = SimpleDateFormat("mm")
            val sdf2 = SimpleDateFormat("dd")
            val currentDate = sdf.format(Date())
            val currentDate1 = sdf1.format(Date())
            val currentDate2 = sdf2.format(Date())

            Log.i(dateCheck(userId,""+currentDate2),"jjj");

            Log.i(timeCheck(SimpleDateFormat("HH").format(cal.time),""+currentDate,SimpleDateFormat("mm").format(cal.time),currentDate1)+"","22");

            if(dateCheck(userId,""+currentDate2).equals("true")) {

                if(timeCheck(SimpleDateFormat("HH").format(cal.time),""+currentDate,SimpleDateFormat("mm").format(cal.time),currentDate1).equals("true")) {

                    //pickUpTime!!.text = "Time : " + SimpleDateFormat("hh:mm a").format(cal.time)

                    selectTime =  "Time : " + SimpleDateFormat("hh:mm a").format(cal.time)

                    Constant.PickUpDate = dateVale + " " + SimpleDateFormat("HH:mm:ss").format(cal.time)

                    webservice(SimpleDateFormat("HH:mm").format(cal.time),dateConversionTimeDay(userId)!!)


                }else{
                    showToast(this,"Please select valid time")
                }
            }else{
                //pickUpTime!!.text = "Time : " + SimpleDateFormat("hh:mm a").format(cal.time)

                selectTime =  "Time : " + SimpleDateFormat("hh:mm a").format(cal.time)

                Constant.PickUpDate = dateVale + " " + SimpleDateFormat("HH:mm:ss").format(cal.time)

                webservice(SimpleDateFormat("HH:mm").format(cal.time),dateConversionTimeDay(userId)!!)
            }

            Log.i(timeCheckValue(Constant.openingTime,""+ Constant.closingTime,SimpleDateFormat("HH").format(cal.time),SimpleDateFormat("mm").format(cal.time)) +"","22");


        }
        TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if (responseObj != null) {
            if (requestType == Constant.MEMBER_groceryAvailability_URL_RQ) {

                loadingScreenClose(this)
                showToast(this, (responseObj as BaseRS).message)

                if((responseObj as BaseRS).status.equals("5833"))
                {
                    //valString = "1"
                    pickUpTime!!.text = ""+selectTime
                }else{
                    pickUpTime!!.text = ""
                }

            }
        }
    }
}
