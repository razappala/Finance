package com.example.finance.ConsultarHistorial;

import android.view.View;
import android.widget.ArrayAdapter;

import com.example.finance.Nucleo.MainActivity;

public class MediadorTrans implements Mediador{
    private InicioFragment inicioF;

    MediadorTrans(InicioFragment f){
        inicioF  = f;
    }

    @Override
    public void actualizarDatos(){

        //String[] strHistorial = MainActivity.historial.mostrarHistorial();
        String[] strHistorial = ordenHistorial();
        ArrayAdapter adaptador = new ArrayAdapter(inicioF.textHistorial.getContext(), android.R.layout.simple_list_item_1, strHistorial);
        inicioF.textHistorial.setAdapter(adaptador);
        inicioF.textMonedero.setText(MainActivity.monedero.mostrarSaldo());

    }

    @Override
    public void actualizarVacio(){
        if (MainActivity.historial.esVacio())
            inicioF.txtVacio.setVisibility(View.VISIBLE);
        else
            inicioF.txtVacio.setVisibility(View.GONE);
    }

    private String[] ordenHistorial(){
        OrganizadorHistorial organizador = new OrganizadorHistorial(MainActivity.historial);
        switch (inicioF.getOrden()){
            case reciente:
                    return MainActivity.historial.mostrarHistorial();
            case antiguo:
                    return organizador.inversor(MainActivity.historial).mostrarHistorial();
            case alfabeticamente:
                    return organizador.inversor(organizador.historialXAbc()).mostrarHistorial();
            case zA:
                    return organizador.historialXAbc().mostrarHistorial();
            case mayor:
                    return organizador.historialXValor().mostrarHistorial();
            case menor:
                    return organizador.inversor(organizador.historialXValor()).mostrarHistorial();
            case ingresos:
                    return organizador.historialIngresos().mostrarHistorial();
            case egresos:
                    return organizador.historialEgresos().mostrarHistorial();
        }
        return null;
    }
}
