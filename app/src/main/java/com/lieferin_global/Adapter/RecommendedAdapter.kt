package com.lieferin_global.Adapter

import android.content.Context
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.Constant
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.colorIcon
import com.lieferin_global.Utility.fontStyle
import com.squareup.picasso.Picasso


class RecommendedAdapter(context: Context, adapterModels: List<Product>) : RecyclerView.Adapter<RecommendedAdapter.MyViewHolder>() {
    private val adapterModels: List<Product>
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

        var viewOffer:TextView

        var cardView:CardView

        var ratingImage : ImageView


        init {

            ratingImage = itemView.findViewById(R.id.ratingImage)

            viewStoreImg = itemView.findViewById(R.id.viewStoreImg)

            viewOffer = itemView.findViewById(R.id.viewOffer)

            viewIcon = itemView.findViewById(R.id.viewIcon)

            viewRating = itemView.findViewById(R.id.viewRating)

            viewStoreName = itemView.findViewById(R.id.viewStoreName)

            viewStoreDescription = itemView.findViewById(R.id.viewStoreDescription)

            viewRatingValue = itemView.findViewById(R.id.viewRatingValue)

            viewDistance = itemView.findViewById(R.id.viewDistance)

            viewStoreDescription = itemView.findViewById(R.id.viewStoreDescription)

            //viewPrice = itemView.findViewById(R.id.viewPrice)

            cardView = itemView.findViewById(R.id.cardView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_recommended_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.ratingImage!!.setColorFilter(
            colorIcon(context, R.color.colorWhite, R.drawable.star, holder.ratingImage!!),
            PorterDuff.Mode.SRC_ATOP
        )

        holder.viewStoreName.setTypeface(fontStyle(context, ""))

        val str = adapterModels[position].name
        val arrOfStr = str!!.split("-").toTypedArray()

        holder.viewStoreName.setText(arrOfStr[0])

        holder.viewDistance.setTypeface(fontStyle(context, "SemiBold"))

        if(adapterModels.get(position).menuImages.equals("Gro")) {
            holder.viewDistance.text = adapterModels[position].allergy1
        }else{
            holder.viewDistance.text = adapterModels[position].categoryId
        }

       /* holder.viewPrice.setTypeface(fontStyle(context, "Light"))

        holder.viewPrice.text = adapterModels[position].offerPrice*/

        holder.viewRatingValue.setTypeface(fontStyle(context, "SemiBold"))

        holder.viewRatingValue.text = adapterModels[position].menuId!!.substring(0)

        holder.viewStoreDescription.setTypeface(fontStyle(context, "Light"))

        holder.viewStoreDescription.text = adapterModels[position].menuReferenceCode

        holder.viewOffer.setTypeface(fontStyle(context, "SemiBold"))

        holder.viewOffer.setText("")

        if(adapterModels[position].name!!.contains("supermarket")) {

            if(!adapterModels[position].categoryId.equals("")) {

                Picasso.with(context).load(adapterModels[position].categoryId).resize(450, 450)
                    .into(holder.viewStoreImg)
            }

        }else{

            var str = adapterModels[position].quantity

            Log.v("===]]]"+str,"====")

            str = str!!.replace("]","").toString()
            val arrOfStr =
                str.split(",")

            Log.v("===]]]"+arrOfStr[0],"====")

            if(!arrOfStr[0].equals("")) {

                if(AppType.equals("0")) {

                    Picasso.with(context)
                        .load("" + arrOfStr[0])
                        .resize(450, 450).placeholder(R.drawable.restaurant_placeholder)
                        .into(holder.viewStoreImg)
                }else{
                    Picasso.with(context)
                        .load(adapterModels.get(position).categoryId)
                        .resize(450, 450).placeholder(R.drawable.item_placeholder_geocery)
                        .into(holder.viewStoreImg)
                }
            }
            /*Log.v("===]]]"+arrOfStr[0],"====")
            Picasso.with(context).load(arrOfStr[0]).resize(450, 450)
                .into(holder.viewStoreImg)*/
        }
        holder.viewIcon.setColorFilter(colorIcon(context,R.color.colorWhite,R.drawable.favorite,holder.viewIcon))
        holder.cardView.setOnClickListener {

            if(AppType.equals("0")) {
                mCallback!!.setOnMaterial("" + arrOfStr[1])
            }else{
                mCallback!!.setOnMaterial("" + adapterModels.get(position).price)
            }
        }

        if(AppType.equals("0")) {
            val favString1 =
                Constant.favStringValue.split(",")

            holder.viewIcon!!.tag = "0"

            for (i in 0 until favString1!!.size) {
                if (favString1[i].equals(adapterModels[position].offerPrice)) {
                    holder.viewIcon.setColorFilter(
                        colorIcon(
                            context,
                            R.color.colorWhite,
                            R.drawable.heart_selected,
                            holder.viewIcon
                        )
                    )

                    holder.viewIcon.tag = "1"

                }
            }
        }else{
            val favString1 =
                Constant.favStringGroceryValue.split(",")

            holder.viewIcon!!.tag = "0"

            for (i in 0 until favString1!!.size) {
                if (favString1[i].equals(adapterModels[position].offerPrice)) {
                    holder.viewIcon.setColorFilter(
                        colorIcon(
                            context,
                            R.color.colorWhite,
                            R.drawable.heart_selected,
                            holder.viewIcon
                        )
                    )

                    holder.viewIcon.tag = "1"

                }
            }
        }


        holder.viewIcon.setOnClickListener(View.OnClickListener {

            if(AppType.equals("0")) {
                if (holder.viewIcon!!.tag.equals("1")) {

                    mCallback!!.setOnDetails("" + arrOfStr[1], "2")
                } else {
                    mCallback!!.setOnDetails("" + arrOfStr[1], "1")
                }
            }else{
                if (holder.viewIcon!!.tag.equals("1")) {

                    mCallback!!.setOnDetails("" + adapterModels.get(position).price, "2")
                } else {
                    mCallback!!.setOnDetails("" + adapterModels.get(position).price, "1")
                }
            }

        })

        if(adapterModels[position].typeView.equals("1"))
        {
            holder.viewRating.setBackgroundResource(R.drawable.home_radius_button_orange)

            holder.viewRating.text = adapterModels[position].menuId

            holder.ratingImage!!.setColorFilter(
                colorIcon(context, R.color.colorWhite, R.drawable.star, holder.ratingImage!!),
                PorterDuff.Mode.SRC_ATOP
            )
            holder.ratingImage.setBackgroundResource(R.drawable.round_red)
            //holder.viewIcon.setBackgroundResource(R.drawable.home_radius_button_orange)

            //holder.viewIcon.setColorFilter(colorIcon(context,R.color.colorWhite,R.drawable.favorite,holder.viewIcon))
        }else{

            holder.viewRating.setBackgroundResource(R.drawable.home_radius_button_green)

            //holder.viewIcon.setBackgroundResource(R.drawable.home_radius_button_green)

            //holder.viewIcon.visibility = View.GONE


            holder.ratingImage!!.setColorFilter(
                colorIcon(context, R.color.colorWhite, R.drawable.star, holder.ratingImage!!),
                PorterDuff.Mode.SRC_ATOP
            )

            holder.ratingImage.setBackgroundResource(R.drawable.round_green)
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

        fun setOnDetails(id: String?,id1: String?)

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