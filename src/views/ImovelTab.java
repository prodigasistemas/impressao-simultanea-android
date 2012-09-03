package views;

import com.IS.R;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import business.ControladorImovel;

public class ImovelTab extends Fragment {

	private TextView sitLigAgua;
	private TextView sitLigEsgoto;
	private TextView nomeUsuario;
	private TextView matricula;
	private TextView inscricao;
	private TextView endereco;
	private TextView seqRota;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		instanciate();
	}

	protected void onNewIntent(Intent intent) {
		instanciate();
	}

	public void instanciate() {
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		if (container == null) {
			return null;
		}
		
		View view = inflater.inflate(R.layout.imoveltab, container, false);
		
		nomeUsuario = (TextView) view.findViewById(R.id.nomeUsuario);
		matricula = (TextView) view.findViewById(R.id.matricula);
		inscricao = (TextView) view.findViewById(R.id.inscricao);
		endereco = (TextView) view.findViewById(R.id.endereco);
		sitLigAgua = (TextView) view.findViewById(R.id.sitLigAgua);
		sitLigEsgoto = (TextView) view.findViewById(R.id.sitLigEsgoto);
		seqRota = (TextView) view.findViewById(R.id.seqRota);
		
		nomeUsuario.setText(ControladorImovel.getInstancia().getImovelSelecionado().getNomeUsuario());
		matricula.setText(""+ControladorImovel.getInstancia().getImovelSelecionado().getMatricula());
		inscricao.setText(ControladorImovel.getInstancia().getImovelSelecionado().getInscricao());
		endereco.setText(ControladorImovel.getInstancia().getImovelSelecionado().getEndereco());
		sitLigAgua.setText(ControladorImovel.getInstancia().getImovelSelecionado().getSituacaoLigAguaString());
		sitLigEsgoto.setText(ControladorImovel.getInstancia().getImovelSelecionado().getSituacaoLigEsgotoString());
		seqRota.setText(""+ControladorImovel.getInstancia().getImovelSelecionado().getSequencialRota());
		
//		instanciate();
		
		return view;
	}


}