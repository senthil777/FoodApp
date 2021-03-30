package com.lieferin_global.Fragment

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.FilterSideListPageAdapter
import com.lieferin_global.Adapter.FiltersidePageAdapter
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
import com.lieferin_global.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rentaloo.CallBack.CallBlacklisting
import java.util.*


class MyBottomSheetFragment : BottomSheetDialogFragment(),
    FiltersidePageAdapter.CallbackDataAdapter {

    var filterListRecyclerView: RecyclerView? = null

    var filtersidePageAdapter: FiltersidePageAdapter? = null

    var rootView: View? = null

    var ft: FragmentTransaction? = null

    var adapterFeature: MutableList<AdapterModel> = ArrayList()

    var adapterProduct: MutableList<Product> = ArrayList()

    var adapterFamous: MutableList<AdapterModel> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    var menuRecyclerView: RecyclerView? = null

    var dialogListPageAdapter: FilterSideListPageAdapter? = null

    var adapterTrendingSample: MutableList<AdapterModel> = ArrayList<AdapterModel>()

    var adapterCategories2: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    internal lateinit var adapterProduct11: Product

    fun MyBottomSheetFragment() {}

    var callBlacklisting: CallBlacklisting? = null

    var cusinesLayout : LinearLayout? = null

    var priceLayout : LinearLayout? = null

    /* override fun onCreateView(
         inflater: LayoutInflater, container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {

         rootView = inflater.inflate(R.layout.fragment_add_menu, container, false)

         return rootView
     }*/


    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val bottomSheetDialog = dialog as BottomSheetDialog
        /*bottomSheetDialog.setContentView(R.layout.bottom_sheet_content)

        cusinesLayout = dialog!!.findViewById(R.id.cusinesLayout) as LinearLayout

        cusinesLayout!!.visibility = View.GONE

        priceLayout= dialog!!.findViewById(R.id.priceLayout) as LinearLayout

        priceLayout!!.visibility = View.VISIBLE*/

        filterListRecyclerView = dialog!!.findViewById(R.id.filterListRecyclerView) as RecyclerView

        filtersidePageAdapter = FiltersidePageAdapter(activity!!, adapterFeature)

        val mLayoutManager3: RecyclerView.LayoutManager = LinearLayoutManager(
            activity!!,
            LinearLayoutManager.VERTICAL, false
        )

        filterListRecyclerView!!.layoutManager = mLayoutManager3

        filterListRecyclerView!!.itemAnimator!!.addDuration = 5000

        filtersidePageAdapter!!.setCallback(this)

        filterListRecyclerView!!.adapter = filtersidePageAdapter!!
        showDataTrending1()

        menuRecyclerView = dialog!!.findViewById(R.id.cuisinesRecyclerView) as RecyclerView

        dialogListPageAdapter = FilterSideListPageAdapter(activity!!, adapterCategories2)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        menuRecyclerView!!.layoutManager = mLayoutManager

        menuRecyclerView!!.itemAnimator!!.addDuration = 5000

        //dialogListPageAdapter!!.setCallback(this)

        menuRecyclerView!!.adapter = dialogListPageAdapter

        showDataProduct1()


        val myDialog: BottomSheetDialog = dialog as BottomSheetDialog
        val dField =
            myDialog.javaClass.getDeclaredField("behavior") //This is the correct name of the variable in the BottomSheetDialog class
        dField.isAccessible = true
        val behavior = dField.get(dialog) as BottomSheetBehavior<*>
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }

    override fun setOnFilterSide(id: String?) {

        filtersidePageAdapter!!.notifyDataSetChanged()
        priceLayout!!.visibility = View.GONE
        cusinesLayout!!.visibility = View.VISIBLE
        //callBlacklisting!!.fragmentChangeView(id.toString(),null)


    }

    fun showDataTrending1() {

        if (adapterFeature.size != 0) {
            adapterFeature.clear()
        }
        adapterModel = AdapterModel(
            R.drawable.recommended,
            "Sort by",
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
            "Cuisine",
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
            "Rating",
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
            "Cast per person",
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
            "More Filter",
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

    fun showDataProduct1() {

        if (adapterCategories2.size != 0) {
            adapterCategories2.clear()
        }
        adapterProduct11 = Product(R.drawable.img_1, "Afghan", "","","€2.00","1","1","200 for two","","","","","","","","","","",adapterProductList)
        adapterCategories2.add(adapterProduct11)

        adapterProduct11 = Product(R.drawable.img_2, "American", "","","€4.00","1","0","200 for two","","","","","","","","","","",adapterProductList)
        adapterCategories2.add(adapterProduct11)

        adapterProduct11 = Product(R.drawable.img_3, "Andhra", "","","€2.00","1","0","200 for two","","","","","","","","","","",adapterProductList)
        adapterCategories2.add(adapterProduct11)

        adapterProduct11 = Product(R.drawable.img_3, "Arabian", "","","€4.00","1","0","200 for two","","","","","","","","","","",adapterProductList)
        adapterCategories2.add(adapterProduct11)

        adapterProduct11 = Product(R.drawable.img_3, "Asian", "","","€0.00","1","0","200 for two","","","","","","","","","","",adapterProductList)
        adapterCategories2.add(adapterProduct11)

        adapterProduct11 = Product(R.drawable.img_3, "BBQ", "","","€4.00","1","0","200 for two","","","","","","","","","","",adapterProductList)
        adapterCategories2.add(adapterProduct11)

        adapterProduct11 = Product(R.drawable.img_3, "Bakery", "","","€0.00","1","0","200 for two","","","","","","","","","","",adapterProductList)
        adapterCategories2.add(adapterProduct11)

        dialogListPageAdapter!!.notifyDataSetChanged()
    }
}