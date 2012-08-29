package model;

import java.util.Date;
import java.util.Vector;

import util.Util;

public class TarifacaoMinima {

	private int codigo;
	
	private int matricula;

	private Date dataVigencia;

	private int codigoCategoria;

	private int codigoSubcategoria;

	private int consumoMinimoSubcategoria;

	private double tarifaMinimaCategoria;

	private Vector faixas;
	
	

	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public void setcodigo(String codigo) {
		this.codigo = Util.verificarNuloInt(codigo);
	}

	public void setDataVigencia(String dataVigencia) {
		this.dataVigencia = Util.getData(Util
				.verificarNuloString(dataVigencia));
	}

	public void setCodigoCategoria(String codigoCategoria) {
		this.codigoCategoria = Util.verificarNuloInt(codigoCategoria);
	}

	public void setCodigoSubcategoria(String codigoSubcategoria) {
		this.codigoSubcategoria = Util
				.verificarNuloInt(codigoSubcategoria);
	}

	public void setConsumoMinimoSubcategoria(
			String consumoMinimoSubcategoria) {
		this.consumoMinimoSubcategoria = Util
				.verificarNuloInt(consumoMinimoSubcategoria);
	}

	public void setTarifaMinimaCategoria(String tarifaMinimaCategoria) {
		this.tarifaMinimaCategoria = Util
				.verificarNuloDouble(tarifaMinimaCategoria);
	}

	public int getCodigo() {
		return codigo;
	}

	public Date getDataVigencia() {
		return dataVigencia;
	}

	public int getCodigoCategoria() {
		return codigoCategoria;
	}

	public int getCodigoSubcategoria() {
		return codigoSubcategoria;
	}

	public int getConsumoMinimoSubcategoria() {
		return consumoMinimoSubcategoria;
	}

	public double getTarifaMinimaCategoria() {
		return tarifaMinimaCategoria;
	}

	public void adicionarFaixa(TarifacaoComplementar faixa) {
		if (this.faixas == null) {
			this.faixas = new Vector();
		}

		this.faixas.addElement(faixa);
	}
}
