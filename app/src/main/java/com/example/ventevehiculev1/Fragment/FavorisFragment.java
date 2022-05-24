package com.example.ventevehiculev1.Fragment;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ventevehiculev1.Adapter.FavorisAdapter;
import com.example.ventevehiculev1.Adapter.annonceAdapter;
import com.example.ventevehiculev1.MainActivity;
import com.example.ventevehiculev1.R;
import com.example.ventevehiculev1.models.Annonce;
import com.example.ventevehiculev1.models.Favori;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;


public class FavorisFragment extends Fragment {


    private static FavorisAdapter adapter2;
    ArrayList<Annonce> annonces;
    annonceAdapter adapter;
    RecyclerView rv;
    FirebaseUser user;

    public FavorisFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        annonces = new ArrayList<>();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favoris, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        rv = v.findViewById(R.id.fav_rc);

        setupRecyclerView();

        return v;
    }

    private void setupRecyclerView() {

        ArrayList<Annonce> annonces = new ArrayList<>();
        Query query;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        query = MainActivity.BDD.getDatabase().getReference()
                .child("Favoris");
        query.orderByChild("idUser")
                .equalTo(user.getUid());
        query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot child : task.getResult().getChildren()) {
                    Favori favori = child.getValue(Favori.class);
                    Log.i("Fav", favori.toString());

                    DatabaseReference query2 = MainActivity.BDD.getDatabase().getReference()
                            .child("Annonce")
                            .child(favori.getIdAnnonce());

                    query2.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {

                            annonces.add(task.getResult().getValue(Annonce.class));
                            FavorisFragment.adapter2 = new FavorisAdapter(annonces);
                            LinearLayoutManager linearLayoutManagers;
                            linearLayoutManagers = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            rv.setLayoutManager(linearLayoutManagers);
                            rv.setAdapter(FavorisFragment.adapter2);
                            //FavorisFragment.adapter.startListening();
                        }
                    });

                }
            }
        });

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