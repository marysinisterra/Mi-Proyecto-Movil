package com.example.lugar_favorito.lista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.lugar_favorito.R;
import com.example.lugar_favorito.lista.database.Persona;
import com.example.lugar_favorito.lista.database.PersonaLab;

import java.util.ArrayList;

public class main_lista extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener {
    // Creo un ArrayList llamado lugaresFavoritos para almacenar objetos de tipo Persona.
    public ArrayList<Persona>lugaresFavoritos=new ArrayList<>();
    // Declaro variables para el RecyclerView, el adaptador, y la base de datos PersonaLab.
    public RecyclerView lista;
    public RecyclerAdapter adapter;
    public PersonaLab personaLab;
    public Button btnAgregarLugar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializo la instancia de PersonaLab para interactuar con la base de datos.
        personaLab=new PersonaLab(this);

        // Creo una instancia del adaptador RecyclerAdapter y lo configuro.
        adapter = new RecyclerAdapter(this, lugaresFavoritos,this);
        adapter.setOnItemClickListener(this);
        // Obtengo una referencia al RecyclerView y configuro su diseño y adaptador.
        lista = findViewById(R.id.recyclerview);
        lista.setLayoutManager(new LinearLayoutManager(this));
        getAllPersonas();
        lista.setAdapter(adapter);
        // Configuro el botón de retroceso en la barra de acción.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnAgregarLugar = findViewById(R.id.btnAgregarLugar);

        // Configuro el OnClickListener para el botón btnAgregarLugar
        btnAgregarLugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 // Cuando se hace clic en el botón, se llama al método Agregarlugarnuevo().
                Agregarlugarnuevo();
            }
        });
    }
    // Método para obtener todas las personas de la base de datos y actualizar la lista
    public void getAllPersonas(){
        lugaresFavoritos.clear();
        lugaresFavoritos.addAll(personaLab.getPersonas());
    }
    // Este método se llama cuando la actividad vuelve a estar en primer plano.
    protected void onResume(){
        super.onResume();
        getAllPersonas();
        adapter.notifyDataSetChanged();
    }
    // Manejo de eventos para el botón de retroceso en la barra de acción.
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Aquí maneja el evento de clic en el botón de retroceso
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // Método para iniciar la actividad para agregar un nuevo lugar.
    private void Agregarlugarnuevo() {
        Intent intent = new Intent(main_lista.this, agregarsitio.class);
        startActivity(intent);
    }
    // Manejo de clics en elementos del RecyclerView.
    @Override
    public void onItemClick(int position) {
        if (position >= 0 && position <lugaresFavoritos.size()){
            Persona lugarseleccionado=lugaresFavoritos.get(position);
// Preparo los datos del lugar seleccionado y inicio la actividad para mostrar los detalles.
            Intent intent = new Intent(main_lista.this, datossitio.class);
            intent.putExtra("id", lugarseleccionado.getId());
            intent.putExtra("NOMBRE", lugarseleccionado.getNombre());
            intent.putExtra("FOTO",lugarseleccionado.getFoto());
            intent.putExtra("DIRECCION", lugarseleccionado.getDireccion());
            intent.putExtra("LATITUD", lugarseleccionado.getLatitud());
            intent.putExtra("LONGITUD", lugarseleccionado.getLongitud());
            intent.putExtra("MENU",lugarseleccionado.getMenu());
            intent.putExtra("HORARIO",lugarseleccionado.getHorario());
            intent.putExtra("TELEFONO", lugarseleccionado.getTelefono());
            intent.putExtra("CALIFICACION", lugarseleccionado.getCalificacion());
            startActivity(intent);
        }

    }
}