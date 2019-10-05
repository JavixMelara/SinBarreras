package com.example.sinbarrerasudb.clases;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

public class Save {

    private Bitmap bmp = null;
    private byte[] imagenbyte;

    public void ConvertBitmapTobyte(Bitmap imagen){
       bmp=imagen;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        imagenbyte=byteArray;
    }

    public void SaveOnInternalMemory(String Nombre, Context context){

        try {
            FileOutputStream outputStream = context.openFileOutput(Nombre, Context.MODE_PRIVATE);
            outputStream.write(imagenbyte);
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public Bitmap getImageSenia(String Nombre, Context context){

        try{
            FileInputStream fileInputStream =
                    new FileInputStream(context.getFilesDir().getPath()+ "/"+Nombre);
            return BitmapFactory.decodeStream(fileInputStream);
        }catch (IOException io){
            io.printStackTrace();
            return null;
        }

    }

}
