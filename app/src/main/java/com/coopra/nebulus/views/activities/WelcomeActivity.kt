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

        val welcomeFragment = WelcomeFragment()

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, welcomeFragment)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun checkForExistingToken() {
        if (!tokenHandler.getToken(applicationContext).isNullOrEmpty()) {
            val intent = Intent(this, HomeActivity::class.java)
            finish()
            startActivity(intent)
        }
    }
}