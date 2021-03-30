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
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.R
import com.lieferin_global.Utility.Constant
import com.lieferin_global.Utility.fontStyle

class FiltersidePageAdapter(context: Context, adapterModels: List<AdapterModel>) :
    RecyclerView.Adapter<FiltersidePageAdapter.MyViewHolder>() {
    private val adapterModels: List<AdapterModel>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var selectView: View

        var filterTitleTV: TextView

        var filterDescriptionTV: TextView

        var filterLL: LinearLayout

        init {

            selectView = itemView.findViewById(R.id.selectView)

            filterTitleTV = itemView.findViewById(R.id.filterTitleTV)

            filterDescriptionTV = itemView.findViewById(R.id.filterDescriptionTV)

            filterLL = itemView.findViewById(R.id.filterLL)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.filter_slide_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(Constant.AppType.equals("0"))
        {
            holder.selectView.setBackgroundColor(Color.parseColor("#ec272b"))

            holder.filterDescriptionTV.setTextColor(Color.parseColor("#ec272b"))
        }else{
            holder.selectView.setBackgroundColor(Color.parseColor("#7DC77D"))

            holder.filterDescriptionTV.setTextColor(Color.parseColor("#7DC77D"))
        }

        holder.filterTitleTV.typeface = fontStyle(context, "")

        holder.filterTitleTV.text = adapterModels.get(position).categoryName

        holder.filterDescriptionTV.typeface = fontStyle(context, "Light")

        holder.filterDescriptionTV.text = adapterModels.get(position).price

        if (adapterModels.get(position).price.equals("")) {
            holder.filterDescriptionTV.visibility = View.GONE
        } else {
            holder.filterDescriptionTV.visibility = View.VISIBLE
        }
        if (!adapterModels.get(position).offerPrice.equals("")) {
            if (adapterModels.get(position).offerPrice.equals("0")) {
                pos = position
            }
        }
        if (pos == position) {
            holder.filterLL.setBackgroundColor(Color.parseColor("#ffffff"))

            holder.selectView.visibility = View.VISIBLE
        } else {
            holder.filterLL.setBackgroundColor(Color.parseColor("#F0EFEF"))

            holder.selectView.visibility = View.GONE
        }

        holder.filterLL.setOnClickListener(View.OnClickListener {

            pos = position

            adapterModels.get(position).offerPrice = ""

            mCallback!!.setOnFilterSide(adapterModels.get(position).categoryName)
        })
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnFilterSide(id: String?)
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