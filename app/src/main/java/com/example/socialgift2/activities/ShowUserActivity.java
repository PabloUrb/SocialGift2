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
import com.example.socialgift2.controllers.FriendsController;
import com.example.socialgift2.controllers.UserController;
import com.example.socialgift2.fragments.SearchFragment;
import com.example.socialgift2.objects.Session;
import com.example.socialgift2.objects.User;
import com.example.socialgift2.requests.Callbacks;

import java.io.InputStream;

public class ShowUserActivity extends AppCompatActivity {

    Button wishlist, reserved, requestBeFriends;
    private TextView nameTextView, friendsCountTextView, reservedGiftsCountTextView, wishlistsCountTextView;
    User user;
    UserController userController;
    FriendsController friendsController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_activity_user);

        user = SearchFragment.user;
        //showUserData(user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        TextView myAwesomeTextView2 = (TextView)findViewById(R.id.userName);

        new DownloadImageFromInternet((ImageView) findViewById(R.id.user_image)).execute(SearchFragment.user.getImage());
        //myAwesomeTextView.setText(SearchFragment.user.getId());
        System.out.println("id user :: "+ SearchFragment.user.getId());
        myAwesomeTextView2.setText(SearchFragment.user.getName() +" "+SearchFragment.user.getLast_name());
        friendsCountTextView = findViewById(R.id.friends_count);
        reservedGiftsCountTextView = findViewById(R.id.reserved_gifts_count);
        wishlistsCountTextView = findViewById(R.id.wishlists_count);
        userController = new UserController(this, getApplicationContext());
        friendsController = new FriendsController(this, getApplicationContext());
        System.out.println("USER ID :: "+SearchFragment.user.getId());
        showUserData(SearchFragment.user);


        wishlist = (Button) findViewById(R.id.wishlistButton);
        requestBeFriends = (Button) findViewById(R.id.rBeFriendsButton);
        reserved = (Button) findViewById(R.id.reservedButton);

        wishlist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), ShowWishlistActivity.class);
                startActivity(myIntent);
            }
        });

        reserved.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), ShowReservedActivity.class);
                startActivity(myIntent);
            }
        });

        requestBeFriends.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("SearchFragment.user.getId() :: "+SearchFragment.user.getId());
                friendsController.sendFriendRequest(SearchFragment.user.getId());
            }
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
    public void showUserData(User user) {
        // Mostrar los datos del usuario en la interfaz de usuario
        userController.getWCountOther(user.getId(), new Callbacks.CallbacksCount<Integer>() {
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

        userController.getReservedCountOther(user.getId(), new Callbacks.CallbacksCount<Integer>() {
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

        userController.getFCountOther(user.getId(),new Callbacks.CallbacksCount<Integer>() {
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