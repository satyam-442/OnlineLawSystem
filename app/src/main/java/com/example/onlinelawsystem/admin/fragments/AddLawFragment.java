package com.example.onlinelawsystem.admin.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.databinding.FragmentAddLawBinding;
import com.example.onlinelawsystem.helper.UniqueIdGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddLawFragment extends Fragment {

    FragmentAddLawBinding binding;
    ProgressDialog loadingBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference lawRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddLawBinding.inflate(inflater, container, false);

        loadingBar = new ProgressDialog(getContext());
        lawRef = db.collection("Laws");

        //LAW CATEGORY CODE
        ArrayAdapter<CharSequence> adapter4Category = ArrayAdapter.createFromResource(getContext(), R.array.jurisdiction_category, android.R.layout.simple_spinner_item);
        adapter4Category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.lawJurisdictionSpinner.setAdapter(adapter4Category);
        binding.lawJurisdictionSpinner.setOnItemSelectedListener(new LawJurisdictionSpinner());

        binding.addLawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.lawTitle.getEditText().getText().toString().trim();
                String section = binding.lawSection.getEditText().getText().toString().trim();
                String description = binding.lawDescription.getEditText().getText().toString().trim();
                String lawId = UniqueIdGenerator.generateLawID();
                String jurisdictionType = binding.lawJurisdictionText.getText().toString().trim();

                if (title.isEmpty() || section.isEmpty() || description.isEmpty()) {
                    Toast.makeText(getContext(), "Field's are empty...", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setMessage("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    HashMap lawMap = new HashMap();
                    lawMap.put("LawTitle", title);
                    lawMap.put("LawSection", section);
                    lawMap.put("LawDescription", description);
                    lawMap.put("LawID", lawId);
                    lawMap.put("LawJurisdiction", jurisdictionType);
                    lawRef.document(lawId).set(lawMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        binding.lawTitle.getEditText().setText("");
        binding.lawSection.getEditText().setText("");
        binding.lawDescription.getEditText().setText("");

        binding.lawTitle.getEditText().requestFocus();
    }

    private class LawJurisdictionSpinner implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            binding.lawJurisdictionText.setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}