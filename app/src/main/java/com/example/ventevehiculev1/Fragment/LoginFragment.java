package com.example.ventevehiculev1.Fragment;

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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ventevehiculev1.Adapter.annonceAdapter;
import com.example.ventevehiculev1.Fragment.FavorisFragment;
import com.example.ventevehiculev1.Fragment.HomeFragment;
import com.example.ventevehiculev1.Fragment.ProfileFragment;
import com.example.ventevehiculev1.LoginActivity;
import com.example.ventevehiculev1.MainActivity;
import com.example.ventevehiculev1.R;
import com.example.ventevehiculev1.RegisterActivity;
import com.example.ventevehiculev1.models.Annonce;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
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
import java.util.List;


public class LoginFragment extends Fragment {
    private Button btnlog;
    private Button btnregister;
    private EditText email;
    private EditText mdp;
    private FirebaseAuth mAuth;



    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        btnlog = view.findViewById(R.id.connect_Button);
        email = view.findViewById(R.id.mail);
        mdp = view.findViewById(R.id.password);
        btnregister = view.findViewById(R.id.button_Register);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(view.getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = email.getText().toString().trim();
                String mdpp = mdp.getText().toString().trim();
                mAuth = FirebaseAuth.getInstance();

                //On vérifie si les champs sont bien rempis

                if (TextUtils.isEmpty(mail)){
                    email.setError("Email is required !");
                }
                if (TextUtils.isEmpty(mdpp)) {
                    mdp.setError("Password is required!");
                    return;
                }

                //Vérification si l'utilisateur est bien dans la base de données

                mAuth.signInWithEmailAndPassword(mail,mdpp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            //Le Toast ne fonctionne pas//
                            //Toast.makeText(getApplicationContext(), "Connexion !" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(view.getContext(), MainActivity.class);
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(view.getContext(), "Error!" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }


}