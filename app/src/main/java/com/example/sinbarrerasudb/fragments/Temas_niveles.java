package com.example.sinbarrerasudb.fragments;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
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
import com.example.sinbarrerasudb.adapters.temasAdapter;
import com.example.sinbarrerasudb.clases.DB.AppDatabase;
import com.example.sinbarrerasudb.clases.DB.Queries;
import com.example.sinbarrerasudb.clases.PreferenciasAjustes;
import com.example.sinbarrerasudb.clases.metodos_aux;
import com.example.sinbarrerasudb.clases.seniasData;
import com.example.sinbarrerasudb.clases.temasData;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.GenericSignatureFormatError;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Temas_niveles.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Temas_niveles#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Temas_niveles extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String nivel = "";
    temasAdapter adapter;
    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerViewTemas;
    ArrayList<temasData> listaTemas;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    FragmentActivity myContext;

    PreferenciasAjustes oPreferenciasAjustes = new PreferenciasAjustes();
    metodos_aux net = new metodos_aux();
    boolean FirstTime = true;

    AppDatabase database;
    Queries objectDAO = null;

    public Temas_niveles() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Temas_niveles.
     */
    // TODO: Rename and change types and number of parameters
    public static Temas_niveles newInstance(String param1, String param2) {
        Temas_niveles fragment = new Temas_niveles();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context=getContext();

        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_temas_niveles, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Temas");
        //color Toolbar
        MainActivity myActivity = (MainActivity) context;
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) myActivity.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.barra2));
        recyclerViewTemas=vista.findViewById(R.id.recycler_temas);
        recyclerViewTemas.setLayoutManager(new LinearLayoutManager(getContext()));

        listaTemas = new ArrayList<>();

        request= Volley.newRequestQueue(getContext());
        //Decidiendo metodo por el cual se obtendran los datos

        if(oPreferenciasAjustes.getPreferenceSwitchOnline(getContext()))
            cargarWebService();
        else
            Offline();

        //  if(net.isOnlineNet())
        //   cargarWebService();
        // else
        //  Toast.makeText(getContext(),"Sin conexión",Toast.LENGTH_SHORT).show();
        return  vista;
    }

    private void Offline() {

        database = AppDatabase.getAppDatabase(getContext());
        objectDAO = database.getQueries();

        // Toast.makeText(getContext(),"Online desactivado",Toast.LENGTH_SHORT).show();
        /*Si es primera ves que se abre el módulo se hará una consulta al SERVIDOR  para guardar los temas en la BD
         * por primera vez , de lo contrario se hará una consulta a la BD para cargar los temas en el RecyclerView*/

        //veficando si es primera vez
    if(nivel.equals("1"))
        FirstTime= oPreferenciasAjustes.getStatusLevel1(getContext());
    else if(nivel.equals("2"))
        FirstTime= oPreferenciasAjustes.getStatusLevel2(getContext());
    else
        FirstTime= oPreferenciasAjustes.getStatusLevel3(getContext());

    //primera ves
        if(!FirstTime)
        {
            if(net.isOnlineNet())
            {
                //realizando consulta a web service
                cargarWebService();
                //actualizando el estado de primera ves
                if (nivel.equals("1"))
                    oPreferenciasAjustes.SaveLevel1FirstTime(getContext(), true);
                else if (nivel.equals("2"))
                    oPreferenciasAjustes.SaveLevel2FirstTime(getContext(), true);
                else
                    oPreferenciasAjustes.SaveLevel3FirstTime(getContext(), true);
            }
            else
                cargarAlertDialog();

        }
        else
        {
            //realizando consulta para llenar la lista
            listaTemas= (ArrayList<temasData>) objectDAO.getTemasDataList(nivel);
            LlenarAdaptador();
        }

    }

    private void cargarAlertDialog() {
        AlertDialog.Builder SinConexion = new AlertDialog.Builder(getContext());
        SinConexion.setMessage("Activa tu conexión a internet, necesitamos descargar algunos recursos")
                .setCancelable(false)
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Niveles fragment = new Niveles();
                        Context context = getContext();
                        MainActivity myActivity = (MainActivity) context;
                        myActivity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_main, fragment)
                                .addToBackStack("fragment").commit();
                    }
                });
        AlertDialog titulo = SinConexion.create();
        titulo.setTitle("Primera vez OffLine");
        titulo.show();
    }

    private void cargarWebService() {
        String url = "http://192.168.1.3/ejemploBDremota/wsJSONConsultarTemas.php?id_nivel=" + nivel;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.i("Error",error.toString());
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
                //si es primera ves
                if (!FirstTime)
                    objectDAO.InsertTemasData(temasData);

            }//fin del  for
        } catch (JSONException e) {
            e.printStackTrace();
        }

        LlenarAdaptador();
    }//fin onResponse

    private void LlenarAdaptador() {
        adapter = new temasAdapter(listaTemas, getContext());
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle myBundle = new Bundle();
                myBundle.putInt("id_tema", (listaTemas.get
                        (recyclerViewTemas.getChildAdapterPosition(v)).getId_tema()));
                myBundle.putString("nivel", nivel);
                myBundle.putString("nombre_tema", listaTemas.get
                        (recyclerViewTemas.getChildAdapterPosition(v)).getNombre());
                Contenido fragment = new Contenido();
                fragment.setArguments(myBundle);

                Context context = getContext();
                MainActivity myActivity = (MainActivity) context;

                myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).addToBackStack("fragment").commit();

            }
        });
        recyclerViewTemas.setAdapter(adapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        myContext=(FragmentActivity) context;
        super.onAttach(context);
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
