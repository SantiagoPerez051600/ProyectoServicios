package com.example.proyectoservicios;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.proyectoservicios.models.Servicios;

import java.util.List;


public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {
    private List<Servicios> mData;
    private LayoutInflater mInflater;
    private Context context;
    final CardsAdapter.OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Servicios item);
    }

    public CardsAdapter(List<Servicios> itemList, Context context , CardsAdapter.OnItemClickListener listener ){

        this.mInflater= LayoutInflater.from(context);
        this.context=context;
        this.mData=itemList;
        this.listener = listener;
    }
    @Override
    public int getItemCount(){return mData.size();}

    @Override
    public CardsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.from(parent.getContext()).inflate(R.layout.cards, parent, false);
        return new CardsAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final CardsAdapter.ViewHolder holder, final int position){
        holder.cv.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        holder.bindData(mData.get(position));

    }

    public void setItems(List<Servicios> items){mData = items;}
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imagen;
        TextView nombre, precio;
        CardView cv;
        ViewHolder(View itemView){
            super(itemView);
            imagen= itemView.findViewById(R.id.imagen);
            nombre = itemView.findViewById(R.id.nombreTV);
            precio= itemView.findViewById(R.id.precioTV);
            cv= itemView.findViewById(R.id.cv);
        }
        void bindData(final Servicios item){
            //imagen.setColorFilter(Color.parseColor(item.getURLfoto()), PorterDuff.Mode.SRC_IN);
            nombre.setText(item.getNombre());
            precio.setText(item.getPrecio());
            Glide.with(context)
                    .load(item.getURLfoto()).centerCrop().crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(imagen);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }

    }

}
