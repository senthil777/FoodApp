package com.lieferin_global.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class FamousAdapter(context: Context, adapterModels: List<AdapterModel>) : RecyclerView.Adapter<FamousAdapter.MyViewHolder>() {
    private val adapterModels: List<AdapterModel>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var itemIV: CircleImageView

        var itemName:TextView

        var itemOption:TextView

        var viewLayout: LinearLayout

        init {

            itemIV = itemView.findViewById(R.id.itemIV)

            itemName = itemView.findViewById(R.id.itemName)

            itemOption = itemView.findViewById(R.id.itemOption)

            viewLayout = itemView.findViewById(R.id.viewLayout)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_famous_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemName.setTypeface(fontStyle(context, "SemiBold"))

        holder.itemName.setText(adapterModels[position].categoryName)

        holder.itemOption.setTypeface(fontStyle(context, "SemiBold"))


        if(!adapterModels[position].price.equals("")) {

            Picasso.with(context).load(adapterModels[position].price).resize(450, 450)
                .into(holder.itemIV)
        }else{
            Picasso.with(context).load(R.drawable.restaurant_view_all).resize(450, 450)
                .into(holder.itemIV)
        }

        holder.viewLayout.setOnClickListener {
            mCallback!!.setOnFamous(adapterModels[position].categoryName)
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