package com.example.finance.RepartirPagos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import com.example.finance.Nucleo.MainActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finance.R;

public class Act_total_factura extends AppCompatActivity {

    private TextView txtTotal;
    private ListView txtDistribucion;
    ///private Button btnAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_total_factura);

        DisplayMetrics view = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(view);
        getWindow().setLayout((int)(view.widthPixels * 1), (int)(view.heightPixels * 1));

        txtTotal = findViewById(R.id.monto_factura);
        txtDistribucion = findViewById(R.id.pagos_miembros);
        //btnAceptar = findViewById(R.id.aceptar_factura);

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, MainActivity.pagos.dividirCuenta(Act_llenar_factura.nombre_grupo));
        txtDistribucion.setAdapter(adaptador);

        txtTotal.setText(MainActivity.pref+" "+Double.toString(Act_llenar_factura.total));
        Act_llenar_factura.total=0;
        MainActivity.pagos.resetCuentaGrupo(Act_llenar_factura.nombre_grupo);


    }

    public void aceptar (View view){

        txtDistribucion=null;
        Act_llenar_factura.btnCancelar.performClick();
        finish();

    }
}