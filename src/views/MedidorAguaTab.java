package views;

import java.util.ArrayList;
import java.util.List;

import model.Imovel;

import util.Constantes;

import business.ControladorImovel;
import business.ControladorRota;

import com.IS.R;

import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class MedidorAguaTab extends Fragment {
	
	
	static boolean consideraEventoItemSelectedListenerCodigoAnormalidade;
	private TextView endereco;
	private TextView hidrometro;
	private TextView locInstalacao;
	private View view;
	List<String> listAnormalidades;
	private static EditText codigoAnormalidade;
    private static EditText leitura;
    private static int leituraDigitada;
    private static View layout;
	private TextView imovelCondominial;
	private ImageView imovelCondominialImage;
	private LinearLayout imovelCondominialLayout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		
		if (container == null) {
			return null;
		}
		
		view = inflater.inflate(R.layout.medidoraguatab, container, false);
		layout = view;
		
		// Define a imagem de fundo de acordo com a orientacao do dispositivo
	    if (getResources().getConfiguration().orientation == getResources().getConfiguration().ORIENTATION_PORTRAIT)
	    	view.setBackgroundResource(R.drawable.fundocadastro);
	    else
	    	view.setBackgroundResource(R.drawable.landscape_background);
	    
		endereco = (TextView) view.findViewById(R.id.endereco);
		hidrometro = (TextView) view.findViewById(R.id.hidrometro);
		locInstalacao = (TextView) view.findViewById(R.id.locInstalacao);
		leitura = (EditText)view.findViewById(R.id.leitura);
		imovelCondominial = (TextView) view.findViewById(R.id.imovelCondominial);
		imovelCondominialImage = (ImageView) view.findViewById(R.id.imovelCondominialImage);
		imovelCondominialLayout = (LinearLayout) view.findViewById(R.id.imovelCondominialLayout);

		endereco.setText(getImovelSelecionado().getEndereco());
		hidrometro.setText(getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).getNumeroHidrometro());
		locInstalacao.setText(getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).getLocalInstalacao());
		
		// Codigo de Anormalidade
        codigoAnormalidade = (EditText)view.findViewById(R.id.codigoAnormalidade);
        codigoAnormalidade.addTextChangedListener(new TextWatcher() {

    		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}  
    		
    	    public void onTextChanged(CharSequence s, int start, int before, int after) {  
    	      
    			// Quando o texto é alterado o onTextChange é chamado. Essa flag evita a chamada infinita desse método  
    			if (consideraEventoItemSelectedListenerCodigoAnormalidade){
    				consideraEventoItemSelectedListenerCodigoAnormalidade = false;  
    				return;  
    			}  
    	      
 				String descricaoAnormalidade = ControladorRota.getInstancia().getDataManipulator().selectDescricaoByCodigoFromTable(Constantes.TABLE_ANORMALIDADE, s.toString());
 				if (descricaoAnormalidade != null){
 					List<String> lista = ControladorRota.getInstancia().getDataManipulator().selectDescricoesFromTable(Constantes.TABLE_ANORMALIDADE);
 					for (int i = 0; i < lista.size(); i++){
 			        	if (lista.get(i).equalsIgnoreCase(descricaoAnormalidade)){
 			                ((Spinner)(view.findViewById(R.id.spinnerAnormalidade))).setSelection(i+1);
 			        		break;
 			        	}else{
 			                ((Spinner)(view.findViewById(R.id.spinnerAnormalidade))).setSelection(0); 			        		
 			        	}
 			        }
 				}
    		}  
    		
    	    public void afterTextChanged(Editable s) {}  
		});

		// Spinner Tipo de Anormalidade
        Spinner spinnerTipoAnormalidade = (Spinner) view.findViewById(R.id.spinnerAnormalidade);
        
        listAnormalidades = new ArrayList<String>();
        listAnormalidades = ControladorRota.getInstancia().getAnormalidades(true);
        listAnormalidades.add(0, "");

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, listAnormalidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoAnormalidade.setAdapter(adapter);
        spinnerTipoAnormalidade.setOnItemSelectedListener(new OnItemSelectedListener () {

        	
			public void onItemSelected(AdapterView parent, View v, int position, long id){
 				String codigo = ControladorRota.getInstancia().getDataManipulator().selectCodigoByDescricaoFromTable(Constantes.TABLE_ANORMALIDADE, ((Spinner)view.findViewById(R.id.spinnerAnormalidade)).getSelectedItem().toString());
 				
 				if (codigo.compareTo(((EditText)view.findViewById(R.id.codigoAnormalidade)).getText().toString()) != 0 &&
 					((Spinner)(view.findViewById(R.id.spinnerAnormalidade))).getSelectedItemPosition() != 0){
 					
 					consideraEventoItemSelectedListenerCodigoAnormalidade = true;  
 					((EditText)view.findViewById(R.id.codigoAnormalidade)).setText(codigo);
	        	}
			}
			
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
        
        //Populate Leitura e Anormalidade
        if (getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).getLeitura() != Constantes.NULO_INT){
        	leitura.setText(String.valueOf(getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).getLeitura()));
        }
        
        if (getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).getAnormalidade() > 0 ){
        	codigoAnormalidade.setText(String.valueOf(getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).getAnormalidade()));
        }
		
		if (getImovelSelecionado().isImovelCondominio()) {
			imovelCondominialLayout.setBackgroundResource(R.drawable.box_imovel_condominio);
			imovelCondominial.setText(" Imóvel " + 
									  getImovelSelecionado().getIndiceImovelCondominio() + 
									  " de " 
									  + getImovelSelecionado().getQuantidadeImoveisCondominio());
			
			imovelCondominial.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			imovelCondominialImage.setImageResource(R.drawable.condominio);
		}
			
		return view;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
			view.setBackgroundDrawable(getResources().getDrawable(R.drawable.landscape_background));
		else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
			view.setBackgroundDrawable(getResources().getDrawable(R.drawable.fundocadastro));
	}

	public static String getLeitura(){
		try {
			return ((EditText)layout.findViewById(R.id.leitura)).getText().toString();
		} catch (NullPointerException e) {
			return "";
		}
	}

	public static String getCodigoAnormalidade(){
		try {
			return ((EditText)layout.findViewById(R.id.codigoAnormalidade)).getText().toString();
		} catch (NullPointerException e) {
			 return "";
		}
	}

	public static void setLeituraDigitada(int leituraDigitada) {
		MedidorAguaTab.leituraDigitada = leituraDigitada;
    }

    public static int getLeituraDigitada() {
    	return MedidorAguaTab.leituraDigitada;
    }

    public static String getLeituraCampo() {
    	return getLeitura();
    }

    public static void setLeituraCampo(String leitura) {
    	((EditText)layout.findViewById(R.id.leitura)).setText(leitura);
    }
    
    public Imovel getImovelSelecionado(){
    	return ControladorImovel.getInstancia().getImovelSelecionado();
    }

}
