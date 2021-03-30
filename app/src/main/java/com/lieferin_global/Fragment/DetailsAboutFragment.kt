package com.lieferin_global.Fragment

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.net.Uri
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
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Activity.ResetPassword
import com.lieferin_global.Adapter.ReviewOrderDetailsAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.util.ArrayList


class DetailsAboutFragment : Fragment(),View.OnClickListener,ResponseListener {

    var rootView : View? = null

    var Img: String=""

    var latitudeString = ""

    var logntitudeString = ""

    var restaurantId: String=""

    var descriptionInfo : TextView? = null

    var moreInfoDescriptionLayout : RelativeLayout? = null

    var moreInfoLayout : RelativeLayout? = null

    var bannerImg:ImageView? = null

    var back:ImageView? = null

    var dishTitle: TextView? = null

    var dishDescription: TextView? = null

    var dishTiming: TextView? = null

    var dishOpen: TextView? = null

    var dishPrice: TextView? = null

    var ratingTV: TextView? = null

    var reviewTV : TextView? = null

    var reviewDescriptionTV : TextView? = null

    var moreInfo: TextView? = null

    var addressTV : TextView? = null

    var moreInfoDescription: TextView? = null

    var tableBooking: TextView? = null

    var tableBookingDescription : TextView? = null

    var addressDesTV : TextView? = null

    var copyIV : ImageView? = null

    var copyLocationTV : TextView? = null

    var getIV : ImageView? = null

    var getDirectionTV : TextView? = null

    var tableBookingRR : RelativeLayout? = null

    fun DetailsTableBookingpageFragment() {}

    var callBlacklisting: CallBlacklisting? = null

    var copyLocationLL : LinearLayout? = null

    var getDirectionLL : LinearLayout? = null

    var title: String? = null

    var favorite: ImageView? = null

    var tableBookingCardView : CardView? = null

    var dummyLL : LinearLayout? = null

    var moreInformationLayout : LinearLayout? = null

    var reviewRecyclerView : RecyclerView? = null

    var reviewOrderDetailsAdapter: ReviewOrderDetailsAdapter? = null

    internal var adapterTrending: MutableList<AdapterModel> = ArrayList<AdapterModel>()

    internal lateinit var adapterModel: AdapterModel

    var timeList: MutableList<String>? = ArrayList()

    var adapterCategories: MutableList<Product> = ArrayList()

    internal lateinit var adapterModel1: AdapterModel

    internal var adapterTrending1: MutableList<AdapterModel> = ArrayList<AdapterModel>()

    var reviewText : TextView? = null

    var userReviewTV : TextView? = null

    var rating5 : LinearLayout? = null

    var rating4 : LinearLayout? = null

    var rating3 : LinearLayout? = null

    var rating2 : LinearLayout? = null

    var rating1 : LinearLayout? = null

    var starFive : TextView? = null

    var starFour : TextView? = null

    var starThree : TextView? = null

    var starTwo : TextView? = null

    var starOne : TextView? = null

    var starImage : ImageView? = null

    var starImageFour : ImageView? = null

    var starImageThree : ImageView? = null

    var starImageTwo : ImageView? = null

    var starImageOne : ImageView? = null

