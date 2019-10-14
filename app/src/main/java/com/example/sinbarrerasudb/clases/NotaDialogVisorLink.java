package com.example.sinbarrerasudb.clases;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.sinbarrerasudb.MainActivity;
import com.example.sinbarrerasudb.R;
import com.example.sinbarrerasudb.clases.DB.AppDatabase;
import com.example.sinbarrerasudb.clases.DB.Queries;
import com.example.sinbarrerasudb.fragments.Notas;
import com.example.sinbarrerasudb.fragments.Visor;

import java.util.Objects;


public class NotaDialogVisorLink {
    AppDatabase database;
    Queries objectDao;
    PreferenciasAjustes oPreferenciasAjustes = new PreferenciasAjustes();
    public NotaDialogVisorLink(final Context context, String texto, final String nombre) {
        final Dialog dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.visor_notas_link);

        final TextView nota;
        Button ir;
        final Button cerrar;

        nota = dialogo.findViewById(R.id.nota);
        ir = dialogo.findViewById(R.id.Ir);
        cerrar = dialogo.findViewById(R.id.cerrar);

        //asignando texto
        nota.setText(texto);
        ir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database= AppDatabase.getAppDatabase(context);
                objectDao=database.getQueries();
                objectDao.DeleteNota(nombre);

                //actualizando listado de notas
                Notas fragment = new Notas();
                //Contenido fragment= new Contenido();
                MainActivity myActivity = (MainActivity) context;

                Objects.requireNonNull(myActivity).getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).addToBackStack("fragment").commit();

                dialogo.dismiss();
            }
        });

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });

        dialogo.show();

    }
}
