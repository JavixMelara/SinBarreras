package com.example.sinbarrerasudb.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sinbarrerasudb.R;
import com.example.sinbarrerasudb.clases.Save;
import com.example.sinbarrerasudb.clases.notasData;
import com.example.sinbarrerasudb.clases.offline.notasDataOffline;

import java.util.ArrayList;

public class notasAdapter extends RecyclerView.Adapter<notasAdapter.NotasViewHolder>
 implements  View.OnClickListener,View.OnLongClickListener{



    ArrayList<notasDataOffline> listaNotas;
    Context context;
    //objeto para cosultar en memoria interna
    Save memoria = new Save();

    private Bitmap imagen;
    private Bitmap imagen2;

    private View.OnClickListener listener;
    private View.OnLongClickListener listener2;

    public notasAdapter(ArrayList<notasDataOffline> listaContenido, Context context) {
        this.listaNotas = listaContenido;
        this.context = context;
    }


    @NonNull
    @Override
    public NotasViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_notas,null,false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new NotasViewHolder(view);
    }

    public void setListener2(View.OnLongClickListener listener2) {
        this.listener2 = listener2;
    }

    @Override
    public void onBindViewHolder(@NonNull NotasViewHolder contenidoViewHolder, int i) {
        //contenidoViewHolder.nombre.setText(""+listaNotas.get(i).getTitulo());
        contenidoViewHolder.nombre_senia.setText(""+listaNotas.get(i).getTitulo());
        //se verificará el numero maximo de caracteres
        //solo se permitiran 50 caracteres para mostrar
        String nota = listaNotas.get(i).getNota();
        if(nota.length()>50)
            nota = nota.substring(0,50)+"...";
        contenidoViewHolder.contenido_nota.setText(""+nota);
        contenidoViewHolder.miniatura.setImageBitmap(listaNotas.get(i).getMiniatura());

        //obteniendo el bitmap segun la ruta que marca el nombre de la imagen
        imagen= memoria.getImageSenia(listaNotas.get(i).getNombreSenia(),context);
        imagen2 = listaNotas.get(i).getMiniatura();
        if(imagen==null && imagen2 == null)
            contenidoViewHolder.miniatura.setImageResource(R.drawable.icono_opt);
    }

    @Override
    public int getItemCount() {
        return listaNotas.size();
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

    @Override
    public boolean onLongClick(View v) {
        if(listener2!=null)
            listener2.onLongClick(v);
        return true;
    }

    public void setOnLongClickListener(View.OnLongClickListener listener2)
    {
        this.listener2= listener2;
    }


    public class NotasViewHolder extends RecyclerView.ViewHolder {
        TextView nombre_senia;
        TextView contenido_nota;
        ImageView miniatura;
        public NotasViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre_senia= itemView.findViewById(R.id.nombre_senia);
            contenido_nota =itemView.findViewById(R.id.contenido_nota);
            miniatura= itemView.findViewById(R.id.miniatura);

        }
    }
}
