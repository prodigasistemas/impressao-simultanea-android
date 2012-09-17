package model;

import helper.EfetuarRateioConsumoHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import business.ControladorConta;
import business.ControladorImovel;
import android.util.Log;

import ui.FileManager;
import util.Constantes;
import util.Util;

public class Imovel {

	public static final String CODIGO_BONUS_SOCIAL = "11"; 

    public static final int PERFIL_GRANDE = 1;
    public static final int PERFIL_GRANDE_MES = 2;
    public static final int PERFIL_ESPECIAL = 3;
    public static final int PERFIL_BONUS_SOCIAL = 4;
    public static final int PERFIL_NORMAL = 5;
    public static final int PERFIL_CORPORATIVO = 6;
    public static final int PERFIL_GOVERNO_METROPOLITANO = 7;
    public static final int PERFIL_GOVERNO_INTERIOR = 8;
    public static final int PERFIL_CONDOMINIAL = 9;
    public static final int PERFIL_COLABORADOR = 10;
    
    // Situação da ligação de Água
    public final static int POTENCIAL = 1;
    public final static int FACTIVEL = 2;
    public final static int LIGADO = 3;
    public final static int EM_FISCALIZACAO = 4;
    public final static int CORTADO = 5;
    public final static int SUPRIMIDO = 6;
    public final static int SUPR_PARC = 7;
    public final static int SUPR_PARC_PEDIDO = 8;
    public final static int EM_CANCELAMENTO = 9;

    // Situação da ligação de Esgoto
    public final static int LIG_FORA_USO = 5;
    public final static int TAMPONADO = 6;
    public final static int CONVERSAO = 9;



    private long id;
	private int matricula;
    private String nomeGerenciaRegional;
    private String nomeEscritorio;
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
    private int sequencialRota;
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
    
    
    
    // ================= Estao no banco, mas nao estao na rota (.txt) ===================
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
    private String mensagemEstouroConsumo1;
    private String mensagemEstouroConsumo2;
    private String mensagemEstouroConsumo3;
    private int imovelStatus;
    private int imovelEnviado;
    private int indcImovelEnviado = Constantes.NAO;
    private int indcImovelImpresso = Constantes.NAO;
    private int indcGeracao = Constantes.SIM;

    // ====================================================================================

    
  //===== Nao estao no banco de dados!! =========
    private Consumo consumoEsgoto;
    private Consumo consumoAgua;
    private SituacaoTipo situacaoTipo;
    private EfetuarRateioConsumoHelper efetuarRateioConsumoDispositivoMovelHelper;
    private int indcImovelCalculado = Constantes.NAO;
    private int anormalidadeSemHidrometro = Constantes.NULO_INT;
    private int indcImoveldo = Constantes.NAO;
    private int idImovelCondominio = Constantes.NULO_INT;
    private int sequencialRotaMarcacao = Constantes.NULO_INT;
    private int indcImoveisVisitados = 0;

    //=============================================
    
    private List<DadosCategoria> dadosCategoria = new ArrayList<DadosCategoria>();
    private List<HistoricoConsumo> historicoConsumo = new ArrayList<HistoricoConsumo>();
    private List<Debito> debitos = new ArrayList<Debito>();
    private List<Credito> creditos = new ArrayList<Credito>();
    private List<Imposto> impostos = new ArrayList<Imposto>();
    private List<Conta> contas = new ArrayList<Conta>();
    private List<Medidor> medidores = new ArrayList<Medidor>();
    private List<TarifacaoMinima> tarifacoesMinimas = new ArrayList<TarifacaoMinima>();
    private List<TarifacaoComplementar> tarifacoesComplementares = new ArrayList<TarifacaoComplementar>();
    
	public void setImovelStatus(String imovelStatus) {
		this.imovelStatus = Util.verificarNuloInt(imovelStatus);
	}
	
	public void setImovelEnviado(String imovelEnviado) {
		this.imovelEnviado = Util.verificarNuloInt(imovelEnviado);
	}
	
    public boolean isIndcAdicionouDadosIniciaisHelperRateio() {
    	return indcAdicionouDadosIniciaisHelperRateio;
    }

	public int getImovelStatus() {
		return this.imovelStatus;
	}

	public int isImovelEnviado() {
		return this.imovelEnviado;
	}

	public double getValorRateioAgua() {
		return valorRateioAgua;
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

    public void setIndcAdicionouDadosIniciaisHelperRateio(int indcAdicionouDadosIniciaisHelperRateio) {

    	if(indcAdicionouDadosIniciaisHelperRateio == Constantes.SIM){
    		this.indcAdicionouDadosIniciaisHelperRateio = true;
    		
    	}else{
    		this.indcAdicionouDadosIniciaisHelperRateio = false;
    	}
    }

    public void setEfetuarRateioConsumoDispositivoMovelHelper(
	    EfetuarRateioConsumoHelper efetuarRateioConsumoDispositivoMovelHelper) {
	this.efetuarRateioConsumoDispositivoMovelHelper = efetuarRateioConsumoDispositivoMovelHelper;
    }

    public EfetuarRateioConsumoHelper getEfetuarRateioConsumoDispositivoMovelHelper() {

	if (this.indcCondominio == Constantes.SIM || this.idImovelCondominio != Constantes.NULO_INT) {

	    long idImovelMacro = Constantes.NULO_INT;

	    if (this.indcCondominio == Constantes.SIM) {
	    	idImovelMacro = this.getId();
	    
	    } else {
	    	idImovelMacro = this.getIdImovelCondominio();
	    }

//	    if (this.efetuarRateioConsumoDispositivoMovelHelper == null) {
//			this.efetuarRateioConsumoDispositivoMovelHelper = new EfetuarRateioConsumoHelper(
//				idImovelMacro, id + quantidadeImoveisCondominio - 1);
//	
//			// Salvamos as informações obtidas
//			Repositorio.salvarObjeto(this);
//	    }
	}

	return efetuarRateioConsumoDispositivoMovelHelper;
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

//    public Vector getValoresContasImpressas() {
//    	return valoresContasImpressas;
//    }
    
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

    public Imovel() {

    	this.matricula = Constantes.NULO_INT;
    	

    }

    public int getIndcImoveisVisitados() {
	return indcImoveisVisitados;
    }

    public void setIndcImoveisVisitados(int indcImoveisVisitados) {
	this.indcImoveisVisitados = indcImoveisVisitados;
    }

    public int getIndcImovelCalculado() {
	return indcImovelCalculado;
    }

    public void setIndcImovelCalculado(int indcImovelCalculado) {

		this.indcImovelCalculado = indcImovelCalculado;
		
		if (indcImovelCalculado == Constantes.SIM){
		    
		    if ( this.isImovelAlterado() ) {
				this.indcImovelEnviado = Constantes.NAO;
				this.indcImovelImpresso = Constantes.NAO;
		    }
		    
		    // Caso esteja incluido como imóvel a revisitar, remove
		    if ( Configuracao.getInstancia().getMatriculasRevisitar() != null && 
			 !Configuracao.getInstancia().getMatriculasRevisitar().isEmpty() &&
			 Configuracao.getInstancia().getMatriculasRevisitar().contains( this.getMatricula()+"" )) {
			
			int size = Configuracao.getInstancia().getMatriculasRevisitar().size();
			size--;
			
			Configuracao.getInstancia().getMatriculasRevisitar().removeElement( this.getMatricula()+"" );
			Configuracao.getInstancia().getMatriculasRevisitar().setSize( size );
		    }
		    
		} else {	
		    
		    if ( !this.isImovelCondominio() ){	    
//				if ( !Configuracao.getInstancia().getIdsImoveisPendentes().contains( new Integer( id ) ) ){
//					Configuracao.getInstancia().getIdsImoveisPendentes().addElement( new Integer( id ) );
//				}
//				Configuracao.getInstancia().getIdsImoveisConcluidos().removeElement( new Integer( id ) );
//			    
//				// Ordena os vetores
//				Util.bubbleSort( Configuracao.getInstancia().getIdsImoveisConcluidos() );
//				Util.bubbleSort( Configuracao.getInstancia().getIdsImoveisPendentes() );
		    }
		}	
		
//		Repositorio.salvarObjeto( Configuracao.getInstancia() );	
    }

    public int getIndcGeracao() {
	return indcGeracao;
    }

    public void setIndcGeracao(int indcGeracao) {
	this.indcGeracao = indcGeracao;
    }

    public void setIndcImovelImpresso(int imovelImpressao) {
    	this.indcImovelImpresso = imovelImpressao;
	}

    public void setQuantidadeContasImpressas(int quantidadeImpressao) {
    	this.quantidadeContasImpressas = quantidadeImpressao;
    }

    
    public Consumo getConsumoAgua() {
	return consumoAgua;
    }

    public void setConsumoAgua(Consumo consumoAgua) {

	DadosRelatorio dadosRelatorio = DadosRelatorio.getInstancia();

	// anormalidade anterior do imóvel
	int anormalidadeRelatorio = Constantes.NULO_INT;
	int leituraRelatorio = Constantes.NULO_INT;
	int anormalidade = Constantes.NULO_INT;
//	int leitura = Constantes.NULO_INT;

//	Integer id = new Integer(this.id);
	String stringQuadra = Util.adicionarZerosEsquerdaNumero(4, quadra);
	boolean temConsumo = true;

	if ( this.isImovelCondominio() ){
	    /**
	     * Caso seja um imóvel condomínio que foi alterado para leitura e
	     * anormalidade em branco; são feitas as alterações necessárias nos
	     * valores do relatório.
	     */
	    if (consumoAgua == null) {
		dadosRelatorio.idsLidosRelatorio.removeElement(id);
		temConsumo = false;
		if (!dadosRelatorio.idsNaoLidosRelatorio.contains(id)) {
		    dadosRelatorio.idsNaoLidosRelatorio.addElement(id);

		    if (ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) != null) {
			leituraRelatorio = ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(
				Constantes.LIGACAO_AGUA).getLeituraRelatorio();
			anormalidadeRelatorio = ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(
				Constantes.LIGACAO_AGUA)
				.getAnormalidadeRelatorio();

			/**
			 * Caso o imóvel tenha anormalidade antes da alteração,
			 * será retirado uma unidade dos lidos com anormalidade.
			 */
			if (anormalidadeRelatorio != Constantes.NULO_INT && anormalidadeRelatorio != 0) {

			    Util.inserirValoresStringRelatorioConsumoNulo("("
				    + stringQuadra + ")", true, false);

			}

			/**
			 * Caso o imóvel possua anteriormente leitura, é
			 * retirado uma unidade de lidos com leitura.
			 */
			if ((anormalidadeRelatorio == Constantes.NULO_INT || anormalidadeRelatorio == 0)
				&& leituraRelatorio != Constantes.NULO_INT) {

			    Util.inserirValoresStringRelatorioConsumoNulo("("
				    + stringQuadra + ")", false, true);
			}
		    }

		    ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA)
			    .setLeituraRelatorio(Constantes.NULO_INT);
		    ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA)
			    .setAnormalidadeRelatorio(Constantes.NULO_INT);

		}

	    }

	    removerAdicionarIdImovelCondominioLidoHelperResumo(consumoAgua);
	}

	if (dadosRelatorio.idsNaoLidosRelatorio != null) {

	    if (temConsumo && ControladorImovel.getInstancia().getImovelSelecionado().getIndcImovelCalculado() == Constantes.SIM) {

		dadosRelatorio.idsNaoLidosRelatorio.removeElement(id);

		if (ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) != null) {
		    anormalidade = ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA)
			    .getAnormalidade();
		    anormalidadeRelatorio = ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(
			    Constantes.LIGACAO_AGUA).getAnormalidadeRelatorio();
		    leituraRelatorio = ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(
			    Constantes.LIGACAO_AGUA).getLeituraRelatorio();
		}

