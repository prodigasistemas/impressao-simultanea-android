package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Consumo;
import model.DadosCategoria;
import model.DadosFaturamento;
import model.DadosFaturamentoFaixa;
import model.DadosGerais;
import model.Imovel;
import model.Imposto;
import model.Medidor;
import util.Constantes;
import util.Util;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import business.ControladorImovel;
import business.ControladorRota;

import com.IS.Fachada;

public class ArquivoRetorno {
	
    private static ArquivoRetorno instancia;
    private static StringBuffer arquivo;

    private StringBuffer registrosTipoImovel = null;
    private StringBuffer registroTipoDadosCategoria = null;
    private StringBuffer registrosTipoImposto = null;
    private StringBuffer registroTipo0 =  null;
    private boolean linhaTipo1Anexada;

    
    private ArquivoRetorno() {
    	super();
    }
	
    public static ArquivoRetorno getInstancia() {
    	if (instancia == null) {
    	    instancia = new ArquivoRetorno();
    	}
    	return instancia;
    }
    
    public StringBuffer gerarDadosImovelParaEnvio(Imovel i){

    	arquivo = new StringBuffer();
		
    	Imovel imovel = ControladorRota.getInstancia().getDataManipulator().selectImovel("id = " + i.getId());
    	gerarRegistroTipoImovel(imovel);
    	gerarRegistroDadosCategoria(imovel);
    	gerarRegistroTipoDebito(imovel);
    		    
    	return arquivo;
    }

