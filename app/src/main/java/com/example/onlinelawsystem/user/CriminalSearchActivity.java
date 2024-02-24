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
import com.example.onlinelawsystem.admin.criminal.EditCriminalActivity;
import com.example.onlinelawsystem.admin.fragments.ViewCriminalFragment;
import com.example.onlinelawsystem.admin.modal.Criminal;
import com.example.onlinelawsystem.databinding.ActivityCriminalSearchBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class CriminalSearchActivity extends AppCompatActivity {

    ActivityCriminalSearchBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference criminalRef;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCriminalSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        criminalRef = db.collection("Criminal");
        dialog = new ProgressDialog(this);

        binding.criminalRecyclerView.setHasFixedSize(true);
        binding.criminalRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                    Query query = criminalRef.orderBy("CriminalName", Query.Direction.ASCENDING).startAt(data).endAt(data + "\uf8ff");
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