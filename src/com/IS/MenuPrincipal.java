package com.IS;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import util.Constantes;
import util.ImpressaoContaCosanpa;
import util.Util;
import views.MainTab;
import views.MedidorAguaTab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import background.EnviarImoveisConcluidosThread;
import background.FinalizarRotaThread;
import background.GerarArquivoCompletoThread;
import background.ImpressaoThread;
import business.BusinessConta;
import business.ControladorAcessoOnline;
import business.ControladorImovel;
import business.ControladorRota;

import com.IS.R.color;
 
public class MenuPrincipal extends Activity {
	
	static final int MENU_LISTA_IMOVEIS = 0;
	static final int MENU_INFO = 1;
	static final int MENU_CONSULTA = 2;
	static final int MENU_ARQUIVO_COMPLETO = 3;
	static final int MENU_IMOVEIS_CONCLUIDOS = 4;
	static final int MENU_FINALIZAR = 5;
	static final int MENU_RELATORIO = 6;
	static final int MENU_NOVO_ROTEIRO = 7;
	static final int MENU_IMPRESSAO_MASSA = 8;

	private ProgressDialog progDialog;

	private GerarArquivoCompletoThread progThread;
	private EnviarImoveisConcluidosThread enviarImoveisThread;
	private FinalizarRotaThread finalizarRotaThread;
	private List<Integer> listaIdsImoveisFixos;

	private String dialogMessage = null;
	public LocationManager mLocManager;
	private static int increment= 0;
	private BluetoothAdapter bluetoothAdapter;
	private ListView listaDispositivos;
	private AlertDialog dialog;
	private int contadorImpressao = 0;
	
	public static String mensagemRetorno;
	
    //---the images to display---
    Integer[] imageIDs = {
            R.drawable.menu_lista_imoveis,
            R.drawable.menu_info,
            R.drawable.menu_consulta,
            R.drawable.menu_arquivo_completo,
            R.drawable.menu_cadastros_concluidos,
            R.drawable.menu_finalizar,
            R.drawable.menu_relatorio,
            R.drawable.menu_novo_roteiro,
            R.drawable.menu_impressao_massa
    };

    //---the texts to display---
    Integer[] TextIDs = {
            R.string.menu_cadastros,
            R.string.menu_info,
            R.string.menu_consulta,
            R.string.menu_arquivo_completo,
            R.string.menu_cadastros_concluidos,
            R.string.menu_finalizar,
            R.string.menu_relatorio,
            R.string.menu_novo_roteiro,
            R.string.menu_impresssao_fixos
    };

