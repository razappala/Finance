package com.example.finance.ConsultarHistorial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finance.R;
import com.example.finance.Nucleo.MainActivity;
import com.example.finance.Nucleo.ControlArchivos;
import com.example.finance.Nucleo.tipoTransaccion;
import com.google.android.material.chip.Chip;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.time.LocalDateTime;

public class ActTransacciones extends AppCompatActivity {

    private Button btnAceptar;
    private Button btnCancelar;
    private EditText txtMonto, txtDesp;
    private Chip chipIngreso;
    private Chip chipEgreso;
    private AlertDialog.Builder alertaAdverEgreso;
    private AlertDialog.Builder alertaError;
    private AlertDialog.Builder alertaLogro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_transacciones);

        DisplayMetrics view = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(view);
        getWindow().setLayout((int)(view.widthPixels * 1), (int)(view.heightPixels * 1));

        txtMonto    = findViewById(R.id.trans_monto);
        txtDesp     = findViewById(R.id.trans_desp);
        btnAceptar  = findViewById(R.id.trans_Aceptar);
        btnCancelar = findViewById(R.id.trans_Cancelar);
        chipIngreso = findViewById(R.id.trans_ingreso);
        chipEgreso  = findViewById(R.id.trans_egreso);
    }

    public void aceptado (View view){
        alertaLogro       = new AlertDialog.Builder(this);
        alertaAdverEgreso = new AlertDialog.Builder(this);
        alertaError       = new AlertDialog.Builder(this);

        alertaError.setMessage("Hubo un error al ingresar los datos, por favor vuelva a intentarlo")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertaLogro.setMessage("Transaccion lograda con exito")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });


        alertaAdverEgreso.setMessage("La transaccion que piensas hacer te dejara un saldo negativo, quieres realizarla igualmente?")
                .setCancelable(false)
                .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double v          = Double.parseDouble(txtMonto.getText().toString());
                        String d          = txtDesp.getText().toString();
                        tipoTransaccion t = tipoTransaccion.ninguna;

                        if (chipIngreso.isChecked())
                            t = tipoTransaccion.ingreso;
                        else if (chipEgreso.isChecked())
                            t = tipoTransaccion.egreso;

                        ControlHistorial manejador  = new ControlHistorial();
                        manejador.agregarHistorial(MainActivity.monedero, MainActivity.historial, v, t, d, LocalDateTime.now());
                        InicioFragment.m.actualizarDatos();
                        InicioFragment.m.actualizarVacio();

                        guardado();
                        AlertDialog alerta3 = alertaLogro.create();
                        alerta3.show();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        procesoDeTrans();
    }

    private void procesoDeTrans(){
        double v          = 0;
        String d          = txtDesp.getText().toString();
        tipoTransaccion t = tipoTransaccion.ninguna;

        boolean verificacion1 = true;

        for (int i = 0; i > txtMonto.getText().toString().length(); i++){
            if (txtMonto.getText().toString().charAt(i) == ',')
                verificacion1 = false;
        }

        if ((txtMonto.getText().toString().length() > 0) && (verificacion1 = true)) {
            v  = Double.parseDouble(txtMonto.getText().toString());
        };

        if (chipIngreso.isChecked())
            t = tipoTransaccion.ingreso;
        else if (chipEgreso.isChecked())
            t = tipoTransaccion.egreso;

        ControlHistorial manejador  = new ControlHistorial();

        if ((v != 0) && (verificacion1 = true) && (d.length() > 0) && (t != tipoTransaccion.ninguna)){
            if ((v > MainActivity.monedero.getSaldo()) && (chipEgreso.isChecked())){
                AlertDialog alerta2 = alertaAdverEgreso.create();
                alerta2.show();
            }
            else{
                manejador.agregarHistorial(MainActivity.monedero, MainActivity.historial, v, t, d, LocalDateTime.now());
                AlertDialog alerta3 = alertaLogro.create();
                alerta3.show();

                InicioFragment.m.actualizarDatos();
                InicioFragment.m.actualizarVacio();
                guardado();
            }

        }
        else{
            AlertDialog alerta1 = alertaError.create();
            alerta1.show();

            InicioFragment.m.actualizarDatos();
        }
    }

    private void guardado(){
        //********GUARDAR INFORMARCION DE LA APP ***********
        ControlArchivos controladorArchivos = new ControlArchivos();
        controladorArchivos.guardarMonedero(this, MainActivity.monedero);
        controladorArchivos.guardarHistorial(this, MainActivity.historial);
        //**************************************************
    }

    public void cancelar (View view){
        finish();
    }
}