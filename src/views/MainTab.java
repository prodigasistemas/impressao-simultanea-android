package views;

import java.util.Iterator;
import java.util.Set;

import model.Consumo;
import model.Imovel;
import util.Constantes;
import util.ImpressaoContaCosanpa;
import util.Util;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
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
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
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
import business.BusinessConta;
import business.ControladorConta;
import business.ControladorImovel;
import business.ControladorRota;

import com.IS.ListaImoveis;
import com.IS.R;
import com.zebra.android.comm.BluetoothPrinterConnection;
import com.zebra.android.comm.ZebraPrinterConnection;
import com.zebra.android.comm.ZebraPrinterConnectionException;

@SuppressLint("NewApi")
public class MainTab extends FragmentActivity implements TabHost.OnTabChangeListener, OnItemClickListener {

	private static TabHost tabHost;
	private BluetoothAdapter bluetoothAdapter;
	private ListView listaDispositivos;
	AlertDialog dialog;
	private ProgressDialog progress;
	private ZebraPrinterConnection conexao;
	private String dialogMessage = null;
	
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
	    	title = titulo + "  "+ControladorImovel.getInstancia().getImovelSelecionado().getId()+" de " + ListaImoveis.tamanhoListaImoveis;
	    }
		
	    tabSpec = tabHost.newTabSpec(tag).setIndicator(title, res.getDrawable(imagem)).setContent(new TabContentFactory() {

            public View createTabContent(String tag) {
            	LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            	View layout = inflater.inflate(view, (ViewGroup) findViewById(R.layout.maintab));
                return layout;
            }
        });
	    
	    tabHost.addTab(tabSpec);
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

	    if (!ControladorImovel.getInstancia().isPrintingAllowed()){
		    menu.findItem(R.id.imprimirConta).setEnabled(false);
	    }

	    if (!ControladorImovel.getInstancia().isUltimoImovelCondominio()) {
		    menu.findItem(R.id.imprimirContasCondominio).setVisible(false);
	    }
    	
	    return super.onPrepareOptionsMenu(menu);
    }

    public void callProximoImovel(){

    	ControladorImovel.getInstancia().isImovelAlterado();
    	
    	if(ControladorImovel.getInstancia().getImovelListPosition() == (ControladorRota.getInstancia().getDataManipulator().getNumeroImoveis())-1){
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
			ControladorImovel.getInstancia().setImovelSelecionadoByListPosition((int)ControladorRota.getInstancia().getDataManipulator().getNumeroImoveis()-1);

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
	    	
	    	//Daniel - Verificar se a data atual é anterior ao mes de referencia da rota em andamento.
	    	if(Util.compararData(getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), Util.dataAtual()) > 0){
	    		showMessage("Data do celular está errada. Por favor, verifique a configuração do celular e tente novamente.");
//	    		Toast.makeText(this, "Data do celular está errada. Por favor, verifique a configuração do celular e tente novamente.", Toast.LENGTH_LONG).show();
	    		
	    		// Data do celular esta correta.
	    	}else{
	    		
	    		boolean leituraInvalida = true;
	    		
	    		// Se o imovel já foi concluido e possui consumo de agua ou esgoto já calculado.
	    		if ( (getImovelSelecionado().getImovelStatus() != Constantes.IMOVEL_STATUS_PENDENTE) &&
	    				(getImovelSelecionado().getConsumoAgua() != null || getImovelSelecionado().getConsumoEsgoto() != null) ) {
	    			
	    			// Nao será recalculado o consumo
	    			Toast.makeText(this, "Novos valores de leitura e anormalidade serão desconsiderados.", Toast.LENGTH_LONG).show();
	    			leituraInvalida = BusinessConta.getInstancia(this).imprimirCalculo(true, true);
	    			
	    		}else{
	    			// calcula consumo
	    			leituraInvalida = BusinessConta.getInstancia(this).imprimirCalculo(true, false);
	    		}
	    		
	    		getImovelSelecionado().setIndcGeracaoConta(Constantes.SIM);
	    		
	    		// Caso o valor da conta seja menor que o valor permitido para ser impresso, não imprimir a conta.
	    		boolean valorAcimaDoMinimo = true;
	    		boolean valorContaMaiorPermitido = false;
	    		boolean emiteConta = true; 
	    		boolean reterConta = false; 
	    		boolean permiteImpressao = true;
	    		
	    		double valorConta = getImovelSelecionado().getValorConta();			
	    		valorAcimaDoMinimo = getImovelSelecionado().isValorContaAcimaDoMinimo();
	    		valorContaMaiorPermitido = getImovelSelecionado().isValorContaMaiorPermitido();
	    		
	    		if (getImovelSelecionado().getIndcEmissaoConta() == 2 && leituraInvalida == false) {
	    			
	    			// A conta já não seria impressa. Mas nos casos abaixo, deve reter a conta, isto é, não deve ser faturado no Gsan.
	    			if ( Integer.parseInt(getImovelSelecionado().getCodigoPerfil()) == Imovel.PERFIL_GOVERNO_METROPOLITANO){
	    				
	    				reterConta = true;
	    				
	    			}else{
	    				// Conta centralizada nao permite impressao. E não é retido.
	    				emiteConta = false;
	    			}
	    			
	    			// Verificando Consumo de agua e Anormalidades de Consumo e Anormalidades de Leitura para imoveis CORPORATIVOS e CONDOMINIAIS
	    		}else if (getImovelSelecionado().getConsumoAgua() != null){
	    			
	    			if ( (getImovelSelecionado().getConsumoAgua().getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_ALTO_CONSUMO ||
	    					getImovelSelecionado().getConsumoAgua().getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_ESTOURO_MEDIA ||
	    					getImovelSelecionado().getConsumoAgua().getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_ESTOURO ||
	    					getImovelSelecionado().getConsumoAgua().getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_HIDR_SUBST_INFO)
	    					
	    					||
	    					
	    					( (Integer.parseInt(getImovelSelecionado().getCodigoPerfil()) == Imovel.PERFIL_CORPORATIVO ||
	    					Integer.parseInt(getImovelSelecionado().getCodigoPerfil()) == Imovel.PERFIL_CONDOMINIAL) 
	    					&&
	    					(getImovelSelecionado().getConsumoAgua().getAnormalidadeLeituraFaturada() == ControladorConta.ANORM_HIDR_LEITURA_IMPEDIDA_CLIENTE ||
	    					getImovelSelecionado().getConsumoAgua().getAnormalidadeLeituraFaturada() == ControladorConta.ANORM_HIDR_PORTAO_FECHADO) ) ){
	    				
	    				reterConta = true;
	    			}
	    		}
	    		
	    		if (!leituraInvalida && !emiteConta){
	    			
		    		showMessage("Conta do imóvel nao pode ser emitida!");
//	    			Toast.makeText(this, "Conta do imóvel nao pode ser emitida!", Toast.LENGTH_LONG).show();
	    			permiteImpressao = false;
	    			
	    		} else if (!leituraInvalida && (valorContaMaiorPermitido || reterConta)){
	    			
	    			getImovelSelecionado().setIndcGeracaoConta(Constantes.NAO);
		    		showMessage("Conta retida, entrega posterior!");
//	    			Toast.makeText(this, "Conta retida, entrega posterior!", Toast.LENGTH_LONG).show();
	    			permiteImpressao = false;
	    			
	    		} else if (!leituraInvalida && !valorAcimaDoMinimo) {
	    			
		    		showMessage("Valor da conta menor que o permitido!");
//	    			Toast.makeText(this, "Valor da conta menor que o permitido!", Toast.LENGTH_LONG).show();
	    			
	    			// Imovel com conta abaixo do minimo nao deve ser impresso, mas não deve fazer parte dos imoveis com conta a imprimir no Gsan. 
	    			getImovelSelecionado().setIndcImovelImpresso(Constantes.SIM);
	    			permiteImpressao = false;
	    			
	    		} else if (!leituraInvalida && 
	    				(getImovelSelecionado().getIndicadorParalizarFaturamentoAgua() == Constantes.SIM || 
	    				getImovelSelecionado().getIndicadorParalizarFaturamentoEsgoto() == Constantes.SIM)){
	    			
	    			getImovelSelecionado().setIndcGeracaoConta(Constantes.NAO);
		    		showMessage("Não é permitido a impressão de conta deste imóvel.");
//	    			Toast.makeText(this, "Não é permitido a impressão de conta deste imóvel.", Toast.LENGTH_LONG).show();
	    			permiteImpressao = false;
	    			
	    		} else if ( !leituraInvalida && valorConta == 0d && getImovelSelecionado().getValorResidualCredito() == 0d) {
		    		showMessage("Conta com valor zerado e sem crédito. Não imprimir!");
//	    			Toast.makeText(this, "Conta com valor zerado e sem crédito. Não imprimir!", Toast.LENGTH_LONG).show();
	    			permiteImpressao = false;
	    			
	    			// Daniel - Imovel com Endereço alternativo
	    			// caso nao haja erro de leitura e imovel contém endereço alternativo
	    		} else if ( !leituraInvalida && getImovelSelecionado().getEnderecoEntrega().length() > 0 ){
	    			
		    		showMessage("Conta do imóvel não pode ser emitida! Entrega  posterior!");
//	    			Toast.makeText(this, "Conta do imóvel não pode ser emitida! Entrega  posterior!", Toast.LENGTH_LONG).show();
	    			permiteImpressao = false;
	    			
	    		}else {
	    			// Validar situacao do retorno do calculo
	    			if (leituraInvalida == false) {
	    				boolean erroImpressao = false;
	    				imprimirConta();
	    				ControladorRota.getInstancia().getDataManipulator().salvarImovel(getImovelSelecionado());
	    				
	    				if (!erroImpressao) {
	    					callProximoImovel();
	    				}
	    			}
	    		}
	    		
	    		if (!permiteImpressao){
	    			// Daniel - lista de imoveis impressos
	    			getImovelSelecionado().setImovelStatus(Constantes.IMOVEL_STATUS_CONCLUIDO);
	    			ControladorRota.getInstancia().getDataManipulator().salvarImovel(getImovelSelecionado());
	    			callProximoImovel();
	    		}
	    		
	    	}
	    	return true;
	    	
	    case R.id.localizarPendente:
	    	
	    	localizarImovelPendente();
	    	finish();
	    	Intent myIntent = new Intent(getApplicationContext(), MainTab.class);
			startActivity(myIntent);
	    	
	    	return true;
	        
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	public void localizarImovelPendente() {
		
		Imovel imovelPendente = ControladorRota.getInstancia().getDataManipulator().selectImovel("imovel_status = "+Constantes.IMOVEL_STATUS_PENDENTE);
		
		// Se nao encontrar imovel com status pendente
		if (imovelPendente == null) {
			Toast.makeText(this, "Não existem imóveis pendentes", Toast.LENGTH_LONG).show();
			return;
		}
		
		ControladorImovel.getInstancia().setImovelSelecionadoByListPosition(Long.valueOf(imovelPendente.getId()).intValue()-1);
		
	}
	
	public void imprimirConta() {
		
		/* Caso não haja nenhum endereco bluetooth préviamente salvo é mostrada a tela de pareamento de dispositivos.
		 * Caso contrário é realizada a conexão com a impressora e impressa a conta
		 */

		if (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")){
			
			ImpressaoContaCosanpa.getInstancia().getComando().getBytes();
//	    	Log.i("Comando", ImpressaoContaCosanpa.getInstancia().getComando());
			setupDataAfterPrinting();
			
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
	    			
	    		new ImpressaoThread(bluetoothAddress).start();
	    			
	    		progress.show();
	    		
	    	}
		}
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

	public void showMessage(View view, String message){

		dialog = new AlertDialog.Builder(this).create();
    	dialog.setTitle(message);
    	dialog.setView(view);
    	dialog.show();
	}
	
	public void showMessage(String message){

		dialog = new AlertDialog.Builder(this).create();
    	dialog.setTitle(message);
//    	dialog.setView(getCurrentFocus());
    	dialog.show();
	}
	
	public Imovel getImovelSelecionado() {
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
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
			tabHost.setBackgroundDrawable(getResources().getDrawable(R.drawable.landscape_background));
		else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
			tabHost.setBackgroundDrawable(getResources().getDrawable(R.drawable.fundocadastro));
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		System.out.println("onItemClick");
		progress = new ProgressDialog(this);
		progress.setTitle("Imprimindo conta");
		progress.setMessage("Aguarde");
		
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
		
		
		new ImpressaoThread(bluetoothAddress).start();
		
	}
	
	
	class ImpressaoThread extends Thread {
		String bluetoothAddress;
		
		public ImpressaoThread(String address) {
			this.bluetoothAddress = address;
		}
		
		@Override
		public void run() {
			
			conexao = new BluetoothPrinterConnection(bluetoothAddress);
			
			try {
				conexao.open();

				if (conexao.isConnected()) {

					progress.show();
					conexao.write(ImpressaoContaCosanpa.getInstancia().getComando().getBytes());
					conexao.close();
					
					ControladorRota.getInstancia().getDataManipulator().updateConfiguracao("bluetooth_address", bluetoothAddress);
					Thread.sleep(1500);
					progress.dismiss();
					
					// already printed!
					setupDataAfterPrinting();
					
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
						ControladorRota.getInstancia().getDataManipulator().updateConfiguracao("bluetooth_address", null);
						
						imprimirConta();
//				    	Log.i("Comando", ImpressaoContaCosanpa.getInstancia().getComando());

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
	
	@Override
	protected Dialog onCreateDialog(final int id) {

    	LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.confirmationdialog, (ViewGroup) findViewById(R.id.root));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if ( id == Constantes.DIALOG_ID_ERRO ||
	    			id == Constantes.DIALOG_ID_SUCESSO ||
	    			id == Constantes.DIALOG_ID_AVISO){		
			  
	        View messageLayout = inflater.inflate(R.layout.custon_dialog, (ViewGroup) findViewById(R.id.layout_root));
	        ((TextView)messageLayout.findViewById(R.id.messageDialog)).setText(dialogMessage);
	        
	        if (id == Constantes.DIALOG_ID_SUCESSO){
		        ((ImageView)messageLayout.findViewById(R.id.imageDialog)).setImageResource(R.drawable.save);

	        }else if (id == Constantes.DIALOG_ID_ERRO){
		        ((ImageView)messageLayout.findViewById(R.id.imageDialog)).setImageResource(R.drawable.erro);

	        }else if (id == Constantes.DIALOG_ID_AVISO){
		        ((ImageView)messageLayout.findViewById(R.id.imageDialog)).setImageResource(R.drawable.aviso);
	        }
	        
	        builder.setView(messageLayout);
	        builder.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	        	
	        	public void onClick(DialogInterface dialog, int whichButton) {
	        		removeDialog(id);
	        	}
	        });

	        AlertDialog messageDialog = builder.create();
	        return messageDialog;

	    }
	    return null;
	}	

	protected void setupDataAfterPrinting(){
    	getImovelSelecionado().setIndcImovelImpresso(Constantes.SIM);

    	getImovelSelecionado().setQuantidadeContasImpressas(1+(getImovelSelecionado().getQuantidadeContasImpressas()));
//    	.getImovelSelecionado().getValoresContasImpressas().addElement(String.valueOf(.getImovelSelecionado().getValorConta()));
    	
    	System.out.println("Quantidade de vezes impressas: " + getImovelSelecionado().getQuantidadeContasImpressas());
    	
    	// Daniel - guarda a data da impressao da conta de imovel nao-medido. Já que não possui data de leitura.
    	if (getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) == null && 
    		getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) == null){
	    	
    		getImovelSelecionado().setDataImpressaoNaoMedido(Util.dateToAnoMesDiaString(Util.dataAtual()));
    	}
    	
		// Imovel da lista de imoveis pendentes
		getImovelSelecionado().setImovelStatus(Constantes.IMOVEL_STATUS_CONCLUIDO);

		ControladorRota.getInstancia().getDataManipulator().salvarImovel(getImovelSelecionado());
//		Repositorio.salvarObjeto(getImovelSelecionado());

		// caso seja imovel impresso pela opcao de imprimir todos os nao-hidrometrados
		// E imovel possui endereço de entrega alternativo
		// remove este imovel da lista de imoveis com endereço de entrega. 
//		if (tipoImpressao == Constantes.IMPRIME_IMOVEIS_FIXOS){			
//			// Daniel - Remove id da lista dos imoveis fixos (nao-hidrometrados).    			
//			Configuracao.getInstancia().getIdsNaoHidrometrados().removeElementAt(0);
//			
////			if(	Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().contains(new Integer(getImovelSelecionado().getId())) ){
////				Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().removeElement( new Integer(getImovelSelecionado().getId()) );
////			}
//		
//		}else if (tipoImpressao == Constantes.IMPRIME_TODOS_IMOVEIS_CONSUMO_MEDIO){
//			
////			if(	Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().contains(new Integer(getImovelSelecionado().getId())) ){
////				Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().removeElement( new Integer(getImovelSelecionado().getId()) );
////			}				
//		}
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
		    }else{
		    	// Daniel - eliminar resquicios da leitura anterior
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
		    }else{
		    	// Daniel - eliminar resquicios da leitura anterior
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
	    	
			if (getImovelSelecionado().getIndcImovelCalculado() == Constantes.NAO){

				// Daniel -  Deve salvar microcondominiais!
				if (getImovelSelecionado().isImovelMicroCondominio()){
					BusinessConta.getInstancia(this).imprimirCalculo(true, false);
					result = true;
					
				}else{
					BusinessConta.getInstancia(this).imprimirCalculo(false, false);
					result = true;
				}
			}
		}
		return result;
	}

}
