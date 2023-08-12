/*package com.example.finance.fragments;

import androidx.appcompat.app.AppCompatActivity;
import com.example.finance.R;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class ActTransaccionesTiempo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_transacciones_tiempo);

        DisplayMetrics view = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(view);
        getWindow().setLayout((int)(view.widthPixels * 0.8), (int)(view.heightPixels * 0.8));
    }
}*/
/*package com.example.finance.fragments;

import androidx.appcompat.app.AppCompatActivity;
import com.example.finance.R;
import com.example.finance.Nucleo.Transaccion;
import com.example.finance.Nucleo.tipoTransaccion;
import com.google.android.material.chip.Chip;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.time.LocalDateTime;

public class ActTransaccionesTiempo extends AppCompatActivity {

    //Asignar las variables de los elementos
    Button btnCancelar;
    Button btnAceptar;
    EditText txtMonto, txtDesp, txtDias;
    Chip chipIngreso, getChipegreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_transacciones_tiempo);

        DisplayMetrics view = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(view);
        getWindow().setLayout((int)(view.widthPixels * 0.8), (int)(view.heightPixels * 0.8));

        //Conectar las variables con sus respectivos elementos de diseno
        btnCancelar = findViewById(R.id.trans_Cancelar);
        btnAceptar = findViewById(R.id.trans_Aceptar);
        txtMonto = findViewById(R.id.trans_monto);
        txtDesp = findViewById(R.id.trans_desp);
        chipIngreso = findViewById(R.id.trans_ingreso);
        getChipegreso = findViewById(R.id.trans_egreso);
        txtDias = findViewById(R.id.trans_dias);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Simplificar algunos parametros
                Double valor  = Double.parseDouble(txtMonto.getText().toString());
                String descripcion = txtDesp.getText().toString();
                LocalDateTime fechaCarga = LocalDateTime.now();

                //Ajustar la fecha de carga
                int numDias = Integer.parseInt(txtDias.getText().toString());
                fechaCarga = fechaCarga.plusDays(numDias);

                if (chipIngreso.isChecked()){ //INGRESO

                    //Crear la transaccion como ingreso
                    Transaccion transaccion = new Transaccion(valor, tipoTransaccion.ingreso, descripcion, fechaCarga);

                    //Subirlo a la cola de transacciones por fecha
                    MainActivity.transaccionesPendientes.add(transaccion);

                } else { //EGRESO
                    //Crear la transaccion como egreso
                    Transaccion transaccion = new Transaccion(valor, tipoTransaccion.egreso, descripcion, fechaCarga);

                    //Subirlo a la cola de transacciones por fecha
                    MainActivity.transaccionesPendientes.add(transaccion);
                }

                finish();
            }
        });
    }
}*/
/*
package com.example.finance.fragments;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finance.R;
import com.example.finance.ConsultarHistorial.ControlHistorial;
import com.example.finance.Nucleo.Transaccion;
import com.example.finance.Nucleo.tipoTransaccion;
import com.google.android.material.chip.Chip;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.time.LocalDateTime;

public class ActTransaccionesTiempo extends AppCompatActivity {

    //Asignar las variables de los elementos
    Button btnCancelar;
    Button btnAceptar;
    EditText txtMonto, txtDesp, txtDias;
    Chip chipIngreso, chipEgreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_transacciones_tiempo);

        DisplayMetrics view = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(view);
        getWindow().setLayout((int)(view.widthPixels * 0.8), (int)(view.heightPixels * 0.8));

        //Conectar las variables con sus respectivos elementos de diseno
        btnCancelar = findViewById(R.id.trans_Cancelar);
        btnAceptar = findViewById(R.id.trans_Aceptar);
        txtMonto = findViewById(R.id.trans_monto);
        txtDesp = findViewById(R.id.trans_desp);
        chipIngreso = findViewById(R.id.trans_ingreso);
        chipEgreso = findViewById(R.id.trans_egreso);
        txtDias = findViewById(R.id.trans_dias);

       /* btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
/*    }

    public void aceptar (View view){

        //Declarar una variable para verificar
        boolean verificacion = true;
        double valor          = 0;
        String descripcion          = txtDesp.getText().toString();
        tipoTransaccion tipo = tipoTransaccion.ninguna;
        ControlHistorial manejador  = new ControlHistorial();

        //Verificar que el monto no sea vacio
        if (txtMonto.getText().toString().length() == 0) {

            verificacion = false;
            //MOSTRAR MENSAJE DE ERROR DE QUE EL MONTO NO PUEDE ESTAR VACIO
            AlertDialog.Builder alertaError = new AlertDialog.Builder(ActTransaccionesTiempo.this);
            alertaError.setMessage("Hubo un error al ingresar los datos: el campo de monto esta vacio. Por favor vuelva a intentarlo")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alerta1 = alertaError.create();
            alerta1.show();
        }

        //Verificar que no se escriban montos con ,
        if (verificacion) {
            for (int i = 0; i > txtMonto.getText().toString().length(); i++){
                if (txtMonto.getText().toString().charAt(i) == ',') {

                    verificacion = false;
                    //MOSTAR ERROR DE QUE NO PUEDEN USAR ,
                    AlertDialog.Builder alertaError = new AlertDialog.Builder(ActTransaccionesTiempo.this);
                    alertaError.setMessage("Hubo un error al ingresar los datos: monto ingresado con coma (,). Por favor vuelva a intentarlo")
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    finish();
                                }
                            });
                    AlertDialog alerta1 = alertaError.create();
                    alerta1.show();
                }

            }
        }

        //Declarar el valor double si es mayor a 0
        if ((txtMonto.getText().toString().length() > 0) && (verificacion = true)) {
            valor  = Double.parseDouble(txtMonto.getText().toString());
        }

        //Escoger el tipo de ingreso
        if (chipIngreso.isChecked())
            tipo = tipoTransaccion.ingreso;
        else if (chipEgreso.isChecked())
            tipo = tipoTransaccion.egreso;
        else{

            verificacion = false;
            //MOSTRAR ERROR DE QUE NO ESTA SELECCIONADO UN TIPO
            AlertDialog.Builder alertaError = new AlertDialog.Builder(ActTransaccionesTiempo.this);
            alertaError.setMessage("Hubo un error al ingresar los datos: tipo de operacion no seleccionada. Por favor vuelva a intentarlo")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alerta1 = alertaError.create();
            alerta1.show();
        }

        //Verificar que la descripcion no este vacia
        if (descripcion.length() == 0) {

            verificacion = false;
            //MOSTRAR ERROR DE QUE NO PUEDEN DEJAR LA DESCRIPCION VACIA
            AlertDialog.Builder alertaError = new AlertDialog.Builder(ActTransaccionesTiempo.this);
            alertaError.setMessage("Hubo un error al ingresar los datos: la descripcion esta vacia. Por favor vuelva a intentarlo")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alerta1 = alertaError.create();
            alerta1.show();
        }

        if (txtDias.getText().toString().length() < 1) {
            verificacion = false;
            //Mostrar mensaje de error
        }

        //Si se cumplen todas las verificaciones basicas
        if (verificacion) {
            //Ajustar la fecha de carga
            LocalDateTime fechaCarga = LocalDateTime.now();
            int numDias = Integer.parseInt(txtDias.getText().toString());
            fechaCarga = fechaCarga.plusDays(numDias);

            //Crear la transaccion como ingreso
            Transaccion transaccion = new Transaccion(valor, tipo, descripcion, fechaCarga);

            //Subirlo a la cola de transacciones por fecha
            MainActivity.transaccionesPendientes.add(transaccion);
            AlertDialog.Builder alertaLogro = new AlertDialog.Builder(this);
            alertaLogro.setMessage("Jose es gei")
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

        //MOSTRAR ADVERTENCIA OBLIGATORIA DE QUE CUANDO ORGANIZA UN EGRESO PUEDE QUEDAR EN DEUDA
        if (verificacion && (tipo == tipoTransaccion.egreso) ) {
            //MOSTRAR PANTALLA CON MENSAJE Y BOTON OK
        }
    }

    public void cancelado (View view){
        finish();
    }
}*/

