package com.printf.shuttle_tracker.View.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.printf.shuttle_tracker.R;
import com.printf.shuttle_tracker.View.Map.MapsActivity;
import com.printf.shuttle_tracker.View.SignUp.SignUpActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button loginButton;
    private TextView SignUpTextView;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.SignInemailAddress);
        password = findViewById(R.id.SignInpasswordAddress);
        loginButton = findViewById(R.id.signInButton);
        SignUpTextView = findViewById(R.id.SignUpTextView);

        auth = FirebaseAuth.getInstance();

        //TODO: Initialize the widgets

        loginButton.setOnClickListener(v -> {
            signInUser();
        });


        SignUpTextView.setOnClickListener(v -> {

            startActivity(new Intent(this, SignUpActivity.class));
        });


    }
    public void signInUser(){
        String emailAddress = email.getText().toString();
        String passwordAddress = password.getText().toString();

        if (TextUtils.isEmpty(emailAddress)){
            email.setError("Email Cannot be empty");
            email.requestFocus();
        } else if (TextUtils.isEmpty(passwordAddress)){
            password.setError("Email Cannot be empty");
            password.requestFocus();
        } else {
            auth.signInWithEmailAndPassword(emailAddress, passwordAddress).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "SignUp successful", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this, MapsActivity.class));
                    }
                }
            });
        }

    }


}