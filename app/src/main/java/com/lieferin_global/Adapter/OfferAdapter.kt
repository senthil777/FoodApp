package com.lieferin_global.Adapter

import android.content.Context
import android.graphics.PorterDuff
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
import com.lieferin_global.Utility.Constant
import com.lieferin_global.Utility.colorIcon
import com.lieferin_global.Utility.fontStyle
import com.squareup.picasso.Picasso

class OfferAdapter(context: Context, adapterModels: List<AdapterModel>) : RecyclerView.Adapter<OfferAdapter.MyViewHolder>() {
    private val adapterModels: List<AdapterModel>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var viewStoreImg: ImageView

        var cardView : CardView

        var viewRating:TextView

        var viewStoreName:TextView

        var viewStoreDescription:TextView

        var viewRatingValue:TextView

        var viewDistance:TextView

        var viewPrice:TextView

        var offerName:TextView

        var ratingImage : ImageView


        init {
            cardView = itemView.findViewById(R.id.cardView)

            ratingImage = itemView.findViewById(R.id.ratingImage)

            viewStoreImg = itemView.findViewById(R.id.viewStoreImg)

            offerName = itemView.findViewById(R.id.offerName)

            viewRating = itemView.findViewById(R.id.viewRating)

            viewStoreName = itemView.findViewById(R.id.viewStoreName)

            viewStoreDescription = itemView.findViewById(R.id.viewStoreDescription)

            viewRatingValue = itemView.findViewById(R.id.viewRatingValue)

            viewDistance = itemView.findViewById(R.id.viewDistance)

            viewStoreDescription = itemView.findViewById(R.id.viewStoreDescription)

            viewPrice = itemView.findViewById(R.id.viewPrice)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_offer_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.viewStoreName.setTypeface(fontStyle(context, ""))

        holder.viewStoreName.setText(adapterModels[position].categoryName)

        holder.viewDistance.setTypeface(fontStyle(context, "SemiBold"))

        holder.viewDistance.setText(adapterModels[position].offerPrice)

        holder.viewStoreDescription.setText(adapterModels[position].price)

        holder.viewPrice.setTypeface(fontStyle(context, "Light"))

        holder.viewPrice.setText(adapterModels[position].categoryName)

        holder.viewRatingValue.setTypeface(fontStyle(context, "SemiBold"))

        holder.viewRatingValue.setText(adapterModels[position].offer)

        holder.viewRating.setTypeface(fontStyle(context, "SemiBold"))

        holder.viewRating.setText(adapterModels[position].parlourRatingValue+"%")

        holder.viewStoreDescription.setTypeface(fontStyle(context, "Light"))

        holder.offerName.setTypeface(fontStyle(context, "SemiBold"))

        Picasso.with(context).load(adapterModels[position].menuImages+"/"+adapterModels[position].openTime).placeholder(R.drawable.restaurant_placeholder).resize(450,450).into(holder.viewStoreImg)

        holder.cardView.setOnClickListener {
            mCallback!!.setOnMaterial(adapterModels[position], true, ""+adapterModels[position].categoryReferenceCode, position)
        }

        if(Constant.AppType.equals("0"))
        {
            holder.ratingImage!!.setColorFilter(
                colorIcon(context, R.color.colorWhite, R.drawable.star, holder.ratingImage!!),
                PorterDuff.Mode.SRC_ATOP
            )
            holder.ratingImage.setBackgroundResource(R.drawable.round_red)

            holder.viewRating.setBackgroundResource(R.drawable.home_radius_button_orange)
            //holder.viewIcon.setBackgroundResource(R.drawable.home_radius_button_orange)

            //holder.viewIcon.setColorFilter(colorIcon(context,R.color.colorWhite,R.drawable.favorite,holder.viewIcon))
        }else{

            holder.ratingImage!!.setColorFilter(
                colorIcon(context, R.color.colorWhite, R.drawable.star, holder.ratingImage!!),
                PorterDuff.Mode.SRC_ATOP
            )
            holder.ratingImage.setBackgroundResource(R.drawable.round_green)

            holder.viewRating.setBackgroundResource(R.drawable.home_radius_button_green)
        }
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnMaterial(userId: AdapterModel?, isTrue: Boolean, id: String?, position: Int)
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