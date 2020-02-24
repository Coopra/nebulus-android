package com.coopra.nebulus.views.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.coopra.nebulus.R
import com.coopra.nebulus.TokenHandler
import com.coopra.nebulus.views.activities.HomeActivity

class LoginFragment : Fragment() {
    private val clientId = "db12996a6c897c97f8ce2df569d3f8dc"
    private val tokenHandler = TokenHandler()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webPage = view.findViewById<WebView>(R.id.web_page)
        webPage.loadUrl(
                "https://soundcloud.com/connect?client_id=$clientId&redirect_uri=http://www.bing.com&response_type=token&scope=non-expiring&display=popup")

        webPage.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                if (url?.contains("bing.com/#access_token") == true) {
                    webPage.stopLoading()
                    saveToken(url)
                }

                super.onPageStarted(view, url, favicon)
            }
        }

        val webSettings = webPage.settings
        webSettings.javaScriptEnabled = true
    }

    private fun saveToken(url: String) {
        val code = url.substring(url.indexOf("=") + 1)
        val token = code.split("&")[0]
        tokenHandler.saveToken(activity?.applicationContext ?: return, token)
        finishLogin()
    }

    private fun finishLogin() {
        val intent = Intent(context, HomeActivity::class.java)
        activity?.finish() ?: return
        startActivity(intent)
    }
}