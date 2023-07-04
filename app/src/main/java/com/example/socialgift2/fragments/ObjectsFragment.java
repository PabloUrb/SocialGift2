package com.example.socialgift2.fragments;

import static android.provider.Settings.System.getString;
import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.example.socialgift2.MainActivity;
import com.example.socialgift2.R;
import com.example.socialgift2.activities.AddProductToWishlistActivity;
import com.example.socialgift2.controllers.ProductController;
import com.example.socialgift2.objects.Product;


import java.util.ArrayList;
import java.util.List;

public class ObjectsFragment extends RecyclerView.Adapter<ObjectsFragment.ProductViewHolder>{
    private ProductController productController;
    private ObjectsFragment objectsFragment;
    private List<Product> products;
    private OnItemClickListener itemClickListener;
    public static Product productC;
    private Context context;
    public static View views;

    public ObjectsFragment(ObjectsFragment objectsFragment) {
        this.objectsFragment = objectsFragment;
        this.products = new ArrayList<>();
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_objects, parent, false);
        context = parent.getContext();
        views = view;
        return new ObjectsFragment.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public View getView() {
        return views;
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }
    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTextView;
        private TextView descriptionTextView;
        private ImageView imageProduct;
        private ImageButton button;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.name);
            descriptionTextView = itemView.findViewById(R.id.description);
            imageProduct = itemView.findViewById(R.id.imageProduct);
            button = itemView.findViewById(R.id.buttonObject);
            itemView.setOnClickListener(this);
        }


        public void bind(Product product){

            System.out.println("getId :: "+product.getId());
            System.out.println("getName :: "+product.getName());
            System.out.println("getDescription :: "+product.getDescription());
            System.out.println("product.getPhoto() :: "+product.getPhoto());

            descriptionTextView.setText(product.getDescription());
            titleTextView.setText(product.getName());

            try {
                Glide.with(context)
                        .asBitmap()
                        .load(product.getPhoto())
                        .error(R.drawable.ic_image)
                        .into(imageProduct);
            }catch (Exception e){
                imageProduct.setImageResource(R.drawable.ic_image);
            }

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    System.out.println("llega");
                    productC = product;
                    Context context = v.getContext();
                    System.out.println("product "+productC);
                    context.startActivity(new Intent(context, AddProductToWishlistActivity.class));
                }
            });

        }

        @Override
        public void onClick(View v) {

        }
    }


}
