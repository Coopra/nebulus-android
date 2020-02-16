package com.coopra.nebulus.views.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.coopra.nebulus.views.activities.HomeActivity;
import com.coopra.nebulus.R;
import com.coopra.nebulus.TokenHandler;

public class LoginFragment extends Fragment {
    private static final String clientId = "db12996a6c897c97f8ce2df569d3f8dc";
    private TokenHandler mTokenHandler = new TokenHandler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final WebView webPage = view.findViewById(R.id.web_page);
        webPage.loadUrl("https://soundcloud.com/connect?client_id=" + clientId + "&redirect_uri=http://www.bing.com&response_type=token&scope=non-expiring&display=popup");

        webPage.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (url.contains("bing.com/#access_token")) {
                    webPage.stopLoading();
                    saveToken(url);
                }

                super.onPageStarted(view, url, favicon);
            }
        });

        WebSettings webSettings = webPage.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    private void saveToken(String url) {
        String code = url.substring(url.indexOf("=") + 1);
        String token = code.split("&")[0];
        mTokenHandler.saveToken(getActivity().getApplicationContext(), token);
        finishLogin();
    }

    private void finishLogin() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }

        Intent intent = new Intent(getContext(), HomeActivity.class);
        activity.finish();
        startActivity(intent);
    }
}
