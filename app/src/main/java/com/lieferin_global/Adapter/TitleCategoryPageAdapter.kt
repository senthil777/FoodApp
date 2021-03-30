package com.lieferin_global.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle
import com.squareup.picasso.Picasso

class TitleCategoryPageAdapter(context: Context, adapterModels: List<AdapterModel>) : RecyclerView.Adapter<TitleCategoryPageAdapter.MyViewHolder>() {
    private val adapterModels: List<AdapterModel>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var cardLayout: LinearLayout

        var filterTV:TextView

        var arrowIV : ImageView

        var filterLL : RelativeLayout

        init {

            cardLayout = itemView.findViewById(R.id.cardLayout)

            arrowIV = itemView.findViewById(R.id.arrowIV)

            filterTV = itemView.findViewById(R.id.filterTV)

            filterLL = itemView.findViewById(R.id.filterLL)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.category_list_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.filterTV.setTypeface(fontStyle(context, "Light"))
        holder.filterTV.setText(adapterModels[position].categoryName)

        if(!adapterModels[position].offerPrice.equals("")) {

            Picasso.with(context).load(adapterModels[position].offerPrice)
                .placeholder(R.drawable.item_placeholder_geocery).into(holder.arrowIV)

        }
        Log.v("0000 "+adapterModels[position].offerPrice,"llllll")

        holder.cardLayout.setOnClickListener {

            mCallback!!.setOnCategoery( ""+adapterModels.get(position).price)
        }
        //holder.arrowIV.setColorFilter(colorIcon(context,R.color.colorWhite,adapterModels[position].image,holder.arrowIV))



        //holder.filterLL.setOnClickListener(View.OnClickListener { mCallback!!.setOnFilter(""+position) })
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnFilter(id: String?)
        fun setOnCategoery(id: String?)
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