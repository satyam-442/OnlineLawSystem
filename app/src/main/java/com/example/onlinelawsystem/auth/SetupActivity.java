package com.example.onlinelawsystem.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.onlinelawsystem.MainActivity;
import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.databinding.ActivityRegisterBinding;
import com.example.onlinelawsystem.databinding.ActivitySetupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SetupActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    ProgressDialog loadingBar;
    ActivitySetupBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef;
    String genderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        usersRef = db.collection("Users");

        binding.setupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = binding.setupFirstName.getEditText().getText().toString().trim();
                String middleName = binding.setupMiddleName.getEditText().getText().toString().trim();
                String lastName = binding.setupLastName.getEditText().getText().toString().trim();
                String phone = binding.setupPhone.getEditText().getText().toString().trim();
                String city = binding.setupCity.getEditText().getText().toString().trim();
                String address = binding.setupAddress.getEditText().getText().toString().trim();
                String gender = binding.setupGender.getEditText().getText().toString().trim();

                if (firstName.isEmpty() || middleName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || city.isEmpty() || address.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Field's are empty", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setTitle("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    String userID = mAuth.getCurrentUser().getUid();

                    HashMap usersMap = new HashMap();
                    usersMap.put("FirstName", firstName);
                    usersMap.put("MiddleName", middleName);
                    usersMap.put("LastName", lastName);
                    usersMap.put("Phone", phone);
                    usersMap.put("City", city);
                    usersMap.put("Address", address);
                    usersMap.put("Gender", gender);
                    usersMap.put("ProfileUpdated", "YES");
                    usersRef.document(userID).update(usersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Account updated successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                                loadingBar.dismiss();
                            } else {
                                String msg = task.getException().getMessage();
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
                }
            }
        });

        // Set a listener for the RadioGroup
        binding.genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected
                RadioButton radioButton = findViewById(checkedId);
                if (radioButton != null) {
                    // Update the EditText with the selected gender
                    binding.setupGender.getEditText().setText(radioButton.getText());
                    Toast.makeText(SetupActivity.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}