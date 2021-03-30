package com.lieferin_global.Fragment

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.*
import androidx.viewpager.widget.ViewPager
import com.lieferin_global.Adapter.*
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.ImageModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.google.android.material.tabs.TabLayout
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Utility.Constant.webServiceValue
import com.rentaloo.CallBack.CallBlacklisting
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class TableReservationFragment : Fragment(), CalenderPageAdapter.CallbackDataAdapter,
    PersonPageAdapter.CallbackDataAdapter, ResponseListener,
    SelectedTimePageAdapter.CallbackDataAdapter,
    View.OnClickListener {

    var tableReservation_Title: TextView? = null

    var tableReservation_Type: TextView? = null

    var tableReservation_back: ImageView? = null

    var chooseTable: TextView? = null

    var noOfPerson: TextView? = null

    var textCount: TextView? = null

    var minus: ImageView? = null

    var selectDate: TextView? = null

    var selectTime: TextView? = null

    var plus: ImageView? = null

    var adapter: ViewPagerAdapter? = null

    private var imageModelArrayList: ArrayList<ImageModel>? = null

    private val myImageList = intArrayOf(R.drawable.favorite, R.drawable.favorite)

    var mAdapter1: CalenderPageAdapter? = null

    internal lateinit var adapterModel: AdapterModel

    var adapterModels1: MutableList<AdapterModel> = ArrayList()

    var adapterModelsPerson: MutableList<AdapterModel> = ArrayList()

    var adapterModelsProduct: MutableList<Product> = ArrayList()

    var timeRecyclerView: RecyclerView? = null

    var mAdapter2: SelectedTimePageAdapter? = null

    var adapterModelsView2: MutableList<Product> = ArrayList()

    var adapterModelsViewList: MutableList<ProductList> = java.util.ArrayList()

    internal lateinit var adapterModelProduct: Product

    var tabLayout: TabLayout? = null

    var viewPager: ViewPager? = null

    var reserveTV: TextView? = null

    var rootView: View? = null

    var tableSelect: RecyclerView? = null

    var personRecyclerView: RecyclerView? = null

    var mAdapterPerson: PersonPageAdapter? = null

    var adapterModelsView: MutableList<AdapterModel> = ArrayList()

    var adapterModelsViewProduct: MutableList<Product> = ArrayList()

    var tableBookingPageAdapter: TableBookingPageAdapter? = null

    fun TableReservationFragment() {}

    var callBlacklisting: CallBlacklisting? = null

    var title: String? = null

    var dateVale =""

    var countPerson = "1"

    var selectTimeValue = ""

    var countTable ="0"

    var dbHelper : DBHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_table_reservation, container, false)

        dbHelper = DBHelper(activity)

        var header = rootView!!.findViewById(R.id.header) as RelativeLayout

        header!!.setOnClickListener(this)

        tableReservation_back = rootView!!.findViewById(R.id.tableReservation_back)

        tableReservation_back!!.setOnClickListener(this)

        imageModelArrayList = ArrayList()
        imageModelArrayList = populateList()

        init()

        val calendar =
            Calendar.getInstance(TimeZone.getDefault())
        calendar[Calendar.YEAR]
        calendar.add(Calendar.DAY_OF_MONTH, 0)
        var dateValue = calendar[Calendar.MONTH] + 1
        dateVale =
            "" + calendar[Calendar.YEAR] + "-" + dateValue + "-" + calendar[Calendar.DAY_OF_MONTH]

        val bundle = this.arguments
        if (bundle != null) {
            title = bundle.getString("Title")
        }

        timeRecyclerView = rootView!!.findViewById(R.id.timeRecyclerView) as RecyclerView

        mAdapter2 = SelectedTimePageAdapter(activity!!, adapterModelsProduct)

        timeRecyclerView!!.layoutManager = GridLayoutManager(activity!!, 3)

        mAdapter2!!.setCallback(this)

        timeRecyclerView!!.adapter = mAdapter2

        timeRecyclerView!!.isNestedScrollingEnabled = false

        viewPager = rootView!!.findViewById(R.id.viewpager) as ViewPager

        tabLayout = rootView!!.findViewById(R.id.tabs) as TabLayout
        tabLayout!!.setupWithViewPager(viewPager);

        adapter =
            ViewPagerAdapter(
                fragmentManager
            )

        adapter!!.addFragment(TimeFragment(), "Breakfast")

        adapter!!.addFragment(TimeFragment(), "Lunch")

        adapter!!.addFragment(TimeFragment(), "Dinner")

        viewPager!!.setAdapter(adapter)

        tabLayout =
            rootView!!.findViewById<View>(R.id.tabs) as TabLayout

        tabLayout!!.setupWithViewPager(viewPager)


        adapter!!.notifyDataSetChanged()

        val betweenSpace = 25

        val slidingTabStrip = tabLayout!!.getChildAt(0) as ViewGroup

        for (i in 0 until slidingTabStrip.childCount - 1) {
            val v: View = slidingTabStrip.getChildAt(i)
            if (v is TextView) {
                (v as TextView).typeface = fontStyle(activity!!, "Light")

                (v as TextView).textSize = 10.0f
            }
            val params = v.getLayoutParams() as ViewGroup.MarginLayoutParams
            params.rightMargin = betweenSpace


        }

        val tabsCount: Int = slidingTabStrip.getChildCount()

        for (j in 0 until tabsCount) {
            val vgTab = slidingTabStrip.getChildAt(j) as ViewGroup
            val tabChildsCount = vgTab.childCount
            for (i in 0 until tabChildsCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) { //Put your font in assests folder
//assign name of the font here (Must be case sensitive)
                    tabViewChild.setTypeface(
                        fontStyle(
                            activity!!,
                            "Light"
                        )
                    )
                }
            }
        }

        webService()

        return rootView
    }

    fun webService() {

    val obj = JSONObject()
    obj.put("restaurantReferenceCode", ""+title)
    obj.put("totalPerson", ""+countPerson)
    obj.put("searchDate", ""+dateConversion1Value(dateVale))
    Log.v("Json", "Value" + obj)
    loadingScreen(activity)
    RequestManager.setTableReservationSlot(activity, obj, this);
}

    class ViewPagerAdapter(manager: FragmentManager?) : FragmentPagerAdapter(manager!!) {
        private val mFragmentList: MutableList<Fragment> =
            java.util.ArrayList()
        private val mFragmentTitleList: MutableList<String> =
            java.util.ArrayList()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size;
        }


        fun addFragment(fragment: Fragment, title: String) {
            val bundle = Bundle()
            bundle.putString("shopId", title)
            fragment.arguments = bundle
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        fun removeFragment(fragment: Fragment?, position: Int) {
            mFragmentList.removeAt(position)
            mFragmentTitleList.removeAt(position)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
        }
    }


    private fun init() {

        tableSelect = rootView!!.findViewById(R.id.tableSelect) as RecyclerView

        tableBookingPageAdapter = TableBookingPageAdapter(activity!!, adapterModelsView)
        val mLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, false)

        tableSelect!!.layoutManager = mLayoutManager

        tableSelect!!.itemAnimator!!.addDuration = 5000

        //specialDealActivity!!.setCallback(this)

        tableSelect!!.adapter = tableBookingPageAdapter

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(tableSelect!!)

        showDataNearBy()


        tableReservation_back!!.setColorFilter(
            colorIcon(
                activity!!,
                R.color.colorBlack,
                R.drawable.abc_ic_ab_back_material,
                tableReservation_back!!
            ),
            PorterDuff.Mode.SRC_ATOP
        )

        tableReservation_Title = rootView!!.findViewById(R.id.orderBill_Title)

        tableReservation_Title!!.typeface = fontStyle(activity!!, "")

        tableReservation_Type = rootView!!.findViewById(R.id.orderDescription_Type)

        tableReservation_Type!!.typeface = fontStyle(activity!!, "Light")

        chooseTable = rootView!!.findViewById(R.id.chooseTable)

        chooseTable!!.typeface = fontStyle(activity!!, "")

        noOfPerson = rootView!!.findViewById(R.id.noOfPerson)

        noOfPerson!!.typeface = fontStyle(activity!!, "Light")

        textCount = rootView!!.findViewById(R.id.textCount)

        textCount!!.typeface = fontStyle(activity!!, "Light")

        selectDate = rootView!!.findViewById(R.id.selectDate)

        selectDate!!.typeface = fontStyle(activity!!, "Light")

        selectTime = rootView!!.findViewById(R.id.selectTime)

        selectTime!!.typeface = fontStyle(activity!!, "Light")

        reserveTV = rootView!!.findViewById(R.id.reserveTV)

        reserveTV!!.typeface = fontStyle(activity!!, "SemiBold")

        reserveTV!!.setOnClickListener(this)

        plus = rootView!!.findViewById(R.id.plus) as ImageView

        plus!!.setColorFilter(
            colorIcon(activity!!, R.color.colorBlack, R.drawable.plus, plus!!),
            PorterDuff.Mode.SRC_ATOP
        )

        plus!!.setOnClickListener(this)

        minus = rootView!!.findViewById(R.id.minus) as ImageView

        minus!!.setColorFilter(
            colorIcon(activity!!, R.color.colorBlack, R.drawable.minus, minus!!),
            PorterDuff.Mode.SRC_ATOP
        )

        minus!!.setOnClickListener(this)

        val calenderViewList = rootView!!.findViewById(R.id.calenderRecyclerView) as RecyclerView
        mAdapter1 = CalenderPageAdapter(activity!!, adapterModels1)


        calenderViewList.layoutManager = LinearLayoutManager(
            activity!!,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        calenderViewList.itemAnimator!!.addDuration = 5000
        mAdapter1!!.setCallback(this)
        calenderViewList.adapter = mAdapter1

        calenderViewList.isNestedScrollingEnabled = false
        showDate()

        val personRecyclerView = rootView!!.findViewById(R.id.personRecyclerView) as RecyclerView
        mAdapterPerson = PersonPageAdapter(activity!!, adapterModelsPerson)


        personRecyclerView.layoutManager = LinearLayoutManager(
            activity!!,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        personRecyclerView.itemAnimator!!.addDuration = 5000
        mAdapterPerson!!.setCallback(this)
        personRecyclerView.adapter = mAdapterPerson

        personRecyclerView.isNestedScrollingEnabled = false
        showDatePerson()


    }

    companion object {

        private var mPager: ViewPager? = null
        private var currentPage = 0
        private var NUM_PAGES = 0
    }

    private fun populateList(): ArrayList<ImageModel> {

        val list = ArrayList<ImageModel>()

        for (i in 0..1) {
            val imageModel = ImageModel()
            imageModel.setImage_drawables(myImageList[i])
            list.add(imageModel)
        }

        return list
    }

    fun showDataNearBy() {

        if (adapterModelsView.size != 0) {
            adapterModelsView.clear()
        }

        adapterModel = AdapterModel(
            R.drawable.table_2_inactive,
            "PIZZA Hut",
            "29 Aug 2019",
            "01:54",
            "Completed",
            "Reorder",
            "Get Help",
            "https://i.pinimg.com/originals/96/0a/be/960abef4528685a8daffe3c4221594f2.png",
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
            adapterModelsProduct
        )
        adapterModelsView.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.table_6,
            "KFC Chicken",
            "29 Aug 2019",
            "01:54",
            "Completed",
            "Reorder",
            "Get Help",
            "https://www.businessinsider.in/photo/55773804.cms",
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
            adapterModelsProduct
        )
        adapterModelsView.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.table_4_inactive,
            "KFC Chicken",
            "28 Aug 2019",
            "01:54",
            "Completed",
            "Reorder",
            "Get Help",
            "https://i.pinimg.com/originals/96/0a/be/960abef4528685a8daffe3c4221594f2.png",
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
            adapterModelsProduct
        )
        adapterModelsView.add(adapterModel)


        tableBookingPageAdapter!!.notifyDataSetChanged()
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

    override fun setOnDetails(userId: String?) {

        Log.v("Date == "+userId,"Value")

        dateVale = userId!!

        webService()

        mAdapter1!!.notifyDataSetChanged()

    }

    fun showDatePerson() {
        if (adapterModelsPerson != null) {
            adapterModelsPerson.clear()
        }
        for (i in 1..20) {
            adapterModel = AdapterModel(
                0,
                "" + i,
                "",
                "",
                "",
                "",
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

            adapterModelsPerson.add(adapterModel)
        }
        mAdapterPerson!!.notifyDataSetChanged()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }

    override fun onClick(view: View?) {

        when (view!!.id) {
            R.id.plus -> {

                if (!textCount!!.text.equals("8")) {

                    textCount!!.text = "" + addIncrease(textCount!!.text.toString())
                }

            }

            R.id.tableReservation_back -> {
                callBlacklisting!!.fragmentBack("")
            }
            R.id.minus -> {

                if (!textCount!!.text.equals("1")) {

                    textCount!!.text = "" + addDecrease(textCount!!.text.toString())
                }

            }
            R.id.reserveTV -> {
                if(!countTable!!.equals("0")) {
                    showDialogInfo(activity!!)
                }else{
                    showToast(activity!!,"Please select other table slot")
                }
            }
        }
    }

    fun showDialogInfo(
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
        dialog.setContentView(R.layout.table_reserve_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val groupLayout= dialog.findViewById<View>(R.id.groupLayout) as LinearLayout

        val processingLayout= dialog.findViewById<View>(R.id.processingLayout) as LinearLayout

        val groupImageView= dialog.findViewById<View>(R.id.groupImageView) as CircleImageView

        groupImageView!!.setColorFilter(colorIcon(context,R.color.colorWhite,R.drawable.group,groupImageView))

        val processingImageView= dialog.findViewById<View>(R.id.processingImageView) as CircleImageView

        processingImageView!!.setColorFilter(colorIcon(context,R.color.colorWhite,R.drawable.user_logo,processingImageView))


        val closeIMG = dialog.findViewById<View>(R.id.closeIMG) as ImageView

        val tableLA = dialog.findViewById<View>(R.id.tableLA) as TextView

        tableLA.typeface = fontStyle(context, "SemiBold")

        val tableLA1 = dialog.findViewById<View>(R.id.tableLA1) as TextView

        tableLA1.typeface = fontStyle(context, "SemiBold")

        val tableLA2 = dialog.findViewById<View>(R.id.tableLA2) as TextView

        tableLA2.typeface = fontStyle(context, "SemiBold")

        groupLayout.setOnClickListener(View.OnClickListener {

            val bundle = Bundle()
            bundle.putString("Title", title)
            bundle.putString("Select Date", dateVale)
            bundle.putString("person Count", countPerson!!)
            bundle.putString("Select Time", selectTimeValue!!)
            bundle.putString("restaurantReferenceCode", ""+title)
            webServiceValue = "0"
            callBlacklisting!!.fragmentChange("Add Person", bundle)

            dialog.cancel()
        })

        closeIMG.setOnClickListener(View.OnClickListener {

            dialog.cancel()
        })

        processingLayout.setOnClickListener(View.OnClickListener {

            showDialogReservation(context)

            dialog.cancel()
        })

        dialog.show()
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

        showToast(activity!!,"Table Booking Three")

        val tableLA = dialog.findViewById<View>(R.id.successText) as TextView

        tableLA.typeface = fontStyle(context, "")

        val tableLA1 = dialog.findViewById<View>(R.id.successDescription) as TextView

        tableLA1.typeface = fontStyle(context, "")

        val tableLA2 = dialog.findViewById<View>(R.id.noTextView) as TextView

        tableLA2.typeface = fontStyle(context, "SemiBold")

        val textInfo = dialog.findViewById<View>(R.id.yesTV) as TextView

        textInfo.typeface = fontStyle(context, "SemiBold")


        tableLA2!!.setOnClickListener(View.OnClickListener {
            webService()
            dialog.cancel()
        })

        textInfo!!.setOnClickListener(View.OnClickListener {
        val bundle = Bundle()
        bundle.putString("Title", title)
        callBlacklisting!!.fragmentChange("Detail Page", bundle)
            dialog.cancel()
        })

        dialog.show()
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
        dialog.setContentView(R.layout.table_menu)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val closeIMG = dialog.findViewById<View>(R.id.closeIMG) as ImageView

        val tableLA = dialog.findViewById<View>(R.id.tableLA) as TextView

        tableLA.typeface = fontStyle(context, "")

        val tableLA1 = dialog.findViewById<View>(R.id.tableLA1) as TextView

        tableLA1.typeface = fontStyle(context, "")

        val tableLA2 = dialog.findViewById<View>(R.id.tableLA2) as TextView

        tableLA2.typeface = fontStyle(context, "")

        val textInfo = dialog.findViewById<View>(R.id.textInfo) as TextView

        textInfo.typeface = fontStyle(context, "")

        tableLA1.setOnClickListener(View.OnClickListener {
            /*val bundle = Bundle()
            bundle.putString("Title", title)
            callBlacklisting!!.fragmentChange("Detail Page", bundle)*/

            webServiceTable()

            dialog.cancel()
        })

        closeIMG.setOnClickListener(View.OnClickListener {

            dialog.cancel()
        })

        tableLA2.setOnClickListener(View.OnClickListener {

            //callBlacklisting!!.fragmentChange("Payment", null)
            dialog.cancel()
        })

        dialog.show()
    }

    fun webServiceTable()
    {
        val obj = JSONObject()
        val objArray = JSONArray()
        val objArray1 = JSONArray()
        val objArray2 = JSONArray()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)
        obj.put("restaurantReferenceCode", ""+title)
        obj.put("numberOfPeron", ""+countPerson)
        obj.put("bookingDate", ""+dateVale)
        obj.put("individual", "1")

        val str = selectTimeValue
        val arrOfStr =
            str.split(" - ")
        obj.put("startTime", ""+dateConversionTimeValue(arrOfStr[0]))
        obj.put("endTime", ""+dateConversionTimeValue(arrOfStr[1]))
        for (i in 0 until 1) {
            val menusList = JSONObject()

            menusList.put("name", "" + dbHelper!!.getUserDetails().name)

            menusList.put("email", ""+ dbHelper!!.getUserDetails().email)

            menusList.put("mobile", "" + dbHelper!!.getUserDetails().mobile)

            menusList.put("payType", "1")

            objArray.put(menusList)
        }

        obj.put("individualList", objArray)
        Log.v("Json", "Value" + obj)
        showToast(activity!!,"Table Booking one")
        RequestManager.setTableReservationInsert(activity, obj, this)
        loadingScreen(activity!!)
    }

    override fun setOnPerson(userId: String?) {
        countPerson = userId!!
        webService()
        mAdapterPerson!!.notifyDataSetChanged()
    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if (responseObj != null) {
            //loadingScreenClose(activity)

            if((responseObj as BaseRS).message != null)
            {
                showToast(activity!!,""+(responseObj as BaseRS).message)
            }
            if (requestType == Constant.MEMBER_tableSlotAvailableList_URL_RQ) {
                loadingScreenClose(activity)
                if (adapterModelsProduct.size != 0) {
                    adapterModelsProduct.clear()
                }
                if ((responseObj as BaseRS).status.equals("5203")) {
                    for (i in 0 until (responseObj as BaseRS).availableSlots!!.size) {
                        adapterModelProduct = Product(
                            0,
                            ""+(responseObj as BaseRS).availableSlots!!.get(i).startTime,
                            ""+(responseObj as BaseRS).availableSlots!!.get(i).availableTable+"",
                            ""+(responseObj as BaseRS).availableSlots!!.get(i).slotTime,
                            "60 % OFF",
                            "2.2 Km",
                            "Purple Beauty Park",
                            "Tatabad,Gandhipuram", "", "", "", "", "", "", "","","","",adapterModelsViewList
                        )

                        selectTimeValue = ""+(responseObj as BaseRS).availableSlots!!.get(0).slotTime

                        countTable= ""+(responseObj as BaseRS).availableSlots!!.get(0).availableTable

                        adapterModelsProduct.add(adapterModelProduct)
                    }

                }
                mAdapter2!!.notifyDataSetChanged()
            }else if (requestType == Constant.MEMBER_tableReservationInsert_URL_RQ){

                loadingScreenClose(activity)

                showToast(activity!!,""+(responseObj as BaseRS).message!!)
                if ((responseObj as BaseRS).status.equals("5211")) {
                    Constant.BookingType = ""+(responseObj as BaseRS).individualBooking!!.tableMemberReferenceCode

                    Constant.isPayType =""+(responseObj as BaseRS).individualBooking!!.payType

                    showToast(activity!!,"Table Booking Two")

                    showDialogTableInfo(activity!!)
                }
            }
        }
    }

    override fun setOnMaterial(userId: String?) {

        Log.v("time"+userId,"value")

        selectTimeValue = ""+userId

        mAdapter2!!.notifyDataSetChanged()
    }

    override fun setOnFavourite(isFav: String?, id: String?) {
        Log.v("time"+isFav,"value"+id)

        selectTimeValue = ""+isFav

        countTable = ""+id

        mAdapter2!!.notifyDataSetChanged()
    }
}
