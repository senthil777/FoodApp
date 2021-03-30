package com.lieferin_global.Fragment

import android.Manifest
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.Dialog
import android.content.*
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Base64.encodeToString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.lieferin_global.Adapter.ProfileListAdapter
import com.lieferin_global.LocalDataBase.DBHelper
import com.lieferin_global.Model.AdapterModel
import com.lieferin_global.Model.Product
import com.lieferin_global.R
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.Constant.AppType
import com.lieferin_global.Utility.Constant.photoConstant
import com.lieferin_global.webservice.RequestManager
import com.lieferin_global.webservice.ResponseListener
import com.lieferin_global.webservices.responce.BaseRS
import com.rentaloo.CallBack.CallBlacklisting
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*


class ProfileFragment : Fragment(),View.OnClickListener,ProfileListAdapter.CallbackDataAdapter,ResponseListener {

    val ROOT_URL = "http://lieferin.de:81/api/customer-profile-update"

    private val REQUEST_CAMERA = 22

    val REQUEST_Storage = 100

    val PICK_IMAGE_REQUEST = 1

    var bitmap: Bitmap? = null

    var filePath: String? = null

    var file: File? = null

    var uriPath: Uri? = null

    var rootView: View? = null

    var profileImg: CircleImageView? = null

    var profileName: TextView? = null

    var profileGender: TextView? = null

    var profileEmail: TextView? = null

    var profileNo: TextView? = null

    var ProfileList: RecyclerView? = null

    var mAdapter: ProfileListAdapter? = null

    var editImage: ImageView? = null

    var adapterProduct: MutableList<Product> = ArrayList()

    var adapterModels: MutableList<AdapterModel> = ArrayList<AdapterModel>()

    internal lateinit var adapterModel: AdapterModel

    var dbHelper:DBHelper? = null


    fun ProfileFragment() { // Required empty public constructor
    }

    var PICK_IMAGE_CAMERA = 1

    var PICK_IMAGE_GALLERY = 2

    var photo: Bitmap? = null

    var bitmapArray = ArrayList<Bitmap>()

    var picUri: Uri? = null
    var callBlacklisting: CallBlacklisting? = null

    var profileBackGround : LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        dbHelper = DBHelper(activity)

        profileBackGround = rootView!!.findViewById(R.id.profileBackGround) as LinearLayout

        if(AppType!!.equals("0"))
        {
            profileBackGround!!.setBackgroundColor(Color.parseColor("#FFA790"))
        }else{
            profileBackGround!!.setBackgroundColor(Color.parseColor("#802ED184"))
        }

        profileImg = rootView!!.findViewById<View>(R.id.profileImg) as CircleImageView

        //profileImg!!.setColorFilter(colorIcon(activity!!,R.color.colorWhite,R.drawable.user_img,profileImg!!))

        if(!dbHelper!!.getUserDetails().profilePicture.equals("")) {

            Picasso.with(activity).load(dbHelper!!.getUserDetails().profilePicture).resize(450, 450)
                .placeholder(R.drawable.place_holder).into(profileImg)

        }
        profileImg!!.setOnClickListener(this)

        editImage = rootView!!.findViewById<View>(R.id.editImage) as ImageView

        editImage!!.setColorFilter(colorIcon(activity!!,R.color.colorWhite,R.drawable.edit,editImage!!))

        editImage!!.setOnClickListener(this)

        profileName = rootView!!.findViewById<View>(R.id.profileName) as TextView

        profileGender = rootView!!.findViewById<View>(R.id.profileGender) as TextView

        profileEmail = rootView!!.findViewById<View>(R.id.profileEmail) as TextView

        profileNo = rootView!!.findViewById<View>(R.id.profileNo) as TextView

        profileName!!.setTypeface(fontStyle(activity!!, "SemiBold"))

        profileName!!.setText(dbHelper!!.getUserDetails().name)

        profileGender!!.setTypeface(fontStyle(activity!!, "SemiBold"))

        profileGender!!.setText(dbHelper!!.getUserDetails().email)

        profileEmail!!.setTypeface(fontStyle(activity!!, ""))

        profileEmail!!.setText(dbHelper!!.getUserDetails().mobile)

        profileNo!!.setTypeface(fontStyle(activity!!, ""))

        ProfileList = rootView!!.findViewById<View>(R.id.ProfileList) as RecyclerView

