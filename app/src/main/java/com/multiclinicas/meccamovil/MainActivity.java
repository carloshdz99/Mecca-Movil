package com.multiclinicas.meccamovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    //etiqueta de mensajes en consola
    private static final String TAG = "LoginFirebase";
    //botones
    private Button btnLoginEmail, btnLoginRegister;
    //campos de texto
    private EditText edtEmail, edtPassword;
    //variable firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_MeccaMovil);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //iniciando firebase
        mAuth = FirebaseAuth.getInstance();

        //inicializando variables del layout
        Init();

        //seteando metodos de botones
        //boton para ir a pantalla de registro
        btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
        //boton para logueo con contraseña y correo
        btnLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginEmailandPassword();
            }
        });
    }

    //metodo para iniciar variables
    private void Init(){
        //botones
        btnLoginEmail = findViewById(R.id.btnLoginEmail);
        btnLoginRegister = findViewById(R.id.btnLoginRegister);

        //campos de texto
        edtEmail = findViewById(R.id.editEmailLogin);
        edtPassword = findViewById(R.id.editPasswordLogin);
    }

    //metodo para inicio de sesion con correo y contraseña
    private void LoginEmailandPassword(){
        String email, password;
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();

        //validando que los campos no esten vacios
        if (TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Por favor ingrese su correo", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Por favor ingrese su contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "te has logueado", Toast.LENGTH_LONG).show();
                        }else {
                            Log.w(TAG, "login fallido ", task.getException());
                            Toast.makeText(getApplicationContext(), "el login ha fallado", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}