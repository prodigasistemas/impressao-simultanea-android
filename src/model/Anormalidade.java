package model;

import util.Util;

public class Anormalidade {

	private int codigo;
	private String descricao;
	private int indicadorLeitura;
	private int idConsumoACobrarSemLeitura;
	private int idConsumoACobrarComLeitura;
	private int idLeituraFaturarSemLeitura;
	private int idLeituraFaturarComLeitura;
	private int indcUso;
	private long id;
	private double numeroFatorSemLeitura;
	private double numeroFatorComLeitura;

	public Anormalidade(byte codigo, String descricao, int indicador) {
		this.codigo = codigo;

		if (descricao != null && descricao.length() > 21) {
			this.descricao = descricao.substring(21);
		} else {
			this.descricao = descricao;
		}

		this.indicadorLeitura = indicador;
	}

	public Anormalidade() {
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public int getIndicadorLeitura() {
		return indicadorLeitura;
	}

	public int getIdConsumoACobrarComLeitura() {
		return idConsumoACobrarComLeitura;
	}
	
	public int getIdConsumoACobrarSemLeitura() {
		return idConsumoACobrarSemLeitura;
	}
	
	public int getIdLeituraFaturarComLeitura() {
		return idLeituraFaturarComLeitura;
	}

	public int getIdLeituraFaturarSemLeitura() {
		return idLeituraFaturarSemLeitura;
	}
	
	public int getIndcUso() {
		return indcUso;
	}

	public void setId(long arg0) {
		this.id = arg0;
	}

	public long getId() {
		return this.id;
	}

	public double getNumeroFatorSemLeitura() {
		return numeroFatorSemLeitura;
	}

	public double getNumeroFatorComLeitura() {
		return numeroFatorComLeitura;
	}
	

	public void setNumeroFatorSemLeitura(String numeroFatorSemLeitura) {
		this.numeroFatorSemLeitura = Util.verificarNuloDouble(numeroFatorSemLeitura);
	}
	
	public void setNumeroFatorComLeitura(String numeroFatorComLeitura) {
		this.numeroFatorComLeitura = Util.verificarNuloDouble(numeroFatorComLeitura);
	}
	
	public void setCodigo(String codigo) {
		this.codigo = Util.verificarNuloInt(codigo);
	}

	public void setDescricao(String descricao) {
		if (descricao != null && descricao.length() > 21) {
			this.descricao = descricao.substring(0, 21);
		} else {
			this.descricao = descricao;
		}
	}

	public void setIndicadorLeitura(String indicadorLeitura) {
		this.indicadorLeitura = Util.verificarNuloInt(indicadorLeitura);
	}

	public void setIdConsumoACobrarComLeitura(String idConsumoACobrarComLeitura) {
		this.idConsumoACobrarComLeitura = Util.verificarNuloInt(idConsumoACobrarComLeitura);
	}

	public void setIdConsumoACobrarSemLeitura(String idConsumoACobrarSemLeitura) {
		this.idConsumoACobrarSemLeitura = Util.verificarNuloInt(idConsumoACobrarSemLeitura);
	}

	public void setIdLeituraFaturarSemLeitura(String idLeituraFaturarSemLeitura) {
		this.idLeituraFaturarSemLeitura = Util.verificarNuloInt(idLeituraFaturarSemLeitura);
	}

	public void setIdLeituraFaturarComLeitura(String idLeituraFaturarComLeitura) {
		this.idLeituraFaturarComLeitura = Util.verificarNuloInt(idLeituraFaturarComLeitura);
	}

	public void setIndcUso(String indcUso) {
		this.indcUso = Util.verificarNuloInt(indcUso);
	}

	public String toString() {
		return this.getDescricao();
	}

	public boolean equals(Object obj) {
		return this.getCodigo() == ((Anormalidade) obj).getCodigo();
	}

}
