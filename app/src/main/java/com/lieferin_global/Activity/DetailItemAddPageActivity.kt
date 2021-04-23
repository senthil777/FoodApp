package com.lieferin_global.Activity

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.CalenderListPageAdapter
import com.lieferin_global.Adapter.DetailedItemAddViewAdapter
import com.lieferin_global.Adapter.DialogListItemAdapter
import com.lieferin_global.Adapter.ToppinesPopupAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
import com.lieferin_global.Model.ProductListView
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.Constant.BookingType
import com.lieferin_global.Utility.Constant.DeliveryType
import com.lieferin_global.Utility.Constant.PickUpDate
import com.lieferin_global.Utility.Constant.PickUpType
import com.lieferin_global.Utility.Constant.closingTime
import com.lieferin_global.Utility.Constant.isAdminString
import com.lieferin_global.Utility.Constant.isPayType
import com.lieferin_global.Utility.Constant.openingTime
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONArray
import org.json.JSONObject
import java.text.DecimalFormat
import java.util.*


class DetailItemAddPageActivity : Activity(),View.OnClickListener,DetailedItemAddViewAdapter.CallbackDataAdapter,CalenderListPageAdapter.CallbackDataAdapter,ResponseListener,ToppinesPopupAdapter.CallbackDataAdapter {

    var details_item_add_back: ImageView? = null

    var asapLL : LinearLayout? = null

    var laterLL : LinearLayout? = null

    var asapIV : ImageView? = null

    var laterIV : ImageView? = null

    var asapTv : TextView? = null

    var adapterCategories: MutableList<Product> = ArrayList()

    var toppinesPopupAdapter: ToppinesPopupAdapter? = null

    var laterTv : TextView? = null

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

    var adapterProductGroupList: MutableList<ProductList> = ArrayList()

    var adapterTrendingItem: MutableList<AdapterModel> = ArrayList<AdapterModel>()

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

    var storeName : String? =null

    var continueTxt : TextView? = null

    var selectDate : TextView? = null

    var detailsDescriptionET : EditText? = null

    var mAdapter1: CalenderListPageAdapter? = null

    internal lateinit var adapterModel: AdapterModel

    var dateVale =""

    var selectTime = ""

    var adapterModels1: MutableList<AdapterModel> = ArrayList()

    var adapterModelsProduct: MutableList<Product> = ArrayList()

    var calLay : LinearLayout? = null

    var deliveryTypeLL : LinearLayout? = null

    var pickUpTime : TextView? = null

    var valString : String = "0"

