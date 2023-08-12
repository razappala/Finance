package com.example.finance.ConsultarHistorial;

import com.example.finance.ConsultarHistorial.Condiciones.CondicionAbc;
import com.example.finance.ConsultarHistorial.Condiciones.CondicionTrans;
import com.example.finance.ConsultarHistorial.Condiciones.CondicionValor;
import com.example.finance.Nucleo.Historial;
import com.example.finance.Nucleo.Transaccion;
import com.example.finance.Nucleo.tipoTransaccion;

import java.time.LocalDateTime;

public class OrganizadorHistorial {
    private Historial libreta;

    OrganizadorHistorial(Historial h){
        this.libreta = h;
    }

    public Historial historialXValor(){
        Historial libretaAux = mergesort(this.copia(libreta), new CondicionValor());
        return libretaAux;
    }

    public Historial historialXAbc(){
        Historial libretaAux = mergesort(this.copia(libreta), new CondicionAbc());
        return libretaAux;
    }

    public Historial inversor(Historial h){
        Historial libretaAux   = this.copia(h);
        Historial libretaInver = new Historial();

        if (!h.esVacio()){
            for (int i = libretaAux.largoHistorial(); i >= 0; i--)
                libretaInver.setHistorico(libretaAux.getHistorico(i));
        }
        return libretaInver;
    }

    public Historial historialIngresos(){
        Historial libretaAux = this.copia(libreta);
        Historial libretaIng = new Historial();

        if (!libreta.esVacio()){
            for (int i = 0; i < libretaAux.largoHistorial(); i++)
                if (libretaAux.getHistorico(i).getTipo() == tipoTransaccion.ingreso)
                    libretaIng.setHistorico(libretaAux.getHistorico(i));
        }
        return libretaIng;
    }

    public Historial historialEgresos(){
        Historial libretaAux = this.copia(libreta);
        Historial libretaEgr = new Historial();

        if (!libreta.esVacio()){
            for (int i = 0; i < libretaAux.largoHistorial(); i++)
                if (libretaAux.getHistorico(i).getTipo() == tipoTransaccion.egreso)
                    libretaEgr.setHistorico(libretaAux.getHistorico(i));
        }
        return libretaEgr;
    }

    private Historial copia(Historial libreta){
        Historial libretaAux = new Historial();
        if (!libreta.esVacio()){
            for (int i = 0; i < this.libreta.largoHistorial(); i++){
                double          valor       = libreta.getHistorico(i).getValor();
                tipoTransaccion tipo        = libreta.getHistorico(i).getTipo();
                String          descripcion = libreta.getHistorico(i).getDescripcion();
                LocalDateTime   fecha       = libreta.getHistorico(i).getFecha();

                Transaccion transAux = new Transaccion(valor, tipo, descripcion, fecha);
                libretaAux.setHistorico(transAux);
            }
        }
        return libretaAux;
    }

    private Historial mergesort(Historial libreta, CondicionTrans c){
        Historial result = new Historial();
        Historial izq    = new Historial();
        Historial der    = new Historial();

        if (libreta.largoHistorial() <= 1){
            return libreta;
        }
        else{
            int medio = libreta.largoHistorial() / 2;

            for (int i = 0; i < medio; i++)
                izq.setHistorico(libreta.getHistorico(i));
            for (int i = medio; i < libreta.largoHistorial(); i++)
                der.setHistorico(libreta.getHistorico(i));
            System.out.println("================================VUELTA================================");
            for (int i = 0; i < izq.largoHistorial(); i++)
                System.out.println(izq.getHistorico(i).toString());
            System.out.println("================================");
            for (int i = 0; i < der.largoHistorial(); i++)
                System.out.println(der.getHistorico(i).toString());
            System.out.println("================================VUELTA================================");
            izq = mergesort(izq, c);
            der = mergesort(der, c);

            if (((izq.largoHistorial() != 0) && (der.largoHistorial() != 0)) && (c.condicion(izq.getHistorico(izq.largoHistorial() -1), der.getHistorico(0)))){
                for (int i = 0; i < der.largoHistorial(); i++)
                    izq.setHistorico(der.getHistorico(i));
                return izq;
            }
            result = merge(izq, der, c);
        }
        return result;
    }

    private Historial merge(Historial izq,Historial der, CondicionTrans c){
        Historial result = new Historial();

        while ((izq.largoHistorial() > 0) && (der.largoHistorial() > 0)){
            if (c.condicion(izq.getHistorico(0), der.getHistorico(0))){
                result.setHistorico(izq.getHistorico(0));
                izq = eliminarPrimera(izq);
            }
            else {
                result.setHistorico(der.getHistorico(0));
                der = eliminarPrimera(der);
            }
        }

        if (izq.largoHistorial() > 0){
            for (int i = 0; i < izq.largoHistorial(); i++)
                result.setHistorico(izq.getHistorico(i));
        }
        if (der.largoHistorial() > 0){
            for (int i = 0; i < der.largoHistorial(); i++)
                result.setHistorico(der.getHistorico(i));
        }

        return result;
    }

    private Historial eliminarPrimera(Historial h){
        Historial libretaAux = new Historial();
        for (int j = 1; j <= h.largoHistorial(); j++)
            libretaAux.setHistorico(h.getHistorico(j));
        return libretaAux;
    }
}
