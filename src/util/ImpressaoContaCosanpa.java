package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.Consumo;
import model.Conta;
import model.Credito;
import model.DadosCategoria;
import model.DadosFaturamentoFaixa;
import model.DadosQualidadeAgua;
import model.Debito;
import model.HistoricoConsumo;
import model.Imovel;
import model.Imposto;
import model.Medidor;
import ui.FileManager;
import views.MedidorAguaTab;
import android.util.Log;
import business.ControladorConta;
import business.ControladorImovel;
import business.ControladorRota;

import com.IS.Fachada;

public class ImpressaoContaCosanpa {

    private static ImpressaoContaCosanpa instancia;
    private static Imovel imovel;
    
    // Numero do Hidrometro
    private String numeroMedidor = "NAO MEDIDO";
    private String dataInstalacao = Constantes.NULO_STRING;
    private String situacaoAgua;
    private String situacaoEsgoto;
    private String leituraAnteriorInformada = Constantes.NULO_STRING;
    private  String leituraAtualInformada = Constantes.NULO_STRING;
    private String leituraAnteriorFaturada = Constantes.NULO_STRING;
    private String leituraAtualFaturada = Constantes.NULO_STRING;
    private String consumo = Constantes.NULO_STRING;
    private String diasConsumo = Constantes.NULO_STRING;
    private Consumo consumoAgua;
    private Consumo consumoEsgoto;
    private Medidor medidorAgua;
    private Medidor medidorPoco;
    private String dataLeituraAnteriorInformada = "";
    private String dataLeituraAtualInformada = "";
    private String dataLeituraAnteriorFaturada = "";
    private String dataLeituraAtualFaturada = "";
    private String media = "0";
    private int tipoConsumo = 0;
    private String economias = "";
    private String endereco = "";
    private String valorDebitos = "";
    private String valorCreditos = "";
    private String valorImpostos = "";
    private String descricaoAnoMesConta = "";
  
    // Linhas e molduras
    private String linesAndBoxes = "";
    
    // Historico consumo
    private String anoMesReferencia = "";
    private String historicoConsumo = "";
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
    
    private String txtConsumo = "";
    private String anormalidadeLeitura = "";
    
    private String montarComandoImpressaoFatura(int tipoImpressao) {
    	String comando = "! 0 200 200 1720 1\n"+
    			
    			linesAndBoxes +

    			"T 0 2 135 121 Versao: "+ Fachada.getAppVersion() + " - "+ MedidorAguaTab.getCurrentDateByGPS() + " /" + (imovel.getQuantidadeContasImpressas()+1) + "\n" +
        		"T 7 1 464 90 "+ imovel.getMatricula() + "\n" +
        		descricaoAnoMesConta +
        		"T 0 0 201 47 "+ Util.formatarCnpj(ControladorRota.getInstancia().getDadosGerais().getCnpjEmpresa().trim()) + "\n" +
        		"T 0 0 285 64 "+ ControladorRota.getInstancia().getDadosGerais().getInscricaoEstadualEmpresa().trim() + "\n" +
        		"T 0 0 222 81 "+ imovel.getGrupoFaturamento() + "\n" +
//        		formarLinha(0, 0, 140, 108, (imovel.getEnderecoAtendimento() != null && !imovel.getEnderecoAtendimento().equals("") ? imovel.getEnderecoAtendimento() + " - " : "")
//        				+ (imovel.getTelefoneLocalidadeDDD() != null && !imovel.getTelefoneLocalidadeDDD().equals("") ? imovel.getTelefoneLocalidadeDDD().trim() : ""), 0, 0) +
        		"T 0 2 52 199 \n"+
        		endereco +
        		"T 7 0 15 250 "+Util.formatarInscricao(imovel.getInscricao())+"\n"+
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
        		anormalidadeLeitura +
        		
        		"T 7 0 163 412 FATURADO\n"+
        		"T 7 0 190 436 "+ leituraAnteriorFaturada + "\n"+
        		"T 7 0 190 460 "+ leituraAtualFaturada + "\n" +
        		"T 7 0 313 412 DATA\n"+
        		"T 7 0 285 436 "+ dataLeituraAnteriorFaturada + "\n" +
        		"T 7 0 285 460 "+ dataLeituraAtualFaturada + "\n" +
        		txtConsumo +
        		"T 7 0 511 436 "+ consumo + "\n" +
        		"T 7 0 745 412 DIAS\n"+
        		"T 7 0 760 436 "+ diasConsumo +"\n" +
        		"T 7 0 37 436 ANTERIOR\n"+
        		"T 7 0 37 460 ATUAL\n"+
        		"T "+ hcMensagem +
        		anoMesReferencia +
        		historicoConsumo +
        		"T 7 0 75 672 MEDIA(m3):\n"+
        		"T 7 0 195 672 "+ media + "\n";
        		
    	//==============================================
    	
    	if (tipoImpressao == Constantes.IMPRESSAO_FATURA){
    		comando +=
        		
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
	        		tarifacaoEsgoto +
	        		rateioAguaEsgoto +
	        		valorDebitos +
	        		valorCreditos +
	        		valorImpostos +
	        		"T 7 1 160 1210 "+ dataVencimentoConta + "\n" +
	        		"T 4 0 640 1210 "+ valorConta + "\n" +
	        		"T 0 2 424 1265 OPCAO PELO DEB. AUTOMATICO: \n"+
	        		"T 5 0 649 1266 "+ opcaoDebitoAutomatico + "\n" +
					mensagens + 
	        		"T 0 2 344 1456 "+ matricula + "\n" +
	        		"T 0 2 443 1456 "+ referencia + "\n" +
	        		"T 0 2 558 1456 "+ dataVencimento + "\n" +
	        		"T 0 2 694 1456 "+ totalAPagar + "\n" +
	        		repNumericaCodBarra +
	        		repCodigoBarrasSemDigitoVerificador +
	        		"T 5 0 109 1661 "+ grupoFaturamento + "\n" +
	        		"T 5 0 352 1661 4\n"+
	        		"T 5 0 680 1661 "+imovel.getId()+"\n"+
	        		"FORM\n"+
	        		"PRINT\n";
        		
    	}else if (tipoImpressao == Constantes.IMPRESSAO_EXTRATO_CONDOMINIAL){
    		comando +=
    				"T 7 0 200 700 EXTRATO DE CONSUMO DO MACROMEDIDOR \n"+
        			"T 7 0 53 725 CONSUMO DO IMOVEL CONDOMINIO \n"+
        			"T 7 0 571 725 "+ imovel.getConsumoAgua().getConsumoCobradoMes() + "\n" +
        			"T 7 0 53 750 SOMA DOS CONSUMOS DOS IMOVEIS VINCULADOS \n"+
        			"T 7 0 571 750 "+ imovel.getEfetuarRateioConsumoHelper().getConsumoLigacaoAguaTotal() + "\n" +
        			"T 7 0 53 775 QUANTIDADE IMOVEIS VINCULADOS \n"+
        			"T 7 0 571 775 "+ imovel.getEfetuarRateioConsumoHelper().getQuantidadeEconomiasAguaTotal() + "\n" +
        			"T 7 0 53 800 VALOR RATEADO \n"+
        			"T 7 0 571 800  R$ "+ Util.formatarDoubleParaMoedaReal(imovel.getEfetuarRateioConsumoHelper().getContaParaRateioAgua()) + "\n" +
        			"T 7 0 53 825 VALOR RATEADO POR UNIDADE \n"+
        			"T 7 0 571 825 R$ "+ Util.formatarDoubleParaMoedaReal(imovel.getEfetuarRateioConsumoHelper().getContaParaRateioAgua()
        																/ imovel.getEfetuarRateioConsumoHelper().getQuantidadeEconomiasAguaTotal()) + "\n" +
        			
        		    "T 7 0 367 850 IMPORTANTE \n"+
        		    "T 7 0 53 900 CASO O VALOR DO RATEIO ESTEJA ELEVADO \n"+
        		    "T 7 0 63 925 1. Confirme a leitura do macro \n"+
        		    "T 7 0 63 950 2. Verifique os reservatorios \n"+
        		    "T 7 0 63 975 3. Verifique se ha apartamento ligado clandestinamente\n"+
        		    "T 7 0 53 1025 QUALQUER IRREGULARIDADE COMUNIQUE A COSANPA ATRAVES DO \n"+
        		    "T 7 0 53 1050 SETOR DE ATENDIMENTO \n"+
        		    "T 7 0 53 1075 RATEIO: Obtido atraves da diferenca do consumo entre \n"+
        		    "T 7 0 53 1100 o macromedidor e os consumos dos apartamentos \n"+

        			"T 0 2 344 1456 "+ matricula + "\n" +
	        		"T 5 0 109 1661 "+ grupoFaturamento + "\n" +
	        		"T 5 0 352 1661 4\n"+
	        		"FORM\n"+
	        		"PRINT\n";
        		}
    	
//    	instancia = null;
    	
    	return comando;
    }
        
