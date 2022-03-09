package com.printf.shuttle_tracker.View.SignUp

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import android.content.Intent
import com.printf.shuttle_tracker.View.Login.LoginActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import android.widget.Toast
import com.printf.shuttle_tracker.R
import com.printf.shuttle_tracker.View.Map.MapsActivity

class SignUpActivity : AppCompatActivity() {
    private var email: EditText? = null
    private var password: EditText? = null
    private var signUpButton: Button? = null
    private var LoginInTextView: TextView? = null
    private var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        email = findViewById(R.id.SignUpemailAddress)
        password = findViewById(R.id.SignUppasswordAddress)
        signUpButton = findViewById(R.id.signUpButton)
        LoginInTextView = findViewById(R.id.LoginInTextView)
        auth = FirebaseAuth.getInstance()

        //TODO: Initialize the widgets
    }

    override fun onStart() {
        super.onStart()
        signUpButton!!.setOnClickListener { v: View? -> createUser() }
        LoginInTextView!!.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )
        }
    }

    fun createUser() {
        val emailAddress = email!!.text.toString()
        val passwordAddress = password!!.text.toString()
        if (TextUtils.isEmpty(emailAddress)) {
            email!!.error = "Email Cannot be empty"
            email!!.requestFocus()
        } else if (TextUtils.isEmpty(passwordAddress)) {
            password!!.error = "Email Cannot be empty"
            password!!.requestFocus()
        } else {
            auth!!.createUserWithEmailAndPassword(emailAddress, passwordAddress)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@SignUpActivity, "SignUp successful", Toast.LENGTH_LONG)
                            .show()
                        startActivity(Intent(this@SignUpActivity, MapsActivity::class.java))
                    }
                }
        }
    }
}