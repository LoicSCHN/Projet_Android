package com.example.ventevehiculev1.Adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.ventevehiculev1.addAnnonceActivity;
import com.example.ventevehiculev1.models.Annonce;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MesAnnoncesAdapter extends FirebaseRecyclerAdapter<Annonce,MesAnnoncesAdapter.myViewHolder> {

    public MesAnnoncesAdapter(@NonNull FirebaseRecyclerOptions<Annonce> options)
    {
        super(options);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Annonce model)
    {
        holder.title.setText(model.getTitle());
        holder.marque.setText(model.getVoiture().getMarque());
        holder.modele.setText(model.getVoiture().getModele());
        holder.id = this.getRef(position).getKey();
        dlImageFromFireBaseStorage(holder, model.getPhoto().get(0));

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Modifier l'annonce")
                        .setMessage("Que souhaitez-vous faire?")
                        .setPositiveButton("Suppression", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.BDD.child("Annonce").child(model.getId()).removeValue();
                            }
                        })
                        .setNegativeButton("Edition", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(v.getContext(), addAnnonceActivity.class);
                                intent.putExtra("idAnnonce",model.getId());
                                v.getContext().startActivity(intent);
                            }
                        })
                        .setNeutralButton("Retour",null)
                        .show();
                return true;
            }
        });



    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_annonce,parent,false);

        return new MesAnnoncesAdapter.myViewHolder(view);
    }


    public static class myViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        private View.OnClickListener onItemClickerListener;
        private ArrayList<Annonce> mExemple;
        TextView title;
        TextView modele;
        TextView marque;
        String id;
        public View view;

        public myViewHolder(@NonNull View itemView)
        {
            super(itemView);
            title = itemView.findViewById(R.id.titleannonce);
            modele = itemView.findViewById(R.id.modele_annonce_card);
            marque = itemView.findViewById(R.id.marque_annonce_card);
            id="no";
            imageView = (ImageView) itemView.findViewById(R.id.appercu_imageView);
            view = itemView;

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

    private void dlImageFromFireBaseStorage(MesAnnoncesAdapter.myViewHolder holder, String url){
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
