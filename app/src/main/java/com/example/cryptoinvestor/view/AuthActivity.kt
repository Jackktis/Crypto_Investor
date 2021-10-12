package com.example.cryptoinvestor.view
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptoinvestor.R
import android.graphics.Color
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_auth.*


class AuthActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        signin.setOnClickListener {
            signin.setTextColor(Color.parseColor("#FFFFFF"))
            signin.setBackgroundColor(this.getColor(R.color.bannerGold))
            signup.setTextColor(this.getColor(R.color.bannerGold))
            signup.setBackgroundResource(R.drawable.border_shape)
            circleImageView.setImageResource(R.drawable.ic_face_foreground)
            signin_signup_txt.text = "Log in"
            signin_signup_btn.text = "Login"
            forgot_password.visibility = View.VISIBLE
        }
        signup.setOnClickListener {
            signup.setTextColor(Color.parseColor("#FFFFFF"))
            signup.setBackgroundColor(this.getColor(R.color.bannerGold))
            signin.setTextColor(this.getColor(R.color.bannerGold))
            signin.setBackgroundResource(R.drawable.border_shape)
            circleImageView.setImageResource(R.drawable.ic_face_foreground)
            signin_signup_txt.text = "Sign Up"
            signin_signup_btn.text = "CREATE"
            forgot_password.visibility = View.INVISIBLE
        }
    }
}