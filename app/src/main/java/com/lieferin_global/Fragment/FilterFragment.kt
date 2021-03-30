package com.lieferin_global.Fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Activity.DashBoardActivity
import com.lieferin_global.Activity.DashBoardGrpceryActivity
import com.lieferin_global.Adapter.FilterPricePageAdapter
import com.lieferin_global.Adapter.FilterSideListPageAdapter
import com.lieferin_global.Adapter.FiltersidePageAdapter
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
import com.lieferin_global.R
import com.lieferin_global.Utility.Constant
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.fontStyle
import com.lieferin_global.Utility.localgetUserInfo
import com.lieferin_global.Utility.localgetUserInfoSlash
import com.rentaloo.CallBack.CallBlacklisting
import java.util.*


class FilterFragment : AppCompatActivity(),FiltersidePageAdapter.CallbackDataAdapter,View.OnClickListener,FilterPricePageAdapter.CallbackDataAdapter {

    var filterListRecyclerView: RecyclerView? = null

    var priceRecyclerView : RecyclerView? = null

    var adapterProduct1: MutableList<Product> = ArrayList()

    var filtersidePageAdapter: FiltersidePageAdapter? = null

    var ft: FragmentTransaction? = null

    var adapterFeature: MutableList<AdapterModel> = ArrayList()

    var adapterProduct: MutableList<Product> = ArrayList()

    var adapterFamous: MutableList<AdapterModel> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    var menuRecyclerView: RecyclerView? = null

    var dialogListPageAdapter: FilterSideListPageAdapter? = null

    var filterPricePageAdapter: FilterPricePageAdapter? = null

    var adapterTrendingSample: MutableList<AdapterModel> = ArrayList<AdapterModel>()

    var adapterCategories2: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    internal lateinit var adapterProduct11: Product

    fun MyBottomSheetFragment() {}

    var callBlacklisting: CallBlacklisting? = null

    var cusinesLayout : LinearLayout? = null

    var priceLayout : LinearLayout? = null

    var rootView: View? = null

    var fragmentManager1: FragmentManager? = null

    var fm1: FragmentManager? = null

    var filterTV: TextView? = null

    var cancel : ImageView? = null

    var filterClearAll : TextView? = null

    //var filterApply : TextView? = null

    companion object {
         var  filterApply : TextView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().statusBarColor = this.resources.getColor(R.color.trans)
        setContentView(R.layout.bottom_sheet_content)
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
        filterTV = findViewById(R.id.filterTV) as TextView

        filterTV!!.typeface = fontStyle(this,"SemiBold")

        cancel = findViewById(R.id.cancel) as ImageView

        cancel!!.setOnClickListener(this)

        filterApply = findViewById(R.id.filterApply) as TextView

        filterApply!!.typeface = fontStyle(this,"SemiBold")

        filterApply!!.setOnClickListener(this)

        filterClearAll = findViewById(R.id.filterClearAll) as TextView

        filterClearAll!!.typeface = fontStyle(this,"Light")

        filterClearAll!!.setOnClickListener(this)

        if(Constant.AppType.equals("0"))
        {

            filterClearAll!!.setTextColor(Color.parseColor("#ec272b"))
        }else{
            filterClearAll!!.setTextColor(Color.parseColor("#7DC77D"))
        }

        filterListRecyclerView = findViewById(R.id.filterListRecyclerView) as RecyclerView

        filtersidePageAdapter = FiltersidePageAdapter(this, adapterFeature)

        val mLayoutManager3: RecyclerView.LayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )

        filterListRecyclerView!!.layoutManager = mLayoutManager3

        filterListRecyclerView!!.itemAnimator!!.addDuration = 5000

        filtersidePageAdapter!!.setCallback(this)

        filterListRecyclerView!!.adapter = filtersidePageAdapter!!

        showDataTrending1(""+getString(R.string.FilterFragmentGrocry_Rating_High_To_Low))

        priceRecyclerView = findViewById(R.id.priceRecyclerView) as RecyclerView

        filterPricePageAdapter = FilterPricePageAdapter(this,adapterCategories2)

        val mLayoutManager1: RecyclerView.LayoutManager = LinearLayoutManager(this)

        priceRecyclerView!!.layoutManager = mLayoutManager1

        priceRecyclerView!!.itemAnimator!!.addDuration = 5000

        filterPricePageAdapter!!.setCallback(this)

