package com.example.socialgift2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.socialgift2.R;
import com.example.socialgift2.controllers.UserController;
import com.example.socialgift2.requests.SocialGiftAPI;

public class SignInActivity extends AppCompatActivity {
    EditText emailEditText, passwordEditText;
    Button loginButton, registerButton;
    UserController userController;
    String userEmail, userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userController = new UserController(this, this);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.button);
        registerButton = findViewById(R.id.button2);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("userEmail "+userEmail);
                System.out.println("userPass "+userPass);
                userController.loginUser(userEmail, userPass);
            }
        });

        emailEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                userEmail = String.valueOf(s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        passwordEditText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                userPass = String.valueOf(s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

}