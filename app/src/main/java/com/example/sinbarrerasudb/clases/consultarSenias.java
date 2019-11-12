package com.example.sinbarrerasudb.clases;

import com.example.sinbarrerasudb.clases.offline.notasDataOffline;

import java.util.ArrayList;

public class consultarSenias {

    static ArrayList<seniasData> listaSenias;
    static int lastTopic;
    static ArrayList<notasDataOffline> listaNotas;

    public static ArrayList<notasDataOffline> getListaNotas() {
        return listaNotas;
    }

    public static void setListaNotas(ArrayList<notasDataOffline> listaNotas) {
        consultarSenias.listaNotas = listaNotas;
    }

    public static int getLastTopic() {
        return lastTopic;
    }

    public static ArrayList<seniasData> getListaSenias() {
        return listaSenias;
    }

    public static void setListaSenias(ArrayList<seniasData> listaSenias, int lastTopic) {
        consultarSenias.lastTopic = lastTopic;
        consultarSenias.listaSenias = listaSenias;
    }




}
