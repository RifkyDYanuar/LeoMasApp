package com.leo.leomasapp;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.leo.leomasapp.Adapter.Resettable;


public class SignInFragment extends Fragment implements Resettable {


    private FirebaseAuth auth;
     private FirebaseFirestore db;
    private static final int RC_SIGN_IN = 9001;
    GoogleSignInClient googleSignInClient;
    public SignInFragment() {
        // Required empty public constructor
    }


    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(requireActivity());
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        TextInputEditText Username = view.findViewById(R.id.username);
        TextInputEditText Password = view.findViewById(R.id.password);
        TextView forgotPassword = view.findViewById(R.id.forgot_password);

        Button btnLogin = view.findViewById(R.id.btn_login);
        ImageView Google = view.findViewById(R.id.google);
        FirebaseApp.initializeApp(requireActivity());
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        googleSignInClient =GoogleSignIn.getClient(requireActivity(),options);


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), ForgotPassword.class);
                startActivity(intent);


            }
        });

        Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = googleSignInClient.getSignInIntent();
                mStartForResult.launch(intent);


            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = Username.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (username.isEmpty() && password.isEmpty()){
                    Username.setError("Please, Fill in username!");
                    Username.requestFocus();
                    Password.setError("Please, Fill i password");
                    Password.requestFocus();
                    Toast.makeText(requireActivity(), "please, fill in username and password", Toast.LENGTH_SHORT).show();
                }else if (username.isEmpty()){
                    Username.setError("Please, Fill in username!");
                    Username.requestFocus();
                } else if (password.isEmpty()) {
                    Password.setError("Please, Fill i password");
                    Password.requestFocus();

                }else {
                    loginWithFirestore(username, password);
                }


            }
        });
        return view;
    }
    private void loginWithFirestore(String username, String password) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (db == null) {
            Toast.makeText(requireActivity(), "Firestore belum diinisialisasi!", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("users")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        String email = document.getString("email");

                        if (email != null) {
                            loginWithEmail(email, password);
                        } else {
                            Toast.makeText(requireActivity(), "Email not found!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        loginWithEmail(username, password);
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(requireActivity(), "Kesalahan Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void loginWithEmail(String email, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth == null) {
            Toast.makeText(requireContext(), "FirebaseAuth belum diinisialisasi!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();
                            loadUserData(userId);
                        }
                    } else {
                        if (task.getException() != null && task.getException().getMessage().contains("password is invalid")) {
                            Toast.makeText(requireActivity(), "Password is invalid", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireActivity(), "Username and Password is invalid", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loadUserData(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (db == null) {
            Toast.makeText(requireContext(), "Firestore belum diinisialisasi!", Toast.LENGTH_SHORT).show();
            return;
        }
        db.collection("users").document(userId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String name = document.getString("name");
                            String email = document.getString("email");
                            String username = document.getString("username");
                            String password = document.getString("password");
                            Toast.makeText(requireActivity(), "Login is Succesfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(requireActivity(), MainActivity.class);
                            intent.putExtra("name", name);
                            intent.putExtra("email", email);
                            intent.putExtra("username", username);
                            intent.putExtra("password", password);
                            startActivity(intent);
                            requireActivity().finish();
                        } else {
                            Toast.makeText(requireActivity(), "User data not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireActivity(), "Failed to load user data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(requireActivity(), "Kesalahan Firestore: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }
    @Override
    public void reset() {
        TextInputEditText username = getView().findViewById(R.id.username);
        TextInputEditText password = getView().findViewById(R.id.password);
        if (username !=null)username.setText("");
        if (password !=null)password.setText("");

    }


    private final ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {

                    auth = FirebaseAuth.getInstance();
                    GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                    AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                    auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                auth = FirebaseAuth.getInstance();
                                FirebaseUser currentUser = auth.getCurrentUser();
                                if (currentUser!= null){
                                    String Email = currentUser.getEmail();
                                    String userId = currentUser.getUid();
                                    String Name = currentUser.getDisplayName();

                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection("users")
                                            .document(userId)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> taskGoogle) {
                                                    if (taskGoogle.isSuccessful()) {
                                                        DocumentSnapshot document = taskGoogle.getResult();
                                                        if (document!=null && document.exists()) {
                                                            String name = document.getString("name");
                                                            String email = document.getString("email");
                                                            String username = document.getString("username");
                                                            String password = document.getString("password");
                                                            Toast.makeText(requireActivity(), "Login is Succesfully!", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(requireActivity(), MainActivity.class);
                                                            intent.putExtra("name", name);
                                                            intent.putExtra("email", Email);
                                                            intent.putExtra("username", username);
                                                            intent.putExtra("password", password);
                                                            startActivity(intent);

                                                        }
                                                    }
                                                    else {

                                                    }
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(requireActivity(), "Failed to load user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }



                            }
                        }
                    });
                } catch (ApiException e) {
                    e.printStackTrace();
                    Toast.makeText(requireActivity(), "Google sign-in failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });


}
