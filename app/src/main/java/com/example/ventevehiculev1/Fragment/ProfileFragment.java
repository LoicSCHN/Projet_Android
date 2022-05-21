package com.example.ventevehiculev1.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ventevehiculev1.Adapter.annonceAdapter;
import com.example.ventevehiculev1.MainActivity;
import com.example.ventevehiculev1.MesAnnoncesActivity;
import com.example.ventevehiculev1.ProfileActivity;
import com.example.ventevehiculev1.R;
import com.example.ventevehiculev1.addAnnonceActivity;
import com.example.ventevehiculev1.models.Annonce;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.core.Context;

import java.util.Arrays;
import java.util.List;


public class ProfileFragment extends Fragment {
    private Button btn_deposer;
    private Button btn_mes_annonces;
    private TextView name;
    private Button coDeco;
    private MesAnnoncesFragment mesAnnoncesFragment;


    public ProfileFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        setupListeners(view);
        btn_deposer = view.findViewById(R.id.button_deposer);
        btn_mes_annonces = view.findViewById(R.id.button_mes_annonces);
        coDeco = view.findViewById(R.id.connexion_button);
        name = view.findViewById(R.id.nomProfil);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
            name.setText(user.getDisplayName());
        else
            name.setText("pas de user");


        if (user != null){
            name.setText(user.getDisplayName());
            coDeco.setText("Deconnexion");

            coDeco.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AuthUI.getInstance()
                            .signOut(view.getContext())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast toast =  Toast.makeText(view.getContext(), "Vous êtes deconnecté !", Toast.LENGTH_SHORT);
                                    toast.show();
                                    Fragment fragment = new HomeFragment();
                                    android.content.Context context = view.getContext();
                                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                }
                            });
                }
            });
        }else {
            coDeco.setText("Connexion");

        }

         btn_deposer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), addAnnonceActivity.class);
                startActivity(intent);

            }
        });

        btn_mes_annonces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(view.getContext(), MesAnnoncesActivity.class);
                //startActivity(intent);
                //Fragment f = new MesAnnoncesFragment();
                //FrameLayout frame = v.findViewById(R.id.FrameMes_annonces);
                Fragment f = new MesAnnoncesFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment,f);
                ft.commit();

            }
        });

        name = view.findViewById(R.id.nomProfil);

        return view;

    }


    private void setupListeners(View view){
        coDeco = view.findViewById(R.id.connexion_button);

        coDeco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignInActivity();
            }
        });
    }


    private void startSignInActivity(){

        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);
    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            name.setText(user.getDisplayName());
            coDeco.setText("Deconnexion");

            coDeco.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AuthUI.getInstance()
                            .signOut(view.getContext())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast toast =  Toast.makeText(view.getContext(), "Vous êtes deconnecté !", Toast.LENGTH_SHORT);
                                    //toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 20, 30);
                                    toast.show();
                                    Fragment fragment = new HomeFragment();
                                    android.content.Context context = view.getContext();
                                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                }
                            });
                }
            });

        } else {
            Toast toast =  Toast.makeText(getView().getContext(), "Vous êtes ps deconnecté !", Toast.LENGTH_SHORT);
        }
    }



}
