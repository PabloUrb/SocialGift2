package com.example.socialgift2.requests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.socialgift2.objects.User;

import org.json.JSONException;
import org.json.JSONObject;

public class SocialGiftAPI implements Callbacks {

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

}
