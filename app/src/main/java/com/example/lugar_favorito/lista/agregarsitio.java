package com.example.lugar_favorito.lista;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.lugar_favorito.Mapa.Main_Mapa;
import com.example.lugar_favorito.lista.database.Persona;
import com.example.lugar_favorito.R;
import com.example.lugar_favorito.lista.database.PersonaLab;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class agregarsitio extends AppCompatActivity implements View.OnClickListener{
    //Declaro las vistas y variables necesarias
    private EditText editSitio, editDireccion, editMenu, editHorario, editCali, edittelefono,editTextLatitud,editTextLongitud;
    private PersonaLab personaLab;
    String sitio,direccion, menu, horario, cali, telefono;
    private Button guardar,camara,ubicacion;
    ImageView imgview;
    private byte[] foto;

    // Método que se ejecuta al crear la actividad.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregarsitio);
        // Verifico los permisos necesarios.
        permiso();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Inicializo las vistas obteniendo sus referencias del diseño XML.
        guardar = findViewById(R.id.btnguardar);
        editSitio = findViewById(R.id.editsitio);
        editDireccion = findViewById(R.id.editdireccion);
        editMenu = findViewById(R.id.editmenu);
        editHorario = findViewById(R.id.edithorario);
        edittelefono=findViewById(R.id.edittelefono);
        editCali = findViewById(R.id.editcali);
        editTextLatitud = findViewById(R.id.editTexLatitud);
        editTextLongitud = findViewById(R.id.editTexLongitud);
        camara = findViewById(R.id.tomarFoto);
        imgview = findViewById(R.id.sitioagre);
        ubicacion=findViewById(R.id.agregarubi);

        // Configuro el OnClickListener para el botón de la cámara.
        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             abrircamara();
            }
        });
        // Recuperar valores de latitud y longitud desde el Intent
        Intent intent = getIntent();
        if (intent != null) {
            double latitud = intent.getDoubleExtra("LATITUD", 0.0);
            double longitud = intent.getDoubleExtra("LONGITUD", 0.0);

            // Mostrar valores en TextViews
            editTextLatitud.setText(String.valueOf(latitud));
            editTextLongitud.setText(String.valueOf(longitud));
        }
// Inicializo la instancia de PersonaLab para interactuar con la base de datos.
        personaLab = new PersonaLab(this);
// Configuro el OnClickListener para el botón de guardar.
        guardar.setOnClickListener(this);
// Configuro el OnClickListener para el botón de ubicación.
        ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abre la Activity del mapa
                Intent intent = new Intent(agregarsitio.this, Main_Mapa.class);
                startActivity(intent);
            }
        });

    }
    // Método que maneja eventos de clic en elementos del menú de la barra de acción.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Regresar a la actividad anterior
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // Método que abre la cámara para capturar una foto.
    public void abrircamara(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1);
    }
    // Método que se ejecuta cuando se obtiene un resultado de otra actividad, como la cámara.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            if (imgBitmap != null) {
                imgview.setImageBitmap(imgBitmap);

                // Convierto la imagen a un array de bytes.
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                foto = stream.toByteArray();
            } else {
                Toast.makeText(this, "La imagen es nula", Toast.LENGTH_SHORT).show();
            }

        }
    }
    // Método que maneja eventos de clic en vistas.
    public void onClick(View v){
        if (v == guardar) {
            insertPersona();
            getAllPersonas();
        }
    }
    // Método para insertar una nueva persona en la base de datos.
    public void insertPersona () {
        // Obtengo los datos ingresados por el usuario desde los EditText.
        sitio = editSitio.getText().toString();
        direccion = editDireccion.getText().toString();
        menu = editMenu.getText().toString();
        horario = editHorario.getText().toString();
        telefono=edittelefono.getText().toString();
        cali = editCali.getText().toString();
        double latitud = Double.parseDouble(editTextLatitud.getText().toString());
        double longitud = Double.parseDouble(editTextLongitud.getText().toString());
// Verifico que no haya campos vacíos.
        if (sitio.isEmpty()|| direccion.isEmpty() || menu.isEmpty() || horario.isEmpty() || cali.isEmpty()||telefono.isEmpty()) {
            showAlert("Todos los campos son obligatorios");
            return;
        }

        // Creo un nuevo objeto Persona con los datos ingresados.
        Persona persona = new Persona();
        persona.setFoto(foto);
        persona.setNombre(sitio);
        persona.setDireccion(direccion);
        persona.setMenu(menu);
        persona.setHorario(horario);
        persona.setTelefono(Integer.parseInt(telefono));
        persona.setCalificacion(Integer.parseInt(cali));
        persona.setLatitud(latitud);
        persona.setLongitud(longitud);
// Agrego la persona a la base de datos.
         personaLab.addPersona(persona);
        // Limpiar los EditText después de insertar
        editSitio.setText("");
        editDireccion.setText("");
        editMenu.setText("");
        editHorario.setText("");
        edittelefono.setText("");
        editCali.setText("");
        editTextLatitud.setText("");
        editTextLongitud.setText("");
        imgview.setImageBitmap(null);
        showSuccessDialog();

    }
    // Método para mostrar un diálogo de éxito después de guardar.
    private void showSuccessDialog() {
            new AlertDialog.Builder(this)
                    .setTitle("Guardado Exitoso")
                    .setMessage("¿Deseas seguir guardando o regresar a la lista?")
                    .setPositiveButton("Seguir Guardando", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Continuar guardando
                        }
                    })
                    .setNegativeButton("\nRegresar a la Lista", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Regresar a la actividad principal o inicio
                            Intent intent= new Intent(agregarsitio.this, main_lista.class);
                            startActivity(intent);
                            // Cerrar la actividad actual si es necesario
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.btn_star)
                    .show();
    }

    public void getAllPersonas(){
        List<Persona> persona = personaLab.getPersonas();

        }
    //Método para mostrar un diálogo sobre error al guardar.
    private void showAlert(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    //Metodo la manejar los permisos de uso la ubicacion
    private void permiso() {
        if (ActivityCompat.checkSelfPermission(agregarsitio.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != android.content.pm.PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(agregarsitio.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != android.content.pm.PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        }
    }
}