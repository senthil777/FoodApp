package com.lieferin_global.Activity

import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.RestaurentPageAdapter
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.colorIcon
import com.lieferin_global.Utility.fontStyle
import com.lieferin_global.Utility.localgetUserInfo
import com.lieferin_global.Utility.localgetUserInfoSlash
import com.squareup.picasso.Picasso
import java.util.*

class StoreListActivity : AppCompatActivity(),RestaurentPageAdapter.CallbackDataAdapter,View.OnClickListener {

    var restTitle : TextView? =null

    var restDescription: TextView? = null

    var back: ImageView? = null

    var fav: ImageView? = null

    var backImg: ImageView? = null

    var list_Of_Items_RecyclerView: RecyclerView? = null

    var restaurentPageAdapter:RestaurentPageAdapter? =null

    var adapterBrand: MutableList<AdapterModel> = ArrayList()

    var adapterProduct: MutableList<Product> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_store_list)

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

        restTitle = findViewById(R.id.restTitle) as TextView

        restTitle!!.typeface = fontStyle(this,"SemiBold")

        restDescription = findViewById(R.id.restDescription) as TextView

        restDescription!!.typeface = fontStyle(this,"Light")

        back = findViewById(R.id.back);

        back!!.setOnClickListener(this)

        back!!.setColorFilter(colorIcon(this,R.color.colorWhite,R.drawable.abc_ic_ab_back_material,back!!), PorterDuff.Mode.SRC_ATOP)

        fav = findViewById(R.id.fav);

        fav!!.setColorFilter(colorIcon(this,R.color.colorWhite,R.drawable.search,fav!!), PorterDuff.Mode.SRC_ATOP)

        backImg = findViewById(R.id.backImg);

        Picasso.with(this).load(R.drawable.img_1).resize(450,450).into(backImg)

        list_Of_Items_RecyclerView = findViewById(R.id.list_Of_Items_RecyclerView) as RecyclerView

        restaurentPageAdapter = RestaurentPageAdapter(this,adapterBrand)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        list_Of_Items_RecyclerView!!.layoutManager = mLayoutManager

        list_Of_Items_RecyclerView!!.itemAnimator!!.addDuration = 5000

        restaurentPageAdapter!!.setCallback(this)

        list_Of_Items_RecyclerView!!.adapter = restaurentPageAdapter

        showDataNearBy()

    }

    fun showDataNearBy() {

        if (adapterBrand.size != 0) {
            adapterBrand.clear()
        }

        adapterModel = AdapterModel(0, "Na Thai Town", "Free Delivery", "4,5", ".  Asian ", ".  € 75.00", " 20 - 30 mins", "https://storage.googleapis.com/phx2-uat-wordpress-uploads/1/2019/08/Mega-Munch-640x360.jpg", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterBrand.add(adapterModel)

        adapterModel = AdapterModel(0, "Na Thai Town", "Free Delivery", "4,5", ".  Asian ", ".  € 75.00", " 20 - 30 mins", "https://i.ndtvimg.com/i/2016-06/tofu-with-rice_625x350_81466070125.jpg", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterBrand.add(adapterModel)

        restaurentPageAdapter!!.notifyDataSetChanged()
    }


    override fun setOnMaterial(userId: String?, view: View?) {

    }

    override fun onClick(view: View?) {
        when(view!!.id)
        {
            R.id.back->{
                finish()
            }
        }
    }
}
