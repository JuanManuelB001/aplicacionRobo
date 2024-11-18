package com.example.aplicacionrobo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.aplicacionrobo.databinding.ActivityInicioBinding;
import com.google.firebase.auth.FirebaseAuth;

public class inicioActivity extends AppCompatActivity  implements  View.OnClickListener{


    private Button btncrearusuario;
    private Button btniniciarsesion;
    private FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.inicio), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth =  FirebaseAuth.getInstance();

        comprobarSesion();

        //BOTON USUARIO
        btncrearusuario= findViewById(R.id.btnCrearUsuario);
        btncrearusuario.setOnClickListener(this);

        //BOTON INICIAR SESION
        btniniciarsesion= findViewById(R.id.btnIniciarSesion);
        btniniciarsesion.setOnClickListener(this);
    }
    public void comprobarSesion(){
        Intent intent = new Intent();
        Object firebaseUser = mAuth.getCurrentUser();
        if( firebaseUser != null){
            Toast.makeText(this,"si sesión",Toast.LENGTH_SHORT).show();
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }else{
            Toast.makeText(this,"No sesesión ", Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    public void onClick(View view) {
        /*
        Intent intent = new Intent();
        if (view.getId() == R.id.btnCrearUsuario) {
            intent = new Intent(MainActivity.this, registrarse.class);
            startActivity(intent);

        }else if(view.getId() == R.id.btnIniciarSesion){
            intent = new Intent(MainActivity.this, IniciarSesionActivity.class);
            startActivity(intent);
        }

         */
    }

}