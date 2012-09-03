package model;

import helper.EfetuarRateioConsumoDispositivoMovelHelper;

import java.util.Date;

import ui.FileManager;
import util.Constantes;
import util.Util;
import android.util.Log;
import business.ControladorImovel;

public class Imovel {

	private long id;
	private int matricula;
	private String nomeEscritorio;
	private String nomeGerenciaRegional;
	private String nomeUsuario;
	private Date dataVencimento;
	private Date dataValidadeConta;
	private String inscricao;
	private String localidade;
	private String setor;
	private String quadra;
	private String lote;
	private String sublote;
	private String endereco;
	private String anoMesConta;
	private int digitoVerificadorConta;
	private String codigoResponsavel;
	private String nomeResponsavel;
	private String enderecoEntrega;
	private String situacaoLigAgua;
	private String situacaoLigEsgoto;
	private String descricaoBanco;
	private String codigoAgencia;
	private int matriculaCondominio;
	private int indcCondominio;
	private String codigoPerfil;
	private int consumoMedio;
	private int indcFaturamentoAgua;
	private int indcFaturamentoEsgoto;
	private int indcEmissaoConta;
	private int consumoMinAgua;
	private int consumoMinEsgoto;
	private double percentColetaEsgoto;
	private double percentCobrancaEsgoto;
	private int tipoPoco;
	private int codigoTarifa;
	private int consumoEstouro;
	private int altoConsumo;
	private int baixoConsumo;
	private double fatorMultEstouro;
	private double fatorMultMediaAltoConsumo;
	private double percentBaixoConsumo;
	private int consumoMaximo;
	private int grupoFaturamento;
	private int codigoRota;
	private int numeroConta;
	private int tipoCalculoTarifa;
	private String enderecoAtendimento;
	private String telefoneLocalidadeDDD;
	private String sequencialRota;
	private String mensagemConta1;
	private String mensagemConta2;
	private String mensagemConta3;
	private String turbidezPadrao;
	private String phPadrao;
	private String corPadrao;
	private String cloroPadrao;
	private String fluorPadrao;
	private String ferroPadrao;
	private String coliformesTotaisPadrao;
	private String coliformesFecaisPadrao;
	private String nitratoPadrao;
	private String coliformesTermoTolerantesPadrao;
	private int amReferenciaQualidadeAgua;
	private double numeroCloroResidual;
	private double numeroTurbidez;
	private double numeroPh;
	private double numeroCor;
	private double numeroFluor;
	private double numeroFerro;
	private double numeroColiformesTotais;
	private double numeroColiformesFecais;
	private double numeroNitrato;
	private double numeroColiformesTermoTolerantes;
	private String descricaoFonteCapacitacao;
	private int quantidadeTurbidezExigidas;
	private int quantidadeCorExigidas;
	private int quantidadeCloroExigidas;
	private int quantidadeFluorExigidas;
	private int quantidadeColiformesTotaisExigidas;
	private int quantidadeColiformesFecaisExigidas;
	private int quantidadeColiformesTermoTolerantesExigidas;
	private int quantidadeTurbidezAnalisadas;
	private int quantidadeCorAnalisadas;
	private int quantidadeCloroAnalisadas;
	private int quantidadeFluorAnalisadas;
	private int quantidadeColiformesTotaisAnalisadas;
	private int quantidadeColiformesFecaisAnalisadas;
	private int quantidadeColiformesTermoTolerantesAnalisadas;
	private int quantidadeTurbidezConforme;
	private int quantidadeCorConforme;
	private int quantidadeCloroConforme;
	private int quantidadeFluorConforme;
	private int quantidadeColiformesTotaisConforme;
	private int quantidadeColiformesFecaisConforme;
	private int quantidadeColiformesTermoTolerantesConforme;
	private int consumoMinimoImovel;
	private int consumoMinimoImovelNaoMedido;
	private String numeroDocumentoNotificacaoDebito;
	private String numeroCodigoBarraNotificacaoDebito;
	private String cpfCnpjCliente;
	private Date dataLeituraAnteriorNaoMedido;
	private short indicadorAbastecimentoAgua;
	private short indicadorImovelSazonal;
	private int indicadorParalizarFaturamentoAgua = Constantes.NAO;
	private int indicadorParalizarFaturamentoEsgoto = Constantes.NAO;
	private int opcaoDebitoAutomatico = Constantes.NULO_INT;
	private double percentualAlternativoEsgoto;
	private int consumoPercentualAlternativoEsgoto;
	private Date dataEmissaoDocumento;

	// ================= Estao no banco, mas nao estao na rota (.txt)
	// ===================
	private int quantidadeContasImpressas = 0;
	private int contagemValidacaoAgua;
	private int contagemValidacaoPoco;

