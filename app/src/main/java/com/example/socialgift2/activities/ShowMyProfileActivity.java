package com.example.socialgift2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialgift2.MainActivity;
import com.example.socialgift2.R;
import com.example.socialgift2.controllers.UserController;
import com.example.socialgift2.fragments.SearchFragment;
import com.example.socialgift2.objects.Session;
import com.example.socialgift2.objects.User;
import com.example.socialgift2.requests.Callbacks;
import com.example.socialgift2.requests.SocialGiftAPI;

import java.io.InputStream;

public class ShowMyProfileActivity extends AppCompatActivity {

    Button edit, logout, requests, wishlist;
    private TextView nameTextView, friendsCountTextView, reservedGiftsCountTextView, wishlistsCountTextView;
    User user;
    UserController userController;

    private com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView;

    //FriendsController friendsController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        bottomNavigationView = findViewById(R.id.bottom_navigationbar);
        bottomNavigationView.getMenu().clear();
        bottomNavigationView.inflateMenu(R.menu.menu);
        bottomNavigationView.setSelectedItemId(R.id.ic_user);


        TextView myAwesomeTextView2 = (TextView)findViewById(R.id.userName);

        new ShowMyProfileActivity.DownloadImageFromInternet((ImageView) findViewById(R.id.image)).execute(Session.user.getImage());
        //myAwesomeTextView.setText(SearchFragment.user.getId());
        myAwesomeTextView2.setText(Session.user.getName() +" "+Session.user.getLast_name());
        friendsCountTextView = findViewById(R.id.friends_count);
        reservedGiftsCountTextView = findViewById(R.id.reserved_gifts_count);
        wishlistsCountTextView = findViewById(R.id.wishlists_count);
        userController = new UserController(this, getApplicationContext());
        //friendsController = new FriendsController(this, getApplicationContext());

        showUserData(Session.user.getId());


        edit = (Button) findViewById(R.id.edit);
        logout = (Button) findViewById(R.id.logout);
        requests = (Button) findViewById(R.id.requests);

        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), EditMyUserActivity.class);
                startActivity(myIntent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                userController.logOut();
            }
        });
        requests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RequestsActivity.class));
            }
        });
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.ic_home:
                    startActivity(new Intent(this, MainActivity.class));
                    break;
                case R.id.ic_user:
                    startActivity(new Intent(this, ShowMyProfileActivity.class));
                    break;
                case R.id.ic_basket:
                    startActivity(new Intent(this, WishlistActivity.class));
                    break;
            }
            return false;
        });

    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return true;
    }
    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;
            Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...",Toast.LENGTH_SHORT).show();
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {
                InputStream in=new java.net.URL(imageURL).openStream();
                bimage= BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

    }
    public void showUserData(int id) {
        // Mostrar los datos del usuario en la interfaz de usuario
        userController.getWishlistsCountOther(id, new Callbacks.CallbacksCount<Integer>() {
            @Override
            public void onSuccess(Integer count) {
                if(count!=null){
                    wishlistsCountTextView.setText("Wishlists: " + count);
                }else{
                    wishlistsCountTextView.setText("Wishlists: 0");
                }

            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_ERROR_GET_WISHLISTS", errorMessage);
                wishlistsCountTextView.setText("Wishlists: 0");
            }
        });

        userController.getReservedGiftsCountOther(id, new Callbacks.CallbacksCount<Integer>() {
            @Override
            public void onSuccess(Integer count) {
                reservedGiftsCountTextView.setText("Regalos reservados: " + count);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_ERROR_GET_GIFTS_RESERVED", errorMessage);
                reservedGiftsCountTextView.setText("Regalos reservados: 0");

            }
        });

        userController.getFriendsCountOther(id,new Callbacks.CallbacksCount<Integer>() {
            @Override
            public void onSuccess(Integer count) {
                TextView friendsCountTextView = findViewById(R.id.friends_count);
                if(count!=null){
                    friendsCountTextView.setText("Amigos: " + count);
                }else{
                    friendsCountTextView.setText("Amigos: 0");
                }


            }

            @Override
            public void onError(String errorMessage) {
                Log.e("API_ERROR_GET_FRIENDS", errorMessage);
                friendsCountTextView.setText("Amigos: 0");
            }
        });
    }


}