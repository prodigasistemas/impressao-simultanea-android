package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import background.CarregarRotaThread;

import com.IS.Fachada;

import dataBase.DataManipulator;

//import model.AnormalidadeImovel;
//import model.Registro;
//import model.Cliente;
import model.Conta;
import model.DadosGerais;
import model.Medidor;
//import model.Imovel;
//import model.Medidor;
//import model.Servicos;

import ui.ArquivoRetorno;
import ui.MessageDispatcher;
import util.Constantes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Controlador {

    public static Controlador instancia;
    private boolean permissionGranted = false;
    private int qtdRegistros = 0;
    private static int linhasLidas = 0;
    
//    private static Cliente clienteSelecionado = new Cliente();
//    private static Imovel imovelSelecionado = new Imovel();
//    private static Servicos servicosSelecionado = new Servicos();
    private static Conta contaSelecionado = new Conta();
    private static Medidor medidorSelecionado = new Medidor();
    private static DadosGerais dadosGerais = new DadosGerais();
//    private static Registro anormalidades = new Registro();
//    private static AnormalidadeImovel anormalidadeImovelSelecionado = new AnormalidadeImovel();
//    private static Registro ramosAtividade = new Registro();
//    
    
    private static long idCadastroSelecionado = 0;
    private static int cadastroListPosition = -1;

    private static int isRotaCarregadaOk = Constantes.NAO;
    
	DataManipulator dmCadastro;
    
    public static Controlador getInstancia() {

    	if (Controlador.instancia == null) {
			Controlador.instancia = new Controlador();

		}
		return Controlador.instancia;
    }

//    public Cliente getClienteSelecionado(){
//    	return Controlador.clienteSelecionado;
//    }
//    
//    public Imovel getImovelSelecionado(){
//    	return Controlador.imovelSelecionado;
//    }
//    
    public Conta getContaSelecionado(){
    	return Controlador.contaSelecionado;
    }

    public Medidor getMedidorSelecionado(){
    	return Controlador.medidorSelecionado;
    }
//    
//    public Servicos getServicosSelecionado(){
//    	return Controlador.servicosSelecionado;
//    }
//    
    public DadosGerais getDadosGerais(){
    	return Controlador.dadosGerais;
    }
//    
//    public Registro getAnormalidades(){
//    	return Controlador.anormalidades;
//    }
//    
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
    	Controlador.contaSelecionado = contaSelecionado;
    }

    public void setMedidorSelecionado(Medidor medidorSelecionado){
    	Controlador.medidorSelecionado = medidorSelecionado;
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
    	Controlador.dadosGerais = dadosGerais;
    }
