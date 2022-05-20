package com.example.ventevehiculev1.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ventevehiculev1.Adapter.annonceAdapter;
import com.example.ventevehiculev1.MainActivity;
import com.example.ventevehiculev1.R;
import com.example.ventevehiculev1.models.Annonce;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class MesAnnoncesFragment extends Fragment {

    DatabaseReference test;
    private RecyclerView recyclerView;
    private annonceAdapter adapter;
    private BottomNavigationView bottomNavigationView;

    private Fragment fragment_Home;
    private Fragment fragment_Profile;
    private Fragment fragment_Fav;
    private Fragment fragment_login;
    private Fragment fragment_Search;

    public MesAnnoncesFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mes_annonces, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = view.findViewById(R.id.annonces2);

        LinearLayoutManager linearLayoutManager;

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        Query query;
        query = MainActivity.BDD.getDatabase().getReference("Annonce")
                .orderByChild("id_proprietaire")
                .equalTo(user.getUid());

        FirebaseRecyclerOptions<Annonce> options = new FirebaseRecyclerOptions.Builder<Annonce>()
                .setQuery(query,Annonce.class)
                .build();

        adapter = new annonceAdapter(options);

        recyclerView.setAdapter(adapter);

        return view;

    }

    @Override public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

}

