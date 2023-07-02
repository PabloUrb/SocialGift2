package com.example.socialgift2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.socialgift2.MainActivity;
import com.example.socialgift2.R;
import com.example.socialgift2.controllers.UserController;
import com.example.socialgift2.objects.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditMyUserActivity extends AppCompatActivity {
    EditText name, lastname, imageurl;
    String userName, userLastName, userImageUrl;
    Button save;
    UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        name = findViewById(R.id.name);
        lastname = findViewById(R.id.lastname);
        imageurl = findViewById(R.id.imageurl);

        save = (Button) findViewById(R.id.save);
        userController = new UserController(this, getApplicationContext());

        name.setText(Session.user.getName());
        lastname.setText(Session.user.getLast_name());
        imageurl.setText(Session.user.getImage());

        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                List<String> lst = new ArrayList<>();
                System.out.println("name :: "+name.getText());
                System.out.println("lastname :: "+lastname.getText());
                System.out.println("imageurl :: "+imageurl.getText());

                lst.add(userName);
                lst.add(userLastName);
                lst.add(userImageUrl);

                userController.updateUser(lst);

            }
        });
        name.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                userName = String.valueOf(s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        lastname.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                userLastName = String.valueOf(s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        imageurl.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                userImageUrl = String.valueOf(s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return true;
    }
}