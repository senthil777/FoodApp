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
import com.squareup.picasso.Picasso

class ViewAllListAdapter(context: Context, adapterModels: List<AdapterModel>) : RecyclerView.Adapter<ViewAllListAdapter.MyViewHolder>() {
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

        var viewIcon: ImageView

        var viewRating:TextView

        var viewStoreName:TextView

        var viewStoreDescription:TextView

        var viewRatingValue:TextView

        var viewDistance:TextView

        var viewPrice:TextView

        var viewOffer:TextView

        var cardView: CardView

        var viewFree: TextView

        var itemDistance : TextView


        init {

            viewStoreImg = itemView.findViewById(R.id.viewStoreImg)

            viewOffer = itemView.findViewById(R.id.viewOffer)

            viewIcon = itemView.findViewById(R.id.viewIcon)

            viewRating = itemView.findViewById(R.id.viewRating)

            viewStoreName = itemView.findViewById(R.id.viewStoreName)

            viewStoreDescription = itemView.findViewById(R.id.viewStoreDescription)

            viewRatingValue = itemView.findViewById(R.id.viewRatingValue)

            viewDistance = itemView.findViewById(R.id.viewDistance)

            viewStoreDescription = itemView.findViewById(R.id.viewStoreDescription)

            viewPrice = itemView.findViewById(R.id.viewPrice)

            cardView = itemView.findViewById(R.id.cardView)

            viewFree = itemView.findViewById(R.id.viewFree)

            itemDistance = itemView.findViewById(R.id.itemDistance)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_all_list, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.viewStoreName.setTypeface(fontStyle(context, ""))
        holder.viewStoreName.setText(adapterModels[position].categoryName)

        holder.viewDistance.setTypeface(fontStyle(context, "Light"))

        holder.viewPrice.setTypeface(fontStyle(context, "Light"))

        holder.viewRatingValue.setTypeface(fontStyle(context, "Light"))

        holder.viewStoreDescription.setTypeface(fontStyle(context, "Light"))

        holder.viewOffer.setTypeface(fontStyle(context, "SemiBold"))

        holder.viewFree.setTypeface(fontStyle(context, ""))

        holder.itemDistance.setTypeface(fontStyle(context, "Light"))

        if(!adapterModels[position].image.equals("")) {

            Picasso.with(context).load(adapterModels[position].image).resize(450, 450)
                .into(holder.viewStoreImg)
        }

        holder.cardView.setOnClickListener {

            mCallback!!.setOnMaterial("")
        }
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnMaterial(id: String?)
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