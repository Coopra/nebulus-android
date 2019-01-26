package com.coopra.nebulus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

public class WelcomeActivity extends AppCompatActivity {
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
        if (!TextUtils.isEmpty(TokenHandler.getToken(this))) {
            Intent intent = new Intent(this, HomeActivity.class);
            finish();
            startActivity(intent);
        }
    }
}
