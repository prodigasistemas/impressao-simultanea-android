package util;

import java.util.ArrayList;
import java.util.List;

import model.Consumo;
import model.DadosCategoria;
import model.DadosFaturamentoFaixa;
import model.HistoricoConsumo;
import model.DadosQualidadeAgua;
import model.Imovel;
import model.Medidor;
import android.util.Log;
import business.ControladorImovel;
import business.ControladorRota;


public class ImpressaoContaCosanpa {

    private static ImpressaoContaCosanpa instancia;
    private static Imovel imovel = ControladorImovel.getInstancia().getImovelSelecionado();
    
    // Numero do Hidrometro
    private String numeroMedidor = "NÃO MEDIDO";
    private String dataInstalacao = Constantes.NULO_STRING;
    private String situacaoAgua = imovel
    		.getDescricaoSitLigacaoAgua(new Integer(imovel.getSituacaoLigAgua()));
    private String situacaoEsgoto = imovel
    		.getDescricaoSitLigacaoEsgoto(new Integer(imovel.getSituacaoLigEsgoto()));
    private String leituraAnteriorInformada = Constantes.NULO_STRING;
    private  String leituraAtualInformada = Constantes.NULO_STRING;
    private String leituraAnteriorFaturada = Constantes.NULO_STRING;
    private String leituraAtualFaturada = Constantes.NULO_STRING;
    private String consumo = Constantes.NULO_STRING;
    private String diasConsumo = Constantes.NULO_STRING;
    private Consumo consumoAgua = imovel.getConsumoAgua();
    private Consumo consumoEsgoto = imovel.getConsumoEsgoto();;
    private Medidor medidorAgua;
    private Medidor medidorPoco;
    private String dataLeituraAnteriorInformada = "";
    private String dataLeituraAtualInformada = "";
    private String dataLeituraAnteriorFaturada = "";
    private String dataLeituraAtualFaturada = "";
    private String media = "0";
    private int tipoConsumo = 0;
    private String economias = "";
    
    // Historico consumo
    private String anoMesReferencia = "";
    private String consumoHistorico = "";
    private String hcMensagem = "7 0 50 499 ULTIMOS CONSUMOS\n";

    // Exigido Portaria 518/2004
    private int quantidadeCorExigidas;
    private int quantidadeTurbidezExigidas;
    private int quantidadeCloroExigidas;
    private int quantidadeFluorExigidas;
    private int quantidadeColiformesTotaisExigidas;
    private int quantidadeColiformesTermoTolerantesExigidas;
    
    // Analisado
    private int quantidadeCorAnalisadas;
    private int quantidadeTurbidezAnalisadas;
    private int quantidadeCloroAnalisadas;
    private int quantidadeFluorAnalisadas;
    private int quantidadeColiformesTotaisAnalisadas;
    private int quantidadeColiformesTermoTolerantesAnalisadas;
    
    // Conforme
    private int quantidadeCorConforme;
    private int quantidadeTurbidezConforme;
    private int quantidadeCloroConforme;
    private int quantidadeFluorConforme;
    private int quantidadeColiformesTotaisConforme;
    private int quantidadeColiformesTermoTolerantesConforme;
    
    private String anormalidadeConsumo = "";
    
    private String tarifacaoAgua = "";
    private String tarifacaoEsgoto = "";
    private String rateioAguaEsgoto = "";
    
    // Dados da conta
    private String dataVencimentoConta = "";
    private String valorConta = "";
    private String opcaoDebitoAutomatico = "";
    private String mensagens = "";
    private String matricula = "";
    private String referencia = "";
    private String dataVencimento = "";
    private String totalAPagar = "";
    private String repNumericaCodBarra = "";
    private String repCodigoBarrasSemDigitoVerificador = "";
    private String grupoFaturamento = "";
    
