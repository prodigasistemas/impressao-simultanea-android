package business;

import static util.Constantes.REGISTRO_TIPO_ANORMALIDADE;
import static util.Constantes.REGISTRO_TIPO_CONTA;
import static util.Constantes.REGISTRO_TIPO_CREDITO;
import static util.Constantes.REGISTRO_TIPO_DADOS_CATEGORIA;
import static util.Constantes.REGISTRO_TIPO_DEBITO;
import static util.Constantes.REGISTRO_TIPO_GERAL;
import static util.Constantes.REGISTRO_TIPO_HISTORICO_CONSUMO;
import static util.Constantes.REGISTRO_TIPO_IMOVEL;
import static util.Constantes.REGISTRO_TIPO_IMPOSTO;
import static util.Constantes.REGISTRO_TIPO_MEDIDOR;
import static util.Constantes.REGISTRO_TIPO_TARIFACAO_COMPLEMENTAR;
import static util.Constantes.REGISTRO_TIPO_TARIFACAO_MINIMA;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import model.Anormalidade;
import model.Consumo;
import model.Conta;
import model.DadosGerais;
import model.Imovel;
import model.Medidor;
import model.SituacaoTipo;
import util.Constantes;
import util.Util;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import dataBase.DataManipulator;

public class ControladorImovel {

    public static ControladorImovel instancia;

    private boolean permissionGranted = false;
    private int qtdRegistros = 0;
    private static int linhasLidas = 0;
    private static int isRotaCarregadaOk = Constantes.NAO;

    private static Imovel imovelSelecionado = new Imovel();
    private static Conta contaSelecionado = new Conta();
    private static Medidor medidorSelecionado = new Medidor();
    private static DadosGerais dadosGerais = new DadosGerais();
    private static Vector anormalidades = new Vector();
    
    private static long idImovelSelecionado = 0;
    private static int cadastroListPosition = -1;
    
    private static Vector anormalidadesIndicadorUso1;
    private static Vector anormalidadesSemIndicadorUso1;
    private static int LEITURA_CONFIRMADA = 99;

	DataManipulator dataManipulator;
    
    public static ControladorImovel getInstancia() {

    	if (ControladorImovel.instancia == null) {
			ControladorImovel.instancia = new ControladorImovel();

		}
		return ControladorImovel.instancia;
    }

    public Imovel getImovelSelecionado(){
    	return ControladorImovel.imovelSelecionado;
    }
    
    public Conta getContaSelecionado(){
    	return ControladorImovel.contaSelecionado;
    }

    public Medidor getMedidorSelecionado(){
    	return ControladorImovel.medidorSelecionado;
    }
//    
//    public Servicos getServicosSelecionado(){
//    	return Controlador.servicosSelecionado;
//    }
//    
    public DadosGerais getDadosGerais(){
    	return ControladorImovel.dadosGerais;
    }
    
    public Vector getAnormalidades(){
    	return ControladorImovel.anormalidades;
    }
    
//    public AnormalidadeImovel getAnormalidadeImovelSelecionado(){
//    	return Controlador.anormalidadeImovelSelecionado;
//    }
//    
//    public Registro getRamosAtividade(){
//    	return Controlador.ramosAtividade;
//    }
//    
//    public void setClienteSelecionado(Cliente clienteSelecionado){
//    	Controlador.clienteSelecionado = clienteSelecionado;
//    }
//    
//    public void setImovelSelecionado(Imovel imovelSelecionado){
//    	Controlador.imovelSelecionado = imovelSelecionado;
//    }
//    
    public void setContaSelecionado(Conta contaSelecionado){
    	ControladorImovel.contaSelecionado = contaSelecionado;
    }

    public void setMedidorSelecionado(Medidor medidorSelecionado){
    	ControladorImovel.medidorSelecionado = medidorSelecionado;
    }
//    
//    public void setServicosSelecionado(Servicos servicosSelecionado){
//    	Controlador.servicosSelecionado = servicosSelecionado;
//    }
//    
//    public void setAnormalidadeImovelSelecionado(AnormalidadeImovel anormalidadeImovelSelecionado){
//    	Controlador.anormalidadeImovelSelecionado = anormalidadeImovelSelecionado;
//    }
//    
    public void setDadosGerais(DadosGerais dadosGerais){
    	ControladorImovel.dadosGerais = dadosGerais;
    }
    
    public void setAnormalidades(Vector anormalidades){
    	ControladorImovel.anormalidades = anormalidades;
    }
//    
//    public void setRamosAtividade(Registro ramosAtividade){
//    	Controlador.ramosAtividade = ramosAtividade;
//    }
     
