package com.example.sinbarrerasudb.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sinbarrerasudb.MainActivity;
import com.example.sinbarrerasudb.R;
import com.example.sinbarrerasudb.adapters.contenidoAdapter;
import com.example.sinbarrerasudb.clases.consultarSenias;
import com.example.sinbarrerasudb.clases.metodos_aux;
import com.example.sinbarrerasudb.clases.seniasData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Contenido.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Contenido#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    contenidoAdapter adapter;

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
        if(net.isOnlineNet())
            cargarWebService();
        else
            Toast.makeText(getContext(),"Sin conexión",Toast.LENGTH_SHORT).show();

        return  vista;
    }

    private void cargarWebService() {

        String url="http://192.168.1.3/ejemploBDremota/wsJSONConsultarListaImagenes.php?id_nivel="+nivel+"&id_tema="+id_tema;
        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }
    @Override
    public void onResponse(JSONObject response) {



        JSONArray json=response.optJSONArray("senias");
        try{

            for (int i = 0; i < json.length(); i++) {
                seniasData= new seniasData();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                seniasData.setTitulo(jsonObject.optString("nombre"));
                seniasData.setDescripcion(jsonObject.optString("descripcion"));
                seniasData.setDato(jsonObject.optString("imagen"));
                listaSenias.add(seniasData);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        //inicializar();
        consultarSenias.setListaSenias(listaSenias);
        adapter=new contenidoAdapter(listaSenias,getContext());
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle myBundle= new Bundle();
               int id= recyclerViewContenido.getChildAdapterPosition(v);
               myBundle.putInt("posicion",id);
                myBundle.putString("nombre_tema",tema);
                 Visor fragment= new Visor();
                //Contenido fragment= new Contenido();
                fragment.setArguments(myBundle);

                Context context=getContext();
                MainActivity myActivity = (MainActivity) context;

                myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).addToBackStack("fragment").commit();

            }
        });

        recyclerViewContenido.setAdapter(adapter);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"No se consulto",Toast.LENGTH_SHORT).show();
        Log.i("Error",error.toString());
        // imagen.setImageResource(R.drawable.contenido_no_disponible_opt);
    }




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
