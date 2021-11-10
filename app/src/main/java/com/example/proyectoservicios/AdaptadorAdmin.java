package com.example.proyectoservicios;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.proyectoservicios.models.Servicios;
import com.example.proyectoservicios.models.Solicitudes;

import java.util.List;


public class AdaptadorAdmin extends RecyclerView.Adapter<AdaptadorAdmin.ViewHolder> {
    private List<Solicitudes> mData;
    private LayoutInflater mInflater;
    private Context context;



    public AdaptadorAdmin(List<Solicitudes> itemList, Context context ){

        this.mInflater= LayoutInflater.from(context);
        this.context=context;
        this.mData=itemList;

    }

    @Override
    public int getItemCount(){return mData.size();}

    @Override
    public AdaptadorAdmin.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.from(parent.getContext()).inflate(R.layout.solicitudes, parent, false);
        return new AdaptadorAdmin.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final AdaptadorAdmin.ViewHolder holder, final int position){

        holder.bindData(mData.get(position));
    }

    public void setItems(List<Solicitudes> items){mData = items;}
    public class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox solicitud;
        ViewHolder(View itemView){
            super(itemView);
            solicitud = itemView.findViewById(R.id.solicitud);

        }
        void bindData(final Solicitudes item){
            //imagen.setColorFilter(Color.parseColor(item.getURLfoto()), PorterDuff.Mode.SRC_IN);
            solicitud.setText(item.getServi());



        }

    }

}