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
import com.example.onlinelawsystem.admin.fragments.ViewLawFragment;
import com.example.onlinelawsystem.admin.laws.EditLawsActivity;
import com.example.onlinelawsystem.admin.modal.Laws;
import com.example.onlinelawsystem.databinding.ActivityLawSearchBinding;
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

public class LawSearchActivity extends AppCompatActivity {

    ActivityLawSearchBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference lawRef;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLawSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        lawRef = db.collection("Laws");
        dialog = new ProgressDialog(this);

        binding.lawRecyclerView.setHasFixedSize(true);
        binding.lawRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
        lawRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshots) {
                if (snapshots.isEmpty()){
                    binding.emptyLawLayout.setVisibility(View.VISIBLE);
                    binding.lawAvailableLayout.setVisibility(View.GONE);
                    dialog.dismiss();
                } else {
                    binding.lawAvailableLayout.setVisibility(View.VISIBLE);
                    binding.emptyLawLayout.setVisibility(View.GONE);
                    Query query = lawRef.orderBy("LawSection", Query.Direction.ASCENDING).startAt(data).endAt(data+"\uf8ff");
                    FirestoreRecyclerOptions<Laws> options = new FirestoreRecyclerOptions.Builder<Laws>().setQuery(query, Laws.class).build();
                    FirestoreRecyclerAdapter<Laws, LawsHolder> fireAdapter = new FirestoreRecyclerAdapter<Laws, LawsHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull LawsHolder holder, int position, @NonNull Laws model) {
                            holder.recyclerLawTitle.setText(model.getLawTitle());
                            holder.recyclerLawSection.setText(model.getLawSection());
                            holder.recyclerLawDescription.setText(model.getLawDescription());
                            holder.recyclerLawJurisdiction.setText(model.getLawJurisdiction());
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
