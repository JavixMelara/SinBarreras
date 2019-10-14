package com.example.sinbarrerasudb.clases;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "NotasSenias")
public class notasData implements Serializable{

    @PrimaryKey
    @NonNull
    private String NombreSenia; //es la ruta de la imagen
    private String titulo;
    private int id;
    private String nota;
    private int nivel;
    private int tema;
    private boolean Online; //true indica que esta nota fue guardada cuando se veia una seña en modo Online

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




    public boolean isOnline() {
        return Online;
    }

    public void setOnline(boolean online) {
        Online = online;
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

    public void setNombreSenia(@NonNull String nombreSenia) {
        NombreSenia = nombreSenia;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

}
