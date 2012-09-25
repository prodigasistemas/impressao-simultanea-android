package views;

import java.util.ArrayList;

import model.DadosCategoria;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import business.ControladorImovel;

import com.IS.R;

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
	private TextView imovelInformativo;
	
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
	    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
	    	view.setBackgroundResource(R.drawable.fundocadastro);
	    else
	    	view.setBackgroundResource(R.drawable.landscape_background);
		
		int situacaoLigAgua = Integer.parseInt(ControladorImovel.getInstancia().getImovelSelecionado().getSituacaoLigAgua());
		int situacaoLigEsgoto = Integer.parseInt(ControladorImovel.getInstancia().getImovelSelecionado().getSituacaoLigEsgoto());
		
		
		String economias = "";
		String categorias = "";
		int qtdDadosCategoria = ControladorImovel.getInstancia().getImovelSelecionado().getDadosCategoria().size();
		
		for (DadosCategoria dc : ControladorImovel.getInstancia().getImovelSelecionado().getDadosCategoria()) {
			
			economias += ""+Integer.parseInt(""+dc.getQtdEconomiasSubcategoria());
			categorias += dc.getDescricaoCategoria();
			
			
			if (qtdDadosCategoria > 1) {
				economias += "\n";
				categorias += "\n";
			} else 
				break;

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
		imovelInformativo = (TextView) view.findViewById(R.id.imovelInformativo);
		
		nomeUsuario.setText(ControladorImovel.getInstancia().getImovelSelecionado().getNomeUsuario());
		matricula.setText(""+ControladorImovel.getInstancia().getImovelSelecionado().getMatricula());
		inscricao.setText(ControladorImovel.getInstancia().getImovelSelecionado().getInscricao());
		endereco.setText(ControladorImovel.getInstancia().getImovelSelecionado().getEndereco());
		sitLigAgua.setText(ControladorImovel.getInstancia().getImovelSelecionado().getDescricaoSitLigacaoAgua(situacaoLigAgua));
		sitLigEsgoto.setText(ControladorImovel.getInstancia().getImovelSelecionado().getDescricaoSitLigacaoEsgoto(situacaoLigEsgoto));
		seqRota.setText(""+ControladorImovel.getInstancia().getImovelSelecionado().getSequencialRota());
		economia.setText(economias);
		categoria.setText(categorias);
		
		if (ControladorImovel.getInstancia().getImovelSelecionado().isImovelInformativo()) {
			imovelInformativo.setText("Imóvel informativo");
			imovelInformativo.setBackgroundResource(R.drawable.box_imovel_informativo);
			imovelInformativo.setTextColor(Color.RED);
			imovelInformativo.setTextSize(20);
			imovelInformativo.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 50));
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
}