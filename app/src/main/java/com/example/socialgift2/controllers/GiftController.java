package com.example.socialgift2.controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.socialgift2.MainActivity;
import com.example.socialgift2.activities.WishlistActivity;
import com.example.socialgift2.adapters.AddProductToWishlistAdapter;
import com.example.socialgift2.adapters.ListGiftsAdapter;
import com.example.socialgift2.objects.Gift;
import com.example.socialgift2.objects.Wishlist;
import com.example.socialgift2.requests.Callbacks;
import com.example.socialgift2.requests.SocialGiftAPI;

public class GiftController {
    private ListGiftsAdapter listGiftsAdapter;
    private static Context context;
    private AddProductToWishlistAdapter addProductToWishlistAdapter;

    public GiftController (ListGiftsAdapter listGiftsAdapter, Context context){
        this.listGiftsAdapter = listGiftsAdapter;
        this.context = context;
    }
    public GiftController (AddProductToWishlistAdapter addProductToWishlistAdapter, Context context){
        this.addProductToWishlistAdapter = addProductToWishlistAdapter;
        this.context = context;
    }

    public static void bookGift(int giftId) {
        System.out.println("giftId :: "+giftId);
        SocialGiftAPI.bookGift(giftId, context, new Callbacks.UserCallback() {
            @Override
            public void onSuccess() {
                System.out.println("Se ha hecho la reserva");
                Toast.makeText(context, "El regalo ha sido reservado",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("No hay wishlists", errorMessage);
            }
        });
    }
    public static void createGift(Gift gift){
        System.out.println("gift :: "+gift);
        SocialGiftAPI.createGift(gift,context,new Callbacks.UserCallback(){
            @Override
            public void onSuccess() {
                System.out.println("Se ha creado el regalo");
                Toast.makeText(context, "El regalo ha sido añadido a la wishlist",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onError(String errorMessage) {
                System.out.println("Error :: "+errorMessage);
                Log.e("No hay wishlists", errorMessage);
            }
        });
    }
}
