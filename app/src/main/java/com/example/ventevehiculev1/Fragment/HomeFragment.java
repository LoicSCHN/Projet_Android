package com.example.ventevehiculev1.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ventevehiculev1.Adapter.annonceAdapter;
import com.example.ventevehiculev1.MainActivity;
import com.example.ventevehiculev1.R;
import com.example.ventevehiculev1.models.Annonce;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private annonceAdapter adapter;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = view.findViewById(R.id.annoncesSearch);

        LinearLayoutManager linearLayoutManager;

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        Query query;
        query = MainActivity.BDD.getDatabase().getReference("Annonce");
        FirebaseRecyclerOptions<Annonce> options = new FirebaseRecyclerOptions.Builder<Annonce>()
                .setQuery(query,Annonce.class)
                .build();

        adapter = new annonceAdapter(options,getContext());

        recyclerView.setAdapter(adapter);




        return view;
    }

    @Override public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

}