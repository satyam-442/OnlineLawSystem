package com.example.onlinelawsystem.user;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.modal.PublicCases;
import com.example.onlinelawsystem.databinding.ActivityViewCasesBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewCasesActivity extends AppCompatActivity {

    ActivityViewCasesBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference publicCaseRef;
    FirebaseAuth mAuth;
    String userID;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewCasesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        publicCaseRef = db.collection("PublicCases");
        dialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        binding.casesRecyclerView.setHasFixedSize(true);
        binding.casesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        dialog.setMessage("please wait");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        startListen();

    }

    private void startListen() {
        publicCaseRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshots) {
                if (snapshots.isEmpty()) {
                    binding.emptyCasesLayout.setVisibility(View.VISIBLE);
                    binding.casesAvailableLayout.setVisibility(View.GONE);
                    dialog.dismiss();
                } else {
                    binding.casesAvailableLayout.setVisibility(View.VISIBLE);
                    binding.emptyCasesLayout.setVisibility(View.GONE);
                    Query query = publicCaseRef.orderBy("PublicCaseName", Query.Direction.ASCENDING).whereEqualTo("UserID",userID);
                    FirestoreRecyclerOptions<PublicCases> options = new FirestoreRecyclerOptions.Builder<PublicCases>().setQuery(query, PublicCases.class).build();
                    FirestoreRecyclerAdapter<PublicCases, PublicCasesHolder> fireAdapter = new FirestoreRecyclerAdapter<PublicCases, PublicCasesHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull PublicCasesHolder holder, int position, @NonNull PublicCases model) {
                            holder.recyclerUserName.setText(model.getPublicCaseName());
                            holder.recyclerUserAge.setText(model.getPublicCaseAge());
                            holder.recyclerCaseDetail.setText(model.getPublicCaseDetail());
                            holder.recyclerUserContact.setText(model.getPublicCaseContact());
                            holder.recyclerUserExpertise.setText(model.getPublicCaseExpertise());
                            holder.recyclerUserStatus.setText(model.getPublicCaseStatus());
                        }

                        @NonNull
                        @Override
                        public PublicCasesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_case_layout, parent, false);
                            return new PublicCasesHolder(view);
                        }
                    };
                    binding.casesRecyclerView.setAdapter(fireAdapter);
                    fireAdapter.startListening();
                    dialog.dismiss();
                }
            }
        });
    }

    private class PublicCasesHolder extends RecyclerView.ViewHolder {

        TextView recyclerUserName, recyclerUserAge, recyclerCaseDetail, recyclerUserContact, recyclerUserExpertise, recyclerUserStatus;

        public PublicCasesHolder(@NonNull View itemView) {
            super(itemView);

            recyclerUserName = itemView.findViewById(R.id.recyclerUserName);
            recyclerUserAge = itemView.findViewById(R.id.recyclerUserAge);
            recyclerCaseDetail = itemView.findViewById(R.id.recyclerCaseDetail);
            recyclerUserContact = itemView.findViewById(R.id.recyclerUserContact);
            recyclerUserExpertise = itemView.findViewById(R.id.recyclerUserExpertise);
            recyclerUserStatus = itemView.findViewById(R.id.recyclerUserStatus);

        }
    }

}