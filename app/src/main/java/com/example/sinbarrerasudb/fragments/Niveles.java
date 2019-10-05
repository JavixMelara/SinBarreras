package com.example.sinbarrerasudb.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sinbarrerasudb.MainActivity;
import com.example.sinbarrerasudb.R;
import com.example.sinbarrerasudb.clases.offline.ResponseListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Niveles.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Niveles#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Niveles extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageButton boton1;
    ImageButton boton2;
    ImageButton boton3;

    private OnFragmentInteractionListener mListener;

    public Niveles() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Niveles.
     */
    // TODO: Rename and change types and number of parameters
    public static Niveles newInstance(String param1, String param2) {
        Niveles fragment = new Niveles();
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
        ((MainActivity) getActivity()).setActionBarTitle("Niveles");
        //color Toolbar
        Context context=getContext();
        MainActivity myActivity = (MainActivity) context;
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) myActivity.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.barra));
        // Inflate the layout for this fragment
         View vista= inflater.inflate(R.layout.fragment_niveles, container, false);

         //nivel 1
        boton1= vista.findViewById(R.id.level1);
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=getContext();
                MainActivity myActivity = (MainActivity) context;
                Bundle myBundle = new Bundle();

                myBundle.putString("nivel","1");
                //Temas_niveles_offline.setNivel(1);

                Temas_niveles fragment = new Temas_niveles();
                fragment.setArguments(myBundle);

                myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).addToBackStack("fragment").commit();
            }
        });

        //nivel 2

        boton2= vista.findViewById(R.id.level2);
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=getContext();
                MainActivity myActivity = (MainActivity) context;
                Bundle myBundle = new Bundle();

                myBundle.putString("nivel","2");
                //Temas_niveles_offline.setNivel(2);

                Temas_niveles fragment = new Temas_niveles();
                fragment.setArguments(myBundle);

                myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).addToBackStack("fragment").commit();
            }
        });

        //nivel 3

        boton3= vista.findViewById(R.id.level3);
        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=getContext();
                MainActivity myActivity = (MainActivity) context;
                Bundle myBundle = new Bundle();

                myBundle.putString("nivel","3");
                //Temas_niveles_offline.setNivel(3);

                Temas_niveles fragment = new Temas_niveles();
                fragment.setArguments(myBundle);

                myActivity.getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).addToBackStack("fragment").commit();
            }
        });
        return vista;
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
