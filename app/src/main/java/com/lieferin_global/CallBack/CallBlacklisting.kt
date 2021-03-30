package com.rentaloo.CallBack

import android.os.Bundle
import android.view.View

interface CallBlacklisting {
    fun fragmentChange(fragment: String?, bundle: Bundle?)
    fun fragmentChangeView(fragment: String?, bundle: Bundle?)
    fun fragmentBack(fragment: String?)
}