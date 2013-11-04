package ui;

public class DadosImovel {

	final private String tipoLinha = "01";
	private int matricula;
	private String anoMesFaturamento;
	private int numeroConta;
	private int grupoFaturamento;
	private int codigoRota;
	private int leituraMedidor;
	private int anormalidadeLeitura;
	private String dataLeitura;
	private String indicadorSituacao;
	private String leituaAtual;
	private String consumoMedidoMes;
	private String consumoCobradoMes;
	private int consumoRateioAgua;
	private double valorRateioAgua;
	private int consumoRateioEsgoto;
	private double valorRateioEsgoto;
	private int tipoConsumo;
	private int anormalidadeConsumo;
	private int indicadorImovelImpresso;
	private String inscricao;
	private int indicadorGeracaoConta;
	private int consumoCobradoImoveisMicro;
	private int anormalidadeLeituraFaturada;
    private String numeroDocumentoNotificacaoDebito;
    private String numeroCodigoBarraNotificacaoDebito;
    private int leituraAnterior;
	private double latitude;
	private double longitude;
	
    public DadosImovel() {

    }
	
	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public String getAnoMesFaturamento() {
		return anoMesFaturamento;
	}

	public void setAnoMesFaturamento(String anoMesFaturamento) {
		this.anoMesFaturamento = anoMesFaturamento;
	}

	public int getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(int numeroConta) {
		this.numeroConta = numeroConta;
	}

	public int getGrupoFaturamento() {
		return grupoFaturamento;
	}

	public void setGrupoFaturamento(int grupoFaturamento) {
		this.grupoFaturamento = grupoFaturamento;
	}

	public int getCodigoRota() {
		return codigoRota;
	}

	public void setCodigoRota(int codigoRota) {
		this.codigoRota = codigoRota;
	}

	public int getLeituraMedidor() {
		return leituraMedidor;
	}

	public void setLeituraMedidor(int leituraMedidor) {
		this.leituraMedidor = leituraMedidor;
	}

	public int getAnormalidadeLeitura() {
		return anormalidadeLeitura;
	}

	public void setAnormalidadeLeitura(int anormalidadeLeitura) {
		this.anormalidadeLeitura = anormalidadeLeitura;
	}

	public String getDataLeitura() {
		return dataLeitura;
	}

	public void setDataLeitura(String dataLeitura) {
		this.dataLeitura = dataLeitura;
	}

	public String getIndicadorSituacao() {
		return indicadorSituacao;
	}

	public void setIndicadorSituacao(String indicadorSituacao) {
		this.indicadorSituacao = indicadorSituacao;
	}

	public String getLeituaAtual() {
		return leituaAtual;
	}

	public void setLeituaAtual(String leituaAtual) {
		this.leituaAtual = leituaAtual;
	}

	public String getConsumoMedidoMes() {
		return consumoMedidoMes;
	}

	public void setConsumoMedidoMes(String consumoMedidoMes) {
		this.consumoMedidoMes = consumoMedidoMes;
	}

	public String getConsumoCobradoMes() {
		return consumoCobradoMes;
	}

	public void setConsumoCobradoMes(String consumoCobradoMes) {
		this.consumoCobradoMes = consumoCobradoMes;
	}

	public int getConsumoRateioAgua() {
		return consumoRateioAgua;
	}

	public void setConsumoRateioAgua(int consumoRateioAgua) {
		this.consumoRateioAgua = consumoRateioAgua;
	}

	public double getValorRateioAgua() {
		return valorRateioAgua;
	}

	public void setValorRateioAgua(double valorRateioAgua) {
		this.valorRateioAgua = valorRateioAgua;
	}

	public int getConsumoRateioEsgoto() {
		return consumoRateioEsgoto;
	}

	public void setConsumoRateioEsgoto(int consumoRateioEsgoto) {
		this.consumoRateioEsgoto = consumoRateioEsgoto;
	}

	public double getValorRateioEsgoto() {
		return valorRateioEsgoto;
	}

	public void setValorRateioEsgoto(double valorRateioEsgoto) {
		this.valorRateioEsgoto = valorRateioEsgoto;
	}

	public int getTipoConsumo() {
		return tipoConsumo;
	}

	public void setTipoConsumo(int tipoConsumo) {
		this.tipoConsumo = tipoConsumo;
	}

	public int getAnormalidadeConsumo() {
		return anormalidadeConsumo;
	}

	public void setAnormalidadeConsumo(int anormalidadeConsumo) {
		this.anormalidadeConsumo = anormalidadeConsumo;
	}

	public int getIndicadorImovelImpresso() {
		return indicadorImovelImpresso;
	}

	public void setIndicadorImovelImpresso(int indicadorImovelImpresso) {
		this.indicadorImovelImpresso = indicadorImovelImpresso;
	}

	public String getInscricao() {
		return inscricao;
	}

	public void setInscricao(String inscricao) {
		this.inscricao = inscricao;
	}

	public int getIndicadorGeracaoConta() {
		return indicadorGeracaoConta;
	}

	public void setIndicadorGeracaoConta(int indicadorGeracaoConta) {
		this.indicadorGeracaoConta = indicadorGeracaoConta;
	}

	public int getConsumoCobradoImoveisMicro() {
		return consumoCobradoImoveisMicro;
	}

	public void setConsumoCobradoImoveisMicro(int consumoCobradoImoveisMicro) {
		this.consumoCobradoImoveisMicro = consumoCobradoImoveisMicro;
	}

	public int getAnormalidadeLeituraFaturada() {
		return anormalidadeLeituraFaturada;
	}

	public void setAnormalidadeLeituraFaturada(int anormalidadeLeituraFaturada) {
		this.anormalidadeLeituraFaturada = anormalidadeLeituraFaturada;
	}

	public String getNumeroDocumentoNotificacaoDebito() {
		return numeroDocumentoNotificacaoDebito;
	}

	public void setNumeroDocumentoNotificacaoDebito(
			String numeroDocumentoNotificacaoDebito) {
		this.numeroDocumentoNotificacaoDebito = numeroDocumentoNotificacaoDebito;
	}

	public String getNumeroCodigoBarraNotificacaoDebito() {
		return numeroCodigoBarraNotificacaoDebito;
	}

	public void setNumeroCodigoBarraNotificacaoDebito(
			String numeroCodigoBarraNotificacaoDebito) {
		this.numeroCodigoBarraNotificacaoDebito = numeroCodigoBarraNotificacaoDebito;
	}

	public int getLeituraAnterior() {
		return leituraAnterior;
	}

	public void setLeituraAnterior(int leituraAnterior) {
		this.leituraAnterior = leituraAnterior;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getTipoLinha() {
		return tipoLinha;
	}

}
