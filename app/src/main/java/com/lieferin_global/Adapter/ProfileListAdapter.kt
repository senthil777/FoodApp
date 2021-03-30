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
import com.lieferin_global.Utility.fontStyle
import com.squareup.picasso.Picasso

class ProfileListAdapter(context: Context, adapterModels: List<AdapterModel>) : RecyclerView.Adapter<ProfileListAdapter.MyViewHolder>() {
    private val adapterModels: List<AdapterModel>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var iconId: ImageView

        var paymentCategory:TextView

        var profileLayout : LinearLayout


        init {

            iconId = itemView.findViewById(R.id.iconId)

            paymentCategory = itemView.findViewById(R.id.paymentCategory)

            profileLayout = itemView.findViewById(R.id.profileLayout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.profile_list_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.paymentCategory.setTypeface(fontStyle(context, ""))
        holder.paymentCategory.setText(adapterModels[position].categoryName)


        holder.profileLayout.setOnClickListener(View.OnClickListener {

            mCallback!!.setOnFav(adapterModels[position].categoryName)
        })

        if(!adapterModels[position].image.equals("")) {

            Picasso.with(context).load(adapterModels[position].image).resize(450, 450)
                .into(holder.iconId)
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