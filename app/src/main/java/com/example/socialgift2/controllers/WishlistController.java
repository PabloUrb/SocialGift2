package com.example.socialgift2.controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.socialgift2.MainActivity;
import com.example.socialgift2.activities.AddProductToWishlistActivity;
import com.example.socialgift2.activities.EditWishlistActivity;
import com.example.socialgift2.activities.NewWishlistActivity;
import com.example.socialgift2.activities.WishlistActivity;
import com.example.socialgift2.objects.Wishlist;
import com.example.socialgift2.requests.Callbacks;
import com.example.socialgift2.requests.SocialGiftAPI;

import java.util.ArrayList;
import java.util.List;

public class WishlistController {
    private WishlistActivity activity;
    private NewWishlistActivity newWishlistActivity;
    private EditWishlistActivity editWishlistActivity;
    private AddProductToWishlistActivity addProductToWishlistActivity;
    private Context context;
    private int currentPage = 0;
    private List<Wishlist> loadedWishlist = new ArrayList<>();

    public WishlistController (Context context, WishlistActivity activity){
        this.activity = activity;
        this.context = context;
    }
    public WishlistController (NewWishlistActivity newWishlistActivity, Context context) {
        this.newWishlistActivity = newWishlistActivity;
        this.context = context;
    }
    public WishlistController (EditWishlistActivity editWishlistActivity, Context context){
        this.editWishlistActivity = editWishlistActivity;
        this.context = context;
    }
    public WishlistController (AddProductToWishlistActivity addProductToWishlistActivity, Context context){
        this.addProductToWishlistActivity = addProductToWishlistActivity;
        this.context = context;
    }
    public void cargarWishlist() {
        SocialGiftAPI.myWishlists(context, new Callbacks.UserCallbackWishlists<Wishlist>() {
            @Override
            public void onSuccess(List<Wishlist> wishlists) {
                System.out.println("wishlists.size() :: "+wishlists.size());
                activity.showWishlists(wishlists);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("No hay wishlists", errorMessage);
            }
        });
    }
    public void cargarWishlist2() {
        SocialGiftAPI.myWishlists(context, new Callbacks.UserCallbackWishlists<Wishlist>() {
            @Override
            public void onSuccess(List<Wishlist> wishlists) {
                System.out.println("wishlists.size() :: "+wishlists.size());
                addProductToWishlistActivity.showWishlists(wishlists);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("No hay wishlists", errorMessage);
            }
        });
    }

    public void NewWishlist(Wishlist wishlist) {
        SocialGiftAPI.createWishlist(wishlist, context, new Callbacks.UserCallback() {
            @Override
            public void onSuccess() {
                Log.d("No hay wishlists", "Wishlist creada");
                Toast.makeText(context, "Tu wishlist ha sido creada",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("No hay wishlists", errorMessage);
            }
        });
    }

    public void editWishlist(Wishlist wishlist) {
        SocialGiftAPI.editWishlist(wishlist, context, new Callbacks.UserCallback() {
            @Override
            public void onSuccess() {
                Log.d("No hay wishlists", "Wishlist editada");
                Toast.makeText(context, "Tu wishlist ha sido modificada",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, WishlistActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("No hay wishlists", errorMessage);
            }
        });
    }
}
