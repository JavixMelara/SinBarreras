package com.example.sinbarrerasudb.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.sinbarrerasudb.adapters.contenidoAdapter;
import com.example.sinbarrerasudb.clases.DB.AppDatabase;
import com.example.sinbarrerasudb.clases.DB.Queries;
import com.example.sinbarrerasudb.clases.PreferenciasAjustes;
import com.example.sinbarrerasudb.clases.Save;
import com.example.sinbarrerasudb.clases.consultarSenias;
import com.example.sinbarrerasudb.clases.metodos_aux;
import com.example.sinbarrerasudb.clases.offline.ResponseListener;
import com.example.sinbarrerasudb.clases.offline.seniasDataOffline;
import com.example.sinbarrerasudb.clases.seniasData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Contenido.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Contenido#newInstance} factory method to
 * create an instance of this fragment.
 */
//Esta clase carga de contenido a ConsultarSenias.java
public class Contenido extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String id_tema;
    private String nivel;
    private String tema;

    RecyclerView recyclerViewContenido;
    ArrayList<seniasData> listaContenido;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    ArrayList<seniasData> listaSenias;
    seniasData seniasData=null;

    PreferenciasAjustes oPreferenciasAjustes = new PreferenciasAjustes();

    AppDatabase database;
    Queries objectDAO = null;

    boolean FirstTime = false;

    seniasDataOffline seniasDataOffline= null;

    contenidoAdapter adapter;

    ResponseListener response = new ResponseListener();

    ProgressDialog progreso;

    metodos_aux net = new metodos_aux();

    private OnFragmentInteractionListener mListener;

    public Contenido() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contenido.
     */
    // TODO: Rename and change types and number of parameters
    public static Contenido newInstance(String param1, String param2) {
        Contenido fragment = new Contenido();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            id_tema= Integer.toString(getArguments().getInt("id_tema")) ;
            nivel = getArguments().getString("nivel");
            tema=getArguments().getString("nombre_tema");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setActionBarTitle(tema);
        View vista=inflater.inflate(R.layout.fragment_contenido, container, false);
        listaContenido= new ArrayList<>();
        recyclerViewContenido=vista.findViewById(R.id.recycler_contenido);
        recyclerViewContenido.setLayoutManager(new LinearLayoutManager(getContext()));
        listaSenias=new ArrayList<>();
        metodos_aux net = new metodos_aux();
        request= Volley.newRequestQueue(getContext());


        response.setListener(new ResponseListener.ResponseObjectListener() {
            @Override
            public void onErrorLoaded(ArrayList<com.example.sinbarrerasudb.clases.seniasData> lista) {
                progreso.hide();
                cargarAlertDialog("Parece que hay un problema con el servidor, no podemos " +
                        "acceder al contenido en este momento, intenta más tarde.", "Ups...");
            }
            @Override
            public void onDataLoaded(ArrayList<com.example.sinbarrerasudb.clases.seniasData> lista) {
                InsertarSeniasDataOffline(lista);
                listaSenias=lista;
                progreso.hide();
                objectDAO.ActualizarEstadoDescarga(id_tema,1);
                LLenarAdaptador();

            }
        });

        //Decidiendo metodo por el cual se obtendran los datos

        if(oPreferenciasAjustes.getPreferenceSwitchOnline(getContext()))
            cargarWebService();
        else
            Offline();

//        if(net.isOnlineNet())
//          //  cargarWebService();
//        else
//            Toast.makeText(getContext(),"Sin conexión",Toast.LENGTH_SHORT).show();

        return  vista;
    }

    private void Offline() {
        database = AppDatabase.getAppDatabase(getContext());
        objectDAO = database.getQueries();

        //verificar si el tema tiene contenido en la BD
        if(objectDAO.getCountTema(id_tema)>0){
            //se realizará un select
            selectContenido();
        }
        else{
            //se insertará el contenido
            FirstTime=true; // en la clase temasData el valor falso significa que es primera vez ( lo contrario de aqui)
           // cargarWebService();
            if(net.isOnlineNet() && FirstTime) {
                progreso = new ProgressDialog(getContext());
                progreso.setMessage("Cargando...");
                progreso.show();
                response.cargarWebService(nivel, id_tema, getContext());
            }
            else
              cargarAlertDialog("Ups... Activa tu conexión a internet para descargarlo","Contenido no descargado");

        }
    }

    private void selectContenido() {
        ArrayList<seniasDataOffline> contenido;
        contenido= new ArrayList<>();
        contenido= (ArrayList<com.example.sinbarrerasudb.clases.offline.seniasDataOffline>) objectDAO.getSeniasDataOfflineList(id_tema);

        Save save = new Save();

        for(seniasDataOffline datos: contenido)
        {
            seniasData = new seniasData();

            seniasData.setTitulo(datos.getTitulo());
            seniasData.setDescripcion(datos.getDescripcion());
            seniasData.setImagen(save.getImageSenia(datos.getRuta_imagen_interna(),getContext()));
            seniasData.setNivel(datos.getNivel());
            seniasData.setTema(datos.getTema());
            seniasData.setNombre_imagen(datos.getRuta_imagen_interna());

            listaSenias.add(seniasData);
        }
        LLenarAdaptador();

    }

    private void cargarWebService() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        String url = "http://192.168.1.3/ejemploBDremota/wsJSONConsultarListaImagenes.php?id_nivel=" + nivel + "&id_tema=" + id_tema;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
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
                seniasData.setNivel(jsonObject.optInt("id_tema"));

                listaSenias.add(seniasData);
            }

            for (final seniasData o : listaSenias) {
                String UrlImagen = "http://192.168.1.3/ejemploBDremota/" + o.getRuta_imagen_servidor();
                UrlImagen.replace(" ", "%20");
                ImageRequest imageRequest = new ImageRequest(UrlImagen, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        o.setImagen(response);
                    }
                }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "No se consulto imagen", Toast.LENGTH_SHORT).show();
                    }
                });
                request.add(imageRequest);
            }

            //a partir de este punto puedo tomar la listaSenias para procesarla en insertar
     //       if (FirstTime)
     //           InsertarSeniasDataOffline(listaSenias);

            LLenarAdaptador();
            progreso.hide();

        } catch (JSONException e) {
            e.printStackTrace();
            progreso.hide();
        }


    }

    private void LLenarAdaptador() {

        consultarSenias.setListaSenias(listaSenias);
        adapter = new contenidoAdapter(listaSenias, getContext());
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle myBundle = new Bundle();
                int id = recyclerViewContenido.getChildAdapterPosition(v);
                myBundle.putInt("posicion", id);
                myBundle.putString("nombre_tema", tema);
                Visor fragment = new Visor();
                //Contenido fragment= new Contenido();
                fragment.setArguments(myBundle);

                Context context = getContext();
                MainActivity myActivity = (MainActivity) context;

                Objects.requireNonNull(myActivity).getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).addToBackStack("fragment").commit();

            }
        });

        recyclerViewContenido.setAdapter(adapter);
    }

    private void InsertarSeniasDataOffline(ArrayList<seniasData> Contenido) {


//        seniasDataOffline.setTitulo(jsonObject.optString("nombre"));
//        seniasDataOffline.setDescripcion(jsonObject.optString("descripcion"));
//        seniasDataOffline.setNivel(Integer.valueOf(nivel));
//        seniasDataOffline.setTema(Integer.valueOf(id_tema));
        for(seniasData datos: Contenido){
            seniasDataOffline = new seniasDataOffline();

            seniasDataOffline.setTema(datos.getTema());
            seniasDataOffline.setNivel(datos.getNivel());
            seniasDataOffline.setTitulo(datos.getTitulo());
            seniasDataOffline.setDescripcion(datos.getDescripcion());
            seniasDataOffline.setRuta_imagen_interna(datos.getNombre_imagen());

            objectDAO.InsertSeniasDataOffline(seniasDataOffline);

            //insertando imagen en memoria
            Save save = new Save();
            //conviertiendo a byte
            save.ConvertBitmapTobyte(datos.getImagen());
            //guardando imagen
            save.SaveOnInternalMemory(datos.getNombre_imagen(), getContext());

        }


        //objectDAO.DeleteSeniasDataOffline(seniasData);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
       // Toast.makeText(getContext(), "No se consulto", Toast.LENGTH_SHORT).show();
       // Log.i("Error", error.toString());
        // imagen.setImageResource(R.drawable.contenido_no_disponible_opt);
        progreso.hide();
        cargarAlertDialog("Parece que hay un problema con el servidor, no podemos " +
                "acceder al contenido en este momento, intenta más tarde.", "Ups...");
    }

    private void cargarAlertDialog(String texto,String nombre) {
        AlertDialog.Builder SinConexion = new AlertDialog.Builder(getContext());
        SinConexion.setMessage(texto)
                .setCancelable(false)
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Context context=getContext();
                        MainActivity myActivity = (MainActivity) context;
                        Bundle myBundle = new Bundle();

                        myBundle.putString("nivel",nivel);
                        //Temas_niveles_offline.setNivel(1);

                        Temas_niveles fragment = new Temas_niveles();
                        fragment.setArguments(myBundle);

                        myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).addToBackStack("fragment").commit();
                    }
                });
        AlertDialog titulo = SinConexion.create();
        titulo.setTitle(nombre);
        titulo.show();
    }

    //metodo de prueba oara abrir visor de seña desde una nota


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
