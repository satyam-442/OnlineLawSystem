package com.example.onlinelawsystem.admin.fragments;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.modal.LawBooks;
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

public class ViewLawBooksFragment extends Fragment {

    FragmentViewLawBooksBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference lawBookRef;
    ProgressDialog dialog;
    StorageReference uploadPdfStorageRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentViewLawBooksBinding.inflate(inflater, container, false);

        lawBookRef = db.collection("LawBooks");
        uploadPdfStorageRef = FirebaseStorage.getInstance().getReference().child("LawBooks");
        dialog = new ProgressDialog(getContext());

        binding.lawBookRecyclerView.setHasFixedSize(true);
        binding.lawBookRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dialog.setMessage("please wait");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        startListen();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        startListen();
    }

    private void startListen() {
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
                    Query query = lawBookRef.orderBy("BookTitle", Query.Direction.ASCENDING);
                    FirestoreRecyclerOptions<LawBooks> options = new FirestoreRecyclerOptions.Builder<LawBooks>().setQuery(query, LawBooks.class).build();
                    FirestoreRecyclerAdapter<LawBooks, LawBooksHolder> fireAdapter = new FirestoreRecyclerAdapter<LawBooks, LawBooksHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull LawBooksHolder holder, int position, @NonNull LawBooks model) {
                            holder.recyclerBookTitle.setText(model.getBookTitle());
                            holder.recyclerBookSummary.setText(model.getBookDescription());
                            holder.recyclerBookTopics.setText(model.getBookTopics());

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Take Actions for " + model.getBookTitle());

                                    // "Delete" button
                                    builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Handle the "Later" button click or dismiss the dialog

                                            lawBookRef.document(model.getBookId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    uploadPdfStorageRef.child(model.getBookNameDefault()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(getContext(), model.getBookTitle() + " removed", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                String msg = task.getException().getMessage();
                                                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }
                                            });
                                            dialog.dismiss();
                                        }
                                    });

                                    // Create and show the AlertDialog
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            });

                            holder.recyclerBookDownload.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ProgressDialog  pd = new ProgressDialog(getContext());
                                    pd.setTitle(model.getBookTitle());
                                    pd.setMessage("Downloading Please Wait!");
                                    pd.setCanceledOnTouchOutside(false);
                                    pd.show();
                                    uploadPdfStorageRef.child(model.getBookNameDefault()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            //downloadBookFunction(getContext(),bookName, ".pdf",DIRECTORY_DOWNLOADS, uri.toString());
                                            downloadBookFunction(getContext(),model.getBookTitle(), ".pdf",DIRECTORY_DOWNLOADS, uri.toString());
                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            if (task.isSuccessful()){
                                                pd.dismiss();
                                                Toast.makeText(getContext(), "downloading", Toast.LENGTH_SHORT).show();
                                            } else {
                                                String msg = task.getException().getMessage();
                                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    Toast.makeText(getContext(), "Book will be downloaded soon...", Toast.LENGTH_SHORT).show();
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