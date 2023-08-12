package com.example.finance.Estadisticas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finance.R;
import com.example.finance.Nucleo.MainActivity;


public class EstadisticasFragment extends Fragment {

    //Variables para modificar los textview
    private TextView resultadoPromGanancias;
    private TextView resultadoPromIngresos;
    private TextView resultadoPromEgresos;
    private TextView resultadoCantidadIngresos;
    private TextView resultadoCantidadEgresos;

    public EstadisticasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_estadisticas, container, false);

        //Caso vacio
        TextView txtVacio = (TextView)view.findViewById(R.id.stats_vacio);

        //Crear un objeto de ControlEstadisticas y proporcionarle el historial
        ControlEstadisticas estadisticas = new ControlEstadisticas();
        estadisticas.obtenerHistorial(MainActivity.historial);

        //Conectar las variables con su respectivo elemento de diseno
        resultadoPromGanancias = (TextView) view.findViewById(R.id.resultadoPromGanancias);
        resultadoPromIngresos = (TextView) view.findViewById(R.id.resultadoPromIngresos);
        resultadoPromEgresos = (TextView) view.findViewById(R.id.resultadoPromEgresos);
        resultadoCantidadIngresos = (TextView) view.findViewById(R.id.resultadoCantidadIngresos);
        resultadoCantidadEgresos = (TextView) view.findViewById(R.id.resultadoCantidadEgresos);

        TextView txtStatic1 = (TextView)view.findViewById(R.id.estPromGanancias);
        TextView txtStatic2 = (TextView)view.findViewById(R.id.estPromIngresos);
        TextView txtStatic3 = (TextView)view.findViewById(R.id.estCPromEgresos);
        TextView txtStatic4 = (TextView)view.findViewById(R.id.estCantidadIngresos);
        TextView txtStatic5 = (TextView)view.findViewById(R.id.estCantidadEgresos);

        //Cambiar los textview del diseno por los valores de estadisticas
        resultadoPromGanancias.setText(String.valueOf(estadisticas.balance()));
        resultadoPromIngresos.setText(String.valueOf(estadisticas.promedioIngresos()));
        resultadoPromEgresos.setText(String.valueOf(estadisticas.promedioEgresos()));
        resultadoCantidadIngresos.setText(String.valueOf(estadisticas.cantidadIngresos()));
        resultadoCantidadEgresos.setText(String.valueOf(estadisticas.cantidadEgresos()));

        if (MainActivity.historial.esVacio()){
            resultadoPromGanancias.setVisibility(View.GONE);
            resultadoPromIngresos.setVisibility(View.GONE);
            resultadoPromEgresos.setVisibility(View.GONE);
            resultadoCantidadIngresos.setVisibility(View.GONE);
            resultadoCantidadEgresos.setVisibility(View.GONE);
            txtStatic1.setVisibility(View.GONE);
            txtStatic2.setVisibility(View.GONE);
            txtStatic3.setVisibility(View.GONE);
            txtStatic4.setVisibility(View.GONE);
            txtStatic5.setVisibility(View.GONE);

            txtVacio.setVisibility(View.VISIBLE);
        }
        else{
            resultadoPromGanancias.setVisibility(View.VISIBLE);
            resultadoPromIngresos.setVisibility(View.VISIBLE);
            resultadoPromEgresos.setVisibility(View.VISIBLE);
            resultadoCantidadIngresos.setVisibility(View.VISIBLE);
            resultadoCantidadEgresos.setVisibility(View.VISIBLE);
            txtStatic1.setVisibility(View.VISIBLE);
            txtStatic2.setVisibility(View.VISIBLE);
            txtStatic3.setVisibility(View.VISIBLE);
            txtStatic4.setVisibility(View.VISIBLE);
            txtStatic5.setVisibility(View.VISIBLE);

            txtVacio.setVisibility(View.GONE);
        }

        // Inflate the layout for this fragment
        return view;

    }

}