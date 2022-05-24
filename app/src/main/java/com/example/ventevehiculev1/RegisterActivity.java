package com.example.ventevehiculev1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ventevehiculev1.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText mdp;
    private EditText name;
    private EditText lastName;
    private Button btnreg;
    private RadioGroup groupRadio;
    private CheckBox abo;
    private TextView textAbo;
    private boolean pro;
    private boolean abonne;
    DatabaseReference databaseUser;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.searchBar);
        mdp = findViewById(R.id.mdp_reg);
        name = findViewById(R.id.name_reg);
        lastName = findViewById(R.id.lastName_reg);
        btnreg = findViewById(R.id.btnreg);
        groupRadio = findViewById(R.id.radioGroup);
        abo = findViewById(R.id.checkAbo);
        textAbo = findViewById(R.id.textAbo);
        abo.setVisibility(View.INVISIBLE);
        textAbo.setVisibility(View.INVISIBLE);




        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = email.getText().toString().trim();
                String mdpp = mdp.getText().toString().trim();
                String prenom = name.getText().toString().trim();
                String nom = lastName.getText().toString().trim();

                if (TextUtils.isEmpty(mail)) {
                    email.setError("Email is required!");
                    return;
                }

                if (TextUtils.isEmpty(mdpp)) {
                    mdp.setError("Password is required!");
                    return;
                }
                if (TextUtils.isEmpty(prenom)) {
                    name.setError("Name is required!");
                    return;
                }
                if (TextUtils.isEmpty(nom)) {
                    lastName.setError("Last name is required!");
                    return;
                }

                if (mdpp.length() < 6) {
                    mdp.setError("Password too short, enter minimum 6 characters!");
                    return;
                }
                mAuth.createUserWithEmailAndPassword(mail,mdpp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
                            databaseUser = FirebaseDatabase.getInstance("https://vente-voiture-ceac9-default-rtdb.europe-west1.firebasedatabase.app").getReference("User");
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nom)
                                    .build();

                            u.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });
                            User user = new User(u.getUid().toString(), nom.toString(), prenom.toString(),pro , abonne );
                            String id = databaseUser.push().getKey();
                            databaseUser.child(id).setValue(user);
                            Toast.makeText(getApplicationContext(), "User is created successfuly :D", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "ERROR !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        abo = findViewById(R.id.checkAbo);
        textAbo = findViewById(R.id.textAbo);
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.Particulier:
                if (checked)
                    abo.setVisibility(View.INVISIBLE);
                    textAbo.setVisibility(View.INVISIBLE);
                    pro = false;
                    break;
            case R.id.pro:
                if (checked)
                    abo.setVisibility(View.VISIBLE);
                    textAbo.setVisibility(View.VISIBLE);
                    pro = true;
                    break;
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkAbo:
                if (checked)
                    abonne = true;
                else
                    abonne = false;

                break;

        }
    }
}