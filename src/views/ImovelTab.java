package views;

import java.util.ArrayList;

import util.Constantes;

import com.IS.R;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
	private TextView economia;
	private TextView categoria;
	ArrayList<String> economias;
	private View view;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		if (container == null) {
			return null;
		}
		
		view = inflater.inflate(R.layout.imoveltab, container, false);
		
		// Define a imagem de fundo de acordo com a orientacao do dispositivo
	    if (getResources().getConfiguration().orientation == getResources().getConfiguration().ORIENTATION_PORTRAIT)
	    	view.setBackgroundResource(R.drawable.fundocadastro);
	    else
	    	view.setBackgroundResource(R.drawable.landscapte_background);
		
		int situacaoLigAgua = Integer.parseInt(ControladorImovel.getInstancia().getImovelSelecionado().getSituacaoLigAgua());
		int situacaoLigEsgoto = Integer.parseInt(ControladorImovel.getInstancia().getImovelSelecionado().getSituacaoLigEsgoto());
		
		
		int i = 0;
		String economias = "";
		String categorias = "";
		
		Log.i("IMOVELTAB>> ", ControladorImovel.getInstancia().getDadosCategoria().get(0));
		
		for (String s : ControladorImovel.getInstancia().getDadosCategoria()) {
			if (i > 1) {
				economias += "\n";
				categorias += "\n";
				i = 0;
			}
			
			if (i == 0){
				s = ""+Integer.parseInt(s);
				economias += s;
			}
			
			if (i == 1) {
				categorias += s;
			}
			
			i++;
		}

		nomeUsuario = (TextView) view.findViewById(R.id.nomeUsuario);
		matricula = (TextView) view.findViewById(R.id.matricula);
		inscricao = (TextView) view.findViewById(R.id.inscricao);
		endereco = (TextView) view.findViewById(R.id.endereco);
		sitLigAgua = (TextView) view.findViewById(R.id.sitLigAgua);
		sitLigEsgoto = (TextView) view.findViewById(R.id.sitLigEsgoto);
		seqRota = (TextView) view.findViewById(R.id.seqRota);
		economia = (TextView) view.findViewById(R.id.economias);
		categoria = (TextView) view.findViewById(R.id.categoria);
		
		nomeUsuario.setText(ControladorImovel.getInstancia().getImovelSelecionado().getNomeUsuario());
		matricula.setText(""+ControladorImovel.getInstancia().getImovelSelecionado().getMatricula());
		inscricao.setText(ControladorImovel.getInstancia().getImovelSelecionado().getInscricao());
		endereco.setText(ControladorImovel.getInstancia().getImovelSelecionado().getEndereco());
		sitLigAgua.setText(ControladorImovel.getInstancia().getImovelSelecionado().getDescricaoSitLigacaoAgua(situacaoLigAgua));
		sitLigEsgoto.setText(ControladorImovel.getInstancia().getImovelSelecionado().getDescricaoSitLigacaoEsgoto(situacaoLigEsgoto));
		seqRota.setText(""+ControladorImovel.getInstancia().getImovelSelecionado().getSequencialRota());
		economia.setText(economias);
		categoria.setText(categorias);
		
		return view;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
			view.setBackgroundDrawable(getResources().getDrawable(R.drawable.landscapte_background));
		else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
			view.setBackgroundDrawable(getResources().getDrawable(R.drawable.fundocadastro));
	}
}