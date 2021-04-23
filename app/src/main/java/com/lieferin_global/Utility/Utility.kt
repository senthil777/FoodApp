package com.lieferin_global.Utility

import android.R
import android.annotation.SuppressLint
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.location.Address
import android.location.Geocoder
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

var res: Resources? = null

var geocoder: Geocoder? = null

val MyPREFERENCES = "MyPrefs"
val Name = "nameKey"
val userType = "userType"
val Email = "emailKey"

val Token = "token"

val userName = "name"

val imageValue = "image"

val emailId = "email"

val mobile = "mobile"

val address1 = "address"

val referenceCode = "referenceCode"

var sharedpreferences: SharedPreferences? = null

var sharedpreferences1: SharedPreferences? = null

var dialog: Dialog? = null

fun addressLocation(context: Context,latitude:String,longtitude:String): Address
{
    var addresses: MutableList<Address> = ArrayList()

    geocoder = Geocoder(context, Locale.getDefault())

    addresses = geocoder!!.getFromLocation(
        latitude!!.toDouble(),
        longtitude!!.toDouble(),
        1
    )
    
    Log.i("kkkkk","=="+addresses[0])

    return addresses[0]
}

fun fontStyle(context: Context, fontType: String): Typeface {
    val type: Typeface
    type = if (fontType == "SemiBold") {
        Typeface.createFromAsset(context.assets, Constant.FontStyleSemiBold)
    } else if (fontType == "Bold") {
        Typeface.createFromAsset(context.assets, Constant.FontStyleBold)
    } else if (fontType == "Light") {
        Typeface.createFromAsset(context.assets, Constant.FontStyleLight)
    } else if (fontType == "Medium") {
        Typeface.createFromAsset(context.assets, Constant.FontStyleMedium)
    } else {
        Typeface.createFromAsset(context.assets, Constant.FontStyleRegular)
    }
    return type
}

