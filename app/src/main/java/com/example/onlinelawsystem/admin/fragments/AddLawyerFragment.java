package com.example.onlinelawsystem.admin.fragments;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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
import com.example.onlinelawsystem.databinding.FragmentAddLawyerBinding;
import com.example.onlinelawsystem.helper.UniqueIdGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AddLawyerFragment extends Fragment {
    FragmentAddLawyerBinding binding;
    ProgressDialog loadingBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference lawyersRef;
    String subject, body;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddLawyerBinding.inflate(inflater, container, false);

        loadingBar = new ProgressDialog(getContext());
        lawyersRef = db.collection("Lawyers");

        //COURT CATEGORY CODE
        ArrayAdapter<CharSequence> adapter4Category = ArrayAdapter.createFromResource(getContext(), R.array.court_category, android.R.layout.simple_spinner_item);
        adapter4Category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.courtSpinner.setAdapter(adapter4Category);
        binding.courtSpinner.setOnItemSelectedListener(new CourtSpinner());

        //EXPERTISE CATEGORY CODE
        ArrayAdapter<CharSequence> adapter4expertise = ArrayAdapter.createFromResource(getContext(), R.array.expertise_category, android.R.layout.simple_spinner_item);
        adapter4expertise.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.expertiseSpinner.setAdapter(adapter4expertise);
        binding.expertiseSpinner.setOnItemSelectedListener(new ExpertiseSpinner());

        binding.addLawyerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.lawyerFullname.getEditText().getText().toString().trim();
                String age = binding.lawyerAge.getEditText().getText().toString().trim();
                String contact = binding.lawyerContact.getEditText().getText().toString().trim();
                String email = binding.lawyerEmail.getEditText().getText().toString().trim();
                String qualification = binding.lawyerQualification.getEditText().getText().toString().trim();
                String courtType = binding.courtSpinnerText.getText().toString().trim();
                String expertise = binding.expertiseSpinnerText.getText().toString().trim();
                String lawID = UniqueIdGenerator.generateLawyerID();

                if (name.isEmpty() || age.isEmpty() || contact.isEmpty() || qualification.isEmpty()) {
                    Toast.makeText(getContext(), "Field's are empty...", Toast.LENGTH_SHORT).show();
                } else {
                    loadingBar.setMessage("please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    HashMap lawyerMap = new HashMap();
                    lawyerMap.put("LawyerName", name);
                    lawyerMap.put("LawyerAge", age);
                    lawyerMap.put("LawyerContact", contact);
                    lawyerMap.put("LawyerEmail", email);
                    lawyerMap.put("LawyerQualification", qualification);
                    lawyerMap.put("LawyerCourt", courtType);
                    lawyerMap.put("LawyerExpertise", expertise);
                    lawyerMap.put("LawyerID", lawID);
                    lawyerMap.put("Password", lawID);
                    lawyersRef.document(lawID).set(lawyerMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), name + " Added", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                /*Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                        "mailto", email, null));
                                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Welcome to our legal platform");
                                emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear Lawyer,\n\nThank you for joining our legal platform!\n\nYour Lawyer ID and Password(same as user ID) are: " + lawID);

                                try {
                                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }*/

                                subject = "Welcome to Online Law System! Access your Account in 2 minutes.";
                                body = "Hi " + name + " " + lawID + " ,\n" +
                                        "Welcome to Legal Platform! We thank you for choosing us." +
                                        "Your account will be accessed by following details\n" +
                                        "Your Law System Login Credentials:\n" +
                                        "Here is your login ID " + lawID + " for the platform.\n" +
                                        "Here is your password " + lawID + " for the platform.\n" +
                                        "All done!";

                                //Uri uri = Uri.parse(email);
                                Uri uri = Uri.fromParts("mailto", email, null);
                                Intent intent = new Intent(Intent.ACTION_SEND, uri);
                                intent.setType("message/rfc822");
                                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                                intent.putExtra(Intent.EXTRA_TEXT,body);
                                startActivity(Intent.createChooser(intent,"Choose an email client"));

                                //Two
                                /*Uri uri = Uri.fromParts("mailto", email, null);
                                Intent intent = new Intent(Intent.ACTION_SEND, uri);

                                // Specify the package name of Gmail
                                //intent.setPackage("com.google.android.gm");

                                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                                intent.putExtra(Intent.EXTRA_TEXT, body);

                                try {
                                    startActivity(intent);
                                } catch (ActivityNotFoundException e) {
                                    // Handle the case where Gmail is not installed
                                    e.printStackTrace();
                                }*/


                                //Three
                                /*Intent intent = new Intent(Intent.ACTION_SENDTO);
                                intent.setType("message/rfc822");
                                intent.setData(Uri.parse("mailto:" + email));
                                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                                intent.putExtra(Intent.EXTRA_TEXT, body);

                                try {
                                    startActivity(Intent.createChooser(intent, "Choose an email client"));
                                } catch (ActivityNotFoundException e) {
                                    // If no email client is found, open a web browser
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com"));
                                    startActivity(browserIntent);
                                    e.printStackTrace();
                                }*/

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
        binding.lawyerFullname.getEditText().setText("");
        binding.lawyerAge.getEditText().setText("");
        binding.lawyerContact.getEditText().setText("");
        binding.lawyerEmail.getEditText().setText("");
        binding.lawyerQualification.getEditText().setText("");

        binding.lawyerFullname.getEditText().requestFocus();
    }

    private class CourtSpinner implements android.widget.AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            binding.courtSpinnerText.setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class ExpertiseSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String itemSpinner = parent.getItemAtPosition(position).toString();
            binding.expertiseSpinnerText.setText(itemSpinner);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}