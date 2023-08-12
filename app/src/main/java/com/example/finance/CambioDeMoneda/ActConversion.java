package com.example.finance.CambioDeMoneda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.finance.Nucleo.MainActivity;
import com.example.finance.R;
import com.example.finance.Nucleo.UnidadMonetaria;

public class ActConversion extends AppCompatActivity {

    private Button btnAceptar;
    private EditText txtMonto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_conversion);

        DisplayMetrics view = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(view);
        getWindow().setLayout((int)(view.widthPixels * 0.6), (int)(view.heightPixels * 0.5));

        txtMonto    = findViewById(R.id.tasa_monto);
        btnAceptar  = findViewById(R.id.tasa_boton);

    }

    public void cambiar (View view){
        ControlMonetario cambio = new ControlMonetario();
        if (txtMonto.getText().toString().length() > 0) {

            //******************************************************************************
            cambio.setTasa(Float.parseFloat(txtMonto.getText().toString()));

            cambio.convertirDatos(MainActivity.monedero, MainActivity.historial,
                                  UnidadMonetaria.valueOf(MainActivity.monedero.stringDivisa(MonedaFragment.valorDivisa)));
            MonedaFragment.textDivisa.setText(MainActivity.monedero.stringDivisa());
            //******************************************************************************

            AlertDialog.Builder alertaLogro       = new AlertDialog.Builder(this);
            alertaLogro.setMessage("Conversion lograda con exito")
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            finish();
                        }
                    });
            AlertDialog alerta3 = alertaLogro.create();
            alerta3.show();
        }
    }
}