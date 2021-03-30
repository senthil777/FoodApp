package com.lieferin_global.Adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.Product
import com.lieferin_global.R

class MenuListPageAdapter(context: Context, private val adapterModels: List<Product>) : RecyclerView.Adapter<MenuListPageAdapter.MyViewHolder>() {
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    var favorite = 1
    //var month: String

    var selectedPosition = 0
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var dishListView: RadioButton

        var infoRB: ImageView

        var infoCB: ImageView

        var priceTV: TextView

        var dishListViewCheck: CheckBox

        var allergensLinearLayout : LinearLayout

        var title1: TextView

        var title2: TextView

        var title3: TextView

        var title4: TextView

        var title5: TextView

        var title6: TextView

        var title7: TextView

        var title8: TextView


        init {

            dishListView = itemView.findViewById(R.id.dishListView)

            infoRB = itemView.findViewById(R.id.infoRB)

            infoCB = itemView.findViewById(R.id.infoCB)

            priceTV = itemView.findViewById(R.id.priceTV)

            dishListViewCheck = itemView.findViewById(R.id.dishListViewCheck)

            allergensLinearLayout = itemView.findViewById(R.id.allergensLinearLayout)

            title1 = itemView.findViewById(R.id.title1)

            title2 = itemView.findViewById(R.id.title2)

            title3 = itemView.findViewById(R.id.title3)

            title4 = itemView.findViewById(R.id.title4)

            title5 = itemView.findViewById(R.id.title5)

            title6 = itemView.findViewById(R.id.title6)

            title7 = itemView.findViewById(R.id.title7)

            title8 = itemView.findViewById(R.id.title8)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.menu_list_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnRadio(userId: String?,position: String?)
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
}