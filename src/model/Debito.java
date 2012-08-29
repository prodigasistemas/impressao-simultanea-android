package model;

import util.Util;

public class Debito {

	private String codigo;
	private String descricao;
	private double valor;
	private short indcUso;

    public final static String TARIFA_CORTADO_DEC_18_251_94 = "2500";

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
		return obj instanceof Debito
				&& ((Debito) obj).getCodigo().equals(this.getCodigo());
	}

	public void setIndcUso(short indcUso) {
		this.indcUso = indcUso;
	}

	public short getIndcUso() {
		return indcUso;
	}

}
