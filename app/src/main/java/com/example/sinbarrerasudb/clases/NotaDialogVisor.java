package com.example.sinbarrerasudb.clases;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.sinbarrerasudb.R;
import com.example.sinbarrerasudb.clases.DB.AppDatabase;
import com.example.sinbarrerasudb.clases.DB.Queries;


public class NotaDialogVisor {
    AppDatabase database;
    Queries objectDao;
    PreferenciasAjustes oPreferenciasAjustes = new PreferenciasAjustes();
    public NotaDialogVisor(Context context, String texto) {
        final Dialog dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.visor_notas);

        final TextView nota;


        nota = dialogo.findViewById(R.id.nota);

        //asignando texto
        nota.setText(texto);

        dialogo.show();

    }
}
