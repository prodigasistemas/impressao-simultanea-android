package views;

import helper.EfetuarRateioConsumoHelper;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import model.Consumo;
import model.Imovel;
import util.Constantes;
import util.ImpressaoContaCosanpa;
import util.Util;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import android.widget.Toast;
import background.EnviarImovelOnlineThread;
import business.BusinessConta;
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

@SuppressLint("NewApi")
public class MainTab extends FragmentActivity implements TabHost.OnTabChangeListener, OnItemClickListener {

	private static TabHost tabHost;
	private BluetoothAdapter bluetoothAdapter;
	private ListView listaDispositivos;
	AlertDialog dialog;
	private ProgressDialog progress;
	private ZebraPrinterConnection conexao;
	private static int increment;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.maintab);
	    
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
	
	public static void setTabColor() {
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
 
    
	@TargetApi(5)
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
	    	if(Util.compararData(getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), Util.dataAtual()) > 0){
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
		
		Imovel imovelPendente = getDataManipulator().selectImovel("imovel_status = "+Constantes.IMOVEL_STATUS_PENDENTE);
		
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
	
	private void tranmitirImovel() {
		
		if (getImovelSelecionado().getIndcImovelEnviado() == Constantes.IMOVEL_TRANSMITIDO) {
			return;
		}
		
		final Handler handler = new Handler() {
	        public void handleMessage(Message msg) {
	            
	        	// Get the current value of the variable total from the message data and update the progress bar.
	        	int cadastroOnline = msg.getData().getInt("envioCadastroOnline" + String.valueOf(increment));
	         }
	    };
	    
	    new EnviarImovelOnlineThread(handler, this, increment, getImovelSelecionado()).start();
	}
	
	public void imprimirConta() {
		
		/* Caso não haja nenhum endereco bluetooth préviamente salvo é mostrada a tela de pareamento de dispositivos.
		 * Caso contrário é realizada a conexão com a impressora e impressa a conta
		 */

		if (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")){
			
	    	Log.i("Comando", new ImpressaoContaCosanpa().getComando(getImovelSelecionado()));
			setupDataAfterPrinting(false);
			
		}else{
			
			if (ControladorRota.getInstancia().getBluetoothAddress() == null) {
	
				Intent intentBluetooth = new Intent();
		        intentBluetooth.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
		        startActivityForResult(intentBluetooth, 0);
	    	
			} else {
	    		String bluetoothAddress = ControladorRota.getInstancia().getBluetoothAddress();
	    		progress = new ProgressDialog(this);
	    		progress.setTitle("Imprimindo conta");
	    		progress.setMessage("Aguarde");
	    		progress.setCancelable(false);
	    		progress.setOnKeyListener(new DialogInterface.OnKeyListener() {

				    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				        if (keyCode == KeyEvent.KEYCODE_SEARCH && event.getRepeatCount() == 0) {
				            return true; // Pretend we processed it
				        
				        }else if (keyCode == KeyEvent.KEYCODE_HOME && event.getRepeatCount() == 0) {
				            return true; // Pretend we processed it
				        }
				        return false; // Any other keys are still processed as normal
				    }
				});
	    			
	    		new ImpressaoThread(bluetoothAddress, getImovelSelecionado()).start();
	    			
	    		progress.show();
	    		
	    	}
		}
	}

	public void imprimirContaCondominial() {
		
		/* Caso não haja nenhum endereco bluetooth préviamente salvo é mostrada a tela de pareamento de dispositivos.
		 * Caso contrário é realizada a conexão com a impressora e impressa a conta
		 */
		if (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")){
			
			Log.i("Comando", new ImpressaoContaCosanpa().getComando(getImovelSelecionado()));
			setupDataAfterPrinting(true);
			
		}else{
			
			if (ControladorRota.getInstancia().getBluetoothAddress() == null) {
	
				Intent intentBluetooth = new Intent();
		        intentBluetooth.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
		        startActivityForResult(intentBluetooth, 0);
	    	
			} else {
	    		String bluetoothAddress = ControladorRota.getInstancia().getBluetoothAddress();
	    		progress = new ProgressDialog(this);
	    		progress.setTitle("Imprimindo conta");
	    		progress.setMessage("Aguarde");
	    		progress.setCancelable(false);
	    		progress.setOnKeyListener(new DialogInterface.OnKeyListener() {

				    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				        if (keyCode == KeyEvent.KEYCODE_SEARCH && event.getRepeatCount() == 0) {
				            return true; // Pretend we processed it
				        
				        }else if (keyCode == KeyEvent.KEYCODE_HOME && event.getRepeatCount() == 0) {
				            return true; // Pretend we processed it
				        }
				        return false; // Any other keys are still processed as normal
				    }
				});
	    			
	    		new ImpressaoThread(bluetoothAddress, getImovelSelecionado()).start();
	    			
	    		progress.show();
	    		
	    	}
		}
	}
	
	public void controladorImpressaoCondominial(){
//		Vector imoveisParaEnvio = new Vector();
	
		// Carregamos as informações do hidrometro macro
		Imovel imovelMacro = getDataManipulator().selectImovel("matricula="+ getImovelSelecionado().getEfetuarRateioConsumoHelper().getMatriculaMacro());

		EfetuarRateioConsumoHelper helper = imovelMacro.getEfetuarRateioConsumoHelper();

		// Calculamos o hidrometro macro
		ControladorConta.getInstancia().calcularValoresCondominio(imovelMacro,
																  imovelMacro.getConsumoAgua(), 
																  imovelMacro.getConsumoEsgoto());
//		imoveisParaEnvio.addElement(imovelMacro);
	
		if( (getDataManipulator().getListaIdsCondominio(helper.getMatriculaMacro()).size()-1) >= Constantes.QUEBRA_CONTAS_IMOVEL_CONDOMINIO){
			imprimirContasCondominio(helper, (int)(imovelMacro.getId() + 1), (int)(imovelMacro.getId() + Constantes.QUEBRA_CONTAS_IMOVEL_CONDOMINIO));
			
		}else{
			imprimirContasCondominio(helper, (int)(imovelMacro.getId() + 1), helper.getIdUltimoImovelMicro());
		}
	}
	
	private void imprimirContasCondominio(EfetuarRateioConsumoHelper helper, int idImovelinicial, int idImovelFinal){

		int contadorImoveisParaEnvio = 1;
//		Vector imoveisParaEnvio = new Vector();
	
		// Caso o valor da conta seja menor que o valor permitido
		// para ser impresso, não imprimir a conta
		boolean imprimirEnviarValorContaMenorMinimo = true;

		// Para cada imóvel MICRO ligado ou cortado de água:
		for (int indiceHidrometroMicro = idImovelinicial; indiceHidrometroMicro <= idImovelFinal; indiceHidrometroMicro++) {
	
//		    double d = (double) (indiceHidrometroMicro - (imovelMacro.getId() + 1))
//			    / (helper.getIdUltimoImovelMicro() - (imovelMacro.getId() + 1));
//		    byte percentual = (byte) ((d) * 100);
//	
//		    p.setProgress(percentual);
//		    p.repaint();
	
		    Imovel imovelMicro = getDataManipulator().selectImovel("id="+indiceHidrometroMicro);
	
		    if (helper.getContaParaRateioAgua() > 0){
			    
		    	if (imovelMicro.getConsumoAgua() == null){
		    		imovelMicro.setConsumoAgua(new Consumo());
		    	}
		    	
		    	imovelMicro.setValorRateioAgua(Util.arredondar(
		    			helper.getContaParaRateioAgua() * imovelMicro.getQuantidadeEconomiasTotal() 
		    			/ helper.getQuantidadeEconomiasAguaTotal(),2));
		    	
		    	imovelMicro.setConsumoRateioAgua(helper.getConsumoParaRateioAgua() * imovelMicro.getQuantidadeEconomiasTotal()
		    		    / helper.getQuantidadeEconomiasAguaTotal());
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
		    }
		    
		    getDataManipulator().salvarImovel(imovelMicro);
	
		    // Calculamos as contas imovel a imovel
		    ControladorConta.getInstancia().calcularValoresCondominio(imovelMicro,
			    imovelMicro.getConsumoAgua(),
			    imovelMicro.getConsumoEsgoto());
	
		    // Caso o imóvel ja exista na lista não deve ser adicionado novamente
//		    if (!imoveisParaEnvio.contains(imovelMicro)) {
//		    	imoveisParaEnvio.addElement(imovelMicro);
//		    }
	
		    // Caso o valor da conta seja menor que o valor
		    // permitido para ser impresso, não imprimir a conta
		    // ou
		    // o valor do crédito for maior que o valor da conta, não imprime a conta
		    imprimirEnviarValorContaMenorMinimo = imovelMicro.isValorContaAcimaDoMinimo();
	
		    boolean error = false;
	
		    // Imprimimos, Conta
		    if (imprimirEnviarValorContaMenorMinimo
			    && imovelMicro.getIndcEmissaoConta() == Constantes.SIM) {
	
			    if (imovelMicro != null) {
					ControladorImovel.getInstancia().setImovelSelecionado(imovelMicro);
			    }
	
		    	imprimirContaCondominial();
		    }
	
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
	
		    // Perguntamos na ultima conta
		    if (indiceHidrometroMicro == idImovelFinal) {
		    	
		    	// Se for o último imóvel do condominio
		    	if (indiceHidrometroMicro == helper.getIdUltimoImovelMicro()){
		    		mensagemImpressaoCondominialOk(idImovelinicial, idImovelFinal);		    		

		    	}else{
		    		mensagemImpressaoParcialCondominioOk("As últimas "+ Constantes.QUEBRA_CONTAS_IMOVEL_CONDOMINIO + " contas foram emitidas com sucesso ?", idImovelinicial, idImovelFinal);
		    	}
		    }
		}
	}

	
	public void mensagemImpressaoParcialCondominioOk(String mensagem, final int idImovelinicial, final int idImovelFinal) {

        final AlertDialog alertMessage = new AlertDialog.Builder(this).create();
//		alertMessage.setTitle("Aviso");
		alertMessage.setMessage(mensagem);
		
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
		
		alertMessage.setButton(AlertDialog.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				alertMessage.dismiss();
				// abortar impressao
			}
		});

		alertMessage.setButton(AlertDialog.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				alertMessage.dismiss();
				imprimirContasCondominio(getImovelSelecionado().getEfetuarRateioConsumoHelper(), idImovelinicial, idImovelFinal);
			}
		});

		alertMessage.show();
    }
	

	public void mensagemImpressaoCondominialOk(final int idImovelinicial, final int idImovelFinal) {

        final AlertDialog alertMessage = new AlertDialog.Builder(this).create();
		alertMessage.setMessage("As últimas "+ (idImovelFinal-idImovelinicial+1) + " contas foram emitidas com sucesso ?");
		
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
					Imovel imovelCondominial = getDataManipulator().selectImovel("id="+listaIdsCondominio.get(i));
					imovelCondominial.setIndcImovelImpresso(Constantes.SIM);
					imovelCondominial.setImovelStatus(Constantes.IMOVEL_STATUS_CONCLUIDO);
					getDataManipulator().salvarImovel(imovelCondominial);
					setTabColor();
				}
			}
		});

		alertMessage.show();
    }

	public void imprimirExtratoConsumoMacroMedidor(Imovel imovelMacro){
		
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
    final Handler handler = new Handler() {
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
		
		alertMessage.setButton(AlertDialog.BUTTON_NEGATIVE, "Voltar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				
				getDataManipulator().salvarImovel(getImovelSelecionado());
			}
		});

		alertMessage.setButton(AlertDialog.BUTTON_POSITIVE, "Confirmar", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {

				// Nao deve imprimir fatura individual de imovel condominial
				if (!getImovelSelecionado().isImovelCondominio()){
					
					if (BusinessConta.getInstancia(getApplicationContext()).isImpressaoPermitida()){
						imprimirConta();
						
					} else{
						showMessage(BusinessConta.getInstancia().getMensagemPermiteImpressao());
						getImovelSelecionado().setImovelStatus(Constantes.IMOVEL_STATUS_CONCLUIDO);
		    			setTabColor();
						getDataManipulator().salvarImovel(getImovelSelecionado());
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
		
    	ft.commit();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		System.out.println("onItemClick");
		progress = new ProgressDialog(this);
		progress.setTitle("Imprimindo conta");
		progress.setMessage("Aguarde");
		progress.setCancelable(false);
		progress.setOnKeyListener(new DialogInterface.OnKeyListener() {

		    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		        if (keyCode == KeyEvent.KEYCODE_SEARCH && event.getRepeatCount() == 0) {
		            return true; // Pretend we processed it
		        
		        }else if (keyCode == KeyEvent.KEYCODE_HOME && event.getRepeatCount() == 0) {
		            return true; // Pretend we processed it
		        }
		        return false; // Any other keys are still processed as normal
		    }
		});
		
		/* Seleciona a segunda linha do texto
		 * Exemplo:
		 * 			MinhaImpressora
		 * 			00:06:22:64:44:BG
		 * A variável 'bluetoothAddress' teria o valor: 00:06:22:64:44:BG 
		 * 
		 */
		String bluetoothAddress = String.valueOf(((TextView) view).getText()).split("\n")[1];
		
		dialog.dismiss();
		progress.show();
		
		
		new ImpressaoThread(bluetoothAddress, getImovelSelecionado()).start();
		
	}
	
	
	class ImpressaoThread extends Thread {
		String bluetoothAddress;
		Imovel imovelToBePrinted;
		
		public ImpressaoThread(String address, Imovel imovelToBePrinted) {
			this.bluetoothAddress = address;
			this.imovelToBePrinted = imovelToBePrinted;
		}
		
		@Override
		public void run() {
			
			conexao = new BluetoothPrinterConnection(bluetoothAddress);
			
			try {
				conexao.open();

				if (conexao.isConnected()) {
					
					Looper.prepare();

					progress.show();
					String comando = new ImpressaoContaCosanpa().getComando(imovelToBePrinted);
					Log.i("COMANDO IMPRESSORA:", comando);
					conexao.write(comando.getBytes());
					conexao.close();
					
					getDataManipulator().updateConfiguracao("bluetooth_address", bluetoothAddress);
					Thread.sleep(1500);
					progress.dismiss();
					
					// already printed!
					if (!getImovelSelecionado().isImovelCondominio()){
						setupDataAfterPrinting(false);
					}
					
					Looper.loop();
					Looper.getMainLooper().quit();
					
				}
				
			} catch (ZebraPrinterConnectionException e) {

				e.printStackTrace();
				progress.dismiss();
				
				Looper.prepare();
				
				AlertDialog.Builder a = new AlertDialog.Builder(MainTab.this);
				a.setTitle("Erro ao tentar conectar com impressora");
				a.setMessage("Nenhuma impressora encontrada");
				a.setPositiveButton("Selecionar impressora", new DialogInterface.OnClickListener() {
				     
					public void onClick(DialogInterface arg0, int arg1) {
						
						/* Caso não seja possível localizar a impressora,
						 * o campo de endereco bluetooth é apagado e em seguida é chamado o método de impressão 
						 */
						getDataManipulator().updateConfiguracao("bluetooth_address", null);
						
						if (getImovelSelecionado().isImovelCondominio()){
							imprimirContaCondominial();

						}else{
							imprimirConta();
						}
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
			}
		}
	}
	
	protected void setupDataAfterPrinting(boolean isImovelCondominial){
    	getImovelSelecionado().setIndcImovelImpresso(Constantes.SIM);

    	getImovelSelecionado().setQuantidadeContasImpressas(1+(getImovelSelecionado().getQuantidadeContasImpressas()));
//    	.getImovelSelecionado().getValoresContasImpressas().addElement(String.valueOf(.getImovelSelecionado().getValorConta()));
    	
    	System.out.println("Quantidade de vezes impressas: " + getImovelSelecionado().getQuantidadeContasImpressas());
    	
    	// Guarda a data da impressao da conta de imovel nao-medido. Já que não possui data de leitura.
    	if (getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) == null && 
    		getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) == null){
	    	
    		getImovelSelecionado().setDataImpressaoNaoMedido(Util.dateToAnoMesDiaString(Util.dataAtual()));
    	}
    	
    	Log.i(" Imovel Selecionado", String.valueOf(getImovelSelecionado().getMatricula()));

		
		if (!isImovelCondominial){
			// Define imovel como concluido
			getImovelSelecionado().setImovelStatus(Constantes.IMOVEL_STATUS_CONCLUIDO);
			getDataManipulator().salvarImovel(getImovelSelecionado());
			tranmitirImovel();
			callProximoImovel();
		
		}else{
			// Apenas atualiza quantidade de vezes impressas
			getDataManipulator().salvarImovel(getImovelSelecionado());
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
		
		// Daniel - verifica se deve reter impressao das contas condominiais.
		if (getImovelSelecionado().getEfetuarRateioConsumoHelper().isCondominioRetido()){

			showMessage("Contas do condomínio retidas. Impressao não permitida!");

			// Para todos os imoveis do condominio - Macro e micros
			List<Integer> listaIdsCondominio = getDataManipulator().getListaIdsCondominio(getImovelSelecionado().getEfetuarRateioConsumoHelper().getMatriculaMacro());
	
			for(int i=0; i<listaIdsCondominio.size(); i++){
				Imovel imovelCondominial = getDataManipulator().selectImovel("id="+listaIdsCondominio.get(i));
				imovelCondominial.setIndcGeracaoConta(Constantes.NAO);
				imovelCondominial.setImovelStatus(Constantes.IMOVEL_STATUS_CONCLUIDO);
				getDataManipulator().salvarImovel(imovelCondominial);
			}
			setTabColor();

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
			    		
			    		if (BusinessConta.getInstancia(this).isImpressaoPermitida()){
			    			imprimirConta();
			    			
			    		} else{
			    			
			    			getImovelSelecionado().setImovelStatus(Constantes.IMOVEL_STATUS_CONCLUIDO);
			    			setTabColor();
			    			getDataManipulator().salvarImovel(getImovelSelecionado());
			    			tranmitirImovel();
			    		}
			    	}else {
			    		// imovel condominial avança para o próximo imovel.
				    	callProximoImovel();
			    	}
			    }
			    mensagemAnormalidadeConsumo = null;
  			
			} else if (getImovelSelecionado().getSituacaoLigAgua().equals(Constantes.CORTADO)) {

				// Nao deve imprimir fatura individual de imovel condominial
				if (!getImovelSelecionado().isImovelCondominio()) {

					if (BusinessConta.getInstancia(this).isImpressaoPermitida()) {

						imprimirConta();

					} else {

						getImovelSelecionado().setImovelStatus(Constantes.IMOVEL_STATUS_CONCLUIDO);
						ControladorRota.getInstancia().getDataManipulator().salvarImovel(getImovelSelecionado());
						setTabColor();
						tranmitirImovel();
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
					imprimirConta();
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
		
		List<Integer> listaIdsNaoCalculados = getImovelSelecionado().getEfetuarRateioConsumoHelper().getIdsAindaFaltamSerCalculador();
		
		if ( listaIdsNaoCalculados != null && listaIdsNaoCalculados.size() == 0){
			isConcluido = true;
		 }
		
		return isConcluido;
		// Obter a lista de todos os imoveis do condominio
		// verificar 1 a 1 se todos possuem consumoAgua e/ou Esgoto diferente de NULO
		// Imovel CORTADO possuem conta, talves débitos e nao devem ser verificados.
		
	}

    public DataManipulator getDataManipulator(){
    	return ControladorRota.getInstancia().getDataManipulator();
    }
}