fun customText(
    context: Context,
    sVal: String?,
    pos: Int?,
    font1: String?,
    font2: String?
): SpannableStringBuilder {

    val font4 = fontStyle(context, font1!!)
    val font3 = fontStyle(context, font2!!)

    val text =
        "<font >" + sVal!!.substring(0, pos!!) + " </font> " + sVal!!.substring(pos!!) + "</font>"

    val SS1 = SpannableStringBuilder(Html.fromHtml(text))
    SS1.setSpan(CustomTypefaceSpan("", font4), 0, pos!!, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
    SS1.setSpan(
        CustomTypefaceSpan("", font3),
        pos!!,
        sVal!!.length,
        Spanned.SPAN_EXCLUSIVE_INCLUSIVE
    )

    return SS1;
}

@SuppressLint("ResourceType")
fun loadingScreen(context: Context?) {
    val animation: Animation
    dialog = Dialog(context!!,com.lieferin_global.R.style.AppTheme)
    dialog!!.getWindow()!!
        .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    // Include dialog.xml file
    dialog!!.setContentView(com.lieferin_global.R.layout.loading_screen)
    // Set dialog title
    var loading =
        dialog!!.findViewById(com.lieferin_global.R.id.loading) as ImageView

    loading!!.setColorFilter(colorIcon(context,com.lieferin_global.R.color.redColor,com.lieferin_global.R.drawable.refresh,loading!!))
    animation =
        AnimationUtils.loadAnimation(context, com.lieferin_global.R.layout.rotation_animation)
    loading.animation = animation
    dialog!!.setCancelable(false)
    dialog!!.show()
}

fun loadingScreenClose(context: Context?) {
    dialog!!.cancel()
}
fun getImageValue1(value:String?) : String{
    var str = ""+value
    str = str!!.replace("[","").toString()
    str = str!!.replace("]","").toString()
    val arrOfStr =
        str.split(",")

    str = arrOfStr[0]

    Log.v("===]]]"+arrOfStr[0],"====")
    return str
}
fun getImageValue(value:String?) : String{
    var str = ""+value
    str = str!!.replace("[","").toString()
    str = str!!.replace("]","").toString()
    val arrOfStr =
        str.split(",")

    Log.v("===]]]"+arrOfStr[0],"====")
return str
}


fun customTextColor(
    context: Context,
    sVal: String?,
    pos: Int?,
    font1: String?,
    font2: String?
): SpannableStringBuilder {

    val font4 = fontStyle(context, font1!!)
    val font3 = fontStyle(context, font2!!)

    val text =
        "<font color='#ec272b' >" + sVal!!.substring(0, pos!!) + " </font> <font color='#ffffff' >" + sVal!!.substring(pos!!) + "</font>"

    val SS1 = SpannableStringBuilder(Html.fromHtml(text))
    SS1.setSpan(CustomTypefaceSpan("", font4), 0, pos!!, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
    SS1.setSpan(
        CustomTypefaceSpan("", font3),
        pos!!,
        sVal!!.length,
        Spanned.SPAN_EXCLUSIVE_INCLUSIVE
    )

    return SS1;
}

fun colorIcon(mContext: Context, color: Int, img: Int, view: ImageView): Int {
    res = mContext.resources

    val newColorRed = res!!.getColor(color)

    view!!.setImageResource(img)

    return newColorRed
}

fun addPrice(itemPrice: String, count: Int): Int {
    var addValue: Int

    addValue = itemPrice.toInt() * count

    return addValue
}

fun addPriceDouble(itemPrice: String, count: Int): Double {
    var addValue: Double

    addValue = itemPrice.toDouble() * count

    return addValue
}

fun addIncrease(count: String): Int {
    var addValue: Int

    addValue = count.toInt() + 1

    return addValue
}

fun addIncreaseDoubleValue(count: String): Int {
    var addValue: Int

    addValue = count.toInt() + 1

    return addValue
}

fun addIncreaseDoubleOrderValue(count: String): Int {
    var addValue: Int

    addValue = count.toInt() + 2

    return addValue
}

fun addIncreasePrice(orgValue: String,price: String): Double {
    var addValue: Double

    addValue = orgValue.toDouble() + price.toDouble()

    return addValue
}

fun addIncreasePriceByeOneGetOne(orgValue: String,price: String): Double {
    var addValue: Double

    addValue = orgValue.toDouble() + price.toDouble()

    addValue = addValue / 2.0

    return addValue
}

fun addIncreasePriceHole(orgValue: String,price: String): Double {
    var addValue: Double

    addValue = orgValue.toDouble() * price.toDouble()

    return addValue
}
fun addIncreasePriceDivide(orgValue: String,price: String): Double {
    var addValue: Double

    addValue = orgValue.toDouble() / price.toDouble()

    return addValue
}

fun addIncreasePriceDivideBuyOne(orgValue: String): Double {
    var addValue: Double



    addValue = orgValue.toDouble() / 2.0

    return addValue
}
fun DecreaseIncreasePrice(orgValue: String,price: String?): Double {
    var addValue: Double

    addValue = orgValue.toDouble() - price!!.toDouble()

    return addValue
}


fun MultipleIncreasePrice(orgValue: String,price: String): String {
    var addValue: Double

    addValue = orgValue.toDouble() * price.toDouble()

    Log.v("jjjjj","=="+String.format("%.2f",addValue))
    return ""+ addValue.toString()
}

fun addDecrease(count: String): Int {
    var addValue: Int

    addValue = count.toInt() - 1

    return addValue
}

fun buyOneGetOne(price:String): Double {
    var addValue: Double

    addValue = price.toDouble() * 2

    return addValue
}

fun addDecreaseType(count: String): Int {
    var addValue: Int

    addValue = count.toInt() - 2

    return addValue
}

fun minusPrice(itemPrice: String, count: String): Int {
    var addValue: Int

    addValue = itemPrice.substring(2).toInt() - count.toInt()

    return addValue
}

fun minusPriceDouble(itemPrice: String, count: String): Double {
    var addValue: Double

    addValue = itemPrice.substring(2).toDouble() - count.toDouble()

    return addValue
}

fun showHourPicker(context: Context) {
    val myCalender: Calendar = Calendar.getInstance()
    val hour: Int = myCalender.get(Calendar.HOUR_OF_DAY)
    val minute: Int = myCalender.get(Calendar.MINUTE)
    val myTimeListener =
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            if (view.isShown) {
                myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay)
                myCalender.set(Calendar.MINUTE, minute)
            }
        }
    val timePickerDialog = TimePickerDialog(
        context,
        R.style.Theme_Holo_Light_Dialog_NoActionBar,
        myTimeListener,
        hour,
        minute,
        true
    )


    timePickerDialog.window!!.setBackgroundDrawableResource(R.color.transparent)
    timePickerDialog.show()
}

