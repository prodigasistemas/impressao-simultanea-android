package model;

import java.util.Date;

import util.Constantes;
import util.Util;

public class Medidor {

    private int tipoMedicao;
    private String numeroHidrometro;
    private Date dataInstalacaoHidrometro;
    private int numDigitosLeituraHidrometro;
    private int leituraAnteriorFaturamento;
    private Date dataLeituraAnteriorFaturado;
    private Date dataLeituraAnteriorInformada;
    private Date dataLigacaoFornecimento = null;
    private int codigoSituacaoLeituraAnterior;
    private int leituraEsperadaInicial;
    private int leituraEsperadaFinal;
    private String consumoMedio;
    private int leitura = Constantes.NULO_INT;
    private int anormalidade = Constantes.NULO_INT;
    private Date dataLeitura;
    private String localInstalacao;
    private int leituraAnteriorInformada;
    private int leituraAnterior;
    private Date dataLeituraAtualFaturamento;
    private int qtdDiasAjustado = Constantes.NULO_INT;
    private int leituraAtualFaturamento = Constantes.NULO_INT;
    private int leituraRelatorio = Constantes.NULO_INT;
    private int anormalidadeRelatorio = Constantes.NULO_INT;
    private short tipoRateio = Constantes.NULO_SHORT;
    private int leituraInstalacaoHidrometro = Constantes.NULO_INT;
    
    // Tipos de Rateio
    public static final short TIPO_RATEIO_SEM_RATEIO = 0;
    public static final short TIPO_RATEIO_POR_IMOVEL = 1;
    public static final short TIPO_RATEIO_AREA_CONSTRUIDA = 2;
    public static final short TIPO_RATEIO_NUMERO_MORADORES = 3;
    public static final short TIPO_RATEIO_NAO_MEDIDO_AGUA = 4;	
    
	public Medidor(){
	}

    public void setDataInstalacaoHidrometro(String dataInstalacaoHidrometro) {
    	this.dataInstalacaoHidrometro = Util.
    			getData(Util.verificarNuloString(dataInstalacaoHidrometro));
    }
    
    public void setNumDigitosLeituraHidrometro(String numDigitosLeituraHidrometro) {
    	this.numDigitosLeituraHidrometro = Util.
    			verificarNuloInt(numDigitosLeituraHidrometro);
    }
    
    public void setLeituraAnteriorFaturamento(String leituraAnteriorFaturamento) {
    	this.leituraAnteriorFaturamento = Util.
    			verificarNuloInt(leituraAnteriorFaturamento);
    }
    
    public void setCodigoSituacaoLeituraAnterior(String codigoSituacaoLeituraAnterior) {
    	this.codigoSituacaoLeituraAnterior = Util
    			.verificarNuloInt(codigoSituacaoLeituraAnterior);
    }
    
    public void setConsumoMedio(String consumoMedio) {
    	this.consumoMedio = Util.verificarNuloString(consumoMedio);
    }

    public void setTipoMedicao(String tipoMedicao) {
    	this.tipoMedicao = Util.verificarNuloInt(tipoMedicao);
    }

    public void setNumeroHidrometro(String numeroHidrometro) {
    	this.numeroHidrometro = Util.verificarNuloString(numeroHidrometro);
    }

    public void setDataLeituraAnteriorFaturado(String dataLeituraAnteriorFaturado) {
		this.dataLeituraAnteriorFaturado = Util.getData(Util
			.verificarNuloString(dataLeituraAnteriorFaturado));
    }

    public void setDataLeituraAnteriorInformada(String dataLeituraAnteriorInformada) {
    	this.dataLeituraAnteriorInformada = Util.getData(Util
    		.verificarNuloString(dataLeituraAnteriorInformada));
        }

    public void setDataLigacaoFornecimento(String dataLigacaoFornecimento) {
    	this.dataLigacaoFornecimento = Util.getData(Util
    		.verificarNuloString(dataLigacaoFornecimento));
        }
    
    public void setLeituraEsperadaInicial(String leituraEsperadaInicial) {
    	this.leituraEsperadaInicial = Util
		.verificarNuloInt(leituraEsperadaInicial);
    }

    public void setLeituraEsperadaFinal(String leituraEsperadaFinal) {
    	this.leituraEsperadaFinal = Util.verificarNuloInt(leituraEsperadaFinal);
    }

    public void setLeitura(String leitura) {
    	this.leitura = Util.verificarNuloInt(leitura);
    }

    public void setLeitura(int leitura) {
    	this.leitura = leitura;
    }

    public void setAnormalidade(String anormalidade) {
    	this.anormalidade = Util.verificarNuloInt(anormalidade);
    }

    public void setAnormalidade(int anormalidade) {
    	this.anormalidade = anormalidade;
    }

    public void setDataLeitura(Date dataLeitura) {
    	this.dataLeitura = dataLeitura;
    }

