package com.lieferin_global.Adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle


class CountryAdapter(context: Context, adapterModels: List<AdapterModel>, val dialog: Dialog) :
    RecyclerView.Adapter<CountryAdapter.MyViewHolder>() {
    private val adapterModels: List<AdapterModel>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String

    var valPosition = ""

    var valPosition1 = ""
    var isChecked = true
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var textValue: TextView

        init {

            textValue =  itemView.findViewById(R.id.textValue)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.language_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

       holder.textValue.text = adapterModels.get(position).categoryName

        holder.textValue.typeface = fontStyle(context,"")

        holder.textValue.setOnClickListener(View.OnClickListener { mCallback!!.setOnDetail(adapterModels.get(position).offerPercentage,adapterModels.get(position).price,adapterModels.get(position).offerPrice!!)

            dialog.cancel()})

    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {

        fun setOnDetail(id: String?,position:String?,positionValue:String?)

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