package com.example.sinbarrerasudb.clases;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

@Entity (tableName = "TemasDataTabla")
public class temasData implements Serializable {

    @PrimaryKey
    @NonNull
    private int id_tema;
    private int nivel;
    private String nombre;
    private int descargado=0;

    public int getDescargado() {
        return descargado;
    }

    public void setDescargado(int descargado) {
        this.descargado = descargado;
    }





    public int getNivel() {
        return nivel;
    }

    public int getId_tema() {
        return id_tema;
    }

    public void setId_tema(@NonNull int id_tema) {
        this.id_tema = id_tema;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
