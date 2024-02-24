package com.example.onlinelawsystem.admin.lawyer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.fragments.AddLawyerFragment;
import com.example.onlinelawsystem.databinding.ActivityEditCourtBinding;
import com.example.onlinelawsystem.databinding.ActivityEditLawyerBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class EditLawyerActivity extends AppCompatActivity {
    ProgressDialog loadingBar;
    String lawyerId;
    ActivityEditLawyerBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference lawyersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditLawyerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadingBar = new ProgressDialog(this);
        lawyersRef = db.collection("Lawyers");
        lawyerId = getIntent().getStringExtra("lawyerId");

        //COURT CATEGORY CODE
        ArrayAdapter<CharSequence> adapter4Category = ArrayAdapter.createFromResource(getApplicationContext(), R.array.court_category, android.R.layout.simple_spinner_item);
        adapter4Category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.courtSpinner.setAdapter(adapter4Category);
        binding.courtSpinner.setOnItemSelectedListener(new CourtSpinner());

        //EXPERTISE CATEGORY CODE
        ArrayAdapter<CharSequence> adapter4expertise = ArrayAdapter.createFromResource(getApplicationContext(), R.array.expertise_category, android.R.layout.simple_spinner_item);
        adapter4Category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.expertiseSpinner.setAdapter(adapter4expertise);
        binding.expertiseSpinner.setOnItemSelectedListener(new ExpertiseSpinner());

        lawyersRef.document(lawyerId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    String name = snapshot.getString("LawyerName");
                    String age = snapshot.getString("LawyerAge");
                    String contact = snapshot.getString("LawyerContact");
                    String court = snapshot.getString("LawyerCourt");
                    String expertise = snapshot.getString("LawyerExpertise");
                    String qualification = snapshot.getString("LawyerQualification");

                    binding.editLawyerFullname.getEditText().setText(name);
                    binding.editLawyerAge.getEditText().setText(age);
                    binding.editLawyerContact.getEditText().setText(contact);
                    binding.editCourtSpinnerText.setText(court);
                    binding.editExpertiseSpinnerText.setText(expertise);
                    binding.editLawyerQualification.getEditText().setText(qualification);
                }
            }
        });

        binding.updateLawyerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editLawyerFullname.getEditText().getText().toString().trim();
                String age = binding.editLawyerAge.getEditText().getText().toString().trim();
                String contact = binding.editLawyerContact.getEditText().getText().toString().trim();
                String qualification = binding.editLawyerQualification.getEditText().getText().toString().trim();
                String court = binding.editCourtSpinnerText.getText().toString().trim();
                String expertise = binding.editExpertiseSpinnerText.getText().toString().trim();

                if (name.isEmpty() || age.isEmpty() || contact.isEmpty() || qualification.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Field's are empty!", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setMessage("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    HashMap lawyerMap = new HashMap();
                    lawyerMap.put("LawyerName", name);
                    lawyerMap.put("LawyerAge", age);
                    lawyerMap.put("LawyerContact", contact);
                    lawyerMap.put("LawyerQualification", qualification);
                    lawyerMap.put("LawyerCourt", court);
                    lawyerMap.put("LawyerExpertise", expertise);
                    lawyersRef.document(lawyerId).update(lawyerMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), name + " Updated", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }

    private class CourtSpinner implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            binding.editCourtSpinnerText.setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class ExpertiseSpinner implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            binding.editExpertiseSpinnerText.setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}