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
import java.util.Vector;

import model.DadosGerais;
import model.DadosQualidadeAgua;
import model.SituacaoTipo;
import util.Constantes;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import dataBase.DataManipulator;

public class ControladorRota {

    private static ControladorRota instancia;
	private DataManipulator dataManipulator;
    
    private boolean permissionGranted = false;
    private int qtdRegistros = 0;
    private static int linhasLidas = 0;
    private static int isRotaCarregadaOk = Constantes.NAO;

    private static DadosGerais dadosGerais = new DadosGerais();
    private static Vector anormalidades = new Vector();

    private static int LEITURA_CONFIRMADA = 99;

    public static ControladorRota getInstancia() {
    	
    	if (ControladorRota.instancia == null) {
    		ControladorRota.instancia = new ControladorRota();
    		
    	}
    	return ControladorRota.instancia;
    }
    
    public void cleanControladorRota(){
    	instancia = null;
    }
    
    public void setDadosGerais(DadosGerais dadosGerais){
    	ControladorRota.dadosGerais = dadosGerais;
    }
    
    public void setAnormalidades(Vector anormalidades){
    	ControladorRota.anormalidades = anormalidades;
    }

    public DadosGerais getDadosGerais(){
    	return ControladorRota.dadosGerais;
    }
    
    public Vector getAnormalidades(){
    	return ControladorRota.anormalidades;
    }
    
    //    public AnormalidadeImovel getAnormalidadeImovelSelecionado(){
    public String getBluetoothAddress() {
    	return dataManipulator.selectConfiguracaoElement("bluetooth_address");
    }
    
    /**
     * Carrega o vetor de imóveis e salva cada registro no SQlite database.
     * 
     * @param input
     *            Vetor onde cada elemento uma linha do arquivo de entrada.
     */
    public void carregarDadosParaRecordStore(BufferedReader input, Handler mHandler, Context context) {
		String line = "";
	    linhasLidas = 0;
		int matricula = 0;
		linhasLidas = 0;
		
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
					    	dataManipulator.insertDadosQualidadeAgua(DadosQualidadeAgua.getInstancia());
							break;
							
						case REGISTRO_TIPO_DADOS_CATEGORIA:
							dataManipulator.insertDadosCategoria(line);
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
							dataManipulator.insertImposto(line);
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
    
    public boolean databaseExists(Context context){
    	File dbFile = new File(Constantes.DATABASE_PATH + Constantes.DATABASE_NAME);

    	initiateDataManipulator(context);
    	
     	return (dbFile.exists() && dataManipulator.selectListaAnormalidades(false).size() > 0);
	}
    
    public int isDatabaseRotaCarregadaOk(){

    	if (new Integer(dataManipulator.selectConfiguracaoElement("sucesso_carregamento")) == Constantes.SIM){
    		this.isRotaCarregadaOk = Constantes.SIM;
    	}

     	return this.isRotaCarregadaOk;
	}
    
    public void setRotaCarregamentoOk(int isRotaCarregadaOk){
    	dataManipulator.updateConfiguracao("sucesso_carregamento", Constantes.SIM);
    }
    
    public void deleteDatabase(){
        String strDBFilePath= Constantes.DATABASE_PATH + Constantes.DATABASE_NAME;
        File file = new   File(strDBFilePath);
        file.delete();
	}    

//  /**
//  * Carrega as anormalidades no controlador Imoveis
//  */
// public static Vector carregarAnormalidades(boolean apenasComIndicadorUso1) throws IOException {
//	
//		if ( apenasComIndicadorUso1 && anormalidadesIndicadorUso1 != null ){
//		    return anormalidadesIndicadorUso1;
//		
//		} else if ( !apenasComIndicadorUso1 && anormalidadesSemIndicadorUso1 != null ){
//		    return anormalidadesSemIndicadorUso1;
//		
//		} else {	
//		    
//			// carrega os id's de cada anormalidade
//			Vector anors = carregarVetorAnormalidades(ControladorImovel.anormalidades, apenasComIndicadorUso1);
//			
//		    if (apenasComIndicadorUso1) {
//		    	anormalidadesIndicadorUso1 = anors;
//		    } else {
//		    	anormalidadesSemIndicadorUso1 = anors;
//		    }
//
//		    return anors;
//		    
//		}
// }
//
// /**
//  * Carrega o array que mapeia o indice da anormalidade no identificador.
//  * 
//  * @param anormalidades
//  *            Vetor de anormalidades.
//  */
// public static Vector carregarVetorAnormalidades(Vector anormalidades, boolean apenasComIndicadorUso1) {
//		
// 	if (anormalidades != null){
//	    	
// 		int len = anormalidades.size();
//			
//			// Daniel- corrigindo vetor de anormalidades	
//			Vector retorno = new Vector();
//			Anormalidade anor = new Anormalidade();
//	
//			int contador = 1;
//		
//			// Daniel- corrigindo vetor de anormalidades	
//			for (int i = 1; i < len+1 ; i++) {
//			    Anormalidade reg14 = (Anormalidade) anormalidades.elementAt(i-1);
//			    
//			    // Daniel - Descarta Anormalidade LEITURA_CONFIRMADA - Usuario não pode usar tal opção.
//			    if (reg14.getCodigo() == LEITURA_CONFIRMADA){
//			    	len = len -1;
//			    	continue;
//			    }
//				
//			    anor = reg14;
//			    retorno.add(anor);
//			}
//
//			if (apenasComIndicadorUso1) {
//			    Vector retornoIndicadorUsoSim = new Vector();
//			    
//			    // Daniel- corrigindo vetor de anormalidades	
//			    for (int i = 0; i < len+1; i++) {
//					if ( ((Anormalidade)retorno.elementAt(i)).getIndcUso() == Constantes.SIM ) {
//					    retornoIndicadorUsoSim.add(retorno.elementAt(i));
//					}
//			    }
//
//			    return retornoIndicadorUsoSim;
//			
//			} else {
//			    return retorno;
//			}
// 	}
// 	return null;
// }
//
    /**
     * Carrega as anormalidades direto da Tabela Anormalidade.
     * 
     * @return vetor com as anormalidades
     */
    public ArrayList<String> getAnormalidades(boolean apenasComIndicadorUso1){	
   		ArrayList<String> listaAnormalidades = new ArrayList<String>();
   	
   	    if (getDadosGerais().getCodigoEmpresaFebraban().equals(Constantes.CODIGO_FEBRABAN_COSANPA)) {
   	    	listaAnormalidades = dataManipulator.selectListaAnormalidades(apenasComIndicadorUso1);
   		}
   	
   		return listaAnormalidades;
    }

}

