package model;

import java.util.Date;

import android.util.Log;

import util.Constantes;
import util.Util;

public class DadosGerais {

	private String tipoRegistro;
	private Date dataReferenciaArrecadacao;
	private String anoMesFaturamento;
	private String codigoEmpresaFebraban;
	private String telefone0800;
	private String cnpjEmpresa;
	private String inscricaoEstadualEmpresa;
	private double valorMinimEmissaoConta;
	private double percentToleranciaRateio;
	private int decrementoMaximoConsumoEconomia;
	private int incrementoMaximoConsumoEconomia;
	private short indcTarifaCatgoria;
	private String login;
	private String senha;

	private Date dataAjusteLeitura;
	private int indicadorAjusteConsumo;
	private int indicadorTransmissaoOffline;
	private String versaoCelular;
	private short indcAtualizarSequencialRota;

	private short indcBloquearReemissaoConta;

	private int qtdDiasAjusteConsumo;
	// Daniel - indicador Rota Dividida
	private int indicadorRotaDividida;

	// Daniel - id da Rota
	private int idRota;

	// Daniel - Indicador de calculo de consumo pela médio do HIDROMETRO.
	private int indicadorCalculoPelaMedia = Constantes.NAO;

	private int moduloVerificadorCodigoBarras;

	private Date dataInicio;

	private Date dataFim;

	public static final short CALCULO_POR_CATEGORA = 1;

	private int id;

    public DadosGerais() {}
    
	public short getIndcBloquearReemissaoConta() {
		return indcBloquearReemissaoConta;
	}

	public void setIndcBloquearReemissaoConta(String indcBloquearReemissaoConta) {
		this.indcBloquearReemissaoConta = Util.verificarNuloShort(indcBloquearReemissaoConta);
	}

	public String getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public Date getDataReferenciaArrecadacao() {
		return dataReferenciaArrecadacao;
	}

	public void setDataReferenciaArrecadacao(String dataReferenciaArrecadacao) {
		this.dataReferenciaArrecadacao = Util.getData(Util.verificarNuloString(dataReferenciaArrecadacao));
	}

	public String getAnoMesFaturamento() {
		return anoMesFaturamento;
	}

	public void setAnoMesFaturamento(String anoMesFaturamento) {
		this.anoMesFaturamento = Util.verificarNuloString(anoMesFaturamento);
	}

	public String getCodigoEmpresaFebraban() {
		return codigoEmpresaFebraban;
	}

	public void setCodigoEmpresaFebraban(String codigoEmpresaFebraban) {
		this.codigoEmpresaFebraban = codigoEmpresaFebraban;
	}

	public String getTelefone0800() {
		return telefone0800;
	}

	public void setTelefone0800(String telefone0800) {
		this.telefone0800 = telefone0800;
	}

	public String getCnpjEmpresa() {
		return cnpjEmpresa;
	}

	public void setCnpjEmpresa(String cnpjEmpresa) {
		this.cnpjEmpresa = cnpjEmpresa;
	}

	public String getInscricaoEstadualEmpresa() {
		return inscricaoEstadualEmpresa;
	}

	public void setInscricaoEstadualEmpresa(String inscricaoEstadualEmpresa) {
		this.inscricaoEstadualEmpresa = inscricaoEstadualEmpresa;
	}

	public double getValorMinimEmissaoConta() {
		return valorMinimEmissaoConta;
	}

	public void setValorMinimEmissaoConta(String valorMinimEmissaoConta) {
		this.valorMinimEmissaoConta = Util.verificarNuloDouble(valorMinimEmissaoConta);
	}

	public double getPercentToleranciaRateio() {
		return percentToleranciaRateio;
	}

	public void setPercentToleranciaRateio(String percentToleranciaRateio) {
		this.percentToleranciaRateio = Util.verificarNuloDouble(percentToleranciaRateio);
	}

	public int getDecrementoMaximoConsumoEconomia() {
		return decrementoMaximoConsumoEconomia;
	}

	public void setDecrementoMaximoConsumoEconomia(String decrementoMaximoConsumoEconomia) {
		this.decrementoMaximoConsumoEconomia = Util.verificarNuloInt(decrementoMaximoConsumoEconomia);
	}

	public int getIncrementoMaximoConsumoEconomia() {
		return incrementoMaximoConsumoEconomia;
	}

