package com.example.socialgift2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.socialgift2.activities.ShowMyProfileActivity;
import com.example.socialgift2.activities.ShowUserActivity;
import com.example.socialgift2.activities.WishlistActivity;
import com.example.socialgift2.fragments.SearchFragment;
import com.example.socialgift2.objects.Session;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigationbar);
        bottomNavigationView.getMenu().clear();
        bottomNavigationView.inflateMenu(R.menu.menu);

        System.out.println("2 Session.getuserId() :: "+Session.user.getId());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.ic_home:
                    startActivity(new Intent(this, MainActivity.class));
                    break;
                case R.id.ic_user:
                    startActivity(new Intent(this, ShowMyProfileActivity.class));
                    break;
                case R.id.ic_basket:
                    startActivity(new Intent(this, WishlistActivity.class));
                    break;
            }
            return false;
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_searchBar, SearchFragment.class, null)
                    .commit();
        }

    }
}