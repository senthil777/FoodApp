package com.lieferin_global.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.colorIcon
import com.lieferin_global.Utility.fontStyle

class AddPersoninDividualsAdapter(context: Context, adapterModels: List<Product>) : RecyclerView.Adapter<AddPersoninDividualsAdapter.MyViewHolder>() {
    private val adapterModels: List<Product>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null

    var groupName : String? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var personName:TextView

        var personMobile:TextView

        var deleteIMG : ImageView

        var editIMG : ImageView

        var personNameCheck : CheckBox

        var addPersonGroupList : LinearLayout

        init {

            personNameCheck = itemView.findViewById(R.id.personNameCheck)

            personName = itemView.findViewById(R.id.personName)

            personMobile = itemView.findViewById(R.id.personMobile)

            deleteIMG = itemView.findViewById(R.id.deleteIMG)

            editIMG = itemView.findViewById(R.id.editIMG)

            addPersonGroupList = itemView.findViewById(R.id.addPersonGroupList)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.add_layout_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.personName.setTypeface(fontStyle(context, ""))

        holder.personName.setText(adapterModels[position].name)

        holder.personMobile.setTypeface(fontStyle(context, "Light"))

        holder.personMobile.setText(adapterModels[position].menuId)

        holder.deleteIMG.setColorFilter(colorIcon(context,R.color.colorGreen,R.drawable.edit,holder.deleteIMG))

        holder.editIMG.setColorFilter(colorIcon(context,R.color.colorGreen,R.drawable.edit,holder.editIMG))

        holder.deleteIMG.setOnClickListener {

            mCallback!!.setOnMaterialEdit("Edit",position,adapterModels.get(position))
        }

        holder.editIMG.setOnClickListener {

            mCallback!!.setOnMaterialEdit("Edit",position,adapterModels.get(position))
        }

        if(adapterModels[position].categoryId!!.equals("1"))
        {
            holder.personNameCheck.isChecked = true
        }else{
            holder.personNameCheck.isChecked = false
        }

        holder.personNameCheck.setOnClickListener(View.OnClickListener {

            if(holder.personNameCheck.isChecked)
            {
                mCallback!!.setOnAddIndividuals("1",position)
            }else{
                mCallback!!.setOnAddIndividuals("0",position)
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
        fun setOnMaterialEdit(id: String?,position : Int,product: Product)

        fun setOnAddIndividuals(id: String?,position : Int)

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
        this.groupName = groupName
        this.context = context
    }
}