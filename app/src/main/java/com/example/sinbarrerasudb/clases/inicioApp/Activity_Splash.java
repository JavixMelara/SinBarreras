package com.example.sinbarrerasudb.clases.inicioApp;

import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sinbarrerasudb.MainActivity;
import com.example.sinbarrerasudb.R;

public class Activity_Splash extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;


    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv=(TextView) findViewById(R.id.inicio);
        iv=(ImageView) findViewById(R.id.icono);
        Animation mianimacion = AnimationUtils.loadAnimation(this, R.anim.transicion);
        tv.startAnimation(mianimacion);
        iv.startAnimation(mianimacion);


        final Intent i=new Intent(this, MainActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();

    }
}

