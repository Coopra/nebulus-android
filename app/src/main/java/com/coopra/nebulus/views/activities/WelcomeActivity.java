package com.coopra.nebulus.views.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;

import com.coopra.nebulus.R;
import com.coopra.nebulus.TokenHandler;
import com.coopra.nebulus.views.fragments.WelcomeFragment;

public class WelcomeActivity extends AppCompatActivity {
    private TokenHandler mTokenHandler = new TokenHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkForExistingToken();

        setContentView(R.layout.activity_welcome);

        Fragment welcomeFragment = new WelcomeFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, welcomeFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void checkForExistingToken() {
        if (!TextUtils.isEmpty(mTokenHandler.getToken(getApplicationContext()))) {
            Intent intent = new Intent(this, HomeActivity.class);
            finish();
            startActivity(intent);
        }
    }
}