	public void setIncrementoMaximoConsumoEconomia(String incrementoMaximoConsumoEconomia) {
		this.incrementoMaximoConsumoEconomia = Util.verificarNuloInt(incrementoMaximoConsumoEconomia);
	}

	public short getIndcTarifaCatgoria() {
		return indcTarifaCatgoria;
	}

	public void setIndcTarifaCatgoria(String indcTarifaCatgoria) {
		this.indcTarifaCatgoria = Util.verificarNuloShort(indcTarifaCatgoria);
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login.trim();
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		if (senha != null)
			this.senha = senha.trim();
		else
			this.senha = senha;
	}

	public Date getDataAjusteLeitura() {
		return dataAjusteLeitura;
	}

	public void setDataAjusteLeitura(String dataAjusteLeitura) {
		this.dataAjusteLeitura = Util.getData(Util.verificarNuloString(dataAjusteLeitura));
	}

	public int getIndicadorAjusteConsumo() {
		return indicadorAjusteConsumo;
	}

	public void setIndicadorAjusteConsumo(String indicadorAjusteConsumo) {
		this.indicadorAjusteConsumo = Util.verificarNuloInt(indicadorAjusteConsumo);
	}

	public int getIndicadorTransmissaoOffline() {
		return indicadorTransmissaoOffline;
	}

	public void setIndicadorTransmissaoOffline(String indicadorTransmissaoOffline) {
		this.indicadorTransmissaoOffline = Util.verificarNuloInt(indicadorTransmissaoOffline);
	}

	public String getVersaoCelular() {
		return versaoCelular;
	}

	public void setVersaoCelular(String versaoCelular) {
		this.versaoCelular = Util.verificarNuloString(versaoCelular);
	}

	public void setIndcAtualizarSequencialRota(String indcAtualizarSequencialRota) {
		this.indcAtualizarSequencialRota = Util.verificarNuloShort(indcAtualizarSequencialRota);
	}

	public short getIndcAtualizarSequencialRota() {
		return indcAtualizarSequencialRota;
	}

	public int getQtdDiasAjusteConsumo() {
		return qtdDiasAjusteConsumo;
	}

	public void setQtdDiasAjusteConsumo(String qtdDiasAjusteConsumo) {
		this.qtdDiasAjusteConsumo = Util.verificarNuloInt(qtdDiasAjusteConsumo);
	}

	// Daniel - get indicador Rota Dividida
	public int getIndicadorRotaDividida() {
		return indicadorRotaDividida;
	}

	// Daniel - set indicador Rota Dividida
	public void setIndicadorRotaDividida(String indicadorRotaDividida) {
		this.indicadorRotaDividida = Util.verificarNuloInt(indicadorRotaDividida);
	}

	// Daniel - get Id de Rota
	public int getIdRota() {
		return idRota;
	}

	// Daniel - set Id de Rota
	public void setIdRota(String idRota) {
		this.idRota = Util.verificarNuloInt(idRota);
	}

	// Daniel - get Indicador de calculo de consumo pela média do Hidrometro.
	public int getIdCalculoMedia() {
		return indicadorCalculoPelaMedia;
	}

	// Daniel - set Indicador de calculo de consumo pela média do Hidrometro.
	public void setIdCalculoMedia(String indicadorCalculoPelaMedia) {
		this.indicadorCalculoPelaMedia = Util.verificarNuloInt(indicadorCalculoPelaMedia);
	}

	public int getModuloVerificadorCodigoBarras() {
		return moduloVerificadorCodigoBarras;
	}

	public void setModuloVerificadorCodigoBarras(String moduloVerificadorCodigoBarras) {
		this.moduloVerificadorCodigoBarras = Util.verificarNuloInt(moduloVerificadorCodigoBarras);
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = Util.getData(Util.verificarNuloString(dataInicio));
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = Util.getData(Util.verificarNuloString(dataFim));
	}

	public int getIndicadorCalculoPelaMedia() {
		return indicadorCalculoPelaMedia;
	}

	public void setIndicadorCalculoPelaMedia(int indicadorCalculoPelaMedia) {
		this.indicadorCalculoPelaMedia = indicadorCalculoPelaMedia;
	}

	public static short getCalculoPorCategora() {
		return CALCULO_POR_CATEGORA;
	}
	
}
