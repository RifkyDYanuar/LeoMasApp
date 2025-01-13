package com.leo.leomasapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.leo.leomasapp.Adapter.FavoriteAdapter;
import com.leo.leomasapp.Data.ProductClass;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorite);
        back = findViewById(R.id.back);
        LinearLayout emptyLayout = findViewById(R.id.empty_layout);
        RecyclerView recyclerView= findViewById(R.id.favorite_rv);
        List<ProductClass> productClasses = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(FavoriteActivity.this,2);
        FavoriteAdapter favoriteAdapter = new FavoriteAdapter(productClasses);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(favoriteAdapter);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();

        db.collection("favorites")
                .whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            productClasses.clear(); // Clear the list before adding new data
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                String nameProduct = snapshot.getString("name_product");
                                String kodeProduct = snapshot.getString("kode_product");
                                Long priceProduct = snapshot.getLong("price_product");
                                String detailProduct = snapshot.getString("detail_product");
                                String jenisProduct = snapshot.getString("jenis_product");
                                int imageProduct1 = snapshot.getLong("image_product").intValue();


                                ProductClass productClass = new ProductClass(
                                        nameProduct,
                                        priceProduct,
                                        detailProduct,
                                        jenisProduct,
                                        imageProduct1,
                                        0,
                                        0);

                                productClasses.add(productClass);}

                            // Notify adapter about data changes
                            favoriteAdapter.notifyDataSetChanged();
                            if (productClasses.isEmpty()) {
                                emptyLayout.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }else {
                                emptyLayout.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            // Handle errors
                            Toast.makeText(FavoriteActivity.this, "Failed to load favorites: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}