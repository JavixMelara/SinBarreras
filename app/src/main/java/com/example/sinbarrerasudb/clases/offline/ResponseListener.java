package com.example.sinbarrerasudb.clases.offline;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sinbarrerasudb.MainActivity;
import com.example.sinbarrerasudb.R;
import com.example.sinbarrerasudb.clases.seniasData;
import com.example.sinbarrerasudb.fragments.Temas_niveles;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResponseListener implements Response.Listener<JSONObject>,Response.ErrorListener {
//oyente
    private ResponseObjectListener listener;
    int contador=0;
    String nivel;
    Context context;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    seniasData seniasData=null;
    ArrayList<seniasData> listaSenias;

    //constructor
    public ResponseListener()
    {
        this.listener=null;
        this.nivel=nivel;
        this.context=context;
    }
    //interfaz
    public interface ResponseObjectListener{

        public void onErrorLoaded(ArrayList<seniasData> lista);

        public void onDataLoaded(ArrayList<seniasData> lista);
    }
    // setter del oyente
    public void setListener(ResponseObjectListener listener) {
        this.listener = listener;
    }


    public void cargarWebService(String nivel, String id_tema, Context context) {
        listaSenias=new ArrayList<>();
        request= Volley.newRequestQueue(context);
        String url="http://192.168.1.3/ejemploBDremota/wsJSONConsultarListaImagenes.php?id_nivel="+nivel+"&id_tema="+id_tema;
        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray json = response.optJSONArray("senias");
        try {

            for (int i = 0; i < json.length(); i++) {
                seniasData = new seniasData();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                seniasData.setTitulo(jsonObject.optString("nombre"));
                seniasData.setDescripcion(jsonObject.optString("descripcion"));
                seniasData.setRuta_imagen_servidor(jsonObject.optString("ruta_imagen"));
                seniasData.setNombre_imagen(jsonObject.optString("imagen"));
                seniasData.setNivel(jsonObject.optInt("id_nivel"));
                seniasData.setTema(jsonObject.optInt("id_tema"));
                listaSenias.add(seniasData);

            }
final boolean indicador = false;
            for (final seniasData o : listaSenias) {
                String UrlImagen = "http://192.168.1.3/ejemploBDremota/" + o.getRuta_imagen_servidor();
                UrlImagen.replace(" ", "%20");
                ImageRequest imageRequest = new ImageRequest(UrlImagen, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        o.setImagen(response);
                        enviar();

                    }
                }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  Toast.makeText(getContext(), "No se consulto imagen", Toast.LENGTH_SHORT).show();
                    }
                });
                request.add(imageRequest);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onErrorResponse(VolleyError error) {
      //  Toast.makeText(getContext(), "No se consulto", Toast.LENGTH_SHORT).show();
        Log.i("Error", error.toString());
        // imagen.setImageResource(R.drawable.contenido_no_disponible_opt);
        listener.onErrorLoaded(null);

    }


    private void enviar() {
        contador++;
        if (contador == listaSenias.size() && listener != null)
            listener.onDataLoaded(listaSenias);

    }



}
