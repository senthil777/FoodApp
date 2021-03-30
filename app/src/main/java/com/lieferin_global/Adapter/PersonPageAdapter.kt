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

class PersonPageAdapter(context: Context, private val adapterModels: List<AdapterModel>) : RecyclerView.Adapter<PersonPageAdapter.MyViewHolder>() {
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    var favorite = 1

    var selectedPosition = 0
    //var month: String
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var personText: TextView


        var calenderView: LinearLayout


        init {

            calenderView = view.findViewById<View>(R.id.calenderView) as LinearLayout

            personText = view.findViewById<View>(R.id.personText) as TextView



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.person_layout, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {



        holder.personText.setText(adapterModels[position].categoryName)



        holder.personText.setTypeface(fontStyle(context, "Bold"))



        holder.calenderView.setOnClickListener {
            selectedPosition = position

            mCallback!!.setOnPerson(adapterModels[position].categoryName)

        }

        if (position == selectedPosition) {
            holder.calenderView.setBackgroundResource(R.drawable.backgound_light)
            holder.personText.setTextColor(Color.parseColor("#ffffff"))

        } else {
            holder.calenderView.setBackgroundResource(R.drawable.home_cal_black)
            holder.personText.setTextColor(Color.parseColor("#484848"))

        }
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnPerson(userId: String?)

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