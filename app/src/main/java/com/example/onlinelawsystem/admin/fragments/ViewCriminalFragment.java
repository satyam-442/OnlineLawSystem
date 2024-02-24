package com.example.onlinelawsystem.admin.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.court.EditCourtActivity;
import com.example.onlinelawsystem.admin.criminal.EditCriminalActivity;
import com.example.onlinelawsystem.admin.modal.Criminal;
import com.example.onlinelawsystem.databinding.FragmentViewCriminalBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewCriminalFragment extends Fragment {

    FragmentViewCriminalBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference criminalRef;
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentViewCriminalBinding.inflate(inflater, container, false);

        criminalRef = db.collection("Criminal");
        dialog = new ProgressDialog(getContext());

        binding.criminalRecyclerView.setHasFixedSize(true);
        binding.criminalRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
        criminalRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshot) {
                if (snapshot.isEmpty()) {
                    binding.emptyCriminalLayout.setVisibility(View.VISIBLE);
                    binding.criminalAvailableLayout.setVisibility(View.GONE);
                    dialog.dismiss();
                } else {
                    binding.criminalAvailableLayout.setVisibility(View.VISIBLE);
                    binding.emptyCriminalLayout.setVisibility(View.GONE);
                    Query query = criminalRef.orderBy("CriminalName", Query.Direction.ASCENDING);
                    FirestoreRecyclerOptions<Criminal> options = new FirestoreRecyclerOptions.Builder<Criminal>().setQuery(query, Criminal.class).build();

                    FirestoreRecyclerAdapter<Criminal, CriminalHolder> fireAdapter = new FirestoreRecyclerAdapter<Criminal, CriminalHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull CriminalHolder holder, int position, @NonNull Criminal model) {
                            holder.recyclerCriminalName.setText(model.getCriminalName());
                            holder.recyclerCriminalAddress.setText(model.getCriminalAddress());
                            holder.recyclerCriminalAlias.setText(model.getCriminalAlias());
                            holder.recyclerCriminalCaseDetails.setText(model.getCriminalOffense());
                            holder.recyclerCriminalDOB.setText(model.getCriminalDOB());
                            holder.recyclerCriminalGender.setText(model.getCriminalGender());

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Take Actions for "+ model.getCriminalName());

                                    // "Edit" button
                                    builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getContext(), EditCriminalActivity.class);
                                            intent.putExtra("criminalId", model.getCriminalID());
                                            startActivity(intent);
                                        }
                                    });

                                    // "Delete" button
                                    builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Handle the "Later" button click or dismiss the dialog
                                            criminalRef.document(model.getCriminalID()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(getContext(), model.getCriminalName() + " removed", Toast.LENGTH_SHORT).show();
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
                        public CriminalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_criminal_layout, parent, false);
                            return new CriminalHolder(view);
                        }
                    };
                    binding.criminalRecyclerView.setAdapter(fireAdapter);
                    fireAdapter.startListening();
                    dialog.dismiss();
                }
            }
        });
    }

    private class CriminalHolder extends RecyclerView.ViewHolder {

        TextView recyclerCriminalName, recyclerCriminalAlias, recyclerCriminalDOB, recyclerCriminalGender,
                recyclerCriminalAddress, recyclerCriminalCaseDetails;
        public CriminalHolder(@NonNull View itemView) {
            super(itemView);

            recyclerCriminalDOB = itemView.findViewById(R.id.recyclerCriminalDOB);
            recyclerCriminalName = itemView.findViewById(R.id.recyclerCriminalName);
            recyclerCriminalAlias = itemView.findViewById(R.id.recyclerCriminalAlias);
            recyclerCriminalGender = itemView.findViewById(R.id.recyclerCriminalGender);
            recyclerCriminalAddress = itemView.findViewById(R.id.recyclerCriminalAddress);
            recyclerCriminalCaseDetails = itemView.findViewById(R.id.recyclerCriminalCaseDetails);

        }
    }
}