	private int leituraGravadaAnterior;
	private int anormalidadeGravadaAnterior;
	private Date dataImpressaoNaoMedido;
	private double valorResidualCredito;
	private int quantidadeImoveisCondominio;

	private boolean indcAdicionouDadosIniciaisHelperRateio = false;
	private double valorRateioAgua;
	private double valorRateioEsgoto;
	private int consumoRateioAgua;
	private int consumoRateioEsgoto;
	// ====================================================================================

	// ===== Nao estao no banco de dados!! =========
	private Consumo consumoEsgoto;
	private Consumo consumoAgua;
	private SituacaoTipo situacaoTipo;
	private EfetuarRateioConsumoDispositivoMovelHelper efetuarRateioConsumoDispositivoMovelHelper;
	private int indcImovelImpresso = Constantes.NAO;
	private int indcImovelCalculado = Constantes.NAO;
	private int indcGeracao = Constantes.SIM;
	private int anormalidadeSemHidrometro = Constantes.NULO_INT;
	private int indcImovelContado = Constantes.NAO;
	private int indcImovelEnviado = Constantes.NAO;
	private int idImovelCondominio = Constantes.NULO_INT;
	private int sequencialRotaMarcacao = Constantes.NULO_INT;
	private String mensagemEstouroConsumo1;
	private String mensagemEstouroConsumo2;
	private String mensagemEstouroConsumo3;

	// =============================================
	
	

	
	public double getValorRateioAgua() {
		return valorRateioAgua;
	}

	public String getInscricao() {
		return inscricao;
	}

	public void setInscricao(String inscricao) {
		this.inscricao = inscricao;
	}

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

	public String getNomeGerenciaRegional() {
		return nomeGerenciaRegional;
	}

	public void setNomeGerenciaRegional(String nomeGerenciaRegional) {
		this.nomeGerenciaRegional = nomeGerenciaRegional;
	}

	public String getNomeEscritorio() {
		return nomeEscritorio;
	}

