package com.example.finance.CambioDeMoneda;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finance.Nucleo.MainActivity;
import com.example.finance.R;
import com.example.finance.Nucleo.UnidadMonetaria;


public class MonedaFragment extends Fragment {

    public static int valorDivisa = 0;
    public static TextView textDivisa;

    public MonedaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moneda, container, false);

        textDivisa = (TextView)view.findViewById(R.id.cambio_divisa);
        ListView textCambio = (ListView)view.findViewById(R.id.cambio_lista);
        textDivisa.setText(MainActivity.monedero.stringDivisa());

            String[] strDivisa = new String[UnidadMonetaria.values().length];
            strDivisa[UnidadMonetaria.USD.ordinal()] = "USD";
            strDivisa[UnidadMonetaria.EUR.ordinal()] = "EUR";
            strDivisa[UnidadMonetaria.BsS.ordinal()] = "BsS";
            strDivisa[UnidadMonetaria.GBP.ordinal()] = "GBP";
            strDivisa[UnidadMonetaria.COP.ordinal()] = "COP";
            strDivisa[UnidadMonetaria.PEN.ordinal()] = "PEN";
            strDivisa[UnidadMonetaria.JPY.ordinal()] = "JPY";
            strDivisa[UnidadMonetaria.CHF.ordinal()] = "CHF";
            strDivisa[UnidadMonetaria.SEK.ordinal()] = "SEK";

        ArrayAdapter adaptador = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, strDivisa);
        textCambio.setAdapter(adaptador);

        textCambio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                valorDivisa = position;

                //Cuando el usuario esta seleccionando una divisa que ya tiene
                if(UnidadMonetaria.valueOf(MainActivity.monedero.stringDivisa(valorDivisa)) == MainActivity.monedero.getDivisa()){
                    AlertDialog.Builder alertaError = new AlertDialog.Builder(getActivity());
                    alertaError.setMessage("La divisa que seleccionaste ya esta en uso")
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alerta2 = alertaError.create();
                    alerta2.show();
                }
                else{
                    //Si No hay transacciones para hacer el cambio de moneda
                    //***************************************************************************
                    if (MainActivity.historial.esVacio()){
                        ControlMonetario cambio = new ControlMonetario();
                        cambio.setTasa(1);
                        cambio.convertirDatos(MainActivity.monedero, MainActivity.historial,
                                UnidadMonetaria.valueOf(MainActivity.monedero.stringDivisa(MonedaFragment.valorDivisa)));
                        MonedaFragment.textDivisa.setText(MainActivity.monedero.stringDivisa());
                        //******************************************************************************

                        AlertDialog.Builder alertaLogro       = new AlertDialog.Builder(getActivity());
                        alertaLogro.setMessage("Conversion lograda con exito")
                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alerta3 = alertaLogro.create();
                        alerta3.show();
                    }
                    else {
                        Intent actTrans = new Intent(getActivity(), ActConversion.class);
                        startActivity(actTrans);
                    }
                }

            }
        });
        return view;
    }
}