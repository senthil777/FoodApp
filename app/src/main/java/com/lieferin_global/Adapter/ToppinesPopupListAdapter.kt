package com.lieferin_global.Adapter

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.ProductList
import com.lieferin_global.Model.ProductListView
import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle
import com.lieferin_global.Utility.localgetUserInfo
import com.squareup.picasso.Picasso

class ToppinesPopupListAdapter(context: Context, adapterModels: List<ProductListView>, val dialog : Dialog) : RecyclerView.Adapter<ToppinesPopupListAdapter.MyViewHolder>() {
    private val adapterModels: List<ProductListView>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null
    var dbHelper : DBHelper? = null
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var itemList:RadioButton


        init {
            itemList = itemView.findViewById(R.id.itemList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.toppines_item_list, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        dbHelper = DBHelper(context)
        holder.itemList.setTypeface(fontStyle(context, ""))
        holder.itemList.setText(adapterModels[position].name+" ("+adapterModels[position].price+")")

        Log.v("id","== "+adapterModels[position].headerSub)

        if(dbHelper!!.getMenuGroupToppinsCheck(adapterModels[position].toppinsId!!).equals(adapterModels[position].toppinsId!!))
        {
            if(adapterModels[position].headerSub.equals("2")) {
                holder.itemList.isChecked = true

                adapterModels[position].headerSub = "1"
            }
        }

        if(adapterModels[position].headerSub.equals("1"))
        {
            holder.itemList!!.isChecked = true
        }else{
            holder.itemList!!.isChecked = false
        }

        holder.itemList.setOnClickListener(View.OnClickListener {

            for(i in 0 until adapterModels.size)
            {
                adapterModels[i].headerSub ="0"
            }
            adapterModels[position].headerSub = "1"
            mCallback!!.setOnFav(adapterModels[position].name)
        })




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