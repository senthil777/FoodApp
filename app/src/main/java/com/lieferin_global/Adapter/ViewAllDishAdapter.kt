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
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.RoundedTransformation
import com.lieferin_global.Utility.fontStyle
import com.squareup.picasso.Picasso

class ViewAllDishAdapter(context: Context, adapterModels: List<AdapterModel>) : RecyclerView.Adapter<ViewAllDishAdapter.MyViewHolder>() {
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

        var closeLL : LinearLayout

        var dishTitle:TextView

        var dishDescription:TextView

        var dishTiming:TextView

        var dishOpen:TextView

        var dishPrice:TextView

        var ratingTV:TextView

        var listRR:RelativeLayout


        init {

            dishImg = itemView.findViewById(R.id.dishImg)

            dishTitle = itemView.findViewById(R.id.dishTitle)

            dishDescription = itemView.findViewById(R.id.dishDescription)

            dishTiming = itemView.findViewById(R.id.dishTiming)

            dishOpen = itemView.findViewById(R.id.dishOpen)

            dishPrice = itemView.findViewById(R.id.dishPrice)

            ratingTV = itemView.findViewById(R.id.ratingTV)

            listRR = itemView.findViewById(R.id.listRR)

            closeLL = itemView.findViewById(R.id.closeLL)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_of_restraunt, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.dishTitle.setTypeface(fontStyle(context, "SemiBold"))
        holder.dishTitle.setText(adapterModels[position].categoryName)

        holder.dishDescription.setTypeface(fontStyle(context, ""))

        holder.dishDescription.setText(adapterModels[position].price)

        holder.dishTiming.setTypeface(fontStyle(context, ""))

        holder.dishTiming.setText(adapterModels[position].offerPrice)

        holder.dishOpen.setTypeface(fontStyle(context, ""))

        holder.dishOpen.setText(adapterModels[position].offerPercentage)

        if(adapterModels[position].offerPercentage.equals("Closed"))
        {
            holder.closeLL.visibility = View.VISIBLE
        }else{
            holder.closeLL.visibility = View.GONE
        }

        holder.dishPrice.setTypeface(fontStyle(context, ""))

        holder.dishPrice.setText(adapterModels[position].categoryImage)

        holder.ratingTV.setTypeface(fontStyle(context, ""))

        if(adapterModels[position].offer.equals("null"))
        {
            holder.ratingTV.setText("0.0")
        }else {

            holder.ratingTV.setText(adapterModels[position].offer)
        }

        //holder.viewOffer.setTypeface(fontStyle(context, "SemiBold"))

        holder.closeLL.setOnClickListener(View.OnClickListener {  })

        var str = adapterModels[position].parlourAddress

        Log.v("===]]'''']"+str,"====")

        str = str!!.replace("]","").toString()
        val arrOfStr =
            str.split(",")

        Log.v("==]"+adapterModels.get(position).menuImages+"/"+adapterModels.get(position).openTime,"====")

        Picasso.with(context)
            .load(adapterModels.get(position).menuImages+"/"+adapterModels.get(position).openTime)
            .resize(450, 450).transform(RoundedTransformation(16,0)).placeholder(R.drawable.restaurant_placeholder)
            .into(holder.dishImg)
        //

        holder.listRR.setOnClickListener {

            if(!adapterModels[position].offerPercentage.equals("Closed")) {
                mCallback!!.setOnViewAll("" + adapterModels.get(position).categoryReferenceCode)
            }
        }

        if(AppType.equals("0"))
        {
            holder.ratingTV.setBackgroundResource(R.drawable.home_radius_button_orange)
        }else{
            holder.ratingTV.setBackgroundResource(R.drawable.home_radius_button_green)

            Picasso.with(context)
                .load(adapterModels.get(position).menuImages+"/"+adapterModels.get(position).openTime)
                .resize(450, 450).transform(RoundedTransformation(16,0)).placeholder(R.drawable.item_placeholder_geocery)
                .into(holder.dishImg)
        }
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnViewAll(id: String?)
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