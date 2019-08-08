package com.example.sinbarrerasudb.clases;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class seniasData {

    private String titulo;
    private String descripcion;
    private String dato;
    private Bitmap imagen;

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

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;

        try{
            byte[] byteCode= Base64.decode(dato,Base64.DEFAULT);
            this.imagen= BitmapFactory.decodeByteArray(byteCode,0,byteCode.length);

        }catch (Exception e) {
        }


    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
