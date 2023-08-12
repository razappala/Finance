package com.example.finance.RepartirPagos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finance.R;
import com.example.finance.Nucleo.MainActivity;

public class Act_llenar_factura extends AppCompatActivity {

    private TextView txtTotal;
    public static double total=0;
    private TextView txtMiembro;
    private EditText txtDesp;
    private EditText txtPrecio;
    private Button btnAgregar;
    public static Button btnCancelar;
    private Button btnCalcular;
    private ListView miembros;
    public static String nombre_grupo;
    private AlertDialog.Builder alertaMiembro;
    private AlertDialog.Builder alertaProductoEmpty;
    private AlertDialog.Builder alertaPrecioEmpty;
    private AlertDialog.Builder alertaPrecioNega;
    private AlertDialog.Builder alertaInvalido;
    private AlertDialog.Builder alertaExito;
    private AlertDialog.Builder alertaEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_llenar_factura);

        DisplayMetrics view = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(view);
        getWindow().setLayout((int)(view.widthPixels * 1), (int)(view.heightPixels * 1));

        txtDesp = findViewById(R.id.producto);
        txtPrecio = findViewById(R.id.precio);
        txtTotal = findViewById(R.id.saldo_factura);
        txtMiembro = findViewById(R.id.nombre_selec);
        txtMiembro.setText("");
        btnAgregar = findViewById(R.id.agregar_item);
        btnCancelar = findViewById(R.id.cancelar_factura);
        btnCalcular = findViewById(R.id.calcular_factura);
        miembros = findViewById(R.id.miembros_grupo);

        Grupo grupo = MainActivity.pagos.buscarGrupoXnom(nombre_grupo);

        if(grupo!=null){
            ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, grupo.miembros2string());
            miembros.setAdapter(adaptador);
        }

        miembros.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtMiembro.setText(grupo.miembros2string().get(position));
            }
        });

    }

    public void agregarProducto(View view){

        alertaMiembro = new AlertDialog.Builder(this);
        alertaProductoEmpty = new AlertDialog.Builder(this);
        alertaPrecioEmpty = new AlertDialog.Builder(this);
        alertaPrecioNega = new AlertDialog.Builder(this);
        alertaInvalido = new AlertDialog.Builder(this);
        alertaExito = new AlertDialog.Builder(this);

        alertaMiembro.setMessage("Debe seleccionar un miembro para continuar")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertaProductoEmpty.setMessage("Debe ingresar el nombre del producto")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


        alertaPrecioEmpty.setMessage("Debe ingresar el precio del producto")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertaPrecioNega.setMessage("No se aceptan precios negativos")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertaInvalido.setMessage("El precio no puede contener caracteres especiales")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertaExito.setMessage("Producto Agregado con Exito")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        procesoAgregarProducto();

    }

    public void procesoAgregarProducto() {

        if(txtMiembro.getText().toString().equals("")) {
            AlertDialog alerta = alertaMiembro.create();
            alerta.show();
        }else{
            if (txtDesp.getText().toString().equals("")){
                AlertDialog alerta1 = alertaProductoEmpty.create();
                alerta1.show();
            }else{
                if( (txtPrecio.getText().toString().length()==0) ){
                    AlertDialog alerta2 = alertaPrecioEmpty.create();
                    alerta2.show();
                }else{
                    if (Double.parseDouble(txtPrecio.getText().toString())<0){
                        AlertDialog alerta3 = alertaPrecioNega.create();
                        alerta3.show();
                    }else{
                        if( (Double.parseDouble(txtPrecio.getText().toString())==0) ){
                            AlertDialog alerta2 = alertaPrecioEmpty.create();
                            alerta2.show();
                        }else{

                            Item item = new Item(txtMiembro.getText().toString(),Double.parseDouble(txtPrecio.getText().toString()),txtDesp.getText().toString());
                            MainActivity.pagos.addProducto(item);

                            total = total + Double.parseDouble(txtPrecio.getText().toString());
                            txtTotal.setText(MainActivity.pref+Double.toString(total));
                            txtMiembro.setText("");
                            txtDesp.setText("");
                            txtPrecio.getText().clear();

                            AlertDialog alerta3 = alertaExito.create();
                            alerta3.show();

                        }
                    }
                }
            }
        }

        ///explota con las comas ,,,,
        /*boolean verificacion = true;

        for (int i = 0; i > txtPrecio.getText().toString().length(); i++){
            if (txtPrecio.getText().toString().charAt(i) == ',')
                verificacion = false;
        }

        if(!verificacion){
            AlertDialog alerta4 = alertaInvalido.create();
            alerta4.show();
        }*/

    }

    public void calcular (View view){

        alertaEmpty = new AlertDialog.Builder(this);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (MainActivity.pagos.getCuenta().size()>0){

                        Intent actGrupos = new Intent(getApplicationContext(), Act_total_factura.class);
                        startActivity(actGrupos);

                    }else{

                        alertaEmpty.setMessage("Es necesario ingresar productos a la cuenta")
                                .setCancelable(false)
                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alerta4 = alertaEmpty.create();
                        alerta4.show();

                    }

                }
            });

    }

    public void cancelar (View view){
        total=0;
        finish();
    }

}