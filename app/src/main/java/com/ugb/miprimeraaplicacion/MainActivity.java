package com.ugb.miprimeraaplicacion;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    Button btn;
    TextView tempVal;
    DB db;
    @
    @ -19,12+25,14@@

    protected void onCreate(Bundle savedInstanceState) {

        db = new DB(this);
        btn = findViewById(R.id.btnGuardarAmigo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarAmigo();
            }
        });
        btn.setOnClickListener(view -> guardarAmigo());

        fab = findViewById(R.id.fabListaAmigos);
        fab.setOnClickListener(view -> abrirVentana());
    }

    private void abrirVentana() {
        Intent intent = new Intent(this, lista_amigos.class);
        startActivity(intent);
    }

    private void guardarAmigo() {
        tempVal = findViewById(R.id.txtNombre);
        @ @ -44, 6 + 52, 6 @@private void guardarAmigo () {
            String[] datos = {"", nombre, direccion, telefono, email, dui, ""};
            db.administrar_amigos("agregar", datos);
            Toast.makeText(getApplicationContext(), "Registro guardado con exito.", Toast.LENGTH_LONG).show();
            abrirVentana();
        }

    }
}
