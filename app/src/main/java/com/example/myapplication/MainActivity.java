package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton, resetPasswordButton;
    private TextView statusTextView;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Vincula los elementos de la UI
        emailEditText = findViewById(R.id.mailEditText);
        passwordEditText = findViewById(R.id.contraseñaEditText);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registroButton);
        resetPasswordButton = findViewById(R.id.resetButton);
        statusTextView = findViewById(R.id.statusTextView);

        // Maneja el inicio de sesión
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                statusTextView.setText("Por favor, ingresa correo y contraseña.");
                return;
            }

            loginUser(email, password);
        });

        // Maneja el registro de usuario
        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                statusTextView.setText("Por favor, ingresa correo y contraseña.");
                return;
            }

            registerUser(email, password);
        });

        // Maneja el restablecimiento de contraseña
        resetPasswordButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                statusTextView.setText("Por favor, ingresa un correo para restablecer la contraseña.");
                return;
            }

            resetPassword(email);
        });
    }



    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Inicio de sesión exitoso.", Toast.LENGTH_SHORT).show();
                        statusTextView.setText("Inicio de sesión exitoso.");
                    } else {
                        statusTextView.setText("Error: " + task.getException().getMessage());
                    }
                });
    }

    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Usuario registrado exitosamente.", Toast.LENGTH_SHORT).show();
                        statusTextView.setText("Usuario registrado exitosamente.");
                    } else {
                        statusTextView.setText("Error: " + task.getException().getMessage());
                    }
                });
    }
    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Correo de restablecimiento enviado.", Toast.LENGTH_SHORT).show();
                        statusTextView.setText("Correo de restablecimiento enviado.");
                    } else {
                        statusTextView.setText("Error: " + task.getException().getMessage());
                    }
                });
    }
}