package com.example.ventevehiculev1.Adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ventevehiculev1.Fragment.DetailsFragment;
import com.example.ventevehiculev1.Fragment.MesAnnoncesFragment;
import com.example.ventevehiculev1.MainActivity;
import com.example.ventevehiculev1.R;
import com.example.ventevehiculev1.models.Annonce;
import com.example.ventevehiculev1.models.Favori;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class annonceAdapter extends FirebaseRecyclerAdapter<Annonce,annonceAdapter.myViewHolder> {

    ArrayList<Favori> favoris = new ArrayList<>();
    public Context context;

    public annonceAdapter(@NonNull FirebaseRecyclerOptions<Annonce> options,Context context)
    {
        super(options);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        this.context=context;

    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Annonce model)
    {
        holder.title.setText(model.getTitle());
        holder.id = this.getRef(position).getKey();
        dlImageFromFireBaseStorage(holder, model.getPhoto().get(0));
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
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
                if(favori.getIdAnnonce().equals(this.getRef(position).getKey())){
                    holder.checkBox.setChecked(true);
                }
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
                        Toast.makeText(view.getContext(),
                                "SUUUUUUUUU ",
                                Toast.LENGTH_SHORT).show();
                        String key = MainActivity.BDD.getDatabase().getReference("Favoris").push().getKey();
                        Favori favori = new Favori(user.getUid(), holder.id);
                        MainActivity.BDD.child("Favoris").child(key).setValue(favori);
                    }
                }else {
                    MainActivity.BDD.child("Favoris")
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

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_annonce,parent,false);

        return new annonceAdapter.myViewHolder(view);
    }


    public static class myViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        private View.OnClickListener onItemClickerListener;
        private ArrayList<Annonce> mExemple;
        private CheckBox checkBox;
        TextView title;
        String id;

        public myViewHolder(@NonNull View itemView)
        {
            super(itemView);
            title = itemView.findViewById(R.id.titleannonce);
            id="no";
            imageView = (ImageView) itemView.findViewById(R.id.appercu_imageView);
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
    }

    private void dlImageFromFireBaseStorage(myViewHolder holder, String url){
        StorageReference imageRef = MainActivity.STORAGE.getReference().child(url);
        final Bitmap[] img = new Bitmap[1];
        final long ONE_MEGABYTE = 1024 * 1024;
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                img[0] = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.imageView.setImageBitmap(img[0]);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("error", "error dl Image");
            }
        });
    }
}
