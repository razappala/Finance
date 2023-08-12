package com.example.finance.RepartirPagos;

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

import com.example.finance.R;
import com.example.finance.Nucleo.MainActivity;
import com.example.finance.Nucleo.ControlArchivos;
import java.util.ArrayList;

public class ActGrupos extends AppCompatActivity {

    private Button btnGuardar;
    private Button btnCancelar;
    private EditText txtGrupo, txtMiembro;
    private ListView textMiembros;
    private ArrayList<String> miembros = new ArrayList<>();
    private AlertDialog.Builder alertaErrorM1;
    private AlertDialog.Builder alertaErrorM2;
    private AlertDialog.Builder alertaLogroM;
    private AlertDialog.Builder alertaError1;
    private AlertDialog.Builder alertaError2;
    private AlertDialog.Builder alertaError3;
    private AlertDialog.Builder alertaLogro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_grupos);

        DisplayMetrics view = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(view);
        getWindow().setLayout((int)(view.widthPixels * 0.8), (int)(view.heightPixels * 0.9));

        txtGrupo    = findViewById(R.id.grupo_nombre);
        txtMiembro  = findViewById(R.id.miembro_nombre);
        btnCancelar = findViewById(R.id.grupo_cancelar);
        btnGuardar  = findViewById(R.id.guardar_grupo);
        textMiembros =  findViewById(R.id.lista_miembros);
    }

    public void agregarMiembro(View view){

        alertaLogroM       = new AlertDialog.Builder(this);
        alertaErrorM1       = new AlertDialog.Builder(this);
        alertaErrorM2       = new AlertDialog.Builder(this);

        alertaErrorM1.setMessage("El miembro ya se encuentra Registrado")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertaErrorM2.setMessage("Indique el nombre del miembro")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertaLogroM.setMessage("Miembro Registrado con exito")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        procesoMiembro();

    }

    private void procesoMiembro() {

        boolean cumple = true;

        for (String p : miembros){
            if (p.equals(txtMiembro.getText().toString())){
                cumple=false;
            }
        }

        if ((cumple)){

            if (!(txtMiembro.getText().toString().equals(""))){

                //AlertDialog alerta1 = alertaLogroM.create();
                miembros.add(txtMiembro.getText().toString());
                ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, miembros);
                textMiembros.setAdapter(adaptador);
                txtMiembro.getText().clear();
            }
            else {
                AlertDialog alerta2 = alertaErrorM2.create();
                alerta2.show();
            }
        }
        else{
            AlertDialog alerta2 = alertaErrorM1.create();
            alerta2.show();
        }

    }

    public void guardar (View view){

        alertaLogro       = new AlertDialog.Builder(this);
        alertaError1       = new AlertDialog.Builder(this);
        alertaError2       = new AlertDialog.Builder(this);
        alertaError3       = new AlertDialog.Builder(this);

        alertaError1.setMessage("Por favor indique el nombre del Grupo")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertaError2.setMessage("El minimo requerido es de dos miembros")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertaError3.setMessage("Ya existe un grupo asociado a ese nombre")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertaLogro.setMessage("Grupo Registrado con exito")
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                });

        procesoDeGrupo();
    }

    public void procesoDeGrupo(){

        String g = txtGrupo.getText().toString();

        if (!(g.equals(""))){

            if ((miembros.size()>=2)){
                if (!(MainActivity.pagos.existe(g))){

                    Grupo grupo  = new Grupo(txtGrupo.getText().toString());
                    for(String m : miembros){
                        grupo.addMiembro(new Persona(m,0));
                    }
                    this.miembros.clear();

                    MainActivity.pagos.addGrupo(grupo);
                    ArrayAdapter adaptador = new ArrayAdapter(pagosFragment.textGrupos.getContext(), android.R.layout.simple_list_item_1, MainActivity.pagos.getGruposAsString());
                    pagosFragment.textGrupos.setAdapter(adaptador);
                    AlertDialog alerta1 = alertaLogro.create();
                    alerta1.show();


                    ControlArchivosRepartirPagos controlArchivos = new ControlArchivosRepartirPagos();
                    controlArchivos.guardarGruposPersonas(this);


                }
                else {
                    AlertDialog alerta1 = alertaError3.create();
                    alerta1.show();
                }

            }
            else {
                AlertDialog alerta1 = alertaError2.create();
                alerta1.show();
            }

        }
        else{
            AlertDialog alerta1 = alertaError1.create();
            alerta1.show();
        }
    }

    public void cancelar (View view){
        finish();
    }

}