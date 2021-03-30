package com.lieferin_global.Adapter

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Model.HomePageModel
import com.lieferin_global.Model.ProductListView
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.Constant.distance
import com.lieferin_global.Utility.Constant.price
import com.squareup.picasso.Picasso


class DashBoardAdapter(val data: MutableList<HomePageModel>, val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>(), RecommendedAdapter.CallbackDataAdapter,
    FeaturedAdapter.CallbackDataAdapter, FamousAdapter.CallbackDataAdapter,
    ViewAllDishAdapter.CallbackDataAdapter, DetailedViewMainAdapter.CallbackDataAdapter,
    FilterPageAdapter.CallbackDataAdapter,FeaturedStoreAdapter.CallbackDataAdapter,TitleCategoryPageAdapter.CallbackDataAdapter {
    private var listener: OnClickDashBoard? = null
    private val dataSet: MutableList<HomePageModel>
    var mContext: Context
    var total_types: Int
    fun onItem(id: String?) {
        listener!!.onItem(id, "", "", 0)
    }

    class CategoryIconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var recommendedTV: TextView

        var viewAllTV: TextView

        var offerTV: TextView

        var restName: TextView

        var dashBoardAddress: TextView

        var orderNow: TextView

        var recommendedRecyclerView: RecyclerView

        var favoriteImg: ImageView

        var storeImg: ImageView

        var restaurantDescriptionTV : TextView

        init {

            restaurantDescriptionTV = itemView.findViewById(R.id.restaurantDescriptionTV)

            recommendedTV = itemView.findViewById(R.id.recommendedTV)

            viewAllTV = itemView.findViewById(R.id.viewAllTV)

            offerTV = itemView.findViewById(R.id.offerTV)

            restName = itemView.findViewById(R.id.restName)

            favoriteImg = itemView.findViewById(R.id.favoriteImg)

            dashBoardAddress = itemView.findViewById(R.id.dashBoardAddress)

            orderNow = itemView.findViewById(R.id.orderNow)

            storeImg = itemView.findViewById(R.id.storeImg)

            recommendedRecyclerView = itemView.findViewById(R.id.recommendedRecyclerView)
        }
    }

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var featuredTV: TextView

        var viewAllTV: TextView

        var featuredRecyclerView: RecyclerView

        var restaurantDescriptionTV : TextView

        init {

            restaurantDescriptionTV = itemView.findViewById(R.id.restaurantDescriptionTV)

            featuredTV = itemView.findViewById(R.id.featuredTV)

            viewAllTV = itemView.findViewById(R.id.viewAllTV)

            featuredRecyclerView = itemView.findViewById(R.id.featuredRecyclerView)

        }

    }

    class BrowseByCategory(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var restaurantFamousTV: TextView

        var viewAllTV: TextView

        var famousRecyclerView: RecyclerView

        var restaurantDescriptionTV : TextView

        init {
            restaurantFamousTV = itemView.findViewById(R.id.restaurantFamousTV)

            viewAllTV = itemView.findViewById(R.id.viewAllTV)

            restaurantDescriptionTV = itemView.findViewById(R.id.restaurantDescriptionTV)

            famousRecyclerView = itemView.findViewById(R.id.famousRecyclerView)

        }
    }

    class StoreByCategory(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var featuredTV: TextView

        var viewAllTV: TextView

        var featuredRecyclerView: RecyclerView

        init {
            featuredTV = itemView.findViewById(R.id.featuredTV)

            viewAllTV = itemView.findViewById(R.id.viewAllTV)

            featuredRecyclerView = itemView.findViewById(R.id.featuredRecyclerView)

        }
    }

    class CategoryList(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryTV: TextView

        var viewAllTV: TextView

        var categoryRecyclerView: RecyclerView

        init {
            categoryTV = itemView.findViewById(R.id.categoryTV)

            viewAllTV = itemView.findViewById(R.id.viewAllTV)

            categoryRecyclerView = itemView.findViewById(R.id.categoryRecyclerView)

        }
    }

    class TopRated(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /*var details_item_icon: CircleImageView

        var details_item_name: TextView

        var details_item_arrow: ImageView
*/
        var details_RecyclerView: RecyclerView

        var detailsHorizontalRecyclerView: RecyclerView

        /*var cardViewLayout :CardView*/

        init {

            /*cardViewLayout = itemView.findViewById(R.id.cardViewLayout)

            details_item_icon = itemView.findViewById(R.id.details_item_icon)

            details_item_name = itemView.findViewById(R.id.details_item_name)

            details_item_arrow = itemView.findViewById(R.id.details_item_arrow)*/

            details_RecyclerView = itemView.findViewById(R.id.details_RecyclerView)

            detailsHorizontalRecyclerView =
                itemView.findViewById(R.id.detailsHorizontalRecyclerView)


        }

    }

    class TopDestination(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewAllRecyclerView: RecyclerView

        var restaurantDescriptionTV : TextView

        var restaurantFamousTV : TextView

        init {

            viewAllRecyclerView = itemView.findViewById(R.id.viewAllRecyclerView)

            restaurantDescriptionTV = itemView.findViewById(R.id.restaurantDescriptionTV)

            restaurantFamousTV = itemView.findViewById(R.id.restaurantFamousTV)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View

        val viewHolder: RecyclerView.ViewHolder = when (viewType) {
            HomePageModel.CATEGORY -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_of_restarunt, parent, false)
                return CategoryIconViewHolder(view)
            }

            HomePageModel.BANNER -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_of_featured, parent, false)
                return BannerViewHolder(view)
            }

            HomePageModel.OFFER -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.restaurent_famous_layout, parent, false)
                return BrowseByCategory(view)
            }

            HomePageModel.STORE -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_details_list, parent, false)
                return TopRated(view)
            }


            HomePageModel.SPECIAL_OFFER -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_all_dashboard, parent, false)
                return TopDestination(view)
            }

            HomePageModel.STORE_PRODUCT -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_of_featured, parent, false)
                return StoreByCategory(view)
            }
            HomePageModel.STORE_PRODUCT_SUB -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_of_category, parent, false)
                return CategoryList(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_of_restarunt, parent, false)
                return CategoryIconViewHolder(view)
            }
        }
        return viewHolder
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataSet[position].type) {
            0 -> HomePageModel.CATEGORY
            1 -> HomePageModel.BANNER
            2 -> HomePageModel.STORE
            3 -> HomePageModel.OFFER
            4 -> HomePageModel.SPECIAL_OFFER
            5 -> HomePageModel.STORE_PRODUCT
            6 -> HomePageModel.STORE_PRODUCT_SUB
            else -> -1
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, listPosition: Int) {
        val `object`: HomePageModel = dataSet[listPosition]
        if (`object` != null) {
            when (`object`.type) {
                HomePageModel.CATEGORY -> {
                    (holder as CategoryIconViewHolder).recommendedTV.typeface =
                        fontStyle(mContext, "SemiBold")

                    (holder as CategoryIconViewHolder).restaurantDescriptionTV.typeface =
                        fontStyle(mContext, "Light")

                    if(AppType!!.equals("0")) {
                        (holder as CategoryIconViewHolder).restaurantDescriptionTV.text =
                            "Got food delivered from these top restaurant"
                    }else{
                        (holder as CategoryIconViewHolder).restaurantDescriptionTV.text =
                            "Got food delivered from these top store"
                    }

                    (holder as CategoryIconViewHolder).viewAllTV.typeface =
                        fontStyle(mContext, "Light")

                    if(AppType.equals("0"))
                    {
                        (holder as CategoryIconViewHolder).viewAllTV.setTextColor(Color.parseColor("#ec272b"))
                    }else{
                        (holder as CategoryIconViewHolder).viewAllTV.setTextColor(Color.parseColor("#7DC77D"))
                    }

                    (holder as CategoryIconViewHolder).viewAllTV.setOnClickListener(View.OnClickListener {

                        if(AppType.equals("0")) {
                            listener!!.onItem(
                                "View All",
                                ""+mContext.getString(R.string.DashBoard_recommended),
                                "recommended",
                                0
                            )
                        }else{
                            listener!!.onItem(
                                "View All",
                                ""+mContext.getString(R.string.DashBoard_recommended),
                                "recommended",
                                0
                            )
                        }
                    })

                    (holder as CategoryIconViewHolder).offerTV.typeface =
                        fontStyle(mContext, "SemiBold")

                    //(holder as CategoryIconViewHolder).offerTV.text = ""+`object`.adapterModel.get(listPosition).kilometer

                    (holder as CategoryIconViewHolder).restName.typeface =
                        fontStyle(mContext, "SemiBold")



                    if (`object`.adapterModel.get(0).categoryName != null) {

                        (holder as CategoryIconViewHolder).restName.text =
                            `object`.adapterModel.get(0).categoryName
                    }

                    if (`object`.adapterModel.get(0).offerPrice != null) {

                        (holder as CategoryIconViewHolder).dashBoardAddress.text =
                            `object`.adapterModel.get(0).offerPrice
                    }

                    (holder as CategoryIconViewHolder).dashBoardAddress.typeface =
                        fontStyle(mContext, "Light")

                    (holder as CategoryIconViewHolder).orderNow.typeface =
                        fontStyle(mContext, "Light")

                    /*(holder as CategoryIconViewHolder).orderNow.setOnClickListener(View.OnClickListener {

                        distance =""+`object`.adapterModel.get(0).offer

                        price =""+`object`.adapterModel.get(0).parlourAddress

                        listener!!.onItem("Details Page", ""+`object`.adapterModel.get(0).offerPercentage, "1", 0)
                    })
*/
                    (holder as CategoryIconViewHolder).favoriteImg!!.setColorFilter(
                        colorIcon(
                            mContext,
                            R.color.colorWhite,
                            R.drawable.favorite,
                            (holder as CategoryIconViewHolder).favoriteImg!!
                        ),
                        PorterDuff.Mode.SRC_ATOP
                    )

                    (holder as CategoryIconViewHolder).favoriteImg!!.tag = "0"

                    if(AppType.equals("0")) {
                        val favString1 =
                            Constant.favStringValue.split(",")

                        for (i in 0 until favString1!!.size) {
                            if (favString1[i].equals(`object`.adapterModel.get(0).categoryReferenceCode)) {
                                (holder as CategoryIconViewHolder).favoriteImg!!.setColorFilter(
                                    colorIcon(
                                        context,
                                        R.color.colorWhite,
                                        R.drawable.heart_selected,
                                        (holder as CategoryIconViewHolder).favoriteImg!!
                                    )
                                )

                                (holder as CategoryIconViewHolder).favoriteImg!!.tag = "1"
                            }
                        }
                    }else{
                        val favString1 =
                            Constant.favStringGroceryValue.split(",")

                        for (i in 0 until favString1!!.size) {
                            if (favString1[i].equals(`object`.adapterModel.get(0).categoryReferenceCode)) {
                                (holder as CategoryIconViewHolder).favoriteImg!!.setColorFilter(
                                    colorIcon(
                                        context,
                                        R.color.colorWhite,
                                        R.drawable.heart_selected,
                                        (holder as CategoryIconViewHolder).favoriteImg!!
                                    )
                                )

                                (holder as CategoryIconViewHolder).favoriteImg!!.tag = "1"
                            }
                        }
                    }
                    (holder as CategoryIconViewHolder).favoriteImg!!.setOnClickListener(View.OnClickListener {

                        if((holder as CategoryIconViewHolder).favoriteImg!!.tag.equals("1")) {
                            listener!!.onItem(
                                "Add Favorite",
                                "" + `object`.adapterModel.get(0).offerPercentage,
                                "2",
                                0
                            )
                        }else{
                            listener!!.onItem(
                                "Add Favorite",
                                "" + `object`.adapterModel.get(0).offerPercentage,
                                "1",
                                0
                            )
                        }
                    })

                    var str = `object`.adapterModel.get(0).price

                    str = str!!.replace("]","").toString()
                    val arrOfStr =
                        str.split(",")

                    if (`object`.adapterModel.get(0).image != null) {

                        if(!AppType!!.equals("0")) {

                            Picasso.with(mContext)
                                .load(""+arrOfStr[0])
                                .transform(RoundedTransformation(12, 0))
                                .resize(250,250).placeholder(R.drawable.item_placeholder_geocery)
                                .into((holder as CategoryIconViewHolder).storeImg)
                        }else{
                            Picasso.with(mContext)
                                .load(arrOfStr[0])
                                .transform(RoundedTransformation(12, 0)).placeholder(R.drawable.restaurant_placeholder)
                                .into((holder as CategoryIconViewHolder).storeImg)
                        }
                    }

                    (holder as CategoryIconViewHolder).storeImg.setOnClickListener(View.OnClickListener {
                        distance =""+`object`.adapterModel.get(0).offer

                        price =""+`object`.adapterModel.get(0).parlourAddress
                        listener!!.onItem("Banner", ""+`object`.adapterModel.get(0).offerPercentage, "1", 0)
                    })


                    val categoryItemRecyclerView = RecommendedAdapter(
                        mContext,
                        `object`.adapterModel.get(0).menusList
                    )
                    (holder as CategoryIconViewHolder).recommendedRecyclerView.setHasFixedSize(true)
                    (holder as CategoryIconViewHolder).recommendedRecyclerView.setLayoutManager(
                        LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                    )
                    (holder as CategoryIconViewHolder).recommendedRecyclerView.isNestedScrollingEnabled =
                        false

                    categoryItemRecyclerView.setCallback(this)
                    (holder as CategoryIconViewHolder).recommendedRecyclerView.setAdapter(
                        categoryItemRecyclerView
                    )
                }
                HomePageModel.BANNER -> {
                    //binding the data with the viewholder views

                    (holder as BannerViewHolder).featuredTV.typeface =
                        fontStyle(mContext, "SemiBold")

                    (holder as BannerViewHolder).viewAllTV.typeface = fontStyle(mContext, "Light")

                    if(AppType.equals("0"))
                    {
                        (holder as BannerViewHolder).viewAllTV.setTextColor(Color.parseColor("#ec272b"))
                    }else{
                        (holder as BannerViewHolder).viewAllTV.setTextColor(Color.parseColor("#7DC77D"))
                    }

                    (holder as BannerViewHolder).restaurantDescriptionTV.typeface = fontStyle(mContext, "Light")

                    (holder as BannerViewHolder).viewAllTV.setOnClickListener(View.OnClickListener {
                        listener!!.onItem(
                            "View All",
                            ""+mContext.getString(R.string.DashBoard_featured),
                            "",
                            0
                        )
                    })

                    val categoryItemRecyclerView = FeaturedAdapter(mContext, `object`.adapterModel)
                    (holder as BannerViewHolder).featuredRecyclerView.setHasFixedSize(true)
                    (holder as BannerViewHolder).featuredRecyclerView.setLayoutManager(
                        LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                    )
                    (holder as BannerViewHolder).featuredRecyclerView.isNestedScrollingEnabled =
                        false

                    categoryItemRecyclerView.setCallback(this)
                    (holder as BannerViewHolder).featuredRecyclerView.setAdapter(
                        categoryItemRecyclerView
                    )


                }

                HomePageModel.STORE_PRODUCT -> {
                    //binding the data with the viewholder views

                    (holder as StoreByCategory).featuredTV.typeface =
                        fontStyle(mContext, "SemiBold")

                    (holder as StoreByCategory).featuredTV.text = "Featured Store"

                    (holder as StoreByCategory).viewAllTV.typeface = fontStyle(mContext, "Light")

                    if(AppType.equals("0"))
                    {
                        (holder as StoreByCategory).viewAllTV.setTextColor(Color.parseColor("#ec272b"))
                    }else{
                        (holder as StoreByCategory).viewAllTV.setTextColor(Color.parseColor("#7DC77D"))
                    }

                    (holder as StoreByCategory).viewAllTV.setOnClickListener(View.OnClickListener {
                        listener!!.onItem(
                            "View All",
                            "Featured Store",
                            "",
                            0
                        )
                    })

                    val categoryItemRecyclerView = FeaturedStoreAdapter(mContext, `object`.adapterModel)
                    (holder as StoreByCategory).featuredRecyclerView.setHasFixedSize(true)
                    (holder as StoreByCategory).featuredRecyclerView.setLayoutManager(GridLayoutManager(context, 2))
                    (holder as StoreByCategory).featuredRecyclerView.isNestedScrollingEnabled =
                        false

                    categoryItemRecyclerView.setCallback(this)
                    (holder as StoreByCategory).featuredRecyclerView.setAdapter(
                        categoryItemRecyclerView
                    )


                }
                HomePageModel.STORE_PRODUCT_SUB -> {
                    //binding the data with the viewholder views

                    (holder as CategoryList).categoryTV.typeface =
                        fontStyle(mContext, "SemiBold")

                    //(holder as CategoryList).categoryTV.text = ""

                    (holder as CategoryList).viewAllTV.typeface = fontStyle(mContext, "Light")

                    if(AppType.equals("0"))
                    {
                        (holder as CategoryList).viewAllTV.setTextColor(Color.parseColor("#ec272b"))
                    }else{
                        (holder as CategoryList).viewAllTV.setTextColor(Color.parseColor("#7DC77D"))
                    }

                    (holder as CategoryList).viewAllTV.setOnClickListener(View.OnClickListener {
                        listener!!.onItem(
                            "View All Category",
                            "Featured Store",
                            "",
                            0
                        )
                    })

                    val categoryItemRecyclerView = TitleCategoryPageAdapter(mContext, `object`.adapterModel)
                    (holder as CategoryList).categoryRecyclerView.setHasFixedSize(true)
                    val mLayoutManager2: RecyclerView.LayoutManager =
                        LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                    (holder as CategoryList).categoryRecyclerView.setLayoutManager(mLayoutManager2)
                    (holder as CategoryList).categoryRecyclerView.isNestedScrollingEnabled =
                        false

                    categoryItemRecyclerView.setCallback(this)
                    (holder as CategoryList).categoryRecyclerView.setAdapter(
                        categoryItemRecyclerView
                    )


                }

                HomePageModel.OFFER -> {

                    (holder as BrowseByCategory).restaurantFamousTV.typeface =
                        fontStyle(mContext, "SemiBold")

                    (holder as BrowseByCategory).viewAllTV.typeface = fontStyle(mContext, "Light")

                    if(AppType.equals("0"))
                    {
                        (holder as BrowseByCategory).viewAllTV.setTextColor(Color.parseColor("#ec272b"))
                    }else{
                        (holder as BrowseByCategory).viewAllTV.setTextColor(Color.parseColor("#7DC77D"))
                    }

                    (holder as BrowseByCategory).restaurantDescriptionTV.typeface = fontStyle(mContext, "Light")

                    val categoryItemRecyclerView = FamousAdapter(mContext, `object`.adapterModel)
                    (holder as BrowseByCategory).famousRecyclerView.setHasFixedSize(true)
                    (holder as BrowseByCategory).famousRecyclerView.setLayoutManager(
                        LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                    )
                    (holder as BrowseByCategory).famousRecyclerView.isNestedScrollingEnabled = false

                    categoryItemRecyclerView.setCallback(this)
                    (holder as BrowseByCategory).famousRecyclerView.setAdapter(
                        categoryItemRecyclerView
                    )

                }
                HomePageModel.STORE -> {

                    /*(holder as TopRated).details_item_name.typeface =
                        fontStyle(mContext, "SemiBold")

                    Picasso.with(mContext).load(R.drawable.img_2).resize(45, 45)
                        .into((holder as TopRated).details_item_icon)

                    (holder as TopRated).details_item_arrow.setColorFilter(
                        colorIcon(
                            mContext,
                            R.color.redColor,
                            R.drawable.arrow,
                            (holder as TopRated).details_item_arrow
                        ), PorterDuff.Mode.SRC_ATOP
                    )
*/
                    val categoryItemRecyclerView =
                        DetailedViewMainAdapter(mContext, `object`.adapterModel)
                    (holder as TopRated).details_RecyclerView.setHasFixedSize(true)
                    (holder as TopRated).details_RecyclerView.setLayoutManager(
                        LinearLayoutManagerWithSmoothScroller(mContext)
                    )

                    (holder as TopRated).details_RecyclerView.isNestedScrollingEnabled = false

                    categoryItemRecyclerView.setCallback(this)

                    (holder as TopRated).details_RecyclerView.setAdapter(categoryItemRecyclerView)

                    (holder as TopRated).details_RecyclerView!!.smoothScrollToPosition(2)


                    val filtersidePageAdapter = FilterPageAdapter(mContext, `object`.adapterModel)
                    (holder as TopRated).detailsHorizontalRecyclerView.setHasFixedSize(true)
                    (holder as TopRated).detailsHorizontalRecyclerView.setLayoutManager(
                        LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                    )

                    (holder as TopRated).detailsHorizontalRecyclerView.isNestedScrollingEnabled =
                        false

                    filtersidePageAdapter.setCallback(this)

                    (holder as TopRated).detailsHorizontalRecyclerView.setAdapter(
                        filtersidePageAdapter
                    )


                    /*(holder as TopRated).cardViewLayout.setOnClickListener(View.OnClickListener {
                        if ((holder as TopRated).details_item_RecyclerView.getVisibility() != View.VISIBLE) {
                            (holder as TopRated).details_item_arrow.animate().rotation(90F)
                                .setDuration(500).start();

                            (holder as TopRated).details_item_RecyclerView.visibility = View.VISIBLE
                        }else{
                            (holder as TopRated).details_item_arrow.animate().rotation(0F)
                                .setDuration(500).start();

                            (holder as TopRated).details_item_RecyclerView.visibility = View.GONE
                        }
                    })*/

                }

                HomePageModel.OFFER -> {


                }

                HomePageModel.SPECIAL_OFFER -> {

                    if(AppType.equals("0")) {

                        (holder as TopDestination).restaurantFamousTV.text =
                            "" + `object`.adapterModel.size + " restaurants available near you"
                    }else{
                        (holder as TopDestination).restaurantFamousTV.text =
                            "" + `object`.adapterModel.size + " store available near you"
                    }

                    (holder as TopDestination).restaurantFamousTV.typeface = fontStyle(mContext,"SemiBold")

                    (holder as TopDestination).restaurantDescriptionTV.typeface = fontStyle(mContext,"Light")
                    val viewAllDishAdapter = ViewAllDishAdapter(
                        mContext,
                        `object`.adapterModel
                    )
                    (holder as TopDestination).viewAllRecyclerView.setHasFixedSize(true)
                    (holder as TopDestination).viewAllRecyclerView.setLayoutManager(
                        LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
                    )
                    (holder as TopDestination).viewAllRecyclerView.isNestedScrollingEnabled =
                        false

                    viewAllDishAdapter.setCallback(this)
                    (holder as TopDestination).viewAllRecyclerView.setAdapter(
                        viewAllDishAdapter
                    )
                }
            }
        }
    }


    interface OnClickDashBoard {
        fun onItem(type: String?, id: String?, view: String?, postion: Int)
    }

    fun SetOnItemClickListener(mItemClickListener: OnClickDashBoard?) {
        listener = mItemClickListener
    }

    init {
        dataSet = data
        mContext = context
        total_types = dataSet.size
    }

    override fun getItemCount() = dataSet.size
    override fun setOnMaterial(id: String?) {

        listener!!.onItem("Details Page", ""+id, "1", 0)
    }

    override fun setOnDetails(id: String?, id1: String?) {
        listener!!.onItem("Add Favorite", ""+id, ""+id1, 0)
    }

    override fun setOnItemClose(id: String?, position: Int?, isClose: String?) {
        listener!!.onItem(id, "" + position, "" + isClose, 0)
    }

    override fun setOnDetail(id: String?, adpPosition: Int?) {

        listener!!.onItem("View ALL", "", "", adpPosition!!)
    }

    override fun setOnInfo(id: String?, adpPosition: String?) {

       // listener!!.onItem("" + id, "", "", adpPosition!!)
    }

    override fun setOnQuantity(id: String?, pos: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setOnPopup(adapterModels: List<ProductListView>, adpPosition: Int?) {
        listener!!.onItem("Popup", "", "", adpPosition!!)
    }

    override fun setOnCancel(type: String?, position: Double?, isOpen: String?, adpPosition: String?) {

        listener!!.onItem("Cancel", "" + position, "" + isOpen, adpPosition!!.toInt())

    }

    override fun setOnPriceAdd(
        type: String?,
        itemName: String?,
        price: String?,
        adpPosition: Int?
    ) {

        listener!!.onItem(type, "" + itemName, "" + price, adpPosition!!)

    }

    override fun setFinal(type: String?, position: Double?, isOpen: String?, pos: String?)  {

        //listener!!.onItem("Final", "" + itemName, "" + price, adpPosition!!)

    }

    override fun setDelete(type: String?, position: Double?, isOpen: String?, pos: String?) {

    }

    override fun setOnFav(id: String?) {
        Log.v("mmmmm",""+id)
        listener!!.onItem("Details Page", ""+id, "1", 0)
    }

    override fun setOnCategoery(id: String?) {
        Log.v("mmmmm",""+id)
        listener!!.onItem(""+id, ""+id, "1", 0)
    }

    override fun setOnFamous(id: String?) {
        listener!!.onItem(id, "", "", 0)
    }

    override fun setOnViewAll(id: String?) {
        listener!!.onItem("Details Page", ""+id, "1", 0)
    }

    override fun setOnFilter(id: String?) {

        listener!!.onItem("Filter", "" + id!!, "", 0)

    }

    override fun setCancelFilter(id: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}