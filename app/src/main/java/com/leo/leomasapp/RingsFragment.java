package com.leo.leomasapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.leo.leomasapp.Adapter.AllProductAdapter;
import com.leo.leomasapp.Data.ProductClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RingsFragment extends Fragment {



    public RingsFragment() {
        // Required empty public constructor
    }


    public static RingsFragment newInstance(String param1, String param2) {
        RingsFragment fragment = new RingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_rings, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rings_rv);
        List<ProductClass> productClassList = new ArrayList<>();
        AllProductAdapter productAdapter = new AllProductAdapter(productClassList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(productAdapter);
        FirebaseFirestore db_product = FirebaseFirestore.getInstance();
        db_product.collection("product")
                .whereEqualTo("jenis_product", "Rings")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String kodeProduct = document.getString("kode_product");
                                String nameProduct = document.getString("name_product");
                                Long priceProduct = document.getLong("price_product");
                                String detailProduct = document.getString("detail_product");
                                String jenisProduct = document.getString("jenis_product");

                                int imageProduct1, imageProduct2, imageProduct3 ;
                                switch (kodeProduct) {
                                    case "LMR0001":
                                        imageProduct1 = R.drawable.diamond_solitarie_maya_1;
                                        imageProduct2 = R.drawable.diamond_solitarie_maya_2;
                                        imageProduct3 = R.drawable.diamond_solitarie_maya_3;
                                        break;
                                    case "LMR0002":
                                        imageProduct1 = R.drawable.diamond_ring_solitaire_round_rv;
                                        imageProduct2 = R.drawable.diamond_ring_solitaire_round_rv;
                                        imageProduct3 = R.drawable.diamond_ring_solitaire_round_rv;
                                        break;
                                    case "LMR0003":
                                        imageProduct1 = R.drawable.diamond_sing_solitaire_round_cws0282_rv;
                                        imageProduct2 = R.drawable.diamond_sing_solitaire_round_cws0282_rv_2;
                                        imageProduct3 = R.drawable.diamond_sing_solitaire_round_cws0282_rv_3;
                                        break;

                                    default:
                                        imageProduct1 = R.drawable.ic_launcher_background;
                                        imageProduct2 = R.drawable.ic_launcher_background;  // Default for imageProduct2
                                        imageProduct3 = R.drawable.ic_launcher_background;

                                }
                                ProductClass productClass = new ProductClass(
                                        nameProduct,
                                        priceProduct,
                                        detailProduct,
                                        jenisProduct,
                                        imageProduct1,
                                        imageProduct2,
                                        imageProduct3);

                                productClassList.add(productClass);




                            }
                            productAdapter.notifyDataSetChanged();

                        }
                    }
                });
        return view;
    }
}