package com.example.finance.Nucleo;

import java.time.LocalDateTime;

public class Transaccion {
    //=====================// Atributos \\=====================\\
    private double          valor;
    private tipoTransaccion tipo = tipoTransaccion.ninguna;
    private String          descripcion;
    private LocalDateTime   fecha;
    /////////////////////////////////////////////////////////////

    //======================// Metodos \\======================\\
    public Transaccion(){}

	//Constructor con fecha, basicamente
    public Transaccion(double v, tipoTransaccion t, String d, LocalDateTime f){
        valor       = v;
        tipo        = t;
        descripcion = d;
        fecha       = f;
    }
    /////////////////////////////////////////////////////////////

    //Getter y Setter
    public double getValor()            { return this.valor; }
    public void   setValor(double valor){ this.valor = valor; }

    public tipoTransaccion getTipo()                 { return this.tipo; }
    public void            setTipo(tipoTransaccion t){ this.tipo = t; }

    public String getDescripcion()        { return this.descripcion; }
    public void   setDescripcion(String d){ this.descripcion = d; }

    public LocalDateTime getFecha()                   { return this.fecha; }
    public void          setFecha(LocalDateTime fecha){ this.fecha = fecha; }
    /////////////////////////////////////////////////////////////

    //Mostrar los atributos de una transaccion por el momento /***************(Modificar para hacer un string mas bonito)// revisar
    public String toString() {
        return this.valor + "  " + this.tipo + "  " + this.descripcion + "  " + this.fecha;
    }
    /////////////////////////////////////////////////////////////
}

