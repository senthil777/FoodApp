package com.lieferin_global.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.SelectedTimePageAdapter
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList

import com.lieferin_global.R
import java.util.ArrayList


class TimeFragment : Fragment(),SelectedTimePageAdapter.CallbackDataAdapter {

    var rootView:View? =null

    var mAdapter2: SelectedTimePageAdapter? = null

    var adapterModelsView: MutableList<Product> = ArrayList()

    var adapterModelsViewList: MutableList<ProductList> = ArrayList()

    internal lateinit var adapterModel: Product

    var myInt : String? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_time, container, false)

        var calenderTimeRecyclerView= rootView!!.findViewById(R.id.timeRecyclerView) as RecyclerView

        mAdapter2 = SelectedTimePageAdapter(activity!!, adapterModelsView)

        calenderTimeRecyclerView.layoutManager = GridLayoutManager(activity!!,3)

        mAdapter2!!.setCallback(this)

        calenderTimeRecyclerView.adapter = mAdapter2

        calenderTimeRecyclerView.isNestedScrollingEnabled = false


        val bundle = this.arguments
        if (bundle != null) {
            myInt = bundle.getString("shopId")
        }

        if(myInt.equals("Breakfast"))
        {
            showDataTime()
        }else if(myInt.equals("Lunch"))
        {
            showDataTime1()
        }else if(myInt.equals("Dinner"))
        {
            showDataTime2()
        }

        return rootView
    }

    fun showDataTime() {
        if (adapterModelsView != null) {
            adapterModelsView.clear()
        }
        adapterModel = Product(
            0,
            "9:00 AM",
            "4 Table remaining",
            "THU",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram","","","","","","","","","","",adapterModelsViewList)
        adapterModelsView.add(adapterModel)
        adapterModel = Product(
            0,
            "9:30 AM",
            "Unavailable",
            "THU",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram","","","","","","","","","","",adapterModelsViewList)
        adapterModelsView.add(adapterModel)
        adapterModel = Product(
            0,
            "10:00 AM",
            "2 Table remaining",
            "THU",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram","","","","","","","","","","",adapterModelsViewList
        )
        adapterModelsView.add(adapterModel)
        adapterModel = Product(
            0,
            "10:30 AM",
            "2 Table remaining",
            "THU",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram","","","","","","","","","","",adapterModelsViewList
        )
        adapterModelsView.add(adapterModel)
        adapterModel = Product(
            0,
            "11:00 AM",
            "2 Table remaining",
            "THU",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram","","","","","","","","","","",adapterModelsViewList
        )
        adapterModelsView.add(adapterModel)
        adapterModel = Product(
            0,
            "11:30 AM",
            "Unavailable",
            "THU",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram","","","","","","","","","","",adapterModelsViewList
        )

        adapterModelsView.add(adapterModel)
        mAdapter2!!.notifyDataSetChanged()
    }

    fun showDataTime1() {
        if (adapterModelsView != null) {
            adapterModelsView.clear()
        }
        adapterModel = Product(
            0,
            "12:00 PM",
            "2 Table remaining",
            "THU",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram","","","","","","","","","","",adapterModelsViewList)
        adapterModelsView.add(adapterModel)
        adapterModel = Product(
            0,
            "12:30 PM",
            "Unavailable",
            "THU",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram","","","","","","","","","","",adapterModelsViewList)
        adapterModelsView.add(adapterModel)
        adapterModel = Product(
            0,
            "1:00 PM",
            "2 Table remaining",
            "THU",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram","","","","","","","","","","",adapterModelsViewList
        )
        adapterModelsView.add(adapterModel)
        adapterModel = Product(
            0,
            "1:30 PM",
            "2 Table remaining",
            "THU",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram","","","","","","","","","","",adapterModelsViewList
        )
        adapterModelsView.add(adapterModel)
        adapterModel = Product(
            0,
            "2:00 PM",
            "Unavailable",
            "THU",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram","","","","","","","","","","",adapterModelsViewList
        )
        adapterModelsView.add(adapterModel)
        adapterModel = Product(
            0,
            "2:30 PM",
            "2 Table remaining",
            "THU",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram","","","","","","","","","","",adapterModelsViewList
        )

        adapterModelsView.add(adapterModel)
        mAdapter2!!.notifyDataSetChanged()
    }

    fun showDataTime2() {
        if (adapterModelsView != null) {
            adapterModelsView.clear()
        }
        adapterModel = Product(
            0,
            "3:00 PM",
            "2 Table remaining",
            "THU",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram","","","","","","","","","","",adapterModelsViewList)
        adapterModelsView.add(adapterModel)
        adapterModel = Product(
            0,
            "3:30 PM",
            "Unavailable",
            "THU",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram","","","","","","","","","","",adapterModelsViewList)
        adapterModelsView.add(adapterModel)
        adapterModel = Product(
            0,
            "4:00 PM",
            "Unavailable",
            "THU",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram","","","","","","","","","","",adapterModelsViewList
        )
        adapterModelsView.add(adapterModel)
        adapterModel = Product(
            0,
            "4:30 PM",
            "2 Table remaining",
            "THU",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram","","","","","","","","","","",adapterModelsViewList
        )
        adapterModelsView.add(adapterModel)
        adapterModel = Product(
            0,
            "5:00 PM",
            "2 Table remaining",
            "THU",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram","","","","","","","","","","",adapterModelsViewList
        )
        adapterModelsView.add(adapterModel)
        adapterModel = Product(
            0,
            "5:30 PM",
            "2 Table remaining",
            "THU",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram","","","","","","","","","","",adapterModelsViewList
        )

        adapterModelsView.add(adapterModel)
        mAdapter2!!.notifyDataSetChanged()
    }

    override fun setOnMaterial(userId: String?) {
        mAdapter2!!.notifyDataSetChanged()
    }

    override fun setOnFavourite(isFav: String?, id: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
        }
    }
}
