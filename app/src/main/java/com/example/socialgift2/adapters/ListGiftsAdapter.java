package com.example.socialgift2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.socialgift2.R;
import com.example.socialgift2.controllers.GiftController;
import com.example.socialgift2.controllers.MercadoExpressController;
import com.example.socialgift2.controllers.UserController;
import com.example.socialgift2.fragments.SearchFragment;
import com.example.socialgift2.fragments.ShowGiftFragment;
import com.example.socialgift2.fragments.ShowWishlistFragment;
import com.example.socialgift2.objects.Session;

import java.util.ArrayList;

public class ListGiftsAdapter extends BaseAdapter implements ListAdapter {
    public static ArrayList<String> list = new ArrayList<>();
    private Context context;
    TextView tvContact;
    public static MercadoExpressController mercadoExpressController;
    public GiftController giftController;


    public ListGiftsAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return checkNumerico(list.get(pos));
    }

    public static int checkNumerico(String numero){
        int resultado = 0; //Valor predeterminado 0
        try{
            if(numero != null){
                resultado = Integer.parseInt(numero);
            }
        }catch(NumberFormatException nfe){
            System.out.println("Error NumberFormatException value: " + numero);
        }
        return resultado;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_gifts_adapter, null);
            view.setVisibility(View.GONE);
        }
        Button callbtn= (Button)view.findViewById(R.id.btn);
        //Handle TextView and display string from your list
        tvContact= view.findViewById(R.id.tvContact);
        System.out.println("=======================================================================");
        System.out.println("list.get(position) :: "+list.get(position) );
        System.out.println("position :: "+position );
        //System.out.println("ShowGiftFragment.productsId.get(position) :: "+ShowGiftFragment.productsId.get(position) );
        System.out.println("ShowGiftFragment.lstGifts.get(position) :: "+ShowGiftFragment.lstGifts.get(position) );
        System.out.println("ShowWishlistFragment.wishlistfinal :: "+ShowWishlistFragment.wishlistfinal);
        String[] result = ShowGiftFragment.lstGifts.get(position).getProduct_url().split("/");
        System.out.println("result[result.length-1] :: "+result[result.length-1] );
        System.out.println("Session.user.getId() :: "+Session.user.getId() );
        System.out.println("list :: "+list );



        /*for (Gift g: ShowGiftFragment.lstGifts) {
            String[] result = g.getProduct_url().split("/");
            for (Integer s : ShowGiftFragment.productsId) {
                System.out.println("g.getBooked :: "+g.getBooked());
                System.out.println("result[result.length-1] :: "+result[result.length-1]);
                System.out.println("s :: "+s );
                System.out.println("list.get(position) :: "+list.get(position) );
                if(g.getBooked() == 1 && result[result.length-1].equals(s)){
                    callbtn.setVisibility(View.GONE);
                }
            }*/
        if((ShowGiftFragment.productsId.get(position).equals(ShowGiftFragment.lstGifts.get(position).getId()) &&
                ShowGiftFragment.lstGifts.get(position).getBooked()==1 ) ||
                ShowWishlistFragment.wishlistfinal.getId_user() == Session.user.getId()){

                    System.out.println("GONE");
                    callbtn.setVisibility(View.GONE);
        }
            tvContact.setText(list.get(position));
        //}

        giftController = new GiftController(this, context);


        //Handle buttons and add onClickListeners


        callbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                System.out.println("llega");
                System.out.println(position);
                System.out.println(ShowGiftFragment.lstGifts.get(position));
                System.out.println(ShowWishlistFragment.wishlistfinal);
                giftController.bookGift(ShowGiftFragment.lstGifts.get(position).getId());
            }
        });
        view.setVisibility(View.VISIBLE);
        return view;
    }
    public class Temp{
        public String nombre;
        public int id;
    }
}
