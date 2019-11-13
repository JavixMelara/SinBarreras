package com.example.sinbarrerasudb.clases.offline;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity (tableName = "SeniasDataOffline")
public class seniasDataOffline implements Serializable{

    @PrimaryKey
    @NonNull
    private Long id;
    private String titulo;
    private String descripcion;
    private String ruta_imagen_interna; //es el nombre de la imagen
    private int nivel;
    private int tema;

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRuta_imagen_interna() {
        return ruta_imagen_interna;
    }

    public void setRuta_imagen_interna(String ruta_imagen_interna) {
        this.ruta_imagen_interna = ruta_imagen_interna;
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
}