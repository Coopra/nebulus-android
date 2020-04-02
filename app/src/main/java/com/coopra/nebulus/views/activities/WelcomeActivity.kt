package com.coopra.nebulus.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.coopra.nebulus.R
import com.coopra.nebulus.TokenHandler
import com.coopra.nebulus.views.fragments.WelcomeFragment

class WelcomeActivity : AppCompatActivity() {
    private val tokenHandler = TokenHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkForExistingToken()

        setContentView(R.layout.activity_welcome)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, WelcomeFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
        }
    }

    private fun checkForExistingToken() {
        if (!tokenHandler.getToken(applicationContext).isNullOrEmpty()) {
            val intent = Intent(this, HomeActivity::class.java)
            finish()
            startActivity(intent)
        }
    }
}