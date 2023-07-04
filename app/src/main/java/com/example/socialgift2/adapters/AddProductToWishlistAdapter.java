package com.example.socialgift2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialgift2.R;
import com.example.socialgift2.controllers.GiftController;
import com.example.socialgift2.controllers.UserController;
import com.example.socialgift2.controllers.WishlistController;
import com.example.socialgift2.fragments.ObjectsFragment;
import com.example.socialgift2.objects.Gift;
import com.example.socialgift2.objects.Wishlist;

import java.util.ArrayList;
import java.util.List;

public class AddProductToWishlistAdapter  extends RecyclerView.Adapter<AddProductToWishlistAdapter.WishlistViewHolder>{
    private WishlistController wishlistController;
    private List<Wishlist> wishlists;
    private OnItemClickListener itemClickListener;
    private GiftController giftController;
    public static Wishlist wishlistC;

    public AddProductToWishlistAdapter(WishlistController wishlistController) {
        this.wishlistController = wishlistController;
        this.wishlists = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public WishlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_add_product_wishlist, parent, false);
        giftController = new GiftController(this, view.getContext());
        return new AddProductToWishlistAdapter.WishlistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddProductToWishlistAdapter.WishlistViewHolder holder, int position) {
        Wishlist wishlist = wishlists.get(position);
        holder.bind(wishlist);
    }

    public int getItemCount() {
        return wishlists.size();
    }

    public void setWishlists(List<Wishlist> wishlists) {
        this.wishlists = wishlists;
    }

    public interface OnItemClickListener {
        void onItemClick(Wishlist wishlist);
    }

    public class WishlistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView endDateTextView;


        public WishlistViewHolder(View view) {
            super(view);
            titleTextView = itemView.findViewById(R.id.Nombre);
            descriptionTextView = itemView.findViewById(R.id.Descripcion);
            endDateTextView = itemView.findViewById(R.id.tvFecha);
            itemView.setOnClickListener(this);
        }

        //@SuppressLint("SetTextI18n")
        public void bind(Wishlist wishlist) {
            System.out.println("getId :: "+wishlist.getId());
            System.out.println("getName :: "+wishlist.getName());
            System.out.println("getEndDate :: "+wishlist.getEnd_date());
            System.out.println("getDescription :: "+wishlist.getDescription());
            titleTextView.setText(wishlist.getName());
            descriptionTextView.setText("Descripción: "+wishlist.getDescription());

            if (wishlist.getEnd_date() != null) {
                endDateTextView.setText(wishlist.getEnd_date().toString());
            } else {
                endDateTextView.setText("");
            }

        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            System.out.println("llega Wishlist");
            Wishlist wishlist = wishlists.get(position);
            Context context = v.getContext();
            System.out.println("llega Wishlist 2");
            try{
                Gift gift = new Gift();
                gift.setWishlist_id(wishlist.getId());
                gift.setBooked(0);
                gift.setPriority(10);
                gift.setProduct_url(ObjectsFragment.productC.getLink());
                giftController.createGift(gift);
            }catch (Exception e){
                Toast.makeText(context, "No se ha podido añadir el producto",Toast.LENGTH_SHORT).show();
                System.out.println("error :: "+e.getMessage());
            }


        }
    }

}
