package com.example.finance.RepartirPagos;

import java.util.ArrayList;

public class DivisionPago {

	ArrayList<Grupo> grupos = new ArrayList<>();
	ArrayList<Item> cuenta = new ArrayList<>();

	public ArrayList<Item> getCuenta() {
		return cuenta;
	}

	public void addGrupo(Grupo gg) {
		this.grupos.add(gg);

	}

	public boolean hayGrupos(){
		boolean respuesta=true;

		for(Grupo g : grupos){
			if(g!=null){
				respuesta=false;
			}
		}
		return respuesta;
	}

	public ArrayList<Grupo> getGrupos() {
		return grupos;
	}

	public String buscarGrupoXpos(int p){
		return (grupos.get(p).getNombre_grupo()) ;
	}

	public Grupo buscarGrupoXnom(String nombre){

		for (Grupo g : grupos){
			if(g.getNombre_grupo().equals(nombre)){
				return g ;
			}
		}
		return null;
	}

	public ArrayList<String> getGruposAsString() {

		ArrayList<String> nombre_grupos = new ArrayList<>();

		for (Grupo g : grupos){
			nombre_grupos.add(g.getNombre_grupo());
		}

		return nombre_grupos;
	}

	public boolean existe(String grupo) {
		boolean respuesta = false;

		for (Grupo g : grupos) {
			if (g != null) {
				String nombre = g.getNombre_grupo();
				if(/*grupo.length()==nombre.length()*/grupo.equals(g.getNombre_grupo())) {
					respuesta = true;
				}
			}
		}
		return respuesta;
	}

	public void addProducto(Item item) {
		this.cuenta.add(item);
	}

	public ArrayList<String> dividirCuenta(String nombre_grupo) {

		Grupo grupo = null;
		//int cont = 0;
		//int pos = 0;

		for (Grupo g : grupos) {
			if (g != null) {
				if(nombre_grupo.equals(g.getNombre_grupo())) {
					grupo=g;
					//pos=cont;
					for (Item i : cuenta) {
						for (Persona p : g.getMiembros()){
							if(i.getPersona().equals(p.getNombre())) {
								g.personaPidio(i.getPersona(),i.getMonto(),i);
							}
						}
					}
				}
			}
			//cont++;
		}

		//this.grupos.set(pos,grupo);

		return grupo.cuentaGrupal();

	}

	/*public void imprimirDivisionPagos() {
		for (int i = 0; i < this.grupos.size(); i++) { //Recorrer cada grupo
			System.out.println("GRUPO: " + this.grupos.get(i).getNombre_grupo());
			for (int j = 0; j < this.grupos.get(i).getMiembros().size(); j++) { //Recorrer cada miembro de un grupo
				System.out.println("Miembro: " + this.grupos.get(i).getMiembros().get(j).getNombre());
			}
		}
	}*/

	public void resetCuentaGrupo (String nombre_grupo){

		for (Grupo g : grupos){
			g.resetCuenta();
		}
		this.cuenta.clear();

		/*for (Grupo g: grupos){
			if(g.getNombre_grupo().equals(nombre_grupo)){
				g.resetCuenta();
			}
		}*/

	}

}