package com.lieferin_global.Fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.lieferin_global.R
import com.lieferin_global.Utility.Constant
import com.lieferin_global.Utility.fontStyle
import com.jaygoo.widget.*


class CostPerPersonFragment : Fragment() {

    var rootView: View? = null

    var costPerPersonTextView : TextView? = null

    var costPerPriceTextView : TextView? = null

    var costPerMinPriceTextView : TextView? = null

    var costPerMaxPriceTextView : TextView? = null

    var sb_vertical_4: VerticalRangeSeekBar? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_cost_per_person, container, false)

        costPerPersonTextView = rootView!!.findViewById(R.id.costPerPersonTextView)

        costPerPersonTextView!!.typeface = fontStyle(activity!!,"")

        costPerMaxPriceTextView = rootView!!.findViewById(R.id.costPerMaxPriceTextView)

        costPerMaxPriceTextView!!.typeface = fontStyle(activity!!,"Light")

        costPerMinPriceTextView = rootView!!.findViewById(R.id.costPerMinPriceTextView)

        costPerMinPriceTextView!!.typeface = fontStyle(activity!!,"Light")

        costPerPriceTextView = rootView!!.findViewById(R.id.costPerPriceTextView)

        costPerPriceTextView!!.typeface = fontStyle(activity!!,"SemiBold")

        sb_vertical_4 = rootView!!.findViewById(R.id.sb_vertical_4)

        sb_vertical_4?.setIndicatorTextDecimalFormat("0")
        sb_vertical_4?.setProgress(0.0f, 100.0f)



        sb_vertical_4?.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onRangeChanged(rangeSeekBar: RangeSeekBar, leftValue: Float, rightValue: Float, isFromUser: Boolean) {
                changeSeekBarIndicator(rangeSeekBar.leftSeekBar, leftValue)
                changeSeekBarIndicator(rangeSeekBar.rightSeekBar, rightValue)

                var min : Int =0

                min =  0+leftValue.toInt() * 5

                Constant.valueStringMin = min

                var max : Int =0

                max =  rightValue.toInt() * 5

                Constant.valueStringMax = max

                costPerPriceTextView!!.text = "₹ "+min+" - "+max

                Constant.priceLow = ""+min

                Constant.priceHigh = ""+max
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

        min =  0+value.toInt() * 5

        seekbar.setIndicatorText(""+min)
        if(Constant.AppType!!.equals("0")) {
            FilterFragment.filterApply!!.setBackgroundResource(R.drawable.home_radius_button_gray)
        }else{
            FilterFragment.filterApply!!.setBackgroundResource(R.drawable.home_radius_button_green)
        }

        FilterFragment.filterApply!!.setTextColor(Color.parseColor("#ffffff"))
    }
}


