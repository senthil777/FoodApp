package com.lieferin_global.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
import com.lieferin_global.R
import com.lieferin_global.Utility.colorIcon
import com.lieferin_global.Utility.fontStyle
import java.util.ArrayList

class AddGroupAdapter(context: Context, adapterModels: List<AdapterModel>) : RecyclerView.Adapter<AddGroupAdapter.MyViewHolder>(),AddPersonAdapter.CallbackDataAdapter {
    private val adapterModels: List<AdapterModel>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var groupCheck : CheckBox

        var GrupName:TextView

        var GrupMember:TextView

        var name : ImageView

        var addTV : TextView

        var addIcon : ImageView

        var addLayout : LinearLayout

        var addPersonViewRecyclerView : RecyclerView

        init {

            groupCheck = itemView.findViewById(R.id.groupCheck)

            addLayout = itemView.findViewById(R.id.addLayout)

            GrupName = itemView.findViewById(R.id.GrupName)

            GrupMember = itemView.findViewById(R.id.GrupMember)

            name = itemView.findViewById(R.id.name)

            addPersonViewRecyclerView = itemView.findViewById(R.id.addPersonViewRecyclerView)

            addTV = itemView.findViewById(R.id.addTV)

            addIcon = itemView.findViewById(R.id.addIcon)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.group_list_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.GrupName.setTypeface(fontStyle(context, ""))

        holder.addTV.setTypeface(fontStyle(context, "Light"))

        holder.GrupName.setText(adapterModels[position].categoryName)

        holder.GrupMember.setTypeface(fontStyle(context, "Light"))

        holder.name.setColorFilter(colorIcon(context,R.color.colorBlack,R.drawable.arrow,holder.name))

        holder.addPersonViewRecyclerView!!.visibility = View.GONE

        holder.name!!.setOnClickListener(View.OnClickListener {

            //mCallback!!.setOnMaterialDetails(""+adapterModels[position].categoryName)
            if (holder.addPersonViewRecyclerView!!.getVisibility() != View.VISIBLE) {
                holder.name!!.animate().rotation(90F)
                    .setDuration(500).start();

                holder.addPersonViewRecyclerView!!.visibility = View.VISIBLE
            }else{
                holder.name!!.animate().rotation(0F)
                    .setDuration(500).start();

                holder.addPersonViewRecyclerView!!.visibility = View.GONE
            }
        })

        if(adapterModels[position].price.equals(""))
        {
            holder.addLayout.visibility = View.VISIBLE
        }else{
            holder.addLayout.visibility = View.GONE
        }

        holder.addLayout.setOnClickListener(View.OnClickListener {

            mCallback!!.setOnMaterialDetails(""+adapterModels[position].categoryName)
        })

        holder.addIcon!!.setColorFilter(colorIcon(context,R.color.colorWhite,R.drawable.add,holder.addIcon!!))

        var adapterProduct: MutableList<Product> = ArrayList()

        var adapterProductList: MutableList<ProductList> = ArrayList()

        var adapterModelProduct: Product

        for (i in 0 until adapterModels[position].menusList.size) {

            if(adapterModels[position].menusList.get(i).menuReferenceCode.equals(adapterModels[position].categoryName))
            {
                adapterModelProduct = Product(R.drawable.img_4, ""+adapterModels[position].menusList.get(i).name, ""+adapterModels[position].menusList.get(i).menuId, ""+adapterModels[position].menusList.get(i).price, ""+adapterModels[position].categoryName, "1", ""+adapterModels[position].menusList.get(i).categoryId, "Tatabad,Gandhipuram", "4.4", "Very Good", "3054 Ratings>", "5 Star Given by you", "1", "", "", "","",""+adapterModels[position].menusList.get(i).sample, adapterProductList)
                adapterProduct.add(adapterModelProduct)
            }

        }

        holder.GrupMember.setText("Member "+adapterProduct.size)

        val addPersonAdapter = AddPersonAdapter(context, adapterProduct)
        holder.addPersonViewRecyclerView!!.setHasFixedSize(true)
        holder.addPersonViewRecyclerView!!.setLayoutManager(
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        )
        holder.addPersonViewRecyclerView!!.isNestedScrollingEnabled = false

        addPersonAdapter!!.setCallback(this)

        holder.addPersonViewRecyclerView!!.setAdapter(addPersonAdapter)

        if(adapterModels[position].offer!!.equals("1"))
        {
            holder.groupCheck.isChecked = true
        }else{
            holder.groupCheck.isChecked = false
        }

        holder.groupCheck.setOnClickListener(View.OnClickListener {

            if(holder.groupCheck.isChecked)
            {
                mCallback!!.setOnGroudAdd("1",position)
            }else{
                mCallback!!.setOnGroudAdd("0",position)
            }
        })

    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnMaterialDetails(id: String?)

        fun setOnGroudAdd(id: String?,position : Int)

        fun setOnAdd(id: String?,position : Int,mobile : String?)

        fun setOnMaterial(id: String?,position : Int,product: Product)

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

    override fun setOnMaterial(id: String?, position: Int,product: Product) {

        mCallback!!.setOnMaterial(id,position,product)
    }

    override fun setOnAdd(id: String?, position: Int,mobile : String?) {
        mCallback!!.setOnAdd(""+id,position,"")
    }
}