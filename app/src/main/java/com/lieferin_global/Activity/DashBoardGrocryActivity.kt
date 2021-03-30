package com.lieferin_global.Activity

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.lieferin_global.Adapter.DashBoardAdapter
import com.lieferin_global.Adapter.FilterPageAdapter
import com.lieferin_global.Adapter.FiltersidePageAdapter
import com.lieferin_global.Adapter.SlideMenu_item_Adpt
import com.lieferin_global.Fragment.*
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.colorIcon
import com.lieferin_global.Utility.fontStyle
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.rentaloo.CallBack.CallBlacklisting
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class DashBoardGrocryActivity : AppCompatActivity(),SlideMenu_item_Adpt.CallbackDataAdapter,DashBoardAdapter.OnClickDashBoard,View.OnClickListener,CallBlacklisting,FilterPageAdapter.CallbackDataAdapter,FiltersidePageAdapter.CallbackDataAdapter,
    AppBarLayout.OnOffsetChangedListener {

    private val PERCENTAGE_TO_ANIMATE_AVATAR = 20
    private var mIsAvatarShown = true

    private var mMaxScrollSize = 0

    private var navigationView: NavigationView? = null

    private var drawerLayout: DrawerLayout? = null

    var cancel : ImageView? = null

    private var toolbar: Toolbar? = null

    var adapter: SlideMenu_item_Adpt? = null

    var filterPageAdapter: FilterPageAdapter? = null

    var adapterModels: MutableList<AdapterModel> = ArrayList()

    var adapterTrending: MutableList<AdapterModel> = ArrayList()

    var adapterFeature: MutableList<AdapterModel> = ArrayList()

    var adapterFamous: MutableList<AdapterModel> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    var adapterProduct: MutableList<Product> = ArrayList()

    internal lateinit var productModel: Product

    var slideUserName : TextView? = null

    var slideAddress : TextView? = null

    var addressTextView: TextView? = null

    var searchEditText: EditText? = null

    var filterRecyclerView: RecyclerView? = null

    var fragmentManager: FragmentManager? = null

    var fm: FragmentManager? = null

    var fragmentManager1: FragmentManager? = null

    var fm1: FragmentManager? = null

    var homeBottomLL : LinearLayout? = null

    var reservationBottomLL : LinearLayout? = null

    var groceryBottomLL : LinearLayout? = null

    var favoriteBottomLL : LinearLayout? = null

    var accountPassBottomLL : LinearLayout? = null

    var homeBottomTV : TextView? = null

    var reservationBottomTV : TextView? = null

    var groceryBottomTV : TextView? = null

    var favoriteBottomTV : TextView? = null

    var accountPassBottomTV : TextView? = null

    var homeIV : ImageView? = null

    var reservationIV : ImageView? = null

    var groceryIV : ImageView? = null

    var favoriteIV : ImageView? = null

    var accountIV : ImageView? = null

    var mapIV : ImageView? = null

    var headerLayout : LinearLayout? =null

    var profileImg : CircleImageView? = null

    var tabLayout: TabLayout? = null

    var viewPager: ViewPager? = null

    var sheetBehavior: BottomSheetBehavior<*>? = null

    var filterApply : TextView? = null

    var filterClearAll : TextView? = null

    var filterTV : TextView? = null

    var filterListRecyclerView : RecyclerView? = null

    var filtersidePageAdapter: FiltersidePageAdapter? = null

    var radioButton : RadioButton? = null

    var radioButton1 : RadioButton? = null

    var radioButton2 : RadioButton? = null

    var radioButton3 : RadioButton? = null

    var radioButton4 : RadioButton? = null

    var dumy : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_dash_board)
        val extras = intent.extras
        val userName: String?
        if (extras != null) {
            userName = extras.getString("page")
            if(userName!!.equals("My Order")) {
                val activityFragment: Fragment = OrderHistoryActivity()
                loadFragment(activityFragment, "My Orders")
            }else if(userName!!.equals("Table")) {
                val activityFragment: Fragment =
                    TableReservationFragment()
                loadFragment(activityFragment, "Table Reservation")
            }
        }

        navigationView = findViewById<View>(R.id.nvView1) as NavigationView

        drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout

        headerLayout= findViewById<View>(R.id.headerLayout) as LinearLayout

        profileImg= findViewById<View>(R.id.profileImg) as CircleImageView

        profileImg!!.setOnClickListener(this)

        dumy = findViewById<View>(R.id.dumy) as TextView

        headerLayout!!.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));

        slideUserName = findViewById(R.id.slideUserName) as TextView

        slideUserName!!.typeface = fontStyle(this,"SemiBold")

        slideAddress = findViewById(R.id.slideAddress) as TextView

        slideAddress!!.typeface = fontStyle(this,"Light")

        addressTextView = findViewById(R.id.addressTextView) as TextView

        addressTextView!!.typeface = fontStyle(this,"")

        searchEditText = findViewById(R.id.searchEditText) as EditText

        searchEditText!!.typeface = fontStyle(this,"Light")

        searchEditText!!.setOnClickListener(this)

        radioButton = findViewById(R.id.radioButton) as RadioButton

        radioButton!!.typeface = fontStyle(this,"")

        radioButton1 = findViewById(R.id.radioButton2) as RadioButton

        radioButton1!!.typeface = fontStyle(this,"")

        radioButton2 = findViewById(R.id.radioButton3) as RadioButton

        radioButton2!!.typeface = fontStyle(this,"")

        radioButton3 = findViewById(R.id.radioButton4) as RadioButton

        radioButton3!!.typeface = fontStyle(this,"")

        radioButton4 = findViewById(R.id.radioButton5) as RadioButton

        radioButton4!!.typeface = fontStyle(this,"")

        val slideView =
            findViewById(R.id.slideMenu) as RecyclerView

        adapter = SlideMenu_item_Adpt(this, adapterModels)

        val mLayoutManager1: RecyclerView.LayoutManager = LinearLayoutManager(this)

        slideView!!.layoutManager = mLayoutManager1

        slideView!!.itemAnimator!!.addDuration = 5000

        adapter!!.setCallback(this)

        slideView!!.adapter = adapter!!

        navigationView!!.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            // This method will trigger on item Click of navigation menu
            //Checking if the item is in checked state or not, if not make it in checked state
            if (menuItem.isChecked) menuItem.isChecked = false else menuItem.isChecked = true
            //Closing drawer on item click
            drawerLayout!!.closeDrawers()
            when (menuItem.itemId) {
                else -> {
                    Toast.makeText(applicationContext, "Somethings Wrong", Toast.LENGTH_SHORT).show()
                    true
                }
            }
        })

        filterRecyclerView = findViewById(R.id.filterRecyclerView) as RecyclerView

        filterPageAdapter = FilterPageAdapter(this, adapterTrending)

        val mLayoutManager2: RecyclerView.LayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)

        filterRecyclerView!!.layoutManager = mLayoutManager2

        filterRecyclerView!!.itemAnimator!!.addDuration = 5000

        filterPageAdapter!!.setCallback(this)

        filterRecyclerView!!.adapter = filterPageAdapter!!

        showDataTrending()
