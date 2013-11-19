package views;

import helper.EfetuarRateioConsumoHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import model.Consumo;
import model.Imovel;
import util.Constantes;
import util.ImpressaoContaCosanpa;
import util.Util;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import android.widget.Toast;
import background.EnviarImoveisCondominioThread;
import background.ImpressaoThread;
import business.BusinessConta;
import business.ControladorAcessoOnline;
import business.ControladorConta;
import business.ControladorImovel;
import business.ControladorRota;

import com.IS.Fachada;
import com.IS.ListaImoveis;
import com.IS.R;
import com.zebra.android.comm.BluetoothPrinterConnection;
import com.zebra.android.comm.ZebraPrinterConnection;
import com.zebra.android.comm.ZebraPrinterConnectionException;

import dataBase.DataManipulator;

public class MainTab extends FragmentActivity implements TabHost.OnTabChangeListener, OnItemClickListener {

	private static TabHost tabHost;
	private BluetoothAdapter bluetoothAdapter;
	private ListView listaDispositivos;
	AlertDialog dialog;
	private ProgressDialog progress;
	private ProgressDialog progressImpressaoCondominial;
	private ZebraPrinterConnection conexao;
	private static int increment;
	private String dialogMessage = null;
	public LocationManager mLocManager;

	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.maintab);
	    
	    