    private String montarComando() {
    	String comando = "! 0 200 200 1720 1\n"+
        		"BOX 32 435 802 482 1\n"+
        		"LINE 720 415 720 455 1\n"+
        		"LINE 403 415 403 477 1\n"+
        		"BOX 32 411 802 435 1\n"+
        		"LINE 278 415 278 477 1\n"+
        		"BOX 283 518 802 545 1\n"+
        		"BOX 283 545 802 692 1\n"+
        		"LINE 656 518 656 692 1\n"+
        		"LINE 425 518 425 692 1\n"+
        		"LINE 535 518 535 692 1\n"+
        		"T 0 2 135 121 Versao: 4.2.1.1 - 21/07/2012 18:47:39 /2\n"+
        		"T 7 1 464 90 "+ imovel.getMatricula() + "\n" +
        		"T 7 1 669 90 "+ Util.retornaDescricaoAnoMes(imovel.getAnoMesConta()) + "\n" +
        		"T 0 0 201 47 "+ Util.formatarCnpj(ControladorRota.getInstancia().getDadosGerais().getCnpjEmpresa().trim()) + "\n" +
        		"T 0 0 285 64 "+ ControladorRota.getInstancia().getDadosGerais().getInscricaoEstadualEmpresa().trim() + "\n" +
        		"T 0 0 222 81 "+ imovel.getGrupoFaturamento() + "\n" +
        		"T 0 0 140 108 \n"+
        		"T 0 2 52 172 "+imovel.getNomeUsuario() + "\n"+
        		"T 0 2 52 199 \n"+
        		"T 0 2 434 169 "+imovel.getEndereco()+"\n"+
        		"T 7 0 15 250 "+imovel.getInscricao()+"\n"+
        		"T 7 0 315 250 "+imovel.getCodigoRota()+"\n"+
        		"T 7 0 415 250 "+imovel.getSequencialRota()+"\n"+
        		economias +
        		"T 7 0 48 301 "+ numeroMedidor +"\n"+
        		"T 7 0 248 301 "+ dataInstalacao +"\n"+
        		"T 7 0 446 301 "+ situacaoAgua +"\n"+
        		"T 7 0 627 301 "+ situacaoEsgoto +"\n"+
        		"T 7 0 168 330 LEITURA\n"+
        		"T 7 0 190 354 "+ leituraAnteriorInformada +"\n"+
        		"T 7 0 190 378 "+ leituraAtualInformada + "\n" +
        		"T 7 0 313 330 DATA\n"+
        		"T 7 0 285 354 "+ dataLeituraAnteriorInformada +"\n"+
        		"T 7 0 285 378 "+ dataLeituraAtualInformada + "\n"+
        		"T 7 0 37 354 ANTERIOR\n"+
        		"T 7 0 37 378 ATUAL\n"+
        		
        		anormalidadeConsumo +
        		
        		"T 7 0 163 412 FATURADO\n"+
        		"T 7 0 190 436 "+ leituraAnteriorFaturada + "\n"+
        		"T 7 0 190 460 "+ leituraAtualFaturada + "\n" +
        		"T 7 0 313 412 DATA\n"+
        		"T 7 0 285 436 "+ dataLeituraAnteriorFaturada + "\n" +
        		"T 7 0 285 460 "+ dataLeituraAtualFaturada + "\n" +
        		"T 7 0 418 412 CONSUMO (m3)\n"+
        		"T 7 0 511 436 "+ consumo + "\n" +
        		"T 7 0 745 412 DIAS\n"+
        		"T 7 0 760 436 "+ diasConsumo +"\n" +
        		"T 7 0 37 436 ANTERIOR\n"+
        		"T 7 0 37 460 ATUAL\n"+
        		"T "+ hcMensagem +
        		"T 0 2 44 522 "+ anoMesReferencia + "\n" +
        		consumoHistorico +
        		"T 7 0 75 672 MEDIA(m3):\n"+
        		"T 7 0 195 672 "+ media + "\n" +
        		"T 7 0 448 496 QUALIDADE DA AGUA\n"+
        		"T 0 0 672 505 Ref: \n"+
        		"T 0 0 705 505 "+ Util.retornaDescricaoAnoMes(imovel.getAnoMesConta()) + "\n" +
        		"T 7 0 287 520 PARAMETROS\n"+
        		"T 7 0 428 520 PORT. 518\n"+
        		"T 7 0 540 520 ANALISADO\n"+
        		"T 7 0 672 520 CONFORME\n"+
        		"T 0 0 287 552 COR(uH)\n"+
        		"T 0 0 287 571 TURBIDEZ(UT)\n"+
        		"T 0 0 287 590 CLORO(mg/L)\n"+
        		"T 0 0 287 609 FLUOR(mg/L)\n"+
        		"T 0 0 287 628 COLIFORME TOTAL\n"+
        		"T 0 0 287 640 Pres/Aus)\n"+
        		"T 0 0 287 657 COLIFORME TERMO\n"+
        		"T 0 0 287 671 TOLER.(Pres/Aus)\n"+
        		"T 0 0 469 552 "+ quantidadeCorExigidas + "\n" +
        		"T 0 0 469 571 "+ quantidadeTurbidezExigidas + "\n" +
        		"T 0 0 469 590 "+ quantidadeCloroExigidas + "\n"+
        		"T 0 0 469 609 "+ quantidadeFluorExigidas +"\n"+
        		"T 0 0 469 628 "+ quantidadeColiformesTotaisExigidas + "\n"+
        		"T 0 0 469 657 "+ quantidadeColiformesTermoTolerantesExigidas + "\n" +
        		"T 0 0 582 552 "+ quantidadeCorAnalisadas + "\n" +
        		"T 0 0 582 571 "+ quantidadeTurbidezAnalisadas + "\n" +
        		"T 0 0 582 590 "+ quantidadeCloroAnalisadas + "\n" +
        		"T 0 0 582 609 "+ quantidadeFluorAnalisadas + "\n" +
        		"T 0 0 582 628 "+ quantidadeColiformesTotaisAnalisadas + "\n" +
        		"T 0 0 582 657 "+ quantidadeColiformesTermoTolerantesAnalisadas + "\n"+
        		"T 0 0 726 552 "+ quantidadeCorConforme + "\n" +
        		"T 0 0 726 571 "+ quantidadeTurbidezConforme + "\n" +
        		"T 0 0 726 590 "+ quantidadeCloroConforme + "\n" +
        		"T 0 0 726 609 "+ quantidadeFluorConforme + "\n" +
        		"T 0 0 726 628 "+ quantidadeColiformesTotaisConforme + "\n" +
        		"T 0 0 726 657 "+ quantidadeColiformesTermoTolerantesConforme + "\n" +
        		"T 7 0 53 708 DESCRICAO\n"+
        		"T 7 0 571 708 CONSUMO\n"+
        		"T 7 0 687 708 TOTAL(R$)\n"+
        		// Retornando "" (String vazia)
        		tarifacaoAgua +
//        		tarifacaoEsgoto +
//        		rateioAguaEsgoto +
        		"T 7 1 160 1210 "+ dataVencimentoConta + "\n" +
        		"T 4 0 640 1210 "+ valorConta + "\n" +
        		"T 0 2 424 1265 OPCAO PELO DEB. AUTOMATICO: \n"+
        		"T 5 0 649 1266 "+ opcaoDebitoAutomatico + "\n" +
				mensagens + 
        		"T 0 2 344 1456 "+ matricula + "\n" +
        		"T 0 2 443 1456 "+ referencia + "\n" +
        		"T 0 2 558 1456 "+ dataVencimento + "\n" +
        		"T 0 2 694 1456 "+ totalAPagar + "\n" +
        		"T 5 0 66 1515 "+ repNumericaCodBarra +
        		"B I2OF5 1 2 90 35 1538 "+ repCodigoBarrasSemDigitoVerificador + "\n" +
        		"T 5 0 109 1661 "+ grupoFaturamento + "\n" +
        		"T 5 0 352 1661 4\n"+
        		"FORM\n"+
        		"PRINT\n";
    	
    	return comando;
    }
    		
    

    
    protected static ImpressaoContaCosanpa getInstancia(int imovelId) {
		if (instancia == null) {
		    instancia = new ImpressaoContaCosanpa();
		}
		return instancia;
    }
    
    public static ImpressaoContaCosanpa getInstancia() {
    	if (instancia == null) {
    		instancia = new ImpressaoContaCosanpa();
    	}
    	
    	return instancia;
    }
    
    public String getComando() {
    	getDados();
    	
    	return montarComando();
    }
    
