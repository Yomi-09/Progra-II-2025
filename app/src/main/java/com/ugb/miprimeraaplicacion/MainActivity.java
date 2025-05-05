package com.ugb.miprimeraaplicacion;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_TOMAR_FOTO = 1;

    private FloatingActionButton fab;
    private Button btnGuardar;
    private EditText txtNombre, txtPrecio, txtCosto, txtGanancia;
    private ImageView imgFotoProducto;

    private DB db;
    private utilidades utls;
    private detectarInternet di;

    private String accion = "nuevo";
    private String idproducto = "";
    private String id = "";
    private String rev = "";
    private String urlCompletaFoto = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();
        configurarEventos();
        mostrarDatos();
    }

    private void inicializarComponentes() {
        utls = new utilidades();
        db = new DB(this);

        imgFotoProducto = findViewById(R.id.imgFotoProducto);
        btnGuardar = findViewById(R.id.btnGuardarProducto);
        fab = findViewById(R.id.fabListaProductos);

        txtNombre = findViewById(R.id.txtNombre);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtCosto = findViewById(R.id.txtCosto);
        txtGanancia = findViewById(R.id.txtGanancia);
    }

    private void configurarEventos() {
        btnGuardar.setOnClickListener(v -> guardarProducto());
        fab.setOnClickListener(v -> abrirVentanaLista());
        imgFotoProducto.setOnClickListener(v -> tomarFoto());

        // Calcular ganancia automáticamente al cambiar precio o costo
        txtPrecio.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calcularGanancia();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        txtCosto.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                calcularGanancia();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void mostrarDatos() {
        try {
            Bundle parametros = getIntent().getExtras();
            if (parametros != null) {
                accion = parametros.getString("accion", "nuevo");

                if ("modificar".equals(accion)) {
                    JSONObject datos = new JSONObject(parametros.getString("productos"));
                    id = datos.getString("_id");
                    rev = datos.getString("_rev");
                    idproducto = datos.getString("idproducto");

                    txtNombre.setText(datos.getString("nombre"));
                    txtPrecio.setText(datos.getString("precio"));
                    txtCosto.setText(datos.getString("costo"));
                    txtGanancia.setText(datos.getString("ganancias"));

                    urlCompletaFoto = datos.getString("urlFoto");
                    imgFotoProducto.setImageURI(Uri.parse(urlCompletaFoto));
                } else {
                    idproducto = utls.generarUnicoId();
                }
            }
        } catch (Exception e) {
            mostrarMsg("Error al mostrar datos: " + e.getMessage());
        }
    }

    private void tomarFoto() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File fotoProducto = crearImagenProducto();

            if (fotoProducto != null) {
                Uri uriFotoProducto = FileProvider.getUriForFile(this,
                        "com.ugb.miprimeraaplicacion.fileprovider", fotoProducto);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFotoProducto);
                startActivityForResult(intent, REQUEST_TOMAR_FOTO);
            } else {
                mostrarMsg("No se pudo crear la imagen.");
            }
        } catch (Exception e) {
            mostrarMsg("Error al tomar foto: " + e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_TOMAR_FOTO) {
            if (resultCode == RESULT_OK) {
                File fotoProducto = new File(urlCompletaFoto);
                if (fotoProducto.exists()) {
                    imgFotoProducto.setImageURI(Uri.parse(urlCompletaFoto));
                } else {
                    mostrarMsg("No se pudo encontrar la imagen.");
                }
            } else {
                mostrarMsg("No se tomó la foto.");
            }
        }
    }

    private File crearImagenProducto() throws Exception {
        String fechaHoraMs = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "imagen_" + fechaHoraMs + "_";
        File dirAlmacenamiento = getExternalFilesDir(Environment.DIRECTORY_DCIM);

        if (dirAlmacenamiento != null && !dirAlmacenamiento.exists()) {
            dirAlmacenamiento.mkdirs();
        }

        File image = File.createTempFile(fileName, ".jpg", dirAlmacenamiento);
        urlCompletaFoto = image.getAbsolutePath();
        return image;
    }

    private void guardarProducto() {
        try {
            // Obtener datos de los campos
            String nombre = txtNombre.getText().toString().trim();
            String precio = txtPrecio.getText().toString().replace("$", "").trim();
            String costo = txtCosto.getText().toString().replace("$", "").trim();
            String ganancias = txtGanancia.getText().toString().replace("$", "").trim();

            // Validaciones básicas
            if (nombre.isEmpty() || precio.isEmpty() || costo.isEmpty()) {
                mostrarMsg("Por favor, completa todos los campos requeridos.");
                return;
            }

            // Crear objeto JSON para CouchDB
            JSONObject datosProducto = new JSONObject();
            if ("modificar".equals(accion)) {
                datosProducto.put("_id", id);
                datosProducto.put("_rev", rev);
            }

            // Agregar los datos al JSON
            datosProducto.put("idproducto", idproducto);
            datosProducto.put("nombre", nombre);
            datosProducto.put("precio", precio);
            datosProducto.put("costo", costo);
            datosProducto.put("ganancias", ganancias); // GANANCIAS SIEMPRE VAN AQUÍ ABAJO
            datosProducto.put("urlFoto", urlCompletaFoto);

            // Verificar conexión y enviar al servidor
            di = new detectarInternet(this);
            if (di.hayConexionInternet()) {
                enviarDatosServidor objEnviarDatos = new enviarDatosServidor(this);
                String respuesta = objEnviarDatos.execute(
                        datosProducto.toString(), "POST", utilidades.url_mto
                ).get(); // ⚠️ Esto bloquea el hilo principal, idealmente usar AsyncTask correctamente

                // Procesar la respuesta del servidor
                procesarRespuestaServidor(respuesta);
            } else {
                mostrarMsg("No hay conexión a Internet. El registro se guardará localmente.");
            }

            // Guardar datos localmente
            String[] datosLocales = {
                    idproducto, nombre, precio, costo, ganancias, urlCompletaFoto
            };
            db.administrar_productos(accion, datosLocales);

            mostrarMsg("Registro guardado con éxito.");
            abrirVentanaLista();
        } catch (Exception e) {
            mostrarMsg("Error al guardar producto: " + e.getMessage());
            e.printStackTrace(); // Mostrar detalles en Logcat
        }
    }


    @SuppressLint("SetTextI18n")
    private void calcularGanancia() {
        try {
            String precioTexto = txtPrecio.getText().toString().replace("$", "").trim();
            String costoTexto = txtCosto.getText().toString().replace("$", "").trim();

            if (!precioTexto.isEmpty() && !costoTexto.isEmpty()) {
                double precio = Double.parseDouble(precioTexto);
                double costo = Double.parseDouble(costoTexto);
                double ganancia = precio - costo;

                txtGanancia.setText("$" + String.format("%.2f", ganancia));
            } else {
                txtGanancia.setText("$0.00");
            }
        } catch (Exception e) {
            txtGanancia.setText("$0.00");
        }
    }

    private void procesarRespuestaServidor(String respuesta) {
        try {
            JSONObject respuestaJSON = new JSONObject(respuesta);
            if (respuestaJSON.getBoolean("ok")) {
                id = respuestaJSON.getString("id");
                rev = respuestaJSON.getString("rev");
            } else {
                mostrarMsg("Error del servidor: " + respuestaJSON.getString("msg"));
            }
        } catch (Exception e) {
            mostrarMsg("Respuesta del servidor: " + respuesta);
        }
    }

    private void abrirVentanaLista() {
        startActivity(new Intent(this, lista_productos.class));
    }

    private void mostrarMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
