package com.example.socialgift2.controllers;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.socialgift2.adapters.ListGiftsAdapter;
import com.example.socialgift2.fragments.ShowGiftFragment;
import com.example.socialgift2.fragments.ShowReservedFragment;
import com.example.socialgift2.objects.Gift;
import com.example.socialgift2.objects.Product;
import com.example.socialgift2.requests.Callbacks;
import com.example.socialgift2.requests.MercadoExpressAPI;

import java.util.ArrayList;
import java.util.List;

public class MercadoExpressController {
    private ShowGiftFragment showGiftFragment;
    private ShowReservedFragment showReservedFragment;
    private ListGiftsAdapter listGiftsAdapter;

    private Context context;

    public interface DataManagerCallback<T> {
        void onSuccess(T data);

        void onError(String errorMessage);
    }

    public MercadoExpressController(ShowGiftFragment showGiftFragment, Context context) {
        this.showGiftFragment = showGiftFragment;
        this.context = context;
    }

    public MercadoExpressController(ShowReservedFragment showReservedFragment, Context context) {
        this.showReservedFragment = showReservedFragment;
        this.context = context;
    }
    public MercadoExpressController(ListGiftsAdapter listGiftsAdapter, Context context) {
        this.listGiftsAdapter = listGiftsAdapter;
        this.context = context;
    }

    public void getAProduct(int productId, int flag) {
        MercadoExpressAPI.getAProduct(productId, context, new Callbacks.UserCallbackProduct<>() {
            @Override
            public void onSuccess(Product product) {
                if (product != null) {
                    if (flag == 0) {
                        ShowGiftFragment.arrayList.add(product.getName());
                        ShowGiftFragment.listView.requestLayout();
                        ShowGiftFragment.listView.setVisibility(View.VISIBLE);
                    } else if (flag == 1) {
                        System.out.println(product);
                        System.out.println(product.getId());
                        System.out.println(product.getName());
                        ShowReservedFragment.arrayList.add(product.getName());
                        ShowReservedFragment.listView.requestLayout();
                        ShowReservedFragment.listView.setVisibility(View.VISIBLE);
                    } else {

                    }
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(context, "No se han podido recuperar los productos", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public List<Product> getAllProductsFiltered() {
        List<Product> lst = new ArrayList<>();
        MercadoExpressAPI.getAllProducts( context, new Callbacks.UserCallbackProductList<>() {

            @Override
            public List<Product> onSuccess(List<Product> productList) {
                if(productList.size()!=0){
                    return productList;
                }
                return productList;
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(context, "No se han podido recuperar los productos", Toast.LENGTH_SHORT).show();
            }
        });
        return lst;
    }
}