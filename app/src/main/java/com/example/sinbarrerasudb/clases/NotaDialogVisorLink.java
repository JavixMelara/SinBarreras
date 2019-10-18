package com.example.sinbarrerasudb.clases;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.sinbarrerasudb.MainActivity;
import com.example.sinbarrerasudb.R;
import com.example.sinbarrerasudb.clases.DB.AppDatabase;
import com.example.sinbarrerasudb.clases.DB.Queries;
import com.example.sinbarrerasudb.clases.offline.ResponseListener;
import com.example.sinbarrerasudb.clases.offline.seniasDataOffline;
import com.example.sinbarrerasudb.fragments.Notas;
import com.example.sinbarrerasudb.fragments.Visor;

import java.util.ArrayList;
import java.util.Objects;


public class NotaDialogVisorLink {
    AppDatabase database;
    Queries objectDao;
    ArrayList<seniasData> listaSenias;
    PreferenciasAjustes oPreferenciasAjustes = new PreferenciasAjustes();
    seniasData seniasData;
    Context context;
    int id_tema;
    int posicion;
    String nombreSenia;
    int nivel;
    public NotaDialogVisorLink(final Context context, String texto, final String nombre, final int id_tema, final int nivel) {
        this.context=context;
        this.id_tema=id_tema;
        this.nombreSenia=nombre;
        this.nivel=nivel;
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
                listaSenias= new ArrayList<>();
//                objectDao.DeleteNota(nombre);
//
//                //actualizando listado de notas
//                Notas fragment = new Notas();
//                //Contenido fragment= new Contenido();
//                MainActivity myActivity = (MainActivity) context;
//                Objects.requireNonNull(myActivity).getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).addToBackStack("fragment").commit();

                /* si es de forma local que se consultará la seña hay que verficar si el id del tema de la nota
                 * existe en la base de datos, si existe se mandara esta lista a la calse consultarSenias y se
                 * abrira el fragment visor,enviadole la posición  como parámetro que sera la posicion del
                 * elemento en la lista*/

                //verificando si estamos en offline u online
                if(!oPreferenciasAjustes.getPreferenceSwitchOnline(context))
                {
                    //llenando listaSenias
                    selectContenido();
                    //llenando clase consultarSenias
                    consultarSenias.setListaSenias(listaSenias,id_tema);
                    //abriendo visor
                    openViewer();

                }
                else
                {
                    //verificando conexion a internet
                    metodos_aux net = new metodos_aux();

                    //verificando primero si esta descargada
                    selectContenido();
                    if(listaSenias.size()!=0)
                    {
                        consultarSenias.setListaSenias(listaSenias,id_tema);
                        openViewer();
                    }
                    else if(net.isOnlineNet())
                    {
                            ResponseListener response = new ResponseListener();
                            response.setListener(new ResponseListener.ResponseObjectListener() {
                                @Override
                                public void onErrorLoaded(ArrayList<com.example.sinbarrerasudb.clases.seniasData> lista) {
                                    cargarAlertDialog("Ocurrió un error, intentalo más tarde ","¡Vaya!");
                                }

                                @Override
                                public void onDataLoaded(ArrayList<com.example.sinbarrerasudb.clases.seniasData> lista) {
                                    listaSenias=lista;
                                    openViewer();
                                }
                            });
                            response.cargarWebService(String.valueOf(nivel),String.valueOf(id_tema),context);


                    }
                    else
                    {
                        cargarAlertDialog("Activa tu conexión a internet","Sin conexión");
                    }
                }
                    //online
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

    private void openViewer() {
        //buscando posicion
        boolean indicador=false;
        for (seniasData o : listaSenias) {
            if(o.getNombre_imagen().equals(nombreSenia))
            {
                posicion= listaSenias.indexOf(o);
                indicador=true;
                break;
            }
        }
        if(indicador)
        {
            Bundle myBundle = new Bundle();
            myBundle.putInt("posicion",posicion );
            myBundle.putString("nombre_tema", objectDao.ObtenerNombreTema(id_tema));
            Visor fragment = new Visor();
            //Contenido fragment= new Contenido();
            fragment.setArguments(myBundle);
            MainActivity myActivity = (MainActivity) context;
            Objects.requireNonNull(myActivity).getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).addToBackStack("fragment").commit();
        }
        else
            cargarAlertDialog("No has descargado aún esta seña ","¡Vaya!");

    }

    private void selectContenido() {
        ArrayList<seniasDataOffline> contenido;
        listaSenias= new ArrayList<>();
        contenido = new ArrayList<>();
        contenido = (ArrayList<com.example.sinbarrerasudb.clases.offline.seniasDataOffline>) objectDao.getSeniasDataOfflineList(String.valueOf(id_tema));

        Save save = new Save();

        for (seniasDataOffline datos : contenido) {
            seniasData = new seniasData();

            seniasData.setTitulo(datos.getTitulo());
            seniasData.setDescripcion(datos.getDescripcion());
            seniasData.setImagen(save.getImageSenia(datos.getRuta_imagen_interna(), context));
            seniasData.setNivel(datos.getNivel());
            seniasData.setTema(datos.getTema());
            seniasData.setNombre_imagen(datos.getRuta_imagen_interna());

            listaSenias.add(seniasData);
        }
    }//fin selectContenido

    private void cargarAlertDialog(String texto, String nombre) {
        AlertDialog.Builder SinConexion = new AlertDialog.Builder(context);
        SinConexion.setMessage(texto)
                .setCancelable(false)
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog titulo = SinConexion.create();
        titulo.setTitle(nombre);
        titulo.show();
    }
}
