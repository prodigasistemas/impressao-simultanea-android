package ui;

public class DadosTarifacao {

	private int tipoLinha;
	private int matricula;
	private int codigoCategoria;
	private short indicadorTarifaCategoria;
	private String codigoSubcategoria;
	private double valorFaturadoAgua; 
	private int consumoFaturadoAgua;
	private double valorTarifaMinimaAgua; 
	private int consumoMinimoAgua;
	private double valorFaturadoEsgoto; 
	private int consumoFaturadoEsgoto;
	private double valorTarifaMinimaEsgoto; 
	private int consumoMinimoEsgoto;
	private int quantidadeContasImpressas;

    public DadosTarifacao(int tipoLinha) {
    	this.tipoLinha = tipoLinha;
    }
	
	public int getTipoLinha() {
		return tipoLinha;
	}

	public void setTipoLinha(int tipoLinha) {
		this.tipoLinha = tipoLinha;
	}

	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public int getCodigoCategoria() {
		return codigoCategoria;
	}

	public void setCodigoCategoria(int codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}

	public short getIndicadorTarifaCategoria() {
		return indicadorTarifaCategoria;
	}

	public void setIndicadorTarifaCategoria(short indicadorTarifaCategoria) {
		this.indicadorTarifaCategoria = indicadorTarifaCategoria;
	}

	public String getCodigoSubcategoria() {
		return codigoSubcategoria;
	}

	public void setCodigoSubcategoria(String codigoSubcategoria) {
		this.codigoSubcategoria = codigoSubcategoria;
	}

	public double getValorFaturadoAgua() {
		return valorFaturadoAgua;
	}

	public void setValorFaturadoAgua(double valorFaturadoAgua) {
		this.valorFaturadoAgua = valorFaturadoAgua;
	}

	public int getConsumoFaturadoAgua() {
		return consumoFaturadoAgua;
	}

	public void setConsumoFaturadoAgua(int consumoFaturadoAgua) {
		this.consumoFaturadoAgua = consumoFaturadoAgua;
	}

	public double getValorTarifaMinimaAgua() {
		return valorTarifaMinimaAgua;
	}

	public void setValorTarifaMinimaAgua(double valorTarifaMinimaAgua) {
		this.valorTarifaMinimaAgua = valorTarifaMinimaAgua;
	}

	public int getConsumoMinimoAgua() {
		return consumoMinimoAgua;
	}

	public void setConsumoMinimoAgua(int consumoMinimoAgua) {
		this.consumoMinimoAgua = consumoMinimoAgua;
	}

	public double getValorFaturadoEsgoto() {
		return valorFaturadoEsgoto;
	}

	public void setValorFaturadoEsgoto(double valorFaturadoEsgoto) {
		this.valorFaturadoEsgoto = valorFaturadoEsgoto;
	}

	public int getConsumoFaturadoEsgoto() {
		return consumoFaturadoEsgoto;
	}

	public void setConsumoFaturadoEsgoto(int consumoFaturadoEsgoto) {
		this.consumoFaturadoEsgoto = consumoFaturadoEsgoto;
	}

	public double getValorTarifaMinimaEsgoto() {
		return valorTarifaMinimaEsgoto;
	}

	public void setValorTarifaMinimaEsgoto(double valorTarifaMinimaEsgoto) {
		this.valorTarifaMinimaEsgoto = valorTarifaMinimaEsgoto;
	}

	public int getConsumoMinimoEsgoto() {
		return consumoMinimoEsgoto;
	}

	public void setConsumoMinimoEsgoto(int consumoMinimoEsgoto) {
		this.consumoMinimoEsgoto = consumoMinimoEsgoto;
	}

	public int getQuantidadeContasImpressas() {
		return quantidadeContasImpressas;
	}

	public void setQuantidadeContasImpressas(int quantidadeContasImpressas) {
		this.quantidadeContasImpressas = quantidadeContasImpressas;
	}

}
