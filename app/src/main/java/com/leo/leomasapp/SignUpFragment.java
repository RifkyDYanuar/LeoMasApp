package com.leo.leomasapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.leo.leomasapp.Adapter.Resettable;
import com.leo.leomasapp.Data.DataClass;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment implements Resettable {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        TextInputEditText Name = view.findViewById(R.id.name);
        TextInputEditText Email = view.findViewById(R.id.email);
        TextInputEditText Username = view.findViewById(R.id.username);
        TextInputEditText Password = view.findViewById(R.id.password);
        Button Registrasi = view.findViewById(R.id.btn_register);

        Registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString().trim();
                String email = Email.getText().toString();
                String username = Username.getText().toString();
                String password = Password.getText().toString().trim();


                if (name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()){
                    if (name.isEmpty())Name.setError("Name is Required");
                    if (email.isEmpty())Email.setError("Email is Required");
                    if (username.isEmpty())Username.setError("Username is Required");
                    if (password.isEmpty())Password.setError("Password is Required");

                }else {
                    RegisterUser(name, email, username, password);
                    Name.setText("");
                    Email.setText("");
                    Username.setText("");
                    Password.setText("");
                }

            }
        });

        return view;
    }

    @Override
    public void reset() {
        TextInputEditText Name = getView().findViewById(R.id.name);
        TextInputEditText Email = getView().findViewById(R.id.email);
        TextInputEditText Username = getView().findViewById(R.id.username);
        TextInputEditText Password = getView().findViewById(R.id.password);

        if (Name !=null)Name.setText("");
        if (Email !=null)Email.setText("");
        if (Username !=null)Username.setText("");
        if (Password !=null)Password.setText("");

    }

    public void RegisterUser(String name, String email, String username, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            saveData(user.getUid(), name, email, username, password);
                        }
                    } else {
                        Toast.makeText(requireActivity(), "Gagal mendaftar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void saveData(String userId, String name, String email, String username, String password) {
        DataClass dataClass = new DataClass(name, email, username, password);
        db.collection("users").document(userId)
                .set(dataClass, SetOptions.merge())
                .addOnSuccessListener(unused -> {
                    Toast.makeText(requireActivity(), "Berhasil mendaftar dan menyimpan data.", Toast.LENGTH_SHORT).show();

                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireActivity(), "Gagal menyimpan data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


}