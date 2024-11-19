package com.example.aplicacionrobo.Contacto;

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
import com.example.aplicacionrobo.usuario.RegistroUsuarioActivity;
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

public class RegistroContactoActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText txtnombre, txttelefono, txtcorreo_electronico;
    private EditText txtcontrasena, txtconfirmarcontrasena;
    private Button btnguardar;
    //VARIABLE FIREBASE
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_contacto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // INICIALIZAR FIREBASE
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // TEXTO
        txtnombre = findViewById(R.id.txtNombreContacto);
        txttelefono = findViewById(R.id.txtTelefonoContacto);
        txtcorreo_electronico = findViewById(R.id.txtCorreoContacto);
        txtcontrasena = findViewById(R.id.txtcontrasenaContacto);
        txtconfirmarcontrasena = findViewById(R.id.txtConfirmarContrasenaContacto);

        //CONFIGURACION BOTON GUARDAR
        btnguardar = findViewById(R.id.btnSiguiente);
        // CREAR ACTION LISTENER PARA EL BOTON
        btnguardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // CONVERTIR VALORES A STRING DE EDIT TEXT
        String nombre = txtnombre.getText().toString().trim();
        String telefono = txttelefono.getText().toString().trim();
        String correo_electronico = txtcorreo_electronico.getText().toString().trim();
        String contrasena = txtcontrasena.getText().toString().trim();
        String confirContrasena = txtconfirmarcontrasena.getText().toString().trim();



        if (view.getId() == R.id.btnSiguiente) { // ESCUCHAR EL EVENTO BOTON GUARDAR
            // REVISAR QUE LOS EDIT TEXT NO ESTEN VACIOS
            if (nombre.isEmpty() || telefono.isEmpty() || correo_electronico.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese todos los datos", Toast.LENGTH_SHORT).show();
            } else {
                if (!correo_electronico.isEmpty()) {
                    if (contrasena.equals(confirContrasena)) {
                        Toast.makeText(this, "Espere un Momento", Toast.LENGTH_SHORT).show();
                        btnguardar.setEnabled(false);
                        crearUsuario(correo_electronico, contrasena);
                    } else {
                        Toast.makeText(this, "Contraseña no Coincide", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Por favor ingrese Correo Electrónico", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void crearUsuario(String correo, String cont) {
        // CONVERTIR VALORES A STRING DE EDIT TEXT
        String nombre = txtnombre.getText().toString().trim();
        String telefono = txttelefono.getText().toString().trim();
        String correo_electronico = txtcorreo_electronico.getText().toString().trim();
        String contrasena = txtcontrasena.getText().toString().trim();
        String confirContrasena = txtconfirmarcontrasena.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(correo, cont)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            //PASAR LOS DATOS A OTRA VIEW
                            postUsuario(nombre, telefono, correo_electronico);


                        } else {
                            if (cont.length() < 6) {
                                Toast.makeText(RegistroContactoActivity.this, "Error: Ingrese una contraseña válida", Toast.LENGTH_LONG).show();
                                Toast.makeText(RegistroContactoActivity.this, "Mínimo 6 Caracteres.", Toast.LENGTH_LONG).show();
                                btnguardar.setEnabled(true);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegistroContactoActivity.this, "Ha ocurrido un error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        btnguardar.setEnabled(true);
                        Log.e("RegistroUsuarioActivity", "Error al guardar usuario", e);
                    }
                });
    }
    public interface CountCallback {
        void onCountReady(int count);
    }
    private void totalDatos(RegistroUsuarioActivity.CountCallback callback) {
        db.collection("Usuarios").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int count = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            count++;
                        }
                        //Toast.makeText(contancoEmergenciaActivity.this, "Total documentos: " + count, Toast.LENGTH_SHORT).show();
                        callback.onCountReady(count);  // Llama al callback con el conteo
                    } else {
                        Log.w("Error", "Error obteniendo documentos.", task.getException());
                        callback.onCountReady(0);  // En caso de error, retorna 0
                    }
                });
    }
    private void postUsuario(String nombre, String telefono, String correoElectronico) {
        Map<String, Object> user = new HashMap<>();
        // Agregar valores al mapa clave-valor
        user.put("Nombre", nombre);
        user.put("Telefono", telefono);
        user.put("CorreoElectronico", correoElectronico);


        // Agregar valores a la base de datos con un ID generado automáticamente
        db.collection("Contactos")
                .add(user) // Aquí se usa `add` para generar un documento con ID único
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(RegistroContactoActivity.this, "Contacto registrado exitosamente", Toast.LENGTH_SHORT).show();
                    btnguardar.setEnabled(true);
                    // Redirigir a otra actividad si es necesario
                    Intent intent = new Intent(RegistroContactoActivity.this, MainActivity.class); // Cambia `OtraActividad` al nombre correcto
                    intent.putExtra("id", documentReference.getId()); // Pasar el ID del documento generado
                    startActivity(intent);
                    // Limpiar campos
                    limpiarCampos();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(RegistroContactoActivity.this, "Fallo al guardar contacto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("RegistroContactoActivity", "Error al guardar contacto", e);
                    btnguardar.setEnabled(true);
                });
    }
    private void limpiarCampos() {
        txtnombre.setText("");
        txttelefono.setText("");
        txtcorreo_electronico.setText("");
        txtcontrasena.setText("");
        txtconfirmarcontrasena.setText("");
    }
}
