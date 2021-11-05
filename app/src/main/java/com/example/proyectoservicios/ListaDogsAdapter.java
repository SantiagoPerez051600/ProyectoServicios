package com.example.proyectoservicios;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class ListaDogsAdapter extends RecyclerView.Adapter<ListaDogsAdapter.ViewHolder> {
    private ArrayList<String>dataset;
    private Context context;
    public ListaDogsAdapter(Context context, ArrayList<String> dataset ) {
        this.context = context;
       this.dataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent , int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_images_api, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        String imagen = dataset.get(position);
        Glide.with(context)
                .load(imagen).centerCrop().crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.fotoDog);
    }
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaDog(ArrayList<String> listaDogs) {
        dataset.addAll(listaDogs);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView fotoDog;
        public ViewHolder(View itemView){
            super(itemView);
            fotoDog= itemView.findViewById(R.id.fotoDog);

        }
    }

}
