package com.leo.leomasapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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


public class FavoriteFragment extends Fragment {




    public FavoriteFragment() {
        // Required empty public constructor
    }


    public static FavoriteFragment newInstance(String param1, String param2) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        RecyclerView recyclerView= view.findViewById(R.id.favorite_rv);
        List<ProductClass> productClasses = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
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
                        } else {
                            // Handle errors
                            Toast.makeText(getContext(), "Failed to load favorites: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return view;
    }
}