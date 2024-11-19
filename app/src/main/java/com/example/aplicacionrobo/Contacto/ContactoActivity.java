package com.example.aplicacionrobo.Contacto;

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

public class ContactoActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration appBarConfiguration;

    private Button btnSesion, btnCrearContacto;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contacto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.contacto_act), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // BOTONES
        btnSesion = findViewById(R.id.btnIniciarSesionContacto);
        btnSesion.setOnClickListener(this);

        btnCrearContacto = findViewById(R.id.btnRegistrarseContacto);
        btnCrearContacto.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnIniciarSesionContacto){
            Intent intent= new Intent(this, MainActivity.class);
            //startActivity(intent);
        }else if(view.getId() == R.id.btnRegistrarseContacto){
            Intent intent= new Intent(this, RegistroContactoActivity.class);
            startActivity(intent);

        }
    }
}