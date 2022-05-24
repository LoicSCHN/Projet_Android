package com.example.ventevehiculev1;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ventevehiculev1.Fragment.DetailsFragment;

public class AnnonceViewHolder extends RecyclerView.ViewHolder{
    private View.OnClickListener onItemClickListener;


    public TextView title, dscrptTct, pos;
    public ImageView imageView;
    public String id;
    public CheckBox checkBox;
    public View view;

    public AnnonceViewHolder(View itemView) {
        super(itemView);
        title=(TextView) itemView.findViewById(R.id.titleannonce);
        pos =  (TextView) itemView.findViewById(R.id.position_annonce_card);
        imageView = (ImageView) itemView.findViewById(R.id.appercu_imageView);
        id = "no";
        checkBox = (CheckBox) itemView.findViewById(R.id.rb_Fav);
        view = itemView;

        itemView.setTag(this);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(itemView.getContext(), id+"coucou", Toast.LENGTH_SHORT).show();
                Fragment fragment =  DetailsFragment.newInstance(id);
                Context context = itemView.getContext();
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }


}