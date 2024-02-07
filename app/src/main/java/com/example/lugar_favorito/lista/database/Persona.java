package com.example.lugar_favorito.lista.database;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;

@Entity(tableName = "lugar_favorito")
public class Persona {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
   @ColumnInfo(name = "nombre")
    @NonNull
     private String nombre;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] foto;

   @ColumnInfo(name = "direccion")

    private String direccion;
    @ColumnInfo(name = "latitud")
    private double latitud;

    @ColumnInfo(name = "longitud")
    private double longitud;

    @ColumnInfo(name = "menu")
    @NonNull
    private String menu;
    @ColumnInfo(name = "horario")
    @NonNull
    private String horario;

    private int telefono;

    @ColumnInfo(name = "calificacion")
    @NonNull
     private int calificacion;
    //private String categoria;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getDireccion(){
        return direccion;
    }
    public void setDireccion(String direccion){
        this.direccion=direccion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    public String getMenu(){
        return menu;
    }
    public void setMenu(String menu){
        this.menu=menu;
    }
    public String getHorario(){
        return horario;
    }
    public void setHorario(String horario){
        this.horario=horario;
    }
    public int getTelefono(){
        return telefono;
    }
    public void setTelefono(int telefono){
        this.telefono=telefono;
    }
    public int getCalificacion() {return calificacion;}

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }


}