package com.example.sinbarrerasudb.clases;

import com.example.sinbarrerasudb.clases.offline.seniasDataOffline;

import java.util.ArrayList;
import java.util.Collections;

public class Permutar {

    public ArrayList<seniasDataOffline> Barajar(ArrayList<seniasDataOffline> senias)
    {
        Collections.shuffle(senias);
        return senias;
    }
}
