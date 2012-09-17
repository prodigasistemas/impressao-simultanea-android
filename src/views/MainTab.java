package views;

import java.util.Iterator;
import java.util.Set;

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
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import android.widget.Toast;
import business.BusinessConta;
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
	
	private boolean habilitaOpcaoImpressao = true;
	
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

	    if (ControladorImovel.getInstancia().getImovelSelecionado().getMedidorPorTipoMedicao(Constantes.LIGACAO_AGUA) != null)
	    	addTab("medidor", "Medidor Água", R.drawable.tab_medidor, R.layout.medidoraguatab, MedidorAguaTab.class);
	    else if (ControladorImovel.getInstancia().getImovelSelecionado().getMedidorPorTipoMedicao(Constantes.LIGACAO_POCO) != null)
	    	addTab("medidor", "Medidor Poço", R.drawable.tab_medidor, R.layout.medidoraguatab, MedidorPocoTab.class);
	    
//	    addTab("conta", "Conta", R.drawable.text, R.layout.contatab, ContaTab.class);

	}
	
	// Instancia novas tabs
	public void addTab(String tag, String titulo, int imagem, final int view, Class classe) {
		TabHost.TabSpec tabSpec;
	    Resources res = getResources();
	    
	    String title = titulo;
	    
	    if (titulo.equals("Imóvel")) {
	    	title = titulo + " - "+ControladorImovel.getInstancia().getImovelSelecionado().getId()+" de " + ListaImoveis.tamanhoListaImoveis;
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
    
	@TargetApi(5)
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Log.i("Menu item", item.getItemId()+"");
		
	    switch (item.getItemId()) {
	    case R.id.proximoImovel:

	    	ControladorImovel.getInstancia().isImovelAlterado();
	    	
	    	if(ControladorImovel.getInstancia().getImovelListPosition() == (ControladorRota.getInstancia().getDataManipulator().getNumeroImoveis())-1){
				ControladorImovel.getInstancia().setImovelSelecionadoByListPosition(0);

			}else{
		    	ControladorImovel.getInstancia().setImovelSelecionadoByListPosition(ControladorImovel.getInstancia().getImovelListPosition()+1);
			}
	    	finish();
			Intent myIntent = new Intent(getApplicationContext(), MainTab.class);
			startActivity(myIntent);
	    	return true;

	    case R.id.imovelAnterior:

	    	ControladorImovel.getInstancia().isImovelAlterado();
	    	
	    	if(ControladorImovel.getInstancia().getImovelListPosition() <= 0){
				ControladorImovel.getInstancia().setImovelSelecionadoByListPosition((int)ControladorRota.getInstancia().getDataManipulator().getNumeroImoveis()-1);
			}else{
		    	ControladorImovel.getInstancia().setImovelSelecionadoByListPosition(ControladorImovel.getInstancia().getImovelListPosition()-1);
			}
	    	finish();
	    	
			myIntent = new Intent(getApplicationContext(), MainTab.class);
			startActivity(myIntent);
	        return true;
	    
	        
	    case R.id.imprimirConta:
	    	
	    	if (habilitaOpcaoImpressao){
	    		//Daniel - Verificar se a data atual é anterior ao mes de referencia da rota em andamento.
	    		if(Util.compararData(getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), Util.dataAtual()) > 0){
	    			showMessage("Data do celular está errada. Por favor, verifique a configuração do celular e tente novamente.");

	    		// Data do celular esta correta.
	    		}else{

	    			boolean leituraInvalida = true;
	    			
	    			// Se o imovel já foi concluido e possui consumo de agua ou esgoto já calculado.
	   				if ( (getImovelSelecionado().getImovelStatus() != Constantes.IMOVEL_PENDENTE) &&
	   					 (getImovelSelecionado().getConsumoAgua() != null || getImovelSelecionado().getConsumoEsgoto() != null) ) {
	   				
	   					// Nao será recalculado o consumo
	   					showMessage("Novos valores de leitura e anormalidade serão desconsiderados.");
				    	leituraInvalida = BusinessConta.getInstancia(this).imprimirCalculo(true, true);
	    			
	    			}else{
	    				// calcula consumo
			    		leituraInvalida = BusinessConta.getInstancia(this).imprimirCalculo(true, false);
	    			}
		
//    				getImovelSelecionado().setIndcGeracao(Constantes.SIM);
//	    			
//    				// Caso o valor da conta seja menor que o valor permitido para ser impresso, não imprimir a conta.
//	    			boolean valorAcimaDoMinimo = true;
//	    			boolean valorContaMaiorPermitido = false;
//	    			boolean emiteConta = true; 
//	    			boolean reterConta = false; 
//		    		boolean permiteImpressao = true;
//
//	    			double valorConta = getImovelSelecionado().getValorConta();			
//	    			valorAcimaDoMinimo = getImovelSelecionado().isValorContaAcimaDoMinimo();
//	    			valorContaMaiorPermitido = getImovelSelecionado().isValorContaMaiorPermitido();
//
//		    		if (getImovelSelecionado().getIndcEmissaoConta() == 2 && leituraInvalida == false) {
//	
//						// A conta já não seria impressa. Mas nos casos abaixo, deve reter a conta, isto é, não deve ser faturado no Gsan.
//						if ( Integer.parseInt(getImovelSelecionado().getCodigoPerfil()) == Imovel.PERFIL_GOVERNO_METROPOLITANO){
//							
//		    				reterConta = true;
//
//						}else{
//							// Conta centralizada nao permite impressao. E não é retido.
//		    				emiteConta = false;
//						}
//		    		// Daniel - Verificando Consumo de agua e Anormalidades de Consumo e Anormalidades de Leitura para imoveis CORPORATIVOS e CONDOMINIAIS
//	    			}else if (getImovelSelecionado().getConsumoAgua() != null){
//	    				
//	    				if ( (getImovelSelecionado().getConsumoAgua().getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_ALTO_CONSUMO ||
//	    					getImovelSelecionado().getConsumoAgua().getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_ESTOURO_MEDIA ||
//	    					getImovelSelecionado().getConsumoAgua().getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_ESTOURO ||
//	    					getImovelSelecionado().getConsumoAgua().getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_HIDR_SUBST_INFO)
//	    					
//	    					||
//	    					
//	    					( (Integer.parseInt(getImovelSelecionado().getCodigoPerfil()) == Imovel.PERFIL_CORPORATIVO ||
//	    					   Integer.parseInt(getImovelSelecionado().getCodigoPerfil()) == Imovel.PERFIL_CONDOMINIAL) 
//	    					   &&
//	    					  (getImovelSelecionado().getConsumoAgua().getAnormalidadeLeituraFaturada() == ControladorConta.ANORM_HIDR_LEITURA_IMPEDIDA_CLIENTE ||
//	  	    				   getImovelSelecionado().getConsumoAgua().getAnormalidadeLeituraFaturada() == ControladorConta.ANORM_HIDR_PORTAO_FECHADO) ) ){
//				    
//		    				reterConta = true;
//	    				}
//	    			}
//		    		
//    				if (!leituraInvalida && !emiteConta){
//    					
//    	    			showMessage("Conta do imóvel nao pode ser emitida!");
//	    				permiteImpressao = false;
//    				
//    				} else if (!leituraInvalida && (valorContaMaiorPermitido || reterConta)){
//    				
//	    				getImovelSelecionado().setIndcGeracao(Constantes.NAO);
//	    				Repositorio.salvarObjeto(getImovelSelecionado());
//    	    			showMessage("Conta retida, entrega posterior!");	
//	    				permiteImpressao = false;
//
//    				} else if (!leituraInvalida && !valorAcimaDoMinimo) {
//    					showMessage("Valor da conta menor que o permitido!");
//	    				// Imovel com conta abaixo do minimo nao deve ser impresso, mas não deve fazer parte dos imoveis com conta a imprimir no Gsan. 
//	    				getImovelSelecionado().setIndcImovelImpresso(Constantes.SIM);
//	    				Repositorio.salvarObjeto(getImovelSelecionado());
//	    				permiteImpressao = false;
//	
//	    			} else if (!leituraInvalida && 
//	    					    (getImovelSelecionado().getIndicadorParalizarFaturamentoAgua() == Constantes.SIM || 
//	    					     getImovelSelecionado().getIndicadorParalizarFaturamentoEsgoto() == Constantes.SIM)){
//						
//	    				getImovelSelecionado().setIndcGeracao(Constantes.NAO);
//	    				Repositorio.salvarObjeto(getImovelSelecionado());
//	    				showMessage("Não é permitido a impressão de conta deste imóvel.");	
//	    				permiteImpressao = false;
//
//	    			} else if ( !leituraInvalida && valorConta == 0d && getImovelSelecionado().getValorResidualCredito() == 0d) {
//	    				showMessage("Conta com valor zerado e sem crédito. Não imprimir!");
//	    				permiteImpressao = false;
//
//	    			// Daniel - Imovel com Endereço alternativo
//					// caso nao haja erro de leitura e imovel contem endereço alternativo
//					// E  existe imovel sem endereço de entrega normal ainda pendente. 
//	    			} else if (!leituraInvalida && 
//				    			Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().contains(new Integer(getImovelSelecionado().getId()))){
//
//	    				showMessage("Conta do imóvel nao pode ser emitida! Entrega  posterior!");	
//	    				permiteImpressao = false;
//
////	    			} else if (!leituraInvalida && 
////			    		Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().contains(new Integer(getImovelSelecionado().getId())) &&
////			    		(Configuracao.getInstancia().getQtdImoveis() - Configuracao.getInstancia().getIdsImoveisConcluidos().size()) > Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().size()){
////				    	
////	    				System.out.println("Imovel com endereço de entrega alternativo!");
////	    				Util.mostrarErro("Imovel com endereço de entrega alternativo! A conta deste imóvel deve ser impressa somente no final da rota.");
//		    	
//	    			}else {
//	    				// Validar situacao do retorno do calculo
//	    				if (leituraInvalida == false) {
//	    					boolean pesquisarDispositivos = true;
//	    					boolean erroImpressao = false;
//		
//					    // Verificamos se algo ja foi salvo nas configurações
////Daniel
//	    					if (Fachada.getInstancia().getPrinter().equals("1")){
//						    	if (!Constantes.NULO_STRING.equals(Configuracao.getInstancia().getBluetoothAddress())) {
//			
//						    		pesquisarDispositivos = false;
//							
////						    		if (Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().contains(new Integer(getImovelSelecionado().getId()))){
////						    			Util.mensagemAviso("ATENÇÃO!", "Imóvel com endereço de entrega alternativo.");
////						    		}
//						    		erroImpressao = Util.imprimirConta(Constantes.IMPRESSAO_NORMAL);
//						    	}
//			
//						    	try {
//							
//								if (pesquisarDispositivos) {
//							    	LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);
//							    	InquiryList.getInstancia().criarTelaPesquisaDispositivos(true);
//								}
//							
//						    	} catch (Exception e) {
//									Util.mostrarErro("Bluetooth desativado!", e);
//									e.printStackTrace();
//						    	}
//	    					 }else{
////					    		if (Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().contains(new Integer(getImovelSelecionado().getId()))){
////					    			Util.mensagemAviso("ATENÇÃO!", "Imóvel com endereço de entrega alternativo.");
////					    		}
//	    						 erroImpressao = Util.imprimirConta(Constantes.IMPRESSAO_NORMAL);
//	    						 pesquisarDispositivos = false;
//	    					 }
//		
//					    	if (!pesquisarDispositivos && !erroImpressao) {
//					    		ControladorImoveis.getInstancia().proximo();
//					    		getInstancia().criarAbas();
//					    }
//					}
//			    }
//	   			if (!permiteImpressao){
//	   				// Daniel - lista de imoveis impressos
//	   				Configuracao.getInstancia().getIdsImoveisPendentes().removeElement( new Integer(getImovelSelecionado().getId()) );
//	   				if (!Configuracao.getInstancia().getIdsImoveisConcluidos().contains( new Integer(getImovelSelecionado().getId()) )) {
//	   					Configuracao.getInstancia().getIdsImoveisConcluidos().addElement( new Integer(getImovelSelecionado().getId()) );
//	   				}
//
////	   				if(	Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().contains(new Integer(getImovelSelecionado().getId())) ){
////	   					Configuracao.getInstancia().getIdsImoveisEndereçoEntrega().removeElement( new Integer(getImovelSelecionado().getId()) );
////	   				}				
//
//					ControladorImoveis.getInstancia().proximo();
//    				Abas.getInstancia().criarAbas();
//
//	   			}
	    		
	    	}
	    }else{
	    	showMessage("Não é permitido imprimir.");
	    }
	    	
	    	
	    	// imprimirConta();
	        
	    	return true;
	    	
	    case R.id.localizarPendente:
	    	
	    	localizarImovelPendente();
	    	finish();
	    	myIntent = new Intent(getApplicationContext(), MainTab.class);
			startActivity(myIntent);
	    	
	    	return true;
	        
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	public void localizarImovelPendente() {
		
		// Se nao encontrar imovel com status pendente
		if (ControladorRota.getInstancia().getDataManipulator().selectImovel("imovel_status = "+Constantes.IMOVEL_PENDENTE) == null) {
			Toast.makeText(this, "Não existem imóveis pendentes", 10).show();
			return;
		}
		
		ControladorImovel.getInstancia()
				.setImovelListPosition(new Integer(String.valueOf(ControladorImovel.getInstancia().getImovelSelecionado().getId()))-1);
		
	}
	
	public void imprimirConta() {
		
		/* Caso não haja nenhum endereco bluetooth préviamente salvo é mostrada a tela de pareamento de dispositivos.
		 * Caso contrário é realizada a conexão com a impressora e impressa a conta
		 */
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
    	dialog.setView(getCurrentFocus());
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
					
					conexao.write(ImpressaoContaCosanpa.comandoCPCL.getBytes());
					conexao.close();
					
					ControladorRota.getInstancia().getDataManipulator().updateConfiguracao("bluetooth_address", bluetoothAddress);
					Thread.sleep(1500);
					progress.dismiss();
				}
				
			} catch (ZebraPrinterConnectionException e) {
				e.printStackTrace();
				progress.dismiss();
				
				Looper.prepare();
				
				AlertDialog.Builder a = new AlertDialog.Builder(MainTab.this);
				a.setTitle("Erro ao tentar conectar com impressora");
				a.setMessage("Nenhuma impressora encontrada");
				a.setNegativeButton("Selecionar impressora", new DialogInterface.OnClickListener() {
				     
				public void onClick(DialogInterface arg0, int arg1) {
					
					/* Caso não seja possível localizar a impressora,
					 * o campo de endereco bluetooth é apagado e em seguida é chamado o método de impressão 
					 */
					
					ControladorRota.getInstancia().getDataManipulator().updateConfiguracao("bluetooth_address", null);
					
					imprimirConta();
				}});
				
				 a.show();
				
				Looper.loop();
				Looper.getMainLooper().quit();
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
