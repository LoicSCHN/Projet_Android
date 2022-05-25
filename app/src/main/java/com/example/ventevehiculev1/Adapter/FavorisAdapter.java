package com.example.ventevehiculev1.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ventevehiculev1.AnnonceViewHolder;
import com.example.ventevehiculev1.MainActivity;
import com.example.ventevehiculev1.R;
import com.example.ventevehiculev1.models.Annonce;
import com.example.ventevehiculev1.models.Favori;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavorisAdapter extends RecyclerView.Adapter<AnnonceViewHolder> {
        ArrayList<Annonce> annonces;
        ArrayList<Favori> favoris = new ArrayList<>();
        DatabaseReference BDD;

    @Override
    public int getItemCount() {
        return this.annonces.size();
    }

    public FavorisAdapter(ArrayList<Annonce> annonces) {
       this.annonces = annonces;
    }

    @NonNull
    @Override
    public AnnonceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_annonce,
                viewGroup, false);
        return new AnnonceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnonceViewHolder holder, int position) {
        Log.d("BindVIEW", annonces.get(position).toString());
        BDD = FirebaseDatabase.getInstance("https://vente-voiture-ceac9-default-rtdb.europe-west1.firebasedatabase.app").getReference();

        holder.title.setText(annonces.get(position).getTitle());
        holder.id = annonces.get(position).getId();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        MainActivity.BDD.getDatabase().getReference("Favoris").orderByChild("id").equalTo(user.getUid()+";"+holder.id).get().addOnCompleteListener(
                new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));

                        } else {
                            Favori favori = task.getResult().getValue(Favori.class);
                            if(favori != null){
                                holder.checkBox.setChecked(true);
                            }else {
                                holder.checkBox.setChecked(false);

                            }

                        }
                    }
                }
        );

        for(Favori favori: favoris){
            if(favori.getIdAnnonce().equals(annonces.get(position).getId())){
                holder.checkBox.setChecked(true);
            }
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(holder.checkBox.isChecked()){
                    if(user == null){
                        holder.checkBox.setChecked(false);
                        Toast.makeText(view.getContext(),
                                "Vous devez être connecté pour mettre en favori une annonce ",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        String key = BDD.getDatabase().getReference("Favoris").push().getKey();
                        Favori favori = new Favori(user.getUid(), holder.id);
                        MainActivity.BDD.child("Favoris").child(key).setValue(favori);
                    }
                }else {
                    BDD.child("Favoris")
                            .orderByChild("id")
                            .equalTo(user.getUid()+";"+holder.id)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                        appleSnapshot.getRef().removeValue();
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.e("TAG", "onCancelled", databaseError.toException());
                                }
                            });
                }
            }
        });
    }

/*
    public static class myViewHolder extends RecyclerView.ViewHolder
    {
        private View.OnClickListener onItemClickerListener;
        private ArrayList<Annonce> mExemple;
        TextView title;
        String id;
        private CheckBox checkBox;

        public myViewHolder(@NonNull View itemView)
        {
            super(itemView);
            title = itemView.findViewById(R.id.titleannonce);
            id="no";
            checkBox = (CheckBox) itemView.findViewById(R.id.rb_Fav);

            itemView.setTag(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), id+"On test ici", Toast.LENGTH_SHORT).show();
                    Fragment fragment =  DetailsFragment.newInstance(id);
                    Context context = itemView.getContext();
                    FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });

        }
    }*/
}


