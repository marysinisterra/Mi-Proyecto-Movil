package com.example.lugar_favorito.lista;

import com.bumptech.glide.Glide;
import com.example.lugar_favorito.Mapa.Main_Mapa;
import com.example.lugar_favorito.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.lugar_favorito.lista.database.Persona;
import com.example.lugar_favorito.lista.database.PersonaLab;

public class datossitio extends AppCompatActivity {

Persona persona;
PersonaLab personalab;
private ImageView sitiopre;
private String sitio,direccion, menu, horario, cali,telefono;
double latitud, longitud;

private Button btninicio,btnubicacion;
private TextView nombrepre, direcpre, menupre,horariopre,calipre, telepre,latitudpre,longitudpre;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        permiso();
        // Inicializo el objeto PersonaLab para interactuar con la base de datos.
        personalab=new PersonaLab(this);
        // Obtengo los extras del intent para obtener el ID de la persona.
        Bundle extras=getIntent().getExtras();
        int id = extras.getInt("id");
// Obtengo la persona de la base de datos según el ID.
        persona=new Persona();
        persona=personalab.getPersona(String.valueOf(id+1));

        // Obtengo los detalles de la persona.
            sitio = persona.getNombre();
            direccion = persona.getDireccion();
            latitud = persona.getLatitud();
            longitud = persona.getLongitud();
            menu = persona.getMenu();
            horario = persona.getHorario();
            telefono = String.valueOf(persona.getTelefono());
            cali = String.valueOf(persona.getCalificacion());
            byte[] imagen = persona.getFoto();
// Obtengo las referencias a las vistas del diseño XML.
            sitiopre = findViewById(R.id.sitiopres);
            nombrepre = findViewById(R.id.nombrepre);
            direcpre = findViewById(R.id.direcpre);
            latitudpre = findViewById(R.id.textViewLatitud);
            longitudpre = findViewById(R.id.textViewLongitud);
            menupre = findViewById(R.id.menupre);
            horariopre = findViewById(R.id.horariopre);
            telepre = findViewById(R.id.telepre);
            calipre = findViewById(R.id.calipre);

            nombrepre.setText(sitio);
            direcpre.setText(direccion);
            latitudpre.setText(String.valueOf(latitud));
            longitudpre.setText(String.valueOf(longitud));
            menupre.setText(menu);
            horariopre.setText(horario);
            telepre.setText(String.valueOf(telefono));
            calipre.setText(String.valueOf(cali));
            Glide.with(this).load(imagen).into(sitiopre);

            btnubicacion= findViewById(R.id.verubic);
           btnubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verUbicacion();
            }
        });

        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                // Regresar a la actividad anterior
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void verUbicacion() {
        // Obtener las coordenadas almacenadas en la base de datos
        double latitud = persona.getLatitud();
        double longitud = persona.getLongitud();

        // Crear un Intent para abrir la MapActivity
        Intent intent = new Intent(datossitio.this, Main_Mapa.class);
        intent.putExtra("LATITUD", latitud);
        intent.putExtra("LONGITUD", longitud);
        startActivity(intent);
    }
    private void permiso() {
        if (ActivityCompat.checkSelfPermission(datossitio.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != android.content.pm.PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(datossitio.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != android.content.pm.PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        }
    }

}
