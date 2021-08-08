package com.printf.shuttle_tracker.View.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.printf.shuttle_tracker.R;
import com.printf.shuttle_tracker.View.Login.LoginActivity;
import com.printf.shuttle_tracker.View.Map.MapsActivity;

public class SignUpActivity extends AppCompatActivity {


    private EditText email;
    private EditText password;
    private Button signUpButton;
    private TextView LoginInTextView;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.SignUpemailAddress);
        password = findViewById(R.id.SignUppasswordAddress);
        signUpButton = findViewById(R.id.signUpButton);
        LoginInTextView = findViewById(R.id.LoginInTextView);

        auth = FirebaseAuth.getInstance();

        //TODO: Initialize the widgets

        signUpButton.setOnClickListener(v -> {
            createUser();
        });


        LoginInTextView.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });


    }
    public void createUser(){
        String emailAddress = email.getText().toString();
        String passwordAddress = password.getText().toString();

        if (TextUtils.isEmpty(emailAddress)){
            email.setError("Email Cannot be empty");
            email.requestFocus();
        } else if (TextUtils.isEmpty(passwordAddress)){
            password.setError("Email Cannot be empty");
            password.requestFocus();
        } else {
            auth.createUserWithEmailAndPassword(emailAddress, passwordAddress).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignUpActivity.this, "SignUp successful", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignUpActivity.this, MapsActivity.class));
                    }
                }
            });
        }

    }



}