    var dbHelper : DBHelper? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_details_table_bookingpage, container, false)

        dbHelper = DBHelper(activity)

        bannerImg = rootView!!.rootView!!.findViewById(R.id.bannerImg) as ImageView

        tableBookingCardView= rootView!!.rootView!!.findViewById(R.id.tableBookingCardView) as CardView

        dummyLL= rootView!!.rootView!!.findViewById(R.id.dummyLL) as LinearLayout

        moreInformationLayout= rootView!!.rootView!!.findViewById(R.id.moreInformationLayout) as LinearLayout

        Picasso.with(activity!!).load(R.drawable.item_placeholder).resize(450,450).into(bannerImg)

        tableBookingRR  = rootView!!.findViewById(R.id.tableBookingRR) as RelativeLayout

        tableBookingRR!!.setOnClickListener(this)

        back = rootView!!.findViewById(R.id.back) as ImageView

        back!!.setOnClickListener(this)

        back!!.setColorFilter(
            colorIcon(activity!!,R.color.colorWhite,R.drawable.abc_ic_ab_back_material,back!!),
            PorterDuff.Mode.SRC_ATOP)


        favorite = rootView!!.findViewById(R.id.favorite) as ImageView

        favorite!!.setColorFilter(
            colorIcon(activity!!, R.color.colorWhite, R.drawable.favorite, favorite!!),
            PorterDuff.Mode.SRC_ATOP
        )

        favorite!!.setOnClickListener(this)

        favorite!!.tag = "0"
        copyIV = rootView!!.findViewById(R.id.copyIV) as ImageView

        copyIV!!.setColorFilter(
            colorIcon(activity!!,R.color.redColor,R.drawable.copy,copyIV!!),
            PorterDuff.Mode.SRC_ATOP)

        copyIV!!.setOnClickListener(this)


        getIV = rootView!!.findViewById(R.id.getIV) as ImageView

        getIV!!.setColorFilter(
            colorIcon(activity!!,R.color.redColor,R.drawable.copy,getIV!!),
            PorterDuff.Mode.SRC_ATOP)

        copyIV!!.setOnClickListener(this)

        dishTitle = rootView!!.findViewById(R.id.dishTitle) as TextView

        dishDescription = rootView!!.findViewById(R.id.dishDescription) as TextView

        dishTiming = rootView!!.findViewById(R.id.dishTiming) as TextView

        dishOpen = rootView!!.findViewById(R.id.dishOpen) as TextView

        dishPrice = rootView!!.findViewById(R.id.dishPrice) as TextView

        ratingTV = rootView!!.findViewById(R.id.ratingTV) as TextView

        reviewTV = rootView!!.findViewById(R.id.reviewTV) as TextView

        reviewTV!!.typeface = fontStyle(activity!!,"SemiBold")

        reviewDescriptionTV = rootView!!.findViewById(R.id.reviewDescriptionTV) as TextView

        reviewDescriptionTV!!.typeface = fontStyle(activity!!,"Light")

        moreInfo = rootView!!.findViewById(R.id.moreInfo) as TextView

        moreInfo!!.typeface = fontStyle(activity!!,"")

        moreInfoLayout = rootView!!.findViewById(R.id.moreInfoLayout) as RelativeLayout

        moreInfoLayout!!.setOnClickListener(this)

        moreInfoDescriptionLayout = rootView!!.findViewById(R.id.moreInfoDescriptionLayout) as RelativeLayout

        moreInfoDescriptionLayout!!.visibility = View.GONE

        moreInfoDescription = rootView!!.findViewById(R.id.moreInfoDescription) as TextView

        moreInfoDescription!!.typeface = fontStyle(activity!!,"Light")

        descriptionInfo = rootView!!.findViewById(R.id.descriptionInfo) as TextView

        descriptionInfo!!.typeface = fontStyle(activity!!,"Light")

        tableBooking = rootView!!.findViewById(R.id.tableBooking) as TextView

        tableBooking!!.typeface = fontStyle(activity!!,"")

        tableBookingDescription = rootView!!.findViewById(R.id.tableBookingDescription) as TextView

        tableBookingDescription!!.typeface = fontStyle(activity!!,"Light")

        addressTV = rootView!!.findViewById(R.id.addressTV) as TextView

        addressTV!!.typeface = fontStyle(activity!!,"")

        addressDesTV = rootView!!.findViewById(R.id.addressDesTV) as TextView

        addressDesTV!!.typeface = fontStyle(activity!!,"")

        copyLocationTV = rootView!!.findViewById(R.id.copyLocationTV) as TextView

        copyLocationTV!!.typeface = fontStyle(activity!!,"")

        getDirectionTV = rootView!!.findViewById(R.id.getDirectionTV) as TextView

        getDirectionTV!!.typeface = fontStyle(activity!!,"")


        dishTitle!!.setTypeface(fontStyle(activity!!, ""))


        dishDescription!!.setTypeface(fontStyle(activity!!, ""))



        dishTiming!!.setTypeface(fontStyle(activity!!, ""))



        dishOpen!!.setTypeface(fontStyle(activity!!, ""))



        dishPrice!!.setTypeface(fontStyle(activity!!, ""))



        ratingTV!!.setTypeface(fontStyle(activity!!, ""))



        val bundle = this.arguments
        if (bundle != null) {
            title = bundle.getString("restaurantReferenceCode")
        }

        val obj = JSONObject()
        obj.put("restaurantReferenceCode", "" + title)

        /*obj.put("latitude", "52.422271")
        obj.put("longitude", "13.411581")
*/
        obj.put("latitude", ""+Constant.latitudeAdd)
        obj.put("longitude", ""+Constant.longtitudeAdd)
        Log.v("Json", "Value" + obj)
        RequestManager.setRestaurant(activity, obj, this);

        copyLocationLL = rootView!!.findViewById(R.id.copyLocationLL) as LinearLayout

        copyLocationLL!!.setOnClickListener(this)

        getDirectionLL = rootView!!.findViewById(R.id.getDirectionLL) as LinearLayout

        getDirectionLL!!.setOnClickListener(this)

        reviewText = rootView!!.findViewById(R.id.reviewText)

        reviewText!!.typeface = fontStyle(activity!!,"")

        userReviewTV = rootView!!.findViewById(R.id.userReviewTV)

        userReviewTV!!.typeface = fontStyle(activity!!,"")

        reviewRecyclerView = rootView!!.findViewById(R.id.reviewRecyclerView) as RecyclerView

        reviewOrderDetailsAdapter = ReviewOrderDetailsAdapter(activity!!,adapterTrending)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity!!)

        reviewRecyclerView!!.layoutManager = mLayoutManager

        reviewRecyclerView!!.itemAnimator!!.addDuration = 5000

        //reviewOrderDetailsAdapter!!.setCallback(this)

        reviewRecyclerView!!.adapter = reviewOrderDetailsAdapter

        //showDataTrending()

        rating1 = rootView!!.findViewById(R.id.rating1) as LinearLayout

        rating1!!.setOnClickListener(this)

        rating2 = rootView!!.findViewById(R.id.rating2) as LinearLayout

        rating2!!.setOnClickListener(this)

        rating3 = rootView!!.findViewById(R.id.rating3) as LinearLayout

        rating3!!.setOnClickListener(this)

        rating4 = rootView!!.findViewById(R.id.rating4) as LinearLayout

        rating4!!.setOnClickListener(this)

        rating5 = rootView!!.findViewById(R.id.rating5) as LinearLayout

        rating5!!.setOnClickListener(this)

        starFive= rootView!!.findViewById(R.id.starFive) as TextView

        starFive!!.typeface = fontStyle(activity!!,"")

        starFour= rootView!!.findViewById(R.id.starFour) as TextView

        starFour!!.typeface = fontStyle(activity!!,"")

        starThree= rootView!!.findViewById(R.id.starThree) as TextView

        starThree!!.typeface = fontStyle(activity!!,"")

        starTwo= rootView!!.findViewById(R.id.starTwo) as TextView

        starTwo!!.typeface = fontStyle(activity!!,"")

        starOne= rootView!!.findViewById(R.id.starOne) as TextView

        starOne!!.typeface = fontStyle(activity!!,"")

        starImage= rootView!!.findViewById(R.id.starImage) as ImageView

        starImage!!.setColorFilter(colorIcon(activity!!,R.color.colorBlack,R.drawable.star,starImage!!))

        starImageTwo= rootView!!.findViewById(R.id.starImageTwo) as ImageView

        starImageTwo!!.setColorFilter(colorIcon(activity!!,R.color.colorBlack,R.drawable.star,starImageTwo!!))

        starImageThree= rootView!!.findViewById(R.id.starImageThree) as ImageView

        starImageThree!!.setColorFilter(colorIcon(activity!!,R.color.colorBlack,R.drawable.star,starImageThree!!))

        starImageFour= rootView!!.findViewById(R.id.starImageFour) as ImageView

        starImageFour!!.setColorFilter(colorIcon(activity!!,R.color.colorBlack,R.drawable.star,starImageFour!!))

        starImageOne= rootView!!.findViewById(R.id.starImageOne) as ImageView

        starImageOne!!.setColorFilter(colorIcon(activity!!,R.color.colorBlack,R.drawable.star,starImageOne!!))


        webService()
        return rootView
    }

    fun webService(){
        val obj = JSONObject()
        obj.put("token", ""+ dbHelper!!.getUserDetails().token)

        obj.put("restaurantReferenceCode", ""+ title)

        RequestManager.setFetchRestaurantRating(activity,obj,this)
    }
    fun showDataTrending() {

        if (adapterTrending.size != 0) {
            adapterTrending.clear()
        }
        adapterModel = AdapterModel(
            0,
            "Special - Thai - Veg",
            "1 Vegitables,Dal makhani, 3 Roti, rice, Curd,Salad &amp; Papad",
            "€ 18.00",
            "3.0",
            "€ 24.00",
            "https://engineerbabu.com/blog/wp-content/uploads/2016/12/KFC-500x403-1.jpg",
            "Current Task",
            "Order Id : #1235678",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "Senthil",
            "Description",
            "",
            0,
            0,
            0,
            adapterCategories
        )
        adapterTrending.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "Deluxe - Thai - Veg",
            "1 Vegitables,Dal makhani, 3 Roti, rice, Curd,Salad &amp; Papad",
            "€ 28.00",
            "3.5",
            "",
            "https://img.etimg.com/thumb/width-640,height-480,imgsize-202029,resizemode-1,msid-70801191/mcdonalds-halal-certificate-gives-it-a-jhatka-in-india.jpg",
            "New Task",
            "Order Id : #1235678",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "Arun",
            "Description",
            "",
            0,
            0,
            0,
            adapterCategories
        )
        adapterTrending.add(adapterModel)

       // backGround()

        reviewOrderDetailsAdapter!!.notifyDataSetChanged()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }

    override fun onClick(view: View?) {

        when(view!!.id)
        {
            R.id.tableBookingRR ->{

                if(moreInfoLayout!!.visibility == View.GONE) {
                    if (moreInfoDescriptionLayout!!.visibility == View.GONE) {
                        moreInfoDescriptionLayout!!.visibility = View.VISIBLE
                    } else {
                        moreInfoDescriptionLayout!!.visibility = View.GONE
                    }
                }else{
                    val bundle = Bundle()
                    bundle.putString("Title", title)
                    callBlacklisting!!.fragmentChange("Table Booking",bundle)
                }
            }
            R.id.copyLocationLL ->{
                val clipboard =
                    context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Copied Text", addressDesTV!!.text.toString())
                clipboard.setPrimaryClip(clip)
                showToast(activity!!,"Copied")
            }
            R.id.getDirectionLL ->{
                val gmmIntentUri =
                    Uri.parse("geo:0,0?q="+addressDesTV!!.text.toString())
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }


            R.id.favorite -> {
                if (favorite!!.tag.toString().equals("0")) {
                    val obj = JSONObject()
                    obj.put("restaurantReferenceCode", "" + title)

                    obj.put("token", "" + dbHelper!!.getUserDetails().token)

                    obj.put("status", "1")

                    Log.v("Json", "Value" + obj)
                    RequestManager.setFavListAdd(activity, obj, this);

                } else {
                    val obj = JSONObject()
                    obj.put("restaurantReferenceCode", "" + title)

                    obj.put("token", "" + dbHelper!!.getUserDetails().token)

                    obj.put("status", "2")

                    Log.v("Json", "Value" + obj)
                    RequestManager.setFavListRemove(activity, obj, this);

                }
            }


            R.id.moreInfoLayout->{

                if(moreInfoDescriptionLayout!!.visibility == View.GONE)
                {
                    moreInfoDescriptionLayout!!.visibility = View.VISIBLE
                }else{
                    moreInfoDescriptionLayout!!.visibility = View.GONE
                }

            }

            R.id.back ->{
                callBlacklisting!!.fragmentBack("")
            }

            R.id.rating1->{
                backGround()
                rating1!!.setBackgroundResource(R.drawable.rating_select)

                starOne!!.setTextColor(Color.parseColor("#ffffff"))
                starImageOne!!.setColorFilter(colorIcon(activity!!,R.color.colorWhite,R.drawable.star,starImage!!))

                FilterValue("1")
            }
            R.id.rating2->{
                backGround()
                rating2!!.setBackgroundResource(R.drawable.rating_select)
                starTwo!!.setTextColor(Color.parseColor("#ffffff"))
                starImageTwo!!.setColorFilter(colorIcon(activity!!,R.color.colorWhite,R.drawable.star,starImageOne!!))
                FilterValue("2")
            }
            R.id.rating3->{
                backGround()
                rating3!!.setBackgroundResource(R.drawable.rating_select)
                starThree!!.setTextColor(Color.parseColor("#ffffff"))
                starImageThree!!.setColorFilter(colorIcon(activity!!,R.color.colorWhite,R.drawable.star,starImageTwo!!))
                FilterValue("3")
            }
            R.id.rating4->{
                backGround()
                rating4!!.setBackgroundResource(R.drawable.rating_select)
                starFour!!.setTextColor(Color.parseColor("#ffffff"))
                starImageFour!!.setColorFilter(colorIcon(activity!!,R.color.colorWhite,R.drawable.star,starImageThree!!))
                FilterValue("4")
            }
            R.id.rating5->{
                backGround()
                rating5!!.setBackgroundResource(R.drawable.rating_select)
                starFive!!.setTextColor(Color.parseColor("#ffffff"))
                starImage!!.setColorFilter(colorIcon(activity!!,R.color.colorWhite,R.drawable.star,starImageFour!!))
                FilterValue("5")
            }
        }

    }
    fun backGround()
    {
        rating1!!.setBackgroundResource(R.drawable.search_back_black)

        rating2!!.setBackgroundResource(R.drawable.search_back_black)

        rating3!!.setBackgroundResource(R.drawable.search_back_black)

        rating4!!.setBackgroundResource(R.drawable.search_back_black)

        rating5!!.setBackgroundResource(R.drawable.search_back_black)

        starOne!!.setTextColor(Color.parseColor("#000000"))

        starTwo!!.setTextColor(Color.parseColor("#000000"))

        starThree!!.setTextColor(Color.parseColor("#000000"))

        starFour!!.setTextColor(Color.parseColor("#000000"))

        starFive!!.setTextColor(Color.parseColor("#000000"))

        starImage!!.setColorFilter(colorIcon(activity!!,R.color.colorBlack,R.drawable.star,starImage!!))

        starImageTwo!!.setColorFilter(colorIcon(activity!!,R.color.colorBlack,R.drawable.star,starImageTwo!!))

        starImageThree!!.setColorFilter(colorIcon(activity!!,R.color.colorBlack,R.drawable.star,starImageThree!!))

        starImageFour!!.setColorFilter(colorIcon(activity!!,R.color.colorBlack,R.drawable.star,starImageFour!!))

        starImageOne!!.setColorFilter(colorIcon(activity!!,R.color.colorBlack,R.drawable.star,starImageOne!!))


    }

    fun FilterValue(rating:String?)
    {

        if (adapterTrending.size != 0) {
            adapterTrending.clear()
        }

        for (i in 0 until adapterTrending1.size) {
            if(rating!!.equals(adapterTrending1.get(i).offerPercentage)) {
                adapterModel = AdapterModel(
                    0,
                    "" + adapterTrending1.get(i).categoryName,
                    "" ,
                    "" ,
                    "" + adapterTrending1.get(i).offerPercentage,
                    "",
                    "",
                    "Current Task",
                    "Order Id : #",
                    "",
                    "3054 Ratings>",
                    "€ ",
                    "1",
                    "",
                    "",
                    ""+adapterTrending1.get(i).openTime,
                    ""+adapterTrending1.get(i).closeTime,
                    "",
                    0,
                    0,
                    0,
                    adapterCategories
                )
                adapterTrending.add(adapterModel)
            }
        }
        reviewOrderDetailsAdapter!!.notifyDataSetChanged()
    }


    override fun onResponseReceived(responseObj: Any?, requestType: Int) {

        if (responseObj != null) {
            if (requestType == Constant.MEMBER_favoriteRestaurantListAdd_URL_RQ) {

                if((responseObj as BaseRS).status.equals("5029"))
                {
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
                }

            }else if(requestType == Constant.MEMBER_fetchRestaurantRating_URL_RQ)
            {
                if (adapterTrending.size != 0) {
                    adapterTrending.clear()
                }
                if (adapterTrending1.size != 0) {
                    adapterTrending1.clear()
                }
                if((responseObj as BaseRS).status.equals("5036"))
                {

                    userReviewTV!!.text = ""+(responseObj as BaseRS).restaurantRatingList!!.size +" User Reviews"

                    for(i in 0 until (responseObj as BaseRS).restaurantRatingList!!.size)
                    {
                        adapterModel = AdapterModel(
                            0,
                            ""+(responseObj as BaseRS).restaurantRatingList!!.get(i).orderId,
                            "",
                            "€ 28.00",
                            ""+(responseObj as BaseRS).restaurantRatingList!!.get(i).rating,
                            "",
                            "https://img.etimg.com/thumb/width-640,height-480,imgsize-202029,resizemode-1,msid-70801191/mcdonalds-halal-certificate-gives-it-a-jhatka-in-india.jpg",
                            "New Task",
                            "Order Id : #1235678",
                            "Very Good",
                            "3054 Ratings>",
                            "5 Star Given by you",
                            "1",
                            "",
                            "",
                            ""+(responseObj as BaseRS).restaurantRatingList!!.get(i).customerName,
                            ""+(responseObj as BaseRS).restaurantRatingList!!.get(i).feedback,
                            ""+(responseObj as BaseRS).userPath!!+""+(responseObj as BaseRS).restaurantRatingList!!.get(i).profilePicture,
                            0,
                            0,
                            0,
                            adapterCategories
                        )
                        adapterTrending.add(adapterModel)

                    }

                    for(i in 0 until (responseObj as BaseRS).restaurantRatingList!!.size)
                    {
                        adapterModel1 = AdapterModel(
                            0,
                            ""+(responseObj as BaseRS).restaurantRatingList!!.get(i).orderId,
                            "",
                            "€ 28.00",
                            ""+(responseObj as BaseRS).restaurantRatingList!!.get(i).rating,
                            "",
                            "https://img.etimg.com/thumb/width-640,height-480,imgsize-202029,resizemode-1,msid-70801191/mcdonalds-halal-certificate-gives-it-a-jhatka-in-india.jpg",
                            "New Task",
                            "Order Id : #1235678",
                            "Very Good",
                            "3054 Ratings>",
                            "5 Star Given by you",
                            "1",
                            "",
                            "",
                            ""+(responseObj as BaseRS).restaurantRatingList!!.get(i).customerName,
                            ""+(responseObj as BaseRS).restaurantRatingList!!.get(i).feedback,
                            ""+(responseObj as BaseRS).userPath!!+""+(responseObj as BaseRS).restaurantRatingList!!.get(i).profilePicture,
                            0,
                            0,
                            0,
                            adapterCategories
                        )
                        adapterTrending1.add(adapterModel1)

                    }


                    reviewOrderDetailsAdapter!!.notifyDataSetChanged()

                }
            }else if (requestType == Constant.MEMBER_favoriteRestaurantListRemove_URL_RQ) {
                if((responseObj as BaseRS).status.equals("5020"))
                {
                    favorite!!.setColorFilter(
                        colorIcon(
                            activity!!,
                            R.color.colorWhite,
                            R.drawable.favorite,
                            favorite!!
                        ),
                        PorterDuff.Mode.SRC_ATOP
                    )

                    favorite!!.tag = "0"
                }

            } else if (requestType == Constant.MEMBER_getRestaurantData_URL_RQ) {

                if ((responseObj as BaseRS).fetchData != null) {

                if ((responseObj as BaseRS).fetchData!!.tableStatus.equals("0")) {
                    moreInfoLayout!!.visibility = View.GONE

                    tableBookingCardView!!.setCardBackgroundColor(Color.parseColor("#FFFBEA"))

                    tableBooking!!.text = "More Info"

                    tableBookingDescription!!.text = "View additional restaurant details"


                    dummyLL!!.visibility = View.VISIBLE
                } else {
                    moreInfoLayout!!.visibility = View.VISIBLE

                    tableBookingCardView!!.setCardBackgroundColor(Color.parseColor("#E7EEFC"))

                    tableBooking!!.text = "Table Booking"

                    tableBookingDescription!!.text = "Get instant confirmations"

                    dummyLL!!.visibility = View.GONE
                }

                dishTitle!!.setText("" + (responseObj as BaseRS).fetchData!!.restaurantData!!.restaurantName)

                dishDescription!!.setText("Quick Bites - Briyani")

                latitudeString = "" + (responseObj as BaseRS).fetchData!!.latitude

                logntitudeString = "" + (responseObj as BaseRS).fetchData!!.longitude

                reviewTV!!.text = "" + (responseObj as BaseRS).fetchData!!.reviewCount

                descriptionInfo!!.setText("" + (responseObj as BaseRS).fetchData!!.restaurantData!!.description)

                dishTiming!!.setText("" + (responseObj as BaseRS).fetchData!!.restaurantData!!.town)

                dishPrice!!.setText(
                    "" + addPriceDouble(
                        "" + (responseObj as BaseRS).fetchData!!.restaurantData!!.price,
                        2
                    ).toString() + " for two people (Approx)"
                )

                if ((responseObj as BaseRS).fetchData!!.restaurantData!!.rating != null) {
                    ratingTV!!.setText("" + (responseObj as BaseRS).fetchData!!.restaurantData!!.rating)
                } else {
                    ratingTV!!.setText("0.0")
                }

                var str =
                    (responseObj as BaseRS).fetchData!!.restaurantData!!.restaurantImagesList

                Log.v("===]]'''']" + str, "====")

                str = str!!.replace("[", "").toString()
                str = str!!.replace("]", "").toString()
                val arrOfStr =
                    str.split(",")

                Log.v("===]]]" + arrOfStr[0], "====")

                if (!arrOfStr[0].equals("")) {

                    Img =
                        (responseObj as BaseRS).restaurantPath + "" + (responseObj as BaseRS).fetchData!!.restaurantData!!.folderName + "/" + arrOfStr[0]

                    Picasso.with(context)
                        .load((responseObj as BaseRS).restaurantPath + "" + (responseObj as BaseRS).fetchData!!.restaurantData!!.folderName + "/" + arrOfStr[0])
                        .resize(450, 450).transform(RoundedTransformation(16, 0))
                        .placeholder(R.drawable.restaurant_placeholder)
                        .into(bannerImg)

                    Picasso.with(activity!!)
                        .load((responseObj as BaseRS).restaurantPath + "" + (responseObj as BaseRS).fetchData!!.restaurantData!!.folderName + "/" + arrOfStr[0])
                        .resize(450, 450).placeholder(R.drawable.restaurant_placeholder)
                        .into(bannerImg)

                    Picasso.with(activity!!)
                        .load((responseObj as BaseRS).restaurantPath + "" + (responseObj as BaseRS).fetchData!!.restaurantData!!.folderName + "/" + arrOfStr[0])
                        .resize(450, 450).placeholder(R.drawable.restaurant_placeholder)
                        .into(bannerImg)
                }

                addressDesTV!!.text =
                    "" + (responseObj as BaseRS).fetchData!!.restaurantData!!.street + " , " + (responseObj as BaseRS).fetchData!!.restaurantData!!.town

                restaurantId =
                    "" + (responseObj as BaseRS).fetchData!!.restaurantData!!.restaurantId

                val favString1 =
                    Constant.favStringValue.split(",")
                for (i in 0 until favString1!!.size) {
                    if (favString1[i].equals(restaurantId)) {
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
                var categoryValue = ""
                for (i in 0 until (responseObj as BaseRS).fetchData!!.categoryData!!.size) {
                    if (categoryValue!!.equals("")) {
                        categoryValue =
                            (responseObj as BaseRS).fetchData!!.categoryData!!.get(i).categoryName!!
                    } else {
                        categoryValue = categoryValue + "," +
                                (responseObj as BaseRS).fetchData!!.categoryData!!.get(i).categoryName!!
                    }
                }

                dishDescription!!.setText(categoryValue)

            }else{
                    successDialog(activity!!)
                }
            }
        }
    }

    fun successDialog(context: Context)
    {
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
        dialog.setContentView(R.layout.register_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val textSuccess = dialog.findViewById<View>(R.id.textSuccess) as TextView

        textSuccess!!.typeface = fontStyle(context,"")

        textSuccess!!.text = "This Restaurant have no data"

        val okText = dialog.findViewById<View>(R.id.okText) as TextView

        okText!!.typeface = fontStyle(context,"")

        okText!!.setOnClickListener(View.OnClickListener {
            dialog.cancel()
            callBlacklisting!!.fragmentBack("")

        })

        dialog.show()

        //dialog.cancel()
    }
}
