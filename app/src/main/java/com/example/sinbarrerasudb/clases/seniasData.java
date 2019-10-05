package com.example.sinbarrerasudb.clases;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import java.io.Serializable;

//@Entity (tableName = "SeniasData")
public class seniasData implements Serializable{

    //@PrimaryKey
    private Long id;
    private String titulo;
    private String descripcion;
    private Bitmap imagen;
    private String ruta_imagen_servidor;
    private int nivel;
    private int tema;
    private String nombre_imagen;
    private String ruta_imagen_interna;

    public String getRuta_imagen_interna() {
        return ruta_imagen_interna;
    }

    public void setRuta_imagen_interna(String ruta_imagen_interna) {
        this.ruta_imagen_interna = ruta_imagen_interna;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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




    public String getRuta_imagen_servidor() {
        return ruta_imagen_servidor;
    }

    public void setRuta_imagen_servidor(String ruta_imagen_servidor) {
        this.ruta_imagen_servidor = ruta_imagen_servidor;
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

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public String getNombre_imagen() {
        return nombre_imagen;
    }

    public void setNombre_imagen(String nombre_imagen) {
        this.nombre_imagen = nombre_imagen;
    }
}
