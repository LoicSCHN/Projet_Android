package com.example.ventevehiculev1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addAnnonceActivity extends AppCompatActivity {


    private EditText title_add;
    private Button btn_addannonce;

    DatabaseReference databaseAnnonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_annonce);

        title_add = findViewById(R.id.addTitletoAnnonce);
        btn_addannonce = findViewById(R.id.btn_addannonce);

        databaseAnnonce = FirebaseDatabase.getInstance("https://vente-voiture-ceac9-default-rtdb.europe-west1.firebasedatabase.app").getReference("Annonce");

        btn_addannonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAnnonce();
            }
        });

    }

    public void addAnnonce(){

        String title = title_add.getText().toString().trim();

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