package background;

import java.util.Date;

import model.Imovel;
import util.Constantes;
import util.ImpressaoContaCosanpa;
import util.Util;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import business.ControladorAcessoOnline;
import business.ControladorImovel;
import business.ControladorRota;

import com.zebra.android.comm.BluetoothPrinterConnection;
import com.zebra.android.comm.ZebraPrinterConnection;
import com.zebra.android.comm.ZebraPrinterConnectionException;

public class ImpressaoThread extends Thread {
	String bluetoothAddress;
	int impressaoTipo;
	String progressTitleMsg;
	Context context;
	Handler mHandler;
	int increment;
	private ZebraPrinterConnection conexao;

	public ImpressaoThread(String address, Handler mHandler, int impressaoTipo, int increment, Context context) {
		this.bluetoothAddress = address;
		this.impressaoTipo = impressaoTipo;
		this.context = context;
		this.mHandler = mHandler;
		this.increment = increment;
	}
	
	@Override
	public void run() {
		
		conexao = new BluetoothPrinterConnection(bluetoothAddress);
		
		try {
			conexao.open();

			if (conexao.isConnected()) {

				Looper.prepare();

			    switch (impressaoTipo) {
			    case Constantes.IMPRESSAO_FATURA:
					String comando = new ImpressaoContaCosanpa().getComandoImpressaoFatura(getImovelSelecionado(), Constantes.IMPRESSAO_FATURA);
					progressTitleMsg = "Imprimindo Fatura";
					Log.i("COMANDO FATURA:", comando);
					conexao.write(comando.getBytes());
			    	break;

			    case Constantes.IMPRESSAO_NOTIFICACAO_DEBITO:
					progressTitleMsg = "Imprimindo Notificação de Débito";
					comando = new ImpressaoContaCosanpa().imprimirNotificacaoDebito(getImovelSelecionado());
					Log.i("COMANDO NOTIFICAÇÃO DÉBITO:", comando);
					conexao.write(comando.getBytes());
			    	break;

			    case Constantes.IMPRESSAO_FATURA_E_NOTIFICACAO:
					progressTitleMsg = "Imprimindo Fatura e Notificação de Débito";
					comando = new ImpressaoContaCosanpa().getComandoImpressaoFatura(getImovelSelecionado(), Constantes.IMPRESSAO_FATURA);
					Log.i("COMANDO FATURA:", comando);
					conexao.write(comando.getBytes());

					comando = new ImpressaoContaCosanpa().imprimirNotificacaoDebito(getImovelSelecionado());
					Log.i("COMANDO NOTIFICAÇÃO DÉBITO:", comando);
					conexao.write(comando.getBytes());
			    	break;
			    }

				conexao.close();
				
				ControladorRota.getInstancia().getDataManipulator().updateConfiguracao("bluetooth_address", bluetoothAddress);
				ControladorImovel.getInstancia().setupDataAfterPrinting(impressaoTipo);
				ControladorAcessoOnline.getInstancia().transmitirImovel(context, increment);

				Thread.sleep(3000);
		    	
				Bundle b = new Bundle();
				// Send message (with current value of total as data) to Handler on UI thread
				Message msg = mHandler.obtainMessage();
				b.putBoolean("impressaoConcluida", true);
				msg.setData(b);
				mHandler.sendMessage(msg);

		        Looper.loop();
		        Looper.getMainLooper().quit();
			}
			
		} catch (ZebraPrinterConnectionException e) {

			Looper.prepare();

			e.printStackTrace();
			Util.salvarLog(new Date(), e.fillInStackTrace());

	    	Bundle b = new Bundle();
	    	Message msg = mHandler.obtainMessage();
	    	b.putBoolean("impressaoErro", true);
	        msg.setData(b);
	        mHandler.sendMessage(msg);				
			
	        Looper.loop();
	        Looper.getMainLooper().quit();
			
		} catch (Exception e) {
			e.printStackTrace();
			Util.salvarLog(new Date(), e.fillInStackTrace());
		}
	}

	public static Imovel getImovelSelecionado() {
		return ControladorImovel.getInstancia().getImovelSelecionado();
	}

}