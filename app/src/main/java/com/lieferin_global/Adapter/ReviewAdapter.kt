package com.lieferin_global.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.R
import com.lieferin_global.Utility.RoundedTransformation
import com.lieferin_global.Utility.fontStyle
import com.squareup.picasso.Picasso

class ReviewAdapter(context: Context, adapterModels: List<AdapterModel>) : RecyclerView.Adapter<ReviewAdapter.MyViewHolder>() {
    private val adapterModels: List<AdapterModel>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var profileImg: ImageView

        var userName:TextView

        var userNameDate:TextView

        var userDescription : TextView

        var reviewTV : TextView

        var linearLayout : LinearLayout


        init {

            profileImg = itemView.findViewById(R.id.profileImg)

            userName = itemView.findViewById(R.id.userName)

            userNameDate = itemView.findViewById(R.id.userNameDate)

            userDescription = itemView.findViewById(R.id.userDescription)

            reviewTV = itemView.findViewById(R.id.reviewTV)

            linearLayout = itemView.findViewById(R.id.linearLayout)



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.review_layout_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.userName.setTypeface(fontStyle(context, "SemiBold"))

        holder.userName.setText(adapterModels[position].categoryName)

        holder.userNameDate.setTypeface(fontStyle(context, "Light"))

        holder.userNameDate.setText(adapterModels[position].price)

        holder.userDescription.setTypeface(fontStyle(context, "Light"))

        holder.reviewTV.setTypeface(fontStyle(context, "Light"))

        holder.reviewTV.setText(adapterModels[position].offerPrice)

        if(!adapterModels[position].offer.equals("")) {

            Picasso.with(context).load(adapterModels[position].offer).resize(450, 450)
                .transform(RoundedTransformation(16, 0)).into(holder.profileImg)

        }
        holder.linearLayout.removeAllViews()

        for (i in 1..5) {
            val image = ImageView(context)
            image.layoutParams = ViewGroup.LayoutParams(48, 36)
            image.maxHeight = 16
            image.maxWidth = 16

            image.setImageResource(R.drawable.star_re)

            // Adds the view to the layout
            holder.linearLayout.addView(image)
        }

    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnFamous(id: String?)
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