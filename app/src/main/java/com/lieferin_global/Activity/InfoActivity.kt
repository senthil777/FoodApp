package com.lieferin_global.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle
import com.lieferin_global.Utility.localstorageUserInfo
import com.rd.PageIndicatorView


class InfoActivity : Activity(),View.OnClickListener {

    var logoImg: ImageView? = null

    private var viewPager: ViewPager? = null

    private var myViewPagerAdapter: MyViewPagerAdapter? = null

    var pageIndicatorView : PageIndicatorView? = null

    lateinit var layouts: IntArray

    var nextButton: LinearLayout? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        getString(R.string.Added_Item)

        //logoImg = findViewById(R.id.logoImg)


        viewPager = findViewById(R.id.view_pager) as ViewPager

        pageIndicatorView = findViewById(R.id.pageIndicatorView) as PageIndicatorView

        nextButton = findViewById(R.id.nextButton) as LinearLayout

        nextButton!!.setOnClickListener(this)

        layouts = intArrayOf(R.layout.intro_layout,R.layout.intro_layout,R.layout.intro_layout)
        localstorageUserInfo(this,"111","Logout","","","","","")

        pageIndicatorView!!.setCount(layouts.size)
        pageIndicatorView!!.setViewPager(viewPager)
        myViewPagerAdapter = MyViewPagerAdapter(this, layouts)
        viewPager!!.setAdapter(myViewPagerAdapter)
        viewPager!!.addOnPageChangeListener(viewPagerPageChangeListener)
    }


    class MyViewPagerAdapter(private final val myContext: Activity, var layouts: IntArray) :
        PagerAdapter() {
        private var layoutInflater: LayoutInflater? = null


        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater =
                myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
            val view: View = layoutInflater!!.inflate(layouts.get(position), container, false)

            when (position) {
                0 -> {

                    val introTitle = view.findViewById<View>(R.id.introTitle) as TextView

                    introTitle!!.typeface = fontStyle(myContext,"SemiBold")

                    introTitle.text = ""+myContext.getString(R.string.Info_Fast_Delivery)

                    val bgImg= view.findViewById<View>(R.id.bgImg) as ImageView

                    bgImg.setImageResource(R.drawable.delivery_scooter_white)

                    val introDescription = view.findViewById<View>(R.id.introDescription) as TextView

                    introDescription!!.typeface = fontStyle(myContext,"Light")

                }
                1 -> {
                    val introTitle = view.findViewById<View>(R.id.introTitle) as TextView

                    introTitle!!.typeface = fontStyle(myContext,"SemiBold")

                    introTitle.text = ""+myContext.getString(R.string.Info_Secure_Payment)

                    val bgImg= view.findViewById<View>(R.id.bgImg) as ImageView

                    bgImg.setImageResource(R.drawable.secure_payment)

                    val introDescription = view.findViewById<View>(R.id.introDescription) as TextView

                    introDescription!!.typeface = fontStyle(myContext,"Light")
                }
                2 -> {
                    val introTitle = view.findViewById<View>(R.id.introTitle) as TextView

                    introTitle!!.typeface = fontStyle(myContext,"SemiBold")

                    introTitle.text = ""+myContext.getString(R.string.Info_Special_Offers)

                    val bgImg= view.findViewById<View>(R.id.bgImg) as ImageView

                    bgImg.setImageResource(R.drawable.special_offers)

                    val introDescription = view.findViewById<View>(R.id.introDescription) as TextView

                    introDescription!!.typeface = fontStyle(myContext,"Light")
                }
                3 -> {

                }
                4 -> {


                }
            }
            container.addView(view)
            return view
        }


        override fun getCount(): Int {
            return layouts.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }

    var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object :
        ViewPager.OnPageChangeListener {
        override fun onPageSelected(position: Int) {
            //addBottomDots(position)
        // changing the next button text 'NEXT' / 'GOT IT'
        if (position == layouts.size - 1) { // last page. make button text to GOT IT
        } else { // still pages are left
        }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}
        override fun onPageScrollStateChanged(arg0: Int) {}
    }

    override fun onClick(view: View?) {

        when(view!!.id)
        {
            R.id.nextButton ->{

                val mIentent = Intent(this, DashBoardActivity::class.java)
                //mIentent.putExtra("page",Constant.Location);
                startActivity(mIentent)
            }
        }

    }
}
