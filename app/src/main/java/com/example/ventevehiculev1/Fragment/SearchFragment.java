package com.example.ventevehiculev1.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.ventevehiculev1.Adapter.annonceAdapter;
import com.example.ventevehiculev1.MainActivity;
import com.example.ventevehiculev1.R;
import com.example.ventevehiculev1.models.Annonce;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;


public class SearchFragment extends Fragment {
    private EditText editText;
    private RecyclerView recyclerView;
    private annonceAdapter adapter;
    private Button search;
    Query query;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        editText = view.findViewById(R.id.searchBar);
        recyclerView =  view.findViewById(R.id.annoncesRC);
        search = view.findViewById(R.id.Recherche);

        LinearLayoutManager linearLayoutManager;

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);




        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query = MainActivity.BDD.getDatabase().getReference("Annonce")
                        .equalTo(editText.getText().toString().trim())
                        .orderByChild("title");
                FirebaseRecyclerOptions<Annonce> options = new FirebaseRecyclerOptions.Builder<Annonce>()
                        .setQuery(query,Annonce.class)
                        .build();


                recyclerView.setAdapter(adapter);

            }
        });




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