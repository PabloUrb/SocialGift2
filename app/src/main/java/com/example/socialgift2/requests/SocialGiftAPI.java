package com.example.socialgift2.requests;

import android.content.Context;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.socialgift2.objects.Session;
import com.example.socialgift2.objects.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SocialGiftAPI implements Callbacks {
    private static Session sessionController;
    private static String baseUrl ="https://balandrau.salle.url.edu/i3/socialgift/api/v1";

    public static void createUser(User user, Context context, DataManagerCallback callback){

        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject jsonObject;
        String finalUrl = baseUrl +"/users";

        try {
            jsonObject = new JSONObject(user.toJSON());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, finalUrl, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.getMessage());
                }
        });

        queue.add(stringRequest);
    }

    public static void loginUser(List<String> userData, Context context, final DataManagerCallbackLogin callback) {

        try {

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
                            String errorMessage = "Error en la solicitud";
                                callback.onError(error.getMessage());
                        }
                    });

            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(stringRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
