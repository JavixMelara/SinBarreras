package com.example.sinbarrerasudb.clases;

import java.util.ArrayList;
import java.util.List;

public class temasCenter {

    private List<temasData> temasCenterList;
    String[] nombre_temas={"Alfabeto","Los números","La familia","Frases comunes","Pronombres personales"};
    int n_temas=nombre_temas.length;
    temasData[] temas= new temasData[n_temas];


    public temasCenter() {
        temasCenterList= new ArrayList<>();
      for (int i=0;i<n_temas;i++)
      {
          temas[i]= new temasData();
          temas[i].setNivel(1);
          temas[i].setNombre(nombre_temas[i]);
          temasCenterList.add(temas[i]);
      }
    }

    public List<temasData> getTemasCenterList() {
        return temasCenterList;
    }

    public void setTemasCenterList(List<temasData> temasCenterList) {
        this.temasCenterList = temasCenterList;
    }
}
