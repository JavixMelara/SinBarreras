package com.example.sinbarrerasudb.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.sinbarrerasudb.R;
import com.example.sinbarrerasudb.clases.temasData;

import java.util.ArrayList;

public class EjerciciosTemasSelectorAdapter extends RecyclerView.Adapter<EjerciciosTemasSelectorAdapter.TemasViewHolder>
implements View.OnClickListener, View.OnLongClickListener{

    ArrayList<temasData> listaTemas;
    Context context;
    private View.OnClickListener listener;
    private View.OnLongClickListener listener2;

    public EjerciciosTemasSelectorAdapter(ArrayList<temasData> listaTemas, Context context){
        this.listaTemas=listaTemas;
        this.context=context;
    }
    @NonNull
    @Override
    public TemasViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_ejercicios_temas_selector,null,false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new EjerciciosTemasSelectorAdapter.TemasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemasViewHolder temasViewHolder, int i) {
    temasViewHolder.nombre.setText(""+listaTemas.get(i).getNombre());
//    if(listaTemas.get(i).getDescargado()==1)
//        temasViewHolder.indicador.setBackgroundColor(context.getResources().getColor(R.color.descargado));
//    else
//        temasViewHolder.indicador.setBackgroundColor(context.getResources().getColor(R.color.White));
//
   }

    @Override
    public int getItemCount() {
        return listaTemas.size();
    }

    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener= listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null)
            listener.onClick(v);

    }

    public void setOnLongClickListener(View.OnLongClickListener listener2)
    {
        this.listener2= listener2;
    }

    @Override
    public boolean onLongClick(View v) {
        if(listener2!=null)
            listener2.onLongClick(v);
        return true;
    }

    public class TemasViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        ImageView indicador;
        Switch selector;
        public TemasViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre= itemView.findViewById(R.id.nombre_tema);
            indicador= itemView.findViewById(R.id.indicador);
            selector= itemView.findViewById(R.id.seleccion);
        }
    }

}
