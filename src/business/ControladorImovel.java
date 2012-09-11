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
import java.util.List;
import java.util.Vector;

import model.Consumo;
import model.Conta;
import model.DadosGerais;
import model.Imovel;
import model.Medidor;
import model.SituacaoTipo;
import util.Constantes;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import dataBase.DataManipulator;

public class ControladorImovel {

    public static ControladorImovel instancia;

    private static Imovel imovelSelecionado = new Imovel();
    private static Conta contaSelecionado = new Conta();
    private static Medidor medidorSelecionado = new Medidor();
    private static List<String> dadosCategoria = new ArrayList<String>();
    
    private static long idImovelSelecionado = 0;
    private static int imovelListPosition = -1;
    
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
    
    public List<String> getDadosCategoria() {
    	return ControladorImovel.dadosCategoria;
    }

//    	return Controlador.anormalidadeImovelSelecionado;
//    }
//    
    public void setImovelSelecionado(Imovel imovelSelecionado){
    	ControladorImovel.imovelSelecionado = imovelSelecionado;
    }
//    
    public void setContaSelecionado(Conta contaSelecionado){
    	ControladorImovel.contaSelecionado = contaSelecionado;
    }

    public void setMedidorSelecionado(Medidor medidorSelecionado){
    	ControladorImovel.medidorSelecionado = medidorSelecionado;
    }
//    
//    public void setAnormalidadeImovelSelecionado(AnormalidadeImovel anormalidadeImovelSelecionado){
//    	Controlador.anormalidadeImovelSelecionado = anormalidadeImovelSelecionado;
//    }
//    
//    
//    public void setRamosAtividade(Registro ramosAtividade){
//    	Controlador.ramosAtividade = ramosAtividade;
//    }
     
    public void setImovelSelecionadoByListPosition(int listPosition){
    	initImovelTabs();
    	setImovelListPosition(listPosition);
    	idImovelSelecionado = getIdImovelSelecionado(listPosition, null);
//    	dmImovel.selectCliente(idImovelSelecionado);
    	ControladorRota.getInstancia().dataManipulator.selectImovel(idImovelSelecionado);
//    	dmImovel.selectServico(idImovelSelecionado);
    	ControladorRota.getInstancia().dataManipulator.selectMedidor(ControladorImovel.getInstancia().getImovelSelecionado().getMatricula());
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
    	ControladorRota.getInstancia().dataManipulator.selectImovel(idImovelSelecionado);
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
        	return Integer.parseInt(ControladorRota.getInstancia().getDataManipulator().selectIdImoveis(condition).get(listPosition));
     	}
    }
    
    public int getImovelListPositionById(long id){
    	int position = 0;
    	ArrayList<String> listIds = (ArrayList<String>) ControladorRota.getInstancia().getDataManipulator().selectIdImoveis(null);
    	
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
    
    // Retorna o Id do imovel selecionado
    public long getIdImovelSelecionado(){
    	return idImovelSelecionado;
    }
    
    // Retorna a posição do imovel selecionado na lista de imoveis ordenada por inscrição
    public int getImovelListPosition(){
    	return imovelListPosition;
    }
    
    // Guarda a posição do imovel selecionado na lista de imoveis ordenada por inscrição
    public void setImovelListPosition(int position){
    	this.imovelListPosition = position;
//    	dataManipulator.updateConfiguracao("posicao_cadastro_selecionado", position);
    }
    
    /**
     * [UC0743] Calcular Valores de Água/Esgoto
     */
    public void calcularValores(Imovel imovel, Consumo consumo,int tipoMedicao) {
    }
}
