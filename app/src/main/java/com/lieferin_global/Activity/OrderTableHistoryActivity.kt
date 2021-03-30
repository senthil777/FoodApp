package com.lieferin_global.Activity

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.lieferin_global.Fragment.NewOrderTableFragment
import com.lieferin_global.R
import com.lieferin_global.Utility.colorIcon
import com.lieferin_global.Utility.fontStyle
import com.google.android.material.tabs.TabLayout
import com.lieferin_global.Fragment.PostOrderTableFragment
import com.rentaloo.CallBack.CallBlacklisting

class OrderTableHistoryActivity : Fragment(),View.OnClickListener {

    var orders_back: ImageView? = null

    var orders_Title: TextView? = null

    var tableOrderHistory : TabLayout? = null

    var viewpagerOrderTableHistory : ViewPager? = null

    var rootView : View? = null

    var callBlacklisting: CallBlacklisting? = null

    var adapter: ViewPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_table_order_history, container, false)

        var header = rootView!!.findViewById(R.id.header) as LinearLayout

        header!!.setOnClickListener(this)

        orders_back = rootView!!.findViewById(R.id.orders_back)

        orders_back!!.setOnClickListener(this)

        orders_back!!.setColorFilter(
            colorIcon(activity!!,R.color.colorBlack,R.drawable.abc_ic_ab_back_material,orders_back!!),
            PorterDuff.Mode.SRC_ATOP)

        orders_Title = rootView!!.findViewById(R.id.orders_Title)

        orders_Title!!.typeface = fontStyle(activity!!,"")

        viewpagerOrderTableHistory = rootView!!.findViewById(R.id.viewpagerOrderTableHistory) as ViewPager


        adapter =
            ViewPagerAdapter(
                fragmentManager
            )

        adapter!!.addFragment(NewOrderTableFragment(), getString(R.string.Orders_New))

        adapter!!.addFragment(PostOrderTableFragment(), getString(R.string.Orders_Old))

        viewpagerOrderTableHistory!!.setAdapter(adapter)

        tableOrderHistory = rootView!!.findViewById(R.id.tableOrderHistory) as TabLayout
        tableOrderHistory!!.setupWithViewPager(viewpagerOrderTableHistory);

        val betweenSpace = 25

        val slidingTabStrip = tableOrderHistory!!.getChildAt(0) as ViewGroup

        for (i in 0 until slidingTabStrip.childCount - 1) {
            val v: View = slidingTabStrip.getChildAt(i)
            if (v is TextView) {
                (v as TextView).typeface = fontStyle(activity!!,"Light")

                (v as TextView).textSize =10.0f
            }
            val params = v.getLayoutParams() as ViewGroup.MarginLayoutParams
            params.rightMargin = betweenSpace



        }

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
                            activity!!,
                            ""
                        )
                    )
                }
            }
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }
    class ViewPagerAdapter(manager: FragmentManager?) : FragmentPagerAdapter(manager!!) {
        private val mFragmentList: MutableList<Fragment> =
            java.util.ArrayList()
        private val mFragmentTitleList: MutableList<String> =
            java.util.ArrayList()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size;
        }



        fun addFragment(fragment: Fragment, title: String) {
            val bundle = Bundle()
            bundle.putString("shopId", title)
            fragment.arguments = bundle
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        fun removeFragment(fragment: Fragment?, position: Int) {
            mFragmentList.removeAt(position)
            mFragmentTitleList.removeAt(position)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
        }
    }

    override fun onClick(view: View?) {
        when(view!!.id)
        {
            R.id.orders_back ->{
                callBlacklisting!!.fragmentBack("")
            }
        }
    }
}
