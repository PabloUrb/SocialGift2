package com.example.socialgift2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.socialgift2.R;
import com.example.socialgift2.activities.ShowGiftActivity;
import com.example.socialgift2.controllers.UserController;
import com.example.socialgift2.objects.Wishlist;

import java.util.ArrayList;
import java.util.List;

public class ShowWishlistFragment extends Fragment {
    public static ArrayAdapter<String> adapter;
    private UserController userController;
    public static ListView listView;
    public static ArrayList<String> arrayList;
    public static List<Wishlist> lstWishlist = new ArrayList<>();
    public static Wishlist wishlist;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userController = new UserController(this, getActivity());
        View rootView = inflater.inflate(R.layout.fragment_show_wishlist, container, false);
        userController.getWishlistByUser(SearchFragment.user.getId());
        //ImageView imageView = (ImageView) getView().findViewById(R.id.SearchFragment.user);
        listView = (ListView) rootView.findViewById(R.id.lv_fragmentWishlist);

        listView.setVisibility(View.GONE);

        arrayList = new ArrayList<>();

        adapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        listView.requestLayout();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.requestLayout();
                wishlist = new Wishlist();
                wishlist.setId(lstWishlist.get((int) id).getId());
                wishlist.setName(lstWishlist.get((int) id).getName());
                wishlist.setCreation_date(lstWishlist.get((int) id).getCreation_date());
                wishlist.setDescription(lstWishlist.get((int) id).getDescription());
                wishlist.setGifts(lstWishlist.get((int) id).getGifts());
                wishlist.setId_user(lstWishlist.get((int) id).getId_user());
                wishlist.setEnd_date(lstWishlist.get((int) id).getEnd_date());
                System.out.println("wishlist getId :: "+wishlist.getId());
                System.out.println("wishlist getName :: "+wishlist.getName());
                System.out.println("wishlist getDescription :: "+wishlist.getDescription());
                System.out.println("wishlist getIdUser :: "+wishlist.getId_user());
                System.out.println("wishlist getCreationDate :: "+wishlist.getCreation_date());
                System.out.println("wishlist getGifts :: "+wishlist.getGifts());
                System.out.println("wishlist getEnd_date :: "+wishlist.getEnd_date());
                if(wishlist.getGifts()==null){
                    Toast.makeText(getActivity().getApplicationContext(), "No tiene regalos relacionados",Toast.LENGTH_SHORT).show();
                }else{
                    ShowGiftFragment.lstGifts = wishlist.getGifts();
                    startActivity( new Intent(getActivity().getApplicationContext(), ShowGiftActivity.class));
                }
            }
        });

        return rootView;
    }
}
