package com.lieferin_global.Activity

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.location.*
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.iid.FirebaseInstanceId
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.lieferin_global.Adapter.*
import com.lieferin_global.Fragment.*
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.AddressTXT
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.Constant.isTableBooking
import com.lieferin_global.Utility.Constant.photoConstant
import com.lieferin_global.Utility.Constant.photoSelect
import com.rentaloo.CallBack.CallBlacklisting
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.lang.reflect.InvocationTargetException
import java.util.*


class DashBoardActivity : AppCompatActivity(), SlideMenu_item_Adpt.CallbackDataAdapter,
    DashBoardAdapter.OnClickDashBoard, View.OnClickListener, CallBlacklisting,
    FilterPageAdapter.CallbackDataAdapter, FiltersidePageAdapter.CallbackDataAdapter,
    LanguageAdapter.CallbackDataAdapter,
    AppBarLayout.OnOffsetChangedListener {

    var dbHelper: DBHelper? = null

    private val mGoogleApiClient: GoogleApiClient? = null
    private val ACCESS_FINE_LOCATION_INTENT_ID = 3
    private val BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED"

    private val PERCENTAGE_TO_ANIMATE_AVATAR = 20
    private var mIsAvatarShown = true

    protected val REQUEST_CHECK_SETTINGS = 1000
    private var mMaxScrollSize = 0

    private var navigationView: NavigationView? = null

    private var drawerLayout: DrawerLayout? = null

    var cancel: ImageView? = null

    private var locationManager: LocationManager? = null

    private var toolbar: Toolbar? = null

    var geocoder: Geocoder? = null
    var addresses: MutableList<Address> = ArrayList()

    var adapter: SlideMenu_item_Adpt? = null

    var filterPageAdapter: FilterPageAdapter? = null

    var adapterModels: MutableList<AdapterModel> = ArrayList()

    var adapterTrending: MutableList<AdapterModel> = ArrayList()

    var adapterFeature: MutableList<AdapterModel> = ArrayList()

    var adapterFamous: MutableList<AdapterModel> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    var adapterProduct: MutableList<Product> = ArrayList()

    internal lateinit var productModel: Product

    var slideUserName: TextView? = null

    var slideAddress: TextView? = null

    var addressTextView: TextView? = null

    var searchEditText: TextView? = null

    var filterRecyclerView: RecyclerView? = null

    var fragmentManager: FragmentManager? = null

    var fm: FragmentManager? = null

    var fragmentManager1: FragmentManager? = null

    var fm1: FragmentManager? = null

    var homeBottomLL: LinearLayout? = null

    var reservationBottomLL: LinearLayout? = null

    var groceryBottomLL: LinearLayout? = null

    var favoriteBottomLL: LinearLayout? = null

    var accountPassBottomLL: LinearLayout? = null

    var homeBottomTV: TextView? = null

    var reservationBottomTV: TextView? = null

    var groceryBottomTV: TextView? = null

    var favoriteBottomTV: TextView? = null

    var accountPassBottomTV: TextView? = null

    var homeIV: ImageView? = null

    var reservationIV: ImageView? = null

    var groceryIV: ImageView? = null

    var favoriteIV: ImageView? = null

    var accountIV: ImageView? = null

    var mapIV: ImageView? = null

    var headerLayout: LinearLayout? = null

    var profileImg: CircleImageView? = null

    var userImage: CircleImageView? = null

    var qrCodeIV : ImageView? = null

    var tabLayout: TabLayout? = null

    var viewPager: CustomViewPager? = null

    var sheetBehavior: BottomSheetBehavior<*>? = null

    var filterApply: TextView? = null

    var filterClearAll: TextView? = null

    var filterTV: TextView? = null

    var filterListRecyclerView: RecyclerView? = null

    var filtersidePageAdapter: FiltersidePageAdapter? = null

    var radioButton: RadioButton? = null

    var radioButton1: RadioButton? = null

    var radioButton2: RadioButton? = null

    var radioButton3: RadioButton? = null

    var radioButton4: RadioButton? = null

    var dumy: TextView? = null

    var languageAdapter: LanguageAdapter? = null

    var adapterDetails: MutableList<AdapterModel> = ArrayList()

    var adapterDetailsCategory: MutableList<AdapterModel> = ArrayList()

    var adapterDetailChild: MutableList<Product> = ArrayList()

    private val REQUEST_LOCATION = 22

    var appbarLayout: AppBarLayout? = null

    var locationRelation: RelativeLayout? = null

    var valueString: String = "0"

    var latitude: String = ""

    var longtitude: String = ""

    var editProfile: ImageView? = null

    var searchImageView: ImageView? = null

    var locationManager1: LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("onCreate", "kkkkk")

        Log.i("" + FirebaseInstanceId.getInstance().getToken(), "FCM")

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setContentView(R.layout.activity_dash_board)

        qrCodeIV = findViewById(R.id.qrCodeIV)

        qrCodeIV!!.setOnClickListener(this)

        dbHelper = DBHelper(this)

        /*if(Constant.latitudeAdd.equals(""))
        {
            var intent = Intent(this, Main4Activity::class.java)

            startActivity(intent)
        }*/

        isTableBooking = "0"

        navigationView = findViewById<View>(R.id.nvView1) as NavigationView

        editProfile = findViewById(R.id.editProfile) as ImageView

        editProfile!!.setOnClickListener(this)

        locationRelation = findViewById<View>(R.id.locationRelation) as RelativeLayout

        drawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout

        headerLayout = findViewById<View>(R.id.headerLayout) as LinearLayout

        profileImg = findViewById<View>(R.id.profileImg) as CircleImageView

        profileImg!!.setOnClickListener(this)

        userImage = findViewById<View>(R.id.userImage) as CircleImageView

        userImage!!.setOnClickListener(this)

        dumy = findViewById<View>(R.id.dumy) as TextView

        headerLayout!!.setBackgroundColor(ContextCompat.getColor(this, R.color.redColor));

        slideUserName = findViewById(R.id.slideUserName) as TextView

        slideUserName!!.typeface = fontStyle(this, "SemiBold")

        slideAddress = findViewById(R.id.slideAddress) as TextView

        slideAddress!!.typeface = fontStyle(this, "Light")

        addressTextView = findViewById(R.id.addressTextView) as TextView

        addressTextView!!.typeface = fontStyle(this, "")

        addressTextView!!.setOnClickListener(this)

        searchEditText = findViewById(R.id.searchEditText) as TextView

        searchEditText!!.typeface = fontStyle(this, "Light")

        searchEditText!!.setOnClickListener(this)

        searchImageView = findViewById(R.id.searchImageView) as ImageView

        searchImageView!!.setColorFilter(
            colorIcon(this!!, R.color.colorGray, R.drawable.search, searchImageView!!),
            PorterDuff.Mode.SRC_ATOP
        )

        radioButton = findViewById(R.id.radioButton) as RadioButton

        radioButton!!.typeface = fontStyle(this, "")

        radioButton1 = findViewById(R.id.radioButton2) as RadioButton

        radioButton1!!.typeface = fontStyle(this, "")

        radioButton2 = findViewById(R.id.radioButton3) as RadioButton

        radioButton2!!.typeface = fontStyle(this, "")

        radioButton3 = findViewById(R.id.radioButton4) as RadioButton

        radioButton3!!.typeface = fontStyle(this, "")

        radioButton4 = findViewById(R.id.radioButton5) as RadioButton

        radioButton4!!.typeface = fontStyle(this, "")

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
                    Toast.makeText(applicationContext, "Somethings Wrong", Toast.LENGTH_SHORT)
                        .show()
                    true
                }
            }
        })

        filterRecyclerView = findViewById(R.id.filterRecyclerView) as RecyclerView

        filterPageAdapter = FilterPageAdapter(this, adapterTrending)

        val mLayoutManager2: RecyclerView.LayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        filterRecyclerView!!.layoutManager = mLayoutManager2

        filterRecyclerView!!.itemAnimator!!.addDuration = 5000

        filterPageAdapter!!.setCallback(this)

        filterRecyclerView!!.adapter = filterPageAdapter!!

        showDataTrending()
        /*
        //mAdapterShip = HomeShipAdapter(this, topRate)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        allRecyclerView!!.layoutManager = mLayoutManager

        allRecyclerView!!.itemAnimator!!.addDuration = 5000
        */

        init_footer()

        show()

        /*addProduct()

        showDataProduct()

        showDataTrending()

        showDataFeature()

        showDataFamous()*/

        viewPager = findViewById(R.id.college_viewpager) as CustomViewPager
        setupViewPager(viewPager!!);

        viewPager!!.disableScroll(true)

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

        filterTV!!.typeface = fontStyle(this, "")

        filterApply = findViewById(R.id.filterApply) as TextView

        filterApply!!.typeface = fontStyle(this, "SemiBold")

        filterClearAll = findViewById(R.id.filterClearAll) as TextView

        filterClearAll!!.typeface = fontStyle(this, "")

        filterListRecyclerView = findViewById(R.id.filterListRecyclerView) as RecyclerView

        filtersidePageAdapter = FiltersidePageAdapter(this, adapterFeature)

        val mLayoutManager3: RecyclerView.LayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

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
                    BottomSheetBehavior.STATE_COLLAPSED -> Log.d(
                        ContentValues.TAG,
                        "State Collapsed"
                    )
                    BottomSheetBehavior.STATE_DRAGGING -> {

                        sheetBehavior!!.setState(BottomSheetBehavior.STATE_EXPANDED);

                    }
                    BottomSheetBehavior.STATE_EXPANDED -> Log.d(ContentValues.TAG, "State Expanded")
                    BottomSheetBehavior.STATE_HIDDEN -> Log.d(ContentValues.TAG, "State Hidden")
                    BottomSheetBehavior.STATE_SETTLING -> Log.d(ContentValues.TAG, "State Settling")
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        appbarLayout = findViewById<View>(R.id.materialup_appbar) as AppBarLayout

        appbarLayout!!.addOnOffsetChangedListener(this)

        geocoder = Geocoder(this, Locale.getDefault())
        val extras = intent.extras
        val userName: String?
        if (extras != null) {
            //showToast(this, "" + extras.getString("NotificationTitle"))
            if (extras.getString("page") != null) {
                userName = extras.getString("page")
                if (userName!!.equals(getString(R.string.My_Order))) {
                    val activityFragment: Fragment = OrderHistoryActivity()
                    loadFragment(activityFragment, getString(R.string.My_Orders))
                } else if (userName!!.equals(getString(R.string.Table))) {
                    val activityFragment: Fragment =
                        TableReservationFragment()
                    loadFragment(activityFragment, getString(R.string.Table_Reservation))
                } else if (userName!!.equals(getString(R.string.Profile))) {
                    colorIconNormal()
                    accountIV!!.setColorFilter(
                        colorIcon(this, R.color.redColor, R.drawable.my_account, accountIV!!),
                        PorterDuff.Mode.SRC_ATOP
                    )

                    accountPassBottomTV!!.setTextColor(Color.parseColor("#ec272b"))

                    val activityFragment: Fragment = ProfileFragment()
                    loadFragment(activityFragment, getString(R.string.Profile))

                    photoConstant = null

                } else if (userName!!.equals(getString(R.string.Filter))) {

                    appbarLayout!!.setExpanded(false)

                    locationRelation!!.visibility = View.GONE

                    Constant.valueString = "" + getString(R.string.Filter)

                    //Constant.valueStringFour = 4.0
                } else if (userName!!.equals(getString(R.string.Details_Page))) {
                    val bundle = Bundle()
                    bundle.putString("Title", extras.getString("Title"))
                    val activityFragment: Fragment = DetailPageActivity()
                    activityFragment.arguments = bundle
                    loadFragment(activityFragment, "" + getString(R.string.Detail_Page))
                }
            }
            if (extras.getString("LocationText") != null) {
                addressTextView!!.text = AddressTXT
                Constant.valueString = ""
                dbHelper!!.deleteLocation()
                dbHelper!!.addLocation(Constant.latitudeAdd,Constant.longtitudeAdd, AddressTXT)

            }

            if (extras.getString("latitude") != null) {

                latitude = "" + extras.getString("latitude")

            }

            if (extras.getString("longtitude") != null) {

                longtitude = "" + extras.getString("longtitude")

            }
        }
        locationManager1 = getSystemService(LOCATION_SERVICE) as LocationManager?
        //getLocation()

        if(dbHelper!!.getUserLocation().address != null) {

            AddressTXT = ""+dbHelper!!.getUserLocation().address
            addressTextView!!.text = dbHelper!!.getUserLocation().address
            Constant.latitudeAdd = dbHelper!!.getUserLocation().latitude!!
            Constant.longtitudeAdd = dbHelper!!.getUserLocation().longitude!!

            Log.v("Location == "+Constant.latitudeAdd,"Location == "+Constant.longtitudeAdd)
        }else{

        }
    }


    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this@DashBoardActivity, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this@DashBoardActivity, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {
            val locationGPS =
                locationManager1!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (locationGPS != null) {
                val lat = locationGPS.latitude
                val longi = locationGPS.longitude
                /*Toast.makeText(this, "Your Location: \n" +
                        "Latitude: $lat\n" +
                        "Longitude:$longi", Toast.LENGTH_SHORT).show()*/
                    //showLocation.setText("Your Location: \nLatitude: $lat\nLongitude: $longitude")

                //getCompleteAddressString(this@DashBoardActivity,locationGPS.latitude,locationGPS.longitude)

                //Log.i("Location","=="+getCompleteAddressString(this@DashBoardActivity,locationGPS.latitude,locationGPS.longitude))
                val geocoder: Geocoder
                val yourAddresses: List<Address>
                geocoder = Geocoder(this, Locale.getDefault())
                yourAddresses = geocoder.getFromLocation(lat, longi, 1)

                if (yourAddresses.size > 0) {
                    val yourAddress = yourAddresses[0].getAddressLine(0)
                    val yourCity = yourAddresses[0].getAddressLine(1)
                    val yourCountry = yourAddresses[0].getAddressLine(2)

                    Log.i("Location","=="+yourAddress)
                    Toast.makeText(this, yourAddress, Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupViewPager(viewPager: ViewPager) {


        val adapter = ViewPagerAdapter(supportFragmentManager)

        for (i in 0 until adapter.count) {
            adapter.removeFragment(i)
        }

        adapter.addFragment(DeliveryFragment(), getString(R.string.Delivery))

        adapter.addFragment(PickUpPagesFragment(), getString(R.string.Pick_Up))
        Log.v("lllll "+adapter.count,"----")
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

        fun removeFragment(position: Int) {
            mFragmentList.removeAt(position)
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

    fun backFragment() {
        if (fm != null) {
            if (fm!!.backStackEntryCount > 0) {
                if (dbHelper!!.getMenu().size == 0) {
                    fm!!.popBackStackImmediate()
                } else {
                    if (!fm!!.getBackStackEntryAt(fm!!.getBackStackEntryCount() - 1).getName()
                            .equals("Details About")
                    ) {
                        showDialogTableInfo(this)
                    }else{
                        fm!!.popBackStackImmediate()
                    }
                }
            } else {
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP //***Change Here***
                startActivity(intent)
                finish()
                System.exit(0)
                //finish()
            }
            if (fm!!.backStackEntryCount > 0) {

                if (fm!!.getBackStackEntryAt(fm!!.getBackStackEntryCount() - 1).getName()
                        .equals(getString(R.string.Table_Reservation))
                ) {

                    colorIconNormal()
                    reservationIV!!.setColorFilter(
                        colorIcon(this, R.color.redColor, R.drawable.table, reservationIV!!),
                        PorterDuff.Mode.SRC_ATOP
                    )

                    reservationBottomTV!!.setTextColor(Color.parseColor("#ec272b"))


                } else if (fm!!.getBackStackEntryAt(fm!!.getBackStackEntryCount() - 1).getName()
                        .equals(getString(R.string.Favourites))
                ) {

                    colorIconNormal()
                    favoriteIV!!.setColorFilter(
                        colorIcon(this, R.color.redColor, R.drawable.favorite, favoriteIV!!),
                        PorterDuff.Mode.SRC_ATOP
                    )

                    favoriteBottomTV!!.setTextColor(Color.parseColor("#ec272b"))

                } else if (fm!!.getBackStackEntryAt(fm!!.getBackStackEntryCount() - 1).getName()
                        .equals(getString(R.string.Profile))
                ) {

                    colorIconNormal()
                    accountIV!!.setColorFilter(
                        colorIcon(this, R.color.redColor, R.drawable.my_account, accountIV!!),
                        PorterDuff.Mode.SRC_ATOP
                    )
                    accountPassBottomTV!!.setTextColor(Color.parseColor("#ec272b"))

                } else if (fm!!.getBackStackEntryAt(fm!!.getBackStackEntryCount() - 1).getName()
                        .equals(getString(R.string.Detail_Page))
                ) {

                    colorIconNormal()
                    homeIV!!.setColorFilter(
                        colorIcon(this, R.color.redColor, R.drawable.home, homeIV!!),
                        PorterDuff.Mode.SRC_ATOP
                    )
                    homeBottomTV!!.setTextColor(Color.parseColor("#ec272b"))

                }


            }
            if (fm!!.backStackEntryCount === 0) {

                colorIconNormal()
                homeIV!!.setColorFilter(
                    colorIcon(this, R.color.redColor, R.drawable.home, homeIV!!),
                    PorterDuff.Mode.SRC_ATOP
                )
                homeBottomTV!!.setTextColor(Color.parseColor("#ec272b"))
                Log.v("jjjjj", "Sucess")
            } else {

                Log.v("jjjjj", "Sucess1")
            }
        }
    }

    fun showDataTrending() {

        if (adapterTrending.size != 0) {
            adapterTrending.clear()
        }
        adapterModel = AdapterModel(
            R.drawable.recommended,
            "" + getString(R.string.Filter),
            "1",
            "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002",
            "Save 60 %",
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
            adapterProduct
        )
        adapterTrending.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.recommended,
            "" + getString(R.string.RatingValue),
            "0",
            "",
            "Save 60 %",
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
            adapterProduct
        )
        adapterTrending.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.recommended,
            "" + getString(R.string.Cuisine),
            "2",
            "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002",
            "Save 60 %",
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
            adapterProduct
        )
        //adapterTrending.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.recommended,
            "" + getString(R.string.Ratings),
            "2",
            "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002",
            "Save 60 %",
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
            adapterProduct
        )
        adapterTrending.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.recommended,
            "" + getString(R.string.Cost),
            "2",
            "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002",
            "Save 60 %",
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
            adapterProduct
        )
        adapterTrending.add(adapterModel)

//        adapterModel = AdapterModel(R.drawable.recommended, "Culisines", "2", "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002", "Save 60 %", " 976", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
//        adapterTrending.add(adapterModel)


        filterPageAdapter!!.notifyDataSetChanged()
    }


    fun showDataTrending1() {

        if (adapterFeature.size != 0) {
            adapterFeature.clear()
        }
        adapterModel = AdapterModel(
            R.drawable.recommended,
            "" + getString(R.string.Sort_by),
            "Popularity",
            "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002",
            "Save 60 %",
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
            adapterProduct
        )
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.recommended,
            "" + getString(R.string.Cuisine),
            "",
            "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002",
            "Save 60 %",
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
            adapterProduct
        )
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.recommended,
            "" + getString(R.string.Rating),
            "",
            "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002",
            "Save 60 %",
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
            adapterProduct
        )
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.recommended,
            "" + getString(R.string.Cast_per_person),
            "",
            "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002",
            "Save 60 %",
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
            adapterProduct
        )
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.recommended,
            "" + getString(R.string.More_Filter),
            "",
            "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002",
            "Save 60 %",
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
            adapterProduct
        )
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
        if (userId!!.equals(getString(R.string.Rererrals))) {
            val activityFragment: Fragment = ReferralActivity()
            loadFragment(activityFragment, getString(R.string.Rererrals))
        } else if (userId!!.equals(getString(R.string.Favourites))) {
            val activityFragment: Fragment = FavoriteFragment()
            loadFragment(activityFragment, getString(R.string.Favourites))
        } else if (userId!!.equals(getString(R.string.My_Orders))) {
            val activityFragment: Fragment = OrderHistoryActivity()
            loadFragment(activityFragment, getString(R.string.My_Orders))
        } else if (userId!!.equals(getString(R.string.Table_Booking))) {
            val activityFragment: Fragment = OrderTableHistoryActivity()
            loadFragment(activityFragment, getString(R.string.Table_Booking))
/*
            showToast(this,"Coming Soon")*/
        } else if (userId!!.equals(getString(R.string.Offers))) {
            val activityFragment: Fragment = OffersFragment()
            loadFragment(activityFragment, getString(R.string.Offers))
        } else if (userId!!.equals(getString(R.string.Manage_Address))) {
            startActivity(Intent(this, AddressDetailsActivity::class.java))
        } else if (userId!!.equals(getString(R.string.Payment))) {
            val activityFragment: Fragment = SaveCardFragment()
            loadFragment(activityFragment, getString(R.string.Payment))
        } else if (userId!!.equals(getString(R.string.Change_Language))) {
            showDialogAddService(this)
        }

    }

    fun showLanguage() {
        if (adapterDetails!!.size != 0) {
            adapterDetails!!.clear()
        }
        adapterModel = AdapterModel(
            0,
            "English",
            "",
            "",
            "",
            "3 Years",
            "",
            "",
            "",
            "",
            "",
            "VTU",
            "Rank ",
            "10% \nOFF",
            "Admission Open",
            "Follow",
            "Apply",
            "1",
            0,
            0,
            0,
            adapterDetailChild
        )
        adapterDetails!!.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "Dutch",
            "",
            "",
            "",
            "3 Years",
            "",
            "",
            "",
            "",
            "",
            "VTU",
            "Rank ",
            "10% \nOFF",
            "Admission Open",
            "Follow",
            "Apply",
            "1",
            0,
            0,
            0,
            adapterDetailChild
        )

        adapterDetails!!.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "Portuguese",
            "",
            "",
            "",
            "3 Years",
            "",
            "",
            "",
            "",
            "",
            "VTU",
            "Rank ",
            "10% \nOFF",
            "Admission Open",
            "Follow",
            "Apply",
            "1",
            0,
            0,
            0,
            adapterDetailChild
        )

        adapterDetails!!.add(adapterModel)
    }

    fun show() {
        if (adapterModels.size != 0) {
            adapterModels.clear()
        }
        adapterModel = AdapterModel(
            R.drawable.manage_address,
            "" + getString(R.string.Manage_Address),
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
            0, adapterProduct
        )
        adapterModels.add(adapterModel)
        adapterModel = AdapterModel(
            R.drawable.payments,
            "" + getString(R.string.Payment),
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
            0, adapterProduct
        )
        adapterModels.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.favorite,
            "" + getString(R.string.Favourites),
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
            0, adapterProduct
        )
        adapterModels.add(adapterModel)
        adapterModel = AdapterModel(
            R.drawable.referrals,
            "" + getString(R.string.Rererrals),
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
            0, adapterProduct
        )
        adapterModels.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.my_orders,
            "" + getString(R.string.My_Orders),
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
            0, adapterProduct
        )
        adapterModels.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.table,
            "" + getString(R.string.Table_Booking),
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
            0, adapterProduct
        )
        adapterModels.add(adapterModel)
        adapterModel = AdapterModel(
            R.drawable.offers,
            "" + getString(R.string.Offers),
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
            0, adapterProduct
        )
        adapterModels.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.settings,
            "" + getString(R.string.App_Settings),
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
            0, adapterProduct
        )
        //adapterModels.add(adapterModel)
        adapterModel = AdapterModel(
            R.drawable.language,
            "" + getString(R.string.Change_Language),
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
            0, adapterProduct
        )
        adapterModels.add(adapterModel)

        adapter!!.notifyDataSetChanged()

        val rc: Int = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (rc == PackageManager.PERMISSION_GRANTED) {
            displayLocationSettingsRequest(this)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION
            )
        }
    }

    private fun displayLocationSettingsRequest(context: Context) {
        val googleApiClient = GoogleApiClient.Builder(context)
            .addApi(LocationServices.API).build()
        googleApiClient.connect()
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 10000 / 2.toLong()
        val builder =
            LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)


        val result =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback { result ->
            val status = result.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS -> {

                    if(Constant.latitudeAdd.equals("")) {

                        if(dbHelper!!.getUserLocation().address == null) {
                            gpsLocation()
                        }
                    }
                    //getLocation()
                    Log.i("", "All location settings are satisfied.")
                }
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    Log.i(
                        "",
                        "Location settings are not satisfied. Show the user a dialog to upgrade location settings "
                    )
                    try { // Show the dialog by calling startResolutionForResult(), and check the result
                        // in onActivityResult().
                        status.startResolutionForResult(
                            this,
                            REQUEST_CHECK_SETTINGS
                        )
                    } catch (e: SendIntentException) {
                        Log.i("", "PendingIntent unable to execute request.")
                    }
                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Log.i(
                    "",
                    "Location settings are inadequate, and cannot be fixed here. Dialog not created."
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        print("onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.v("onResume DashBoard", "kkkkk")

        val extras = intent.extras
        val userName: String?
        Log.v("not"+extras, "kkkkk")
        if (extras != null) {
            //showToast(this, "ppp" + extras.getString("NotificationTitle"))

            Log.v("not"+extras, "kkkkk "+ extras.getString("NotificationTitle"))

            if(extras.getString("NotificationTitle").equals("Order Placed"))
            {
                val activityFragment: Fragment = OrderHistoryActivity()
                loadFragment(activityFragment, getString(R.string.My_Orders))

                //startActivity(Intent(this, OrderRatingActivity::class.java))
            }
        }

        try {
            slideAddress!!.text = dbHelper!!.getUserDetails().address

            slideUserName!!.text = dbHelper!!.getUserDetails().name

            if(!dbHelper!!.getUserDetails().profilePicture.equals("")) {

                Picasso.with(this).load(dbHelper!!.getUserDetails().profilePicture).resize(450, 450)
                    .placeholder(R.drawable.place_holder).into(profileImg)
                Picasso.with(this).load(dbHelper!!.getUserDetails().profilePicture).resize(450, 450)
                    .placeholder(R.drawable.place_holder).into(userImage)
            }



        } catch (e: NullPointerException) {

        }

    }

    override fun onPause() {
        super.onPause()
        print("onPause")
    }

    override fun onStop() {
        super.onStop()
        print("onStop")
    }

    override fun onRestart() {
        super.onRestart()

        //registerReceiver(broadCastReceiver, IntentFilter(BROADCAST_ACTION))
        print("onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        /*if (broadCastReceiver != null)
            unregisterReceiver(broadCastReceiver);*/

        print("onDestroy")

        if (isFinishing) {
            AppType = "1"
        }
    }

    val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            Log.i("", "PendingIntxzxzxzzxent unable to execute request.")
            when (intent?.action) {

                BROADCAST_ACTION -> {

                    Log.i("", "PendingIntent unable to execute request.")
                }

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                Log.i("", "PendrrrrringIntent unable to execute request.")

                if(Constant.latitudeAdd.equals("")) {

                    if(dbHelper!!.getUserLocation().address == null) {
                        gpsLocation()
                    }
                }
            }
        }else  if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Log.v("llllll", "lllll")
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                photoConstant = MediaStore.Images.Media.getBitmap(this.contentResolver, resultUri)
                val activityFragment: Fragment = ProfileFragment()
                loadFragment(activityFragment, getString(R.string.Profile))
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }else{
            if(!photoSelect.equals("0")) {
                val result: IntentResult =
                    IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

                if(result.getContents() != null) {
                    var bundle = Bundle()

                    bundle.putString("Title", result.getContents())

                    val activityFragment: Fragment = DetailPageActivity()
                    activityFragment.arguments = bundle
                    loadFragment(activityFragment, "Detail Page")
                    Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG)
                        .show();
                }

                photoSelect ="0"
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {

            REQUEST_LOCATION -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                displayLocationSettingsRequest(this)
            } else {
                showToast(this, "Permission Denied")
            }
        }
    }

    @SuppressLint("ServiceCast", "MissingPermission")
    fun gpsLocation() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        locationManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0L,
            0f,
            locationListener
        )


    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            //thetext.text = ("" + location.longitude + ":" + location.latitude)

            if(location != null) {
                Constant.latitudeAdd = "" + location.latitude

                Constant.longtitudeAdd = "" + location.longitude
                try {
                    Log.v(getCompleteAddressString(this@DashBoardActivity,location.longitude,location.latitude)+" pppp "+location.longitude,"===="+ location.latitude)


                    addText(getCompleteAddressString(this@DashBoardActivity,location.latitude,location.longitude)!!)


                    /*addresses = geocoder!!.getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                    ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    Constant.latitudeAdd = "" + location.latitude

                    Constant.longtitudeAdd = "" + location.longitude

                    val address: String = addresses[0].getAddressLine(0)

                    Log.v("Address = " + Constant.latitudeAdd, "" + address)*/

                    //showToast(c,"dddd")




                } catch (ex: InvocationTargetException) {

                }
            }

        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    fun addText(address: String) {
        if (latitude!!.equals("")) {
            AddressTXT =  "" + address
            addressTextView!!.text = "" + AddressTXT
            dbHelper!!.deleteLocation()
            dbHelper!!.addLocation(Constant.latitudeAdd,Constant.longtitudeAdd, AddressTXT)
        }
        setupViewPager(viewPager!!);
        //tabLayout!!.setupWithViewPager(viewPager);

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
        locationManager?.removeUpdates(locationListener)
    }

    fun init_footer() {
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

        homeBottomTV!!.typeface = fontStyle(this, "")

        reservationBottomTV = findViewById(R.id.reservationBottomTV) as TextView

        reservationBottomTV!!.typeface = fontStyle(this, "")

        groceryBottomTV = findViewById(R.id.groceryBottomTV) as TextView

        groceryBottomTV!!.typeface = fontStyle(this, "")

        favoriteBottomTV = findViewById(R.id.favoriteBottomTV) as TextView

        favoriteBottomTV!!.typeface = fontStyle(this, "")

        accountPassBottomTV = findViewById(R.id.accountPassBottomTV) as TextView

        accountPassBottomTV!!.typeface = fontStyle(this, "")

        homeIV = findViewById(R.id.homeIV) as ImageView

        reservationIV = findViewById(R.id.reservationIV) as ImageView

        groceryIV = findViewById(R.id.groceryIV) as ImageView

        favoriteIV = findViewById(R.id.favoriteIV) as ImageView

        accountIV = findViewById(R.id.accountIV) as ImageView

        mapIV = findViewById(R.id.mapIV) as ImageView

        mapIV!!.setColorFilter(
            colorIcon(this, R.color.redColor, R.drawable.map_pin, mapIV!!),
            PorterDuff.Mode.SRC_ATOP
        )

        homeIV!!.setColorFilter(
            colorIcon(this, R.color.redColor, R.drawable.home, homeIV!!),
            PorterDuff.Mode.SRC_ATOP
        )

        homeBottomTV!!.setTextColor(Color.parseColor("#ec272b"))
    }


    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.homeBottomLL -> {
                if(!dbHelper!!.getUserDetails().profilePicture.equals("")) {
                    Picasso.with(this).load(dbHelper!!.getUserDetails().profilePicture)
                        .resize(450, 450)
                        .placeholder(R.drawable.place_holder).into(profileImg)

                    Picasso.with(this).load(dbHelper!!.getUserDetails().profilePicture)
                        .resize(450, 450)
                        .placeholder(R.drawable.place_holder).into(userImage)
                }
                colorIconNormal()
                homeIV!!.setColorFilter(
                    colorIcon(this, R.color.redColor, R.drawable.home, homeIV!!),
                    PorterDuff.Mode.SRC_ATOP
                )

                homeBottomTV!!.setTextColor(Color.parseColor("#ec272b"))

                if (fm != null) {
                    if (fm!!.backStackEntryCount != 0) {
                        supportFragmentManager.beginTransaction()
                            .remove(supportFragmentManager.findFragmentById(R.id.frame_container)!!)
                            .commit()
                    }
                    var i = 0
                    while (i < fm!!.backStackEntryCount) {
                        fm!!.popBackStack()
                        ++i
                    }
                }

            }

            R.id.reservationBottomLL -> {
                colorIconNormal()
                reservationIV!!.setColorFilter(
                    colorIcon(this, R.color.redColor, R.drawable.table, reservationIV!!),
                    PorterDuff.Mode.SRC_ATOP
                )

                reservationBottomTV!!.setTextColor(Color.parseColor("#ec272b"))

                val activityFragment: Fragment =
                    TableBookingRestrauntFragment()
                loadFragment(activityFragment, "Table Reservation")

                //showToast(this,"This feature is currently unavailable")

            }

            R.id.groceryBottomLL -> {
                //colorIconNormal()
                groceryIV!!.setColorFilter(
                    colorIcon(this, R.color.colorGreen, R.drawable.grocery, groceryIV!!),
                    PorterDuff.Mode.SRC_ATOP
                )

                groceryBottomTV!!.setTextColor(Color.parseColor("#7DC77D"))

                //startActivity(Intent(this, DashBoardGrpceryActivity::class.java))

                var intent = (Intent(this, DashBoardGrpceryActivity::class.java))

                intent.putExtra("AddressTxt", "" + addressTextView!!.text.toString())
                startActivity(intent)

                AppType = "1"

            }
            R.id.favoriteBottomLL -> {
                colorIconNormal()
                favoriteIV!!.setColorFilter(
                    colorIcon(this, R.color.redColor, R.drawable.favorite, favoriteIV!!),
                    PorterDuff.Mode.SRC_ATOP
                )

                favoriteBottomTV!!.setTextColor(Color.parseColor("#ec272b"))

                val activityFragment: Fragment =
                    FavoriteFragment()
                loadFragment(activityFragment, "Favorite")
            }

            R.id.editProfile -> {
                startActivity(Intent(this, UpdateProfileFragment::class.java))
            }
            R.id.addressTextView -> {

                startActivity(Intent(this, LocationActivity::class.java))

            }

            R.id.qrCodeIV ->{
                val integrator = IntentIntegrator(this)
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                integrator.setPrompt("Scan")
                integrator.setCameraId(0)
                integrator.captureActivity = CaptureActivityPortrait::class.java
                integrator.setBeepEnabled(false)
                integrator.setOrientationLocked(true);
                integrator.setBarcodeImageEnabled(true)
                integrator.initiateScan()

                photoSelect = "1"
            }

            R.id.accountPassBottomLL -> {

                colorIconNormal()
                accountIV!!.setColorFilter(
                    colorIcon(this, R.color.redColor, R.drawable.my_account, accountIV!!),
                    PorterDuff.Mode.SRC_ATOP
                )

                accountPassBottomTV!!.setTextColor(Color.parseColor("#ec272b"))
                if(dbHelper!!.getUserDetails().token == null)
                {
                    val mIentent = Intent(this, LoginActivity::class.java)
                    //mIentent.putExtra("page",Constant.Location);
                    startActivity(mIentent)
                }else {
                    val activityFragment: Fragment = ProfileFragment()
                    loadFragment(activityFragment, "ProFile")
                }

                photoConstant = null

            }

            R.id.cancel -> {
                if (sheetBehavior!!.state == BottomSheetBehavior.STATE_COLLAPSED) //If state is in collapse mode expand it
                    sheetBehavior!!.setState(BottomSheetBehavior.STATE_EXPANDED) else  //else if state is expanded collapse it
                    sheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }

            R.id.profileImg -> {

                drawerLayout!!.openDrawer(Gravity.RIGHT)
            }

            R.id.searchEditText!! -> {

                var intent = (Intent(this, SearchActivity::class.java))

                intent.putExtra("Address", "" + addressTextView!!.text.toString())
                startActivity(intent)
            }
        }
    }

    fun colorIconNormal() {
        homeIV!!.setColorFilter(
            colorIcon(this, R.color.colorGray, R.drawable.home, homeIV!!),
            PorterDuff.Mode.SRC_ATOP
        )

        reservationIV!!.setColorFilter(
            colorIcon(this, R.color.colorGray, R.drawable.table, reservationIV!!),
            PorterDuff.Mode.SRC_ATOP
        )


        favoriteIV!!.setColorFilter(
            colorIcon(this, R.color.colorGray, R.drawable.favorite, favoriteIV!!),
            PorterDuff.Mode.SRC_ATOP
        )

        accountIV!!.setColorFilter(
            colorIcon(this, R.color.colorGray, R.drawable.my_account, accountIV!!),
            PorterDuff.Mode.SRC_ATOP
        )

        homeBottomTV!!.setTextColor(Color.parseColor("#ACACAC"))

        reservationBottomTV!!.setTextColor(Color.parseColor("#ACACAC"))

        favoriteBottomTV!!.setTextColor(Color.parseColor("#ACACAC"))

        accountPassBottomTV!!.setTextColor(Color.parseColor("#ACACAC"))
    }

    override fun fragmentChange(fragment: String?, bundle: Bundle?) {
        if (fragment!!.equals("Track")) {
            val activityFragment: Fragment =
                OrderMapDetails()
            activityFragment.arguments = bundle
            loadFragment(activityFragment, "Track")
        }else if (fragment!!.equals("Terms")) {
            val activityFragment: Fragment = TeamsAndConditionsFragment()
            activityFragment.arguments = bundle
            loadFragment(activityFragment, "Terms")
            //showToast(this,"Coming Soon")
        }else if (fragment!!.equals("Offers")){
            val activityFragment: Fragment = OffersFragment()
            loadFragment(activityFragment, getString(R.string.Offers))
        }else if (fragment!!.equals("Table Booking Lists")) {
            val activityFragment: Fragment = OrderTableHistoryActivity()
            loadFragment(activityFragment, "Table Booking Lists")
            //showToast(this,"Coming Soon")
        } else if (fragment!!.equals("View Menu")) {
            val activityFragment: Fragment = ViewMenuFragment()
            activityFragment.arguments = bundle
            loadFragment(activityFragment, "View Menu")
        } else if (fragment!!.equals("Total Pay")) {
            startActivity(Intent(this, PaymentTotalActivity::class.java))
        } else if (fragment!!.equals("Review Page")) {
            val activityFragment: Fragment = ReviewStatusFragment()
            activityFragment.arguments = bundle
            loadFragment(activityFragment, "Review Page")
        } else if (fragment!!.equals("Member Details")) {
            val activityFragment: Fragment = MemberDetailsFragment()
            activityFragment.arguments = bundle
            loadFragment(activityFragment, "Member Details")
        } else if (fragment!!.equals("Rererrals")) {
            val activityFragment: Fragment = ReferralActivity()
            loadFragment(activityFragment, "Rererrals")
        } else if (fragment!!.equals("My Orders")) {
            val activityFragment: Fragment = OrderHistoryActivity()
            loadFragment(activityFragment, "My Orders")
        } else if (fragment!!.equals("Payment")) {
            val activityFragment: Fragment = SaveCardFragment()
            loadFragment(activityFragment, "Payment")
        } else if (fragment!!.equals("View Order")) {
            val activityFragment: Fragment =
                OrderBillDetailsActivity()
            activityFragment.arguments = bundle
            loadFragment(activityFragment, "View Order")
        } else if (fragment!!.equals("SearchActivity")) {
            //startActivity(Intent(this, SearchInnerActivity::class.java))

            var extras = intent.extras

            var value: String = ""
            if (extras != null) {
                value = extras.getString("Title").toString();
            }

            var intent = Intent(this, SearchInnerActivity::class.java)

            intent.putExtras(bundle!!)

            startActivity(intent)
        } else if (fragment!!.equals("DetailItemAddPageActivity")) {

            var extras = intent.extras

            var value: String = ""
            if (extras != null) {
                value = extras.getString("Title").toString();
            }

            var intent = Intent(this, DetailItemAddPageActivity::class.java)
            intent.putExtras(bundle!!)
            startActivity(intent)
        } else if (fragment!!.equals("Payment")) {
            startActivity(Intent(this, OrderDetailsActivity::class.java))
        } else if (fragment!!.equals("Help")) {
            /*val activityFragment: Fragment =
                HelpActivity()
            loadFragment(activityFragment, "Help")*/
        } else if (fragment!!.equals("Product List")) {
            val activityFragment: Fragment = ViewAllPagesFragment()
            activityFragment.arguments = bundle
            loadFragment(activityFragment, "Product List")
        } else if (fragment!!.equals("Detail Page")) {
            val activityFragment: Fragment = DetailPageActivity()
            activityFragment.arguments = bundle
            loadFragment(activityFragment, "Detail Page")
        } else if (fragment!!.equals("Table Details Page")) {
            val activityFragment: Fragment = DetailsTableBookingpageFragment()
            activityFragment.arguments = bundle
            loadFragment(activityFragment, "Table Details Page")
        } else if (fragment!!.equals("Details About")) {
            val activityFragment: Fragment = DetailsAboutFragment()
            activityFragment.arguments = bundle
            loadFragment(activityFragment, "Details About")
        } else if (fragment!!.equals("Table Booking")) {
            val activityFragment: Fragment = TableReservationFragment()
            activityFragment.arguments = bundle
            loadFragment(activityFragment, "Table Booking")
            //showToast(this,"Coming Soon")
        } else if (fragment!!.equals("Add Person")) {
            val activityFragment: Fragment = AddPersonActivity()
            activityFragment.arguments = bundle
            loadFragment(activityFragment, "Add Person")
        } else if (fragment!!.equals("Add Menu")) {
            val activityFragment: Fragment = AddMenuFragment()
            loadFragment(activityFragment, "Add Menu")
        } else if (fragment!!.equals("Logout")) {
            val mIentent = Intent(this, LoginActivity::class.java)
            mIentent.putExtra("page","Login");
            startActivity(mIentent)
            //startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else if (fragment!!.equals("Update Profile")) {
//            val activityFragment: Fragment = UpdateProfileFragment()
//            loadFragment(activityFragment, "Update Profile")
            startActivity(Intent(this, UpdateProfileFragment::class.java))
        } else if (fragment!!.equals("CardDetails")) {
            val activityFragment: Fragment = CardDetailsFragment()
            loadFragment(activityFragment, "CardDetails")
        } else if (fragment!!.equals("Filter Page")) {
            var extras = intent.extras
            var value: String = ""
            if (extras != null) {
                value = extras.getString("Title").toString();
            }
            var intent = Intent(this, FilterInnerPage::class.java)
            intent.putExtras(bundle!!)
            startActivity(intent)
        }else if (fragment!!.equals("Grocery")) {
            groceryIV!!.setColorFilter(
                colorIcon(this, R.color.colorGreen, R.drawable.grocery, groceryIV!!),
                PorterDuff.Mode.SRC_ATOP
            )

            groceryBottomTV!!.setTextColor(Color.parseColor("#7DC77D"))

            startActivity(Intent(this, DashBoardGrpceryActivity::class.java))

            AppType = "1"
        } else if (fragment!!.equals("Login")) {
            var intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)

            finish()
        } else if (fragment!!.equals("GroupList")) {
            val activityFragment: Fragment = AddGroupDetailsFragment()
            activityFragment.arguments = bundle
            loadFragment(activityFragment, "GroupList")
        } else if (fragment!!.equals("Share")) {
            try {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.setType("text/plain")
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Lieferin")

                shareIntent.putExtra(Intent.EXTRA_TEXT, ""+ bundle!!.get("link"))
                startActivity(Intent.createChooser(shareIntent, "choose one"))
            } catch (e: Exception) {
                //e.toString();
            }
        }else if (fragment!!.equals("Home")) {
            if(!dbHelper!!.getUserDetails().profilePicture.equals("")) {
                Picasso.with(this).load(dbHelper!!.getUserDetails().profilePicture).resize(450, 450)
                    .placeholder(R.drawable.place_holder).into(profileImg)

                Picasso.with(this).load(dbHelper!!.getUserDetails().profilePicture).resize(450, 450)
                    .placeholder(R.drawable.place_holder).into(userImage)
            }
            colorIconNormal()
            homeIV!!.setColorFilter(
                colorIcon(this, R.color.redColor, R.drawable.home, homeIV!!),
                PorterDuff.Mode.SRC_ATOP
            )

            homeBottomTV!!.setTextColor(Color.parseColor("#ec272b"))

            if (fm != null) {
                if (fm!!.backStackEntryCount != 0) {
                    supportFragmentManager.beginTransaction()
                        .remove(supportFragmentManager.findFragmentById(R.id.frame_container)!!)
                        .commit()
                }
                var i = 0
                while (i < fm!!.backStackEntryCount) {
                    fm!!.popBackStack()
                    ++i
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                backFragment()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun fragmentChangeView(fragment: String?, bundle: Bundle?) {

        /*if(fragment!!.equals("Cuisine")){
            val activityFragment: Fragment = FilterPriceFragment()
            filterFragment(activityFragment, "Cuisine")
        }else if(fragment!!.equals("More Filter")){
            val activityFragment: Fragment = FilterPriceFragment()
            filterFragment(activityFragment, "More Filter")
        }else if(fragment!!.equals("Rating")){
            val activityFragment: Fragment = RatingFragment()
            filterFragment(activityFragment, "Rating")
        }else if(fragment!!.equals("Cast per person")){
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

    override fun fragmentBack(fragment: String?) {
        backFragment()
    }

    override fun setOnFilter(id: String?) {
        Log.v("valueFilter","== "+Constant.valueStringFour)
        /*val fragment = MyBottomSheetFragment()
        fragment.show(supportFragmentManager, "TAG")*/

//        finish();
//        overridePendingTransition(0, 0);
//        startActivity(getIntent());
//        overridePendingTransition(0, 0);

        if (id!!.equals(getString(R.string.Filter))) {
            startActivity(Intent(this, FilterFragment::class.java))
        } else if (id!!.equals(getString(R.string.RatingValue))) {

            if (Constant.valueStringFilter == 4.0) {
                appbarLayout!!.setExpanded(false)

                locationRelation!!.visibility = View.GONE
            } else if (Constant.valueStringFilter == 0.0) {
                appbarLayout!!.setExpanded(true)

                locationRelation!!.visibility = View.VISIBLE
            }

            setupViewPager(viewPager!!);
            tabLayout!!.setupWithViewPager(viewPager);
        } else if (id!!.equals(getString(R.string.Cost))) {

            //startActivity(Intent(this, FilterFragment::class.java))

            var intent = Intent(this, FilterFragment::class.java)

            intent.putExtra("page", "Cost")
            startActivity(intent)

        } else if (id!!.equals(getString(R.string.Ratings))) {

            var intent = Intent(this, FilterFragment::class.java)

            intent.putExtra("page", "Rating")
            startActivity(intent)
        }else if (id!!.equals(getString(R.string.Cuisine))) {

            var intent = Intent(this, FilterFragment::class.java)

            intent.putExtra("page", "Cuisine")
            startActivity(intent)
        }
    }

    override fun setCancelFilter(id: String?) {
//        appbarLayout!!.setExpanded(true)
//
//        locationRelation!!.visibility = View.VISIBLE
//
//        Constant.valueString ="0"
//
//        Constant.valueStringFour = 0.0
//        setupViewPager(viewPager!!);
//        tabLayout!!.setupWithViewPager(viewPager);
    }

    override fun setOnFilterSide(id: String?) {

        filtersidePageAdapter!!.notifyDataSetChanged()
/*

        if(id!!.equals("Cuisine")){
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
        }
*/

    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
        if (mMaxScrollSize == 0) mMaxScrollSize = appBarLayout.totalScrollRange
        val percentage = Math.abs(i) * 100 / mMaxScrollSize

        Log.v("kkkk", "value" + percentage);
/*
        if (percentage <= 99) {


            Log.v("kkkk12","value"+percentage);
        }
        if (percentage >= 99) {
            Log.v("kkkk123","value"+percentage);
            locationRelation!!.visibility = View.GONE
        }*/
        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
            mIsAvatarShown = false

            locationRelation!!.visibility = View.GONE
            locationRelation!!.animate()
                .scaleY(0f).scaleX(0f)
                .start()
            //dumy!!.visibility = View.GONE


        }
        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
            mIsAvatarShown = true
            tabLayout!!.animate()
                .scaleY(1f).scaleX(1f)
                .start()
            locationRelation!!.visibility = View.VISIBLE

            locationRelation!!.animate()
                .scaleY(1f).scaleX(1f)
                .start()
            //tabLayout!!.visibility = View.VISIBLE

            //dumy!!.visibility = View.VISIBLE

        }
    }

    override fun onItem(type: String?, id: String?, view: String?, postion: Int) {
        if (id!!.equals(getString(R.string.View_All))) {
            val activityFragment: Fragment = ViewAllFragment()
            loadFragment(activityFragment, getString(R.string.View_All))

        } else {
            val activityFragment: Fragment = DetailPageActivity()
            loadFragment(activityFragment, getString(R.string.Detail_Page))
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
        dialog.setContentView(R.layout.menu_remove_list)
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
            dbHelper!!.deleteMenu()

            dbHelper!!.deleteCategory()

            dbHelper!!.deleteToppinsGroup()

            dbHelper!!.deleteToppins()

            dbHelper!!.deleteRest()

            if (fm != null) {
                if (fm!!.backStackEntryCount != 0) {
                    supportFragmentManager.beginTransaction()
                        .remove(supportFragmentManager.findFragmentById(R.id.frame_container)!!)
                        .commit()
                }
                var i = 0
                while (i < fm!!.backStackEntryCount) {
                    fm!!.popBackStack()
                    ++i
                }
            }
            dialog.cancel()
        })

        dialog.show()
    }

    fun showDialogOrderDelete(
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
        dialog.setContentView(R.layout.order_delete)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val loginText = dialog.findViewById<View>(R.id.loginText) as TextView
        loginText!!.typeface = fontStyle(this, "")

        val textImg = dialog.findViewById<View>(R.id.textImg) as TextView
        textImg!!.typeface = fontStyle(this, "")

        val tableLA2 = dialog.findViewById<View>(R.id.tableLA2) as TextView
        tableLA2!!.typeface = fontStyle(this, "SemiBold")

        val tableLA1 = dialog.findViewById<View>(R.id.tableLA1) as TextView
        tableLA1!!.typeface = fontStyle(this, "SemiBold")

        val closeIV = dialog.findViewById<View>(R.id.closeIV) as ImageView
        closeIV!!.setOnClickListener(View.OnClickListener { dialog.cancel() })

        tableLA2!!.setOnClickListener(View.OnClickListener { dialog.cancel() })

        tableLA1!!.setOnClickListener(View.OnClickListener {

            dbHelper!!.deleteMenu()

            dbHelper!!.deleteCategory()

            dbHelper!!.deleteToppinsGroup()

            dbHelper!!.deleteToppins()

            dbHelper!!.deleteRest()

            if (fm != null) {
                if (fm!!.backStackEntryCount != 0) {
                    supportFragmentManager.beginTransaction()
                        .remove(supportFragmentManager.findFragmentById(R.id.frame_container)!!)
                        .commit()
                }
                var i = 0
                while (i < fm!!.backStackEntryCount) {
                    fm!!.popBackStack()
                    ++i
                }
            }
            dialog.cancel()
        })


        dialog.show()
    }

    fun showDialogAddService(
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
        dialog.setContentView(R.layout.select_language_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val languageList = dialog.findViewById<View>(R.id.languageList) as RecyclerView

        languageAdapter = LanguageAdapter(context, adapterDetails, dialog)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        languageList!!.layoutManager = mLayoutManager

        languageList!!.itemAnimator!!.addDuration = 5000

        languageAdapter!!.setCallback(this)

        languageList!!.adapter = languageAdapter

        val loginText = dialog.findViewById<View>(R.id.loginText) as TextView
        loginText!!.typeface = fontStyle(this, "")

        val closeIV = dialog.findViewById<View>(R.id.closeIV) as ImageView
        closeIV!!.setOnClickListener(View.OnClickListener { dialog.cancel() })

        showLanguage()
        dialog.show()
    }

    override fun setOnDetail(id: String?, position: String?, positionValue: String?) {
        if (id!!.equals("English")) {
            val config = resources.configuration
            val locale = Locale("en")
            Locale.setDefault(locale)
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
            localstorageValue(this, "en")
        } else if (id!!.equals("Dutch")) {

            val config = resources.configuration
            val locale = Locale("de")
            Locale.setDefault(locale)
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
            localstorageValue(this, "de")
        } else {
            val config = resources.configuration
            val locale = Locale("pt")
            Locale.setDefault(locale)
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
            localstorageValue(this, "pt")
        }
        startActivity(Intent(this, DashBoardActivity::class.java))
    }


}
