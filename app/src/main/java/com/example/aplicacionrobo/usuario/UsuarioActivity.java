package com.example.aplicacionrobo.usuario;

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
import androidx.navigation.ui.AppBarConfiguration;

import com.example.aplicacionrobo.MainActivity;
import com.example.aplicacionrobo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;


public class UsuarioActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText txtcorreo, txtcontrasena;
    private AppBarConfiguration appBarConfiguration;
    private Button btnsesion, btnregistrarse;
    private static final String TAG = "UsuarioActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.usuario_Act), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //INICIALIZAR AUTENTIFICACION
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        //INICIARLIZAR TEXT VIEW
        txtcorreo = findViewById(R.id.txtUsuario);
        txtcontrasena = findViewById(R.id.txtCon);

        //BOTON INICIO SESION
        btnsesion = findViewById(R.id.btnIniciarSesionUsuario);
        btnsesion.setOnClickListener(this);
        //BOTON REGISTRO
        btnregistrarse = findViewById(R.id.btnRegistrarseUsuario);
        btnregistrarse.setOnClickListener(this);
        ////
        EditText txtCon = findViewById(R.id.txtCon);

        // Variable para rastrear el estado de visibilidad
        final boolean[] isPasswordVisible = {false};

        // Configura el OnTouchListener para detectar clics en el ícono
        txtCon.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Comprueba si el clic fue en el área del drawableEnd
                if (event.getRawX() >= (txtCon.getRight() - txtCon.getCompoundPaddingEnd())) {
                    // Alterna la visibilidad de la contraseña
                    if (isPasswordVisible[0]) {
                        // Ocultar contraseña
                        txtCon.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        txtCon.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visiblility_off, 0);
                    } else {
                        // Mostrar contraseña
                        txtCon.setInputType(InputType.TYPE_CLASS_TEXT);
                        txtCon.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_remove, 0);
                    }
                    // Mueve el cursor al final
                    txtCon.setSelection(txtCon.getText().length());
                    // Alterna el estado
                    isPasswordVisible[0] = !isPasswordVisible[0];
                    return true;
                }
            }
            return false;
        });

        ///
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnRegistrarseUsuario){
            Intent intent= new Intent(this, RegistroUsuarioActivity.class);
            startActivity(intent);
        }else if(view.getId() == R.id.btnIniciarSesionUsuario) {
            String email = txtcorreo.getText().toString();
            String password = txtcontrasena.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Ingrese Usuario/Contaseña", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this,"Espere un Momento",Toast.LENGTH_SHORT).show();
                btnsesion.setEnabled(false);
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (email.isEmpty() || password.isEmpty()) {
                                    Toast.makeText(UsuarioActivity.this, "Ingrese Correo y Contraseña", Toast.LENGTH_LONG).show();
                                } else {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        Toast.makeText(UsuarioActivity.this, "Usuario o Contraseña Incorrectos.",
                                                Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }
                                }
                            }
                        });
            }
            btnsesion.setEnabled(true);
        }

    }
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            //METODO PARA OPTENER EL NOMBRE
            getNombre(optenerEmail(user));
            Intent intent = new Intent(UsuarioActivity.this, MainActivity.class);
            startActivity(intent);
            // startActivity(intent);
            // finish(); // Opcional, si no deseas regresar a esta actividad
        } else {
            // Si el usuario no está autenticado, puedes mostrar un mensaje o resetear campos
            Log.d(TAG, "No hay usuario autenticado");
            // Por ejemplo, limpiar los campos de texto
            txtcorreo.setText("");
            txtcontrasena.setText("");
        }
    }
    private void getNombre(String getEmail) {
        // Buscar el documento del usuario por correo electrónico
        db.collection("Usuarios")
                .whereEqualTo("Correo Electronico", getEmail)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Obtener el primer documento encontrado (asumiendo que el correo electrónico es único)
                        DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                        String documentId = document.getId();

                        //OPTENER EL NOMBRE DEL USUARIO
                        String nombreUsuario = document.getString("Nombre");
                        if(nombreUsuario != null){
                            // Usar el nombre del usuario según sea necesario
                            Toast.makeText(this, "Bienvenido " + nombreUsuario, Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error en la búsqueda", Toast.LENGTH_SHORT).show();
                });
    }
    public String optenerEmail(FirebaseUser user){
        String getEmail;
        getEmail = user.getEmail();
        return getEmail;
    }

}