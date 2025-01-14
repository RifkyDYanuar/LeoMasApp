package com.leo.leomasapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    private boolean isFavorite = false;
    private String favID = null;
    private String HisID;
    private FirebaseFirestore db ;
    private FirebaseAuth auth;
    TextView nameProduct,hargaProduct,jenisProduct,detailProduct;
    ImageView firstImage,secondImage,lastImage,back,favorite;
    ProgressBar loading;
    Button addToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        addToCart = findViewById(R.id.btn_buy_now);
        loading = findViewById(R.id.loading);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        Intent intent = getIntent();
        String name = intent.getStringExtra("nameProduct");
        Long Harga = intent.getLongExtra("priceProduct",0);
        String price = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(Harga);
        String jenis = intent.getStringExtra("jenisProduct");
        String detail = intent.getStringExtra("detailProduct");
        int image1 = intent.getIntExtra("imageProduct1",0);
        int image2 = intent.getIntExtra("imageProduct2",0);
        int image3 = intent.getIntExtra("imageProduct3",0);

        nameProduct = findViewById(R.id.name_product);
        hargaProduct = findViewById(R.id.harga_product);
        jenisProduct = findViewById(R.id.jenis_product);
        detailProduct = findViewById(R.id.detail_product);
        firstImage = findViewById(R.id.first_image);
        secondImage = findViewById(R.id.second_image);
        lastImage = findViewById(R.id.last_image);
        back = findViewById(R.id.back);
        favorite = findViewById(R.id.favorite);
        nameProduct.setText(name);
        hargaProduct.setText(price);
        jenisProduct.setText(jenis);
        detailProduct.setText(detail);
        firstImage.setImageResource(image1);
        secondImage.setImageResource(image2);
        lastImage.setImageResource(image3);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        chekFavorite(name);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite){
                    removeFavorite();
                }else {
                    addFavorite(name,Harga,jenis,detail,image1);
                }
            }
        });
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setVisibility(View.VISIBLE);
                addToCart.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      loading.setVisibility(View.GONE);
                      addToCart.setEnabled(true);
                      addHistory(name,Harga,jenis,detail,image1);
                  }
                  },3000);

            }
        });


    }
    private void chekFavorite(String name) {
        String userId = auth.getCurrentUser().getUid();
        db.collection("favorites") // Pastikan nama koleksi sesuai
                .whereEqualTo("userId", userId)
                .whereEqualTo("name_product", name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot result = task.getResult();
                            if (!result.isEmpty()) {
                                isFavorite = true;
                                favID = result.getDocuments().get(0).getId();
                                favorite.setImageResource(R.drawable.baseline_favorite_24);
                            } else {
                                isFavorite = false;
                                favID = null;
                                favorite.setImageResource(R.drawable.outline_favorite_border_2);
                            }
                        } else {
                            isFavorite = false;
                            favorite.setImageResource(R.drawable.outline_favorite_border_2);
                            Toast.makeText(DetailActivity.this, "Failed to check favorite status!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addFavorite(String name,Long Harga,String jenis,String detail,int image){
        String userId = auth.getCurrentUser().getUid();
        HashMap<String, Object> favoriteData = new HashMap<>();
        favoriteData.put("userId",userId);
        favoriteData.put("name_product",name);
        favoriteData.put("price_product",Harga);
        favoriteData.put("jenis_product",jenis);
        favoriteData.put("detail_product",detail);
        favoriteData.put("image_product",image);
        db.collection("favorites")
                .add(favoriteData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        isFavorite = true;
                        favID = documentReference.getId();
                        favorite .setImageResource(R.drawable.baseline_favorite_24);
                        Toast.makeText(DetailActivity.this, "Product is added to Favorite", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DetailActivity.this, "Failed to added product in Favorite!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void removeFavorite(){
        if (favID!= null){
            db.collection("favorites")
                    .document(favID)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            isFavorite = false;
                            favID = null;
                            favorite.setImageResource(R.drawable.outline_favorite_border_2);
                            Toast.makeText(DetailActivity.this, "Product is removed from Favorite", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DetailActivity.this, "Failed remove product from Favorite!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    private void addHistory(String name,Long Harga,String jenis,String detail,int image){
        String userId = auth.getCurrentUser().getUid();
        HashMap<String, Object> favoriteData = new HashMap<>();
        favoriteData.put("userId",userId);
        favoriteData.put("name_product",name);
        favoriteData.put("price_product",Harga);
        favoriteData.put("jenis_product",jenis);
        favoriteData.put("detail_product",detail);
        favoriteData.put("image_product",image);
        db.collection("history")
                .add(favoriteData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        HisID = documentReference.getId();
                        Toast.makeText(DetailActivity.this, "Thanks you for buying!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DetailActivity.this, "Failed to added product in Favorite!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}