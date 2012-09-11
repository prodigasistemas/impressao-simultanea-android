package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import business.ControladorRota;
import util.Constantes;
import util.Util;

public class ArquivoRetorno {
	
    private static ArquivoRetorno instancia;
    private static StringBuffer arquivo;

    private static StringBuffer registrosTipoCLiente = null;
    private static StringBuffer registrosTipoImovel = null;
    private static StringBuffer registrosTipoRamoAtividadeImovel = null;
    private static StringBuffer registroTipoServico =  null;
    private static StringBuffer registroTipoMedidor =  null;
    private static StringBuffer registroTipoAnormalidadeImovel =  null;
    private StringBuffer registroTipoCabecalho =  null;

    
    private ArquivoRetorno() {
    	super();
    }
	
    public static ArquivoRetorno getInstancia() {
    	if (instancia == null) {
    	    instancia = new ArquivoRetorno();
    	}
    	return instancia;
    }
    
    public static StringBuffer gerarDadosImovelSelecionado(){

    	arquivo = new StringBuffer();
		
    	gerarRegistroTipoImovel();
    	gerarRegistrosTipoRamosAtividadeImovel();
    	gerarRegistroTipoServico();
    	gerarRegistroTipoMedidor();
    	gerarRegistroTipoAnormalidadeImovel();
    		    
    	return arquivo;
    }

