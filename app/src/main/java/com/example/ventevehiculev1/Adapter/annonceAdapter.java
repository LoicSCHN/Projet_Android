package com.example.ventevehiculev1.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ventevehiculev1.R;
import com.example.ventevehiculev1.models.Annonce;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class annonceAdapter extends FirebaseRecyclerAdapter<Annonce,annonceAdapter.myViewHolder> {

    public annonceAdapter(@NonNull FirebaseRecyclerOptions<Annonce> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Annonce model)
    {
        holder.title.setText(model.getTitle());
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
        private ArrayList<Annonce> mExemple;
        TextView title;


        public myViewHolder(@NonNull View itemView)
        {
            super(itemView);
            title = itemView.findViewById(R.id.titleannonce);
        }
    }
}
