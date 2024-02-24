package com.example.onlinelawsystem.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.onlinelawsystem.MainActivity;
import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    ProgressDialog loadingBar;
    ActivityRegisterBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        usersRef = db.collection("Users");

        binding.registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.registerEmail.getEditText().getText().toString().trim();
                String password = binding.registerPassword.getEditText().getText().toString().trim();
                String confirmPassword = binding.confirmPassword.getEditText().getText().toString().trim();

                if (email.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Field's are empty", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setTitle("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String userID = mAuth.getCurrentUser().getUid();

                            HashMap usersMap = new HashMap();
                            usersMap.put("Email", email);
                            usersMap.put("Password", password);
                            usersMap.put("Image", "default");
                            usersMap.put("UserID", userID);
                            usersMap.put("ProfileUpdated", "NO");
                            usersRef.document(userID).set(usersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                        loadingBar.dismiss();
                                    } else {
                                        String msg = task.getException().getMessage();
                                        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

    }

    public void goToLoginScreen(View view) {
        finish();
    }
}