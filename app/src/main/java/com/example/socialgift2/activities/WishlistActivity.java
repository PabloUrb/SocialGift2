package com.example.socialgift2.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift2.MainActivity;
import com.example.socialgift2.R;
import com.example.socialgift2.adapters.WishlistAdapter;
import com.example.socialgift2.controllers.WishlistController;
import com.example.socialgift2.objects.Wishlist;
import com.example.socialgift2.requests.Callbacks;

import java.util.List;


public class WishlistActivity extends AppCompatActivity implements Callbacks {
    private Wishlist wishlist;
    private RecyclerView recyclerView;
    private WishlistAdapter wishlistAdapter;
    private WishlistController wishlistController;
    private Button createNewWishlits;
    private com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        bottomNavigationView = findViewById(R.id.bottom_navigationbar);
        bottomNavigationView.getMenu().clear();
        bottomNavigationView.inflateMenu(R.menu.menu);
        bottomNavigationView.setSelectedItemId(R.id.ic_basket);

        createNewWishlits= findViewById(R.id.createWishlistButton);

        wishlistController = new WishlistController(this, this);

        recyclerView = findViewById(R.id.recyclerViewWishlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wishlistAdapter = new WishlistAdapter(wishlistController);
        wishlistAdapter.setOnItemClickListener(this::onItemClick);
        recyclerView.setAdapter(wishlistAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        wishlistController.cargarWishlist();

        createNewWishlits.setOnClickListener(view -> {
            onCreateClick(wishlist, createNewWishlits);
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.ic_home:
                    startActivity(new Intent(this, MainActivity.class));
                    break;
                case R.id.ic_user:
                    startActivity(new Intent(this, ShowMyProfileActivity.class));
                    break;
                case R.id.ic_basket:
                    break;
            }
            return false;
        });
    }

    public void showWishlists(List<Wishlist> wishlists) {
        wishlistAdapter.setWishlists(wishlists);
        wishlistAdapter.notifyDataSetChanged();
    }

    public void onItemClick(Wishlist wishlist) {
        Log.d("WishlistActivity", "ID (wishlist): " + wishlist.getId());
    }

    public void onCreateClick(Wishlist wishlist, Button createNewWishlits) {
        Intent intent = new Intent(this, NewWishlistActivity.class);
        startActivity(intent);
    }

}