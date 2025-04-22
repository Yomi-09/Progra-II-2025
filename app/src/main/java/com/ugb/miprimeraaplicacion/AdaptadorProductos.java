package com.ugb.miprimeraaplicacion;  // Asegúrate de que el paquete coincida con el de la clase productos
import com.ugb.miprimeraaplicacion.productos;  // Ajusta el paquete según sea necesario

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorProductos extends ArrayAdapter<productos> {

    private Context context;
    private ArrayList<productos> productosList;

    // Constructor del adaptador
    public AdaptadorProductos(Context context, ArrayList<productos> alProductos) {
        super(context, 0, alProductos);  // Usamos el constructor de ArrayAdapter
        this.context = context;
        this.productosList = alProductos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        // Obtener el producto en la posición actual
        productos producto = productosList.get(position);

        // Establecer los valores en la vista
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(producto.getNombre() + " - Ganancia: " + producto.getGanancia());

        return convertView;
    }
}
