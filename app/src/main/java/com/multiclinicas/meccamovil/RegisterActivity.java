package com.multiclinicas.meccamovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    //botones
    private Button btnRegisterEmail, btnRegisterLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        //inicializando variables del layout
        Init();

        //seteando botones de eventos
        btnRegisterLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    //metodo para iniciar variables
    private void Init(){
        //botones
        btnRegisterEmail = findViewById(R.id.btnRegisterEmail);
        btnRegisterLogin = findViewById(R.id.btnRegisterLogin);
    }
}