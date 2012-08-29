package model;

import util.Util;

public class Credito {

    public final static String DESCRICAO_CERDITO_NITRATO = "DEDUCAO JUDICIAL";

	private int id;
	private int matricula;
	private String codigo;
	private String descricao;
	private double valor;
	private short indcUso;

	public void setDescricao(String descricao) {
		this.descricao = Util.verificarNuloString(descricao);
	}

	public void setValor(String valor) {
		this.valor = Util.verificarNuloDouble(valor);
	}

	public String getDescricao() {
		return descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setCodigo(String codigo) {
		this.codigo = Util.verificarNuloString(codigo);
	}

	public String getCodigo() {
		return codigo;
	}

	public boolean equals(Object obj) {
		return obj instanceof Credito
				&& ((Credito) obj).getCodigo().equals(this.getCodigo());
	}

	public void setIndcUso(short indcUso) {
		this.indcUso = indcUso;
	}

	public short getIndcUso() {
		return indcUso;
	}

}
