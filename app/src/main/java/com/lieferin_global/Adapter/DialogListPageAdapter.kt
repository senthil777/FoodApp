package com.lieferin_global.Adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle
import java.util.ArrayList

class DialogListPageAdapter(context: Context, private val adapterModels: List<AdapterModel>, private val dialog: Dialog) : RecyclerView.Adapter<DialogListPageAdapter.MyViewHolder>(),DishListPageAdapter.CallbackDataAdapter {
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    var favorite = 1
    //var month: String
    var itemView: View? = null

    var adapterTrendingSample: MutableList<AdapterModel> = ArrayList<AdapterModel>()

    var dialogListPageAdapter:DishListPageAdapter? =null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var titleText: TextView

        var requestText: TextView

        var dishList : RecyclerView


        init {

            titleText = itemView.findViewById(R.id.titleText)

            requestText = itemView.findViewById(R.id.requestText)

            dishList = itemView.findViewById(R.id.dishList)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.extras_list, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.titleText.typeface =
            fontStyle(context, "SemiBold")

        holder.titleText.text = adapterModels.get(position).categoryName

        holder.requestText.typeface =
            fontStyle(context, "Light")

        holder.requestText.text = adapterModels.get(position).price

        dialogListPageAdapter = DishListPageAdapter(context,adapterModels.get(position).menusList,dialog)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        holder.dishList!!.layoutManager = mLayoutManager

        holder.dishList!!.itemAnimator!!.addDuration = 5000

        dialogListPageAdapter!!.setCallback(this)

        holder.dishList!!.adapter = dialogListPageAdapter


    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnPopUp(type: String?,postion: String?)
        fun setOnFavourite(isFav: String?, id: String?)

    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = FADE_DURATION.toLong()
        view.startAnimation(anim)
    }

    fun getImageUri(inContext: Context, inImage: Bitmap?): Drawable {
        return BitmapDrawable(inContext.resources, inImage)
    }

    companion object {
        private const val FADE_DURATION = 2000
    }

    init {
        //month = month
        this.context = context
    }

    override fun setOnRadio(type: String?,position: String?) {

        mCallback!!.setOnPopUp(type,position)
    }

    override fun setOnFavourite(isFav: String?, id: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}