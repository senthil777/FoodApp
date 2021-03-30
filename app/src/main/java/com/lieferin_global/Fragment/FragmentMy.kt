package com.lieferin_global.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.lieferin_global.R
import com.flipboard.bottomsheet.commons.BottomSheetFragment


class FragmentMy :  BottomSheetFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_my, container, false)


    }



}
