package views;

import business.ControladorImovel;

import com.IS.R;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MedidorTab extends Fragment {
	
	
	private TextView endereco;
	private TextView hidrometro;
	private TextView locInstalacao;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		if (container == null) {
			return null;
		}
		
		View view = inflater.inflate(R.layout.medidoraguatab, container, false);
		
		Log.i("LOG>> ", ""+ControladorImovel.getInstancia().getMedidorSelecionado().getMatricula());
//		Log.i("LOG>> ", ""+ControladorImovel.getInstancia().getMedidorSelecionado().getNumeroHidrometro());
//		Log.i("LOG>> ", ""+ControladorImovel.getInstancia().getMedidorSelecionado().getLocalInstalacao());
		
		endereco = (TextView) view.findViewById(R.id.endereco);
		hidrometro = (TextView) view.findViewById(R.id.hidrometro);
		locInstalacao = (TextView) view.findViewById(R.id.locInstalacao);
		
		endereco.setText(ControladorImovel.getInstancia().getImovelSelecionado().getEndereco());
		hidrometro.setText(ControladorImovel.getInstancia().getMedidorSelecionado().getNumeroHidrometro());
		locInstalacao.setText(ControladorImovel.getInstancia().getMedidorSelecionado().getLocalInstalacao());
		
		
		return view;
	}

}
