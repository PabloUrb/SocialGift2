package com.example.socialgift2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.socialgift2.activities.ShowMyProfileActivity;
import com.example.socialgift2.activities.WishlistActivity;
import com.example.socialgift2.controllers.ProductController;
import com.example.socialgift2.fragments.ObjectsFragment;
import com.example.socialgift2.fragments.SearchFragment;
import com.example.socialgift2.objects.Product;
import com.example.socialgift2.objects.Session;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static Context context;
    BottomNavigationView bottomNavigationView;
    public static RecyclerView recyclerView;
    private ObjectsFragment objectsFragment;
    private ProductController productController;
    public static FragmentManager fragmentManager;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigationbar);
        bottomNavigationView.getMenu().clear();
        bottomNavigationView.inflateMenu(R.menu.menu);

        productController = new ProductController(this, this);

        System.out.println("2 Session.getuserId() :: "+Session.user.getId());

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
        fragmentManager = this.getSupportFragmentManager();

        recyclerView = findViewById(R.id.recyclerViewObjects);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        objectsFragment = new ObjectsFragment(objectsFragment);
        objectsFragment.setOnItemClickListener(this::onItemClick);
        recyclerView.setAdapter(objectsFragment);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        productController.cargarProducts();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_searchBar, SearchFragment.class, null)
                    .commit();
        }

    }
    public void showProductList(List<Product> productList) {
        objectsFragment.setProducts(productList);
        objectsFragment.notifyDataSetChanged();
    }

    private void onItemClick(Product product) {
        Log.d("MainActivity", "ID Product: " + product.getId());
    }
}