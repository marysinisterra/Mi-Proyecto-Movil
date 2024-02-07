package com.example.lugar_favorito.lista;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lugar_favorito.R;
import com.example.lugar_favorito.lista.database.Persona;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<Persona> datos;
    private Context context;
    private OnItemClickListener listener;
    public RecyclerAdapter(Context context , ArrayList<Persona> datos,OnItemClickListener listener){
        this.datos=datos;
        this.context=context;
        this.listener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list,viewGroup,false);
       Log.i(null,"onBindViewHolder; ");
        return new ViewHolder(view);
    }
    public void onBindViewHolder (@NonNull ViewHolder holder, int position){
        Persona persona = datos.get(position);
        if (persona.getFoto() != null && persona.getFoto().length > 0) {
            Glide.with(context)
                    .load(persona.getFoto())
                    .into(holder.getImg());
        } else {
            // Si no hay foto, puedes mostrar una imagen por defecto
            holder.getImg().setImageResource(R.drawable.logo);
        }
        holder.getnombrere().setText(datos.get(position).getNombre());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = holder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position);
            }
            Intent intent = new Intent(context, datossitio.class);
            intent.putExtra("id", position);
            context.startActivity(intent);
        }
    });
    }
    public int getItemCount(){

        return datos.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView txtNombre;
        private ImageView img;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txtNombre= itemView.findViewById(R.id.textView);
            img=itemView.findViewById(R.id.imageView);

        }
        public TextView getnombrere(){

            return txtNombre;
        }
        public ImageView getImg(){

            return img;
        }
    }

}
