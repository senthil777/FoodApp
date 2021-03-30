package com.lieferin_global.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.lieferin_global.R


class PrivayPolicyFragment : Fragment() {
    var rootView : View? = null

    var webView : WebView? = null

    var pageString: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_privay_polocy, container, false)

        val bundle = this.arguments
        if (bundle != null) {
            pageString= ""+ bundle.getString("page")
        }

        val webView = rootView!!.findViewById<WebView>(R.id.pageView)
        webView.webViewClient = WebViewClient()
        webView.loadUrl(pageString)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.setDomStorageEnabled(true);

        return rootView;
    }


}