package com.example.socialgift2.controllers;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.socialgift2.MainActivity;
import com.example.socialgift2.activities.SignInActivity;
import com.example.socialgift2.objects.User;
import com.example.socialgift2.requests.Callbacks;
import com.example.socialgift2.requests.SocialGiftAPI;

import java.util.ArrayList;
import java.util.List;

public class UserController {

    private SignInActivity signInActivity;
    private Context context;

    public UserController(SignInActivity signInActivity, Context context) {
        this.signInActivity = signInActivity;
        this.context = context;
    }

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

    public void loginUser(String userEmail, String userPass){
        try{
            List<String> userData = new ArrayList<>();
            userData.add(userEmail);
            userData.add(userPass);
            SocialGiftAPI.loginUser(userData, context, new Callbacks.DataManagerCallbackLogin() {
                @Override
                public void onSuccess(String token) {
                    System.out.println("Usuario logueado");
                    Toast.makeText(context, "Te has logueado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }

                @Override
                public void onError(String errorMessage) {
                    System.out.println("Usuario NO logueado");
                    Toast.makeText(context, "Algo ha salido mal, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            System.out.println("ERROR :: "+e.getMessage());
        }
    }
}
