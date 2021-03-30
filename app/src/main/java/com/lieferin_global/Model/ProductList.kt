package com.lieferin_global.Model

import java.io.Serializable

class ProductList(var imageIcon: Int, var name: String, var toppinType: String, var toppinsGroupId: String, var menuId: String, var toppinGroupReferenceCode: String, var distance: String, var offerPrice: String, var typeView: String, var toppinsList: MutableList<ProductListView>) : Serializable