    public static void gerarArquivoCompleto(Handler mHandler, Context context, int increment) {

    	try {
    		
            File diretorioRetorno = new File(Environment.getExternalStorageDirectory() + "/external_sd/Cadastro", "Retorno");
            if(!diretorioRetorno.exists()) {
            	diretorioRetorno.mkdirs();
            }

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

//		       	Controlador.getInstancia().getCadastroDataManipulator().selectCliente(Long.parseLong(listIdImoveis.get(i)));
//		    	Controlador.getInstancia().getCadastroDataManipulator().selectImovel(Long.parseLong(listIdImoveis.get(i)));
//		    	Controlador.getInstancia().getCadastroDataManipulator().selectServico(Long.parseLong(listIdImoveis.get(i)));
//		       	Controlador.getInstancia().getCadastroDataManipulator().selectMedidor(Long.parseLong(listIdImoveis.get(i)));
//		    	Controlador.getInstancia().getCadastroDataManipulator().selectAnormalidadeImovel(Long.parseLong(listIdImoveis.get(i)));

		    	gerarRegistroTipoImovel();
		    	gerarRegistrosTipoRamosAtividadeImovel();
		    	gerarRegistroTipoServico();
		    	gerarRegistroTipoMedidor();
		    	gerarRegistroTipoAnormalidadeImovel();
		    	
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
    public static StringBuffer gerarDadosFinalizacaoRotaOnline(Handler mHandler, Context context, int increment) {

	    arquivo = new StringBuffer();
	    
	    String filterCondition = "(imovel_enviado = " + Constantes.NAO + ")";	    				
	    
	    ArrayList<String> listIdImoveis = (ArrayList<String>) ControladorRota.getInstancia().getDataManipulator().selectIdImoveis(filterCondition);
	    
	    for (int i = 0; i < listIdImoveis.size(); i++){

//	       	Controlador.getInstancia().getCadastroDataManipulator().selectCliente(Long.parseLong(listIdImoveis.get(i)));
//	    	Controlador.getInstancia().getCadastroDataManipulator().selectImovel(Long.parseLong(listIdImoveis.get(i)));
//	    	Controlador.getInstancia().getCadastroDataManipulator().selectServico(Long.parseLong(listIdImoveis.get(i)));
//	       	Controlador.getInstancia().getCadastroDataManipulator().selectMedidor(Long.parseLong(listIdImoveis.get(i)));
//	    	Controlador.getInstancia().getCadastroDataManipulator().selectAnormalidadeImovel(Long.parseLong(listIdImoveis.get(i)));

	    	gerarRegistroTipoImovel();
	    	gerarRegistrosTipoRamosAtividadeImovel();
	    	gerarRegistroTipoServico();
	    	gerarRegistroTipoMedidor();
	    	gerarRegistroTipoAnormalidadeImovel();
	    	
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
    
    private static void gerarRegistroTipoImovel() {

    	registrosTipoImovel = new StringBuffer();

//    	registrosTipoImovel.append("02");
//       	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(9, String.valueOf(getImovelSelecionado().getMatricula())));
//    	registrosTipoImovel.append(Util.adicionarCharDireita(30, String.valueOf(getImovelSelecionado().getCodigoCliente()), ' '));
//    	registrosTipoImovel.append(Util.adicionarCharDireita(17, getImovelSelecionado().getInscricao(), ' '));
//    	registrosTipoImovel.append(Util.adicionarCharDireita(2, getImovelSelecionado().getRota(), ' '));
//    	registrosTipoImovel.append(Util.adicionarCharDireita(2, getImovelSelecionado().getFace(), ' '));
//    	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(8, String.valueOf(getImovelSelecionado().getCodigoMunicipio())));
//    	registrosTipoImovel.append(Util.adicionarCharDireita(31, getImovelSelecionado().getIptu(), ' '));
//    	registrosTipoImovel.append(Util.adicionarCharDireita(20, getImovelSelecionado().getNumeroCelpa(), ' '));
//    	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, String.valueOf(getImovelSelecionado().getNumeroPontosUteis())));
//
//	   	registrosTipoImovel.append(Util.adicionarCharDireita(40, getImovelSelecionado().getEnderecoImovel().getLogradouro(), ' '));
//	   	registrosTipoImovel.append(Util.adicionarCharDireita(5, getImovelSelecionado().getEnderecoImovel().getNumero(), ' '));
//	   	registrosTipoImovel.append(Util.adicionarCharDireita(25, getImovelSelecionado().getEnderecoImovel().getComplemento(), ' '));
//	   	registrosTipoImovel.append(Util.adicionarCharDireita(20, getImovelSelecionado().getEnderecoImovel().getBairro(), ' '));
//	   	registrosTipoImovel.append(Util.adicionarCharDireita(8, getImovelSelecionado().getEnderecoImovel().getCep().replaceAll("[-]", ""), ' '));
//	   	registrosTipoImovel.append(Util.adicionarCharDireita(15, getImovelSelecionado().getEnderecoImovel().getMunicipio(), ' '));
//	   	registrosTipoImovel.append(Util.adicionarCharDireita(9, String.valueOf(getImovelSelecionado().getCodigoLogradouro()), ' '));
//
//	   	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, String.valueOf(getImovelSelecionado().getCategoriaResidencial().getEconomiasSubCategoria1())));
//	   	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, String.valueOf(getImovelSelecionado().getCategoriaResidencial().getEconomiasSubCategoria2())));
//	   	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, String.valueOf(getImovelSelecionado().getCategoriaResidencial().getEconomiasSubCategoria3())));
//	   	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, String.valueOf(getImovelSelecionado().getCategoriaResidencial().getEconomiasSubCategoria4())));
//
//	   	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, String.valueOf(getImovelSelecionado().getCategoriaComercial().getEconomiasSubCategoria1())));
//	   	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, String.valueOf(getImovelSelecionado().getCategoriaComercial().getEconomiasSubCategoria2())));
//	   	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, String.valueOf(getImovelSelecionado().getCategoriaComercial().getEconomiasSubCategoria3())));
//	   	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, String.valueOf(getImovelSelecionado().getCategoriaComercial().getEconomiasSubCategoria4())));
//
//	   	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, String.valueOf(getImovelSelecionado().getCategoriaPublica().getEconomiasSubCategoria1())));
//	   	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, String.valueOf(getImovelSelecionado().getCategoriaPublica().getEconomiasSubCategoria2())));
//	   	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, String.valueOf(getImovelSelecionado().getCategoriaPublica().getEconomiasSubCategoria3())));
//	   	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, String.valueOf(getImovelSelecionado().getCategoriaPublica().getEconomiasSubCategoria4())));
//
//	   	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, String.valueOf(getImovelSelecionado().getCategoriaIndustrial().getEconomiasSubCategoria1())));
//	   	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, String.valueOf(getImovelSelecionado().getCategoriaIndustrial().getEconomiasSubCategoria2())));
//	   	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, String.valueOf(getImovelSelecionado().getCategoriaIndustrial().getEconomiasSubCategoria3())));
//	   	registrosTipoImovel.append(Util.adicionarZerosEsquerdaNumero(3, String.valueOf(getImovelSelecionado().getCategoriaIndustrial().getEconomiasSubCategoria4())));
//
//	   	registrosTipoImovel.append(getImovelSelecionado().getTipoFonteAbastecimento());
//	   	registrosTipoImovel.append(Util.adicionarCharDireita(20, String.valueOf(getImovelSelecionado().getLatitude() != Constantes.NULO_DOUBLE ? getImovelSelecionado().getLatitude() : " "), ' '));
//    	registrosTipoImovel.append(Util.adicionarCharDireita(20, String.valueOf(getImovelSelecionado().getLongitude() != Constantes.NULO_DOUBLE ? getImovelSelecionado().getLongitude() : " "), ' '));
//    	registrosTipoImovel.append(Util.adicionarCharEsquerda(26, getImovelSelecionado().getData(), ' '));
	   	registrosTipoImovel.append("\n");

    	arquivo.append(registrosTipoImovel);
    }
    
    private static void gerarRegistrosTipoRamosAtividadeImovel() {
    	
    }
    
    private static void gerarRegistroTipoServico() {

    }
    
    private static void gerarRegistroTipoMedidor() {
    	
       	registroTipoMedidor = new StringBuffer();

       	registroTipoMedidor.append("05");
    	
//       	registroTipoMedidor.append(Util.adicionarZerosEsquerdaNumero(9, String.valueOf(getImovelSelecionado().getMatricula())));
//       	registroTipoMedidor.append(getMedidorSelecionado().getPossuiMedidor());
//       	registroTipoMedidor.append(Util.adicionarCharDireita(10, getMedidorSelecionado().getNumeroHidrometro(), ' '));
//       	registroTipoMedidor.append(Util.adicionarCharDireita(2, String.valueOf(getMedidorSelecionado().getMarca()), ' '));
//       	registroTipoMedidor.append(Util.adicionarCharDireita(5, String.valueOf(getMedidorSelecionado().getCapacidade() != Constantes.NULO_DOUBLE ? getMedidorSelecionado().getCapacidade() : " "), ' '));
//       	registroTipoMedidor.append(Util.adicionarCharDireita(2, String.valueOf(getMedidorSelecionado().getTipoCaixaProtecao()), ' '));
//       	registroTipoMedidor.append(Util.adicionarCharDireita(20, String.valueOf(getMedidorSelecionado().getLatitude() != Constantes.NULO_DOUBLE ? getMedidorSelecionado().getLatitude() : " "), ' '));
//       	registroTipoMedidor.append(Util.adicionarCharDireita(20, String.valueOf(getMedidorSelecionado().getLongitude() != Constantes.NULO_DOUBLE ? getMedidorSelecionado().getLongitude() : " "), ' '));
//       	registroTipoMedidor.append(Util.adicionarCharEsquerda(26, getMedidorSelecionado().getData(), ' '));
       	registroTipoMedidor.append("\n");
       	
    	arquivo.append(registroTipoMedidor);
    }
    
    private static void gerarRegistroTipoAnormalidadeImovel() {

    	registroTipoAnormalidadeImovel = new StringBuffer();

    	registroTipoAnormalidadeImovel.append("06");
//    	registroTipoAnormalidadeImovel.append(Util.adicionarZerosEsquerdaNumero(9, String.valueOf(getImovelSelecionado().getMatricula())));
//    	registroTipoAnormalidadeImovel.append(Util.adicionarCharDireita(20, String.valueOf(getAnormalidadeImovelSelecionado().getLatitude() != Constantes.NULO_DOUBLE ? getAnormalidadeImovelSelecionado().getLatitude() : " "), ' '));
//    	registroTipoAnormalidadeImovel.append(Util.adicionarCharDireita(20, String.valueOf(getAnormalidadeImovelSelecionado().getLongitude() != Constantes.NULO_DOUBLE ? getAnormalidadeImovelSelecionado().getLongitude() : " "), ' '));
//    	registroTipoAnormalidadeImovel.append(Util.adicionarCharDireita(3, String.valueOf(getAnormalidadeImovelSelecionado().getCodigoAnormalidade()), ' '));
//    	registroTipoAnormalidadeImovel.append(Util.adicionarCharDireita(3, String.valueOf(getAnormalidadeImovelSelecionado().getComentario()), ' '));
//    	registroTipoAnormalidadeImovel.append(Util.adicionarCharDireita(3, String.valueOf(getAnormalidadeImovelSelecionado().getPathFoto1()), ' '));
//    	registroTipoAnormalidadeImovel.append(Util.adicionarCharDireita(3, String.valueOf(getAnormalidadeImovelSelecionado().getPathFoto2()), ' '));
//    	registroTipoAnormalidadeImovel.append(Util.adicionarCharEsquerda(26, getAnormalidadeImovelSelecionado().getData(), ' '));
    	registroTipoAnormalidadeImovel.append("\n");

    	arquivo.append(registroTipoAnormalidadeImovel);
    }
    
//    public static Cliente getClienteSelecionado(){
//    	return Controlador.getInstancia().getClienteSelecionado();
//    }
//    
//    public static Imovel getImovelSelecionado(){
//    	return Controlador.getInstancia().getImovelSelecionado();
//    }
//    
//    public static Medidor getMedidorSelecionado(){
//    	return Controlador.getInstancia().getMedidorSelecionado();
//    }
//    
//    public static Servicos getServicosSelecionado(){
//    	return Controlador.getInstancia().getServicosSelecionado();
//    }
//    
//    public static AnormalidadeImovel getAnormalidadeImovelSelecionado(){
//    	return Controlador.getInstancia().getAnormalidadeImovelSelecionado();
//    }

}
