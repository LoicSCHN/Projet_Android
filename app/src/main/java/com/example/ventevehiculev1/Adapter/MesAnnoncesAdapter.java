package com.example.ventevehiculev1.Adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

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
        holder.id = this.getRef(position).getKey();

        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Modification sur l'annonce")
                        .setMessage("Choisissez la modification à faire")
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
        private View.OnClickListener onItemClickerListener;
        private ArrayList<Annonce> mExemple;
        TextView title;
        String id;
        public View view;

        public myViewHolder(@NonNull View itemView)
        {
            super(itemView);
            title = itemView.findViewById(R.id.titleannonce);
            id="no";
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
}