    public void getDados() {
    	
    	imovel = ControladorImovel.getInstancia().getImovelSelecionado();
    	
    	List dc = imovel.getDadosCategoria();
    	List quantidadeEconomias = categoriasEconomias(dc);
	    
	    for (int i = 0; i < quantidadeEconomias.size(); i++) {
			Object[] dadosCategoria = (Object[]) quantidadeEconomias.get(i);
			economias += formarLinha(0, 0, 470, 254, dadosCategoria[0] + "", i * 85, 0);
			economias += formarLinha(7, 0, 539, 250, dadosCategoria[1] + "", i * 85, 0);
	    }
    	
    	medidorAgua = imovel.getMedidor(Constantes.LIGACAO_AGUA);
    	medidorPoco = imovel.getMedidor(Constantes.LIGACAO_POCO);
    	
	    if (medidorAgua != null) {

			numeroMedidor = medidorAgua.getNumeroHidrometro();
			media = String.valueOf(medidorAgua.getConsumoMedio());
	
			if (medidorAgua.getLeituraAnteriorInformada() != Constantes.NULO_INT){
				leituraAnteriorInformada = medidorAgua.getLeituraAnteriorInformada() + "";
			}
			dataLeituraAnteriorFaturada = Util.dateToString(medidorAgua.getDataLeituraAnteriorFaturada());
			dataLeituraAnteriorInformada = Util.dateToString(medidorAgua.getDataLeituraAnteriorInformada());
			dataLeituraAtualInformada = Util.dateToString(medidorAgua.getDataLeitura());
			dataInstalacao = Util.dateToString(medidorAgua.getDataInstalacao());
	
			if (consumoAgua != null) {
	
				tipoConsumo = consumoAgua.getTipoConsumo();
				
				if(medidorAgua.getLeituraAnteriorFaturamento() != Constantes.NULO_INT){
					leituraAnteriorFaturada = String.valueOf(medidorAgua.getLeituraAnteriorFaturamento());
				}
				
			    if (medidorAgua.getLeituraAtualFaturamento() != Constantes.NULO_INT) {
	
			    	leituraAtualFaturada = String.valueOf(medidorAgua.getLeituraAtualFaturamento());
			    	dataLeituraAtualFaturada = Util.dateToString(medidorAgua.getDataLeituraAtualFaturamento());
			    	
//				    if (imovel.getIdImovelCondominio() != Constantes.NULO_INT ) {
//				    	consumo = (consumoAgua.getConsumoCobradoMes() - consumoAgua.getConsumoRateio()) + "";
//
//				    }else{
				    	consumo = consumoAgua.getConsumoCobradoMes() + "";				    	
				    
//				    }
				    
					diasConsumo = Long.toString(medidorAgua.getQtdDiasAjustado());
	
			    } else {
	
			    	leituraAtualFaturada = "";
			    	consumo = consumoAgua.getConsumoCobradoMes() + "";
					diasConsumo = consumoAgua.getDiasConsumo() + "";
			    }
	
				if (medidorAgua.getLeitura()!= Constantes.NULO_INT &&
						consumoAgua.getLeituraAtual() != Constantes.NULO_INT) {
				    
					leituraAtualInformada = medidorAgua.getLeitura() + "";
				} else {
				    leituraAtualInformada = "";
				    dataLeituraAtualInformada = "";
				}
			}

	    } else if (medidorPoco != null) {

			media = String.valueOf(medidorPoco.getConsumoMedio());
			numeroMedidor = medidorPoco.getNumeroHidrometro();
	
			if (medidorPoco.getLeituraAnteriorInformada() != Constantes.NULO_INT){
				leituraAnteriorInformada = medidorPoco.getLeituraAnteriorInformada()+ "";
			}
			dataLeituraAnteriorFaturada = Util.dateToString(medidorPoco.getDataLeituraAnteriorFaturada());
			dataLeituraAnteriorInformada = Util.dateToString(medidorPoco.getDataLeituraAnteriorInformada());
			dataLeituraAtualInformada = Util.dateToString(medidorPoco.getDataLeitura());
			dataInstalacao = Util.dateToString(medidorPoco.getDataInstalacao());
	
			if (consumoEsgoto != null) {
	
				tipoConsumo = consumoEsgoto.getTipoConsumo();
	
				if(medidorPoco.getLeituraAnteriorFaturamento() != Constantes.NULO_INT){
					leituraAnteriorFaturada = String.valueOf(medidorPoco.getLeituraAnteriorFaturamento());
				}

				if (medidorPoco.getLeituraAtualFaturamento() != Constantes.NULO_INT) {
	
			    	leituraAtualFaturada = String.valueOf(medidorPoco.getLeituraAtualFaturamento());
			    	dataLeituraAtualFaturada = Util.dateToString(medidorPoco.getDataLeituraAtualFaturamento());

				    	consumo = consumoEsgoto.getConsumoCobradoMes() + "";				    	
//				    }
					diasConsumo = Long.toString(medidorPoco.getQtdDiasAjustado());
	
			    } else {
	
			    	leituraAtualFaturada = "";
			    	consumo = consumoEsgoto.getConsumoCobradoMes() + "";
					diasConsumo = consumoEsgoto.getDiasConsumo() + "";
			    }
	
				if (medidorPoco.getLeitura()!= Constantes.NULO_INT &&
						consumoEsgoto.getLeituraAtual() != Constantes.NULO_INT) {
	
				    leituraAtualInformada = medidorPoco.getLeitura() + "";
				} else {
				    leituraAtualInformada = "";
				    dataLeituraAtualInformada = "";
				}
	
			}
	    }
	    
	    if (imovel.getConsumoAgua() != null){
			String anormalidadeConsumo = Util.validarAnormalidadeConsumo(imovel.getConsumoAgua());
			if( anormalidadeConsumo != null){
				anormalidadeConsumo = formarLinha(0, 2, 460, 460, "ANORM. CONSUMO: " + anormalidadeConsumo, 0, 0);
			}
		}
	    

	    List<HistoricoConsumo> historicosConsumo = imovel.getHistoricosConsumo();
	    int k = 0;
	    if (historicosConsumo.size() > 0) {
	    	hcMensagem += "LINE 115 525 115 665 1\n"; 
	    	for (HistoricoConsumo hc : historicosConsumo) {
				anoMesReferencia = Util.getAnoBarraMesReferencia(hc.getAnoMesReferencia());
				
				String anormalidade = "";
			    if (hc.getAnormalidadeLeitura() != Constantes.NULO_INT && hc.getAnormalidadeLeitura() != 0) {
			    	anormalidade = "A. Leit.:" + hc.getAnormalidadeLeitura() + "";
			    } else if (hc.getAnormalidadeConsumo() != Constantes.NULO_INT && hc.getAnormalidadeConsumo() != 0) {
			    	anormalidade = "A. Cons.:" + hc.getAnormalidadeConsumo() + "";
			    }
			    
			    consumoHistorico += formarLinha(0, 2, 127, 522, Util.verificarNuloInt(hc.getConsumo()) + "m3" + anormalidade, 0, k*25);
			    k++;
			}
	    } else {
	    	hcMensagem = "7 0 50 499 HISTORICO DE CONSUMO\nT 0 50 520 INEXISTENTE\n";
	    }
	    
	    // Exigido Portaria 518/2004
	    if (DadosQualidadeAgua.getInstancia().getQuantidadeCorExigidas() != Constantes.NULO_INT){
	    	quantidadeCorExigidas = DadosQualidadeAgua.getInstancia().getQuantidadeCorExigidas();
	    }
	    if (DadosQualidadeAgua.getInstancia().getQuantidadeTurbidezExigidas() != Constantes.NULO_INT){
		    quantidadeTurbidezExigidas = DadosQualidadeAgua.getInstancia().getQuantidadeTurbidezExigidas();
	    }
	    if (DadosQualidadeAgua.getInstancia().getQuantidadeCloroExigidas() != Constantes.NULO_INT){
	    	quantidadeCloroExigidas = DadosQualidadeAgua.getInstancia().getQuantidadeCloroExigidas();
	    }
	    if (DadosQualidadeAgua.getInstancia().getQuantidadeFluorExigidas() != Constantes.NULO_INT){
		    quantidadeFluorExigidas = DadosQualidadeAgua.getInstancia().getQuantidadeFluorExigidas();
	    }
	    if (DadosQualidadeAgua.getInstancia().getQuantidadeColiformesTotaisExigidas() != Constantes.NULO_INT){
		    quantidadeColiformesTotaisExigidas = DadosQualidadeAgua.getInstancia().getQuantidadeColiformesTotaisExigidas();
	    }
	    if (DadosQualidadeAgua.getInstancia().getQuantidadeColiformesTermoTolerantesExigidas() != Constantes.NULO_INT){
		    quantidadeColiformesTermoTolerantesExigidas = DadosQualidadeAgua.getInstancia().getQuantidadeColiformesTermoTolerantesExigidas();
	    }
	    
	    // Analisado
	    if (DadosQualidadeAgua.getInstancia().getQuantidadeCorAnalisadas() != Constantes.NULO_INT){
		    quantidadeCorAnalisadas = DadosQualidadeAgua.getInstancia().getQuantidadeCorAnalisadas();
	    }
	    if (DadosQualidadeAgua.getInstancia().getQuantidadeTurbidezAnalisadas() != Constantes.NULO_INT){
		    quantidadeTurbidezAnalisadas = DadosQualidadeAgua.getInstancia().getQuantidadeTurbidezAnalisadas();
	    }
	    if (DadosQualidadeAgua.getInstancia().getQuantidadeCloroAnalisadas() != Constantes.NULO_INT){
		    quantidadeCloroAnalisadas = DadosQualidadeAgua.getInstancia().getQuantidadeCloroAnalisadas();
	    }
	    if (DadosQualidadeAgua.getInstancia().getQuantidadeFluorAnalisadas() != Constantes.NULO_INT){
		    quantidadeFluorAnalisadas = DadosQualidadeAgua.getInstancia().getQuantidadeFluorAnalisadas();
	    }
	    if (DadosQualidadeAgua.getInstancia().getQuantidadeColiformesTotaisAnalisadas() != Constantes.NULO_INT){
		    quantidadeColiformesTotaisAnalisadas = DadosQualidadeAgua.getInstancia().getQuantidadeColiformesTotaisAnalisadas();
	    }
	    if (DadosQualidadeAgua.getInstancia().getQuantidadeColiformesTermoTolerantesAnalisadas() != Constantes.NULO_INT){
		    quantidadeColiformesTermoTolerantesAnalisadas = DadosQualidadeAgua.getInstancia().getQuantidadeColiformesTermoTolerantesAnalisadas();	    	
	    }
	    
	    //  Conforme
	    if (DadosQualidadeAgua.getInstancia().getQuantidadeCorConforme() != Constantes.NULO_INT){
		    quantidadeCorConforme = DadosQualidadeAgua.getInstancia().getQuantidadeCorConforme();
	    }
	    if (DadosQualidadeAgua.getInstancia().getQuantidadeTurbidezConforme() != Constantes.NULO_INT){
		    quantidadeTurbidezConforme = DadosQualidadeAgua.getInstancia().getQuantidadeTurbidezConforme();
	    }
	    if (DadosQualidadeAgua.getInstancia().getQuantidadeCloroConforme() != Constantes.NULO_INT){
		    quantidadeCloroConforme = DadosQualidadeAgua.getInstancia().getQuantidadeCloroConforme();
	    }
	    if (DadosQualidadeAgua.getInstancia().getQuantidadeFluorConforme() != Constantes.NULO_INT){
		    quantidadeFluorConforme = DadosQualidadeAgua.getInstancia().getQuantidadeFluorConforme();
	    }
	    if (DadosQualidadeAgua.getInstancia().getQuantidadeColiformesTotaisConforme() != Constantes.NULO_INT){
		    quantidadeColiformesTotaisConforme = DadosQualidadeAgua.getInstancia().getQuantidadeColiformesTotaisConforme();
	    }
	    if (DadosQualidadeAgua.getInstancia().getQuantidadeColiformesTermoTolerantesConforme() != Constantes.NULO_INT){
		    quantidadeColiformesTermoTolerantesConforme = DadosQualidadeAgua.getInstancia().getQuantidadeColiformesTermoTolerantesConforme();
	    }
	    
	    int ultimaLinhaAgua = 0;
	    int ultimaLinhaPoco = 0;
	    int quantidadeLinhasAtual = 0;
	    int quantidadeMaximaLinhas = 18;
	    List linhaAgua = gerarLinhasTarifaAgua(consumoAgua);
	    Log.i("Linhas", ">>>" + ((String) linhaAgua.get(0) == null));
	    tarifacaoAgua = (String) linhaAgua.get(0);
	    ultimaLinhaAgua = (((Integer) linhaAgua.get(1)).intValue());
	    if (ultimaLinhaAgua != 0) {
	    	quantidadeLinhasAtual = quantidadeLinhasAtual + ultimaLinhaAgua + 1;
	    }
	    ultimaLinhaAgua *= 34;
	    List tarifasPoco = gerarLinhasTarifaPoco();
	    ultimaLinhaPoco = ultimaLinhaAgua;
	    for (int i = 0; i < tarifasPoco.size(); i++) {
			String[] tarifaPoco = (String[]) tarifasPoco.get(i);
			ultimaLinhaPoco = ultimaLinhaAgua + ((i + 1) * 34);
			quantidadeLinhasAtual++;
			int deslocaDireitaColuna;
			if (i == 0 || i == 1 || i == 2) {
			    deslocaDireitaColuna = i;
			} else {
			    deslocaDireitaColuna = 2;
			}
			if (tarifaPoco[0] != null) {
			    tarifacaoEsgoto += formarLinha(7, 0, 53, 733, tarifaPoco[0], deslocaDireitaColuna * 10, (i + 1) * 34 + ultimaLinhaAgua);
			}
			if (tarifaPoco[1] != null) {
			    tarifacaoEsgoto += formarLinha(7, 0, 571, 733, tarifaPoco[1], 0, (i + 1) * 34 + ultimaLinhaAgua);
			}
			if (tarifaPoco[2] != null) {
			    tarifacaoEsgoto += formarLinha(7, 0, 697, 733, tarifaPoco[2], 0, (i + 1) * 34 + ultimaLinhaAgua);
			}
	    }
	    
	    
	 // Daniel Dados dos Valores de Rateio de Água e Esgoto
	    List rateios = gerarLinhasRateioAguaEsgotoCobrados();
	    int ultimaLinhaRateio = ultimaLinhaPoco;
	    for (int i = 0; i < rateios.size(); i++) {
			String[] debito = (String[]) rateios.get(i);
			ultimaLinhaRateio = ultimaLinhaPoco + ((i + 1) * 34);
			quantidadeLinhasAtual++;
			if (debito[0] != null) {
			    rateioAguaEsgoto = formarLinha(7, 0, 53, 733, debito[0], 0, (i + 1) * 34 + ultimaLinhaPoco);
			}
			if (debito[1] != null) {
			    rateioAguaEsgoto = formarLinha(7, 0, 697, 733, debito[1], 0, (i + 1) * 34 + ultimaLinhaPoco);
			}
	    }
	    
	    dataVencimentoConta = Util.dateToString(imovel.getDataVencimento());
	    
	    valorConta = Util.formatarDoubleParaMoedaReal(imovel.getValorConta());
	    
	    opcaoDebitoAutomatico = imovel.getOpcaoDebitoAutomatico() == Constantes.NULO_INT ? "" : imovel.getOpcaoDebitoAutomatico()+"";
	    Log.i("opcao debito automatico", opcaoDebitoAutomatico);

		if (imovel.getMensagemEstouroConsumo1() != null && !imovel.getMensagemEstouroConsumo1().equals("")) {
		mensagens = formarLinha(0, 3, 35, 1300, imovel.getMensagemEstouroConsumo1() != null && imovel.getMensagemEstouroConsumo1().length() > 45 ? imovel.getMensagemEstouroConsumo1().substring(0, 45) : imovel.getMensagemEstouroConsumo1(), 0, 0)
			+ formarLinha(0, 3, 35, 1330, imovel.getMensagemEstouroConsumo2() != null && imovel.getMensagemEstouroConsumo2().length() > 45 ? imovel.getMensagemEstouroConsumo2().substring(0, 45) : imovel.getMensagemEstouroConsumo2(), 0, 0)
			+ formarLinha(0, 3, 35, 1360, imovel.getMensagemEstouroConsumo3() != null && imovel.getMensagemEstouroConsumo3().length() > 45 ? imovel.getMensagemEstouroConsumo3().substring(0, 45) : imovel.getMensagemEstouroConsumo3(), 0, 0);
	    } else {
		mensagens = formarLinha(0, 3, 35, 1300, imovel.getMensagemConta1() != null && imovel.getMensagemConta1().length() > 45 ? imovel.getMensagemConta1().substring(0, 45) : imovel.getMensagemConta1(), 0, 0)
			+ formarLinha(0, 3, 35, 1330, imovel.getMensagemConta2() != null && imovel.getMensagemConta2().length() > 45 ? imovel.getMensagemConta2().substring(0, 45) : imovel.getMensagemConta2(), 0, 0)
			+ formarLinha(0, 3, 35, 1360, imovel.getMensagemConta3() != null && imovel.getMensagemConta3().length() > 45 ? imovel.getMensagemConta3().substring(0, 45) : imovel.getMensagemConta3(), 0, 0);
	    }
		
	    matricula = ""+imovel.getMatricula();
	    referencia = Util.formatarAnoMesParaMesAno(imovel.getAnoMesConta());
	    dataVencimento = Util.dateToString(imovel.getDataVencimento());
	    totalAPagar = Util.formatarDoubleParaMoedaReal(imovel.getValorConta());
	    
	    // Situação criada para imprimir codigo de barras apenas quando o valor da conta for maior que o mínimo.
	    if (imovel.getCodigoAgencia() == null || imovel.getCodigoAgencia().equals("")) {
			System.out.println("##COD AGENCIA DO IF: " + imovel.getCodigoAgencia());
			
			String representacaoNumericaCodBarra = Util.obterRepresentacaoNumericaCodigoBarra(new Integer(3), imovel.getValorConta(), new Integer(Integer.parseInt(imovel.getInscricao().substring(0, 3))), new Integer(imovel.getMatricula()),
					Util.formatarAnoMesParaMesAnoSemBarra(imovel.getAnoMesConta()), new Integer(imovel.getDigitoVerificadorConta()), null, null, null, null, null, null);
				String representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarra.substring(0, 11).trim() + "-" + representacaoNumericaCodBarra.substring(11, 12).trim() + " " + representacaoNumericaCodBarra.substring(12, 23).trim() + "-"
					+ representacaoNumericaCodBarra.substring(23, 24).trim() + " " + representacaoNumericaCodBarra.substring(24, 35).trim() + "-" + representacaoNumericaCodBarra.substring(35, 36).trim() + " " + representacaoNumericaCodBarra.substring(36, 47).trim() + "-"
					+ representacaoNumericaCodBarra.substring(47, 48);
				repNumericaCodBarra += formarLinha(5, 0, 66, 1515, representacaoNumericaCodBarraFormatada, 0, 0);
				String representacaoCodigoBarrasSemDigitoVerificador = representacaoNumericaCodBarra.substring(0, 11) + representacaoNumericaCodBarra.substring(12, 23) + representacaoNumericaCodBarra.substring(24, 35) + representacaoNumericaCodBarra.substring(36, 47);
				repCodigoBarrasSemDigitoVerificador += representacaoCodigoBarrasSemDigitoVerificador;

	    } else {
			repCodigoBarrasSemDigitoVerificador = formarLinha(4, 0, 182, 1538, "DÉBITO AUTOMÁTICO", 0, 0);
	    }
	    
	    grupoFaturamento = ""+imovel.getGrupoFaturamento();
	    
    }
    