/*
        //mAdapterShip = HomeShipAdapter(this, topRate)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        allRecyclerView!!.layoutManager = mLayoutManager

        allRecyclerView!!.itemAnimator!!.addDuration = 5000*/

        init_footer()

        show()

        /*addProduct()

        showDataProduct()

        showDataTrending()

        showDataFeature()

        showDataFamous()*/

        viewPager = findViewById(R.id.college_viewpager) as ViewPager
        setupViewPager(viewPager!!);

        tabLayout = findViewById(R.id.college_tabs) as TabLayout
        tabLayout!!.setupWithViewPager(viewPager);

        val slidingTabStrip = tabLayout!!.getChildAt(0) as ViewGroup

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
                            this,
                            ""
                        )
                    )
                }
            }
        }
        val bottomSheet: View = findViewById<View>(R.id.bottom_sheet)

        cancel = findViewById(R.id.cancel) as ImageView

        cancel!!.setOnClickListener(this)

        filterTV = findViewById(R.id.filterTV) as TextView

        filterTV!!.typeface = fontStyle(this,"")

        filterApply = findViewById(R.id.filterApply) as TextView

        filterApply!!.typeface = fontStyle(this,"SemiBold")

        filterClearAll = findViewById(R.id.filterClearAll) as TextView

        filterClearAll!!.typeface = fontStyle(this,"")

        filterListRecyclerView = findViewById(R.id.filterListRecyclerView) as RecyclerView

        filtersidePageAdapter = FiltersidePageAdapter(this, adapterFeature)

        val mLayoutManager3: RecyclerView.LayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)

        filterListRecyclerView!!.layoutManager = mLayoutManager3

        filterListRecyclerView!!.itemAnimator!!.addDuration = 5000

        filtersidePageAdapter!!.setCallback(this)

        filterListRecyclerView!!.adapter = filtersidePageAdapter!!

        showDataTrending1()

        sheetBehavior = BottomSheetBehavior.from(bottomSheet)

        //By default set BottomSheet Behavior as Collapsed and Height 0
        //By default set BottomSheet Behavior as Collapsed and Height 0
        sheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)
        sheetBehavior!!.setPeekHeight(0)
        //If you want to handle callback of Sheet Behavior you can use below code
        //If you want to handle callback of Sheet Behavior you can use below code
        sheetBehavior!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> Log.d(ContentValues.TAG, "State Collapsed")
                    BottomSheetBehavior.STATE_DRAGGING ->{

                        sheetBehavior!!.setState(BottomSheetBehavior.STATE_EXPANDED);

                    }
                    BottomSheetBehavior.STATE_EXPANDED -> Log.d(ContentValues.TAG, "State Expanded")
                    BottomSheetBehavior.STATE_HIDDEN -> Log.d(ContentValues.TAG, "State Hidden")
                    BottomSheetBehavior.STATE_SETTLING -> Log.d(ContentValues.TAG, "State Settling")
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        val appbarLayout = findViewById<View>(R.id.materialup_appbar) as AppBarLayout

        appbarLayout.addOnOffsetChangedListener(this)


    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(DeliveryFragment(), "Delivery")

        adapter.addFragment(DeliveryFragment(), "Pick Up")

        viewPager.adapter = adapter
    }

    internal class ViewPagerAdapter(manager: FragmentManager?) :
        FragmentPagerAdapter(manager!!) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }

    private fun loadFragment(fragment: Fragment, title: String) {
        fm = supportFragmentManager
        runOnUiThread {
            fragmentManager = supportFragmentManager
            val fragmentTransactionManager = fragmentManager!!.beginTransaction()
            fragmentTransactionManager.replace(R.id.frame_container, fragment)
            fragmentTransactionManager.addToBackStack(title)
            fragmentTransactionManager.commit()
        }
    }

    private fun filterFragment(fragment: Fragment, title: String) {
        /*fm1 = supportFragmentManager
        runOnUiThread {
            fragmentManager1 = supportFragmentManager
            val fragmentTransactionManager = fragmentManager1!!.beginTransaction()
            fragmentTransactionManager.replace(R.id.frame_container_filter, fragment)
            fragmentTransactionManager.addToBackStack(title)
            fragmentTransactionManager.commit()
        }*/
    }

    fun showDataTrending() {

        if (adapterTrending.size != 0) {
            adapterTrending.clear()
        }
        adapterModel = AdapterModel(R.drawable.recommended, "Filter", "1", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterTrending.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Rating 4.0+", "0", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterTrending.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Fastest Delivery", "0", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterTrending.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Ratings", "2", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterTrending.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Cost", "2", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterTrending.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Culisines", "2", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterTrending.add(adapterModel)


        filterPageAdapter!!.notifyDataSetChanged()
    }


    fun showDataTrending1() {

        if (adapterFeature.size != 0) {
            adapterFeature.clear()
        }
        adapterModel = AdapterModel(R.drawable.recommended, "Sort by", "Popularity", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Cuisine", "", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Rating", "", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "Cast per person", "", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.recommended, "More Filter", "", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)

        filtersidePageAdapter!!.notifyDataSetChanged()
    }


    /*private fun addProduct() {

        val list: MutableList<HomePageModel> = ArrayList<HomePageModel>()
        list.add(HomePageModel(HomePageModel.CATEGORY, adapterTrending,1))

        list.add(HomePageModel(HomePageModel.BANNER, adapterFeature,1))

        list.add(HomePageModel(HomePageModel.OFFER, adapterFamous,1))

        //list.add(new HomePageModel(HomePageModel.BANNER, productHorizontal, null,2));
        val adapter = DashBoardAdapter(list, this)
        adapter.SetOnItemClickListener(this)
        allRecyclerView!!.adapter = adapter
    }

    fun showDataTrending() {

        if (adapterTrending.size != 0) {
            adapterTrending.clear()
        }
        adapterModel = AdapterModel(R.drawable.recommended, "Thalappakatty restaurant", "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNdVp58T_eVgzLEw-_dpw.730x390.jpg", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterTrending.add(adapterModel)


        //hotDealsAdapter!!.notifyDataSetChanged()
    }

    fun showDataFamous() {

        if (adapterFamous.size != 0) {
            adapterFamous.clear()
        }
        adapterModel = AdapterModel(R.drawable.img_6, "CHINESE", "35 OPTIONS", "75 minutes", "Save 60 %", " 1", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFamous.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.img_7, "NORTH INDIAN", "10 OPTIONS", "75 minutes", "Save 60 %", " 1", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFamous.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.img_8, "PIZZA", "67 OPTIONS", "75 minutes", "Save 60 %", "1", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFamous.add(adapterModel)
        //hotDealsAdapter!!.notifyDataSetChanged()
    }


    fun showDataFeature() {

        if (adapterFeature.size != 0) {
            adapterFeature.clear()
        }
        adapterModel = AdapterModel(R.drawable.img_4, "Thalapakatty Restaurant", "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNdVp58T_eVgzLEw-_dpw.730x390.jpg", "75 minutes", "Save 60 %", "1", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.img_5, "SMS Multi Cusine Restaurant", "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNdVp58T_eVgzLEw-_dpw.730x390.jpg", "75 minutes", "Save 60 %", "1", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)
        //hotDealsAdapter!!.notifyDataSetChanged()
    }

    fun showDataProduct() {

        if (adapterProduct.size != 0) {
            adapterProduct.clear()
        }
        productModel = Product(R.drawable.img_1, "Kritunga Restaurant", "4.4","50% OFF","South Indian, Chinese, Chat, North Indian...","4.0","40 Mins","200 for two","1")
        adapterProduct.add(productModel)

        productModel = Product(R.drawable.img_2, "Kritunga Restaurant", "4.4","50% OFF","South Indian, Chinese, Chat, North Indian...","4.0","40 Mins","200 for two","1")
        adapterProduct.add(productModel)

        productModel = Product(R.drawable.img_3, "Kritunga Restaurant", "4.4","50% OFF","South Indian, Chinese, Chat, North Indian...","4.0","40 Mins","200 for two","1")
        adapterProduct.add(productModel)


        //hotDealsAdapter!!.notifyDataSetChanged()
    }*/


    override fun setOnMaterial(userId: String?) {

        drawerLayout!!.closeDrawer(Gravity.RIGHT)
        if(userId!!.equals("Rererrals"))
        {   val activityFragment: Fragment = ReferralActivity()
            loadFragment(activityFragment, "Rererrals")
        }else if(userId!!.equals("Favourites")){
            val activityFragment: Fragment = FavoriteFragment()
            loadFragment(activityFragment, "Favourites")
        }else if(userId!!.equals("My Orders")){
            val activityFragment: Fragment = OrderHistoryActivity()
            loadFragment(activityFragment, "My Orders")
        }else if(userId!!.equals("Offers")){
            val activityFragment: Fragment = OffersFragment()
            loadFragment(activityFragment, "Offers")
        }

    }

    fun show() {
        if (adapterModels.size != 0) {
            adapterModels.clear()
        }
        adapterModel = AdapterModel(
            R.drawable.manage_address,
            "Manage Address",
            "Thu,14 Nov,10:00 AM \nThu,21 Nov,10:00 AM",
            "",
            "CHANGE",
            "1",
            "",
            "0",
            "4.4",
            "1",
            "3054 Ratings>",
            "5 Star Given by you",
            "",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,adapterProduct
        )
        adapterModels.add(adapterModel)
        adapterModel = AdapterModel(
            R.drawable.payments,
            "Payment",
            "Thu,14 Nov,10:00 AM \nThu,21 Nov,10:00 AM",
            "",
            "CHANGE",
            "1",
            "",
            "0",
            "4.4",
            "1",
            "3054 Ratings>",
            "5 Star Given by you",
            "",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,adapterProduct
        )
        adapterModels.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.favorite,
            "Favourites",
            "Thu,14 Nov,10:00 AM \nThu,21 Nov,10:00 AM",
            "",
            "CHANGE",
            "1",
            "",
            "0",
            "4.4",
            "1",
            "3054 Ratings>",
            "5 Star Given by you",
            "",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,adapterProduct
        )
        adapterModels.add(adapterModel)
        adapterModel = AdapterModel(
            R.drawable.referrals,
            "Rererrals",
            "Thu,14 Nov,10:00 AM \nThu,21 Nov,10:00 AM",
            "",
            "CHANGE",
            "1",
            "",
            "0",
            "4.4",
            "1",
            "3054 Ratings>",
            "5 Star Given by you",
            "",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,adapterProduct
        )
        adapterModels.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.my_orders,
            "My Orders",
            "Thu,14 Nov,10:00 AM \nThu,21 Nov,10:00 AM",
            "",
            "CHANGE",
            "1",
            "",
            "0",
            "4.4",
            "1",
            "3054 Ratings>",
            "5 Star Given by you",
            "",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,adapterProduct
        )
        adapterModels.add(adapterModel)
        adapterModel = AdapterModel(
            R.drawable.offers,
            "Offers",
            "Thu,14 Nov,10:00 AM \nThu,21 Nov,10:00 AM",
            "",
            "CHANGE",
            "1",
            "",
            "0",
            "4.4",
            "1",
            "3054 Ratings>",
            "5 Star Given by you",
            "",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,adapterProduct
        )
        adapterModels.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.settings,
            "App Settings",
            "Thu,14 Nov,10:00 AM \nThu,21 Nov,10:00 AM",
            "",
            "CHANGE",
            "1",
            "",
            "0",
            "4.4",
            "1",
            "3054 Ratings>",
            "5 Star Given by you",
            "",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,adapterProduct
        )
        adapterModels.add(adapterModel)
        adapterModel = AdapterModel(
            R.drawable.language,
            "Change Language",
            "Thu,14 Nov,10:00 AM \nThu,21 Nov,10:00 AM",
            "",
            "CHANGE",
            "1",
            "",
            "0",
            "4.4",
            "1",
            "3054 Ratings>",
            "5 Star Given by you",
            "",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,adapterProduct
        )
        adapterModels.add(adapterModel)

        adapter!!.notifyDataSetChanged()

    }



    fun init_footer()
    {
        homeBottomLL = findViewById(R.id.homeBottomLL) as LinearLayout

        homeBottomLL!!.setOnClickListener(this)

        reservationBottomLL = findViewById(R.id.reservationBottomLL) as LinearLayout

        reservationBottomLL!!.setOnClickListener(this)

        groceryBottomLL = findViewById(R.id.groceryBottomLL) as LinearLayout

        groceryBottomLL!!.setOnClickListener(this)

        favoriteBottomLL = findViewById(R.id.favoriteBottomLL) as LinearLayout

        favoriteBottomLL!!.setOnClickListener(this)

        accountPassBottomLL = findViewById(R.id.accountPassBottomLL) as LinearLayout

        accountPassBottomLL!!.setOnClickListener(this)

        homeBottomTV = findViewById(R.id.homeBottomTV) as TextView

        homeBottomTV!!.typeface = fontStyle(this,"")

        reservationBottomTV = findViewById(R.id.reservationBottomTV) as TextView

        reservationBottomTV!!.typeface = fontStyle(this,"")

        groceryBottomTV = findViewById(R.id.groceryBottomTV) as TextView

        groceryBottomTV!!.typeface = fontStyle(this,"")

        favoriteBottomTV = findViewById(R.id.favoriteBottomTV) as TextView

        favoriteBottomTV!!.typeface = fontStyle(this,"")

        accountPassBottomTV = findViewById(R.id.accountPassBottomTV) as TextView

        accountPassBottomTV!!.typeface = fontStyle(this,"")

        homeIV = findViewById(R.id.homeIV) as ImageView

        reservationIV = findViewById(R.id.reservationIV) as ImageView

        groceryIV = findViewById(R.id.groceryIV) as ImageView

        favoriteIV = findViewById(R.id.favoriteIV) as ImageView

        accountIV = findViewById(R.id.accountIV) as ImageView

        mapIV = findViewById(R.id.mapIV) as ImageView

        mapIV!!.setColorFilter(
            colorIcon(this,R.color.redColor,R.drawable.map_pin,mapIV!!),
            PorterDuff.Mode.SRC_ATOP)

        homeIV!!.setColorFilter(
            colorIcon(this,R.color.redColor,R.drawable.home,homeIV!!),
            PorterDuff.Mode.SRC_ATOP)

        homeBottomTV!!.setTextColor(Color.parseColor("#ec272b"))
    }

    override fun onClick(view: View?) {
        when(view!!.id)
        {
            R.id.homeBottomLL ->{

                colorIconNormal()
                homeIV!!.setColorFilter(
                    colorIcon(this,R.color.redColor,R.drawable.home,homeIV!!),
                    PorterDuff.Mode.SRC_ATOP)

                homeBottomTV!!.setTextColor(Color.parseColor("#ec272b"))

                if (fm != null) {
                    if (fm!!.backStackEntryCount != 0) {
                        supportFragmentManager.beginTransaction().remove(supportFragmentManager.findFragmentById(R.id.frame_container)!!).commit()
                    }
                    var i = 0
                    while (i < fm!!.backStackEntryCount) {
                        fm!!.popBackStack()
                        ++i
                    }
                }

            }
            R.id.reservationBottomLL ->{
                colorIconNormal()
                reservationIV!!.setColorFilter(
                    colorIcon(this,R.color.redColor,R.drawable.table,reservationIV!!),
                    PorterDuff.Mode.SRC_ATOP)

                reservationBottomTV!!.setTextColor(Color.parseColor("#ec272b"))

                val activityFragment: Fragment =
                    TableBookingRestrauntFragment()
                loadFragment(activityFragment, "Table Reservation")

            }

            R.id.groceryBottomLL ->{
                //colorIconNormal()
                groceryIV!!.setColorFilter(
                    colorIcon(this,R.color.colorGreen,R.drawable.grocery,groceryIV!!),
                    PorterDuff.Mode.SRC_ATOP)

                groceryBottomTV!!.setTextColor(Color.parseColor("#7DC77D"))

                //startActivity(Intent(this,DashBoardGrpceryActivity::class.java))

            }
            R.id.favoriteBottomLL ->{
                colorIconNormal()
                favoriteIV!!.setColorFilter(
                    colorIcon(this,R.color.redColor,R.drawable.favorite,favoriteIV!!),
                    PorterDuff.Mode.SRC_ATOP)

                favoriteBottomTV!!.setTextColor(Color.parseColor("#ec272b"))

                val activityFragment: Fragment =
                    FavoriteFragment()
                loadFragment(activityFragment, "Favorite")


            }
            R.id.accountPassBottomLL ->{
                colorIconNormal()
                accountIV!!.setColorFilter(
                    colorIcon(this,R.color.redColor,R.drawable.my_account,accountIV!!),
                    PorterDuff.Mode.SRC_ATOP)

                accountPassBottomTV!!.setTextColor(Color.parseColor("#ec272b"))

            }

            R.id.cancel ->{
                if (sheetBehavior!!.state == BottomSheetBehavior.STATE_COLLAPSED) //If state is in collapse mode expand it
                    sheetBehavior!!.setState(BottomSheetBehavior.STATE_EXPANDED) else  //else if state is expanded collapse it
                    sheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }

            R.id.profileImg ->{

                drawerLayout!!.openDrawer(Gravity.RIGHT)
            }

            R.id.searchEditText!! ->{
                searchEditText!!.isFocusableInTouchMode = true
            }
        }
    }


    fun colorIconNormal()
    {
        homeIV!!.setColorFilter(colorIcon(this,R.color.colorGray,R.drawable.home,homeIV!!),
            PorterDuff.Mode.SRC_ATOP)

        reservationIV!!.setColorFilter(colorIcon(this,R.color.colorGray,R.drawable.table,reservationIV!!),
            PorterDuff.Mode.SRC_ATOP)


        favoriteIV!!.setColorFilter(colorIcon(this,R.color.colorGray,R.drawable.favorite,favoriteIV!!),
            PorterDuff.Mode.SRC_ATOP)

        accountIV!!.setColorFilter(colorIcon(this,R.color.colorGray,R.drawable.my_account,accountIV!!),
            PorterDuff.Mode.SRC_ATOP)

        homeBottomTV!!.setTextColor(Color.parseColor("#ACACAC"))

        reservationBottomTV!!.setTextColor(Color.parseColor("#ACACAC"))

        favoriteBottomTV!!.setTextColor(Color.parseColor("#ACACAC"))

        accountPassBottomTV!!.setTextColor(Color.parseColor("#ACACAC"))
    }

    override fun fragmentChange(fragment: String?, bundle: Bundle?) {

        if(fragment!!.equals("Track"))
        {   val activityFragment: Fragment =
            OrderMapDetails()
            loadFragment(activityFragment, "Track")
        }
        else if(fragment!!.equals("View Order"))
        {   val activityFragment: Fragment =
            OrderBillDetailsActivity()
            loadFragment(activityFragment, "View Order")
        }else if(fragment!!.equals("DetailItemAddPageActivity"))
        {
            startActivity(Intent(this,DetailItemAddPageActivity::class.java))
        }else if(fragment!!.equals("Payment"))
        {
            startActivity(Intent(this,OrderDetailsActivity::class.java))
        }else if(fragment!!.equals("Help"))
        {
            /*val activityFragment: Fragment =
                HelpActivity()
            loadFragment(activityFragment, "Help")*/
        }else if(fragment!!.equals("Product List"))
        {
            val activityFragment: Fragment = ViewAllPagesFragment()
            activityFragment.arguments = bundle
            loadFragment(activityFragment, "Product List")
        }else if(fragment!!.equals("Detail Page")){

            val activityFragment: Fragment = DetailPageActivity()
            activityFragment.arguments = bundle
            loadFragment(activityFragment, "Detail Page")
        }else if(fragment!!.equals("Table Details Page")){

            val activityFragment: Fragment = DetailsTableBookingpageFragment()
            loadFragment(activityFragment, "Table Detail Page")
        }else if(fragment!!.equals("Table Booking")){

            val activityFragment: Fragment = TableReservationFragment()
            loadFragment(activityFragment, "Table Detail")
        }else if(fragment!!.equals("Add Person")){

            val activityFragment: Fragment = AddPersonActivity()
            loadFragment(activityFragment, "Add Person")
        }else if(fragment!!.equals("Add Menu")){

            val activityFragment: Fragment = AddMenuFragment()
            loadFragment(activityFragment, "Add Menu")
        }

    }

    override fun fragmentChangeView(fragment: String?, bundle: Bundle?) {

    }

    override fun fragmentBack(fragment: String?) {

    }

    override fun setOnFilter(id: String?) {
        if (sheetBehavior!!.state == BottomSheetBehavior.STATE_COLLAPSED) //If state is in collapse mode expand it
            sheetBehavior!!.setState(BottomSheetBehavior.STATE_EXPANDED) else  //else if state is expanded collapse it
            sheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)

    }

    override fun setCancelFilter(id: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setOnFilterSide(id: String?) {

        filtersidePageAdapter!!.notifyDataSetChanged()

        /*if(id!!.equals("Cuisine")){
            val activityFragment: Fragment = FilterPriceFragment()
            filterFragment(activityFragment, "Cuisine")
        }else if(id!!.equals("More Filter")){
            val activityFragment: Fragment = FilterPriceFragment()
            filterFragment(activityFragment, "More Filter")
        }else if(id!!.equals("Rating")){
            val activityFragment: Fragment = RatingFragment()
            filterFragment(activityFragment, "Rating")
        }else if(id!!.equals("Cast per person")){
            val activityFragment: Fragment = RatingFragment()
            filterFragment(activityFragment, "Cast per person")
        }else{
            if (fm1 != null) {
                if (fm1!!.backStackEntryCount != 0) {
                    supportFragmentManager.beginTransaction().remove(supportFragmentManager.findFragmentById(R.id.frame_container_filter)!!).commit()
                }
                var i = 0
                while (i < fm1!!.backStackEntryCount) {
                    fm1!!.popBackStack()
                    ++i
                }
            }
        }*/

    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
        if (mMaxScrollSize == 0) mMaxScrollSize = appBarLayout.totalScrollRange
        val percentage = Math.abs(i) * 100 / mMaxScrollSize

        Log.v("kkkk","value"+percentage);
        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false

            tabLayout!!.visibility = View.GONE

            dumy!!.visibility = View.GONE



        }
        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true
            tabLayout!!.animate()
                .scaleY(1f).scaleX(1f)
                .start()


            tabLayout!!.visibility = View.VISIBLE

            dumy!!.visibility = View.VISIBLE

        }
    }

    override fun onItem(type: String?, id: String?, view: String?, postion: Int) {
        if(id!!.equals("View All"))
        {
            val activityFragment: Fragment = ViewAllFragment()
            loadFragment(activityFragment, "View All")

        }else {

            val activityFragment: Fragment = DetailPageActivity()
            //activityFragment.arguments = bundle
            loadFragment(activityFragment, "Detail Page")
        }
    }


}
