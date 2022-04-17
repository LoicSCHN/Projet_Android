package com.example.ventevehiculev1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button btn_login;
    private Button btn_register;
    private Button btn_annonce;
    private EditText edit;

    private BottomNavigationView bottomNavigationView;

    DatabaseReference databaseAnnonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = findViewById(R.id.add_annonce);
        btn_annonce = findViewById(R.id.button_annonce);
        btn_login = findViewById(R.id.button_to_login);
        btn_register = findViewById(R.id.button_to_register);


        //----------------------------------------------------------------------------------------------------------------------------------------------------------
        // NavBar

        // A voir si on utilise des fragments pour la suite //

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

        /* A voir si on utilise des fragments pour la suite

        private void replaceFragment(Fragment fragment)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
        }
        */

        //----------------------------------------------------------------------------------------------------------------------------------------------------------

        databaseAnnonce = FirebaseDatabase.getInstance("https://vente-voiture-ceac9-default-rtdb.europe-west1.firebasedatabase.app").getReference("Annonce");

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

        btn_annonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAnnonce();
            }
        });

    }

    public void addAnnonce(){
        String title = edit.getText().toString().trim();

        if(!TextUtils.isEmpty(title))
        {
            String id = databaseAnnonce.push().getKey();
            Annonce annonce = new Annonce(id,title);
            databaseAnnonce.child(id).setValue(annonce);
            //databaseAnnonce.push();
            Toast.makeText(this,"Annonce add",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();
        }
    }

}