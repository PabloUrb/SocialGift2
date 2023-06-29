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
import com.example.socialgift2.objects.User;

public class SignUpActivity extends AppCompatActivity {
    UserController userController;
    EditText name, lastName, email, password;
    String userName, userLastName, userEmail, userPassword;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userController = new UserController(this, this);

        name = findViewById(R.id.name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("userEmail "+userName);
                System.out.println("userPass "+userLastName);
                System.out.println("email "+userEmail);
                System.out.println("password "+userPassword);
                User u = new User(userName,userLastName,userPassword,userEmail,"https://balandrau.salle.url.edu/i3/repositoryimages/photo/47601a8b-dc7f-41a2-a53b-19d2e8f54cd0.png");
                /*u.setName(userName);
                u.setLastName(userLastName);
                u.setEmail(userEmail);
                u.setPassword(userPassword);*/
                userController.createUser(u);
            }
        });

        name.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                userName = String.valueOf(s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        lastName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                userLastName = String.valueOf(s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        email.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                userEmail = String.valueOf(s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        password.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                userPassword = String.valueOf(s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }
}