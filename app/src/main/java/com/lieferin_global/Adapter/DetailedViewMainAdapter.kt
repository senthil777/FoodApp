package com.lieferin_global.Adapter

import android.content.Context
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.ProductListView
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.pos
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class DetailedViewMainAdapter(context: Context, adapterModels: List<AdapterModel>) :
    RecyclerView.Adapter<DetailedViewMainAdapter.MyViewHolder>(),DetailedViewAdapter.CallbackDataAdapter {
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

        var details_item_icon: CircleImageView

        var details_item_name: TextView

        var details_item_arrow: ImageView

        var details_item_RecyclerView: RecyclerView

        var visibleView : View

        init {

            visibleView = itemView.findViewById(R.id.visibleView)

            details_item_icon = itemView.findViewById(R.id.details_item_icon)

            details_item_name = itemView.findViewById(R.id.details_item_name)

            details_item_arrow = itemView.findViewById(R.id.details_item_arrow)

            details_item_RecyclerView = itemView.findViewById(R.id.details_item_RecyclerView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.details_menu, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(adapterModels.get(position).imageOne == 1)
        {
            holder.visibleView.visibility = View.INVISIBLE
        }else{
            holder.visibleView.visibility = View.VISIBLE
        }

        holder.details_item_name.typeface =
            fontStyle(context, "SemiBold")

        holder.details_item_name.text = adapterModels.get(position).categoryName

        if(!adapterModels.get(position).price.equals("")) {

            Picasso.with(context).load(adapterModels.get(position).price).resize(45, 45)
                .into(holder.details_item_icon)
        }

        holder.details_item_arrow.setColorFilter(
            colorIcon(
                context,
                R.color.redColor,
                R.drawable.arrow,
                holder.details_item_arrow
            ), PorterDuff.Mode.SRC_ATOP
        )

        Log.v("KKK","==="+adapterModels.get(position).offerPercentage);

        valPosition = ""+position

        valPosition1 = adapterModels.get(position).offerPercentage!!
        /*for (i in 0 until adapterModels.get(position).menusList.size) {
            for (j in 0 until adapterModels.get(position).menusList.get(i).toppinsGroupList.size) {
                for (k in 0 until adapterModels.get(position).menusList.get(i).toppinsGroupList.get(j).toppinsList.size) {
                    if (adapterModels.get(position).menusList.get(i).toppinsGroupList.get(j).toppinsList.get(k).typeView == null) {
                        adapterModels.get(position).menusList.get(i).toppinsGroupList.get(j).toppinsList.get(k).typeView= "1"
                    }
                }
            }
        }*/


            val categoryItemRecyclerView = DetailedViewAdapter(
                context,
                adapterModels.get(position).menusList,
                "" + valPosition,
                "" + valPosition1,""+adapterModels.get(position).closeTime
            )
            holder.details_item_RecyclerView.setHasFixedSize(true)
            holder.details_item_RecyclerView.setLayoutManager(
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            )

            holder.details_item_RecyclerView.isNestedScrollingEnabled = false

            categoryItemRecyclerView.setCallback(this)

            holder.details_item_RecyclerView.setAdapter(categoryItemRecyclerView)


        /*if(adapterModels.get(position).offerPrice.equals("0"))
        {
            holder.details_item_RecyclerView.visibility = View.VISIBLE

        }else{
            holder.details_item_RecyclerView.visibility = View.GONE
        }*/

       /* holder.cardViewLayout.setOnClickListener(View.OnClickListener {
            pos = position
            if (holder.details_item_RecyclerView.getVisibility() != View.VISIBLE) {
                holder.details_item_arrow.animate().rotation(90F)
                    .setDuration(500).start();

                //holder.details_item_RecyclerView.visibility = View.VISIBLE
                mCallback!!.setOnItemClose("ItemClose",position,"1")
            }else{
                holder.details_item_arrow.animate().rotation(0F)
                    .setDuration(500).start();

                //holder.details_item_RecyclerView.visibility = View.GONE
                mCallback!!.setOnItemClose("ItemClose",position,"0")
            }

        })*/


    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {

        fun setOnItemClose(id: String?,position:Int?,isClose:String?)

        fun setOnDetail(id: String?,position:Int?)

        fun setOnInfo(id: String?,position:String?)

        fun setOnQuantity(id: String?, pos: String?)

        fun setOnPopup(adapterModels: List<ProductListView>,position:Int?)

        fun setOnCancel(type:String?,position:Double?,isOpen:String?,pos:String?)

        fun setOnPriceAdd(type:String?,itemName:String?,price:String?,position:Int?)

        fun setFinal(type: String?, position: Double?, isOpen: String?,posValue:String?)

        fun setDelete(type: String?, position: Double?, isOpen: String?, pos: String?)
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



    override fun setOnDetail(id: String?,position:Int?) {

        mCallback!!.setOnDetail(id,pos!!)
    }

    override fun setOnInfo(id: String?,position:String?) {
        Log.v("jjjjj "+id!!,"ppppp "+position!!);
        mCallback!!.setOnInfo(id,position)
    }

    override fun setOnQuantity(id: String?, pos: String?) {
        mCallback!!.setOnQuantity(id, pos!!)
    }

    override fun setOnPopup(adapterModels: List<ProductListView>, position:Int?) {
        mCallback!!.setOnPopup(adapterModels,pos!!)
    }

    override fun setOnCancel(type: String?, position: Double?, isOpen: String?,posValue:String?) {

        mCallback!!.setOnCancel(type,position,""+isOpen,posValue)

    }

    override fun setOnPriceAdd(type: String?, itemName: String?, price: String?,position:Int?) {

        mCallback!!.setOnPriceAdd(type,""+itemName,""+price,pos!!)
    }

    override fun setFinal(type: String?, position: Double?, isOpen: String?, pos: String?) {

        mCallback!!.setFinal(type,position,""+isOpen,pos)
    }

    override fun setDelete(type: String?, position: Double?, isOpen: String?, pos: String?) {
        mCallback!!.setDelete(type,position,""+isOpen,pos)
    }
}