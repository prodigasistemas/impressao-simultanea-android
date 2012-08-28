package model;

import util.Util;

public class SituacaoTipo {

    private static SituacaoTipo instancia;
    private int id;
    private int matricula;
	private int tipoSituacaoEspecialFeturamento;
	private int idAnormalidadeConsumoSemLeitura;
	private int idAnormalidadeConsumoComLeitura;
	private int idAnormalidadeLeituraSemLeitura;
	private int idAnormalidadeLeituraComLeitura;
	private int consumoAguaMedidoHistoricoFaturamento;
	private int consumoAguaNaoMedidoHistoricoFaturamento;
	private int volumeEsgotoMedidoHistoricoFaturamento;
	private int volumeEsgotoNaoMedidoHistoricoFaturamento;
	private int indcValidaAgua = 0;
	private int indcValidaEsgoto = 0;

	public final static int NITRATO = 9;

	public SituacaoTipo() {

	}
	
	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public int getTipoSituacaoEspecialFeturamento() {
		return tipoSituacaoEspecialFeturamento;
	}

	public void setTipoSituacaoEspecialFeturamento(
			String tipoSituacaoEspecialFeturamento) {
		this.tipoSituacaoEspecialFeturamento = Util
				.verificarNuloInt(tipoSituacaoEspecialFeturamento);
		// Daniel
		// System.out.println("TipoSituacaoEspecialFaturamento: " +
		// this.tipoSituacaoEspecialFeturamento);
	}

	public int getIdAnormalidadeConsumoSemLeitura() {
		return idAnormalidadeConsumoSemLeitura;
	}

	public void setIdAnormalidadeConsumoSemLeitura(
			String idAnormalidadeConsumoSemLeitura) {
		this.idAnormalidadeConsumoSemLeitura = Util
				.verificarNuloInt(idAnormalidadeConsumoSemLeitura);
	}

	public int getIdAnormalidadeConsumoComLeitura() {
		return idAnormalidadeConsumoComLeitura;
	}

	public void setIdAnormalidadeConsumoComLeitura(
			String idAnormalidadeConsumoComLeitura) {
		this.idAnormalidadeConsumoComLeitura = Util
				.verificarNuloInt(idAnormalidadeConsumoComLeitura);
	}

	public int getIdAnormalidadeLeituraSemLeitura() {
		return idAnormalidadeLeituraSemLeitura;
	}

	public void setIdAnormalidadeLeituraSemLeitura(
			String idAnormalidadeLeituraSemLeitura) {
		this.idAnormalidadeLeituraSemLeitura = Util
				.verificarNuloInt(idAnormalidadeLeituraSemLeitura);
	}

	public int getIdAnormalidadeLeituraComLeitura() {
		return idAnormalidadeLeituraComLeitura;
	}

	public void setIdAnormalidadeLeituraComLeitura(
			String idAnormalidadeLeituraComLeitura) {
		this.idAnormalidadeLeituraComLeitura = Util
				.verificarNuloInt(idAnormalidadeLeituraComLeitura);
	}

	public int getConsumoAguaMedidoHistoricoFaturamento() {
		return consumoAguaMedidoHistoricoFaturamento;
	}

	public void setConsumoAguaMedidoHistoricoFaturamento(
			String consumoAguaMedidoHistoricoFaturamento) {
		this.consumoAguaMedidoHistoricoFaturamento = Util
				.verificarNuloInt(consumoAguaMedidoHistoricoFaturamento);
	}

	public int getConsumoAguaNaoMedidoHistoricoFaturamento() {
		return consumoAguaNaoMedidoHistoricoFaturamento;
	}

	public void setConsumoAguaNaoMedidoHistoricoFaturamento(
			String consumoAguaNaoMedidoHistoricoFaturamento) {
		this.consumoAguaNaoMedidoHistoricoFaturamento = Util
				.verificarNuloInt(consumoAguaNaoMedidoHistoricoFaturamento);
	}

	public int getVolumeEsgotoNaoMedidoHistoricoFaturamento() {
		return volumeEsgotoNaoMedidoHistoricoFaturamento;
	}

	public void setVolumeEsgotoNaoMedidoHistoricoFaturamento(
			String volumeEsgotoNaoMedidoHistoricoFaturamento) {
		this.volumeEsgotoNaoMedidoHistoricoFaturamento = Util
				.verificarNuloInt(volumeEsgotoNaoMedidoHistoricoFaturamento);
	}

	public int getVolumeEsgotoMedidoHistoricoFaturamento() {
		return volumeEsgotoMedidoHistoricoFaturamento;
	}

	public void setVolumeEsgotoMedidoHistoricoFaturamento(
			String volumeEsgotoMedidoHistoricoFaturamento) {
		this.volumeEsgotoMedidoHistoricoFaturamento = Util
				.verificarNuloInt(volumeEsgotoMedidoHistoricoFaturamento);
	}

	public int getIndcValidaAgua() {
		return indcValidaAgua;
	}

	public void setIndcValidaAgua(String indcValidaAgua) {
		this.indcValidaAgua = Util.verificarNuloInt(indcValidaAgua);
	}

	public int getIndcValidaEsgoto() {
		return indcValidaEsgoto;
	}

	public void setIndcValidaEsgoto(String indcValidaEsgoto) {
		this.indcValidaEsgoto = Util.verificarNuloInt(indcValidaEsgoto);
	}

	public static SituacaoTipo getInstancia() {
		if (SituacaoTipo.instancia == null) {
			SituacaoTipo.instancia = new SituacaoTipo();
		}
		return SituacaoTipo.instancia;
	}

	public void setId(int arg0) {
		this.id = arg0;
	}

	public int getId() {
		return this.id;
	}

}
