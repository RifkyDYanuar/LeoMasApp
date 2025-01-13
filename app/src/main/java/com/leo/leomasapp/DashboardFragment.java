package com.leo.leomasapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.leo.leomasapp.Adapter.ProductAdapter;
import com.leo.leomasapp.Data.DataClass;
import com.leo.leomasapp.Data.ProductClass;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        TextView NameProduct = view.findViewById(R.id.name_product);
        TextView NameProduct2 = view.findViewById(R.id.name_product_2);
        TextView viewDetail = view.findViewById(R.id.view_detail);
        TextView viewDetail2 = view.findViewById(R.id.view_detail2);
        LinearLayout showProduct = view.findViewById(R.id.show_product);
        LinearLayout showHistory = view.findViewById(R.id.show_history);
        LinearLayout showFavorite = view.findViewById(R.id.show_favorite);
        TextView viewAll = view.findViewById(R.id.view_all);

        showProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), AllProduct.class);
                startActivity(intent);
            }
        });
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), AllProduct.class);
                startActivity(intent);
            }
        });
        showHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), HistoryActivity.class);
                startActivity(intent);
            }
        });
        showFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), FavoriteActivity.class);
                startActivity(intent);
            }
        });


        Bundle bundle = getArguments();
        if (bundle != null){
            DataClass data = (DataClass) bundle.getSerializable("data");

            if (data != null){
                TextView showUsername = view.findViewById(R.id.username);
                TextView showFullname = view.findViewById(R.id.full_name);
                String username = data.getUsername();
                String fullname = data.getName();
                showUsername.setText(username);
                showFullname.setText(fullname);
            }
        }
        TextView showUsername = view.findViewById(R.id.username);
        TextView showFullname = view.findViewById(R.id.full_name);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userId = auth.getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(userId)
                .addSnapshotListener((snapshot, e) -> {
                    if (snapshot != null && snapshot.exists()) {
                        String fullName = snapshot.getString("name");
                        String username = snapshot.getString("username");
                        String email = snapshot.getString("email");

                        showFullname.setText(fullName);
                        showUsername.setText(username);

                    } else {
                        Log.d("Dashboard", "Current data: null");
                    }
                });


        RecyclerView ringsRecyle = view.findViewById(R.id.rings_recyle);
        RecyclerView necklaceRecyle = view.findViewById(R.id.nekclace_recyle);
        ringsRecyle.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        necklaceRecyle.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        List<ProductClass> ringsList = new ArrayList<>();
        List<ProductClass> necklaceList = new ArrayList<>();

        viewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db_popular = FirebaseFirestore.getInstance();
                db_popular.collection("product")
                        .whereEqualTo("kode_product", "LMR0001")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot snapshot : task.getResult()){
                                    String nameProduct = snapshot.getString("name_product");
                                    String kodeProduct = snapshot.getString("kode_product");
                                    Long priceProduct = snapshot.getLong("price_product");
                                    String detailProduct = snapshot.getString("detail_product");
                                    String jenisProduct = snapshot.getString("jenis_product");
                                    int imageProduct1, imageProduct2, imageProduct3 ;
                                    if (kodeProduct.equalsIgnoreCase("LMR0001")){
                                        NameProduct.setText(nameProduct);
                                        imageProduct1 = R.drawable.diamond_solitarie_maya_1;
                                        imageProduct2 = R.drawable.diamond_solitarie_maya_2;
                                        imageProduct3 = R.drawable.diamond_solitarie_maya_3;
                                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                                        intent.putExtra("nameProduct", nameProduct);
                                        intent.putExtra("imageProduct1", imageProduct1);
                                        intent.putExtra("imageProduct2", imageProduct2);
                                        intent.putExtra("imageProduct3", imageProduct3);
                                        intent.putExtra("priceProduct", priceProduct);
                                        intent.putExtra("detailProduct", detailProduct);
                                        intent.putExtra("jenisProduct", jenisProduct);
                                        startActivity(intent);

                                    }
                                }

                            }
                        });
            }
        });
        viewDetail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db_popular = FirebaseFirestore.getInstance();
                db_popular.collection("product").whereEqualTo("kode_product", "LMN0004")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot snapshot : task.getResult()){

                                    String nameProduct = snapshot.getString("name_product");
                                    String kodeProduct = snapshot.getString("kode_product");
                                    Long priceProduct = snapshot.getLong("price_product");
                                    String detailProduct = snapshot.getString("detail_product");
                                    String jenisProduct = snapshot.getString("jenis_product");
                                    int imageProduct1, imageProduct2, imageProduct3 ;
                                     if (kodeProduct.equalsIgnoreCase("LMN0004")){
                                        imageProduct1 = R.drawable.diamond_necklace_ashoka_lwf01005_1;
                                        imageProduct2 = R.drawable.diamond_necklace_ashoka_lwf01005_2;
                                        imageProduct3 = R.drawable.diamond_necklace_ashoka_lwf01005_3;
                                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                                        intent.putExtra("nameProduct", nameProduct);
                                        intent.putExtra("imageProduct1", imageProduct1);
                                        intent.putExtra("imageProduct2", imageProduct2);
                                        intent.putExtra("imageProduct3", imageProduct3);
                                        intent.putExtra("priceProduct", priceProduct);
                                        intent.putExtra("detailProduct", detailProduct);
                                        intent.putExtra("jenisProduct", jenisProduct);
                                        startActivity(intent);
                                        NameProduct2.setText(nameProduct);


                                    }
                                }

                            }
                        });
            }
        });


        ProductAdapter productAdapter = new ProductAdapter(ringsList);
        ProductAdapter productAdapter2 = new ProductAdapter(necklaceList);
        ringsRecyle.setAdapter(productAdapter);
        necklaceRecyle.setAdapter(productAdapter2);

        FirebaseFirestore db_product = FirebaseFirestore.getInstance();


