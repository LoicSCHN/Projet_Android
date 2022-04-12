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

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText mdp;
    private Button btnreg;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email_reg);
        mdp = findViewById(R.id.mdp_reg);
        btnreg = findViewById(R.id.btnreg);

        mAuth = FirebaseAuth.getInstance();

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = email.getText().toString().trim();
                String mdpp = mdp.getText().toString().trim();

                if (TextUtils.isEmpty(mail)) {
                    email.setError("Email is required!");
                    return;
                }

                if (TextUtils.isEmpty(mdpp)) {
                    mdp.setError("Password is required!");
                    return;
                }

                if (mdpp.length() < 6) {
                    mdp.setError("Password too short, enter minimum 6 characters!");
                    return;
                }
                mAuth.createUserWithEmailAndPassword(mail,mdpp).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "User is created successfuly :D", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "ERROR !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }
}