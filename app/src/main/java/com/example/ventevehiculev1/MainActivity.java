package com.example.ventevehiculev1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.ventevehiculev1.Fragment.FavorisFragment;
import com.example.ventevehiculev1.Fragment.HomeFragment;
import com.example.ventevehiculev1.Fragment.ProfileFragment;
import com.example.ventevehiculev1.Fragment.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {



    private Fragment fragment_Home;
    private Fragment fragment_Profile;
    private Fragment fragment_Fav;
    private Fragment fragment_login;

    private ImageButton btn_user;

    public static DatabaseReference BDD;
    //public static FirebaseStorage STORAGE;
    public static FirebaseAuth AUTH;


    private LinearLayout linearLayoutHome;
    private LinearLayout linearLayoutProfile;
    private LinearLayout linearLayoutFav;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BDD = FirebaseDatabase.getInstance("https://vente-voiture-ceac9-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        AUTH = FirebaseAuth.getInstance();

        btn_user = findViewById(R.id.btnUser);

        fragment_Home = new HomeFragment();
        fragment_Profile = new ProfileFragment();
        fragment_Fav = new FavorisFragment();
        fragment_login = new LoginFragment();

        linearLayoutHome     = findViewById(R.id.homeLayout);
        linearLayoutProfile  = findViewById(R.id.profileLayout);
        linearLayoutFav      = findViewById(R.id.FavLayout);

        replaceCurrentFragmentBy(fragment_Home);
        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceCurrentFragmentBy(fragment_login);
            }
        });

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







