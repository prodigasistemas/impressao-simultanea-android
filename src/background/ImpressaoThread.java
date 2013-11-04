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
	Imovel imovelToBePrinted;
	int impressaoTipo;
	String progressTitleMsg;
	Context context;
	Handler mHandler;
	int increment;
	private ZebraPrinterConnection conexao;

	public ImpressaoThread(String address, Handler mHandler, Imovel imovelToBePrinted, int impressaoTipo, int increment, Context context) {
		this.bluetoothAddress = address;
		this.imovelToBePrinted = imovelToBePrinted;
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
					String comando = new ImpressaoContaCosanpa().getComandoImpressaoFatura(imovelToBePrinted, Constantes.IMPRESSAO_FATURA);
					progressTitleMsg = "Imprimindo Fatura";
					Log.i("COMANDO FATURA:", comando);
					conexao.write(comando.getBytes());
			    	break;

			    case Constantes.IMPRESSAO_NOTIFICACAO_DEBITO:
					progressTitleMsg = "Imprimindo Notificação de Débito";
					comando = new ImpressaoContaCosanpa().imprimirNotificacaoDebito(imovelToBePrinted);
					Log.i("COMANDO NOTIFICAÇÃO DÉBITO:", comando);
					conexao.write(comando.getBytes());
			    	break;

			    case Constantes.IMPRESSAO_FATURA_E_NOTIFICACAO:
					progressTitleMsg = "Imprimindo Fatura e Notificação de Débito";
					comando = new ImpressaoContaCosanpa().getComandoImpressaoFatura(imovelToBePrinted, Constantes.IMPRESSAO_FATURA);
					Log.i("COMANDO FATURA:", comando);
					conexao.write(comando.getBytes());

					comando = new ImpressaoContaCosanpa().imprimirNotificacaoDebito(imovelToBePrinted);
					Log.i("COMANDO NOTIFICAÇÃO DÉBITO:", comando);
					conexao.write(comando.getBytes());
			    	break;
			    }

				conexao.close();
				
				ControladorRota.getInstancia().getDataManipulator().updateConfiguracao("bluetooth_address", bluetoothAddress);
				Thread.sleep(1500);
				
		    	ControladorImovel.getInstancia().setupDataAfterPrinting(impressaoTipo, increment);
		    	ControladorAcessoOnline.getInstancia().transmitirImovel(context, increment);
		    	
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

			e.printStackTrace();
			Util.salvarLog(new Date(), e.fillInStackTrace());
			
			Looper.prepare();
			
			AlertDialog.Builder a = new AlertDialog.Builder(context);
			a.setTitle("Erro ao imprimir fatura");
			a.setMessage("Tentar imprimir novamente?");
			a.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			     
				public void onClick(DialogInterface arg0, int arg1) {
					
					new ImpressaoThread(ControladorRota.getInstancia().getBluetoothAddress(),
										mHandler,
										imovelToBePrinted, 
										impressaoTipo, 
										increment, 
										context).start();
				}
			});
			
			a.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			     
				public void onClick(DialogInterface arg0, int arg1) {}
			});
			
			a.show();
			
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