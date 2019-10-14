package com.example.sinbarrerasudb.clases.offline;



import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.io.Serializable;

public class notasDataOffline implements Serializable{

    private String NombreSenia; //es la ruta de la imagen
    private String titulo;
    private int id;
    private String Nota;
    private int nivel;
    private int tema;
    private Bitmap miniatura;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private boolean Online; //true indica que esta nota fue guardada cuando se veia una seña en modo Online


    public boolean isOnline() {
        return Online;
    }

    public void setOnline(boolean online) {
        Online = online;
    }

    public Bitmap getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(Bitmap miniatura) {
        this.miniatura = miniatura;
    }


    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getTema() {
        return tema;
    }

    public void setTema(int tema) {
        this.tema = tema;
    }

    @NonNull
    public String getNombreSenia() {
        return NombreSenia;
    }

    public void setNombreSenia(String nombreSenia) {
        NombreSenia = nombreSenia;
    }

    public String getNota() {
        return Nota;
    }

    public void setNota(String nota) {
        Nota = nota;
    }

}