    public void gerarArquivoCompleto(Handler mHandler, Context context, int increment) {

    	try {
    		
            File fileArquivoCompleto = new File(Util.getRetornoRotaDirectory(), Util.getRotaFileName());
            
            if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Toast.makeText(context, "Erro ao salvar no cartão de memória!", Toast.LENGTH_SHORT).show();
                return;
            }

            FileOutputStream os = new FileOutputStream(fileArquivoCompleto); 
            OutputStreamWriter out = new OutputStreamWriter(os);

		    arquivo = new StringBuffer();
		    
		    ArrayList<String> listIdImoveis = (ArrayList<String>) ControladorRota.getInstancia().getDataManipulator().selectIdImoveis(null);
		    
		    for (int i = 0; i < listIdImoveis.size(); i++){

		    	Imovel imovel = ControladorRota.getInstancia().getDataManipulator().selectImovel("id = " + (i+1));
		    	
		    	if (imovel.isImovelInformativo())
		    		continue;
		    	
		    	gerarRegistroTipo0( Constantes.INDC_FINALIZAR_ROTEIRO_TODOS_IMOVEIS, imovel);
		    	
		    	if ( i == 0 ){
		    		arquivo = arquivo.append(registroTipo0);
		    	 }

		    	gerarRegistroTipoImovel(imovel);
		    	gerarRegistroDadosCategoria(imovel);
		    	gerarRegistroTipoDebito(imovel);
		    	
		        Bundle b = new Bundle();
		        // Send message (with current value of total as data) to Handler on UI thread
		        // so that it can update the progress bar.
		        Message msg = mHandler.obtainMessage();
		        b.putInt("arquivoCompleto" + String.valueOf(increment), (i+1));
		        msg.setData(b);
		        mHandler.sendMessage(msg);
		    }
		    
			out.write(arquivo.toString());
			out.close();
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}    
    }
    
    // Gera StringBuffer contendo apenas cadastros concluidos e nao transmitidos
    public StringBuffer gerarDadosFinalizacaoRotaOnline(Handler mHandler, Context context, int increment) {

	    arquivo = new StringBuffer();
	    
	    String filterCondition = "(imovel_enviado = " + Constantes.NAO + ")";	    				
	    
	    ArrayList<String> listIdImoveis = (ArrayList<String>) ControladorRota.getInstancia().getDataManipulator().selectIdImoveis(filterCondition);
	    
	    for (int i = 0; i < listIdImoveis.size(); i++){

//	       	Controlador.getInstancia().getCadastroDataManipulator().selectCliente(Long.parseLong(listIdImoveis.get(i)));
//	    	Controlador.getInstancia().getCadastroDataManipulator().selectImovel(Long.parseLong(listIdImoveis.get(i)));
//	    	Controlador.getInstancia().getCadastroDataManipulator().selectServico(Long.parseLong(listIdImoveis.get(i)));
//	       	Controlador.getInstancia().getCadastroDataManipulator().selectMedidor(Long.parseLong(listIdImoveis.get(i)));
//	    	Controlador.getInstancia().getCadastroDataManipulator().selectAnormalidadeImovel(Long.parseLong(listIdImoveis.get(i)));

//	    	gerarRegistroTipoImovel();
	    	
	        Bundle b = new Bundle();
	        // Send message (with current value of total as data) to Handler on UI thread
	        // so that it can update the progress bar.
	        Message msg = mHandler.obtainMessage();
	        b.putInt("finalizacao" + String.valueOf(increment), (i+1));
	        msg.setData(b);
	        mHandler.sendMessage(msg);
	    }
	    
	    return arquivo;		    
    }
    
    private void gerarRegistroTipoImovel(Imovel imovel) {

    	registrosTipoImovel = new StringBuffer();
    	
    	String anormalidadeLeitura = "";
	    Date dataLeitura = new Date();
	    
	    String anoMesFaturamento = ControladorRota.getInstancia().getDataManipulator().selectInformacoesRota().get(4);
	    
	    Medidor medidorAgua = imovel.getMedidor(Constantes.LIGACAO_AGUA);
	    Medidor medidorPoco = imovel.getMedidor(Constantes.LIGACAO_POCO);
	    
	    Consumo consumoAgua = imovel.getConsumoAgua();
		Consumo consumoEsgoto = imovel.getConsumoEsgoto();
		boolean temAgua = false;
		boolean temEsgoto = false;
	    
		linhaTipo1Anexada = true;
		if ( (consumoAgua != null || (medidorAgua != null && !medidorAgua.equals(""))) &&
				 (consumoAgua != null && consumoAgua.getAnormalidadeConsumo() != 0) ){
			
			temAgua = true;
	
		    String indicadorSituacao = "" + Constantes.LEITURA_REALIZADA;
	
		    if (medidorAgua != null && medidorAgua.getLeitura() >= medidorAgua.getLeituraEsperadaInicial() && medidorAgua.getLeitura() <= medidorAgua.getLeituraEsperadaFinal()) {
		    	indicadorSituacao = "" + Constantes.LEITURA_REALIZADA;
		    } else {
		    	if (medidorAgua != null) {
		    		indicadorSituacao = "" + Constantes.LEITURA_CONFIRMADA;
		    	}
		    }
	
		    if (medidorAgua == null && medidorPoco == null && imovel.getDataImpressaoNaoMedido() != null){
		    	dataLeitura = imovel.getDataImpressaoNaoMedido();
		    
		    }else if (medidorAgua != null && !medidorAgua.equals("")) {
				anormalidadeLeitura = "" + medidorAgua.getAnormalidade();
				dataLeitura = medidorAgua.getDataLeitura();
		    
		    } else if(medidorPoco != null && !medidorPoco.equals("") && medidorPoco.getDataLeitura() != null) {
		    	dataLeitura = medidorPoco.getDataLeitura();
		    }
		    
    	// Tipo de Registro
    	registrosTipoImovel.append("1"); 
    	// Matricula
    	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getMatricula()+ "")); 
    	// Tipo de Medição
    	registrosTipoImovel.append("1"); 
	    // Ano/Mês do faturamento
    	registrosTipoImovel.append(Util.formatarAnoMesParaMesAnoSemBarra(anoMesFaturamento)); 
	    // Número da Conta
	    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getNumeroConta() + "")); 
	    // Grupo de faturamento
	    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, imovel.getGrupoFaturamento() + "")); 
	    // Código da rota
	    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(7, imovel.getCodigoRota() + "")); 
	    // Leitura do Hidrômetro
	    registrosTipoImovel.append(Util.adicionarCharEsquerda(7, (imovel.getMedidor(Constantes.LIGACAO_AGUA) != null ? imovel.getMedidor(Constantes.LIGACAO_AGUA).getLeitura() + "" : ""), ' ')); 
	    // Anormalidade de Leitura
	    registrosTipoImovel.append(Util.adicionarCharEsquerda(2, anormalidadeLeitura.equals(Constantes.NULO_INT+"") ? "0" : anormalidadeLeitura, ' ')); 
	    // Data e hora da leitura
	    registrosTipoImovel.append(Util.adicionarCharEsquerda(26, (Util.formatarData(dataLeitura)), ' ')); 
	    // Indicador de situação da leitura
	    registrosTipoImovel.append(indicadorSituacao); 
	    // Leitura de faturamento
	    registrosTipoImovel.append(Util.adicionarCharEsquerda(7, consumoAgua != null ? consumoAgua.getLeituraAtual() + "" : "", ' ')); 
	    // Consumo Medido no Mes
	    registrosTipoImovel.append(Util.adicionarCharEsquerda(6, consumoAgua != null ? consumoAgua.getConsumoMedidoMes() + "": "", ' ')); 
	    // Consumo Cobrado no Mes
	    registrosTipoImovel.append(Util.adicionarCharEsquerda(6, consumoAgua != null ? consumoAgua.getConsumoCobradoMes() + "": "", ' ')); 
	    // Consumo Rateio Agua
	    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(6, imovel.getConsumoRateioAgua() > 0 ? imovel.getConsumoRateioAgua() + "": "")); 
	    // Valor Rateio Agua
	    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(8, imovel.getValorRateioAgua() > 0 ? imovel.getValorRateioAgua() + "": "")); 
	    // Consumo Rateio Esgoto
	    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(6, imovel.getConsumoRateioEsgoto() > 0 ? imovel.getConsumoRateioEsgoto() + "" : "") ); 
	    // Valor Rateio Esgoto
	    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(8, imovel.getValorRateioEsgoto() > 0 ? imovel.getValorRateioEsgoto() + "" : "") ); 
	    // Tipo de Consumo
	    registrosTipoImovel.append(Util.adicionarCharEsquerda(2, consumoAgua != null ? consumoAgua.getTipoConsumo() + "": "", ' ') ); 
	    // Anormalidade de Consumo
	    registrosTipoImovel.append(Util.adicionarCharEsquerda(2, consumoAgua != null ? consumoAgua.getAnormalidadeConsumo() + "": "", ' ') ); 
	    // Indicador de emissao de conta
	    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(1, imovel.getIndcImovelImpresso() + "") ); 
	    // Inscricao do Imovel
	    registrosTipoImovel.append(imovel.getInscricao()); 
	    // Indicador Conta Retida
	    registrosTipoImovel.append(imovel.getIndcGeracaoConta()); 
	    // Consumo Imóveis MICRO Sem Rateio
	    registrosTipoImovel.append(Util.adicionarCharEsquerda(6, consumoAgua != null ? consumoAgua.getConsumoCobradoMesImoveisMicro() + "": "", ' ') ); 
	    // Anormalidade de faturamento
	    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(2, consumoAgua != null ? consumoAgua.getAnormalidadeLeituraFaturada() + "": "")); 
	    // ID do documento de cobrança
	    registrosTipoImovel.append(Util.adicionarCharEsquerda(9,imovel.getNumeroDocumentoNotificacaoDebito(), ' ')); 
	    // Leitura Anterior do Hidrômetro
	    registrosTipoImovel.append(Util.adicionarCharEsquerda(7, (medidorAgua != null ? medidorAgua.getLeituraAnterior() + "" : ""), ' ')); 
	    // Versao do I.S. em uso
	    registrosTipoImovel.append(Util.adicionarCharEsquerda(12, Fachada.getAppVersion(), ' ')); 
	    registrosTipoImovel.append("\n"); 
	
	    System.out.println(registrosTipoImovel);

