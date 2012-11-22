package com.IS;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import model.Imovel;
import util.Constantes;
import util.Util;
import views.MainTab;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
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
import business.ControladorAcessoOnline;
import business.ControladorImovel;
import business.ControladorRota;

import com.IS.R.color;
 
@SuppressLint("NewApi")
public class MenuPrincipal extends Activity {
	
	static final int MENU_LISTA_CADASTROS = 0;
	static final int MENU_INFO = 1;
	static final int MENU_CONSULTA = 2;
	static final int MENU_ARQUIVO_COMPLETO = 3;
	static final int MENU_CADASTROS_CONCLUIDOS = 4;
	static final int MENU_FINALIZAR = 5;
	static final int MENU_RELATORIO = 6;
	static final int MENU_NOVO_ROTEIRO = 7;
	static final int MENU_SELECIONAR_IMPRESSORA = 8;

	private ProgressDialog progDialog;
	private GerarArquivoCompletoThread progThread;
	private EnviarImoveisConcluidosThread enviarImoveisThread;
	private FinalizarRotaThread finalizarRotaThread;
	private String dialogMessage = null;
	public LocationManager mLocManager;
	private static int increment= 0;
	private BluetoothAdapter bluetoothAdapter;
	private ListView listaDispositivos;
	private AlertDialog dialog;
	
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
            R.drawable.menu_select_impressora
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
            R.string.menu_selecionar_impressora
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
				
            	if (position == MENU_LISTA_CADASTROS){
            		
            		// Verifica se GPS esta ligado
            		/* Use the LocationManager class to obtain GPS locations */
                    mLocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                    
                    boolean enabled = mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                    // Check if enabled and if not send user to the GSP settings
                    // Better solution would be to display a dialog and suggesting to 
                    // go to the settings
                    if (!enabled){
            	        dialogMessage = "GPS está desligado. Por favor, ligue-o para continuar o cadastro.";
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
            		
            	}else if (position == MENU_CADASTROS_CONCLUIDOS){
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
				} else if (position == MENU_SELECIONAR_IMPRESSORA) {
					
					Intent intentBluetooth = new Intent();
			        intentBluetooth.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
			        startActivityForResult(intentBluetooth, 0);
				}
            }
        });        
	}
	
	@TargetApi(5)
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
					
					Toast.makeText(MenuPrincipal.this, "Impressora registrada", 5).show();
					
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
			    increment += 15;
            }
         }
    };
    
    final Handler imoveisNaoTransmitidosHandler = new Handler() {
        @SuppressWarnings({ "deprecation", "static-access" })
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
            	
            	increment += 15;
            	
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
        @SuppressWarnings({ "deprecation", "static-access" })
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
            	
            	increment += 15;
            	
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
	    		  id ==  Constantes.DIALOG_ID_ERRO_GPS_DESLIGADO){
	    	
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			  
			final View layoutCustonDialog = inflater.inflate(R.layout.custon_dialog, (ViewGroup) findViewById(R.id.layout_root));
	        ((TextView)layoutCustonDialog.findViewById(R.id.messageDialog)).setText(dialogMessage);
	        
	        if (id == Constantes.DIALOG_ID_SUCESSO){
		        ((ImageView)layoutCustonDialog.findViewById(R.id.imageDialog)).setImageResource(R.drawable.save);

	        }else if (id == Constantes.DIALOG_ID_ERRO || id == Constantes.DIALOG_ID_ERRO_GPS_DESLIGADO){
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

	        		localizarImovelPendente();
	        		
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
 
    public class ImageAdapter extends BaseAdapter 
    {
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
    
    public void localizarImovelPendente() {
    	
    	ListaImoveis.tamanhoListaImoveis = ControladorRota.getInstancia().getDataManipulator().getNumeroImoveis();
		
		Imovel imovelPendente = ControladorRota.getInstancia().getDataManipulator().selectImovel("imovel_status = "+Constantes.IMOVEL_STATUS_PENDENTE, false);
		
		// Se nao encontrar imovel com status pendente
		if (imovelPendente == null) {
			return;
		}
		
		ControladorImovel.getInstancia().setImovelSelecionadoByListPosition(Long.valueOf(imovelPendente.getId()).intValue()-1);
	
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