    private String formarLinha(int font, int tamanhoFonte, int x, int y, String texto, int adicionarColuna, int adicionarLinha) {
    	return "T " + font + " " + tamanhoFonte + " " + (x + adicionarColuna) + " " + (y + adicionarLinha) + " " + texto + "\n";
    }
    
    /**
     * [SB0003] - Gerar Linhas da Tarifa de Agua
     * 
     * @return Os dados estão dividos em 3 partes Descricao, de indice 0
     *         Consumo, de indice 1 Valor, de indice 2
     */
    private List gerarLinhasTarifaAgua(Consumo consumoAgua) {
	String linhas = "";
	// Verificamos se o tipo de calculo é por categoria ou por subcategoria
	boolean tipoTarifaPorCategoria = ControladorImovel.getInstancia().tipoTarifaPorCategoria(imovel);
	int qtdLinhas = 0;
	// 3
	    for (int i = 0; i < imovel.getDadosCategoria().size(); i++) {
		DadosCategoria dadosEconomiasSubcategorias = imovel.getDadosCategoria().get(i);
		if (dadosEconomiasSubcategorias.getFaturamentoAgua() == null) {
		    continue;
		}
		qtdLinhas++;
		if (linhas.equals("")) {
		    // 2
		    linhas += formarLinha(7, 0, 53, 733, "AGUA", 0, 0);
		}
		// 3.1
		int quantidaEconomias = 0;
		// 3.1.1
		if (!Constantes.NULO_STRING.equals(dadosEconomiasSubcategorias.getFatorEconomiaCategoria().trim())) {
		    quantidaEconomias = Integer.parseInt(dadosEconomiasSubcategorias.getFatorEconomiaCategoria());
		    // 3.1.2
		} else {
		    quantidaEconomias = dadosEconomiasSubcategorias.getQtdEconomiasSubcategoria();
		}
		// 3.3.1
		String descricao = "";
		if (tipoTarifaPorCategoria) {
		    descricao = dadosEconomiasSubcategorias.getDescricaoCategoria() + " " + quantidaEconomias + " " + "UNIDADE(S)";
		    // 3.3.1.1, 3.3.1.2 e 3.3.2.2, 3.3.3
		    if (descricao.length() > 40) {
			linhas += formarLinha(7, 0, 63, 733, descricao.substring(0, 40), 0, qtdLinhas * 34);
		    } else {
			linhas += formarLinha(7, 0, 63, 733, descricao, 0, qtdLinhas * 34);
		    }
		} else {
		    descricao = dadosEconomiasSubcategorias.getDescricaoAbreviadaSubcategoria() + " " + quantidaEconomias + " " + "UNIDADE(S)";
		    // 3.3.2.1, 3.3.1.2 e 3.3.2.2, 3.3.3
		    if (descricao.length() > 40) {
			linhas += formarLinha(7, 0, 63, 733, descricao.substring(0, 40), 0, qtdLinhas * 34);
		    } else {
			linhas += formarLinha(7, 0, 63, 733, descricao, 0, qtdLinhas * 34);
		    }
		}
		int consumoMinimo = 0;
		if (imovel.getConsumoMinAgua() > imovel.getConsumoMinimoImovel()) {
		    consumoMinimo = imovel.getConsumoMinAgua();
		} else {
		    consumoMinimo = imovel.getConsumoMinimoImovel();
		}
		// 3.4
		if (consumoAgua == null && dadosEconomiasSubcategorias.getFaturamentoAgua() != null && dadosEconomiasSubcategorias.getFaturamentoAgua().getConsumoFaturado() <= consumoMinimo) {
		    qtdLinhas++;
		    descricao = "";
		    // 3.4.2
		    descricao = "TARIFA MINIMA " + Util.formatarDoubleParaMoedaReal(dadosEconomiasSubcategorias.getFaturamentoAgua().getValorTarifaMinima() / quantidaEconomias) + " POR UNIDADE ";
		    linhas += formarLinha(7, 0, 63, 733, descricao, 0, qtdLinhas * 34);
		    descricao = consumoMinimo + " m3";
		    // 3.4.3
		    linhas += formarLinha(7, 0, 571, 733, descricao, 0, qtdLinhas * 34);
		    // 3.4.4
		    linhas += formarLinha(7, 0, 697, 733, Util.formatarDoubleParaMoedaReal(dadosEconomiasSubcategorias.getFaturamentoAgua().getValorTarifaMinima()), 0, qtdLinhas * 34);
		    // 3.5
		} else {
		    // 3.5.1
			System.out.println("dadosEconomiasSubcategorias.getFaturamentoAgua(): " + dadosEconomiasSubcategorias.getFaturamentoAgua());
			System.out.println("dadosEconomiasSubcategorias.getFaturamentoAgua().getFaixas(): " + dadosEconomiasSubcategorias.getFaturamentoAgua().getFaixas());
			System.out.println("dadosEconomiasSubcategorias.getFaturamentoAgua().getFaixas().size(): " + dadosEconomiasSubcategorias.getFaturamentoAgua().getFaixas().size());
			if (dadosEconomiasSubcategorias.getFaturamentoAgua() != null && dadosEconomiasSubcategorias.getFaturamentoAgua().getFaixas() != null && dadosEconomiasSubcategorias.getFaturamentoAgua().getFaixas().size() > 0) {
			qtdLinhas++;
			// 3.5.1.1
			    descricao = "ATE " + ((int) dadosEconomiasSubcategorias.getFaturamentoAgua().getConsumoMinimo() / quantidaEconomias) + " m3 - " + Util.formatarDoubleParaMoedaReal(dadosEconomiasSubcategorias.getFaturamentoAgua().getValorTarifaMinima() / quantidaEconomias)
				    + " POR UNIDADE";
			    linhas += formarLinha(7, 0, 73, 733, descricao, 0, qtdLinhas * 34);
			    // 3.5.1.2.3
			    linhas += formarLinha(7, 0, 571, 733, dadosEconomiasSubcategorias.getFaturamentoAgua().getConsumoMinimo() + " m3", 0, qtdLinhas * 34);
			    // 3.5.1.2.4
			    linhas += formarLinha(7, 0, 697, 733, Util.formatarDoubleParaMoedaReal(dadosEconomiasSubcategorias.getFaturamentoAgua().getValorTarifaMinima()), 0, qtdLinhas * 34);
			    // 3.5.1.2.5
			    for (int j = 0; j < dadosEconomiasSubcategorias.getFaturamentoAgua().getFaixas().size(); j++) {
				qtdLinhas++;
				// 3.5.1.2.5
				DadosFaturamentoFaixa faixa = (DadosFaturamentoFaixa) dadosEconomiasSubcategorias.getFaturamentoAgua().getFaixas().get(j);
				// 3.5.1.2.5.1
				if (faixa.getLimiteFinalConsumo() == Constantes.LIMITE_SUPERIOR_FAIXA_FINAL) {
				    // 3.5.1.2.5.1.2.1, 3.5.1.2.5.1.2.2,
				    // 3.5.1.2.5.1.2.3, 3.5.1.2.5.1.2.4,
				    // 3.5.1.2.5.1.2.5
				    descricao = "ACIMA DE " + (faixa.getLimiteInicialConsumo() - 1) + " m3 - R$ " + Util.formatarDoubleParaMoedaReal(faixa.getValorTarifa()) + " POR m3";
				    linhas += formarLinha(7, 0, 73, 733, descricao, 0, qtdLinhas * 34);
				    // 3.5.1.2.5.1.3.1
				    linhas += formarLinha(7, 0, 571, 733, faixa.getConsumoFaturado() * quantidaEconomias + " m3 ", 0, qtdLinhas * 34);
				    // 3.5.1.2.5.1.3.2
				    // 3.5.1.2.5.1.4
				    linhas += formarLinha(7, 0, 697, 733, Util.formatarDoubleParaMoedaReal(faixa.getValorFaturado() * quantidaEconomias), 0, qtdLinhas * 34);
				    // 3.5.1.2.5.2
				} else {
				    // 3.5.1.2.5.2.2.1, 3.5.1.2.5.2.2.2,
				    // 3.5.1.2.5.2.2.3, 3.5.1.2.5.2.2.4,
				    // 3.5.1.2.5.2.2.5, 3.5.1.2.5.2.2.6
				    descricao = faixa.getLimiteInicialConsumo() + " m3 A " + faixa.getLimiteFinalConsumo() + " m3 - R$ " + Util.formatarDoubleParaMoedaReal(faixa.getValorTarifa()) + " POR M3 ";
				    // 3.5.1.2.5.2.3.1
				    linhas += formarLinha(7, 0, 73, 733, descricao, 0, qtdLinhas * 34);
				    // 3.5.1.2.5.1.3.2
				    linhas += formarLinha(7, 0, 571, 733, faixa.getConsumoFaturado() * quantidaEconomias + " m3 ", 0, qtdLinhas * 34);
				    // 3.5.1.2.5.1.4.1
				    linhas += formarLinha(7, 0, 697, 733, Util.formatarDoubleParaMoedaReal(faixa.getValorFaturado() * quantidaEconomias), 0, qtdLinhas * 34);
				}
			    }
//			}
		    } else {
			if (dadosEconomiasSubcategorias.getFaturamentoAgua() != null) {
			    qtdLinhas++;
			    descricao = "CONSUMO DE AGUA";
			    // 3.5.1.1.2.1
			    linhas += formarLinha(7, 0, 73, 733, descricao, 0, qtdLinhas * 34);
			    linhas += formarLinha(7, 0, 571, 733, ((int) dadosEconomiasSubcategorias.getFaturamentoAgua().getConsumoFaturado()) + " m3", 0, qtdLinhas * 34);
			    // 3.5.1.1.2.2
			    linhas += formarLinha(7, 0, 697, 733, Util.formatarDoubleParaMoedaReal(dadosEconomiasSubcategorias.getFaturamentoAgua().getValorFaturado()), 0, qtdLinhas * 34);
			}
		    }
		}
	    }
	    
	List retornar = new ArrayList();
	retornar.add(linhas);
	retornar.add(new Integer(qtdLinhas));
	return retornar;
    }

