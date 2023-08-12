package com.example.finance.ConsultarHistorial;

import com.example.finance.Nucleo.Historial;
import com.example.finance.Nucleo.Monedero;
import com.example.finance.Nucleo.Transaccion;
import com.example.finance.Nucleo.tipoTransaccion;

import java.time.LocalDateTime;

public class ControlHistorial {

    public void agregarHistorial(Monedero cartera, Historial libreta, double valor,
                                 tipoTransaccion tipo, String descipcion, LocalDateTime fecha) {

        //Actualizamos el monedero
        if (tipo == tipoTransaccion.egreso)
            valor = -valor;
        cartera.setSaldo(cartera.getSaldo() + valor);
        //--------------------------------

        //Guardamos en el historial nueva transaccion
        Transaccion transaccion = new Transaccion(valor, tipo, descipcion, fecha);

        if (this.hayVacio(libreta))
            libreta.setHistorico(transaccion);
        else
            libreta.remplazarPrimero(transaccion);
        //--------------------------------

    }

    public void agregarHistorialCrono(Monedero cartera, Historial libreta, double valor,
                                 tipoTransaccion tipo, String descipcion , LocalDateTime fecha) {

        //Vefificamos que la fecha de hoy sea la misma o ya haya pasado
        LocalDateTime fechaHoy = LocalDateTime.now();

        if(fechaHoy.equals(fecha) || fechaHoy.isBefore(fecha)) {
            if (tipo == tipoTransaccion.egreso)
                valor = -valor;
            cartera.setSaldo(cartera.getSaldo() + valor);
            //cartera.calcularSaldo(valor);

            //Guardamos en el historial nueva transaccion
            Transaccion transaccion = new Transaccion(valor, tipo, descipcion, fechaHoy);
            if (this.hayVacio(libreta))
                libreta.setHistorico(transaccion);
            else
                libreta.remplazarPrimero(transaccion);
        }
    }
    /////////////////////////////////////////////////////////////

    private boolean hayVacio(Historial libreta) {
        int noVacios = 0;

        for (int i = 0; i < libreta.largoHistorial(); i++){
            if (libreta.getHistorico(i) != null)
                noVacios++;
        }
        return !(noVacios == 30);
    }
}
