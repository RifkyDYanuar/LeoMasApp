package com.leo.leomasapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    TextView nameProduct,hargaProduct,jenisProduct,detailProduct;
    ImageView firstImage,secondImage,lastImage,back,favorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

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


    }
}