package com.lieferin_global.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.AdapterModelGrocery
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.fontStyle
import java.util.ArrayList

class CategoryDetailsPageListingAdapter(context: Context, adapterModels: List<AdapterModelGrocery>) : RecyclerView.Adapter<CategoryDetailsPageListingAdapter.MyViewHolder>(),GroceryListItemAdapter.CallbackDataAdapter {
    private val adapterModels: List<AdapterModelGrocery>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        //var filterIM: ImageView

        var categoryName:TextView

        var detailsRecyclerView : RecyclerView

        /*var filterLL : LinearLayout*/

        init {

            //filterIM = itemView.findViewById(R.id.filterIM)

            //arrowIV = itemView.findViewById(R.id.arrowIV)

            categoryName = itemView.findViewById(R.id.categoryName)

            detailsRecyclerView = itemView.findViewById(R.id.detailsRecyclerView)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.category_list_details_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.categoryName.setTypeface(fontStyle(context, "SemiBold"))
        holder.categoryName.setText(adapterModels[position].categoryName)

        var adapterDetails: MutableList<AdapterModel> = ArrayList()
        lateinit var adapterModel: AdapterModel
        var adapterDetailChild: MutableList<Product> = ArrayList()
        for(i in 0 until adapterModels.get(position).menusList!!.size)
        {
            if(adapterModels.get(position).menusList!!.get(i).categoryId.equals(adapterModels[position].categoryImage)) {
                adapterModel = AdapterModel(
                    0,
                    "" + adapterModels.get(position).menusList!!.get(i).categoryName,
                    "" + adapterModels.get(position).menusList!!.get(i).price,
                    "" + adapterModels.get(position).menusList!!.get(i).offerPrice,
                    "" + adapterModels.get(position).menusList!!.get(i).offerPercentage,
                    "" + adapterModels.get(position).menusList!!.get(i).categoryImage,
                    "" + adapterModels.get(position).menusList!!.get(i).offer,
                    "" + adapterModels.get(position).menusList!!.get(i).parlourAddress!!,
                    "" + adapterModels.get(position).menusList!!.get(i).parlourRatingValue!!,
                    "" + adapterModels.get(position).menusList!!.get(i).categoryReferenceCode,
                    "" + adapterModels.get(position).menusList!!.get(i).parlourRatingCount!!,
                    "" + adapterModels.get(position).menusList!!.get(i).parlourGivenRating!!,
                    "" + adapterModels.get(position).menusList!!.get(i).categoryId,
                    "" + adapterModels.get(position).menusList!!.get(i).shopTotRate!!,
                    "" + adapterModels.get(position).menusList!!.get(i).menuImages!!,
                    "" + adapterModels.get(position).menusList!!.get(i).openTime!!,
                    "" + adapterModels.get(position).menusList!!.get(i).closeTime!!,
                    "" + adapterModels.get(position).menusList!!.get(i).noOfEmp!!,
                    0,
                    0,
                    0,
                    adapterDetailChild
                )
                adapterDetails.add(adapterModel)
            }

        }

        var dialogListPageAdapter = GroceryListItemAdapter(context,adapterDetails)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        holder.detailsRecyclerView!!.setLayoutManager(GridLayoutManager(context!!, 2))

        holder.detailsRecyclerView!!.itemAnimator!!.addDuration = 5000

        holder.detailsRecyclerView!!.isNestedScrollingEnabled = false

        dialogListPageAdapter!!.setCallback(this)

        holder.detailsRecyclerView!!.adapter = dialogListPageAdapter

        /*Picasso.with(context).load(adapterModels[position].categoryImage).placeholder(R.drawable.item_placeholder_geocery).into(holder.arrowIV)

        //holder.arrowIV.setColorFilter(colorIcon(context,R.color.colorWhite,adapterModels[position].image,holder.arrowIV))

        holder.filterLL.setOnClickListener(View.OnClickListener { mCallback!!.setOnFilter1(""+position) })*/
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnExtraItem(type: String?, id: String?)
        fun setOnFavouriteValue(isFav: AdapterModel?, id: String?,totalPrice : String)

        fun setOnProduct(isFav: String?, id: String?)
    }
    override fun setOnExtraItem(type: String?, id: String?) {

        mCallback!!.setOnExtraItem(type,  id)
    }

    override fun setOnFavouriteValue(isFav: AdapterModel?, id: String?,totalPrice : String) {

        mCallback!!.setOnFavouriteValue(isFav,  id,totalPrice)
    }

    override fun setOnProduct(isFav: String?, id: String?) {

        mCallback!!.setOnProduct(isFav,  id)
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