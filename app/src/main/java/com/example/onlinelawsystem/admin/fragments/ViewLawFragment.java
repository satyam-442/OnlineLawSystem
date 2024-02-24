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
import com.example.onlinelawsystem.admin.court.EditCourtActivity;
import com.example.onlinelawsystem.admin.laws.EditLawsActivity;
import com.example.onlinelawsystem.admin.modal.Courts;
import com.example.onlinelawsystem.admin.modal.Laws;
import com.example.onlinelawsystem.databinding.FragmentViewLawBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewLawFragment extends Fragment {
    FragmentViewLawBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference lawRef;
    ProgressDialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentViewLawBinding.inflate(inflater, container, false);

        lawRef = db.collection("Laws");
        dialog = new ProgressDialog(getContext());

        binding.lawRecyclerView.setHasFixedSize(true);
        binding.lawRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
        lawRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshots) {
                if (snapshots.isEmpty()){
                    binding.emptyLawLayout.setVisibility(View.VISIBLE);
                    binding.viewAvailableLayout.setVisibility(View.GONE);
                    dialog.dismiss();
                } else {
                    binding.viewAvailableLayout.setVisibility(View.VISIBLE);
                    binding.emptyLawLayout.setVisibility(View.GONE);
                    Query query = lawRef.orderBy("LawSection", Query.Direction.ASCENDING);
                    FirestoreRecyclerOptions<Laws> options = new FirestoreRecyclerOptions.Builder<Laws>().setQuery(query, Laws.class).build();
                    FirestoreRecyclerAdapter<Laws, LawsHolder> fireAdapter = new FirestoreRecyclerAdapter<Laws, LawsHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull LawsHolder holder, int position, @NonNull Laws model) {
                            holder.recyclerLawTitle.setText(model.getLawTitle());
                            holder.recyclerLawSection.setText(model.getLawSection());
                            holder.recyclerLawDescription.setText(model.getLawDescription());
                            holder.recyclerLawJurisdiction.setText(model.getLawJurisdiction());

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Take Actions for "+ model.getLawSection());

                                    // "Edit" button
                                    builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getContext(), EditLawsActivity.class);
                                            intent.putExtra("lawID", model.getLawID());
                                            startActivity(intent);
                                        }
                                    });

                                    // "Delete" button
                                    builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Handle the "Later" button click or dismiss the dialog
                                            lawRef.document(model.getLawID()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(getContext(), model.getLawTitle() + " removed", Toast.LENGTH_SHORT).show();
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
                        public LawsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_law_layout, parent, false);
                            return new LawsHolder(view);
                        }
                    };
                    binding.lawRecyclerView.setAdapter(fireAdapter);
                    fireAdapter.startListening();
                    dialog.dismiss();
                }
            }
        });
    }

    private class LawsHolder extends RecyclerView.ViewHolder {

        TextView recyclerLawTitle, recyclerLawSection, recyclerLawDescription, recyclerLawJurisdiction;
        public LawsHolder(@NonNull View itemView) {
            super(itemView);

            recyclerLawTitle = itemView.findViewById(R.id.recyclerLawTitle);
            recyclerLawSection = itemView.findViewById(R.id.recyclerLawSection);
            recyclerLawJurisdiction = itemView.findViewById(R.id.recyclerLawJurisdiction);
            recyclerLawDescription = itemView.findViewById(R.id.recyclerLawDescription);
        }
    }
}