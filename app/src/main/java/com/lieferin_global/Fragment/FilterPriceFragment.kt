package com.lieferin_global.Fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.FilterSideListPageAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList

import com.lieferin_global.R
import com.lieferin_global.Utility.Constant
import com.lieferin_global.Utility.Constant.adapterCategories10
import com.lieferin_global.Utility.Constant.priceHighToLow
import com.lieferin_global.Utility.Constant.priceLowToHigh
import com.lieferin_global.Utility.Constant.ratingLowToHigh
import com.lieferin_global.Utility.fontStyle
import com.lieferin_global.Utility.localgetUserInfo
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import org.json.JSONObject
import java.util.ArrayList

class FilterPriceFragment : Fragment(),ResponseListener,FilterSideListPageAdapter.CallbackDataAdapter,View.OnClickListener {

    var rootView: View? = null

    var menuRecyclerView : RecyclerView? = null

    var searchEditText : EditText? = null

    var dialogListPageAdapter : FilterSideListPageAdapter? = null

    var adapterTrendingSample: MutableList<AdapterModel> = ArrayList<AdapterModel>()

    var adapterCategories2: MutableList<Product> = ArrayList()

    var adapterCategories3: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    internal lateinit var adapterProduct: Product

    companion object {
        var adapterTrendingSample10: MutableList<AdapterModel> = ArrayList<AdapterModel>()
    }

