package com.example.sinbarrerasudb;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.sinbarrerasudb.clases.PreferenciasAjustes;
import com.example.sinbarrerasudb.fragments.Ajustes;
import com.example.sinbarrerasudb.fragments.Consulta;
import com.example.sinbarrerasudb.fragments.Contenido;
import com.example.sinbarrerasudb.fragments.Ejercicios;
import com.example.sinbarrerasudb.fragments.EjerciciosElije;
import com.example.sinbarrerasudb.fragments.EjerciciosFin;
import com.example.sinbarrerasudb.fragments.EjerciciosInterpreta;
import com.example.sinbarrerasudb.fragments.EjerciciosNivelSelector;
import com.example.sinbarrerasudb.fragments.EjerciciosTemasSelector;
import com.example.sinbarrerasudb.fragments.Fragmentelige;
import com.example.sinbarrerasudb.fragments.Inicio;
import com.example.sinbarrerasudb.fragments.Niveles;
import com.example.sinbarrerasudb.fragments.Notas;
import com.example.sinbarrerasudb.fragments.Temas_niveles;
import com.example.sinbarrerasudb.fragments.Visor;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Inicio.OnFragmentInteractionListener, Niveles.OnFragmentInteractionListener,
Ejercicios.OnFragmentInteractionListener, Ajustes.OnFragmentInteractionListener, Temas_niveles.OnFragmentInteractionListener,
        Visor.OnFragmentInteractionListener,Consulta.OnFragmentInteractionListener, Contenido.OnFragmentInteractionListener,
        Notas.OnFragmentInteractionListener, EjerciciosInterpreta.OnFragmentInteractionListener,
        EjerciciosElije.OnFragmentInteractionListener, EjerciciosFin.OnFragmentInteractionListener,
        EjerciciosNivelSelector.OnFragmentInteractionListener, EjerciciosTemasSelector.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //asignando el nivel
        PreferenciasAjustes preferenciasAjustes = new PreferenciasAjustes();
        if(preferenciasAjustes.getCurrentLevel(getApplicationContext())==0)
            preferenciasAjustes.SaveCurrentLevel(getApplicationContext(),3);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new Inicio()).commit();


    }

    @Override
    public void onBackPressed() {
       // DrawerLayout drawer = findViewById(R.id.drawer_layout);
       // if (drawer.isDrawerOpen(GravityCompat.START)) {
       //     drawer.closeDrawer(GravityCompat.START);
       // } else {
       //     super.onBackPressed();
       // }
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getSupportFragmentManager();
        int id = item.getItemId();
        Fragment fragment=null;
        Boolean selectFragment= false;

        if (id == R.id.nav_start) {
            fragment= new Inicio();
            //fragment= new Consulta();
            selectFragment= true;

        } else if (id == R.id.nav_levels) {
            fragment= new Niveles();
            selectFragment= true;
        } else if (id == R.id.nav_exercises) {
            fragment= new Ejercicios();
            selectFragment= true;
        }else if (id == R.id.nav_settings) {
            fragment = new Ajustes();
            selectFragment= true;
        } else if (id == R.id.nav_notes) {
            fragment = new Notas();
            selectFragment= true;
        }



        if(selectFragment)
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void setActionBarTitle(String title)
    {
        getSupportActionBar().setTitle(title);
    }


}
