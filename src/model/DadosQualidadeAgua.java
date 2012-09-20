package model;

import util.Util;

public class DadosQualidadeAgua {

	private static DadosQualidadeAgua instancia;
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
    
    private DadosQualidadeAgua() {}
    
    public static DadosQualidadeAgua getInstancia() {
    	if (instancia == null) {
    		instancia = new DadosQualidadeAgua();
    	}
    	
    	return instancia;
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
}
