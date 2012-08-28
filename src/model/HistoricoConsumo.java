package model;

import util.Util;

public class HistoricoConsumo {

	private long id;
	private int matricula;
	private int tipoLigacao;
	private int anoMesReferencia;
	private String consumo;
	private int anormalidadeLeitura;
	private int anormalidadeConsumo;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public int getAnoMesReferencia() {
		return anoMesReferencia;
	}

	public int getAnormalidadeConsumo() {
		return anormalidadeConsumo;
	}

	public void setAnoMesReferencia(String ano) {
		this.anoMesReferencia = Util.verificarNuloInt(ano);
	}

	public int getTipoLigacao() {
		return tipoLigacao;
	}

	public void setTipoLigacao(String tipoLigacao) {
		this.tipoLigacao = Util.verificarNuloInt(tipoLigacao);
	}

	public String getConsumo() {
		return consumo;
	}

	public void setConsumo(String consumo) {
		this.consumo = consumo;
	}

	public int getAnormalidadeLeitura() {
		return anormalidadeLeitura;
	}

	public void setAnormalidadeLeitura(String anormalidadeLeitura) {
		this.anormalidadeLeitura = Util.verificarNuloInt(anormalidadeLeitura);
	}

	public void setAnormalidadeConsumo(String anormalidadeConsumo) {
		this.anormalidadeConsumo = Util.verificarNuloInt(anormalidadeConsumo);
	}

}
