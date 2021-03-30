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
import com.lieferin_global.Utility.Constant
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.fontStyle

class FilterSideListPageAdapter(context: Context, private val adapterModels: List<Product>) : RecyclerView.Adapter<FilterSideListPageAdapter.MyViewHolder>() {
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    var favorite = 1
    //var month: String

    var selectedPosition = 0
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var dishListViewCheck: CheckBox

        var dishListViewCheckGreen : CheckBox


        init {

            dishListViewCheck = itemView.findViewById(R.id.dishListViewCheck)

            dishListViewCheckGreen = itemView.findViewById(R.id.dishListViewCheckGreen)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.filter_side_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        if(Constant.AppType.equals("0"))
        {
            holder.dishListViewCheck.visibility = View.VISIBLE

            holder.dishListViewCheckGreen.visibility = View.GONE
        }else{
            holder.dishListViewCheck.visibility = View.GONE

            holder.dishListViewCheckGreen.visibility = View.VISIBLE
        }

        holder.dishListViewCheck.typeface =
            fontStyle(context, "")

        holder.dishListViewCheckGreen.typeface =
            fontStyle(context, "")

        holder.dishListViewCheck.text = adapterModels.get(position).name

        holder.dishListViewCheckGreen.text = adapterModels.get(position).name

        holder.dishListViewCheck.setOnClickListener(View.OnClickListener {

            if(AppType.equals("0")) {
                if (holder.dishListViewCheck!!.isChecked == true) {

                    mCallback!!.setOnFavourite(adapterModels.get(position).name, "1")
                } else {
                    mCallback!!.setOnFavourite(adapterModels.get(position).name, "0")
                }
            }else{
                if (holder.dishListViewCheck!!.isChecked == true) {

                    mCallback!!.setOnFavourite(adapterModels.get(position).menuId, "1")
                } else {
                    mCallback!!.setOnFavourite(adapterModels.get(position).menuId, "0")
                }
            }
        })

        holder.dishListViewCheckGreen.setOnClickListener(View.OnClickListener {

            if(holder.dishListViewCheckGreen!!.isChecked == true) {
                mCallback!!.setOnFavourite(adapterModels.get(position).menuId, "1")
            }else{
                mCallback!!.setOnFavourite(adapterModels.get(position).menuId, "0")
            }
        })
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