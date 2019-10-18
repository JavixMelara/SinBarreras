package com.example.sinbarrerasudb.clases;

import java.util.ArrayList;

public class consultarSenias {

    static ArrayList<seniasData> listaSenias;
    static int lastTopic;

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
