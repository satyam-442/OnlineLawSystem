package com.example.onlinelawsystem.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.casestudy.EditCasestudyActivity;
import com.example.onlinelawsystem.admin.casestudy.ReadCaseStudyActivity;
import com.example.onlinelawsystem.admin.fragments.ViewCaseStudyFragment;
import com.example.onlinelawsystem.admin.modal.CaseStudy;
import com.example.onlinelawsystem.databinding.ActivityCaseStudySearchBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class CaseStudySearchActivity extends AppCompatActivity {

    ActivityCaseStudySearchBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference caseStudyRef;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCaseStudySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        caseStudyRef = db.collection("CaseStudy");
        dialog = new ProgressDialog(this);

        binding.caseStudyRecyclerView.setHasFixedSize(true);
        binding.caseStudyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
        caseStudyRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshots) {
                if (snapshots.isEmpty()){
                    binding.emptyCaseStudyLayout.setVisibility(View.VISIBLE);
                    binding.caseStudyAvailableLayout.setVisibility(View.GONE);
                    dialog.dismiss();
                } else {
                    binding.caseStudyAvailableLayout.setVisibility(View.VISIBLE);
                    binding.emptyCaseStudyLayout.setVisibility(View.GONE);
                    Query query = caseStudyRef.orderBy("CaseStudyTitle", Query.Direction.ASCENDING).startAt(data).endAt(data + "\uf8ff");
                    FirestoreRecyclerOptions<CaseStudy> options = new FirestoreRecyclerOptions.Builder<CaseStudy>().setQuery(query, CaseStudy.class).build();
                    FirestoreRecyclerAdapter<CaseStudy, CaseStudyHolder> fireAdapter = new FirestoreRecyclerAdapter<CaseStudy, CaseStudyHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull CaseStudyHolder holder, int position, @NonNull CaseStudy model) {
                            holder.recyclerCaseStudyTitle.setText(model.getCaseStudyTitle());
                            holder.recyclerCaseStudyIssues.setText(model.getCaseStudyIssues());
                            holder.recyclerCaseStudyDescription.setText(model.getCaseStudyDescription());
                            holder.recyclerCaseStudyCourt.setText(model.getCaseStudyCourt());
                            holder.recyclerCaseStudyVerdict.setText(model.getCaseStudyVerdict());

                            holder.recyclerCaseStudyReadMore.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getApplicationContext(), ReadCaseStudyActivity.class);
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