//	    TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//	    int type = tm.getNetworkType();
//
//	    if (type != TelephonyManager.NETWORK_TYPE_HSDPA
//	        && type != TelephonyManager.NETWORK_TYPE_HSPA
//	        && type != TelephonyManager.NETWORK_TYPE_HSUPA
//	        && type != TelephonyManager.NETWORK_TYPE_UMTS
//	        && type != TelephonyManager.NETWORK_TYPE_EVDO_0
//	        && type != TelephonyManager.NETWORK_TYPE_EVDO_A
//	        && type != TelephonyManager.NETWORK_TYPE_EVDO_B
//	        && type != TelephonyManager.NETWORK_TYPE_EDGE
//	        && type != TelephonyManager.NETWORK_TYPE_GPRS
//	        && type != TelephonyManager.NETWORK_TYPE_HSPAP) {
//
//	        Toast.makeText(getApplicationContext(), "network type " + type, Toast.LENGTH_SHORT).show();
//	    }
	    
        /* Use the LocationManager class to obtain GPS locations */
        mLocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        
        boolean enabled = mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Check if enabled and if not send user to the GPS settings
        // Better solution would be to display a dialog and suggesting to 
        // go to the settings
        if (!enabled){
	        dialogMessage = " GPS está desligado. Por favor, ligue-o para continuar o cadastro. ";
	    	showDialog(Constantes.DIALOG_ID_ERRO_GPS_DESLIGADO);
        }	    
	    
	    tabHost = (TabHost) findViewById(android.R.id.tabhost);
	    tabHost.setup();
	    tabHost.setOnTabChangedListener(this);
	    
	    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

	    // Define a imagem de fundo de acordo com a orientacao do dispositivo
	    if (getResources().getConfiguration().orientation == getResources().getConfiguration().ORIENTATION_PORTRAIT)
	    	tabHost.setBackgroundResource(R.drawable.fundocadastro);
	    else
	    	tabHost.setBackgroundResource(R.drawable.landscape_background);
	    
	    FragmentManager fm = getSupportFragmentManager();
	    Fragment fragment = fm.findFragmentById(android.R.id.tabcontent);
	    
	    if (fragment == null) {
	    	FragmentTransaction ft = fm.beginTransaction();
	    	ft.add(android.R.id.tabcontent, new ImovelTab());
	    	ft.commit();
	    }
	    
	    addTab("imovel", "Imóvel", R.drawable.tab_imovel, R.layout.imoveltab, ImovelTab.class);

	    if (isTabMedidorAguaNeeded())
	    	addTab("medidor", "Medidor Água", R.drawable.tab_medidor, R.layout.medidoraguatab, MedidorAguaTab.class);
	    
	    else if (isTabMedidorPocoNeeded())
	    	addTab("medidor", "Medidor Poço", R.drawable.tab_medidor, R.layout.medidoraguatab, MedidorPocoTab.class);
	    
	    else if (isTabContaNeeded()){
	    	addTab("conta", "Conta", R.drawable.text, R.layout.contatab, ContaTab.class);
	    }

	    tabHost.setCurrentTab(tabHost.getChildCount());
	    
	}
	
	// Instancia novas tabs
	public void addTab(String tag, String titulo, int imagem, final int view, Class classe) {
		TabHost.TabSpec tabSpec;
	    Resources res = getResources();
	    
	    String title = titulo;
	    
	    if (titulo.equals("Imóvel")) {
	    	title = titulo + "  "+getImovelSelecionado().getId()+" de " + ListaImoveis.tamanhoListaImoveis;
	    }
		
	    tabSpec = tabHost.newTabSpec(tag).setIndicator(title, res.getDrawable(imagem)).setContent(new TabContentFactory() {

            public View createTabContent(String tag) {
            	LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            	View layout = inflater.inflate(view, (ViewGroup) findViewById(R.layout.maintab));
                return layout;
            }
        });
	    
	    tabHost.addTab(tabSpec);
	    
	    setTabColor();
	}
	
	public void setTabColor() {
        for(int i=0;i<tabHost.getTabWidget().getChildCount();i++){
        	if (getImovelSelecionado().getImovelStatus() == Constantes.IMOVEL_STATUS_CONCLUIDO){
        		tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_custom_green);
            }
            else if(getImovelSelecionado().getImovelStatus() == Constantes.IMOVEL_STATUS_PENDENTE){
            	tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_custom_white);   
            }
        }
    }
	
	
    public boolean onKeyDown(int keyCode, KeyEvent event){
        
    	if ((keyCode == KeyEvent.KEYCODE_BACK)){
			finish();
            return true;

        }else{
            return super.onKeyDown(keyCode, event);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);

    	MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.menuoptions, menu);
	    
    	return true;
    }
    
    @Override

    public boolean onPrepareOptionsMenu(Menu menu) {

    	if(getImovelSelecionado().isImovelCondominio()){
		    menu.findItem(R.id.imprimirConta).setVisible(false);
    	}else if (!ControladorImovel.getInstancia().isPrintingAllowed()){
		    menu.findItem(R.id.imprimirConta).setEnabled(false);
	    }
    	
	    if (!getImovelSelecionado().isImovelCondominio()) {
		    menu.findItem(R.id.imprimirContasCondominio).setVisible(false);
	    }else if (getImovelSelecionado().isImovelCondominio() && !isCondominioConcluido()){
		    menu.findItem(R.id.imprimirContasCondominio).setEnabled(false);
	    }
	    
	    // Todos imóveis condominiais com leitura individualizada possuem a opção de calcular consumo habilitada
	    if (!getImovelSelecionado().isImovelCondominio()) {
		    menu.findItem(R.id.calcularConsumo).setVisible(false);
	    }
    	
	    return super.onPrepareOptionsMenu(menu);
    }

    public void callProximoImovel(){

    	ControladorImovel.getInstancia().isImovelAlterado();
    	
    	if(ControladorImovel.getInstancia().getImovelListPosition() == (getDataManipulator().getNumeroImoveis())-1){
			ControladorImovel.getInstancia().setImovelSelecionadoByListPosition(0);

		}else{
	    	ControladorImovel.getInstancia().setImovelSelecionadoByListPosition(ControladorImovel.getInstancia().getImovelListPosition()+1);
		}
    	finish();

    	Intent myIntent = new Intent(getApplicationContext(), MainTab.class);
		startActivity(myIntent);
    }
    
    public void callImovelAnterior(){

    	ControladorImovel.getInstancia().isImovelAlterado();
    	
    	if(ControladorImovel.getInstancia().getImovelListPosition() <= 0){
			ControladorImovel.getInstancia().setImovelSelecionadoByListPosition((int)getDataManipulator().getNumeroImoveis()-1);

    	}else{
	    	ControladorImovel.getInstancia().setImovelSelecionadoByListPosition(ControladorImovel.getInstancia().getImovelListPosition()-1);
		}
    	finish();
    	
    	Intent myIntent = new Intent(getApplicationContext(), MainTab.class);
		startActivity(myIntent);
    }
 
    
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Log.i("Menu item", item.getItemId()+"");
		
	    switch (item.getItemId()) {
	    case R.id.proximoImovel:

	    	callProximoImovel();
	    	return true;

	    case R.id.imovelAnterior:

	    	callImovelAnterior();
	    	return true;
	        
	    case R.id.imprimirConta:
	    	
	    	//Verifica se a data atual é anterior ao mes de referencia da rota em andamento.
	    	if(Util.compararData(getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), MedidorAguaTab.getCurrentDateByGPS()) > 0){
	    		// Data do celular esta correta.
	    		showMessage("Data do celular está errada. Por favor, verifique a configuração do celular e tente novamente.");
	    		
	    	}else{
	    		// inicia procedimento de cálculo de consumo e impressao da fatura
	    		calculoEImpressao();
	    	}
	    	return true;
	    	
	    case R.id.calcularConsumo:
    		// inicia procedimento de cálculo de consumo de imóveis condominiais
    		calculoEImpressao();
	    	return true;

	    case R.id.imprimirContasCondominio:
	    	imprimirCondominio();
	    	return true;
	   
	    case R.id.localizarPendente:
	    	localizarImovelPendente();
	    	return true;
	        
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	public void localizarImovelPendente() {
		
		Imovel imovelPendente = getDataManipulator().selectImovel("imovel_status = "+Constantes.IMOVEL_STATUS_PENDENTE, false);
		
		// Se nao encontrar imovel com status pendente
		if (imovelPendente == null) {
			Toast.makeText(this, "Não existem imóveis pendentes", Toast.LENGTH_LONG).show();
			return;
		}
		
		ControladorImovel.getInstancia().setImovelSelecionadoByListPosition(Long.valueOf(imovelPendente.getId()).intValue()-1);
		
		finish();
    	Intent myIntent = new Intent(getApplicationContext(), MainTab.class);
		startActivity(myIntent);
		
	}
		
    // Handler on the main (UI) thread that will receive messages from the second thread and update the progress.
    final Handler impressaoHandler = new Handler() {
        @SuppressWarnings({ "deprecation" })
        public void handleMessage(Message msg) {
            
        	// Get the current value of the variable total from the message data and update the progress bar.
            if (msg.getData().getBoolean("impressaoConcluida")){
				progress.dismiss();
			    increment += 17;
			    callProximoImovel();

            }else if (msg.getData().getBoolean("impressaoErro")){
				progress.dismiss();
				
				AlertDialog.Builder a = new AlertDialog.Builder(MainTab.this);
				a.setTitle("Erro ao imprimir fatura");
				a.setMessage("Tentar imprimir novamente?");

				a.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {

						imprimirConta(ControladorRota.getInstancia().getBluetoothAddress(), 
									  ControladorImovel.getInstancia().getImpressaoTipo(getApplicationContext()));
					}
				});
				
				a.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						getDataManipulator().updateConfiguracao("bluetooth_address", null);
			    		dialogMessage = "Não foi possível imprimir.";
		    	    	showDialog(Constantes.DIALOG_ID_ERRO);
					    increment += 17;
					}
				});
				a.show();
            }
         }
    };

	public void imprimirConta(String bluetoothAddress, int impressaoTipo) {
		
		/* Caso não haja nenhum endereco bluetooth préviamente salvo é mostrada a tela de pareamento de dispositivos.
		 * Caso contrário é realizada a conexão com a impressora e impressa a conta
		 */
		String progressTitleMsg = null;

		if (Util.isEmulator()){
			
		    switch (impressaoTipo) {
		    case Constantes.IMPRESSAO_FATURA:
		    	Log.i("Comando Fatura", new ImpressaoContaCosanpa().getComandoImpressaoFatura(getImovelSelecionado(), Constantes.IMPRESSAO_FATURA));
				progressTitleMsg = "Imprimindo Fatura";
		    	ControladorImovel.getInstancia().setupDataAfterPrinting(impressaoTipo);
		    	ControladorAcessoOnline.getInstancia().transmitirImovel(getApplicationContext(), increment);
				callProximoImovel();
		    	break;

		    case Constantes.IMPRESSAO_NOTIFICACAO_DEBITO:
		    	Log.i("Comando Notificação Débito", new ImpressaoContaCosanpa().imprimirNotificacaoDebito(getImovelSelecionado()));
		    	progressTitleMsg = "Imprimindo Notificação de Débito";
		    	ControladorImovel.getInstancia().setupDataAfterPrinting(impressaoTipo);
		    	ControladorAcessoOnline.getInstancia().transmitirImovel(getApplicationContext(), increment);
		    	break;

		    case Constantes.IMPRESSAO_FATURA_E_NOTIFICACAO:
		    	Log.i("Comando Fatura", new ImpressaoContaCosanpa().getComandoImpressaoFatura(getImovelSelecionado(), Constantes.IMPRESSAO_FATURA));
		    	Log.i("Comando Notificação Débito", new ImpressaoContaCosanpa().imprimirNotificacaoDebito(getImovelSelecionado()));
		    	progressTitleMsg = "Imprimindo Fatura e Notificação de Débito";
		    	ControladorImovel.getInstancia().setupDataAfterPrinting(impressaoTipo);
		    	ControladorAcessoOnline.getInstancia().transmitirImovel(getApplicationContext(), increment);
				callProximoImovel();
		    	break;
		    }

		}else{
			
			if (bluetoothAddress == null) {
	
				Intent intentBluetooth = new Intent();
		        intentBluetooth.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
		        startActivityForResult(intentBluetooth, 0);
	    	
			} else {
				
			    switch (impressaoTipo) {
			    case Constantes.IMPRESSAO_FATURA:
					progressTitleMsg = "Imprimindo Fatura";
			    	break;

			    case Constantes.IMPRESSAO_NOTIFICACAO_DEBITO:
			    	progressTitleMsg = "Imprimindo Notificação de Débito";
			    	break;

			    case Constantes.IMPRESSAO_FATURA_E_NOTIFICACAO:
			    	progressTitleMsg = "Imprimindo Fatura e Notificação de Débito";
			    	break;
			    }

	    		progress = new ProgressDialog(this);
	    		progress.setTitle(progressTitleMsg);
	    		progress.setMessage("Aguarde");
	    		progress.setCancelable(false);
	    		progress.setOnKeyListener(new DialogInterface.OnKeyListener() {

				    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				        if ( (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_BACK) && 
					         (event.getRepeatCount() == 0)) {
					            
					        return true; // Pretend we processed it
				        }
				        return false; // Any other keys are still processed as normal
				    }
				});
	    			
	    		new ImpressaoThread(bluetoothAddress, impressaoHandler, impressaoTipo, increment, MainTab.this).start();
	    		progress.show();
	    	}
		}
	}

	public void imprimirContaCondominial(int idImovelInicial, int idImovelFinal, List<Imovel> imoveis) {
		
		/* Caso não haja nenhum endereco bluetooth préviamente salvo é mostrada a tela de pareamento de dispositivos.
		 * Caso contrário é realizada a conexão com a impressora e impressa a conta
		 */
		if (Util.isEmulator()){
			
			for (Imovel imovel : imoveis) {

				int indiceImovelImpresso = getDataManipulator().getListaIdsCondominio(imovel.getEfetuarRateioConsumoHelper().getMatriculaMacro())
						.indexOf(Long.valueOf(imovel.getId()).intValue());
				
				String comando = new ImpressaoContaCosanpa().getComandoImpressaoFatura(imovel, Constantes.IMPRESSAO_FATURA);
				Log.i("COMANDO FATURA:", comando);
				
//				if (imovel.getContas() != null && imovel.getContas().size() > 0){
//					comando = new ImpressaoContaCosanpa().imprimirNotificacaoDebito(imovel);
//					Log.i("COMANDO NOTIFICAÇÃO DÉBITO:", comando);
//				}
				
				progressImpressaoCondominial.setProgress(indiceImovelImpresso);
				
				imovel.setIndcImovelImpresso(Constantes.SIM);
		    	imovel.setQuantidadeContasImpressas(1+(imovel.getQuantidadeContasImpressas()));

		    	// Guarda a data da impressao da conta de imovel nao-medido. Já que não possui data de leitura.
		    	if (imovel.getMedidor(Constantes.LIGACAO_AGUA) == null && 
		    		imovel.getMedidor(Constantes.LIGACAO_POCO) == null){
		    		imovel.setDataImpressaoNaoMedido(Util.dateToAnoMesDiaString(MedidorAguaTab.getCurrentDateByGPS()));
		    	}
				getDataManipulator().salvarImovel(imovel);
				
				// Perguntamos na ultima conta
			    if (imovel.getId() == idImovelFinal) {
			    	
			    	// Se for o último imóvel do condominio
			    	if (imovel.getId() == imovel.getEfetuarRateioConsumoHelper().getIdUltimoImovelMicro()){
			    		mensagemImpressaoCondominialOk(idImovelInicial, idImovelFinal);
			    	}else{
			    		mensagemImpressaoParcialCondominioOk("As últimas "+ Constantes.QUEBRA_CONTAS_IMOVEL_CONDOMINIO + " contas foram emitidas com sucesso ?", idImovelInicial, idImovelFinal);
			    	}
			    }
			}

		}else{
			
			if (ControladorRota.getInstancia().getBluetoothAddress() == null) {
	
				Intent intentBluetooth = new Intent();
		        intentBluetooth.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
		        startActivityForResult(intentBluetooth, 0);
	    	
			} else {
	    		String bluetoothAddress = ControladorRota.getInstancia().getBluetoothAddress();
	    		new ImpressaoCondominialThread(bluetoothAddress, imoveis, idImovelInicial, idImovelFinal).start();
	    	}
		}
	}
	
	public void controladorImpressaoCondominial(){
	
		// Carregamos as informações do hidrometro macro
		Imovel imovelMacro = getDataManipulator().selectImovel("matricula="+ getImovelSelecionado().getEfetuarRateioConsumoHelper().getMatriculaMacro(), true);

		EfetuarRateioConsumoHelper helper = imovelMacro.getEfetuarRateioConsumoHelper();

		// Calculamos o hidrometro macro
		ControladorConta.getInstancia().calcularValoresCondominio(imovelMacro,
																  imovelMacro.getConsumoAgua(), 
																  imovelMacro.getConsumoEsgoto());
		
		progressImpressaoCondominial = new ProgressDialog(this);
		progressImpressaoCondominial.setTitle("Imprimindo contas condominiais");
		progressImpressaoCondominial.setMessage("Imóveis impressos");
		progressImpressaoCondominial.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressImpressaoCondominial.setCancelable(false);
		progressImpressaoCondominial.setMax(getDataManipulator().selectQuantidadeImoveisCondominio(imovelMacro.getMatricula())-1);
		progressImpressaoCondominial.setOnKeyListener(new DialogInterface.OnKeyListener() {

		    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		        if ( (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_BACK) && 
			         (event.getRepeatCount() == 0)) {
			            
			        return true; // Pretend we processed it
		        }
		        return false; // Any other keys are still processed as normal
		    }
		});
		
		progressImpressaoCondominial.show();
	
		if( (getDataManipulator().getListaIdsCondominio(helper.getMatriculaMacro()).size()-1) >= Constantes.QUEBRA_CONTAS_IMOVEL_CONDOMINIO){
			imprimirContasCondominio(helper, (int)(imovelMacro.getId() + 1), (int)(imovelMacro.getId() + Constantes.QUEBRA_CONTAS_IMOVEL_CONDOMINIO));
			
		}else{
			imprimirContasCondominio(helper, (int)(imovelMacro.getId() + 1), helper.getIdUltimoImovelMicro());
		}
	}
	
	private void imprimirContasCondominio(EfetuarRateioConsumoHelper helper, int idImovelinicial, int idImovelFinal){
		
		List<Imovel> imoveis = new ArrayList<Imovel>();

		// Caso o valor da conta seja menor que o valor permitido
		// para ser impresso, não imprimir a conta
		boolean imprimirEnviarValorContaMenorMinimo = true;

		// Para cada imóvel MICRO ligado ou cortado de água:
		for (int indiceHidrometroMicro = idImovelinicial; indiceHidrometroMicro <= idImovelFinal; indiceHidrometroMicro++) {
	
		    Imovel imovelMicro = getDataManipulator().selectImovel("id="+indiceHidrometroMicro, true);
	
		    if (helper.getContaParaRateioAgua() > 0){
			    
		    	if (imovelMicro.getConsumoAgua() == null){
		    		imovelMicro.setConsumoAgua(new Consumo());
		    	}
		    	
		    	imovelMicro.setValorRateioAgua(Util.arredondar(
		    			helper.getContaParaRateioAgua() * imovelMicro.getQuantidadeEconomiasTotal() 
		    			/ helper.getQuantidadeEconomiasAguaTotal(),2));
		    	
		    	imovelMicro.setConsumoRateioAgua(helper.getConsumoParaRateioAgua() * imovelMicro.getQuantidadeEconomiasTotal()
		    		    / helper.getQuantidadeEconomiasAguaTotal());
		    }else{
		    	imovelMicro.setValorRateioAgua(Util.arredondar(0));
		    	imovelMicro.setConsumoRateioAgua(0);
		    }
		    
		    if (helper.getContaParaRateioEsgoto() > 0){
		    	
		    	if (imovelMicro.getConsumoEsgoto() == null){
		    		imovelMicro.setConsumoEsgoto(new Consumo());
		    	}
	
		    	imovelMicro.setValorRateioEsgoto(Util.arredondar(
		    			helper.getContaParaRateioEsgoto() * imovelMicro.getQuantidadeEconomiasTotal() 
		    			/ helper.getQuantidadeEconomiasEsgotoTotal(),2));
	
		    	imovelMicro.setConsumoRateioEsgoto(helper.getConsumoParaRateioEsgoto() * imovelMicro.getQuantidadeEconomiasTotal()
		    		    / helper.getQuantidadeEconomiasEsgotoTotal());
		    }else{
		    	imovelMicro.setValorRateioEsgoto(Util.arredondar(0));
		    	imovelMicro.setConsumoRateioEsgoto(0);
		    }
		    
		    // Calculamos as contas imovel a imovel
		    ControladorConta.getInstancia().calcularValoresCondominio(imovelMicro,
			    imovelMicro.getConsumoAgua(),
			    imovelMicro.getConsumoEsgoto());

		    getDataManipulator().salvarImovel(imovelMicro);
				
		    // Caso o valor da conta seja menor que o valor
		    // permitido para ser impresso, não imprimir a conta
		    // ou
		    // o valor do crédito for maior que o valor da conta, não imprime a conta
		    imprimirEnviarValorContaMenorMinimo = imovelMicro.isValorContaAcimaDoMinimo();
	
		    System.out.println("********** " + imovelMicro.getMatricula() + "********** ");
		    System.out.println("Consumo Agua: " + (imovelMicro.getConsumoAgua() != null ? imovelMicro
		    		.getConsumoAgua().getConsumoCobradoMes() + "" : "Nulo"));
		    System.out.println("Valor de Rateio de Agua: "  + (imovelMicro.getConsumoAgua() != null ? imovelMicro
		    		.getValorRateioAgua() + "" : "Nulo"));
		    System.out.println("Consumo Esgoto: " + (imovelMicro.getConsumoEsgoto() != null ? imovelMicro
		    		.getConsumoEsgoto().getConsumoCobradoMes() + "" : "Nulo"));
		    System.out.println("Valor de Rateio de Esgoto: " + (imovelMicro.getConsumoEsgoto() != null ? imovelMicro
		    		.getValorRateioEsgoto() + "" : "Nulo"));
		    System.out.println("*************************** ");

		    // Imprimimos, Conta
		    if (imprimirEnviarValorContaMenorMinimo
			    && imovelMicro.getIndcEmissaoConta() == Constantes.SIM) {
	
			    if (imovelMicro != null) {
					imoveis.add(imovelMicro);
			    }
		    }
		}
		imprimirContaCondominial(idImovelinicial, idImovelFinal, imoveis);
	}

	
	public void mensagemImpressaoParcialCondominioOk(String mensagem, final int idImovelinicial, final int idImovelFinal) {

        final AlertDialog alertMessage = new AlertDialog.Builder(this).create();
		alertMessage.setMessage(mensagem);
		alertMessage.setOnKeyListener(new DialogInterface.OnKeyListener() {

		    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		        if ( (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_BACK) && 
		        	 (event.getRepeatCount() == 0)) {
		            
		        	return true; // Pretend we processed it
		        }
		        return false; // Any other keys are still processed as normal
		    }
		});
		
		alertMessage.setButton(AlertDialog.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				
				alertMessage.dismiss();
				mensagemReemitirImpressaoParcialCondominio(idImovelinicial, idImovelFinal);
			}
		});

		alertMessage.setButton(AlertDialog.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				
				alertMessage.dismiss();
				if (idImovelFinal+Constantes.QUEBRA_CONTAS_IMOVEL_CONDOMINIO <= getImovelSelecionado().getEfetuarRateioConsumoHelper().getIdUltimoImovelMicro()){
					imprimirContasCondominio(getImovelSelecionado().getEfetuarRateioConsumoHelper(), 
											 idImovelFinal+1, 
											 idImovelFinal+Constantes.QUEBRA_CONTAS_IMOVEL_CONDOMINIO);
					
				}else{
					imprimirContasCondominio(getImovelSelecionado().getEfetuarRateioConsumoHelper(), 
											 idImovelFinal+1, 
											 getImovelSelecionado().getEfetuarRateioConsumoHelper().getIdUltimoImovelMicro());
				}
			}
		});

		alertMessage.show();
    }
	
	public void mensagemReemitirImpressaoParcialCondominio(final int idImovelinicial, final int idImovelFinal) {

        final AlertDialog alertMessage = new AlertDialog.Builder(this).create();
		alertMessage.setMessage("Reemitir últimas " + (idImovelFinal-idImovelinicial+1) + " contas?");
		alertMessage.setOnKeyListener(new DialogInterface.OnKeyListener() {

		    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		        if ( (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_BACK) && 
			         (event.getRepeatCount() == 0)) {
			            
			        return true; // Pretend we processed it
		        }
		        return false; // Any other keys are still processed as normal
		    }
		});

		alertMessage.setButton(AlertDialog.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				alertMessage.dismiss();
				progressImpressaoCondominial.dismiss();
				// abortar impressao
			}
		});

		alertMessage.setButton(AlertDialog.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				alertMessage.dismiss();
				progressImpressaoCondominial.setProgress(idImovelinicial-1);
				imprimirContasCondominio(getImovelSelecionado().getEfetuarRateioConsumoHelper(), idImovelinicial, idImovelFinal);
			}
		});

		alertMessage.show();
    }
	

	public void mensagemImpressaoCondominialOk(final int idImovelinicial, final int idImovelFinal) {

        final AlertDialog alertMessage = new AlertDialog.Builder(this).create();
		alertMessage.setMessage("As últimas "+ (idImovelFinal-idImovelinicial+1) + " contas foram emitidas com sucesso ?");
		alertMessage.setOnKeyListener(new DialogInterface.OnKeyListener() {

		    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		        if ( (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_BACK) && 
			         (event.getRepeatCount() == 0)) {
			            
			        return true; // Pretend we processed it
		        }
		        return false; // Any other keys are still processed as normal
		    }
		});
		
		alertMessage.setButton(AlertDialog.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				alertMessage.dismiss();
				mensagemReemitirImpressaoParcialCondominio(idImovelinicial, idImovelFinal);
			}
		});
		
		alertMessage.setButton(AlertDialog.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {

				alertMessage.dismiss();

				// ID de todos os imoveis do condominio - Macro e micros
				List<Integer> listaIdsCondominio = getDataManipulator().getListaIdsCondominio(getImovelSelecionado().getEfetuarRateioConsumoHelper().getMatriculaMacro());
				
				//define Status Concluido
				for(int i=0; i<listaIdsCondominio.size(); i++){
					Imovel imovelCondominial = getDataManipulator().selectImovel("id="+listaIdsCondominio.get(i), false);
					imovelCondominial.setIndcImovelImpresso(Constantes.SIM);
					imovelCondominial.setImovelStatus(Constantes.IMOVEL_STATUS_CONCLUIDO);
					getDataManipulator().salvarImovel(imovelCondominial);
				}
				
				progressImpressaoCondominial.dismiss();
				
				new EnviarImoveisCondominioThread(listaIdsCondominio).start();

		    	finish();
				ControladorImovel.getInstancia().setImovelSelecionado(ControladorRota.getInstancia().getDataManipulator().selectImovel("id = " + getImovelSelecionado().getId(), true));
				Intent myIntent = new Intent(getApplicationContext(), MainTab.class);
				startActivity(myIntent);
			}
		});

		alertMessage.show();
    }

	@Override
	protected void onActivityResult(int arg0, int resultCode, Intent arg2) {
		if (resultCode == 0) {
			Set<BluetoothDevice> dispositivosPareados = bluetoothAdapter.getBondedDevices();
	    	Iterator<BluetoothDevice> iterator = dispositivosPareados.iterator();
	    	
	    	listaDispositivos = new ListView(this);
	    	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
	    	listaDispositivos.setAdapter(arrayAdapter);
	    	listaDispositivos.setCacheColorHint(0);
	    	listaDispositivos.setOnItemClickListener(this);
	    	
	    	while (iterator.hasNext()) {
	    		BluetoothDevice device = iterator.next();
	    		// Seleciona apenas as impressoras pareadas
	    		if (device.getBluetoothClass().getMajorDeviceClass() == 1536) {
	    			arrayAdapter.add(device.getName() + "\n" + device.getAddress());
	    		}
	    	}

	    	showMessage(listaDispositivos, "Impressoras pareadas");
		}
	}
	
	// Handler on the main (UI) thread that will receive messages from the second thread and update the progress.
    final Handler carregarRotaHandler = new Handler() {
        public void handleMessage(Message msg) {

        	int total = msg.getData().getInt("message");

            if (total == 10){
            	
                dismissDialog(Constantes.DIALOG_ID_CARREGAR_ROTA);
    			setResult(RESULT_FIRST_USER, new Intent(getBaseContext(), Fachada.class));
        		finish();
            }
        }
    };


	public void mensagemConsumo(String mensagem, double valor) {

        AlertDialog alertMessage = new AlertDialog.Builder(this).create();
		alertMessage.setTitle("Aviso");
		alertMessage.setMessage( mensagem + "\n" + "Valor: " + valor );
		alertMessage.setOnKeyListener(new DialogInterface.OnKeyListener() {

		    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		        if ( (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_BACK) && 
			         (event.getRepeatCount() == 0)) {
			            
			        return true; // Pretend we processed it
		        }
		        return false; // Any other keys are still processed as normal
		    }
		});
		
		alertMessage.setButton(AlertDialog.BUTTON_NEGATIVE, "Voltar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				
				getDataManipulator().salvarImovel(getImovelSelecionado());
			}
		});

		alertMessage.setButton(AlertDialog.BUTTON_POSITIVE, "Confirmar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {


				// Nao deve imprimir fatura individual de imovel condominial
				if (!getImovelSelecionado().isImovelCondominio()){
					
				    switch (ControladorImovel.getInstancia().getImpressaoTipo(MainTab.this)) {
				    case Constantes.IMPRESSAO_FATURA:
				    	imprimirConta(ControladorRota.getInstancia().getBluetoothAddress(), Constantes.IMPRESSAO_FATURA);
				    	break;

				    case Constantes.IMPRESSAO_FATURA_E_NOTIFICACAO:
				    	imprimirConta(ControladorRota.getInstancia().getBluetoothAddress(), Constantes.IMPRESSAO_FATURA_E_NOTIFICACAO);
				    	break;
				        
				    case Constantes.IMPRESSAO_NOTIFICACAO_DEBITO:
				    	imprimirConta(ControladorRota.getInstancia().getBluetoothAddress(), Constantes.IMPRESSAO_NOTIFICACAO_DEBITO);
				    	break;

				    case Constantes.IMPRESSAO_NAO_PERMITIDA:
						showMessage(BusinessConta.getInstancia().getMensagemPermiteImpressao());
						getImovelSelecionado().setImovelStatus(Constantes.IMOVEL_STATUS_CONCLUIDO);
						setTabColor();
						getDataManipulator().salvarImovel(getImovelSelecionado());
						break;
				    }

				}else{
					// imovel condominial avança para o próximo imovel.
			    	callProximoImovel();
				}
			}
		});

		alertMessage.show();
    }

	public void showMessage(View view, String message){

		dialog = new AlertDialog.Builder(this).create();
    	dialog.setTitle(message);
    	dialog.setView(view);
    	dialog.show();
	}
	
	public void showMessage(String message){

		dialog = new AlertDialog.Builder(this).create();
    	dialog.setTitle("Aviso");
    	dialog.setMessage(message);
    	
    	dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				dialog.dismiss();
			}
		});
    	
    	dialog.show();
	}
	
	public static Imovel getImovelSelecionado() {
		return ControladorImovel.getInstancia().getImovelSelecionado();
	}
	
	public void onTabChanged(String tabId) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
    	
		if (tabId.equals("imovel"))
			ft.add(android.R.id.tabcontent, new ImovelTab());
		else if (tabId.equals("medidor"))
			ft.add(android.R.id.tabcontent, new MedidorAguaTab());
		else if (tabId.equals("conta"))
			ft.add(android.R.id.tabcontent, new ContaTab());
		
