package com.example.lugar_favorito.Mapa;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.example.lugar_favorito.R;
import com.example.lugar_favorito.lista.agregarsitio;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import android.content.pm.PackageManager;
import android.Manifest;
import com.google.android.gms.tasks.OnSuccessListener;

public class Main_Mapa extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private Button guardar, ubicacion;
    private LatLng ubicacionGuardada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // Inicializa el ImageView para el botón de ubicación
        ubicacion = findViewById(R.id.btnlocali);
        ubicacion.setOnClickListener(this);
        miPosicion();
        // Inicializa el botón para mostrar la ubicación guardada
        guardar = findViewById(R.id.btnguardarubi);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la latitud y longitud actual
                double latitud = ubicacionGuardada != null ? ubicacionGuardada.latitude : 0.0;
                double longitud = ubicacionGuardada != null ? ubicacionGuardada.longitude : 0.0;

                // Crear un Intent para pasar la información a FormularioActivity
                Intent intent = new Intent(Main_Mapa.this, agregarsitio.class);
                intent.putExtra("LATITUD", latitud);
                intent.putExtra("LONGITUD", longitud);
                startActivity(intent);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMyLocationEnabled(true);
        Intent intent = getIntent();
        double latitud = intent.getDoubleExtra("LATITUD", 0.0);
        double longitud = intent.getDoubleExtra("LONGITUD", 0.0);

        // Agregar un marcador en la ubicación recibida
        LatLng location = new LatLng(latitud, longitud);
        addMarkerToMap(location);
    }

    private void addMarkerToMap(LatLng location) {
        mMap.addMarker(createMarkerOptions(location));
        ubicacionGuardada = location;
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 1000, null);
    }

    private MarkerOptions createMarkerOptions(LatLng location) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title("Ubicación Guardada");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.alfiler));
        return markerOptions;
    }

    private void miPosicion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
            return;
        }

        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            LatLng ubicacion = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 18));
                            mMap.addMarker(new MarkerOptions().position(ubicacion).title("Mi Ubicación"));
                        } else {
                            Toast.makeText(Main_Mapa.this, "Ubicación no disponible", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    @Override
    public void onClick(View view) {
        if (view == ubicacion) {
            miPosicion();
        }
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

            addMarkerToMap(latLng);
            Toast.makeText(Main_Mapa.this, "click en " + latLng, Toast.LENGTH_SHORT).show();
            // Depuración
            Log.d("Main_Mapa", "Click largo en " + latLng);
        }

}

