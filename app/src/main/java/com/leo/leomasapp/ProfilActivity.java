package com.leo.leomasapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.leo.leomasapp.Data.DataClass;

public class ProfilActivity extends AppCompatActivity {
    TextView fullName, username, email;
    Button Edit;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profil);
        fullName = findViewById(R.id.full_name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        Button Edit = findViewById(R.id.update_profil);

        DataClass data = (DataClass) getIntent().getSerializableExtra("data");
        if (data != null){
            fullName.setText(data.getName());
            username.setText(data.getUsername());
            email.setText(data.getEmail());
        }
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSheet();
            }
        });
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            finish();
        });

    }


    private void showSheet(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.changes_bottomsheet, null);
        TextInputEditText fullNameInput = view.findViewById(R.id.name);
        TextInputEditText usernameInput = view.findViewById(R.id.username);
        TextInputEditText emailInput = view.findViewById(R.id.email);
        Button Edit = view.findViewById(R.id.fix_changes);
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        DataClass currentUser = (DataClass) getIntent().getSerializableExtra("data");
        if (currentUser != null) {
            fullNameInput.setText(currentUser.getName());
            usernameInput.setText(currentUser.getUsername());
            emailInput.setText(currentUser.getEmail());
        }

        // Handle Save Button Click
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedFullName = fullNameInput.getText().toString().trim();
                String updatedUsername = usernameInput.getText().toString().trim();
                String updatedEmail = emailInput.getText().toString().trim();

                // Validate input fields
                if (updatedFullName.isEmpty() || updatedUsername.isEmpty() || updatedEmail.isEmpty()) {
                    fullNameInput.setError("Field cannot be empty");
                    usernameInput.setError("Field cannot be empty");
                    emailInput.setError("Field cannot be empty");
                    return;
                }

                // Update data in Firebase
                FirebaseAuth auth = FirebaseAuth.getInstance();
                String userId = auth.getCurrentUser().getUid(); // Get current user's UID
                FirebaseFirestore db = FirebaseFirestore.getInstance(); // Initialize Firestore

                // Prepare updated dat


//                Erir disini
                // Save changes to Firestore
                db.collection("users").document(userId)
                        .update(
                                "name", updatedFullName,
                                "username", updatedUsername,
                                "email", updatedEmail
                        )
                        .addOnSuccessListener(unused -> {
                            // Update UI with new data
                            ProfilActivity.this.fullName.setText(updatedFullName);
                            ProfilActivity.this.username.setText(updatedUsername);
                            ProfilActivity.this.email.setText(updatedEmail);

                            Toast.makeText(ProfilActivity.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                            bottomSheetDialog.dismiss();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(ProfilActivity.this, "Failed to update profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });

            }
        });
        bottomSheetDialog.setContentView(view);
        FrameLayout bottomSheet = bottomSheetDialog.findViewById(R.id.bottom_sheet);
        BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetDialog.show();

    }
}