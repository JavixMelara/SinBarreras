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



import com.example.sinbarrerasudb.MainActivity;
import com.example.sinbarrerasudb.R;

public class Fragmentejercicios extends Fragment {
    private static final String TAG="Fragment Ejercicios";

    private Button btnNavEjercicios;
    private Button btnNavEjerciciosInterpreta;
    private Button btnNavEjerciciosEscoge;
    private Button btnNavFinEjercicios;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ejercicios_layout, container, false);
        btnNavEjerciciosInterpreta = (Button) view.findViewById(R.id.btnNavEjerciciosInterpreta);
        btnNavEjerciciosEscoge  = (Button) view.findViewById(R.id.btnNavEjerciciosEscoge);

        Log.d(TAG, "onCreateView: started.");


        btnNavEjerciciosInterpreta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnNavEjerciciosEscoge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}
