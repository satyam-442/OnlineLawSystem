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
import com.example.onlinelawsystem.admin.fragments.ViewLawyerFragment;
import com.example.onlinelawsystem.admin.lawyer.EditLawyerActivity;
import com.example.onlinelawsystem.admin.modal.Lawyers;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class LawyerSearchActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference lawyerRef;
    ProgressDialog dialog;
    com.example.onlinelawsystem.databinding.ActivityLawyerSearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.onlinelawsystem.databinding.ActivityLawyerSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        lawyerRef = db.collection("Lawyers");
        dialog = new ProgressDialog(this);

        binding.lawyerRecyclerView.setHasFixedSize(true);
        binding.lawyerRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.searchView.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

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
                    Query query = lawyerRef.orderBy("LawyerName", Query.Direction.ASCENDING).startAt(data).endAt(data + "\uf8ff");
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