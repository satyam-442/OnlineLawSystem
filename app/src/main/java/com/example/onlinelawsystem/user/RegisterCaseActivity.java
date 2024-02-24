package com.example.onlinelawsystem.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.fragments.AddLawyerFragment;
import com.example.onlinelawsystem.databinding.ActivityRegisterCaseBinding;
import com.example.onlinelawsystem.databinding.FragmentAddLawyerBinding;
import com.example.onlinelawsystem.helper.UniqueIdGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterCaseActivity extends AppCompatActivity {

    ActivityRegisterCaseBinding binding;
    ProgressDialog loadingBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference casesRef;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterCaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        casesRef = db.collection("PublicCases");

        ArrayAdapter<CharSequence> adapter4expertise = ArrayAdapter.createFromResource(this, R.array.expertise_category, android.R.layout.simple_spinner_item);
        adapter4expertise.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.expertiseSpinner.setAdapter(adapter4expertise);
        binding.expertiseSpinner.setOnItemSelectedListener(new ExpertiseSpinner());

        binding.addCaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.userFullname.getEditText().getText().toString().trim();
                String age = binding.userAge.getEditText().getText().toString().trim();
                String address = binding.userAddress.getEditText().getText().toString().trim();
                String contact = binding.userContact.getEditText().getText().toString().trim();
                String detail = binding.caseDetail.getEditText().getText().toString().trim();
                String expertise = binding.expertiseSpinnerText.getText().toString().trim();
                String caseID = UniqueIdGenerator.generatePublicCaseID();
                String userID = mAuth.getCurrentUser().getUid();

                if (name.isEmpty() || age.isEmpty() || contact.isEmpty() || address.isEmpty() || detail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Field's are empty...", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setMessage("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    HashMap publicCaseMap = new HashMap();
                    publicCaseMap.put("PublicCaseName", name);
                    publicCaseMap.put("PublicCaseAge", age);
                    publicCaseMap.put("PublicCaseContact", contact);
                    publicCaseMap.put("PublicCaseAddress", address);
                    publicCaseMap.put("PublicCaseDetail", detail);
                    publicCaseMap.put("PublicCaseExpertise", expertise);
                    publicCaseMap.put("UserID", userID);
                    publicCaseMap.put("PublicCaseStatus", "Raised");
                    publicCaseMap.put("PublicCaseID", caseID);
                    casesRef.document(caseID).set(publicCaseMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Case Added", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                clearFields();
                            }
                        }
                    });
                }
            }
        });

    }

    private void clearFields() {
        binding.userFullname.getEditText().setText("");
        binding.userAge.getEditText().setText("");
        binding.userAddress.getEditText().setText("");
        binding.userContact.getEditText().setText("");
        binding.caseDetail.getEditText().setText("");

        binding.userFullname.getEditText().requestFocus();
    }

    private class ExpertiseSpinner implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            binding.expertiseSpinnerText.setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}