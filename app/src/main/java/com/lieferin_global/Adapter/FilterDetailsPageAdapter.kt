package com.lieferin_global.Adapter

import android.content.Context
import android.graphics.Color
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
import com.lieferin_global.Utility.colorIcon
import com.lieferin_global.Utility.fontStyle

class FilterDetailsPageAdapter(context: Context, adapterModels: List<AdapterModel>) : RecyclerView.Adapter<FilterDetailsPageAdapter.MyViewHolder>() {
    private val adapterModels: List<AdapterModel>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var filterIM: ImageView

        var filterTV:TextView

        var arrowIV : ImageView

        var filterLL : LinearLayout

        init {

            filterIM = itemView.findViewById(R.id.filterIM)

            arrowIV = itemView.findViewById(R.id.arrowIV)

            filterTV = itemView.findViewById(R.id.filterTV)

            filterLL = itemView.findViewById(R.id.filterLL)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.filter_list_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.filterTV.setTypeface(fontStyle(context, "Light"))
        holder.filterTV.setText(adapterModels[position].categoryName)

        if(adapterModels.get(position).imageOne == 1)
        {
            holder.filterLL.visibility = View.GONE
        }else{
            holder.filterLL.visibility = View.VISIBLE
        }

        holder.arrowIV.setColorFilter(colorIcon(context,R.color.colorBlack,R.drawable.arrow_down,holder.arrowIV))

        if(adapterModels[position].price.equals("1"))
        {
            holder.filterIM.visibility = View.VISIBLE
            holder.arrowIV.visibility = View.GONE
        }else if(adapterModels[position].price.equals("2")) {
            holder.filterIM.visibility = View.GONE
            holder.arrowIV.visibility = View.VISIBLE
        }else{
            holder.arrowIV.visibility = View.GONE
            holder.filterIM.visibility = View.GONE
        }

        holder.arrowIV.setOnClickListener(View.OnClickListener {


    })

        if(adapterModels[position].offerPrice.equals("1"))
        {
            holder.filterLL.setBackgroundResource(R.drawable.home_radius_button_gray)

            holder.filterTV.setTextColor(Color.parseColor("#FFFFFF"))

        }else{

            holder.filterLL.setBackgroundResource(R.drawable.home_radius_filter_black)

            holder.filterTV.setTextColor(Color.parseColor("#979797"))
        }

        holder.filterLL.setOnClickListener(View.OnClickListener {


            mCallback!!.setOnFilter(""+position) })


        mCallback!!.setCancelFilter(""+adapterModels[position].categoryName)
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnFilter(id: String?)
        fun setCancelFilter(id: String?)
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