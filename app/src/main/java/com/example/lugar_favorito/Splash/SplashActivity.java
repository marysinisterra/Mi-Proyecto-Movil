package com.example.lugar_favorito.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.lugar_favorito.R;
import com.example.lugar_favorito.lista.main_lista;

public class SplashActivity extends AppCompatActivity {
    private final int DURATION_SPLASH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, main_lista.class);
                startActivity(intent);
                finish();
            };
        },DURATION_SPLASH);


    }
}