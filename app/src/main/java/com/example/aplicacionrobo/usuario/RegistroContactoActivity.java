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

import com.example.aplicacionrobo.MainActivity;
import com.example.aplicacionrobo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RegistroContactoActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String getEmail;
    private EditText txtnombreContacto, txttelefonoContacto, txtcorreoContacto;
    private Button btnAgregar,btnsiguiente;
    public String getId;
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

        //INSTANCIAR VALORES DEL EDITTEXT DE ACTIVITY_REGISTRARSE
        getId = getIntent().getStringExtra("id");

        // INICIALIZAR FIREBASE
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //CONFIGURACION EDIT-TEXT
        txtnombreContacto = findViewById(R.id.txtNombreContacto);
        txttelefonoContacto = findViewById(R.id.txtTelefonoContacto);
        txtcorreoContacto = findViewById(R.id.txtEmailContacto);

        //BOTON AGREGAR
        btnAgregar = findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(this);

        //CONFIGURACION BOTON
        btnsiguiente = findViewById(R.id.btnSiguiente);
        btnsiguiente.setOnClickListener(RegistroContactoActivity.this);


        Toast.makeText(RegistroContactoActivity.this,"numero--> "+getId,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnSiguiente){
            contactoIsEmpty(getId);

        }else if(view.getId() == R.id.btnAgregar){
            String getNombre = txtnombreContacto.getText().toString().trim();
            String getTelefono = txttelefonoContacto.getText().toString().trim();
            String getCorreo = txtcorreoContacto.getText().toString().trim();

            if(getNombre.isEmpty() || getTelefono.isEmpty() || getCorreo.isEmpty()){
                Toast.makeText(RegistroContactoActivity.this,"Por favor ingrese todos los valores", Toast.LENGTH_SHORT).show();
            }else{
                agregarNuevoContactoEmergencia(getId, getNombre, getTelefono,getCorreo);
                limpiarCampos();
            }

        }
    }

    private void contactoIsEmpty(String documentId) {
        // Obtener el documento del usuario
        db.collection("Usuarios").document(documentId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Leer la lista actual de contactos de emergencia
                        List<Map<String, Object>> contactosActuales = (List<Map<String, Object>>) documentSnapshot.get("Contacto Emergencia");
                        if (contactosActuales.size() <=0) {
                            Toast.makeText(RegistroContactoActivity.this,"Ingrese por lo menos 1 Contacto de Emergencia", Toast.LENGTH_SHORT).show();
                        }else{
                            //MANDAR A OTRA VISTA
                            Toast.makeText(RegistroContactoActivity.this,"Se Ha Creado un Usuario", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistroContactoActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(RegistroContactoActivity.this, "Error Vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                });
    }

    public boolean validar(){
        String nombre = txtnombreContacto.getText().toString().trim();
        String telefono = txttelefonoContacto.getText().toString().trim();
        String correo = txtcorreoContacto.getText().toString().trim();

        if(nombre.isEmpty() || telefono.isEmpty() ||correo.isEmpty()){
            return false;
        }else{
            return  true;
        }
    }

    // Función para agregar un nuevo contacto de emergencia a un usuario existente
    private void agregarNuevoContactoEmergencia(String documentId, String nombreContacto, String telefonoContacto, String correoContacto) {
        // Crear el nuevo contacto a agregar
        Map<String, Object> nuevoContacto = new HashMap<>();
        nuevoContacto.put("NombreContacto", nombreContacto);
        nuevoContacto.put("TelefonoContacto", telefonoContacto);
        nuevoContacto.put("CorreoContacto", correoContacto);

        // Obtener el documento del usuario
        db.collection("Usuarios").document(documentId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Leer la lista actual de contactos de emergencia
                        List<Map<String, Object>> contactosActuales = (List<Map<String, Object>>) documentSnapshot.get("Contacto Emergencia");
                        if (contactosActuales == null) {
                            contactosActuales = new ArrayList<>();
                        }

                        // Agregar el nuevo contacto a la lista
                        contactosActuales.add(nuevoContacto);

                        // Actualizar el campo "Contacto Emergencia" en Firestore
                        db.collection("Usuarios").document(documentId)
                                .update("Contacto Emergencia", contactosActuales)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(RegistroContactoActivity.this, "Contacto de emergencia agregado", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(RegistroContactoActivity.this, "Error al actualizar contactos", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(RegistroContactoActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(RegistroContactoActivity.this, "Error al obtener el documento", Toast.LENGTH_SHORT).show();
                });
    }
    public void limpiarCampos(){
        txtnombreContacto.setText("");
        txttelefonoContacto.setText("");
        txtcorreoContacto.setText("");
    }

}