    /**
     * [SB0004] - Gerar Linhas da Tarifa de Esgoto
     * 
     * @return Os dados estão dividos em 3 partes Descricao, de indice 0
     *         Consumo, de indice 1 Valor, de indice 2
     */
    @SuppressWarnings("rawtypes")
	private List gerarLinhasTarifaPoco() {
	List retorno = new ArrayList();
	// Os dados estão dividos em 3 partes
	// Descricao, de indice 0
	// Consumo, de indice 1
	// Valor, de indice 2
	String[] dados = new String[3];
	double valorEsgoto = 0d;
	int consumoAgua = 0;
	int consumoEsgoto = 0;
	double valorAgua = 0d;
	// 3
	if (imovel.getDadosCategoria() != null) {
	    for (int i = 0; i < imovel.getDadosCategoria().size(); i++) {
		DadosCategoria dadosEconomiasSubcategorias = imovel.getDadosCategoria().get(i);
		// 1
		if (dadosEconomiasSubcategorias.getFaturamentoEsgoto() != null && dadosEconomiasSubcategorias.getFaturamentoEsgoto().getValorFaturado() != Constantes.NULO_DOUBLE) {
		    valorEsgoto += dadosEconomiasSubcategorias.getFaturamentoEsgoto().getValorFaturado();
		}
		if (dadosEconomiasSubcategorias.getFaturamentoEsgoto() != null && dadosEconomiasSubcategorias.getFaturamentoEsgoto().getConsumoFaturado() != Constantes.NULO_INT) {
		    consumoEsgoto += dadosEconomiasSubcategorias.getFaturamentoEsgoto().getConsumoFaturado();
		}
		if (dadosEconomiasSubcategorias.getFaturamentoAgua() != null) {
		    if (dadosEconomiasSubcategorias.getFaturamentoAgua().getConsumoFaturado() != Constantes.NULO_INT) {
			consumoAgua += dadosEconomiasSubcategorias.getFaturamentoAgua().getConsumoFaturado();
		    }
		    if (dadosEconomiasSubcategorias.getFaturamentoAgua().getValorFaturado() != Constantes.NULO_DOUBLE) {
			valorAgua += dadosEconomiasSubcategorias.getFaturamentoAgua().getValorFaturado();
		    }
		}
	    }
	    dados = new String[3];
	    if (consumoAgua == consumoEsgoto && valorAgua != 0) {
		if (valorEsgoto != 0) {
		    // 1.2.1
		    dados[0] = "ESGOTO ";
		    // 1.2.3
		    dados[0] += Util.formatarDoubleParaMoedaReal(imovel.getPercentCobrancaEsgoto());
		    // 1.2.3
		    dados[0] += " % DO VALOR DE ÁGUA";
		    // 1.4
		    dados[2] = Util.formatarDoubleParaMoedaReal(valorEsgoto);
		    retorno.add(dados);
		}
	    } else {
		if (valorEsgoto != 0) {
		    // 1.2.1
		    dados[0] = "ESGOTO ";
		    // 1.3.1
		    dados[1] = consumoEsgoto + "";
		    // 1.3.2
		    dados[1] += " m3";
		    // 1.4
		    dados[2] = Util.formatarDoubleParaMoedaReal(valorEsgoto);
		    retorno.add(dados);
		}
	    }
	}
	return retorno;
    }
    
