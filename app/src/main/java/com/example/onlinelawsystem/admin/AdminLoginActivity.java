package com.example.onlinelawsystem.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.onlinelawsystem.MainActivity;
import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.auth.LoginActivity;
import com.example.onlinelawsystem.databinding.ActivityAdminLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginActivity extends AppCompatActivity {
    ActivityAdminLoginBinding binding;
    ProgressDialog loadingBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        binding.adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.adminLoginEmail.getEditText().getText().toString().trim();
                String password = binding.adminLoginPassword.getEditText().getText().toString().trim();

                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Field is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setMessage("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
                                startActivity(intent);
                                finish();
                                loadingBar.dismiss();
                            } else {
                                String msg = task.getException().getMessage();
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }
}