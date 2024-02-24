package com.example.onlinelawsystem.admin.criminal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.databinding.ActivityEditCourtBinding;
import com.example.onlinelawsystem.databinding.ActivityEditCriminalBinding;
import com.example.onlinelawsystem.helper.UniqueIdGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class EditCriminalActivity extends AppCompatActivity {

    String criminalId;
    ProgressDialog loadingBar;
    ActivityEditCriminalBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference criminalRef;
    DatePickerDialog.OnDateSetListener mDateSetListenerBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditCriminalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        criminalId = getIntent().getStringExtra("criminalId");

        loadingBar = new ProgressDialog(this);
        criminalRef = db.collection("Criminal");

        criminalRef.document(criminalId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    String name = snapshot.getString("CriminalName");
                    String alias = snapshot.getString("CriminalAlias");
                    String dob = snapshot.getString("CriminalDOB");
                    String gender = snapshot.getString("CriminalGender");
                    String offense = snapshot.getString("CriminalOffense");
                    String address = snapshot.getString("CriminalAddress");

                    binding.editCriminalFullname.getEditText().setText(name);
                    binding.editCriminalAlias.getEditText().setText(alias);
                    binding.editCriminalAddress.getEditText().setText(address);
                    binding.editCriminalDOB.getEditText().setText(dob);
                    binding.editCriminalGender.getEditText().setText(gender);
                    binding.editCriminalCaseDetails.getEditText().setText(offense);
                }
            }
        });

        binding.editCriminalDOB.setEndIconOnClickListener(new View.OnClickListener() {
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
                        EditCriminalActivity.this,
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
                binding.editCriminalDOB.getEditText().setText(proDate);
            }
        };

        binding.genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected
                RadioButton radioButton = binding.getRoot().findViewById(checkedId);
                if (radioButton != null) {
                    // Update the EditText with the selected gender
                    binding.editCriminalGender.getEditText().setText(radioButton.getText());
                    Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.updateCriminalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editCriminalFullname.getEditText().getText().toString().trim();
                String alias = binding.editCriminalAlias.getEditText().getText().toString().trim();
                String dob = binding.editCriminalDOB.getEditText().getText().toString().trim();
                String gender = binding.editCriminalGender.getEditText().getText().toString().trim();
                String address = binding.editCriminalAddress.getEditText().getText().toString().trim();
                String offense = binding.editCriminalCaseDetails.getEditText().getText().toString().trim();

                if (name.isEmpty() || alias.isEmpty() || dob.isEmpty() || gender.isEmpty() || address.isEmpty() || offense.isEmpty() ) {
                    Toast.makeText(getApplicationContext(), "Field's are empty!", Toast.LENGTH_SHORT).show();
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
                    criminalRef.document(criminalId).update(criminalMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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
}