package com.lieferin_global.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.ViewAllListAdapter
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product

import com.lieferin_global.R
import com.lieferin_global.Utility.colorIcon
import com.lieferin_global.Utility.fontStyle
import com.rentaloo.CallBack.CallBlacklisting
import java.util.ArrayList


class ViewAllFragment : Fragment(),View.OnClickListener {

    var favoriteRecyclerView: RecyclerView? = null

    var addressTextView: TextView? = null

    var tableReservation_back : ImageView? =null

    var mapIV : ImageView? = null

    var categoryItemRecyclerView: ViewAllListAdapter? = null

    var adapterFeature: MutableList<AdapterModel> = ArrayList()

    var adapterProduct: MutableList<Product> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    var callBlacklisting: CallBlacklisting? = null

    var rootView : View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_view_all, container, false)

        addressTextView = rootView!!.findViewById(R.id.addressTextView) as TextView

        addressTextView!!.typeface = fontStyle(activity!!,"Light")

        tableReservation_back = rootView!!.findViewById(R.id.tableReservation_back)

        tableReservation_back!!.setOnClickListener(this)

        mapIV = rootView!!.findViewById(R.id.mapIV) as ImageView

        mapIV!!.setColorFilter(colorIcon(activity!!,R.color.redColor,R.drawable.map_pin,mapIV!!))

        favoriteRecyclerView = rootView!!.findViewById(R.id.favoriteRecyclerView) as RecyclerView

        categoryItemRecyclerView = ViewAllListAdapter(activity!!, adapterFeature)
        favoriteRecyclerView!!.setHasFixedSize(true)
        favoriteRecyclerView!!.setLayoutManager(
            LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        )
        favoriteRecyclerView!!.isNestedScrollingEnabled =
            false
        favoriteRecyclerView!!.setAdapter(
            categoryItemRecyclerView
        )

        showDataFeature()

        return rootView
    }

    fun showDataFeature() {

        if (adapterFeature.size != 0) {
            adapterFeature.clear()
        }
        adapterModel = AdapterModel(R.drawable.img_4, "Thalapakatty Restaurant", "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNdVp58T_eVgzLEw-_dpw.730x390.jpg", "75 minutes", "Save 60 %", "1", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)

        adapterModel = AdapterModel(R.drawable.img_5, "SMS Multi Cusine Restaurant", "https://d1zgdcrdir5wgt.cloudfront.net/media/vehicle/images/mVNdVp58T_eVgzLEw-_dpw.730x390.jpg", "75 minutes", "Save 60 %", "1", " 425", "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "", "", "", 0, 0, 0,adapterProduct)
        adapterFeature.add(adapterModel)
        categoryItemRecyclerView!!.notifyDataSetChanged()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }


    override fun onClick(view: View?) {
        when(view!!.id)
        {
            R.id.tableReservation_back ->{

                callBlacklisting!!.fragmentBack("")
            }
        }
    }

}
