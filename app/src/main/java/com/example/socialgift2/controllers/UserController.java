package com.example.socialgift2.controllers;

import android.content.Context;
import android.widget.Toast;

import com.example.socialgift2.objects.User;
import com.example.socialgift2.requests.Callbacks;
import com.example.socialgift2.requests.SocialGiftAPI;

public class UserController {

    private Context context;

    public void createUser(){
        try{
            User u = new User();
            SocialGiftAPI.createUser(u,context, new SocialGiftAPI.DataManagerCallback(){
                @Override
                public void onSuccess() {
                    System.out.println("Usuario creado");
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
}
