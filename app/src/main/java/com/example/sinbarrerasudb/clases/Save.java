package com.example.sinbarrerasudb.clases;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Save {

        private String guardarImagen (Context context, String folder,String nombre, Bitmap imagen){
            ContextWrapper cw = new ContextWrapper(context);
            File dirImages = cw.getDir("modulo"+folder, Context.MODE_PRIVATE);
            File myPath = new File(dirImages,nombre);

            FileOutputStream fos = null;
            try{
                fos = new FileOutputStream(myPath);
                imagen.compress(Bitmap.CompressFormat.JPEG, 10, fos);
                fos.flush();
            }catch (FileNotFoundException ex){
                ex.printStackTrace();
            }catch (IOException ex){
                ex.printStackTrace();
            }
            return myPath.getAbsolutePath();
        }
}
