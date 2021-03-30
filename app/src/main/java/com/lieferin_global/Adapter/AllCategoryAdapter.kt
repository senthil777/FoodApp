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

class AllCategoryAdapter(context: Context, adapterModels: List<AdapterModel>) : RecyclerView.Adapter<AllCategoryAdapter.MyViewHolder>() {
    private val adapterModels: List<AdapterModel>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imageProduct: ImageView

        var productName:TextView

        var categoryCardView : CardView


        init {

            imageProduct = itemView.findViewById(R.id.imageProduct)

            productName = itemView.findViewById(R.id.productName)

            categoryCardView = itemView.findViewById(R.id.categoryCardView)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.all_category_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.productName.setTypeface(fontStyle(context, ""))
        holder.productName.setText(adapterModels[position].categoryName)

        holder.categoryCardView.setOnClickListener(View.OnClickListener {

            mCallback!!.setOnFav(""+adapterModels.get(position).openTime)
        })

        if(!adapterModels[position].price!!.equals("")) {
            Picasso.with(context).load(adapterModels[position].price).resize(450, 450)
                .placeholder(R.drawable.item_placeholder_geocery).into(holder.imageProduct)
        }
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