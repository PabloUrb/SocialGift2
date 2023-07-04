package com.example.socialgift2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.socialgift2.MainActivity;
import com.example.socialgift2.R;
import com.example.socialgift2.adapters.AddProductToWishlistAdapter;
import com.example.socialgift2.adapters.WishlistAdapter;
import com.example.socialgift2.controllers.WishlistController;
import com.example.socialgift2.objects.Wishlist;

import java.util.List;

public class AddProductToWishlistActivity extends AppCompatActivity {
    private Wishlist wishlist;
    private RecyclerView recyclerView;
    private AddProductToWishlistAdapter addProductToWishlistAdapter;
    private WishlistController wishlistController;
    private Button createNewWishlits;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_to_wishlist);

        createNewWishlits= findViewById(R.id.createWishlistButton);

        wishlistController = new WishlistController(this, this);

        recyclerView = findViewById(R.id.recyclerViewWishlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addProductToWishlistAdapter = new AddProductToWishlistAdapter(wishlistController);
        addProductToWishlistAdapter.setOnItemClickListener(this::onItemClick);
        recyclerView.setAdapter(addProductToWishlistAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        wishlistController.cargarWishlist2();

        createNewWishlits.setOnClickListener(view -> {
            onCreateClick(wishlist, createNewWishlits);
        });
    }

    public void showWishlists(List<Wishlist> wishlists) {
        addProductToWishlistAdapter.setWishlists(wishlists);
        addProductToWishlistAdapter.notifyDataSetChanged();
    }

    public void onItemClick(Wishlist wishlist) {
        Log.d("WishlistActivity", "ID (wishlist): " + wishlist.getId());
    }

    public void onCreateClick(Wishlist wishlist, Button createNewWishlits) {
        Intent intent = new Intent(this, NewWishlistActivity.class);
        startActivity(intent);
    }
}