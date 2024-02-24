package com.example.onlinelawsystem.lawyer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinelawsystem.databinding.ActivityLawyerLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LawyerLoginActivity extends AppCompatActivity {

    ProgressDialog loadingBar;
    ActivityLawyerLoginBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLawyerLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadingBar = new ProgressDialog(this);
        usersRef = db.collection("Lawyers");

        binding.lawyerLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = binding.lawyerLoginID.getEditText().getText().toString().trim();
                String password = binding.lawyerLoginPassword.getEditText().getText().toString().trim();

                if (userID.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LawyerLoginActivity.this, "Field's are empty...", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setTitle("please wait...");
                    loadingBar.show();
                    usersRef.document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                DocumentSnapshot snapshot = task.getResult();
                                String userID = snapshot.getString("LawyerID");
                                String password = snapshot.getString("Password");
                                String enteredPwd = binding.lawyerLoginPassword.getEditText().getText().toString().trim();

                                if (snapshot.exists()){
                                    if (enteredPwd.equals(password)){
                                        Intent intent = new Intent(getApplicationContext(), LawyerMainActivity.class);
                                        startActivity(intent);
                                        loadingBar.dismiss();

                                        SharedPreferences sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        // Assuming userID is the unique identifier for the user
                                        editor.putString("user_id", userID);

                                        // Add other details as needed
                                        // editor.putString("email", userEmail);

                                        // Commit the changes
                                        editor.apply();

                                    } else {
                                        Toast.makeText(LawyerLoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(LawyerLoginActivity.this, "User ID doesn't exist", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });
    }
}