    public String getComandoImpressaoFatura(Imovel imovel, int impressaoTipo) {
    	this.imovel = imovel;
    	getDadosFatura(imovel, impressaoTipo);
    	return montarComandoImpressaoFatura(impressaoTipo);
    }
        
    public void getDadosFatura(Imovel imovel, int tipoImpressao) {
    	
		if (tipoImpressao == Constantes.IMPRESSAO_FATURA){
			linesAndBoxes = "BOX 32 435 802 482 1\n"+
							"LINE 720 415 720 455 1\n"+
							"LINE 403 415 403 477 1\n"+
							"BOX 32 411 802 435 1\n"+
							"LINE 278 415 278 477 1\n"+
							"BOX 283 518 802 545 1\n"+
							"BOX 283 545 802 692 1\n"+
							"LINE 656 518 656 692 1\n"+
							"LINE 425 518 425 692 1\n"+
							"LINE 535 518 535 692 1\n";
			
		}else if (tipoImpressao == Constantes.IMPRESSAO_EXTRATO_CONDOMINIAL){
			linesAndBoxes = "BOX 32 435 802 482 1\n" + 
							"BOX 32 411 802 435 1\n" + 
							"LINE 720 415 720 455 1\n" + 
							"LINE 403 415 403 477 1\n" + 
							"LINE 290 415 290 477 1\n";
		}

		if (imovel.getAnoMesConta().compareTo(Constantes.NULO_STRING) != 0){
			descricaoAnoMesConta = "T 7 1 669 90 "+ Util.retornaDescricaoAnoMes(imovel.getAnoMesConta()) + "\n";
			
		}else{
			descricaoAnoMesConta = "T 7 1 669 90 "+ Util.retornaDescricaoAnoMes(ControladorRota.getInstancia().getDadosGerais().getAnoMesFaturamento()) + "\n";
		}
		
    	List dc = imovel.getDadosCategoria();
    	List quantidadeEconomias = categoriasEconomias(dc);
    	
    	situacaoAgua = imovel.getDescricaoSitLigacaoAgua(new Integer(imovel.getSituacaoLigAgua()));
    	situacaoEsgoto = imovel.getDescricaoSitLigacaoEsgoto(new Integer(imovel.getSituacaoLigEsgoto()));
    	consumoAgua = imovel.getConsumoAgua();
        consumoEsgoto = imovel.getConsumoEsgoto();;
    	
    	String cpfCnpjFormatado = "";
 	    if (imovel.getCpfCnpjCliente() != null && !imovel.getCpfCnpjCliente().equals("")) {
 	    	cpfCnpjFormatado = imovel.getCpfCnpjCliente().trim();
 	    }
    	if (imovel.getEnderecoEntrega().trim().length() == 0){
		    endereco = 	formarLinha(0, 2, 52, 172, imovel.getNomeUsuario().trim(), 0, 0) + formarLinha(0, 2, 52, 199, cpfCnpjFormatado, 0, 0) + dividirLinha(0, 2, 434, 169, imovel.getEndereco(), 40, 27);
	    }else{
		    endereco =	formarLinha(0, 2, 52, 172, imovel.getNomeUsuario().trim(), 0, 0) + formarLinha(0, 2, 52, 199, cpfCnpjFormatado, 0, 0) + dividirLinha(0, 2, 434, 169, imovel.getEnderecoEntrega(), 40, 27);
	    }
	    
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
	    } else if (medidorAgua == null && medidorPoco == null) {

	    	List<HistoricoConsumo> histConsumo = imovel.getHistoricosConsumo();
		    
	    	if (histConsumo.size() > 0) {
				int sumConsumo = 0;
				for (int i = 0; i < histConsumo.size(); i++) {
				    HistoricoConsumo reg3 = histConsumo.get(i);
				    sumConsumo += Integer.parseInt(reg3.getConsumo());
				}
				media = (int) (sumConsumo / histConsumo.size()) + "";
		    }else{
		    	media = "0";
		    }


			if (consumoAgua != null) {
	
	//		    if (consumoAgua.getLeituraAtual() != Constantes.NULO_INT) {
	//		    	leituraAtual = consumoAgua.getLeituraAtual() + "";
	//		    } else {
			    	leituraAtualInformada = "";
				    dataLeituraAtualInformada = "";
	//		    }
	
			    consumo = consumoAgua.getConsumoCobradoMes() + "";
			    // Numero de dias de consumo nunca deve ser ZERO mesmo para imoveis fixos.
			    diasConsumo =  Long.toString(Util.quantidadeDiasMes(Calendar.getInstance())) + "";
			}
	    }
	    
