package background;

import java.io.IOException;

import model.Imovel;
import ui.ArquivoRetorno;
import ui.FileManager;
import util.Constantes;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import business.ControladorAcessoOnline;
import business.ControladorImovel;
import business.ControladorRota;
    

// Class that performs progress calculations on a thread.  Implement
// the thread by subclassing Thread and overriding its run() method.  Also provide
// a setState(state) method to stop the thread gracefully.
        
public class EnviarImovelOnlineThread extends Thread {	
    
    // Class constants defining state of the thread
    public final static int DONE_OK = 2;
    public final static int DONE_ERROR = 3;
    public final static int RUNNING = 1;
    private static Context context;
    private Imovel imovel;
    
    Handler mHandler;
    int mState;
    int total;
    int increment;

    // Constructor with an argument that specifies Handler on main thread
    // to which messages will be sent by this thread.
    public EnviarImovelOnlineThread(Handler h, Context context, int increment, Imovel imovel) {
    	this.mHandler = h;
        EnviarImovelOnlineThread.context = context;
        this.total = 0;
        this.increment = increment;
        this.imovel = imovel;
    }
    
    // Override the run() method that will be invoked automatically when 
    // the Thread starts.  Do the work required to update the progress bar on this
    // thread but send a message to the Handler on the main UI thread to actually
    // change the visual representation of the progress. In this example we count
    // the index total down to zero, so the horizontal progress bar will start full and
    // count down.
    
    @Override
    public void run() {
    	
    	mState = RUNNING;
    	FileManager.getInstancia();
    		
    	if (imovel.getIndcImovelEnviado() == Constantes.SIM) {
    		return;
    	}

    	// Obter dados do imóvel finalizado
    	StringBuffer mensagem = ArquivoRetorno.getInstancia().gerarDadosImovelParaEnvio(imovel);
    	
		// Transmitir dados do imovel ao servidor        			
		try {
			
			ControladorAcessoOnline.getInstancia().enviarCadastro(mensagem.toString().getBytes());
		
			
			System.out.println("Antes");
		    if (ControladorAcessoOnline.getInstancia().isRequestOK()) {
//				SET CADASTRO PARA TRANSMITIDO!!
//		    	mensagemSucesso("Imovel enviado!");
		    	mState = DONE_OK;
		    	imovel.setIndcImovelEnviado(Constantes.SIM);
		    	ControladorRota.getInstancia().getDataManipulator().salvarImovel(imovel);
		    	System.out.println("Dentro IF");
		    } else {
//		    	mensagemAviso("Aviso:", "Imovel não enviado!");
		    	mState = DONE_ERROR;
		    	System.out.println("Dentro ELSE");
		    }	    

		} catch (IOException e) {
	    	mState = DONE_ERROR;
		}
    	total = 100;
    	
        Bundle b = new Bundle();
        // Send message (with current value of total as data) to Handler on UI thread
        Message msg = mHandler.obtainMessage();
        b.putInt("envioCadastroOnline" + String.valueOf(increment), 100);
        msg.setData(b);
        mHandler.sendMessage(msg);

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