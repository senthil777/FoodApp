package com.lieferin_global.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lieferin_global.Adapter.SearchPageAdapter
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.colorIcon
import com.lieferin_global.Utility.fontStyle
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.lieferin_global.Utility.localgetUserInfo
import com.lieferin_global.Utility.localgetUserInfoSlash
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder
import java.util.*

class AddressSearch : AppCompatActivity(), SearchPageAdapter.CallbackDataAdapter,View.OnClickListener {

    private val LOG_TAG = "Google Places Autocomplete"
    private val PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place"
    private val TYPE_AUTOCOMPLETE = "/autocomplete"
    private val OUT_JSON = "/json"

    private val API_KEY = "AIzaSyBXiP7Rd8h84uv-TVIZEwI4NurJMcDTBhE"

    var cancel: ImageView? = null

    var searchEdit: EditText? = null

    var myArrayValue: String? = null

    private var searchAdap: SearchPageAdapter? = null

    var loca:  MutableList<String> = ArrayList()

    var locationList: RecyclerView? = null

    var profileViews: MutableList<Product> = ArrayList()

    var profileView: Product? = null

    var addAddress_Title : TextView? = null

    var addAddress_back : ImageView? = null

    var longtitude: String? = ""

    var latitude: String? = ""

    var total_value:String = ""

