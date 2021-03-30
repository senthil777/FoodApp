package com.lieferin_global.Adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.ProductList
import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle
import com.lieferin_global.Utility.localgetUserInfo
import com.squareup.picasso.Picasso

class ToppinesPopupAdapter(context: Context, adapterModels: List<ProductList>, val dialog : Dialog) : RecyclerView.Adapter<ToppinesPopupAdapter.MyViewHolder>(),ToppinesPopupListAdapter.CallbackDataAdapter,ToppinesPopupListMultiAdapter.CallbackDataAdapter {
    private val adapterModels: List<ProductList>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var toppinesPopupListAdapter: ToppinesPopupListAdapter? = null

    var toppinesPopupListMultiAdapter: ToppinesPopupListMultiAdapter? = null
    var isChecked = true
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var groupListTV:TextView

        var toppinsList : RecyclerView

        init {
            groupListTV = itemView.findViewById(R.id.groupListTV)

            toppinsList = itemView.findViewById(R.id.toppinsList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.toppines_group_list, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.groupListTV.setTypeface(fontStyle(context, ""))
        holder.groupListTV.setText(adapterModels[position].name)

        if(adapterModels[position].toppinType.equals("1")) {
            toppinesPopupListAdapter = ToppinesPopupListAdapter(
                context,
                adapterModels.get(position).toppinsList, dialog
            )
            holder.toppinsList.setHasFixedSize(true)
            holder.toppinsList.setLayoutManager(
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            )

            holder.toppinsList.isNestedScrollingEnabled = false

            toppinesPopupListAdapter!!.setCallback(this)

            holder.toppinsList.setAdapter(toppinesPopupListAdapter)
        }else{
            toppinesPopupListMultiAdapter = ToppinesPopupListMultiAdapter(
                context,
                adapterModels.get(position).toppinsList, dialog
            )
            holder.toppinsList.setHasFixedSize(true)
            holder.toppinsList.setLayoutManager(
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            )

            holder.toppinsList.isNestedScrollingEnabled = false

            toppinesPopupListMultiAdapter!!.setCallback(this)

            holder.toppinsList.setAdapter(toppinesPopupListMultiAdapter)
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

        fun setOnValue(id: String?)
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

    override fun setOnFav(id: String?) {
        toppinesPopupListAdapter!!.notifyDataSetChanged()
    }

    override fun setOnValue(id: String?) {
        toppinesPopupListMultiAdapter!!.notifyDataSetChanged()
    }
}