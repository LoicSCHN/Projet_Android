package com.example.ventevehiculev1;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ventevehiculev1.Adapter.annonceAdapter;
import com.example.ventevehiculev1.Fragment.FavorisFragment;
import com.example.ventevehiculev1.Fragment.HomeFragment;
import com.example.ventevehiculev1.Fragment.ProfileFragment;
import com.example.ventevehiculev1.models.Annonce;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    private Fragment fragment_Home;
    private Fragment fragment_Profile;
    private Fragment fragment_Fav;

    public static DatabaseReference BDD;
    //public static FirebaseStorage STORAGE;
    public static FirebaseAuth AUTH;
    //private RecyclerView recyclerView;
    //private annonceAdapter adapter;

    private LinearLayout linearLayoutHome;
    private LinearLayout linearLayoutProfile;
    private LinearLayout linearLayoutFav;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BDD = FirebaseDatabase.getInstance("https://vente-voiture-ceac9-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        AUTH = FirebaseAuth.getInstance();

        fragment_Home = new HomeFragment();
        fragment_Profile = new ProfileFragment();
        fragment_Fav = new FavorisFragment();

        linearLayoutHome     = findViewById(R.id.homeLayout);
        linearLayoutProfile  = findViewById(R.id.profileLayout);
        linearLayoutFav      = findViewById(R.id.FavLayout);

        linearLayoutHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceCurrentFragmentBy(fragment_Home);
            }
        });

        linearLayoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceCurrentFragmentBy(fragment_Profile);
            }
        });

        linearLayoutFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceCurrentFragmentBy(fragment_Fav);
            }

        });

    }

    private void replaceCurrentFragmentBy(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, fragment);
        ft.commit();
    }





}

        /*

        //----------------------------------------------------------------------------------------------------------------------------------------------------------
        // NavBar

        databaseAnnonce = FirebaseDatabase.getInstance("https://vente-voiture-ceac9-default-rtdb.europe-west1.firebasedatabase.app").getReference("Annonce");

        recyclerView = findViewById(R.id.recycler1);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Annonce> options = new FirebaseRecyclerOptions.Builder<Annonce>()
                .setQuery(databaseAnnonce,Annonce.class)
                .build();

        adapter = new annonceAdapter(options);
            recyclerView.setAdapter(adapter);

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
        //----------------------------------------------------------------------------------------------------------------------------------------------------------
        /*
*/