//		if (getImovelSelecionado().getImovelStatus() == Constantes.IMOVEL_STATUS_CONCLUIDO) {
//			tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.tab_custom_green);
//		} else if (getImovelSelecionado().getImovelStatus() == Constantes.IMOVEL_STATUS_PENDENTE) {
//			tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundResource(R.drawable.tab_custom_white);
//		}
		
    	ft.commit();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		if (!getImovelSelecionado().isImovelCondominio()){
		
			dialog.dismiss();

//			if (getImovelSelecionado().getContas() != null && getImovelSelecionado().getContas().size() >0){
//				imprimirConta(String.valueOf(((TextView) view).getText()).split("\n")[1], Constantes.IMPRESSAO_FATURA_E_NOTIFICACAO);
//			}else{
				imprimirConta(String.valueOf(((TextView) view).getText()).split("\n")[1], Constantes.IMPRESSAO_FATURA);
//			}
			
		}else{
			getDataManipulator().updateConfiguracao("bluetooth_address", String.valueOf(((TextView) view).getText()).split("\n")[1]);

			dialog.dismiss();
			controladorImpressaoCondominial();		
		}		
	}
	
	class ImpressaoCondominialThread extends Thread {
		String bluetoothAddress;
		List<Imovel> imoveisToBePrinted;
		int idImovelInicial;
		int idImovelFinal;
		
		public ImpressaoCondominialThread(String address, List<Imovel> imovelToBePrinted, int idImovelInicial, int idImovelFinal) {
			this.bluetoothAddress = address;
			this.imoveisToBePrinted = imovelToBePrinted;
			this.idImovelInicial = idImovelInicial;
			this.idImovelFinal = idImovelFinal;
		}
		
		@Override
		public void run() {
			conexao = new BluetoothPrinterConnection(bluetoothAddress);

			System.out.println("Imoveis para impressao: " + imoveisToBePrinted);

			try {
				conexao.open();
				if (conexao.isConnected()) {
					
					for (Imovel imovel : imoveisToBePrinted) {
						
						int indiceImovelImpresso = getDataManipulator().getListaIdsCondominio(imovel.getEfetuarRateioConsumoHelper().getMatriculaMacro())
								.indexOf(Long.valueOf(imovel.getId()).intValue());
						
						System.out.println("Indice Imovel Impresso: " + indiceImovelImpresso);
						
						String comando = null;
						
						comando = new ImpressaoContaCosanpa().getComandoImpressaoFatura(imovel, Constantes.IMPRESSAO_FATURA);
						Log.i("COMANDO FATURA:", comando);
						conexao.write(comando.getBytes());
						
//						if (imovel.getContas() != null && imovel.getContas().size() > 0){
//							comando = new ImpressaoContaCosanpa().imprimirNotificacaoDebito(imovel);
//							Log.i("COMANDO NOTIFICAÇÃO DÉBITO:", comando);
//							conexao.write(comando.getBytes());
//						}
						
						getDataManipulator().updateConfiguracao("bluetooth_address", bluetoothAddress);
						Thread.sleep(1500);
						
						progressImpressaoCondominial.setProgress(indiceImovelImpresso);
						
						imovel.setIndcImovelImpresso(Constantes.SIM);
				    	imovel.setQuantidadeContasImpressas(1+(imovel.getQuantidadeContasImpressas()));
				    	// Guarda a data da impressao da conta de imovel nao-medido. Já que não possui data de leitura.
				    	if (imovel.getMedidor(Constantes.LIGACAO_AGUA) == null && 
				    		imovel.getMedidor(Constantes.LIGACAO_POCO) == null){
				    		imovel.setDataImpressaoNaoMedido(Util.dateToAnoMesDiaString(MedidorAguaTab.getCurrentDateByGPS()));
				    	}
						getDataManipulator().salvarImovel(imovel);
						
						// Verifica se é a última conta
					    if (imovel.getId() == idImovelFinal) {
					 
					    	// Se for último imóvel do condomínio
							if ( idImovelFinal  == imovel.getEfetuarRateioConsumoHelper().getIdUltimoImovelMicro()){

								// Carregamos as informações do imovel Macro
								Imovel imovelMacro = getDataManipulator().selectImovel("matricula="+ imovel.getEfetuarRateioConsumoHelper().getMatriculaMacro(), true);

								comando = new ImpressaoContaCosanpa().getComandoImpressaoFatura(imovelMacro, Constantes.IMPRESSAO_EXTRATO_CONDOMINIAL);
								Log.i("COMANDO EXTRATO CONDOMINIAL:", comando);
								conexao.write(comando.getBytes());
								
								getDataManipulator().updateConfiguracao("bluetooth_address", bluetoothAddress);
								Thread.sleep(3000);
					    	}
					    	
					    	// Se for o último imóvel do condominio
					    	if (imovel.getId() == imovel.getEfetuarRateioConsumoHelper().getIdUltimoImovelMicro()){
					    		conexao.close();
					    		Looper.prepare();
					    		mensagemImpressaoCondominialOk(idImovelInicial, idImovelFinal);
					    		Looper.loop();
					    		Looper.getMainLooper().quit();
					    	}else{
					    		conexao.close();
					    		Looper.prepare();
					    		mensagemImpressaoParcialCondominioOk("As últimas "+ Constantes.QUEBRA_CONTAS_IMOVEL_CONDOMINIO + " contas foram emitidas com sucesso ?", idImovelInicial, idImovelFinal);
					    		Looper.loop();
					    		Looper.getMainLooper().quit();
					    	}
					    }
					}
					conexao.close();
				}
			} catch (ZebraPrinterConnectionException e) {

				e.printStackTrace();
				Util.salvarLog(new Date(), e.fillInStackTrace());
				progressImpressaoCondominial.dismiss();
				
				Looper.prepare();
				
				AlertDialog.Builder a = new AlertDialog.Builder(MainTab.this);
				a.setTitle("Erro ao imprimir fatura condominial");
				a.setMessage("impressora não encontrada");
				a.setPositiveButton("Selecionar impressora", new DialogInterface.OnClickListener() {
				     
					public void onClick(DialogInterface arg0, int arg1) {
						
						/* Caso não seja possível localizar a impressora,
						 * o campo de endereco bluetooth é apagado e em seguida é chamado o método de impressão 
						 */
						getDataManipulator().updateConfiguracao("bluetooth_address", null);
						
						imprimirContaCondominial(idImovelInicial, idImovelFinal, imoveisToBePrinted);
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
	}

	protected boolean isTabMedidorAguaNeeded(){
	    boolean result = false;
	    
		if (ControladorRota.getInstancia().getDadosGerais().getIdCalculoMedia() == Constantes.NAO){

	    	if ( (getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) != null) && 
	    		 (!getImovelSelecionado().getSituacaoLigAgua().equals(Constantes.CORTADO)) ){
	
		    	if ( (!getImovelSelecionado().isImovelInformativo())  ||
		    		 (getImovelSelecionado().getIndcCondominio() == Constantes.SIM) ){
		    		
		    		result = true;
		    	}
		    }
	    }
		return result;
	}
	
	protected boolean isTabMedidorPocoNeeded(){
	    boolean result = false;
		
	    if (ControladorRota.getInstancia().getDadosGerais().getIdCalculoMedia() == Constantes.NAO){

		    if ( (getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) != null) && 
	    		 ( !getImovelSelecionado().getSituacaoLigAgua().equals(Constantes.CORTADO)) ){
	
		    	if ( (!getImovelSelecionado().isImovelInformativo()) ||
		    		 (getImovelSelecionado().getIndcCondominio() == Constantes.SIM) ){
		    	
		    		result = true;
		    	}
		    }
	    }
		return result;
	}

	protected boolean isTabContaNeeded(){
	    boolean result = false;
		
		if ( ( (getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) == null) && 
	    	   (getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) == null) &&
	    	   (!getImovelSelecionado().isImovelInformativo()) ) 
	    			||
	    	 ( (getImovelSelecionado().getSituacaoLigAgua().equals(Constantes.CORTADO)) &&
	    	   (!getImovelSelecionado().isImovelInformativo()) )
	    			||
	    	 (ControladorRota.getInstancia().getDadosGerais().getIdCalculoMedia() == Constantes.SIM))
		{
	    	
			result = true;
			if (getImovelSelecionado().getIndcImovelCalculado() == Constantes.NAO){

				BusinessConta.getInstancia(this).imprimirCalculo(false);
				System.out.println("Imovel ja calculado: " + (getImovelSelecionado().getIndcImovelCalculado() == Constantes.SIM));
			}
		}
		return result;
	}
	
    /**
     * Inicia procedimento para impressão das faturas condominiais. 
     */
	protected void imprimirCondominio(){
		
		// Realiza calculos de rateio condominial
		ControladorConta.getInstancia().determinarRateio();
		
		// Verifica se deve reter impressao das contas condominiais.
		if (getImovelSelecionado().getEfetuarRateioConsumoHelper().isCondominioRetido()){
			
			showMessage("Contas do condomínio retidas. Impressao não permitida!");

			// Para todos os imoveis do condominio - Macro e micros
			List<Integer> listaIdsCondominio = getDataManipulator().getListaIdsCondominio(getImovelSelecionado().getEfetuarRateioConsumoHelper().getMatriculaMacro());
	
			for(int i=0; i<listaIdsCondominio.size(); i++){
				Imovel imovelCondominial = getDataManipulator().selectImovel("id="+listaIdsCondominio.get(i), false);
				imovelCondominial.setIndcGeracaoConta(Constantes.NAO);
				imovelCondominial.setImovelStatus(Constantes.IMOVEL_STATUS_CONCLUIDO);
				setTabColor();
				getDataManipulator().salvarImovel(imovelCondominial);
			}

		}else{
			controladorImpressaoCondominial();
		}
	}
	
    /**
     * Inicia procedimento para calcular consumo de água e esgoto do imóvel e imprime a fatura caso seja permitido. 
     */
	protected void calculoEImpressao(){
		
		boolean descartaLeitura = false;
		
		// Se o imovel já foi concluido e possui consumo de agua ou esgoto já calculado.
		if ( (getImovelSelecionado().getImovelStatus() != Constantes.IMOVEL_STATUS_PENDENTE) &&
			 (getImovelSelecionado().getConsumoAgua() != null || getImovelSelecionado().getConsumoEsgoto() != null) ) {
			
			// Nao será recalculado o consumo
			Toast.makeText(this, "Novos valores de leitura e anormalidade serão desconsiderados.", Toast.LENGTH_LONG).show();
			descartaLeitura = true;
		}

		Consumo validacaoConsumo = BusinessConta.getInstancia(this).imprimirCalculo(descartaLeitura);
		
		if(!descartaLeitura){

			if (validacaoConsumo != null){
				
				String mensagemAnormalidadeConsumo = "";
				mensagemAnormalidadeConsumo = Util.validarAnormalidadeConsumo(validacaoConsumo);
			    System.out.println("Consumo = " + mensagemAnormalidadeConsumo);
			    
			    // Se houve anormalidade de consumo
			    if (mensagemAnormalidadeConsumo != null && !mensagemAnormalidadeConsumo.equals("")) {
				
			    	if (getImovelSelecionado().getValorConta() != Constantes.NULO_DOUBLE) {
					    System.out.println("Valor = " + getImovelSelecionado().getValorConta());
					    mensagemConsumo(mensagemAnormalidadeConsumo, getImovelSelecionado().getValorConta());
					}
			    
			    // Nao houve anormalidade de consumo. 	
			    }else{

					// Nao deve imprimir fatura individual de imovel condominial
			    	if (!getImovelSelecionado().isImovelCondominio()){

					    switch (ControladorImovel.getInstancia().getImpressaoTipo(getApplicationContext())) {
					    case Constantes.IMPRESSAO_FATURA:
					    	imprimirConta(ControladorRota.getInstancia().getBluetoothAddress(), Constantes.IMPRESSAO_FATURA);
					    	break;

					    case Constantes.IMPRESSAO_FATURA_E_NOTIFICACAO:
					    	imprimirConta(ControladorRota.getInstancia().getBluetoothAddress(), Constantes.IMPRESSAO_FATURA_E_NOTIFICACAO);
					    	break;
					        
					    case Constantes.IMPRESSAO_NOTIFICACAO_DEBITO:
					    	imprimirConta(ControladorRota.getInstancia().getBluetoothAddress(), Constantes.IMPRESSAO_NOTIFICACAO_DEBITO);
					    	break;

					    case Constantes.IMPRESSAO_NAO_PERMITIDA:
			    			getImovelSelecionado().setImovelStatus(Constantes.IMOVEL_STATUS_CONCLUIDO);
			    			setTabColor();
			    			getDataManipulator().salvarImovel(getImovelSelecionado());
			    			ControladorAcessoOnline.getInstancia().transmitirImovel(this, increment);
			    			break;
					    }
			    		
			    	}else {
			    		// imovel condominial avança para o próximo imovel.
				    	callProximoImovel();
			    	}
			    }
			    mensagemAnormalidadeConsumo = null;
  			
			} else if (getImovelSelecionado().getSituacaoLigAgua().equals(Constantes.LIGADO) == false){

				// Nao deve imprimir fatura individual de imovel condominial
				if (!getImovelSelecionado().isImovelCondominio()) {

				    switch (ControladorImovel.getInstancia().getImpressaoTipo(getApplicationContext())) {
				    case Constantes.IMPRESSAO_FATURA:
				    	imprimirConta(ControladorRota.getInstancia().getBluetoothAddress(), Constantes.IMPRESSAO_FATURA);
				    	break;

				    case Constantes.IMPRESSAO_FATURA_E_NOTIFICACAO:
				    	imprimirConta(ControladorRota.getInstancia().getBluetoothAddress(), Constantes.IMPRESSAO_FATURA_E_NOTIFICACAO);
				        break;
				        
				    case Constantes.IMPRESSAO_NOTIFICACAO_DEBITO:
				    	imprimirConta(ControladorRota.getInstancia().getBluetoothAddress(), Constantes.IMPRESSAO_NOTIFICACAO_DEBITO);
				    	break;
				    	
				    case Constantes.IMPRESSAO_NAO_PERMITIDA:
		    			getImovelSelecionado().setImovelStatus(Constantes.IMOVEL_STATUS_CONCLUIDO);
		    			setTabColor();
		    			getDataManipulator().salvarImovel(getImovelSelecionado());
		    			ControladorAcessoOnline.getInstancia().transmitirImovel(this, increment);
		    			break;
				    }

				} else {
					// imovel condominial avança para o próximo imovel.
					callProximoImovel();
				}
			}
		
		// Se deve descartar leitura, imóvel já está calculado e salvo no DB.
		} else {
			if (!getImovelSelecionado().isImovelCondominio()) {
				if (BusinessConta.getInstancia(this).isImpressaoPermitida()) {

//					if (getImovelSelecionado().getContas() != null && getImovelSelecionado().getContas().size() > 0 ){
//						imprimirConta(ControladorRota.getInstancia().getBluetoothAddress(), Constantes.IMPRESSAO_FATURA_E_NOTIFICACAO);
//					}else{
						imprimirConta(ControladorRota.getInstancia().getBluetoothAddress(), Constantes.IMPRESSAO_FATURA);
//					}
				}

			} else {
				// imovel condominial avança para o próximo imovel.
				callProximoImovel();
			}
		}
	}
	
    /**
     * Verifica se todos os imóveis condominiais já possuem consumo calculado. 
     */
	protected boolean isCondominioConcluido(){
		boolean isConcluido = false; 
		
		List<Integer> listaIdsNaoCalculados = getImovelSelecionado().getEfetuarRateioConsumoHelper().getIdsAindaFaltamSerCalculados();
		
		if ( listaIdsNaoCalculados != null && listaIdsNaoCalculados.size() == 0){
			isConcluido = true;
		 }
		
		return isConcluido;
	}

    public DataManipulator getDataManipulator(){
    	return ControladorRota.getInstancia().getDataManipulator();
    }

	public void onProviderDisabled(String provider) {
        // Check if enabled and if not send user to the GSP settings
		dialogMessage = " GPS está desligado. Por favor, ligue-o para continuar a leitura. ";
    	showDialog(Constantes.DIALOG_ID_ERRO_GPS_DESLIGADO);
	}
	
	public void onProviderEnabled(String provider) {
		Toast.makeText( getApplicationContext(),"GPS ligado",Toast.LENGTH_SHORT).show();
	}
	
	public void onStatusChanged(String provider, int status, Bundle extras) {}
	
	@Override
	protected Dialog onCreateDialog(final int id) {
	        
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		AlertDialog.Builder builder;
	  
		if (id ==  Constantes.DIALOG_ID_ERRO || 
	    		  id ==  Constantes.DIALOG_ID_SUCESSO || 
	    		  id ==  Constantes.DIALOG_ID_ERRO_GPS_DESLIGADO ||
	    		  id == Constantes.DIALOG_ID_ENDERECO_BLUETOOTH_FALTANDO){
	    	
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			  
			final View layoutCustonDialog = inflater.inflate(R.layout.custon_dialog, (ViewGroup) findViewById(R.id.layout_root));
	        ((TextView)layoutCustonDialog.findViewById(R.id.messageDialog)).setText(dialogMessage);
	        
	        if (id == Constantes.DIALOG_ID_SUCESSO){
		        ((ImageView)layoutCustonDialog.findViewById(R.id.imageDialog)).setImageResource(R.drawable.save);

	        }else {
		        ((ImageView)layoutCustonDialog.findViewById(R.id.imageDialog)).setImageResource(R.drawable.aviso);
	        }
	        
	        builder = new AlertDialog.Builder(this);
	        builder.setView(layoutCustonDialog);
	        builder.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	        	
	        	public void onClick(DialogInterface dialog, int whichButton) {
	        		removeDialog(id);

	        		if (id == Constantes.DIALOG_ID_ERRO_GPS_DESLIGADO){
	        			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	        			startActivity(intent);

	        		}else if (id == Constantes.DIALOG_ID_ENDERECO_BLUETOOTH_FALTANDO){
						Intent intentBluetooth = new Intent();
				        intentBluetooth.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
				        startActivityForResult(intentBluetooth, 0);
	        		}
	        	}
	        });

	        AlertDialog messageDialog = builder.create();
	        return messageDialog;
		}
        return null;
	}
}
