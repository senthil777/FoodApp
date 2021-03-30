package com.lieferin_global.Activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.HelpAdapter
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle
import java.util.ArrayList

class HelpActivity : Fragment() {

    var orderBill_Title: TextView? = null

    var help_Title: TextView? = null

    var helpRecyclerView: RecyclerView? = null

    var helpAdapter: HelpAdapter? = null

    internal lateinit var productModel: Product

    var adapterDetailChild: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    var rootView : View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for activity fragment
        rootView = inflater.inflate(R.layout.activity_help, container, false)
        

        orderBill_Title = rootView!!.findViewById(R.id.orderBill_Title) as TextView

        orderBill_Title!!.typeface = fontStyle(activity!!,"SemiBold")

        help_Title = rootView!!.findViewById(R.id.help_Title) as TextView

        help_Title!!.typeface = fontStyle(activity!!,"Light")

        helpRecyclerView = rootView!!.findViewById(R.id.helpRecyclerView) as RecyclerView

        helpAdapter = HelpAdapter(activity!!,adapterDetailChild)

        helpRecyclerView!!.setHasFixedSize(true)

        helpRecyclerView!!.setLayoutManager(
            LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        )
        helpRecyclerView!!.isNestedScrollingEnabled = false
        helpRecyclerView!!.setAdapter(helpAdapter!!)

        showDataProduct()

    return rootView
    }

    fun showDataProduct() {

        if (adapterDetailChild.size != 0) {
            adapterDetailChild.clear()
        }
        /*productModel = Product(R.drawable.img_1, "I haven't received activity order", "Lorem ipsum dolor sit amet, consectetur adipiscing elit laoreet consectetur..","€ 8.99","Add","4.0","40 Mins","200 for two","","","","","","","","",adapterProductList)
        adapterDetailChild.add(productModel)

        productModel = Product(R.drawable.img_2, "Items are missing form my order", "Lorem ipsum dolor sit amet, consectetur adipiscing elit laoreet consectetur..","€ 8.99","Add","4.0","40 Mins","200 for two","","","","","","","","",adapterProductList)
        adapterDetailChild.add(productModel)

        productModel = Product(R.drawable.img_3, "Items are different form what I ordered", "Lorem ipsum dolor sit amet, consectetur adipiscing elit laoreet consectetur..","€ 8.99","Add","4.0","40 Mins","200 for two","","","","","","","","",adapterProductList)
        adapterDetailChild.add(productModel)

        productModel = Product(R.drawable.img_1, "I have packaging or spillage issue with activity order", "Lorem ipsum dolor sit amet, consectetur adipiscing elit laoreet consectetur..","€ 8.99","Add","4.0","40 Mins","200 for two","","","","","","","","",adapterProductList)
        adapterDetailChild.add(productModel)

        productModel = Product(R.drawable.img_2, "I have received bad quality food", "Lorem ipsum dolor sit amet, consectetur adipiscing elit laoreet consectetur..","€ 8.99","Add","4.0","40 Mins","200 for two","","","","","","","","",adapterProductList)
        adapterDetailChild.add(productModel)

        productModel = Product(R.drawable.img_3, "The quantity of food is not adequate", "Lorem ipsum dolor sit amet, consectetur adipiscing elit laoreet consectetur..","€ 8.99","Add","4.0","40 Mins","200 for two","","","","","","","","",adapterProductList)
        adapterDetailChild.add(productModel)

        productModel = Product(R.drawable.img_3, "I have payment,refund and bill related queries for activity order", "Lorem ipsum dolor sit amet, consectetur adipiscing elit laoreet consectetur..","€ 8.99","Add","4.0","40 Mins","200 for two","","","","","","","","",adapterProductList)
        adapterDetailChild.add(productModel)


        productModel = Product(R.drawable.img_3, "I have coupon related queries for activity order", "Lorem ipsum dolor sit amet, consectetur adipiscing elit laoreet consectetur..","€ 8.99","Add","4.0","40 Mins","200 for two","","","","","","","","",adapterProductList)
        adapterDetailChild.add(productModel)
*/
        helpAdapter!!.notifyDataSetChanged()
    }
}
