package com.lieferin_global.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.ProductListView
import com.lieferin_global.R
import com.lieferin_global.Utility.addIncreasePrice
import com.lieferin_global.Utility.addIncreasePriceHole
import com.lieferin_global.Utility.fontStyle

class CategoryListPageAdapter(context: Context, private val adapterModels: List<ProductListView>) : RecyclerView.Adapter<CategoryListPageAdapter.MyViewHolder>() {
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    var favorite = 1
    //var month: String
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var orderTitle: TextView

        var orderSubOrder : TextView

        var orderQty: TextView

        var orderPrice: TextView

        init {

            orderTitle = itemView.findViewById(R.id.orderTitle)

            orderQty = itemView.findViewById(R.id.orderQty)

            orderPrice = itemView.findViewById(R.id.orderPrice)

            orderSubOrder = itemView.findViewById(R.id.orderSubOrder)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.order_detail_list_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.orderTitle.typeface =
            fontStyle(context, "Light")


        holder.orderQty.typeface =
            fontStyle(context, "Light")

        holder.orderQty.text = "Qty : "+adapterModels.get(position).price

        holder.orderPrice.typeface =
            fontStyle(context, "Light")

        holder.orderPrice.text = "€ "+adapterModels.get(position).toppinsGroupId

        holder.orderSubOrder.visibility = View.GONE

        holder.orderSubOrder.typeface =
            fontStyle(context, "Light")

        holder.orderTitle.text = adapterModels.get(position).name

        var total = 0.0
        var subItem = ""
        for(i in 0 until adapterModels.get(position).toppinsGroupJsonData.toppinsGroupList!!.size)
        {
            for(j in 0 until adapterModels.get(position).toppinsGroupJsonData.toppinsGroupList!!.get(i).toppinsList.size)
            {
                holder.orderSubOrder.visibility = View.VISIBLE
                if(subItem.equals("")) {
                    subItem =
                        "+ "+adapterModels.get(position).toppinsGroupJsonData.toppinsGroupList!!.get(
                            i
                        ).toppinsList.get(j).name
                }else{
                    subItem =
                        subItem + "\n+ " + adapterModels.get(position).toppinsGroupJsonData.toppinsGroupList!!.get(
                            i
                        ).toppinsList.get(j).name
                }
                total = total +adapterModels.get(position).toppinsGroupJsonData.toppinsGroupList!!.get(i).toppinsList.get(j).price!!.toDouble()
            }

        }

        holder.orderSubOrder.text = ""+subItem
        holder.orderPrice.text = "€ "+ addIncreasePriceHole(addIncreasePrice(adapterModels.get(position).toppinsGroupId!!,total.toString()).toString(),adapterModels.get(position).price!!)
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnMaterial(userId: String?,orderId:String?)
        fun setOnFavourite(isFav: String?, id: String?)

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