package com.example.onlinelawsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.onlinelawsystem.auth.LoginActivity;
import com.example.onlinelawsystem.auth.SetupActivity;
import com.example.onlinelawsystem.databinding.ActivityMainBinding;
import com.example.onlinelawsystem.user.AboutUsActivity;
import com.example.onlinelawsystem.user.CaseStudySearchActivity;
import com.example.onlinelawsystem.user.CourtSearchActivity;
import com.example.onlinelawsystem.user.CriminalSearchActivity;
import com.example.onlinelawsystem.user.LawBookSearchActivity;
import com.example.onlinelawsystem.user.LawSearchActivity;
import com.example.onlinelawsystem.user.LawyerSearchActivity;
import com.example.onlinelawsystem.user.ProfileActivity;
import com.example.onlinelawsystem.user.RegisterCaseActivity;
import com.example.onlinelawsystem.user.ViewCasesActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef;
    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        usersRef = db.collection("Users");

        usersRef.document(currentUserID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    String profileStatus = snapshot.getString("ProfileUpdated");

                    if (profileStatus.equals("NO")) {
                        // Call this method to show the AlertDialog
                        showUpdateAddressDialog();
                        binding.welcomeText.setText("Welcome User,");
                    } else {
                        String userName = snapshot.getString("FirstName");
                        binding.welcomeText.setText("Welcome "+ userName +",");
                    }
                }
            }
        });

        binding.logoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void showUpdateAddressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Profile");
        builder.setMessage("Profile not updated. Want to update?");

        // "Yes" button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), SetupActivity.class);
                startActivity(intent);
            }
        });

        // "Later" button
        builder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle the "Later" button click or dismiss the dialog
                dialog.dismiss();
            }
        });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void goToCourtSection(View view) {
        Intent intent = new Intent(this, CourtSearchActivity.class);
        startActivity(intent);
    }

    public void goToLawSearch(View view) {
        Intent intent = new Intent(this, LawSearchActivity.class);
        startActivity(intent);
    }

    public void goToLawyerSection(View view) {
        Intent intent = new Intent(this, LawyerSearchActivity.class);
        startActivity(intent);
    }

    public void goToCriminalSearch(View view) {
        Intent intent = new Intent(this, CriminalSearchActivity.class);
        startActivity(intent);
    }

    public void goToCaseStudySection(View view) {
        Intent intent = new Intent(this, CaseStudySearchActivity.class);
        startActivity(intent);
    }

    public void goToLawBooksSearch(View view) {
        Intent intent = new Intent(this, LawBookSearchActivity.class);
        startActivity(intent);
    }

    public void goToProfileSection(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void goToAboutUsSection(View view) {
        Intent intent = new Intent(this, AboutUsActivity.class);
        startActivity(intent);
    }

    public void goToCaseRegisterSection(View view) {
        Intent intent = new Intent(this, RegisterCaseActivity.class);
        startActivity(intent);
    }

    public void goToRegisteredCasesSection(View view) {
        Intent intent = new Intent(this, ViewCasesActivity.class);
        startActivity(intent);
    }
}