package com.example.socialgift2.controllers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.socialgift2.MainActivity;
import com.example.socialgift2.activities.EditMyUserActivity;
import com.example.socialgift2.activities.ShowGiftActivity;
import com.example.socialgift2.activities.ShowMyProfileActivity;
import com.example.socialgift2.activities.ShowUserActivity;
import com.example.socialgift2.activities.SignInActivity;
import com.example.socialgift2.activities.SignUpActivity;
import com.example.socialgift2.fragments.SearchFragment;
import com.example.socialgift2.fragments.ShowReservedFragment;
import com.example.socialgift2.fragments.ShowWishlistFragment;
import com.example.socialgift2.objects.Gift;
import com.example.socialgift2.objects.Session;
import com.example.socialgift2.objects.User;
import com.example.socialgift2.objects.Wishlist;
import com.example.socialgift2.requests.SocialGiftAPI;
import com.example.socialgift2.requests.Callbacks;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private SignInActivity signInActivity;
    private SignUpActivity signUpActivity;
    private SearchFragment searchFragment;
    private ShowUserActivity showUserActivity;
    private ShowMyProfileActivity showMyProfileActivity;
    private ShowWishlistFragment showWishlistFragment;
    private EditMyUserActivity editMyUserActivity;
    private ShowReservedFragment showReservedFragment;

    private com.example.socialgift2.requests.Callbacks callbacks;
    private Context context;



    public UserController(SignUpActivity signUpActivity, Context context) {
        this.signUpActivity = signUpActivity;
        this.context = context;
    }
    public UserController(SignInActivity signInActivity, Context context) {
        this.signInActivity = signInActivity;
        this.context = context;
    }
    public UserController(SearchFragment searchFragment, Context context) {
        this.searchFragment = searchFragment;
        this.context = context;
    }public UserController(ShowMyProfileActivity showMyProfileActivity, Context context) {
        this.showMyProfileActivity = showMyProfileActivity;
        this.context = context;
    }
    public UserController(ShowUserActivity showUserActivity, Context context) {
        this.showUserActivity = showUserActivity;
        this.context = context;
    }
    public UserController(ShowWishlistFragment showWishlistFragment, Context context) {
        this.showWishlistFragment = showWishlistFragment;
        this.context = context;
    }
    public UserController(EditMyUserActivity editMyUserActivity, Context context) {
        this.editMyUserActivity = editMyUserActivity;
        this.context = context;
    }
    public UserController(ShowReservedFragment showReservedFragment, Context context) {
        this.showReservedFragment = showReservedFragment;
        this.context = context;
    }
    public void createUser(User u){
        try{
            SocialGiftAPI.createUser(u,context, new Callbacks.UserCallback(){
                @Override
                public void onSuccess() {
                    loginUser(u.getEmail(),u.getPassword());
                }

                @Override
                public void onError(String errorMessage) {
                    System.out.println("Error al crear el usuario");
                }
            });
        }catch (Exception e){
            System.out.println("ERROR :: "+e.getMessage());
        }
    }

    public void loginUser(String userEmail, String userPass){
        try{
            List<String> userData = new ArrayList<>();
            userData.add(userEmail);
            userData.add(userPass);
            SocialGiftAPI.loginUser(userData, context, new Callbacks.UserCallbackLogin() {
                @Override
                public void onSuccess(String token) {
                    System.out.println("Usuario logueado");

                }

                @Override
                public void onError(String errorMessage) {
                    System.out.println("Usuario NO logueado");
                    System.out.println(errorMessage);
                    Toast.makeText(context, "Algo ha salido mal, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            System.out.println("ERROR :: "+e.getMessage());
        }
    }
    public void searchUserByEmail(String search){
        SocialGiftAPI.searchUser(search, context, new Callbacks.UserCallbackSearch<User>(){

            @Override
            public void onSuccess(List<User> list) {
                for (User u: list ) {
                    SearchFragment.arrayList.add(u.getEmail());
                    SearchFragment.lstUsers.add(u);
                }
                SearchFragment.listView.setVisibility(View.VISIBLE);
                SearchFragment.adapter.getFilter().filter(search);
                System.out.println(SearchFragment.arrayList);
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
    public void getWCountOther(int id, Callbacks.CallbacksCount<Integer> callback) {
        SocialGiftAPI.getWishlistByUser(id,context, new Callbacks.UserCallbackWishlists<Wishlist>() {
            @Override
            public void onSuccess(List<Wishlist> wishlists) {
                int count = wishlists.size();
                callback.onSuccess(count);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }
    public void getReservedCountOther(int id, Callbacks.CallbacksCount<Integer> callback) {
        SocialGiftAPI.getGiftsReserved(id, context, new Callbacks.UserCallbackGift<Gift>() {
            @Override
            public void onSuccess(List<Gift> gifts) {
                int count = gifts.size();
                callback.onSuccess(count);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }
    public void getFCountOther(int id, Callbacks.CallbacksCount<Integer> callback) {
        SocialGiftAPI.getUserFriends(id, context, new Callbacks.UserCallbackUsersList<User>() {
            @Override
            public void onSuccess(List<User> users) {
                int count = users.size();
                callback.onSuccess(count);
            }

            @Override
            public void onError(String errorMessage) {
                callback.onError(errorMessage);
            }
        });
    }
    public void getWishlistByUser(int id) {
        SocialGiftAPI.getWishlistByUser(id, context, new Callbacks.UserCallbackWishlists<>() {
            @Override
            public void onSuccess(List<Wishlist> lstWishlist) {
                Log.d("API_SUCCESS_SEARCH_USER", "Mi LISTA DE WISHLIST ES:  " + lstWishlist);
                if(lstWishlist!=null){
                    ShowWishlistFragment.arrayList.clear();
                    for (Wishlist w: lstWishlist ) {
                        System.out.println("w :: "+w.getName());
                        ShowWishlistFragment.lstWishlist.add(w);
                        ShowWishlistFragment.arrayList.add(w.getName());
                    }
                    ShowWishlistFragment.listView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_ERROR_SEARCH_USER", errorMessage);
            }
        });
    }
    public void updateUser(List<String> lst){
        SocialGiftAPI.updateUser(lst.get(0), lst.get(1), lst.get(2), context, new Callbacks.UserCallback(){
            @Override
            public void onSuccess() {
                Toast.makeText(context, "Tu usuario se ha updateado", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(context, "Tu usuario NO se ha updateado", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void logOut(){
        Session.user = null;
        Session.setToken("");
        Intent intent = new Intent(context, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public void getGiftsReserved(int id){
        SocialGiftAPI.getGiftsReserved(id, context, new Callbacks.UserCallbackGift<>() {

            @Override
            public void onSuccess(List<Gift> gift) {
                System.out.println("MI ID QUE LE ESTOY PASANDO AL RESERVADO ES :: "+id);
                Log.d("API_SUCCESS_SEARCH_USER", "Mi LISTA DE WISHLIST ES:  " + gift);
                System.out.println("lista reseved gifts:: "+gift);
                if(gift!=null){
                    ShowReservedFragment.arrayList.clear();
                    for (Gift w: gift ) {
                        //ShowReservedFragment.lstGift.add(w);
                        String[] result = w.getProduct_url().split("/");
                        //ShowReservedFragment.arrayList.add(result[result.length-1]);
                        System.out.println("w.getProductUrl() :: "+w.getProduct_url());
                        System.out.println("result[result.length-1]  :: "+result[result.length-1]);
                        try{
                            Integer idGift = Integer.valueOf(result[result.length-1]);
                            if(idGift!=null){

                                ShowReservedFragment.mercadoExpressController.getAProduct(idGift,1);
                            }

                        }catch (Exception e){
                            Toast.makeText(context, "No tiene regalos reservados",Toast.LENGTH_SHORT).show();
                        }

                    }
                    ShowReservedFragment.listView.setVisibility(View.VISIBLE);;
                    //ShowGiftFragment.productsId = ShowReservedFragment.lstGift;
                }else{
                    Toast.makeText(context, "No tiene regalos reservados",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_ERROR_SEARCH_USER", errorMessage);
            }
        });
    }
}