//    	arquivo.append(registrosTipoImovel);
		}
		
		if ( (consumoEsgoto != null || (medidorPoco != null && !medidorPoco.equals(""))) && 
				 (consumoEsgoto != null && consumoEsgoto.getAnormalidadeConsumo() != 0) ) {
				
				temEsgoto = true;
		
			    String indicadorSituacao = "" + Constantes.LEITURA_REALIZADA;
		
			    if (medidorPoco != null && medidorPoco.getLeitura() >= medidorPoco.getLeituraEsperadaInicial() && medidorPoco.getLeitura() <= medidorPoco.getLeituraEsperadaFinal()) {
			    	indicadorSituacao = "" + Constantes.LEITURA_REALIZADA;
			    } else {
					if (medidorPoco != null) {
					    indicadorSituacao = "" + Constantes.LEITURA_CONFIRMADA;
					}
			    }
		
			    if (medidorPoco != null && !medidorPoco.equals("")) {
			    	anormalidadeLeitura = "" + medidorPoco.getAnormalidade();
			    	dataLeitura = medidorPoco.getDataLeitura();
			    } else {
					if (imovel.getAnormalidadeSemHidrometro() != Constantes.NULO_INT) {
					    anormalidadeLeitura = "" + imovel.getAnormalidadeSemHidrometro();
					}
					if (medidorAgua != null && !medidorAgua.equals("") && medidorAgua.getDataLeitura() != null) {
					    dataLeitura = medidorAgua.getDataLeitura();
					}
			    }
		
		    	// Tipo de Registro
	    		registrosTipoImovel.append("1"); 
	    		// Matricula
	    		registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getMatricula() + "")); 
			    // Tipo de Medição
	    		registrosTipoImovel.append("2"); 
	    		// Ano/Mês do faturamento
	    		registrosTipoImovel.append(Util.formatarAnoMesParaMesAnoSemBarra(anoMesFaturamento)); 
			    // Número da Conta
	    		registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getNumeroConta() + "") ); 
			    // Grupo de faturamento
	    		registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, imovel.getGrupoFaturamento() + "") ); 
			    // Código da rota
	    		registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(7, imovel.getCodigoRota() + "") ); 
			    // Leitura do Hidrômetro
	    		registrosTipoImovel.append(Util.adicionarCharEsquerda(7, (medidorPoco != null ? medidorPoco.getLeitura() + "" : ""), ' ') );  
			    // Anormalidade de Leitura
	    		registrosTipoImovel.append(Util.adicionarCharEsquerda(2, anormalidadeLeitura.equals(Constantes.NULO_INT+"") ? "0" : anormalidadeLeitura, ' ')); 
			    // Data e hora da leitura
	    		registrosTipoImovel.append(Util.adicionarCharEsquerda(26, (Util.formatarData(dataLeitura)), ' ') ); 
			    // Indicador de situação da leitura
	    		registrosTipoImovel.append(indicadorSituacao); 
			    // Leitura de faturamento
	    		registrosTipoImovel.append(Util.adicionarCharEsquerda(7, consumoEsgoto != null ? consumoEsgoto.getLeituraAtual() + "": "", ' ') ); 
			    // Consumo Medido no Mes
	    		registrosTipoImovel.append(Util.adicionarCharEsquerda(6, consumoEsgoto != null ? consumoEsgoto.getConsumoMedidoMes() + "": "", ' ') ); 
			    // Consumo Cobrado no Mes
	    		registrosTipoImovel.append(Util.adicionarCharEsquerda(6, consumoEsgoto != null ? consumoEsgoto.getConsumoCobradoMes() + "": "", ' ') ); 
			    // Consumo Rateio Agua
			    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(6, imovel.getConsumoRateioAgua() > 0 ? imovel.getConsumoRateioAgua() + "": "")); 
			    // Valor Rateio Agua
			    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(8, imovel.getValorRateioAgua() > 0 ? imovel.getValorRateioAgua() + "": "")); 
			    // Consumo Rateio Esgoto
			    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(6, imovel.getConsumoRateioEsgoto() > 0 ? imovel.getConsumoRateioEsgoto() + "" : "") ); 
			    // Valor Rateio Esgoto
			    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(8, imovel.getValorRateioEsgoto() > 0 ? imovel.getValorRateioEsgoto() + "" : "") ); 
			    // Tipo de Consumo
	    		registrosTipoImovel.append(Util.adicionarCharEsquerda(2, consumoEsgoto != null ? consumoEsgoto.getTipoConsumo() + "": "", ' ') ); 
			    // Anormalidade de Consumo
	    		registrosTipoImovel.append(Util.adicionarCharEsquerda(2, consumoEsgoto != null ? consumoEsgoto.getAnormalidadeConsumo() + "": "", ' ') ); 
			    // Indicador de emissao de conta
	    		registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(1, imovel.getIndcImovelImpresso() + "") ); 
			    // Inscricao do Imovel
	    		registrosTipoImovel.append(imovel.getInscricao() ); 
	    		// Indicador Conta Retida
	    		registrosTipoImovel.append(imovel.getIndcGeracaoConta() ); 
			    // Consumo Imóveis MICRO Sem Rateio
	    		registrosTipoImovel.append(Util.adicionarCharEsquerda(6, consumoEsgoto != null ? consumoEsgoto.getConsumoCobradoMesImoveisMicro() + "": "", ' ') ); 
			    // Anormalidade de faturamento
	    		registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(2, consumoEsgoto != null ? consumoEsgoto.getAnormalidadeLeituraFaturada() + "": "")); 
	    		// ID do documento de cobrança
			    registrosTipoImovel.append(Util.adicionarCharEsquerda(9,imovel.getNumeroDocumentoNotificacaoDebito(), ' ')); 
			    // Leitura Anterior do Hidrômetro
			    registrosTipoImovel.append(Util.adicionarCharEsquerda(7, (medidorPoco != null ? medidorPoco.getLeituraAnterior() + "" : ""), ' ')); 
			    // Versao do I.S. em uso
			    registrosTipoImovel.append(Util.adicionarCharEsquerda(12, Fachada.getAppVersion(), ' ')); 
	    		registrosTipoImovel.append("\n"); 
		
			    System.out.println(registrosTipoImovel);
			}
		
		// Caso nao tenha consumo nem de agua nem de esgoto
				// geramos um registro tipo 1 apenas com os débitos.
				if (!temAgua && !temEsgoto) {
				    // Tipo de Registro
				    registrosTipoImovel.append("1"); 
				    // Matricula
				    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getMatricula() + "")); 
				    // Tipo de Medição
				    registrosTipoImovel.append("1" ); 
				    // Ano/Mês do faturamento
			    	registrosTipoImovel.append(Util.formatarAnoMesParaMesAnoSemBarra(anoMesFaturamento)); 
				    // Número da Conta
				    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getNumeroConta() + "")); 
				    // Grupo de faturamento
				    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, imovel.getGrupoFaturamento() + "") ); 
				    // Código da rota
				    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(7, imovel.getCodigoRota() + "")); 
				    // Leitura do Hidrômetro
				    registrosTipoImovel.append(Util.adicionarCharEsquerda(7, (medidorAgua != null ? medidorAgua.getLeitura() + "" : ""), ' ') ); 
				    // Anormalidade de Leitura
				    registrosTipoImovel.append(
					    Util.adicionarCharEsquerda(
						    2, ( imovel.getAnormalidadeSemHidrometro() != Constantes.NULO_INT ? imovel.getAnormalidadeSemHidrometro() + "" : (medidorAgua != null ? medidorAgua.getAnormalidade() + "" : "")), ' ') ); 
				    // Data e hora da leitura
				    registrosTipoImovel.append(Util.adicionarCharEsquerda(26, Util.formatarData(new Date()), ' ') ); 
				    // Indicador de situação da leitura
				    registrosTipoImovel.append(" " ); 
				    // Leitura de faturamento
				    registrosTipoImovel.append(Util.adicionarCharEsquerda(7, (medidorAgua != null ? medidorAgua.getLeitura() + "" : ""), ' ')); 
				    // Consumo Medido no Mes
				    registrosTipoImovel.append(Util.adicionarCharEsquerda(6, " ", ' ') ); 
				    // Consumo Cobrado no Mes
				    registrosTipoImovel.append(Util.adicionarCharEsquerda(6, " ", ' ') ); 
				    // Consumo Rateio Agua
				    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(6, "0") ); 
				    // Valor Rateio Agua
				    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(8, "0") ); 
				    // Consumo Rateio Esgoto
				    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(6, "0") ); 
				    // Valor Rateio Esgoto
				    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(8, "0") ); 
				    // Tipo de Consumo
				    registrosTipoImovel.append(Util.adicionarCharEsquerda(2, " ", ' ')); 
				    // Anormalidade de Consumo
				    registrosTipoImovel.append(Util.adicionarCharEsquerda(2, " ", ' ')); 
				    // Indicador de emissao de conta
				    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(1, imovel.getIndcImovelImpresso() + "")); 
				    // Inscricao do Imovel
				    registrosTipoImovel.append(imovel.getInscricao()); 
				    // Indicador Conta Retida
				    registrosTipoImovel.append(imovel.getIndcGeracaoConta()); 
				    // Consumo Imóveis MICRO Sem Rateio
				    registrosTipoImovel.append(Util.adicionarCharEsquerda(6, "", ' ')); 
				    // Anormalidade de faturamento
				    registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(2, "")); 
				    // ID do documento de cobrança
				    registrosTipoImovel.append(Util.adicionarCharEsquerda(9,imovel.getNumeroDocumentoNotificacaoDebito(), ' ')); 
				    // Leitura Anterior do Hidrômetro
				    registrosTipoImovel.append(Util.adicionarCharEsquerda(7, (medidorAgua != null ? medidorAgua.getLeituraAnterior() + "" : ""), ' ')); 
				    // Versao do I.S. em uso
				    registrosTipoImovel.append(Util.adicionarCharEsquerda(12, Fachada.getAppVersion(), ' ')); 
				    registrosTipoImovel.append("\n");
				
				    System.out.println(registrosTipoImovel);
				
				}

				arquivo.append(registrosTipoImovel);
    }
    
    
    private void gerarRegistroDadosCategoria(Imovel imovel) {


    	registroTipoDadosCategoria = new StringBuffer();
    	
    	List<DadosCategoria> registrosTipoDadosCategoria = imovel.getDadosCategoria();
	
    	if (registrosTipoDadosCategoria != null) {
	
    	    for (int i = 0; i < registrosTipoDadosCategoria.size(); i++) {
	
	    		DadosCategoria dadosCategoria = (DadosCategoria) registrosTipoDadosCategoria.get(i);
		
	    		DadosFaturamento faturamentoAgua = new DadosFaturamento();
		
	    		if (dadosCategoria.getFaturamentoAgua() != null) {
	    		    faturamentoAgua = dadosCategoria.getFaturamentoAgua();
	    		}
		
	    		DadosFaturamento faturamentoEsgoto = new DadosFaturamento();
		
	    		if (dadosCategoria.getFaturamentoEsgoto() != null) {
	    		    faturamentoEsgoto = dadosCategoria.getFaturamentoEsgoto();
	    		}
		
	    		// Tipo de registro (1)
	    		registroTipoDadosCategoria.append("2");
	    		// Matrícula (9);
	    		registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getMatricula() + ""));
	    		// Código da categoria (1)
	    		registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(1, dadosCategoria.getCodigoCategoria() + ""));
	    		// Código da subcategoria (3)    		
	    		short indicadorTarifa = ControladorRota.getInstancia().getDadosGerais().getIndcTarifaCatgoria();
	    		String codigoSubcategoria = "";
	    		if (indicadorTarifa == DadosGerais.CALCULO_POR_CATEGORA) {
	    		    codigoSubcategoria = Util.adicionarCharEsquerda(3, "0", ' ');
	    		} else {
	    		    codigoSubcategoria = Util.adicionarCharEsquerda(3, dadosCategoria.getCodigoSubcategoria(), ' ');
	    		}
				
	    		registroTipoDadosCategoria.append(codigoSubcategoria);
	    		// Valor faturado de água na categoria (13,2)
	    		registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faturamentoAgua.getValorFaturado())));
	    		// Consumo faturado de água na categoria (6);
	    		registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(6, faturamentoAgua.getConsumoFaturado() + ""));
	    		// Valor da tarifa mínima de água da categoria (13,2);
	    		registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faturamentoAgua.getValorTarifaMinima())));
	    		// Consumo mínimo de água da categoria (6);
	    		registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(6, faturamentoAgua.getConsumoMinimo() + ""));
	    		// Valor faturado de esgoto na categoria (13,2)
	    		registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faturamentoEsgoto.getValorFaturado())));
	    		// Consumo faturado de esgoto na categoria (6);
	    		registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(6, faturamentoEsgoto.getConsumoFaturado() + ""));
	    		// Valor da tarifa mínima de esgoto da categoria (13,2);
	    		registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faturamentoEsgoto.getValorTarifaMinima())));
	    		// Consumo mínimo de esgoto da categoria (6);
	    		registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(6, faturamentoEsgoto.getConsumoMinimo() + ""));
    			// Numero de vezes que a conta foi impressa
    			registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(2, String.valueOf(imovel.getQuantidadeContasImpressas())));
    			// Valores das contas impressas