    @Override    
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        
        instanciate();
	}
    
	protected void onNewIntent(Intent intent) {
		  super.onNewIntent(intent);
		  setIntent(intent);//must store the new intent unless getIntent() will return the old one.
		  instanciate();
	}

	public void instanciate(){

		GridView gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new ImageAdapter(this));
   	 
		gridView.setOnItemClickListener(new OnItemClickListener(){
        	public void onItemClick(AdapterView parent, View v, int position, long id){        
        		String text = parent.getItemAtPosition(position).toString();
				
            	if (position == MENU_LISTA_IMOVEIS){
            		
            		// Verifica se GPS esta ligado
            		/* Use the LocationManager class to obtain GPS locations */
                    mLocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                    
                    boolean enabled = mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                    // Check if enabled and if not send user to the GPS settings
                    if (!enabled){
            	        dialogMessage = "GPS está desligado. Por favor, ligue-o para continuar a leitura.";
            	    	showDialog(Constantes.DIALOG_ID_ERRO_GPS_DESLIGADO);
                    }
            		
                    // Permite abrir a lista de cadastros apenas se GPS estiver ligado.
                    if (enabled){
						Intent myIntent = new Intent(getApplicationContext(),ListaImoveis.class);
		        		startActivity(myIntent);
                    }
				
            	}else if (position == MENU_INFO){
					Intent myIntent = new Intent(getApplicationContext(),TelaInformacoes.class);
	        		startActivity(myIntent);

            	}else if (position == MENU_CONSULTA){
					Intent myIntent = new Intent(getApplicationContext(),Consulta.class);
	        		startActivity(myIntent);            		
					
            	}else if (position == MENU_ARQUIVO_COMPLETO){
                    boolean statusOk = true;
        	    	
                    // 	Verifica se todos os imoveis já foram visitados.
        	    	ArrayList<String> listStatusImoveis = (ArrayList)ControladorRota.getInstancia().getDataManipulator().selectStatusImoveis(null);
        	    	int imoveisPendentes = 0;
        	    	
        	    	for (int i=0; i < listStatusImoveis.size(); i++){
        	    		if ( Integer.parseInt(listStatusImoveis.get(i)) == Constantes.IMOVEL_STATUS_PENDENTE ){
        	    			statusOk = false;
        	    			imoveisPendentes++;
        				}
        	    	}
        	    	
        	    	if (statusOk){
                		showDialog(Constantes.DIALOG_ID_GERAR_ARQUIVO_COMPLETO + increment);
        	    	
        	    	}else{
            		
        	    		dialogMessage = "Não é permitido gerar arquivo de retorno Completo. Ainda há " + imoveisPendentes + " imóveis não visitados.";
            	    	showDialog(Constantes.DIALOG_ID_ROTA_NAO_FINALIZADA);
            	    	
        	    	}
            		
            	}else if (position == MENU_IMOVEIS_CONCLUIDOS){
					if (ControladorRota.getInstancia().getDataManipulator().selectIdsImoveisConcluidosENaoEnviados().size() > 0) {
						showDialog(Constantes.DIALOG_ID_ENVIAR_IMOVEIS_NAO_TRANSMITIDOS+increment);
					} else {
						dialogMessage = "Não existem imóveis a serem transmitidos";
	            		showDialog(Constantes.DIALOG_ID_ERRO);
					}
            	
            	}else if (position == MENU_FINALIZAR){
            		boolean statusOk = true;
        	    	
                    // 	Verifica se todos os imoveis já foram visitados.
        	    	ArrayList<String> listStatusImoveis = (ArrayList)ControladorRota.getInstancia().getDataManipulator().selectStatusImoveis(null);
        	    	int imoveisPendentes = 0;
        	    	
        	    	for (int i=0; i < listStatusImoveis.size(); i++){
        	    		if ( Integer.parseInt(listStatusImoveis.get(i)) == Constantes.IMOVEL_STATUS_PENDENTE ){
        	    			statusOk = false;
        	    			imoveisPendentes++;
        				}
        	    	}
        	    	
        	    	if (statusOk) {
        	    		if (envio()) {
        	    			finalizarRotaThread = new FinalizarRotaThread(finalizarRotaHandler, MenuPrincipal.this, increment);
        	    			finalizarRotaThread.start();
        	    		} else {
        	    			showDialog(Constantes.DIALOG_ID_FINALIZA_ROTA+increment);
        	    		}
        	    	} else {
            		
        	    		dialogMessage = "Não é permitido Finalizar Rota. Ainda há " + imoveisPendentes + " imóveis não visitados.";
            	    	showDialog(Constantes.DIALOG_ID_ROTA_NAO_FINALIZADA);
        	    	}

            	}else if (position == MENU_RELATORIO){
					Intent myIntent = new Intent(getApplicationContext(),TelaRelatorio.class);
	        		startActivity(myIntent);
            		
            	}else if (position == MENU_NOVO_ROTEIRO){
        	    	showDialog(Constantes.DIALOG_ID_CLEAN_DB);
        	    	
				} else if (position == MENU_IMPRESSAO_MASSA) {
					
		    		Util.salvarLog("<----- Impressao imoveis fixos em massa----->");
					
					listaIdsImoveisFixos = ControladorRota.getInstancia().getDataManipulator().getListaIdsImoveisFixos(false);

					if (listaIdsImoveisFixos == null || listaIdsImoveisFixos.isEmpty()){
						Toast.makeText(MenuPrincipal.this, "Todos os imóveis fixos já foram impressos", Toast.LENGTH_LONG).show();
			    		Util.salvarLog("Todos os imoveis fixos ja foram impressos");

					}else{
						progDialog = new ProgressDialog(MenuPrincipal.this);
						progDialog.setTitle("Imprimindo contas imóveis fixos");
						progDialog.setMessage("Imóveis impressos");
						progDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
						progDialog.setCancelable(false);
						progDialog.setMax(listaIdsImoveisFixos.size());
						progDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
							
							public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
								if ( (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_BACK) && 
										(event.getRepeatCount() == 0)) {
									
									return true; // Pretend we processed it
								}
								return false; // Any other keys are still processed as normal
							}
						});						
						progDialog.show();
						
						if (Util.isEmulator()){

							contadorImpressao = 0;

							for (int imovelId : listaIdsImoveisFixos) {
								ControladorImovel.getInstancia().setImovelSelecionado(ControladorRota.getInstancia().getDataManipulator().selectImovel("id = " + imovelId, true));
								BusinessConta.getInstancia(MenuPrincipal.this).imprimirCalculo(false);
								
								int impressaoTipo = Constantes.IMPRESSAO_FATURA;
								
//								if (getImovelSelecionado().getContas() != null && getImovelSelecionado().getContas().size() > 0 ){
//									impressaoTipo = Constantes.IMPRESSAO_FATURA_E_NOTIFICACAO;
//								}
								
								Log.i("Comando Fatura", new ImpressaoContaCosanpa().getComandoImpressaoFatura(ControladorImovel.getInstancia().getImovelSelecionado(), Constantes.IMPRESSAO_FATURA));
								ControladorImovel.getInstancia().setupDataAfterPrinting(impressaoTipo);
								contadorImpressao++;
								
								if (listaIdsImoveisFixos.size() > contadorImpressao){
									progDialog.setProgress(contadorImpressao);
									
								} else{
									progDialog.dismiss();
									dialogMessage = "Impressão dos imóveis não-hidrometrados finalizada.";
									showDialog(Constantes.DIALOG_ID_SUCESSO);
									increment += 17;
								}
							}
							
						}else{
							if (ControladorRota.getInstancia().getBluetoothAddress() == null) {
								progDialog.dismiss();
					    		Util.salvarLog("Impressao cancelada. Por favor selecione a impressora.");
					    		dialogMessage = "Impressão cancelada. Por favor selecione a impressora.";
				    	    	showDialog(Constantes.DIALOG_ID_ENDERECO_BLUETOOTH_FALTANDO);
							    increment += 17;
								
							}else{
								 contadorImpressao = 0;
	                                ControladorImovel.getInstancia().setImovelSelecionado(ControladorRota.getInstancia().getDataManipulator().selectImovel("id = " + listaIdsImoveisFixos.get(0), true));
	                                if(Util.compararData(ControladorImovel.getInstancia().getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), MedidorAguaTab.getCurrentDateByGPS()) > 0) {
	                                    Bundle b = new Bundle();
	                                    Message msg = impressaoFixosHandler.obtainMessage();
	                                    b.putBoolean("dataComErro", true);
	                                    msg.setData(b);
	                                    impressaoFixosHandler.sendMessage(msg);
	                                } else {
	                                    BusinessConta.getInstancia(MenuPrincipal.this).imprimirCalculo(false);
	                                    new ImpressaoThread(ControladorRota.getInstancia().getBluetoothAddress(),
	                                            impressaoFixosHandler,
	                                            ControladorImovel.getInstancia().getImpressaoTipo(getApplicationContext()),
	                                            increment,
	                                            MenuPrincipal.this).start();
	                                }
							}
						}
					}
				}
            }
        });        
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
	    	listaDispositivos.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> parent, View view,	int position, long id) {
					String bluetoothAddress = String.valueOf(((TextView) view).getText()).split("\n")[1];
					ControladorRota.getInstancia().getDataManipulator().updateConfiguracao("bluetooth_address", bluetoothAddress);
					
					Toast.makeText(MenuPrincipal.this, "Impressora registrada", Toast.LENGTH_LONG).show();
					
					dialog.dismiss();
				}
			});
	    	
	    	while (iterator.hasNext()) {
	    		BluetoothDevice device = iterator.next();
	    		// Seleciona apenas as impressoras pareadas
	    		if (device.getBluetoothClass().getMajorDeviceClass() == 1536) {
	    			arrayAdapter.add(device.getName() + "\n" + device.getAddress());
	    		}
	    	}
	    	
	    	dialog = new AlertDialog.Builder(this).create();
	    	dialog.setTitle("Impressoras pareadas");
	    	dialog.setView(listaDispositivos);
	    	dialog.show();
		}
	}
	
    // Handler on the main (UI) thread that will receive messages from the second thread and update the progress.
    final Handler arquivoCompletoHandler = new Handler() {
        public void handleMessage(Message msg) {
            
        	// Get the current value of the variable total from the message data and update the progress bar.
        	int totalArquivoCompleto = msg.getData().getInt("arquivoCompleto" + String.valueOf(increment));
            progDialog.setProgress(totalArquivoCompleto);
            
            if (msg.getData().getBoolean("geracaoDoArquivoCompletoConcluido")){
                
            	dismissDialog(Constantes.DIALOG_ID_GERAR_ARQUIVO_COMPLETO + increment);
            	
	    		dialogMessage = "Arquivo de retorno COMPLETO gerado com sucesso. Enviar o arquivo ao supervisor para carregar via cabo USB.";
    	    	showDialog(Constantes.DIALOG_ID_SUCESSO);
			    increment += 17;
            }
         }
    };

    // Handler on the main (UI) thread that will receive messages from the second thread and update the progress.
    final Handler impressaoFixosHandler = new Handler() {
        @SuppressWarnings({ "deprecation" })
        public void handleMessage(Message msg) {
            
        	// Get the current value of the variable total from the message data and update the progress bar.
            if (msg.getData().getBoolean("impressaoConcluida")){
            	contadorImpressao++;
            	
				if (listaIdsImoveisFixos.size() > contadorImpressao){

					progDialog.setProgress(contadorImpressao);
					ControladorImovel.getInstancia().setImovelSelecionado(ControladorRota.getInstancia().getDataManipulator().selectImovel("id = " + listaIdsImoveisFixos.get(contadorImpressao), true));
					BusinessConta.getInstancia(MenuPrincipal.this).imprimirCalculo(false);
					new ImpressaoThread(ControladorRota.getInstancia().getBluetoothAddress(),
							impressaoFixosHandler,
							ControladorImovel.getInstancia().getImpressaoTipo(getApplicationContext()),
							increment,
							MenuPrincipal.this).start();
            		
            	}else{
            		progDialog.dismiss();
    	    		dialogMessage = "Impressão dos imóveis não-hidrometrados finalizada.";
        	    	showDialog(Constantes.DIALOG_ID_SUCESSO);
            	}
			    increment += 17;

            }else if (msg.getData().getBoolean("impressaoErro")){
				AlertDialog.Builder a = new AlertDialog.Builder(MenuPrincipal.this);
				a.setTitle("Erro ao imprimir fatura");
				a.setMessage("Tentar imprimir novamente?");

				a.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {

						new ImpressaoThread(ControladorRota.getInstancia().getBluetoothAddress(),
								impressaoFixosHandler,
								ControladorImovel.getInstancia().getImpressaoTipo(getApplicationContext()),
								increment,
								MenuPrincipal.this).start();
								increment += 17;
					}
				});
				
				a.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						ControladorRota.getInstancia().getDataManipulator().updateConfiguracao("bluetooth_address", null);
	            		progDialog.dismiss();
			    		dialogMessage = "Não foi possível imprimir.";
		    	    	showDialog(Constantes.DIALOG_ID_ERRO);
					    increment += 17;
					}
				});
				a.show();

			}else if(msg.getData().getBoolean("dataComErro")) {
               progDialog.dismiss();
               Util.salvarLog("Data do celular está errada. Por favor, verifique a configuração do celular e tente novamente.");
               dialogMessage = "Data do celular está errada. Por favor, verifique a configuração do celular e tente novamente.";
               showDialog(Constantes.DIALOG_ID_ERRO);
               increment += 17;
           }
         }
    };
    
    final Handler imoveisNaoTransmitidosHandler = new Handler() {
        @SuppressWarnings({ "deprecation"})
		public void handleMessage(Message msg) {
            
        	// Get the current value of the variable total from the message data and update the progress bar.
        	int totalArquivoCompleto = msg.getData().getInt("imoveisNaoTransmitidos" + String.valueOf(increment));
            progDialog.setProgress(totalArquivoCompleto);
            
            if (msg.getData().getBoolean("geracaoDosImoveisNaoTransmitidosConcluido")){
            	dismissDialog(Constantes.DIALOG_ID_ENVIAR_IMOVEIS_NAO_TRANSMITIDOS+increment);
            	showDialog(Constantes.DIALOG_ID_SPINNER+increment);
            }
            
            if (msg.getData().getBoolean("recebeuResposta")) {
            	dismissDialog(Constantes.DIALOG_ID_SPINNER+increment);
            	increment += 17;
            	dialogMessage = mensagemRetorno;
            	
            	if (!ControladorAcessoOnline.getInstancia().isRequestOK()) {
            		showDialog(Constantes.DIALOG_ID_ERRO);
	    		} else {
	    			showDialog(Constantes.DIALOG_ID_SUCESSO);
	    		}
            }
         }
    };
    
    final Handler finalizarRotaHandler = new Handler() {
        @SuppressWarnings({ "deprecation"})
		public void handleMessage(Message msg) {
            
            if (msg.getData().getBoolean("arquivoJaExistente")) {
            	showDialog(Constantes.DIALOG_ID_SPINNER+increment);
            } else {
            	
            	int totalArquivoCompleto = msg.getData().getInt("finalizarRota" + String.valueOf(increment));
                progDialog.setProgress(totalArquivoCompleto);
            
	            if (msg.getData().getBoolean("geracaoDosImoveisParaFinalizaRotaConcluido")){
	            	dismissDialog(Constantes.DIALOG_ID_FINALIZA_ROTA+increment);
	            	showDialog(Constantes.DIALOG_ID_SPINNER+increment);
	            }
            }
            
            if (msg.getData().getBoolean("recebeuResposta")) {
            	
            	if (!msg.getData().getBoolean("arquivoJaExistente"))
            		dismissDialog(Constantes.DIALOG_ID_SPINNER+increment);
            	
            	increment += 17;
            	dialogMessage = mensagemRetorno;
            	
            	if (!ControladorAcessoOnline.getInstancia().isRequestOK()) {
            		showDialog(Constantes.DIALOG_ID_ERRO);
	    		} else {
	    			showDialog(Constantes.DIALOG_ID_CONFIRMAR_FINALIZACAO_ROTA+increment);
	    		}
            }
         }
    };


	@Override
	protected Dialog onCreateDialog(final int id) {
		LayoutInflater inflater;
		AlertDialog.Builder builder;
		
	    if (id == Constantes.DIALOG_ID_CLEAN_DB){
	        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        final View layoutConfirmationDialog = inflater.inflate(R.layout.confirmationdialog, (ViewGroup) findViewById(R.id.root));
	        
	        builder = new AlertDialog.Builder(this);
	        builder.setTitle("Atenção!");
	        builder.setView(layoutConfirmationDialog);
	        
	        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
	        	
	        	public void onClick(DialogInterface dialog, int whichButton) {
	        		removeDialog(id);
	        	}
	        });
	        	 
	        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	        	public void onClick(DialogInterface dialog, int which) {
	        		EditText senha = (EditText) layoutConfirmationDialog.findViewById(R.id.txtSenha);

	        		if (senha.getText().toString().equals("apagar")) {
		        		removeDialog(id);
		        		ControladorRota.getInstancia().finalizeDataManipulator();
		        		ControladorRota.getInstancia().deleteDatabase();
		        		ControladorRota.getInstancia().setPermissionGranted(false);
		        		ControladorRota.getInstancia().initiateDataManipulator(layoutConfirmationDialog.getContext());
		        		
		        	    Toast.makeText(getBaseContext(),"Todas as informações foram apagadas com sucesso!",Toast.LENGTH_LONG).show();
	
	        		    Intent myIntent = new Intent(layoutConfirmationDialog.getContext(), Fachada.class);
	        	        startActivity(myIntent);

	        		} else {
	        			AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
	        	        builder.setTitle("Erro");
	        	        builder.setMessage("Senha inválida");
	        	        ((EditText)layoutConfirmationDialog.findViewById(R.id.txtSenha)).setText("");
	        	        
	        	        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {}
						});
	        	        
	        	        builder.show();
	        		}

	        	}
	        });
	        
	        AlertDialog passwordDialog = builder.create();
	        return passwordDialog;
	    
	    }else if (id == Constantes.DIALOG_ID_GERAR_ARQUIVO_COMPLETO + increment) {
	    	progDialog = new ProgressDialog(this);
	    	progDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	    	progDialog.setCancelable(false);
	    	progDialog.setMessage("Por favor, espere enquanto o Arquivo de Retorno Completo está sendo gerado...");
	    	progDialog.setMax(ControladorRota.getInstancia().getDataManipulator().getNumeroImoveisNaoInformativos());
	    	progThread = new GerarArquivoCompletoThread(arquivoCompletoHandler, this, increment);
	    	progThread.start();
	    	return progDialog;
	            
	    }else if (id ==  Constantes.DIALOG_ID_ERRO || 
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
	    	
	    } else if (id == Constantes.DIALOG_ID_ENVIAR_IMOVEIS_NAO_TRANSMITIDOS+increment) {
	    	progDialog = new ProgressDialog(this);
            progDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progDialog.setCancelable(false);
            progDialog.setMessage("Por favor, aguarde enquanto os imóveis estão sendo enviados...");
            progDialog.setMax(ControladorRota.getInstancia().getDataManipulator().selectIdsImoveisConcluidosENaoEnviados().size());
            enviarImoveisThread = new EnviarImoveisConcluidosThread(imoveisNaoTransmitidosHandler, this, increment);
            enviarImoveisThread.start();
            return progDialog;
            
	    } else if (id == Constantes.DIALOG_ID_FINALIZA_ROTA+increment) {
	    		progDialog = new ProgressDialog(this);
	            progDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				progDialog.setCancelable(false);
				progDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

				    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				        if (keyCode == KeyEvent.KEYCODE_SEARCH && event.getRepeatCount() == 0) {
				            return true; // Pretend we processed it
				        
				        }else if (keyCode == KeyEvent.KEYCODE_HOME && event.getRepeatCount() == 0) {
				            return true; // Pretend we processed it
				        }
				        return false; // Any other keys are still processed as normal
				    }
				});
	            progDialog.setMessage("Por favor, aguarde enquanto os imóveis estão sendo gerados e enviados...");
	            progDialog.setMax(ControladorRota.getInstancia().getDataManipulator().selectIdsImoveisConcluidosENaoEnviados().size());
	            finalizarRotaThread = new FinalizarRotaThread(finalizarRotaHandler, this, increment);
	            finalizarRotaThread.start();
	            return progDialog;
	            
	    } else if (id == Constantes.DIALOG_ID_ROTA_NAO_FINALIZADA) {
	    	
	    	inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	
	    	final View layoutCustonDialog = inflater.inflate(R.layout.custon_dialog, (ViewGroup) findViewById(R.id.layout_root));
	    	
	    	((ImageView)layoutCustonDialog.findViewById(R.id.imageDialog)).setImageResource(R.drawable.aviso);
	    	((TextView)layoutCustonDialog.findViewById(R.id.messageDialog)).setText(dialogMessage);
	    	
	    	builder = new AlertDialog.Builder(this);
	        builder.setView(layoutCustonDialog);
	        builder.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	        	
	        	public void onClick(DialogInterface dialog, int whichButton) {
	        		removeDialog(id);
	        		ControladorRota.getInstancia().localizarImovelPendente();	        		
	        		Intent myIntent = new Intent(getApplicationContext(),MainTab.class);
	        		startActivity(myIntent);
	        	}
	        });
	        
	        AlertDialog messageDialog = builder.create();
	        return messageDialog;

	    } else if (id == Constantes.DIALOG_ID_SPINNER+increment) {
	    	progDialog = new ProgressDialog(MenuPrincipal.this);
            progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progDialog.setCancelable(false);
            progDialog.setMessage("Por favor, aguarde enquanto os imóveis estão sendo enviados...");            
            return progDialog;
            
	    } else if (id == Constantes.DIALOG_ID_CONFIRMAR_FINALIZACAO_ROTA+increment) {
	    	
	    	inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			  
			final View layoutCustonDialog = inflater.inflate(R.layout.custon_dialog, (ViewGroup) findViewById(R.id.layout_root));
	        ((TextView)layoutCustonDialog.findViewById(R.id.messageDialog)).setText(dialogMessage);
	        ((ImageView)layoutCustonDialog.findViewById(R.id.imageDialog)).setImageResource(R.drawable.save);
	    	
	    	builder = new AlertDialog.Builder(this);
	    	builder.setView(layoutCustonDialog);
	        builder.setTitle("Sucesso!");
	        
	        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	        	public void onClick(DialogInterface dialog, int which) {
	        		removeDialog(id);
	        		ControladorRota.getInstancia().finalizeDataManipulator();
	        		ControladorRota.getInstancia().deleteDatabase();
	        		ControladorRota.getInstancia().setPermissionGranted(false);
	        		ControladorRota.getInstancia().initiateDataManipulator(MenuPrincipal.this);
	        		
        		    Intent myIntent = new Intent(MenuPrincipal.this, Fachada.class);
        	        startActivity(myIntent);
	        	}
	        });
	        
	        AlertDialog passwordDialog = builder.create();
	        return passwordDialog;
	    }

	    return null;
	}
 
    public class ImageAdapter extends BaseAdapter {
        private Context context;
		public static final int ACTIVITY_CREATE = 10;

        public ImageAdapter(Context c){
            context = c;
        }
 
        //returns the number of images
        public int getCount() {
            return imageIDs.length;
        }
 
        //returns the ID of an item
        public Integer getItem(int position) {
            return TextIDs[position];
        }
 
        public long getItemId(int position) {
            return position;
        }
        		
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			
			if(convertView==null){
				LayoutInflater inflator = getLayoutInflater();
				view = inflator.inflate(R.layout.icon, null);				
			
			}else{
				view = convertView;
			}
			
			TextView textView = (TextView)view.findViewById(R.id.icon_text);
			textView.setTextColor(color.labelColor);
			textView.setText(TextIDs[position]);
			ImageView imageView = (ImageView)view.findViewById(R.id.icon_image);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(5, 5, 5, 5);
			imageView.setImageResource(imageIDs[position]);
			
			return view;
		}
    }  
    
    public File arquivoFinalizacaoRota() {
		File file = new File(Util.getRetornoRotaDirectory(), Util.getNomeArquivoEnviarConcluidos());
		
		return file.exists() ? file : null;
	}
    
    public boolean envio() {
    	if (arquivoFinalizacaoRota() != null)
    		return true;
    	
    	return false;
    }
}