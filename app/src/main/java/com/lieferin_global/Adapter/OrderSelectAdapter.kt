package com.lieferin_global.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle

class OrderSelectAdapter(context: Context, adapterModels: List<Product>) : RecyclerView.Adapter<OrderSelectAdapter.MyViewHolder>() {
    private val adapterModels: List<Product>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String

    var selectedPosition = -1
    var pos = -1
    var isChecked = true
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var order_type_tv: RadioButton

        var paymentSelect : LinearLayout

        init {
            order_type_tv = itemView.findViewById(R.id.order_type_tv)

            paymentSelect = itemView.findViewById(R.id.paymentSelect)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.select_order_type_layout, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.order_type_tv.setTypeface(fontStyle(context, ""))

        if(adapterModels[position].menuId.equals("")) {

            holder.order_type_tv.setText(adapterModels[position].name)
        }else{

            if(adapterModels[position].menuReferenceCode.equals(adapterModels[position].name)) {

                holder.order_type_tv.setText(adapterModels[position].name + " (" + adapterModels[position].menuId + ")")

            }else{
                holder.order_type_tv.setText(adapterModels[position].name)
            }

        }

        holder.order_type_tv.setOnClickListener(View.OnClickListener {
            selectedPosition = position
            mCallback!!.setOnMaterial(adapterModels[position].name)
        })

        if (position == selectedPosition) {

            holder.order_type_tv.isChecked = true

        } else {
            holder.order_type_tv.isChecked = false
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