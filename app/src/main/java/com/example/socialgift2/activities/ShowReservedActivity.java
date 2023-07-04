package com.example.socialgift2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.socialgift2.MainActivity;
import com.example.socialgift2.R;
import com.example.socialgift2.controllers.UserController;
import com.example.socialgift2.fragments.ShowReservedFragment;

public class ShowReservedActivity extends AppCompatActivity {
    private UserController usersController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reserved);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ShowReservedFragment showReservedFragment = new ShowReservedFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_show_reserved, showReservedFragment)
                .commit();
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