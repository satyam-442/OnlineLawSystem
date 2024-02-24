package com.example.onlinelawsystem.user;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.fragments.ViewLawBooksFragment;
import com.example.onlinelawsystem.admin.modal.LawBooks;
import com.example.onlinelawsystem.databinding.ActivityLawBookSearchBinding;
import com.example.onlinelawsystem.databinding.FragmentViewLawBooksBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LawBookSearchActivity extends AppCompatActivity {

    ActivityLawBookSearchBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference lawBookRef;
    ProgressDialog dialog;
    StorageReference uploadPdfStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLawBookSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        lawBookRef = db.collection("LawBooks");
        uploadPdfStorageRef = FirebaseStorage.getInstance().getReference().child("LawBooks");
        dialog = new ProgressDialog(this);

        binding.lawBookRecyclerView.setHasFixedSize(true);
        binding.lawBookRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString() != null) {
                    startListen(s.toString());
                } else {
                    startListen("");
                }
            }
        });

        dialog.setMessage("please wait");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        startListen("");
    }

    private void startListen(String data) {
        lawBookRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshots) {
                if (snapshots.isEmpty()) {
                    binding.emptyLawBookLayout.setVisibility(View.VISIBLE);
                    binding.viewAvailableLayout.setVisibility(View.GONE);
                    dialog.dismiss();
                } else {
                    binding.viewAvailableLayout.setVisibility(View.VISIBLE);
                    binding.emptyLawBookLayout.setVisibility(View.GONE);
                    Query query = lawBookRef.orderBy("BookTitle", Query.Direction.ASCENDING).startAt(data).endAt(data + "\uf8ff");
                    FirestoreRecyclerOptions<LawBooks> options = new FirestoreRecyclerOptions.Builder<LawBooks>().setQuery(query, LawBooks.class).build();
                    FirestoreRecyclerAdapter<LawBooks, LawBooksHolder> fireAdapter = new FirestoreRecyclerAdapter<LawBooks, LawBooksHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull LawBooksHolder holder, int position, @NonNull LawBooks model) {
                            holder.recyclerBookTitle.setText(model.getBookTitle());
                            holder.recyclerBookSummary.setText(model.getBookDescription());
                            holder.recyclerBookTopics.setText(model.getBookTopics());

                            holder.recyclerBookDownload.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ProgressDialog  pd = new ProgressDialog(LawBookSearchActivity.this);
                                    pd.setTitle(model.getBookTitle());
                                    pd.setMessage("Downloading Please Wait!");
                                    pd.setCanceledOnTouchOutside(false);
                                    pd.show();
                                    uploadPdfStorageRef.child(model.getBookNameDefault()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            //downloadBookFunction(getContext(),bookName, ".pdf",DIRECTORY_DOWNLOADS, uri.toString());
                                            downloadBookFunction(getApplicationContext(),model.getBookTitle(), ".pdf",DIRECTORY_DOWNLOADS, uri.toString());
                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful()){
                                                pd.dismiss();
                                                Toast.makeText(getApplicationContext(), "downloading", Toast.LENGTH_SHORT).show();
                                            } else {
                                                String msg = task.getException().getMessage();
                                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    Toast.makeText(getApplicationContext(), "Check your downloads...", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @NonNull
                        @Override
                        public LawBooksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_book_layout, parent, false);
                            return new LawBooksHolder(view);
                        }
                    };
                    binding.lawBookRecyclerView.setAdapter(fireAdapter);
                    fireAdapter.startListening();
                    dialog.dismiss();
                }
            }
        });
    }

    private void downloadBookFunction(Context context, String fileName, String extension, String destinationDir, String url) {
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(destinationDir,"Online Law System/"+"Books/"+fileName+extension);
        manager.enqueue(request);
    }

    private class LawBooksHolder extends RecyclerView.ViewHolder {

        TextView recyclerBookTitle, recyclerBookSummary, recyclerBookTopics;
        ImageView recyclerBookDownload;

        public LawBooksHolder(@NonNull View itemView) {
            super(itemView);

            recyclerBookTitle = itemView.findViewById(R.id.recyclerBookTitle);
            recyclerBookSummary = itemView.findViewById(R.id.recyclerBookSummary);
            recyclerBookTopics = itemView.findViewById(R.id.recyclerBookTopics);
            recyclerBookDownload = itemView.findViewById(R.id.recyclerBookDownload);
        }
    }
}