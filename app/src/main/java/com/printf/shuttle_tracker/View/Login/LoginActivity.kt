package com.printf.shuttle_tracker.View.Login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.printf.shuttle_tracker.View.SignUp.SignUpActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.printf.shuttle_tracker.R
import com.printf.shuttle_tracker.View.Map.MapsActivity

class LoginActivity : AppCompatActivity() {
    private var email: EditText? = null
    private var password: EditText? = null
    private var loginButton: Button? = null
    private var SignUpTextView: TextView? = null
    private var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        email = findViewById(R.id.SignInemailAddress)
        password = findViewById(R.id.SignInpasswordAddress)
        loginButton = findViewById(R.id.signInButton)
        SignUpTextView = findViewById(R.id.SignUpTextView)
        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        loginButton!!.setOnClickListener { v: View? -> signInUser() }
        SignUpTextView!!.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    this,
                    SignUpActivity::class.java
                )
            )
        }
    }

    fun signInUser() {
        val emailAddress = email!!.text.toString()
        val passwordAddress = password!!.text.toString()
        if (TextUtils.isEmpty(emailAddress)) {
            email!!.error = "Email Cannot be empty"
            email!!.requestFocus()
        } else if (TextUtils.isEmpty(passwordAddress)) {
            password!!.error = "Email Cannot be empty"
            password!!.requestFocus()
        } else {
            auth!!.signInWithEmailAndPassword(emailAddress, passwordAddress)
                .addOnCompleteListener { p0 ->
                    if (p0.isSuccessful) {
                        Toast.makeText(
                            this@LoginActivity,
                            "SignUp successful",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(Intent(this@LoginActivity, MapsActivity::class.java))
                    }
                }
        }
    }
}