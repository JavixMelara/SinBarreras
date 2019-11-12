package com.example.sinbarrerasudb.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sinbarrerasudb.MainActivity;
import com.example.sinbarrerasudb.R;
import com.example.sinbarrerasudb.adapters.EjerciciosTemasSelectorAdapter;
import com.example.sinbarrerasudb.clases.DB.AppDatabase;
import com.example.sinbarrerasudb.clases.DB.Queries;
import com.example.sinbarrerasudb.clases.PreferenciasAjustes;
import com.example.sinbarrerasudb.clases.metodos_aux;
import com.example.sinbarrerasudb.clases.temasData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EjerciciosTemasSelector.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EjerciciosTemasSelector#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EjerciciosTemasSelector extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int Ejercicio=0;

    EjerciciosTemasSelectorAdapter adapter;
    private String nivel = "";
    RecyclerView recyclerViewTemas;
    ArrayList<temasData> listaTemas;
    Button jugar;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    FragmentActivity myContext;

    PreferenciasAjustes oPreferenciasAjustes = new PreferenciasAjustes();
    metodos_aux net = new metodos_aux();
    boolean FirstTime = true;

    AppDatabase database;
    Queries objectDAO = null;

    ProgressDialog progreso;

    private OnFragmentInteractionListener mListener;

    public EjerciciosTemasSelector() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EjerciciosTemasSelector.
     */
    // TODO: Rename and change types and number of parameters
    public static EjerciciosTemasSelector newInstance(String param1, String param2) {
        EjerciciosTemasSelector fragment = new EjerciciosTemasSelector();
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
            nivel = getArguments().getString("nivel");
            Ejercicio= getArguments().getInt("Ejercicio");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context=getContext();
        View vista=inflater.inflate(R.layout.fragment_ejercicios_temas_selector, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Temas");
        //color Toolbar
        MainActivity myActivity = (MainActivity) context;
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) myActivity.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.barra2));
        recyclerViewTemas=vista.findViewById(R.id.recycler_temas_selector);
        recyclerViewTemas.setLayoutManager(new LinearLayoutManager(getContext()));

        listaTemas = new ArrayList<>();

        request= Volley.newRequestQueue(getContext());

        if(oPreferenciasAjustes.getPreferenceSwitchOnline(getContext()))
        {
            if(net.isOnlineNet())
                cargarWebService();
            else
                cargarAlertDialog("Activa tu conexión a internet para ver el contenido","Sin conexión",new Niveles());
        }

        else
            Offline();

        // Inflate the layout for this fragment
        jugar = vista.findViewById(R.id.btnSiguiente);

        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean indicador=false;
                String ids="";
                for (int i=0; i<listaTemas.size(); i++)
                {
                    View a = recyclerViewTemas.getLayoutManager().findViewByPosition(i);
                    Switch seleccion= a.findViewById(R.id.seleccion);
                    if(seleccion.isChecked())
                    {
                        indicador=true;
                        ids+= listaTemas.get(recyclerViewTemas.getChildAdapterPosition(a)).getId_tema()+" ";
                    }
                }

                if(!indicador)
                    cargarAlertDialog("No has seleccionado ningún tema","Error", null);
                else
                {
                    Fragment fragment= null;
                    Context context=getContext();
                    MainActivity myActivity = (MainActivity) context;
                    Bundle myBundle = new Bundle();

                    myBundle.putString("ids",ids.trim());
                    //Temas_niveles_offline.setNivel(1);

                    if(Ejercicio==1)
                    {
                         fragment= new EjerciciosInterpreta();
                    }
                    else
                    {
                         fragment = new EjerciciosElije();
                    }

                   // Temas_niveles fragment = new Temas_niveles();
                    fragment.setArguments(myBundle);

                    myActivity.getSupportFragmentManager().
                            beginTransaction().replace(R.id.content_main,fragment).
                            addToBackStack("fragment").commit();
                }
            }
        });
        return vista;
    }

    //metodos
    private void Offline() {

        database = AppDatabase.getAppDatabase(getContext());
        objectDAO = database.getQueries();

            //realizando consulta para llenar la lista
            listaTemas = (ArrayList<temasData>) objectDAO.getTemasNamesNotNull(nivel);
            if(listaTemas.size()==0)
                cargarAlertDialog("Aún no has abierto este nivel en modo Offline","Error",new Niveles());
            else
                LlenarAdaptador();


    }

    private void cargarWebService() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        String url = "http://192.168.1.3/ejemploBDremota/wsJSONConsultarTemas.php?id_nivel=" + nivel;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.i("Error", error.toString());
        progreso.hide();

        cargarAlertDialog("Parece que hay un problema con el servidor, no podemos " +
                "acceder al contenido en este momento, intenta más tarde.", "Ups...",new Niveles());
    }
    @Override
    public void onResponse(JSONObject response) {
        temasData temasData = null;
        JSONArray json = response.optJSONArray("temas");
        try {
            for (int i = 0; i < json.length(); i++) {
                temasData = new temasData();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);
                temasData.setId_tema(jsonObject.optInt("id_tema"));
                temasData.setNombre(jsonObject.optString("nombre"));
                temasData.setNivel(jsonObject.optInt("id_nivel"));

                listaTemas.add(temasData);


            }//fin del  for
        } catch (JSONException e) {
            e.printStackTrace();
            progreso.hide();

        }

        LlenarAdaptador();
        progreso.hide();
    }//fin onResponse

    private void LlenarAdaptador() {
        adapter = new EjerciciosTemasSelectorAdapter(listaTemas, getContext());
        recyclerViewTemas.setAdapter(adapter);

    }

    private void cargarAlertDialog(String texto, String nombre, final Fragment fragment) {
        AlertDialog.Builder SinConexion = new AlertDialog.Builder(getContext());
        SinConexion.setMessage(texto)
                .setCancelable(false)
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                       // Niveles fragment = new Niveles();
                        if(fragment!=null)
                        {
                            Context context = getContext();
                            MainActivity myActivity = (MainActivity) context;
                            myActivity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.content_main, fragment)
                                    .addToBackStack("fragment").commit();
                        }

                    }
                });
        AlertDialog titulo = SinConexion.create();
        titulo.setTitle(nombre);
        titulo.show();
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
