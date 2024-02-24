package com.example.onlinelawsystem.admin.casestudy;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinelawsystem.databinding.ActivityEditCasestudyBinding;
import com.example.onlinelawsystem.helper.UniqueIdGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class EditCasestudyActivity extends AppCompatActivity {

    String caseStudyID;
    ProgressDialog loadingBar;
    ActivityEditCasestudyBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference caseStudyRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditCasestudyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        caseStudyID = getIntent().getStringExtra("caseStudyID");

        loadingBar = new ProgressDialog(this);
        caseStudyRef = db.collection("CaseStudy");

        caseStudyRef.document(caseStudyID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    String title = snapshot.getString("CaseStudyTitle");
                    String desc = snapshot.getString("CaseStudyDescription");
                    String issues = snapshot.getString("CaseStudyIssues");
                    String court = snapshot.getString("CaseStudyCourt");
                    String outcome = snapshot.getString("CaseStudyVerdict");

                    binding.editCaseStudyTitle.getEditText().setText(title);
                    binding.editCaseStudyDescription.getEditText().setText(desc);
                    binding.editCaseStudyCourtName.getEditText().setText(issues);
                    binding.editCaseStudyLegalIssues.getEditText().setText(court);
                    binding.editCaseStudyVerdict.getEditText().setText(outcome);
                }
            }
        });

        binding.updateCaseStudies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.editCaseStudyTitle.getEditText().getText().toString().trim();
                String description = binding.editCaseStudyDescription.getEditText().getText().toString().trim();
                String issues = binding.editCaseStudyLegalIssues.getEditText().getText().toString().trim();
                String court = binding.editCaseStudyCourtName.getEditText().getText().toString().trim();
                String verdict = binding.editCaseStudyVerdict.getEditText().getText().toString().trim();

                if (title.isEmpty() || description.isEmpty() || issues.isEmpty() || court.isEmpty() || verdict.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Field's are empty!", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setMessage("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    HashMap caseStudyMap = new HashMap();
                    caseStudyMap.put("CaseStudyTitle", title);
                    caseStudyMap.put("CaseStudyDescription", description);
                    caseStudyMap.put("CaseStudyIssues", issues);
                    caseStudyMap.put("CaseStudyCourt", court);
                    caseStudyMap.put("CaseStudyVerdict", verdict);
                    caseStudyRef.document(caseStudyID).update(caseStudyMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), title + " updated", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }
}