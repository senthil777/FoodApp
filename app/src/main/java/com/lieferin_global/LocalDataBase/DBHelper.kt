package com.lieferin_global.LocalDataBase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.lieferin_global.Model.*
import com.lieferin_global.Utility.*
import com.lieferin_global.Utility.addIncreasePriceDivide
import com.lieferin_global.webservices.request.DetailsINfo
import com.lieferin_global.webservices.responce.ResultRes
import com.lieferin_global.webservices.responce.WorkExperience




class DBHelper(context: Context?) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_ITEM_INFO_TABLE =
            ("CREATE TABLE " + ITEM_INFO_TABLE_NAME + "("
                    + CATEGORY_COLUMN_ID + " INTEGER PRIMARY KEY," + CATEGORY_COLUMN_NAME + " TEXT,"+ CATEGORY_RESTAURANT_ID + " TEXT," + CATEGORY_REFERENCE_CODE + " TEXT" + ")")

        val CREATE_MENU_INFO_TABLE =
            ("CREATE TABLE " + MENU_INFO_TABLE_NAME + "("
                    + MENU_ID + " INTEGER PRIMARY KEY," + MENU_CATEGORY_ID + " TEXT," + MENU_PRICE + " TEXT,"  + MENU_NAME + " TEXT," + MENU_REFERENCE_CODE + " TEXT,"+MENU_QUANTITY +" TEXT," +MENU_RESTAURANT_ID +" TEXT," + MENU_NOTES_ID +" TEXT,"+ MENU_PRICE_TOTAL +" TEXT," + MENU_INFO_OFFER_TYPE + " TEXT" + ")")

        val CREATE_TOPPINS_GROUP_INFO_TABLE =
            ("CREATE TABLE " + TOPPINS_GROUP_INFO_TABLE_NAME + "("
                    + TOPPINS_GROUP_ID + " INTEGER PRIMARY KEY," + TOPPINS_GROUP_NAME + " TEXT," + TOPPINS_GROUP_REFERENCE_CODE + " TEXT,"  + TOPPINS_GROUP_MENU_ID + " TEXT," + TOPPINS_GROUP_RESTAURANT_ID + " TEXT," + TOPPINS_GROUP_TYPE + " TEXT" + ")")


        val CREATE_TOPPINS_INFO_TABLE =
            ("CREATE TABLE " + TOPPINS_INFO_TABLE_NAME + "("
                    + TOPPINS_ID + " TEXT," + TOPPINS_NAME + " TEXT," + TOPPINS_SUB_GROUP_ID + " TEXT,"  + TOPPINS_REFERENCE_CODE + " TEXT," + TOPPINS_DESCRIPTION + " TEXT," + TOPPINS_RESTAURANT_ID + " TEXT," + TOPPINS_ISQUANTITY + " TEXT,"+ TOPPINS_PRICE + " TEXT," + TOPPINS_MIN_MENU_ID + " TEXT" + ")")

        val CREATE_RESTAURANT_INFO_TABLE =
            ("CREATE TABLE " + RESTAURANT_INFO_TABLE_NAME + "("
                    + RESTAURANT_ID + " INTEGER PRIMARY KEY," + RESTAURANT_NAME + " TEXT," + RESTAURANT_REFERENCE_CODE + " TEXT," + RESTAURANT_Address + " TEXT" + ")")

        val CREATE_GEOCERY_INFO_TABLE =
            ("CREATE TABLE " + GROCERY_INFO_TABLE_NAME + "("
                    + GROCERY_ID + " TEXT," + GROCERY_STORE_REFERENCE_CODE + " TEXT," + GROCERY_STORE_NAME + " TEXT," + GROCERY_STORE_PRODUCT + " TEXT," + GROCERY_STORE_PRODUCT_REFERENCE_CODE + " TEXT," + GROCERY_STORE_PRODUCT_QUALITY + " TEXT," + GROCERY_STORE_PRODUCT_PRICE + " TEXT," + GROCERY_STORE_PRODUCT_TOTAL_PRICE + " TEXT" + ")")

        val CREATE_USER_INFO_TABLE =
            ("CREATE TABLE " + USER_INFO_TABLE_NAME + "("
                    + USER_ID + " TEXT," + USER_TOKEN_CODE + " TEXT," + USER_NAME + " TEXT," + USER_EMAIL + " TEXT," + USER_PHONE + " TEXT," + USER_ADDRESS + " TEXT," + USER_LATITUDE + " TEXT," + USER_LONGTITUDE + " TEXT," + USER_PROFILE_IMG + " TEXT" + ")")

        val CREATE_LOCATION_TABLE =
            ("CREATE TABLE " + LOCATION_TABLE_NAME + "("
                    + LOCATION_LATITUDE + " TEXT," + LOCATION_LONGTITUDE + " TEXT," + LOCATION_ADDRESS + " TEXT" + ")")

        db.execSQL(CREATE_ITEM_INFO_TABLE)

        db.execSQL(CREATE_MENU_INFO_TABLE)

        db.execSQL(CREATE_TOPPINS_GROUP_INFO_TABLE)

        db.execSQL(CREATE_TOPPINS_INFO_TABLE)

        db.execSQL(CREATE_RESTAURANT_INFO_TABLE)

        db.execSQL(CREATE_GEOCERY_INFO_TABLE)

        db.execSQL(CREATE_USER_INFO_TABLE)

        db.execSQL(CREATE_LOCATION_TABLE)


    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        db.execSQL(" DROP TABLE IF EXISTS $ITEM_INFO_TABLE_NAME")

        db.execSQL(" DROP TABLE IF EXISTS $MENU_INFO_TABLE_NAME")

        db.execSQL(" DROP TABLE IF EXISTS $TOPPINS_GROUP_INFO_TABLE_NAME")

        db.execSQL(" DROP TABLE IF EXISTS $TOPPINS_INFO_TABLE_NAME")

        db.execSQL(" DROP TABLE IF EXISTS $RESTAURANT_INFO_TABLE_NAME")

        db.execSQL(" DROP TABLE IF EXISTS $GROCERY_INFO_TABLE_NAME")

        db.execSQL(" DROP TABLE IF EXISTS $USER_INFO_TABLE_NAME")

        db.execSQL(" DROP TABLE IF EXISTS $LOCATION_TABLE_NAME")
        //Create tables again
        onCreate(db)
    }

    fun addItemCategory_info_reOrder(userInfo: ProductListView,restaurant: String,restRefenceCode : String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(GROCERY_ID, userInfo.toppinsReferenceCode)
        values.put(GROCERY_STORE_REFERENCE_CODE, restRefenceCode)
        values.put(GROCERY_STORE_NAME, restaurant)
        values.put(GROCERY_STORE_PRODUCT,userInfo.name)
        values.put(GROCERY_STORE_PRODUCT_REFERENCE_CODE, userInfo.toppinsId)
        values.put(GROCERY_STORE_PRODUCT_QUALITY, userInfo.price)
        values.put(GROCERY_STORE_PRODUCT_PRICE, userInfo.toppinsGroupId)

        values.put(GROCERY_STORE_PRODUCT_TOTAL_PRICE,addIncreasePriceHole(userInfo.toppinsGroupId!!,userInfo.price!!))
        // Inserting Row
        db.insert(GROCERY_INFO_TABLE_NAME, null, values)
        db.close() // Closing database connection
    }

    fun addItemCategory_info(userInfo: AdapterModel,restaurant: String,restRefenceCode : String,id:String,totalValue:String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(GROCERY_ID, userInfo.categoryId)
        values.put(GROCERY_STORE_REFERENCE_CODE, restRefenceCode)
        values.put(GROCERY_STORE_NAME, restaurant)
        values.put(GROCERY_STORE_PRODUCT,userInfo.categoryName)
        values.put(GROCERY_STORE_PRODUCT_REFERENCE_CODE, userInfo.categoryReferenceCode)
        values.put(GROCERY_STORE_PRODUCT_QUALITY, id)
        if(userInfo.closeTime.equals("0")) {
            values.put(GROCERY_STORE_PRODUCT_PRICE, userInfo.price)
        }else{
            values.put(GROCERY_STORE_PRODUCT_PRICE, userInfo.closeTime)
        }
        values.put(GROCERY_STORE_PRODUCT_TOTAL_PRICE,totalValue)
        // Inserting Row
        db.insert(GROCERY_INFO_TABLE_NAME, null, values)
        db.close() // Closing database connection
    }

    fun addItemCategory_infoValue(categoryId: String,categoryName: String,categoryReferenceCode: String,restaurant: String,restRefenceCode : String,price: String,offerPrice: String,id:String,totalValue:String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(GROCERY_ID, categoryId)
        values.put(GROCERY_STORE_REFERENCE_CODE, categoryName)
        values.put(GROCERY_STORE_NAME, restaurant)
        values.put(GROCERY_STORE_PRODUCT,categoryReferenceCode)
        values.put(GROCERY_STORE_PRODUCT_REFERENCE_CODE, restRefenceCode)
        values.put(GROCERY_STORE_PRODUCT_QUALITY, id)
        if(offerPrice.equals("0")) {
            values.put(GROCERY_STORE_PRODUCT_PRICE, price)
        }else{
            values.put(GROCERY_STORE_PRODUCT_PRICE, offerPrice)
        }
        values.put(GROCERY_STORE_PRODUCT_TOTAL_PRICE,totalValue)
        // Inserting Row
        db.insert(GROCERY_INFO_TABLE_NAME, null, values)
        db.close() // Closing database connection
    }

    fun addLocation(latitude: String, longtitude : String,address: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(LOCATION_LATITUDE, latitude)
        values.put(LOCATION_LONGTITUDE, longtitude)
        values.put(LOCATION_ADDRESS, address)

        /*values.put(USER_LATITUDE,totalValue)
        values.put(USER_LONGTITUDE,totalValue)*/
        // Inserting Row
        Log.v("Location == "+latitude,"Locatiovvvvn == "+address)
        db.insert(LOCATION_TABLE_NAME, null, values)
        db.close() // Closing database connection
    }

    fun addUser(userInfo: DetailsINfo, restRefenceCode: String, userPath : String,userPath1: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(USER_ID, userInfo.id)
        values.put(USER_TOKEN_CODE, restRefenceCode)
        values.put(USER_NAME, userInfo.name)
        values.put(USER_EMAIL,userInfo.email)
        values.put(USER_PHONE, userInfo.mobile)
        values.put(USER_ADDRESS, "")
        values.put(USER_PROFILE_IMG,""+userPath+""+userInfo.profilePicture)
        /*values.put(USER_LATITUDE,totalValue)
        values.put(USER_LONGTITUDE,totalValue)*/
        // Inserting Row
        db.insert(USER_INFO_TABLE_NAME, null, values)
        db.close() // Closing database connection
    }

    fun updateUserImage(id: String?,token: String): Boolean {
        val mDb = this.writableDatabase
        val values = ContentValues()
        values.put(USER_PROFILE_IMG, id)

        return mDb.update(USER_INFO_TABLE_NAME, values, USER_TOKEN_CODE.toString() + " = "+"'"+token+"'"+"", null) > 0
    }

    fun updateUserDetails(name: String?,email: String?,phone: String?,address: String?,token: String): Boolean {
        val mDb = this.writableDatabase
        val values = ContentValues()
        values.put(USER_NAME, name)

        values.put(USER_EMAIL, email)

        values.put(USER_PHONE, phone)

        values.put(USER_ADDRESS, address)

        return mDb.update(USER_INFO_TABLE_NAME, values, USER_TOKEN_CODE.toString() + " = "+"'"+token+"'"+"", null) > 0
    }

    fun updateGroceryDetailsPage(userInfo: AdapterModel, id: String?,totalPrice: String): Boolean {
        val mDb = this.writableDatabase
        val values = ContentValues()
        values.put(GROCERY_STORE_PRODUCT_QUALITY, id)
        values.put(GROCERY_STORE_PRODUCT_TOTAL_PRICE, totalPrice)

        return mDb.update(GROCERY_INFO_TABLE_NAME, values, GROCERY_STORE_PRODUCT_REFERENCE_CODE.toString() + " = "+"'"+userInfo.categoryReferenceCode+"'"+"", null) > 0
    }

    fun updateGrocerydetails(userInfo: AdapterModel, id: String?,totalPrice: String): Boolean {
        val mDb = this.writableDatabase
        val values = ContentValues()
        values.put(GROCERY_STORE_PRODUCT_QUALITY, id)
        values.put(GROCERY_STORE_PRODUCT_TOTAL_PRICE, totalPrice)

        return mDb.update(GROCERY_INFO_TABLE_NAME, values, GROCERY_STORE_PRODUCT_REFERENCE_CODE.toString() + " = "+"'"+userInfo.categoryReferenceCode+"'"+"", null) > 0
    }

    fun updateGroceryDetailsOne(userInfo: Product, id: String?,totalPrice: String): Boolean {
        val mDb = this.writableDatabase
        val values = ContentValues()
        values.put(GROCERY_STORE_PRODUCT_QUALITY, totalPrice)
        values.put(GROCERY_STORE_PRODUCT_TOTAL_PRICE, id)

        return mDb.update(GROCERY_INFO_TABLE_NAME, values, GROCERY_STORE_PRODUCT_REFERENCE_CODE.toString() + " = "+"'"+userInfo.menuReferenceCode+"'"+"", null) > 0
    }

    fun deleteGroceryFull(id:String) {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.GROCERY_INFO_TABLE_NAME +" where "+ GROCERY_STORE_PRODUCT_REFERENCE_CODE + " = "+"'"+id+"'"+"")
    }
    fun addItem_info(userInfo: AdapterModel,restaurant: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CATEGORY_COLUMN_ID, userInfo.categoryId)
        values.put(CATEGORY_COLUMN_NAME, userInfo.categoryName)
        values.put(CATEGORY_REFERENCE_CODE, userInfo.categoryReferenceCode)
        values.put(CATEGORY_RESTAURANT_ID,restaurant)
        // Inserting Row
        db.insert(ITEM_INFO_TABLE_NAME, null, values)
        db.close() // Closing database connection
    }

    fun updatedetails(userInfo: Product, id: String?,totalPrice: String): Boolean {
        val mDb = this.writableDatabase
        val values = ContentValues()
        values.put(MENU_ID, userInfo.menuId)
        values.put(MENU_CATEGORY_ID, userInfo.categoryId)
        values.put(MENU_QUANTITY, Constant.valueStringItem)
        values.put(MENU_PRICE_TOTAL,addIncreasePriceDivide(totalPrice,Constant.valueStringItem))
        values.put(MENU_NAME, userInfo.name)
        values.put(MENU_REFERENCE_CODE, userInfo.menuReferenceCode)
        values.put(MENU_PRICE, userInfo.price)

        return mDb.update(MENU_INFO_TABLE_NAME, values, MENU_ID.toString() + "=" + id, null) > 0
    }

    fun updatedetailsPrice(userInfo: Product,totalPrice: String,quantity:String): Boolean {
        val mDb = this.writableDatabase
        val values = ContentValues()

        values.put(MENU_QUANTITY, quantity)
        //values.put(MENU_PRICE_TOTAL,totalPrice)


        return mDb.update(MENU_INFO_TABLE_NAME, values, MENU_ID.toString() + "=" + userInfo.menuReferenceCode, null) > 0
    }
    fun updatedetailsNotes(menuId: String,quantity:String): Boolean {
        val mDb = this.writableDatabase
        val values = ContentValues()

        values.put(MENU_NOTES_ID, quantity)
        //values.put(MENU_PRICE_TOTAL,totalPrice)


        return mDb.update(MENU_INFO_TABLE_NAME, values, MENU_ID.toString() + "=" + menuId, null) > 0
    }

    fun addMenuPrice(adapterProductListView:ProductListView,restaurant:String,totalPrice:String) {
        val db = this.writableDatabase
        val values = ContentValues()

            values.put(MENU_ID, adapterProductListView!!.menuId)
            values.put(MENU_CATEGORY_ID, adapterProductListView!!.categoryId)
            values.put(MENU_RESTAURANT_ID, restaurant)
            values.put(MENU_QUANTITY, adapterProductListView!!.price)
            values.put(
                MENU_PRICE_TOTAL,
                addIncreasePriceHole(addIncreasePrice(adapterProductListView!!.toppinsGroupId!!,totalPrice).toString(), "1")
            )
            values.put(MENU_NAME, adapterProductListView!!.name)
            values.put(MENU_REFERENCE_CODE, adapterProductListView!!.toppinsId)
            values.put(MENU_PRICE, adapterProductListView!!.toppinsGroupId)

            values.put(MENU_NOTES_ID, "")
            // Inserting Row
            db.insert(MENU_INFO_TABLE_NAME, null, values)

        db.close() // Closing database connection
    }


    fun addMenu_info(userInfo: Product,restaurant: String,totalPrice: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(MENU_ID, userInfo.menuId)
        values.put(MENU_CATEGORY_ID, userInfo.categoryId)
        values.put(MENU_RESTAURANT_ID, restaurant)
        if(userInfo!!.offerType.equals("4")) {

            var quantity =  Constant.valueStringItem.toInt() + Constant.valueStringItem.toInt()

            values.put(MENU_QUANTITY, quantity)

            values.put(MENU_PRICE_TOTAL, addIncreasePriceDivideBuyOne(""+totalPrice))

            values.put(MENU_PRICE, addIncreasePriceDivideBuyOne(""+userInfo.price))
        }else {
            values.put(MENU_QUANTITY, Constant.valueStringItem)

            values.put(MENU_PRICE_TOTAL, addIncreasePriceDivide(totalPrice,Constant.valueStringItem))

            values.put(MENU_PRICE, userInfo.price)
        }

        values.put(MENU_NAME, userInfo.name)
        values.put(MENU_REFERENCE_CODE, userInfo.menuReferenceCode)


        values.put(MENU_INFO_OFFER_TYPE, userInfo!!.offerType)
        values.put(MENU_NOTES_ID, "")
        // Inserting Row
        db.insert(MENU_INFO_TABLE_NAME, null, values)
        db.close() // Closing database connection
    }

    fun addToppinsGroup_info_reorder(userInfo: ProductListSubView, restaurant: String,menuId: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TOPPINS_GROUP_ID, userInfo.toppinsGroupId)
        values.put(TOPPINS_GROUP_MENU_ID, menuId)
        values.put(TOPPINS_GROUP_RESTAURANT_ID, restaurant)
        values.put(TOPPINS_GROUP_NAME, userInfo.name)
        values.put(TOPPINS_GROUP_REFERENCE_CODE, userInfo.toppinsGroupReferenceCode)
        values.put(TOPPINS_GROUP_TYPE, userInfo.toppinType)
        // Inserting Row
        db.insert(TOPPINS_GROUP_INFO_TABLE_NAME, null, values)
        db.close() // Closing database connection
    }

    fun addToppins_info_reorder(userInfo: ProductSubListView,restaurant: String,menuId: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TOPPINS_ID, userInfo.toppinsId)
        values.put(TOPPINS_SUB_GROUP_ID, userInfo.toppinsGroupId)
        values.put(TOPPINS_NAME, userInfo.name)
        values.put(TOPPINS_RESTAURANT_ID, restaurant)
        values.put(TOPPINS_REFERENCE_CODE, userInfo.toppinsReferenceCode)
        values.put(TOPPINS_PRICE, userInfo.price)
        values.put(TOPPINS_MIN_MENU_ID, menuId)
        // Inserting Row
        db.insert(TOPPINS_INFO_TABLE_NAME, null, values)
        db.close() // Closing database connection
    }

    fun addToppinsGroup_info(userInfo: ProductList,restaurant: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TOPPINS_GROUP_ID, userInfo.toppinsGroupId)
        values.put(TOPPINS_GROUP_MENU_ID, userInfo.menuId)
        values.put(TOPPINS_GROUP_RESTAURANT_ID, restaurant)
        values.put(TOPPINS_GROUP_NAME, userInfo.name)
        values.put(TOPPINS_GROUP_REFERENCE_CODE, userInfo.toppinGroupReferenceCode)
        values.put(TOPPINS_GROUP_TYPE, userInfo.toppinType)
        // Inserting Row
        db.insert(TOPPINS_GROUP_INFO_TABLE_NAME, null, values)
        db.close() // Closing database connection
    }

    fun addToppins_info(userInfo: ProductListView,restaurant: String,menuId: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TOPPINS_ID, userInfo.toppinsId)
        values.put(TOPPINS_SUB_GROUP_ID, userInfo.toppinsGroupId)
        values.put(TOPPINS_NAME, userInfo.name)
        values.put(TOPPINS_RESTAURANT_ID, restaurant)
        values.put(TOPPINS_REFERENCE_CODE, userInfo.toppinsReferenceCode)
        values.put(TOPPINS_PRICE, userInfo.price)
        values.put(TOPPINS_MIN_MENU_ID, menuId)
        // Inserting Row
        db.insert(TOPPINS_INFO_TABLE_NAME, null, values)
        db.close() // Closing database connection
    }

    fun addRestaurant_info(userInfo: WorkExperience) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(RESTAURANT_ID, userInfo.restaurantId)
        values.put(RESTAURANT_NAME, userInfo.restaurantName)
        values.put(RESTAURANT_Address, userInfo.street+","+userInfo.town)
        values.put(RESTAURANT_REFERENCE_CODE, ""+userInfo.restaurantReferenceCode)
        // Inserting Row
        db.insert(RESTAURANT_INFO_TABLE_NAME, null, values)
        db.close() // Closing database connection
    }
    fun addRestaurant_info_Data(restaurantId: String,restaurantName: String,address: String,restaurantReferenceCode: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(RESTAURANT_ID, restaurantId)
        values.put(RESTAURANT_NAME, restaurantName)
        values.put(RESTAURANT_Address, address)
        values.put(RESTAURANT_REFERENCE_CODE, ""+restaurantReferenceCode)
        // Inserting Row
        db.insert(RESTAURANT_INFO_TABLE_NAME, null, values)
        db.close() // Closing database connection
    }

    fun getMenuCount(): Int {
        val countQuery = "SELECT  * FROM $MENU_INFO_TABLE_NAME"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(countQuery, null)
        val count: Int = cursor.getCount()
        cursor.close()
        return count
    }

    fun deleteRestaurant(id:String) {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.RESTAURANT_INFO_TABLE_NAME +" where "+ RESTAURANT_ID + " = "+id+"")
    }
    fun deleteToppinsFull(id:String) {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.TOPPINS_INFO_TABLE_NAME +" where "+ TOPPINS_RESTAURANT_ID + " = "+id+"")
    }

    fun deleteToppinsGroupFull(id:String) {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.TOPPINS_GROUP_INFO_TABLE_NAME +" where "+ TOPPINS_GROUP_RESTAURANT_ID + " = "+id+"")
    }

    fun deleteToppinsGroupMenuId(id:String) {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.TOPPINS_GROUP_INFO_TABLE_NAME +" where "+ TOPPINS_MENU_ID + " = "+id+"")
    }
    fun deleteMenuValueReat(id:String) {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.MENU_INFO_TABLE_NAME +" where "+ MENU_RESTAURANT_ID + " = "+id+"")
    }

    fun deleteCategory(id:String) {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.ITEM_INFO_TABLE_NAME +" where "+ CATEGORY_RESTAURANT_ID + " = "+id+"")
    }

    fun deleteToppins(id:String) {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.TOPPINS_INFO_TABLE_NAME +" where "+ TOPPINS_SUB_GROUP_ID + " = "+id+"")
    }

    fun deleteToppinsDelete(id:String) {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.TOPPINS_INFO_TABLE_NAME +" where "+ TOPPINS_ID + " = "+id+"")
    }

    fun deleteToppinsDeletemenu(id:String) {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.TOPPINS_INFO_TABLE_NAME +" where "+ TOPPINS_MIN_MENU_ID + " = "+id+"")
    }

    fun deleteMenuValue(id:String) {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.MENU_INFO_TABLE_NAME +" where "+ MENU_ID + " = "+id+"")
    }

    fun deleteToppinsGroupDelete(id:String) {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.TOPPINS_GROUP_INFO_TABLE_NAME +" where "+ TOPPINS_GROUP_MENU_ID + " = "+id+"")
    }


    fun deleteMenu() {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.MENU_INFO_TABLE_NAME)
    }

    fun deleteGrocery() {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.GROCERY_INFO_TABLE_NAME)
    }

    fun deleteUser() {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.USER_INFO_TABLE_NAME)
    }

    fun deleteCategory() {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.ITEM_INFO_TABLE_NAME)
    }

    fun deleteToppinsGroup() {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.TOPPINS_GROUP_INFO_TABLE_NAME)
    }

    fun deleteRest() {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.RESTAURANT_INFO_TABLE_NAME)
    }
    fun deleteToppins() {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.TOPPINS_INFO_TABLE_NAME)
    }

    fun deleteLocation() {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.LOCATION_TABLE_NAME)
    }

    fun add_to_cart(
        userId: String?,
        IsAdded: String?,
        price: String?,
        serviceName: String?,
        categoryName: String?
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TOPPINS_GROUP_TYPE, userId)
        values.put(TOPPINS_ID, IsAdded)
        values.put(TOPPINS_NAME, price)
        values.put(TOPPINS_REFERENCE_CODE, serviceName)
        values.put(TOPPINS_MENU_ID, categoryName)
        // Inserting Row
        db.insert(TOPPINS_GROUP_MENU_ID, null, values)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
    }
    fun add_to_cart_total(userId: String?) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TOPPINS_ISQUANTITY, userId)
        values.put(TOPPINS_PRICE, "0")
        values.put(TOPPINS_DESCRIPTION, "0")
        // Inserting Row
        db.insert(TOPPINS_SUB_GROUP_ID, null, values)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
    }

    fun getUserDetails(): DetailsINfo {
        var contactList = DetailsINfo()
        // Select All Query
        val selectQuery =
            "SELECT  * FROM $USER_INFO_TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val contact = DetailsINfo()

                contact.token =cursor.getString(1)

                contact.name =cursor.getString(2)

                contact.email =cursor.getString(3)

                contact.mobile =cursor.getString(4)

                contact.address =cursor.getString(5)

                contact.profilePicture =cursor.getString(8)
                // Adding contact to list
                contactList = contact
            } while (cursor.moveToNext())
        }
        // return contact list
        return contactList
    }

    fun getUserLocation(): ResultRes {
        var contactList = ResultRes()
        // Select All Query
        val selectQuery =
            "SELECT  * FROM $LOCATION_TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val contact = ResultRes()

                contact.latitude =cursor.getString(0)

                contact.longitude =cursor.getString(1)

                contact.address =cursor.getString(2)


                // Adding contact to list
                contactList = contact
            } while (cursor.moveToNext())
        }
        // return contact list
        return contactList
    }



    fun getRestaurant(): WorkExperience {
        var contactList = WorkExperience()
        // Select All Query
        val selectQuery =
            "SELECT  * FROM $RESTAURANT_INFO_TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val contact = WorkExperience()
                contact.restaurantId =cursor.getString(0)

                contact.restaurantName =cursor.getString(1)

                contact.restaurantReferenceCode =cursor.getString(2)

                contact.town =cursor.getString(3)
                // Adding contact to list
                contactList = contact
            } while (cursor.moveToNext())
        }
        // return contact list
        return contactList
    }

    fun getGerocyList(id:String): WorkExperience {
        var contactList = WorkExperience()
        // Select All Query
        val selectQuery =
            "SELECT * FROM grocery_info WHERE groceryStoreProductReferenceCode ="+"'"+id+"'"+""
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val contact = WorkExperience()
                contact.quantity =cursor.getString(5)

                // Adding contact to list
                contactList = contact
            } while (cursor.moveToNext())
        }
        // return contact list
        return contactList
    }





   /* fun getAllContacts1(id: String, Value: String?): String {
        val contactList = UserInfo()
        // Select All Query
        var value = ""
        val selectQuery =
            "SELECT  * FROM $TOPPINS_GROUP_MENU_ID Where $TOPPINS_ID=$id"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val contact = UserInfo()
                value = cursor.getString(1)
            } while (cursor.moveToNext())
        }
        // return contact list
        return value
    }

    fun getAllContacts2(id: String, Value: String?): UserInfo {
        var contactList = UserInfo()
        // Select All Query
        val selectQuery =
            "SELECT  * FROM $TOPPINS_GROUP_MENU_ID Where $TOPPINS_ID=$id"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val contact = UserInfo()
                contact.setUserGender(cursor.getString(2))
                // Adding contact to list
                contactList = contact
            } while (cursor.moveToNext())
        }
        // return contact list
        return contactList
    }

    fun add_Address(
        latitude: String?,
        longitude: String?,
        address: String?,
        city: String?
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(RESTAURANT_REFERENCE_CODE, latitude)
        values.put(RESTAURANT_NAME, longitude)
        values.put(RESTAURANT_Address, address)
        values.put(MENU_RESTAURANT_ID, city)
        // Inserting Row
        db.insert(RESTAURANT_ID, null, values)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
    }

    fun deleteAll() {
        val db = this.writableDatabase
        db.execSQL("delete from $ITEM_INFO_TABLE_NAME")
    }// Adding contact to list
    // return contact list

    // Select All Query
    val allContacts: UserInfo
        // looping through all rows and adding to list
        get() {
            var contactList = UserInfo()
            // Select All Query
            val selectQuery =
                "SELECT  * FROM $ITEM_INFO_TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    val contact = UserInfo()
                    contact.setUserId(cursor.getString(0))
                    contact.setUserName(cursor.getString(1))
                    contact.setUserGender(cursor.getString(2))
                    contact.setUserAddress(cursor.getString(3))
                    contact.setUserPhoneNo(cursor.getString(4))
                    contact.setUserLandMark(cursor.getString(5))
                    contact.setUserCity(cursor.getString(6))
                    contact.setUserDisticit(cursor.getString(7))
                    contact.setUserState(cursor.getString(8))
                    contact.setUserPincode(cursor.getString(9))
                    contact.setUserEmail(cursor.getString(10))
                    // Adding contact to list
                    contactList = contact
                } while (cursor.moveToNext())
            }
            // return contact list
            return contactList
        }// Adding contact to list
    // return contact list

    // Select All Query
    val allCartDetailsTotal: List<Any>
        get() {
            val contactList: MutableList<UserInfo> = ArrayList<UserInfo>()
            // Select All Query
            val selectQuery =
                "SELECT  * FROM $TOPPINS_SUB_GROUP_ID"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    val contact = UserInfo()
                    contact.setUserName(cursor.getString(2))
                    // Adding contact to list
                    contactList.add(contact)
                } while (cursor.moveToNext())
            }
            db.close()
            // return contact list
            return contactList
        }

    fun getAllCartDetailsTotalCheck(Id: String): String {
        val contactList: MutableList<UserInfo> = ArrayList<UserInfo>()
        var CatId = "0"
        // Select All Query
        val selectQuery =
            "SELECT  * FROM $TOPPINS_SUB_GROUP_ID Where $TOPPINS_PRICE = $Id"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val contact = UserInfo()
                CatId = cursor.getString(1)
                // Adding contact to list
                contactList.add(contact)
            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return CatId
    }

    fun updateData(
        id: String,
        isAdded: String?,
        price: String?
    ): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TOPPINS_ID, isAdded)
        contentValues.put(TOPPINS_NAME, price)
        db.update(
            TOPPINS_GROUP_MENU_ID,
            contentValues,
            "add_to_cart_id = ?",
            arrayOf(id)
        )
        return true
    }

    fun updateDataTotal(
        id: String,
        price: String?,
        subCatId: String?
    ): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TOPPINS_PRICE, subCatId)
        contentValues.put(TOPPINS_DESCRIPTION, price)
        db.update(
            TOPPINS_SUB_GROUP_ID,
            contentValues,
            "$TOPPINS_ISQUANTITY = ?",
            arrayOf(id)
        )
        return true
    }

    fun updateDataTotalRemove(id: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TOPPINS_PRICE, "0")
        contentValues.put(TOPPINS_DESCRIPTION, "0")
        db.update(
            TOPPINS_SUB_GROUP_ID,
            contentValues,
            "$TOPPINS_PRICE = ?",
            arrayOf(id)
        )
        return true
    }

    fun deleteAllCart() {
        val db = this.writableDatabase
        db.execSQL("delete from $TOPPINS_GROUP_MENU_ID")
    }

    fun deleteAllCartTotal() {
        val db = this.writableDatabase
        db.execSQL("delete from $TOPPINS_SUB_GROUP_ID")
    }

    fun getAllCatsAdd(id: String): String {
        var contactList = UserInfo()
        // Select All Query
        val selectQuery =
            "SELECT  * FROM $TOPPINS_GROUP_MENU_ID WHERE $TOPPINS_GROUP_TYPE=$id"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val contact = UserInfo()
                contact.setUserGender(cursor.getString(1))
                // Adding contact to list
                contactList = contact
            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return contactList.userGender
    }// Adding contact to list
    // return contact list

    // Select All Query
    val allCatsAddItem: List<Any>
        get() {
            val contactList: MutableList<UserInfo> = ArrayList<UserInfo>()
            // Select All Query
            val selectQuery =
                "SELECT  * FROM $TOPPINS_GROUP_MENU_ID WHERE $TOPPINS_ID=1"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    val contact = UserInfo()
                    contact.setUserId(cursor.getString(3))
                    contact.setUserName(cursor.getString(4))
                    contact.setUserEmail(cursor.getString(2))
                    contact.setUserGender(cursor.getString(0))
                    // Adding contact to list
                    contactList.add(contact)
                } while (cursor.moveToNext())
            }
            db.close()
            // return contact list
            return contactList
        }*/

    fun getMenuCountPrice(id:String): String {
        var contactList: String = ""
        // Select All Query
        val selectQuery =
            "SELECT COUNT (*) FROM menu_info WHERE menuRestaurantId ="+id
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                contactList = cursor.getString(0)
            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return contactList
    }

    fun getGrecoryCount(id:String): String {
        var contactList: String = ""
        // Select All Query
        val selectQuery =
            "SELECT COUNT (*) FROM grocery_info WHERE groceryReferenceCode ="+"'"+id+"'"+""
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                contactList = cursor.getString(0)
            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return contactList
    }

    fun getGrecoryTotalPrice(id:String): MutableList<WorkExperience> {
        val contactList: MutableList<WorkExperience> = ArrayList<WorkExperience>()
        // Select All Query
        val selectQuery =
            "SELECT * FROM grocery_info WHERE groceryReferenceCode ="+"'"+id+"'"+""
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val contact = WorkExperience()

                contact.name = cursor.getString(2)

                contact.productName = cursor.getString(3)

                contact.productReferenceCode = cursor.getString(4)

                contact.quantity = cursor.getString(5)

                contact.price = cursor.getString(6)

                contact.totalPrice = cursor.getString(7)
                // Adding contact to list
                contactList.add(contact)
            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return contactList
    }
    fun getGrecoryTotalAll(): MutableList<WorkExperience> {
        val contactList: MutableList<WorkExperience> = ArrayList<WorkExperience>()
        // Select All Query
        val selectQuery =
            "SELECT * FROM grocery_info "
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val contact = WorkExperience()

                contact.restaurantReferenceCode = cursor.getString(1)

                contact.productName = cursor.getString(3)

                contact.productReferenceCode = cursor.getString(4)

                contact.quantity = cursor.getString(5)

                contact.price = cursor.getString(6)

                contact.totalPrice = cursor.getString(7)
                // Adding contact to list
                contactList.add(contact)
            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return contactList
    }

    fun getGrecoryTotalPrice1(id:String): String {
        var contactList: String = ""
        // Select All Query
        val selectQuery =
            "SELECT * FROM grocery_info WHERE groceryReferenceCode ="+"'"+id+"'"+""
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                contactList = cursor.getString(7)
            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return contactList
    }

    fun getGrecoryCountPrice(id:String): String {
        var contactList: String = ""
        // Select All Query
        val selectQuery =
            "SELECT COUNT (*) FROM grocery_info WHERE groceryStoreProductReferenceCode ="+"'"+id+"'"+""
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                contactList = cursor.getString(0)
            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return contactList
    }

    fun getMenuCountValue1(id:String): String {
        var contactList: String = ""
        // Select All Query
        val selectQuery =
            "SELECT COUNT (*) FROM menu_info"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                contactList = cursor.getString(0)
            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return contactList
    }


    fun getMenuCountValue(id:String): String {
        var contactList: String = ""
        // Select All Query
        val selectQuery =
            "SELECT COUNT (*) FROM menu_info WHERE menuRestaurantId ="+id
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                contactList = cursor.getString(0)
            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return contactList
    }

    fun getRestrentList(id:String):  MutableList<WorkExperience> {
        val contactList: MutableList<WorkExperience> = ArrayList<WorkExperience>()
        // Select All Query
        // Select All Query
        val selectQuery =
            "SELECT  * FROM $RESTAURANT_INFO_TABLE_NAME";

        Log.v("ppp","llll"+selectQuery)
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val contact = WorkExperience()
                contact.id = cursor.getString(0)

                contactList.add(contact)
            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return contactList
    }

    fun getRestrent(): String {
        var contactList: String = ""
        // Select All Query
        val selectQuery =
            "SELECT  * FROM $RESTAURANT_INFO_TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                contactList = cursor.getString(0)
            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return contactList
    }

    fun getMenuList(id:String): MutableList<WorkExperience> {
        val contactList: MutableList<WorkExperience> = ArrayList<WorkExperience>()
        // Select All Query
        val selectQuery =
            "SELECT  * FROM $MENU_INFO_TABLE_NAME WHERE $MENU_ID = "+id
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val contact = WorkExperience()
                contact.id = cursor.getString(0)

                contact.name = cursor.getString(3)

                contact.price = cursor.getString(2)

                contact.token = cursor.getString(4)

                contact.rating = cursor.getString(5)

                contact.restaurantId = cursor.getString(6)

                contact.totalPrice = cursor.getString(7)
                // Adding contact to list
                contactList.add(contact)
            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return contactList
    }

    fun getGroceryList(): MutableList<WorkExperience> {
        val contactList: MutableList<WorkExperience> = ArrayList<WorkExperience>()
        // Select All Query
        val selectQuery =
            "SELECT  * FROM $GROCERY_INFO_TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val contact = WorkExperience()
                contact.id = cursor.getString(0)

                contact.name = cursor.getString(3)

                contact.price = cursor.getString(2)

                contact.token = cursor.getString(4)

                contact.rating = cursor.getString(5)

                contact.restaurantId = cursor.getString(6)

                contact.totalPrice = cursor.getString(7)
                // Adding contact to list
                contactList.add(contact)
            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return contactList
    }


    fun getMenu(): MutableList<WorkExperience> {
            val contactList: MutableList<WorkExperience> = ArrayList<WorkExperience>()
            // Select All Query
            val selectQuery =
                "SELECT  * FROM $MENU_INFO_TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    val contact = WorkExperience()
                    contact.id = cursor.getString(0)

                    contact.name = cursor.getString(3)

                    contact.price = cursor.getString(2)

                    contact.token = cursor.getString(4)

                    contact.rating = cursor.getString(5)

                    contact.restaurantId = cursor.getString(6)

                    contact.blockName = cursor.getString(7)

                    contact.totalPrice = cursor.getString(8)

                    contact.offerType = cursor.getString(9)
                    // Adding contact to list

                    contactList.add(contact)
                } while (cursor.moveToNext())
            }
            db.close()
            // return contact list
            return contactList
        }
    fun getMenuGroupToppind(id:String): MutableList<WorkExperience> {
        val contactList: MutableList<WorkExperience> = ArrayList<WorkExperience>()
        // Select All Query
        val selectQuery =
            "SELECT  * FROM $TOPPINS_INFO_TABLE_NAME WHERE $TOPPINS_SUB_GROUP_ID ="+id
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val contact = WorkExperience()

                contact.token = cursor.getString(3)
                contact.user_id = cursor.getString(8)
                // Adding contact to list
                contactList.add(contact)
            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return contactList
    }

    fun getMenuGroupToppinsCheck(id:String): String {
        var toppinsCheck: String = ""
        // Select All Query
        val selectQuery =
            "SELECT  * FROM $TOPPINS_INFO_TABLE_NAME WHERE $TOPPINS_ID ="+id
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {


                toppinsCheck = cursor.getString(0)

                // Adding contact to list

            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return toppinsCheck
    }
    fun toppinsDelete(id:String) {
        val db = this.writableDatabase
        db.execSQL("delete from " + DBHelper.TOPPINS_INFO_TABLE_NAME +" where "+ TOPPINS_MIN_MENU_ID + " = "+"'"+id+"'"+"")
    }

    fun updateMenuTotal(id: String?,price: String): Boolean {
        val mDb = this.writableDatabase
        val values = ContentValues()
        values.put(MENU_PRICE_TOTAL, price)

        return mDb.update(MENU_INFO_TABLE_NAME, values, MENU_ID.toString() + " = "+"'"+id+"'"+"", null) > 0
    }
    fun getMenuRestaurant(id:String): String {
        var toppinsCheck: String = ""
        // Select All Query
        val selectQuery =
            "SELECT  * FROM $MENU_INFO_TABLE_NAME WHERE $MENU_ID ="+id
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                toppinsCheck = cursor.getString(6)

                // Adding contact to list

            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return toppinsCheck
    }

    fun getMenuPrice(id:String): String {
        var toppinsCheck: String = ""
        // Select All Query
        val selectQuery =
            "SELECT  * FROM $MENU_INFO_TABLE_NAME WHERE $MENU_ID ="+id
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                toppinsCheck = cursor.getString(2)

                // Adding contact to list

            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return toppinsCheck
    }

    fun getMenuToppinsPrice(id:String): String {
        var toppinsCheck: String = ""
        // Select All Query
        val selectQuery =
            "SELECT  * FROM $TOPPINS_INFO_TABLE_NAME WHERE $TOPPINS_MIN_MENU_ID ="+id
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                toppinsCheck = cursor.getString(7)

                // Adding contact to list

            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return toppinsCheck
    }
    fun getMenuGroup(id:String): MutableList<WorkExperience> {
        val contactList: MutableList<WorkExperience> = ArrayList<WorkExperience>()
        // Select All Query
        val selectQuery =
            "SELECT  * FROM $TOPPINS_GROUP_INFO_TABLE_NAME WHERE $TOPPINS_GROUP_MENU_ID ="+id
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val contact = WorkExperience()

                contact.id = cursor.getString(0)

                contact.token = cursor.getString(2)
                // Adding contact to list
                contactList.add(contact)
            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return contactList
    }

    fun getMenu_toppins(id:String): MutableList<WorkExperience> {
        val contactList: MutableList<WorkExperience> = ArrayList<WorkExperience>()
        // Select All Query
        val selectQuery =
            "SELECT toppins_info.toppinsName,toppins_info.toppinsPrice FROM menu_info INNER JOIN toppins_group_info ON menu_info.menu_id = toppins_group_info.toppinsGroupMenuId INNER JOIN toppins_info ON toppins_group_info.toppinsGroupId = toppins_info.toppinsGroupId WHERE menu_info.menu_id ="+id;
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                val contact = WorkExperience()
                contact.blockName = cursor.getString(0)

                contact.price = cursor.getString(1)

                // Adding contact to list
                contactList.add(contact)
            } while (cursor.moveToNext())
        }
        db.close()
        // return contact list
        return contactList
    }



    companion object {
        private const val DATABASE_VERSION = 4
        const val DATABASE_NAME = "Lieferin.db"
        const val ITEM_INFO_TABLE_NAME = "item_info"
        const val CATEGORY_COLUMN_ID = "category_id"
        const val CATEGORY_COLUMN_NAME = "category_name"
        const val CATEGORY_RESTAURANT_ID = "category_restaurantId"
        const val CATEGORY_REFERENCE_CODE = "category_reference_code"
        const val MENU_INFO_TABLE_NAME = "menu_info"
        const val MENU_ID = "menu_id"
        const val MENU_PRICE_TOTAL = "menu_price_total"
        const val MENU_QUANTITY = "menu_quantity"
        const val MENU_NAME = "menu_name"
        const val MENU_REFERENCE_CODE = "menu_reference_code"
        const val MENU_PRICE = "menu_price"
        const val MENU_CATEGORY_ID = "menu_category_id"
        const val MENU_RESTAURANT_ID = "menuRestaurantId"
        const val MENU_NOTES_ID = "menuNotesId"
        const val MENU_INFO_OFFER_TYPE = "menu_info_offer_type"
        const val TOPPINS_GROUP_INFO_TABLE_NAME = "toppins_group_info"
        const val TOPPINS_GROUP_ID = "toppinsGroupId"
        const val TOPPINS_GROUP_NAME = "toppinsGroupName"
        const val TOPPINS_GROUP_RESTAURANT_ID = "toppinsGroupRestaurantId"
        const val TOPPINS_GROUP_REFERENCE_CODE = "toppinGroupReferenceCode"
        const val TOPPINS_GROUP_MENU_ID = "toppinsGroupMenuId"
        const val TOPPINS_GROUP_TYPE = "toppinType"
        const val TOPPINS_INFO_TABLE_NAME = "toppins_info"
        const val TOPPINS_ID = "toppinsId"
        const val TOPPINS_NAME = "toppinsName"
        const val TOPPINS_REFERENCE_CODE = "toppinsReferenceCode"
        const val TOPPINS_MENU_ID = "toppinsMenuId"
        const val TOPPINS_RESTAURANT_ID = "toppinsRestaurantId"
        const val TOPPINS_SUB_GROUP_ID = "toppinsGroupId"
        const val TOPPINS_ISQUANTITY = "isQuantity"
        const val TOPPINS_PRICE = "toppinsPrice"
        const val TOPPINS_DESCRIPTION = "description"
        const val TOPPINS_MIN_MENU_ID = "toppinsMinMenuId"
        const val RESTAURANT_INFO_TABLE_NAME = "restaurant_info"
        const val RESTAURANT_ID = "restaurantId"
        const val RESTAURANT_REFERENCE_CODE = "restaurantReferenceCode"
        const val RESTAURANT_NAME = "restaurantName"
        const val RESTAURANT_Address = "restaurantAddress"
        const val GROCERY_INFO_TABLE_NAME = "grocery_info"
        const val GROCERY_ID = "groceryId"
        const val GROCERY_STORE_REFERENCE_CODE = "groceryReferenceCode"
        const val GROCERY_STORE_NAME = "groceryStoreName"
        const val GROCERY_STORE_PRODUCT = "groceryStoreProduct"
        const val GROCERY_STORE_PRODUCT_REFERENCE_CODE = "groceryStoreProductReferenceCode"
        const val GROCERY_STORE_PRODUCT_QUALITY = "groceryStoreQuality"
        const val GROCERY_STORE_PRODUCT_PRICE = "groceryStorePrice"
        const val GROCERY_STORE_PRODUCT_TOTAL_PRICE = "groceryStoreTotalPrice"

        const val USER_INFO_TABLE_NAME = "user_info_table"
        const val USER_ID = "userId"
        const val USER_TOKEN_CODE = "token"
        const val USER_NAME = "userName"
        const val USER_ADDRESS = "userAddress"
        const val USER_EMAIL = "userEmail"
        const val USER_PHONE = "userPhone"
        const val USER_PROFILE_IMG = "userProfileImage"
        const val USER_LATITUDE = "userLatitude"
        const val USER_LONGTITUDE = "userLongtitude"

        const val LOCATION_TABLE_NAME = "location_table_name"
        const val LOCATION_LATITUDE = "location_latitude"
        const val LOCATION_LONGTITUDE = "location_longtitude"
        const val LOCATION_ADDRESS = "location_address"

    }
}