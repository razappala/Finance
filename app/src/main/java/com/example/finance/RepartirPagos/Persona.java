package com.example.finance.RepartirPagos;

import com.example.finance.Nucleo.MainActivity;

import java.util.ArrayList;

public class Persona {

	private String nombre;
	private double paga ;
	private ArrayList<String> pidio = new ArrayList<>();

	public Persona(String nombreMiembro,double paga) {
		this.nombre = nombreMiembro;
		this.paga=paga;
	}

	public String getNombre() {
		return nombre;
	}

	public void setPaga(double pagaa, Item i) {

		this.paga = this.paga +pagaa;
		pidio.add(MainActivity.pref.toString()+" "+i.getMonto()+" "+i.getDescripcion());

	}

	@Override
	public String toString() {
		return nombre + " = "+MainActivity.pref+" "+paga+" "+pidio.toString() ;
	}

	/*public void reset (){
		this.paga=0;
		pidio.clear();

	}*/

}