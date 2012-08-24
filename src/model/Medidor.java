package model;

import util.Constantes;
import util.Util;

public class Medidor {

	private int possuiMedidor;
	private String numeroHidrometro;
	private int marca;
	private double capacidade;
	private int tipoCaixaProtecao;
	private boolean tabSaved;
	private double latitude;
	private double longitude;
	private String data;
	
	public Medidor(){
		possuiMedidor = Constantes.NAO;
		numeroHidrometro = "";
		marca = 0;
		capacidade = 0;
		tipoCaixaProtecao = 0;
		latitude = 0;
		longitude = 0;
		data = "";
	}
	
	public void setPossuiMedidor(String possuiMedidor) {
		this.possuiMedidor = Util.verificarNuloInt(possuiMedidor);
	}

	public void setNumeroHidrometro(String numeroHidrometro) {
		this.numeroHidrometro = Util.verificarNuloString(numeroHidrometro);
	}

	public void setMarca(String marca) {
		this.marca = Util.verificarNuloInt(marca);
	}

	public void setCapacidade(String capacidade) {
		this.capacidade = Util.verificarNuloDouble(capacidade);
	}

	public void setTipoCaixaProtecao(String tipoCaixaProtecao) {
		this.tipoCaixaProtecao = Util.verificarNuloInt(tipoCaixaProtecao);
	}

	public void setLatitude(String latitude) {
		this.latitude = Util.verificarNuloDouble(latitude);
	}

	public void setLongitude(String longitude) {
		this.longitude = Util.verificarNuloDouble(longitude);
	}

	public void setData(String data) {
		this.data = Util.verificarNuloString(data);
	}

	public void setTabSaved(boolean tabSaved) {
		this.tabSaved = tabSaved;
    }
	
	public int getPossuiMedidor() {
		return this.possuiMedidor;
	}

	public String getNumeroHidrometro() {
		return this.numeroHidrometro;
	}

	public int getMarca() {
		return this.marca;
	}

	public double getCapacidade() {
		return this.capacidade;
	}

	public int getTipoCaixaProtecao() {
		return this.tipoCaixaProtecao;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public String getData() {
		return this.data;
	}

	public boolean isTabSaved(){
		return tabSaved;
	}

}
