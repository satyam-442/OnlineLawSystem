package com.example.onlinelawsystem.user;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinelawsystem.R;
import com.example.onlinelawsystem.admin.modal.Courts;
import com.example.onlinelawsystem.databinding.ActivityCourtSearchBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class CourtSearchActivity extends AppCompatActivity {

    ActivityCourtSearchBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference courtRef;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourtSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        courtRef = db.collection("Courts");
        dialog = new ProgressDialog(this);

        binding.courtRecyclerView.setHasFixedSize(true);
        binding.courtRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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

    /*@Override
    protected void onResume() {
        super.onResume();
        startListen();
    }*/

    private void startListen(String data) {
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
                    Query query = courtRef.orderBy("CourtCity", Query.Direction.ASCENDING).startAt(data).endAt(data + "\uf8ff");
                    FirestoreRecyclerOptions<Courts> options = new FirestoreRecyclerOptions.Builder<Courts>().setQuery(query, Courts.class).build();
                    FirestoreRecyclerAdapter<Courts, CourtHolder> fireAdapter = new FirestoreRecyclerAdapter<Courts, CourtHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull CourtHolder holder, int position, @NonNull Courts model) {
                            holder.recyclerCourtName.setText(model.getCourtName());
                            holder.recyclerCourtAddress.setText("Address: " + model.getCourtAddress());
                            holder.recyclerCourtContact.setText("Phone: " + model.getCourtContact());
                            holder.recyclerCourtCity.setText("City: " + model.getCourtCity());
                            holder.recyclerCourtType.setText("Type: " + model.getCourtType());
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