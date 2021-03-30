package com.lieferin_global.Adapter

import android.content.Context
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
import com.lieferin_global.Utility.colorIcon
import com.lieferin_global.Utility.fontStyle
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ReviewOrderDetailsAdapter(context: Context, adapterModels: List<AdapterModel>) : RecyclerView.Adapter<ReviewOrderDetailsAdapter.MyViewHolder>() {
    private val adapterModels: List<AdapterModel>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var orderId:TextView

        var orderSubDelivery:TextView

        var orderSubItem:TextView

        var orderSubItemValue:TextView

        var orderSubRating : TextView

        var starImage : ImageView

        var orderSubRatingValue : TextView

        var orderSubDescription : TextView

        var orderIdView : TextView

        var userName : TextView

        var userImg : CircleImageView

        var ratingLayout : LinearLayout


        init {

            ratingLayout = itemView.findViewById(R.id.ratingLayout)

            userName = itemView.findViewById(R.id.userName)

            userImg = itemView.findViewById(R.id.userImg)

            orderId = itemView.findViewById(R.id.orderId)

            orderSubDelivery = itemView.findViewById(R.id.orderSubDelivery)

            orderSubItem = itemView.findViewById(R.id.orderSubItem)

            orderSubItemValue = itemView.findViewById(R.id.orderSubItemValue)

            orderSubRating = itemView.findViewById(R.id.orderSubRating)

            starImage = itemView.findViewById(R.id.starImage)

            orderSubRatingValue = itemView.findViewById(R.id.orderSubRatingValue)

            orderSubDescription = itemView.findViewById(R.id.orderSubDescription)

            orderIdView = itemView.findViewById(R.id.orderIdView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.review_layout_view_user, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.userName.typeface = fontStyle(context,"")

        holder.userName.text = adapterModels.get(position).openTime

        if(adapterModels.get(position).noOfEmp.equals(""))
        {
            Picasso.with(context).load(R.drawable.place_holder).resize(450, 450)
                .placeholder(R.drawable.place_holder).into(holder.userImg)
        }else {

            Picasso.with(context).load(adapterModels.get(position).noOfEmp).resize(450, 450)
                .placeholder(R.drawable.place_holder).into(holder.userImg)
        }
        holder.orderId.typeface = fontStyle(context,"")

        holder.orderId.text = "Order #"+adapterModels.get(position).categoryName

        holder.orderSubDelivery.typeface = fontStyle(context,"")

        holder.orderSubDelivery.text = adapterModels.get(position).price

        holder.orderSubDescription.typeface = fontStyle(context,"Light")

        holder.orderSubDescription.text = adapterModels.get(position).closeTime

        holder.orderSubItemValue.typeface = fontStyle(context,"Light")

        holder.orderSubItemValue.text = adapterModels.get(position).offerPrice

        holder.orderSubRatingValue.typeface = fontStyle(context,"Light")

        holder.orderSubRatingValue.text = adapterModels.get(position).offerPercentage

        holder.orderSubItem.typeface = fontStyle(context,"")

        holder.orderIdView.typeface = fontStyle(context,"SemiBold")

        holder.orderSubRatingValue.typeface = fontStyle(context,"Light")

        holder.starImage.setColorFilter(colorIcon(context, R.color.colorWhite,R.drawable.star,holder.starImage))

        if(AppType!!.equals("0"))
        {
         holder.ratingLayout.setBackgroundResource(R.drawable.round_red)
        }else{
            holder.ratingLayout.setBackgroundResource(R.drawable.round_green)
        }

    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnMaterial(userId: AdapterModel?)

        fun setOnMaterialPage( id: String?)
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