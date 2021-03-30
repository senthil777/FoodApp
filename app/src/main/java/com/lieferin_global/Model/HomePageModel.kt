package com.lieferin_global.Model

class HomePageModel(var type: Int, adapterModel: List<AdapterModel>, viewType: Int) {
    var viewType: Int
    var adapterModel: List<AdapterModel>

    companion object {
        const val CATEGORY = 0
        const val BANNER = 1
        const val STORE = 2
        const val OFFER = 3
        const val SPECIAL_OFFER = 4
        const val STORE_PRODUCT = 5
        const val STORE_PRODUCT_SUB = 6
    }

    init {
        this.adapterModel = adapterModel
        this.viewType = viewType
    }
}