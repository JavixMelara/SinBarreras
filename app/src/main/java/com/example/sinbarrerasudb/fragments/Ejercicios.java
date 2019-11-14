package com.example.sinbarrerasudb.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sinbarrerasudb.MainActivity;
import com.example.sinbarrerasudb.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Ejercicios.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Ejercicios#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ejercicios extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnNavEjerciciosInterpreta;
    private Button btnNavEjerciciosEscoge;

    private OnFragmentInteractionListener mListener;

    public Ejercicios() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ejercicios.
     */
    // TODO: Rename and change types and number of parameters
    public static Ejercicios newInstance(String param1, String param2) {
        Ejercicios fragment = new Ejercicios();
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

        View vista = inflater.inflate(R.layout.fragment_ejercicios_layout, container, false);
        ((MainActivity) getActivity()).setActionBarTitle("Ejercicios");
        Context context=getContext();
        //color Toolbar
        MainActivity myActivity = (MainActivity) context;
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) myActivity.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.BarraEjericios));
        btnNavEjerciciosInterpreta = vista.findViewById(R.id.btnNavEjerciciosInterpreta);
        btnNavEjerciciosEscoge  =  vista.findViewById(R.id.btnNavEjerciciosEscoge);

        btnNavEjerciciosInterpreta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("Ejercicio",1);
                EjerciciosNivelSelector fragment = new EjerciciosNivelSelector();
                fragment.setArguments(bundle);
                Context context = getContext();
                MainActivity myActivity = (MainActivity) context;
                myActivity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.content_main, fragment).
                        addToBackStack("fragment").commit();
            }
        });

        btnNavEjerciciosEscoge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("Ejercicio",2);
                EjerciciosNivelSelector fragment = new EjerciciosNivelSelector();
                fragment.setArguments(bundle);
                Context context = getContext();
                MainActivity myActivity = (MainActivity) context;
                myActivity.getSupportFragmentManager().beginTransaction().
                       // replace(R.id.content_main, new EjerciciosElije()).
                        //replace(R.id.content_main, new EjerciciosFin()).
                        replace(R.id.content_main,  fragment).
                        addToBackStack("fragment").commit();

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