	public void setNomeEscritorio(String nomeEscritorio) {
		this.nomeEscritorio = nomeEscritorio;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataValidadeConta() {
		return dataValidadeConta;
	}

	public void setDataValidadeConta(Date dataValidadeConta) {
		this.dataValidadeConta = dataValidadeConta;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public String getQuadra() {
		return quadra;
	}

	public void setQuadra(String quadra) {
		this.quadra = quadra;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getSublote() {
		return sublote;
	}

	public void setSublote(String sublote) {
		this.sublote = sublote;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getAnoMesConta() {
		return anoMesConta;
	}

	public void setAnoMesConta(String anoMesConta) {
		this.anoMesConta = anoMesConta;
	}

	public int getDigitoVerificadorConta() {
		return digitoVerificadorConta;
	}

	public void setDigitoVerificadorConta(int digitoVerificadorConta) {
		this.digitoVerificadorConta = digitoVerificadorConta;
	}

	public String getCodigoResponsavel() {
		return codigoResponsavel;
	}

	public void setCodigoResponsavel(String codigoResponsavel) {
		this.codigoResponsavel = codigoResponsavel;
	}

	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(String enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public String getSituacaoLigAgua() {
		return situacaoLigAgua;
	}
	
	public String getSituacaoLigAguaString() {
		return situacaoLigAgua.equals(Constantes.LIGADO) ? "Ligado" : "Desligado";
	}

	public void setSituacaoLigAgua(String situacaoLigAgua) {
		this.situacaoLigAgua = situacaoLigAgua;
	}

	public String getSituacaoLigEsgoto() {
		return situacaoLigEsgoto;
	}
	
	public String getSituacaoLigEsgotoString() {
		return situacaoLigEsgoto.equals(Constantes.LIGADO) ? "Ligado" : "Desligado";
	}

	public void setSituacaoLigEsgoto(String situacaoLigEsgoto) {
		this.situacaoLigEsgoto = situacaoLigEsgoto;
	}

	public String getDescricaoBanco() {
		return descricaoBanco;
	}

	public void setDescricaoBanco(String descricaoBanco) {
		this.descricaoBanco = descricaoBanco;
	}

	public String getCodigoAgencia() {
		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}

	public int getMatriculaCondominio() {
		return matriculaCondominio;
	}

	public void setMatriculaCondominio(int matriculaCondominio) {
		this.matriculaCondominio = matriculaCondominio;
	}

	public int getIndcCondominio() {
		return indcCondominio;
	}

	public void setIndcCondominio(int indcCondominio) {
		this.indcCondominio = indcCondominio;
	}

	public String getCodigoPerfil() {
		return codigoPerfil;
	}

	public void setCodigoPerfil(String codigoPerfil) {
		this.codigoPerfil = codigoPerfil;
	}

	public int getConsumoMedio() {
		return consumoMedio;
	}

	public void setConsumoMedio(int consumoMedio) {
		this.consumoMedio = consumoMedio;
	}

	public int getIndcFaturamentoAgua() {
		return indcFaturamentoAgua;
	}

	public void setIndcFaturamentoAgua(int indcFaturamentoAgua) {
		this.indcFaturamentoAgua = indcFaturamentoAgua;
	}

	public int getIndcFaturamentoEsgoto() {
		return indcFaturamentoEsgoto;
	}

	public void setIndcFaturamentoEsgoto(int indcFaturamentoEsgoto) {
		this.indcFaturamentoEsgoto = indcFaturamentoEsgoto;
	}

	public int getIndcEmissaoConta() {
		return indcEmissaoConta;
	}

	public void setIndcEmissaoConta(int indcEmissaoConta) {
		this.indcEmissaoConta = indcEmissaoConta;
	}

	public int getConsumoMinAgua() {
		return consumoMinAgua;
	}

	public void setConsumoMinAgua(int consumoMinAgua) {
		this.consumoMinAgua = consumoMinAgua;
	}

	public int getConsumoMinEsgoto() {
		return consumoMinEsgoto;
	}

	public void setConsumoMinEsgoto(int consumoMinEsgoto) {
		this.consumoMinEsgoto = consumoMinEsgoto;
	}

	public double getPercentColetaEsgoto() {
		return percentColetaEsgoto;
	}

	public void setPercentColetaEsgoto(double percentColetaEsgoto) {
		this.percentColetaEsgoto = percentColetaEsgoto;
	}

	public double getPercentCobrancaEsgoto() {
		return percentCobrancaEsgoto;
	}

	public void setPercentCobrancaEsgoto(double percentCobrancaEsgoto) {
		this.percentCobrancaEsgoto = percentCobrancaEsgoto;
	}

	public int getTipoPoco() {
		return tipoPoco;
	}

	public void setTipoPoco(int tipoPoco) {
		this.tipoPoco = tipoPoco;
	}

	public int getCodigoTarifa() {
		return codigoTarifa;
	}

	public void setCodigoTarifa(int codigoTarifa) {
		this.codigoTarifa = codigoTarifa;
	}

	public int getConsumoEstouro() {
		return consumoEstouro;
	}

	public void setConsumoEstouro(int consumoEstouro) {
		this.consumoEstouro = consumoEstouro;
	}

	public int getAltoConsumo() {
		return altoConsumo;
	}

	public void setAltoConsumo(int altoConsumo) {
		this.altoConsumo = altoConsumo;
	}

	public int getBaixoConsumo() {
		return baixoConsumo;
	}

	public void setBaixoConsumo(int baixoConsumo) {
		this.baixoConsumo = baixoConsumo;
	}

	public double getFatorMultEstouro() {
		return fatorMultEstouro;
	}

	public void setFatorMultEstouro(double fatorMultEstouro) {
		this.fatorMultEstouro = fatorMultEstouro;
	}

	public double getFatorMultMediaAltoConsumo() {
		return fatorMultMediaAltoConsumo;
	}

	public void setFatorMultMediaAltoConsumo(double fatorMultMediaAltoConsumo) {
		this.fatorMultMediaAltoConsumo = fatorMultMediaAltoConsumo;
	}

	public double getPercentBaixoConsumo() {
		return percentBaixoConsumo;
	}

	public void setPercentBaixoConsumo(double percentBaixoConsumo) {
		this.percentBaixoConsumo = percentBaixoConsumo;
	}

	public int getConsumoMaximo() {
		return consumoMaximo;
	}

	public void setConsumoMaximo(int consumoMaximo) {
		this.consumoMaximo = consumoMaximo;
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

	public int getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(int numeroConta) {
		this.numeroConta = numeroConta;
	}

	public int getTipoCalculoTarifa() {
		return tipoCalculoTarifa;
	}

	public void setTipoCalculoTarifa(int tipoCalculoTarifa) {
		this.tipoCalculoTarifa = tipoCalculoTarifa;
	}

	public String getEnderecoAtendimento() {
		return enderecoAtendimento;
	}

	public void setEnderecoAtendimento(String enderecoAtendimento) {
		this.enderecoAtendimento = enderecoAtendimento;
	}

	public String getTelefoneLocalidadeDDD() {
		return telefoneLocalidadeDDD;
	}

	public void setTelefoneLocalidadeDDD(String telefoneLocalidadeDDD) {
		this.telefoneLocalidadeDDD = telefoneLocalidadeDDD;
	}

	public String getSequencialRota() {
		return sequencialRota;
	}

	public void setSequencialRota(String sequencialRota) {
		this.sequencialRota = sequencialRota;
	}

	public String getMensagemConta1() {
		return mensagemConta1;
	}

	public void setMensagemConta1(String mensagemConta1) {
		this.mensagemConta1 = mensagemConta1;
	}

	public String getMensagemConta2() {
		return mensagemConta2;
	}

	public void setMensagemConta2(String mensagemConta2) {
		this.mensagemConta2 = mensagemConta2;
	}

	public String getMensagemConta3() {
		return mensagemConta3;
	}

	public void setMensagemConta3(String mensagemConta3) {
		this.mensagemConta3 = mensagemConta3;
	}

	public String getTurbidezPadrao() {
		return turbidezPadrao;
	}

	public void setTurbidezPadrao(String turbidezPadrao) {
		this.turbidezPadrao = turbidezPadrao;
	}

	public String getPhPadrao() {
		return phPadrao;
	}

	public void setPhPadrao(String phPadrao) {
		this.phPadrao = phPadrao;
	}

	public String getCorPadrao() {
		return corPadrao;
	}

	public void setCorPadrao(String corPadrao) {
		this.corPadrao = corPadrao;
	}

	public String getCloroPadrao() {
		return cloroPadrao;
	}

	public void setCloroPadrao(String cloroPadrao) {
		this.cloroPadrao = cloroPadrao;
	}

	public String getFluorPadrao() {
		return fluorPadrao;
	}

	public void setFluorPadrao(String fluorPadrao) {
		this.fluorPadrao = fluorPadrao;
	}

	public String getFerroPadrao() {
		return ferroPadrao;
	}

	public void setFerroPadrao(String ferroPadrao) {
		this.ferroPadrao = ferroPadrao;
	}

	public String getColiformesTotaisPadrao() {
		return coliformesTotaisPadrao;
	}

	public void setColiformesTotaisPadrao(String coliformesTotaisPadrao) {
		this.coliformesTotaisPadrao = coliformesTotaisPadrao;
	}

	public String getColiformesFecaisPadrao() {
		return coliformesFecaisPadrao;
	}

	public void setColiformesFecaisPadrao(String coliformesFecaisPadrao) {
		this.coliformesFecaisPadrao = coliformesFecaisPadrao;
	}

	public String getNitratoPadrao() {
		return nitratoPadrao;
	}

	public void setNitratoPadrao(String nitratoPadrao) {
		this.nitratoPadrao = nitratoPadrao;
	}

	public String getColiformesTermoTolerantesPadrao() {
		return coliformesTermoTolerantesPadrao;
	}

	public void setColiformesTermoTolerantesPadrao(
			String coliformesTermoTolerantesPadrao) {
		this.coliformesTermoTolerantesPadrao = coliformesTermoTolerantesPadrao;
	}

	public int getAmReferenciaQualidadeAgua() {
		return amReferenciaQualidadeAgua;
	}

	public void setAmReferenciaQualidadeAgua(int amReferenciaQualidadeAgua) {
		this.amReferenciaQualidadeAgua = amReferenciaQualidadeAgua;
	}

	public double getNumeroCloroResidual() {
		return numeroCloroResidual;
	}

	public void setNumeroCloroResidual(double numeroCloroResidual) {
		this.numeroCloroResidual = numeroCloroResidual;
	}

	public double getNumeroTurbidez() {
		return numeroTurbidez;
	}

	public void setNumeroTurbidez(double numeroTurbidez) {
		this.numeroTurbidez = numeroTurbidez;
	}

	public double getNumeroPh() {
		return numeroPh;
	}

	public void setNumeroPh(double numeroPh) {
		this.numeroPh = numeroPh;
	}

	public double getNumeroCor() {
		return numeroCor;
	}

	public void setNumeroCor(double numeroCor) {
		this.numeroCor = numeroCor;
	}

	public double getNumeroFluor() {
		return numeroFluor;
	}

	public void setNumeroFluor(double numeroFluor) {
		this.numeroFluor = numeroFluor;
	}

	public double getNumeroFerro() {
		return numeroFerro;
	}

	public void setNumeroFerro(double numeroFerro) {
		this.numeroFerro = numeroFerro;
	}

	public double getNumeroColiformesTotais() {
		return numeroColiformesTotais;
	}

	public void setNumeroColiformesTotais(double numeroColiformesTotais) {
		this.numeroColiformesTotais = numeroColiformesTotais;
	}

	public double getNumeroColiformesFecais() {
		return numeroColiformesFecais;
	}

	public void setNumeroColiformesFecais(double numeroColiformesFecais) {
		this.numeroColiformesFecais = numeroColiformesFecais;
	}

	public double getNumeroNitrato() {
		return numeroNitrato;
	}

	public void setNumeroNitrato(double numeroNitrato) {
		this.numeroNitrato = numeroNitrato;
	}

	public double getNumeroColiformesTermoTolerantes() {
		return numeroColiformesTermoTolerantes;
	}

	public void setNumeroColiformesTermoTolerantes(
			double numeroColiformesTermoTolerantes) {
		this.numeroColiformesTermoTolerantes = numeroColiformesTermoTolerantes;
	}

	public String getDescricaoFonteCapacitacao() {
		return descricaoFonteCapacitacao;
	}

	public void setDescricaoFonteCapacitacao(String descricaoFonteCapacitacao) {
		this.descricaoFonteCapacitacao = descricaoFonteCapacitacao;
	}

	public int getQuantidadeTurbidezExigidas() {
		return quantidadeTurbidezExigidas;
	}

	public void setQuantidadeTurbidezExigidas(int quantidadeTurbidezExigidas) {
		this.quantidadeTurbidezExigidas = quantidadeTurbidezExigidas;
	}

	public int getQuantidadeCorExigidas() {
		return quantidadeCorExigidas;
	}

	public void setQuantidadeCorExigidas(int quantidadeCorExigidas) {
		this.quantidadeCorExigidas = quantidadeCorExigidas;
	}

	public int getQuantidadeCloroExigidas() {
		return quantidadeCloroExigidas;
	}

	public void setQuantidadeCloroExigidas(int quantidadeCloroExigidas) {
		this.quantidadeCloroExigidas = quantidadeCloroExigidas;
	}

	public int getQuantidadeFluorExigidas() {
		return quantidadeFluorExigidas;
	}

	public void setQuantidadeFluorExigidas(int quantidadeFluorExigidas) {
		this.quantidadeFluorExigidas = quantidadeFluorExigidas;
	}

	public int getQuantidadeColiformesTotaisExigidas() {
		return quantidadeColiformesTotaisExigidas;
	}

	public void setQuantidadeColiformesTotaisExigidas(
			int quantidadeColiformesTotaisExigidas) {
		this.quantidadeColiformesTotaisExigidas = quantidadeColiformesTotaisExigidas;
	}

	public int getQuantidadeColiformesFecaisExigidas() {
		return quantidadeColiformesFecaisExigidas;
	}

	public void setQuantidadeColiformesFecaisExigidas(
			int quantidadeColiformesFecaisExigidas) {
		this.quantidadeColiformesFecaisExigidas = quantidadeColiformesFecaisExigidas;
	}

	public int getQuantidadeColiformesTermoTolerantesExigidas() {
		return quantidadeColiformesTermoTolerantesExigidas;
	}

	public void setQuantidadeColiformesTermoTolerantesExigidas(
			int quantidadeColiformesTermoTolerantesExigidas) {
		this.quantidadeColiformesTermoTolerantesExigidas = quantidadeColiformesTermoTolerantesExigidas;
	}

	public int getQuantidadeTurbidezAnalisadas() {
		return quantidadeTurbidezAnalisadas;
	}

	public void setQuantidadeTurbidezAnalisadas(int quantidadeTurbidezAnalisadas) {
		this.quantidadeTurbidezAnalisadas = quantidadeTurbidezAnalisadas;
	}

	public int getQuantidadeCorAnalisadas() {
		return quantidadeCorAnalisadas;
	}

	public void setQuantidadeCorAnalisadas(int quantidadeCorAnalisadas) {
		this.quantidadeCorAnalisadas = quantidadeCorAnalisadas;
	}

	public int getQuantidadeCloroAnalisadas() {
		return quantidadeCloroAnalisadas;
	}

	public void setQuantidadeCloroAnalisadas(int quantidadeCloroAnalisadas) {
		this.quantidadeCloroAnalisadas = quantidadeCloroAnalisadas;
	}

	public int getQuantidadeFluorAnalisadas() {
		return quantidadeFluorAnalisadas;
	}

	public void setQuantidadeFluorAnalisadas(int quantidadeFluorAnalisadas) {
		this.quantidadeFluorAnalisadas = quantidadeFluorAnalisadas;
	}

	public int getQuantidadeColiformesTotaisAnalisadas() {
		return quantidadeColiformesTotaisAnalisadas;
	}

	public void setQuantidadeColiformesTotaisAnalisadas(
			int quantidadeColiformesTotaisAnalisadas) {
		this.quantidadeColiformesTotaisAnalisadas = quantidadeColiformesTotaisAnalisadas;
	}

	public int getQuantidadeColiformesFecaisAnalisadas() {
		return quantidadeColiformesFecaisAnalisadas;
	}

	public void setQuantidadeColiformesFecaisAnalisadas(
			int quantidadeColiformesFecaisAnalisadas) {
		this.quantidadeColiformesFecaisAnalisadas = quantidadeColiformesFecaisAnalisadas;
	}

	public int getQuantidadeColiformesTermoTolerantesAnalisadas() {
		return quantidadeColiformesTermoTolerantesAnalisadas;
	}

	public void setQuantidadeColiformesTermoTolerantesAnalisadas(
			int quantidadeColiformesTermoTolerantesAnalisadas) {
		this.quantidadeColiformesTermoTolerantesAnalisadas = quantidadeColiformesTermoTolerantesAnalisadas;
	}

	public int getQuantidadeTurbidezConforme() {
		return quantidadeTurbidezConforme;
	}

	public void setQuantidadeTurbidezConforme(int quantidadeTurbidezConforme) {
		this.quantidadeTurbidezConforme = quantidadeTurbidezConforme;
	}

	public int getQuantidadeCorConforme() {
		return quantidadeCorConforme;
	}

	public void setQuantidadeCorConforme(int quantidadeCorConforme) {
		this.quantidadeCorConforme = quantidadeCorConforme;
	}

	public int getQuantidadeCloroConforme() {
		return quantidadeCloroConforme;
	}

	public void setQuantidadeCloroConforme(int quantidadeCloroConforme) {
		this.quantidadeCloroConforme = quantidadeCloroConforme;
	}

	public int getQuantidadeFluorConforme() {
		return quantidadeFluorConforme;
	}

	public void setQuantidadeFluorConforme(int quantidadeFluorConforme) {
		this.quantidadeFluorConforme = quantidadeFluorConforme;
	}

	public int getQuantidadeColiformesTotaisConforme() {
		return quantidadeColiformesTotaisConforme;
	}

	public void setQuantidadeColiformesTotaisConforme(
			int quantidadeColiformesTotaisConforme) {
		this.quantidadeColiformesTotaisConforme = quantidadeColiformesTotaisConforme;
	}

	public int getQuantidadeColiformesFecaisConforme() {
		return quantidadeColiformesFecaisConforme;
	}

	public void setQuantidadeColiformesFecaisConforme(
			int quantidadeColiformesFecaisConforme) {
		this.quantidadeColiformesFecaisConforme = quantidadeColiformesFecaisConforme;
	}

	public int getQuantidadeColiformesTermoTolerantesConforme() {
		return quantidadeColiformesTermoTolerantesConforme;
	}

	public void setQuantidadeColiformesTermoTolerantesConforme(
			int quantidadeColiformesTermoTolerantesConforme) {
		this.quantidadeColiformesTermoTolerantesConforme = quantidadeColiformesTermoTolerantesConforme;
	}

	public int getConsumoMinimoImovel() {
		return consumoMinimoImovel;
	}

	public void setConsumoMinimoImovel(int consumoMinimoImovel) {
		this.consumoMinimoImovel = consumoMinimoImovel;
	}

	public int getConsumoMinimoImovelNaoMedido() {
		return consumoMinimoImovelNaoMedido;
	}

	public void setConsumoMinimoImovelNaoMedido(int consumoMinimoImovelNaoMedido) {
		this.consumoMinimoImovelNaoMedido = consumoMinimoImovelNaoMedido;
	}

	public String getCpfCnpjCliente() {
		return cpfCnpjCliente;
	}

	public void setCpfCnpjCliente(String cpfCnpjCliente) {
		this.cpfCnpjCliente = cpfCnpjCliente;
	}

	public Date getDataLeituraAnteriorNaoMedido() {
		return dataLeituraAnteriorNaoMedido;
	}

	public void setDataLeituraAnteriorNaoMedido(Date dataLeituraAnteriorNaoMedido) {
		this.dataLeituraAnteriorNaoMedido = dataLeituraAnteriorNaoMedido;
	}

	public short getIndicadorAbastecimentoAgua() {
		return indicadorAbastecimentoAgua;
	}

	public void setIndicadorAbastecimentoAgua(short indicadorAbastecimentoAgua) {
		this.indicadorAbastecimentoAgua = indicadorAbastecimentoAgua;
	}

	public short getIndicadorImovelSazonal() {
		return indicadorImovelSazonal;
	}

	public void setIndicadorImovelSazonal(short indicadorImovelSazonal) {
		this.indicadorImovelSazonal = indicadorImovelSazonal;
	}

	public int getIndicadorParalizarFaturamentoAgua() {
		return indicadorParalizarFaturamentoAgua;
	}

	public void setIndicadorParalizarFaturamentoAgua(
			int indicadorParalizarFaturamentoAgua) {
		this.indicadorParalizarFaturamentoAgua = indicadorParalizarFaturamentoAgua;
	}

	public int getIndicadorParalizarFaturamentoEsgoto() {
		return indicadorParalizarFaturamentoEsgoto;
	}

	public void setIndicadorParalizarFaturamentoEsgoto(
			int indicadorParalizarFaturamentoEsgoto) {
		this.indicadorParalizarFaturamentoEsgoto = indicadorParalizarFaturamentoEsgoto;
	}

	public int getOpcaoDebitoAutomatico() {
		return opcaoDebitoAutomatico;
	}

	public void setOpcaoDebitoAutomatico(int opcaoDebitoAutomatico) {
		this.opcaoDebitoAutomatico = opcaoDebitoAutomatico;
	}

	public double getPercentualAlternativoEsgoto() {
		return percentualAlternativoEsgoto;
	}

	public void setPercentualAlternativoEsgoto(double percentualAlternativoEsgoto) {
		this.percentualAlternativoEsgoto = percentualAlternativoEsgoto;
	}

	public int getConsumoPercentualAlternativoEsgoto() {
		return consumoPercentualAlternativoEsgoto;
	}

	public void setConsumoPercentualAlternativoEsgoto(
			int consumoPercentualAlternativoEsgoto) {
		this.consumoPercentualAlternativoEsgoto = consumoPercentualAlternativoEsgoto;
	}

	public Date getDataEmissaoDocumento() {
		return dataEmissaoDocumento;
	}

	public void setDataEmissaoDocumento(Date dataEmissaoDocumento) {
		this.dataEmissaoDocumento = dataEmissaoDocumento;
	}

	public int getContagemValidacaoAgua() {
		return contagemValidacaoAgua;
	}

	public void setContagemValidacaoAgua(int contagemValidacaoAgua) {
		this.contagemValidacaoAgua = contagemValidacaoAgua;
	}

	public int getContagemValidacaoPoco() {
		return contagemValidacaoPoco;
	}

	public void setContagemValidacaoPoco(int contagemValidacaoPoco) {
		this.contagemValidacaoPoco = contagemValidacaoPoco;
	}

	public Date getDataImpressaoNaoMedido() {
		return dataImpressaoNaoMedido;
	}

	public void setDataImpressaoNaoMedido(Date dataImpressaoNaoMedido) {
		this.dataImpressaoNaoMedido = dataImpressaoNaoMedido;
	}

	public double getValorResidualCredito() {
		return valorResidualCredito;
	}

	public void setValorResidualCredito(double valorResidualCredito) {
		this.valorResidualCredito = valorResidualCredito;
	}

	public int getQuantidadeImoveisCondominio() {
		return quantidadeImoveisCondominio;
	}

	public void setQuantidadeImoveisCondominio(int quantidadeImoveisCondominio) {
		this.quantidadeImoveisCondominio = quantidadeImoveisCondominio;
	}

	public Consumo getConsumoEsgoto() {
		return consumoEsgoto;
	}

	public void setConsumoEsgoto(Consumo consumoEsgoto) {
		this.consumoEsgoto = consumoEsgoto;
	}

	public Consumo getConsumoAgua() {
		return consumoAgua;
	}

	public void setConsumoAgua(Consumo consumoAgua) {
		this.consumoAgua = consumoAgua;
	}

	public SituacaoTipo getSituacaoTipo() {
		return situacaoTipo;
	}

	public void setSituacaoTipo(SituacaoTipo situacaoTipo) {
		this.situacaoTipo = situacaoTipo;
	}

	public int getIndcGeracao() {
		return indcGeracao;
	}

	public void setIndcGeracao(int indcGeracao) {
		this.indcGeracao = indcGeracao;
	}

	public int getAnormalidadeSemHidrometro() {
		return anormalidadeSemHidrometro;
	}

	public void setAnormalidadeSemHidrometro(int anormalidadeSemHidrometro) {
		this.anormalidadeSemHidrometro = anormalidadeSemHidrometro;
	}

	public int getIndcImovelContado() {
		return indcImovelContado;
	}

	public void setIndcImovelContado(int indcImovelContado) {
		this.indcImovelContado = indcImovelContado;
	}

	public int getIdImovelCondominio() {
		return idImovelCondominio;
	}

	public void setIdImovelCondominio(int idImovelCondominio) {
		this.idImovelCondominio = idImovelCondominio;
	}

	public int getSequencialRotaMarcacao() {
		return sequencialRotaMarcacao;
	}

	public void setSequencialRotaMarcacao(int sequencialRotaMarcacao) {
		this.sequencialRotaMarcacao = sequencialRotaMarcacao;
	}

	public String getMensagemEstouroConsumo1() {
		return mensagemEstouroConsumo1;
	}

	public void setMensagemEstouroConsumo1(String mensagemEstouroConsumo1) {
		this.mensagemEstouroConsumo1 = mensagemEstouroConsumo1;
	}

	public String getMensagemEstouroConsumo2() {
		return mensagemEstouroConsumo2;
	}

	public void setMensagemEstouroConsumo2(String mensagemEstouroConsumo2) {
		this.mensagemEstouroConsumo2 = mensagemEstouroConsumo2;
	}

	public String getMensagemEstouroConsumo3() {
		return mensagemEstouroConsumo3;
	}

	public void setMensagemEstouroConsumo3(String mensagemEstouroConsumo3) {
		this.mensagemEstouroConsumo3 = mensagemEstouroConsumo3;
	}

	public boolean isIndcAdicionouDadosIniciaisHelperRateio() {
		return indcAdicionouDadosIniciaisHelperRateio;
	}

	public EfetuarRateioConsumoDispositivoMovelHelper getEfetuarRateioConsumoDispositivoMovelHelper() {
		return efetuarRateioConsumoDispositivoMovelHelper;
	}

	public void setQuantidadeContasImpressas(int quantidadeContasImpressas) {
		this.quantidadeContasImpressas = quantidadeContasImpressas;
	}

	public void setIndcImovelImpresso(int indcImovelImpresso) {
		this.indcImovelImpresso = indcImovelImpresso;
	}

	public void setIndcImovelCalculado(int indcImovelCalculado) {
		this.indcImovelCalculado = indcImovelCalculado;
	}

	public void setValorRateioAgua(double valorRateioAgua) {
		this.valorRateioAgua = valorRateioAgua;
	}

	public int getConsumoRateioAgua() {
		return consumoRateioAgua;
	}

	public void setConsumoRateioAgua(int consumoRateioAgua) {
		this.consumoRateioAgua = consumoRateioAgua;
	}

	public double getValorRateioEsgoto() {
		return valorRateioEsgoto;
	}

	public void setValorRateioEsgoto(double valorRateioEsgoto) {
		this.valorRateioEsgoto = valorRateioEsgoto;
	}

	public int getConsumoRateioEsgoto() {
		return consumoRateioEsgoto;
	}

	public void setConsumoRateioEsgoto(int consumoRateioEsgoto) {
		this.consumoRateioEsgoto = consumoRateioEsgoto;
	}

	public void setIndcAdicionouDadosIniciaisHelperRateio(
			boolean indcAdicionouDadosIniciaisHelperRateio) {
		this.indcAdicionouDadosIniciaisHelperRateio = indcAdicionouDadosIniciaisHelperRateio;
	}

	public void setEfetuarRateioConsumoDispositivoMovelHelper(
			EfetuarRateioConsumoDispositivoMovelHelper efetuarRateioConsumoDispositivoMovelHelper) {
		this.efetuarRateioConsumoDispositivoMovelHelper = efetuarRateioConsumoDispositivoMovelHelper;
	}

	public int getIndcImovelEnviado() {
		return indcImovelEnviado;
	}

	public void setIndcImovelEnviado(int indcImovelEnviado) {
		this.indcImovelEnviado = indcImovelEnviado;
	}

	public int getIndcImovelImpresso() {
		return indcImovelImpresso;
	}

	public int getQuantidadeContasImpressas() {
		return quantidadeContasImpressas;
	}

	public String getNumeroDocumentoNotificacaoDebito() {
		return numeroDocumentoNotificacaoDebito;
	}

	public void setNumeroDocumentoNotificacaoDebito(
			String numeroDocumentoNotificacaoDebito) {
		this.numeroDocumentoNotificacaoDebito = numeroDocumentoNotificacaoDebito;
	}

	public void setNumeroCodigoBarraNotificacaoDebito(
			String numeroCodigoBarraNotificacaoDebito) {
		this.numeroCodigoBarraNotificacaoDebito = numeroCodigoBarraNotificacaoDebito;
	}

	public String getNumeroCodigoBarraNotificacaoDebito() {
		return numeroCodigoBarraNotificacaoDebito;
	}

	public int getIndcImovelCalculado() {
		return indcImovelCalculado;
	}

	public int getAnormalidadeGravadaAnterior() {
		return anormalidadeGravadaAnterior;
	}

	public void setAnormalidadeGravadaAnterior(int anormalidadeGravadaAnterior) {
		this.anormalidadeGravadaAnterior = anormalidadeGravadaAnterior;
	}

	public int getLeituraGravadaAnterior() {
		return leituraGravadaAnterior;
	}

	public void setLeituraGravadaAnterior(int leituraGravadaAnterior) {
		this.leituraGravadaAnterior = leituraGravadaAnterior;
	}

}
