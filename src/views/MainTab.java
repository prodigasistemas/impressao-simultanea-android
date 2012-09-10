package views;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import model.Imovel;
import model.Medidor;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.ParcelUuid;
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
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import business.ControladorImovel;

import com.IS.R;
import com.zebra.android.comm.BluetoothPrinterConnection;
import com.zebra.android.comm.ZebraPrinterConnection;
import com.zebra.android.comm.ZebraPrinterConnectionException;

@SuppressLint("NewApi")
public class MainTab extends FragmentActivity implements TabHost.OnTabChangeListener {

	private static TabHost tabHost;
	private BluetoothAdapter bluetoothAdapter;
	private ListView listaDispositivos;
	
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
	    	
	    	if(ControladorImovel.getInstancia().getImovelListPosition() == (ControladorImovel.getInstancia().getDataManipulator().getNumeroImoveis())-1){
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
				ControladorImovel.getInstancia().setImovelSelecionadoByListPosition((int)ControladorImovel.getInstancia().getDataManipulator().getNumeroImoveis()-1);
			}else{
		    	ControladorImovel.getInstancia().setImovelSelecionadoByListPosition(ControladorImovel.getInstancia().getImovelListPosition()-1);
			}
	    	finish();
	    	
			myIntent = new Intent(getApplicationContext(), MainTab.class);
			startActivity(myIntent);
	        return true;
	    
	        
	    case R.id.imprimirConta:
	    	
	    	ZebraPrinterConnection conexao = null;
	    	Set<BluetoothDevice> dispositivosPareados = bluetoothAdapter.getBondedDevices();
	    	Iterator<BluetoothDevice> iterator = dispositivosPareados.iterator();
	    	List<BluetoothDevice> impressorasPareadas = new ArrayList<BluetoothDevice>(); 
	    	
	    	while (iterator.hasNext()) {
	    		BluetoothDevice device = iterator.next();
	    		// Seleciona apenas as impressoras pareadas
	    		if (device.getBluetoothClass().getMajorDeviceClass() == 1536) {
	    			impressorasPareadas.add(device);
	    		}
	    	}
	    	

	    	
	    	if (impressorasPareadas.size() > 0) {
	    		Log.i("PAREADOS: ", ""+ impressorasPareadas);
	    		
	    		try {
	    			if (impressorasPareadas.size() == 1) {
		    			BluetoothDevice device = impressorasPareadas.get(0);
		    			conexao = new BluetoothPrinterConnection(device.getAddress());
		    			conexao.open();
		    		}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						conexao.close();
					} catch (ZebraPrinterConnectionException e) {
						e.printStackTrace();
					}
				}
	    		
	    		
	    	} else {
	    		Intent intentBluetooth = new Intent();
		        intentBluetooth.setAction(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS);
		        startActivity(intentBluetooth);
	    	}
	        
	    	return true;
	        
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		return super.onCreateDialog(id);
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
}