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
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.fontStyle

class AddAddressAdapter(context: Context, adapterModels: List<Product>) : RecyclerView.Adapter<AddAddressAdapter.MyViewHolder>() {
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

        var select_address_radio_buttonGreen : RadioButton

        var addressNameTV:TextView

        var landMarkTV : TextView

        var groceryTV : TextView

        var addressTV:TextView

        var delete:ImageView

        var addressType : TextView

        var selectOrder : LinearLayout

        init {

            select_address_radio_button = itemView.findViewById(R.id.select_address_radio_button)

            select_address_radio_buttonGreen = itemView.findViewById(R.id.select_address_radio_buttonGreen)

            addressNameTV = itemView.findViewById(R.id.addressNameTV)

            landMarkTV = itemView.findViewById(R.id.landMarkTV)

            groceryTV = itemView.findViewById(R.id.groceryTV)

            addressTV = itemView.findViewById(R.id.addressTV)

            delete = itemView.findViewById(R.id.delete)

            selectOrder = itemView.findViewById(R.id.selectOrder)

            addressType= itemView.findViewById(R.id.addressType)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.select_address_layout, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.addressType.setTypeface(fontStyle(context, "SemiBold"))

        if(adapterModels[position].type!!.equals("1"))
        {
            holder.addressType!!.text ="Home"

        }else if(adapterModels[position].type!!.equals("2"))
        {
            holder.addressType!!.text ="Work"
        }else if(adapterModels[position].type!!.equals("3"))
        {
            holder.addressType!!.text ="Other"
        }

        holder.addressNameTV.setTypeface(fontStyle(context, ""))

        holder.addressNameTV.setText(adapterModels[position].menuId)

        if(adapterModels[position].typeView!!.equals("null") ||adapterModels[position].typeView!!.equals(""))
        {
            holder.addressTV!!.visibility = View.GONE
        }else{
            holder.addressTV!!.visibility = View.VISIBLE
        }

        if(adapterModels[position].quantity!!.equals("null") ||adapterModels[position].quantity!!.equals(""))
        {
            holder.landMarkTV!!.visibility = View.GONE
        }else{
            holder.landMarkTV!!.visibility = View.VISIBLE

        }

        if(adapterModels[position].menuImages!!.equals("null") ||adapterModels[position].menuImages!!.equals(""))
        {
            holder.groceryTV!!.visibility = View.GONE
        }else{
            holder.groceryTV!!.visibility = View.VISIBLE
        }

        holder.addressTV.setTypeface(fontStyle(context, "Light"))

        holder.addressTV.setText(adapterModels[position].typeView)

        holder.landMarkTV.setTypeface(fontStyle(context, "Light"))

        holder.landMarkTV.setText(adapterModels[position].quantity)

        holder.groceryTV.setTypeface(fontStyle(context, "Light"))

        holder.groceryTV.setText(adapterModels[position].menuImages)

        holder.delete.setImageResource(adapterModels[position].imageIcon)

        holder.selectOrder.setOnClickListener(View.OnClickListener {

            pos = position
            mCallback!!.setOnAddress(adapterModels[position].price,adapterModels[position].menuReferenceCode,adapterModels[position].allergy1,adapterModels[position].categoryId,adapterModels[position].offerPrice)
        })

        if (position == pos) {

            holder.select_address_radio_button.isChecked = true

            holder.select_address_radio_buttonGreen.isChecked = true

        } else {
            holder.select_address_radio_button.isChecked = false

            holder.select_address_radio_buttonGreen.isChecked = false
        }

        holder.delete.setOnClickListener(View.OnClickListener {
            mCallback!!.setOnDelete(adapterModels[position].price)

        })

        if(AppType!!.equals("0"))
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
        fun setOnAddress(id: String?,pincode: String?,longtitude: String?,latitude : String?,address : String?)

        fun setOnDelete(id: String?)
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