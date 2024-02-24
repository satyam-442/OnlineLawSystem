package com.example.onlinelawsystem.lawyer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.auth.LoginActivity;
import com.example.onlinelawsystem.databinding.ActivityLawyerMainBinding;

public class LawyerMainActivity extends AppCompatActivity {

    ActivityLawyerMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLawyerMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.lawyerLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // Clear all stored information
                editor.clear();

                // Commit the changes
                editor.apply();

                // Navigate to the login page
                Intent intent = new Intent(LawyerMainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear the back stack
                startActivity(intent);
                finish(); // Finish the current activity
            }
        });

    }

    public void goToProfileSection(View view) {
        Intent intent = new Intent(getApplicationContext(), LawyerProfileActivity.class);
        startActivity(intent);
    }

    public void goToPublicCasesSection(View view) {
        Intent intent = new Intent(getApplicationContext(), LawyersCasesActivity.class);
        startActivity(intent);
    }
}