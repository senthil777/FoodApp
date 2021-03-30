package com.lieferin_global.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.fontStyle

class FilterPricePageAdapter(context: Context, private val adapterModels: List<Product>) : RecyclerView.Adapter<FilterPricePageAdapter.MyViewHolder>() {
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    var favorite = 1
    //var month: String

    var selectedPosition = 0
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var dishListViewCheck: RadioButton

        var dishListViewCheckGreen: RadioButton

        var priceLayout : RelativeLayout

        init {

            dishListViewCheck = itemView.findViewById(R.id.dishListViewCheck)

            dishListViewCheckGreen = itemView.findViewById(R.id.dishListViewCheckGreen)

            priceLayout = itemView.findViewById(R.id.priceLayout)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.filter_side_price_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.dishListViewCheck.typeface =
            fontStyle(context, "")

        holder.dishListViewCheck.text = adapterModels.get(position).name

        holder.dishListViewCheckGreen.typeface =
            fontStyle(context, "")

        holder.dishListViewCheckGreen.text = adapterModels.get(position).name


        holder.dishListViewCheckGreen.setOnClickListener(View.OnClickListener {

            mCallback!!.setOnRadio(adapterModels.get(position).name,"") })

        holder.dishListViewCheck.setOnClickListener(View.OnClickListener {

            mCallback!!.setOnRadio(adapterModels.get(position).name,"") })

        if(adapterModels.get(position).menuId.equals("1"))
        {
            holder.dishListViewCheck.isChecked = true
            holder.dishListViewCheckGreen.isChecked = true

        }else{
            holder.dishListViewCheck.isChecked = false
            holder.dishListViewCheckGreen.isChecked = false
        }



        if(AppType.equals("0"))
        {
            holder.dishListViewCheck.visibility = View.VISIBLE

            holder.dishListViewCheckGreen.visibility = View.GONE
        }else{
            holder.dishListViewCheckGreen.visibility = View.VISIBLE

            holder.dishListViewCheck.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnRadio(userId: String?,position: String?)
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