package com.lieferin_global.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.Constant
import com.lieferin_global.Utility.fontStyle

class PaymentListAdapter(context: Context, adapterModels: List<Product>) : RecyclerView.Adapter<PaymentListAdapter.MyViewHolder>() {
    private val adapterModels: List<Product>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var select_address_radio_button: RadioButton

        var select_address_radio_buttonGreen: RadioButton

        var addressNameTV:TextView

        var addressTV:TextView

        var delete:ImageView

        var paymentLayout : LinearLayout

        init {

            select_address_radio_button = itemView.findViewById(R.id.select_address_radio_button)

            select_address_radio_buttonGreen = itemView.findViewById(R.id.select_address_radio_buttonGreen)

            addressNameTV = itemView.findViewById(R.id.addressNameTV)

            addressTV = itemView.findViewById(R.id.addressTV)

            delete = itemView.findViewById(R.id.delete)

            paymentLayout = itemView.findViewById(R.id.paymentLayout)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.select_payment_layout, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.addressNameTV.setTypeface(fontStyle(context, ""))

        holder.addressNameTV.setText(adapterModels[position].name)

        holder.addressTV.setTypeface(fontStyle(context, "Light"))

        holder.addressTV.setText(adapterModels[position].menuId)

        if(adapterModels[position].menuId.equals(""))
        {
            holder.addressTV.visibility = View.GONE
        }

        holder.delete.setImageResource(adapterModels[position].imageIcon)

        holder.paymentLayout.setOnClickListener(View.OnClickListener {

            pos = position
            mCallback!!.setOnAddress(adapterModels[position].name)
        })

        if (position == pos) {

            holder.select_address_radio_button.isChecked = true

            holder.select_address_radio_buttonGreen.isChecked = true

        } else {
            holder.select_address_radio_button.isChecked = false

            holder.select_address_radio_buttonGreen.isChecked = false
        }

        if(Constant.AppType!!.equals("0"))
        {
            holder.select_address_radio_buttonGreen.visibility = View.GONE

            holder.select_address_radio_button.visibility = View.VISIBLE
        }else{
            holder.select_address_radio_buttonGreen.visibility = View.VISIBLE

            holder.select_address_radio_button.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnAddress(id: String?)
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