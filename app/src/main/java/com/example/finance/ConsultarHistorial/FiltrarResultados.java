package com.example.finance.ConsultarHistorial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.finance.Nucleo.MainActivity;
import com.example.finance.R;

import java.util.ArrayList;

public class FiltrarResultados extends AppCompatActivity {

    private Button btnBuscar;
    private Button btnCancelar;
    private EditText txtDesp;
    private ListView textbusqueda;
    private AlertDialog.Builder alertaNoHay;
    private AlertDialog.Builder alertaEmpty;
    private AlertDialog.Builder alertaInvalido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_buscar_transacciones);

        DisplayMetrics view = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(view);
        getWindow().setLayout((int)(view.widthPixels * 1), (int)(view.heightPixels * 1));

        txtDesp = findViewById(R.id.nombre_trans);
        btnBuscar = findViewById(R.id.buscar_trans);
        btnCancelar = findViewById(R.id.cancelar_buscar);
        textbusqueda  = findViewById(R.id.busqueda_historial);

    }

    public void busqueda (View view){

        alertaNoHay  = new AlertDialog.Builder(this);
        alertaEmpty = new AlertDialog.Builder(this);
        alertaInvalido = new AlertDialog.Builder(this);

        alertaNoHay.setMessage("No hay ninguna transaccion registrada bajo ese nombre")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertaEmpty.setMessage("No hay ninguna transaccion registrada")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        //finish();
                    }
                });


        alertaInvalido.setMessage("Debe introducir la descripcion en la caja de texto para continuar")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        procesoBusqueda();

    }

    public void procesoBusqueda(){

        String descrip = txtDesp.getText().toString();
        ArrayList<String> resultado = MainActivity.historial.buscarTransaccion(descrip);


        if(MainActivity.historial.esVacio()){
            AlertDialog alerta1 = alertaEmpty.create();
            alerta1.show();
        }else{
            if(descrip.equals("")){
                AlertDialog alerta2 = alertaInvalido.create();
                alerta2.show();
            }else{
                if(resultado.size()==0){
                    AlertDialog alerta3 = alertaNoHay.create();
                    alerta3.show();
                }
                else{
                    ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, resultado);
                    textbusqueda.setAdapter(adaptador);
                    txtDesp.getText().clear();
                }
            }
        }
    }

    public void cancelar (View view){
        finish();
    }
}