fun dateConversionValue(dateValue: String?): String? {
    var givenDateString = dateValue
    val sdf = SimpleDateFormat("HH:mm")
    val date24Format = SimpleDateFormat("hh:mm a")
    try {
        val mDate = sdf.parse(givenDateString)
        val timeInMilliseconds = mDate.time
        givenDateString = date24Format.format(sdf.parse(givenDateString))
        // Log.v("value","==== "+date24Format.format(sdf.parse(givenDateString)));
        println("Date in milli :: $timeInMilliseconds")
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return givenDateString
}
fun dateConversion1Value(dateValue: String?): String? {
    var givenDateString = dateValue
    val sdf = SimpleDateFormat("yyyy-mm-dd")
    val date24Format = SimpleDateFormat("yyyy-mm-DD")
    try {
        val mDate = sdf.parse(givenDateString)
        val timeInMilliseconds = mDate.time
        givenDateString = date24Format.format(sdf.parse(givenDateString))
        // Log.v("value","==== "+date24Format.format(sdf.parse(givenDateString)));
        println("Date in milli :: $timeInMilliseconds")
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return givenDateString
}

fun dateConversionType(dateValue: String?): String? {
    var givenDateString = dateValue
    val sdf = SimpleDateFormat("dd mmm yyyy")
    val date24Format = SimpleDateFormat("yyyy-mm-dd")
    try {
        val mDate = sdf.parse(givenDateString)
        val timeInMilliseconds = mDate.time
        givenDateString = date24Format.format(sdf.parse(givenDateString))
        // Log.v("value","==== "+date24Format.format(sdf.parse(givenDateString)));
        println("Date in milli :: $timeInMilliseconds")
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return givenDateString
}

fun dateConversionVal(dateValue: String?): String? {
    var givenDateString = dateValue
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date24Format = SimpleDateFormat("MMMM dd hh:mm a")
    try {
        val mDate = sdf.parse(givenDateString)
        val timeInMilliseconds = mDate.time
        givenDateString = date24Format.format(sdf.parse(givenDateString))
        // Log.v("value","==== "+date24Format.format(sdf.parse(givenDateString)));
        println("Date in milli :: $timeInMilliseconds")
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return givenDateString
}

fun dateConversionTimeValue(dateValue: String?): String? {
    var givenDateString = dateValue
    val sdf = SimpleDateFormat("hh:mm a")
    val date24Format = SimpleDateFormat("HH:mm")
    try {
        val mDate = sdf.parse(givenDateString)
        val timeInMilliseconds = mDate.time
        givenDateString = date24Format.format(sdf.parse(givenDateString))
        // Log.v("value","==== "+date24Format.format(sdf.parse(givenDateString)));
        println("Date in milli :: $timeInMilliseconds")
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return givenDateString
}

fun dateConversionDateOnly(dateValue: String?): String? {
    var givenDateString = dateValue
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val date24Format = SimpleDateFormat("MMM dd")
    try {
        val mDate = sdf.parse(givenDateString)
        val timeInMilliseconds = mDate.time
        givenDateString = date24Format.format(sdf.parse(givenDateString))
        // Log.v("value","==== "+date24Format.format(sdf.parse(givenDateString)));
        println("Date in milli :: $timeInMilliseconds")
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return givenDateString
}

fun dateConversionTimeDay(dateValue: String?): String? {
    var givenDateString = dateValue
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val date24Format = SimpleDateFormat("EEE")
    try {
        val mDate = sdf.parse(givenDateString)
        val timeInMilliseconds = mDate.time
        givenDateString = date24Format.format(sdf.parse(givenDateString))
        // Log.v("value","==== "+date24Format.format(sdf.parse(givenDateString)));
        println("Date in milli :: $timeInMilliseconds")
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return givenDateString
}


fun dateConversionTimeOnly(dateValue: String?): String? {
    var givenDateString = dateValue
    val sdf = SimpleDateFormat("HH:mm:ss")
    val date24Format = SimpleDateFormat("HH:mm:a")
    try {
        val mDate = sdf.parse(givenDateString)
        val timeInMilliseconds = mDate.time
        givenDateString = date24Format.format(sdf.parse(givenDateString))
        // Log.v("value","==== "+date24Format.format(sdf.parse(givenDateString)));
        println("Date in milli :: $timeInMilliseconds")
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return givenDateString
}


@SuppressLint("ResourceType")
fun showToast(context: Context, msg: String?) {
    val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
    val toastLayout = toast.view as LinearLayout
    val toastTV = toastLayout.getChildAt(0) as TextView
    toastTV.textSize = 12f
    toastTV.typeface = fontStyle(context, "")
    toast.show()
}

fun localgetUserInfo(getString: String?): String?
{
    if (sharedpreferences!!.getString(getString, "") != null) {
        return sharedpreferences!!.getString(getString, "")
    } else {
        return ""
    }
}

fun timeCheck(fromTime :String,toTime : String,fromMin :String,toMin : String): String?
{
    Log.i("from"+fromTime,"to"+toTime)
    Log.i("from"+fromMin,"to"+toMin)
    if(fromTime.toInt() >= toTime.toInt()) {

        if(fromMin.toInt() >= toMin.toInt()) {
            return "true"
        }else {
            return "false"
        }
    }else{
        return "false"
    }
}

 fun getCompleteAddressString(context: Context,
    LATITUDE: Double,
    LONGITUDE: Double
): String? {
    var strAdd = ""
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        val addresses =
            geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
        if (addresses != null) {
            val returnedAddress = addresses[0]
            val strReturnedAddress = StringBuilder("")
            for (i in 0..returnedAddress.maxAddressLineIndex) {
                strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")

                //Log.v("My Current loction", returnedAddress.locality)
            }
            strAdd = strReturnedAddress.toString()
            Log.w("My Current loction", strReturnedAddress.toString())
        } else {
            Log.w("My Current loction", "No Address returned!")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Log.w("My Current loction", "Canont get Address!")
    }
    return strAdd
}

fun getCompleteAddressLocation(context: Context,
                               LATITUDE: Double,
                               LONGITUDE: Double
): String? {
    var strAdd = ""
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        val addresses =
            geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
        if (addresses != null) {
            val returnedAddress = addresses[0]
            val strReturnedAddress = StringBuilder("")
            for (i in 0..returnedAddress.maxAddressLineIndex) {
                strReturnedAddress.append(returnedAddress.locality).append("\n")

                //Log.v("My Current loction", returnedAddress.locality)
            }
            strAdd = strReturnedAddress.toString()
            Log.w("My Current loction", strReturnedAddress.toString())
        } else {
            Log.w("My Current loction", "No Address returned!")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Log.w("My Current loction", "Canont get Address!")
    }
    return strAdd
}

fun timeCheckValue(fromTime :String,toTime : String,fromMin :String,toMin : String): String?
{
    Log.i("from"+fromTime,"to"+toTime)
    Log.i("from"+fromMin,"to"+toMin)

    val arrOfStr1 =
        fromTime.split("-")

    val arrOfStr2 =
        toTime.split("-")
    if(arrOfStr1[0].toInt() >= toTime.toInt()) {

        if(fromMin.toInt() >= toMin.toInt()) {
            return "true"
        }else {
            return "false"
        }
    }else{
        return "false"
    }
}


fun dateCheck(fromDate :String,toDate : String): String?
{
    val arrOfStr1 =
        fromDate.split("-")
    if(arrOfStr1[2].toInt() == toDate.toInt()) {
        return "true"
    }else{
        return "false"
    }
}


fun localgetUserInfoSlash(context: Context,getString: String?): String?
{
    sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    if (sharedpreferences!!.getString(getString, "") != null) {
        return sharedpreferences!!.getString(getString, "")
    } else {
        return ""
    }

}


fun localstorageValue(context: Context,language:String?)
{
    sharedpreferences1 = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    if(!language!!.equals("")) {
        val editor = sharedpreferences1!!.edit()
        editor.putString(Name, language!!);

        editor.apply()
    }
}
fun localstorageUserInfo(context: Context,token:String?,username:String?,emailid:String?,mobileNo:String?,address:String?,reference_Code:String?,image:String?)
{
    sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    if(!token!!.equals("")) {
        val editor = sharedpreferences!!.edit()
        editor.putString(Token, token!!);

        editor.putString(userName, username!!);

        editor.putString(emailId, emailid!!);

        editor.putString(mobile, mobileNo!!);

        editor.putString(address1, address!!);

        editor.putString(referenceCode, reference_Code!!);

        editor.putString(imageValue, image!!);

        editor.apply()
    }
}

fun isEmailValid(email: String): Boolean {
    val regExpn = ("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")
    val inputStr: CharSequence = email
    val pattern: Pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE)
    val matcher: Matcher = pattern.matcher(inputStr)
    return if (matcher.matches()) true else false
}