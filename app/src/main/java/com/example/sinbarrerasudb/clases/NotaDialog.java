package com.example.sinbarrerasudb.clases;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinbarrerasudb.R;
import com.example.sinbarrerasudb.clases.DB.AppDatabase;
import com.example.sinbarrerasudb.clases.DB.Queries;



public class NotaDialog {
    AppDatabase database;
    Queries objectDao;
    PreferenciasAjustes oPreferenciasAjustes = new PreferenciasAjustes();
    public NotaDialog(final Context context, final int tema, final int nivel, final String nombreImagen,
                      final String titulo, final boolean nuevo, String TextoNota) {
        final Dialog dialogo = new Dialog(context);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.notas_dialog);

        final EditText nota; //TextView
        Button guardar;
        final Button cancelar;


        nota = dialogo.findViewById(R.id.nota);
        guardar = dialogo.findViewById(R.id.guardar);
        cancelar = dialogo.findViewById(R.id.cancelar);

        if(nuevo) //si ya existe
        {
        nota.setText(TextoNota);
        }

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database= AppDatabase.getAppDatabase(context);
                objectDao= database.getQueries();

                notasData obj = new notasData();
                obj.setId(objectDao.getCountNotas()+1);
                obj.setNombreSenia(nombreImagen);
                obj.setTitulo(titulo);
                obj.setNota(nota.getText().toString().trim());
                obj.setTema(tema);
                obj.setNivel(nivel);
                obj.setOnline(oPreferenciasAjustes.getPreferenceSwitchOnline(context));

                if((obj.getNota().trim()).length()>0)
                {
                    if(nuevo)
                        objectDao.UpdateNotasData(obj);
                    else
                        objectDao.InsertNotasData(obj);
                }
                else
                    Toast.makeText(context,"Ninguna nota guardada",Toast.LENGTH_SHORT).show();

                dialogo.dismiss();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
            }
        });

        dialogo.show();

    }
}
