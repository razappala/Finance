package com.example.finance.RepartirPagos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.finance.R;
import com.example.finance.Nucleo.MainActivity;

public class pagosFragment extends Fragment {

    public static Button   buttonGrupo;
    public static Button   buttonTest;
    public static ListView textGrupos;
    public static TextView txtVacio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pagos, container, false);
        buttonGrupo      = (Button)view.findViewById(R.id.boton_newgrupo);
        //buttonTest      = (Button)view.findViewById(R.id.boton_test);
        textGrupos    = (ListView) view.findViewById(R.id.lista_grupos);

        ArrayAdapter adaptador = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, MainActivity.pagos.getGruposAsString());
        textGrupos.setAdapter(adaptador);
        txtVacio = (TextView)view.findViewById(R.id.grupos_vacio);

        if (MainActivity.pagos.hayGrupos()){
            txtVacio.setVisibility(View.VISIBLE);
        }
        else{
            txtVacio.setVisibility(View.GONE);
        }

        buttonGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actGrupos = new Intent(getActivity(), ActGrupos.class);
                startActivity(actGrupos);
            }
        });

        /*buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.pagos.imprimirDivisionPagos();

            }
        });*/

        textGrupos.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Aqui esta lo que se hace al pulsar un elemento de la lista

                Intent actPersonas = new Intent(getActivity(), Act_llenar_factura.class);
                startActivity(actPersonas);

                Act_llenar_factura.nombre_grupo = MainActivity.pagos.buscarGrupoXpos(position);


                //String item = (String) textGrupos.getItemAtPosition(position);
                //Toast.makeText(requireContext(),"You selected : " + item,Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public pagosFragment() {
        // Required empty public constructor
    }

    public static pagosFragment newInstance(String param1, String param2) {
        pagosFragment fragment = new pagosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}