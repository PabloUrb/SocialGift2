package com.example.socialgift2.controllers;

import android.content.Context;

import com.example.socialgift2.MainActivity;
import com.example.socialgift2.objects.Product;
import com.example.socialgift2.requests.Callbacks;
import com.example.socialgift2.requests.MercadoExpressAPI;

import java.util.List;

public class ProductController {
    private static MainActivity activity;
    private static Context context;
    public ProductController (Context context, MainActivity activity){
        this.activity = activity;
        this.context = context;
    }

    public static void cargarProducts() {
        MercadoExpressAPI.getAllProducts(context, new Callbacks.UserCallbackProductList<Product>() {
            @Override
            public List<Product> onSuccess(List<Product> products) {
                System.out.println("wishlists.size() :: "+products.size());
                activity.showProductList(products);
                return products;
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
}
