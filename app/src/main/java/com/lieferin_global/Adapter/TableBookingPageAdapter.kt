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
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.R
import com.squareup.picasso.Picasso

class TableBookingPageAdapter(context: Context, private val adapterModels: List<AdapterModel>) : RecyclerView.Adapter<TableBookingPageAdapter.MyViewHolder>() {
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    var favorite = 1
    //var month: String
    var itemView: View? = null

    var dateString: String? = ""

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        var tableSeat:ImageView


        init {

            tableSeat = view.findViewById(R.id.tableSeat) as ImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.table_list, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        if(!adapterModels.get(position).image.equals("")) {

            Picasso.with(context).load(adapterModels.get(position).image).into(holder.tableSeat)
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