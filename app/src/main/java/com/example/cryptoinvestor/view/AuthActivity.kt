package com.example.cryptoinvestor.view
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptoinvestor.R
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.cryptoinvestor.databinding.ActivityAuthBinding
import com.example.cryptoinvestor.viewmodel.AuthViewModel
import com.example.cryptoinvestor.viewmodel.AuthViewModelFactory
import kotlinx.android.synthetic.main.activity_auth.*


class AuthActivity : AppCompatActivity(){

    private val viewModel: AuthViewModel by viewModels{
        AuthViewModelFactory(application)
    }

    private lateinit var binding: ActivityAuthBinding

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
            signin_signup_btn.setOnClickListener{
                logIn(it)
            }
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

            signin_signup_btn.setOnClickListener{
                register(it)
            }
        }
    }

    fun register(view : View){
        val email = emailAddressEditText.text.toString()
        val password = password.text.toString()

        viewModel.signUp(email, password).addOnCompleteListener{ task ->
            if(task.isSuccessful){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener{ exception ->
            Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    fun logIn(view: View){
        val email = emailAddressEditText.text.toString()
        val password = password.text.toString()

        viewModel.signIn(email, password).addOnCompleteListener{ task ->
            if(task.isSuccessful){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener{ exception ->
            Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }
}