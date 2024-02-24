package com.example.onlinelawsystem.admin.casestudy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.databinding.ActivityReadCaseStudyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReadCaseStudyActivity extends AppCompatActivity {

    String caseStudyID;
    ActivityReadCaseStudyBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference caseStudyRef;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReadCaseStudyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        caseStudyID = getIntent().getStringExtra("caseStudyID");

        caseStudyRef = db.collection("CaseStudy");
        dialog = new ProgressDialog(this);

        dialog.setMessage("please wait");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        caseStudyRef.document(caseStudyID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    String title = snapshot.getString("CaseStudyTitle");
                    String description = snapshot.getString("CaseStudyDescription");
                    String issues = snapshot.getString("CaseStudyIssues");
                    String court = snapshot.getString("CaseStudyCourt");
                    String verdict = snapshot.getString("CaseStudyVerdict");

                    binding.caseStudyTitle.setText(title);
                    binding.caseStudyDescription.setText(description);
                    binding.caseStudyLegalIssues.setText(issues);
                    binding.caseStudyCourtJurisdiction.setText(court);
                    binding.caseStudyVerdict.setText(verdict);

                    dialog.dismiss();
                }
            }
        });
    }

    public void goToLoginScreen(View view) {
        finish();
    }
}