package com.example.lugar_favorito.lista.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonDAO {

    @Query("SELECT * FROM lugar_favorito")
    List<Persona> getPersona();

    @Query("SELECT * FROM lugar_favorito WHERE id== :uuid")
    Persona getPersona(String uuid);

    @Insert
    void addPersona(Persona p);

    @Delete
    void deletePersona(Persona p);

    @Update
    void updatePersona(Persona p);

    @Query("DELETE FROM lugar_favorito")
    void deleteAllPersona();
}