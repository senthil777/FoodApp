package com.lieferin_global.Adapter

import android.content.Context
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
import com.lieferin_global.Utility.fontStyle
import com.lieferin_global.Utility.localgetUserInfo
import com.squareup.picasso.Picasso

class CouponListAdapter(context: Context, adapterModels: List<AdapterModel>) : RecyclerView.Adapter<CouponListAdapter.MyViewHolder>() {
    private val adapterModels: List<AdapterModel>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var applyTV: TextView

        var couponName:TextView

        var offerTV: TextView

        var offerDescription:TextView


        init {

            applyTV = itemView.findViewById(R.id.applyTV)

            couponName = itemView.findViewById(R.id.couponName)

            offerTV = itemView.findViewById(R.id.offerTV)

            offerDescription = itemView.findViewById(R.id.offerDescription)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.appliy_coupon_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.couponName.setTypeface(fontStyle(context, "SemiBold"))

        holder.couponName.setText(adapterModels[position].categoryName)

        holder.applyTV.setTypeface(fontStyle(context, "SemiBold"))

        holder.applyTV.setOnClickListener(View.OnClickListener {

            mCallback!!.setOnFav(""+adapterModels.get(position).categoryName)
        })

        if(adapterModels[position].offerPercentage.equals("2"))
        {
            holder.offerTV.setText("Get "+adapterModels[position].price+"% off ")
        }else{
            holder.offerTV.setText("Get € "+adapterModels[position].price+" off ")
        }

        holder.offerTV.setTypeface(fontStyle(context, "SemiBold"))



        holder.offerDescription.setTypeface(fontStyle(context, "Light"))

        holder.offerDescription.setText(adapterModels[position].offerPrice)



    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnFav(id: String?)
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