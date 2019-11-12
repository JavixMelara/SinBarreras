package com.example.sinbarrerasudb.clases;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;

public class metodos_aux {

    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public static void wait(Context context, boolean indicador)
    {
        ProgressDialog progreso = new ProgressDialog(context);
        progreso.setMessage("Cargando...");

        if (indicador)
            progreso.show();
        else
            progreso.hide();

    }
}
