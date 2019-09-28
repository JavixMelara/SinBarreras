package com.example.sinbarrerasudb.clases;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class seniasData {

    private String titulo;
    private String descripcion;
    private Bitmap imagen;
    private String ruta_imagen;
    private int nivel;
    private int tema;

    public String getRuta_imagen() {
        return ruta_imagen;
    }

    public void setRuta_imagen(String ruta_imagen) {
        this.ruta_imagen = ruta_imagen;
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
}
