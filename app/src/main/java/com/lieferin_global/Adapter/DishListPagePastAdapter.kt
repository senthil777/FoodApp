package com.lieferin_global.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.ProductList
import com.lieferin_global.Model.ProductListView
import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle
import java.text.DecimalFormat

class DishListPagePastAdapter(context: Context, private val adapterModels: List<ProductList>) : RecyclerView.Adapter<DishListPagePastAdapter.MyViewHolder>(),MultipleListView.CallbackDataAdapter {
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    var favorite = 1
    //var price: String

    var selectedPosition = 0
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var textValue: TextView

          var singleMultiple: RecyclerView

          var itemRL: RelativeLayout

          var itemName : TextView

        init {

            textValue = itemView.findViewById(R.id.textValue)

            singleMultiple = itemView.findViewById(R.id.singleMultiple)

            itemRL = itemView.findViewById(R.id.itemRL)

            itemName = itemView.findViewById(R.id.itemName)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.dish_list_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.textValue.text = adapterModels.get(position).name

        holder.textValue.typeface = fontStyle(context,"")

        holder.itemName.typeface = fontStyle(context, "")

        if(adapterModels.get(position).toppinsList!!.size != 0) {

            for (i in 0 until adapterModels.get(position).toppinsList!!.size) {

                if(adapterModels.get(position).toppinsList!!.get(i).categoryId != null) {

                    if (adapterModels.get(position).toppinsList!!.get(i).categoryId.equals("1")) {
                        holder.itemName.text = adapterModels.get(position).toppinsList!!.get(i).name+ " ( € "+ DecimalFormat("##.##").format(adapterModels.get(position).toppinsList!!.get(i).price!!.toDouble())+")"

                    }
                }else{
                    holder.itemName.text = "Select Items"
                }

            }


        }
        val dialogListPageAdapter = MultipleListView(context,adapterModels[position].toppinsList,adapterModels[position].toppinType)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        holder.singleMultiple!!.layoutManager = mLayoutManager

        holder.singleMultiple!!.itemAnimator!!.addDuration = 5000

        dialogListPageAdapter!!.setCallback(this)

        holder.singleMultiple!!.adapter = dialogListPageAdapter

        if(adapterModels[position].toppinType.equals("2"))
        {
            holder.singleMultiple!!.visibility = View.VISIBLE

            holder.itemRL!!.visibility = View.GONE
        }else{
            holder.singleMultiple!!.visibility = View.GONE

            holder.itemRL!!.visibility = View.VISIBLE
        }

        holder.itemName.setOnClickListener(View.OnClickListener {

            mCallback!!.setOnFavourite1(adapterModels.get(position).toppinsList,"")

        })


    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnRadio(addPrice:String?,userId: String?,position: String?)
        fun setOnFavourite1(adapterModels: List<ProductListView>, id: String?)

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
        //this.price = price
        this.context = context
    }

    override fun setOnRadio(addPrice: String?, userId: String?, position: String?) {

        mCallback!!.setOnRadio(addPrice,userId,position)

    }

    override fun setOnFavourite(isFav: String?, id: String?) {

    }
}