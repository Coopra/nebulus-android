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
import com.coopra.nebulus.BuildConfig
import com.coopra.nebulus.TokenHandler
import com.coopra.nebulus.databinding.FragmentLoginBinding
import com.coopra.nebulus.views.activities.HomeActivity

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val tokenHandler = TokenHandler()
    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webPage.loadUrl(
                "https://soundcloud.com/connect?client_id=${BuildConfig.CLIENT_ID}&redirect_uri=http://www.bing.com&response_type=token&scope=non-expiring&display=popup")

        binding.webPage.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                if (url?.contains("bing.com/#access_token") == true) {
                    binding.webPage.stopLoading()
                    saveToken(url)
                }

                super.onPageStarted(view, url, favicon)
            }
        }

        val webSettings = binding.webPage.settings
        webSettings.javaScriptEnabled = true
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun saveToken(url: String) {
        val code = url.substring(url.indexOf("=") + 1)
        val token = code.split("&")[0]
        tokenHandler.saveToken(activity?.applicationContext ?: return, token)
        finishLogin()
    }

    private fun finishLogin() {
        val intent = Intent(context, HomeActivity::class.java)
        requireActivity().finish()
        startActivity(intent)
    }
}