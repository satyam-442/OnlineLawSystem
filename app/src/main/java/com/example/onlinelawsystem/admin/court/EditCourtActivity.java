package com.example.onlinelawsystem.admin.court;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.fragments.AddCourtFragment;
import com.example.onlinelawsystem.databinding.ActivityEditCourtBinding;
import com.example.onlinelawsystem.databinding.FragmentAddCourtBinding;
import com.example.onlinelawsystem.helper.UniqueIdGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class EditCourtActivity extends AppCompatActivity {

    ProgressDialog loadingBar;
    String courtId;
    ActivityEditCourtBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference courtsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditCourtBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadingBar = new ProgressDialog(this);
        courtsRef = db.collection("Courts");
        courtId = getIntent().getStringExtra("courtId");

        //COURT CATEGORY CODE
        ArrayAdapter<CharSequence> adapter4Category = ArrayAdapter.createFromResource(getApplicationContext(), R.array.court_category, android.R.layout.simple_spinner_item);
        adapter4Category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.editCourtTypeSpinner.setAdapter(adapter4Category);
        binding.editCourtTypeSpinner.setOnItemSelectedListener(new CourtCategorySpinner());

        courtsRef.document(courtId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    String name = snapshot.getString("CourtName");
                    String address = snapshot.getString("CourtAddress");
                    String contact = snapshot.getString("CourtContact");
                    String city = snapshot.getString("CourtCity");
                    String type = snapshot.getString("CourtType");

                    binding.editCourtName.getEditText().setText(name);
                    binding.editCourtAddress.getEditText().setText(address);
                    binding.editCourtContact.getEditText().setText(contact);
                    binding.editCourtCategoryText.setText(type);
                    binding.editCourtCity.getEditText().setText(city);
                }
            }
        });

        binding.courtEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courtName = binding.editCourtName.getEditText().getText().toString().trim();
                String courtAddress = binding.editCourtAddress.getEditText().getText().toString().trim();
                String courtContact = binding.editCourtContact.getEditText().getText().toString().trim();
                String courtCity = binding.editCourtCity.getEditText().getText().toString().trim();
                String courtType = binding.editCourtCategoryText.getText().toString().trim();

                if (courtName.isEmpty() || courtAddress.isEmpty() || courtContact.isEmpty() || courtCity.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Field's are empty!", Toast.LENGTH_SHORT).show();
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
                    courtsRef.document(courtId).update(courtMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), courtName + " Updated", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
                }
            }
        });

    }

    private class CourtCategorySpinner implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            binding.editCourtCategoryText.setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}