//
        db_product.collection("product")
                .whereIn("kode_product", Arrays.asList("LMR0001","LMR0002","LMR0003", "LMN0001", "LMN0002", "LMN0003"))
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

                                    case "LMN0001":
                                        imageProduct1 = R.drawable.diamond_necklace_te_amo_lwf0689_rv;
                                        imageProduct2 = R.drawable.diamond_necklace_teamolwf0689_2;
                                        imageProduct3 = R.drawable.diamond_necklace_teamolwf0689_3;
                                        break;
                                    case "LMN0002":
                                        imageProduct1 = R.drawable.fuchsia_gold_diamond_necklace_lws0189_rv;
                                        imageProduct2 = R.drawable.fuchsia_gold_diamond_necklace_lws0189_2;
                                        imageProduct3 = R.drawable.fuchsia_gold_diamond_necklace_lws0189_3;
                                        break;
                                    case "LMN0003":
                                        imageProduct1 = R.drawable.disney_diamond_ladies_necklace_dis_lwf1429_rv;
                                        imageProduct3 = R.drawable.disney_diamond_ladies_necklace_dislwf1429_2;
                                        imageProduct2 = R.drawable.disney_diamond_ladies_necklace_dislwf1429_3;

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
                                if ("Rings".equalsIgnoreCase(jenisProduct)){
                                    ringsList.add(productClass);

                                }else if ("Necklace".equalsIgnoreCase(jenisProduct)){
                                    necklaceList.add(productClass);

                                }
                                NameProduct2.setText(nameProduct);
                                NameProduct.setText(nameProduct);



                            }
                            productAdapter2.notifyDataSetChanged();
                            productAdapter.notifyDataSetChanged();

                        }
                    }
                });
        showPopular(view);
        return view;
    }
    private void showPopular(View view){
        TextView NameProduct = view.findViewById(R.id.name_product);
        TextView NameProduct2 = view.findViewById(R.id.name_product_2);
        FirebaseFirestore db_popular = FirebaseFirestore.getInstance();
        db_popular.collection("product").whereIn("kode_product", Arrays.asList("LMR0001", "LMN0004"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       for (QueryDocumentSnapshot snapshot : task.getResult()){
                           String nameProduct = snapshot.getString("name_product");
                           String kodeProduct = snapshot.getString("kode_product");
                           if (kodeProduct.equalsIgnoreCase("LMR0001")){
                               NameProduct.setText(nameProduct);

                           }else if (kodeProduct.equalsIgnoreCase("LMN0004")){
                               NameProduct2.setText(nameProduct);
                           }
                       }

                    }
                });
    }

}
