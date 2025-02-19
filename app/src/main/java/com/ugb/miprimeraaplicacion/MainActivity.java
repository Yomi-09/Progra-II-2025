package com.ugb.miprimeraaplicacion;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText txtNum1, txtNum2;
    private TextView lblRespuesta;
    private Button btnCalcular;
    private RadioGroup radioGroupOperaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Vincular elementos de la interfaz
        txtNum1 = findViewById(R.id.txtNum1);
        txtNum2 = findViewById(R.id.txtNum2);
        lblRespuesta = findViewById(R.id.lblRespuesta);
        btnCalcular = findViewById(R.id.btnCalcular);
        radioGroupOperaciones = findViewById(R.id.radioGroupOperaciones);

        // Evento del botón calcular
        btnCalcular.setOnClickListener(view -> calcular());

        // Limpiar los campos de texto cuando cambie la operación seleccionada
        radioGroupOperaciones.setOnCheckedChangeListener((group, checkedId) -> {
            // Limpiar los campos de texto
            txtNum1.setText("");
            txtNum2.setText("");
            lblRespuesta.setText("Respuesta");
        });
    }

    private void calcular() {
        // Obtener los valores ingresados
        String num1Str = txtNum1.getText().toString();
        String num2Str = txtNum2.getText().toString();

        if (num1Str.isEmpty() || num2Str.isEmpty()) {
            lblRespuesta.setText("Por favor ingresa ambos números");
            return;
        }

        try {
            double num1 = Double.parseDouble(num1Str);
            double num2 = Double.parseDouble(num2Str);
            double resultado = 0;
            String operacion = "";

            // Obtener la operación seleccionada
            int selectedId = radioGroupOperaciones.getCheckedRadioButtonId();
            if (selectedId == -1) {
                lblRespuesta.setText("Selecciona una operación");
                return;
            }

            // Obtener el RadioButton seleccionado
            RadioButton selectedRadioButton = findViewById(selectedId);
            String opcion = selectedRadioButton.getText().toString();

            switch (opcion) {
                case "Suma":
                    resultado = num1 + num2;
                    operacion = "Suma";
                    break;
                case "Resta":
                    resultado = num1 - num2;
                    operacion = "Resta";
                    break;
                case "Multiplicación":
                    resultado = num1 * num2;
                    operacion = "Multiplicación";
                    break;
                case "División":
                    if (num2 == 0) {
                        lblRespuesta.setText("Error: División por 0");
                        return;
                    }
                    resultado = num1 / num2;
                    operacion = "División";
                    break;
                case "Exponenciación":
                    resultado = Math.pow(num1, num2);
                    operacion = "Exponenciación";
                    break;
                default:
                    lblRespuesta.setText("Operación no válida");
                    return;
            }

            // Determinar si el resultado es entero o decimal
            String resultadoStr;
            if (resultado == (int) resultado) {
                resultadoStr = String.format("%d", (int) resultado);
            } else {
                resultadoStr = String.format("%.2f", resultado);
            }

            // Mostrar el resultado
            lblRespuesta.setText(operacion + ": " + resultadoStr);

        } catch (NumberFormatException e) {
            lblRespuesta.setText("Error: Ingresa números válidos");
        }
    }
}