    public Date getDataInstalacaoHidrometro() {
    	return dataInstalacaoHidrometro;
    }

    public int getNumDigitosLeituraHidrometro() {
    	return numDigitosLeituraHidrometro;
    }

    public int getLeituraAnteriorFaturamento() {
    	return leituraAnteriorFaturamento;
    }

    public int getCodigoSituacaoLeituraAnterior() {
    	return codigoSituacaoLeituraAnterior;
    }

    public int getConsumoMedio() {
    	return Util.verificarNuloInt(consumoMedio);
    }

    public int getTipoMedicao() {
    	return tipoMedicao;
    }

    public String getNumeroHidrometro() {
    	return numeroHidrometro;
    }

    public int getLeitura() {
    	return leitura;
    }

    public int getAnormalidade() {
    	return anormalidade;
    }

    public int getSituacaoLeituraAnterior() {
    	return codigoSituacaoLeituraAnterior;
    }

    public int getLeituraEsperadaInicial() {
    	return leituraEsperadaInicial;
    }

    public int getLeituraEsperadaFinal() {
    	return leituraEsperadaFinal;
    }

    public int getNumDigitosLeitura() {
    	return numDigitosLeituraHidrometro;
    }

    public Date getDataInstalacao() {
    	return dataInstalacaoHidrometro;
    }

    public Date getDataLeitura() {
    	return dataLeitura;
    }

    public Date getDataLeituraAnteriorFaturada() {
    	return dataLeituraAnteriorFaturado;
    }

    public Date getDataLeituraAnteriorInformada() {
    	return dataLeituraAnteriorInformada;
    }

    public Date getDataLigacaoFornecimento() {
    	return dataLigacaoFornecimento;
	}

    public String getTipoMedicaoString() {
    	String literal = "Não inform.";

    	if (this.tipoMedicao == Constantes.LIGACAO_AGUA) {
    		literal = "Lig Àgua";
    	}

		if (this.tipoMedicao == Constantes.LIGACAO_POCO) {
		    literal = "Lig Poço";
		}
		return literal;
    }

    public double getNumeroHidrometroConvertendoLetraParaNumeros() {
    	return Util.convertendoLetraParaNumeros(this.getNumeroHidrometro());
    }

    public String getLocalInstalacao() {
	return localInstalacao;
    }

    public void setLocalInstalacao(String localInstalacao) {
	this.localInstalacao = Util.verificarNuloString(localInstalacao);
    }

    public int getLeituraAnteriorInformada() {
	return leituraAnteriorInformada;
    }

    public void setLeituraAnteriorInformada(String leituraAnteriorInformada) {
	this.leituraAnteriorInformada = Util
		.verificarNuloInt(leituraAnteriorInformada);
    }

    public int getLeituraAnterior() {
	return leituraAnterior;
    }

    public void setLeituraAnterior(int leituraAnterior) {
	this.leituraAnterior = leituraAnterior;
    }

    public Date getDataLeituraAtualFaturamento() {
	return dataLeituraAtualFaturamento;
    }

    public void setDataLeituraAtualFaturamento(Date dataLeituraAtualFaturamento) {
	this.dataLeituraAtualFaturamento = dataLeituraAtualFaturamento;
    }

    public int getLeituraAtualFaturamento() {
	return leituraAtualFaturamento;
    }

    public void setLeituraAtualFaturamento(int leituraAtualFaturamento) {
	this.leituraAtualFaturamento = leituraAtualFaturamento;
    }

    public int getLeituraRelatorio() {
	return leituraRelatorio;
    }

    public void setLeituraRelatorio(int leituraRelatorio) {
	this.leituraRelatorio = leituraRelatorio;
    }

    public int getAnormalidadeRelatorio() {
	return anormalidadeRelatorio;
    }

    public void setAnormalidadeRelatorio(int anormalidadeRelatorio) {
	this.anormalidadeRelatorio = anormalidadeRelatorio;
    }

    public int getQtdDiasAjustado() {
	return qtdDiasAjustado;
    }

    public void setQtdDiasAjustado(int qtdDiasAjustado) {
	this.qtdDiasAjustado = qtdDiasAjustado;
    }

    public void setTipoRateio(String tipoRateio) {
	this.tipoRateio = Util.verificarNuloShort(tipoRateio);
    }

    public short getTipoRateio() {
	return tipoRateio;
    }
 
    public int getLeituraInstalacaoHidrometro() {
    	return leituraInstalacaoHidrometro;
	}

	public void setLeituraInstalacaoHidrometro(String leituraInstalacaoHidrometro) {
		if ( Util.verificarNuloInt(leituraInstalacaoHidrometro) == Constantes.NULO_INT ){
			this.leituraInstalacaoHidrometro = 0;
		}else{
			this.leituraInstalacaoHidrometro = Util.verificarNuloInt(leituraInstalacaoHidrometro);
		}
	}    
}
