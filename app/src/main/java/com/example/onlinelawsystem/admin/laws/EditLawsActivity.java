package com.example.onlinelawsystem.admin.laws;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.court.EditCourtActivity;
import com.example.onlinelawsystem.databinding.ActivityEditCourtBinding;
import com.example.onlinelawsystem.databinding.ActivityEditLawsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class EditLawsActivity extends AppCompatActivity {

    String lawID;
    ActivityEditLawsBinding binding;

    ProgressDialog loadingBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference lawsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditLawsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        lawID = getIntent().getStringExtra("lawID");

        loadingBar = new ProgressDialog(this);
        lawsRef = db.collection("Laws");

        //COURT CATEGORY CODE
        ArrayAdapter<CharSequence> adapter4Category = ArrayAdapter.createFromResource(getApplicationContext(), R.array.jurisdiction_category, android.R.layout.simple_spinner_item);
        adapter4Category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.editLawJurisdictionSpinner.setAdapter(adapter4Category);
        binding.editLawJurisdictionSpinner.setOnItemSelectedListener(new LawJurisdictionSpinner());

        lawsRef.document(lawID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    String title = snapshot.getString("LawTitle");
                    String section = snapshot.getString("LawSection");
                    String description = snapshot.getString("LawDescription");
                    String jurisdiction = snapshot.getString("LawJurisdiction");

                    binding.editLawTitle.getEditText().setText(title);
                    binding.editLawSection.getEditText().setText(section);
                    binding.editLawDescription.getEditText().setText(description);
                    binding.editLawJurisdictionText.setText(jurisdiction);
                }
            }
        });

        binding.addLawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.editLawTitle.getEditText().getText().toString().trim();
                String section = binding.editLawSection.getEditText().getText().toString().trim();
                String desc = binding.editLawDescription.getEditText().getText().toString().trim();
                String type = binding.editLawJurisdictionText.getText().toString().trim();

                if (title.isEmpty() || section.isEmpty() || desc.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Field's are empty!", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setMessage("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    HashMap lawMap = new HashMap();
                    lawMap.put("LawTitle", title);
                    lawMap.put("LawSection", section);
                    lawMap.put("LawDescription", desc);
                    lawMap.put("LawJurisdiction", type);
                    lawsRef.document(lawID).update(lawMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), title + " Updated", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }

    private class LawJurisdictionSpinner implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            binding.editLawJurisdictionText.setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}