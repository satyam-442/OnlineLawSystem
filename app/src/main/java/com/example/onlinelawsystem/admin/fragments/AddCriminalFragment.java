package com.example.onlinelawsystem.admin.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.auth.SetupActivity;
import com.example.onlinelawsystem.databinding.FragmentAddCourtBinding;
import com.example.onlinelawsystem.databinding.FragmentAddCriminalBinding;
import com.example.onlinelawsystem.helper.UniqueIdGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddCriminalFragment extends Fragment {
    FragmentAddCriminalBinding binding;
    ProgressDialog loadingBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference criminalRef;
    DatePickerDialog.OnDateSetListener mDateSetListenerBirth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddCriminalBinding.inflate(inflater, container, false);

        loadingBar = new ProgressDialog(getContext());
        criminalRef = db.collection("Criminal");

        binding.criminalDOB.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date = simpleDateFormat.format(Calendar.getInstance().getTime());
                //patientDOB.setText(date);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Animation_Dialog,
                        mDateSetListenerBirth,
                        year, month, day);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.show();
            }
        });

        mDateSetListenerBirth = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                //Log.d(TAG,"onDateSet: " + month + "/" + dayOfMonth + "/" + year);
                final String proDate = dayOfMonth + "/" + month + "/" + year;
                //Toast.makeText(SignupForm.this, proDate, Toast.LENGTH_SHORT).show();
                binding.criminalDOB.getEditText().setText(proDate);
            }
        };

        binding.genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected
                RadioButton radioButton = binding.getRoot().findViewById(checkedId);
                if (radioButton != null) {
                    // Update the EditText with the selected gender
                    binding.criminalGender.getEditText().setText(radioButton.getText());
                    Toast.makeText(getContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.addCriminalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.criminalFullname.getEditText().getText().toString().trim();
                String alias = binding.criminalAlias.getEditText().getText().toString().trim();
                String dob = binding.criminalDOB.getEditText().getText().toString().trim();
                String gender = binding.criminalGender.getEditText().getText().toString().trim();
                String address = binding.criminalAddress.getEditText().getText().toString().trim();
                String offense = binding.criminalCaseDetails.getEditText().getText().toString().trim();
                String courtId = UniqueIdGenerator.generateCriminalID();

                if (name.isEmpty() || alias.isEmpty() || dob.isEmpty() || gender.isEmpty() || address.isEmpty() || offense.isEmpty() ) {
                    Toast.makeText(getContext(), "Field's are empty!", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setMessage("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    HashMap criminalMap = new HashMap();
                    criminalMap.put("CriminalName", name);
                    criminalMap.put("CriminalAlias", alias);
                    criminalMap.put("CriminalDOB", dob);
                    criminalMap.put("CriminalGender", gender);
                    criminalMap.put("CriminalAddress", address);
                    criminalMap.put("CriminalOffense", offense);
                    criminalMap.put("CriminalID", courtId);
                    criminalRef.document(courtId).set(criminalMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), name + " Added", Toast.LENGTH_SHORT).show();
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
        binding.criminalFullname.getEditText().setText("");
        binding.criminalAlias.getEditText().setText("");
        binding.criminalAddress.getEditText().setText("");
        binding.criminalDOB.getEditText().setText("");
        binding.criminalCaseDetails.getEditText().setText("");

        binding.criminalFullname.getEditText().requestFocus();
    }
}