        mAdapter = ProfileListAdapter(activity!!, adapterModels)

        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)

        ProfileList!!.layoutManager = mLayoutManager

        ProfileList!!.itemAnimator!!.addDuration = 5000

        mAdapter!!.setCallback(this)

        ProfileList!!.adapter = mAdapter

        showData(dbHelper!!.getUserDetails().address)

        if(photoConstant != null)
        {
            photo = photoConstant

            encodeToString(photo!!)

            val resizedBitmap = Bitmap.createScaledBitmap(photo!!, 150, 150, false)
            val bytearrayoutputstream = ByteArrayOutputStream()
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytearrayoutputstream)
            photo = resizedBitmap
            profileImg!!.setImageBitmap(photo)
            uploadBitmap(resizedBitmap)
            //uploadBitmap(photo!!)
        }

        return rootView;
    }

    fun checkPermissionPhone() {
        val rc = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.CAMERA
        )
        if (rc == PackageManager.PERMISSION_GRANTED) {
            checkPermissionSms()
        } else {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA
            )
        }
    }

    fun checkPermissionSms() {
        if (ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) && ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    REQUEST_Storage
                )
            }
        } else {
            Log.e("Else", "Else")
            selectImage()
        }
    }

    override fun onClick(view: View?) {

        when(view!!.id)
        {
            R.id.editImage ->{

                callBlacklisting!!.fragmentChange("Update Profile",null)
            }

            R.id.profileImg ->{
                checkPermissionPhone()
                //selectImage()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {

            REQUEST_CAMERA -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                checkPermissionSms()
                showToast(activity!!, "Success")
            } else {
                showToast(activity!!, "Permission Denied")
            }
            REQUEST_Storage -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                selectImage()
                showToast(activity!!, "Success1")
            } else {
                showToast(activity!!, "Permission Denied")
            }
        }
    }

