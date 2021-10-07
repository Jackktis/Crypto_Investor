package com.example.cryptoinvestor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils.replace
import android.view.WindowManager
import android.widget.Button
import androidx.fragment.app.Fragment

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val loginButton = findViewById<Button>(R.id.BTLogin)
        loginButton.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val signUpButton = findViewById<Button>(R.id.BTSignUp)
        signUpButton.setOnClickListener() {

        }
    }

}