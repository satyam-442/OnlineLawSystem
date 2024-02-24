package com.example.onlinelawsystem.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.onlinelawsystem.MainActivity;
import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.AdminMainActivity;
import com.example.onlinelawsystem.lawyer.LawyerMainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mAuth = FirebaseAuth.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);

        // Retrieve user ID
        String userID = sharedPreferences.getString("user_id", null);

        // Check if the user is logged in
        if (userID != null) {
            // User is logged in, perform necessary actions
            Intent intent = new Intent(getApplicationContext(), LawyerMainActivity.class);
            startActivity(intent);
        } else {
            // User is not logged in, handle accordingly
        }


    }

    public void goToLoginScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null && mAuth.getCurrentUser().getUid().equals("JFyJNoAd4WTzSiB4nBK0WLjtPu52")) {
            Intent intent = new Intent(getApplicationContext(), AdminMainActivity.class);
            startActivity(intent);
        } else if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }
}