package com.lieferin_global.Activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.lieferin_global.R
import com.flipboard.bottomsheet.BottomSheetLayout


class BottomSheetFragmentActivity : AppCompatActivity() {

    var bottomSheetLayout: BottomSheetLayout? = null

    var bottomsheet_fragment_button : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet)
        bottomSheetLayout = findViewById(R.id.bottomsheet) as BottomSheetLayout

        bottomsheet_fragment_button = findViewById(R.id.bottomsheet_fragment_button) as Button

        bottomsheet_fragment_button!!.setOnClickListener(View.OnClickListener {

        })

    }

}
