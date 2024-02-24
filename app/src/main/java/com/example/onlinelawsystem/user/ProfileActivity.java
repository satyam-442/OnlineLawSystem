package com.example.onlinelawsystem.user;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.onlinelawsystem.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    ProgressDialog loadingBar;
    ActivityProfileBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference usersRef;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        usersRef = db.collection("Users");

        userID = mAuth.getCurrentUser().getUid();

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = binding.updateFirstName.getEditText().getText().toString().trim();
                String middleName = binding.updateMiddleName.getEditText().getText().toString().trim();
                String lastName = binding.updateLastName.getEditText().getText().toString().trim();
                String phone = binding.updatePhone.getEditText().getText().toString().trim();
                String city = binding.updateCity.getEditText().getText().toString().trim();
                String address = binding.updateAddress.getEditText().getText().toString().trim();
                String gender = binding.updateGender.getEditText().getText().toString().trim();

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

        binding.genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected
                RadioButton radioButton = findViewById(checkedId);
                if (radioButton != null) {
                    // Update the EditText with the selected gender
                    binding.updateGender.getEditText().setText(radioButton.getText());
                }
            }
        });

        usersRef.document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot snapshot = task.getResult();
                String name = snapshot.getString("FirstName");
                String middle = snapshot.getString("MiddleName");
                String lastname = snapshot.getString("LastName");
                String phone = snapshot.getString("Phone");
                String address = snapshot.getString("Address");
                String city = snapshot.getString("City");
                String gender = snapshot.getString("Gender");

                binding.updateFirstName.getEditText().setText(name);
                binding.updateMiddleName.getEditText().setText(middle);
                binding.updateLastName.getEditText().setText(lastname);
                binding.updatePhone.getEditText().setText(phone);
                binding.updateCity.getEditText().setText(city);
                binding.updateAddress.getEditText().setText(address);
                binding.updateGender.getEditText().setText(gender);

                if (gender.equals("Male")){
                    binding.updateMale.setChecked(true);
                } else if (gender.equals("Female")){
                    binding.updateFemale.setChecked(true);
                } else {
                    binding.updateOther.setChecked(true);
                }
            }
        });

    }
}