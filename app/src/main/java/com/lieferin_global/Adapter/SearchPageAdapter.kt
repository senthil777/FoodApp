package com.lieferin_global.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle
import java.util.ArrayList

class SearchPageAdapter(context: Context, adapterModels: ArrayList<String>) : RecyclerView.Adapter<SearchPageAdapter.MyViewHolder>() {
    private val adapterModels: ArrayList<String>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    var favorite = 1
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemName: TextView

        var placeHolder: ImageView

        var frameLayout : LinearLayout

        init {
            itemName = view.findViewById<View>(R.id.itemName) as TextView

            placeHolder = view.findViewById<View>(R.id.placeHolder) as ImageView

            frameLayout = view.findViewById<View>(R.id.frameLayout) as LinearLayout

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.custom_adapter_row, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemName.setTypeface(fontStyle(context, ""))

        holder.itemName.setText(adapterModels[position])

        holder.frameLayout.setOnClickListener {
            mCallback!!.setOnMaterial(adapterModels[position]) }
        //viewHolder.subTitleProfile
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnMaterial(userId: String?)
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
        this.adapterModels = adapterModels
        //month = month
        this.context = context
    }
}