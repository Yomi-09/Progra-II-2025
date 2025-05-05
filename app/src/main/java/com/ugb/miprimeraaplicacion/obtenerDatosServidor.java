package com.ugb.miprimeraaplicacion;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class obtenerDatosServidor extends AsyncTask<String, String, String> {
    HttpURLConnection httpURLConnection;
    ArrayList<productos> listaProductos = new ArrayList<>();  // Inicializamos la lista

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            // Procesamos la respuesta JSON
            JSONObject jsonObject = new JSONObject(s);
            JSONArray rows = jsonObject.getJSONArray("rows");

            for (int i = 0; i < rows.length(); i++) {
                JSONObject fila = rows.getJSONObject(i);
                JSONObject doc = fila.getJSONObject("doc"); // Obtiene el documento

                // Crear el objeto producto con los datos correctos
                productos producto = new productos(
                        doc.getString("idproducto"),    // idproducto: String
                        doc.getString("nombre"),       // nombre: String
                        doc.getDouble("precio"),       // precio: double
                        doc.getDouble("costo"),        // costo: double
                        doc.getDouble("ganancia"),     // ganancia: double
                        doc.getString("urlFoto")       // urlFoto: String
                );


                // Agregar el producto a la lista
                listaProductos.add(producto);
            }

            // Aquí deberías notificar a tu adaptador de RecyclerView para actualizar la vista
            // adaptador.notifyDataSetChanged();  // Esto debe estar en tu actividad o fragmento

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String doInBackground(String... parametros) {
        StringBuilder respuesta = new StringBuilder();
        try {
            URL url = new URL(utilidades.url_consulta);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Authorization", "Basic " + utilidades.credencialesCodificadas);

            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                respuesta.append(linea);
            }
        } catch (Exception e) {
            return e.getMessage();
        } finally {
            httpURLConnection.disconnect();
        }
        return respuesta.toString();
    }
}

