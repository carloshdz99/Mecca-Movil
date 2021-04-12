package com.multiclinicas.meccamovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //botones
    private Button btnLoginEmail, btnLoginRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_MeccaMovil);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //inicializando variables del layout
        Init();

        //seteando metodos de botones
        btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    //metodo para iniciar variables
    private void Init(){
        //botones
        btnLoginEmail = findViewById(R.id.btnLoginEmail);
        btnLoginRegister = findViewById(R.id.btnLoginRegister);
    }
}