    /**
     * [SB0005] - Gerar Linhas da Debitos Cobrados
     * 
     * @return Os dados estão dividos em 3 partes Descricao, de indice 0
     *         Consumo, de indice 1 Valor, de indice 2
     */
    private List gerarLinhasRateioAguaEsgotoCobrados() {
		List retorno = new ArrayList();
		// Os dados estão dividos em 2 partes:
		// Descricao, de indice 0
		// Valor, de indice 2
		String[] dados = new String[2];
		
		// Valor do rateio de Água
		if (imovel.getConsumoAgua() != null && imovel.getValorRateioAgua() > 0) {

		    dados = new String[2];
		    dados[0] = "RATEIO DE ÁGUA DO CONDOMÍNIO";
		    dados[1] = Util.formatarDoubleParaMoedaReal(imovel.getValorRateioAgua());
		    retorno.add(dados);
		}

		// Valor do rateio de Esgoto
		if (imovel.getConsumoEsgoto() != null && imovel.getValorRateioEsgoto() > 0) {

		    dados = new String[2];
		    dados[0] = "RATEIO DE ESGOTO DO CONDOMÍNIO";
		    dados[1] = Util.formatarDoubleParaMoedaReal(imovel.getValorRateioEsgoto());
		    retorno.add(dados);
		}
		return retorno;
    }
    
