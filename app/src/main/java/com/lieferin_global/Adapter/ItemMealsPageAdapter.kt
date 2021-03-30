package com.lieferin_global.Adapter


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Utility.fontStyle
import com.lieferin_global.R
import com.github.angads25.toggle.LabeledSwitch
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class ItemMealsPageAdapter(context: Context, private val adapterModels: List<AdapterModel>) : RecyclerView.Adapter<ItemMealsPageAdapter.MyViewHolder>() {
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    var favorite = 1
    //var month: String
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

         var itemImg: CircleImageView

        var mealsName :TextView

        var mealsDescription :TextView

        var switch4 : LabeledSwitch

        var priceText :TextView

        var offerPriceText : TextView

        var toppinsName : TextView

        init {

            toppinsName = itemView.findViewById(R.id.toppinsName)

            itemImg = itemView.findViewById(R.id.itemImg)

            mealsName = itemView.findViewById(R.id.mealsName)

            mealsDescription = itemView.findViewById(R.id.mealsDescription)

            priceText = itemView.findViewById(R.id.priceText)

            offerPriceText = itemView.findViewById(R.id.offerPriceText)

            switch4 = itemView.findViewById(R.id.switch4)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_meals_list_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var toppinsTotal =0.0

        holder.mealsName.typeface =
            fontStyle(context, "")

        holder.mealsName.text = adapterModels.get(position).categoryName

        holder.mealsDescription.typeface =
            fontStyle(context, "Light")

        holder.mealsDescription.text = adapterModels.get(position).categoryImage

        holder.offerPriceText.typeface =
            fontStyle(context, "Light")

        holder.offerPriceText.text = adapterModels.get(position).price

        holder.offerPriceText.setPaintFlags(holder.offerPriceText.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

        holder.toppinsName.typeface =
            fontStyle(context, "Light")
        holder.priceText.typeface =
            fontStyle(context, "Light")

        if(adapterModels.get(position).parlourAddress.equals("0")) {
            holder.switch4.isOn = true
        }else{
            holder.switch4.isOn = false
        }

        holder.switch4.setOnToggledListener { labeledSwitch, isOn ->if(holder.switch4.isOn){

            mCallback!!.setOnMap("0",adapterModels.get(position).offer)

        }else{

            mCallback!!.setOnMap("1",adapterModels.get(position).offer)

        }}

        /*
*/
//        Picasso.with(context).load(adapterModels.get(position).offerPercentage).resize(450,450).placeholder(R.drawable.item_placeholder).into(holder.itemImg)

        if(adapterModels.get(position).offerPercentage!!.equals(""))
        {
            Picasso.with(context).load(R.drawable.item_placeholder).resize(450,450).placeholder(R.drawable.item_placeholder).into(holder.itemImg)
        }else{
            Picasso.with(context).load(adapterModels.get(position).offerPercentage).resize(450,450).placeholder(R.drawable.item_placeholder).into(holder.itemImg)
        }
        //holder.toppinsName.visibility = View.GONE

        var toppinsList = ""

        var toppinsPrice =0.0
        for (i in 0 until adapterModels.get(position).menusList.size) {

            if(adapterModels.get(position).offer!!.equals(adapterModels.get(position).menusList.get(i).price))
            {
                holder.toppinsName.visibility = View.VISIBLE
                if(toppinsList!!.equals(""))
                {
                    toppinsList = ""+adapterModels.get(position).menusList.get(i).name
                }else{
                    toppinsList = toppinsList+","+adapterModels.get(position).menusList.get(i).name
                }
                holder.toppinsName.text = ""+toppinsList
                toppinsPrice = toppinsPrice + adapterModels.get(position).menusList.get(i).menuId!!.toDouble()
            }
        }

        toppinsTotal = adapterModels.get(position).offerPrice!!.toDouble()

        toppinsTotal = toppinsTotal + toppinsPrice

        holder.priceText.text = "€ "+toppinsTotal
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnMaterial(userId: String?)
        fun setOnMap(isFav: String?, id: String?)

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