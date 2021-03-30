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
import com.lieferin_global.Model.ProductListView
import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle
import java.text.DecimalFormat

class MultipleListView(context: Context, private val adapterModels: List<ProductListView>,typeView:String) : RecyclerView.Adapter<MultipleListView.MyViewHolder>() {
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    var favorite = 1
    var typeView: String

    var selectedPosition = 0
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        var dishListViewCheck: CheckBox

        var allergensLinearLayout : LinearLayout

        var title1: TextView

        var title2: TextView

        var title3: TextView

        var title4: TextView

        var title5: TextView

        var title6: TextView

        var title7: TextView

        var title8: TextView


        init {


            dishListViewCheck = itemView.findViewById(R.id.dishListViewCheck)

            allergensLinearLayout = itemView.findViewById(R.id.allergensLinearLayout)

            title1 = itemView.findViewById(R.id.title1)

            title2 = itemView.findViewById(R.id.title2)

            title3 = itemView.findViewById(R.id.title3)

            title4 = itemView.findViewById(R.id.title4)

            title5 = itemView.findViewById(R.id.title5)

            title6 = itemView.findViewById(R.id.title6)

            title7 = itemView.findViewById(R.id.title7)

            title8 = itemView.findViewById(R.id.title8)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_list_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        if(typeView.equals("2"))
        {
            holder.dishListViewCheck.visibility = View.VISIBLE
        }else{
            holder.dishListViewCheck.visibility = View.GONE
        }
        holder.dishListViewCheck.typeface =
            fontStyle(context, "")


            holder.dishListViewCheck.text =
                adapterModels.get(position).name + " (€ " + DecimalFormat("##.##").format(
                    adapterModels.get(position).price!!.toDouble()
                ) + ")"


        holder.dishListViewCheck.typeface = fontStyle(context,"Light")
        /*if(adapterModels.get(position).offerPrice.equals("0"))
        {
            holder.dishListViewCheck!!.isChecked = false
        }else{
            holder.dishListViewCheck!!.isChecked = true
        }*/

        if(adapterModels.get(position).categoryId == null)
        {
            holder.dishListViewCheck.isChecked = false
        }else {
            if (adapterModels.get(position).categoryId!!.equals("1")) {
                holder.dishListViewCheck.isChecked = true
            } else if (adapterModels.get(position).categoryId!!.equals("0")) {
                holder.dishListViewCheck.isChecked = false
            }
        }

        holder.dishListViewCheck.setOnClickListener(View.OnClickListener {

            if(holder.dishListViewCheck.isChecked)
            {
                mCallback!!.setOnRadio(
                    "1",
                    "" + adapterModels.get(position).toppinsId,
                    adapterModels.get(position).toppinsGroupId
                )
            }else{
                mCallback!!.setOnRadio(
                    "0",
                    "" + adapterModels.get(position).toppinsId,
                    adapterModels.get(position).toppinsGroupId
                )
            }

            /*if(adapterModels.get(position).offerPrice.equals("1"))
            {
                *//*mCallback!!.setOnRadio(
                    "Decrease Price",
                    "" + position,
                    adapterModels.get(position).headerSub
                )*//*
            }else {

                *//*mCallback!!.setOnRadio(
                    "Add Price",
                    "" + position,
                    adapterModels.get(position).headerSub
                )*//*
            }*/
        })





    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnRadio(addPrice:String?,userId: String?,position: String?)
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
        this.typeView = typeView
        this.context = context
    }
}