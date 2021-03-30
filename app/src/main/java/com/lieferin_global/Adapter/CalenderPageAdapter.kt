package com.lieferin_global.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle

class CalenderPageAdapter(context: Context, private val adapterModels: List<AdapterModel>) : RecyclerView.Adapter<CalenderPageAdapter.MyViewHolder>() {
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    var favorite = 1

    var selectedPosition = 0
    //var month: String
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var monthName: TextView

        var monthDate:TextView

        var monthDay:TextView

        var calenderView: LinearLayout

        init {

            calenderView = view.findViewById<View>(R.id.calenderView) as LinearLayout

            monthName = view.findViewById<View>(R.id.monthName) as TextView

            monthDate = view.findViewById<View>(R.id.monthDate) as TextView

            monthDay = view.findViewById<View>(R.id.monthDay) as TextView

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.calender_layout, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.monthDate.setText(adapterModels[position].price)

        holder.monthName.setText(adapterModels[position].categoryName)

        holder.monthDay.setText(adapterModels[position].offerPrice)

        holder.monthDate.setTypeface(fontStyle(context, "Light"))

        holder.monthName.setTypeface(fontStyle(context, "Light"))

        holder.monthDay.setTypeface(fontStyle(context, "Light"))

        holder.calenderView.setOnClickListener {
            selectedPosition = position

            mCallback!!.setOnDetails(""+adapterModels[position].offerPercentage+"-"+adapterModels[position].categoryImage+"-"+adapterModels[position].price)

        }

        if(position == 0)
        {
            holder.monthDay.setText("Today")
        }else if(position == 1)
        {
            holder.monthDay.setText("Tomorrow")
        }

        if (position == selectedPosition) {
            holder.calenderView.setBackgroundResource(R.drawable.backgound_light)
            holder.monthDate.setTextColor(Color.parseColor("#ffffff"))
            holder.monthName.setTextColor(Color.parseColor("#ffffff"))
            holder.monthDay.setTextColor(Color.parseColor("#ffffff"))
        } else {
            holder.calenderView.setBackgroundResource(R.drawable.home_cal_black)
            holder.monthDate.setTextColor(Color.parseColor("#484848"))
            holder.monthName.setTextColor(Color.parseColor("#484848"))
            holder.monthDay.setTextColor(Color.parseColor("#ACACAC"))
        }
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnDetails(userId: String?)

    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = FADE_DURATION.toLong()
        view.startAnimation(anim)
    }

    fun getImageUri(inContext: Context, inImage: Bitmap?): Drawable {
        return BitmapDrawable(inContext.resources, inImage)
    }

    companion object {
        private const val FADE_DURATION = 2000
    }

    init {
        //month = month
        this.context = context
    }
}