		if (!dadosRelatorio.idsLidosRelatorio.contains(id)) {
		    dadosRelatorio.idsLidosRelatorio.addElement(id);

		    if (ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) != null) {

			anormalidade = ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(
				Constantes.LIGACAO_AGUA).getAnormalidade();

			/**
			 * Caso o imóvel tenha sido calculado com anormalidade
			 * será inserido como lido com anormalidade nas
			 * informações do relatório
			 */
			if (anormalidade != Constantes.NULO_INT	&& anormalidade != 0) {
			    Util.inserirValoresStringRelatorio("("
				    + stringQuadra + ")", true, false);

			} else {
			    Util.inserirValoresStringRelatorio("("
				    + stringQuadra + ")", false, true);
			}

		    } else {

			/**
			 * Caso o imóvel seja não medido, o mesmo será inserido
			 * como lido com leitura nas informações do relatório
			 */
			if (ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) == null) {
			    Util.inserirValoresStringRelatorio("("
				    + stringQuadra + ")", false, true);
			}
		    }

		} else {

		    // Caso o imovel seja lido com leitura e posteriormente seja
		    // lido novamente colocando uma anormalidade
		    // o mesmo deve ser retirado do relatorio como lido com
		    // leitura e inserido como lido com anormalidade
		    if (ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) != null) {

			if (anormalidade != 0 && anormalidadeRelatorio == 0) {

			    Util.inserirValoresStringRelatorioConsumoNulo("("
				    + stringQuadra + ")", false, true);

			    Util.inserirValoresStringRelatorio("("
				    + stringQuadra + ")", true, false);
			}

			// Caso o imovel seja lido com leitura e posteriormente
			// seja lido novamente colocando uma anormalidade
			// o mesmo deve ser retirado do relatorio como lido com
			// leitura e inserido como lido com anormalidade
			if (anormalidade == 0 && anormalidadeRelatorio != 0) {

			    Util.inserirValoresStringRelatorioConsumoNulo("("
				    + stringQuadra + ")", true, false);

			    Util.inserirValoresStringRelatorio("("
				    + stringQuadra + ")", false, true);
			}

		    }

		}
	    }
	}

	this.consumoAgua = consumoAgua;

//	Repositorio.salvarObjeto(dadosRelatorio);
    }

    public Consumo getConsumoEsgoto() {
	return consumoEsgoto;
    }

    public void setConsumoEsgoto(Consumo consumoEsgoto) {

	DadosRelatorio dadosRelatorio = DadosRelatorio.getInstancia();

	int anormalidade = Constantes.NULO_INT;
	int anormalidadeRelatorio = Constantes.NULO_INT;
	int leituraRelatorio = Constantes.NULO_INT;
//	int leitura = Constantes.NULO_INT;

//	Integer id = new Integer(this.id);
	String stringQuadra = Util.adicionarZerosEsquerdaNumero(4, quadra);
	boolean temConsumo = true;

	if (this.isImovelCondominio()) {
	    /**
	     * Caso seja um imóvel condomínio que foi alterado para leitura e
	     * anormalidade em branco; são feitas as alterações necessárias nos
	     * valores do relatório.
	     */
	    if (consumoEsgoto == null) {
		dadosRelatorio.idsLidosRelatorio.removeElement(id);
		temConsumo = false;
		if (!dadosRelatorio.idsNaoLidosRelatorio.contains(id)) {
		    dadosRelatorio.idsNaoLidosRelatorio.addElement(id);

		    if (ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) != null
			    && ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) == null) {
			leituraRelatorio = ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(
				Constantes.LIGACAO_POCO).getLeituraRelatorio();
			anormalidadeRelatorio = ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(
				Constantes.LIGACAO_POCO)
				.getAnormalidadeRelatorio();

			/**
			 * Caso o imóvel tenha anormalidade antes da alteração,
			 * será retirado uma unidade dos lidos com anormalidade.
			 */
			if (anormalidadeRelatorio != Constantes.NULO_INT && anormalidadeRelatorio != 0 ) {

			    Util.inserirValoresStringRelatorioConsumoNulo("("
				    + stringQuadra + ")", true, false);

			}

			/**
			 * Caso o imóvel possua anteriormente leitura, é
			 * retirado uma unidade de lidos com leitura.
			 */
			if ((anormalidadeRelatorio == Constantes.NULO_INT || anormalidadeRelatorio == 0)
				&& leituraRelatorio != Constantes.NULO_INT) {

			    Util.inserirValoresStringRelatorioConsumoNulo("("
				    + stringQuadra + ")", false, true);
			}
		    }

		    ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO)
			    .setLeituraRelatorio(Constantes.NULO_INT);
		    ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO)
			    .setAnormalidadeRelatorio(Constantes.NULO_INT);

		}

	    }

	    removerAdicionarIdImovelCondominioLidoHelperResumo(consumoEsgoto);
	}

	if (dadosRelatorio.idsNaoLidosRelatorio != null) {

	    if (temConsumo && ControladorImovel.getInstancia().getImovelSelecionado().getIndcImovelCalculado() == Constantes.SIM) {

		dadosRelatorio.idsNaoLidosRelatorio.removeElement(id);

		if (ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) != null) {
		    anormalidade = ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO)
			    .getAnormalidade();
		    anormalidadeRelatorio = ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(
			    Constantes.LIGACAO_POCO).getAnormalidadeRelatorio();
		    leituraRelatorio = ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(
			    Constantes.LIGACAO_POCO).getLeituraRelatorio();
		}

		if (!dadosRelatorio.idsLidosRelatorio.contains(id)) {
		    dadosRelatorio.idsLidosRelatorio.addElement(id);

		    if (ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) != null
			    && ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) == null) {

			anormalidade = ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(
				Constantes.LIGACAO_POCO).getAnormalidade();

			/**
			 * Caso o imóvel tenha sido calculado com anormalidade
			 * será inserido como lido com anormalidade nas
			 * informações do relatório
			 */
			if (anormalidade != Constantes.NULO_INT
				&& anormalidade != 0) {
			    Util.inserirValoresStringRelatorio("("
				    + stringQuadra + ")", true, false);

			} else {
			    Util.inserirValoresStringRelatorio("("
				    + stringQuadra + ")", false, true);
			}

		    } else {
			if (ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) == null
				&& ControladorImovel.getInstancia().getImovelSelecionado().getConsumoAgua() == null) {

			    /**
			     * Caso o imóvel seja não medido, o mesmo será
			     * inserido como lido com leitura nas informações do
			     * relatório
			     */
			    Util.inserirValoresStringRelatorio("("
				    + stringQuadra + ")", false, true);
			}
		    }

		} else {

		    // Caso o imovel seja lido com leitura e posteriormente seja
		    // lido novamente colocando uma anormalidade
		    // o mesmo deve ser retirado do relatorio como lido com
		    // leitura e inserido como lido com anormalidade
		    if (ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) != null
			    && ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) == null) {
			if (anormalidade != 0 && anormalidadeRelatorio == 0) {

			    Util.inserirValoresStringRelatorioConsumoNulo("("
				    + stringQuadra + ")", false, true);

			    Util.inserirValoresStringRelatorio("("
				    + stringQuadra + ")", true, false);
			}

			// Caso o imovel seja lido com leitura e posteriormente
			// seja lido novamente colocando uma anormalidade
			// o mesmo deve ser retirado do relatorio como lido com
			// leitura e inserido como lido com anormalidade
			if (anormalidade == 0 && anormalidadeRelatorio != 0) {

			    Util.inserirValoresStringRelatorioConsumoNulo("("
				    + stringQuadra + ")", true, false);

			    Util.inserirValoresStringRelatorio("("
				    + stringQuadra + ")", false, true);
			}

		    }

		}
	    }
	}

	this.consumoEsgoto = consumoEsgoto;

