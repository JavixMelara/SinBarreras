package com.example.sinbarrerasudb.clases.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.sinbarrerasudb.clases.notasData;
import com.example.sinbarrerasudb.clases.offline.seniasDataOffline;
import com.example.sinbarrerasudb.clases.temasData;

import java.util.List;

@Dao
public interface Queries {
    @Insert
    public void InsertTemasData(temasData TemasData);

    @Insert
    public void InsertSeniasDataOffline(seniasDataOffline SeniasDataOffline);

    @Insert
    public void InsertNotasData(notasData NotasData);

    @Update
    public void UpdateTemasData(temasData TemasData);

    @Update
    public void UpdateSeniasDataOffline(seniasDataOffline SeniasDataOffline);

    @Update
    public void UpdateNotasData(notasData NotasData);

    @Delete
    public void DeleteTemasData(temasData TemasData);

    @Delete
    public void DeleteSeniasDataOffline(seniasDataOffline SeniasDataOffline);

    @Delete
    public void DeleteNotasData(notasData NotasData);

    @Query("SELECT * FROM TemasDataTabla WHERE nivel = :nivel")
    public List<temasData> getTemasDataList(String nivel);

    @Query("SELECT * FROM TemasDataTabla as t WHERE t.nivel = :nivel AND t.descargado=1")
    public List<temasData> getTemasNamesNotNull(String nivel);


    @Query("SELECT COUNT(tema) FROM seniasdataoffline WHERE tema = :IdTema")
    public int getCountTema(String IdTema);

    @Query("SELECT * FROM seniasdataoffline WHERE tema = :IdTema")
    public List<seniasDataOffline> getSeniasDataOfflineList(String IdTema);

    @Query("UPDATE TemasDataTabla SET descargado= :estado WHERE id_tema = :id_tema")
    public void ActualizarEstadoDescarga(String id_tema,int estado);

    @Query("DELETE FROM seniasdataoffline WHERE tema = :IdTema")
    public void EliminarSenias(String IdTema);

    @Query("SELECT * FROM NotasSenias WHERE Online = :Estado")
    public List<notasData> GetNotas(boolean Estado);

    @Query("SELECT nota FROM NotasSenias WHERE NombreSenia = :nombre")
    public String getNotaTexto(String nombre);

    @Query("SELECT COUNT(NombreSenia) FROM NotasSenias")
    public int getCountNotas();

    @Query("DELETE FROM NotasSenias WHERE NombreSenia = :nombre")
    public void DeleteNota(String nombre);

    @Query("SELECT nombre FROM TemasDataTabla WHERE id_tema = :IdTema")
    public String ObtenerNombreTema (int IdTema);



}
