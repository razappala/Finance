package com.example.finance.Nucleo;

public class Monedero {

    //=====================// Atributos \\=====================\\
	private double          saldo;
    private UnidadMonetaria divisa;
    /////////////////////////////////////////////////////////////


    //======================// Metodos \\======================\\
    public Monedero(double saldo, UnidadMonetaria divisa) {
		this.saldo  = saldo;
        this.divisa = divisa;
	}
    /////////////////////////////////

	//Getter y Setter
    public double getSaldo()            { return this.saldo;  }
    public void   setSaldo(double monto){
        this.saldo = monto; }

    public UnidadMonetaria getDivisa()                 { return this.divisa; }
    public void            setDivisa(UnidadMonetaria d){this.divisa = d; }
    /////////////////////////////////////////////////////////////

    //Mostrar Informacion de saldo del monedero
	public String mostrarSaldo() {
        return divisa + " " + Math.round(saldo*100.0)/100.0;
	}
    /////////////////////////////////

    //Mostrar valor de la divisa segun el monedero
    public String stringDivisa(){
        //GBP,COP,PEN,JPY,CHF,SEK
        switch (this.divisa){
            case USD: return "USD";
            case EUR: return "EUR";
            case BsS: return "BsS";
            case GBP: return "GBP";
            case COP: return "COP";
            case PEN: return "PEN";
            case JPY: return "JPY";
            case CHF: return "CHF";
            case SEK: return "SEK";
        }
        return "";
    }
    /////////////////////////////////////////////////////////////

    //Monstrar divisas segun un entero /***************(Se usa para hacer lista de MonedaFragment)// revisar
    public String stringDivisa(int i){
        //GBP,COP,PEN,JPY,CHF,SEK
        switch (i){
            case 0: return "USD";
            case 1: return "EUR";
            case 2: return "BsS";
            case 3: return "GBP";
            case 4: return "COP";
            case 5: return "PEN";
            case 6: return "JPY";
            case 7: return "CHF";
            case 8: return "SEK";
        }
        return "";
    }
    /////////////////////////////////
}
