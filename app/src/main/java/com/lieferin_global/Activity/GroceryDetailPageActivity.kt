package com.lieferin_global.Activity

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.*
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.*
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONObject
import java.text.DecimalFormat
import java.util.*


class GroceryDetailPageActivity : Fragment(), CategoryDetailsPageAdapter.CallbackDataAdapter,
    ResponseListener, CategoryDetailsPageListingAdapter.CallbackDataAdapter, View.OnClickListener,
    DashBoardAdapter.OnClickDashBoard, DialogListPageAdapter.CallbackDataAdapter,
    DialogListItemAdapter.CallbackDataAdapter, FilterPageAdapter.CallbackDataAdapter,
    DetailedViewMainAdapter.CallbackDataAdapter {

    var bannerImg: ImageView? = null

    var back: ImageView? = null

    var dbHelper: DBHelper? = null

    var search: ImageView? = null

    var favorite: ImageView? = null

    var hotelIcon: CircleImageView? = null

    var hotelName: TextView? = null

    var priceValue = 0.0

    var adapterTrendingSample2: MutableList<ProductListView> = ArrayList<ProductListView>()

    var hotelDescription: TextView? = null

    var eyeImg: ImageView? = null

    var aboutUsTV: TextView? = null

    var starIV: ImageView? = null

    var ratingTV: TextView? = null

    var reviewTv: TextView? = null

    var timeTV: TextView? = null

    var deliveryTv: TextView? = null

    var priceTV: TextView? = null

    var priceDescriptionTv: TextView? = null

    var sortImg: ImageView? = null

    var sortTV: TextView? = null

    var filterImg: ImageView? = null

    var filterTV: TextView? = null

    var switchVeg: TextView? = null

    var specialTV: TextView? = null

    var seeAll: TextView? = null

    var minimum : Double? = 0.0

    var specialIV: ImageView? = null

    var specialRightIV: ImageView? = null

    var specialTitleTV: TextView? = null

    var specialPriceTV: TextView? = null

    var specialDescriptionTV: TextView? = null

    var priceLayout: RelativeLayout? = null

    var detailsRecyclerView: RecyclerView? = null

    var detailsHorizontalRecyclerView: RecyclerView? = null

    var adapterDetails: MutableList<AdapterModel> = ArrayList()

    var adapterDetails1: MutableList<AdapterModel> = ArrayList()

    var adapterDetailsCategory: MutableList<AdapterModelGrocery> = ArrayList()

    internal lateinit var adapterModelGrocery: AdapterModelGrocery

    internal lateinit var adapterModel: AdapterModel

    internal lateinit var productModel: Product

    var adapterDetailChild: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    var addItemTV: TextView? = null

    var addPrice: TextView? = null

    var addToBasket: TextView? = null

    var rootView: View? = null

    var adapterCategories: MutableList<Product> = ArrayList()

    var adapterCategories1: MutableList<Product> = ArrayList()

    var adapterCategories2: MutableList<Product> = ArrayList()

    internal lateinit var adapterProduct: Product

    internal lateinit var productList: ProductList

    var adapterTrendingSample: MutableList<AdapterModel> = ArrayList<AdapterModel>()

    var adapterTrendingItem: MutableList<AdapterModel> = ArrayList<AdapterModel>()

    fun DetailPageActivity() {}

    var callBlacklisting: CallBlacklisting? = null

    var dialogListPageAdapter: DialogListPageAdapter? = null

    var dialogListItemAdapter: DialogListItemAdapter? = null

    var adapter: DashBoardAdapter? = null

    var fullView: LinearLayout? = null

    var detailedViewMainAdapter: CategoryDetailsPageListingAdapter? = null

    var filterPageAdapter: CategoryDetailsPageAdapter? = null

    var sortLL: LinearLayout? = null

    var filterLL: LinearLayout? = null

    var title: String? = null

    var imageUrl: String? = null

    var groceryId: String? = null

    var aboutUsLinearLayout: LinearLayout? = null

    var favString: String = ""

    var selectFav: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_grocery_detail_page, container, false)

        bannerImg = rootView!!.findViewById(R.id.bannerImg) as ImageView

        aboutUsLinearLayout = rootView!!.findViewById(R.id.aboutUsLinearLayout) as LinearLayout

        aboutUsLinearLayout!!.setOnClickListener(this)

        dbHelper = DBHelper(activity)

        /*Picasso.with(activity!!)
            .load("https://alliancegrocerykart.com/Content/images/banner_13.jpg").resize(450, 450)
            .into(bannerImg)*/

        hotelIcon = rootView!!.findViewById(R.id.hotelIcon) as CircleImageView

        sortLL = rootView!!.findViewById(R.id.sortLL) as LinearLayout

        sortLL!!.setOnClickListener(this)

        filterLL = rootView!!.findViewById(R.id.filterLL) as LinearLayout

        filterLL!!.setOnClickListener(this)

        /*Picasso.with(activity!!)
            .load("https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/logo.png")
            .resize(450, 450).into(hotelIcon)*/

        back = rootView!!.findViewById(R.id.back) as ImageView

        back!!.setColorFilter(
            colorIcon(activity!!, R.color.colorWhite, R.drawable.abc_ic_ab_back_material, back!!),
            PorterDuff.Mode.SRC_ATOP
        )

        favorite = rootView!!.findViewById(R.id.favorite) as ImageView

        favorite!!.setColorFilter(
            colorIcon(activity!!, R.color.colorWhite, R.drawable.favorite, favorite!!),
            PorterDuff.Mode.SRC_ATOP
        )

        favorite!!.setOnClickListener(this)

        favorite!!.tag = "0"

        search = rootView!!.findViewById(R.id.search) as ImageView

        search!!.visibility = View.INVISIBLE

        search!!.setColorFilter(
            colorIcon(activity!!, R.color.colorWhite, R.drawable.search, search!!),
            PorterDuff.Mode.SRC_ATOP
        )

        eyeImg = rootView!!.findViewById(R.id.eyeImg) as ImageView

        eyeImg!!.setColorFilter(
            colorIcon(activity!!, R.color.colorGreen, R.drawable.eye, eyeImg!!),
            PorterDuff.Mode.SRC_ATOP
        )


        starIV = rootView!!.findViewById(R.id.starIV) as ImageView

        starIV!!.setColorFilter(
            colorIcon(activity!!, R.color.colorWhite, R.drawable.star, starIV!!),
            PorterDuff.Mode.SRC_ATOP
        )

        sortImg = rootView!!.findViewById(R.id.sortImg) as ImageView

        sortImg!!.setColorFilter(
            colorIcon(activity!!, R.color.colorBlack, R.drawable.sort, sortImg!!),
            PorterDuff.Mode.SRC_ATOP
        )

        filterImg = rootView!!.findViewById(R.id.filterImg) as ImageView

        //fullView = rootView!!.findViewById(R.id.fullView) as LinearLayout

        //fullView!!.setOnClickListener(View.OnClickListener {  })

        val bundle = this.arguments
        if (bundle != null) {
            title = bundle.getString("Title")

            Constant.DeliveryType = bundle.getString("Delivery Type").toString()

            Constant.DeliveryType = bundle.getString("Delivery Type").toString()
        }

        Log.v("Delivery Type"," = "+Constant.DeliveryType)

        filterImg!!.setColorFilter(
            colorIcon(activity!!, R.color.colorBlack, R.drawable.filter, filterImg!!),
            PorterDuff.Mode.SRC_ATOP
        )

        hotelName = rootView!!.findViewById(R.id.hotelName) as TextView

        hotelName!!.typeface = fontStyle(activity!!, "SemiBold")

        hotelDescription = rootView!!.findViewById(R.id.hotelDescription) as TextView

        hotelDescription!!.typeface = fontStyle(activity!!, "Light")

        aboutUsTV = rootView!!.findViewById(R.id.aboutUsTV) as TextView

        aboutUsTV!!.typeface = fontStyle(activity!!, "Light")

        ratingTV = rootView!!.findViewById(R.id.ratingTV) as TextView

        ratingTV!!.typeface = fontStyle(activity!!, "Light")

        priceLayout = rootView!!.findViewById(R.id.priceLayout) as RelativeLayout

        priceLayout!!.visibility = View.GONE

        reviewTv = rootView!!.findViewById(R.id.reviewTv) as TextView

        reviewTv!!.typeface = fontStyle(activity!!, "Light")

        deliveryTv = rootView!!.findViewById(R.id.deliveryTv) as TextView

        deliveryTv!!.typeface = fontStyle(activity!!, "Light")

        timeTV = rootView!!.findViewById(R.id.timeTV) as TextView

        timeTV!!.typeface = fontStyle(activity!!, "Light")

        priceTV = rootView!!.findViewById(R.id.priceTV) as TextView

        priceTV!!.typeface = fontStyle(activity!!, "Light")

        priceDescriptionTv = rootView!!.findViewById(R.id.priceDescriptionTv) as TextView

        priceDescriptionTv!!.typeface = fontStyle(activity!!, "Light")

        sortTV = rootView!!.findViewById(R.id.sortTV) as TextView

        sortTV!!.typeface = fontStyle(activity!!, "Light")

        filterTV = rootView!!.findViewById(R.id.filterTV) as TextView

        filterTV!!.typeface = fontStyle(activity!!, "Light")

        switchVeg = rootView!!.findViewById(R.id.switchVeg) as TextView

        switchVeg!!.typeface = fontStyle(activity!!, "Light")

        seeAll = rootView!!.findViewById(R.id.seeAll) as TextView

        seeAll!!.typeface = fontStyle(activity!!, "Light")

        specialTV = rootView!!.findViewById(R.id.specialTV) as TextView

        specialTV!!.typeface = fontStyle(activity!!, "SemiBold")

        specialTitleTV = rootView!!.findViewById(R.id.specialTitleTV) as TextView

        specialTitleTV!!.typeface = fontStyle(activity!!, "SemiBold")

        specialPriceTV = rootView!!.findViewById(R.id.specialPriceTV) as TextView

        specialPriceTV!!.typeface = fontStyle(activity!!, "Light")

        specialDescriptionTV = rootView!!.findViewById(R.id.specialDescriptionTV) as TextView

        specialDescriptionTV!!.typeface = fontStyle(activity!!, "Light")

        addToBasket = rootView!!.findViewById(R.id.addToBasket) as TextView

        addToBasket!!.setOnClickListener(this)

        addToBasket!!.typeface = fontStyle(activity!!, "Light")

        addItemTV = rootView!!.findViewById(R.id.addItemTV) as TextView

        addItemTV!!.typeface = fontStyle(activity!!, "Light")

        addPrice = rootView!!.findViewById(R.id.addPrice) as TextView

        addPrice!!.text = customText(activity!!, "€ 125.5 Plus Taxes", 7, "SemiBold", "")

        specialIV = rootView!!.findViewById(R.id.specialIV) as ImageView

        specialRightIV = rootView!!.findViewById(R.id.specialRightIV) as ImageView

        Picasso.with(activity!!)
            .load(R.drawable.img_5)
            .transform(RoundedTransformation(20, 0))
            .resize(450, 450)
            .into(specialIV)

        Picasso.with(activity!!)
            .load(R.drawable.img_4)
            .transform(RoundedTransformation(20, 0))
            .resize(450, 450)
            .into(specialRightIV)

        detailsRecyclerView = rootView!!.findViewById(R.id.detailsRecyclerView) as RecyclerView

        //mAdapterShip = HomeShipAdapter(this, topRate)

        detailedViewMainAdapter = CategoryDetailsPageListingAdapter(activity!!, adapterDetailsCategory)
        //detailsRecyclerView!!.setLayoutManager(GridLayoutManager(activity!!, 2))

        detailsRecyclerView!!.setLayoutManager(LinearLayoutManagerWithSmoothScroller(activity!!))

        detailedViewMainAdapter!!.setCallback(this)

        detailsRecyclerView!!.setAdapter(detailedViewMainAdapter)

        detailsHorizontalRecyclerView =
            rootView!!.findViewById(R.id.CategoryHorizontalRecyclerView) as RecyclerView

        filterPageAdapter = CategoryDetailsPageAdapter(activity!!, adapterDetails1)
        detailsHorizontalRecyclerView!!.setHasFixedSize(true)
        detailsHorizontalRecyclerView!!.setLayoutManager(
            LinearLayoutManager(
                activity!!,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )

        detailsHorizontalRecyclerView!!.isNestedScrollingEnabled = false

        filterPageAdapter!!.setCallback(this)

        detailsHorizontalRecyclerView!!.setAdapter(filterPageAdapter!!)

        //showDataTrending()

        //addProduct()

        //showDataProductExtras()

        showDataProduct()

        //showDataTrending1()

        wedServicePage()

        priceTotalLayout()
        return rootView
    }

    fun wedServicePage() {
        val obj = JSONObject()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)
        obj.put("groceryReferenceCode", "" + title)
        /*obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")*/
        obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)
        obj.put("distance", "50")
        RequestManager.setFetchGroceryData(activity, obj, this);

        Log.v("value   " + obj, "lllll")
    }

    private fun addProduct() {

        val list: MutableList<HomePageModel> = ArrayList<HomePageModel>()
        list.add(HomePageModel(HomePageModel.STORE, adapterDetails, 1))

        list.add(HomePageModel(HomePageModel.STORE, adapterDetails, 1))

        //list.add(new HomePageModel(HomePageModel.BANNER, productHorizontal, null,2));
        adapter = DashBoardAdapter(list, activity!!)
        adapter!!.SetOnItemClickListener(this)
        detailsRecyclerView!!.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }

    fun showDataTrending() {

        if (adapterDetails.size != 0) {
            adapterDetails.clear()
        }
        adapterModel = AdapterModel(
            0,
            "Ornare sed consequat nisl eget",
            "€ 16.19",
            "3 pec",
            "Deal ended soon",
            "€ 20.19",
            "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product03.jpg",
            "0",
            "0",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterDetailChild
        )
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "Ornare sed consequat nisl eget",
            "€ 11.19",
            "2 pec",
            "Deal ended soon",
            "€ 15.19",
            "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product04.jpg",
            "0",
            "0",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterDetailChild
        )
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "Ornare sed consequat nisl eget",
            "€ 25.19",
            "1   pec",
            "Deal ended soon",
            "€ 30.19",
            "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product05.jpg",
            "0",
            "0",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterDetailChild
        )
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "Ornare sed consequat nisl eget",
            "€ 16.19",
            "3 pec",
            "Deal ended soon",
            "€ 20.19",
            "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product06.jpg",
            "0",
            "0",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterDetailChild
        )
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "Ornare sed consequat nisl eget",
            "€ 11.19",
            "2 pec",
            "Deal ended soon",
            "€ 15.19",
            "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product07.jpg",
            "0",
            "0",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterDetailChild
        )
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "Ornare sed consequat nisl eget",
            "€ 25.19",
            "1   pec",
            "Deal ended soon",
            "€ 30.19",
            "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product09.jpg",
            "0",
            "0",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterDetailChild
        )
        adapterDetails.add(adapterModel)
        //hotDealsAdapter!!.notifyDataSetChanged()
    }


    fun showDataTrending1() {

        if (adapterDetails1.size != 0) {
            adapterDetails1.clear()
        }
        adapterModel = AdapterModel(
            R.drawable.list,
            "All Fruits",
            "€ 16.19",
            "3 pec",
            "Deal ended soon",
            "€ 20.19",
            "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product03.jpg",
            "0",
            "0",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterDetailChild
        )
        adapterDetails1.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.cleaning,
            "Household",
            "",
            "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002",
            "Save 60 %",
            " 976",
            " 425",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterDetailChild
        )
        adapterDetails1.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.fruit,
            "Fruits",
            "",
            "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002",
            "Save 60 %",
            " 976",
            " 425",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterDetailChild
        )
        adapterDetails1.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.olive,
            "Vegetable",
            "",
            "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002",
            "Save 60 %",
            " 976",
            " 425",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterDetailChild
        )
        adapterDetails1.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.stationary,
            "Stationary",
            "",
            "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002",
            "Save 60 %",
            " 976",
            " 425",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterDetailChild
        )
        adapterDetails1.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.fruit,
            "Fruits",
            "",
            "5, E Arokiasamy Rd,R S Puram West,Coimbatore,Tamil Nadu 641002",
            "Save 60 %",
            " 976",
            " 425",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterDetailChild
        )
        adapterDetails1.add(adapterModel)
        //hotDealsAdapter!!.notifyDataSetChanged()
    }

    fun showDataProduct() {

        if (adapterDetailChild.size != 0) {
            adapterDetailChild.clear()
        }
        productModel = Product(
            R.drawable.img_1,
            "Pizza Capricciosa",
            "https://media-cdn.tripadvisor.com/media/photo-s/0e/42/fd/d2/veg-starter.jpg",
            "3.50",
            "Klein 20cm 3.50 cm",
            "0",
            "1",
            "200 for two",
            "Klein 20cm 3.50 cm",
            "",
            "",
            "",
            "",
            "",
            "",
            "","","",
            adapterProductList
        )
        adapterDetailChild.add(productModel)

        productModel = Product(
            R.drawable.img_1,
            "Pizza Capricciosa",
            "https://ubfoody.com/wp-content/uploads/2017/02/Chicken-Spring-Roll.png",
            "3.50",
            "Klein 20cm 3.50 cm",
            "0",
            "1",
            "200 for two",
            "Klein 20cm 3.50 cm",
            "",
            "",
            "",
            "",
            "",
            "",
            "","","",
            adapterProductList
        )
        adapterDetailChild.add(productModel)

        productModel = Product(
            R.drawable.img_1,
            "Pizza Capricciosa",
            "https://www.bbcgoodfood.com/sites/default/files/recipe-collections/collection-image/2013/05/halloumi-carrot-orange-salad.jpg",
            "3.50",
            "Klein 20cm 3.50 cm",
            "0",
            "1",
            "200 for two",
            "Klein 20cm 3.50 cm",
            "",
            "",
            "",
            "",
            "",
            "",
            "","","",
            adapterProductList
        )
        adapterDetailChild.add(productModel)


        //hotDealsAdapter!!.notifyDataSetChanged()
    }

    fun showDataProductExtras() {

        if (adapterProductList.size != 0) {
            adapterProductList.clear()
        }
        productList = ProductList(
            R.drawable.img_1,
            "Cocktail",
            "0",
            "",
            "2.00",
            "1",
            "1",
            "0",
            "",
            adapterTrendingSample2
        )
        adapterProductList.add(productList)

        productList = ProductList(
            R.drawable.img_2,
            "Coke / Diet Coke / Pepsi",
            "0",
            "",
            "4.00",
            "1",
            "0",
            "0",
            "",
            adapterTrendingSample2
        )
        adapterProductList.add(productList)

        productList = ProductList(
            R.drawable.img_3,
            "Sangria",
            "0",
            "",
            "2.00",
            "1",
            "0",
            "0",
            "",
            adapterTrendingSample2
        )
        adapterProductList.add(productList)

        productList = ProductList(
            R.drawable.img_3,
            "Red Bull",
            "0",
            "",
            "4.00",
            "1",
            "0",
            "0",
            "",
            adapterTrendingSample2
        )
        adapterProductList.add(productList)

        productList = ProductList(
            R.drawable.img_3,
            "Sprite / 7up / Fresh Lime",
            "0",
            "",
            "0.00",
            "1",
            "0",
            "0",
            "",
            adapterTrendingSample2
        )
        adapterProductList.add(productList)
    }

    fun priceTotalLayout() {
        var totalPrice = 0.0
        for (i in 0 until dbHelper!!.getGrecoryTotalPrice(title!!).size) {
            totalPrice =
                totalPrice + dbHelper!!.getGrecoryTotalPrice(title!!).get(i).totalPrice!!.toDouble()
        }
        if (!dbHelper!!.getGrecoryCount(title!!).equals("0")) {
            priceLayout!!.visibility = View.VISIBLE

            addItemTV!!.text = getString(R.string.Added_Item) + dbHelper!!.getGrecoryCount(title!!)

            addPrice!!.text =
                "" + customText(activity!!, "€ " + DecimalFormat("##.##").format(totalPrice) + " "+getString(R.string.Plus_Taxes), 7, "SemiBold", "")
        } else {
            priceLayout!!.visibility = View.GONE
        }

        priceValue = totalPrice
    }

    override fun onClick(view: View?) {

        when (view!!.id) {
            R.id.favorite -> {
                if (favorite!!.tag.toString().equals("0")) {
                    val obj = JSONObject()
                    obj.put("groceryReferenceCode", "" + title)

                    obj.put("token", "" + dbHelper!!.getUserDetails().token)

                    obj.put("status", "1")

                    Log.v("Json", "Value" + obj)
                    RequestManager.settableFavoriteStoreAdd(activity, obj, this);
                    /*favorite!!.setColorFilter(
                        colorIcon(
                            activity!!,
                            R.color.colorWhite,
                            R.drawable.heart_selected,
                            favorite!!
                        ),
                        PorterDuff.Mode.SRC_ATOP
                    )*/
                    /*if (favString!!.equals("0")) {

                        favorite!!.setColorFilter(
                            colorIcon(
                                activity!!,
                                R.color.colorWhite,
                                R.drawable.heart_selected,
                                favorite!!
                            ),
                            PorterDuff.Mode.SRC_ATOP
                        )
                    } else {
                        favorite!!.setColorFilter(
                            colorIcon(
                                activity!!,
                                R.color.colorBlack,
                                R.drawable.heart_selected,
                                favorite!!
                            ),
                            PorterDuff.Mode.SRC_ATOP
                        )
                    }
    */
                    favorite!!.tag = "1"
                    selectFav = "1"
                } else {
                    val obj = JSONObject()
                    obj.put("groceryReferenceCode", "" + title)

                    obj.put("token", "" + dbHelper!!.getUserDetails().token)

                    obj.put("status", "2")

                    Log.v("Json", "Value" + obj)

                    /*favorite!!.setColorFilter(
                        colorIcon(
                            activity!!,
                            R.color.colorWhite,
                            R.drawable.favorite,
                            favorite!!
                        ),
                        PorterDuff.Mode.SRC_ATOP
                    )*/
                    /*if (favString!!.equals("0")) {

                        favorite!!.setColorFilter(
                            colorIcon(
                                activity!!,
                                R.color.colorWhite,
                                R.drawable.favorite,
                                favorite!!
                            ),
                            PorterDuff.Mode.SRC_ATOP
                        )
                    } else {
                        favorite!!.setColorFilter(
                            colorIcon(
                                activity!!,
                                R.color.colorBlack,
                                R.drawable.favorite,
                                favorite!!
                            ),
                            PorterDuff.Mode.SRC_ATOP
                        )
                    }*/
                    RequestManager.settableFavoriteStoreAdd(activity, obj, this);

                    favorite!!.tag = "0"

                    selectFav = "0"
                }
            }


            R.id.aboutUsLinearLayout -> {
                val bundle = Bundle()
                bundle.putString("groceryReferenceCode", "" + title)
                callBlacklisting!!.fragmentChange("Details About", bundle)
            }

            R.id.aboutUsLinearLayout -> {
                val bundle = Bundle()
                bundle.putString("groceryReferenceCode", "" + title)
                callBlacklisting!!.fragmentChange("Details About", bundle)
            }

            R.id.addToBasket -> {

                //startActivity(Intent(this,DetailItemAddPageActivity::class.java))
                if(priceValue.toDouble() > minimum!!) {
                val bundle = Bundle()
                bundle.putString("restaurantReferenceCode", "" + title)
                bundle.putString("restaurantImg", "" + imageUrl)

                callBlacklisting!!.fragmentChange("DetailItemAddPageActivity", bundle)
                }else{
                    showToast(activity!!,"Please add minimum amount "+minimum)
                }
            }

            R.id.sortLL -> {

                //startActivity(Intent(this,DetailItemAddPageActivity::class.java))

                callBlacklisting!!.fragmentChange("Filter", null)

            }

            R.id.filterLL -> {

                //startActivity(Intent(this,DetailItemAddPageActivity::class.java))

                callBlacklisting!!.fragmentChange("Filter", null)

            }
        }

    }

    fun showDialogAddService(
        context: Context?
    ) {
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
        dialog.setContentView(R.layout.extras)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val menuTV = dialog.findViewById<View>(R.id.menuTV) as TextView

        menuTV!!.typeface = fontStyle(context, "SemiBold")

        val resetTV = dialog.findViewById<View>(R.id.resetTV) as TextView

        resetTV!!.typeface = fontStyle(context, "SemiBold")

        val extras = dialog.findViewById<View>(R.id.extras) as TextView

        extras!!.typeface = fontStyle(context, "")

        val closeIcon = dialog.findViewById<View>(R.id.closeIcon) as ImageView

        val orderList = dialog.findViewById<View>(R.id.itemList) as RecyclerView

        dialogListPageAdapter = DialogListPageAdapter(context, adapterTrendingSample, dialog)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        orderList!!.layoutManager = mLayoutManager

        orderList!!.itemAnimator!!.addDuration = 5000

        dialogListPageAdapter!!.setCallback(this)

        orderList!!.adapter = dialogListPageAdapter

        showDataProduct1("", "")

        showDataProduct2("", "")

        showDataProduct3("", "")

        showDataData()

        closeIcon.setOnClickListener(View.OnClickListener { dialog.cancel() })

        resetTV!!.setOnClickListener(View.OnClickListener { dialog.cancel() })

        menuTV!!.setOnClickListener(View.OnClickListener { dialog.cancel() })


        dialog.show()
    }

    fun showDialogInfo(
        context: Context?
    ) {
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
        dialog.setContentView(R.layout.info_layout)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val title = dialog.findViewById<View>(R.id.title) as TextView

        title.typeface = fontStyle(context, "SemiBold")

        title.text = "Deluxe Thali-Veg"

        val title1 = dialog.findViewById<View>(R.id.title1) as TextView

        title1.typeface = fontStyle(context, "SemiBold")

        val title2 = dialog.findViewById<View>(R.id.title2) as TextView

        title2.typeface = fontStyle(context, "")

        val title3 = dialog.findViewById<View>(R.id.title3) as TextView

        title3.typeface = fontStyle(context, "")

        val title4 = dialog.findViewById<View>(R.id.title4) as TextView

        title4.typeface = fontStyle(context, "SemiBold")

        val title5 = dialog.findViewById<View>(R.id.title5) as TextView

        title5.typeface = fontStyle(context, "")

        val title6 = dialog.findViewById<View>(R.id.title6) as TextView

        title6.typeface = fontStyle(context, "")

        val title7 = dialog.findViewById<View>(R.id.title7) as TextView

        title7.typeface = fontStyle(context, "")

        val title8 = dialog.findViewById<View>(R.id.title8) as TextView

        title8.typeface = fontStyle(context, "Light")

        title8.text =
            Html.fromHtml("<font color='#484848' >Please cantact </font><font color='#4079F1' >Customer Service </font><font color='#484848' >in case you allergens or intolerances.</font>");

        val closeIcon = dialog.findViewById<View>(R.id.closeIcon) as ImageView

        closeIcon.setOnClickListener(View.OnClickListener { dialog.cancel() })


        dialog.show()
    }

    fun showDialogItem(
        context: Context?, position: String?
    ) {
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
        dialog.setContentView(R.layout.item_value_main)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val orderList = dialog.findViewById<View>(R.id.itemRecyclerView) as RecyclerView

        dialogListItemAdapter =
            DialogListItemAdapter(context, adapterTrendingItem, dialog!!, position!!)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        orderList!!.layoutManager = mLayoutManager

        orderList!!.itemAnimator!!.addDuration = 5000

        orderList!!.adapter = dialogListItemAdapter

        dialogListItemAdapter!!.setCallback(this)

        showDataDataItem()
        dialog.show()
    }
    /*override fun onItem(id: String?, view: String?,type:String) {

       Log.v("ff"+id,"iii")

        if(!id!!.equals("Info")) {

            *//*addItemTV!!.text = "Added Item : " + id;

            showDialogAddService(activity)

            if (id!!.equals("0")) {
                priceLayout!!.visibility = View.GONE
            } else {
                priceLayout!!.visibility = View.VISIBLE
            }*//*

            showDialogItem(activity)
        }else{
            showDialogInfo(activity)
        }
    }*/

    fun showDataDataItem() {

        if (adapterTrendingItem.size != 0) {
            adapterTrendingItem.clear()
        }
        adapterModel = AdapterModel(
            0,
            "mit Gnocchi",
            "Required 1",
            "75 minutes",
            "Save 60 %",
            " 976",
            " 425",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterCategories
        )
        adapterTrendingItem.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "mit Penne",
            "Required 2",
            "75 minutes",
            "Save 60 %",
            " 976",
            " 425",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterCategories1
        )
        adapterTrendingItem.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "mit Spaghetti",
            "Required 3",
            "75 minutes",
            "Save 60 %",
            " 976",
            " 425",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterCategories2
        )
        adapterTrendingItem.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "mit Tortellini (+ € 0.70)",
            "Required 3",
            "75 minutes",
            "Save 60 %",
            " 976",
            " 425",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterCategories2
        )
        adapterTrendingItem.add(adapterModel)

        dialogListItemAdapter!!.notifyDataSetChanged()
    }

    fun showDataData() {

        if (adapterTrendingSample.size != 0) {
            adapterTrendingSample.clear()
        }
        adapterModel = AdapterModel(
            0,
            "Cold Drink",
            "Required 1",
            "75 minutes",
            "Save 60 %",
            " 976",
            " 425",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterCategories
        )
        adapterTrendingSample.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "Sausages",
            "Required 2",
            "75 minutes",
            "Save 60 %",
            " 976",
            " 425",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterCategories1
        )
        adapterTrendingSample.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "Extra Topping",
            "Required 3",
            "75 minutes",
            "Save 60 %",
            " 976",
            " 425",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "",
            0,
            0,
            0,
            adapterCategories2
        )
        adapterTrendingSample.add(adapterModel)

        dialogListPageAdapter!!.notifyDataSetChanged()
    }

    fun showDataProduct1(id: String?, type: String?) {

        if (adapterCategories.size != 0) {
            adapterCategories.clear()
        }
        productModel = Product(
            R.drawable.img_1,
            "Cocktail",
            "" + id,
            "" + type,
            "€2.00",
            "0",
            "1",
            "200 for two",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "","","",
            adapterProductList
        )
        adapterCategories.add(productModel)

        productModel = Product(
            R.drawable.img_2,
            "Coke / Diet Coke / Pepsi",
            "" + id,
            "" + type,
            "€4.00",
            "0",
            "0",
            "200 for two",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "","","",
            adapterProductList
        )
        adapterCategories.add(productModel)

        productModel = Product(
            R.drawable.img_3,
            "Sangria",
            "" + id,
            "" + type,
            "€2.00",
            "0",
            "0",
            "200 for two",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "","","",
            adapterProductList
        )
        adapterCategories.add(productModel)

        productModel = Product(
            R.drawable.img_3,
            "Red Bull",
            "" + id,
            "" + type,
            "€4.00",
            "0",
            "0",
            "200 for two",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "","","",
            adapterProductList
        )
        adapterCategories.add(productModel)

        productModel = Product(
            R.drawable.img_3,
            "Sprite / 7up / Fresh Lime",
            "" + id,
            "" + type,
            "€0.00",
            "0",
            "0",
            "200 for two",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "","","",
            adapterProductList
        )
        adapterCategories.add(productModel)

        //hotDealsAdapter!!.notifyDataSetChanged()
    }

    fun showDataProduct2(id: String?, type: String?) {

        if (adapterCategories1.size != 0) {
            adapterCategories1.clear()
        }
        productModel = Product(
            R.drawable.img_1,
            "Cocktail",
            "" + id,
            "" + type,
            "€2.00",
            "1",
            "0",
            "200 for two",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "","","",
            adapterProductList
        )
        adapterCategories1.add(productModel)

        productModel = Product(
            R.drawable.img_2,
            "Coke / Diet Coke / Pepsi",
            "" + id,
            "" + type,
            "€4.00",
            "1",
            "0",
            "200 for two",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "","","",
            adapterProductList
        )
        adapterCategories1.add(productModel)

        productModel = Product(
            R.drawable.img_3,
            "Sangria",
            "" + id,
            "" + type,
            "€2.00",
            "1",
            "1",
            "200 for two",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "","","",
            adapterProductList
        )
        adapterCategories1.add(productModel)

        productModel = Product(
            R.drawable.img_3,
            "Red Bull",
            "" + id,
            "" + type,
            "€4.00",
            "1",
            "0",
            "200 for two",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "","","",
            adapterProductList
        )
        adapterCategories1.add(productModel)

        productModel = Product(
            R.drawable.img_3,
            "Sprite / 7up / Fresh Lime",
            "" + id,
            "" + type,
            "€0.00",
            "1",
            "40 Mins",
            "200 for two",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "","","",
            adapterProductList
        )
        adapterCategories1.add(productModel)

        //hotDealsAdapter!!.notifyDataSetChanged()
    }

    fun showDataProduct3(id: String?, type: String?) {

        if (adapterCategories2.size != 0) {
            adapterCategories2.clear()
        }
        productModel = Product(
            R.drawable.img_1,
            "Cocktail",
            "" + id,
            "" + type,
            "€2.00",
            "2",
            "0",
            "200 for two",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "","","",
            adapterProductList
        )
        adapterCategories2.add(productModel)

        productModel = Product(
            R.drawable.img_2,
            "Coke / Diet Coke / Pepsi",
            "" + id,
            "" + type,
            "€4.00",
            "2",
            "0",
            "200 for two",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "","","",
            adapterProductList
        )
        adapterCategories2.add(productModel)

        productModel = Product(
            R.drawable.img_3,
            "Sangria",
            "" + id,
            "" + type,
            "€2.00",
            "2",
            "0",
            "200 for two",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "","","",
            adapterProductList
        )
        adapterCategories2.add(productModel)

        productModel = Product(
            R.drawable.img_3,
            "Red Bull",
            "" + id,
            "" + type,
            "€4.00",
            "2",
            "0",
            "200 for two",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "","","",
            adapterProductList
        )
        adapterCategories2.add(productModel)

        productModel = Product(
            R.drawable.img_3,
            "Sprite / 7up / Fresh Lime",
            "" + id,
            "" + type,
            "€0.00",
            "2",
            "0",
            "200 for two",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "","","",
            adapterProductList
        )
        adapterCategories2.add(productModel)

        //hotDealsAdapter!!.notifyDataSetChanged()
    }

    override fun setOnPopUp(type: String?, position: String?) {

        if (position!!.equals("0")) {
            showDataProduct1("", "" + type)
        } else if (position!!.equals("2")) {

            showDataProduct2("", "" + type)

        } else if (position!!.equals("3")) {

            showDataProduct3("", "" + type)

        }

        showDataData()

    }

    override fun setOnFavourite(isFav: String?, id: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setOnExtraItem(item: String?, id: String?) {

        for (i in 0 until adapterDetails.size) {
            if (id!!.toInt() == i) {

                adapterDetails.get(i).parlourRatingValue = "1"
            } else {
                adapterDetails.get(i).parlourRatingValue = "0"
            }
        }

        if (!item!!.equals("")) {
            dbHelper!!.deleteGroceryFull(item!!)
        }

        priceTotalLayout()

        //detailedViewMainAdapter!!.notifyDataSetChanged()
    }


    fun isValid(): Boolean {
        var isTrue: Boolean = false
        for (i in 0 until adapterDetails.size) {

            if (!adapterDetails.get(i).parlourAddress.equals("0")) {
                isTrue = true
            }

        }
        return isTrue
    }

    override fun setOnFavouriteValue(isFav: AdapterModel?, id: String?, totalPrice: String) {

        if (isValid()) {
            priceLayout!!.visibility = View.VISIBLE
        } else {
            priceLayout!!.visibility = View.GONE
        }
        Log.v("kkkkk", "111" + totalPrice)

        if (dbHelper!!.getGrecoryCountPrice(isFav!!.categoryReferenceCode!!).equals("0")) {
            dbHelper!!.addItemCategory_info(
                isFav!!,
                hotelName!!.text.toString(),
                title!!,
                id!!,
                totalPrice!!
            )
        } else {
            dbHelper!!.updateGrocerydetails(isFav!!, id, totalPrice)
        }
        priceTotalLayout()

        //detailedViewMainAdapter!!.notifyDataSetChanged()

    }

    override fun setOnProduct(isFav: String?, id: String?) {
        val arrOfStr1 =
            id!!.split("-")

        var bundle = Bundle()

        bundle.putString("groceryId", "" + isFav)

        bundle.putString("productId", arrOfStr1[1])

        bundle.putString("categoryId", arrOfStr1[0])

        bundle.putString("offerPrice", arrOfStr1[2])

        bundle.putString("groceryReferenceCode", ""+title)

        bundle.putString("groceryStoreName", ""+hotelName!!.text)

        bundle.putString("Delivery Type",Constant.DeliveryType)

         callBlacklisting!!.fragmentChange("ProductDetailsPage",bundle)
    }

    override fun onItem(type: String?, id: String?, view: String?, postion: Int) {
        if (type!!.equals("Popup")) {

            /*addItemTV!!.text = "Added Item : " + id;

            showDialogAddService(activity)

            if (id!!.equals("0")) {
                priceLayout!!.visibility = View.GONE
            } else {
                priceLayout!!.visibility = View.VISIBLE
            }*/

            showDialogItem(activity, id)
        } else if (type!!.equals("Cancel")) {

            for (i in 0 until adapterDetailChild.size) {
                if (id!!.equals("" + i)) {
                    adapterDetailChild.get(i).allergy1 = view!!.toString()
                } else {
                    adapterDetailChild.get(i).allergy1 = "0"
                }
            }


            for (i in 0 until adapterDetails.size) {
                if (postion == i) {

                    adapterDetails.get(i).offerPrice = "1"
                } else {
                    adapterDetails.get(i).offerPrice = "0"
                }
            }

            adapter!!.notifyDataSetChanged()

            if (id!!.equals("2")) {
                if (adapterDetailChild.get(2).allergy1.equals("1")) {
                    priceLayout!!.visibility = View.VISIBLE
                } else {
                    priceLayout!!.visibility = View.GONE
                }
            } else {

                priceLayout!!.visibility = View.GONE

            }

        } else if (type!!.equals("Final")) {
            if (adapterDetailChild.get(id!!.toInt()).allergy1.equals("1")) {
                priceLayout!!.visibility = View.VISIBLE
            } else {
                priceLayout!!.visibility = View.GONE
            }

        } else if (type!!.equals("Info")) {
            showDialogInfo(activity)
        } else if (type!!.equals("Add Price")) {
            for (i in 0 until adapterDetailChild.size) {
                if (adapterDetailChild.get(i).allergy1!!.equals("1")) {
                    adapterDetailChild.get(i).price =
                        "" + addIncreasePrice(adapterDetailChild.get(i).price!!, view!!)

                    //adapterDetailChild.get(i).offerValue = ""+ MultipleIncreasePrice(adapterDetailChild.get(i).offerValue,adapterDetailChild.get(i).distance)
                }
            }
            for (i in 0 until adapterDetails.size) {
                if (postion == i) {

                    adapterDetails.get(i).offerPrice = "1"
                } else {
                    adapterDetails.get(i).offerPrice = "0"
                }
            }

            adapterProductList.get(id!!.toInt()).offerPrice = "1"

            adapter!!.notifyDataSetChanged()
        } else if (type!!.equals("Decrease Price")) {
            for (i in 0 until adapterDetailChild.size) {
                if (adapterDetailChild.get(i).allergy1!!.equals("1")) {
                    adapterDetailChild.get(i).price =
                        "" + DecreaseIncreasePrice(adapterDetailChild.get(i).price!!, view!!)

                    //adapterDetailChild.get(i).offerValue = ""+ MultipleIncreasePrice(adapterDetailChild.get(i).offerValue,adapterDetailChild.get(i).distance)
                }
            }


            for (i in 0 until adapterDetails.size) {
                if (postion == i) {

                    adapterDetails.get(i).offerPrice = "1"
                } else {
                    adapterDetails.get(i).offerPrice = "0"
                }
            }

            adapterProductList.get(id!!.toInt()).offerPrice = "0"

            adapter!!.notifyDataSetChanged()
        } else if (type!!.equals("ItemClose")) {
            for (i in 0 until adapterDetails.size) {
                if (id!!.toInt() == i) {

                    adapterDetails.get(i).offerPrice = view!!
                } else {
                    adapterDetails.get(i).offerPrice = "0"
                }
            }
            adapter!!.notifyDataSetChanged()
        } else if (type!!.equals("Filter")) {
            //allRestaruntFamous()
            adapter!!.notifyDataSetChanged()
            detailsRecyclerView!!.smoothScrollToPosition(1)
        }
    }

    override fun setOnFilter(id: String?) {

        detailsRecyclerView!!.smoothScrollToPosition(id!!.toInt())

    }

    override fun setCancelFilter(id: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setOnItemClose(id: String?, position: Int?, isClose: String?) {

    }

    override fun setOnDetail(id: String?, position: Int?) {

    }

    override fun setOnInfo(id: String?, position: String?) {
        showDialogInfo(activity)
    }

    override fun setOnQuantity(id: String?, pos: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setOnPopup(adapterModels: List<ProductListView>, position: Int?) {

        //showDialogItem(activity,id)

    }

    override fun setOnCancel(type: String?, position: Double?, isOpen: String?, pos: String?) {

        Log.v("" + position + "llll" + type, "pp " + pos)

        for (i in 0 until adapterDetailChild.size) {
            Log.v("llll", "pp " + adapterDetailChild.get(i).imageIcon)
            if (position!!.toInt() == i) {
                adapterDetailChild.get(i).allergy1 = isOpen!!
            } else {
                adapterDetailChild.get(i).allergy1 = "0"
            }
        }


        for (i in 0 until adapterDetails.size) {
            adapterDetails.get(i).offerPercentage = "" + pos + " " + position
        }

        detailedViewMainAdapter!!.notifyDataSetChanged()

        if (position!! == 2.0) {
            if (adapterDetailChild.get(2).allergy1.equals("1")) {
                priceLayout!!.visibility = View.VISIBLE
            } else {
                priceLayout!!.visibility = View.GONE
            }
        } else {

            //priceLayout!!.visibility = View.GONE

        }
    }

    override fun setOnPriceAdd(type: String?, itemName: String?, price: String?, position: Int?) {


        Log.v(price + "===" + type, "===" + position + "==" + itemName);

        if (type!!.equals("Add Price")) {
            for (i in 0 until adapterDetailChild.size) {
                if (adapterDetailChild.get(i).allergy1!!.equals("1")) {
                    adapterDetailChild.get(i).price =
                        "" + addIncreasePrice(adapterDetailChild.get(i).price!!, price!!)

                    //adapterDetailChild.get(i).offerValue = ""+ MultipleIncreasePrice(adapterDetailChild.get(i).offerValue,adapterDetailChild.get(i).distance)
                }
            }
            for (i in 0 until adapterDetails.size) {
                if (position == i) {

                    adapterDetails.get(i).offerPrice = "1"
                } else {
                    adapterDetails.get(i).offerPrice = "0"
                }
            }

            adapterProductList.get(itemName!!.toInt()).offerPrice = "1"

            detailedViewMainAdapter!!.notifyDataSetChanged()
        } else if (type!!.equals("Decrease Price")) {
            for (i in 0 until adapterDetailChild.size) {
                if (adapterDetailChild.get(i).allergy1!!.equals("1")) {
                    adapterDetailChild.get(i).price =
                        "" + DecreaseIncreasePrice(adapterDetailChild.get(i).price!!, price!!)

                    //adapterDetailChild.get(i).offerValue = ""+ MultipleIncreasePrice(adapterDetailChild.get(i).offerValue,adapterDetailChild.get(i).distance)
                }
            }


            for (i in 0 until adapterDetails.size) {
                if (position == i) {

                    adapterDetails.get(i).offerPrice = "1"
                } else {
                    adapterDetails.get(i).offerPrice = "0"
                }
            }

            adapterProductList.get(itemName!!.toInt()).offerPrice = "0"

            detailedViewMainAdapter!!.notifyDataSetChanged()
        }

    }

    override fun setFinal(type: String?, position: Double?, isOpen: String?, pos: String?) {

        if (adapterDetailChild.get(position!!.toInt()).allergy1.equals("1")) {
            priceLayout!!.visibility = View.VISIBLE
        } else {
            // priceLayout!!.visibility = View.GONE
        }

        for (i in 0 until adapterDetailChild.size) {
            adapterDetailChild.get(i).allergy1 = "0"
        }

        showDataProductExtras()

        showDataProduct()

        detailedViewMainAdapter!!.notifyDataSetChanged()
    }

    override fun setDelete(type: String?, position: Double?, isOpen: String?, pos: String?) {

    }

    override fun setOnFilter1(id: String?) {

        detailsRecyclerView!!.smoothScrollToPosition(id!!.toInt())

        //callBlacklisting!!.fragmentChange("All Category", null)
    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {

        if (responseObj != null) {
            if (requestType == Constant.MEMBER_fetchGroceryData_URL_RQ) {

                if ((responseObj as BaseRS).status.equals("5802")) {

                    Picasso.with(activity!!)
                        .load((responseObj as BaseRS).groceryPath + "/" + getImageValue1((responseObj as BaseRS).fetchData!!.groceryData!!.groceryImages))
                        .resize(450, 450).placeholder(R.drawable.item_placeholder_geocery)
                        .into(bannerImg)

                    Picasso.with(activity!!)
                        .load((responseObj as BaseRS).groceryPath + "/" + getImageValue1((responseObj as BaseRS).fetchData!!.groceryData!!.groceryLogo))
                        .resize(450, 450).placeholder(R.drawable.item_placeholder_geocery)
                        .into(hotelIcon)


                    imageUrl =
                        "" + (responseObj as BaseRS).groceryPath + "/" + getImageValue1((responseObj as BaseRS).fetchData!!.groceryData!!.groceryImages)

                    hotelName!!.setText((responseObj as BaseRS).fetchData!!.groceryData!!.groceryName)

                    hotelDescription!!.setText((responseObj as BaseRS).fetchData!!.groceryData!!.street + "," + (responseObj as BaseRS).fetchData!!.groceryData!!.town)

                    ratingTV!!.setText((responseObj as BaseRS).fetchData!!.groceryData!!.rating)

                    timeTV!!.setText((responseObj as BaseRS).fetchData!!.groceryData!!.distance)

                    priceTV!!.setText("€ " + (responseObj as BaseRS).fetchData!!.groceryData!!.minimumOrderAmount)

                    minimum =(responseObj as BaseRS).fetchData!!.groceryData!!.minimumOrderAmount!!.toDouble()

                    groceryId = (responseObj as BaseRS).fetchData!!.groceryData!!.groceryId

                    reviewTv!!.text = ""+(responseObj as BaseRS).fetchData!!.reviewCount+" + rating"

                    Constant.favStringGroceryValue = ""
                    for (i in 0 until (responseObj as BaseRS).fetchData!!.favoriteList!!.size) {
                        if (Constant.favStringGroceryValue.equals("")) {
                            Constant.favStringGroceryValue =
                                "" + (responseObj as BaseRS).fetchData!!.favoriteList!!.get(i).groceryId
                        } else {
                            Constant.favStringGroceryValue =
                                Constant.favStringGroceryValue + "," + (responseObj as BaseRS).fetchData!!.favoriteList!!.get(
                                    i
                                ).groceryId
                        }

                    }

                    val favString1 =
                        Constant.favStringGroceryValue.split(",")
                    favorite!!.setColorFilter(
                        colorIcon(
                            activity!!,
                            R.color.colorWhite,
                            R.drawable.favorite,
                            favorite!!
                        ),
                        PorterDuff.Mode.SRC_ATOP
                    )
                    for (i in 0 until favString1!!.size) {
                        if (favString1[i].equals(groceryId)) {
                            favorite!!.setColorFilter(
                                colorIcon(
                                    activity!!,
                                    R.color.colorWhite,
                                    R.drawable.heart_selected,
                                    favorite!!
                                ),
                                PorterDuff.Mode.SRC_ATOP
                            )
                            favorite!!.tag = "1"

                            //selectFav= "1"

                        }
                    }



                    if (adapterDetails1.size != 0) {
                        adapterDetails1.clear()
                    }

                    adapterModel = AdapterModel(
                        R.drawable.list,
                        "All Fruits",
                        "€ 16.19",
                        "3 pec",
                        "Deal ended soon",
                        "€ 20.19",
                        "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product03.jpg",
                        "0",
                        "0",
                        "Very Good",
                        "3054 Ratings>",
                        "5 Star Given by you",
                        "1",
                        "",
                        "",
                        "",
                        "",
                        "",
                        0,
                        0,
                        0,
                        adapterDetailChild
                    )
                    //adapterDetails1.add(adapterModel)
                    for (i in 0 until (responseObj as BaseRS).fetchData!!.groceryCategoryData !!.size) {
                        adapterModel = AdapterModel(
                            R.drawable.cleaning,
                            "" + (responseObj as BaseRS).fetchData!!.groceryCategoryData !!.get(i).categoryName,
                            "€ 16.19",
                            "3 pec",
                            "Deal ended soon",
                            (responseObj as BaseRS).categoryPath!! + "/" + (responseObj as BaseRS).fetchData!!.groceryCategoryData !!.get(
                                i
                            ).categoryImage,
                            "https://demo.hasthemes.com/greenfarm-preview/greenfarm/assets/images/products/product03.jpg",
                            "0",
                            "0",
                            "Very Good",
                            "3054 Ratings>",
                            "5 Star Given by you",
                            "1",
                            "",
                            "",
                            "",
                            "",
                            "",
                            0,
                            0,
                            0,
                            adapterDetailChild
                        )
                        adapterDetails1.add(adapterModel)
                    }
                    filterPageAdapter!!.notifyDataSetChanged()
                    if (adapterDetailsCategory.size != 0) {
                        adapterDetailsCategory.clear()
                    }

                    if (adapterDetails.size != 0) {
                        adapterDetails.clear()
                    }
                    for (j in 0 until (responseObj as BaseRS).fetchData!!.groceryCategoryData!!.size) {
                        for (i in 0 until (responseObj as BaseRS).fetchData!!.groceryCategoryData!!.get(j).productData!!.size) {
                            var productCount = "0"
                            if (dbHelper!!.getGerocyList(
                                    "" + (responseObj as BaseRS).fetchData!!.groceryCategoryData!!.get(j).productData!!.get(
                                        i
                                    ).productReferenceCode
                                ).quantity != null
                            ) {
                                productCount = "" + dbHelper!!.getGerocyList(
                                    "" + (responseObj as BaseRS).fetchData!!.groceryCategoryData!!.get(j).productData!!.get(i).productReferenceCode
                                ).quantity

                            } else {
                                productCount = "0"
                            }
                            adapterModel = AdapterModel(
                                0,
                                "" + (responseObj as BaseRS).fetchData!!.groceryCategoryData!!.get(j).productData!!.get(i).productName,
                                "" + (responseObj as BaseRS).fetchData!!.groceryCategoryData!!.get(j).productData!!.get(i).price,
                                (responseObj as BaseRS).fetchData!!.groceryCategoryData!!.get(j).productData!!.get(i).quantity + " pec",
                                "" + (responseObj as BaseRS).fetchData!!.groceryCategoryData!!.get(j).productData!!.get(i).description,
                                "€ 20.19",
                                "" + (responseObj as BaseRS).groceryPath + "/" + getImageValue1(
                                    (responseObj as BaseRS).fetchData!!.groceryCategoryData!!.get(j).productData!!.get(
                                        i
                                    ).productImages
                                ),
                                "" + productCount!!,
                                "0",
                                "" + (responseObj as BaseRS).fetchData!!.groceryCategoryData!!.get(j).productData!!.get(i).productReferenceCode,
                                "3054 Ratings>",
                                "5 Star Given by you",
                                ""+(responseObj as BaseRS).fetchData!!.groceryCategoryData!!.get(j).productData!!.get(i).categoryId,
                                "",
                                "",
                                ""+(responseObj as BaseRS).fetchData!!.groceryCategoryData!!.get(j).productData!!.get(i).productId,
                                ""+(responseObj as BaseRS).fetchData!!.groceryCategoryData!!.get(j).productData!!.get(i).offerPrice,
                                ""+(responseObj as BaseRS).fetchData!!.groceryCategoryData!!.get(j).productData!!.get(i).groceryId,
                                0,
                                0,
                                0,
                                adapterDetailChild
                            )
                            adapterDetails.add(adapterModel)
                        }
                    }


                    for (i in 0 until (responseObj as BaseRS).fetchData!!.groceryCategoryData !!.size) {
/*                        var productCount = "0"
                        if (dbHelper!!.getGerocyList(
                                "" + (responseObj as BaseRS).fetchData!!.productData!!.get(
                                    i
                                ).productReferenceCode
                            ).quantity != null
                        ) {
                            productCount = "" + dbHelper!!.getGerocyList(
                                "" + (responseObj as BaseRS).fetchData!!.productData!!.get(i).productReferenceCode
                            ).quantity

                        } else {
                            productCount = "0"
                        }*/
                        adapterModelGrocery = AdapterModelGrocery(
                            0,
                            "" + (responseObj as BaseRS).fetchData!!.groceryCategoryData !!.get(i).categoryName,
                            ""+ (responseObj as BaseRS).fetchData!!.groceryCategoryData !!.get(i).categoryId,
                            "",
                            "",
                            "€ 20.19",
                            "",
                            "",
                            "0",
                            "",
                            "3054 Ratings>",
                            "5 Star Given by you",
                            "1",
                            "",
                            "",
                            "",
                            "",
                            "",
                            0,
                            0,
                            0,
                            adapterDetails
                        )
                        adapterDetailsCategory.add(adapterModelGrocery)
                    }
                    detailedViewMainAdapter!!.notifyDataSetChanged()
                } else if ((responseObj as BaseRS).status.equals("5803")){
                    showToast(activity!!,""+(responseObj as BaseRS).message)

                    callBlacklisting!!.fragmentBack("")
                }
                //detailedViewMainAdapter!!.notifyDataSetChanged()
                /*timeTV!!.setText((responseObj as BaseRS).fetchData!!.restaurantData!!.distance+" KM")

                    priceTV!!.setText("€ "+(responseObj as BaseRS).fetchData!!.restaurantData!!.price)

                    if((responseObj as BaseRS).fetchData!!.description != null) {

                        restaruntDes = "" + (responseObj as BaseRS).fetchData!!.description!!
                    }*/


                }else if(requestType == Constant.MEMBER_tableFavoriteStoreAdd_URL_RQ){
                wedServicePage()
                if ((responseObj as BaseRS).status.equals("5821")) {



                }else if ((responseObj as BaseRS).status.equals("5818")) {

                }
            }
            }
        }
}