//    
//    public void setAnormalidades(Registro anormalidades){
//    	Controlador.anormalidades = anormalidades;
//    }
//    
//    public void setRamosAtividade(Registro ramosAtividade){
//    	Controlador.ramosAtividade = ramosAtividade;
//    }
     
    public void setCadastroSelecionadoByListPosition(int listPosition){
    	initCadastroTabs();
    	setCadastroListPosition(listPosition);
    	idCadastroSelecionado = getIdCadastroSelecionado(listPosition, null);
//    	dmCadastro.selectCliente(idCadastroSelecionado);
//    	dmCadastro.selectImovel(idCadastroSelecionado);
//    	dmCadastro.selectServico(idCadastroSelecionado);
//    	dmCadastro.selectMedidor(idCadastroSelecionado);
//    	dmCadastro.selectAnormalidadeImovel(idCadastroSelecionado);
    }
    
    public void setCadastroSelecionadoByListPositionInConsulta(int listPositionInConsulta, String condition){
    	initCadastroTabs();
    	idCadastroSelecionado = getIdCadastroSelecionado(listPositionInConsulta, condition);
    	setCadastroListPosition(getCadastroListPositionById(idCadastroSelecionado));

//    	dmCadastro.selectCliente(idCadastroSelecionado);
//    	dmCadastro.selectImovel(idCadastroSelecionado);
//    	dmCadastro.selectServico(idCadastroSelecionado);
//    	dmCadastro.selectMedidor(idCadastroSelecionado);
//    	dmCadastro.selectAnormalidadeImovel(idCadastroSelecionado);
    }
    
    public void setCadastroSelecionado(long id){
    	initCadastroTabs();
    	idCadastroSelecionado = id;
//    	dmCadastro.selectCliente(idCadastroSelecionado);
//    	dmCadastro.selectImovel(idCadastroSelecionado);
//    	dmCadastro.selectServico(idCadastroSelecionado);
//    	dmCadastro.selectMedidor(idCadastroSelecionado);
//    	dmCadastro.selectAnormalidadeImovel(idCadastroSelecionado);
    }
    
    public void initCadastroTabs(){
//        clienteSelecionado = new Cliente();
//        imovelSelecionado = new Imovel();
//        medidorSelecionado = new Medidor();
//        servicosSelecionado = new Servicos();
//        anormalidadeImovelSelecionado = new AnormalidadeImovel();
    }
    
    public int getIdCadastroSelecionado(int listPosition, String condition){
    	// se for cadastro novo
    	if (listPosition == -1){
    		return 0;
 
    	}else{
        	return Integer.parseInt(Controlador.getInstancia().getCadastroDataManipulator().selectIdImoveis(condition).get(listPosition));
     	}
    }
    
    public int getCadastroListPositionById(long id){
    	int position = 0;
    	ArrayList<String> listIds = (ArrayList<String>) Controlador.getInstancia().getCadastroDataManipulator().selectIdImoveis(null);
    	
    	for(int i = 0; i < listIds.size(); i++){
    		if (id == Long.parseLong(listIds.get(i))){
    			position = i;
    			break;
    		}
      	}
    	return position;
    }
    
    public boolean isCadastroAlterado(){
    	boolean result = false;
    	
    	// guarda instancia de cliente, imovel, medidor e servico 
//    	Cliente clienteEditado = clienteSelecionado;
//    	Imovel imovelEditado = imovelSelecionado;
//    	Servicos servicoEditado = servicosSelecionado;
//    	Medidor medidorEditado = medidorSelecionado;
//    	AnormalidadeImovel anormalidadeImovelEditado = anormalidadeImovelSelecionado;
//    	
//    	// atualiza as instancias clienteSelecionado, imovelSelecionado, servicoSelecionado e medidorSelecionado com os valores do banco de dados.
//    	dmCadastro.selectCliente(idCadastroSelecionado);
//    	dmCadastro.selectImovel(idCadastroSelecionado);
//    	dmCadastro.selectServico(idCadastroSelecionado);
//    	dmCadastro.selectMedidor(idCadastroSelecionado);
//    	dmCadastro.selectAnormalidadeImovel(idCadastroSelecionado);
    	
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
				    
				    Log.i("Tipo linha", ""+tipoRegistro);
				    
				    if (tipoRegistro == Constantes.REGISTRO_TIPO_IMOVEL) {
//				    	dmCadastro.insertImovel(line);

				    }else if (tipoRegistro == Constantes.REGISTRO_TIPO_DADOS_CATEGORIA) {

				    }else if (tipoRegistro == Constantes.REGISTRO_TIPO_LIGACAO) {

				    }else if (tipoRegistro == Constantes.REGISTRO_TIPO_DEBITO) {

				    }else if (tipoRegistro == Constantes.REGISTRO_TIPO_CREDITO) {

				    }else if (tipoRegistro == Constantes.REGISTRO_TIPO_IMPOSTO) {

				    }else if (tipoRegistro == Constantes.REGISTRO_TIPO_CONTA) {
				    	dmCadastro.insertConta(line);

				    }else if (tipoRegistro == Constantes.REGISTRO_TIPO_MEDIDOR) {
				    	dmCadastro.insertMedidor(line);

				    }else if (tipoRegistro == Constantes.REGISTRO_TIPO_TARIFACAO_MINIMA) {
				    	dmCadastro.insertTarifacaoMinima(line);

				    }else if (tipoRegistro == Constantes.REGISTRO_TIPO_TARIFACAO_COMPLEMENTAR) {
				    	dmCadastro.insertTarifacaoComplementar(line);
				    
				    }else if (tipoRegistro == Constantes.REGISTRO_TIPO_GERAL) {
				    	dmCadastro.insertDadosGerais(line);

				    }else if (tipoRegistro == Constantes.REGISTRO_TIPO_ANORMALIDADE) {
				    	dmCadastro.insertAnormalidade(line);
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

    	if (dmCadastro == null){
	    	dmCadastro = new DataManipulator(context);
			dmCadastro.open();
    	}
    }    
    public void finalizeDataManipulator(){
    	if (dmCadastro != null){
	    	dmCadastro.close();
	    	dmCadastro = null;
    	}
    }
    
    public DataManipulator getCadastroDataManipulator(){
    	return dmCadastro;
    }
    
    // Retorna o Id do cadastro selecionado
    public long getIdCadastroSelecionado(){
    	return idCadastroSelecionado;
    }
    
    // Retorna a posição do cadastro selecionado na lista de cadastros ordenada por inscrição
    public int getCadastroListPosition(){
    	return cadastroListPosition;
    }
    
    // Guarda a posição do cadastro selecionado na lista de cadastros ordenada por inscrição
    public void setCadastroListPosition(int position){
    	this.cadastroListPosition = position;
    	dmCadastro.updateConfiguracao("posicao_cadastro_selecionado", position);
    }
    
    public boolean databaseExists(Context context){
    	File dbFile = new File(Constantes.DATABASE_PATH + Constantes.DATABASE_NAME);

    	initiateDataManipulator(context);
    	
     	return (dbFile.exists() && dmCadastro.selectAnormalidades().size() > 0);
	}
    
    public int isDatabaseRotaCarregadaOk(){

    	if (dmCadastro.selectConfiguracaoElement("rota_carregada") == Constantes.SIM){
    		this.isRotaCarregadaOk = Constantes.SIM;
    	}

     	return this.isRotaCarregadaOk;
	}
    
    public void setRotaCarregamentoOk(int isRotaCarregadaOk){
    	dmCadastro.updateConfiguracao("rota_carregada", Constantes.SIM);
    }
    
    public void deleteDatabase(){
        String strDBFilePath= Constantes.DATABASE_PATH + Constantes.DATABASE_NAME;
        File file = new   File(strDBFilePath);
        file.delete();
	}    
}
