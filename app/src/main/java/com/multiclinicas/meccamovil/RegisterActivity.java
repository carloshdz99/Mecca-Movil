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

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "LoginFirebase";
    //firebase
    private FirebaseAuth mAuth;
    //botones
    private Button btnRegisterEmail, btnRegisterLogin;
    //campos de texto
    private EditText edtEmail, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        //iniciando firebase
        mAuth = FirebaseAuth.getInstance();
        //inicializando variables del layout
        Init();

        //seteando botones de eventos
        //boton para ir al login
        btnRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        //boton de registro
        btnRegisterEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterEmailandPassword();
            }
        });
    }

    //metodo para iniciar variables
    private void Init(){
        //botones
        btnRegisterEmail = findViewById(R.id.btnRegisterEmail);
        btnRegisterLogin = findViewById(R.id.btnRegisterLogin);
        //campos de texto
        edtEmail = findViewById(R.id.editEmailRegister);
        edtPassword = findViewById(R.id.editPasswordRegister);
    }

    //metodo de registro de usuario con correo y contraseña
    private void RegisterEmailandPassword(){
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

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "te has registrado", Toast.LENGTH_LONG).show();
                        }else {
                            Log.w(TAG, "login fallido ", task.getException());
                            Toast.makeText(getApplicationContext(), "el registro ha fallado", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}