    private List categoriasEconomias(List regsTipo2) {
    	List retorno = new ArrayList();
    	int quantidadeEconomiasResidencial = 0;
    	int quantidadeEconomiasComercial = 0;
    	int quantidadeEconomiasIndustrial = 0;
    	int quantidadeEconomiasPublico = 0;
    	String descricaoResidencial = "";
    	String descricaoComercial = "";
    	String descricaoIndustrial = "";
    	String descricaoPublico = "";
    	for (int i = 0; i < regsTipo2.size(); i++) {
    	    DadosCategoria reg2 = (DadosCategoria) regsTipo2.get(i);
    	    String descricaoCategoria = reg2.getDescricaoCategoria();
    	    if (descricaoCategoria.length() > 8) {
    		descricaoCategoria = descricaoCategoria.substring(0, 8);
    	    }
    	    switch (reg2.getCodigoCategoria()) {
    	    case DadosCategoria.RESIDENCIAL:
    		quantidadeEconomiasResidencial += reg2.getQtdEconomiasSubcategoria();
    		descricaoResidencial = descricaoCategoria;
    		break;
    	    case DadosCategoria.COMERCIAL:
    		quantidadeEconomiasComercial += reg2.getQtdEconomiasSubcategoria();
    		descricaoComercial = descricaoCategoria;
    		break;
    	    case DadosCategoria.INDUSTRIAL:
    		quantidadeEconomiasIndustrial += reg2.getQtdEconomiasSubcategoria();
    		descricaoIndustrial = descricaoCategoria;
    		break;
    	    case DadosCategoria.PUBLICO:
    		quantidadeEconomiasPublico += reg2.getQtdEconomiasSubcategoria();
    		descricaoPublico = descricaoCategoria;
    		break;
    	    }
    	}
    	if (quantidadeEconomiasResidencial > 0) {
    	    Object[] dadosResidencial = new Object[2];
    	    dadosResidencial[0] = descricaoResidencial;
    	    dadosResidencial[1] = new Integer(quantidadeEconomiasResidencial);
    	    retorno.add(dadosResidencial);
    	}
    	if (quantidadeEconomiasComercial > 0) {
    	    Object[] dadosComercial = new Object[2];
    	    dadosComercial[0] = descricaoComercial;
    	    dadosComercial[1] = new Integer(quantidadeEconomiasComercial);
    	    retorno.add(dadosComercial);
    	}
    	if (quantidadeEconomiasIndustrial > 0) {
    	    Object[] dadosIndustrial = new Object[2];
    	    dadosIndustrial[0] = descricaoIndustrial;
    	    dadosIndustrial[1] = new Integer(quantidadeEconomiasIndustrial);
    	    retorno.add(dadosIndustrial);
    	}
    	if (quantidadeEconomiasPublico > 0) {
    	    Object[] dadosPublico = new Object[2];
    	    dadosPublico[0] = descricaoPublico;
    	    dadosPublico[1] = new Integer(quantidadeEconomiasPublico);
    	    retorno.add(dadosPublico);
    	}
    	return retorno;
        }
    
}