	    if (imovel.getConsumoAgua() != null){
			anormalidadeConsumo = Util.validarAnormalidadeConsumo(imovel.getConsumoAgua());
			if( anormalidadeConsumo != null){
				anormalidadeConsumo = formarLinha(0, 2, 430, 460, "ANORM. CONSUMO: " + anormalidadeConsumo, 0, 0);
			} else {
				anormalidadeConsumo = Constantes.NULO_STRING;
			}
		}
	    
	    if (imovel.getConsumoAgua() != null){
			if (imovel.getConsumoAgua().getAnormalidadeLeituraFaturada() != 0 &&
					imovel.getConsumoAgua().getAnormalidadeLeituraFaturada() != Constantes.NULO_INT	){

				try {
					anormalidadeLeitura += formarLinha(0, 2, 430, 374, "ANORM. LEITURA: " + FileManager.getAnormalidade(imovel.getConsumoAgua().getAnormalidadeLeituraFaturada()).getDescricao(), 0, 0);
				} catch (IOException e) {
					e.printStackTrace();
					Util.salvarLog(new Date(), e.fillInStackTrace());
				}
			}
		}
	    

	    List<HistoricoConsumo> historicosConsumo = imovel.getHistoricosConsumo();
	    int k = 0;
	    hcMensagem += "LINE 115 525 115 665 1\n"; 
	    for (HistoricoConsumo hc : historicosConsumo) {
	    	anoMesReferencia += formarLinha(0, 2, 44, 522, Util.getAnoBarraMesReferencia(hc.getAnoMesReferencia()) + "", 0, k * 25);
	    	
	    	String anormalidade = "";
	    	if (hc.getAnormalidadeLeitura() != Constantes.NULO_INT && hc.getAnormalidadeLeitura() != 0) {
	    		anormalidade = "A. Leit.:" + hc.getAnormalidadeLeitura() + "";
	    	} else if (hc.getAnormalidadeConsumo() != Constantes.NULO_INT && hc.getAnormalidadeConsumo() != 0) {
	    		anormalidade = "A. Cons.:" + hc.getAnormalidadeConsumo() + "";
	    	}
	    	
	    	historicoConsumo += formarLinha(0, 2, 127, 522, Util.verificarNuloInt(hc.getConsumo()) + " m3 " + anormalidade, 0, k*25);
	    	k++;
	    }
	    