    var dbHelper : DBHelper? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_filter_price, container, false)

        dbHelper = DBHelper(activity)

        menuRecyclerView = rootView!!.findViewById(R.id.cuisinesRecyclerView) as RecyclerView

        searchEditText = rootView!!.findViewById(R.id.searchEditText) as EditText

        searchEditText!!.typeface = fontStyle(activity!!,"")

        dialogListPageAdapter = FilterSideListPageAdapter(activity!!,adapterCategories2)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        menuRecyclerView!!.layoutManager = mLayoutManager

        menuRecyclerView!!.itemAnimator!!.addDuration = 5000

        dialogListPageAdapter!!.setCallback(this)

        menuRecyclerView!!.adapter = dialogListPageAdapter

        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)
        /*obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")*/
        obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)
        obj.put("distance", "50")
        Log.v("Json", "Value" + obj)
        RequestManager.setDashBoard(activity, obj, this);
        //showDataProduct1()

        //showDataProduct1()

        searchEditText!!.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

                if(p0!!.length != 0) {
                    searchValue(p0.toString())
                }else{
                    searchValue()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        return rootView
    }

    fun searchValue(value : String){
        if (adapterCategories2.size != 0) {
            adapterCategories2.clear()
        }

        for (i in 0 until adapterCategories3.size) {
            if(adapterCategories3.get(i).name!!.toLowerCase().contains(value.toLowerCase())) {
                adapterProduct = Product(
                    R.drawable.img_1,
                    "" + adapterCategories3.get(i).name,
                    "",
                    "",
                    "€2.00",
                    "1",
                    "1",
                    "200 for two",
                    "","","","","","","","","","",
                    adapterProductList
                )
                adapterCategories2.add(adapterProduct)
            }
        }
        dialogListPageAdapter!!.notifyDataSetChanged()
    }

    fun searchValue(){
        if (adapterCategories2.size != 0) {
            adapterCategories2.clear()
        }

        for (i in 0 until adapterCategories3.size) {

                adapterProduct = Product(
                    R.drawable.img_1,
                    "" + adapterCategories3.get(i).name,
                    "",
                    "",
                    "€2.00",
                    "1",
                    "1",
                    "200 for two",
                    "","","","","","","","","","",
                    adapterProductList
                )
                adapterCategories2.add(adapterProduct)

        }
        dialogListPageAdapter!!.notifyDataSetChanged()
    }

    fun showDataProduct1() {

        if (adapterCategories2.size != 0) {
            adapterCategories2.clear()
        }
        adapterProduct = Product(R.drawable.img_1, "Afghan", "","","€2.00","1","1","200 for two","","","","","","","","","","",adapterProductList)
        adapterCategories2.add(adapterProduct)

        adapterProduct = Product(R.drawable.img_2, "American", "","","€4.00","1","0","200 for two","","","","","","","","","","",adapterProductList)
        adapterCategories2.add(adapterProduct)

        adapterProduct = Product(R.drawable.img_3, "Andhra", "","","€2.00","1","0","200 for two","","","","","","","","","","",adapterProductList)
        adapterCategories2.add(adapterProduct)

        adapterProduct = Product(R.drawable.img_3, "Arabian", "","","€4.00","1","0","200 for two","","","","","","","","","","",adapterProductList)
        adapterCategories2.add(adapterProduct)

        adapterProduct = Product(R.drawable.img_3, "Asian", "","","€0.00","1","0","200 for two","","","","","","","","","","",adapterProductList)
        adapterCategories2.add(adapterProduct)

        adapterProduct = Product(R.drawable.img_3, "BBQ", "","","€4.00","1","0","200 for two","","","","","","","","","","",adapterProductList)
        adapterCategories2.add(adapterProduct)

        adapterProduct = Product(R.drawable.img_3, "Bakery", "","","€0.00","1","0","200 for two","","","","","","","","","","",adapterProductList)
        adapterCategories2.add(adapterProduct)

        dialogListPageAdapter!!.notifyDataSetChanged()
    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {
        if(responseObj != null) {
            if (requestType == Constant.MEMBER_getUserRestaurantDashBoard_URL_RQ) {
                if ((responseObj as BaseRS).status.equals("3024")) {
                    if (adapterCategories2.size != 0) {
                        adapterCategories2.clear()
                    }
                    for (i in 0 until (responseObj as BaseRS).fetchData!!.cuisionRestaurantList!!.size) {
                        adapterProduct = Product(R.drawable.img_1, ""+(responseObj as BaseRS).fetchData!!.cuisionRestaurantList!!.get(i).cuisionName, "","","€2.00","1","1","200 for two","","","","","","","","","","",adapterProductList)
                        adapterCategories2.add(adapterProduct)
                    }

                    for (i in 0 until (responseObj as BaseRS).fetchData!!.cuisionRestaurantList!!.size) {
                        adapterProduct = Product(R.drawable.img_1, ""+(responseObj as BaseRS).fetchData!!.cuisionRestaurantList!!.get(i).cuisionName, "","","€2.00","1","1","200 for two","","","","","","","","","","",adapterProductList)
                        adapterCategories3.add(adapterProduct)
                    }
                    dialogListPageAdapter!!.notifyDataSetChanged()
                }
            }
        }
    }

    override fun setOnRadio(userId: String?, position: String?) {






    }

    override fun setOnFavourite(isFav: String?, id: String?) {

        if(id!!.equals("1")) {
            adapterProduct = Product(
                R.drawable.img_1,
                "" + isFav,
                "",
                "",
                "€2.00",
                "1",
                "1",
                "200 for two",
                "",
                "","","","","","","","","",adapterProductList
            )
            adapterCategories10.add(adapterProduct)
        }else{

            for (i in 0 until adapterCategories10.size) {

                if(adapterCategories10.get(i).name.equals(isFav))
                {
                    adapterCategories10.removeAt(i)
                }
            }
        }

        if(adapterCategories10.size != 0)
        {
            if(Constant.AppType!!.equals("0")) {
                FilterFragment.filterApply!!.setBackgroundResource(R.drawable.home_radius_button_gray)
            }else{
                FilterFragment.filterApply!!.setBackgroundResource(R.drawable.home_radius_button_green)
            }

            FilterFragment.filterApply!!.setTextColor(Color.parseColor("#ffffff"))
        }else{
            FilterFragment.filterApply!!.setBackgroundResource(R.drawable.home_radius_button_gray_light)

            FilterFragment.filterApply!!.setTextColor(Color.parseColor("#ACACAC"))
        }
    }

    override fun onClick(view: View?) {

        when(view!!.id)
        {
        }
    }
}
