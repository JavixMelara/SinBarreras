package com.example.sinbarrerasudb.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinbarrerasudb.MainActivity;
import com.example.sinbarrerasudb.R;
import com.example.sinbarrerasudb.clases.DB.AppDatabase;
import com.example.sinbarrerasudb.clases.DB.Queries;
import com.example.sinbarrerasudb.clases.NotaDialog;
import com.example.sinbarrerasudb.clases.NotaDialogVisor;
import com.example.sinbarrerasudb.clases.NotaDialogVisorLink;
import com.example.sinbarrerasudb.clases.PreferenciasAjustes;
import com.example.sinbarrerasudb.clases.consultarSenias;
import com.example.sinbarrerasudb.clases.seniasData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Visor.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Visor#newInstance} factory method to
 * create an instance of this fragment.
 */

//Esta clase se alimenta de la clase ConsultarSenias.java
public class Visor extends Fragment{
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
    private int cont_visor=0;
    private int posicion=0;
    ImageView imagen;
    TextView titulo;
    TextView contenido;

    FloatingActionButton left;
    FloatingActionButton right;
    com.getbase.floatingactionbutton.FloatingActionButton editar;
    com.getbase.floatingactionbutton.FloatingActionButton ver;
    //FloatingActionButton editar;
    //FloatingActionButton ver;
    ArrayList<seniasData> listaSenias;

    private OnFragmentInteractionListener mListener;

    PreferenciasAjustes oPreferenciasAjustes = new PreferenciasAjustes();

    AppDatabase database;
    Queries objectDAO = null;

    public Visor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Visor.
     */
    // TODO: Rename and change types and number of parameters
    public static Visor newInstance(String param1, String param2) {
        Visor fragment = new Visor();
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
          //  id_tema= Integer.toString(getArguments().getInt("id_tema")) ;
          //  nivel = getArguments().getString("nivel");
            tema=getArguments().getString("nombre_tema");
          //  Toast.makeText(getContext(),"id_tema"+id_tema,Toast.LENGTH_SHORT).show();
            posicion= getArguments().getInt("posicion");
            cont_visor=posicion;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionBarTitle(tema);
        //color Toolbar
        Context context=getContext();
        MainActivity myActivity = (MainActivity) context;
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) myActivity.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.barra2));
        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_visor, container, false);

        imagen= vista.findViewById(R.id.visor_imagen);
        titulo= vista.findViewById(R.id.titulo);
        contenido= vista.findViewById(R.id.contenido);
        listaSenias=new ArrayList<>();
        listaSenias = consultarSenias.getListaSenias();
        database= AppDatabase.getAppDatabase(getContext());
        objectDAO=database.getQueries();
        inicializar();
      //  request= Volley.newRequestQueue(getContext());

      //  metodos_aux net = new metodos_aux();
       // if(net.isOnlineNet())
        //    cargarWebService();
       // else
       //     Toast.makeText(getContext(),"Sin conexión",Toast.LENGTH_SHORT).show();

        //al iniciar
       // inicializar();inicializar();
        left=vista.findViewById(R.id.btn_left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // cont_atras=cont_adelante;
               cont_visor--;
                if(cont_visor>=0) {
                    titulo.setText("" + listaSenias.get(cont_visor).getTitulo());
                    contenido.setText("" + listaSenias.get(cont_visor).getDescripcion());
                    if (listaSenias.get(cont_visor).getImagen() != null) {
                        imagen.setImageBitmap(listaSenias.get(cont_visor).getImagen());
                    } else {
                        imagen.setImageResource(R.drawable.contenido_no_disponible_opt);
                    }
                }else{
                   // Toast.makeText(getContext(),"fin del tema",Toast.LENGTH_SHORT).show();
                    cont_visor=0;
                }
            }
        });

        right= vista.findViewById(R.id.btn_right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cont_visor++;
                if(cont_visor<listaSenias.size()) {
                    titulo.setText("" + listaSenias.get(cont_visor).getTitulo());
                    contenido.setText("" + listaSenias.get(cont_visor).getDescripcion());
                    if (listaSenias.get(cont_visor).getImagen() != null) {
                        imagen.setImageBitmap(listaSenias.get(cont_visor).getImagen());
                    } else {
                        imagen.setImageResource (R.drawable.contenido_no_disponible_opt);
                    }
                }else{
                    Toast.makeText(getContext(),"fin del tema",Toast.LENGTH_SHORT).show();
                    cont_visor=listaSenias.size()-1;
                }

            }
        });
        ver= vista.findViewById(R.id.ver);
        ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nota;
                nota=objectDAO.getNotaTexto(listaSenias.get(cont_visor).getNombre_imagen());
                if(nota!=null)
                {
                    NotaDialogVisor visor= new NotaDialogVisor(getContext(),nota);
                }
                else
                {
                    Toast.makeText(getContext(),"Crea una nota para ver",Toast.LENGTH_SHORT).show();
                }

            }
        });

        editar = vista.findViewById(R.id.editar);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // NotaDialog nota = new NotaDialog(getContext());
                /* primero se verificará si la nota ya existe, en caso de existir se mostrará
                 * la nota ya existente precargada en el editext y se mandará un parámetro a
                 * notas dialog que le indicará que debe actualizar al presionar el boton guardar*/
                String TextoNota= objectDAO.getNotaTexto(listaSenias.get(cont_visor).getNombre_imagen());
                boolean actualizar= false;
                if(TextoNota!=null) //significa que ya existe una nota
                    actualizar=true;

                NotaDialog nota = new NotaDialog(getContext(),listaSenias.get(cont_visor).getTema(),
                        listaSenias.get(cont_visor).getNivel(),listaSenias.get(cont_visor).getNombre_imagen(),
                        listaSenias.get(cont_visor).getTitulo(),actualizar,TextoNota);
            }
        });

        return vista;
    }

    private void inicializar() {
        titulo.setText(""+listaSenias.get(posicion).getTitulo());
        contenido.setText(""+listaSenias.get(posicion).getDescripcion());
        //modificar esta condicion con el almacenamiento local
        if(oPreferenciasAjustes.getPreferenceSwitchOnline(getContext()))
        {
            if(listaSenias.get(posicion).getImagen()!=null)
            {
                imagen.setImageBitmap(listaSenias.get(posicion).getImagen());
            }
            else{
                imagen.setImageResource(R.drawable.contenido_no_disponible_opt);
            }
        }
        else
        {
            if(listaSenias.get(posicion).getImagen()!=null)
            {
                imagen.setImageBitmap(listaSenias.get(posicion).getImagen());
            }
            else{
                imagen.setImageResource(R.drawable.contenido_no_disponible_opt);
            }
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
