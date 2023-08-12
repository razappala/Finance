package com.example.finance.RepartirPagos;

public class Item {

	private String persona;
	private double monto;
	private String descripcion;

	public Item(String persona, double monto, String descripcion) {
		super();
		this.persona = persona;
		this.monto = monto;
		this.descripcion = descripcion;
	}

	public String getPersona() {
		return persona;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public double getMonto() {
		return monto;
	}

}