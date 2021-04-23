package com.lieferin_global.Adapter

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductListView
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.text.DecimalFormat


class DetailedViewAdapter(
    context: Context,
    adapterModels: List<Product>,
    point: String,
    point1: String,imagePath: String
) :
    RecyclerView.Adapter<DetailedViewAdapter.MyViewHolder>(),
    DishListPagePastAdapter.CallbackDataAdapter {
    private val adapterModels: List<Product>
    // private val adapterModels1: List<AdapterModel>
    var mCallback: CallbackDataAdapter? = null
    var context: Context
    // var month: String

    var valueInt : Double? = 0.0
    var add: Double =0.0
    var point: String
    var point1: String

    var imagePath: String
    var pos = -1
    var isChecked = true
    var itemView: View? = null
    var dbHelper: DBHelper? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var item_sub_CircleImageView: CircleImageView

        var item_sub_TV: TextView

        var item_sub_description_TV: TextView

        var item_sub_price_TV: TextView

        var item_sub_add_TV: TextView

        var addItem: RelativeLayout

        var add_item_plus: ImageView

        var add_item_increase: TextView

        var add_item_minus: ImageView

        var infoRB: ImageView

        var plus: ImageView

        var textDescription: TextView

        var textInfoDescription: TextView

        var textPrice: TextView

        var extras: RecyclerView

        var priceMinus: ImageView

        var priceAdd: ImageView

        var totalValPrice: TextView

        var textIncrement: TextView

        var subItemsLL: LinearLayout

        var menuLayout : LinearLayout

        var itemIMG: ImageView

        var addLayout : LinearLayout

        var deleteLayout : LinearLayout

        var textPriceOffer  : TextView

        var addPrice : TextView

        var addPriceLL : LinearLayout


        init {

            addPriceLL = itemView.findViewById(R.id.addPriceLL)

            addLayout = itemView.findViewById(R.id.addLayout)

            deleteLayout = itemView.findViewById(R.id.deleteLayout)

            menuLayout = itemView.findViewById(R.id.menuLayout)

            item_sub_CircleImageView = itemView.findViewById(R.id.item_sub_CircleImageView)

            item_sub_TV = itemView.findViewById(R.id.item_sub_TV)

            item_sub_description_TV = itemView.findViewById(R.id.item_sub_description_TV)

            item_sub_price_TV = itemView.findViewById(R.id.item_sub_price_TV)

            item_sub_add_TV = itemView.findViewById(R.id.item_sub_add_TV)

            addItem = itemView.findViewById(R.id.addItem)

            add_item_plus = itemView.findViewById(R.id.add_item_plus)

            add_item_increase = itemView.findViewById(R.id.add_item_increase)

            add_item_minus = itemView.findViewById(R.id.add_item_minus)

            infoRB = itemView.findViewById(R.id.infoRB)

            plus = itemView.findViewById(R.id.plus)

            textDescription = itemView.findViewById(R.id.textDescription)

            textInfoDescription = itemView.findViewById(R.id.textInfoDescription)

            textPrice = itemView.findViewById(R.id.textPrice)

            extras = itemView.findViewById(R.id.extras)

            priceMinus = itemView.findViewById(R.id.priceMinus)

            priceAdd = itemView.findViewById(R.id.priceAdd)

            totalValPrice = itemView.findViewById(R.id.totalValPrice)

            textIncrement = itemView.findViewById(R.id.textIncrement)

            subItemsLL = itemView.findViewById(R.id.subItemsLL)

            itemIMG = itemView.findViewById(R.id.itemIMG)

            textPriceOffer = itemView.findViewById(R.id.textPriceOffer)

            addPrice = itemView.findViewById(R.id.addPrice)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.detail_sub_view, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Log.v("KKK", "===" + point);

        dbHelper = DBHelper(context)

        if(Constant.vegOnly.equals("1")) {

            if (!adapterModels.get(position).type.equals("1")) {
                holder.menuLayout.visibility = View.GONE
            } else {
                holder.menuLayout.visibility = View.VISIBLE
            }
        }

        if (adapterModels.get(position).allergy1 == null) {
            adapterModels.get(position).allergy1 = "0"
        }

        holder.item_sub_TV.setTypeface(fontStyle(context, ""))

        holder.addPrice.setTypeface(fontStyle(context, "SemiBold"))

        Log.v("name "+adapterModels[position].name,"lenth"+adapterModels[position].name!!.length)
        if(adapterModels[position].name!!.length > 20)
        {
            holder.item_sub_TV.setText(adapterModels[position].name!!.substring(0,20)+"...")
        }else{
            holder.item_sub_TV.setText(adapterModels[position].name)
        }

        if(adapterModels.get(position).offerType!!.equals("4"))
        {
            holder.textIncrement.text = "2"
        }else{
            holder.textIncrement.text = "1"
        }


        holder.item_sub_description_TV.setTypeface(fontStyle(context, "Light"))

        holder.item_sub_description_TV.setText(adapterModels[position].menuId)

        holder.item_sub_add_TV.setTypeface(fontStyle(context, "Light"))

        holder.textDescription.setTypeface(fontStyle(context, ""))

        holder.textDescription.text = adapterModels[position].description

        holder.textPrice.setTypeface(fontStyle(context, "SemiBold"))



            if (adapterModels.get(position).offer.equals("0")) {
                holder.textPrice.setText(DecimalFormat("##.##").format(adapterModels.get(position).price!!.toDouble()) + " € ")
                holder.textPriceOffer.visibility = View.GONE
            } else {

                if(!adapterModels.get(position).offerType.equals("4")) {
                if (Constant.BookingType!!.equals("0")) {
                    holder.textPriceOffer.visibility = View.VISIBLE

                    holder.textPrice.setText(
                        DecimalFormat("##.##").format(
                            adapterModels.get(
                                position
                            ).offer!!.toDouble()
                        ) + " € "
                    )

                    holder.textPriceOffer.setText(
                        DecimalFormat("##.##").format(
                            adapterModels.get(
                                position
                            ).price!!.toDouble()
                        ) + " € "
                    )

                    holder.textPriceOffer.setTypeface(fontStyle(context, "Light"))

                    holder.textPriceOffer.setPaintFlags(holder.textPriceOffer.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                } else {
                    holder.textPrice.setText(
                        DecimalFormat("##.##").format(
                            adapterModels.get(
                                position
                            ).price!!.toDouble()
                        ) + " € "
                    )
                    holder.textPriceOffer.visibility = View.GONE
                }
            }else{
                    holder.textPrice.setText(DecimalFormat("##.##").format(adapterModels.get(position).price!!.toDouble()) + " € ")
                    holder.textPriceOffer.visibility = View.GONE
                }
        }

        holder.totalValPrice.setTypeface(fontStyle(context, "SemiBold"))

        holder.textIncrement.setTypeface(fontStyle(context, "SemiBold"))

        if (adapterModels.get(position).categoryId != null) {

            adapterModels.get(position).categoryId = "" + adapterModels.get(position).categoryId!!.toInt()

           // holder.textIncrement.text = adapterModels.get(position).categoryId

            Log.v("jjjjj"+MultipleIncreasePrice(
                holder.textPrice!!.text.toString().replace(" €",""),
                adapterModels.get(position).categoryId!!
            )," == "+adapterModels.get(position).categoryId!!);


            holder.totalValPrice.text = "€ " + DecimalFormat("##.##").format(MultipleIncreasePrice(
                holder.textPrice!!.text.toString().replace(" €",""),
                adapterModels.get(position).categoryId!!
            ).toDouble())


        }


        //valueInt = adapterModels.get(position).price.toDouble()

        for (i in 0 until adapterModels.get(position).toppinsGroupList.size) {


                for (j in 0 until adapterModels.get(position).toppinsGroupList.get(i).toppinsList.size) {
                    if (adapterModels.get(position).toppinsGroupList.get(i).toppinsList.get(j).categoryId != null) {
                        if (adapterModels.get(position).toppinsGroupList.get(i).toppinsList.get(j).categoryId.equals("1")) {


                            valueInt = addIncreasePrice(""+valueInt!!,
                                adapterModels.get(position).toppinsGroupList.get(i).toppinsList.get(
                                    j
                                ).price!!
                            ).toDouble()
                        }
                    }
                }
        }

        holder.totalValPrice.text = "€ " + DecimalFormat("##.##").format(addIncreasePrice(""+valueInt!!,
            holder.textPrice!!.text.toString().replace(" €","")
        ).toDouble())
        //holder.itemSubName.text = adapterModels[position].typeView

        holder.item_sub_add_TV.setText(adapterModels[position].menuReferenceCode)

        holder.plus.setOnClickListener(View.OnClickListener {

            if (adapterModels.get(position).allergy1.equals("0")) {

                holder.plus.animate().rotation(45F)
                    .setDuration(500).start();
                holder.plus.setColorFilter(
                    colorIcon(
                        context,
                        R.color.colorGray,
                        R.drawable.plus,
                        holder.plus
                    )
                )
                Log.v("jkjkjkjk", "===" + point!!)

                for (i in 0 until adapterModels.size) {
                    adapterModels.get(i).allergy1 = "0"
                }
                adapterModels.get(position).allergy1 = "1"

                Constant.valueStringItem =""+holder.textIncrement!!.text.toString()

                mCallback!!.setOnCancel("1", holder.textPrice.text.toString().replace(" € ","").toDouble(), ""+adapterModels[position].menuId, ""+adapterModels[position].toppinsGroupList!!.size)


            } else {
                holder.plus.animate().rotation(0F)
                    .setDuration(500).start();
                holder.plus.setColorFilter(
                    colorIcon(
                        context,
                        R.color.redColor,
                        R.drawable.plus,
                        holder.plus
                    )
                )

                holder.subItemsLL.visibility = View.GONE

                adapterModels.get(position).allergy1 = "0"

                Constant.valueStringItem =""+holder.textIncrement!!.text.toString()

                mCallback!!.setOnCancel("0", holder.textPrice.text.toString().replace("€ ","").toDouble(), ""+adapterModels[position].menuId, ""+adapterModels[position].toppinsGroupList!!.size)
            }
        })
        if (adapterModels.get(position).allergy1.equals("0")) {
            holder.subItemsLL.visibility = View.GONE
            holder.plus.animate().rotation(0F)
                .setDuration(500).start();
            holder.plus.setColorFilter(
                colorIcon(
                    context,
                    R.color.redColor,
                    R.drawable.plus,
                    holder.plus
                )
            )
        } else {
            if (adapterModels[position].toppinsGroupList!!.size != 0) {
                holder.subItemsLL!!.visibility = View.VISIBLE
            } else {
                holder.subItemsLL!!.visibility = View.GONE
            }
            holder.plus.animate().rotation(45F)
                .setDuration(500).start();
            holder.plus.setColorFilter(
                colorIcon(
                    context,
                    R.color.colorGray,
                    R.drawable.plus,
                    holder.plus
                )
            )
        }

        if (dbHelper!!.getMenuList(adapterModels.get(position).menuId!!) != null) {

            if (dbHelper!!.getMenuList(adapterModels.get(position).menuId!!).size != 0) {

                holder.deleteLayout!!.visibility = View.VISIBLE

                holder.addItem!!.visibility = View.GONE

            } else {
                holder.deleteLayout!!.visibility = View.GONE

                holder.addItem!!.visibility = View.VISIBLE
            }
        }else{
            holder.deleteLayout!!.visibility = View.GONE

            holder.addItem!!.visibility = View.VISIBLE
        }

        holder.deleteLayout.setOnClickListener(View.OnClickListener {

            mCallback!!.setDelete("Delete",0.0,""+adapterModels.get(position).menuId,"")

            adapterModels.get(position).allergy1 = "0"

        })

        holder.priceAdd.setOnClickListener(View.OnClickListener {

            if(adapterModels.get(position).offerType!!.equals("4"))
            {
                holder.textIncrement!!.setText(
                    "" +
                            addIncreaseDoubleOrderValue(holder.textIncrement!!.text.toString()).toInt()
                )

                Log.v("jjjjj", "====" + valueInt);

                add = addIncreasePriceByeOneGetOne(
                    "" + valueInt!!,
                    holder.textPrice!!.text.toString().replace(" €", "")
                ).toDouble()
                holder.totalValPrice.text = "€ " + DecimalFormat("##.##").format(
                    MultipleIncreasePrice(
                        "" +
                                add!!,
                        holder.textIncrement!!.text.toString()
                    ).toDouble()
                )
            }else {
                holder.textIncrement!!.setText(
                    "" +
                            addIncreasePrice(holder.textIncrement!!.text.toString(), "1").toInt()
                )

                Log.v("jjjjj", "====" + valueInt);

                add = addIncreasePrice(
                    "" + valueInt!!,
                    holder.textPrice!!.text.toString().replace(" €", "")
                ).toDouble()
                holder.totalValPrice.text = "€ " + DecimalFormat("##.##").format(
                    MultipleIncreasePrice(
                        "" +
                                add!!,
                        holder.textIncrement!!.text.toString()
                    ).toDouble()
                )
            }

            mCallback!!.setOnQuantity(""+holder.textIncrement!!.text,""+adapterModels[position].menuId)
        })

        holder.addPriceLL.setOnClickListener(View.OnClickListener {



                var finalValue: Double =
                    holder.totalValPrice.text.toString().replace("€ ", "").toDouble()

                Constant.valueStringItem = "" + holder.textIncrement!!.text.toString()
            if(adapterModels.get(position).offerType!!.equals("4")) {

                finalValue = finalValue / 2
            }

            mCallback!!.setFinal("0", finalValue.toDouble(), ""+adapterModels[position].menuId, ""+adapterModels[position].toppinsGroupList!!.size)
            adapterModels.get(position).allergy1 = "0"
        })

        holder.priceMinus.setOnClickListener(View.OnClickListener {

            if (!holder.textIncrement!!.text.equals("1")) {
                holder.textIncrement!!.setText(
                    "" +
                            DecreaseIncreasePrice(
                                holder.textIncrement!!.text.toString(),
                                "1"
                            ).toInt()
                )
                Constant.valueStringItem =""+holder.textIncrement!!.text.toString()
                holder.totalValPrice.text = "€ " + DecimalFormat("##.##").format(MultipleIncreasePrice(""+
                        add!!,
                    holder.textIncrement!!.text.toString()
                ).toDouble())
            }

            Constant.valueStringItem =""+holder.textIncrement!!.text.toString()
            mCallback!!.setOnQuantity(""+holder.textIncrement!!.text,""+adapterModels[position].menuId)
        })

        holder.priceAdd.setColorFilter(
            colorIcon(
                context,
                com.lieferin_global.R.color.colorGray,
                R.drawable.plus,
                holder.add_item_plus
            )
        )

        holder.priceMinus.setColorFilter(
            colorIcon(
                context,
                R.color.colorGray,
                R.drawable.minus,
                holder.priceMinus
            )
        )

        holder.infoRB.setOnClickListener(View.OnClickListener {
            Log.v("jjjjj "+adapterModels[position].allergy,"ppppp "+position!!);
            mCallback!!.setOnInfo(""+adapterModels[position].name,""+adapterModels[position].allergy)
        })
        /*if(adapterModels.get(position).allergy.equals("0"))
        {
            holder.plus.animate().rotation(0F)
                .setDuration(500).start();
            holder.plus.setColorFilter(colorIcon(context,R.color.redColor,R.drawable.plus,holder.plus))

            holder.subItemsLL.visibility = View.VISIBLE

        }else{
            val com = point+" "+position
            Log.v("jkjkjkjk123",point1+"======== +++"+position+"+ ====="+point!!)
            if(com.equals(point1)) {

                    Log.v("jkjkjkjk","======== +++++ ====="+point!!)
                    holder.plus.animate().rotation(45F)
                        .setDuration(500).start();
                    holder.plus.setColorFilter(
                        colorIcon(
                            context,
                            R.color.colorGray,
                            R.drawable.plus,
                            holder.plus
                        )
                    )

                    holder.subItemsLL.visibility = View.VISIBLE
                }else{
                    holder.plus.animate().rotation(0F)
                        .setDuration(500).start();
                    holder.plus.setColorFilter(colorIcon(context,R.color.redColor,R.drawable.plus,holder.plus))

                    holder.subItemsLL.visibility = View.VISIBLE
                }



        }*/

        /*holder.itemRL.setOnClickListener(View.OnClickListener {

            mCallback!!.setOnPopup(""+position+"nor",0)
        })*/

        /*holder.itemSubRL.setOnClickListener(View.OnClickListener {

            mCallback!!.setOnPopup(""+position+"unnor",0)
        })*/

        /*holder.item_sub_add_TV.setOnClickListener(View.OnClickListener {
            holder.addItem.visibility = View.VISIBLE
            holder.item_sub_add_TV.visibility = View.GONE
            holder.add_item_increase.text =
                "" + addIncrease(holder.add_item_increase.text.toString())
            mCallback!!.setOnDetail("" + holder.add_item_increase.text.toString(),0)
        })

        holder.add_item_plus.setOnClickListener(View.OnClickListener {
            holder.add_item_increase.text =
                "" + addIncrease(holder.add_item_increase.text.toString())
            mCallback!!.setOnDetail("" + holder.add_item_increase.text.toString(),0)
        })

        holder.add_item_plus.setColorFilter(
            colorIcon(
                context,
                R.color.redColor,
                R.drawable.plus,
                holder.add_item_plus
            )
        )

        holder.priceAdd.setOnClickListener(View.OnClickListener {
            holder.textIncrement!!.setText(""+
                    addIncreasePrice(adapterModels.get(position).distance,"1").toInt())

            adapterModels.get(position).distance = ""+addIncreasePrice(adapterModels.get(position).distance,"1").toInt()

            //adapterModels.get(position).offerValue =""+ MultipleIncreasePrice(adapterModels.get(position).offerValue, holder.textIncrement!!.text.toString())

            holder.totalValPrice.text = "€ "+MultipleIncreasePrice(adapterModels.get(position).price, holder.textIncrement!!.text.toString())
        })



        holder.priceMinus.setOnClickListener(View.OnClickListener {

            if(!holder.textIncrement!!.text.toString().equals("1")) {
                holder.textIncrement!!.setText(
                    "" +
                            DecreaseIncreasePrice(adapterModels.get(position).distance, "1").toInt()
                )

                adapterModels.get(position).distance =
                    "" + DecreaseIncreasePrice(adapterModels.get(position).distance, "1").toInt()

                //adapterModels.get(position).offerValue =""+MultipleIncreasePrice(adapterModels.get(position).offerValue, holder.textIncrement!!.text.toString())

                holder.totalValPrice.text = "€ "+MultipleIncreasePrice(adapterModels.get(position).price, holder.textIncrement!!.text.toString())
            }
        })



        holder.add_item_minus.setOnClickListener(View.OnClickListener {

            holder.add_item_increase.text =
                "" + addDecrease(holder.add_item_increase.text.toString())
            if (!holder.add_item_increase.text.equals("0")) {
                mCallback!!.setOnDetail("" + holder.add_item_increase.text.toString(),0)
            } else {
                holder.addItem.visibility = View.GONE
                holder.item_sub_add_TV.visibility = View.VISIBLE
                mCallback!!.setOnDetail("" + holder.add_item_increase.text.toString(),0)
            }
        })*/

        Log.v("==="+imagePath,"mmm"+getImageValue(adapterModels.get(position).menuImages))
        Picasso.with(context)
            .load(imagePath+"/"+getImageValue(adapterModels.get(position).menuImages))
            .resize(150, 150).transform(RoundedTransformation(6, 0)).placeholder(R.drawable.item_placeholder).into(holder.itemIMG)

        /*holder.add_item_minus.setColorFilter(
            colorIcon(
                context,
                R.color.redColor,
                R.drawable.plus,
                holder.add_item_plus
            )
        )

        holder.totalValPrice.setOnClickListener(View.OnClickListener {

            mCallback!!.setFinal("",""+position,"",0)

        })

        holder.add_item_increase.setTypeface(fontStyle(context, "Light"))

        holder.item_sub_price_TV.setTypeface(fontStyle(context, ""))

        holder.item_sub_price_TV.setText("€ "+adapterModels[position].price)*/

        Picasso.with(context)
            .load("https://media-cdn.tripadvisor.com/media/photo-s/0e/42/fd/d2/veg-starter.jpg")
            .resize(450, 450)
            .into(holder.item_sub_CircleImageView)

        /*holder.cardView.setOnClickListener {

            //mCallback!!.setOnMaterial(adapterModels[position], true, "", position)
        }*/

//        if(adapterModels[position].toppinsGroupList!!.size != 0)
//        {
//            holder.subItemsLL!!.visibility = View.VISIBLE
//        }else{
//            holder.subItemsLL!!.visibility = View.GONE
//        }

        val dialogListPageAdapter =
            DishListPagePastAdapter(context, adapterModels[position].toppinsGroupList)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        holder.extras!!.layoutManager = mLayoutManager

        holder.extras!!.itemAnimator!!.addDuration = 5000

        dialogListPageAdapter!!.setCallback(this)

        holder.extras!!.adapter = dialogListPageAdapter

        //holder.itemSubRL.visibility = View.VISIBLE


        /*if(position == 0) {

            holder.extras!!.adapter = dialogListPageAdapter

            holder.itemSubRL.visibility = View.GONE

            holder.extras!!.visibility = View.VISIBLE
        }else if(position == 1){
            holder.itemSubRL.visibility = View.VISIBLE

            holder.extras!!.visibility = View.GONE

        }else if(position == 2)
        {
            holder.itemSubRL.visibility = View.GONE

            holder.extras!!.visibility = View.GONE

            holder.subItemsLL.visibility = View.GONE
        }
*/
    }

    override fun getItemCount(): Int {
        return adapterModels.size
    }

    fun setCallback(mCallback: CallbackDataAdapter?) {
        this.mCallback = mCallback
    }

    interface CallbackDataAdapter {
        fun setOnDetail(id: String?, pos: Int?)

        fun setOnInfo(id: String?, id1: String?)

        fun setOnQuantity(id: String?, pos: String?)

        fun setOnPopup(adapterModels: List<ProductListView>, pos: Int?)

        fun setOnCancel(type: String?, position: Double?, isOpen: String?, pos: String?)

        fun setOnPriceAdd(type: String?, itemName: String?, price: String?, pos: Int?)

        fun setFinal(type: String?, position: Double?, isOpen: String?, pos: String?)

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

        this.imagePath = imagePath

        this.point = point

        this.point1 = point1
    }

    override fun setOnRadio(addPrice: String?, itemName: String?, price: String?) {

        mCallback!!.setOnPriceAdd(addPrice, itemName, price, 0)
    }

    override fun setOnFavourite1(adapterModels: List<ProductListView>, id: String?) {
        mCallback!!.setOnPopup(adapterModels, 0)
    }
}