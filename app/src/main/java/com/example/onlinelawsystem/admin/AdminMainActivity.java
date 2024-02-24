package com.example.onlinelawsystem.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.casestudy.AdminCaseStudyActivity;
import com.example.onlinelawsystem.admin.court.AdminCourtsActivity;
import com.example.onlinelawsystem.admin.criminal.AdminCriminalActivity;
import com.example.onlinelawsystem.admin.lawbooks.AdminLawBookActivity;
import com.example.onlinelawsystem.admin.laws.AdminLawActivity;
import com.example.onlinelawsystem.admin.lawyer.AdminLawyerActivity;
import com.example.onlinelawsystem.auth.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AdminMainActivity extends AppCompatActivity {
    
    FirebaseAuth mAuth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        mAuth = FirebaseAuth.getInstance();
    }
    public void goToCourtScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), AdminCourtsActivity.class);
        startActivity(intent);
    }
    public void goToLawyerScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), AdminLawyerActivity.class);
        startActivity(intent);
    }

    public void goToLawScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), AdminLawActivity.class);
        startActivity(intent);
    }

    public void goToCriminalScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), AdminCriminalActivity.class);
        startActivity(intent);
    }

    public void goToCaseStudyScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), AdminCaseStudyActivity.class);
        startActivity(intent);
    }

    public void goToLawBooksScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), AdminLawBookActivity.class);
        startActivity(intent);
    }

    public void logoutAdmin(View view) {
        mAuth.signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}