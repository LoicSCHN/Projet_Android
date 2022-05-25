package com.example.ventevehiculev1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ventevehiculev1.models.Annonce;
import com.example.ventevehiculev1.models.User;
import com.example.ventevehiculev1.models.Voiture;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

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
    private EditText numero_add;
    private String id;
    Activity activity;
    ImageView imageViewSelected;
    ArrayList<String> photos;

    private Button btn_addannonce;

    DatabaseReference databaseAnnonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_annonce);

        id = getIntent().getStringExtra("idAnnonce");
        if (id != null) {
            ((Button) findViewById(R.id.btn_addannonce)).setText("Modifier");

            MainActivity.BDD.child("Annonce").child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    Annonce annonce = task.getResult().getValue(Annonce.class);

                    ((EditText) findViewById(R.id.addTitletoAnnonce)).setText(annonce.getTitle());
                    for (String s : annonce.getPhoto()) {
                        switch (s.split("/")[1]) {
                            case "image1.png":
                                dlImageFromFireBaseStorage(findViewById(R.id.image1), s);
                                break;
                            case "image2.png":
                                dlImageFromFireBaseStorage(findViewById(R.id.image2), s);
                                break;
                            case "image3.png":
                                dlImageFromFireBaseStorage(findViewById(R.id.image3), s);
                                break;
                            case "image4.png":
                                dlImageFromFireBaseStorage(findViewById(R.id.image4), s);
                                break;
                        }
                    }
                }
            });
        }


        title_add = findViewById(R.id.addTitletoAnnonce);
        marque_add = findViewById(R.id.addMarqueToAnnonce);
        modele_add = findViewById(R.id.addModeleToAnnonce);
        categorie_add = findViewById(R.id.addCategory);
        puissance_add = findViewById(R.id.addPuissance);
        kilometrage_add = findViewById(R.id.addKilometrage);
        btv_add = findViewById(R.id.addVitess);
        nbp_add = findViewById(R.id.addDoor);
        energie_add = findViewById(R.id.addEnergy);
        numero_add = findViewById(R.id.num_annonce);
        activity = this;

        btn_addannonce = findViewById(R.id.btn_addannonce);

        databaseAnnonce = FirebaseDatabase.getInstance("https://vente-voiture-ceac9-default-rtdb.europe-west1.firebasedatabase.app").getReference("Annonce");
        activity = this;
        photos = new ArrayList<>();


        findViewById(R.id.image1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galeryActivityResultLauncher.launch(i);
                imageViewSelected = findViewById(R.id.image1);
            }
        });
        findViewById(R.id.image2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galeryActivityResultLauncher.launch(i);
                imageViewSelected = findViewById(R.id.image2);

            }
        });
        findViewById(R.id.image3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galeryActivityResultLauncher.launch(i);
                imageViewSelected = findViewById(R.id.image3);

            }
        });
        findViewById(R.id.image4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galeryActivityResultLauncher.launch(i);
                imageViewSelected = findViewById(R.id.image4);

            }
        });

        btn_addannonce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key;
                if (id == null) {
                    String title = title_add.getText().toString().trim();
                    String marque = marque_add.getText().toString().trim();
                    String modele = modele_add.getText().toString().trim();
                    String categorie = categorie_add.getText().toString().trim();
                    String puissance = puissance_add.getText().toString().trim();
                    String kilometrage = kilometrage_add.getText().toString().trim();
                    String btv = btv_add.getText().toString().trim();
                    String nbp = nbp_add.getText().toString().trim();
                    String energie = energie_add.getText().toString().trim();
                    String numero = numero_add.getText().toString().trim();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (!TextUtils.isEmpty(title)) {
                        String id = databaseAnnonce.push().getKey();
                        Voiture voiture = new Voiture(marque, modele, categorie, energie, kilometrage, btv, nbp, puissance,numero);
                        Annonce annonce = new Annonce(id, title, user.getUid(),photos, voiture);
                        databaseAnnonce.child(id).setValue(annonce);
                        //databaseAnnonce.push();
                        //Toast.makeText(this, "Annonce add", Toast.LENGTH_LONG).show();
                        if(((ImageView) findViewById(R.id.image1)).getDrawable() != null)
                            storageImage(findViewById(R.id.image1), "image1", id);
                        if(((ImageView) findViewById(R.id.image2)).getDrawable() != null)
                            storageImage(findViewById(R.id.image2), "image2", id);
                        if(((ImageView) findViewById(R.id.image3)).getDrawable() != null)
                            storageImage(findViewById(R.id.image3), "image3", id);
                        if(((ImageView) findViewById(R.id.image4)).getDrawable() != null)
                            storageImage(findViewById(R.id.image4), "image4", id);
                        databaseAnnonce.child(id).setValue(annonce);
                        activity.finish();
                    } else {
                        //Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                    }
                } else {
                    key = id;
                    // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Voiture voiture = new Voiture(marque_add.getText().toString(), modele_add.getText().toString(), categorie_add.getText().toString(), energie_add.getText().toString(), kilometrage_add.getText().toString(), btv_add.getText().toString(), nbp_add.getText().toString(),puissance_add.getText().toString(),numero_add.getText().toString());
                    //Voiture voiture = new Voiture(marque_add.getText().toString(), modele_add.getText().toString(), categorie_add.getText().toString(), "", "", "", "", "");
                    Annonce annonce = new Annonce(key, title_add.getText().toString(), user.getUid(),photos, voiture);
                    MainActivity.BDD.child("Annonce").child(key).setValue(annonce);
                    activity.finish();
                }
            }
        });





    }



    public void storageImage(ImageView imageView, String nom, String key){
        // Get the data from an ImageView as bytes
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        photos.add(key+"/"+nom+".png");

        UploadTask uploadTask = MainActivity.STORAGE.getReference(key+"/"+nom+".png").putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(imageView.getContext(), "Error"+exception, Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }


    ActivityResultLauncher<Intent> galeryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Uri selectedImage = data.getData();
                        Glide.with(activity).load(selectedImage).into(imageViewSelected);
                        imageViewSelected.setBackground(null);
                    }
                }
            });

    private void dlImageFromFireBaseStorage(ImageView imageView, String url){
        StorageReference imageRef = MainActivity.STORAGE.getReference().child(url);
        final Bitmap[] img = new Bitmap[1];
        final long ONE_MEGABYTE = 1024 * 1024;
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                img[0] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(img[0]);
                imageView.setBackground(null);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("error", "error dl Image");
            }
        });
    }
    private void replaceCurrentFragmentBy(int fragment){
        FrameLayout frameLayout = findViewById(R.id.fragmentAddAnnonce);
        LayoutInflater inflater = getLayoutInflater();
        frameLayout.addView(inflater.inflate(fragment, null));

    }
}