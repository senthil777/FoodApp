package com.lieferin_global.Adapter

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import java.text.DecimalFormat

class DetailedItemAddViewAdapter(context: Context, adapterModels: List<Product>) : RecyclerView.Adapter<DetailedItemAddViewAdapter.MyViewHolder>() {
    private val adapterModels: List<Product>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true

    var itemView: View? = null

    var dbHelper: DBHelper? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var add_item_minus: ImageView

        var add_item_plus: ImageView

        var add_item_name:TextView

        var add_item_price:TextView

        var add_item_namePrice:TextView

        var add_item_increase:TextView

        var addNote : TextView

        var add_item_namePriceDetailsEdit : TextView

        var detail : RelativeLayout

        var add_item_namePriceDetails : TextView


        init {

            add_item_minus = itemView.findViewById(R.id.add_item_minus)

            add_item_plus = itemView.findViewById(R.id.add_item_plus)

            add_item_name = itemView.findViewById(R.id.add_item_name)

            add_item_price = itemView.findViewById(R.id.add_item_price)

            add_item_namePrice = itemView.findViewById(R.id.add_item_namePrice)

            add_item_increase = itemView.findViewById(R.id.add_item_increase)

            addNote = itemView.findViewById(R.id.addNote)

            detail = itemView.findViewById(R.id.detail)

            add_item_namePriceDetails = itemView.findViewById(R.id.add_item_namePriceDetails)

            add_item_namePriceDetailsEdit = itemView.findViewById(R.id.add_item_namePriceDetailsEdit)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_add_layout, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        dbHelper = DBHelper(context)

        holder.add_item_name.setTypeface(fontStyle(context, ""))

        holder.add_item_name.setText(adapterModels[position].name)

        holder.add_item_namePrice.setTypeface(fontStyle(context, "Light"))

        holder.add_item_namePrice.setText(adapterModels[position].categoryId)

        holder.add_item_namePriceDetails.setTypeface(fontStyle(context, "Light"))

        holder.add_item_namePriceDetails.setText(adapterModels[position].categoryId)

        holder.add_item_namePriceDetailsEdit.setTypeface(fontStyle(context, "Light"))

        holder.add_item_namePriceDetailsEdit.setOnClickListener(View.OnClickListener {

            mCallback!!.setOnEdit(""+adapterModels[position].menuReferenceCode)
        })

        if(adapterModels[position].categoryId.equals(""))
        {
            holder.detail.visibility = View.GONE
        }

        holder.detail.setOnClickListener(View.OnClickListener {
            holder.add_item_namePrice.visibility = View.VISIBLE
        })

        holder.add_item_price.setTypeface(fontStyle(context, "Light"))

        //var totalValue = addIncreasePrice(adapterModels[position].menuId,adapterModels[position].offerPrice)

       var totalValue = addIncreasePriceHole(""+adapterModels[position].menuId,adapterModels[position].price!!)

        holder.add_item_price.setText("€ "+DecimalFormat("##.##").format(totalValue))

        holder.add_item_increase.setTypeface(fontStyle(context, ""))

        holder.add_item_increase.setText(adapterModels[position].price)

        holder.add_item_minus.setColorFilter(colorIcon(context,R.color.colorGray,R.drawable.minus,holder.add_item_minus), PorterDuff.Mode.SRC_ATOP
        )

        holder.add_item_plus.setColorFilter(colorIcon(context,R.color.colorGreen,R.drawable.plus,holder.add_item_plus), PorterDuff.Mode.SRC_ATOP
        )

        holder.add_item_minus.setOnClickListener(View.OnClickListener {

            if(addDecrease(holder.add_item_increase.text as String) > 0) {

                holder.add_item_price.text = "€ " +DecimalFormat("##.##").format(minusPriceDouble(
                    holder.add_item_price.text as String,
                    ""+adapterModels[position].menuId).toString().toDouble())

                holder.add_item_increase.text =
                    addDecrease(holder.add_item_increase.text as String).toString()

                mCallback!!.setOnMaterial(adapterModels[position],true,holder.add_item_price.text.toString().replace("€ ",""),holder.add_item_increase.text.toString().toInt())


                //holder.add_item_namePrice.setText(holder.add_item_increase.text as String+" * "+adapterModels[position].ratingText+" = "+holder.add_item_price.text as String)
            }else{

                mCallback!!.setOnAddText(adapterModels[position].menuReferenceCode)
            }
        })

        holder.add_item_plus.setOnClickListener(View.OnClickListener {

            holder.add_item_price.text = "€ "+ DecimalFormat("##.##").format(addPriceDouble(""+adapterModels[position].menuId,
                addIncreaseDoubleValue(holder.add_item_increase.text as String)).toString().toDouble())

            holder.add_item_increase.text = addIncrease(holder.add_item_increase.text as String).toString()

            mCallback!!.setOnMaterial(adapterModels[position],true,holder.add_item_price.text.toString().replace("€ ",""),holder.add_item_increase.text.toString().toInt())

            //holder.add_item_namePrice.setText(holder.add_item_increase.text as String+" * "+adapterModels[position].ratingText+" = "+holder.add_item_price.text as String)

        })

        if(adapterModels[position].typeView.equals(""))
        {
           holder.addNote!!.visibility = View.VISIBLE
        }else{
           holder.addNote!!.visibility = View.GONE
        }

        holder.addNote.typeface = fontStyle(context,"Light")

        holder.addNote.setOnClickListener(View.OnClickListener {

            val dialog = Dialog(context!!)
            val decorView = dialog
                .window!!
                .getDecorView()
            val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                decorView,
                PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
                PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
                PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f)
            )
            scaleDown.duration = 400
            scaleDown.start()
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.add_note)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val title = dialog.findViewById<View>(R.id.title) as TextView

            title!!.typeface = fontStyle(context,"SemiBold")

            val noteEdit = dialog.findViewById<View>(R.id.noteEdit) as TextView

            noteEdit!!.typeface = fontStyle(context,"")

            val cancelText = dialog.findViewById<View>(R.id.cancelText) as TextView

            cancelText!!.typeface = fontStyle(context,"")

            val submitText = dialog.findViewById<View>(R.id.submitText) as TextView

            submitText!!.typeface = fontStyle(context,"")

            val closeIcon = dialog.findViewById<View>(R.id.closeIcon) as ImageView

            dialog.show()

            cancelText.setOnClickListener(View.OnClickListener { dialog.cancel() })

            submitText.setOnClickListener(View.OnClickListener {

                if(!noteEdit!!.text.equals("")) {

                    holder.addNote.text = noteEdit!!.text

                    dbHelper!!.updatedetailsNotes(adapterModels[position].menuReferenceCode!!,""+noteEdit!!.text)

                    holder.addNote.setTextColor(Color.parseColor("#484848"))

                    holder.addNote.setBackgroundResource(R.drawable.home_radius_button_gray_back)
                }else{

                    holder.addNote.text = "Add Notes"

                    dbHelper!!.updatedetailsNotes(adapterModels[position].menuReferenceCode!!,"")

                    holder.addNote.setTextColor(Color.parseColor("#7DC77D"))

                    holder.addNote.setBackgroundResource(0)
                }
                dialog.cancel() })

            closeIcon.setOnClickListener(View.OnClickListener { dialog.cancel() })
        })

        /*holder.cardView.setOnClickListener {

            //mCallback!!.setOnMaterial(adapterModels[position], true, "", position)
        }*/
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnMaterial(userId: Product?, isTrue: Boolean, id: String?, position: Int)

        fun setOnAddText(addText: String?)

        fun setOnEdit(addText: String?)
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