    var minimumAmount : String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_detail_item_add_page)

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

        PickUpType = "0"

        calLay = findViewById(R.id.calLay) as LinearLayout

        dbHelper = DBHelper(this)

        deliveryTypeLL = findViewById(R.id.deliveryTypeLL) as LinearLayout

        asapLL = findViewById(R.id.asapLL) as LinearLayout

        asapLL!!.setOnClickListener(this)

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

        minimumAmount= ""+getBundle!!.getString("minimumAmount")


        for (i in 0 until dbHelper!!.getMenu().size) {

            if(!dbHelper!!.getMenu().get(i).restaurantId!!.equals(name))
            {

                productModel = Product(
                    R.drawable.img_1,
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

        detailsDescriptionTV!!.setOnClickListener(this)

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

        detailsDescriptionET!!.hint = "Any restaurant request? We will try our best to convey it"

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
            details_item_add_hotelName!!.text = "GreenFram"

            continueTxt!!.setBackgroundResource(R.drawable.home_radius_button_green)
        }

        addItemTV!!.text = ""+getString(R.string.Added_Item)+adapterProduct.size

        if(DeliveryType!!.equals("3"))
        {
            deliveryTypeLL!!.visibility = View.VISIBLE
        }else{
            deliveryTypeLL!!.visibility = View.GONE
        }
        Log.v("kkk"+DeliveryType,"===")
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
    fun showDialogItem(
        context: Context?
    ) {
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
        dialog.setContentView(R.layout.item_value_main)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val titleEdit = dialog.findViewById<View>(R.id.titleEdit) as TextView

        titleEdit.typeface = fontStyle(this,"SemiBold")

        val yesTV = dialog.findViewById<View>(R.id.yesTV) as TextView

        yesTV.typeface = fontStyle(this,"SemiBold")

        yesTV.setOnClickListener(View.OnClickListener {
            updateToppins()
            dialog.cancel() })

        val orderList = dialog.findViewById<View>(R.id.itemRecyclerView) as RecyclerView
        for(i in 0 until adapterProductGroupList!!.size) {
            for (j in 0 until adapterProductGroupList!!.get(i).toppinsList.size) {
                adapterProductGroupList!!.get(i).toppinsList.get(j).headerSub ="2"

            }
        }
        toppinesPopupAdapter =
            ToppinesPopupAdapter(context, adapterProductGroupList, dialog!!)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        orderList!!.layoutManager = mLayoutManager

        orderList!!.itemAnimator!!.addDuration = 5000

        orderList!!.adapter = toppinesPopupAdapter

        toppinesPopupAdapter!!.setCallback(this)

        dialog.show()
    }

    fun updateToppins()
    {
        var menuId = ""

        var restaurant = ""

        var totalValue = 0.0

        var totalMenuPrice =0.0
        for(i in 0 until adapterProductGroupList!!.size)
        {
            for(j in 0 until adapterProductGroupList!!.get(i).toppinsList.size)
            {
                menuId = ""+adapterProductGroupList!!.get(i).toppinsList.get(j).menuId!!

                totalMenuPrice =dbHelper!!.getMenuPrice(menuId).toDouble()

                restaurant = ""+dbHelper!!.getMenuRestaurant(menuId)

               Log.v("llll "+adapterProductGroupList!!.get(i).toppinsList.get(j).headerSub,"ppp"+adapterProductGroupList!!.get(i).toppinsList.get(j).price)

                if(adapterProductGroupList!!.get(i).toppinsList.get(j).headerSub.equals("1"))
                {
                    totalValue = totalValue +adapterProductGroupList!!.get(i).toppinsList.get(j).price!!.toDouble()
                }
            }
        }
        Log.v("llll ","ppp "+totalValue)
        totalMenuPrice = totalMenuPrice + totalValue
        dbHelper!!.toppinsDelete(menuId)
        dbHelper!!.updateMenuTotal(menuId,totalMenuPrice.toString())

        for(i in 0 until adapterProductGroupList!!.size) {
            for (j in 0 until adapterProductGroupList!!.get(i).toppinsList.size) {
                if(adapterProductGroupList!!.get(i).toppinsList.get(j).headerSub.equals("1")) {
                    dbHelper!!.addToppins_info(
                        adapterProductGroupList!!.get(i).toppinsList.get(j),
                        restaurant,
                        menuId
                    )
                }
            }
        }

        showDataProduct()
    }


    fun showDataProduct() {

        if (adapterProduct.size != 0) {
            adapterProduct.clear()
        }

        toPayPriceTV!!.text ="€ 00.00"

        itemTotalPrice!!.text ="€ 00.00"
        for (i in 0 until dbHelper!!.getMenu().size) {

            var addVal:String = ""

            var addPrice:Double = 0.0

            for(j in 0 until dbHelper!!.getMenu_toppins(dbHelper!!.getMenu().get(i).id!!).size)
            {
                addPrice = addPrice + dbHelper!!.getMenu_toppins(dbHelper!!.getMenu().get(i).id!!).get(j).price!!.toDouble()
                if(addVal!!.equals("")) {
                    addVal = "+ "+dbHelper!!.getMenu_toppins(dbHelper!!.getMenu().get(i).id!!).get(j).blockName!!
                }else{
                    addVal = addVal +"\n + "+ dbHelper!!.getMenu_toppins(dbHelper!!.getMenu().get(i).id!!).get(j).blockName!!
                }
            }

            productModel = Product(
                R.drawable.img_1,
                ""+dbHelper!!.getMenu().get(i).name,
                ""+dbHelper!!.getMenu().get(i).totalPrice,
                ""+dbHelper!!.getMenu().get(i).rating,
                ""+dbHelper!!.getMenu().get(i).id!!,
                "4.0",
                ""+addVal,
                ""+addPrice,
                ""+dbHelper!!.getMenu().get(i).offerType,"","","","","","","","","",

                adapterProductList
            )
            adapterProduct.add(productModel)

            totalPrice = 0.0

            for(i in 0 until dbHelper!!.getMenu().size) {

                totalPrice = totalPrice!! + addIncreasePriceHole(dbHelper!!.getMenu().get(i).totalPrice!!,""+dbHelper!!.getMenu().get(i).rating!!)

                Log.v("Price ="+dbHelper!!.getMenu().get(i).totalPrice!!,"Quality ="+dbHelper!!.getMenu().get(i).rating!!)
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

                PickUpType = "0"
            }
            R.id.laterLL ->{
                asapIV!!.setColorFilter(colorIcon(this,R.color.colorBlack,R.drawable.asap,asapIV!!))
                laterIV!!.setColorFilter(colorIcon(this,R.color.colorWhite,R.drawable.later,laterIV!!))
                asapLL!!.setBackgroundResource(R.drawable.home_cal_black_corner)
                laterLL!!.setBackgroundResource(R.drawable.backgound_light_corner)
                asapTv!!.setTextColor(Color.parseColor("#484848"))
                laterTv!!.setTextColor(Color.parseColor("#ffffff"))
                calLay!!.visibility = View.VISIBLE
                PickUpType = "1"
            }
            R.id.continueTxt ->{
                //startActivity(Intent(this,OrderDetailsActivity::class.java))

                if(dbHelper!!.getUserDetails().token == null)
                {
                    val mIentent = Intent(this, LoginActivity::class.java)
                    //mIentent.putExtra("page",Constant.Location);
                    startActivity(mIentent)
                }else {

                    Constant.totalPrice = "" + toPayPriceTV!!.text.toString()

                    if (isAdminString!!.equals("1")) {

                        if (toPayPriceTV!!.text.toString().replace("€ ", "")
                                .toDouble() > minimumAmount!!.toDouble()
                        ) {
                            nextPay()
                        } else {
                            showToast(this, "Please add minimum amount " + minimumAmount)
                        }
                    } else if (isAdminString!!.equals("0")) {
                        if (isPayType!!.equals("0")) {
                            if (toPayPriceTV!!.text.toString().replace("€ ", "")
                                    .toDouble() > minimumAmount!!.toDouble()
                            ) {
                                nextPay()
                            } else {
                                showToast(this, "Please add minimum amount " + minimumAmount)
                            }
                        } else {
                            RequestManager.setTableReservtionMenuPicked(
                                this,
                                createWebServiceTable(),
                                this
                            )
                        }
                    }
                }
            }

            R.id.order_back ->{
                var intent = Intent(this, DashBoardActivity::class.java)

                intent.putExtra("Title", "" + storeName)

                intent.putExtra("page", getString(R.string.Details_Page))

                startActivity(intent)

                finish()
            }
            R.id.detailsDescriptionTV ->{

                if(detailsDescriptionTV!!.visibility == View.VISIBLE)
                {
                    detailsDescriptionTV!!.visibility = View.GONE

                    detailsDescriptionET!!.visibility == View.VISIBLE
                }
            }
            R.id.applyCouponTV ->{
                /*applyCouponTV!!.visibility = View.GONE

                applyCouponET!!.isEnabled = true

                applyCouponET!!.visibility = View.VISIBLE

                applyCouponET!!.requestFocus();*/
                showDialogReservationCancel(this)
            }
        }
    }

    fun showDialogReservation(
        context: Context?
    ) {
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
        dialog.setContentView(R.layout.offer_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val textInfo = dialog.findViewById<View>(R.id.textInfo) as TextView

        textInfo.typeface = fontStyle(context,"SemiBold")

        val textPrice = dialog.findViewById<View>(R.id.textPrice) as TextView

        textPrice.typeface = fontStyle(context,"SemiBold")

        val textSaving = dialog.findViewById<View>(R.id.textSaving) as TextView

        textSaving.typeface = fontStyle(context,"Light")

        val textDescription = dialog.findViewById<View>(R.id.textDescription) as TextView

        textDescription.typeface = fontStyle(context,"")

        val textOk = dialog.findViewById<View>(R.id.textOk) as TextView

        textOk.typeface = fontStyle(context,"SemiBold")

        textOk.setOnClickListener(View.OnClickListener { dialog.cancel() })

        dialog.show()
    }

    fun showDialogReservationCancel(
        context: Context?
    ) {
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
        dialog.setContentView(R.layout.offer_cancel)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val textInfo = dialog.findViewById<View>(R.id.textInfo) as TextView

        textInfo.typeface = fontStyle(context,"SemiBold")

        val textPrice = dialog.findViewById<View>(R.id.textPrice) as TextView

        textPrice.typeface = fontStyle(context,"SemiBold")

        val textSaving = dialog.findViewById<View>(R.id.textSaving) as TextView

        textSaving.typeface = fontStyle(context,"Light")

        val textDescription = dialog.findViewById<View>(R.id.textDescription) as TextView

        textDescription.typeface = fontStyle(context,"")

        val textOk = dialog.findViewById<View>(R.id.textOk) as TextView

        textOk.typeface = fontStyle(context,"SemiBold")

        textOk.setOnClickListener(View.OnClickListener { dialog.cancel() })

        dialog.show()
    }

    fun nextPay ()
    {
        Constant.AddRestaruantNote = ""+detailsDescriptionET!!.text.toString()

        if(dbHelper!!.getMenu().size != 0) {

            if(BookingType!!.equals("0")) {

                var intent = Intent(this, OrderDetailsActivity::class.java)

                intent.putExtra("Total Amount", "" + toPayPriceTV!!.text.toString())

                intent.putExtra("Item", "" + adapterProduct.size)

                if(PickUpType.equals("1"))
                {
                    if(!pickUpTime!!.text.equals(""))
                    {
                        startActivity(intent)
                    }else{
                        showToast(this,"Please select data and time")

                        //PickUpType = "0"
                    }

                }else {
                    startActivity(intent)
                }
                Constant.totalPrice = ""+toPayPriceTV!!.text.toString()

                Constant.quality = ""+adapterProduct.size
            }else{
                var intent = Intent(this, OrderDetailsActivity::class.java)

                intent.putExtra("Total Amount", "" + toPayPriceTV!!.text.toString())

                intent.putExtra("Item", "" + adapterProduct.size)

                //intent.putExtra("item", "" + itemValue)
                Constant.totalPrice = ""+toPayPriceTV!!.text.toString()

                Constant.quality = ""+adapterProduct.size
                startActivity(intent)
            }
        }else{
            showToast(this,""+getString(R.string.Please_select_item))
        }
    }
    fun createWebServiceTable(): JSONObject {
        val obj = JSONObject()
        val objArray = JSONArray()
        val objArray1 = JSONArray()
        val objArray2 = JSONArray()
        obj.put("tableMemberReferenceCode", "" + BookingType)
        obj.put("bookingAmount", ""+ Constant.totalPrice.replace("€ ",""))
//        obj.put("paymentMode", ""+paymentTypeTV)
//        obj.put("paymentType", "1")

        for (i in 0 until dbHelper!!.getMenu().size) {
            val menusList = JSONObject()

            menusList.put("menuReferenceCode", "" + dbHelper!!.getMenu().get(i).token)

            menusList.put("quantity", "1")

            for (j in 0 until dbHelper!!.getMenuGroup(dbHelper!!.getMenu().get(i).id!!).size) {

                //Log.v(";;;;"," === "+dbHelper!!.getMenuGroup(dbHelper!!.getMenu().get(i).id!!).get(j).token)
                val menusList1 = JSONObject()

                menusList1.put(
                    "toppinsGroupReferenceCode",
                    "" + dbHelper!!.getMenuGroup(dbHelper!!.getMenu().get(i).id!!).get(j).token
                )
                for (k in 0 until dbHelper!!.getMenuGroupToppind(
                    dbHelper!!.getMenuGroup(
                        dbHelper!!.getMenu().get(
                            i
                        ).id!!
                    ).get(j).id!!
                ).size) {
                    val menusList2 = JSONObject()

                    menusList2.put(
                        "toppinsReferenceCode",
                        "" + dbHelper!!.getMenuGroupToppind(
                            dbHelper!!.getMenuGroup(
                                dbHelper!!.getMenu().get(
                                    i
                                ).id!!
                            ).get(j).id!!
                        ).get(k).token
                    )

                    objArray2.put(menusList2)

                    menusList1.put("toppinsList",objArray2)
                }

                objArray1.put(menusList1)

                menusList.put("toppinsGroupList",objArray1)
            }

            objArray.put(menusList)


        }

        obj.put("menusList", objArray)

        /*for (i in 0 until (responseObj as BaseRS).fetchData!!.restaurantListing!!.size) {

        }*/

        Log.v("kkkk","=="+obj)

        return obj
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                var intent = Intent(this, DashBoardActivity::class.java)

                intent.putExtra("Title", "" + storeName)

                intent.putExtra("page", getString(R.string.Details_Page))

                startActivity(intent)

                finish()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun setOnMaterial(userId: Product?, isTrue: Boolean, id: String?, position: Int) {

        Log.v("hhhh","=="+id);

        dbHelper!!.updatedetailsPrice(userId!!,id!!,""+position!!)

        totalPrice = 0.0

        for(i in 0 until dbHelper!!.getMenu().size) {

            totalPrice = totalPrice!! + + addIncreasePriceHole(dbHelper!!.getMenu().get(i).totalPrice!!,""+dbHelper!!.getMenu().get(i).rating!!)
        }


        itemTotalPrice!!.text = "€ "+DecimalFormat("##.##").format(totalPrice!!)

        totalPriceValue = totalPrice!! + restaurantPriceTV!!.text.toString().replace("€ ","").toDouble() + deliveryFeePriceTV!!.text.toString().replace("€ ","").toDouble()

        toPayPriceTV!!.text = "€ "+DecimalFormat("##.##").format(totalPrice!!)

        addPrice!!.text = customText(this,"€ "+DecimalFormat("##.##").format(totalPriceValue!!)+" "+getString(R.string.Plus_Taxes),7,"SemiBold","")
        if(dbHelper!!.getMenu().size == 0)
        {
            var intent = Intent(this, DashBoardActivity::class.java)

            intent.putExtra("Title", "" + storeName)

            intent.putExtra("page", getString(R.string.Details_Page))

            startActivity(intent)

            finish()
        }
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
        obj.put("restaurantReferenceCode", ""+dbHelper!!.getRestaurant().restaurantReferenceCode)
        obj.put("bookingTime", ""+time)
        obj.put("dayId", ""+day)
        RequestManager.setRestaurantAvailability(this,obj,this)
        loadingScreen(this)
    }

    override fun setOnAddText(addText: String?) {
        dbHelper!!.deleteMenuValue(addText!!)

        dbHelper!!.deleteToppinsGroupDelete(addText!!)

        showDataProduct()
    }

    override fun setOnEdit(addText: String?) {
        val obj = JSONObject()
        obj.put("menuId", ""+addText)
        RequestManager.setFetchMenuToppingList(this,obj,this)
        loadingScreen(this)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun setOnDetails(userId: String?) {
        Log.v("Date == "+userId,"Value")

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

                    PickUpDate = dateVale + " " + SimpleDateFormat("HH:mm:ss").format(cal.time)

                    webservice(SimpleDateFormat("HH:mm").format(cal.time),dateConversionTimeDay(userId)!!)


                }else{
                    showToast(this,"Please select valid time")
                }
            }else{
                //pickUpTime!!.text = "Time : " + SimpleDateFormat("hh:mm a").format(cal.time)

                selectTime =  "Time : " + SimpleDateFormat("hh:mm a").format(cal.time)

                PickUpDate = dateVale + " " + SimpleDateFormat("HH:mm:ss").format(cal.time)

                webservice(SimpleDateFormat("HH:mm").format(cal.time),dateConversionTimeDay(userId)!!)
            }

            Log.i(timeCheckValue(openingTime,""+closingTime,SimpleDateFormat("HH").format(cal.time),SimpleDateFormat("mm").format(cal.time)) +"","22");


        }
        TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if(responseObj != null) {
            if (requestType == Constant.MEMBER_tableReservtionMenuPicked_RQ) {

                showToast(this, (responseObj as BaseRS).message)

                if((responseObj as BaseRS).status.equals("5216"))
                {
                    dbHelper!!.deleteMenu()

                    dbHelper!!.deleteCategory()

                    dbHelper!!.deleteToppinsGroup()

                    dbHelper!!.deleteToppins()

                    dbHelper!!.deleteRest()


                    //var intent = Intent(this, OrderCompleteActivity::class.java)
                    var intent = Intent(this, DashBoardActivity::class.java)


                    startActivity(intent)
                }
            }else if (requestType == Constant.MEMBER_restaurantAvailability_URL_RQ) {

                loadingScreenClose(this)
                showToast(this, (responseObj as BaseRS).message)

                if((responseObj as BaseRS).status.equals("5112"))
                {
                    valString = "1"
                    pickUpTime!!.text = ""+selectTime
                }else{
                    pickUpTime!!.text = ""
                }

            }else if (requestType == Constant.MEMBER_fetchMenuToppingList_URL_RQ) {
                loadingScreenClose(this)
                showToast(this, (responseObj as BaseRS).message)
                if((responseObj as BaseRS).status.equals("3060"))
                {
                    adapterProductGroupList = (responseObj as BaseRS).createToppinsGroupList!!;

                    showDialogItem(this)
                }
            }
        }
    }

    fun showDataDataItem(adapterModels: List<ProductListView>) {

        if (adapterTrendingItem.size != 0) {
            adapterTrendingItem.clear()
        }

        for (i in 0 until adapterModels.size) {

            adapterModel = AdapterModel(
                0,
                "" + adapterModels.get(i).name + " (€ " + adapterModels.get(i).price + " )",
                "Required 1",
                "" + adapterModels.get(i).toppinsGroupId,
                "" + adapterModels.get(i).toppinsId,
                " 976",
                " 425",
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
                "",
                0,
                0,
                0,
                adapterCategories
            )
            adapterTrendingItem.add(adapterModel)

        }
    }

    override fun setOnFav(id: String?) {

    }

    override fun setOnValue(id: String?) {
        TODO("Not yet implemented")
    }
}
