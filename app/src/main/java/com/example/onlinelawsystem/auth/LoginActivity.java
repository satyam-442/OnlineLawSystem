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
import com.example.onlinelawsystem.admin.AdminLoginActivity;
import com.example.onlinelawsystem.databinding.ActivityLoginBinding;
import com.example.onlinelawsystem.lawyer.LawyerLoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth mAuth;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.loginEmail.getEditText().getText().toString().trim();
                String password = binding.loginPassword.getEditText().getText().toString().trim();

                if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Field is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setMessage("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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

    public void goToRegisterScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    public void goToAdminLoginScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), AdminLoginActivity.class);
        startActivity(intent);
    }

    public void goToLawyerLoginScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), LawyerLoginActivity.class);
        startActivity(intent);
    }
}