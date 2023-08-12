package com.example.finance.ConsultarHistorial.Condiciones;

import com.example.finance.Nucleo.Transaccion;

public class CondicionAbc implements CondicionTrans{
    @Override
    public boolean condicion(Transaccion a, Transaccion b){
        return a.getDescripcion().charAt(0) <= b.getDescripcion().charAt(0);
    }
}