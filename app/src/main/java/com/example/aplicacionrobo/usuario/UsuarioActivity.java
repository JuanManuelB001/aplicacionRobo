package com.example.aplicacionrobo.usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.aplicacionrobo.MainActivity;
import com.example.aplicacionrobo.R;


public class UsuarioActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration appBarConfiguration;
    private Button btnsesion, btnregistrarse;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.usuario_Act), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //BOTON INICIO SESION
        btnsesion = findViewById(R.id.btnIniciarSesionUsuario);
        btnsesion.setOnClickListener(this);
        //BOTON REGISTRO
        btnregistrarse = findViewById(R.id.btnRegistrarseUsuario);
        btnregistrarse.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnRegistrarseUsuario){
            Intent intent= new Intent(this, RegistroUsuarioActivity.class);
            startActivity(intent);
        }
    }
}