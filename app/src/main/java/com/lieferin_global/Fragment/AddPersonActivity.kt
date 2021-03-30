package com.lieferin_global.Fragment

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.*
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.Model.ProductList
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.webServiceValue
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import org.json.JSONObject
import java.util.ArrayList

class AddPersonActivity : Fragment(), View.OnClickListener,
    AddPersoninDividualsAdapter.CallbackDataAdapter, AddPersonAdapter.CallbackDataAdapter,
    AddGroupAdapter.CallbackDataAdapter, ResponseListener,
    LanguageTextViewAdapter.CallbackDataAdapter {

    var rootView: View? = null

    var orderBill_Title: TextView? = null

    var addPersonTV: TextView? = null

    var addGroupTV: TextView? = null

    var addTV: TextView? = null

    var addPersonRecyclerView: RecyclerView? = null

    var adapterFeature: ArrayList<AdapterModel> = ArrayList()

    var adapterProduct: ArrayList<Product> = ArrayList()

    var adapterProductIndividuals: ArrayList<Product> = ArrayList()

    var adapterProductList: MutableList<ProductList> = ArrayList()

    internal lateinit var adapterModel: AdapterModel

    internal lateinit var adapterModelProduct: Product

    var addGroupAdapter: AddGroupAdapter? = null

    var addPersonAdapter: AddPersoninDividualsAdapter? = null

    var addIndividualsPersonRecyclerView: RecyclerView? = null

    fun AddPersonActivity() {}

    var callBlacklisting: CallBlacklisting? = null

    var addIcon: ImageView? = null

    var title: String? = null

    var dateVale: String? = null

    var countPerson: String? = null

    var selectTimeValue: String? = null

    var restaurantReferenceCode: String? = null

    var tableReservation_back: ImageView? = null

    var checkPerson: CheckBox? = null

    var countryCodeRL: RelativeLayout? = null

    var countryCodeString: String? = ""

    var languageAdapter: LanguageTextViewAdapter? = null

    var adapterDetails: MutableList<AdapterModel> = ArrayList()

    var adapterDetailChild: MutableList<Product> = ArrayList()

    var dbHelper: DBHelper? = null

    var addPersonLayout: LinearLayout? = null

    var personContValue = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_add_person, container, false)

        dbHelper = DBHelper(activity)

        var header = rootView!!.findViewById(R.id.header) as RelativeLayout

        header!!.setOnClickListener(this)

        addPersonLayout = rootView!!.findViewById(R.id.addPersonLayout) as LinearLayout

        var addGroupRelativeLayout =
            rootView!!.findViewById(R.id.addGroupRelativeLayout) as RelativeLayout

        addGroupRelativeLayout!!.setOnClickListener(this)

        orderBill_Title = rootView!!.findViewById(R.id.orderBill_Title) as TextView

        orderBill_Title!!.typeface = fontStyle(activity!!, "")

        checkPerson = rootView!!.findViewById(R.id.checkPerson) as CheckBox

        checkPerson!!.typeface = fontStyle(activity!!, "")

        checkPerson!!.setOnClickListener(this)

        tableReservation_back = rootView!!.findViewById(R.id.tableReservation_back) as ImageView

        tableReservation_back!!.setOnClickListener(this)

        addPersonTV = rootView!!.findViewById(R.id.addPersonTV) as TextView

        addPersonTV!!.typeface = fontStyle(activity!!, "SemiBold")

        addGroupTV = rootView!!.findViewById(R.id.addGroupTV) as TextView

        addGroupTV!!.typeface = fontStyle(activity!!, "SemiBold")

        addTV = rootView!!.findViewById(R.id.addTV) as TextView

        addTV!!.typeface = fontStyle(activity!!, "Light")

        addTV!!.setOnClickListener(this)

        addPersonTV!!.setOnClickListener(this)

        addGroupTV!!.setOnClickListener(this)

        addIcon = rootView!!.findViewById(R.id.addIcon) as ImageView

        addIcon!!.setColorFilter(
            colorIcon(
                activity!!,
                R.color.colorWhite,
                R.drawable.add,
                addIcon!!
            )
        )

        addPersonRecyclerView = rootView!!.findViewById(R.id.addPersonRecyclerView) as RecyclerView

        addGroupAdapter = AddGroupAdapter(activity!!, adapterFeature)
        addPersonRecyclerView!!.setHasFixedSize(true)
        addPersonRecyclerView!!.setLayoutManager(
            LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        )
        addPersonRecyclerView!!.isNestedScrollingEnabled = false

        addGroupAdapter!!.setCallback(this)

        addPersonRecyclerView!!.setAdapter(addGroupAdapter)

        addIndividualsPersonRecyclerView =
            rootView!!.findViewById(R.id.addIndividualsPersonRecyclerView) as RecyclerView

        addPersonAdapter = AddPersoninDividualsAdapter(activity!!, adapterProductIndividuals)
        addIndividualsPersonRecyclerView!!.setHasFixedSize(true)
        addIndividualsPersonRecyclerView!!.setLayoutManager(
            LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        )
        addIndividualsPersonRecyclerView!!.isNestedScrollingEnabled = false

        addPersonAdapter!!.setCallback(this)

        addIndividualsPersonRecyclerView!!.setAdapter(addPersonAdapter)


        //showDialogInfo(activity!!,"Add")

        showDetailsTitle()

        //showDetails()

        val bundle = this.arguments
        if (bundle != null) {
            title = bundle.getString("Title")

            dateVale = bundle.getString("Select Date")

            countPerson = bundle.getString("person Count")

            selectTimeValue = bundle.getString("Select Time")

            restaurantReferenceCode = bundle.getString("restaurantReferenceCode")
        }
        if (webServiceValue.equals("0")) {
            webService()
        }

        return rootView
    }

    fun webService() {
        val obj = JSONObject()
        obj.put("token", "" + dbHelper!!.getUserDetails().token)

        Log.v("Json", "Value" + obj)
        RequestManager.setCustomerExistingGroup(activity, obj, this)
    }


    fun showDetails() {
        adapterModel = AdapterModel(
            R.drawable.img_4,
            "Friends Group",
            "Member 1",
            "75 minutes",
            "Save 60 %",
            "1",
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
            adapterProduct
        )
        adapterFeature.add(adapterModel)

        addPersonAdapter!!.notifyDataSetChanged()
    }

    fun showDetailsTitle() {


    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.addPersonTV -> {

                //showDialogReservation(activity!!)

                if (adapterProduct!!.size == 0 && adapterProductIndividuals!!.size == 0) {
                    showToast(activity!!, "Please add member's")
                } else {


                    val bundle = Bundle()
                    bundle.putParcelableArrayList("Product", adapterFeature!!)

                    bundle.putParcelableArrayList("ProductIndividuals", adapterProductIndividuals!!)

                    bundle.putString("Title", title)
                    bundle.putString("Select Date", dateVale)
                    bundle.putString("person Count", countPerson!!)
                    bundle.putString("Select Time", selectTimeValue!!)
                    bundle.putString("restaurantReferenceCode", "" + title)

                    if (checkPerson!!.isChecked) {
                        bundle.putString("userCustomer", "1")

                    } else {
                        bundle.putString("userCustomer", "0")
                    }
                    var totalVal = 0

                    for (i in 0 until adapterFeature!!.size) {

                        totalVal = adapterFeature!!.get(i).menusList.size

                        for (j in 0 until adapterFeature!!.get(i).menusList.size) {
                            if (adapterFeature!!.get(i).menusList!!.get(j).categoryId!!.equals("1")) {
                                totalVal = totalVal + 1
                            }
                        }
                    }
                    for (i in 0 until adapterProductIndividuals!!.size) {
                        if (adapterProductIndividuals!!.get(i).categoryId!!.equals("1")) {
                            totalVal = totalVal + 1
                        }
                    }
                    //totalVal = totalVal + adapterProductIndividuals!!.size

                    if (totalVal!!.toString().equals(countPerson)) {
                        callBlacklisting!!.fragmentChange("GroupList", bundle)

                        webServiceValue = "1"
                    } else {
                        showToast(activity!!, "Please add " + countPerson + " member's")
                    }
                }
            }
            R.id.checkPerson -> {

                    if (checkPerson!!.isChecked) {
                        if (!personContValue!!.toString().equals(countPerson)) {
                            adapterModelProduct = Product(
                            R.drawable.img_4,
                            "" + dbHelper!!.getUserDetails().name,
                            "" + dbHelper!!.getUserDetails().mobile,
                            "" + dbHelper!!.getUserDetails().email,
                            "",
                            "",
                            "1",
                            "Individual",
                            "4.4",
                            "1",
                            "3054 Ratings>",
                            "5 Star Given by you",
                            "1",
                            "",
                            "0",
                            "",
                            "",
                            "0",
                            adapterProductList
                        )
                        adapterProductIndividuals.add(adapterModelProduct)
                    }else{
                        checkPerson!!.isChecked = false
                        showToast(activity!!,"Please add "+countPerson+" member's")
                    }
                    } else {
                        for (i in 0 until adapterProductIndividuals!!.size) {
                            if (adapterProductIndividuals!!.get(i).menuId.equals(localgetUserInfo("mobile"))) {
                                try {
                                    adapterProductIndividuals!!.removeAt(i)
                                } catch (e: IndexOutOfBoundsException) {

                                }
                            }

                        }
                    }

                    validation()


                //showDialogReservation(activity!!)

            }

            R.id.addGroupTV -> {

                showDialogGroup(activity!!, "")

            }
            R.id.addTV -> {
                showDialogInfo(activity!!, "Add", "1")
            }

            R.id.tableReservation_back -> {
                callBlacklisting!!.fragmentBack("")
            }
        }
    }

    fun showDialogAddService(
        context: Context?, textView: TextView?
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
        dialog.setContentView(R.layout.select_language_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val languageList = dialog.findViewById<View>(R.id.languageList) as RecyclerView

        languageAdapter = LanguageTextViewAdapter(context, adapterDetails, dialog, textView!!)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        languageList!!.layoutManager = mLayoutManager

        languageList!!.itemAnimator!!.addDuration = 5000

        languageAdapter!!.setCallback(this)

        languageList!!.adapter = languageAdapter

        val loginText = dialog.findViewById<View>(R.id.loginText) as TextView
        loginText!!.typeface = fontStyle(context, "")

        loginText!!.text = "Select Country Code"

        val closeIV = dialog.findViewById<View>(R.id.closeIV) as ImageView
        closeIV!!.setOnClickListener(View.OnClickListener { dialog.cancel() })

        showLanguage()
        dialog.show()
    }

    fun showLanguage() {
        if (adapterDetails!!.size != 0) {
            adapterDetails!!.clear()
        }
        adapterModel = AdapterModel(
            0,
            "India +91",
            "",
            "",
            "",
            "3 Years",
            "",
            "",
            "",
            "",
            "",
            "VTU",
            "Rank ",
            "10% \nOFF",
            "Admission Open",
            "Follow",
            "Apply",
            "1",
            0,
            0,
            0,
            adapterDetailChild
        )
        adapterDetails!!.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "Germany +49",
            "",
            "",
            "",
            "3 Years",
            "",
            "",
            "",
            "",
            "",
            "VTU",
            "Rank ",
            "10% \nOFF",
            "Admission Open",
            "Follow",
            "Apply",
            "1",
            0,
            0,
            0,
            adapterDetailChild
        )

        adapterDetails!!.add(adapterModel)

        adapterModel = AdapterModel(
            0,
            "Portugal +351",
            "",
            "",
            "",
            "3 Years",
            "",
            "",
            "",
            "",
            "",
            "VTU",
            "Rank ",
            "10% \nOFF",
            "Admission Open",
            "Follow",
            "Apply",
            "1",
            0,
            0,
            0,
            adapterDetailChild
        )

        adapterDetails!!.add(adapterModel)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }

    fun showDialogInfo(context: Context?, value: String?, type: String?) {
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
        dialog.setContentView(R.layout.add_person_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val closeIMG = dialog.findViewById<View>(R.id.closeIMG) as ImageView

        val CancelTV = dialog.findViewById<View>(R.id.CancelTV) as TextView

        CancelTV.typeface = fontStyle(context, "SemiBold")

        val submitTV = dialog.findViewById<View>(R.id.submitTV) as TextView

        submitTV.typeface = fontStyle(context, "SemiBold")

        val tableLA = dialog.findViewById<View>(R.id.tableLA) as TextView

        tableLA.typeface = fontStyle(context, "")

        val tableLA1 = dialog.findViewById<View>(R.id.enterUserName) as EditText

        tableLA1.typeface = fontStyle(context, "")

        val tableLA2 = dialog.findViewById<View>(R.id.enterMobile) as EditText

        tableLA2.typeface = fontStyle(context, "")

        val enterEmail = dialog.findViewById<View>(R.id.enterEmail) as EditText

        enterEmail.typeface = fontStyle(context, "")
        val countryCodeTV = dialog!!.findViewById(R.id.countryCodeTV) as TextView

        countryCodeTV.typeface = fontStyle(context, "")

        countryCodeTV!!.tag = ""
        val countryCodeRL = dialog!!.findViewById(R.id.countryCodeRL) as RelativeLayout

        countryCodeRL!!.setOnClickListener(View.OnClickListener {

            showDialogAddService(context, countryCodeTV)
        })

        closeIMG.setOnClickListener(View.OnClickListener {

            dialog.cancel()
        })

        CancelTV.setOnClickListener(View.OnClickListener {

            dialog.cancel()
        })

        submitTV.setOnClickListener(View.OnClickListener {

            if (tableLA1.equals("")) {
                showToast(activity!!, "" + getString(R.string.PleaseEnterName))

            } else if (countryCodeTV!!.tag.toString().equals("")) {
                showToast(activity!!, "Please select country code")
            } else if (tableLA2.length() != 10) {
                showToast(activity!!, "" + getString(R.string.PleaseEnterValidMobileNumber))
            } else if (enterEmail.text.toString().equals("")) {
                showToast(
                    activity!!,
                    "" + getString(R.string.ForgetPasswordActivity_Please_enter_email_id)
                )
            } else {

                if (type!!.equals("0")) {

                    if (isValidation(countryCodeTV!!.tag.toString() + "-" + tableLA2.text.toString())) {
                        showDataAddPerson(
                            tableLA1.text.toString(),
                            countryCodeTV!!.tag.toString() + "-" + tableLA2.text.toString(),
                            enterEmail.text.toString(),
                            value!!
                        )
                        validation()

                        dialog.cancel()
                    } else {
                        showToast(activity!!, "Mobile number already exist")
                    }
                } else {
                    if (isValidationIndividuals(countryCodeTV!!.tag.toString() + "-" + tableLA2.text.toString())) {
                        showDataAddPersonIndividuals(
                            tableLA1.text.toString(),
                            countryCodeTV!!.tag.toString() + "-" + tableLA2.text.toString(),
                            enterEmail.text.toString(),
                            value!!
                        )

                        validation()

                        dialog.cancel()
                    } else {
                        showToast(activity!!, "Mobile number already exist")
                    }
                }
            }
        })

        dialog.show()
    }

    fun validation() {
        var totalVal = 0

        for (i in 0 until adapterFeature!!.size) {

            //totalVal = adapterFeature!!.get(i).menusList.size

            for (j in 0 until adapterFeature!!.get(i).menusList.size) {
                if (adapterFeature!!.get(i).menusList!!.get(j).categoryId!!.equals("1")) {
                    totalVal = totalVal + 1
                }
            }
        }
        /*if (checkPerson!!.isChecked) {

            totalVal = totalVal + 1

            for (i in 0 until adapterProductIndividuals!!.size){

                if(adapterProductIndividuals!!.get(i).categoryId!!.equals("1"))
                {
                    totalVal = totalVal + 1
                }
            }

        }else{*/

        for (i in 0 until adapterProductIndividuals!!.size) {

            if (adapterProductIndividuals!!.get(i).categoryId!!.equals("1")) {
                totalVal = totalVal + 1

                Log.v("PersonCount", " === " + adapterProductIndividuals!!.get(i).categoryId!!)
            }
        }
        //totalVal = totalVal + adapterProductIndividuals!!.size
        //}

        Log.v("PersonCount", " === " + totalVal)

        if (totalVal!!.toString().equals(countPerson)) {
            addPersonLayout!!.visibility = View.GONE

            for (i in 0 until adapterFeature!!.size) {

                adapterFeature!!.get(i).price="1"
            }

        }
        else {
            addPersonLayout!!.visibility = View.VISIBLE

            for (i in 0 until adapterFeature!!.size) {

                adapterFeature!!.get(i).price=""
            }
        }

        personContValue = ""+totalVal

        addGroupAdapter!!.notifyDataSetChanged()
    }

    fun showDialogInfoEdit(context: Context?, value: Int?, type: String?, product: Product) {
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
        dialog.setContentView(R.layout.add_person_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val closeIMG = dialog.findViewById<View>(R.id.closeIMG) as ImageView

        val CancelTV = dialog.findViewById<View>(R.id.CancelTV) as TextView

        CancelTV.typeface = fontStyle(context, "SemiBold")

        val submitTV = dialog.findViewById<View>(R.id.submitTV) as TextView

        submitTV.typeface = fontStyle(context, "SemiBold")

        val tableLA = dialog.findViewById<View>(R.id.tableLA) as TextView

        tableLA.typeface = fontStyle(context, "")

        val tableLA1 = dialog.findViewById<View>(R.id.enterUserName) as EditText

        tableLA1.typeface = fontStyle(context, "")

        tableLA1.setText(product.name)

        val tableLA2 = dialog.findViewById<View>(R.id.enterMobile) as EditText

        tableLA2.typeface = fontStyle(context, "")

        val arrOfStr =
            product.menuId!!.split("-")

        tableLA2.setText(arrOfStr[1])

        val enterEmail = dialog.findViewById<View>(R.id.enterEmail) as EditText

        enterEmail.typeface = fontStyle(context, "")

        enterEmail.setText(product.price)

        val countryCodeTV = dialog!!.findViewById(R.id.countryCodeTV) as TextView

        countryCodeTV.typeface = fontStyle(context, "")

        countryCodeTV.text = localeToEmoji(isCountry(arrOfStr[0])) + arrOfStr[0]

        countryCodeTV!!.tag = "" + arrOfStr[0]
        val countryCodeRL = dialog!!.findViewById(R.id.countryCodeRL) as RelativeLayout

        countryCodeRL!!.setOnClickListener(View.OnClickListener {

            showDialogAddService(context, countryCodeTV)
        })

        closeIMG.setOnClickListener(View.OnClickListener {

            dialog.cancel()
        })

        CancelTV.setOnClickListener(View.OnClickListener {

            dialog.cancel()
        })

        submitTV.setOnClickListener(View.OnClickListener {

            if (tableLA1.equals("")) {
                showToast(activity!!, "" + getString(R.string.PleaseEnterName))

            } else if (countryCodeTV!!.tag.toString().equals("")) {
                showToast(activity!!, "Please select country code")
            } else if (tableLA2.length() != 10) {
                showToast(activity!!, "" + getString(R.string.PleaseEnterValidMobileNumber))
            } else if (enterEmail.text.toString().equals("")) {
                showToast(
                    activity!!,
                    "" + getString(R.string.ForgetPasswordActivity_Please_enter_email_id)
                )
            } else {

                if (type!!.equals("0")) {
                    showDataAddPersonGroupEdit(
                        tableLA1.text.toString(),
                        countryCodeTV!!.tag.toString() + "-" + tableLA2.text.toString(),
                        enterEmail.text.toString(), product.menuReferenceCode!!,
                        product.menuId!!
                    )
                } else {
                    showDataAddPersonEdit(
                        tableLA1.text.toString(),
                        countryCodeTV!!.tag.toString() + "-" + tableLA2.text.toString(),
                        enterEmail.text.toString(),
                        value!!
                    )
                }

                dialog.cancel()
            }
        })

        dialog.show()
    }


    fun showDialogGroup(context: Context?, value: String?) {
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
        dialog.setContentView(R.layout.add_group_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val closeIMG = dialog.findViewById<View>(R.id.closeIMG) as ImageView

        val CancelTV = dialog.findViewById<View>(R.id.CancelTV) as TextView

        CancelTV.typeface = fontStyle(context, "SemiBold")

        val submitTV = dialog.findViewById<View>(R.id.submitTV) as TextView

        submitTV.typeface = fontStyle(context, "SemiBold")

        val tableLA = dialog.findViewById<View>(R.id.tableLA) as TextView

        tableLA.typeface = fontStyle(context, "")

        val tableLA1 = dialog.findViewById<View>(R.id.enterUserName) as EditText

        tableLA1.typeface = fontStyle(context, "")

        closeIMG.setOnClickListener(View.OnClickListener {

            dialog.cancel()
        })

        CancelTV.setOnClickListener(View.OnClickListener {

            dialog.cancel()
        })

        submitTV.setOnClickListener(View.OnClickListener {

            if (tableLA1.equals("")) {
                showToast(activity!!, "" + getString(R.string.PleaseEnterTheGroupName))

            } else {

                showDataFeature(tableLA1.text.toString(), "")

                Log.v("''''", "llll")

                dialog.cancel()
            }
        })
        dialog.show()
    }

    fun showDataFeature(name: String, mobile: String) {

        adapterModel = AdapterModel(
            R.drawable.img_4,
            "" + name.toString(),
            "",
            "75 minutes",
            "Save 60 %",
            "1",
            "1",
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
            adapterProduct
        )
        adapterFeature.add(adapterModel)

        addGroupAdapter!!.notifyDataSetChanged()
    }

    fun showDataAddPerson(name: String, mobile: String, email: String, groupName: String) {

        adapterModelProduct = Product(
            R.drawable.img_4,
            "" + name.toString(),
            "" + mobile,
            "" + email,
            "" + groupName,
            "",
            "1",
            "New Group",
            "4.4",
            "0",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "0",
            adapterProductList
        )
        adapterProduct.add(adapterModelProduct)

        addGroupAdapter!!.notifyDataSetChanged()
    }

    fun showDataAddPersonGroupEdit(
        name: String,
        mobile: String,
        email: String,
        groupName: String,
        mobileOld: String
    ) {

        for (i in 0 until adapterProduct!!.size) {
            if (adapterProduct!!.get(i).menuId!!.equals(mobileOld)) {
                adapterProduct!!.removeAt(i)

                //Log.i("o "+adapterProduct!!.get(i).menuId!!,"D "+mobile)
            }
        }
        adapterModelProduct = Product(
            R.drawable.img_4,
            "" + name.toString(),
            "" + mobile,
            "" + email,
            "" + groupName,
            "",
            "1",
            "New Group",
            "4.4",
            "0",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "0",
            adapterProductList
        )
        adapterProduct.add(adapterModelProduct)

        Log.v(email + "llll" + name, groupName + "==" + mobile)

        addGroupAdapter!!.notifyDataSetChanged()
    }

    fun showDataAddPersonIndividuals(
        name: String,
        mobile: String,
        email: String,
        groupName: String
    ) {

        adapterModelProduct = Product(
            R.drawable.img_4,
            "" + name.toString(),
            "" + mobile,
            "" + email,
            "" + groupName,
            "",
            "1",
            "Individual",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "5 Star Given by you",
            "1",
            "",
            "",
            "",
            "",
            "0",
            adapterProductList
        )
        adapterProductIndividuals.add(adapterModelProduct)

        addPersonAdapter!!.notifyDataSetChanged()
    }

    fun showDataAddPersonEdit(name: String, mobile: String, email: String, position: Int) {

        adapterProductIndividuals.get(position).name = name

        adapterProductIndividuals.get(position).menuId = mobile

        adapterProductIndividuals.get(position).price = email

        addPersonAdapter!!.notifyDataSetChanged()
    }

    override fun setOnMaterial(id: String?, position: Int, product: Product) {

        showDialogInfoEdit(activity!!, position, "0", product)
    }

    override fun setOnAdd(id: String?, position: Int, mobile: String?) {

        adapterProduct.get(position).categoryId = "" + id

        validation()
    }

    override fun setOnMaterialEdit(id: String?, position: Int, product: Product) {
        showDialogInfoEdit(activity!!, position, "1", product)
    }

    override fun setOnAddIndividuals(id: String?, position: Int) {

        adapterProductIndividuals.get(position).categoryId = "" + id

        validation()
    }

    fun showDialogReservation(
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
        dialog.setContentView(R.layout.table_menu)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val closeIMG = dialog.findViewById<View>(R.id.closeIMG) as ImageView

        val tableLA = dialog.findViewById<View>(R.id.tableLA) as TextView

        tableLA.typeface = fontStyle(context, "")

        val tableLA1 = dialog.findViewById<View>(R.id.tableLA1) as TextView

        tableLA1.typeface = fontStyle(context, "")

        val tableLA2 = dialog.findViewById<View>(R.id.tableLA2) as TextView

        tableLA2.typeface = fontStyle(context, "")

        val textInfo = dialog.findViewById<View>(R.id.textInfo) as TextView

        textInfo.typeface = fontStyle(context, "")

        tableLA1.setOnClickListener(View.OnClickListener {

            if (adapterProduct!!.size == 0 && adapterProductIndividuals!!.size == 0) {
                showToast(activity!!, "Please add member's")
            } else {


                val bundle = Bundle()
                bundle.putParcelableArrayList("Product", adapterFeature!!)

                bundle.putParcelableArrayList("ProductIndividuals", adapterProductIndividuals!!)

                bundle.putString("Title", title)
                bundle.putString("Select Date", dateVale)
                bundle.putString("person Count", countPerson!!)
                bundle.putString("Select Time", selectTimeValue!!)
                bundle.putString("restaurantReferenceCode", "" + title)

                if (checkPerson!!.isChecked) {
                    bundle.putString("userCustomer", "1")
                    adapterModelProduct = Product(
                        R.drawable.img_4,
                        "" + localgetUserInfo("name"),
                        "" + localgetUserInfo("mobile"),
                        "" + localgetUserInfo("email"),
                        "",
                        "",
                        "1",
                        "Individual",
                        "4.4",
                        "1",
                        "3054 Ratings>",
                        "5 Star Given by you",
                        "1",
                        "",
                        "",
                        "",
                        "",
                        "0",
                        adapterProductList
                    )
                    adapterProductIndividuals.add(adapterModelProduct)
                } else {
                    bundle.putString("userCustomer", "0")

                    for (i in 0 until adapterProductIndividuals!!.size) {
                        if (adapterProductIndividuals!!.get(i).price.equals(localgetUserInfo("mobile"))) {
                            adapterProductIndividuals!!.removeAt(i)
                        }

                    }
                }

                callBlacklisting!!.fragmentChange("GroupList", bundle)
            }

            dialog.cancel()
        })

        closeIMG.setOnClickListener(View.OnClickListener {

            dialog.cancel()
        })

        tableLA2.setOnClickListener(View.OnClickListener {

            //callBlacklisting!!.fragmentChange("Payment",null)
            dialog.cancel()
        })

        dialog.show()
    }

    override fun setOnMaterialDetails(id: String?) {

        /* val bundle = Bundle()
         bundle.putString("title", "" + id)
         callBlacklisting!!.fragmentChange("GroupList",bundle)*/

        showDialogInfo(activity, id, "0")
    }

    override fun setOnGroudAdd(id: String?, position: Int) {

        adapterFeature.get(position).offer = id

        for (i in 0 until adapterProduct!!.size) {
            adapterProduct.get(i).categoryId = id
        }

        addGroupAdapter!!.notifyDataSetChanged()

    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {

        if (responseObj != null) {
            if (requestType == Constant.MEMBER_customerExistingGroup_URL_RQ) {

                showToast(activity!!, "" + (responseObj as BaseRS).message!!)

                if ((responseObj as BaseRS).status.equals("5205")) {

                    if (adapterFeature!!.size != 0) {
                        adapterFeature.clear()
                    }

                    if (adapterProduct!!.size != 0) {
                        adapterProduct.clear()
                    }
                    for (i in 0 until (responseObj as BaseRS).groupList!!.size) {

                        for (j in 0 until (responseObj as BaseRS).groupList!!.get(i).groupMemberList!!.size) {
                            adapterModelProduct = Product(
                                R.drawable.img_4,
                                "" + (responseObj as BaseRS).groupList!!.get(i).groupMemberList!!.get(
                                    j
                                ).name.toString(),
                                "" + (responseObj as BaseRS).groupList!!.get(i).groupMemberList!!.get(
                                    j
                                ).mobile.toString(),
                                "" + (responseObj as BaseRS).groupList!!.get(i).groupMemberList!!.get(
                                    j
                                ).email.toString(),
                                "" + (responseObj as BaseRS).groupList!!.get(i).groupName.toString(),
                                "" + (responseObj as BaseRS).groupList!!.get(i).groupMemberList!!.get(
                                    j
                                ).groupMemberReferenceCode.toString(),
                                "1",
                                "Existing",
                                "4.4",
                                "0",
                                "3054 Ratings>",
                                "5 Star Given by you",
                                "1",
                                "",
                                "",
                                "",
                                "",
                                "1",
                                adapterProductList
                            )
                            adapterProduct.add(adapterModelProduct)
                        }
                        adapterModel = AdapterModel(
                            R.drawable.img_4,
                            "" + (responseObj as BaseRS).groupList!!.get(i).groupName.toString(),
                            "" + (responseObj as BaseRS).groupList!!.get(i).groupReferenceCode.toString(),
                            "75 minutes",
                            "Save 60 %",
                            "1",
                            "1",
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
                            adapterProduct
                        )
                        adapterFeature.add(adapterModel)

                        addGroupAdapter!!.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun localeToEmoji(country: String): String? {

        val firstLetter = Character.codePointAt(country, 0) - 0x41 + 0x1F1E6
        val secondLetter = Character.codePointAt(country, 1) - 0x41 + 0x1F1E6
        return String(Character.toChars(firstLetter)) + String(
            Character.toChars(
                secondLetter
            )
        )
    }

    override fun setOnDetail(id: String?, position: String?, textView: TextView) {
        Log.v("Country" + position, "==" + id)

        var countryCode = ""

        var country = ""

        if (id!!.contains("India +91")) {
            countryCode = "+91"

            country = "IN"

            textView!!.text = localeToEmoji(country) + " " + countryCode

            textView!!.tag = countryCode

        } else if (id!!.contains("Germany +49")) {
            countryCode = "+49"
            country = "DE"
            textView!!.text = localeToEmoji(country) + " " + countryCode
            textView!!.tag = countryCode
        } else if (id!!.contains("Portugal +351")) {
            countryCode = "+351"

            country = "PT"
            textView!!.text = localeToEmoji(country) + " " + countryCode
            textView!!.tag = countryCode
        }

        countryCodeString = countryCode
    }

    fun isCountry(country: String): String {
        var countryCode: String = ""
        if (country.equals("+91")) {
            countryCode = "IN"
        } else if (country.equals("+49")) {
            countryCode = "DE"
        } else if (country.equals("+351")) {
            countryCode = "PT"
        }

        Log.v("o1 " + countryCode, "D ")
        return countryCode
    }

    fun isValidation(mobile: String): Boolean {
        var isValid: Boolean = true
        for (i in 0 until adapterProduct!!.size) {
            Log.v("o12 ", "D " + mobile)
            if (adapterProduct!!.get(i).menuId!!.equals(mobile)) {
                isValid = false
                Log.v("o1 " + adapterProduct!!.get(i).menuId!!, "D " + mobile)
            }
        }
        return isValid
    }

    fun isValidationIndividuals(mobile: String): Boolean {
        var isValid: Boolean = true
        for (i in 0 until adapterProductIndividuals!!.size) {
            if (adapterProductIndividuals!!.get(i).menuId!!.equals(mobile)) {
                isValid = false
            }
        }
        return isValid
    }
}
