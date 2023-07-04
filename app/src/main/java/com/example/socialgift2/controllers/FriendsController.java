package com.example.socialgift2.controllers;


import android.content.Context;
import android.view.View;
import android.widget.Toast;


import com.example.socialgift2.activities.RequestsActivity;
import com.example.socialgift2.activities.ShowUserActivity;
import com.example.socialgift2.adapters.RequestsAdapter;
import com.example.socialgift2.objects.User;
import com.example.socialgift2.requests.Callbacks;
import com.example.socialgift2.requests.SocialGiftAPI;

import java.util.Arrays;
import java.util.List;

public class FriendsController {

    //private UserFriendsFragment userFriendsFragment;
    private RequestsActivity requestsActivity;
    private ShowUserActivity showUserProfile;

    private Context context;

    public FriendsController(RequestsActivity requestsActivity, Context context) {
        this.requestsActivity = requestsActivity;
        this.context = context;
    }

    public FriendsController(ShowUserActivity showUserProfile, Context context) {
        this.showUserProfile = showUserProfile;
        this.context = context;
    }

    public void getFriendRequests(){
        SocialGiftAPI.getFriendRequests(context, new Callbacks.UserCallbackUsersList<User>(){
            @Override
            public void onSuccess(List<User> users) {
                if(users!=null){
                    requestsActivity.list.clear();
                    for (User u: users ) {
                        System.out.println("user :: "+users);
                        requestsActivity.list.add(u.getEmail());
                        requestsActivity.lstUsers.add(u);
                    }
                    requestsActivity.listView.setAdapter(new RequestsAdapter( requestsActivity.list, context) );
                }else{
                    Toast.makeText(context, "No tienes solicitudes pendientes",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(context, "No se han podido recuperar sus solicitudes",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendFriendRequest(int userId){
        System.out.println("userId 1 :: "+userId);
        SocialGiftAPI.sendFriendRequest(userId, context, new Callbacks.UserCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "Se ha enviado tu solicitud de amistad",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(context, "Ha ocurrido un error, no se ha podido enviar tu solicitud",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void acceptFriendRequest(int requestId, String position){

        SocialGiftAPI.acceptFriendRequest(requestId, context, new Callbacks.UserCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "Se ha aceptado la solicitud de amistad",Toast.LENGTH_SHORT).show();
                requestsActivity.list.remove(position);
                requestsActivity.listView.setAdapter(new RequestsAdapter( requestsActivity.list, context) );
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(context, "No se ha podido aceptar la solicitud de amistad",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void rejectFriendRequest(int requestId, String position){
        SocialGiftAPI.rejectFriendRequest(requestId, context, new Callbacks.UserCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "Se ha rechazado la solicitud de amistad",Toast.LENGTH_SHORT).show();
                requestsActivity.list.remove(position);
                requestsActivity.listView.setAdapter(new RequestsAdapter( requestsActivity.list, context) );
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(context, "No se ha podido rechazar la solicitud de amistad",Toast.LENGTH_SHORT).show();
            }
        });
    }
}