package com.lieferin_global.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle

class SlideMenu_item_Adpt(context: Context, adapterModels: List<AdapterModel>) : RecyclerView.Adapter<SlideMenu_item_Adpt.MyViewHolder>() {
    private val adapterModels: List<AdapterModel>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var slideMenuItemName: TextView
        var brandIcon: ImageView
        var cardView: LinearLayout

        init {
            slideMenuItemName = view.findViewById<View>(R.id.slideMenuItemName) as TextView
            brandIcon = view.findViewById<View>(R.id.iconImg) as ImageView
            cardView = view.findViewById<View>(R.id.layout) as LinearLayout
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.slide_menu, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.brandIcon.setImageResource(adapterModels[position].image)
        holder.slideMenuItemName.setTypeface(fontStyle(context, ""))
        holder.slideMenuItemName.setText(adapterModels[position].categoryName)

        holder.cardView.setOnClickListener {
            pos = position
            mCallback!!.setOnMaterial(adapterModels[position].categoryName)
        }
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnMaterial(id: String?)
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = FADE_DURATION.toLong()
        view.startAnimation(anim)
    }

    companion object {
        private const val FADE_DURATION = 2000
    }

    init {
        this.adapterModels = adapterModels
        /*adapterModels1 = adapterModels1
        month = month*/
        this.context = context
    }
}