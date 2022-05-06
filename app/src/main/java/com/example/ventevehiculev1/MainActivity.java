package com.example.ventevehiculev1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btn_login;
    private Button btn_register;

    private BottomNavigationView bottomNavigationView;

    private RecyclerView recyclerView;
    private annonceAdapter adapter;

    DatabaseReference databaseAnnonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupListeners();

        bottomNavigationView = findViewById(R.id.BottomNavBar);
        bottomNavigationView.setSelectedItemId(R.id.search);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.fav:
                        startActivity(new Intent(getApplicationContext(),FavorisActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        btn_login = findViewById(R.id.button_to_login);
        btn_register = findViewById(R.id.button_to_register);

        databaseAnnonce = FirebaseDatabase.getInstance("https://vente-voiture-ceac9-default-rtdb.europe-west1.firebasedatabase.app").getReference("Annonce");

        recyclerView = findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Annonce> options = new FirebaseRecyclerOptions.Builder<Annonce>()
                .setQuery(databaseAnnonce,Annonce.class)
                .build();

        adapter = new annonceAdapter(options);
        recyclerView.setAdapter(adapter);


    }

    private void setupListeners(){
        // Login Button
        btn_login = findViewById(R.id.button_to_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignInActivity();
            }
        });

    }

    private void startSignInActivity(){

        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build());

        // Launch the activity
        startActivity(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false, true)
                        //.setLogo(R.drawable.ic_logo_auth)
                        .build()
        );
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
        /*setContentView(R.layout.activity_main);



        */
        //----------------------------------------------------------------------------------------------------------------------------------------------------------
        // NavBar

        // A voir si on utilise des fragments pour la suite //



        /* A voir si on utilise des fragments pour la suite

        private void replaceFragment(Fragment fragment)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
        }


        //----------------------------------------------------------------------------------------------------------------------------------------------------------
        /*

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });

    }

    */

}