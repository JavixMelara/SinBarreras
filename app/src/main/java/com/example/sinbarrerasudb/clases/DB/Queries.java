package com.example.sinbarrerasudb.clases.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.sinbarrerasudb.clases.offline.seniasDataOffline;
import com.example.sinbarrerasudb.clases.seniasData;
import com.example.sinbarrerasudb.clases.temasData;

import java.util.List;

@Dao
public interface Queries {
    @Insert
    public void InsertTemasData(temasData TemasData);

    @Insert
    public void InsertSeniasDataOffline(seniasDataOffline SeniasDataOffline);

    @Update
    public void UpdateTemasData(temasData TemasData);

    @Update
    public void UpdateSeniasDataOffline(seniasDataOffline SeniasDataOffline);

    @Delete
    public void DeleteTemasData(temasData TemasData);

    @Delete
    public void DeleteSeniasDataOffline(seniasDataOffline SeniasDataOffline);

    @Query("SELECT * FROM TemasDataTabla WHERE nivel = :nivel")
    public List<temasData> getTemasDataList(String nivel);

    @Query("SELECT COUNT(*) FROM seniasdataoffline WHERE tema = :IdTema")
    public int getCountTema(String IdTema);

    @Query("SELECT * FROM seniasdataoffline WHERE tema = :IdTema")
    public List<seniasDataOffline> getSeniasDataOfflineList(String IdTema);

    @Query("UPDATE TemasDataTabla SET descargado= :estado WHERE id_tema = :id_tema")
    public void ActualizarEstadoDescarga(String id_tema,int estado);

}
