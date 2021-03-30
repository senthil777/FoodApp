package com.lieferin_global.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.R
import com.lieferin_global.Utility.addDecrease
import com.lieferin_global.Utility.addIncrease
import com.lieferin_global.Utility.colorIcon
import com.lieferin_global.Utility.fontStyle
import com.squareup.picasso.Picasso


class GroceryListItemHorizontal2Adapter(context: Context, private val adapterModels: List<AdapterModel>) : RecyclerView.Adapter<GroceryListItemHorizontal2Adapter.MyViewHolder>() {
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    var favorite = 1
    //var month: String
    var itemView: View? = null


    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var addImage: ImageView

        var productPrice: TextView

        var productName: TextView

        var productQty: TextView

        var productOfferDeal: TextView

        var productPriceOffer: TextView

        var productImg: ImageView

        var minusLayout : LinearLayout

        var addLayout : LinearLayout

        var addItem : TextView

        var layoutVM : LinearLayout

        var minus : ImageView

        var puls : ImageView

        var addCard : CardView

        var addText : TextView

        var productRelative : RelativeLayout


        init {

            addImage = itemView.findViewById(R.id.addImage)

            productPrice = itemView.findViewById(R.id.productPrice)

            productName = itemView.findViewById(R.id.productName)

            productQty = itemView.findViewById(R.id.productQty)

            productOfferDeal = itemView.findViewById(R.id.productOfferDeal)

            productImg = itemView.findViewById(R.id.productImg)

            productPriceOffer = itemView.findViewById(R.id.productPriceOffer)

            addItem = itemView.findViewById(R.id.addItem)

            minusLayout = itemView.findViewById(R.id.minusLayout)

            addLayout = itemView.findViewById(R.id.addLayout)

            layoutVM = itemView.findViewById(R.id.layoutVM)

            minus = itemView.findViewById(R.id.minus)

            puls = itemView.findViewById(R.id.puls)

            addCard = itemView.findViewById(R.id.addCard)

            addText = itemView.findViewById(R.id.addText)

            productRelative = itemView.findViewById(R.id.productRelative)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.product_horizontal_item_dialog, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        if(adapterModels.get(position).parlourRatingValue.equals("0"))
        {
            holder.layoutVM.visibility = View.GONE

            holder.addCard.visibility = View.GONE
        }else{
            holder.layoutVM.visibility = View.VISIBLE

            holder.addCard.visibility = View.VISIBLE

            if(adapterModels.get(position).parlourAddress.equals("1"))
            {
                holder.minus.setColorFilter(colorIcon(context,R.color.colorGreen,R.drawable.delete,holder.minus!!))
            }else{
                holder.minus.setColorFilter(colorIcon(context,R.color.colorGreen,R.drawable.minus,holder.minus!!))
            }
        }

        if(adapterModels.get(position).parlourAddress.equals("0"))
        {
           holder.addImage.visibility = View.VISIBLE

           holder.addText.visibility = View.GONE
        }else{
            holder.addImage.visibility = View.GONE

            holder.addText.visibility = View.VISIBLE
        }

        holder.addText.setText(adapterModels.get(position).parlourAddress)

        holder.addText.typeface = fontStyle(context,"")

        holder.addItem.setText(adapterModels.get(position).parlourAddress)

        holder.productName.typeface =
            fontStyle(context, "")

        holder.productName.text = adapterModels.get(position).categoryName

        holder.addImage.setColorFilter(colorIcon(context,R.color.colorGreen,R.drawable.add,holder.addImage!!))

        holder.puls.setColorFilter(colorIcon(context,R.color.colorGreen,R.drawable.plus,holder.puls!!))

        holder.productPrice.typeface =
            fontStyle(context, "")

        holder.productPrice.text = adapterModels.get(position).price

        holder.productQty.typeface =
            fontStyle(context, "")

        holder.productQty.text = adapterModels.get(position).offerPrice

        holder.productOfferDeal.typeface =
            fontStyle(context, "")

        holder.productOfferDeal.text = adapterModels.get(position).offerPercentage

        if(!adapterModels.get(position).offer.equals("")) {

            Picasso.with(context).load(adapterModels.get(position).offer).resize(450, 450)
                .into(holder.productImg)
        }

        holder.productPriceOffer.typeface =
            fontStyle(context, "")

        holder.productPriceOffer.text = adapterModels.get(position).categoryImage

        holder.productPriceOffer.setPaintFlags(holder.productPriceOffer.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

        holder.addImage.setOnClickListener(View.OnClickListener {

            holder.layoutVM.visibility = View.VISIBLE

            holder.addCard.visibility = View.VISIBLE

            adapterModels.get(position).parlourAddress = ""+addIncrease(adapterModels.get(position).parlourAddress!!)

            holder.addItem.setText(adapterModels.get(position).parlourAddress)

            mCallback!!.setOnExtraItem2("",""+position)

            mCallback!!.setOnFavourite("","")

            if(adapterModels.get(position).parlourAddress.equals("1"))
            {
                holder.minus.setColorFilter(colorIcon(context,R.color.colorGreen,R.drawable.delete,holder.minus!!))
            }else{
                holder.minus.setColorFilter(colorIcon(context,R.color.colorGreen,R.drawable.minus,holder.minus!!))
            }

        })

        holder.layoutVM.setOnClickListener(View.OnClickListener {
            holder.layoutVM.visibility = View.GONE

            holder.addCard.visibility = View.GONE

            if(adapterModels.get(position).parlourAddress.equals("0"))
            {
                holder.addImage.visibility = View.VISIBLE

                holder.addText.visibility = View.GONE
            }else{
                holder.addImage.visibility = View.GONE

                holder.addText.visibility = View.VISIBLE

               // holder.addText.setText(adapterModels.get(position).parlourAddress)
            }

        })

        holder.addText.setOnClickListener(View.OnClickListener {

            holder.layoutVM.visibility = View.VISIBLE

            holder.addCard.visibility = View.VISIBLE

            //adapterModels.get(position).parlourAddress = ""+addIncrease(adapterModels.get(position).parlourAddress)

            //holder.addItem.setText(adapterModels.get(position).parlourAddress)

            if(adapterModels.get(position).parlourAddress.equals("1"))
            {
                holder.minus.setColorFilter(colorIcon(context,R.color.colorGreen,R.drawable.delete,holder.minus!!))
            }else{
                holder.minus.setColorFilter(colorIcon(context,R.color.colorGreen,R.drawable.minus,holder.minus!!))
            }
            mCallback!!.setOnExtraItem2("",""+position)

        })

        holder.addLayout.setOnClickListener(View.OnClickListener {

            holder.minus.setColorFilter(colorIcon(context,R.color.colorGreen,R.drawable.minus,holder.minus!!))

            adapterModels.get(position).parlourAddress = ""+addIncrease(adapterModels.get(position).parlourAddress!!)

            holder.addItem.setText(adapterModels.get(position).parlourAddress)

            Log.v("kkk"," == "+adapterModels.get(position).parlourAddress)

            mCallback!!.setOnFavourite("","")
        })
        holder.minusLayout.setOnClickListener(View.OnClickListener {

            Log.v("kkk"," == "+adapterModels.get(position).parlourAddress)

            if(adapterModels.get(position).parlourAddress.equals("2"))
            {
                holder.minus.setColorFilter(colorIcon(context,R.color.colorGreen,R.drawable.delete,holder.minus!!))

                adapterModels.get(position).parlourAddress = ""+addDecrease(adapterModels.get(position).parlourAddress!!)
            }else if(adapterModels.get(position).parlourAddress.equals("1"))
            {

                holder.layoutVM.visibility = View.GONE

                holder.addCard.visibility = View.GONE

                holder.addText.visibility = View.GONE

                holder.addImage.visibility = View.VISIBLE

                adapterModels.get(position).parlourAddress = ""+addDecrease(adapterModels.get(position).parlourAddress!!)
            }else{
                adapterModels.get(position).parlourAddress = ""+addDecrease(adapterModels.get(position).parlourAddress!!)
            }

            mCallback!!.setOnFavourite("","")
            holder.addItem.setText(adapterModels.get(position).parlourAddress)
        })

        holder.productRelative.setOnClickListener(View.OnClickListener {mCallback!!.setOnProduct("","")  })


    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnExtraItem2(type: String?, id: String?)
        fun setOnFavourite(isFav: String?, id: String?)

        fun setOnProduct(isFav: String?, id: String?)

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