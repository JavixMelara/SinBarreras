package com.example.sinbarrerasudb.clases;

import java.io.Serializable;

public class temasData implements Serializable {

    private int id_tema;
    private int nivel;
    private String nombre;

    public int getNivel() {
        return nivel;
    }

    public int getId_tema() {
        return id_tema;
    }

    public void setId_tema(int id_tema) {
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
