package com.example.finance.Nucleo;

import java.util.ArrayList;

public class Historial {

    //=====================// Atributos \\=====================\\
    private Transaccion[] historico = new Transaccion[30];
    /////////////////////////////////////////////////////////////

    //======================// Metodos \\======================\\
    public Historial(){
        for (int i = 0; i < 30; i++){
            this.historico[i] = null;
        }
    }
    /////////////////////////////////

    //Verifica que el historial esta completamente vacio
    public boolean esVacio(){
        for (int i = 0; i < 30; i++){
            if (historico[i] != null)
                return false;
        }
        return true;
    }
    /////////////////////////////////////////////////////////////


    //Agrega una transaccion al historial
    public void setHistorico(Transaccion transaccion){
        for (int i = 0; i < 30; i++) {
            if (this.historico[i] == null) {
                this.historico[i] = transaccion;
                break;
            }
        }
    }
    /////////////////////////////////

    //Devuelve la ultima transaccion que se agrego
    public Transaccion getHistorico(int i){
        return this.historico[i];
    }
    /////////////////////////////////////////////////////////////

    //********************************************************************************** (Revisar si mover a ControlHistorial)
    //Reemplaza la primera posicion del historial si esta lleno
    public void remplazarPrimero(Transaccion transaccion) {

        this.historico[0] = null;

        for (int i = 1; i < 29; i++) {
            this.historico[i-1] = this.historico[i];
        }

        this.historico[29] = transaccion;
    }
    //**********************************************************************************
    /////////////////////////////////

    //********************************************************************************** (Revisar si mover a ControlMonedero)
    public void convertirHistorial(float tasa) {
        for (Transaccion transaccion : historico)
            if (transaccion != null){
                double nuevoSaldo = transaccion.getValor() * tasa;
                transaccion.setValor(Math.round(nuevoSaldo*100.0)/100.0);
            }
    }
    //**********************************************************************************
    /////////////////////////////////////////////////////////////

    //**********************************************************************************
    public ArrayList<String> buscarTransaccion(String descrip){

        ArrayList<String> resultado = new ArrayList<>();

        for (int i = 29; i >= 0; i--) {
            if (historico[i] != null){
                if(historico[i].getDescripcion().equals(descrip)){
                    resultado.add(historico[i].toString());
                }
            }
        }

        return resultado;
    }
    //**********************************************************************************
    /////////////////////////////////////////////////////////////

    //Largo del historial
    public int largoHistorial(){
        int largo = 0;
        for (int i = 0; i < 30; i++)
            if (this.historico[i] != null) largo++;
        return largo;
    }
    /////////////////////////////////////////////////////////////

    //Mostrar el historial completo
    public String[] mostrarHistorial() {
        String[] strHistorial = new String[30];
        int j = 0;

        for (int i = 29; i >= 0; i--) {
            if (historico[i] != null){
                strHistorial[j] = historico[i].toString();
                j++;
            }
        }
        for (int i = j; i < 30; i++){
            strHistorial[i] = "";
        }

        return strHistorial;
    }
    /////////////////////////////////////////////////////////////
}
