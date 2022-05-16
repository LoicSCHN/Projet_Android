package com.example.ventevehiculev1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ventevehiculev1.models.Annonce;
import com.example.ventevehiculev1.models.Voiture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addAnnonceActivity extends AppCompatActivity {


    private EditText title_add;
    private EditText marque_add;
    private EditText modele_add;
    private EditText categorie_add;
    private EditText puissance_add;
    private EditText kilometrage_add;
    private EditText btv_add;
    private EditText nbp_add;
    private EditText energie_add;


    private Button btn_addannonce;

    DatabaseReference databaseAnnonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_annonce);

        title_add = findViewById(R.id.addTitletoAnnonce);
        marque_add = findViewById(R.id.addMarqueToAnnonce);
        modele_add = findViewById(R.id.addModeleToAnnonce);
        categorie_add = findViewById(R.id.addCategory);
        puissance_add = findViewById(R.id.addPuissance);
        kilometrage_add = findViewById(R.id.addKilometrage);
        btv_add = findViewById(R.id.addVitess);
        nbp_add = findViewById(R.id.addDoor);
        energie_add = findViewById(R.id.addEnergy);


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
        String marque = marque_add.getText().toString().trim();
        String modele = modele_add.getText().toString().trim();
        String categorie = categorie_add.getText().toString().trim();
        String puissance = puissance_add.getText().toString().trim();
        String kilometrage = kilometrage_add.getText().toString().trim();
        String btv = btv_add.getText().toString().trim();
        String nbp = nbp_add.getText().toString().trim();
        String energie = energie_add.getText().toString().trim();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(!TextUtils.isEmpty(title))
        {
            String id = databaseAnnonce.push().getKey();
            Voiture voiture = new Voiture(marque,modele,categorie,energie,kilometrage,btv,nbp,puissance);
            Annonce annonce = new Annonce(title,user.getUid(),voiture);
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