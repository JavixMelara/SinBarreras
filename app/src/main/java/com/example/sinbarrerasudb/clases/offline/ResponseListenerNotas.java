package com.example.sinbarrerasudb.clases.offline;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sinbarrerasudb.clases.notasData;
import com.example.sinbarrerasudb.clases.seniasData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResponseListenerNotas {
//oyente
    private ResponseObjectListener listener;
    int contador=0;
    String nivel;
    Context context;
    RequestQueue request;

    seniasData seniasData=null;
    ArrayList<notasDataOffline> listaNotas;

    //constructor
    public ResponseListenerNotas(Context context)
    {
        this.listener=null;
        this.nivel=nivel;
        this.context=context;


    }
    //interfaz
    public interface ResponseObjectListener{

        public void onErrorLoaded(ArrayList<notasDataOffline> lista);

        public void onDataLoaded(ArrayList<notasDataOffline> lista);
    }
    // setter del oyente
    public void setListener(ResponseObjectListener listener) {
        this.listener = listener;
    }


    public void cargarWebService(final ArrayList<notasDataOffline> listaNotas, Context context) {
        contador=0;
        request= Volley.newRequestQueue(context);
        this.listaNotas=listaNotas;
        final boolean indicador = false;
        for (final notasDataOffline o : listaNotas) {
            String UrlImagen = "http://192.168.1.3/ejemploBDremota/imagenes/" + o.getNombreSenia();
            UrlImagen.replace(" ", "%20");
            ImageRequest imageRequest = new ImageRequest(UrlImagen, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    o.setMiniatura(response);
                    enviar();

                }
            }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  Toast.makeText(getContext(), "No se consulto imagen", Toast.LENGTH_SHORT).show();
                    listener.onErrorLoaded(listaNotas);
                }
            });
            request.add(imageRequest);
        }
    }



    private void enviar() {
        contador++;
        if (contador == listaNotas.size() && listener != null)
            listener.onDataLoaded(listaNotas);

    }



}
