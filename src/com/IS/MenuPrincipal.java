package com.IS;

import java.util.ArrayList;

import util.Constantes;
import background.CarregarRotaThread;
import background.GerarArquivoCompletoThread;
import business.Controlador;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
 
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
	private String dialogMessage = null;
	public LocationManager mLocManager;
	private static int increment= 0;
	
    //---the images to display---
    Integer[] imageIDs = {
            R.drawable.menu_cadastros,
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
        	    	ArrayList<String> listStatusImoveis = (ArrayList)Controlador.getInstancia().getCadastroDataManipulator().selectStatusImoveis(null);
        	    	
        	    	for (int i=0; i < listStatusImoveis.size(); i++){
        	    		if ( Integer.parseInt(listStatusImoveis.get(i)) == Constantes.IMOVEL_PENDENTE ){
        	    			statusOk = false;
        				}
        	    	}
        	    	
        	    	if (statusOk){
                		showDialog(Constantes.DIALOG_ID_GERAR_ARQUIVO_COMPLETO + increment);
        	    	
        	    	}else{
            		
        	    		dialogMessage = "Não é permitido gerar arquivo de retorno Completo enquanto houver imóveis não visitados.";
            	    	showDialog(Constantes.DIALOG_ID_ERRO);
        	    	}
            		
            	}else if (position == MENU_CADASTROS_CONCLUIDOS){
					
            	}else if (position == MENU_FINALIZAR){
					
            	}else if (position == MENU_RELATORIO){
					Intent myIntent = new Intent(getApplicationContext(),TelaRelatorio.class);
	        		startActivity(myIntent);
            		
            	}else if (position == MENU_NOVO_ROTEIRO){
        	    	showDialog(Constantes.DIALOG_ID_CLEAN_DB);
				}
            }
        });        
	}
	
    // Handler on the main (UI) thread that will receive messages from the second thread and update the progress.
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            
        	// Get the current value of the variable total from the message data and update the progress bar.
        	int totalArquivoCompleto = msg.getData().getInt("arquivoCompleto" + String.valueOf(increment));
            progDialog.setProgress(totalArquivoCompleto);
            
            if (totalArquivoCompleto >= Controlador.getInstancia().getCadastroDataManipulator().getNumeroCadastros() || 
            	progThread.getCustomizedState() == CarregarRotaThread.DONE){
                
            	dismissDialog(Constantes.DIALOG_ID_GERAR_ARQUIVO_COMPLETO + increment);
            	
	    		dialogMessage = "Arquivo de retorno Completo gerado com sucesso!";
    	    	showDialog(Constantes.DIALOG_ID_SUCESSO);
			    increment = increment + 5;
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
	        		removeDialog(id);
	        		Controlador.getInstancia().getCadastroDataManipulator().close();
	        		Controlador.getInstancia().deleteDatabase();
	        		Controlador.getInstancia().setPermissionGranted(false);
	        		Controlador.getInstancia().initiateDataManipulator(layoutConfirmationDialog.getContext());
	        		
	        	    Toast.makeText(getBaseContext(),"Todas as informações foram apagadas com sucesso!",Toast.LENGTH_LONG).show();

	        	    Controlador.getInstancia().finalizeDataManipulator();
        		    Intent myIntent = new Intent(layoutConfirmationDialog.getContext(), Fachada.class);
        	        startActivity(myIntent);

	        	}
	        });
	        
	        AlertDialog passwordDialog = builder.create();
	        return passwordDialog;
	    
	    }else if (id == Constantes.DIALOG_ID_GERAR_ARQUIVO_COMPLETO + increment) {
		    	progDialog = new ProgressDialog(this);
	            progDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	            progDialog.setCancelable(false);
	            progDialog.setMessage("Por favor, espere enquanto o Arquivo de Retorno Completo está sendo gerado...");
	            progDialog.setMax(Controlador.getInstancia().getCadastroDataManipulator().getNumeroCadastros());
	            progThread = new GerarArquivoCompletoThread(handler, this, increment);
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
        public Object getItem(int position) {
            return position;
        }
 
        public long getItemId(int position) {
            return position;
        }
 
		
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;

			if(convertView==null){
				
				LayoutInflater inflator = getLayoutInflater();
				view = inflator.inflate(R.layout.icon, null);
				TextView textView = (TextView)view.findViewById(R.id.icon_text);
				textView.setText(TextIDs[position]);
				ImageView imageView = (ImageView)view.findViewById(R.id.icon_image);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(5, 5, 5, 5);
				imageView.setImageResource(imageIDs[position]);
			
			}else{
				view = convertView;
			}
			return view;
		}
    }    
}