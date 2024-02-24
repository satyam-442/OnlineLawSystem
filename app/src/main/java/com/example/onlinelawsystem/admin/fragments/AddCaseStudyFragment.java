package com.example.onlinelawsystem.admin.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.onlinelawsystem.databinding.FragmentAddCaseStudyBinding;
import com.example.onlinelawsystem.databinding.FragmentAddCourtBinding;
import com.example.onlinelawsystem.helper.UniqueIdGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddCaseStudyFragment extends Fragment {

    FragmentAddCaseStudyBinding binding;
    ProgressDialog loadingBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference caseStudyRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddCaseStudyBinding.inflate(inflater, container, false);

        loadingBar = new ProgressDialog(getContext());
        caseStudyRef = db.collection("CaseStudy");

        binding.addCaseStudies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.caseStudyTitle.getEditText().getText().toString().trim();
                String description = binding.caseStudyDescription.getEditText().getText().toString().trim();
                String issues = binding.caseStudyLegalIssues.getEditText().getText().toString().trim();
                String court = binding.caseStudyCourtName.getEditText().getText().toString().trim();
                String verdict = binding.caseStudyVerdict.getEditText().getText().toString().trim();

                String caseStudyId = UniqueIdGenerator.generateCaseStudyID();

                if (title.isEmpty() || description.isEmpty() || issues.isEmpty() || court.isEmpty() || verdict.isEmpty()) {
                    Toast.makeText(getContext(), "Field's are empty!", Toast.LENGTH_SHORT).show();
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
                    caseStudyMap.put("CaseStudyID", caseStudyId);
                    caseStudyRef.document(caseStudyId).set(caseStudyMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), title + " Added", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                clearFields();
                            }
                        }
                    });
                }
            }
        });

        return binding.getRoot();
    }

    private void clearFields() {
        binding.caseStudyTitle.getEditText().setText("");
        binding.caseStudyVerdict.getEditText().setText("");
        binding.caseStudyCourtName.getEditText().setText("");
        binding.caseStudyDescription.getEditText().setText("");
        binding.caseStudyLegalIssues.getEditText().setText("");

        binding.caseStudyTitle.getEditText().requestFocus();
    }
}