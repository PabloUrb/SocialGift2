package com.example.socialgift2.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import com.example.socialgift2.R;
import com.example.socialgift2.controllers.UserController;
import com.example.socialgift2.objects.User;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    public static ArrayList<String> arrayList;
    public static ArrayAdapter<String> adapter;

    private UserController userController;

    public static ListView listView;
    public static List<User> lstUsers = new ArrayList<>();
    public static User user = new User();

    SearchView searchView;
    Activity activity = getActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userController = new UserController(this, getActivity());
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        //ImageView imageView = (ImageView) getView().findViewById(R.id.);
        searchView = (SearchView) rootView.findViewById(R.id.sv_fragmentSearch);
        listView = (ListView) rootView.findViewById(R.id.lv_fragmentSearch);

        listView.setVisibility(View.GONE);

        arrayList = new ArrayList<>();

        adapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)){
                    listView.setVisibility(View.GONE);
                }else{
                    System.out.println("s :: " +s);
                    userController.searchUserByEmail(s);

                }
                return false;
            }
        });
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Object o = listView.getItemAtPosition(position);
                String str= (String) o;

                System.out.println(str);
                for (User u: lstUsers) {
                    if(u.getEmail() == str){
                        user=u;
                    }
                }
                /*Intent homepage = new Intent(getActivity(), com.example.socialgift.view.ShowUserProfile.class);
                startActivity(homepage);*/
            }
        });
        return rootView;
    }

}
