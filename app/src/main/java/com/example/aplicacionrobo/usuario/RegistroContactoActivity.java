package com.example.aplicacionrobo.usuario;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aplicacionrobo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RegistroContactoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtnombre, txttelefono, txtcorreo_electronico;
    private EditText txtcontrasena, txtconfirmarcontrasena;
    private Button btnguardar;
    public String documentId;
    //VARIABLE FIREBASE
    private FirebaseFirestore db;
    //AUTENTIFICACION CUENTA
    @SuppressLint("RestrictedApi")
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_usuario_contacto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registro_usuario_contacto), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    @Override
    public void onClick(View view) {

    }


}