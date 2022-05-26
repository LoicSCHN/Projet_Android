package com.example.ventevehiculev1.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ventevehiculev1.ContactActivity;
import com.example.ventevehiculev1.MainActivity;
import com.example.ventevehiculev1.R;
import com.example.ventevehiculev1.models.Annonce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;


public class DetailsFragment extends Fragment {

    private static final String ARG_ID="id";
    private Context context;
    private TextView title;
    private TextView puissance;
    private String id;
    private Annonce annonce;
    private Button button_contact;

    public DetailsFragment(){}

    public static DetailsFragment newInstance(String id) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getString(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_details, container, false);
        button_contact = v.findViewById(R.id.btn_contact);


        MainActivity.BDD.child("Annonce").child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("FIREBASE", "ERREUR", task.getException());
                }
                else {
                    Log.d("FIREBASE", String.valueOf(task.getResult().getValue()));
                    TextView title = v.findViewById(R.id.title_details_annonce);
                    TextView puissance = v.findViewById(R.id.puissance_voiture);
                    TextView marque = v.findViewById(R.id.marque_voiture);
                    TextView modele = v.findViewById(R.id.modele_voiture);
                    TextView kilometrage = v.findViewById(R.id.kilometrage_voiture);
                    TextView prix = v.findViewById(R.id.prix);
                    TextView type = v.findViewById(R.id.type_details);
                    TextView boite = v.findViewById(R.id.boite_voiture);
                    TextView nbPortes = v.findViewById(R.id.nb_portes_details);
                    TextView numero = v.findViewById(R.id.numero_a_contacter);

                    Annonce a = task.getResult().getValue(Annonce.class);
                    title.setText(a.getTitle());
                    puissance.setText(a.getVoiture().getPuissance());
                    marque.setText((a.getVoiture().getMarque()));
                    modele.setText(a.getVoiture().getModele());
                    kilometrage.setText(a.getVoiture().getKilometrage());
                    prix.setText(a.getPrix());
                    type.setText(a.getType());
                    boite.setText(a.getVoiture().getBoiteVitesse());
                    nbPortes.setText(a.getVoiture().getNbPortes());
                    numero.setText(a.getVoiture().getNumero());

                    for (String s : a.getPhoto()) {
                        switch (s.split("/")[1]) {
                            case "image1.png":
                                dlImageFromFireBaseStorage(v.findViewById(R.id.image1), s);
                                break;
                            case "image2.png":
                                dlImageFromFireBaseStorage(v.findViewById(R.id.image2), s);
                                break;
                            case "image3.png":
                                dlImageFromFireBaseStorage(v.findViewById(R.id.image3), s);
                                break;
                            case "image4.png":
                                dlImageFromFireBaseStorage(v.findViewById(R.id.image4), s);
                                break;
                        }
                    }

                    button_contact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Annonce a = task.getResult().getValue(Annonce.class);
                            String number = a.getVoiture().getNumero();
                            Intent intent = new Intent(v.getContext(), ContactActivity.class);
                            intent.putExtra("number",number);
                            Toast.makeText(v.getContext(), number+"ici", Toast.LENGTH_SHORT).show();
                            v.getContext().startActivity(intent);

                        }
                    });

                }
            }
        });


        return v;
    }
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
}