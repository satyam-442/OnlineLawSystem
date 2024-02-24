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
import com.example.onlinelawsystem.admin.modal.Courts;
import com.example.onlinelawsystem.databinding.FragmentViewCourtBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewCourtFragment extends Fragment {

    FragmentViewCourtBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference courtRef;
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentViewCourtBinding.inflate(inflater, container, false);

        courtRef = db.collection("Courts");
        dialog = new ProgressDialog(getContext());

        binding.courtRecyclerView.setHasFixedSize(true);
        binding.courtRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
        courtRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshot) {
                if (snapshot.isEmpty()) {
                    binding.emptyCourtLayout.setVisibility(View.VISIBLE);
                    binding.courtAvailableLayout.setVisibility(View.GONE);
                    dialog.dismiss();
                } else {
                    binding.courtAvailableLayout.setVisibility(View.VISIBLE);
                    binding.emptyCourtLayout.setVisibility(View.GONE);
                    Query query = courtRef.orderBy("CourtName", Query.Direction.ASCENDING);
                    FirestoreRecyclerOptions<Courts> options = new FirestoreRecyclerOptions.Builder<Courts>().setQuery(query, Courts.class).build();

                    FirestoreRecyclerAdapter<Courts, CourtHolder> fireAdapter = new FirestoreRecyclerAdapter<Courts, CourtHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull CourtHolder holder, int position, @NonNull Courts model) {
                            holder.recyclerCourtName.setText(model.getCourtName());
                            holder.recyclerCourtAddress.setText("Address: " + model.getCourtAddress());
                            holder.recyclerCourtContact.setText("Phone: " + model.getCourtContact());
                            holder.recyclerCourtCity.setText("City: " + model.getCourtCity());
                            holder.recyclerCourtType.setText("Type: " + model.getCourtType());

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Take Actions");

                                    // "Edit" button
                                    builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getContext(), EditCourtActivity.class);
                                            intent.putExtra("courtId", model.getCourtID());
                                            startActivity(intent);
                                        }
                                    });

                                    // "Delete" button
                                    builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Handle the "Later" button click or dismiss the dialog
                                            courtRef.document(model.getCourtID()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(getContext(), model.getCourtName() + " removed", Toast.LENGTH_SHORT).show();
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
                        public CourtHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_court_layout, parent, false);
                            return new CourtHolder(view);
                        }
                    };
                    binding.courtRecyclerView.setAdapter(fireAdapter);
                    fireAdapter.startListening();
                    dialog.dismiss();
                }
            }
        });
    }

    private class CourtHolder extends RecyclerView.ViewHolder {
        TextView recyclerCourtName, recyclerCourtContact, recyclerCourtCity, recyclerCourtType, recyclerCourtAddress;

        public CourtHolder(@NonNull View itemView) {
            super(itemView);

            recyclerCourtName = itemView.findViewById(R.id.recyclerCourtName);
            recyclerCourtContact = itemView.findViewById(R.id.recyclerCourtContact);
            recyclerCourtCity = itemView.findViewById(R.id.recyclerCourtCity);
            recyclerCourtType = itemView.findViewById(R.id.recyclerCourtType);
            recyclerCourtAddress = itemView.findViewById(R.id.recyclerCourtAddress);
        }
    }
}