package com.example.sinbarrerasudb.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sinbarrerasudb.R;
import com.example.sinbarrerasudb.clases.seniasData;
import com.example.sinbarrerasudb.fragments.Temas_niveles;

import java.util.ArrayList;

public class contenidoAdapter extends RecyclerView.Adapter<contenidoAdapter.ContenidoViewHolder>
 implements  View.OnClickListener{



    ArrayList<seniasData> listaContenido;
    Context context;
    private View.OnClickListener listener;

    public contenidoAdapter(ArrayList<seniasData> listaContenido, Context context) {
        this.listaContenido = listaContenido;
        this.context = context;
    }


    @NonNull
    @Override
    public contenidoAdapter.ContenidoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_contenido,null,false);
        view.setOnClickListener(this);
        return new contenidoAdapter.ContenidoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull contenidoAdapter.ContenidoViewHolder contenidoViewHolder, int i) {
        contenidoViewHolder.nombre.setText(""+listaContenido.get(i).getTitulo());
    }

    @Override
    public int getItemCount() {
        return listaContenido.size();
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

    public class ContenidoViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        public ContenidoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre= itemView.findViewById(R.id.nombre_contenido);
        }
    }
}
