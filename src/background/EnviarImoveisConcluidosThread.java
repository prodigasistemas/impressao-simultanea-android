package background;

import java.io.File;
import java.util.List;

import com.IS.MenuPrincipal;

import model.Imovel;
import ui.ArquivoRetorno;
import ui.MessageDispatcher;
import util.Constantes;
import util.Util;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import business.ControladorAcessoOnline;
import business.ControladorRota;

public class EnviarImoveisConcluidosThread extends Thread {
	
    public final static int DONE = 2;
    public final static int RUNNING = 1;
    
    int mState;
    
    private Handler handler;
    private Context context;
    private int increment;
    
    public static List<Integer> idsImoveisAEnviar;
    
    public EnviarImoveisConcluidosThread(Handler handler, Context context, int increment) {
    	this.handler = handler;
    	this.context = context;
    	this.increment = increment;
    }
	
	@Override
	public void run() {
		
		try {
			
			idsImoveisAEnviar = ArquivoRetorno.getInstancia().gerarArquivoParaEnvio(handler, context, increment, Constantes.TIPO_ENVIO_IMOVEIS_NAO_TRANSMITIDOS);
//			idsImoveisAEnviar = ControladorRota.getInstancia().getDataManipulator().selectIdsImoveisConcluidosENaoEnviados();
			
			mState = DONE;
			
			Bundle b = new Bundle();
	        // Send message (with current value of total as data) to Handler on UI thread
	        Message msg = handler.obtainMessage();
	        b.putBoolean("geracaoDosImoveisNaoTransmitidosConcluido", true);
	        msg.setData(b);
	        handler.sendMessage(msg);
	        
			if (idsImoveisAEnviar.size() > 0) {
//				enviando = true;
				Bundle b1 = new Bundle();
		        Message msg1 = handler.obtainMessage();
		        msg1.setData(b1);
		        handler.sendMessage(msg1);
				ControladorAcessoOnline.getInstancia().enviarCadastro(ArquivoRetorno.getInstancia().getConteudoArquivoRetorno().getBytes());
				
				if (ControladorAcessoOnline.getInstancia().isRequestOK()) {
					for (Integer id : idsImoveisAEnviar) {
						Imovel imovel = ControladorRota.getInstancia().getDataManipulator().selectImovel("id = " + id);
						
						imovel.setIndcImovelEnviado(Constantes.SIM);
						
						ControladorRota.getInstancia().getDataManipulator().salvarImovel(imovel);
					}
					
					MenuPrincipal.mensagemRetorno = "Imóveis transmitidos com sucesso!";
					
					File file = new File(Util.getRetornoRotaDirectory(), Util.getNomeArquivoEnviarConcluidos());
					boolean bool = file.delete();
				} else {
					String resposta = MessageDispatcher.getMensagemError();
				    
				    if ( resposta != null && !resposta.equals( "" ) ){
				    	MenuPrincipal.mensagemRetorno = resposta;
				    } else {
				    	MenuPrincipal.mensagemRetorno = "Ocorreu um erro na trasmissão dos imóveis!";
				    }
				}
				
				Bundle b2 = new Bundle();
		        Message msg2 = handler.obtainMessage();
		        b2.putBoolean("recebeuResposta", true);
		        msg2.setData(b2);
		        handler.sendMessage(msg2);	
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}

	// Set current state of thread
    public void setState(int state) {
        mState = state;
    }
    
    // get current state of thread
    public int getCustomizedState() {
        return mState;
    }
}
