package com.example.sinbarrerasudb.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


import com.example.sinbarrerasudb.R;

public class Fragmentelige extends Fragment {
    private static final String TAG="Fragment Ejercicios";

    private Button btnNavEjercicios;
    private Button btnNavEjerciciosInterpreta;
    private Button btnNavEjerciciosEscoge;
    private Button btnNavFinEjercicios;
    private ImageButton opc1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ejercicioselige_layout, container, false);
        btnNavEjercicios = (Button) view.findViewById(R.id.Regresar);
        btnNavEjerciciosInterpreta = (Button) view.findViewById(R.id.btnNavEjerciciosInterpreta);
        btnNavEjerciciosEscoge  = (Button) view.findViewById(R.id.btnNavEjerciciosEscoge);
        btnNavFinEjercicios  = (Button) view.findViewById(R.id.Responder);
        opc1= view.findViewById(R.id.opcion1);
      // opc1.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_letter_b));
        //opc1.setBackgroundResource(R.drawable.ic_letter_b);
        opc1.setImageResource(R.drawable.ic_letter_b); // esta es la buena


        Log.d(TAG, "onCreateView: started.");

        btnNavEjercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        btnNavFinEjercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}
