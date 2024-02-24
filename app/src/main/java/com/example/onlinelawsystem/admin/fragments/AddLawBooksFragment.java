package com.example.onlinelawsystem.admin.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.onlinelawsystem.databinding.FragmentAddLawBooksBinding;
import com.example.onlinelawsystem.helper.UniqueIdGenerator;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddLawBooksFragment extends Fragment {

    FragmentAddLawBooksBinding binding;
    StorageReference uploadPdfStorageRef;
    int PDF_CODE = 1;
    Uri pdfUri;
    String uploadBookId;
    ProgressDialog loadingBar;
    StorageTask uploadTask;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddLawBooksBinding.inflate(inflater, container, false);

        loadingBar = new ProgressDialog(getContext());

        uploadPdfStorageRef = FirebaseStorage.getInstance().getReference().child("LawBooks");
        binding.selectPdfFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    SelectFileFromStorage();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }
        });

        binding.addLawBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pdfUri != null) {
                    UploadPdfToFirebaseStorage(pdfUri);
                } else {
                    Toast.makeText(getContext(), "please select file", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return binding.getRoot();
    }

    private void UploadPdfToFirebaseStorage(final Uri pdfUri) {
        loadingBar.setMessage("please wait...");
        loadingBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //loadingBar.setProgress(0);
        loadingBar.setCanceledOnTouchOutside(true);
        loadingBar.show();

        final String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH-MM-ss");
        saveCurrentTime = currentTime.format(calForTime.getTime());

        uploadBookId = UniqueIdGenerator.generateLawBookID();
        //uploadBookId = saveCurrentDate + saveCurrentTime;

        final StorageReference fileReference = uploadPdfStorageRef.child(getFileName(pdfUri));
        //uploadPdfStorageRef.child(getFileName(pdfUri)).putFile(pdfUri);
        uploadTask = fileReference.putFile(pdfUri);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    String mUri = downloadUri.toString();
                    //String current_uid = mAuth.getUid();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("BooksPdf", mUri);
                    map.put("BookTitle", binding.lawBookTitle.getEditText().getText().toString());
                    map.put("BookNameDefault", binding.selectedFileName.getText().toString());
                    map.put("BookDescription", binding.lawBookDescription.getEditText().getText().toString());
                    map.put("BookTopics", binding.lawBookTopics.getEditText().getText().toString());
                    map.put("Date", saveCurrentDate);
                    map.put("Time", saveCurrentTime);
                    map.put("BookId", uploadBookId);
                    db.collection("LawBooks").document(uploadBookId).set(map).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Book Uploaded Successfully...", Toast.LENGTH_LONG).show();
                                ClearFields();
                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText(getContext(), "Error Occurred:" + message, Toast.LENGTH_SHORT).show();
                            }
                            loadingBar.dismiss();
                        }
                    });
                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText(getContext(), "Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getContext(), "Error Occurred!" + exception, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ClearFields() {
        binding.lawBookTitle.getEditText().setText("");
        binding.lawBookDescription.getEditText().setText("");
        binding.lawBookTopics.getEditText().setText("");

        binding.lawBookTitle.getEditText().requestFocus();
        pdfUri = null;
    }

    @SuppressLint("Range")
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                //cursor = getContentResolver().query(uri, null, null, null);
                cursor = requireContext().getContentResolver().query(uri, null, null, null);
            }
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void SelectFileFromStorage() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PDF_CODE);
    }

    @SuppressLint("Range")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PDF_CODE && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            File myFile = new File(String.valueOf(pdfUri));
            String displayName = null;

            if (pdfUri.toString().startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getActivity().getContentResolver().query(pdfUri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else if (pdfUri.toString().startsWith("file://")) {
                displayName = myFile.getName();
            }

            binding.selectedFileName.setText(displayName);
            binding.uploadLawBook.fromUri(pdfUri).load();
        } else {
            Toast.makeText(getContext(), "Please select file", Toast.LENGTH_SHORT).show();
        }
    }
}