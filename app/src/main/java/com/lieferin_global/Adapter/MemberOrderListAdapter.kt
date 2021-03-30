package com.lieferin_global.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.fontStyle
import com.lieferin_global.Utility.localgetUserInfo

class MemberOrderListAdapter(context: Context, adapterModels: List<Product>) : RecyclerView.Adapter<MemberOrderListAdapter.MyViewHolder>() {
    private val adapterModels: List<Product>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null
    var dbHelper : DBHelper? = null
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var itemName: TextView

        var itemAddress: TextView

        var itemPrice: TextView

        var itemDescription: TextView

        var itemDate: TextView

        var viewOrder: TextView

        var trackOrder: TextView

        var leftLL : LinearLayout

        var rightLL : LinearLayout

        var orderType : LinearLayout



        init {

            orderType = itemView.findViewById(R.id.orderType)

            itemName = itemView.findViewById(R.id.itemName)

            itemAddress = itemView.findViewById(R.id.itemAddress)

            itemPrice = itemView.findViewById(R.id.itemPrice)

            itemDescription = itemView.findViewById(R.id.itemDescription)

            itemDate = itemView.findViewById(R.id.itemDate)

            viewOrder = itemView.findViewById(R.id.viewOrder)

            trackOrder = itemView.findViewById(R.id.trackOrder)

            leftLL = itemView.findViewById(R.id.leftLL)

            rightLL = itemView.findViewById(R.id.rightLL)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.order_history_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        dbHelper = DBHelper(context)

        holder.itemName.setTypeface(fontStyle(context, "SemiBold"))

        holder.itemName.setText(adapterModels[position].name)

        holder.itemAddress.setTypeface(fontStyle(context, "Light"))

        holder.itemAddress.setText(adapterModels[position].menuId)

        holder.itemPrice.setTypeface(fontStyle(context, "Light"))

        holder.itemPrice.setText("Mobile : "+adapterModels[position].price)

        holder.itemDescription.setText(adapterModels[position].menuReferenceCode)

        holder.itemDate.setTypeface(fontStyle(context, "Light"))

        if(adapterModels[position].allergy1.equals("0")) {
            holder.itemDate.setText("Payment Type : Your Self")
            holder.leftLL.visibility = View.GONE
        }else{
            holder.itemDate.setText("Payment Type : Admin")
            holder.leftLL.visibility = View.VISIBLE
        }

        holder.viewOrder.setTypeface(fontStyle(context, "SemiBold"))

        holder.viewOrder.setText(adapterModels[position].categoryId)

        holder.trackOrder.setTypeface(fontStyle(context, "SemiBold"))

        holder.trackOrder.setText(adapterModels[position].offerPrice)

        holder.viewOrder.setOnClickListener(View.OnClickListener { mCallback!!.setOnMaterial(""+holder.viewOrder.text.toString(),adapterModels[position]) })

        holder.trackOrder.setOnClickListener(View.OnClickListener {

                mCallback!!.setOnMaterial(""+holder.trackOrder.text.toString(), adapterModels[position])
            })

        if(adapterModels[position].categoryId.equals("Reorder"))
        {
            if(AppType.equals("0")) {
                holder.leftLL.setBackgroundResource(R.drawable.home_button_orange_view)
                holder.viewOrder.setTextColor(Color.parseColor("#ec272b"))
            }else{
                holder.leftLL.setBackgroundResource(R.drawable.home_green_white)
                holder.viewOrder.setTextColor(Color.parseColor("#ec272b"))
            }
        }else if(adapterModels[position].categoryId.equals("Reorder\n" +
                    "Not Available"))
        {
            holder.leftLL.setBackgroundResource(R.drawable.home_button_gray_view)
            holder.viewOrder.setTextColor(Color.parseColor("#ACACAC"))
        }else if(adapterModels[position].categoryId.equals("View Order"))
        {
            holder.leftLL.setBackgroundResource(R.drawable.home_button_white)
            holder.viewOrder.setTextColor(Color.parseColor("#484848"))
        }

        if(adapterModels[position].offerPrice.equals(""))
        {
            holder.rightLL!!.visibility = View.INVISIBLE
        }else if(adapterModels[position].offerPrice.equals("Track"))
        {
            holder.rightLL!!.visibility = View.VISIBLE
            if(AppType.equals("0")) {
                holder.rightLL.setBackgroundResource(R.drawable.home_button_orange_view)
                holder.trackOrder.setTextColor(Color.parseColor("#ec272b"))
            }else{
            holder.rightLL.setBackgroundResource(R.drawable.home_green_white)
            holder.trackOrder.setTextColor(Color.parseColor("#7DC77D"))
        }
        }else if(adapterModels[position].offerPrice.equals("Table"))
        {
            holder.rightLL!!.visibility = View.GONE
            if(AppType.equals("0")) {
                holder.rightLL.setBackgroundResource(R.drawable.home_button_orange_view)
                holder.trackOrder.setTextColor(Color.parseColor("#ec272b"))
            }else{
                holder.rightLL.setBackgroundResource(R.drawable.home_green_white)
                holder.trackOrder.setTextColor(Color.parseColor("#7DC77D"))
            }
        }else if(adapterModels[position].offerPrice.equals("Rate Meal"))
        {
            holder.rightLL!!.visibility = View.VISIBLE
            holder.rightLL.setBackgroundResource(R.drawable.home_button_white)
            holder.trackOrder.setTextColor(Color.parseColor("#484848"))
        }
        if(adapterModels[position].typeView.equals("0"))
        {
            holder.orderType.visibility = View.VISIBLE
        }else{
            holder.orderType.visibility = View.GONE
        }
        if(adapterModels[position].type.equals("0"))
        {
            if(adapterModels[position].price!!.replace("+91","").equals(dbHelper!!.getUserDetails().mobile!!.replace("+91","")))
            {
                holder.orderType.visibility = View.VISIBLE
                holder.leftLL.visibility = View.VISIBLE

                if(!adapterModels[position].offerType!!.equals("0"))
                {
                    holder.leftLL.visibility = View.GONE
                    holder.rightLL.setBackgroundResource(R.drawable.home_button_white)
                    holder.trackOrder.setTextColor(Color.parseColor("#484848"))
                    holder.trackOrder.setText("View Menu")
                }
            }else{
                holder.orderType.visibility = View.GONE
            }
        }else{
            holder.orderType.visibility = View.VISIBLE
            if(!adapterModels[position].offerType!!.equals("0"))
            {
                holder.leftLL.visibility = View.VISIBLE
                holder.viewOrder.setTextColor(Color.parseColor("#484848"))
                holder.viewOrder.setText("Pay Now")
                holder.rightLL.setBackgroundResource(R.drawable.home_button_white)
                holder.trackOrder.setTextColor(Color.parseColor("#484848"))
                holder.trackOrder.setText("View Menu")

                if(adapterModels[position].price!!.replace("+91","").equals(dbHelper!!.getUserDetails().mobile!!.replace("+91","")))
                {
                    holder.leftLL.visibility = View.GONE
                }else if(adapterModels[position].allergy1.equals("0")){
                    holder.leftLL.visibility = View.GONE
                    }
                if(!adapterModels[position].offer!!.equals("0")){
                    holder.leftLL.visibility = View.GONE
                }
            }
        }

        if(adapterModels[position].quantity!!.equals("1"))
        {
            holder.rightLL.visibility = View.GONE

            holder.viewOrder.setTextColor(Color.parseColor("#484848"))
            holder.viewOrder.setText("Remove")
        }
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnMaterial( id:String,adapterModels: Product?)
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