//	Repositorio.salvarObjeto(dadosRelatorio);
    }

    public void setMatricula(String matricula) {
    	this.matricula = Util.verificarNuloInt(matricula);
    }

    public void setNomeGerenciaRegional(String nomeGerenciaRegional) {
    	this.nomeGerenciaRegional = Util.verificarNuloString(nomeGerenciaRegional);
    }

    public void setNomeEscritorio(String nomeEscritorio) {
    	this.nomeEscritorio = Util.verificarNuloString(nomeEscritorio);
    }

    public void setNomeUsuario(String nomeUsuario) {
    	this.nomeUsuario = Util.verificarNuloString(nomeUsuario);
    }

    public void setDataVencimento(String dataVencimento) {
    	this.dataVencimento = Util.getData(Util.verificarNuloString(dataVencimento));
    }

    public void setDataValidadeConta(String dataValidadeConta) {
    	this.dataValidadeConta = Util.getData(Util.verificarNuloString(dataValidadeConta));
    }

    public void setInscricao(String inscricao) {
		this.inscricao = Util.verificarNuloString(inscricao);
	    
		if (this.inscricao != Constantes.NULO_STRING){
	    	
	    	localidade = inscricao.substring(0, 3);
	    	setor = inscricao.substring(3, 6);
	    	quadra = inscricao.substring(6, 10);
	    	lote = inscricao.substring(10, 14);
	    	sublote = inscricao.substring(14, 17);
	    }
    }

    public void setEndereco(String endereco) {
    	this.endereco = Util.verificarNuloString(endereco);
    }

    public void setAnoMesConta(String anoMesConta) {
    	this.anoMesConta = Util.verificarNuloString(anoMesConta);
    }

    public void setDigitoVerificadorConta(String digitoVerificadorConta) {
    	this.digitoVerificadorConta = Util.verificarNuloInt(digitoVerificadorConta);
    }

    public void setCodigoResponsavel(String codigoResponsavel) {
    	this.codigoResponsavel = Util.verificarNuloString(codigoResponsavel);
    }

    public void setNomeResponsavel(String nomeResponsavel) {
    	this.nomeResponsavel = Util.verificarNuloString(nomeResponsavel);
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
    	this.enderecoEntrega = Util.verificarNuloString(enderecoEntrega);
    }

    public void setSituacaoLigAgua(String situacaoLigAgua) {
    	this.situacaoLigAgua = Util.verificarNuloString(situacaoLigAgua);
    }

    public void setSituacaoLigEsgoto(String situacaoLigEsgoto) {
    	this.situacaoLigEsgoto = Util.verificarNuloString(situacaoLigEsgoto);
    }

    public void setDescricaoBanco(String descricaoBanco) {
    	this.descricaoBanco = Util.verificarNuloString(descricaoBanco);
    }

    public void setCodigoAgencia(String codigoAgencia) {
    	this.codigoAgencia = Util.verificarNuloString(codigoAgencia);
    }

    public void setMatriculaCondominio(String matriculaCondominio) {
    	this.matriculaCondominio = Util.verificarNuloInt(matriculaCondominio);
    }

    public void setIndcCondominio(String indcCondominio) {
    	this.indcCondominio = Util.verificarNuloInt(indcCondominio);
    }

    public void setCodigoPerfil(String codigoPerfil) {
    	this.codigoPerfil = Util.verificarNuloString(codigoPerfil);
    }

    public void setConsumoMedio(String consumoMedio) {
    	this.consumoMedio = Util.verificarNuloInt(consumoMedio);
    }

    public void setIndcFaturamentoAgua(String indcFaturamentoAgua) {
    	this.indcFaturamentoAgua = Util.verificarNuloInt(indcFaturamentoAgua);
    }

    public void setIndcFaturamentoEsgoto(String indcFaturamentoEsgoto) {
    	this.indcFaturamentoEsgoto = Util.verificarNuloInt(indcFaturamentoEsgoto);
    }

    public void setIndcEmissaoConta(String indcEmissaoConta) {
    	this.indcEmissaoConta = Util.verificarNuloInt(indcEmissaoConta);
    }

    public void setConsumoMinAgua(String consumoMinAgua) {
    	this.consumoMinAgua = Util.verificarNuloInt(consumoMinAgua);
    }

    public void setConsumoMinEsgoto(String consumoMinEsgoto) {
    	this.consumoMinEsgoto = Util.verificarNuloInt(consumoMinEsgoto);
    }

    public void setPercentColetaEsgoto(String percentColetaEsgoto) {
    	this.percentColetaEsgoto = Util.verificarNuloDouble(percentColetaEsgoto);
    }

    public void setPercentCobrancaEsgoto(String percentCobrancaEsgoto) {
    	this.percentCobrancaEsgoto = Util.verificarNuloDouble(percentCobrancaEsgoto);
    }

    public void setTipoPoco(String tipoPoco) {
    	this.tipoPoco = Util.verificarNuloInt(tipoPoco);
    }

    public void setCodigoTarifa(String codigoTarifa) {
    	this.codigoTarifa = Util.verificarNuloInt(codigoTarifa);
    }

    public void setConsumoEstouro(String consumoEstouro) {
    	this.consumoEstouro = Util.verificarNuloInt(consumoEstouro);
    }

    public void setAltoConsumo(String altoConsumo) {
    	this.altoConsumo = Util.verificarNuloInt(altoConsumo);
    }

    public void setBaixoConsumo(String baixoConsumo) {
    	this.baixoConsumo = Util.verificarNuloInt(baixoConsumo);
    }

    public void setFatorMultEstouro(String fatorMultEstouro) {
    	this.fatorMultEstouro = Util.verificarNuloDouble(fatorMultEstouro);
    }

    public void setFatorMultMediaAltoConsumo(String fatorMultMediaAltoConsumo) {
    	this.fatorMultMediaAltoConsumo = Util.verificarNuloDouble(fatorMultMediaAltoConsumo);
    }

    public void setPercentBaixoConsumo(String percentBaixoConsumo) {
    	this.percentBaixoConsumo = Util.verificarNuloDouble(percentBaixoConsumo);
    }

    public void setConsumoMaximo(String consumoMaximo) {
    	this.consumoMaximo = Util.verificarNuloInt(consumoMaximo);
    }

    public void setNumeroConta(String numeroConta) {
    	this.numeroConta = Util.verificarNuloInt(numeroConta);
    }

    public void setTipoCalculoTarifa(String tipoCalculoTarifa) {
    	this.tipoCalculoTarifa = Util.verificarNuloInt(tipoCalculoTarifa);
    }

    public void setGrupoFaturamento(String grupoFaturamento) {
    	this.grupoFaturamento = Util.verificarNuloInt(grupoFaturamento);
    }

    public void setCodigoRota(String codigoRota) {
    	this.codigoRota = Util.verificarNuloInt(codigoRota);
    }

    public void setEnderecoAtendimento(String enderecoAtendimento) {
    	this.enderecoAtendimento = Util.verificarNuloString(enderecoAtendimento);
    }

    public void setTelefoneLocalidadeDDD(String telefoneLocalidadeDDD) {
    	this.telefoneLocalidadeDDD = Util.verificarNuloString(telefoneLocalidadeDDD);
    }

    public void setSequencialRota(String sequencialRota) {
    	this.sequencialRota = Util.verificarNuloInt(sequencialRota);
    }

    public void setMensagemConta1(String mensagemConta1) {
    	this.mensagemConta1 = Util.verificarNuloString(mensagemConta1);
    }

    public void setMensagemConta2(String mensagemConta2) {
    	this.mensagemConta2 = Util.verificarNuloString(mensagemConta2);
    }

    public void setMensagemConta3(String mensagemConta3) {
    	this.mensagemConta3 = Util.verificarNuloString(mensagemConta3);
    }

    public void setTurbidezPadrao(String turbidezPadrao) {
    	this.turbidezPadrao = Util.verificarNuloString(turbidezPadrao);
    }

    public void setPhPadrao(String phPadrao) {
    	this.phPadrao = Util.verificarNuloString(phPadrao);
    }

    public void setCorPadrao(String corPadrao) {
    	this.corPadrao = Util.verificarNuloString(corPadrao);
    }

    public void setCloroPadrao(String cloroPadrao) {
    	this.cloroPadrao = Util.verificarNuloString(cloroPadrao);
    }

    public void setFluorPadrao(String fluorPadrao) {
    	this.fluorPadrao = Util.verificarNuloString(fluorPadrao);
    }

    public void setFerroPadrao(String ferroPadrao) {
    	this.ferroPadrao = Util.verificarNuloString(ferroPadrao);
    }

    public void setColiformesTotaisPadrao(String coliformesTotaisPadrao) {
    	this.coliformesTotaisPadrao = Util.verificarNuloString(coliformesTotaisPadrao);
    }

    public void setColiformesFecaisPadrao(String coliformesFecaisPadrao) {
    	this.coliformesFecaisPadrao = Util.verificarNuloString(coliformesFecaisPadrao);
    }

    public void setNitratoPadrao(String nitratoPadrao) {
    	this.nitratoPadrao = Util.verificarNuloString(nitratoPadrao);
    }

    public void setColiformesTermoTolerantesPadrao(String coliformesTermoTolerantesPadrao) {
    	this.coliformesTermoTolerantesPadrao = Util.verificarNuloString(coliformesTermoTolerantesPadrao);
    }

    public void setAmReferenciaQualidadeAgua(String amReferenciaQualidadeAgua) {
    	this.amReferenciaQualidadeAgua = Util.verificarNuloInt(amReferenciaQualidadeAgua);
    }

    public void setNumeroCloroResidual(String numeroCloroResidual) {
    	this.numeroCloroResidual = Util.verificarNuloDouble(numeroCloroResidual);
    }

    public void setNumeroTurbidez(String numeroTurbidez) {
    	this.numeroTurbidez = Util.verificarNuloDouble(numeroTurbidez);
    }

    public void setNumeroPh(String numeroPh) {
    	this.numeroPh = Util.verificarNuloDouble(numeroPh);
    }

    public void setNumeroCor(String numeroCor) {
    	this.numeroCor = Util.verificarNuloDouble(numeroCor);
    }

    public void setNumeroFluor(String numeroFluor) {
    	this.numeroFluor = Util.verificarNuloDouble(numeroFluor);
    }

    public void setNumeroFerro(String numeroFerro) {
    	this.numeroFerro = Util.verificarNuloDouble(numeroFerro);
    }

    public void setNumeroColiformesTotais(String numeroColiformesTotais) {
    	this.numeroColiformesTotais = Util.verificarNuloDouble(numeroColiformesTotais);
    }

    public void setNumeroColiformesFecais(String numeroColiformesFecais) {
    	this.numeroColiformesFecais = Util.verificarNuloDouble(numeroColiformesFecais);
    }

    public void setNumeroNitrato(String numeroNitrato) {
    	this.numeroNitrato = Util.verificarNuloDouble(numeroNitrato);
    }

    public void setNumeroColiformesTermoTolerantes(String numeroColiformesTermoTolerantes) {
    	this.numeroColiformesTermoTolerantes = Util.verificarNuloDouble(numeroColiformesTermoTolerantes);
    }

    public void setDescricaoFonteCapacitacao(String descricaoFonteCapacitacao) {
    	this.descricaoFonteCapacitacao = Util.verificarNuloString(descricaoFonteCapacitacao);
    }

    public void setQuantidadeTurbidezExigidas(String quantidadeTurbidezExigidas) {
    	this.quantidadeTurbidezExigidas = Util.verificarNuloInt(quantidadeTurbidezExigidas);
    }

    public void setQuantidadeCorExigidas(String quantidadeCorExigidas) {
    	this.quantidadeCorExigidas = Util.verificarNuloInt(quantidadeCorExigidas);
    }

    public void setQuantidadeCloroExigidas(String quantidadeCloroExigidas) {
    	this.quantidadeCloroExigidas = Util.verificarNuloInt(quantidadeCloroExigidas);
    }

    public void setQuantidadeFluorExigidas(String quantidadeFluorExigidas) {
    	this.quantidadeFluorExigidas = Util.verificarNuloInt(quantidadeFluorExigidas);
    }

    public void setQuantidadeColiformesTotaisExigidas(String quantidadeColiformesTotaisExigidas) {
    	this.quantidadeColiformesTotaisExigidas = Util.verificarNuloInt(quantidadeColiformesTotaisExigidas);
    }

    public void setQuantidadeColiformesFecaisExigidas(String quantidadeColiformesFecaisExigidas) {
    	this.quantidadeColiformesFecaisExigidas = Util.verificarNuloInt(quantidadeColiformesFecaisExigidas);
    }

    public void setQuantidadeColiformesTermoTolerantesExigidas(String quantidadeColiformesTermoTolerantesExigidas) {
    	this.quantidadeColiformesTermoTolerantesExigidas = Util.verificarNuloInt(quantidadeColiformesTermoTolerantesExigidas);
    }

    public void setQuantidadeTurbidezAnalisadas(String quantidadeTurbidezAnalisadas) {
    	this.quantidadeTurbidezAnalisadas = Util.verificarNuloInt(quantidadeTurbidezAnalisadas);
    }

    public void setQuantidadeCorAnalisadas(String quantidadeCorAnalisadas) {
    	this.quantidadeCorAnalisadas = Util.verificarNuloInt(quantidadeCorAnalisadas);
    }

    public void setQuantidadeCloroAnalisadas(String quantidadeCloroAnalisadas) {
    	this.quantidadeCloroAnalisadas = Util.verificarNuloInt(quantidadeCloroAnalisadas);
    }

    public void setQuantidadeFluorAnalisadas(String quantidadeFluorAnalisadas) {
    	this.quantidadeFluorAnalisadas = Util.verificarNuloInt(quantidadeFluorAnalisadas);
    }

    public void setQuantidadeColiformesTotaisAnalisadas(String quantidadeColiformesTotaisAnalisadas) {
    	this.quantidadeColiformesTotaisAnalisadas = Util.verificarNuloInt(quantidadeColiformesTotaisAnalisadas);
    }

    public void setQuantidadeColiformesFecaisAnalisadas(String quantidadeColiformesFecaisAnalisadas) {
    	this.quantidadeColiformesFecaisAnalisadas = Util.verificarNuloInt(quantidadeColiformesFecaisAnalisadas);
    }

    public void setQuantidadeColiformesTermoTolerantesAnalisadas(String quantidadeColiformesTermoTolerantesAnalisadas) {
    	this.quantidadeColiformesTermoTolerantesAnalisadas = Util.verificarNuloInt(quantidadeColiformesTermoTolerantesAnalisadas);
    }

    public void setQuantidadeTurbidezConforme(String quantidadeTurbidezConforme) {
    	this.quantidadeTurbidezConforme = Util
		.verificarNuloInt(quantidadeTurbidezConforme);
    }

    public void setQuantidadeCorConforme(String quantidadeCorConforme) {
    	this.quantidadeCorConforme = Util
		.verificarNuloInt(quantidadeCorConforme);
    }

    public void setQuantidadeCloroConforme(String quantidadeCloroConforme) {
    	this.quantidadeCloroConforme = Util
		.verificarNuloInt(quantidadeCloroConforme);
    }

    public void setQuantidadeFluorConforme(String quantidadeFluorConforme) {
    	this.quantidadeFluorConforme = Util
		.verificarNuloInt(quantidadeFluorConforme);
    }

    public void setQuantidadeColiformesTotaisConforme(String quantidadeColiformesTotaisConforme) {
    	this.quantidadeColiformesTotaisConforme = Util.verificarNuloInt(quantidadeColiformesTotaisConforme);
    }

    public void setQuantidadeColiformesFecaisConforme(String quantidadeColiformesFecaisConforme) {
    	this.quantidadeColiformesFecaisConforme = Util.verificarNuloInt(quantidadeColiformesFecaisConforme);
    }

    public void setQuantidadeColiformesTermoTolerantesConforme(String quantidadeColiformesTermoTolerantesConforme) {
    	this.quantidadeColiformesTermoTolerantesConforme = Util.verificarNuloInt(quantidadeColiformesTermoTolerantesConforme);
    }

    public void setConsumoMinimoImovel(String consumoMinimoImovel) {
    	this.consumoMinimoImovel = Util.verificarNuloInt(consumoMinimoImovel);
	}

    public void setConsumoMinimoImovelNaoMedido(String consumoMinimoImovelNaoMedido) {
    	this.consumoMinimoImovelNaoMedido = Util.verificarNuloInt(consumoMinimoImovelNaoMedido);
    }
    
    public void setIndicadorParalizarFaturamentoAgua(String indicadorParalizarFaturamentoAgua) {
    	this.indicadorParalizarFaturamentoAgua = Util.verificarNuloInt(indicadorParalizarFaturamentoAgua);
    }

    public void setIndicadorParalizarFaturamentoEsgoto(String indicadorParalizarFaturamentoEsgoto) {
    	this.indicadorParalizarFaturamentoEsgoto = Util.verificarNuloInt(indicadorParalizarFaturamentoEsgoto);
    }

    public String getInscricao() {
	    if (inscricao.length() == 16){
	    	inscricao = inscricao +  " ";
	    }
    	return inscricao;
    }

    public String getEndereco() {
    	return endereco;
    }

    public String getAnoMesConta() {
    	return anoMesConta;
    }

    public int getDigitoVerificadorConta() {
    	return digitoVerificadorConta;
    }

    public String getCodigoResponsavel() {
    	return codigoResponsavel;
    }

    public String getNomeResponsavel() {
    	return nomeResponsavel;
    }

    public String getEnderecoEntrega() {
    	return enderecoEntrega;
    }

    public String getSituacaoLigAgua() {
    	return situacaoLigAgua;
    }

    public String getSituacaoLigEsgoto() {
    	return situacaoLigEsgoto;
    }

    public String getDescricaoBanco() {
    	return descricaoBanco;
    }

    public String getCodigoAgencia() {
    	return codigoAgencia;
    }

    public int getMatriculaCondominio() {
    	return matriculaCondominio;
    }

    public int getIndcCondominio() {
    	return indcCondominio;
    }

    public String getCodigoPerfil() {
    	return codigoPerfil;
    }

    public int getConsumoMedio() {
    	return consumoMedio;
    }

    public int getIndcFaturamentoAgua() {
    	return indcFaturamentoAgua;
    }

    public int getIndcFaturamentoEsgoto() {
    	return indcFaturamentoEsgoto;
    }

    public int getIndcEmissaoConta() {
    	return indcEmissaoConta;
    }

    public int getConsumoMinAgua() {
    	return consumoMinAgua;
    }

    public int getConsumoMinEsgoto() {
    	return consumoMinEsgoto;
    }

    public double getPercentColetaEsgoto() {
    	return percentColetaEsgoto;
    }

    public double getPercentCobrancaEsgoto() {
    	return percentCobrancaEsgoto;
    }

    public int getTipoPoco() {
    	return tipoPoco;
    }

    public int getCodigoTarifa() {
    	return codigoTarifa;
    }

    public int getConsumoEstouro() {
    	return consumoEstouro;
    }

    public int getAltoConsumo() {
    	return altoConsumo;
    }

    public int getBaixoConsumo() {
    	return baixoConsumo;
    }

    public double getFatorMultEstouro() {
    	return fatorMultEstouro;
    }

    public double getFatorMultMediaAltoConsumo() {
    	return fatorMultMediaAltoConsumo;
    }

    public double getPercentBaixoConsumo() {
    	return percentBaixoConsumo;
    }

    public int getConsumoMaximo() {
    	return consumoMaximo;
    }

    public int getNumeroConta() {
    	return numeroConta;
    }

    public int getTipoCalculoTarifa() {
    	return tipoCalculoTarifa;
    }

    public int getGrupoFaturamento() {
    	return grupoFaturamento;
    }

    public int getCodigoRota() {
    	return codigoRota;
    }

    public String getEnderecoAtendimento() {
    	return enderecoAtendimento;
    }

    public String getTelefoneLocalidadeDDD() {
    	return telefoneLocalidadeDDD;
    }

    public int getSequencialRota() {
    	return sequencialRota;
    }

    public String getMensagemConta1() {
    	return mensagemConta1;
    }

    public String getMensagemConta2() {
    	return mensagemConta2;
    }

    public String getMensagemConta3() {
    	return mensagemConta3;
    }

    public String getTurbidezPadrao() {
    	return turbidezPadrao;
    }

    public String getPhPadrao() {
    	return phPadrao;
    }

    public String getCorPadrao() {
    	return corPadrao;
    }

    public String getCloroPadrao() {
    	return cloroPadrao;
    }

    public String getFluorPadrao() {
    	return fluorPadrao;
    }

    public String getFerroPadrao() {
    	return ferroPadrao;
    }

    public String getColiformesTotaisPadrao() {
    	return coliformesTotaisPadrao;
    }

    public String getColiformesFecaisPadrao() {
    	return coliformesFecaisPadrao;
    }

    public String getNitratoPadrao() {
    	return nitratoPadrao;
    }

    public String getColiformesTermoTolerantesPadrao() {
    	return coliformesTermoTolerantesPadrao;
    }

    public int getAmReferenciaQualidadeAgua() {
    	return amReferenciaQualidadeAgua;
    }

    public double getNumeroCloroResidual() {
    	return numeroCloroResidual;
    }

    public double getNumeroTurbidez() {
    	return numeroTurbidez;
    }

    public double getNumeroPh() {
    	return numeroPh;
    }

    public double getNumeroCor() {
    	return numeroCor;
    }

    public double getNumeroFluor() {
    	return numeroFluor;
    }

    public double getNumeroFerro() {
    	return numeroFerro;
    }

    public double getNumeroColiformesTotais() {
    	return numeroColiformesTotais;
    }

    public double getNumeroColiformesFecais() {
    	return numeroColiformesFecais;
    }

    public double getNumeroNitrato() {
    	return numeroNitrato;
    }

    public double getNumeroColiformesTermoTolerantes() {
    	return numeroColiformesTermoTolerantes;
    }

    public String getDescricaoFonteCapacitacao() {
    	return descricaoFonteCapacitacao;
    }

    public int getQuantidadeTurbidezExigidas() {
    	return quantidadeTurbidezExigidas;
    }

    public int getQuantidadeCorExigidas() {
    	return quantidadeCorExigidas;
    }

    public int getQuantidadeCloroExigidas() {
    	return quantidadeCloroExigidas;
    }

    public int getQuantidadeFluorExigidas() {
    	return quantidadeFluorExigidas;
    }

    public int getQuantidadeColiformesTotaisExigidas() {
    	return quantidadeColiformesTotaisExigidas;
    }

    public int getQuantidadeColiformesFecaisExigidas() {
    	return quantidadeColiformesFecaisExigidas;
    }

    public int getQuantidadeColiformesTermoTolerantesExigidas() {
    	return quantidadeColiformesTermoTolerantesExigidas;
    }

    public int getQuantidadeTurbidezAnalisadas() {
    	return quantidadeTurbidezAnalisadas;
    }

    public int getQuantidadeCorAnalisadas() {
    	return quantidadeCorAnalisadas;
    }

    public int getQuantidadeCloroAnalisadas() {
    	return quantidadeCloroAnalisadas;
    }

    public int getQuantidadeFluorAnalisadas() {
    	return quantidadeFluorAnalisadas;
    }

    public int getQuantidadeColiformesTotaisAnalisadas() {
    	return quantidadeColiformesTotaisAnalisadas;
    }

    public int getQuantidadeColiformesFecaisAnalisadas() {
    	return quantidadeColiformesFecaisAnalisadas;
    }

    public int getQuantidadeColiformesTermoTolerantesAnalisadas() {
    	return quantidadeColiformesTermoTolerantesAnalisadas;
    }

    public int getQuantidadeTurbidezConforme() {
    	return quantidadeTurbidezConforme;
    }

    public int getQuantidadeCorConforme() {
    	return quantidadeCorConforme;
    }

    public int getQuantidadeCloroConforme() {
    	return quantidadeCloroConforme;
    }

    public int getQuantidadeFluorConforme() {
    	return quantidadeFluorConforme;
    }

    public int getQuantidadeColiformesTotaisConforme() {
    	return quantidadeColiformesTotaisConforme;
    }

    public int getQuantidadeColiformesFecaisConforme() {
    	return quantidadeColiformesFecaisConforme;
    }

    public int getQuantidadeColiformesTermoTolerantesConforme() {
    	return quantidadeColiformesTermoTolerantesConforme;
    }

    public int getConsumoMinimoImovel() {
    	return consumoMinimoImovel;
	}

    public int getconsumoMinimoImovelNaoMedido() {
    	return consumoMinimoImovelNaoMedido;
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

    public void adicionaRegistro5(Credito reg) {
		if (this.creditos == null) {
		    this.creditos = new Vector();
		}
	
	//	Daniel - Controle do valor do Bonus Social
		if ((reg.getCodigo().equalsIgnoreCase(CODIGO_BONUS_SOCIAL)) && (reg.getValor() > 4.2)){
				
			reg.setValor(String.valueOf(4.2));
		}
		this.creditos.add(reg);
    }

    public String getNumeroHidrometro(int tipoMedicao) {

		String retorno = null;
	
		if (this.medidores != null) {
		    int tamanho = this.medidores.size();
	
		    for (int i = 0; i < tamanho; i++) {
				Medidor medidor = (Medidor) this.medidores.get(i);
				if (medidor.getTipoMedicao() == tipoMedicao) {
				    retorno = medidor.getNumeroHidrometro();
				}
		    }
		}
	
		return retorno;
    }
	
    public int getQuantidadeEconomiasTotal() {
		int tamanho = this.dadosCategoria.size();
		int retorno = 0;
		
		for (int i = 0; i < tamanho; i++) {
		    
			retorno = retorno + ((DadosCategoria) this.dadosCategoria.get(i)).getQtdEconomiasSubcategoria();
		}
	
		return retorno;
    }

    public Medidor getMedidor(int tipoMedicao) {
		Medidor retorno = null;

		if (this.medidores != null) {
		    int tamanho = this.medidores.size();

		    for (int i = 0; i < tamanho; i++) {
				
		    	Medidor medidor = (Medidor) this.medidores.get(i);
				if (medidor.getTipoMedicao() == tipoMedicao) {
				    retorno = medidor;
				}
		    }
		}
	
		return retorno;
    }
    
    public boolean isHistoricoFaturamentoEmpty(){
    	boolean isEmpty = false;
    	if (this.historicoConsumo == null){
    		isEmpty = true;
    	}
    	return isEmpty;
    }

    public HistoricoConsumo getHistoricoFaturamento(int anoMes) {
    	HistoricoConsumo retorno = null;

    	if (this.historicoConsumo != null){
	    	int tamanho = this.historicoConsumo.size();
			for (int i = 0; i < tamanho; i++) {
			    HistoricoConsumo historicoFaturamento = (HistoricoConsumo) this.historicoConsumo.get(i);
		
			    int anoMesReferencia = historicoFaturamento.getAnoMesReferencia();
		
			    if (anoMesReferencia == anoMes) {
			    	retorno = historicoFaturamento;
			    }
			}
    	}
		return retorno;
    }

    public HistoricoConsumo getRegistro3(int anoMes, int idAnormalidadeConsumo) {
		int tamanho = 0;
		
		if (this.historicoConsumo != null && !this.historicoConsumo.isEmpty()) {
		    tamanho = this.historicoConsumo.size();
		}
		
		HistoricoConsumo retorno = null;
		
		for (int i = 0; i < tamanho; i++) {
		    HistoricoConsumo reg3 = (HistoricoConsumo) this.historicoConsumo.get(i);
	
		    int anoMesReferencia = reg3.getAnoMesReferencia();
	
		    if (anoMes == anoMesReferencia && reg3.getAnormalidadeConsumo() == idAnormalidadeConsumo) {
		    	retorno = reg3;
		    }
		}
	
		return retorno;
    }

    public int getQuantidadeEconomias(int codigoCategoria, String codigoSubcategoria) {
		int tamanho = this.dadosCategoria.size();
		int retorno = 0;
		DadosCategoria dadosCategoria = null;
		
		for (int i = 0; i < tamanho; i++) {
		    dadosCategoria = (DadosCategoria) this.dadosCategoria.get(i);
	
		    if (dadosCategoria.getCodigoCategoria() == codigoCategoria
			    && dadosCategoria.getCodigoSubcategoria().equals(codigoSubcategoria)) {

		    	i = tamanho;
				retorno = dadosCategoria.getQtdEconomiasSubcategoria();
		    }
		}
	
		return retorno;
    }

    public boolean isLeituraRealizada() {
		boolean ret = true;
		int size = this.medidores.size();
		
		for (int i = 0; i < size && ret; i++) {
		    Medidor medidor = (Medidor) this.medidores.get(i);
		    
		    if (medidor.getAnormalidade() == 0 && medidor.getLeitura() == Constantes.LEITURA_INVALIDA) {
		    	ret = false;
		    }
		}
	
		return ret;
    }

    public int getQuadra() {
    	return Integer.parseInt(quadra);    		
    }

    public List<Medidor> getMedidores() {
    	return medidores;
    }
    
    public Medidor getMedidorPorTipoMedicao(int tipoMedicao) {
    	for (Medidor m : medidores) {
			if (m.getTipoMedicao() == tipoMedicao)
				return m;
		}
    	
    	return null;
    }

    public List<DadosCategoria> getDadosCategoria() {
    	return dadosCategoria;
    }

    public SituacaoTipo getSituacaoTipo() {
    	return situacaoTipo;
    }

    public List<HistoricoConsumo> getHistoricosConsumo() {
    	return historicoConsumo;
    }

    public List<Debito> getDebitos() {
	
		if ( debitos != null ){	
		    // Retornamos apenas os débitos com indicador de uso = SIM
			ArrayList<Debito> tempDebito = new ArrayList<Debito>();
		
		    for ( int i = 0; i < debitos.size(); i++ ){
		    
				Debito debito = (Debito)debitos.get( i );
			    
				if ( debito.getIndcUso() == Constantes.SIM ){
				    tempDebito.add( debito );
				}
		    }
		    
		    return tempDebito;	    
	
		} else {
		    return null;
		}	
    }

    public List<Credito> getCreditos() {
	
		if ( creditos != null ){	
	
			ArrayList<Credito> tempCreditos = new ArrayList<Credito>();
		
		    // Retornamos apenas os creditos com indicador de uso = SIM
		    for ( int i = 0; i < creditos.size(); i++ ){
		    
				Credito credito = (Credito)creditos.get( i );
			    
				if ( credito.getIndcUso() == Constantes.SIM ){
				    tempCreditos.add( credito );
				}
		    }
		    
		    return tempCreditos;
	
		} else {
		    return null;
		}		
    }

    public List<Imposto> getImpostos() {
    	return impostos;
    }

    public List<Conta> getContas() {
    	return contas;
    }

    /**
     * Calcula a tarifa de consumo por categoria ou subcategoria
     * 
     * @param tipoTarifaPorCategoria
     *            informa se devemos pesquisar por categoria ou por sub
     * @param codigo
     *            codigo da categiria ou da sub
     * @return tarifa de consumo calculada;
     */
    public TarifacaoMinima pesquisarDadosTarifaImovel(boolean tipoTarifaPorCategoria, 
    												  String codigo, 
    												  int codigoTarifa) {

		TarifacaoMinima retorno = null;
	
		for (int i = 0; i < this.tarifacoesMinimas.size(); i++) {
	
		    TarifacaoMinima registro = (TarifacaoMinima) this.tarifacoesMinimas.get(i);
	
		    if (tipoTarifaPorCategoria) {
				
		    	if ((Integer.parseInt(codigo) == registro.getCodigoCategoria()) && (codigoTarifa == registro.getCodigo())) {
				    
					retorno = registro;
				    break;
				}
		    } else {
				
		    	if ((codigo.equals(registro.getCodigoSubcategoria() + "")) && (codigoTarifa == registro.getCodigo())) {
				    	
		    		retorno = registro;
				    break;
				}
		    }
		}
	
		return retorno;
    }

    /**
     * seleciona as faixas para calcular o valor faturado
     * 
     * @param tipoTarifaPorCategoria
     *            informa se o tipo de calculo da tarifa é por categoria
     * @param inicioVigencia
     *            data de inicio da vigencia
     * @param codigo
     *            código da categoria ou subcategoria
     * @return
     */
    public Vector selecionarFaixasCalculoValorFaturado(boolean tipoTarifaPorCategoria, String codigo) {

		Vector retorno = new Vector();
	
		for (int i = 0; i < this.tarifacoesComplementares.size(); i++) {
		    TarifacaoComplementar registro = (TarifacaoComplementar) this.tarifacoesComplementares.get(i);
	
		    if (tipoTarifaPorCategoria) {
				
		    	if (Integer.parseInt(codigo) == registro.getCodigoCategoria()) {
				    retorno.addElement(registro);
				}
		    
		    } else {
				if (Integer.parseInt(codigo) == registro.getCodigoSubcategoria()) {
				    
					retorno.addElement(registro);
				}
		    }
		}
	
		return retorno;
    }

    public double getValorAgua() {

		double soma = 0d;
	
		for (int i = 0; i < this.dadosCategoria.size(); i++) {
		    
			if ((DadosFaturamento) ((DadosCategoria) this.dadosCategoria.get(i)).getFaturamentoAgua() != null) {
		    	soma += ((DadosFaturamento) ((DadosCategoria) this.dadosCategoria.get(i)).getFaturamentoAgua()).getValorFaturado();
		    }
		}
	
		return Util.arredondar(soma, 2);
    }

    public double getValorEsgoto() {

		double soma = 0d;
	
		for (int i = 0; i < this.dadosCategoria.size(); i++) {
		    
			if ((DadosFaturamento) ((DadosCategoria) this.dadosCategoria.get(i)).getFaturamentoEsgoto() != null) {
				soma += ((DadosFaturamento) ((DadosCategoria) this.dadosCategoria.get(i)).getFaturamentoEsgoto()).getValorFaturado();
		    }
		}
	
		return Util.arredondar(soma, 2);
    }

    public double getValorDebitos() {

		double soma = 0d;
	
		if (this.getDebitos() != null) {
		    for (int i = 0; i < this.getDebitos().size(); i++) {
		    	soma += ((Debito) (this.getDebitos().get(i))).getValor();
		    }
		}
	
		return Util.arredondar(soma, 2);
    }

    public double getValorCreditos() {

		double soma = 0d;
	
	//	Daniel - Tratamento de Bônus Social
		if (this.getCreditos() != null) {
		    
			for (int i = 0; i < this.getCreditos().size(); i++) {
		    	
				if( ((Credito) (this.getCreditos().get(i))).getCodigo().equalsIgnoreCase(CODIGO_BONUS_SOCIAL)  &&
		    		Integer.parseInt(this.getCodigoPerfil()) == PERFIL_BONUS_SOCIAL &&
		    		this.getConsumoAgua() != null &&
		    		this.getConsumoAgua().getConsumoCobradoMes() > 10 ){
		    		
		    			System.out.println("CREDITO DE BONUS SOCIAL DESCARTADO!");
	
		    	}else{
		    		soma += ((Credito) (this.getCreditos().get(i))).getValor();
		    	}
		    }
		}
	
		if (valorResidualCredito != 0d) {
		    soma = soma - this.valorResidualCredito;
		}
	
		return Util.arredondar(soma, 2);
    }

    public double getValorContaSem() {

		double valorContaSem = (this.getValorAgua()
								+ this.getValorEsgoto()
								+ this.getValorDebitos() 
								+ this.getValorRateioAgua()
								+ this.getValorRateioEsgoto())
								- this.getValorCreditos();
	
		if (valorContaSem < 0d) {
		    valorContaSem = 0d;
		}
		return Util.arredondar(valorContaSem, 2);
    }

    public double getValores() {
		double soma = 0d;
		
		if (impostos != null) {
		    
			for (int i = 0; i < this.impostos.size(); i++) {
				double percentualAlicota = ((Imposto) (this.impostos.get(i))).getPercentualAlicota();
				double valor = this.getValorContaSem() * Util.arredondar((percentualAlicota / 100), 7);
				soma += valor;
		    }
		}
	
		return Util.arredondar(soma, 2);
    }

    public double getValorConta() {

		double valorConta = this.getValorContaSem() - this.getValores();
		
		if (valorConta < 0d) {
		    valorConta = 0d;
		}
		
		return Util.arredondar(valorConta, 2);
    }

    public double getValorContaSemCreditos() {

		double valorContaSemCreditos = (this.getValorAgua()
										+ this.getValorEsgoto()
										+ this.getValorDebitos() 
										+ this.getValorRateioAgua()
										+ this.getValorRateioEsgoto()
										- this.getValores());
	
		return Util.arredondar(valorContaSemCreditos, 2);
    }

    public String getLocalidade() {
    	return localidade;
    }

    public String getSetorComercial() {
    	return setor;
    }

    public String getInscricaoFormatada() {
		String inscricaoFormatada = this.inscricao.trim();
		localidade = inscricaoFormatada.substring(0, 3);
		setor = inscricaoFormatada.substring(3, 6);
		quadra = inscricaoFormatada.substring(6, 10);
		lote = inscricaoFormatada.substring(10, 14);
		sublote = inscricaoFormatada.substring(14, 17);
	
		inscricaoFormatada = localidade + "." + setor + "." + quadra + "." + lote + "." + sublote;
	
		return inscricaoFormatada;
    }

    public void setId(long imovelId) {
    	this.id = imovelId;
    }

    public long getId() {
    	return id;
    }

    public int getAnormalidadeSemHidrometro() {
    	return anormalidadeSemHidrometro;
    }

    public void setAnormalidadeSemHidrometro(int anormalidadeSemHidrometro) {
    	this.anormalidadeSemHidrometro = anormalidadeSemHidrometro;
    }

    /**
     * Calcula a tarifa de consumo por categoria ou subcategoria
     * 
     * @param tipoTarifaPorCategoria
     *            informa se devemos pesquisar por categoria ou por sub
     * @param codigo
     *            codigo da categiria ou da sub
     * @return tarifa de consumo calculada;
     */
    public TarifacaoMinima pesquisarDadosTarifaImovel( boolean tipoTarifaPorCategoria, 
    												   String codigoCategoria,
    												   String codigoSubCategoria, 
    												   int codigoTarifa, 
    												   Date dataInicioVigencia) {

		TarifacaoMinima retorno = null;
	
		for (int i = 0; i < tarifacoesMinimas.size(); i++) {
	
		    TarifacaoMinima tarifacaoMinima = (TarifacaoMinima) tarifacoesMinimas.get(i);
	
		    if (tipoTarifaPorCategoria) {
	
			// System.out.println( registro.getCodigoCategoriaReg9() );
	
			if (Util.compararData(dataInicioVigencia, tarifacaoMinima.getDataVigencia()) == 0
				&& codigoTarifa == tarifacaoMinima.getCodigo()
				&& Integer.parseInt(codigoCategoria) == tarifacaoMinima.getCodigoCategoria()
				&& (tarifacaoMinima.getCodigoSubcategoria() == Constantes.NULO_INT || tarifacaoMinima.getCodigoSubcategoria() == 0)) {
			    	
					retorno = tarifacaoMinima;
					break;
			}
		    } else {
		    	
				if (Util.compararData(dataInicioVigencia, tarifacaoMinima.getDataVigencia()) == 0
					&& codigoTarifa == tarifacaoMinima.getCodigo()
					&& Integer.parseInt(codigoCategoria) == tarifacaoMinima.getCodigoCategoria()
					&& Integer.parseInt(codigoSubCategoria) == tarifacaoMinima.getCodigoSubcategoria()) {
					    
						retorno = tarifacaoMinima;
					    break;
				}
		    }
		}
	
		return retorno;
    }

    /**
     * seleciona as faixas para calcular o valor faturado
     * 
     * @param tipoTarifaPorCategoria informa se o tipo de calculo da tarifa é por categoria
     * 
     * @param inicioVigencia data de inicio da vigencia
     * 
     * @param codigo código da categoria ou subcategoria
     * 
     * @return
     */
    public Vector selecionarFaixasCalculoValorFaturado(boolean tipoTarifaPorCategoria, 
    												   String codigo, 
    												   int codigoTarifa,
    												   Date dataInicioVigencia) {

		Vector retorno = new Vector();
	
		for (int i = 0; i < tarifacoesComplementares.size(); i++) {
		    
			TarifacaoComplementar tarifacaoComplementar = (TarifacaoComplementar) tarifacoesComplementares.get(i);
	
		    if (tipoTarifaPorCategoria) {
				
		    	if (Util.compararData(dataInicioVigencia, tarifacaoComplementar.getDataInicioVigencia()) == 0
					&& codigoTarifa == tarifacaoComplementar.getCodigo()
					&& Integer.parseInt(codigo) == tarifacaoComplementar.getCodigoCategoria()
					&& (tarifacaoComplementar.getCodigoSubcategoria() == Constantes.NULO_INT || tarifacaoComplementar.getCodigoSubcategoria() == 0)) {
				    
		    			retorno.addElement(tarifacaoComplementar);
				}
		    } else {

		    	if (Util.compararData(dataInicioVigencia, tarifacaoComplementar.getDataInicioVigencia()) == 0
		    		&& codigoTarifa == tarifacaoComplementar.getCodigo()
		    		&& Integer.parseInt(codigo) == tarifacaoComplementar.getCodigoCategoria()
		    		&& Integer.parseInt(codigo) == tarifacaoComplementar.getCodigoSubcategoria()) {
			    
		    			retorno.addElement(tarifacaoComplementar);
		    	}
		    }
		}
	
		return retorno;
    }

    public String getDescricaoSitLigacaoAgua(int situacaoLigAgua) {
		String descricaoSitLigacaoAgua = "";
		
		if (situacaoLigAgua != 0) {
		
			switch (situacaoLigAgua) {
		    
			case POTENCIAL:
			descricaoSitLigacaoAgua = "POTENCIAL";
			break;
		    case FACTIVEL:
			descricaoSitLigacaoAgua = "FACTIVEL";
			break;
		    case LIGADO:
			descricaoSitLigacaoAgua = "LIGADO";
			break;
		    case EM_FISCALIZACAO:
			descricaoSitLigacaoAgua = "LIGADO EM ANALISE.";
			break;
		    case CORTADO:
			descricaoSitLigacaoAgua = "CORTADO";
			break;
		    case SUPRIMIDO:
			descricaoSitLigacaoAgua = "SUPRIMIDO";
			break;
		    case SUPR_PARC:
			descricaoSitLigacaoAgua = "SUPR. PARC.";
			break;
		    case SUPR_PARC_PEDIDO:
			descricaoSitLigacaoAgua = "SUP. PARC. PED.";
			break;
		    case EM_CANCELAMENTO:
			descricaoSitLigacaoAgua = "EM CANCEL.";
			break;
		    }
		}
		return descricaoSitLigacaoAgua;
    }

    public String getDescricaoSitLigacaoEsgoto(int situacaoLigEsgoto) {
		String descricaoSitLigacaoEsgoto = "";
		
		if (situacaoLigEsgoto != 0) {
		    switch (situacaoLigEsgoto) {

		    case POTENCIAL:
			descricaoSitLigacaoEsgoto = "POTENCIAL";
			break;
		    case FACTIVEL:
			descricaoSitLigacaoEsgoto = "FACTIVEL";
			break;
		    case LIGADO:
			descricaoSitLigacaoEsgoto = "LIGADO";
			break;
		    case EM_FISCALIZACAO:
			descricaoSitLigacaoEsgoto = "EM FISCAL.";
			break;
		    case LIG_FORA_USO:
			descricaoSitLigacaoEsgoto = "LIG. FORA DE USO";
			break;
		    case TAMPONADO:
			descricaoSitLigacaoEsgoto = "TAMPONADO";
			break;
		    case CONVERSAO:
			descricaoSitLigacaoEsgoto = "CONVERSAO";
			break;
	
		    }
		}
		return descricaoSitLigacaoEsgoto;
    }

    public int getIndcImoveldo() {
    	return indcImoveldo;
    }

    public void setIndcImoveldo(int indcImoveldo) {
    	this.indcImoveldo = indcImoveldo;
    }

    public double getValorDebitosAnteriores() {

		double soma = 0d;
	
		if (this.contas != null) {
		    
			for (int i = 0; i < this.contas.size(); i++) {
		    	soma += ((Conta) (this.contas.get(i))).getValor();
		    }
		}
	
		return Util.arredondar(soma, 2);
    }

    public String toString() {
    	return this.getMatricula() + " - " + this.getEndereco();
    }

    public String getCpfCnpjCliente() {
    	return cpfCnpjCliente;
    }

    public void setCpfCnpjCliente(String cpfCnpjCliente) {
    	this.cpfCnpjCliente = cpfCnpjCliente;
    }

    public double getValorResidualCredito() {
    	return valorResidualCredito;
    }

    public void setValorResidualCredito(double valorResidualCredito) {
    	this.valorResidualCredito = valorResidualCredito;
    }

    /**
     * Método que verifica se o imóvel deve ser enviado logo após sua Impressao,
     * ou apenas no final do roteiro
     * 
     * @author Daniel Zaccarias
     * @date 22/09/2011
     * @return true - O imóvel deve ser enviado assim que impresso false - O
     *         imóvel deve NÃO ser enviado assim que impresso
     **/
    public boolean enviarAoImprimir() {

		// Verificamos o valor mínimo da conta
		boolean enviarContaValorMaiorPermitido = isValorContaAcimaDoMinimo();
	
		// Daniel - Imovel deve ter sido impresso também.
		/**
		 * Será necessário reenviar caso haja alteração na leitura de agua ou
		 * anormalidade de agua ou na leitura de poco ou anormalidade de poco ou
		 * na anormalidade sem hidrometro. Imóveis que possuem débito do tipo
		 * cortado de água, com esgoto à 30%, devem ser enviados apenas no final
		 **/
		if ( this.indcImovelCalculado == Constantes.SIM && 
			 this.indcImovelEnviado == Constantes.NAO && 
			 (this.indcImovelImpresso == Constantes.SIM || this.indcGeracao == Constantes.NAO) && 
			 this.valorResidualCredito == 0d && 
			 this.getDebito( Debito.TARIFA_CORTADO_DEC_18_251_94 ) == null &&
			 enviarContaValorMaiorPermitido) {
			 
				return true;
		
		} else {
		    return false;
		}
    }

    /**
     * Método que verifica se o imóvel deve ser enviado ao finalizar o processo.
     * 
     * @author Bruno Barros
     * @date 18/05/2010
     * @return true - O imóvel deve ser enviado false - O imóvel deve NÃO ser
     *         enviado
     * 
     **/
    public boolean enviarAoFinalizar() {
    	return this.indcImovelCalculado == Constantes.SIM && this.indcImovelEnviado == Constantes.NAO;
    }

    public boolean isImovelCondominio() {
	return (indcCondominio == Constantes.SIM || (indcCondominio == Constantes.NAO && matriculaCondominio != Constantes.NULO_INT));
    }

    public boolean isImovelMicroCondominio() {
    	return (indcCondominio == Constantes.NAO && matriculaCondominio != Constantes.NULO_INT);
    }
   
    public int getQuantidadeImoveisCondominio() {
    	return quantidadeImoveisCondominio;
    }

    public void setQuantidadeImoveisCondominio(int quantidadeImoveisCondominio) {
    	this.quantidadeImoveisCondominio = quantidadeImoveisCondominio;
    }

    public int getIdImovelCondominio() {
    	return idImovelCondominio;
    }

    public void setIdImovelCondominio(int idImovelCondominio) {
    	this.idImovelCondominio = idImovelCondominio;
    }

    public void setSituacaoTipo(SituacaoTipo situacaoTipo) {
    	this.situacaoTipo = situacaoTipo;
    }

    public Date getDataLeituraAnteriorNaoMedido() {
    	return dataLeituraAnteriorNaoMedido;
    }

    public void setDataLeituraAnteriorNaoMedido(String dataLeituraAnteriorNaoMedido) {
    	this.dataLeituraAnteriorNaoMedido = Util.getData(Util.verificarNuloString(dataLeituraAnteriorNaoMedido));
    }

    public Date getDataImpressaoNaoMedido() {
    	return dataImpressaoNaoMedido;
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

	public String getNomeEscritorio() {
		return nomeEscritorio;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public Date getDataValidadeConta() {
		return dataValidadeConta;
	}

	public String getSituacaoLigAguaString() {
		return situacaoLigAgua.equals(Constantes.LIGADO) ? "Ligado" : "Desligado";
	}

	public String getSituacaoLigEsgotoString() {
		return situacaoLigEsgoto.equals(Constantes.LIGADO) ? "Ligado" : "Desligado";
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

	public void setDataImpressaoNaoMedido(String dataImpressaoNaoMedido) {
		this.dataImpressaoNaoMedido = Util.getData(Util.verificarNuloString(dataImpressaoNaoMedido));
	}

    public short getIndicadorAbastecimentoAgua() {
    	return indicadorAbastecimentoAgua;
    }

    public void setIndicadorAbastecimentoAgua(String indicadorAbastecimentoAgua) {
    	this.indicadorAbastecimentoAgua = Util.verificarNuloShort(indicadorAbastecimentoAgua);
    }

    public short getIndicadorImovelSazonal() {
    	return indicadorImovelSazonal;
    }

    public void setIndicadorImovelSazonal(String indicadorImovelSazonal) {
    	this.indicadorImovelSazonal = Util.verificarNuloShort(indicadorImovelSazonal);
    }

    /**
     * Pesquisa a principal categoria do imóvel
     */
    public int pesquisarPrincipalCategoria() {

		int idCateoria = 0;
		int maiorQuantidadeEconomias = 0;
	
		for (int i = 0; i < this.dadosCategoria.size(); i++) {
	
		    DadosCategoria dadosCategoria = (DadosCategoria) this.dadosCategoria.get(i);
	
		    if (dadosCategoria.getCodigoCategoria() != idCateoria) {
				
		    	int quantidadeEconomias = this.getQuantidadeEconomias(dadosCategoria.getCodigoCategoria(),dadosCategoria.getCodigoSubcategoria());
				
		    	if (maiorQuantidadeEconomias < quantidadeEconomias) {
				    
		    		maiorQuantidadeEconomias = quantidadeEconomias;
				    idCateoria = dadosCategoria.getCodigoCategoria();
				}
		    }
	
		}
	
		return idCateoria;
    }

    /**
     * Esse metodo verifica se a instancia do imovel está com valores iguais aos
     * que foram informados nas abas. Esse método deve ser utilizado para julgar
     * se algo mudou na entrada do objeto.
     * 
     * @author Bruno Barros
     * @date 11/01/2009
     * @return Se mudou, retornar true, senão retorna false;
     */
    public boolean verificarAlteracaoDadosImovel() {
    	return true;
    }

    public boolean isImovelAlterado() {return true;}
    
    /**
     * Julga se é necessário zerar os consumos, pois o usuário apagou os dados
     * de de leitura e/ou anormalidade do imovel selecionado
     * 
     * @author Bruno Barros
     * @date 11/01/2009
     * @return Se mudou, retornar true, senão retorna false;
     */
    public boolean verificarLeituraAnormalidadeZeradas() {return true;}

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

    /**
     * Metodo que atualiza o resumo necessário para o rateio do imóvel
     * condominio
     * 
     * @author Bruno Barros
     * @date 11/03/2010
     * @param consumoAgua
     *            Consumo de agua novo, caso esteja atualizando o consumo de
     *            esgoto, setar nulo
     * @param consumoEsgoto
     *            Consumo de esgoto novo, caso esteja atualizando o consumo de
     *            agua, setar nulo
     */
    public void atualizarResumoEfetuarRateio(Consumo consumoAgua,
	    Consumo consumoEsgoto) {

	// Verificamos se é um imóvel micro
	if (this.idImovelCondominio != Constantes.NULO_INT) {

	    Imovel hidrometroMacro = new Imovel();
//	    Repositorio.carregarObjeto(hidrometroMacro, this.idImovelCondominio);

	    EfetuarRateioConsumoHelper helper = hidrometroMacro
		    .getEfetuarRateioConsumoDispositivoMovelHelper();

	    // Apenas adicionamos a quantidade de economias,
	    // se ela não houver sido adicionada anteriormente
	    if ((!indcAdicionouDadosIniciaisHelperRateio)) {

		indcAdicionouDadosIniciaisHelperRateio = true;

		// Verificamos se o imóvel é faturado de agua
		if ((this.getIndcFaturamentoAgua() == Constantes.SIM) ||
			(this.getIndcFaturamentoAgua() == Constantes.NAO && isImovelMicroCondominio())) {

		    // Calculamos a quantidade de econimias total
		    int quantidadeEconomiasImovel = this
			    .getQuantidadeEconomiasTotal();

		    Medidor ligacaoAguaImovelMicro = this
			    .getMedidor(Constantes.LIGACAO_AGUA);

			helper.setQuantidadeEconomiasAguaTotal(helper.getQuantidadeEconomiasAguaTotal() + quantidadeEconomiasImovel);

		}

		// Verifica se o imóvel é faturado de esgoto
	   	if ( (this.getIndcFaturamentoEsgoto() == Constantes.SIM) ||
	         (this.getIndcFaturamentoEsgoto() == Constantes.NAO && this.isImovelMicroCondominio()) ){

//		if (this.getIndcFaturamentoEsgoto() == Constantes.SIM) {

		    // Calculamos a quantidade de econimias total
		    int quantidadeEconomiasImovel = this
			    .getQuantidadeEconomiasTotal();

		    // Selecionamos a ligação de agua do imovel macro
		    // para verificarmos se o mesmo possue
		    // tipo de rateio = 4
		    Medidor ligacaoAguaImovelMacro = hidrometroMacro
			    .getMedidor(Constantes.LIGACAO_AGUA);
		    /*
		     * Considerar não medido de esgoto igual a não medido de
		     * água, ou seja, de acordo com a existência de hidrometro
		     * de ligação de água quando o tipo de rateio for igual a
		     * rateio não medido de água.
		     */
		    if (ligacaoAguaImovelMacro != null
			    && ligacaoAguaImovelMacro.getTipoRateio() == Medidor.TIPO_RATEIO_NAO_MEDIDO_AGUA) {

			Medidor ligacaoAguaImovelMicro = this
				.getMedidor(Constantes.LIGACAO_AGUA);

			helper.setQuantidadeEconomiasEsgotoTotal(helper.getQuantidadeEconomiasEsgotoTotal() + quantidadeEconomiasImovel);

		    } else {
			Medidor ligacaoEsgotoImovelMicro = this
				.getMedidor(Constantes.LIGACAO_POCO);

		    }
		}
		
		// Adicionamos o consumo mínimo do imóvel ao total
//		helper.setConsumoMinimoTotal(helper.getConsumoMinimoTotal()
//			+ ControladorImoveis.getInstancia().getImovelSelecionado().getConsumoMinimoImovel());
	    }

	    if (consumoAgua != null) {

			boolean valorContaMaiorPermitido = ControladorImovel.getInstancia().getImovelSelecionado().isValorContaMaiorPermitido();
	
			if (ControladorImovel.getInstancia().getImovelSelecionado().consumoAgua != null) {
			    // Removemos do total o consumo calculado anteriormente,
			    // para logo mais abaixo, adicionamos o novo consumo
			    helper.setConsumoLigacaoAguaTotal(helper
				    .getConsumoLigacaoAguaTotal()
				    - ControladorImovel.getInstancia().getImovelSelecionado().consumoAgua.getConsumoCobradoMesOriginal());
			}
	
			// Adicionamos o consumo de agua total
			helper.setConsumoLigacaoAguaTotal(helper
				.getConsumoLigacaoAguaTotal()
				+ consumoAgua.getConsumoCobradoMesOriginal());
			
			//Daniel - Veifica se houve anormalidade de consumo para reter conta.
			if (consumoAgua.getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_ALTO_CONSUMO ||
				consumoAgua.getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_ESTOURO_MEDIA ||
				consumoAgua.getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_ESTOURO ||
				consumoAgua.getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_HIDR_SUBST_INFO ){
				
					helper.setReterImpressaoConta(Constantes.SIM);
			}
	
			//Daniel - Veifica se houve anormalidade de leitura para reter conta.
			if (consumoAgua.getAnormalidadeLeituraFaturada() == ControladorConta.ANORM_HIDR_LEITURA_IMPEDIDA_CLIENTE ||
				consumoAgua.getAnormalidadeLeituraFaturada() == ControladorConta.ANORM_HIDR_PORTAO_FECHADO){
				
					helper.setReterImpressaoConta(Constantes.SIM);
			}
			// Daniel - desconsiderando caso de nao imprimir contas abaixo do permitido para condomínio.
//			if (valorContaMaiorPermitido){
//				helper.setReterImpressaoConta(true);
//			}
	    }

	    if (consumoEsgoto != null) {

		if (ControladorImovel.getInstancia().getImovelSelecionado().consumoEsgoto != null) {
		    // Removemos do total o consumo calculado anteriormente,
		    // para logo mais abaixo, adicionarmos o novo consumo
		    helper
			    .setConsumoLigacaoEsgotoTotal(helper
				    .getConsumoLigacaoEsgotoTotal()
				    - ControladorImovel.getInstancia().getImovelSelecionado().consumoEsgoto
					    .getConsumoCobradoMesOriginal());
		}

		// Adicionamos o consumo de esgoto total
		helper.setConsumoLigacaoEsgotoTotal(helper
			.getConsumoLigacaoEsgotoTotal()
			+ consumoEsgoto.getConsumoCobradoMesOriginal());
	    }
		
//	    Repositorio.salvarObjeto(hidrometroMacro);

//	    Daniel - Caso seja Hidrometro Macro
	}else if (this.indcCondominio == Constantes.SIM && this.matriculaCondominio == Constantes.NULO_INT){

	    if (consumoAgua != null) {

//Daniel - Veifica se houve anormalidade de consumo para reter conta.
			if (consumoAgua.getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_ALTO_CONSUMO ||
					consumoAgua.getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_ESTOURO_MEDIA ||
					consumoAgua.getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_ESTOURO ||
					consumoAgua.getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_HIDR_SUBST_INFO ){

				this.getEfetuarRateioConsumoDispositivoMovelHelper().setReterImpressaoConta(Constantes.SIM);
			}
	
			//Daniel - Veifica se houve anormalidade de leitura para reter conta.
			if (consumoAgua.getAnormalidadeLeituraFaturada() == ControladorConta.ANORM_HIDR_LEITURA_IMPEDIDA_CLIENTE ||
				consumoAgua.getAnormalidadeLeituraFaturada() == ControladorConta.ANORM_HIDR_PORTAO_FECHADO){
				
				this.getEfetuarRateioConsumoDispositivoMovelHelper().setReterImpressaoConta(Constantes.SIM);
			}
	    }
//	    Repositorio.salvarObjeto(this);
	}
	
    }

    public int getIndicadorParalizarFaturamentoAgua() {
    	return indicadorParalizarFaturamentoAgua;
    }

    public int getIndicadorParalizarFaturamentoEsgoto() {
    	return indicadorParalizarFaturamentoEsgoto;
    }

    public int getOpcaoDebitoAutomatico() {
    	return opcaoDebitoAutomatico;
    }

    public void setOpcaoDebitoAutomatico(String opcaoDebitoAutomatico) {
    	this.opcaoDebitoAutomatico = Util.verificarNuloInt(opcaoDebitoAutomatico);
    }

    /**
     * @param consumo
     */
    private void removerAdicionarIdImovelCondominioLidoHelperResumo(Consumo consumo) {
	EfetuarRateioConsumoHelper helper = null;
	Imovel macro = null;

	if (this.getIdImovelCondominio() != Constantes.NULO_INT) {
	    macro = new Imovel();
//	    Repositorio.carregarObjeto(macro, this.getIdImovelCondominio());
	    helper = macro.getEfetuarRateioConsumoDispositivoMovelHelper();
	} else if (this.indcCondominio == Constantes.SIM) {
	    helper = this.getEfetuarRateioConsumoDispositivoMovelHelper();
	}

	boolean removido = false;
	boolean adicionado = false;

//	if (consumo != null || this.getSituacaoLigAgua().equals(Constantes.CORTADO)) {
//	    removido = helper.getIdsAindaFaltamSerCalculador().removeElement( new Integer(this.getId()));
//	} else {
//		
//		// Daniel - remover imoveis do condominio que não possuem conta.	    
//		if (isImovelInformativo()) {
//			if (helper != null && helper.getIdsAindaFaltamSerCalculador().contains(new Integer(this.getId()))){
//			    removido = helper.getIdsAindaFaltamSerCalculador().removeElement(new Integer(this.getId()));				
//			}
//			
//		}else{
//			int indice = helper.getIdsAindaFaltamSerCalculador().indexOf(new Integer(this.getId()));
//	
//		    if (indice == -1) {
//				adicionado = true;
//				helper.getIdsAindaFaltamSerCalculador().addElement(new Integer(this.getId()));
//		    }
//		}
//	}

	if (removido || adicionado) {
	    if (macro != null) {
		macro.setEfetuarRateioConsumoDispositivoMovelHelper(helper);
//		Repositorio.salvarObjeto(macro);
	    } else {
		this.setEfetuarRateioConsumoDispositivoMovelHelper(helper);
//		Repositorio.salvarObjeto(this);
	    }
	}
    }

    /**
     * 
     * Método julga se imóvel em questão pode ter os seus valores de leitura ou
     * anormalidade alterados
     * 
     * @author Bruno Barros
     * @date 25/05/2010
     * @return Pode alterar ?
     */
    public boolean podeAlterarLeituraAnormalidade() {
		boolean habilitar = true;
	
		if (DadosGerais.getInstancia().getIndcBloquearReemissaoConta() == Constantes.SIM) {
		    
			if (this.isImovelCondominio()) {
	
				if (this.getIndcCondominio() == Constantes.SIM) {
				    
					if (this.getIndcImovelImpresso() == Constantes.SIM) {
				    	habilitar = false;
				    }
				
				} else {
				    Imovel imovelMacro = new Imovel();
		//		    Repositorio.carregarObjeto(imovelMacro, this.getIdImovelCondominio());
		
				    if (imovelMacro.getIndcImovelImpresso() == Constantes.SIM) {
				    	habilitar = false;
				    }
				}
			
			} else if (this.getIndcImovelImpresso() == Constantes.SIM) {
				habilitar = false;
		    }
		}
	
		return habilitar;
    }

     /**
     * Verifica se o valor da conta informada no imóvel é superio ao valor
     * máximo permitido para impressão da mesma de acordo com o seu perfil
     * 
     * @author Daniel Zaccarias
     * @date 02/10/2011
     * @return
     */
    public boolean isValorContaMaiorPermitido() {
    	// Caso o valor da conta seja maior que o valor
    	// permitido para ser impresso,
    	// só enviar a conta no final do processo (Finalizar Roteiro)
		boolean contaValorMaiorPermitido = false;
	
		double valorConta = this.getValorConta();
		double valorMaximoEmissaoConta = ControladorConta.VALOR_LIMITE_CONTA;
		
	    switch (Integer.parseInt(this.getCodigoPerfil())) {
		    case PERFIL_GRANDE:
		    	valorMaximoEmissaoConta = ControladorConta.VALOR_LIMITE_PERFIL_GRANDE;
		    	break;
		 
		    case PERFIL_GRANDE_MES:
		    	valorMaximoEmissaoConta = ControladorConta.VALOR_LIMITE_PERFIL_GRANDE_MES;
		    	break;
		
		    case PERFIL_ESPECIAL:
		    	valorMaximoEmissaoConta = ControladorConta.VALOR_LIMITE_PERFIL_ESPECIAL;
		    	break;
		    
		    case PERFIL_BONUS_SOCIAL:
		    	valorMaximoEmissaoConta = ControladorConta.VALOR_LIMITE_PERFIL_BONUS_SOCIAL;
		    	break;
		    
		    case PERFIL_NORMAL:
		    	valorMaximoEmissaoConta = ControladorConta.VALOR_LIMITE_PERFIL_NORMAL;
		    	break;
		    
		    case PERFIL_CORPORATIVO:
		    	valorMaximoEmissaoConta = ControladorConta.VALOR_LIMITE_PERFIL_CORPORATIVO;
		    	break;
		    
		    case PERFIL_GOVERNO_METROPOLITANO:
		    	valorMaximoEmissaoConta = ControladorConta.VALOR_LIMITE_PERFIL_GOVERNO_METROPOLITANO;
		    	break;
		    
		    case PERFIL_GOVERNO_INTERIOR:
		    	valorMaximoEmissaoConta = ControladorConta.VALOR_LIMITE_PERFIL_GOVERNO_INTERIOR;
		    	break;
		    
		    case PERFIL_CONDOMINIAL:
		    	valorMaximoEmissaoConta = ControladorConta.VALOR_LIMITE_PERFIL_CONDOMINIAL;
		    	break;
		    
		    case PERFIL_COLABORADOR:
		    	valorMaximoEmissaoConta = ControladorConta.VALOR_LIMITE_PERFIL_COLABORADOR;
		    	break;
	    }
	
		if (valorConta > valorMaximoEmissaoConta) {
			contaValorMaiorPermitido = true;
		}
		
		return contaValorMaiorPermitido;
    }

    /**
     * Verifica se o valor da conta informada no imóvel é inferior ao valor
     * minimo permitido para impressão da mesma
     * 
     * @author Bruno Barros
     * @date 28/05/2010
     * @return
     */
    public boolean isValorContaAcimaDoMinimo() {

		// Caso o valor da conta seja menor que o valor
		// permitido para ser impresso,
		// só enviar a conta no final do processo (Finalizar Roteiro)
		boolean enviarContaValorMenorPermitido = true;
	
		double valorConta = this.getValorConta();
		double valorMinimoEmissaoConta = DadosGerais.getInstancia().getValorMinimEmissaoConta();

		if (valorConta < valorMinimoEmissaoConta) {
		
			if (this.getValorCreditos() == 0d) {
		    	enviarContaValorMenorPermitido = false;
		    }
		}
	
		return enviarContaValorMenorPermitido;
    }

    /**
     * Verifica se o crédito do imóvel é de Nitrato, Caso seja, então seta 50 %
     * do valor de água nesse crédito que será atualizado no GSAN
     * 
     * @author Sávio Luiz
     * @date 14/06/2010
     * @return
     */
    public void setValorCreditosNitrato(double valorCreditoNitrato) {
		if (this.creditos != null) {
		    
			for (int i = 0; i < this.creditos.size(); i++) {
	
			Credito registroDescricaoValor = ((Credito) (this.creditos.get(i)));
	
			String descricaoCredito = registroDescricaoValor.getDescricao();
	
			if (descricaoCredito != null && !descricaoCredito.equals("")) {
	
			    if (descricaoCredito.substring(0, 16).equals(Credito.DESCRICAO_CERDITO_NITRATO)) {
	
			    	registroDescricaoValor.setValor("" + valorCreditoNitrato);
			    }
			}
	
		    }
		}
    }

    /**
     * Para matriculas iguais consideramos o mesmo imóvel
     * 
     * @author Bruno Barros
     * @date 16/06/2010
     * @return boleano
     * 
     */
    public boolean equals(Object obj) {
		return (obj instanceof Imovel) && ((Imovel) obj).getMatricula() == this.getMatricula();
    }

    public void setDataEmissaoDocumento(String dataEmissaoDocumento) {
    	this.dataEmissaoDocumento = Util.getData(Util.verificarNuloString(dataEmissaoDocumento));
    }

    public Date getDataEmissaoDocumento() {
    	return dataEmissaoDocumento;
    }

    public void setPercentualAlternativoEsgoto(String percentColetaEsgoto) {
    	this.percentualAlternativoEsgoto = Util.verificarNuloDouble(percentColetaEsgoto);
    }

    public void setConsumoPercentualAlternativoEsgoto(String percentColetaEsgoto) {
    	this.consumoPercentualAlternativoEsgoto = Util.verificarNuloInt(percentColetaEsgoto);
    }

    public double getPercentualAlternativoEsgoto() {
    	return percentualAlternativoEsgoto;
    }

    public int getConsumoPercentualAlternativoEsgoto() {
    	return consumoPercentualAlternativoEsgoto;
    }

    /**
     * Verifica se o imóvel tem um percentual de esgoto alternativo
     * 
     * @author Sávio Luiz
     * @date 27/03/2008
     * 
     */
    public void verificarPercentualEsgotoAlternativo(int consumoFaturadoEsgoto) {

		double percentualEsgoto = 0.00;
	
		/*
		 * CASO O IMÓVEL SEJA PARA FATURAR ESGOTO Essa verificação se faz
		 * necessária para o pré-faturamento.
		 */
	   	if ( (this.getIndcFaturamentoEsgoto() == Constantes.SIM) ||
		     (this.getIndcFaturamentoEsgoto() == Constantes.NAO && this.isImovelMicroCondominio()) ){
	
		    // Recupera o percentual de esgoto do imóvel.
		    percentualEsgoto = this.getPercentCobrancaEsgoto();
	
		    // CASO O IMÓVEL SEJA PARA FATURAR ÁGUA
		    if (this.getIndcFaturamentoAgua() == Constantes.SIM && consumoFaturadoEsgoto != Constantes.NULO_INT) {
	
				// Caso o percentual alternativo de esgoto seja diferente de nulo
				if (this.getPercentualAlternativoEsgoto() != Constantes.NULO_DOUBLE) {
		
				    double qtdeEconomia = this.getQuantidadeEconomiasTotal();
				    double consumoFaturadoEsgotoDouble = consumoFaturadoEsgoto;
		
				    int consumoPorEconomia = Util.arredondar(consumoFaturadoEsgotoDouble / qtdeEconomia);
		
				    // verificar se o consumo por economia é
				    // menor ou igual ao consumo do percentual alternativo
				    if (consumoPorEconomia <= this.getConsumoPercentualAlternativoEsgoto()) {
		
				    	// enviar como percentual de esgoto o menor valor entre
				    	// percentual e percentualAlternativo
						if (this.getPercentualAlternativoEsgoto() < percentualEsgoto) {
						    percentualEsgoto = this.getPercentualAlternativoEsgoto();
						}
				    }
				}
		    }
		}
	
		this.setPercentCobrancaEsgoto("" + percentualEsgoto);
    }
    
    /**
     * 
     * Verifica se existe um débito com o código informado
     * Caso positivo, retornar senão nulo
     * 
     * @author Bruno Barros
     * @date 12/07/2010
     * 
     * @param codigo 
     * @return
     */
    public Debito getDebito( String codigo ){
	
	    Debito regTemp = new Debito();
		regTemp.setCodigo( codigo );
		
		if ( this.debitos != null && this.debitos.contains( regTemp ) ){
		    return ( Debito ) this.debitos.get( this.debitos.indexOf( regTemp ) );

		} else {
		    return null;
		}
    }
    
    /**
     * 
     * Verifica se existe um credito com o código informado
     * Caso positivo, retornar senão nulo
     * 
     * @author Bruno Barros
     * @date 12/07/2010
     * 
     * @param codigo 
     * @return
     */
    public Credito getCredito( String codigo ){
	
    	Credito regTemp = new Credito();
    	regTemp.setCodigo( codigo );
	
		if ( this.creditos != null && this.creditos.contains( regTemp ) ){
		    return ( Credito ) this.creditos.get( this.creditos.indexOf( regTemp ) );

		} else {
		    return null;
		}

    }
    
    public List<TarifacaoComplementar> selecionarFaixasCalculoValorFaturado(boolean tipoTarifaPorCategoria, 
    												   String codigoCategoria,
    												   String codigoSubCategoria, 
    												   int codigoTarifa, 
    												   Date dataInicioVigencia) {


        List<TarifacaoComplementar> retorno = new ArrayList<TarifacaoComplementar>();
	
		for (int i = 0; i < tarifacoesComplementares.size(); i++) {
		    
			TarifacaoComplementar tarifacaoComplementar = (TarifacaoComplementar) tarifacoesComplementares.get(i);
	
		    if (tipoTarifaPorCategoria) {

		    	if ( Util.compararData(dataInicioVigencia, tarifacaoComplementar.getDataInicioVigencia()) == 0 && 
		    		 codigoTarifa == tarifacaoComplementar.getCodigo() && 
					 Integer.parseInt(codigoCategoria) == tarifacaoComplementar.getCodigoCategoria() && 
					 (tarifacaoComplementar.getCodigoSubcategoria() == Constantes.NULO_INT || tarifacaoComplementar.getCodigoSubcategoria() == 0) ) {
				    
		    		retorno.add(tarifacaoComplementar);
				}
			    
		    } else {
				if ( Util.compararData(dataInicioVigencia, tarifacaoComplementar.getDataInicioVigencia()) == 0 && 
					 codigoTarifa == tarifacaoComplementar.getCodigo() &&
					 Integer.parseInt(codigoCategoria) == tarifacaoComplementar.getCodigoCategoria() && 
					 Integer.parseInt(codigoSubCategoria) == tarifacaoComplementar.getCodigoSubcategoria() ) {
				    
					retorno.add(tarifacaoComplementar);
				}
		    }
		}

		return retorno;
    }

    /**
     * 
     * Verifica se o imóvel é do tipo informativo.
     * 
     * @author Daniel Zaccarias
     * @date 03/07/2011
     * 
     * @param  
     * @return
     */
    public boolean isImovelInformativo(){
    	boolean informativo = true;
    	boolean paralizarFaturamento = false;
    	
    	if (getIndicadorParalizarFaturamentoAgua() == Constantes.SIM  || 
    		getIndicadorParalizarFaturamentoEsgoto() == Constantes.SIM){
    		
    		paralizarFaturamento = true;
    	}
    	
    	if( (getNumeroConta() != Constantes.NULO_INT) || 
    		(getNumeroConta() == Constantes.NULO_INT && paralizarFaturamento && getSituacaoLigAgua().equals(Constantes.LIGADO)) ){
  
     		informativo = false;
     	}
     	
    	return informativo;
    }
 
    public void setSequencialRotaMarcacao(int sequencialRotaMarcacao) {
    	this.sequencialRotaMarcacao = sequencialRotaMarcacao;
    }

    public int getSequencialRotaMarcacao() {
    	return sequencialRotaMarcacao;
    }

	public List<TarifacaoMinima> getTarifacoesMinimas() {
		return tarifacoesMinimas;
	}

	public void setTarifacoesMinimas(List<TarifacaoMinima> tarifacoesMinimas) {
		this.tarifacoesMinimas = tarifacoesMinimas;
	}

	public List<TarifacaoComplementar> getTarifacoesComplementares() {
		return tarifacoesComplementares;
	}

	public void setTarifacoesComplementares(
			List<TarifacaoComplementar> tarifacoesComplementares) {
		this.tarifacoesComplementares = tarifacoesComplementares;
	}    
        
}
