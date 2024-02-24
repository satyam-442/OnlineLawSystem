package com.example.onlinelawsystem.admin.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.lawyer.EditLawyerActivity;
import com.example.onlinelawsystem.admin.modal.Lawyers;
import com.example.onlinelawsystem.databinding.FragmentViewLawyerBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewLawyerFragment extends Fragment {

    FragmentViewLawyerBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference lawyerRef;
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentViewLawyerBinding.inflate(inflater, container, false);

        lawyerRef = db.collection("Lawyers");
        dialog = new ProgressDialog(getContext());

        binding.lawyerRecyclerView.setHasFixedSize(true);
        binding.lawyerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
        lawyerRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshot) {
                if (snapshot.isEmpty()) {
                    binding.emptyLawyerLayout.setVisibility(View.VISIBLE);
                    binding.lawyerAvailableLayout.setVisibility(View.GONE);
                    dialog.dismiss();
                } else {
                    binding.lawyerAvailableLayout.setVisibility(View.VISIBLE);
                    binding.emptyLawyerLayout.setVisibility(View.GONE);
                    Query query = lawyerRef.orderBy("LawyerName", Query.Direction.ASCENDING);
                    FirestoreRecyclerOptions<Lawyers> options = new FirestoreRecyclerOptions.Builder<Lawyers>().setQuery(query, Lawyers.class).build();

                    FirestoreRecyclerAdapter<Lawyers, LawyerHolder> fireAdapter = new FirestoreRecyclerAdapter<Lawyers, LawyerHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull LawyerHolder holder, int position, @NonNull Lawyers model) {
                            holder.recyclerLawyerName.setText(model.getLawyerName());
                            holder.recyclerLawyerAge.setText(model.getLawyerAge());
                            holder.recyclerLawyerContact.setText(model.getLawyerContact());
                            holder.recyclerLawyerQualification.setText(model.getLawyerQualification());
                            holder.recyclerLawyerExpertise.setText(model.getLawyerExpertise());
                            holder.recyclerLawyerCourt.setText(model.getLawyerCourt());

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Take Actions for " + model.getLawyerName());

                                    // "Edit" button
                                    builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getContext(), EditLawyerActivity.class);
                                            intent.putExtra("lawyerId", model.getLawyerID());
                                            startActivity(intent);
                                        }
                                    });

                                    // "Delete" button
                                    builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Handle the "Later" button click or dismiss the dialog
                                            lawyerRef.document(model.getLawyerID()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(getContext(), model.getLawyerName() + " removed", Toast.LENGTH_SHORT).show();
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
                        }

                        @NonNull
                        @Override
                        public LawyerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_lawyer_layout, parent, false);
                            return new LawyerHolder(view);
                        }
                    };
                    binding.lawyerRecyclerView.setAdapter(fireAdapter);
                    fireAdapter.startListening();
                    dialog.dismiss();
                }
            }
        });
    }

    private class LawyerHolder extends RecyclerView.ViewHolder {
        TextView recyclerLawyerName, recyclerLawyerAge, recyclerLawyerContact, recyclerLawyerCourt,
                recyclerLawyerExpertise, recyclerLawyerQualification;

        public LawyerHolder(@NonNull View itemView) {
            super(itemView);

            recyclerLawyerAge = itemView.findViewById(R.id.recyclerLawyerAge);
            recyclerLawyerName = itemView.findViewById(R.id.recyclerLawyerName);
            recyclerLawyerCourt = itemView.findViewById(R.id.recyclerLawyerCourt);
            recyclerLawyerContact = itemView.findViewById(R.id.recyclerLawyerContact);
            recyclerLawyerExpertise = itemView.findViewById(R.id.recyclerLawyerExpertise);
            recyclerLawyerQualification = itemView.findViewById(R.id.recyclerLawyerQualification);
        }
    }
}