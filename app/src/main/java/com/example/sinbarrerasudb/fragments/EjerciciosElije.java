package com.example.sinbarrerasudb.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinbarrerasudb.MainActivity;
import com.example.sinbarrerasudb.R;
import com.example.sinbarrerasudb.clases.DB.AppDatabase;
import com.example.sinbarrerasudb.clases.DB.Queries;
import com.example.sinbarrerasudb.clases.PreferenciasAjustes;
import com.example.sinbarrerasudb.clases.Save;
import com.example.sinbarrerasudb.clases.offline.ResponseListener;
import com.example.sinbarrerasudb.clases.offline.seniasDataOffline;
import com.example.sinbarrerasudb.clases.seniasData;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EjerciciosElije.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EjerciciosElije#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EjerciciosElije extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String ids;
    private String nivel;
    private boolean status=false;
    private String[] id;
    private int cantidadTemas=0;
    private int numeroPregunta=0; // posiicion de la lista desordenada actual
    private int respuesta=1; //opcion en donde se encuentra la respuesta
    private boolean usuarioRespondio=false;
    private int puntos=0;
    ArrayList<seniasData> ListaOnline;
    ArrayList<seniasDataOffline> ListaOffline;
    ArrayList<Integer> elejidas = new ArrayList<>(); //número de preguntas a mostrar
    private TextView senia;
    private ImageButton opc1;
    private ImageButton opc2;
    private ImageButton opc3;
    private Button responder;
    private TextView NPreguntas;
    private TextView Puntaje;
    ProgressDialog progreso;
    PreferenciasAjustes preferenciasAjustes;
    ResponseListener response = new ResponseListener();
    AppDatabase database;
    Queries objectDAO = null;
    Save interna;
    private OnFragmentInteractionListener mListener;

    public EjerciciosElije() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EjerciciosInterpreta.
     */
    // TODO: Rename and change types and number of parameters
    public static EjerciciosElije newInstance(String param1, String param2) {
        EjerciciosElije fragment = new EjerciciosElije();
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
            ids= getArguments().getString("ids");
            nivel= getArguments().getString("nivel");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista= inflater.inflate(R.layout.fragment_ejercicioselige_layout,container,false);
        ((MainActivity) getActivity()).setActionBarTitle("Ejercicios");
        Context context=getContext();
        //color Toolbar
        MainActivity myActivity = (MainActivity) context;
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) myActivity.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.BarraEjericios));
        senia= vista.findViewById(R.id.textopreguntadetalle);
        opc1= vista.findViewById(R.id.opcion1_e2);
        opc2= vista.findViewById(R.id.opcion2_e2);
        opc3= vista.findViewById(R.id.opcion3_e2);

        responder= vista.findViewById(R.id.Responder);
        NPreguntas= vista.findViewById(R.id.cantidad);
        Puntaje = vista.findViewById(R.id.cantidadpuntos);
        preferenciasAjustes =  new PreferenciasAjustes();
        //Convirtiendo a vector la cadena de ids recibida
        id= ids.split(" ");
        cantidadTemas=id.length;

        response.setListener(new ResponseListener.ResponseObjectListener() {
            @Override
            public void onErrorLoaded(ArrayList<seniasData> lista) {
                progreso.hide();
                cargarAlertDialog("Parece que hay un problema con el servidor, no podemos " +
                        "acceder al contenido en este momento, intenta más tarde.", "Ups...",new Inicio());
            }

            @Override
            public void onDataLoaded(ArrayList<seniasData> lista) {
                cantidadTemas--;
                for (seniasData o : lista)
                    ListaOnline.add(o);

                if (cantidadTemas != 0) {
                    response.cargarWebService(nivel, id[id.length - cantidadTemas], getContext());
                } else {
                    progreso.hide();
                    validarCantidad(true);
                    Inicializar();
                }
            }
        });

        if (status = preferenciasAjustes.getPreferenceSwitchOnline(getContext())) {
            ListaOnline = new ArrayList<>();
            mostrarProgress();
            response.cargarWebService(nivel, id[0], getContext());
        } else {
            ListaOffline = new ArrayList<>();
            database= AppDatabase.getAppDatabase(getContext());
            objectDAO= database.getQueries();
            interna= new Save();
            mostrarProgress();
            cargarListaOffline();
            progreso.hide();
            validarCantidad(false);
            Inicializar();
        }

        opc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!usuarioRespondio){
                    usuarioRespondio = true;
                    if (respuesta == 1){
                        puntos+=10;
                        opc1.setBackground(getResources().getDrawable(R.drawable.correcta_e2, null));
                    }
                    else {
                        opc1.setBackground(getResources().getDrawable(R.drawable.incorrecta_e2, null));
                        colorearCorrecta();
                    }
                }
            }
        });

        opc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!usuarioRespondio){
                    usuarioRespondio = true;
                    if (respuesta == 2){
                        puntos+=10;
                        opc2.setBackground(getResources().getDrawable(R.drawable.correcta_e2, null));
                    }
                    else {
                        opc2.setBackground(getResources().getDrawable(R.drawable.incorrecta_e2, null));
                        colorearCorrecta();
                    }
                }
            }
        });

        opc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!usuarioRespondio){
                    usuarioRespondio = true;
                    if (respuesta == 3){
                        puntos+=10;
                        opc3.setBackground(getResources().getDrawable(R.drawable.correcta_e2, null));
                    }
                    else {
                        opc3.setBackground(getResources().getDrawable(R.drawable.incorrecta_e2, null));
                        colorearCorrecta();
                    }
                }
            }
        });



        responder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usuarioRespondio) {
                    usuarioRespondio=false;
                    reiniciarColores();
                    Marcadores();
                    Preguntar();
                } else
                    Toast.makeText(getContext(), "Selecciona una opción", Toast.LENGTH_SHORT).show();
                if(numeroPregunta==11) {
                    Context context = getContext();
                    MainActivity myActivity = (MainActivity) context;
                    Bundle myBundle = new Bundle();
                    myBundle.putInt("puntos", puntos);
                    EjerciciosFin fragment = new EjerciciosFin();
                    fragment.setArguments(myBundle);
                    myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).addToBackStack("fragment").commit();
                }

            }
        });

        return vista;
    }

    //metodos
    public ArrayList<seniasDataOffline> BarajarOffline(ArrayList<seniasDataOffline> senias) {
        Collections.shuffle(senias);
        return senias;
    }

    public ArrayList<seniasData> BarajarOnline(ArrayList<seniasData> senias) {
        Collections.shuffle(senias);
        return senias;
    }

    private void cargarAlertDialog(String texto, String nombre, final Fragment fragment) {
        AlertDialog.Builder SinConexion = new AlertDialog.Builder(getContext());
        SinConexion.setMessage(texto)
                .setCancelable(false)
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Context context = getContext();
                        MainActivity myActivity = (MainActivity) context;
                        myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).addToBackStack("fragment").commit();
                    }
                });
        AlertDialog titulo = SinConexion.create();
        titulo.setTitle(nombre);
        titulo.show();
    }

    private void mostrarProgress() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
    }

    private void Inicializar() {
        if (status) //online
        {
            ListaOnline = BarajarOnline(ListaOnline);
            Preguntar();
        } else {
            ListaOffline = BarajarOffline(ListaOffline);
            Preguntar();
        }
    }

    private int Aleatorio(int a, int b) {
        int random = (int) (Math.random() * ((b) - ((a) - 1))) + (a);
        return random;
    }

    private void Preguntar() {
        elejidas = new ArrayList<>();
        elejidas.add(numeroPregunta);
        boolean[] indicadores = {false, false, false};
        int aux = 0; //para extraer numero de opciones incorrectas

        if (status) {
            //colocando imagen principal
            senia.setText(ListaOnline.get(numeroPregunta).getTitulo());
            //colocando la respuesta correcta
            respuesta = Aleatorio(1, 3);
            switch (respuesta) {
                case 1:
                    opc1.setImageBitmap(ListaOnline.get(numeroPregunta).getImagen());
                    indicadores[0] = true;
                    break;

                case 2:
                    opc2.setImageBitmap(ListaOnline.get(numeroPregunta).getImagen());
                    indicadores[1] = true;
                    break;

                case 3:
                    opc3.setImageBitmap(ListaOnline.get(numeroPregunta).getImagen());
                    indicadores[2] = true;
                    break;
            }
            numeroPregunta++;
            //colocando las respuestas incorrectas
            if (!indicadores[0]) {
                aux = Aleatorio(0, ListaOnline.size() - 1);
                while (aux <= numeroPregunta || elejidas.contains(aux))
                    aux = Aleatorio(0, ListaOnline.size() - 1);
               // opc1.setText(ListaOnline.get(aux).getTitulo());
                opc1.setImageBitmap(ListaOnline.get(aux).getImagen());
                elejidas.add(aux);
            }
            if (!indicadores[1]) {
                aux = Aleatorio(0, ListaOnline.size() - 1);
                while (aux <= numeroPregunta || elejidas.contains(aux))
                    aux = Aleatorio(0, ListaOnline.size() - 1);
                opc2.setImageBitmap(ListaOnline.get(aux).getImagen());
                elejidas.add(aux);
            }
            if (!indicadores[2]) {
                aux = Aleatorio(0, ListaOnline.size() - 1);
                while (aux <= numeroPregunta || elejidas.contains(aux))
                    aux = Aleatorio(0, ListaOnline.size() - 1);
                opc3.setImageBitmap(ListaOnline.get(aux).getImagen());
                elejidas.add(aux);
            }

        } else { //modo Offline
            //colocando imagen principal
            senia.setText(ListaOffline.get(numeroPregunta).getTitulo());
            //colocando la respuesta correcta
            respuesta = Aleatorio(1, 3);
            switch (respuesta) {
                case 1:
                    opc1.setImageBitmap( interna.getImageSenia(ListaOffline.get(numeroPregunta).getRuta_imagen_interna(),getContext()));
                    indicadores[0] = true;
                    break;

                case 2:
                    opc2.setImageBitmap( interna.getImageSenia(ListaOffline.get(numeroPregunta).getRuta_imagen_interna(),getContext()));
                    indicadores[1] = true;
                    break;

                case 3:
                    opc3.setImageBitmap( interna.getImageSenia(ListaOffline.get(numeroPregunta).getRuta_imagen_interna(),getContext()));
                    indicadores[2] = true;
                    break;

            }
            numeroPregunta++;
            //colocando las respuestas incorrectas
            if (!indicadores[0]) {
                aux = Aleatorio(0, ListaOffline.size() - 1);
                while (aux <= numeroPregunta || elejidas.contains(aux))
                    aux = Aleatorio(0, ListaOffline.size() - 1);
                opc1.setImageBitmap( interna.getImageSenia(ListaOffline.get(aux).getRuta_imagen_interna(),getContext()));
                elejidas.add(aux);
            }
            if (!indicadores[1]) {
                aux = Aleatorio(0, ListaOffline.size() - 1);
                while (aux <= numeroPregunta || elejidas.contains(aux))
                    aux = Aleatorio(0, ListaOffline.size() - 1);
                opc2.setImageBitmap( interna.getImageSenia(ListaOffline.get(aux).getRuta_imagen_interna(),getContext()));
                elejidas.add(aux);
            }
            if (!indicadores[2]) {
                aux = Aleatorio(0, ListaOffline.size() - 1);
                while (aux <= numeroPregunta || elejidas.contains(aux))
                    aux = Aleatorio(0, ListaOffline.size() - 1);
                opc3.setImageBitmap( interna.getImageSenia(ListaOffline.get(aux).getRuta_imagen_interna(),getContext()));
                elejidas.add(aux);
            }
        }
    }

    private void colorearCorrecta() {
        switch (respuesta) {
            case 1:
                opc1.setBackground(getResources().getDrawable(R.drawable.correcta_e2, null));
                break;

            case 2:
                opc2.setBackground(getResources().getDrawable(R.drawable.correcta_e2, null));
                break;

            case 3:
                opc3.setBackground(getResources().getDrawable(R.drawable.correcta_e2, null));
                break;

        }
    }

    private void reiniciarColores() {
        usuarioRespondio = false;
        opc1.setBackground(getResources().getDrawable(R.drawable.botonredondo, null));
        opc2.setBackground(getResources().getDrawable(R.drawable.botonredondo, null));
        opc3.setBackground(getResources().getDrawable(R.drawable.botonredondo, null));
    }

    private void cargarListaOffline(){
        ArrayList<seniasDataOffline> lista= new ArrayList<>();
        while (cantidadTemas!=0)
        {
            lista= (ArrayList<seniasDataOffline>) objectDAO.getSeniasDataOfflineList(id[id.length-cantidadTemas]);
            for (seniasDataOffline o: lista)
                ListaOffline.add(o);
            cantidadTemas--;
        }
    }

    private void Marcadores(){
        NPreguntas.setText((numeroPregunta+1)+"/10");
        Puntaje.setText(puntos+"");
    }

    private void validarCantidad(boolean Online){
        if(Online){
            if(ListaOnline.size()<=16)
                cargarAlertDialog("El tema tiene muy poco contenido para ejecutar el ejecicio. Incluye más temas","Error", new Ejercicios());
        }
        else{
            if(ListaOffline.size()<=16)
                cargarAlertDialog("El tema tiene muy poco contenido para ejecutar el ejecicio. Incluye más temas","Error",new Ejercicios());
        }
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
