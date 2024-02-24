package com.example.onlinelawsystem.lawyer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinelawsystem.databinding.ActivityLawyerProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LawyerProfileActivity extends AppCompatActivity {

    ActivityLawyerProfileBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference lawyersRef;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLawyerProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
        // Retrieve user ID
        String userID = sharedPreferences.getString("user_id", null);

        loadingBar = new ProgressDialog(this);
        lawyersRef = db.collection("Lawyers");

        lawyersRef.document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    String age = snapshot.getString("LawyerAge");
                    String name = snapshot.getString("LawyerName");
                    String email = snapshot.getString("LawyerEmail");
                    String court = snapshot.getString("LawyerCourt");
                    String contact = snapshot.getString("LawyerContact");
                    String expertise = snapshot.getString("LawyerExpertise");
                    String qualification = snapshot.getString("LawyerQualification");

                    binding.lawyerAge.setText("Age: "+age);
                    binding.lawyerName.setText("Name: "+name);
                    binding.lawyerEmail.setText("Email: "+email);
                    binding.lawyerCourt.setText("Court: "+court);
                    binding.lawyerContact.setText("Contact: "+contact);
                    binding.lawyerExpertise.setText("Expertise: "+expertise);
                    binding.lawyerQualification.setText("Qualification: "+qualification);
                }
            }
        });
    }
}