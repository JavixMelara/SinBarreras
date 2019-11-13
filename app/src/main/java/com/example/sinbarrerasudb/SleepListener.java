package com.example.sinbarrerasudb;

import com.example.sinbarrerasudb.clases.offline.ResponseListener;
import com.example.sinbarrerasudb.clases.offline.ResponseListenerNotas;

import static java.lang.Thread.sleep;


public class SleepListener {
    private ResponseObjectListener listener;

    public void setListener(ResponseObjectListener listener) {
        this.listener = listener;
    }

    public SleepListener()
    {

    }

    public interface ResponseObjectListener{
        public void WakeUp();
    }

    public void dormir(int time){
        try {
            sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        listener.WakeUp();
    }
}
