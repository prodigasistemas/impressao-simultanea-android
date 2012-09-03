package views;

import util.Constantes;
import model.Imovel;
import model.Medidor;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
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
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import business.ControladorImovel;

import com.IS.ListaImoveis;
import com.IS.MenuPrincipal;
import com.IS.R;

public class MainTab extends FragmentActivity implements TabHost.OnTabChangeListener {

	private static TabHost tabHost;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.maintab);
	    
	    tabHost = (TabHost) findViewById(android.R.id.tabhost);
	    tabHost.setup();
	    tabHost.setOnTabChangedListener(this);
	    
	    FragmentManager fm = getSupportFragmentManager();
	    Fragment fragment = fm.findFragmentById(android.R.id.tabcontent);
	    
	    if (fragment == null) {
	    	FragmentTransaction ft = fm.beginTransaction();
	    	ft.add(android.R.id.tabcontent, new ImovelTab());
	    	ft.commit();
	    }
	    
	    
	    addTab("imovel", "Imóvel", R.drawable.tab_imovel, R.layout.imoveltab, ImovelTab.class);

	    final Medidor medidor = ControladorImovel.getInstancia().getMedidorSelecionado();
	    
	    if (medidor.getNumeroHidrometro() != "") {
	    	addTab("medidor", "Medidor", R.drawable.tab_medidor, R.layout.medidoraguatab, MedidorTab.class);
	    } else {
	    	addTab("conta", "Conta", R.drawable.text, R.layout.contatab, ContaTab.class);
	    }

	}
	
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
    
	public boolean onOptionsItemSelected(MenuItem item) {
		
		Log.i("Menu item", item.getItemId()+"");
		
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.proximoImovel:

	    	ControladorImovel.getInstancia().isCadastroAlterado();
	    	
	    	if(ControladorImovel.getInstancia().getCadastroListPosition() == (ControladorImovel.getInstancia().getCadastroDataManipulator().getNumeroCadastros())-1){
				ControladorImovel.getInstancia().setCadastroSelecionadoByListPosition(0);

			}else{
		    	ControladorImovel.getInstancia().setCadastroSelecionadoByListPosition(ControladorImovel.getInstancia().getCadastroListPosition()+1);
			}
	    	finish();
			Intent myIntent = new Intent(getApplicationContext(), MainTab.class);
			startActivity(myIntent);
	    	return true;

	    case R.id.imovelAnterior:

	    	ControladorImovel.getInstancia().isCadastroAlterado();
	    	
	    	if(ControladorImovel.getInstancia().getCadastroListPosition() <= 0){
				ControladorImovel.getInstancia().setCadastroSelecionadoByListPosition((int)ControladorImovel.getInstancia().getCadastroDataManipulator().getNumeroCadastros()-1);
			}else{
		    	ControladorImovel.getInstancia().setCadastroSelecionadoByListPosition(ControladorImovel.getInstancia().getCadastroListPosition()-1);
			}
	    	finish();
	    	
			myIntent = new Intent(getApplicationContext(), MainTab.class);
			startActivity(myIntent);
	        return true;
	    
//	    case R.id.adicionarNovo:
//			
//			myIntent = new Intent(getApplicationContext(), ListaAddImovel.class);
//			startActivity(myIntent);

//	    	ControladorImovel.getInstancia().setCadastroSelecionadoByListPosition(-1);
//	    	ControladorImovel.getInstancia().initCadastroTabs();
//	    	finish();
//	    	myIntent = new Intent(getApplicationContext(), MainTab.class);
//			startActivity(myIntent);
//	        return true;
	        
//	    case R.id.menuPrincipal:
//			
//	    	myIntent = new Intent(getApplicationContext(), MenuPrincipal.class);
//			startActivity(myIntent);
//	        return true;
	        
	    case R.id.listCadastros:
			
	    	myIntent = new Intent(getApplicationContext(), ListaImoveis.class);
			startActivity(myIntent);
	        return true;
	        
//	    case R.id.sair:
//			
//	        return true;
	        
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	protected void onResume() {
//		initializeTabs();
		super.onResume();
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

}