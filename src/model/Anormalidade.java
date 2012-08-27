package model;

public class Anormalidade {

	private int codigo;

	private String descricao;

	private int indicadorLeitura;

	private int idConsumoACobrarSemLeitura;

	private int idConsumoACobrarComLeitura;

	private int idLeituraFaturarSemLeitura;

	private int idLeituraFaturarComLeitura;

	private int indcUso;

	private int id;

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

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void setDescricao(String descricao) {
		if (descricao != null && descricao.length() > 21) {
			this.descricao = descricao.substring(0, 21);
		} else {
			this.descricao = descricao;
		}
	}

	public void setIndicadorLeitura(int indicadorLeitura) {
		this.indicadorLeitura = indicadorLeitura;

	}

	public int getIdConsumoACobrarComLeitura() {
		return idConsumoACobrarComLeitura;
	}

	public int getIdConsumoACobrarSemLeitura() {
		return idConsumoACobrarSemLeitura;
	}

	public void setIdConsumoACobrarComLeitura(int idConsumoACobrarComLeitura) {
		this.idConsumoACobrarComLeitura = idConsumoACobrarComLeitura;
	}

	public void setIdConsumoACobrarSemLeitura(int idConsumoACobrarSemLeitura) {
		this.idConsumoACobrarSemLeitura = idConsumoACobrarSemLeitura;
	}

	public int getIdLeituraFaturarSemLeitura() {
		return idLeituraFaturarSemLeitura;
	}

	public void setIdLeituraFaturarSemLeitura(int idLeituraFaturarSemLeitura) {
		this.idLeituraFaturarSemLeitura = idLeituraFaturarSemLeitura;
	}

	public void setIdLeituraFaturarComLeitura(int idLeituraFaturarComLeitura) {
		this.idLeituraFaturarComLeitura = idLeituraFaturarComLeitura;
	}

	public int getIdLeituraFaturarComLeitura() {
		return idLeituraFaturarComLeitura;
	}

	public void setIndcUso(int indcUso) {
		this.indcUso = indcUso;
	}

	public int getIndcUso() {
		return indcUso;
	}

	public void setId(int arg0) {
		this.id = arg0;
	}

	public int getId() {
		return this.id;
	}

	public double getNumeroFatorSemLeitura() {
		return numeroFatorSemLeitura;
	}

	public void setNumeroFatorSemLeitura(double numeroFatorSemLeitura) {
		this.numeroFatorSemLeitura = numeroFatorSemLeitura;
	}

	public double getNumeroFatorComLeitura() {
		return numeroFatorComLeitura;
	}

	public void setNumeroFatorComLeitura(double numeroFatorComLeitura) {
		this.numeroFatorComLeitura = numeroFatorComLeitura;
	}

	public String toString() {
		return this.getDescricao();
	}

	public boolean equals(Object obj) {
		return this.getCodigo() == ((Anormalidade) obj).getCodigo();
	}

}