//    			for (int count = 0; count < imovel.getValoresContasImpressas().size(); count++){
//	    			registroTipoDadosCategoriaEHistoricoConsumo.append(Util.adicionarZerosEsquerdaNumero(10, Util.formatarDoubleParaMoedaReal(Double.parseDouble((String)imovel.getValoresContasImpressas().elementAt(count)))));
//    			}
	    		registroTipoDadosCategoria.append("\n");
		
	    		List faixasAgua = faturamentoAgua.getFaixas();
	    		List faixasEsgoto = faturamentoEsgoto.getFaixas();
		
	    		if (faixasAgua != null) {
	    		    for (int j = 0; j < faixasAgua.size(); j++) {
		    			DadosFaturamentoFaixa faixaAgua = (DadosFaturamentoFaixa) faixasAgua.get(j);
			
		    			DadosFaturamentoFaixa faixaEsgoto = new DadosFaturamentoFaixa();
			
		    			if (faixasEsgoto != null && !faixasEsgoto.isEmpty()) {
		    			    
		    			    for (int k = 0; k < faixasEsgoto.size(); k++) {
			
			    				faixaEsgoto = (DadosFaturamentoFaixa) faixasEsgoto.get(k);
				
			    				if (faixaAgua.equals(faixaEsgoto)) {
			    				    break;
			    				}
		    			    }
		    			}
			
		    			// Tipo de registro (1)
		    			registroTipoDadosCategoria.append("3");
		    			// Matrícula (9);
		    			registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getMatricula() + ""));
		    			// Código da categoria (1)
		    			registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(1, dadosCategoria.getCodigoCategoria() + ""));
		    			// Código da subcategoria (3)
		    			registroTipoDadosCategoria.append(Util.adicionarCharEsquerda(3, dadosCategoria.getCodigoSubcategoria(), ' '));
		    			// Consumo faturado de água na faixa (6)
		    			registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(6, faixaAgua.getConsumoFaturado() + ""));
		    			// Valor faturado de água na faixa (13,2)
		    			registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faixaAgua.getValorFaturado())));
		    			// Limite inicial de consumo na faixa (6);
		    			registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(6, faixaAgua.getLimiteInicialConsumo() + ""));
		    			// Limite final de consumo na faixa (6);
		    			registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(6, faixaAgua.getLimiteFinalConsumo() + ""));
		    			// Valor da tarifa de água na faixa (13,2);
		    			registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faixaAgua.getValorTarifa())));
		    			// Valor da tarifa de esgoto na faixa (13,2);
		    			registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faixaEsgoto.getValorTarifa())));
		    			// Consumo Faturado de esgoto na faixa (6)
		    			registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(6, faixaEsgoto.getConsumoFaturado() + ""));
		    			// Valor faturado de esgoto na faixa (13,2)
		    			registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faixaEsgoto.getValorFaturado())));
		    			registroTipoDadosCategoria.append("\n");
	    		    }
	    		} else if (faixasEsgoto != null) {
		
	    		    DadosFaturamentoFaixa faixaEsgoto = new DadosFaturamentoFaixa();
		
	    		    for (int k = 0; k < faixasEsgoto.size(); k++) {
		
		    			faixaEsgoto = (DadosFaturamentoFaixa) faixasEsgoto.get(k);
			
		    			// Tipo de registro (1)
		    			registroTipoDadosCategoria.append("3");
		    			// Matrícula (9);
		    			registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getMatricula() + ""));
		    			// Código da categoria (1)
		    			registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(1, dadosCategoria.getCodigoCategoria() + ""));
		    			// Código da subcategoria (3)
		    			registroTipoDadosCategoria.append(Util.adicionarCharEsquerda(3, dadosCategoria.getCodigoSubcategoria(), ' '));
		    			// Consumo faturado de água na faixa (6)
		    			registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(6, ""));
		    			// Valor faturado de água na faixa (13,2)
		    			registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(16, ""));
		    			// Limite inicial de consumo na faixa (6);
						registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(6, faixaEsgoto.getLimiteInicialConsumo() + ""));
						// Limite final de consumo na faixa (6);
						registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(6, faixaEsgoto.getLimiteFinalConsumo() + ""));
						// Valor da tarifa de água na faixa (13,2);
						registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(16, ""));
						// Valor da tarifa de esgoto na faixa (13,2);
						registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faixaEsgoto.getValorTarifa())));
						// Consumo Faturado de esgoto na faixa (6)
						registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(6, faixaEsgoto.getConsumoFaturado() + ""));
						// Valor faturado de esgoto na faixa (13,2)
						registroTipoDadosCategoria.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(faixaEsgoto.getValorFaturado())));
						registroTipoDadosCategoria.append("\n");
	    		    }
	    		}
    	    }
    	}
    	
    	arquivo.append(registroTipoDadosCategoria);
    
    }
    
    private void gerarRegistroTipoDebito(Imovel imovel) {

    	registrosTipoImposto = new StringBuffer();

    	List<Imposto> impostos = imovel.getImpostos();
	
    	if (impostos != null) {
    	    for (int i = 0; i < impostos.size(); i++) {
    		Imposto reg6 = impostos.get(i);
		
    		// Tipo de registro (1)
    		registrosTipoImposto.append("4");
				
    		// Matricula (9)
    		registrosTipoImposto.append(Util.adicionarZerosEsquerdaNumero(9, imovel.getMatricula() + ""));
    		
    		// Tipo de Imposto(1)
    		registrosTipoImposto.append(Util.adicionarZerosEsquerdaNumero(1, reg6.getTipoImposto() + ""));
    		// Descrição do imposto (15);
    		registrosTipoImposto.append(Util.adicionarCharEsquerda(15, reg6.getDescricaoImposto(), ' '));
    		// Percentual da Alíquota (6);
    		registrosTipoImposto.append(Util.adicionarZerosEsquerdaNumero(6, Util.formatarDoubleParaMoedaReal(reg6.getPercentualAlicota())));
    		// Valor do Imposto (13,2)
    		double valorImposto = imovel.getValorContaSemImposto() * Util.arredondar((reg6.getPercentualAlicota() / 100), 7);
    		registrosTipoImposto.append(Util.adicionarZerosEsquerdaNumero(16, Util.formatarDoubleParaMoedaReal(valorImposto)) );
    		registrosTipoImposto.append("\n");
    	    }
    	}
    	
    	arquivo.append(registrosTipoImposto);
    }
    
    private void gerarRegistroTipo0(int tipoFinalizacao, Imovel imovel) {

    	registroTipo0 = new StringBuffer();
		
    	// Tipo de registro (1)
    	registroTipo0.append("0");				
    	// Tipo de finalização (1)
    	registroTipo0.append(tipoFinalizacao + "");    	
    	// Localidade(3)
    	registroTipo0.append(Util.adicionarZerosEsquerdaNumero(3, imovel.getLocalidade() + ""));    	
    	//Setor Comercial(3)
    	registroTipo0.append(Util.adicionarZerosEsquerdaNumero(3, imovel.getSetorComercial() + ""));				
    	// Rota (3);
    	registroTipo0.append(Util.adicionarZerosEsquerdaNumero(7, imovel.getCodigoRota() + ""));
// Daniel - Id da Rota   	
    	if (ControladorRota.getInstancia().getDadosGerais().getIdRota() != 9999){
        	registroTipo0.append(Util.adicionarZerosEsquerdaNumero(4, ControladorRota.getInstancia().getDadosGerais().getIdRota() + ""));		
    	}

// Daniel - Indicador de rota dividida    	
    	if (ControladorRota.getInstancia().getDadosGerais().getIndicadorRotaDividida() != 999999){
        	registroTipo0.append(Util.adicionarZerosEsquerdaNumero(2, ControladorRota.getInstancia().getDadosGerais().getIndicadorRotaDividida() + ""));		
    	}else{
        	registroTipo0.append(Util.adicionarZerosEsquerdaNumero(2, "00"));		
    	}
    	
    	registroTipo0.append("\n");
    	
    }
    
    public static Imovel getImovelSelecionado(){
    	return ControladorImovel.getInstancia().getImovelSelecionado();
    }

}
