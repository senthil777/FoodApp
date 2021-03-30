package com.lieferin_global.Activity

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.*
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.*
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.DeliveryType
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.lieferin_global.webservices.responce.WorkExperience
import com.github.angads25.toggle.LabeledSwitch
import com.github.angads25.toggle.interfaces.OnToggledListener
import com.google.android.material.appbar.AppBarLayout
import com.rentaloo.CallBack.CallBlacklisting
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONObject
import java.text.DecimalFormat
import java.util.*

class DetailPageActivity : Fragment(), AppBarLayout.OnOffsetChangedListener, View.OnClickListener,
    DashBoardAdapter.OnClickDashBoard, DialogListPageAdapter.CallbackDataAdapter,
    DialogListItemAdapter.CallbackDataAdapter, FilterDetailsPageAdapter.CallbackDataAdapter,
    DetailedViewMainAdapter.CallbackDataAdapter, ResponseListener {

    var bannerImg: ImageView? = null

    var Img: String=""

    var restaruntDes = ""

    var restaurantData: WorkExperience? = null

    var categoryData: MutableList<AdapterModel>? = ArrayList()

    var categoryData1: MutableList<AdapterModel>? = ArrayList()

    var back: ImageView? = null

    var search: ImageView? = null

    var favorite: ImageView? = null

    var restToken: String = ""

    private var mMaxScrollSize = 0

    var priceValue = 0.0

    var count = 0

    var minimum : Double? = 0.0

    var hotelIcon: CircleImageView? = null

    var hotelName: TextView? = null

    var hotelDescription: TextView? = null

    var hotelIcon1: CircleImageView? = null

    var hotelName1: TextView? = null

    var hotelDescription1: TextView? = null

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

    var specialIV: ImageView? = null

    var specialRightIV: ImageView? = null

    var specialTitleTV: TextView? = null

    var specialPriceTV: TextView? = null

    var specialDescriptionTV: TextView? = null

    var priceLayout: RelativeLayout? = null

    var pricedup: LinearLayout? = null

    var detailsRecyclerView: RecyclerView? = null

    var detailsHorizontalRecyclerView: RecyclerView? = null

    var adapterDetails: MutableList<AdapterModel> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    internal lateinit var productModel: Product

    var adapterDetailChild: MutableList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    var addItemTV: TextView? = null

    var filterLL: LinearLayout? = null

    var sortLL: LinearLayout? = null

    var addPrice: TextView? = null

    var addToBasket: TextView? = null

    var rootView: View? = null

    var adapterCategories: MutableList<Product> = ArrayList()

    var adapterCategories1: MutableList<Product> = ArrayList()

    var adapterCategories2: MutableList<Product> = ArrayList()

    internal lateinit var adapterProduct: Product

    internal lateinit var productList: ProductList

    var adapterTrendingSample2: MutableList<ProductListView> = ArrayList<ProductListView>()

    var adapterTrendingSample: MutableList<AdapterModel> = ArrayList<AdapterModel>()

    var adapterTrendingItem: MutableList<AdapterModel> = ArrayList<AdapterModel>()

    fun DetailPageActivity() {}

    var callBlacklisting: CallBlacklisting? = null

    var dialogListPageAdapter: DialogListPageAdapter? = null

    var dialogListItemAdapter: DialogListItemAdapter? = null

    var adapter: DashBoardAdapter? = null

    var fullView: LinearLayout? = null

    var appbarLayout: AppBarLayout? = null

    var detailedViewMainAdapter: DetailedViewMainAdapter? = null

    var filterDetailsPageAdapter: FilterDetailsPageAdapter? = null

    var titleVisible: RelativeLayout? = null

    var dbHelper: DBHelper? = null

    var title: String? = null

    var switch4: LabeledSwitch? = null

    var aboutUsLinearLayout: LinearLayout? = null

    var restaurantId: String = ""

    var favString: String = ""

    var selectFav: String = ""

    var deliveryType: String = ""

    var openStatusTV: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_detail_page, container, false)

        val bundle = this.arguments

        if (bundle != null) {
            title = bundle.getString("Title")

            deliveryType = bundle.getString("Delivery Type").toString()

            DeliveryType= bundle.getString("Delivery Type").toString()
        }

        dbHelper = DBHelper(activity)

        bannerImg = rootView!!.findViewById(R.id.bannerImg) as ImageView

        //Picasso.with(activity!!).load(R.drawable.img_1).resize(450, 450).into(bannerImg)

        openStatusTV = rootView!!.findViewById(R.id.openStatusTV) as TextView

        openStatusTV!!.typeface = fontStyle(activity!!,"Light")

        filterLL = rootView!!.findViewById(R.id.filterLL) as LinearLayout

        filterLL!!.setOnClickListener(this)

        aboutUsLinearLayout = rootView!!.findViewById(R.id.aboutUsLinearLayout) as LinearLayout

        aboutUsLinearLayout!!.setOnClickListener(this)

        sortLL = rootView!!.findViewById(R.id.sortLL) as LinearLayout

        sortLL!!.setOnClickListener(this)

        switch4 = rootView!!.findViewById(R.id.switch4) as LabeledSwitch

        hotelIcon = rootView!!.findViewById(R.id.hotelIcon) as CircleImageView

        //Picasso.with(activity!!).load(R.drawable.img_2).resize(450, 450).into(hotelIcon)

        hotelIcon1 = rootView!!.findViewById(R.id.hotelIcon1) as CircleImageView

        //Picasso.with(activity!!).load(R.drawable.img_2).resize(450, 450).into(hotelIcon1)

        back = rootView!!.findViewById(R.id.back) as ImageView

        back!!.setColorFilter(
            colorIcon(activity!!, R.color.colorWhite, R.drawable.abc_ic_ab_back_material, back!!),
            PorterDuff.Mode.SRC_ATOP
        )

        back!!.setOnClickListener(this)

        favorite = rootView!!.findViewById(R.id.favorite) as ImageView

        favorite!!.setColorFilter(
            colorIcon(activity!!, R.color.colorWhite, R.drawable.favorite, favorite!!),
            PorterDuff.Mode.SRC_ATOP
        )

        favorite!!.setOnClickListener(this)

        favorite!!.tag = "0"

        search = rootView!!.findViewById(R.id.search) as ImageView

        search!!.setColorFilter(
            colorIcon(activity!!, R.color.colorWhite, R.drawable.search, search!!),
            PorterDuff.Mode.SRC_ATOP
        )

        search!!.setOnClickListener(this)

        eyeImg = rootView!!.findViewById(R.id.eyeImg) as ImageView

        eyeImg!!.setColorFilter(
            colorIcon(activity!!, R.color.redColor, R.drawable.eye, eyeImg!!),
            PorterDuff.Mode.SRC_ATOP
        )

        starIV = rootView!!.findViewById(R.id.starIV) as ImageView

        starIV!!.setColorFilter(
            colorIcon(activity!!, R.color.colorWhite, R.drawable.star, starIV!!),
            PorterDuff.Mode.SRC_ATOP
        )

        sortImg = rootView!!.findViewById(R.id.sortImg) as ImageView

        sortImg!!.setColorFilter(
            colorIcon(activity!!, R.color.colorGray, R.drawable.sort, sortImg!!),
            PorterDuff.Mode.SRC_ATOP
        )

        filterImg = rootView!!.findViewById(R.id.filterImg) as ImageView

        ///fullView = rootView!!.findViewById(R.id.fullView) as LinearLayout

        //fullView!!.setOnClickListener(View.OnClickListener {  })

        filterImg!!.setColorFilter(
            colorIcon(activity!!, R.color.colorGray, R.drawable.filter, filterImg!!),
            PorterDuff.Mode.SRC_ATOP
        )

        titleVisible = rootView!!.findViewById(R.id.titleVisible) as RelativeLayout

        titleVisible!!.visibility = View.GONE

        hotelName = rootView!!.findViewById(R.id.hotelName) as TextView

        hotelName!!.typeface = fontStyle(activity!!, "SemiBold")

        hotelDescription = rootView!!.findViewById(R.id.hotelDescription) as TextView

        hotelDescription!!.typeface = fontStyle(activity!!, "Light")

        hotelName1 = rootView!!.findViewById(R.id.hotelName1) as TextView

        hotelName1!!.typeface = fontStyle(activity!!, "SemiBold")

        hotelDescription1 = rootView!!.findViewById(R.id.hotelDescription1) as TextView

        hotelDescription1!!.typeface = fontStyle(activity!!, "Light")

        aboutUsTV = rootView!!.findViewById(R.id.aboutUsTV) as TextView

        aboutUsTV!!.typeface = fontStyle(activity!!, "Light")

        ratingTV = rootView!!.findViewById(R.id.ratingTV) as TextView

        ratingTV!!.typeface = fontStyle(activity!!, "Light")

        priceLayout = rootView!!.findViewById(R.id.priceLayout) as RelativeLayout

        priceLayout!!.visibility = View.GONE

        pricedup = rootView!!.findViewById(R.id.pricedup) as LinearLayout

        pricedup!!.visibility = View.GONE

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

        //addPrice!!.text = customText(activity!!,"€ 125.5 Plus Taxes",7,"SemiBold","")

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

        detailedViewMainAdapter = DetailedViewMainAdapter(activity!!, categoryData!!)
        detailsRecyclerView!!.setLayoutManager(LinearLayoutManagerWithSmoothScroller(activity!!))

        detailedViewMainAdapter!!.setCallback(this)

        detailsRecyclerView!!.setAdapter(detailedViewMainAdapter)

        detailsRecyclerView!!.isNestedScrollingEnabled = true

        detailsHorizontalRecyclerView =
            rootView!!.findViewById(R.id.detailsHorizontalRecyclerView) as RecyclerView

        filterDetailsPageAdapter = FilterDetailsPageAdapter(activity!!, categoryData!!)
        detailsHorizontalRecyclerView!!.setHasFixedSize(true)
        detailsHorizontalRecyclerView!!.setLayoutManager(
            LinearLayoutManager(
                activity!!,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )

        detailsHorizontalRecyclerView!!.isNestedScrollingEnabled = false

        filterDetailsPageAdapter!!.setCallback(this)

        detailsHorizontalRecyclerView!!.setAdapter(filterDetailsPageAdapter!!)

        showDataTrending()

        //addProduct()

        showDataProductExtras()

        showDataProduct()

        webService()

        appbarLayout = rootView!!.findViewById<View>(R.id.materialup_appbar) as AppBarLayout

        appbarLayout!!.addOnOffsetChangedListener(this)

        switch4!!.setOnToggledListener(OnToggledListener { labeledSwitch, isOn ->

            if (isOn) {
                Log.v("value", "" + isOn)
                vegOnly()
                switch4!!.colorOn = ContextCompat.getColor(activity!!, R.color.colorGreen)
            } else {
                nonVegOnly()
                switch4!!.colorOn = ContextCompat.getColor(activity!!, R.color.redColor)
            }
        })


        return rootView
    }

    fun webService(){
        val obj = JSONObject()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)
        obj.put("restaurantReferenceCode", "" + title)
        obj.put("filterType", "" )

        obj.put("orderBy", "")
        /*obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")*/
        obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)
        Log.i("Json", "Value" + obj)
        RequestManager.setRestaurant(activity, obj, this);
        loadingScreen(activity)
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
            "Insalata - Salate",
            "https://i2.wp.com/www.vegrecipesofindia.com/wp-content/uploads/2016/08/hara-bhara-kabab-2a.jpg",
            "0",
            "",
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
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "Pizza",
            "https://www.delonghi.com/Global/recipes/multifry/pizza_fresca.jpg",
            "0",
            "",
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
        adapterDetails.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "Burger",
            "https://media1.s-nbcnews.com/j/newscms/2019_21/2870431/190524-classic-american-cheeseburger-ew-207p_d9270c5c545b30ea094084c7f2342eb4.fit-760w.jpg",
            "0",
            "",
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
        adapterDetails.add(adapterModel)

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
            "", "", "", "", "","","","","",
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
            "", "", "", "", "","","","","",
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
            "", "", "", "", "","","","","",
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

    override fun onClick(view: View?) {

        when (view!!.id) {
            R.id.addToBasket -> {
                //startActivity(Intent(this,DetailItemAddPageActivity::class.java))

                    val bundle = Bundle()
                    bundle.putString("restaurantReferenceCode", "" + title)
                    bundle.putString("restaurantImg", "" + Img)
                    bundle.putString("minimumAmount", "" + minimum)
                    callBlacklisting!!.fragmentChange("DetailItemAddPageActivity", bundle)

            }

            R.id.search -> {
                val bundle = Bundle()
                bundle.putString("restaurantReferenceCode", "" + title)
                callBlacklisting!!.fragmentChange("SearchActivity", bundle)

            }

            R.id.aboutUsLinearLayout -> {

                val bundle = Bundle()
                bundle.putString("restaurantReferenceCode", "" + title)
                callBlacklisting!!.fragmentChange("Details About", bundle)

                /*val dialog = Dialog(context!!)
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
                dialog.setContentView(R.layout.about_us)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                val title = dialog.findViewById<View>(R.id.title) as TextView

                title!!.typeface = fontStyle(activity!!, "SemiBold")

                val contentText = dialog.findViewById<View>(R.id.contentText) as TextView

                contentText!!.typeface = fontStyle(activity!!, "")

                contentText!!.text = restaruntDes

                val closeIcon = dialog.findViewById<View>(R.id.closeIcon) as ImageView

                dialog.show()

                closeIcon.setOnClickListener(View.OnClickListener { dialog.cancel() })*/
            }

            R.id.sortLL -> {
                /*val bundle = Bundle()
                bundle.putString("restaurantReferenceCode", "" + title)
                callBlacklisting!!.fragmentChange("Filter Page", bundle)*/
            }

            R.id.filterLL -> {
               /* val bundle = Bundle()
                bundle.putString("Title", "" + restaurantData!!.restaurantId)
                callBlacklisting!!.fragmentChange("Filter Page", bundle)*/
            }

            R.id.favorite -> {
                if (favorite!!.tag.toString().equals("0")) {
                    val obj = JSONObject()
                    obj.put("restaurantReferenceCode", "" + restToken)

                    obj.put("token", "" + dbHelper!!.getUserDetails().token)

                    obj.put("status", "1")

                    Log.v("Json", "Value" + obj)
                    RequestManager.setFavListAdd(activity, obj, this);

                    if (favString!!.equals("0")) {

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

                    favorite!!.tag = "1"
                    selectFav = "1"
                } else {
                    val obj = JSONObject()
                    obj.put("restaurantReferenceCode", "" + restToken)

                    obj.put("token", "" + dbHelper!!.getUserDetails().token)

                    obj.put("status", "2")

                    Log.v("Json", "Value" + obj)
                    if (favString!!.equals("0")) {

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
                    }
                    RequestManager.setFavListRemove(activity, obj, this);

                    favorite!!.tag = "0"

                    selectFav = "0"
                }
            }

            R.id.back -> {
                callBlacklisting!!.fragmentBack("")
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
        context: Context?,titleValue:String,Value:String
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

        title.text = ""+titleValue

        val title1 = dialog.findViewById<View>(R.id.title1) as TextView

        title1.typeface = fontStyle(context, "SemiBold")

        title1.text = ""+Value


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

    fun showDataDataItem(adapterModels: List<ProductListView>) {

        if (adapterTrendingItem.size != 0) {
            adapterTrendingItem.clear()
        }

        for (i in 0 until adapterModels.size) {

            adapterModel = AdapterModel(
                0,
                "" + adapterModels.get(i).name + " (€ " + adapterModels.get(i).price + " )",
                "Required 1",
                "" + adapterModels.get(i).toppinsGroupId,
                "" + adapterModels.get(i).toppinsId,
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

        }

        //dialogListItemAdapter!!.notifyDataSetChanged()
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
            R.drawable.img_2,
            "Coke / Diet Coke / Pepsi",
            "" + id,
            "" + type,
            "€4.00",
            "0",
            "0",
            "200 for two",
            "",
            "", "", "", "", "","","","","",
            adapterProductList
        )
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
            "", "", "", "", "","","","","",
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
            "", "", "", "", "","","","","",
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
            "", "", "", "", "","","","","", adapterProductList
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
            "", "", "", "", "","","","","",
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
            "", "", "", "", "","","","","",
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
            "", "", "", "", "","","","","",
            "", adapterProductList
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
            "", "", "", "", "","","","","",
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
            "", "", "", "", "","","","","",
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
            "", "", "", "", "","","","","",
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
            "", "", "", "", "","","","","", adapterProductList
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
            "", "", "", "", "","","","","",
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
            "", "", "", "", "","","","","",
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
            "", "", "", "", "","","","","", adapterProductList
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
            "", "", "", "","","","","", adapterProductList
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

    override fun setOnExtraItem(item: String?, id: String?) {

        for (i in 0 until categoryData!!.size) {

            for (j in 0 until categoryData!!.get(i).menusList.size) {

                for (k in 0 until categoryData!!.get(i).menusList.get(j).toppinsGroupList.size) {

                    if (categoryData!!.get(i).menusList.get(j).toppinsGroupList.get(k).toppinsGroupId.equals(
                            item
                        )
                    ) {
                        for (l in 0 until categoryData!!.get(i).menusList.get(j).toppinsGroupList.get(
                            k
                        ).toppinsList.size) {

                            if (categoryData!!.get(i).menusList.get(j).toppinsGroupList.get(k).toppinsList.get(
                                    l
                                ).toppinsId.equals(id)
                            ) {
                                categoryData!!.get(i).menusList.get(j).toppinsGroupList.get(k)
                                    .toppinsList.get(l).categoryId = "1"

                                categoryData!!.get(i).menusList.get(j).toppinsGroupList.get(k)
                                    .toppinsList.get(l).menuId =
                                    categoryData!!.get(i).menusList.get(j).toppinsGroupList.get(k)
                                        .toppinsList.get(l).price
                            } else {
                                categoryData!!.get(i).menusList.get(j).toppinsGroupList.get(k)
                                    .toppinsList.get(l).categoryId = "0"

                                categoryData!!.get(i).menusList.get(j).toppinsGroupList.get(k)
                                    .toppinsList.get(l).menuId = "0"
                            }

                        }

                    }

                }

            }

        }

        detailedViewMainAdapter!!.notifyDataSetChanged()

    }

    override fun setOnFavourite(isFav: String?, id: String?) {

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

                    pricedup!!.visibility = View.VISIBLE
                } else {
                    priceLayout!!.visibility = View.GONE

                    pricedup!!.visibility = View.GONE
                }
            } else {
                priceLayout!!.visibility = View.GONE

                pricedup!!.visibility = View.GONE
            }

        } else if (type!!.equals("Final")) {
            if (adapterDetailChild.get(id!!.toInt()).allergy1.equals("1")) {
                priceLayout!!.visibility = View.VISIBLE

                pricedup!!.visibility = View.VISIBLE
            } else {
                priceLayout!!.visibility = View.GONE

                pricedup!!.visibility = View.GONE
            }

        } else if (type!!.equals("Info")) {
            showDialogInfo(activity,"","")
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

        for(i in 0 until categoryData!!.size)
        {
            categoryData!!.get(i).offerPrice = "0"
        }

        categoryData!!.get(id!!.toInt()).offerPrice = "1"

        detailsRecyclerView!!.smoothScrollToPosition(id!!.toInt())
        appbarLayout!!.setExpanded(false)

        filterDetailsPageAdapter!!.notifyDataSetChanged()
    }

    override fun setCancelFilter(id: String?) {

    }

    override fun setOnItemClose(id: String?, position: Int?, isClose: String?) {

    }

    override fun setOnDetail(id: String?, position: Int?) {

    }

    override fun setOnInfo(id: String?, position: String?) {

        Log.v("jjjjj "+id!!,"ppppp "+position!!);
        showDialogInfo(activity,id!!,position!!)


    }

    override fun setOnQuantity(id: String?, pos: String?) {

        for (i in 0 until categoryData!!.size) {

            for (j in 0 until categoryData!!.get(i).menusList.size) {

                if (categoryData!!.get(i).menusList.get(j).menuId.equals(id)) {
                    categoryData!!.get(i).menusList.get(j).quantity = "" + id
                }

            }
        }

    }

    override fun setOnPopup(adapterModels: List<ProductListView>, position: Int?) {

        showDataDataItem(adapterModels)

        showDialogItem(activity, "" + position)

    }

    override fun setOnCancel(type: String?, position: Double?, isOpen: String?, pos: String?) {

        Log.v("" + position + "llll33333r" + type, isOpen + "pp " + pos)


        if (type!!.equals("1")) {
            if (pos!!.equals("0")) {
                priceLayout!!.visibility = View.VISIBLE

                pricedup!!.visibility = View.VISIBLE

                if (dbHelper!!.getRestrentList("" + restaurantId).size != 0) {
                    Log.v(
                        "ppp444" + dbHelper!!.getRestrentList("" + restaurantId).get(0).id!!,
                        "llll444" + dbHelper!!.getRestrentList("" + restaurantId).size
                    )

                    for (i in 0 until dbHelper!!.getRestrentList("" + restaurantId).size) {
                        if (!dbHelper!!.getRestrentList("" + restaurantId).get(i).id!!.equals(
                                restaurantId
                            )
                        ) {
                            dbHelper!!.deleteMenu()

                            dbHelper!!.deleteCategory()

                            dbHelper!!.deleteToppinsGroup()

                            dbHelper!!.deleteToppins()

                            dbHelper!!.deleteRest()

                            Log.v("ppp", "llll" + restaurantId)
                        }
                    }
                }
            }
        } else {
            if(dbHelper!!.getMenuCountValue1("" + restaurantData!!.restaurantId).equals("0")) {
                priceLayout!!.visibility = View.GONE

                pricedup!!.visibility = View.GONE
            }else{

            }
        }
        detailedViewMainAdapter!!.notifyDataSetChanged()

        for (i in 0 until categoryData!!.size) {

            for (j in 0 until categoryData!!.get(i).menusList.size) {

                if (categoryData!!.get(i).menusList.get(j).menuId.equals(isOpen)) {
                    if (pos!!.equals("0")) {

                        count = count + 1


                        //customText(activity!!,"€ " + categoryData!!.get(i).menusList.get(j).price+" Plus Taxes",",7,"SemiBold","")

                        /*priceValue = priceValue + position!!

                        Log.v("22=======" + priceValue ,  "pp " + pos)

                        addPrice!!.text = customText(
                            activity!!,
                            "€ " + DecimalFormat("##.##").format(priceValue) + " Plus Taxes",
                            2 + categoryData!!.get(i).menusList.get(j).price.length,
                            "SemiBold",
                            ""
                        )*/

                        /*dbHelper!!.deleteRestaurant()

                        dbHelper!!.deleteCategory()

                        dbHelper!!.deleteMenu()*/

                        Log.v(
                            "ppp;;" + dbHelper!!.getRestrentList("" + restaurantId).size,
                            "llllValue" + restaurantId
                        )

                        if (pos!!.equals("0")) {
                            /* if(dbHelper!!.getRestrent().equals("") || dbHelper!!.getRestrent().equals(restaurantData!!.restaurantId)) {
 */
                            if (dbHelper!!.getMenuList(categoryData!!.get(i).menusList.get(j).menuId!!).size == 0) {

                                dbHelper!!.addMenu_info(
                                    categoryData!!.get(i).menusList.get(j),
                                    "" + restaurantData!!.restaurantId, "" + position
                                )
                            } else {
                                dbHelper!!.updatedetails(
                                    categoryData!!.get(i).menusList.get(j),
                                    categoryData!!.get(i).menusList.get(j).menuId!!,
                                    "" + position
                                )
                            }

                            dbHelper!!.addItem_info(
                                categoryData!!.get(i),
                                "" + restaurantData!!.restaurantId
                            )

                            dbHelper!!.addRestaurant_info(restaurantData!!)

                            priceValue = 0.0

                            for (i in 0 until dbHelper!!.getMenu().size) {

                                priceValue =
                                    priceValue + addIncreasePriceHole(dbHelper!!.getMenu().get(i).totalPrice!!,dbHelper!!.getMenu().get(i).rating!!).toDouble()
                            }

                            addPrice!!.text = customText(
                                activity!!,
                                "€ " + DecimalFormat("##.##").format(priceValue) + " "+getString(R.string.Plus_Taxes),
                                2 + categoryData!!.get(i).menusList.get(j).price!!.length,
                                "SemiBold",
                                ""
                            )

                            addItemTV!!.text =
                                customText(
                                    activity!!,
                                    getString(R.string.Added_Item)+ dbHelper!!.getMenuCountValue1("" + restaurantData!!.restaurantId),
                                    13,
                                    "SemiBold",
                                    ""
                                )


                            /*}else{
                                dbHelper!!.deleteRestaurant()

                                dbHelper!!.deleteToppinsFull()

                                dbHelper!!.deleteToppinsGroupFull()

                                dbHelper!!.deleteMenu()

                                dbHelper!!.deleteCategory()

                                dbHelper!!.addMenu_info(categoryData!!.get(i).menusList.get(j), ""+restaurantData!!.restaurantId)

                                dbHelper!!.addItem_info(categoryData!!.get(i))

                                dbHelper!!.addRestaurant_info(restaurantData!!)
                            }*/
                        }
                    }
                }
            }
        }

        for (i in 0 until categoryData!!.size) {

            for (j in 0 until categoryData!!.get(i).menusList.size) {

                for (k in 0 until categoryData!!.get(i).menusList.get(j).toppinsGroupList.size) {

                    for (l in 0 until categoryData!!.get(i).menusList.get(j).toppinsGroupList.get(k).toppinsList.size) {
                        categoryData!!.get(i).menusList.get(j).toppinsGroupList.get(k)
                            .toppinsList.get(l).categoryId = "0"
                    }
                }
            }
        }
    }

    override fun setOnPriceAdd(type: String?, itemName: String?, price: String?, position: Int?) {
        Log.v(price + "===" + type, "===" + position + "==" + itemName);

        for (i in 0 until categoryData!!.size) {

            for (j in 0 until categoryData!!.get(i).menusList.size) {

                for (k in 0 until categoryData!!.get(i).menusList.get(j).toppinsGroupList.size) {

                    if (categoryData!!.get(i).menusList.get(j).toppinsGroupList.get(k).toppinsGroupId.equals(
                            price
                        )
                    ) {
                        for (l in 0 until categoryData!!.get(i).menusList.get(j).toppinsGroupList.get(
                            k
                        ).toppinsList.size) {

                            if (categoryData!!.get(i).menusList.get(j).toppinsGroupList.get(k).toppinsList.get(
                                    l
                                ).toppinsId.equals(itemName)
                            ) {
                                categoryData!!.get(i).menusList.get(j).toppinsGroupList.get(k)
                                    .toppinsList.get(l).categoryId = type.toString()

                            }
                        }
                    }
                }
            }
        }

        detailedViewMainAdapter!!.notifyDataSetChanged()

        /*if(type!!.equals("Add Price"))
        {
            for (i in 0 until adapterDetailChild.size)
            {
                if(adapterDetailChild.get(i).allergy1!!.equals("1")) {
                    adapterDetailChild.get(i).price = ""+addIncreasePrice(adapterDetailChild.get(i).price,price!!)

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
        }
        else if(type!!.equals("Decrease Price"))
        {
            for (i in 0 until adapterDetailChild.size)
            {
                if(adapterDetailChild.get(i).allergy1!!.equals("1")) {
                    adapterDetailChild.get(i).price = ""+DecreaseIncreasePrice(adapterDetailChild.get(i).price,price!!)

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
        }*/

    }

    fun vegOnly() {
        val obj = JSONObject()
        obj.put("restaurantReferenceCode", "" + title)
        /*obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")*/
        obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)
        obj.put("filterType", "1")
        /*obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")*/
        Log.i("Json", "Value" + obj)
        RequestManager.setRestaurant(activity, obj, this);

        loadingScreen(activity)

    }

    fun nonVegOnly() {
        val obj = JSONObject()
        obj.put("restaurantReferenceCode", "" + title)
        /*obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")*/
        obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)
        Log.v("Json", "Value" + obj)
        RequestManager.setRestaurant(activity, obj, this);
        loadingScreen(activity)
    }


    override fun setFinal(type: String?, position: Double?, isOpen: String?, pos: String?) {


        priceLayout!!.visibility = View.VISIBLE

        pricedup!!.visibility = View.VISIBLE
        Log.v("ppp", "llllValue" + restaurantId)
        if (dbHelper!!.getRestrentList("" + restaurantId).size != 0) {
            Log.v(
                "ppp" + dbHelper!!.getRestrentList("" + restaurantId).get(0).id!!,
                "llll" + dbHelper!!.getRestrentList("" + restaurantId).size
            )
            for (i in 0 until dbHelper!!.getRestrentList("" + restaurantId).size) {
                if (!dbHelper!!.getRestrentList("" + restaurantId).get(i).id!!.equals(
                        restaurantId
                    )
                ) {
                    dbHelper!!.deleteMenu()

                    dbHelper!!.deleteCategory()

                    dbHelper!!.deleteToppinsGroup()

                    dbHelper!!.deleteToppins()

                    dbHelper!!.deleteRest()

                    Log.v("ppp", "llll")
                }
            }
        }

        for (i in 0 until categoryData!!.size) {

            for (j in 0 until categoryData!!.get(i).menusList.size) {

                if (categoryData!!.get(i).menusList.get(j).menuId.equals(isOpen)) {

                    count = count + 1


                    //customText(activity!!,"€ " + categoryData!!.get(i).menusList.get(j).price+" Plus Taxes",",7,"SemiBold","")

                    // priceValue = priceValue + position!!

                    //categoryData!!.get(i).menusList.get(j).price = "" + position

                    Log.v("22=======" + priceValue, "pp " + pos)


                    /*if(dbHelper!!.getRestrent().equals("") || dbHelper!!.getRestrent().equals(restaurantData!!.restaurantId)) {*/
                    if (dbHelper!!.getMenuList(categoryData!!.get(i).menusList.get(j).menuId!!).size == 0) {

                        dbHelper!!.addMenu_info(
                            categoryData!!.get(i).menusList.get(j),
                            "" + restaurantData!!.restaurantId, "" + position
                        )
                    } else {
                        dbHelper!!.updatedetails(
                            categoryData!!.get(i).menusList.get(j),
                            categoryData!!.get(i).menusList.get(j).menuId!!,
                            "" + position
                        )
                    }

                    dbHelper!!.addItem_info(
                        categoryData!!.get(i),
                        "" + restaurantData!!.restaurantId
                    )

                    dbHelper!!.addRestaurant_info(restaurantData!!)

                    priceValue = 0.0



                    for (i in 0 until dbHelper!!.getMenu().size) {

                        priceValue =
                            priceValue + addIncreasePriceHole(dbHelper!!.getMenu().get(i).totalPrice!!,dbHelper!!.getMenu().get(i).rating!!).toDouble()
                    }

                    addPrice!!.text = customText(
                        activity!!,
                        "€ " + DecimalFormat("##.##").format(priceValue) + " "+getString(R.string.Plus_Taxes),
                        2 + categoryData!!.get(i).menusList.get(j).price!!.length,
                        "SemiBold",
                        ""
                    )

                    addItemTV!!.text =
                        customText(
                            activity!!,
                            getString(R.string.Added_Item) + dbHelper!!.getMenuCountValue1("" + restaurantData!!.restaurantId),
                            13,
                            "SemiBold",
                            ""
                        )
                    /*}else{
                        dbHelper!!.deleteRestaurant()

                        dbHelper!!.deleteToppinsFull()

                        dbHelper!!.deleteToppinsGroupFull()

                        dbHelper!!.deleteMenu()

                        dbHelper!!.deleteCategory()

                        dbHelper!!.addMenu_info(
                            categoryData!!.get(i).menusList.get(j),
                            "" + restaurantData!!.restaurantId
                        )
                    }
*/
                    for (k in 0 until categoryData!!.get(i).menusList.get(j).toppinsGroupList.size) {

                        for (l in 0 until categoryData!!.get(i).menusList.get(j).toppinsGroupList.get(
                            k
                        ).toppinsList.size) {

                            if (categoryData!!.get(i).menusList.get(j).toppinsGroupList.get(k).toppinsList.get(
                                    l
                                ).categoryId.equals("1")
                            ) {
                                /*dbHelper!!.deleteRestaurant()

                                dbHelper!!.deleteCategory()

                               dbHelper!!.deleteMenu()*/

                                /*           dbHelper!!.addMenu_info(
                                    categoryData!!.get(i).menusList.get(j),
                                    "" + restaurantData!!.restaurantId
                                )
*/

                                if (dbHelper!!.getRestrent().equals("") || dbHelper!!.getRestrent().equals(
                                        restaurantData!!.restaurantId
                                    )
                                ) {
                                    dbHelper!!.addItem_info(
                                        categoryData!!.get(i),
                                        "" + restaurantData!!.restaurantId
                                    )

                                    dbHelper!!.addRestaurant_info(restaurantData!!)

                                    dbHelper!!.addToppinsGroup_info(
                                        categoryData!!.get(i).menusList.get(
                                            j
                                        ).toppinsGroupList.get(k)
                                        , "" + restaurantData!!.restaurantId
                                    )

                                    if (categoryData!!.get(i).menusList.get(j).toppinsGroupList.get(
                                            k
                                        ).toppinType.equals("1")
                                    ) {
                                        dbHelper!!.deleteToppins(
                                            categoryData!!.get(i).menusList.get(
                                                j
                                            ).toppinsGroupList.get(k).toppinsGroupId!!
                                        )
                                    }

                                    dbHelper!!.deleteToppinsDelete(
                                        categoryData!!.get(i).menusList.get(
                                            j
                                        ).toppinsGroupList!!.get(k).toppinsList!!.get(l).toppinsId!!
                                    )


                                    dbHelper!!.addToppins_info(
                                        categoryData!!.get(i).menusList.get(j).toppinsGroupList.get(
                                            k
                                        ).toppinsList.get(l),
                                        "" + restaurantData!!.restaurantId,
                                        categoryData!!.get(i).menusList.get(j).menuId!!
                                    )
                                }
                            }
                        }
                    }

                    detailedViewMainAdapter!!.notifyDataSetChanged()
                }
            }
        }
    }

    override fun setDelete(type: String?, position: Double?, isOpen: String?, pos: String?) {

        dbHelper!!.deleteMenuValue(isOpen!!)

        dbHelper!!.deleteToppinsGroupFull(isOpen!!)

        dbHelper!!.deleteToppinsDeletemenu(isOpen!!)

        detailedViewMainAdapter!!.notifyDataSetChanged()



        if (dbHelper!!.getMenu() != null) {

            priceValue = 0.0
            if (dbHelper!!.getMenu().size != 0) {
                for (i in 0 until dbHelper!!.getMenu().size) {

                    priceValue = priceValue + addIncreasePriceHole(dbHelper!!.getMenu().get(i).totalPrice!!,dbHelper!!.getMenu().get(i).rating!!).toDouble()
                }

                addPrice!!.text = customText(
                    activity!!,
                    "€ " + DecimalFormat("##.##").format(priceValue) + " "+getString(R.string.Plus_Taxes),
                    2,
                    "SemiBold",
                    ""
                )

                addItemTV!!.text =
                    customText(
                        activity!!,
                        getString(R.string.Added_Item) + dbHelper!!.getMenuCountValue1("" + restaurantId),
                        13,
                        "SemiBold",
                        ""
                    )

                priceLayout!!.visibility = View.VISIBLE

                pricedup!!.visibility = View.VISIBLE
            } else {
                priceLayout!!.visibility = View.GONE

                pricedup!!.visibility = View.GONE
            }
        }

    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {

        if (responseObj != null) {
            loadingScreenClose(activity)
            if (requestType == Constant.MEMBER_getRestaurantData_URL_RQ) {

                if ((responseObj as BaseRS).status.equals("3026")) {
                    restaurantData = (responseObj as BaseRS).fetchData!!.restaurantData!!

                    reviewTv!!.text = (responseObj as BaseRS).fetchData!!.reviewCount+"+ rating"

                    hotelName!!.setText((responseObj as BaseRS).fetchData!!.restaurantData!!.restaurantName)

                    timeTV!!.setText((responseObj as BaseRS).fetchData!!.restaurantData!!.distance+" KM")

                    if((responseObj as BaseRS).fetchData!!.restaurantData!!.minimumOrder != null) {

                        priceTV!!.setText("€ " + (responseObj as BaseRS).fetchData!!.restaurantData!!.minimumOrder)

                        minimum =
                            (responseObj as BaseRS).fetchData!!.restaurantData!!.minimumOrder!!.toDouble()
                    }else{
                        priceTV!!.setText("€ 0")

                        minimum = 0.0
                    }

                    if(Constant.DeliveryType.equals("3")) {
                        if ((responseObj as BaseRS).fetchData!!.selfPickupMinimumAmount != null) {
                            priceTV!!.setText("€ " + (responseObj as BaseRS).fetchData!!.selfPickupMinimumAmount)

                            minimum =
                                (responseObj as BaseRS).fetchData!!.selfPickupMinimumAmount!!.toDouble()
                        } else {
                            priceTV!!.setText("€ 0")

                            minimum = 0.0
                        }
                    }

                    openStatusTV!!.text = ""+(responseObj as BaseRS).fetchData!!.restaurantOpenStatus

                    if(!(responseObj as BaseRS).fetchData!!.restaurantOpenStatus!!.equals("Closed"))
                    {
                        openStatusTV!!.visibility = View.GONE
                    }else{
                        openStatusTV!!.visibility = View.VISIBLE
                    }


                    if((responseObj as BaseRS).fetchData!!.description != null) {

                        restaruntDes = "" + (responseObj as BaseRS).fetchData!!.description!!
                    }

                    ratingTV!!.text = (responseObj as BaseRS).fetchData!!.restaurantData!!.rating

                    restaurantId =
                        "" + (responseObj as BaseRS).fetchData!!.restaurantData!!.restaurantId

                    Constant.favStringValue = ""
                    for (i in 0 until (responseObj as BaseRS).fetchData!!.favoriteList!!.size) {
                        if (Constant.favStringValue.equals("")) {
                            Constant.favStringValue =
                                "" + (responseObj as BaseRS).fetchData!!.favoriteList!!.get(i).restaurantId
                        } else {
                            Constant.favStringValue =
                                Constant.favStringValue + "," + (responseObj as BaseRS).fetchData!!.favoriteList!!.get(
                                    i
                                ).restaurantId
                        }

                    }

                    val favString1 =
                        Constant.favStringValue.split(",")
                    if (favString!!.equals("1")) {

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
                    }

                    for (i in 0 until favString1!!.size) {
                        if (favString1[i].equals(restaurantId)) {

                            if (favString!!.equals("0")) {

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

                            favorite!!.tag = "1"
                            selectFav = "1"

                            //selectFav= "1"

                        }
                    }


                    restToken =
                        "" + (responseObj as BaseRS).fetchData!!.restaurantData!!.restaurantReferenceCode

                    var str =
                        (responseObj as BaseRS).fetchData!!.restaurantData!!.restaurantImagesList

                    Log.v("===]]'''']" + str, "====")

                    str = str!!.replace("[", "").toString()
                    str = str!!.replace("]", "").toString()
                    val arrOfStr =
                        str.split(",")

                    Log.v("===]]]" + arrOfStr[0], "====")

                    if (!arrOfStr[0].equals("")) {

                        Img = (responseObj as BaseRS).restaurantPath + "" + (responseObj as BaseRS).fetchData!!.restaurantData!!.folderName + "/" + arrOfStr[0]

                        Picasso.with(context)
                            .load((responseObj as BaseRS).restaurantPath + "" + (responseObj as BaseRS).fetchData!!.restaurantData!!.folderName + "/" + arrOfStr[0])
                            .resize(450, 450).transform(RoundedTransformation(16, 0)).placeholder(R.drawable.restaurant_placeholder)
                            .into(bannerImg)

                        Picasso.with(activity!!).load((responseObj as BaseRS).restaurantPath + "" + (responseObj as BaseRS).fetchData!!.restaurantData!!.folderName + "/" + arrOfStr[0]).resize(450, 450).placeholder(R.drawable.restaurant_placeholder).into(hotelIcon)

                        Picasso.with(activity!!).load((responseObj as BaseRS).restaurantPath + "" + (responseObj as BaseRS).fetchData!!.restaurantData!!.folderName + "/" + arrOfStr[0]).resize(450, 450).placeholder(R.drawable.restaurant_placeholder).into(hotelIcon1)
                    }
                    //Picasso.with(activity!!).load(R.drawable.img_1).resize(450, 450).into(bannerImg)

                    hotelDescription!!.setText((responseObj as BaseRS).fetchData!!.restaurantData!!.street + "," + (responseObj as BaseRS).fetchData!!.restaurantData!!.town)

                    hotelName1!!.setText((responseObj as BaseRS).fetchData!!.restaurantData!!.restaurantName)

                    hotelDescription1!!.setText((responseObj as BaseRS).fetchData!!.restaurantData!!.street + "," + (responseObj as BaseRS).fetchData!!.restaurantData!!.town)

                    if (categoryData!!.size != 0) {
                        categoryData!!.clear()
                    }

                    categoryData1 = (responseObj as BaseRS).fetchData!!.categoryData!!

                    for (i in 0 until (responseObj as BaseRS).fetchData!!.categoryData!!.size) {

                        if ((responseObj as BaseRS).fetchData!!.categoryData!!.get(i).menusList.size != 0) {

                            adapterModel = AdapterModel(
                                0,
                                "" + (responseObj as BaseRS).fetchData!!.categoryData!!.get(i).categoryName,
                                "https://i2.wp.com/www.vegrecipesofindia.com/wp-content/uploads/2016/08/hara-bhara-kabab-2a.jpg",
                                "0",
                                "",
                                " 976",
                                " 425",
                                "Tatabad,Gandhipuram",
                                "4.4",
                                "" + (responseObj as BaseRS).fetchData!!.categoryData!!.get(i).categoryReferenceCode,
                                "3054 Ratings>",
                                "5 Star Given by you",
                                "" + (responseObj as BaseRS).fetchData!!.categoryData!!.get(i).categoryId,
                                "",
                                "",
                                "",
                                ""+(responseObj as BaseRS).restaurantPath + "" + (responseObj as BaseRS).fetchData!!.restaurantData!!.folderName,
                                "",
                                0,
                                0,
                                0,
                                (responseObj as BaseRS).fetchData!!.categoryData!!.get(i).menusList!!
                            )

                            categoryData!!.add(adapterModel)
                        }


                    }

                    adapterModel = AdapterModel(
                        0,
                        "",
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
                        1,
                        0,
                        0,
                        adapterCategories
                    )
                    categoryData!!.add(adapterModel)

                    detailedViewMainAdapter!!.notifyDataSetChanged()

                    filterDetailsPageAdapter!!.notifyDataSetChanged()

                    if (dbHelper!!.getMenu() != null) {

                        if (dbHelper!!.getMenu().size != 0) {
                            for (i in 0 until dbHelper!!.getMenu().size) {

                                priceValue =
                                    priceValue + addIncreasePriceHole(dbHelper!!.getMenu().get(i).totalPrice!!,dbHelper!!.getMenu().get(i).rating!!).toDouble()
                            }

                            addPrice!!.text = customText(
                                activity!!,
                                "€ " + DecimalFormat("##.##").format(priceValue) + " "+getString(R.string.Plus_Taxes),
                                2,
                                "SemiBold",
                                ""
                            )

                            addItemTV!!.text =
                                customText(
                                    activity!!,
                                    getString(R.string.Added_Item) + dbHelper!!.getMenuCountValue1("" + restaurantId),
                                    13,
                                    "SemiBold",
                                    ""
                                )

                            priceLayout!!.visibility = View.VISIBLE

                            pricedup!!.visibility = View.VISIBLE
                        }
                    }
                }
                else if ((responseObj as BaseRS).status.equals("3027")){

                    showToast(activity!!,""+(responseObj as BaseRS).message)

                    callBlacklisting!!.fragmentBack("")

                }
            }else if (requestType == Constant.MEMBER_favoriteRestaurantListAdd_URL_RQ) {

            webService()
            }else if (requestType == Constant.MEMBER_favoriteRestaurantListRemove_URL_RQ) {
                webService()
            }
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, i: Int) {
        if (mMaxScrollSize == 0) mMaxScrollSize = appBarLayout!!.totalScrollRange
        val percentage = Math.abs(i) * 100 / mMaxScrollSize

        if (percentage > 98) {
            back!!.setColorFilter(
                colorIcon(
                    activity!!,
                    R.color.colorBlack,
                    R.drawable.abc_ic_ab_back_material,
                    back!!
                ),
                PorterDuff.Mode.SRC_ATOP
            )

            if (selectFav.equals("1")) {
                favorite!!.setColorFilter(
                    colorIcon(
                        activity!!,
                        R.color.colorBlack,
                        R.drawable.heart_selected,
                        favorite!!
                    ),
                    PorterDuff.Mode.SRC_ATOP
                )
               // favorite!!.tag = "0"
            } else {
                favorite!!.setColorFilter(
                    colorIcon(activity!!, R.color.colorBlack, R.drawable.favorite, favorite!!),
                    PorterDuff.Mode.SRC_ATOP
                )
                //favorite!!.tag = "1"
            }
            favString = "1"
            search!!.setColorFilter(
                colorIcon(activity!!, R.color.colorBlack, R.drawable.search, search!!),
                PorterDuff.Mode.SRC_ATOP
            )

            //favString = "1"

            favorite!!.tag = "1"
            titleVisible!!.visibility = View.VISIBLE
        } else if (percentage < 98) {
            back!!.setColorFilter(
                colorIcon(
                    activity!!,
                    R.color.colorWhite,
                    R.drawable.abc_ic_ab_back_material,
                    back!!
                ),
                PorterDuff.Mode.SRC_ATOP
            )
            if (selectFav!!.equals("1")) {
                favorite!!.setColorFilter(
                    colorIcon(
                        activity!!,
                        R.color.colorWhite,
                        R.drawable.heart_selected,
                        favorite!!
                    ),
                    PorterDuff.Mode.SRC_ATOP
                )
                //favorite!!.tag = "0"
            } else {
                favorite!!.setColorFilter(
                    colorIcon(activity!!, R.color.colorWhite, R.drawable.favorite, favorite!!),
                    PorterDuff.Mode.SRC_ATOP
                )
                //favorite!!.tag = "1"
            }
            favString = "0"

            //favorite!!.tag = "0"
            search!!.setColorFilter(
                colorIcon(activity!!, R.color.colorWhite, R.drawable.search, search!!),
                PorterDuff.Mode.SRC_ATOP
            )
            titleVisible!!.visibility = View.GONE
        }
        Log.v("kkkk", "value" + percentage);
    }
}
