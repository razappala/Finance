package com.example.finance.ConsultarHistorial.Condiciones;

import com.example.finance.Nucleo.Transaccion;

public class CondicionValor implements CondicionTrans{
    @Override
    public boolean condicion(Transaccion a, Transaccion b){
        return a.getValor() <= b.getValor();
    }
}
