package com.example.ventevehiculev1.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ventevehiculev1.MainActivity;
import com.example.ventevehiculev1.R;
import com.example.ventevehiculev1.models.Annonce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import org.w3c.dom.Text;


public class DetailsFragment extends Fragment {

    private static final String ARG_ID="id";
    private Context context;
    private TextView title;
    private TextView puissance;
    private String id;
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

        button_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

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

                    Annonce a = task.getResult().getValue(Annonce.class);
                    title.setText(a.getTitle());
                    puissance.setText(a.getVoiture().getPuissance());
                    marque.setText((a.getVoiture().getMarque()));
                    modele.setText(a.getVoiture().getModele());
                    kilometrage.setText(a.getVoiture().getKilometrage());

                }
            }
        });

        return v;
    }
}