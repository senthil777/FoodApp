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
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.R
import com.lieferin_global.Utility.Constant
import com.lieferin_global.Utility.fontStyle

class CardListViewAdapter(context: Context, private val adapterModels: List<AdapterModel>) : RecyclerView.Adapter<CardListViewAdapter.MyViewHolder>() {
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    var favorite = 1
    //var month: String
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {



        var cardList: LinearLayout

        var cardHolderName : TextView


        var cardName:TextView

        var cardImg:ImageView

        var cardNumber:TextView

        var cardSelect:ImageView

        var cardDelete : ImageView

        init {

            cardList = itemView!!.findViewById(R.id.cardList) as LinearLayout

            cardHolderName = itemView!!.findViewById(R.id.cardHolderName) as TextView

            cardName = itemView!!.findViewById(R.id.cardName) as TextView

            cardNumber = itemView!!.findViewById(R.id.cardNumber) as TextView

            cardImg = itemView!!.findViewById(R.id.cardImg) as ImageView

            cardSelect = itemView!!.findViewById(R.id.cardSelect) as ImageView

            cardDelete = itemView!!.findViewById(R.id.cardDelete) as ImageView

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardlist_viewlist, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {



        holder.cardName.typeface = fontStyle(context,"SemiBold")

        holder.cardName.text = adapterModels.get(position).categoryName

        holder.cardHolderName.typeface = fontStyle(context,"")

        holder.cardHolderName.text = adapterModels.get(position).offerPrice

        holder.cardNumber.typeface = fontStyle(context,"")

        holder.cardNumber.text = "XXXX XXXX XXXX "+adapterModels.get(position).price!!.substring(14)

        //Picasso.with(context).load(adapterModels.get(position).offerPrice).resize(80,40).into(holder.cardImg)

        if(adapterModels.get(position).offer!!.equals("0"))
        {
            holder.cardList.setBackgroundResource(R.drawable.round_corner_detail_gray)
        }else{

            if(Constant.AppType!!.equals("0")) {

                holder.cardList.setBackgroundResource(R.drawable.round_corner_detail_gray_corner)
            }else{

                holder.cardList.setBackgroundResource(R.drawable.round_corner_detail_green_corner)
            }

        }

        holder.cardList.setOnClickListener(View.OnClickListener {

            //holder.cardList.setBackgroundResource(R.drawable.round_corner_detail_gray_corner)

            mCallback!!.setOnFavourite(adapterModels.get(position),position)
        })

        holder.cardDelete.setOnClickListener(View.OnClickListener {

            mCallback!!.setOnCardDelete(adapterModels.get(position).categoryImage)
        })

    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnMaterial(userId: AdapterModel?)
        fun setOnFavourite(userId: AdapterModel?, id: Int?)

        fun setOnCardDelete(position: String?)

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


