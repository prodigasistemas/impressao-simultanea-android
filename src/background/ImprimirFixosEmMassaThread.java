package background;

import java.util.Date;
import java.util.List;

import model.Imovel;

import com.zebra.android.comm.BluetoothPrinterConnection;
import com.zebra.android.comm.ZebraPrinterConnection;
import com.zebra.android.comm.ZebraPrinterConnectionException;

import business.BusinessConta;
import business.ControladorImovel;
import business.ControladorRota;

import ui.FileManager;
import util.Constantes;
import util.ImpressaoContaCosanpa;
import util.Util;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
    

// Class that performs progress calculations on a thread.  Implement
// the thread by subclassing Thread and overriding its run() method.  Also provide
// a setState(state) method to stop the thread gracefully.
        
public class ImprimirFixosEmMassaThread extends Thread {	
    
    // Class constants defining state of the thread
    public final static int DONE = 2;
    public final static int RUNNING = 1;
    private static Context context;
	private ZebraPrinterConnection conexao;

    
    Handler mHandler;
    int mState;
    int increment;
    private List<Integer> listaIdsImoveisFixos;

    // Constructor with an argument that specifies Handler on main thread
    // to which messages will be sent by this thread.
    public ImprimirFixosEmMassaThread(List<Integer> listaIdsImoveisFixos, Handler h, Context context, int increment) {
    	this.mHandler = h;
        ImprimirFixosEmMassaThread.context = context;
        this.increment = increment;
        this.listaIdsImoveisFixos = listaIdsImoveisFixos;
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
		    	    	

		if (Util.isEmulator()){

			int contadorImpressao = 0;
			Looper.prepare();

			for (int imovelId : listaIdsImoveisFixos) {
				ControladorImovel.getInstancia().setImovelSelecionado(ControladorRota.getInstancia().getDataManipulator().selectImovel("id = " + imovelId, true));
				BusinessConta.getInstancia(context).imprimirCalculo(false);
				
				int impressaoTipo = Constantes.IMPRESSAO_FATURA;
				
//				if (getImovelSelecionado().getContas() != null && getImovelSelecionado().getContas().size() > 0 ){
//					impressaoTipo = Constantes.IMPRESSAO_FATURA_E_NOTIFICACAO;
//				}
				
				Log.i("Comando Fatura", new ImpressaoContaCosanpa().getComandoImpressaoFatura(ControladorImovel.getInstancia().getImovelSelecionado(), Constantes.IMPRESSAO_FATURA));
				ControladorImovel.getInstancia().setupDataAfterPrinting(Constantes.IMPRESSAO_FATURA, increment);
				contadorImpressao++;
				Bundle b = new Bundle();
				Message msg = mHandler.obtainMessage();
				
				if (listaIdsImoveisFixos.size() > contadorImpressao){
					b.putInt("impressaoFixos" + String.valueOf(increment), (contadorImpressao));
					
				} else{
					b.putBoolean("impressaoFixosConcluido", true);
				}
				
				msg.setData(b);
				mHandler.sendMessage(msg);
			}
			Looper.loop();
			
		}else{
			
			if (ControladorRota.getInstancia().getBluetoothAddress() == null) {
				
				mState = DONE;
				Bundle b = new Bundle();
				// Send message (with current value of total as data) to Handler on UI thread
				Message msg = mHandler.obtainMessage();
				b.putBoolean("enderecoBluetoothFaltando", true);
				msg.setData(b);
				mHandler.sendMessage(msg);
				
			}else{
				impressaoImovel(listaIdsImoveisFixos);
			}
		}
    }
    
    public void impressaoImovel(List<Integer> listaIdsImoveisFixos){

    	conexao = new BluetoothPrinterConnection(ControladorRota.getInstancia().getBluetoothAddress());

    	try {

			conexao.open();

			if (conexao.isConnected()) {

				int contadorImpressao = 0;

				// Para cada imóvel fixo:
				for (int imovelId : listaIdsImoveisFixos) {
					
					ControladorImovel.getInstancia().setImovelSelecionado(ControladorRota.getInstancia().getDataManipulator().selectImovel("id = " + imovelId, true));
					BusinessConta.getInstancia(context).imprimirCalculo(false);
					
					int impressaoTipo = Constantes.IMPRESSAO_FATURA;
//					if (getImovelSelecionado().getContas() != null && getImovelSelecionado().getContas().size() > 0 ){
//						impressaoTipo = Constantes.IMPRESSAO_FATURA_E_NOTIFICACAO;
//					}
					
					switch (impressaoTipo) {
					case Constantes.IMPRESSAO_FATURA:
						String comando = new ImpressaoContaCosanpa().getComandoImpressaoFatura(getImovelSelecionado(), Constantes.IMPRESSAO_FATURA);
						Log.i("COMANDO FATURA:", comando);
						conexao.write(comando.getBytes());
						break;
						
					case Constantes.IMPRESSAO_NOTIFICACAO_DEBITO:
						comando = new ImpressaoContaCosanpa().imprimirNotificacaoDebito(getImovelSelecionado());
						Log.i("COMANDO NOTIFICAÇÃO DÉBITO:", comando);
						conexao.write(comando.getBytes());
						break;
						
					case Constantes.IMPRESSAO_FATURA_E_NOTIFICACAO:
						comando = new ImpressaoContaCosanpa().getComandoImpressaoFatura(getImovelSelecionado(), Constantes.IMPRESSAO_FATURA);
						Log.i("COMANDO FATURA:", comando);
						conexao.write(comando.getBytes());
						
						comando = new ImpressaoContaCosanpa().imprimirNotificacaoDebito(getImovelSelecionado());
						Log.i("COMANDO NOTIFICAÇÃO DÉBITO:", comando);
						conexao.write(comando.getBytes());
						break;
					}
					
					Thread.sleep(5000);
					ControladorImovel.getInstancia().setupDataAfterPrinting(impressaoTipo, increment);
					
					contadorImpressao++;
					Bundle b = new Bundle();
					Message msg = mHandler.obtainMessage();
					
					if (listaIdsImoveisFixos.size() > contadorImpressao){
						b.putInt("impressaoFixos" + String.valueOf(increment), (contadorImpressao));
					} else{
						b.putBoolean("impressaoFixosConcluido", true);
					}
					
					msg.setData(b);
					mHandler.sendMessage(msg);	
				}
		        conexao.close();							
			}
			
		} catch (ZebraPrinterConnectionException e) {

			Looper.prepare();

			e.printStackTrace();
			Util.salvarLog(new Date(), e.fillInStackTrace());
						
			AlertDialog.Builder a = new AlertDialog.Builder(context);
			a.setTitle("Erro ao imprimir fatura");
			a.setMessage("Tentar imprimir novamente?");

			a.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
					new ImprimirFixosEmMassaThread(ControladorRota.getInstancia().getDataManipulator().getListaIdsImoveisFixos(false), 
												   mHandler, 
												   context, 
												   increment).start();
				}
			});
			
			a.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {
			    	Bundle b = new Bundle();
			    	Message msg = mHandler.obtainMessage();
			    	b.putBoolean("impressaoErro", true);
			        msg.setData(b);
			        mHandler.sendMessage(msg);				
				}
			});

			a.show();
			Looper.loop();
			Looper.getMainLooper().quit();
			
		} catch (Exception e) {
			e.printStackTrace();
			Util.salvarLog(new Date(), e.fillInStackTrace());
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
    
	public static Imovel getImovelSelecionado() {
		return ControladorImovel.getInstancia().getImovelSelecionado();
	}

}