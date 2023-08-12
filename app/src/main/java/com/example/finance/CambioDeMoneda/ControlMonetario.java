package com.example.finance.CambioDeMoneda;

import com.example.finance.Nucleo.Historial;
import com.example.finance.Nucleo.Monedero;
import com.example.finance.Nucleo.UnidadMonetaria;

public class ControlMonetario {
	private float tasa;

	public void setTasa(float tasa2) {
		this.tasa = tasa2;
	}

	public void convertirDatos(Monedero cartera, Historial libreta, UnidadMonetaria unidadMonetaria) {
		double nuevoSaldo = cartera.getSaldo() * this.tasa;
		cartera.setSaldo(Math.round(nuevoSaldo*100.0)/100.0);
		cartera.setDivisa(unidadMonetaria);
		libreta.convertirHistorial(this.tasa);
	}
}
