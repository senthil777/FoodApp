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
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle

class SelectedTimePageAdapter(context: Context, private val adapterModels: List<Product>) : RecyclerView.Adapter<SelectedTimePageAdapter.MyViewHolder>() {
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    var favorite = 1
    //var month: String
    var itemView: View? = null

    var selectedPosition = 0

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var timeAppointment:TextView

        var unavailableAppointment : TextView

        var backLayout : LinearLayout


        init {

            timeAppointment = itemView!!.findViewById(R.id.timeAppointment) as TextView

            unavailableAppointment = itemView!!.findViewById(R.id.unavailableAppointment) as TextView

            backLayout = itemView!!.findViewById(R.id.backLayout) as LinearLayout

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.select_date, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.timeAppointment.typeface = fontStyle(context,"")

        holder.timeAppointment.text = adapterModels.get(position).name

        holder.unavailableAppointment.typeface = fontStyle(context,"Light")

        holder.unavailableAppointment.text = adapterModels.get(position).menuId+" Table remaining"

        holder.backLayout.setOnClickListener(View.OnClickListener {

            /*if(!adapterModels.get(position).menuId.equals("Unavailable")) {
                selectedPosition = position
                mCallback!!.setOnMaterial("")
            }*/

            selectedPosition = position

            //Log.v("","")
            try {
                mCallback!!.setOnFavourite(
                    "" + adapterModels.get(position).price,
                    adapterModels.get(position).menuId
                )
            }catch (e:IndexOutOfBoundsException){

            }
        })



        if (position == selectedPosition) {
            holder.backLayout.setBackgroundResource(R.drawable.backgound_light)
            holder.timeAppointment.setTextColor(Color.parseColor("#ffffff"))

            if(adapterModels.get(position).menuId.equals("Unavailable"))
            {
                holder.unavailableAppointment.visibility = View.VISIBLE
                holder.unavailableAppointment.textSize = 12.0f
                holder.backLayout.setBackgroundResource(R.drawable.backgound_gray)
                holder.timeAppointment.setTextColor(Color.parseColor("#CCCCCC"))
                holder.unavailableAppointment.setTextColor(Color.parseColor("#CCCCCC"))
            }else{
                holder.unavailableAppointment.visibility = View.VISIBLE
                holder.unavailableAppointment.textSize = 10.0f
                holder.backLayout.setBackgroundResource(R.drawable.backgound_light)
                holder.timeAppointment.setTextColor(Color.parseColor("#ffffff"))
                holder.unavailableAppointment.setTextColor(Color.parseColor("#ffffff"))
            }

        } else {
            holder.backLayout.setBackgroundResource(R.drawable.home_cal_black)
            holder.timeAppointment.setTextColor(Color.parseColor("#484848"))
            if(adapterModels.get(position).menuId.equals("Unavailable"))
            {
                holder.unavailableAppointment.visibility = View.VISIBLE
                holder.unavailableAppointment.textSize = 12.0f
                holder.backLayout.setBackgroundResource(R.drawable.backgound_gray)
                holder.timeAppointment.setTextColor(Color.parseColor("#CCCCCC"))
                holder.unavailableAppointment.setTextColor(Color.parseColor("#CCCCCC"))
            }else{
                holder.unavailableAppointment.visibility = View.VISIBLE
                holder.unavailableAppointment.textSize = 10.0f
                holder.unavailableAppointment.setTextColor(Color.parseColor("#7DC77D"))
                holder.backLayout.setBackgroundResource(R.drawable.home_cal_black)
                holder.timeAppointment.setTextColor(Color.parseColor("#484848"))
            }
        }
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnMaterial(userId: String?)
        fun setOnFavourite(isFav: String?, id: String?)

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