package com.leo.leomasapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.leo.leomasapp.Adapter.HistoryAdapter;
import com.leo.leomasapp.Data.ProductClass;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    FirebaseFirestore db ;
    FirebaseAuth auth;
    List<ProductClass> productClass;
    RecyclerView recyclerView;
    LinearLayout emptyLayout;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history_activity);
        emptyLayout = findViewById(R.id.empty_layout);
        back = findViewById(R.id.back);
        RecyclerView recyclerView = findViewById(R.id.history_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<ProductClass> productClass = new ArrayList<>();
        HistoryAdapter historyAdapter = new HistoryAdapter(productClass);
        recyclerView.setAdapter(historyAdapter);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();
        db.collection("history")
                .whereEqualTo("userId",userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            productClass.clear();
                           for(QueryDocumentSnapshot snapshot : task.getResult()){
                               String nameProduct = snapshot.getString("name_product");
                               String kodeProduct = snapshot.getString("kode_product");
                               Long priceProduct = snapshot.getLong("price_product");
                               String detailProduct = snapshot.getString("detail_product");
                               String jenisProduct = snapshot.getString("jenis_product");
                               int imageProduct1 = snapshot.getLong("image_product").intValue();
                               ProductClass productClasses = new ProductClass(
                                       nameProduct,
                                       priceProduct,
                                       detailProduct,
                                       jenisProduct,
                                       imageProduct1,
                                       0,0);
                               productClass.add(productClasses);



                           };

                            historyAdapter.notifyDataSetChanged();

                            if (productClass.isEmpty()){
                                recyclerView.setVisibility(View.GONE);
                                emptyLayout.setVisibility(View.VISIBLE);

                            }else {
                                recyclerView.setVisibility(View.VISIBLE);
                                emptyLayout.setVisibility(View.GONE);
                            }
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