package com.example.aplicacionrobo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.aplicacionrobo.Contacto.ContactoActivity;
import com.example.aplicacionrobo.usuario.UsuarioActivity;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class inicioActivity extends AppCompatActivity  implements  View.OnClickListener{


    private Button btncrearusuario;
    private Button btniniciarsesion;

    private FirebaseAuth mAuth;
    private Button btncrearusuario2;
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
        btncrearusuario= findViewById(R.id.btnUsuario);
        btncrearusuario.setOnClickListener(this);

        btncrearusuario2= findViewById(R.id.btnContacto);
        btncrearusuario2.setOnClickListener(this);


        //BOTON INICIAR SESION
        btniniciarsesion= findViewById(R.id.btnContacto);
        btniniciarsesion.setOnClickListener(this);
    }
    public void comprobarSesion(){
        Intent intent = new Intent();
        Object firebaseUser = mAuth.getCurrentUser();
        if( firebaseUser != null){
            Toast.makeText(this,"si sesión",Toast.LENGTH_SHORT).show();
            /*intent = new Intent(this, MainActivity.class);
            startActivity(intent);

                */
        }else{
            Toast.makeText(this,"No sesesión ", Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    public void onClick(View view) {

        Intent intent = new Intent();
        if (view.getId() == R.id.btnUsuario) {
            intent = new Intent(inicioActivity.this, UsuarioActivity.class);
            startActivity(intent);

        }else if(view.getId() == R.id.btnContacto){
            intent = new Intent(inicioActivity.this, ContactoActivity.class);
            startActivity(intent);
        }


    }

}