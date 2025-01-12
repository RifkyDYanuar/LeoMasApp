package com.leo.leomasapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.leo.leomasapp.Data.DataClass;


public class AccountFragment extends Fragment {


    public AccountFragment() {
    }

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        Bundle bundle1 = getArguments();
        if (bundle1 != null){
            DataClass data = (DataClass) bundle1.getSerializable("data");

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
        LinearLayout myProfil = view.findViewById(R.id.my_profile);
        LinearLayout myFavorite = view.findViewById(R.id.my_favorite);
        LinearLayout myHistory = view.findViewById(R.id.my_history);
        LinearLayout aboutUs = view.findViewById(R.id.about_us);
        LinearLayout contactUs = view.findViewById(R.id.contact_us);
        LinearLayout policies = view.findViewById(R.id.policies);
        LinearLayout logout = view.findViewById(R.id.logout);

        myProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bundle1 != null){
                    DataClass data = (DataClass) bundle1.getSerializable("data");

                    if (data != null){
                        Intent intent = new Intent(requireActivity(), ProfilActivity.class);
                        intent.putExtra("data",data);
                        startActivity(intent);
                    }
                }

            }
        });
        myFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), FavoriteActivity.class);
                startActivity(intent);
            }
        });
        myHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), HistoryActivity.class);
                startActivity(intent);
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), AboutUsAcitvity.class);
                startActivity(intent);
            }
        });
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), ContactUsActivity.class);
                startActivity(intent);
            }
        });
        policies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), TermActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Logout")
                        .setMessage("Apakah Anda Yakin Ingin Logout?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        auth.signOut();
                        Intent intent = new Intent(requireActivity(), SigInSignUp.class);
                               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                               startActivity(intent);
                            Toast.makeText(requireActivity(), "Logout Berhasil", Toast.LENGTH_SHORT).show();
                               requireActivity().finish();
///                      googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Intent intent = new Intent(requireActivity(), SigInSignUp.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                                Toast.makeText(requireActivity(), "Logout Berhasil", Toast.LENGTH_SHORT).show();
//                                requireActivity().finish();
//                            }
//                        });

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(requireActivity(),  "Logout Dibatalkan", Toast.LENGTH_SHORT).show();
                    }
                });

                final  AlertDialog dialog = builder.create();
                dialog.show();
            }

        });



        return view;
    }
}