    public void setImovelSelecionadoByListPosition(int listPosition){
    	initImovelTabs();
    	setImovelListPosition(listPosition);
    	idImovelSelecionado = getIdImovelSelecionado(listPosition, null);
//    	dmImovel.selectCliente(idImovelSelecionado);
//    	dmImovel.selectImovel(idImovelSelecionado);
//    	dmImovel.selectServico(idImovelSelecionado);
//    	dmImovel.selectMedidor(idImovelSelecionado);
//    	dmImovel.selectAnormalidadeImovel(idImovelSelecionado);
    }
    
    public void setImovelSelecionadoByListPositionInConsulta(int listPositionInConsulta, String condition){
    	initImovelTabs();
    	idImovelSelecionado = getIdImovelSelecionado(listPositionInConsulta, condition);
    	setImovelListPosition(getImovelListPositionById(idImovelSelecionado));

//    	dmImovel.selectCliente(idImovelSelecionado);
//    	dmImovel.selectImovel(idImovelSelecionado);
//    	dmImovel.selectServico(idImovelSelecionado);
//    	dmImovel.selectMedidor(idImovelSelecionado);
//    	dmImovel.selectAnormalidadeImovel(idImovelSelecionado);
    }
    
    public void setImovelSelecionado(long id){
    	initImovelTabs();
    	idImovelSelecionado = id;
//    	dmImovel.selectCliente(idImovelSelecionado);
//    	dmImovel.selectImovel(idImovelSelecionado);
//    	dmImovel.selectServico(idImovelSelecionado);
//    	dmImovel.selectMedidor(idImovelSelecionado);
//    	dmImovel.selectAnormalidadeImovel(idImovelSelecionado);
    }
    
    public void initImovelTabs(){
//        clienteSelecionado = new Cliente();
//        imovelSelecionado = new Imovel();
//        medidorSelecionado = new Medidor();
//        servicosSelecionado = new Servicos();
//        anormalidadeImovelSelecionado = new AnormalidadeImovel();
    }
    
    public int getIdImovelSelecionado(int listPosition, String condition){
    	// se for cadastro novo
    	if (listPosition == -1){
    		return 0;
 
    	}else{
        	return Integer.parseInt(ControladorImovel.getInstancia().getDataManipulator().selectIdImoveis(condition).get(listPosition));
     	}
    }
    
    public int getImovelListPositionById(long id){
    	int position = 0;
    	ArrayList<String> listIds = (ArrayList<String>) ControladorImovel.getInstancia().getDataManipulator().selectIdImoveis(null);
    	
    	for(int i = 0; i < listIds.size(); i++){
    		if (id == Long.parseLong(listIds.get(i))){
    			position = i;
    			break;
    		}
      	}
    	return position;
    }
    
    public boolean isImovelAlterado(){
    	boolean result = false;
    	
    	// guarda instancia de cliente, imovel, medidor e servico 
//    	Cliente clienteEditado = clienteSelecionado;
//    	Imovel imovelEditado = imovelSelecionado;
//    	Servicos servicoEditado = servicosSelecionado;
//    	Medidor medidorEditado = medidorSelecionado;
//    	AnormalidadeImovel anormalidadeImovelEditado = anormalidadeImovelSelecionado;
//    	
//    	// atualiza as instancias clienteSelecionado, imovelSelecionado, servicoSelecionado e medidorSelecionado com os valores do banco de dados.
//    	dmImovel.selectCliente(idImovelSelecionado);
//    	dmImovel.selectImovel(idImovelSelecionado);
//    	dmImovel.selectServico(idImovelSelecionado);
//    	dmImovel.selectMedidor(idImovelSelecionado);
//    	dmImovel.selectAnormalidadeImovel(idImovelSelecionado);
    	
//    	if (clienteEditado != clienteSelecionado){
//    		result = true;
//    	
//    	}else if (imovelEditado != imovelSelecionado){
//    		result = true;
//    		
//    	}else if (servicoEditado != servicosSelecionado){
//    		result = true;
//    		
//    	}else if (medidorEditado != medidorSelecionado){
//    		result = true;
//    		
//    	}else if (anormalidadeImovelEditado != anormalidadeImovelSelecionado){
//    		result = true;
//    		
//    	}
    	
    	// Restaura os valores editados nas instancias selecionadas
    	if(result){
//        	clienteSelecionado = clienteEditado;
//        	imovelSelecionado = imovelEditado;
//        	servicosSelecionado = servicoEditado;
//        	medidorSelecionado  = medidorEditado;
//        	anormalidadeImovelSelecionado  = anormalidadeImovelEditado;
    	}
    	return result;
    }
    