	    if (tipoImpressao == Constantes.IMPRESSAO_FATURA){
	
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
		    
		    if (imovel.isImovelCondominio()) {
		    	txtConsumo += formarLinha(7, 0, 418, 412, "CONSUMO (m3)", 0, 0);// + formarLinha(7, 0, 511, 436, consumo, 0, 0);
		    
		    } else {
		    	txtConsumo +=	formarLinha(7, 0, 412, 412, ControladorConta.getInstancia().getTipoConsumoToPrint(tipoConsumo), 0, 0);
	//	    	txtConsumo +=	formarLinha(7, 0, 511, 436, consumo, 0, 0);
		    }
		    
		    int ultimaLinhaAgua = 0;
		    int ultimaLinhaPoco = 0;
		    int quantidadeLinhasAtual = 0;
		    int quantidadeMaximaLinhas = 18;
		    List linhaAgua = gerarLinhasTarifaAgua(consumoAgua);
	//	    Log.i("Linhas", ">>>" + ((String) linhaAgua.get(0) == null));
		    tarifacaoAgua = (String) linhaAgua.get(0);
		    ultimaLinhaAgua = (((Integer) linhaAgua.get(1)).intValue());
		    if (ultimaLinhaAgua != 0) {
		    	quantidadeLinhasAtual = quantidadeLinhasAtual + ultimaLinhaAgua + 1;
		    }
		    ultimaLinhaAgua *= 25;
		    List tarifasPoco = gerarLinhasTarifaPoco();
		    ultimaLinhaPoco = ultimaLinhaAgua;
		    for (int i = 0; i < tarifasPoco.size(); i++) {
				String[] tarifaPoco = (String[]) tarifasPoco.get(i);
				ultimaLinhaPoco = ultimaLinhaAgua + ((i + 1) * 25);
				quantidadeLinhasAtual++;
				int deslocaDireitaColuna;
				if (i == 0 || i == 1 || i == 2) {
				    deslocaDireitaColuna = i;
				} else {
				    deslocaDireitaColuna = 2;
				}
				if (tarifaPoco[0] != null) {
				    tarifacaoEsgoto += formarLinha(7, 0, 53, 710, tarifaPoco[0], deslocaDireitaColuna * 10, (i + 1) * 25 + ultimaLinhaAgua);
				}
				if (tarifaPoco[1] != null) {
				    tarifacaoEsgoto += formarLinha(7, 0, 571, 710, tarifaPoco[1], 0, (i + 1) * 25 + ultimaLinhaAgua);
				}
				if (tarifaPoco[2] != null) {
				    tarifacaoEsgoto += formarLinha(7, 0, 697, 710, tarifaPoco[2], 0, (i + 1) * 25 + ultimaLinhaAgua);
				}
		    }
		    
		    
		 // Dados dos Valores de Rateio de Água e Esgoto
		    List rateios = gerarLinhasRateioAguaEsgotoCobrados();
		    int ultimaLinhaRateio = ultimaLinhaPoco;
		    rateioAguaEsgoto = "";
		    for (int i = 0; i < rateios.size(); i++) {
				String[] debito = (String[]) rateios.get(i);
				ultimaLinhaRateio = ultimaLinhaPoco + ((i + 1) * 25);
				quantidadeLinhasAtual++;
				if (debito[0] != null) {
				    rateioAguaEsgoto += formarLinha(7, 0, 53, 710, debito[0], 0, (i + 1) * 25 + ultimaLinhaPoco);
				}
				if (debito[1] != null) {
				    rateioAguaEsgoto += formarLinha(7, 0, 697, 710, debito[1], 0, (i + 1) * 25 + ultimaLinhaPoco);
				}
		    }
		    
		    int indicadorDiscriminarDescricao = retornaIndicadorDiscriminar(quantidadeMaximaLinhas, quantidadeLinhasAtual, 'd');
		    List debitos = this.gerarLinhasDebitosCobrados(indicadorDiscriminarDescricao);
		    int ultimaLinhaDebito = ultimaLinhaRateio;
		    for (int i = 0; i < debitos.size(); i++) {
			String[] debito = (String[]) debitos.get(i);
			ultimaLinhaDebito = ultimaLinhaRateio + ((i + 1) * 25);
			quantidadeLinhasAtual++;
			// int deslocaDireitaColuna;
			// if( i == 0 || i == 1 || i==2 ){
			// deslocaDireitaColuna = i;
			// } else {
			// deslocaDireitaColuna = 2;
			// }
			if (debito[0] != null) {
			    valorDebitos += formarLinha(7, 0, 53, 710, debito[0], 0, (i + 1) * 25 + ultimaLinhaRateio);
			}
			if (debito[1] != null) {
			    valorDebitos += formarLinha(7, 0, 571, 710, debito[1], 0, (i + 1) * 25 + ultimaLinhaRateio);
			}
			if (debito[2] != null) {
			    valorDebitos += formarLinha(7, 0, 697, 710, debito[2], 0, (i + 1) * 25 + ultimaLinhaRateio);
			}
		    }
		    indicadorDiscriminarDescricao = retornaIndicadorDiscriminar(quantidadeMaximaLinhas, quantidadeLinhasAtual, 'c');
		    List creditos = this.gerarLinhasCreditosRealizados(indicadorDiscriminarDescricao);
		    int ultimaLinhaCredito = ultimaLinhaDebito;
		    for (int i = 0; i < creditos.size(); i++) {
			String[] credito = (String[]) creditos.get(i);
			ultimaLinhaCredito = ultimaLinhaDebito + ((i + 1) * 25);
			// int deslocaDireitaColuna;
			// if( i == 0 || i == 1 || i==2 ){
			// deslocaDireitaColuna = i;
			// } else {
			// deslocaDireitaColuna = 2;
			// }
			if (credito[0] != null) {
			    valorCreditos += formarLinha(7, 0, 53, 710, credito[0], 0, (i + 1) * 25 + ultimaLinhaDebito);
			}
			if (credito[1] != null) {
			    valorCreditos += formarLinha(7, 0, 571, 710, credito[1], 0, (i + 1) * 25 + ultimaLinhaDebito);
			}
			if (credito[2] != null) {
			    valorCreditos += formarLinha(7, 0, 697, 710, credito[2], 0, (i + 1) * 25 + ultimaLinhaDebito);
			}
		    }
		    List impostos = this.gerarLinhasImpostosRetidos();
		    for (int i = 0; i < impostos.size(); i++) {
			String[] imposto = (String[]) impostos.get(i);
			int deslocaDireitaColuna;
			if (i == 0 || i == 1) {
			    deslocaDireitaColuna = i;
			} else {
			    deslocaDireitaColuna = 1;
			}
			if (imposto[0] != null) {
			    valorImpostos += formarLinha(7, 0, 53, 710, imposto[0], deslocaDireitaColuna * 10, (i + 1) * 25 + ultimaLinhaCredito);
			}
			if (imposto[1] != null) {
			    valorImpostos += formarLinha(7, 0, 571, 710, imposto[1], 0, (i + 1) * 25 + ultimaLinhaCredito);
			}
			if (imposto[2] != null) {
			    valorImpostos += formarLinha(7, 0, 697, 710, imposto[2], 0, (i + 1) * 25 + ultimaLinhaCredito);
			}
		    }
		    
		    dataVencimentoConta = Util.dateToString(imovel.getDataVencimento());
		    
		    valorConta = Util.formatarDoubleParaMoedaReal(imovel.getValorConta());
		    
		    opcaoDebitoAutomatico = imovel.getOpcaoDebitoAutomatico() == Constantes.NULO_INT ? "" : imovel.getOpcaoDebitoAutomatico()+"";
		    Log.i("opcao debito automatico", opcaoDebitoAutomatico);
	
			if (imovel.getMensagemQuitacaoAnual() != null && !imovel.getMensagemQuitacaoAnual().equals("")) {
				mensagens = dividirLinha(0, 3, 35, 1300, imovel.getMensagemQuitacaoAnual(), 45, 30);
				
			} else if (imovel.getMensagemEstouroConsumo1() != null && !imovel.getMensagemEstouroConsumo1().equals("")) {
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
					repCodigoBarrasSemDigitoVerificador += "B I2OF5 1 2 90 35 1538 " + representacaoCodigoBarrasSemDigitoVerificador + "\n";
	
		    } else {
				repCodigoBarrasSemDigitoVerificador = formarLinha(4, 0, 182, 1538, "DEBITO AUTOMATICO", 0, 0);
		    }
		    
		    grupoFaturamento = ""+imovel.getGrupoFaturamento();
		    
		}
    }
    
    public String imprimirNotificacaoDebito(Imovel imovel) {
    	ImpressaoContaCosanpa.imovel = imovel;
    	getDadosAvisoDebito(ImpressaoContaCosanpa.imovel);

    	String comando = "! 0 816 0 1720 1\n"+

			//Incluindo data de impressao, Versao do Leitura e Impressao Simultanea e
		    // Data e hora de impressão na conta impressa
			"T 0 2 135 121 Versao: "+ Fachada.getAppVersion() + " - "+ MedidorAguaTab.getCurrentDateByGPS() + " /" + (imovel.getQuantidadeContasImpressas()+1) + "\n" +
		    // Matricula do imovel e Ano mes de referencia da conta
    		"T 7 1 464 90 "+ imovel.getMatricula() + "\n" + descricaoAnoMesConta +
    		"T 0 0 201 47 "+ Util.formatarCnpj(ControladorRota.getInstancia().getDadosGerais().getCnpjEmpresa().trim()) + "\n" +
    		"T 0 0 285 64 "+ ControladorRota.getInstancia().getDadosGerais().getInscricaoEstadualEmpresa().trim() + "\n" +
		    // Grupo
    		"T 0 0 222 81 "+ imovel.getGrupoFaturamento() + "\n" +
    		"T 0 2 52 199 \n"+
		    // Dados do cliente
    		endereco +
		    // Inscrição estadual
    		"T 7 0 15 250 "+Util.formatarInscricao(imovel.getInscricao())+"\n"+
		    // Codigo da Rota
    		"T 7 0 315 250 "+imovel.getCodigoRota()+"\n"+
		    // Sequencial da Rota
    		"T 7 0 415 250 "+imovel.getSequencialRota()+"\n"+
    		economias +
		    // Numero do Hidrometro
    		"T 7 0 48 301 "+ numeroMedidor +"\n"+
		    // Data da Instalação
    		"T 7 0 248 301 "+ dataInstalacao +"\n"+
	    	// Situacao da ligacao de Agua
    		"T 7 0 446 301 "+ situacaoAgua +"\n"+
	    	// Situacao da ligacao de Esgoto
    		"T 7 0 627 301 "+ situacaoEsgoto +"\n"+

    	
    		"T 7 1 300 350 AVISO DE DEBITO \n"+
    		"T 7 0 37 390 Prezado Cliente, \n"+
    		dividirLinha(7, 0, 37, 426, "Ate o presente momento nao registramos a confirmacao do Paga-mento da(s) conta(s) abaixo:", 61, 24)+
    		"T 7 0 53 486 MES REFERENCIA \n"+
    		"T 7 0 400 486 VENCIMENTO \n"+
    		"T 7 0 697 486 VALOR(R$) \n";
    	
    	int totalLinhas = imovel.getContas().size();
    	int qtdLinhasDebitoImpressas = 0;
    	double subTotal = 0;
    	
    	for (int i = 0; i < totalLinhas; i++) {
    		Conta conta = ((Conta) imovel.getContas().get(i));
    		
    		if (totalLinhas > 11){
    			
    			if ((totalLinhas - i) == 11){
    				
    				comando += formarLinha(7, 0, 83, 510, "OUTROS", 0, qtdLinhasDebitoImpressas * 24)+
    						   formarLinha(7, 0, 405, 510, "     --", 0, qtdLinhasDebitoImpressas * 24)+
    						   formarLinha(7, 0, 707, 510, Util.formatarDoubleParaMoedaReal(subTotal), 0, qtdLinhasDebitoImpressas * 24);
    				qtdLinhasDebitoImpressas++;
    				
    			}
    			
    			if ( (totalLinhas - i) > 11 ){
    				subTotal += conta.getValor();
    			
    			}else{
    				comando += formarLinha(7, 0, 83, 510, Util.formatarAnoMesParaMesAno(conta.getAnoMes() + ""), 0, qtdLinhasDebitoImpressas * 24)+
    						   formarLinha(7, 0, 405, 510, Util.dateToString(conta.getDataVencimento()), 0, qtdLinhasDebitoImpressas * 24)+
    						   formarLinha(7, 0, 707, 510, Util.formatarDoubleParaMoedaReal(conta.getValor()), 0, qtdLinhasDebitoImpressas * 24);
    				qtdLinhasDebitoImpressas++;
    				
    			}
    			
    		}else {
    			comando += formarLinha(7, 0, 83, 510, Util.formatarAnoMesParaMesAno(conta.getAnoMes() + ""), 0, qtdLinhasDebitoImpressas * 24)+
    					   formarLinha(7, 0, 405, 510, Util.dateToString(conta.getDataVencimento()), 0, qtdLinhasDebitoImpressas * 24)+
    					   formarLinha(7, 0, 707, 510, Util.formatarDoubleParaMoedaReal(conta.getValor()), 0, qtdLinhasDebitoImpressas * 24);
    			qtdLinhasDebitoImpressas++;
    		}
    	}

    	comando += dividirLinha(7, 0, 37, (546 + (qtdLinhasDebitoImpressas*24)) , "Pertencente(s) ao imovel localizado na " + imovel.getEndereco() + ".", 61, 24)+
    	
    			dividirLinha(7, 0, 37, (606 + (qtdLinhasDebitoImpressas*24)) , "De acordo com a legislacao em vigor (Lei numero 11.445/07), o nao pagamento " +
    			"deste debito nos autoriza a suspender o forne-cimento de agua/esgoto para o seu imovel", 61, 24)+
    	
    			dividirLinha(7, 0, 37, (690 + (qtdLinhasDebitoImpressas*24)) , "Estamos a sua disposicao em nossas lojas de atendimento, escri-torios regionais e " +
    			"no telefone 0800-7071-195, ligacao gratuita, para esclarecer qualquer duvida.", 63, 24)+
    	
    			dividirLinha(7, 0, 37, (774 + (qtdLinhasDebitoImpressas*24)) , "Caso o(s) debito(s) ja esteja(m) quitado(s), pedimos desculpas e que desconsidere este aviso.", 62, 24)+
    	
    			formarLinha(0, 2, 155, 1265, "Numero do Documento de Notificacao de Debito: ", 0, 0)+
    			formarLinha(5, 0, 555, 1265, imovel.getNumeroDocumentoNotificacaoDebito(), 0, 0)+
    			"FORM\n" + "PRINT ";
    	
    	return comando;
    }
    
    public void getDadosAvisoDebito(Imovel imovel) {

		if (imovel.getAnoMesConta().compareTo(Constantes.NULO_STRING) != 0){
			descricaoAnoMesConta = "T 7 1 669 90 "+ Util.retornaDescricaoAnoMes(imovel.getAnoMesConta()) + "\n";
			
		}else{
			descricaoAnoMesConta = "T 7 1 669 90 "+ Util.retornaDescricaoAnoMes(ControladorRota.getInstancia().getDadosGerais().getAnoMesFaturamento()) + "\n";
		}
		
    	List dc = imovel.getDadosCategoria();
    	List quantidadeEconomias = categoriasEconomias(dc);
    	
    	situacaoAgua = imovel.getDescricaoSitLigacaoAgua(Integer.valueOf(imovel.getSituacaoLigAgua()));
    	situacaoEsgoto = imovel.getDescricaoSitLigacaoEsgoto(Integer.valueOf(imovel.getSituacaoLigEsgoto()));
    	consumoAgua = imovel.getConsumoAgua();
        consumoEsgoto = imovel.getConsumoEsgoto();;
    	
    	String cpfCnpjFormatado = "";
 	    if (imovel.getCpfCnpjCliente() != null && !imovel.getCpfCnpjCliente().equals("")) {
 	    	cpfCnpjFormatado = imovel.getCpfCnpjCliente().trim();
 	    }
    	if (imovel.getEnderecoEntrega().trim().length() == 0){
		    endereco = 	formarLinha(0, 2, 52, 172, imovel.getNomeUsuario().trim(), 0, 0) + formarLinha(0, 2, 52, 199, cpfCnpjFormatado, 0, 0) + dividirLinha(0, 2, 434, 169, imovel.getEndereco(), 40, 27);
	    }else{
		    endereco =	formarLinha(0, 2, 52, 172, imovel.getNomeUsuario().trim(), 0, 0) + formarLinha(0, 2, 52, 199, cpfCnpjFormatado, 0, 0) + dividirLinha(0, 2, 434, 169, imovel.getEnderecoEntrega(), 40, 27);
	    }
	    
	    for (int i = 0; i < quantidadeEconomias.size(); i++) {
			Object[] dadosCategoria = (Object[]) quantidadeEconomias.get(i);
			economias += formarLinha(0, 0, 470, 254, dadosCategoria[0] + "", i * 85, 0);
			economias += formarLinha(7, 0, 539, 250, dadosCategoria[1] + "", i * 85, 0);
	    }
    	
    	medidorAgua = imovel.getMedidor(Constantes.LIGACAO_AGUA);
    	medidorPoco = imovel.getMedidor(Constantes.LIGACAO_POCO);
    	
	    if (medidorAgua != null) {
			numeroMedidor = medidorAgua.getNumeroHidrometro();
			dataInstalacao = Util.dateToString(medidorAgua.getDataInstalacao());

	    } else if (medidorPoco != null) {

			numeroMedidor = medidorPoco.getNumeroHidrometro();
			dataInstalacao = Util.dateToString(medidorPoco.getDataInstalacao());
	    }
    }

    private static String dividirLinha(int fonte, int tamanhoFonte, int x, int y, String texto, int tamanhoLinha, int deslocarPorLinha) {
    	String retorno = "";
    	int contador = 0;
    	int i;
    	
    	for (i = 0; i < texto.length(); i += tamanhoLinha) {
    	    contador += tamanhoLinha;
    	    
    	    if (contador > texto.length()) {
    	    	retorno += "T " + fonte + " " + tamanhoFonte + " " + x + " " + y + " " + texto.substring(i, texto.length()).trim() + "\n";
    	    
    	    } else {
    	    	retorno += "T " + fonte + " " + tamanhoFonte + " " + x + " " + y + " " + texto.substring(i, contador).trim() + "\n";
    	    }
    	    y += deslocarPorLinha;
    	}
    	
    	return retorno;
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
			    linhas += formarLinha(7, 0, 53, 710, "AGUA", 0, qtdLinhas * 25);
			    qtdLinhas++;
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
				linhas += formarLinha(7, 0, 63, 710, descricao.substring(0, 40), 0, qtdLinhas * 25);
			    } else {
				linhas += formarLinha(7, 0, 63, 710, descricao, 0, qtdLinhas * 25);
			    }
			} else {
			    descricao = dadosEconomiasSubcategorias.getDescricaoAbreviadaSubcategoria() + " " + quantidaEconomias + " " + "UNIDADE(S)";
			    // 3.3.2.1, 3.3.1.2 e 3.3.2.2, 3.3.3
			    if (descricao.length() > 40) {
				linhas += formarLinha(7, 0, 63, 710, descricao.substring(0, 40), 0, qtdLinhas * 25);
			    } else {
				linhas += formarLinha(7, 0, 63, 710, descricao, 0, qtdLinhas * 25);
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
			    linhas += formarLinha(7, 0, 63, 710, descricao, 0, qtdLinhas * 25);
			    descricao = consumoMinimo + " m3";
			    // 3.4.3
			    linhas += formarLinha(7, 0, 571, 710, descricao, 0, qtdLinhas * 25);
			    // 3.4.4
			    linhas += formarLinha(7, 0, 697, 710, Util.formatarDoubleParaMoedaReal(dadosEconomiasSubcategorias.getFaturamentoAgua().getValorTarifaMinima()), 0, qtdLinhas * 25);
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
				    linhas += formarLinha(7, 0, 73, 710, descricao, 0, qtdLinhas * 25);
				    // 3.5.1.2.3
				    linhas += formarLinha(7, 0, 571, 710, dadosEconomiasSubcategorias.getFaturamentoAgua().getConsumoMinimo() + " m3", 0, qtdLinhas * 25);
				    // 3.5.1.2.4
				    linhas += formarLinha(7, 0, 697, 710, Util.formatarDoubleParaMoedaReal(dadosEconomiasSubcategorias.getFaturamentoAgua().getValorTarifaMinima()), 0, qtdLinhas * 25);
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
					    linhas += formarLinha(7, 0, 73, 710, descricao, 0, qtdLinhas * 25);
					    // 3.5.1.2.5.1.3.1
					    linhas += formarLinha(7, 0, 571, 710, faixa.getConsumoFaturado() * quantidaEconomias + " m3 ", 0, qtdLinhas * 25);
					    // 3.5.1.2.5.1.3.2
					    // 3.5.1.2.5.1.4
					    linhas += formarLinha(7, 0, 697, 710, Util.formatarDoubleParaMoedaReal(faixa.getValorFaturado() * quantidaEconomias), 0, qtdLinhas * 25);
					    // 3.5.1.2.5.2
					} else {
					    // 3.5.1.2.5.2.2.1, 3.5.1.2.5.2.2.2,
					    // 3.5.1.2.5.2.2.3, 3.5.1.2.5.2.2.4,
					    // 3.5.1.2.5.2.2.5, 3.5.1.2.5.2.2.6
					    descricao = faixa.getLimiteInicialConsumo() + " m3 A " + faixa.getLimiteFinalConsumo() + " m3 - R$ " + Util.formatarDoubleParaMoedaReal(faixa.getValorTarifa()) + " POR M3 ";
					    // 3.5.1.2.5.2.3.1
					    linhas += formarLinha(7, 0, 73, 710, descricao, 0, qtdLinhas * 25);
					    // 3.5.1.2.5.1.3.2
					    linhas += formarLinha(7, 0, 571, 710, faixa.getConsumoFaturado() * quantidaEconomias + " m3 ", 0, qtdLinhas * 25);
					    // 3.5.1.2.5.1.4.1
					    linhas += formarLinha(7, 0, 697, 710, Util.formatarDoubleParaMoedaReal(faixa.getValorFaturado() * quantidaEconomias), 0, qtdLinhas * 25);
					}
				    }
	//			}
			    } else {
				if (dadosEconomiasSubcategorias.getFaturamentoAgua() != null) {
				    qtdLinhas++;
				    descricao = "CONSUMO DE AGUA";
				    // 3.5.1.1.2.1
				    linhas += formarLinha(7, 0, 73, 710, descricao, 0, qtdLinhas * 25);
				    linhas += formarLinha(7, 0, 571, 710, ((int) dadosEconomiasSubcategorias.getFaturamentoAgua().getConsumoFaturado()) + " m3", 0, qtdLinhas * 25);
				    // 3.5.1.1.2.2
				    linhas += formarLinha(7, 0, 697, 710, Util.formatarDoubleParaMoedaReal(dadosEconomiasSubcategorias.getFaturamentoAgua().getValorFaturado()), 0, qtdLinhas * 25);
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
		    dados[0] += " % DO VALOR DE AGUA";
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
		    dados[0] = "RATEIO DE AGUA DO CONDOMINIO";
		    dados[1] = Util.formatarDoubleParaMoedaReal(imovel.getValorRateioAgua());
		    retorno.add(dados);
		}

		// Valor do rateio de Esgoto
		if (imovel.getConsumoEsgoto() != null && imovel.getValorRateioEsgoto() > 0) {

		    dados = new String[2];
		    dados[0] = "RATEIO DE ESGOTO DO CONDOMINIO";
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
    	    DadosCategoria categoria = (DadosCategoria) regsTipo2.get(i);
    	    String descricaoCategoria = categoria.getDescricaoCategoria();
    	    if (descricaoCategoria.length() > 8) {
    		descricaoCategoria = descricaoCategoria.substring(0, 8);
    	    }
    	    switch (categoria.getCodigoCategoria()) {
    	    case DadosCategoria.RESIDENCIAL:
    		quantidadeEconomiasResidencial += categoria.getQtdEconomiasSubcategoria();
    		descricaoResidencial = descricaoCategoria;
    		break;
    	    case DadosCategoria.COMERCIAL:
    		quantidadeEconomiasComercial += categoria.getQtdEconomiasSubcategoria();
    		descricaoComercial = descricaoCategoria;
    		break;
    	    case DadosCategoria.INDUSTRIAL:
    		quantidadeEconomiasIndustrial += categoria.getQtdEconomiasSubcategoria();
    		descricaoIndustrial = descricaoCategoria;
    		break;
    	    case DadosCategoria.PUBLICO:
    		quantidadeEconomiasPublico += categoria.getQtdEconomiasSubcategoria();
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
    
    private static int retornaIndicadorDiscriminar(int quantidadeMaximaLinhas, int quantidadeLinhasAtual, char servicos) {
    	int indicadorDiscriminarDescricao = 1;
    	int linhasRestantesDescricao = 0;
    	switch (servicos) {
    	case 'd':
    	    // linhas que ainda aparecerão depois do débio (crédito e imposto)
    	    // linhas de crédito
    	    if (imovel.getCreditos(Constantes.SIM).size() > 0) {
    		linhasRestantesDescricao = linhasRestantesDescricao + 1;
    	    }
    	    // linhas de imposto
    	    if (imovel.getImpostos().size() > 0) {
    		linhasRestantesDescricao = linhasRestantesDescricao + 2;
    	    }
    	    // linhas de débito
    	    if (imovel.getDebitos(Constantes.SIM).size() > 0) {
    		// linhasRestantesDescricao = linhasRestantesDescricao + 1;
    		int limiteDescriminar = quantidadeMaximaLinhas - quantidadeLinhasAtual - linhasRestantesDescricao;
    		int quantidadeDebitos = imovel.getDebitos(Constantes.SIM).size();
    		if (quantidadeDebitos > limiteDescriminar) {
    		    indicadorDiscriminarDescricao = 2;
    		}
    	    }
    	    break;
    	case 'c':
    	    // linhas que ainda aparecerão depois do débio (crédito e imposto)
    	    // linhas de imposto
    	    if (imovel.getImpostos().size() > 0) {
    		linhasRestantesDescricao = linhasRestantesDescricao + 2;
    	    }
    	    // linhas de credito
    	    if (imovel.getCreditos(Constantes.SIM).size() > 0) {
    		// linhasRestantesDescricao = linhasRestantesDescricao + 1;
    		int limiteDescriminar = quantidadeMaximaLinhas - quantidadeLinhasAtual - linhasRestantesDescricao;
    		int quantidadeCreditos = imovel.getCreditos(Constantes.SIM).size();
    		if (quantidadeCreditos > limiteDescriminar) {
    		    indicadorDiscriminarDescricao = 2;
    		}
    	    }
    	    break;
    	}
    	return indicadorDiscriminarDescricao;
        }
    
    /**
     * [SB0005] - Gerar Linhas da Debitos Cobrados
     * 
     * @return Os dados estão dividos em 3 partes Descricao, de indice 0
     *         Consumo, de indice 1 Valor, de indice 2
     */
    private List gerarLinhasDebitosCobrados(int indicadorDiscriminarDescricao) {
	List retorno = new ArrayList();
	// Os dados estão dividos em 3 partes
	// Descricao, de indice 0
	// Consumo, de indice 1
	// Valor, de indice 2
	String[] dados = new String[3];
	// 3
	if (imovel.getDebitos(Constantes.SIM).size() > 0) {
	    // caso seja para discriminar os dados dos débitos
	    if (indicadorDiscriminarDescricao == 1) {
		for (int i = 0; i < imovel.getDebitos(Constantes.SIM).size(); i++) {
		    dados = new String[3];
		    Debito dadosDebitosCobrados = imovel.getDebitos(Constantes.SIM).get(i);
		    // 1.1.2
		    dados[0] = dadosDebitosCobrados.getDescricao();
		    // 1.1.3
		    dados[2] = Util.formatarDoubleParaMoedaReal(dadosDebitosCobrados.getValor());
		    retorno.add(dados);
		}
	    } else {
		double soma = 0d;
		for (int i = 0; i < imovel.getDebitos(Constantes.SIM).size(); i++) {
		    Debito dadosDebitosCobrados = imovel.getDebitos(Constantes.SIM).get(i);
		    soma += dadosDebitosCobrados.getValor();
		}
		dados = new String[3];
		// 1.1.2
		dados[0] = "DEBITOS";
		// 1.1.3
		dados[2] = Util.formatarDoubleParaMoedaReal(soma);
		retorno.add(dados);
	    }
	}
	return retorno;
    }
    
    /**
     * [SB0006] - Gerar Linhas de Creditos Realizados
     * 
     * @return Os dados estão dividos em 3 partes Descricao, de indice 0
     *         Consumo, de indice 1 Valor, de indice 2
     */
    private List gerarLinhasCreditosRealizados(int indicadorDiscriminarDescricao) {
	List retorno = new ArrayList();
	// Os dados estão dividos em 3 partes
	// Descricao, de indice 0
	// Consumo, de indice 1
	// Valor, de indice 2
	String[] dados = new String[3];
	// 3
	if (imovel.getCreditos(Constantes.SIM).size() > 0) {
	    // caso seja para discriminar os dados dos créditos
	    if (indicadorDiscriminarDescricao == 1) {
		// caso o valor do crédito seja maior que o valor da conta sem o
		// crédito
		double valorContaSemCreditos = 0d;
		double valorContaResidual = 0d;
		boolean valorCreditoMaiorValorConta = false;
		boolean naoEmitirMaisCreditos = false;
		if (imovel.getValorResidualCredito() != 0d) {
		    valorContaSemCreditos = imovel.getValorContaSemCreditos();
		    valorCreditoMaiorValorConta = true;
		}
		for (int i = 0; i < imovel.getCreditos(Constantes.SIM).size(); i++) {
		    Credito dadosCreditosRealizado = imovel.getCreditos(Constantes.SIM).get(i);
		    // caso o valor dos créditos n seja maior que o valor da
		    // conta sem os créditos
		    
		    //Verificar se o imovel deve ou nao considerar Bonus Social.
	    	if( ( (imovel.getCreditos(Constantes.SIM).get(i))).getCodigo().equalsIgnoreCase(Imovel.CODIGO_BONUS_SOCIAL)  &&
		    		Integer.parseInt(imovel.getCodigoPerfil()) == Imovel.PERFIL_BONUS_SOCIAL &&
		    		imovel.getConsumoAgua() != null &&
		    		imovel.getConsumoAgua().getConsumoCobradoMes() > 10 ){
		    		
		    		continue;
	    	}
	    	
		    if (!valorCreditoMaiorValorConta) {
		    	dados = new String[3];
				// 1.1.2
				dados[0] = dadosCreditosRealizado.getDescricao();
				// 1.1.3
				dados[2] = Util.formatarDoubleParaMoedaReal(dadosCreditosRealizado.getValor());
				retorno.add(dados);

		    }
		    // //caso o valor dos créditos seja maior que o valor das
		    // contas sem os créditos
		    else {
				if (!naoEmitirMaisCreditos) {
				    double valorCredito = dadosCreditosRealizado.getValor();
				    valorContaResidual = valorContaSemCreditos - valorCredito;
				    // emite as créditos até o valor dos creditos ser
				    // menor que o valor da conta
				    if (valorContaResidual < 0d) {
//				    	valorContaResidual = valorContaResidual * -1;
				    	naoEmitirMaisCreditos = true;
	
					    dados = new String[3];
					    // 1.1.2
					    dados[0] = dadosCreditosRealizado.getDescricao();
					    // 1.1.3
	//				    dados[2] = Util.formatarDoubleParaMoedaReal(valorCredito);
					    dados[2] = Util.formatarDoubleParaMoedaReal(valorContaSemCreditos);
					    
					    retorno.add(dados);
	
				    }else{
				    	
				    	valorContaSemCreditos = valorContaSemCreditos - valorCredito;

				    	dados = new String[3];
					    // 1.1.2
					    dados[0] = dadosCreditosRealizado.getDescricao();
					    // 1.1.3
					    dados[2] = Util.formatarDoubleParaMoedaReal(valorCredito);
	//				    dados[2] = Util.formatarDoubleParaMoedaReal(valorContaSemCreditos);
					    
					    retorno.add(dados);			    	
				    }
				}
		    }
		}
	    } else {
		double soma = imovel.getValorCreditos();
		// for ( int i = 0; i < imovel.getRegistros5().size(); i++ ){
		//
		// RegistroDescricaoValor dadosCreditosRealizado = (
		// RegistroDescricaoValor ) imovel.getRegistros5().elementAt( i
		// );
		// soma += dadosCreditosRealizado.getValor();
		// }
		dados = new String[3];
		// 1.1.2
		dados[0] = "CREDITOS";
		// 1.1.3
		dados[2] = Util.formatarDoubleParaMoedaReal(soma);
		retorno.add(dados);
	    }
	}
	return retorno;
    }
    
    /**
     * [SB0007] - Gerar Linhas Impostos Retidos
     * 
     * @return Os dados estão dividos em 3 partes Descricao, de indice 0
     *         Consumo, de indice 1 Valor, de indice 2
     */
    private List gerarLinhasImpostosRetidos() {
	List retorno = new ArrayList();
	// Os dados estão dividos em 3 partes
	// Descricao, de indice 0
	// Consumo, de indice 1
	// Valor, de indice 2
	String[] dados = new String[3];
	// 3
	if (imovel.getImpostos().size() > 0) {
	    String dadosImposto = "";
	    for (int i = 0; i < imovel.getImpostos().size(); i++) {
		Imposto imoReg6 = imovel.getImpostos().get(i);
		String descricaoImposto = imoReg6.getDescricaoImposto();
		String percentualAliquota = Util.formatarDoubleParaMoedaReal(imoReg6.getPercentualAlicota());
		dadosImposto += descricaoImposto + "-" + percentualAliquota + "% ";
	    }
	    dados = new String[3];
	    // 1.1.2
	    dados[0] = "DED. IMPOSTOS LEI FEDERAL N.9430 DE 27/12/1996";
	    // 1.1.3
	    dados[2] = Util.formatarDoubleParaMoedaReal(imovel.getValorImpostos());
	    retorno.add(dados);
	    dados = new String[3];
	    // 1.1.2
	    dados[0] = dadosImposto;
	    retorno.add(dados);
	}
	return retorno;
    }
    
}
