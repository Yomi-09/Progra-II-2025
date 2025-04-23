package com.ugb.miprimeraaplicacion;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListaProductos extends AppCompatActivity {

    private ArrayList<productos> alProductos = new ArrayList<>();
    private ArrayAdapter<productos> adapter;
    private ListView ltsProductos;
    private EditText txtBuscarProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listaproductos);

        txtBuscarProducto = findViewById(R.id.txtBuscarProducto);
        ltsProductos = findViewById(R.id.ltsProductos);
        FloatingActionButton fabAgregarProducto = findViewById(R.id.fabAgregarProducto);

        // Datos de ejemplo
        alProductos.add(new productos("Camiseta", "url_imagen_1", 10.0, 5.0));
        alProductos.add(new productos("Zapatos", "url_imagen_2", 15.0, 7.0));
        alProductos.add(new productos("Sombrero", "url_imagen_3", 20.0, 10.0));

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alProductos);
        ltsProductos.setAdapter(adapter);

        // Buscar en tiempo real
        txtBuscarProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrar(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // Acción de agregar producto
        fabAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaProductos.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // Método para filtrar la lista de productos según el texto de búsqueda
    private void filtrar(String texto) {
        ArrayList<productos> filtrados = new ArrayList<>();
        for (productos p : alProductos) {
            if (p.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                filtrados.add(p);
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filtrados);
        ltsProductos.setAdapter(adapter);
    }
}