    var itemValue : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_search)
        if(localgetUserInfoSlash(this,"nameKey").equals(""))
        {
            val config = resources.configuration
            val locale = Locale("en")
            Locale.setDefault(locale)
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
        }else
        {
            val config = resources.configuration
            val locale = Locale(localgetUserInfo("nameKey"))
            Locale.setDefault(locale)
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
        }

        addAddress_back = findViewById(R.id.addAddress_back) as ImageView

        addAddress_back!!.setOnClickListener(this)

        addAddress_back!!.setColorFilter(
            colorIcon(this,R.color.colorBlack,R.drawable.abc_ic_ab_back_material,addAddress_back!!),
            PorterDuff.Mode.SRC_ATOP)

        addAddress_Title = findViewById(R.id.addAddress_Title)

        addAddress_Title!!.typeface = fontStyle(this,"Light")

        locationList = findViewById<View>(R.id.locationList) as RecyclerView


        searchAdap = SearchPageAdapter(this, loca as ArrayList<String>)

        locationList!!.setLayoutManager(
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        )

        locationList!!.setAdapter(searchAdap)

        searchAdap!!.setCallback(this)

        searchEdit = findViewById<View>(R.id.searchEdit) as EditText

        searchEdit!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.toString().length >= 0) {
                    voll(charSequence.toString(), this@AddressSearch)
                    locationList!!.visibility = View.VISIBLE
                    if (loca == null) {
                    } else {
                        locationList!!.visibility = View.VISIBLE
                        //pickerPoint.setVisibility(View.GONE);
                    }
                    searchAdap!!.notifyDataSetChanged()
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        val intent = intent

        if(intent != null) {
            if (intent.getStringExtra("Total Amount") != null) {
                total_value = intent.getStringExtra("Total Amount")
            }

            if (intent.getStringExtra("Item") != null) {
                itemValue = intent.getStringExtra("Item")
            }
        }
    }

    fun autocomplete(input: String?): ArrayList<*>? {
        var resultList: ArrayList<*>? = null
        var conn: HttpURLConnection? = null
        val jsonResults = StringBuilder()
        try {
            val sb = StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON)
            sb.append("?key=" + API_KEY)
            //sb.append("&components=country:gr");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"))
            val url = URL(sb.toString())
            conn = url.openConnection() as HttpURLConnection
            val `in` = InputStreamReader(conn.inputStream)
            // Load the results into a StringBuilder
            var read: Int
            val buff = CharArray(1024)
            while (`in`.read(buff).also { read = it } != -1) {
                jsonResults.append(buff, 0, read)
            }
        } catch (e: MalformedURLException) { //Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList
        } catch (e: IOException) { //Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList
        } finally {
            conn?.disconnect()
        }
        try { // Create a JSON object hierarchy from the results
            val jsonObj = JSONObject(jsonResults.toString())
            val predsJsonArray = jsonObj.getJSONArray("predictions")
            // Extract the Place descriptions from the results
            resultList = ArrayList<Any?>(predsJsonArray.length())
            for (i in 0 until predsJsonArray.length()) {
                println(predsJsonArray.getJSONObject(i).getString("description"))
                println("============================================================")
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"))
            }
        } catch (e: JSONException) {
            Log.e("", "Cannot process JSON results", e)
        }
        return resultList
    }

    fun vollAddress(input: String, context: Context?) {
        val queue = Volley.newRequestQueue(context)
        val myArray = arrayOfNulls<String>(1)
        val sr: StringRequest = @SuppressLint("LongLogTag")
        object : StringRequest(Method.GET, "https://maps.googleapis.com/maps/api/geocode/json?address="+input+"&sensor=false&key="+getString(R.string.GCM_Api_Main),
            Response.Listener { response ->

                Log.v("Link = "+"https://maps.googleapis.com/maps/api/geocode/json?address="+input+"&sensor=false&key="+getString(R.string.GCM_Api_Main),"==")


                Log.e("HttpClient", "success! response: $response")
                try { // Create a JSON object hierarchy from the results
                    val jsonObj = JSONObject(response)
                    val predsJsonArray = jsonObj.getJSONArray("results")
                    // Extract the Place descriptions from the results
                    if (loca != null) {
                        if (loca.size != 0) {
                            loca.clear()
                        }
                    }
                    for (i in 0 until predsJsonArray.length()) {
                        println(predsJsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat"));
                        println("============================================================")
                        println(predsJsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng"));
                        println("============================================================")
                        ;


                        longtitude =
                            "" + predsJsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject(
                                "location"
                            ).getString("lng")

                        latitude =
                            "" + predsJsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject(
                                "location"
                            ).getString("lat")

                        var intent = Intent(this, MapsInnerActivity::class.java)

                        intent.putExtra("Page", "Add Address")
                        intent.putExtra("latitude", ""+latitude)
                        intent.putExtra("longitude", ""+longtitude)
                        intent.putExtra("Total Amount", "" + total_value)
                        intent.putExtra("item", "" + itemValue)
                        startActivity(intent)
                        finish()
                        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()!!.getWindowToken(), 0)
                        finish()
                    }
                } catch (e: JSONException) {
                    Log.e(LOG_TAG!!, "Cannot process JSON results", e)
                }
            },
            Response.ErrorListener { error -> Log.e("HttpClient", "error: $error") }) {
            override fun getParams(): Map<String, String> {
                return HashMap()
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Content-Type"] = "application/x-www-form-urlencoded"
                return params
            }
        }
        queue.add(sr)
    }

    fun voll(input: String, context: Context?) {
        val queue = Volley.newRequestQueue(context)
        val myArray = arrayOfNulls<String>(1)
        //Log.v("HttpClient", "https://maps.googleapis.com/maps/api/geocode/json?address="+input+"&sensor=false&key=AIzaSyAu5vm4TyW2fi3I-acXC0IeFGm_bpB0cyA")
        val sr: StringRequest = object : StringRequest(Method.GET, "https://maps.googleapis.com/maps/api/place/autocomplete/json?key="+getString(R.string.GCM_Api_Main)+"&input=$input",
                Response.Listener { response ->
                    //Log.v("HttpClient", "https://maps.googleapis.com/maps/api/geocode/json?address="+input+"&sensor=false&key=AIzaSyAu5vm4TyW2fi3I-acXC0IeFGm_bpB0cyA")
                    Log.e("HttpClient", "success! response: $response")
                    try { // Create a JSON object hierarchy from the results
                        val jsonObj = JSONObject(response)
                        val predsJsonArray = jsonObj.getJSONArray("predictions")
                        // Extract the Place descriptions from the results
                        if (loca != null) {
                            if (loca.size != 0) {
                                loca.clear()
                            }
                        }
                        for (i in 0 until predsJsonArray.length()) {
                            println(predsJsonArray.getJSONObject(i).getString("description"))
                            println("============================================================")
                            loca.add(predsJsonArray.getJSONObject(i).getString("description"))
                            searchAdap!!.notifyDataSetChanged()
                            myArrayValue = myArrayValue + "-" + predsJsonArray.getJSONObject(i).getString("description")

                            Log.v("Enter","jj"+loca[0]);
                        }
                    } catch (e: JSONException) {
                        Log.e(LOG_TAG!!, "Cannot process JSON results", e)
                    }
                },
                Response.ErrorListener { error -> Log.e("HttpClient", "error: $error") }) {
            override fun getParams(): Map<String, String> {
                return HashMap()
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Content-Type"] = "application/x-www-form-urlencoded"
                return params
            }
        }
        queue.add(sr)
    }



    override fun setOnMaterial(price: String?) {
        vollAddress(price!!,this)
        searchEdit!!.setText(price)
        //startActivity(Intent(this, AddNewAddressActivity::class.java))


    }

    override fun onClick(view: View?) {

        when(view!!.id)
        {
            R.id.addAddress_back->{
                finish()
            }
        }

    }
}
