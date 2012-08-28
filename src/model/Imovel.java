package model;

import helper.EfetuarRateioConsumoDispositivoMovelHelper;

import java.util.Date;

import util.Constantes;

public class Imovel {
	
	private int matricula;
    private String nomeGerenciaRegional;
    private String nomeEscritorio;
    private String nomeUsuario;
    private Date dataVencimento;
    private Date dataValidadeConta;
    private String inscricao;
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
    // ====================================================================================


    
  //===== Nao estao no banco de dados!! =========
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
    //=============================================
    
    
    
}
