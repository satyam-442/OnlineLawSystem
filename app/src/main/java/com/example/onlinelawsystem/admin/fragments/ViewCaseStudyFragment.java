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
import com.example.onlinelawsystem.admin.casestudy.EditCasestudyActivity;
import com.example.onlinelawsystem.admin.casestudy.ReadCaseStudyActivity;
import com.example.onlinelawsystem.admin.modal.CaseStudy;
import com.example.onlinelawsystem.databinding.FragmentViewCaseStudyBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewCaseStudyFragment extends Fragment {

    FragmentViewCaseStudyBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference caseStudyRef;
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentViewCaseStudyBinding.inflate(inflater, container, false);

        caseStudyRef = db.collection("CaseStudy");
        dialog = new ProgressDialog(getContext());

        binding.caseStudyRecyclerView.setHasFixedSize(true);
        binding.caseStudyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
        caseStudyRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshots) {
                if (snapshots.isEmpty()){
                    binding.emptyCaseStudyLayout.setVisibility(View.VISIBLE);
                    binding.viewAvailableLayout.setVisibility(View.GONE);
                    dialog.dismiss();
                } else {
                    binding.viewAvailableLayout.setVisibility(View.VISIBLE);
                    binding.emptyCaseStudyLayout.setVisibility(View.GONE);
                    Query query = caseStudyRef.orderBy("CaseStudyTitle", Query.Direction.ASCENDING);
                    FirestoreRecyclerOptions<CaseStudy> options = new FirestoreRecyclerOptions.Builder<CaseStudy>().setQuery(query, CaseStudy.class).build();
                    FirestoreRecyclerAdapter<CaseStudy, CaseStudyHolder> fireAdapter = new FirestoreRecyclerAdapter<CaseStudy, CaseStudyHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull CaseStudyHolder holder, int position, @NonNull CaseStudy model) {
                            holder.recyclerCaseStudyTitle.setText(model.getCaseStudyTitle());
                            holder.recyclerCaseStudyIssues.setText(model.getCaseStudyIssues());
                            holder.recyclerCaseStudyDescription.setText(model.getCaseStudyDescription());
                            holder.recyclerCaseStudyCourt.setText(model.getCaseStudyCourt());
                            holder.recyclerCaseStudyVerdict.setText(model.getCaseStudyVerdict());

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Take Actions for "+ model.getCaseStudyTitle());

                                    // "Edit" button
                                    builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getContext(), EditCasestudyActivity.class);
                                            intent.putExtra("caseStudyID", model.getCaseStudyID());
                                            startActivity(intent);
                                        }
                                    });

                                    // "Delete" button
                                    builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Handle the "Later" button click or dismiss the dialog
                                            caseStudyRef.document(model.getCaseStudyID()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(getContext(), model.getCaseStudyTitle() + " removed", Toast.LENGTH_SHORT).show();
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

                            holder.recyclerCaseStudyReadMore.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getContext(), ReadCaseStudyActivity.class);
                                    intent.putExtra("caseStudyID", model.getCaseStudyID());
                                    startActivity(intent);
                                }
                            });
                        }

                        @NonNull
                        @Override
                        public CaseStudyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_casestudy_layout, parent, false);
                            return new CaseStudyHolder(view);
                        }
                    };
                    binding.caseStudyRecyclerView.setAdapter(fireAdapter);
                    fireAdapter.startListening();
                    dialog.dismiss();
                }
            }
        });
    }

    private class CaseStudyHolder extends RecyclerView.ViewHolder {

        TextView recyclerCaseStudyTitle, recyclerCaseStudyDescription, recyclerCaseStudyIssues,
                recyclerCaseStudyCourt, recyclerCaseStudyVerdict, recyclerCaseStudyReadMore;
        public CaseStudyHolder(@NonNull View itemView) {
            super(itemView);

            recyclerCaseStudyTitle = itemView.findViewById(R.id.recyclerCaseStudyTitle);
            recyclerCaseStudyDescription = itemView.findViewById(R.id.recyclerCaseStudyDescription);
            recyclerCaseStudyIssues = itemView.findViewById(R.id.recyclerCaseStudyIssues);
            recyclerCaseStudyCourt = itemView.findViewById(R.id.recyclerCaseStudyCourt);
            recyclerCaseStudyVerdict = itemView.findViewById(R.id.recyclerCaseStudyVerdict);
            recyclerCaseStudyReadMore = itemView.findViewById(R.id.recyclerCaseStudyReadMore);
        }
    }
}