package com.example.finance.ConsultarHistorial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finance.R;
import com.example.finance.Nucleo.MainActivity;
import com.example.finance.Nucleo.Transaccion;
import com.example.finance.Nucleo.tipoTransaccion;
import com.google.android.material.chip.Chip;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.time.LocalDateTime;

public class ActTransaccionesTiempo extends AppCompatActivity {

    //Asignar las variables de los elementos
    private Button btnCancelar;
    private Button btnAceptar;
    private EditText txtMonto, txtDesp, txtDias;
    private Chip chipIngreso, chipEgreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_transacciones_tiempo);

        DisplayMetrics view = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(view);
        getWindow().setLayout((int)(view.widthPixels * 1), (int)(view.heightPixels * 1));

        //Conectar las variables con sus respectivos elementos de diseno
        btnCancelar = findViewById(R.id.trans_Cancelar);
        btnAceptar = findViewById(R.id.trans_Aceptar);
        txtMonto = findViewById(R.id.trans_monto);
        txtDesp = findViewById(R.id.trans_desp);
        chipIngreso = findViewById(R.id.trans_ingreso);
        chipEgreso = findViewById(R.id.trans_egreso);
        txtDias = findViewById(R.id.trans_dias);

       /* btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
    }

    public void aceptar (View view){

        //Declarar una variable para verificar
        boolean verificacion = true;
        double valor          = 0;
        String descripcion          = txtDesp.getText().toString();
        tipoTransaccion tipo = tipoTransaccion.ninguna;
        ControlHistorial manejador  = new ControlHistorial();

        //Verificar que el monto no sea vacio
        if (txtMonto.getText().toString().length() == 0) {

            verificacion = false;
            //MOSTRAR MENSAJE DE ERROR DE QUE EL MONTO NO PUEDE ESTAR VACIO
            AlertDialog.Builder alertaError = new AlertDialog.Builder(ActTransaccionesTiempo.this);
            alertaError.setMessage("Hubo un error al ingresar los datos: el campo de monto esta vacio. Por favor vuelva a intentarlo")
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alerta1 = alertaError.create();
            alerta1.show();
        }

        //Verificar que no se escriban montos con ,
        if (verificacion) {
            for (int i = 0; i > txtMonto.getText().toString().length(); i++){
                if (txtMonto.getText().toString().charAt(i) == ',') {

                    verificacion = false;
                    //MOSTAR ERROR DE QUE NO PUEDEN USAR ,
                    AlertDialog.Builder alertaError = new AlertDialog.Builder(ActTransaccionesTiempo.this);
                    alertaError.setMessage("Hubo un error al ingresar los datos: monto ingresado con coma (,). Por favor vuelva a intentarlo")
                            .setCancelable(false)
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    finish();
                                }
                            });
                    AlertDialog alerta1 = alertaError.create();
                    alerta1.show();
                }

            }
        }

        //Declarar el valor double si es mayor a 0
        if ((txtMonto.getText().toString().length() > 0) && (verificacion = true)) {
            valor  = Double.parseDouble(txtMonto.getText().toString());
        }

        //Escoger el tipo de ingreso
        if (chipIngreso.isChecked())
            tipo = tipoTransaccion.ingreso;
        else if (chipEgreso.isChecked())
            tipo = tipoTransaccion.egreso;
        else{

            verificacion = false;
            //MOSTRAR ERROR DE QUE NO ESTA SELECCIONADO UN TIPO
            AlertDialog.Builder alertaError = new AlertDialog.Builder(ActTransaccionesTiempo.this);
            alertaError.setMessage("Hubo un error al ingresar los datos: tipo de operacion no seleccionada. Por favor vuelva a intentarlo")
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alerta1 = alertaError.create();
            alerta1.show();
        }

        //Verificar que la descripcion no este vacia
        if (descripcion.length() == 0) {

            verificacion = false;
            //MOSTRAR ERROR DE QUE NO PUEDEN DEJAR LA DESCRIPCION VACIA
            AlertDialog.Builder alertaError = new AlertDialog.Builder(ActTransaccionesTiempo.this);
            alertaError.setMessage("Hubo un error al ingresar los datos: la descripcion esta vacia. Por favor vuelva a intentarlo")
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alerta1 = alertaError.create();
            alerta1.show();
        }

        if (txtDias.getText().toString().length() < 1) {
            verificacion = false;
            //Mostrar mensaje de error
            AlertDialog.Builder alertaError = new AlertDialog.Builder(ActTransaccionesTiempo.this);
            alertaError.setMessage("Hubo un error al ingresar los datos: la cantidad de dias ingresados no es valida. Por favor vuelva a intentarlo")
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alerta4 = alertaError.create();
            alerta4.show();
        }

        //Si se cumplen todas las verificaciones basicas
        if (verificacion) {
            //Ajustar la fecha de carga
            LocalDateTime fechaCarga = LocalDateTime.now();
            int numDias = Integer.parseInt(txtDias.getText().toString());
            fechaCarga = fechaCarga.plusDays(numDias);

            //Crear la transaccion como ingreso
            Transaccion transaccion = new Transaccion(valor, tipo, descripcion, fechaCarga);

            //Subirlo a la cola de transacciones por fecha
            MainActivity.transaccionesPendientes.add(transaccion);
            AlertDialog.Builder alertaLogro = new AlertDialog.Builder(this);
            alertaLogro.setMessage("Transaccion agregada a cola correctamente")
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

        //MOSTRAR ADVERTENCIA OBLIGATORIA DE QUE CUANDO ORGANIZA UN EGRESO PUEDE QUEDAR EN DEUDA
        if (verificacion && (tipo == tipoTransaccion.egreso) ) {
            //MOSTRAR PANTALLA CON MENSAJE Y BOTON OK
        }
    }

    public void cancelado (View view){
        finish();
    }
}