        priceRecyclerView!!.adapter = filterPricePageAdapter

        showDataProduct1()

        val extras = intent.extras
        val userName: String?
        if (extras != null) {
            userName = extras.getString("page")
            if (userName!!.equals("Rating")) {
                val activityFragment: Fragment = RatingFragment()
                filterFragment(activityFragment, "Rating")

                for (i in 0 until adapterFeature.size) {

                    if(adapterFeature.get(i).categoryName.equals("Rating"))
                    {
                        adapterFeature.get(i).offerPrice = "0"
                    }else{
                        adapterFeature.get(i).offerPrice = ""
                    }

                }
                }else if (userName!!.equals("Cost")) {
                val activityFragment: Fragment = CostPerPersonFragment()
                filterFragment(activityFragment, "Cast per person")

                for (i in 0 until adapterFeature.size) {

                    if(adapterFeature.get(i).categoryName.equals("Cast per person"))
                    {
                        adapterFeature.get(i).offerPrice = "0"
                    }else{
                        adapterFeature.get(i).offerPrice = ""
                    }

                }
            }else if (userName!!.equals("Cuisine")){
                val activityFragment: Fragment = FilterPriceFragment()
                filterFragment(activityFragment, "Cuisine")

                for (i in 0 until adapterFeature.size) {

                    if(adapterFeature.get(i).categoryName.equals("Cuisine"))
                    {
                        adapterFeature.get(i).offerPrice = "0"
                    }else{
                        adapterFeature.get(i).offerPrice = ""
                    }

                }
            }
        }


/*
        menuRecyclerView = findViewById(R.id.cuisinesRecyclerView) as RecyclerView

        dialogListPageAdapter = FilterSideListPageAdapter(this, adapterCategories2)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        menuRecyclerView!!.layoutManager = mLayoutManager

        menuRecyclerView!!.itemAnimator!!.addDuration = 5000

        //dialogListPageAdapter!!.setCallback(this)

        menuRecyclerView!!.adapter = dialogListPageAdapter

        showDataProduct1()*/

