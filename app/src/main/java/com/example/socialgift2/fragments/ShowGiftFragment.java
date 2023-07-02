package com.example.socialgift2.fragments;

import static com.example.socialgift2.fragments.SearchFragment.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.socialgift2.R;
import com.example.socialgift2.controllers.MercadoExpressController;
import com.example.socialgift2.objects.Gift;

import java.util.ArrayList;
import java.util.List;

public class ShowGiftFragment extends Fragment {
    private MercadoExpressController mercadoExpressController;
    public static ListView listView;
    public static List<Gift> lstGifts = new ArrayList<>();
    public static ArrayList<String> arrayList = new ArrayList<>();

    public static List<String> productsId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mercadoExpressController = new MercadoExpressController(this, getActivity());
        View rootView = inflater.inflate(R.layout.fragment_show_gift, container, false);

        System.out.println("lstGifts.size() :: "+lstGifts.size());
        if(lstGifts.size() > 0){
            arrayList.clear();

            for (Gift g: lstGifts) {
                System.out.println("producto");
                String[] result = g.getProduct_url().split("/");
                System.out.println("product id :: "+result[result.length-1]);
                mercadoExpressController.getAProduct(Integer.parseInt(result[result.length-1]),0);
                //arrayList.add(g.getProductUrl());
            }
        }
        //ImageView imageView = (ImageView) getView().findViewById(R.id.);
        listView = (ListView) rootView.findViewById(R.id.lv_fragmentGift);

        listView.setVisibility(View.GONE);

        //arrayList = new ArrayList<>();

        adapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        listView.requestLayout();

        return rootView;
    }

}
