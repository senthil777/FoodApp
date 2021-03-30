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
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.R
import com.lieferin_global.Utility.colorIcon
import com.lieferin_global.Utility.fontStyle
import com.squareup.picasso.Picasso

class RestaurentPageAdapter(context: Context, private val adapterModels: List<AdapterModel>) : RecyclerView.Adapter<RestaurentPageAdapter.MyViewHolder>() {
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    var favorite = 1
    //var month: String
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var itemImg:ImageView

        var itemName:TextView

        var itemDelivery:TextView

        var itemRating:TextView

        var itemType: TextView

        var itemPrice: TextView

        var itemDistance: TextView

        var star: ImageView

        var cardView: CardView

        init {

            itemImg = itemView!!.findViewById(R.id.itemImg) as ImageView

            itemName = itemView!!.findViewById(R.id.itemName) as TextView

            itemDelivery = itemView!!.findViewById(R.id.itemDelivery) as TextView

            itemRating = itemView!!.findViewById(R.id.itemRating) as TextView

            itemType = itemView!!.findViewById(R.id.itemType) as TextView

            itemPrice = itemView!!.findViewById(R.id.itemPrice) as TextView

            itemDistance = itemView!!.findViewById(R.id.itemDistance) as TextView

            star = itemView!!.findViewById(R.id.star) as ImageView

            cardView = itemView!!.findViewById(R.id.cardView) as CardView

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        if(!adapterModels.get(position).parlourAddress.equals("")) {
            Picasso.with(context).load(adapterModels.get(position).parlourAddress).resize(450, 450)
                .into(holder.itemImg)
        }

        holder.star.setColorFilter(colorIcon(context,R.color.colorAccent,R.drawable.star,holder.star!!))

        holder.itemName.typeface = fontStyle(context,"SemiBold")

        holder.itemName.text = adapterModels.get(position).categoryName

        holder.itemDelivery.typeface = fontStyle(context,"")

        holder.itemDelivery.text = adapterModels.get(position).price

        holder.itemRating.typeface = fontStyle(context,"Light")

        holder.itemRating.text = adapterModels.get(position).offerPrice

        holder.itemType.typeface = fontStyle(context,"Light")

        holder.itemType.text = adapterModels.get(position).offerPercentage

        holder.itemPrice.typeface = fontStyle(context,"Light")

        holder.itemPrice.text = adapterModels.get(position).categoryImage

        holder.itemDistance.typeface = fontStyle(context,"Light")

        holder.itemDistance.text = adapterModels.get(position).offer

        holder.cardView.setOnClickListener(View.OnClickListener { mCallback!!.setOnMaterial("",holder.cardView) })



    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnMaterial(userId: String?,view: View?)


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