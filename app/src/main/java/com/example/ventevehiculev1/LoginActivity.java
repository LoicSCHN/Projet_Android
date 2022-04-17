package com.example.ventevehiculev1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Button btnlog;
    private EditText email;
    private EditText mdp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Boutton mail and mdp
        btnlog = findViewById(R.id.btnlog);
        email = findViewById(R.id.email);
        mdp = findViewById(R.id.mdp);

        // Création de l'instance
        mAuth = FirebaseAuth.getInstance();

        //Action après que l'on appuye sur le bouton valdider
        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = email.getText().toString().trim();
                String mdpp = mdp.getText().toString().trim();

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
                            Intent intent =new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Error!" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}