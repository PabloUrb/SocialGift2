package com.example.socialgift2.requests;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.socialgift2.HttpsTrustManager;
import com.example.socialgift2.MainActivity;
import com.example.socialgift2.objects.Gift;
import com.example.socialgift2.objects.Session;
import com.example.socialgift2.objects.User;
import com.example.socialgift2.objects.Wishlist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SocialGiftAPI implements Callbacks {
    public static Session sessionController;
    private static String baseUrl ="https://balandrau.salle.url.edu/i3/socialgift/api/v1";

    public static void createUser(User u, Context context, UserCallback callback){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String finalUrl = baseUrl +"/users";

        try {
            JSONObject user = new JSONObject(u.toJSON());
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, finalUrl, user,
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
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public static void loginUser(List<String> userData, Context context, final UserCallbackLogin callback) {

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            String userEmail = userData.get(0);
            String userPass = userData.get(1);


            JSONObject body = new JSONObject();
            body.put("email", userEmail);
            body.put("password", userPass);


            HttpsTrustManager.allowAllSSL();
            String finalUrl = baseUrl + "/users/login";
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, finalUrl, body,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String token = response.getString("accessToken");

                                sessionController.setToken(token);

                                searchUser(userEmail, context, new Callbacks.UserCallbackSearch<User>(){
                                    @Override
                                    public void onSuccess(List<User> lstUsers) {
                                        if(lstUsers.size()!=0 && lstUsers.get(0)!=null){
                                            System.out.println("llega al id");
                                            Session.setUser(lstUsers.get(0));
                                            Toast.makeText(context, "Te has logueado", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, MainActivity.class);
                                            context.startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onError(String errorMessage) {

                                    }
                                });
                                callback.onSuccess(token);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                callback.onError(e.getMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                                callback.onError(error.getMessage());
                        }
                    });
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static void searchUser(String search, Context context, final UserCallbackSearch<User> callback){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String finalUrl = baseUrl + "/users/search?s="+search;

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
                    callback.onError(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                callback.onError(error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Session.getToken());
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }
    public static void getWishlistByUser(int id, Context context, UserCallbackWishlists<Wishlist> callback){
        String finalUrl = baseUrl + "/users/" + id + "/wishlists";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, finalUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<Wishlist> wishlistLst = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject wishlistObject = response.getJSONObject(i);

                                int idW = wishlistObject.getInt("id");
                                String nameW = wishlistObject.getString("name");
                                int userIdW = wishlistObject.getInt("user_id");
                                String creationDateS = wishlistObject.getString("creation_date");
                                String descriptionW = wishlistObject.getString("description");
                                String endDateS = wishlistObject.has("end_date") ? wishlistObject.getString("end_date") : null;
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                                Date creationDateW = format.parse(creationDateS);
                                Date endDateW = null;

                                if (endDateS != null) {
                                    endDateW = format.parse(endDateS);
                                }

                                List<Gift> giftLst = new ArrayList<>();

                                if (wishlistObject.has("gifts")) {
                                    JSONArray giftArray = wishlistObject.getJSONArray("gifts");
                                    for (int j = 0; j < giftArray.length(); j++) {
                                        JSONObject giftObject = giftArray.getJSONObject(j);

                                        int idG = giftObject.getInt("id");
                                        int bookedG = giftObject.getInt("booked");
                                        int wishlistIdG = giftObject.getInt("wishlist_id");
                                        int priorityG = giftObject.getInt("priority");
                                        String productUrlG = giftObject.getString("product_url");


                                        Gift gift = new Gift(idG, wishlistIdG, productUrlG, priorityG, bookedG);
                                        giftLst.add(gift);
                                    }
                                }

                                Wishlist w = new Wishlist(idW, nameW, descriptionW,  userIdW, creationDateW);

                                if (endDateW != null) {
                                    w.setEnd_date(endDateW);
                                }
                                if (giftLst.size() > 0) {
                                    w.setGifts(giftLst);
                                }

                                wishlistLst.add(w);
                            }

                            callback.onSuccess(wishlistLst);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError(e.getMessage());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
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
    public static void getUserFriends(int userId, Context context, UserCallbackUsersList<User> callback) {
        String finalUrl = baseUrl + "/users/" + userId + "/friends";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, finalUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<User> userFriendsLst = new ArrayList<>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject friendJson = response.getJSONObject(i);

                                User friend = new User();
                                friend.setId(friendJson.getInt("id"));
                                friend.setName(friendJson.getString("name"));
                                friend.setLastName(friendJson.getString("last_name"));
                                friend.setImage(friendJson.getString("image"));
                                friend.setEmail(friendJson.getString("email"));

                                userFriendsLst.add(friend);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        callback.onSuccess(userFriendsLst);
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

    public static void getGiftsReserved(int userId, Context context, UserCallbackGift<Gift> callback){
        String finalUrl = baseUrl + "/users/" + userId + "/gifts/reserved";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, finalUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Gift> giftsReservedLst = new ArrayList<>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject giftObject = response.getJSONObject(i);

                                Gift reserved = new Gift();
                                reserved.setId(giftObject.getInt("id"));
                                reserved.setProduct_url(giftObject.getString("product_url"));
                                reserved.setWishlist_id(giftObject.getInt("wishlist_id"));
                                reserved.setBooked(giftObject.getInt("booked"));
                                reserved.setPriority(giftObject.getInt("priority"));

                                giftsReservedLst.add(reserved);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.onSuccess(giftsReservedLst);
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

    }public static void wishlistsMyUser(Context context, UserCallbackWishlists<Wishlist> callback){
        String finalUrl = baseUrl + "/users/" + Session.user.getId() + "/wishlists";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, finalUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<Wishlist> wishlistLst = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject wishlistObject = response.getJSONObject(i);

                                int idW = wishlistObject.getInt("id");
                                String nameW = wishlistObject.getString("name");
                                int userIdW = wishlistObject.getInt("user_id");
                                String creationDateS = wishlistObject.getString("creation_date");
                                String descriptionW = wishlistObject.getString("description");
                                String endDateS = wishlistObject.has("end_date") ? wishlistObject.getString("end_date") : null;
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                                Date creationDateW = format.parse(creationDateS);
                                Date endDateW = null;

                                if (endDateS != null) {
                                    endDateW = format.parse(endDateS);
                                }

                                List<Gift> giftLst = new ArrayList<>();

                                if (wishlistObject.has("gifts")) {
                                    JSONArray giftArray = wishlistObject.getJSONArray("gifts");
                                    for (int j = 0; j < giftArray.length(); j++) {
                                        JSONObject giftObject = giftArray.getJSONObject(j);

                                        int idG = giftObject.getInt("id");
                                        int bookedG = giftObject.getInt("booked");
                                        int wishlistIdG = giftObject.getInt("wishlist_id");
                                        int priorityG = giftObject.getInt("priority");
                                        String productUrlG = giftObject.getString("product_url");


                                        Gift gift = new Gift(idG, wishlistIdG, productUrlG, priorityG, bookedG);
                                        giftLst.add(gift);
                                    }
                                }

                                Wishlist w = new Wishlist(idW, nameW, descriptionW,  userIdW, creationDateW);

                                if (endDateW != null) {
                                    w.setEnd_date(endDateW);
                                }
                                if (giftLst.size() > 0) {
                                    w.setGifts(giftLst);
                                }

                                wishlistLst.add(w);
                            }

                            callback.onSuccess(wishlistLst);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError(e.getMessage());
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
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
    public static void createWishlist(Wishlist wishlist, Context context, final UserCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String finalUrl = baseUrl + "/wishlists";

        JSONObject requestBody = new JSONObject();
        try {

            requestBody.put("name", wishlist.getName());
            requestBody.put("description", wishlist.getDescription());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            String currentDate = format.format(new Date());
            requestBody.put("date", currentDate);

            if (wishlist.getEnd_date() != null) {
                requestBody.put("finished_at", wishlist.getEnd_date());
            }

            if (wishlist.getGifts() != null) {
                JSONArray giftsArray = new JSONArray();
                for (Gift gift : wishlist.getGifts()) {
                    JSONObject giftObject = new JSONObject();

                    giftObject.put("id", gift.getId());
                    giftObject.put("product_url", gift.getProduct_url());
                    giftObject.put("wishlist_id", gift.getWishlist_id());
                    giftObject.put("booked", gift.getBooked());
                    giftObject.put("priority", gift.getPriority());

                    giftsArray.put(giftObject);
                }
                requestBody.put("gifts", giftsArray);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, finalUrl, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
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
    public static void editWishlist(Wishlist wishlist, Context context, UserCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String finalUrl = baseUrl + "/wishlists/" + wishlist.getId();
        JSONObject body = new JSONObject();

        try {
            body.put("id", wishlist.getId());
            body.put("name", wishlist.getName());
            body.put("description", wishlist.getDescription());
            body.put("user_id", wishlist.getId_user());
            if (wishlist.getGifts() != null) {
                JSONArray giftsArray = new JSONArray();
                for (Gift gift : wishlist.getGifts()) {
                    JSONObject giftJson = new JSONObject();

                    giftJson.put("id", gift.getId());
                    giftJson.put("wishlist_id", gift.getWishlist_id());
                    giftJson.put("product_url", gift.getProduct_url());
                    giftJson.put("priority", gift.getPriority());
                    giftJson.put("booked", gift.getBooked());
                    giftsArray.put(giftJson);
                }
                body.put("gifts", giftsArray);
            }


            body.put("creation_date", wishlist.getCreation_date());


            if (wishlist.getEnd_date() != null){
                body.put("end_date", wishlist.getEnd_date());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, finalUrl, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
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
    public static void updateUser(String name, String lastName, String image, Context context, final UserCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String finalUrl = baseUrl + "/users";

        if(name!=null){Session.user.setName(name);}
        if(lastName!=null){Session.user.setLastName(lastName);}
        if(image!=null){Session.user.setImage(image);}

        JSONObject body = new JSONObject();
        try {
            body.put("name", Session.user.getName());
            body.put("last_name", Session.user.getLast_name());
            body.put("image", Session.user.getImage());
            body.put("password", Session.user.getPassword());
            body.put("email", Session.user.getEmail());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, finalUrl, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
                        System.out.println("Se ha actualizado el usuario");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.getMessage());
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Session.getToken());
                return headers;
            }
        };


        requestQueue.add(request);
    }


    public static void getFriendRequests(Context context, UserCallbackUsersList<User> callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String finalUrl = baseUrl + "/friends/requests";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, finalUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<User> requestsLst = new ArrayList<>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject requestJson = response.getJSONObject(i);

                                User request = new User();
                                request.setId(requestJson.getInt("id"));
                                request.setImage(requestJson.getString("image"));
                                request.setName(requestJson.getString("name"));
                                request.setEmail(requestJson.getString("email"));
                                request.setLastName(requestJson.getString("last_name"));

                                requestsLst.add(request);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        callback.onSuccess(requestsLst);
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

    public static void sendFriendRequest(int userId, Context context, UserCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String finalUrl = baseUrl + "/friends/" + userId;
        System.out.println("userId 2 :: "+userId);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, finalUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
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
    public static void acceptFriendRequest(int requestId, Context context, UserCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String finalUrl = baseUrl + "/friends/" + requestId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, finalUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
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
    public static void rejectFriendRequest(int requestId, Context context, UserCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String finalUrl = baseUrl + "/friends/" + requestId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, finalUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
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
    public static void bookGift(int giftId, Context context, UserCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String finalUrl = baseUrl + "/gifts/" + giftId + "/book";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, finalUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
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
    public static void createGift(Gift gift, Context context, UserCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String finalUrl = baseUrl + "/gifts";

        JSONObject body = new JSONObject();

        try {
            body.put("wishlist_id", gift.getWishlist_id());
            body.put("product_url", gift.getProduct_url());
            body.put("priority", gift.getPriority());
            body.put("booked", gift.getBooked());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(finalUrl);
        System.out.println(body);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, finalUrl, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess();
                        System.out.println("Se ha creado el gift");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.getMessage());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Session.getToken());
                return headers;
            }
        };
        requestQueue.add(request);
    }
}
