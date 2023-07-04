


package com.example.socialgift2.requests;

import android.content.Context;
import android.icu.util.ULocale;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.socialgift2.objects.Gift;
import com.example.socialgift2.objects.Product;
import com.example.socialgift2.objects.Session;
import com.example.socialgift2.objects.Wishlist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MercadoExpressAPI {

    private static final String baseUrl = "https://balandrau.salle.url.edu/i3/mercadoexpress/api/v1";

    public static void getAProduct(int productId, Context context, Callbacks.UserCallbackProduct<Product> callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String finalUrl = baseUrl + "/products/" + productId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, finalUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int id = response.getInt("id");
                            String name = response.getString("name");
                            String description = response.getString("description");
                            String link = response.getString("link");
                            String photo = response.getString("photo");
                            float price = response.getInt("price");
                            int active = response.getInt("is_active");
                            JSONArray categoryList = response.getJSONArray("categoryIds");
                            int[] categoryArray = new int[categoryList.length()];
                            for (int i = 0; i < categoryList.length(); i++) {
                                categoryArray[i] = categoryList.getInt(i);
                            }

                            Product product = new Product(id, name, description, link, photo, price, active, categoryArray);

                            callback.onSuccess(product);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError("Error al tratar con el sevidor");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.getMessage());
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Session.getToken());
                return headers;
            }
        };
        requestQueue.add(request);
    }
    public static void getAllProducts(Context context, Callbacks.UserCallbackProductList<Product> callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String finalUrl = baseUrl + "/products";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, finalUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Product> productList = new ArrayList<>();

                        // Parsear el JSONArray y obtener la lista de productos
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject productObject = response.getJSONObject(i);
                                int id = productObject.getInt("id");
                                String name = productObject.getString("name");
                                String description = productObject.getString("description");
                                String link = productObject.getString("link");
                                String photo = productObject.getString("photo");
                                float price = productObject.getInt("price");
                                int is_active = productObject.getInt("is_active");
                                JSONArray categoryIdsArray = new JSONArray();
                                int[] categoryIds;
                                if(productObject.getJSONArray("categoryIds")!=null){
                                    categoryIdsArray = productObject.getJSONArray("categoryIds");
                                    categoryIds = new int[categoryIdsArray.length()];
                                    for (int a = 0; a < categoryIdsArray.length(); a++) {
                                        categoryIds[a] = categoryIdsArray.getInt(a);
                                    }
                                }else{
                                    categoryIds = new int[0];
                                }

                                Product product = new Product(id, name, description, link, photo, price, is_active, categoryIds);
                                productList.add(product);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        // Llamar al callback onSuccess con la lista de productos
                        callback.onSuccess(productList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Llamar al callback onError en caso de error
                        String errorMessage = "Error en la solicitud: " + error.getMessage();
                        callback.onError(errorMessage);
                    }
                });
        requestQueue.add(request);
    }
}