        filtersidePageAdapter!!.notifyDataSetChanged()

    }


    private fun filterFragment(fragment: Fragment, title: String) {
        fm1 = supportFragmentManager
        runOnUiThread {
            fragmentManager1 = supportFragmentManager
            val fragmentTransactionManager = fragmentManager1!!.beginTransaction()
            fragmentTransactionManager.replace(R.id.frame_container_filter, fragment)
            fragmentTransactionManager.addToBackStack(title)
            fragmentTransactionManager.commit()
        }
    }
    override fun setOnFilterSide(id: String?) {

        for (i in 0 until adapterFeature.size) {
            adapterFeature.get(i).offerPrice = ""
        }

        filtersidePageAdapter!!.notifyDataSetChanged()
        if(id!!.equals(getString(R.string.Cuisine))){
            val activityFragment: Fragment = FilterPriceFragment()
            filterFragment(activityFragment, "Cuisine")
        }else if(id!!.equals("More Filter")){
            val activityFragment: Fragment = FilterPriceFragment()
            filterFragment(activityFragment, "More Filter")
        }else if(id!!.equals(getString(R.string.Category))){
            val activityFragment: Fragment = CategoryListFragment()
            filterFragment(activityFragment, "Category")
        }
        else if(id!!.equals(getString(R.string.Rating))){
            val activityFragment: Fragment = RatingFragment()
            filterFragment(activityFragment, "Rating")
        }else if(id!!.equals("Price range")){
            if(AppType!!.equals("0")) {
                val activityFragment: Fragment = CostPerPersonFragment()
                filterFragment(activityFragment, "Cast per person")
            }else{
                val activityFragment: Fragment = CostPerPersonGroFragment()
                filterFragment(activityFragment, "Cast per person")
            }
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
    }

    fun showDataTrending1(value:String) {

        if (adapterFeature.size != 0) {
            adapterFeature.clear()
        }
        adapterModel = AdapterModel(
            R.drawable.recommended,
            ""+getString(R.string.Sort_by),
            ""+value,
            "0",
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

        if(AppType!!.equals("0")) {
            adapterModel = AdapterModel(
                R.drawable.recommended,
                ""+getString(R.string.Cuisine),
                "",
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
            adapterFeature.add(adapterModel)
        }else {

            adapterModel = AdapterModel(
                R.drawable.recommended,
                ""+getString(R.string.Category),
                "",
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
            adapterFeature.add(adapterModel)
        }

        adapterModel = AdapterModel(
            R.drawable.recommended,
            ""+getString(R.string.Rating),
            "",
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
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.recommended,
            "Price range",
            "",
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
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.recommended,
            "More Filter",
            "",
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
        //adapterFeature.add(adapterModel)

        filtersidePageAdapter!!.notifyDataSetChanged()
    }

    fun showDataProduct1() {

        if (adapterCategories2.size != 0) {
            adapterCategories2.clear()
        }
        adapterProduct11 = Product(R.drawable.img_1, "Popularity", "1","","€2.00","1","1","200 for two","","","","","","","","","","",adapterProductList)
        //adapterCategories2.add(adapterProduct11)

        adapterProduct11 = Product(R.drawable.img_2, ""+getString(R.string.FilterFragmentGrocry_Rating_High_To_Low), "1","","€4.00","1","0","200 for two","","","","","","","","","","",adapterProductList)
        adapterCategories2.add(adapterProduct11)

        adapterProduct11 = Product(R.drawable.img_3, "Delivery Time", "","","€2.00","1","0","200 for two","","","","","","","","","","",adapterProductList)
        //adapterCategories2.add(adapterProduct11)

        adapterProduct11 = Product(R.drawable.img_3, ""+getString(R.string.FilterFragmentGrocry_Low_To_High), "","","€4.00","1","0","200 for two","","","","","","","","","","",adapterProductList)
        adapterCategories2.add(adapterProduct11)

        adapterProduct11 = Product(R.drawable.img_3, ""+getString(R.string.FilterFragmentGrocry_High_To_Low), "","","€0.00","1","0","200 for two","","","","","","","","","","",adapterProductList)
        adapterCategories2.add(adapterProduct11)


        filterPricePageAdapter!!.notifyDataSetChanged()
    }

    override fun onClick(view: View?) {

        when(view!!.id)
        {
            R.id.cancel ->{

                finish()
            }

            R.id.filterClearAll ->{
                Constant.valueString =""
                finish()

                if(AppType!!.equals("0")) {
                    var intent = Intent(this, DashBoardActivity::class.java)

                    startActivity(intent)
                }else{
                    var intent = Intent(this, DashBoardGrpceryActivity::class.java)

                    startActivity(intent)
                }
            }

            R.id.filterApply ->{
                finish()
                if(AppType!!.equals("0")) {
                    var intent = Intent(this, DashBoardActivity::class.java)

                    intent.putExtra("page", "Filter")

                    startActivity(intent)
                }else{
                    var intent = Intent(this, DashBoardGrpceryActivity::class.java)

                    intent.putExtra("page", "Filter")

                    startActivity(intent)
                }
            }
        }

    }

    override fun setOnRadio(userId: String?, position: String?) {

        Log.v("Status"," == "+userId)

        if(userId!!.equals("Rating : High To Low"))
        {
            Constant.priceHighToLow = ""
            Constant.ratingLowToHigh = "1"
            Constant.priceLowToHigh = ""
        }else if(userId!!.equals("Cost : Low To High"))
        {
            Constant.priceHighToLow = ""
            Constant.ratingLowToHigh = ""
            Constant.priceLowToHigh = "1"
        }else if(userId!!.equals("Cost : High To Low"))
        {
            Constant.priceHighToLow = "1"
            Constant.ratingLowToHigh = ""
            Constant.priceLowToHigh = ""
        }


        for (i in 0 until adapterCategories2.size) {

            if(adapterCategories2.get(i).name.equals(userId)) {
                adapterCategories2.get(i).menuId = "1"
            }else{
                adapterCategories2.get(i).menuId = ""
            }

        }

        filterPricePageAdapter!!.notifyDataSetChanged()

        showDataTrending1(userId.toString())

        if(!userId.equals("Popularity"))
        {
            if(AppType!!.equals("0")) {
                filterApply!!.setBackgroundResource(R.drawable.home_radius_button_gray)
            }else{
                filterApply!!.setBackgroundResource(R.drawable.home_radius_button_green)
            }

            filterApply!!.setTextColor(Color.parseColor("#ffffff"))

        }else{
            filterApply!!.setBackgroundResource(R.drawable.home_radius_button_gray_light)

            filterApply!!.setTextColor(Color.parseColor("#ACACAC"))
        }


    }

    override fun setOnFavourite(isFav: String?, id: String?) {
    }
}