    /**
     * Carrega o vetor de imóveis e salva cada registro no SQlite database.
     * 
     * @param input
     *            Vetor onde cada elemento uma linha do arquivo de entrada.
     */
    public void carregarDadosParaRecordStore(BufferedReader input, Handler mHandler, Context context) {
		String line = "";
		int matricula = 0;
		
		if (input != null){
			
			try {

			    Bundle b = new Bundle();

			    while((line = input.readLine()) != null) {                         
				    					
				    if (linhasLidas == 0) {
						qtdRegistros = Integer.parseInt(line);
						linhasLidas++;
						continue;
				    }
				    
					linhasLidas++;

					Log.i("Linha", "Linha :" + line);

				    int tipoRegistro = Integer.parseInt(line.substring(0, 2));
				    
//				    Log.i("Tipo linha", ""+tipoRegistro);
				    
				    switch (tipoRegistro) {
				    
						case REGISTRO_TIPO_IMOVEL:
							dataManipulator.insertImovel(line);
					    	dataManipulator.insertSituacaoTipo(SituacaoTipo.getInstancia());
							break;
							
						case REGISTRO_TIPO_DADOS_CATEGORIA:
							
							break;
							
						case REGISTRO_TIPO_HISTORICO_CONSUMO:
							dataManipulator.insertHistoricoConsumo(line);
							break;
							
						case REGISTRO_TIPO_DEBITO:
							dataManipulator.insertDebito(line);
							break;
							
						case REGISTRO_TIPO_CREDITO:
							dataManipulator.insertCredito(line);
							break;
							
						case REGISTRO_TIPO_IMPOSTO:
							
							break;
							
						case REGISTRO_TIPO_CONTA:
							dataManipulator.insertConta(line);
							break;
							
						case REGISTRO_TIPO_MEDIDOR:
							dataManipulator.insertMedidor(line);
							break;
							
						case REGISTRO_TIPO_TARIFACAO_MINIMA:
							dataManipulator.insertTarifacaoMinima(line);
							break;
							
						case REGISTRO_TIPO_TARIFACAO_COMPLEMENTAR:
							dataManipulator.insertTarifacaoComplementar(line);
							break;
							
						case REGISTRO_TIPO_GERAL:
							dataManipulator.insertDadosGerais(line);
							break;
							
						case REGISTRO_TIPO_ANORMALIDADE:
							dataManipulator.insertAnormalidade(line);
							break;
	
						default:
							Log.e("Erro no switch - classe Controlador", String.format("tipo registro: %d", tipoRegistro));
							break;
					}

				    
				    if (linhasLidas < qtdRegistros){
				        // Send message (with current value of total as data) to Handler on UI thread
				        // so that it can update the progress bar.
				        Message msg = mHandler.obtainMessage();
				        b.putInt("total", linhasLidas);
				        msg.setData(b);
				        mHandler.sendMessage(msg);
			        }
				}

			    // Define Rota Carregada com sucesso.
		    	setRotaCarregamentoOk(Constantes.SIM);
		    	
		        // Send message (with current value of total as data) to Handler on UI thread
		        // so that it can update the progress bar.
		        Message msg = mHandler.obtainMessage();
		        b.putInt("total", linhasLidas);
		        msg.setData(b);
		        mHandler.sendMessage(msg);

				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    
	public int getQtdRegistros() {
    	return qtdRegistros;
	}

    public void setPermissionGranted(boolean state){
    	this.permissionGranted = state;
    }

    public boolean isPermissionGranted(){
    	return this.permissionGranted;
    }

    public void initiateDataManipulator(Context context){

    	if (dataManipulator == null){
	    	dataManipulator = new DataManipulator(context);
			dataManipulator.open();
    	}
    }    
    public void finalizeDataManipulator(){
    	if (dataManipulator != null){
	    	dataManipulator.close();
	    	dataManipulator = null;
    	}
    }
    
    public DataManipulator getDataManipulator(){
    	return dataManipulator;
    }
    
    // Retorna o Id do cadastro selecionado
    public long getIdImovelSelecionado(){
    	return idImovelSelecionado;
    }
    
    // Retorna a posição do cadastro selecionado na lista de cadastros ordenada por inscrição
    public int getImovelListPosition(){
    	return cadastroListPosition;
    }
    
    // Guarda a posição do cadastro selecionado na lista de cadastros ordenada por inscrição
    public void setImovelListPosition(int position){
    	this.cadastroListPosition = position;
//    	dataManipulator.updateConfiguracao("posicao_cadastro_selecionado", position);
    }
    
    public boolean databaseExists(Context context){
    	File dbFile = new File(Constantes.DATABASE_PATH + Constantes.DATABASE_NAME);

    	initiateDataManipulator(context);
    	
     	return (dbFile.exists() && dataManipulator.selectAnormalidades().size() > 0);
	}
    
    public int isDatabaseRotaCarregadaOk(){

    	if (dataManipulator.selectConfiguracaoElement("rota_carregada") == Constantes.SIM){
    		this.isRotaCarregadaOk = Constantes.SIM;
    	}

     	return this.isRotaCarregadaOk;
	}
    
    public void setRotaCarregamentoOk(int isRotaCarregadaOk){
//    	dataManipulator.updateConfiguracao("rota_carregada", Constantes.SIM);
    }
    
    public void deleteDatabase(){
        String strDBFilePath= Constantes.DATABASE_PATH + Constantes.DATABASE_NAME;
        File file = new   File(strDBFilePath);
        file.delete();
	}    

    /**
     * [UC0743] Calcular Valores de Água/Esgoto
     */
    public void calcularValores(Imovel imovel, Consumo consumo,int tipoMedicao) {
    }

    /**
     * Carrega as anormalidades no controlador Imoveis
     */
    public static Vector carregarAnormalidades(boolean apenasComIndicadorUso1) throws IOException {
	
		if ( apenasComIndicadorUso1 && anormalidadesIndicadorUso1 != null ){
		    return anormalidadesIndicadorUso1;
		
		} else if ( !apenasComIndicadorUso1 && anormalidadesSemIndicadorUso1 != null ){
		    return anormalidadesSemIndicadorUso1;
		
		} else {	
		    
			// carrega os id's de cada anormalidade
			Vector anors = carregarVetorAnormalidades(ControladorImovel.anormalidades, apenasComIndicadorUso1);
			
		    if (apenasComIndicadorUso1) {
		    	anormalidadesIndicadorUso1 = anors;
		    } else {
		    	anormalidadesSemIndicadorUso1 = anors;
		    }

		    return anors;
		    
		}
    }

    /**
     * Carrega o array que mapeia o indice da anormalidade no identificador.
     * 
     * @param anormalidades
     *            Vetor de anormalidades.
     */
    public static Vector carregarVetorAnormalidades(Vector anormalidades, boolean apenasComIndicadorUso1) {
		
    	if (anormalidades != null){
	    	
    		int len = anormalidades.size();
			
			// Daniel- corrigindo vetor de anormalidades	
			Vector retorno = new Vector();
			Anormalidade anor = new Anormalidade();
	
			int contador = 1;
		
			// Daniel- corrigindo vetor de anormalidades	
			for (int i = 1; i < len+1 ; i++) {
			    Anormalidade reg14 = (Anormalidade) anormalidades.elementAt(i-1);
			    
			    // Daniel - Descarta Anormalidade LEITURA_CONFIRMADA - Usuario não pode usar tal opção.
			    if (reg14.getCodigo() == LEITURA_CONFIRMADA){
			    	len = len -1;
			    	continue;
			    }
				
			    anor = reg14;
			    retorno.add(anor);
			}

			if (apenasComIndicadorUso1) {
			    Vector retornoIndicadorUsoSim = new Vector();
			    
			    // Daniel- corrigindo vetor de anormalidades	
			    for (int i = 0; i < len+1; i++) {
					if ( ((Anormalidade)retorno.elementAt(i)).getIndcUso() == Constantes.SIM ) {
					    retornoIndicadorUsoSim.add(retorno.elementAt(i));
					}
			    }

			    return retornoIndicadorUsoSim;
			
			} else {
			    return retorno;
			}
    	}
    	return null;
    }

    /**
     * Carrega as anormalidades direto da Tabela Anormalidade.
     * 
     * @return vetor com as anormalidades
     */
    public static Vector getAnormalidades(boolean apenasComIndicadorUso1){	
    	Vector anormalidades = null;
	
		try {
		    if (DadosGerais.getInstancia().getCodigoEmpresaFebraban().equals(Constantes.CODIGO_FEBRABAN_COSANPA)) {
		    	anormalidades = carregarAnormalidades(apenasComIndicadorUso1);
			}
		} catch (IOException e) {
		    e.printStackTrace();
	//	    Util.mostrarErro("Erro ao carregar arquivo de anormalidade");
		}
	
		return anormalidades;
    }

}
