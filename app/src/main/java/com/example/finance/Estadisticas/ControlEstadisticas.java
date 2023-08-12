package com.example.finance.Estadisticas;

import com.example.finance.Nucleo.Historial;
import com.example.finance.Nucleo.Transaccion;
import com.example.finance.Nucleo.tipoTransaccion;

public class ControlEstadisticas {

	private Historial libreta;

	public void obtenerHistorial(Historial historial) {
		this.libreta = historial;
	}

	public double balance() { // cambiar nombre por balance

		double balance =0;
		int cont = 0;

		for (int i = 0; i < libreta.largoHistorial(); i++) {
			if (libreta.getHistorico(i) != null) {
				cont++;
				balance = balance + libreta.getHistorico(i).getValor();
			}
		}

		balance = balance / cont;

		return balance;

	}

	public double promedioIngresos() {

		double total =0;
		int cont =0;
		double promedioIngresos;

		for (int i = 0; i < libreta.largoHistorial(); i++) {
			if (libreta.getHistorico(i) != null) {
				if(libreta.getHistorico(i).getTipo() == tipoTransaccion.ingreso) {
					cont++;
					total = total + libreta.getHistorico(i).getValor();
				}
			}

		}

		promedioIngresos=total/cont;

		return promedioIngresos;

	}

	public double promedioEgresos() {

		double total =0;
		int cont =0;
		double promedioEgresos;

		for (int i = 0; i < libreta.largoHistorial(); i++) {
			if (libreta.getHistorico(i) != null) {
				if(libreta.getHistorico(i).getTipo() == tipoTransaccion.egreso) {
					cont++;
					total = total + libreta.getHistorico(i).getValor();
				}
			}

		}

		promedioEgresos=total/cont;

		return promedioEgresos;

	}

	public int cantidadIngresos() {

		int cont =0;

		for (int i = 0; i < libreta.largoHistorial(); i++) {
			if (libreta.getHistorico(i) != null) {
				if(libreta.getHistorico(i).getTipo() == tipoTransaccion.ingreso) {
					cont++;
				}
			}

		}

		return cont;

	}

	public int cantidadEgresos() {

		int cont =0;

		for (int i = 0; i < libreta.largoHistorial(); i++) {
			if (libreta.getHistorico(i) != null) {
				if(libreta.getHistorico(i).getTipo() == tipoTransaccion.egreso) {
					cont++;
				}
			}

		}

		return cont;
	}

}

