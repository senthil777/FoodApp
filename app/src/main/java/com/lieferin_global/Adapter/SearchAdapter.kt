package com.lieferin_global.Adapter

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
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
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.RoundedTransformation
import com.lieferin_global.Utility.colorIcon
import com.lieferin_global.Utility.fontStyle
import com.squareup.picasso.Picasso
import org.jetbrains.anko.textColor

class SearchAdapter(context: Context, adapterModels: List<AdapterModel>) : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {
    private val adapterModels: List<AdapterModel>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var dishImg: ImageView

        var ratingTitle : TextView

        var dishTitle:TextView

        var dishDescription:TextView

        var dishTiming:TextView

        var dishOpen:TextView

        var dishPrice:TextView

        var ratingTV:TextView

        var viewAllOutlets : TextView

        var ratingImage:ImageView

        var dishArrow : ImageView

        var holeLayout : LinearLayout


        init {

            holeLayout = itemView.findViewById(R.id.holeLayout)

            dishImg = itemView.findViewById(R.id.dishImg)

            ratingTitle = itemView.findViewById(R.id.ratingTitle)

            dishTitle = itemView.findViewById(R.id.dishTitle)

            dishDescription = itemView.findViewById(R.id.dishDescription)

            dishTiming = itemView.findViewById(R.id.dishTiming)

            dishOpen = itemView.findViewById(R.id.dishOpen)

            ratingTV = itemView.findViewById(R.id.ratingTV)

            dishPrice = itemView.findViewById(R.id.dishPrice)

            ratingTV = itemView.findViewById(R.id.ratingTV)

            ratingImage = itemView.findViewById(R.id.ratingImage)

            dishArrow = itemView.findViewById(R.id.dishArrow)

            viewAllOutlets = itemView.findViewById(R.id.viewAllOutlets)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.search_adapter_list, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.dishTitle.setTypeface(fontStyle(context, "SemiBold"))
        holder.dishTitle.setText(adapterModels[position].categoryName)

        holder.dishDescription.setTypeface(fontStyle(context, "Light"))

        holder.dishDescription.setText(adapterModels[position].offerPrice)

        holder.dishTiming.setTypeface(fontStyle(context, "Light"))

        holder.dishOpen.setText(adapterModels[position].offerPercentage)

        holder.dishOpen.setTypeface(fontStyle(context, "Light"))

        holder.dishPrice.setText(adapterModels[position].categoryImage)

        holder.dishPrice.setTypeface(fontStyle(context, "Light"))

        holder.ratingTitle.setText(adapterModels[position].offer)

        holder.ratingTitle.setTypeface(fontStyle(context, "SemiBold"))

        holder.viewAllOutlets.setTypeface(fontStyle(context, "Light"))

        holder.ratingImage!!.setColorFilter(
            colorIcon(context, R.color.colorWhite, R.drawable.star, holder.ratingImage!!),
            PorterDuff.Mode.SRC_ATOP
        )


        if(AppType!!.equals("0")) {
            holder.dishArrow!!.setColorFilter(
                colorIcon(context, R.color.colorRed, R.drawable.arrow_down, holder.dishArrow!!),
                PorterDuff.Mode.SRC_ATOP
            )
            Picasso.with(context)
                .load(adapterModels.get(position).menuImages+"/"+adapterModels.get(position).openTime)
                .resize(450, 450).transform(RoundedTransformation(16,0)).placeholder(R.drawable.restaurant_placeholder)
                .into(holder.dishImg)
            holder.viewAllOutlets.textColor = Color.parseColor("#F44336")

            holder.ratingImage.setBackgroundResource(R.drawable.home_radius_button_orange)
        }else{
            holder.dishArrow!!.setColorFilter(
                colorIcon(context, R.color.colorGreen, R.drawable.arrow_down, holder.dishArrow!!),
                PorterDuff.Mode.SRC_ATOP
            )
            Picasso.with(context)
                .load(adapterModels.get(position).menuImages+"/"+adapterModels.get(position).openTime)
                .resize(450, 450).transform(RoundedTransformation(16,0)).placeholder(R.drawable.item_placeholder_geocery)
                .into(holder.dishImg)
            holder.viewAllOutlets.textColor = Color.parseColor("#7DC77D")

            holder.ratingImage.setBackgroundResource(R.drawable.home_radius_button_green)
        }
        //holder.viewOffer.setTypeface(fontStyle(context, "SemiBold"))


        holder.holeLayout.setOnClickListener {

            mCallback!!.setOnMaterial(adapterModels[position], true, ""+adapterModels[position].parlourAddress, position)
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