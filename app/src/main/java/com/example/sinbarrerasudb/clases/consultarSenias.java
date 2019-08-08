package com.example.sinbarrerasudb.clases;

import java.util.ArrayList;

public class consultarSenias {

    static ArrayList<seniasData> listaSenias ;

    public static ArrayList<seniasData> getListaSenias() {
        return listaSenias;
    }

    public static void setListaSenias(ArrayList<seniasData> listaSenias) {

        consultarSenias.listaSenias = listaSenias;
    }




}
