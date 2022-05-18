package com.example.ventevehiculev1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ventevehiculev1.Adapter.annonceAdapter;
import com.example.ventevehiculev1.Fragment.FavorisFragment;
import com.example.ventevehiculev1.Fragment.HomeFragment;
import com.example.ventevehiculev1.Fragment.ProfileFragment;
import com.example.ventevehiculev1.Fragment.SearchFragment;
import com.example.ventevehiculev1.models.Annonce;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class MesAnnoncesActivity extends AppCompatActivity {

    DatabaseReference test;
    private RecyclerView recyclerView;
    private annonceAdapter adapter;
    private BottomNavigationView bottomNavigationView;

    private Fragment fragment_Home;
    private Fragment fragment_Profile;
    private Fragment fragment_Fav;
    private Fragment fragment_login;
    private Fragment fragment_Search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_annonces);

        fragment_Home = new HomeFragment();
        fragment_Profile = new ProfileFragment();
        fragment_Fav = new FavorisFragment();
        fragment_Search = new SearchFragment();

        test = FirebaseDatabase.getInstance("https://vente-voiture-ceac9-default-rtdb.europe-west1.firebasedatabase.app").getReference("Annonce");

        recyclerView = findViewById(R.id.recycler_mes_annonces);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Query query = test.getDatabase().getReference("Annonce")
                .orderByChild("id_proprietaire")
                .equalTo(user.getUid());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Annonce> options = new FirebaseRecyclerOptions.Builder<Annonce>()
                .setQuery(query,Annonce.class)
                .build();

        adapter = new annonceAdapter(options);
        recyclerView.setAdapter(adapter);

        //----------------------------------------------------------------------------------------------------------------------------------------------------------
        // NavBar

        /*
        bottomNavigationView.setSelectedItemId(R.id.profileLayout);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.homeLayout:
                        replaceCurrentFragmentBy(fragment_Home);
                        return true;

                    case R.id.profileLayout:
                        replaceCurrentFragmentBy(fragment_Profile);
                        return true;

                    case R.id.FavLayout:
                        replaceCurrentFragmentBy(fragment_Fav);
                        return true;

                    case R.id.SearchLayout:
                        replaceCurrentFragmentBy(fragment_Search);
                        return true;
                }
                return false;
            }
        });*/


    }
    private void replaceCurrentFragmentBy(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, fragment);
        ft.commit();
    }
    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }



}