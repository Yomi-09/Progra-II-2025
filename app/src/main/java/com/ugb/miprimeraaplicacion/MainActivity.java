package com.ugb.miprimeraaplicacion;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText edtConsumo, etCantidad;
    Button btnCalcularAgua, btnConvertir;
    TextView txtResultadoAgua, tvResultado;
    Spinner spinnerFrom, spinnerTo;
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuración del TabHost
        tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab1");
        spec1.setIndicator("Pago de Agua");
        spec1.setContent(R.id.tab1);
        tabHost.addTab(spec1);

        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab2");
        spec2.setIndicator("Conversor de Área");
        spec2.setContent(R.id.tab2);
        tabHost.addTab(spec2);

        // Referencias a los elementos de la UI
        edtConsumo = findViewById(R.id.edtConsumo);
        btnCalcularAgua = findViewById(R.id.btnCalcularAgua);
        txtResultadoAgua = findViewById(R.id.txtResultadoAgua);

        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        etCantidad = findViewById(R.id.etCantidad);
        btnConvertir = findViewById(R.id.btnConvertir);
        tvResultado = findViewById(R.id.tvResultado);

        // Cargar opciones en los Spinners
        String[] unidades = {"Pie Cuadrado", "Vara Cuadrada", "Yarda Cuadrada", "Metro Cuadrado", "Tareas", "Manzana", "Hectárea"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, unidades);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        // Botón para calcular pago del agua
        btnCalcularAgua.setOnClickListener(v -> calcularPagoAgua());

        // Botón para conversor de área
        btnConvertir.setOnClickListener(v -> convertirArea());
    }

    private void calcularPagoAgua() {
        double consumo = Double.parseDouble(edtConsumo.getText().toString());
        double pago = 0;

        if (consumo <= 18) {
            pago = 6;
        } else if (consumo <= 28) {
            pago = 6 + (consumo - 18) * 0.45;
        } else {
            pago = 6 + (10 * 0.45) + (consumo - 28) * 0.65;
        }

        txtResultadoAgua.setText("Pago: $" + String.format("%.2f", pago));
    }

    private void convertirArea() {
        double cantidad = Double.parseDouble(etCantidad.getText().toString());
        String unidadDesde = spinnerFrom.getSelectedItem().toString();
        String unidadHasta = spinnerTo.getSelectedItem().toString();
        double factorConversion = obtenerFactorConversion(unidadDesde, unidadHasta);
        double resultado = cantidad * factorConversion;
        tvResultado.setText("Resultado: " + String.format("%.4f", resultado));
    }

    private double obtenerFactorConversion(String desde, String hasta) {
        // Factores de conversión entre las unidades
        double metroCuadrado = 1;
        double pieCuadrado = 0.092903;
        double varaCuadrada = 0.6987;
        double yardaCuadrada = 0.836127;
        double tarea = 437.5;
        double manzana = 7000;
        double hectarea = 10000;

        double factorDesde;
        switch (desde) {
            case "Pie Cuadrado":
                factorDesde = pieCuadrado;
                break;
            case "Vara Cuadrada":
                factorDesde = varaCuadrada;
                break;
            case "Yarda Cuadrada":
                factorDesde = yardaCuadrada;
                break;
            case "Tareas":
                factorDesde = tarea;
                break;
            case "Manzana":
                factorDesde = manzana;
                break;
            case "Hectárea":
                factorDesde = hectarea;
                break;
            default:
                factorDesde = metroCuadrado;
                break;

        }

        double factorHasta;
        switch (hasta) {
            case "Pie Cuadrado":
                factorHasta = pieCuadrado;
                break;
            case "Vara Cuadrada":
                factorHasta = varaCuadrada;
                break;
            case "Yarda Cuadrada":
                factorHasta = yardaCuadrada;
                break;
            case "Tareas":
                factorHasta = tarea;
                break;
            case "Manzana":
                factorHasta = manzana;
                break;
            case "Hectárea":
                factorHasta = hectarea;
                break;
            default:
                factorHasta = metroCuadrado;
                break;
        }


        return factorDesde / factorHasta;
    }
}
