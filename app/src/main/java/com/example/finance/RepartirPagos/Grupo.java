package com.example.finance.RepartirPagos;

import java.util.ArrayList;

public class Grupo {

	private String nombre_grupo;
	private ArrayList<Persona> miembros = new ArrayList<>();

	public Grupo(String nombre_grupo) {
		super();
		this.nombre_grupo = nombre_grupo;
	}

	public void addMiembro(Persona p) {
		boolean cumple = true;

		for (Persona m : miembros){
			if (m.getNombre().equals(p.getNombre())){
				cumple=false;
			}
		}

		if(cumple){
			this.miembros.add(p);
		}
	}

	public String getNombre_grupo() {
		return nombre_grupo;
	}

	public ArrayList<Persona> getMiembros() {
		return miembros;
	}

	public void personaPidio(String nombre, double paga, Item product) {

		for (Persona persona : miembros) {
			if (persona != null) {
				if (/*persona.getNombre().length()==nombre.length()*/persona.getNombre().equals(nombre)) {
					persona.setPaga(paga,product);;
				}
			}
		}
	}

	public ArrayList<String> miembros2string() {

		ArrayList<String > integrantes = new ArrayList<>();

		for(Persona p :miembros ){
			integrantes.add(p.getNombre());
		}

		return integrantes;
	}

	public ArrayList<String> cuentaGrupal() {

		ArrayList<String> factura = new ArrayList<>();

		for (Persona p : miembros){
			factura.add(p.toString());
		}

		return factura;
	}

	public void resetCuenta(){

		ArrayList<Persona> copia = new ArrayList<>();
		for (Persona p : miembros){
			copia.add(new Persona(p.getNombre(),0));
		}
		this.miembros=copia;
	}

}
