package com.lieferin_global.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.MenuListPageAdapter
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList

import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle
import com.rentaloo.CallBack.CallBlacklisting
import java.util.ArrayList

class AddMenuFragment : Fragment(),MenuListPageAdapter.CallbackDataAdapter,View.OnClickListener {

    var rootView : View? = null

    var menuRecyclerView : RecyclerView? = null

    var dialogListPageAdapter : MenuListPageAdapter? = null

    var adapterTrendingSample: MutableList<AdapterModel> = ArrayList<AdapterModel>()

    var adapterCategories2: MutableList<Product> = ArrayList()

    var tableReservation_back : ImageView? =null

    var adapterCategoriesList: MutableList<ProductList> = ArrayList()

    internal lateinit var adapterProduct: Product

    var addMenuTV : TextView? = null

    fun AddPersonActivity() {}

    var callBlacklisting: CallBlacklisting? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_add_menu, container, false)

        var header = rootView!!.findViewById(R.id.header) as RelativeLayout

        header!!.setOnClickListener(this)

        tableReservation_back = rootView!!.findViewById(R.id.tableReservation_back) as ImageView

        tableReservation_back!!.setOnClickListener(this)

        menuRecyclerView = rootView!!.findViewById(R.id.menuRecyclerView) as RecyclerView

        dialogListPageAdapter = MenuListPageAdapter(activity!!,adapterCategories2)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        menuRecyclerView!!.layoutManager = mLayoutManager

        menuRecyclerView!!.itemAnimator!!.addDuration = 5000

        dialogListPageAdapter!!.setCallback(this)

        menuRecyclerView!!.adapter = dialogListPageAdapter

        addMenuTV = rootView!!.findViewById(R.id.addMenuTV)

        addMenuTV!!.typeface = fontStyle(activity!!,"SemiBold")

        addMenuTV!!.setOnClickListener(this)

        showDataProduct1()



        return rootView
    }


    fun showDataProduct1() {

        if (adapterCategories2.size != 0) {
            adapterCategories2.clear()
        }
        adapterProduct = Product(R.drawable.img_1, "Cocktail", "","","€2.00","1","1","200 for two","","","","","","","","","","",adapterCategoriesList)
        adapterCategories2.add(adapterProduct)

        adapterProduct = Product(R.drawable.img_2, "Coke / Diet Coke / Pepsi", "","","€4.00","1","0","200 for two","","","","","","","","","","",adapterCategoriesList)
        adapterCategories2.add(adapterProduct)

        adapterProduct = Product(R.drawable.img_3, "Sangria", "","","€2.00","1","0","200 for two","","","","","","","","","","",adapterCategoriesList)
        adapterCategories2.add(adapterProduct)

        adapterProduct = Product(R.drawable.img_3, "Red Bull", "","","€4.00","1","0","200 for two","","","","","","","","","","",adapterCategoriesList)
        adapterCategories2.add(adapterProduct)

        adapterProduct = Product(R.drawable.img_3, "Sprite / 7up / Fresh Lime", "","","€0.00","1","0","200 for two","","","","","","","","","","",adapterCategoriesList)
        adapterCategories2.add(adapterProduct)

        dialogListPageAdapter!!.notifyDataSetChanged()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }

    override fun setOnRadio(userId: String?, position: String?) {

    }

    override fun setOnFavourite(isFav: String?, id: String?) {

    }

    override fun onClick(view: View?) {

        when(view!!.id)
        {
            R.id.addMenuTV ->
            {
               callBlacklisting!!.fragmentChange("Payment",null)
            }

            R.id.tableReservation_back ->{
                callBlacklisting!!.fragmentBack("")
            }
        }

    }

}
