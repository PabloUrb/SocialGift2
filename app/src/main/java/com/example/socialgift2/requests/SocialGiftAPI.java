package com.example.socialgift2.requests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.socialgift2.objects.Session;
import com.example.socialgift2.objects.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocialGiftAPI implements Callbacks {
    private static Session sessionController;
    private static String baseUrl ="https://balandrau.salle.url.edu/i3/socialgift/api/v1";

    public static void createUser(User u, Context context, UserCallback callback){

        RequestQueue volley = Volley.newRequestQueue(context);
        JSONObject user;
        String finalUrl = baseUrl +"/users";

        try {

            user = new JSONObject(u.toJSON());
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, finalUrl, user,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                                callback.onSuccess();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.networkResponse.statusCode);
                    callback.onError(error.getMessage());
                }
            });
            volley.add(stringRequest);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public static void loginUser(List<String> userData, Context context, final UserCallbackLogin callback) {

        try {
            RequestQueue volley = Volley.newRequestQueue(context);
            String userEmail = userData.get(0);
            String userPass = userData.get(1);


            JSONObject body = new JSONObject();
            body.put("email", userEmail);
            body.put("password", userPass);

            String finalUrl = baseUrl + "/users/login";
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, finalUrl, body,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String token = response.getString("accessToken");

                                sessionController.setUserToken(token);
                                sessionController.setUserEmail(userEmail);
                                sessionController.setUserPass(userPass);

                                callback.onSuccess(token);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                callback.onError("Error al procesar la respuesta del servidor");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                                callback.onError(error.getMessage());
                        }
                    });


            volley.add(stringRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void searchUser(String search, Context context, final UserCallbackSearch<User> callback){
        RequestQueue queue = Volley.newRequestQueue(context);
        String finalUrl = baseUrl + "/users/search?s="+search;
        // Request a string response from the provided URL.
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, finalUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    List<User> lstUsers = new ArrayList<>();

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject userObject = response.getJSONObject(i);
                        User u = new User();
                        u.setId(userObject.getInt("id"));
                        u.setName(userObject.getString("name"));
                        u.setLastName(userObject.getString("last_name"));
                        u.setEmail(userObject.getString("email"));
                        u.setImage(userObject.getString("image"));
                        lstUsers.add(u);
                    }

                        callback.onSuccess(lstUsers);

                }catch (Exception e){
                    e.printStackTrace();
                    callback.onError("No se ha podido encontrar al usuario");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Session.getUserToken());
                return headers;
            }
        };
        queue.add(stringRequest);
    }
}
