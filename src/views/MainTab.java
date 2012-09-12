package views;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import model.Imovel;
import util.Constantes;
import util.ImpressaoContaCosanpa;
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
import business.ControladorImovel;
import business.ControladorRota;

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
	    	tabHost.setBackgroundResource(R.drawable.landscapte_background);
	    
	    FragmentManager fm = getSupportFragmentManager();
	    Fragment fragment = fm.findFragmentById(android.R.id.tabcontent);
	    
	    if (fragment == null) {
	    	FragmentTransaction ft = fm.beginTransaction();
	    	ft.add(android.R.id.tabcontent, new ImovelTab());
	    	ft.commit();
	    }
	    
	    addTab("imovel", "Imóvel", R.drawable.tab_imovel, R.layout.imoveltab, ImovelTab.class);

	    if (ControladorImovel.getInstancia().getMedidorSelecionado().getNumeroHidrometro() != "") {
	    	addTab("medidor", "Medidor", R.drawable.tab_medidor, R.layout.medidoraguatab, MedidorTab.class);

	    } else {
	    	addTab("conta", "Conta", R.drawable.text, R.layout.contatab, ContaTab.class);
	    }

	}
	
	// Instancia novas tabs
	public void addTab(String tag, String titulo, int imagem, final int view, Class classe) {
		TabHost.TabSpec tabSpec;
	    Resources res = getResources();
	    Intent intent = null;
		
		intent = new Intent().setClass(this, classe);
	    tabSpec = tabHost.newTabSpec(tag).setIndicator(titulo, res.getDrawable(imagem)).setContent(new TabContentFactory() {

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
	    	
	    	
	    	imprimirConta();
	    	
	    	
	        
	    	return true;
	    	
	    case R.id.localizarPendente:
	    	
	    	localizarImovelPendente();
	    	
	    	return true;
	        
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	public void localizarImovelPendente() {
		List<Imovel> imoveis = ControladorRota.getInstancia().getDataManipulator()
				.selectImovelCondition("imovel_status = "+Constantes.IMOVEL_PENDENTE);
		
		if (imoveis.size() == 0) {
			Toast.makeText(this, "Não existem imóveis pendentes", 10).show();
			return;
		}
		
		ControladorImovel.getInstancia().setImovelSelecionado(imoveis.get(0));
		
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		
		tabHost.setCurrentTab(0);
		ft.add(android.R.id.tabcontent, new ImovelTab());
		ft.commit();
		
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
	    	listaDispositivos.setOnItemClickListener(this);
	    	
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
	
	
	
	public Imovel getImovelSelecionado() {
		return ControladorImovel.getInstancia().getImovelSelecionado();
	}

	public void onTabChanged(String tabId) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
    	
		if (tabId.equals("imovel"))
			ft.add(android.R.id.tabcontent, new ImovelTab());
		else if (tabId.equals("medidor"))
			ft.add(android.R.id.tabcontent, new MedidorTab());
		else if (tabId.equals("conta"))
			ft.add(android.R.id.tabcontent, new ContaTab());
		
    	ft.commit();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
			tabHost.setBackgroundDrawable(getResources().getDrawable(R.drawable.landscapte_background));
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