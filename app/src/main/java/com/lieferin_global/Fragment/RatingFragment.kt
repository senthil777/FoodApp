package com.lieferin_global.Fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.lieferin_global.R
import com.lieferin_global.Utility.Constant
import com.lieferin_global.Utility.fontStyle
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import com.jaygoo.widget.SeekBar
import com.jaygoo.widget.VerticalRangeSeekBar

class RatingFragment : Fragment() {

    var rootView: View? = null

    var costPerPersonTextView : TextView? = null

    var costPerPriceTextView : TextView? = null

    var sb_vertical_6 : VerticalRangeSeekBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_rating, container, false)

        costPerPersonTextView = rootView!!.findViewById(R.id.costPerPersonTextView)

        costPerPersonTextView!!.typeface = fontStyle(activity!!,"")

        costPerPriceTextView = rootView!!.findViewById(R.id.costPerPriceTextView)

        costPerPriceTextView!!.typeface = fontStyle(activity!!,"SemiBold")

        sb_vertical_6 = rootView!!.findViewById(R.id.sb_vertical_6)

        sb_vertical_6?.setIndicatorTextDecimalFormat("0")
        sb_vertical_6?.setProgress(3.0f, 5.0f)

        sb_vertical_6?.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onRangeChanged(rangeSeekBar: RangeSeekBar, leftValue: Float, rightValue: Float, isFromUser: Boolean) {
                changeSeekBarIndicator(rangeSeekBar.leftSeekBar, leftValue)
                changeSeekBarIndicator(rangeSeekBar.rightSeekBar, rightValue)


            }

            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {

            }

            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {

            }

        })


        return rootView
    }

    private fun changeSeekBarIndicator(seekbar: SeekBar, value: Float){
        seekbar.showIndicator(true)
        Log.v("Value","== "+value)

        var min : Int =0

        min =  value.toInt()

        if(min == 25)
        {
            seekbar.setIndicatorText("3.5")

            Constant.valueStringFour =3.5

            if(Constant.AppType!!.equals("0")) {
                FilterFragment.filterApply!!.setBackgroundResource(R.drawable.home_radius_button_gray)
            }else{
                FilterFragment.filterApply!!.setBackgroundResource(R.drawable.home_radius_button_green)
            }
            //FilterFragment.filterApply!!.setBackgroundResource(R.drawable.home_radius_button_gray)

            FilterFragment.filterApply!!.setTextColor(Color.parseColor("#ffffff"))

        }else if(min == 50)
        {
            seekbar.setIndicatorText("4.0")

            Constant.valueStringFour =4.0

            if(Constant.AppType!!.equals("0")) {
                FilterFragment.filterApply!!.setBackgroundResource(R.drawable.home_radius_button_gray)
            }else{
                FilterFragment.filterApply!!.setBackgroundResource(R.drawable.home_radius_button_green)
            }
            FilterFragment.filterApply!!.setTextColor(Color.parseColor("#ffffff"))

        }else if(min == 75)
        {
            seekbar.setIndicatorText("4.5")

            Constant.valueStringFour =4.5

            if(Constant.AppType!!.equals("0")) {
                FilterFragment.filterApply!!.setBackgroundResource(R.drawable.home_radius_button_gray)
            }else{
                FilterFragment.filterApply!!.setBackgroundResource(R.drawable.home_radius_button_green)
            }
            FilterFragment.filterApply!!.setTextColor(Color.parseColor("#ffffff"))


        }else if(min == 100)
        {
            seekbar.setIndicatorText("5.0")

            Constant.valueStringFour =5.0

            if(Constant.AppType!!.equals("0")) {
                FilterFragment.filterApply!!.setBackgroundResource(R.drawable.home_radius_button_gray)
            }else{
                FilterFragment.filterApply!!.setBackgroundResource(R.drawable.home_radius_button_green)
            }
            FilterFragment.filterApply!!.setTextColor(Color.parseColor("#ffffff"))


        }else if(min == 0){
            seekbar.setIndicatorText("3.0")

            //Constant.valueStringFour =3.0

            /*FilterFragment.filterApply!!.setBackgroundResource(R.drawable.home_radius_button_gray_light)

            FilterFragment.filterApply!!.setTextColor(Color.parseColor("#ACACAC"))*/


        }

    }

}
