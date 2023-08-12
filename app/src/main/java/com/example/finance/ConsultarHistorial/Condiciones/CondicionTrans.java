package com.example.finance.ConsultarHistorial.Condiciones;

import com.example.finance.Nucleo.Transaccion;

public interface CondicionTrans {
    public boolean condicion(Transaccion a, Transaccion b);
}
