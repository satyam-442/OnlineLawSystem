package com.example.onlinelawsystem.admin.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.databinding.ActivityRegisterBinding;
import com.example.onlinelawsystem.databinding.FragmentAddCourtBinding;
import com.example.onlinelawsystem.helper.UniqueIdGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddCourtFragment extends Fragment {

    ProgressDialog loadingBar;
    FragmentAddCourtBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference courtsRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddCourtBinding.inflate(inflater, container, false);

        loadingBar = new ProgressDialog(getContext());
        courtsRef = db.collection("Courts");

        //COURT CATEGORY CODE
        ArrayAdapter<CharSequence> adapter4Category = ArrayAdapter.createFromResource(getContext(), R.array.court_category, android.R.layout.simple_spinner_item);
        adapter4Category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.courtTypeSpinner.setAdapter(adapter4Category);
        binding.courtTypeSpinner.setOnItemSelectedListener(new CourtCategorySpinner());

        binding.courtAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courtName = binding.courtName.getEditText().getText().toString().trim();
                String courtAddress = binding.courtAddress.getEditText().getText().toString().trim();
                String courtContact = binding.courtContact.getEditText().getText().toString().trim();
                String courtCity = binding.courtCity.getEditText().getText().toString().trim();
                String courtType = binding.courtCategoryText.getText().toString().trim();
                String courtId = UniqueIdGenerator.generateCourtID();

                if (courtName.isEmpty() || courtAddress.isEmpty() || courtContact.isEmpty() || courtCity.isEmpty()) {
                    Toast.makeText(getContext(), "Field's are empty!", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setMessage("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    HashMap courtMap = new HashMap();
                    courtMap.put("CourtName", courtName);
                    courtMap.put("CourtAddress", courtAddress);
                    courtMap.put("CourtContact", courtContact);
                    courtMap.put("CourtCity", courtCity);
                    courtMap.put("CourtType", courtType);
                    courtMap.put("CourtID", courtId);
                    courtsRef.document(courtId).set(courtMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), courtName + " Added", Toast.LENGTH_SHORT).show();
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
        // Clear input fields after successful upload
        binding.courtName.getEditText().setText("");
        binding.courtAddress.getEditText().setText("");
        binding.courtContact.getEditText().setText("");
        binding.courtCity.getEditText().setText("");

        // Move focus to the courtName EditText
        binding.courtName.getEditText().requestFocus();
    }

    private class CourtCategorySpinner implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            binding.courtCategoryText.setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}