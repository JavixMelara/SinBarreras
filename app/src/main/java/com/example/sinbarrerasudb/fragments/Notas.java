package com.example.sinbarrerasudb.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sinbarrerasudb.MainActivity;
import com.example.sinbarrerasudb.R;
import com.example.sinbarrerasudb.adapters.notasAdapter;
import com.example.sinbarrerasudb.clases.DB.AppDatabase;
import com.example.sinbarrerasudb.clases.DB.Queries;
import com.example.sinbarrerasudb.clases.NotaDialogVisorLink;
import com.example.sinbarrerasudb.clases.PreferenciasAjustes;
import com.example.sinbarrerasudb.clases.Save;
import com.example.sinbarrerasudb.clases.consultarSenias;
import com.example.sinbarrerasudb.clases.metodos_aux;
import com.example.sinbarrerasudb.clases.notasData;
import com.example.sinbarrerasudb.clases.offline.ResponseListenerNotas;
import com.example.sinbarrerasudb.clases.offline.notasDataOffline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Notas.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Notas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notas extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    notasAdapter adapter;
    RecyclerView recyclerViewNotas;
    ArrayList<notasData> listaNotas;
    ArrayList<notasDataOffline> listaNotasOffline;

    AppDatabase database;
    Queries objectDao;
    ResponseListenerNotas response = new ResponseListenerNotas(getContext());

    metodos_aux net = new metodos_aux();
    Save memoria = new Save();
    PreferenciasAjustes oPreferenciasAjustes = new PreferenciasAjustes();

    ProgressDialog progreso;

    public Notas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notas.
     */
    // TODO: Rename and change types and number of parameters
    public static Notas newInstance(String param1, String param2) {
        Notas fragment = new Notas();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context=getContext();
        // Inflate the layout for this fragment
         View vista=inflater.inflate(R.layout.fragment_notas, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Notas");
        //color Toolbar
        MainActivity myActivity = (MainActivity) context;
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) myActivity.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.barra2));

        recyclerViewNotas= vista.findViewById(R.id.recycler_notas);
        recyclerViewNotas.setLayoutManager(new LinearLayoutManager(getContext()));

        database= AppDatabase.getAppDatabase(getContext());
        objectDao= database.getQueries();
        progreso = new ProgressDialog(getContext());

        listaNotas=new ArrayList<>();

        response.setListener(new ResponseListenerNotas.ResponseObjectListener() {
            @Override
            public void onErrorLoaded(ArrayList<notasDataOffline> lista) {
                notasOffline(lista,false);
                progreso.hide();
            }

            @Override
            public void onDataLoaded(ArrayList<notasDataOffline> lista) {
               //la lista devuelta ya trae las imagenes Online
                //ahora se agregaran las notas offline
                consultarSenias.setListaNotas(lista);
                notasOffline(lista,true);
                progreso.hide();
            }
        });

        GetInfo();

        return vista;
    }

    private void notasOffline(ArrayList<notasDataOffline> lista, boolean Online)
    {
         ArrayList<notasData> lista2= new ArrayList<>();
         lista2= (ArrayList<notasData>) objectDao.GetNotas(false);

        if (Online) {
            for (notasData o : lista2) {
                notasDataOffline obj= new notasDataOffline();

                obj.setId(o.getId());
                obj.setTitulo(o.getTitulo());
                obj.setNivel(o.getNivel());
                obj.setNombreSenia(o.getNombreSenia());
                obj.setNota(o.getNota());
                obj.setTema(o.getTema());
                obj.setOnline(o.isOnline());
                obj.setMiniatura(memoria.getImageSenia(o.getNombreSenia(), getContext()));
                //uniendo las dos listas
                lista.add(obj);
            }
            //odenando lista
            Collections.sort(lista, new Comparator<notasDataOffline>() {
                @Override
                public int compare(notasDataOffline o1, notasDataOffline o2) {
                    return new Integer(o2.getId()).compareTo(new Integer(o1.getId()));
                }
            });
        }
        else
        {
            Bitmap image;
           for(notasDataOffline o : lista)
           { 
               image=memoria.getImageSenia(o.getNombreSenia(),getContext());
               if(image!=null)
                   o.setMiniatura(image);
               else
               {
                   image = BitmapFactory.decodeResource(getResources(), R.drawable.icono_opt);
                   o.setMiniatura(image);
               }
               
           }
            lista2= (ArrayList<notasData>) objectDao.GetNotas(false);
           
            for (notasData o : lista2) {
                notasDataOffline obj= new notasDataOffline();

                obj.setId(o.getId());
                obj.setTitulo(o.getTitulo());
                obj.setNivel(o.getNivel());
                obj.setNombreSenia(o.getNombreSenia());
                obj.setNota(o.getNota());
                obj.setTema(o.getTema());
                obj.setOnline(o.isOnline());
                obj.setMiniatura(memoria.getImageSenia(o.getNombreSenia(), getContext()));
                //uniendo las dos listas
                lista.add(obj);
            }
            //ordenando lista
            Collections.sort(lista, new Comparator<notasDataOffline>() {
                @Override
                public int compare(notasDataOffline o1, notasDataOffline o2) {
                    return new Integer(o2.getId()).compareTo(new Integer(o1.getId()));
                }
            });
           
        }//fin else principal

        listaNotasOffline=lista;
        llenarAdaptador();
    }

    private void llenarAdaptador() {

        adapter = new notasAdapter (listaNotasOffline, getContext());
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotaDialogVisorLink visor= new NotaDialogVisorLink(getContext(),
                        listaNotasOffline.get(recyclerViewNotas.getChildAdapterPosition(v)).getNota(),
                        listaNotasOffline.get(recyclerViewNotas.getChildAdapterPosition(v)).getNombreSenia(),
                        listaNotasOffline.get(recyclerViewNotas.getChildAdapterPosition(v)).getTema(),
                        listaNotasOffline.get(recyclerViewNotas.getChildAdapterPosition(v)).getNivel());


            }
        });
        adapter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //llamar al alert dialog de eliminacion
                String id = listaNotasOffline.get(recyclerViewNotas.
                        getChildAdapterPosition(v)).getNombreSenia();
                AlertDialogEliminacion("¿Estás seguro?","Eliminar nota",id);
                return false;
            }
        });

        recyclerViewNotas.setAdapter(adapter);
    }


    private void GetInfo() {
        if(oPreferenciasAjustes.getPreferenceSwitchOnline(getContext()))
        {
            if (net.isOnlineNet())
            {
                listaNotas= (ArrayList<notasData>) objectDao.GetNotas(true);
                listaNotasOffline= new ArrayList<>();
                for(notasData o : listaNotas)
                {
                    notasDataOffline obj= new notasDataOffline();

                    obj.setId(o.getId());
                    obj.setTitulo(o.getTitulo());
                    obj.setNivel(o.getNivel());
                    obj.setNombreSenia(o.getNombreSenia());
                    obj.setNota(o.getNota());
                    obj.setTema(o.getTema());
                    obj.setOnline(o.isOnline());

                    listaNotasOffline.add(obj);

                }

                if(consultarSenias.getListaNotas()!=null)
                    notasOffline(consultarSenias.getListaNotas(),true);
                else
                {
                    response.cargarWebService(listaNotasOffline,getContext());
                    progreso.setMessage("Cargando...");
                    progreso.show();
                }
            }
            else
                Toast.makeText(getContext(), "No se cargaron las imágenes", Toast.LENGTH_SHORT).show();


        }
        else
        {
            listaNotas= (ArrayList<notasData>) objectDao.GetNotas(true);
            listaNotasOffline= new ArrayList<>();
            for(notasData o : listaNotas)
            {
                notasDataOffline obj= new notasDataOffline();

                obj.setId(o.getId());
                obj.setTitulo(o.getTitulo());
                obj.setNivel(o.getNivel());
                obj.setNombreSenia(o.getNombreSenia());
                obj.setNota(o.getNota());
                obj.setTema(o.getTema());
                obj.setOnline(o.isOnline());

                listaNotasOffline.add(obj);

            }

            notasOffline(listaNotasOffline,false);
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




    //ordenando las dos listas
//        Collections.sort(lista, new Comparator() {
//        @Override
//        public int compare(Persona p1, Persona p2) {
//            // Aqui esta el truco, ahora comparamos p2 con p1 y no al reves como antes
//            return new Integer(p2.getEdad()).compareTo(new Integer(p1.getEdad()));
//        }
//    });
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


    private void AlertDialogEliminacion(String texto, String nombre, final String id) {
        AlertDialog.Builder SinConexion = new AlertDialog.Builder(getContext());
        SinConexion.setMessage(texto)
                .setCancelable(false)
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        //eliminando de la base de datos
                        objectDao.DeleteNota(id);
                        //actualizando listado de notas
                        Notas fragment = new Notas();
                        //Contenido fragment= new Contenido();
                        MainActivity myActivity = (MainActivity) getContext();
                        Objects.requireNonNull(myActivity).getSupportFragmentManager().
                                beginTransaction().replace(R.id.content_main, fragment).
                                addToBackStack("fragment").commit();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
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