/*
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    )

        when (requestCode) {
            REQUEST_CAMERA -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                checkPermissionPhone()
            } else {
                showToast(activity!!, "Permission Denied")
            }
           REQUEST_PERMISSIONS -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                checkPermissionSms()
            } else {
               showToast(activity!!, "Permission Denied")
            }

        }
    }
*/

    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            PICK_IMAGE_REQUEST
        )
    }

    override fun setOnFav(id: String?) {

        if(id!!.equals("Logout"))
        {
            val obj = JSONObject()
            obj.put("token", ""+dbHelper!!.getUserDetails().token)
            Log.v("Json", "Value" + obj)
            RequestManager.setCustomerLogout(activity, obj, this);
        }else if(id!!.equals(getString(R.string.Offers)))
        {

            callBlacklisting!!.fragmentChange("Offers",null)

        }else if(id!!.equals("Rate This app"))
        {

            val appPackageName: String =
                activity!!.getPackageName() // getPackageName() from Context or Activity object

            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (anfe: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }

        }else if(id!!.equals("Booking History"))
        {

            callBlacklisting!!.fragmentChange("My Orders",null)

        }else if(id!!.equals("Customer Service"))
        {
            customerServiceDialog(activity!!)
            //callBlacklisting!!.fragmentChange("My Orders",null)

        }else if(id!!.equals("Invite a Friend"))
        {

            callBlacklisting!!.fragmentChange("Rererrals",null)

        }else if(id!!.equals("Privacy Policy"))
        {
            val bundle = Bundle()

            bundle.putString("page",""+id)

            callBlacklisting!!.fragmentChange("Terms",bundle)

        }else if(id!!.equals("Payments & Refunds"))
        {
            val bundle = Bundle()

            bundle.putString("page",""+id)
            callBlacklisting!!.fragmentChange("Terms",bundle)

        }else if(id!!.equals("Terms and conditions"))
        {
            val bundle = Bundle()

            bundle.putString("page",""+id)
            callBlacklisting!!.fragmentChange("Terms",bundle)

        }else if(id!!.equals(getString(R.string.Cancellation_Policy)))
        {
            val bundle = Bundle()

            bundle.putString("page",""+id)
            callBlacklisting!!.fragmentChange("Terms",bundle)

        }

    }
    fun customerServiceDialog(context: Context)
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
        dialog.setContentView(R.layout.customer_service)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tableLA = dialog.findViewById<View>(R.id.tableLA) as TextView

        tableLA!!.typeface = fontStyle(context,"SemiBold")

        tableLA!!.text = "Customer Service"

        val msgTV = dialog.findViewById<View>(R.id.msgTV) as TextView

        msgTV!!.typeface = fontStyle(context,"")


        val descriptionTV= dialog.findViewById<View>(R.id.descriptionTV) as TextView

        descriptionTV!!.typeface = fontStyle(context,"")

        val submitTV = dialog.findViewById<View>(R.id.submitTV) as TextView

        submitTV!!.typeface = fontStyle(context,"SemiBold")

        submitTV!!.setOnClickListener(View.OnClickListener {
            dialog.cancel()

        })

        val CancelTV = dialog.findViewById<View>(R.id.CancelTV) as TextView

        CancelTV!!.typeface = fontStyle(context,"SemiBold")

        CancelTV!!.setOnClickListener(View.OnClickListener {

            dialog.cancel()
        })

        dialog.show()

        //dialog.cancel()
    }
    override fun onActivityCreated(@Nullable savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //getActivity() is fully created in onActivityCreated and instanceOf differentiate it between different Activities
        if (activity is CallBlacklisting) callBlacklisting = activity as CallBlacklisting?
    }

    fun showData(Address: String?) {
        if (adapterModels != null) {
            adapterModels.clear()
        }
        adapterModel = AdapterModel(
            R.drawable.address,
            Address!!,
            "Chin",
            "₹ 25",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "",
            "",
            "",
            "",
            "",
            "",
            "",0,0,0,adapterProduct
        )
        //adapterModels.add(adapterModel)
        adapterModel = AdapterModel(
            R.drawable.history,
            ""+getString(R.string.Booking_History),
            "Face",
            "₹ 115",
            "20 % OFF",
            "4.2 Km",
            "SKM Beauty Spa",
            "Tatabad,Gandhipuram",
            "4.2",
            "Good",
            "3046 Ratings>",
            "",
            "",
            "",
            "",
            "",
            "",
            "",0,0,0,adapterProduct
        )
        adapterModels.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.tag,
            ""+getString(R.string.Offers),
            "Face",
            "₹ 115",
            "20 % OFF",
            "4.2 Km",
            "SKM Beauty Spa",
            "Tatabad,Gandhipuram",
            "4.2",
            "Good",
            "3046 Ratings>",
            "",
            "",
            "",
            "",
            "",
            "",
            "",0,0,0,adapterProduct
        )
        adapterModels.add(adapterModel)
        adapterModel = AdapterModel(
            R.drawable.gift,
            ""+getString(R.string.Invite_a_Friend),
            "Sides",
            "₹ 30",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "",
            "",
            "",
            "",
            "",
            "",
            "",0,0,0,adapterProduct
        )
        adapterModels.add(adapterModel)
        adapterModel = AdapterModel(
            R.drawable.phone_call,
            ""+getString(R.string.Customer_Service),
            "Chin",
            "₹ 25",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "",
            "",
            "",
            "",
            "",
            "",
            "",0,0,0,adapterProduct
        )
        adapterModels.add(adapterModel)
        adapterModel = AdapterModel(
            R.drawable.notepad,
            ""+getString(R.string.Cancellation_Policy),
            "Face",
            "₹ 115",
            "20 % OFF",
            "4.2 Km",
            "SKM Beauty Spa",
            "Tatabad,Gandhipuram",
            "4.2",
            "Good",
            "3046 Ratings>",
            "",
            "",
            "",
            "",
            "",
            "",
            "",0,0,0,adapterProduct
        )
        adapterModels.add(adapterModel)
        adapterModel = AdapterModel(
            R.drawable.notepad,
            "Terms and conditions",
            "Sides",
            "₹ 30",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "",
            "",
            "",
            "",
            "",
            "",
            "",0,0,0,adapterProduct
        )
        adapterModels.add(adapterModel)



        adapterModel = AdapterModel(
            R.drawable.notepad,
            "Privacy Policy",
            "Sides",
            "₹ 30",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "",
            "",
            "",
            "",
            "",
            "",
            "",0,0,0,adapterProduct
        )
        adapterModels.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.notepad,
            "Payments & Refunds",
            "Sides",
            "₹ 30",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "",
            "",
            "",
            "",
            "",
            "",
            "",0,0,0,adapterProduct
        )
        adapterModels.add(adapterModel)
        adapterModel = AdapterModel(
            R.drawable.rate,
            "Rate This app",
            "Sides",
            "₹ 30",
            "60 % OFF",
            "2.2 Km",
            "Purple Beauty Park",
            "Tatabad,Gandhipuram",
            "4.4",
            "Very Good",
            "3054 Ratings>",
            "",
            "",
            "",
            "",
            "",
            "",
            "",0,0,0,adapterProduct
        )
        adapterModels.add(adapterModel)

        adapterModel = AdapterModel(
            R.drawable.logout,
            "Logout",
            "Face",
            "₹ 115",
            "20 % OFF",
            "4.2 Km",
            "SKM Beauty Spa",
            "Tatabad,Gandhipuram",
            "4.2",
            "Good",
            "3046 Ratings>",
            "",
            "",
            "",
            "",
            "",
            "",
            "",0,0,0,adapterProduct
        )
        adapterModels.add(adapterModel)
        mAdapter!!.notifyDataSetChanged()
    }

    private fun selectImage() {
        try {
            val pm: PackageManager = activity!!.packageManager
            val hasPerm =
                pm.checkPermission(Manifest.permission.CAMERA, activity!!.packageName)
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                val options =
                    arrayOf<CharSequence>("Take Photo", "Choose From Gallery", "Cancel")
                val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
                builder.setTitle("Select Option")
                builder.setItems(options,
                    DialogInterface.OnClickListener { dialog, item ->
                        if (options[item] == "Take Photo") {
                            dialog.dismiss()
                            /*val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                            startActivityForResult(intent, PICK_IMAGE_CAMERA)*/

                            val values = ContentValues()
                            values.put(MediaStore.Images.Media.TITLE, "Image File name")
                            uriPath = activity!!.getContentResolver().insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                values
                            )!!
                            val intentPicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            intentPicture.putExtra(MediaStore.EXTRA_OUTPUT, uriPath)
                            //val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                            startActivityForResult(intentPicture, PICK_IMAGE_CAMERA)
                            //CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(activity!!);

                        } else if (options[item] == "Choose From Gallery") {
                            dialog.dismiss()
                            val pickPhoto = Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            )
                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY)
                        } else if (options[item] == "Cancel") {
                            dialog.dismiss()
                        }
                    })
                builder.show()
            } else Toast.makeText(activity!!, "Camera Permission error", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(activity!!, "Camera Permission error", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun onResponseReceived(responseObj: Any?, requestType: Int) {

        if(responseObj != null)
        {
            if(requestType == Constant.MEMBER_getCustomer_URL_RQ)
            {
                showToast(activity!!,(responseObj as BaseRS).message)

                if((responseObj as BaseRS).status.equals("100")) {


                    //startActivity(Intent(this, DashBoardActivity::class.java))

                }

                if ((responseObj as BaseRS).status.equals("3025")) {
                    showToast(activity!!,(responseObj as BaseRS).message)
                    callBlacklisting!!.fragmentChange("Login", null)
                }

            }else if(requestType == Constant.MEMBER_CustomerLogout_URL_RQ)
            {
                //showToast(activity!!,(responseObj as BaseRS).message)

                if((responseObj as BaseRS).status.equals("1010")) {

                    localstorageUserInfo(activity!!,"111","Logout","","","","","")

                    callBlacklisting!!.fragmentChange("Logout",null)
                    //startActivity(Intent(this, DashBoardActivity::class.java))

                }

                if ((responseObj as BaseRS).status.equals("1011")) {
                    showToast(activity!!,(responseObj as BaseRS).message)
                    callBlacklisting!!.fragmentChange("Login", null)
                }

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.v("llllll","lllll"+requestCode)
        if (requestCode == PICK_IMAGE_CAMERA) {
            try {


                CropImage.activity(uriPath).setGuidelines(CropImageView.Guidelines.ON).start(activity!!);

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        } else if (requestCode == PICK_IMAGE_GALLERY) {

            try {
               if (getPickImageResultUri(data) != null) {
                    picUri = getPickImageResultUri(data)
                }
                uriPath= picUri
                CropImage.activity(uriPath).setGuidelines(CropImageView.Guidelines.ON).start(activity!!);
                /*photo = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, picUri)
                profileImg!!.setImageBitmap(photo)
                val resizedBitmap = Bitmap.createScaledBitmap(photo!!, 150, 150, false)
                val bytearrayoutputstream = ByteArrayOutputStream()
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytearrayoutputstream)
                uploadBitmap(resizedBitmap)*/
                //uploadBitmap(photo!!)
                //encodeToString(photo)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun encodeToString(mBitmap: Bitmap): String? { // Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_account_outline_black_24dp);
        val baos = ByteArrayOutputStream()
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos) //bm is the bitmap object
        val b = baos.toByteArray()
        val compressedBitmap = BitmapFactory.decodeByteArray(b, 0, b.size)
        Log.v("hight" + getResizedBitmap(compressedBitmap, 500), "width" + b.size)
        return Base64.encodeToString(b, Base64.NO_WRAP)
    }
    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    fun getPickImageResultUri(data: Intent?): Uri? {
        var isCamera = true
        if (data != null) {
            val action = data.action
            isCamera = action != null && action == MediaStore.ACTION_IMAGE_CAPTURE
        }
        return if (isCamera) getCaptureImageOutputUri() else data!!.data
    }

    private fun getCaptureImageOutputUri(): Uri? {
        var outputFileUri: Uri? = null
        val getImage: File = activity!!.externalCacheDir!!
        if (getImage != null) {
            outputFileUri =
                Uri.fromFile(File(getImage.path, "profile.png"))
        }
        return outputFileUri
    }

    /*protected override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
       // super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val picUri = data.data
            filePath = getPath(picUri)
            if (filePath != null) {
                try {
                    //textView.setText("File Selected")
                    Log.d("filePath", filePath.toString())
                    bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, picUri)
                    //bitmapArray.add(bitmap)
                    //imageView.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(
                    activity!!, "no image selected",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }*/

    fun getPath(uri: Uri?): String {
       /* var cursor: Cursor =
            activity!!.contentResolver.query(uri, null, null, null, null)*/

        var cursor: Cursor? =
            activity!!.contentResolver.query(uri!!,null,null,null,null,null)
        cursor!!.moveToFirst()
        var document_id = cursor.getString(0)
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1)
        cursor!!.close()
        cursor = activity!!.contentResolver!!.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null, MediaStore.Images.Media._ID + " = ? ", arrayOf(document_id), null
        )
        cursor!!.moveToFirst()
        val path =
            cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        cursor.close()
        return path
    }


    fun getFileDataFromDrawable(bitmap: Bitmap): ByteArray? {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    private fun uploadBitmap(bitmapArray: Bitmap) {
        loadingScreen(activity)
        val volleyMultipartRequest: VolleyMultipartRequest = object :
            VolleyMultipartRequest(
                Method.POST,
                ROOT_URL,
                Response.Listener { response ->
                    try {
                        Log.e("GotError1", "" + response)
                        val obj = JSONObject(String(response.data))
                        Log.e("GotError1", "" + obj)
                        Toast.makeText(
                            activity!!,
                            obj.getString("userPath"),
                            Toast.LENGTH_SHORT
                        ).show()
                        loadingScreenClose(activity)
                        localstorageUserInfo(activity!!,localgetUserInfo("token"),localgetUserInfo("name"),localgetUserInfo("email"),localgetUserInfo("mobile"),localgetUserInfo("address"),localgetUserInfo("referenceCode"),obj.getString("userPath"))

                        dbHelper!!.updateUserImage(obj.getString("userPath"),""+localgetUserInfo("token"))
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error ->
                    loadingScreenClose(activity)
                    Toast.makeText(activity!!, error.message, Toast.LENGTH_LONG)
                        .show()
                    Log.e("GotError", "" + error.message)
                }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val params1: MutableMap<String, String> =
                    HashMap()
                params1["token"] = ""+ localgetUserInfo("token")

                Log.v("kkkkk","9999 "+ params1)
                return params1
            }

            override fun getByteData(): Map<String, DataPart>? {
                val params2: MutableMap<String, DataPart> =
                    HashMap()
                val imagename = System.currentTimeMillis()

                params2["profilePicture"] = DataPart(
                    "$imagename.png",
                    getFileDataFromDrawable(bitmapArray)
                )

                return params2
            }
        }
        //adding the request to volley
        Volley.newRequestQueue(activity)
            .add(volleyMultipartRequest)
    }
}
