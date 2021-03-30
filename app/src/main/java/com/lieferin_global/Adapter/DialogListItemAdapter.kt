package com.lieferin_global.Adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle

class DialogListItemAdapter(context: Context, private val adapterModels: List<AdapterModel>, private val dialog: Dialog, val PosValue: String) : RecyclerView.Adapter<DialogListItemAdapter.MyViewHolder>() {
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    var favorite = 1
    //var month: String
    var itemView: View? = null


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var titleText: TextView

        init {

            titleText = itemView.findViewById(R.id.titleText)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_dialog, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.titleText.typeface =
            fontStyle(context, "")

        holder.titleText.text = adapterModels.get(position).categoryName

        holder.titleText.setOnClickListener(View.OnClickListener { mCallback!!.setOnExtraItem(adapterModels.get(position).offerPrice,adapterModels.get(position).offerPercentage)

        dialog.cancel()})



    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnExtraItem(type: String?, id: String?)
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