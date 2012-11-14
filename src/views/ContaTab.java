package views;

import model.Consumo;
import model.Imovel;
import util.Constantes;
import util.Util;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import business.ControladorConta;
import business.ControladorImovel;

import com.IS.R;

public class ContaTab extends Fragment {
	
	private TextView leituraFaturadaAgua;
	private TextView consumoAgua;
	private TextView consumoTipoAgua;
	private TextView anormalidadeConsumoAgua;
	private TextView valorDeAgua;
	private TextView leituraFaturadaPoco;
	private TextView consumoEsgoto;
	private TextView consumoTipoEsgoto;
	private TextView anormalidadeConsumoEsgoto;
	private TextView valorDeEsgoto;
	private TextView diasDeConsumo;
	private TextView valorDebitos;
	private TextView valorCreditos;
	private TextView valorTotal;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		
		View view = inflater.inflate(R.layout.contatab, container, false);
		
		leituraFaturadaAgua = (TextView) view.findViewById(R.id.leituraFaturadaAgua);
		consumoAgua = (TextView) view.findViewById(R.id.consumoAgua);
		consumoTipoAgua = (TextView) view.findViewById(R.id.consumoTipoAgua);
		anormalidadeConsumoAgua = (TextView) view.findViewById(R.id.anormalidadeConsumoAgua);
		valorDeAgua = (TextView) view.findViewById(R.id.valorDeAgua);
		leituraFaturadaPoco = (TextView) view.findViewById(R.id.leituraFaturadaPoco);
		consumoEsgoto = (TextView) view.findViewById(R.id.consumoEsgoto);
		consumoTipoEsgoto = (TextView) view.findViewById(R.id.consumoTipoEsgoto);
		anormalidadeConsumoEsgoto = (TextView) view.findViewById(R.id.anormalidadeConsumoEsgoto);
		valorDeEsgoto = (TextView) view.findViewById(R.id.valorDeEsgoto);
		diasDeConsumo = (TextView) view.findViewById(R.id.diasDeConsumo);
		valorDebitos = (TextView) view.findViewById(R.id.valorDebitos);
		valorCreditos = (TextView) view.findViewById(R.id.valorCreditos);
		valorTotal = (TextView) view.findViewById(R.id.valorTotal);
		
		
		
		Consumo consumoAgua = getImovelSelecionado().getConsumoAgua();
		if ( consumoAgua == null ){
			consumoAgua = new Consumo();
		}
		
		Consumo consumoEsgoto = getImovelSelecionado().getConsumoEsgoto();
		if ( consumoEsgoto == null ){
			consumoEsgoto = new Consumo();
		}
		
		int leituraAgua = 0;
		int leituraEsgoto = 0;
		int qtdDiasAjustado = 0;
		boolean ajustado = false;
		
		if(getImovelSelecionado().getMedidor(ControladorConta.LIGACAO_AGUA) != null
			     && getImovelSelecionado().getMedidor(ControladorConta.LIGACAO_AGUA)
				.getLeituraAtualFaturamento() != Constantes.NULO_INT){
			    
			   leituraAgua = getImovelSelecionado().getMedidor(ControladorConta.LIGACAO_AGUA)
				.getLeituraAtualFaturamento();
			   
			   qtdDiasAjustado = getImovelSelecionado().getMedidor(ControladorConta.LIGACAO_AGUA).getQtdDiasAjustado();

			   Log.i("Leitura>> ", ">" + leituraAgua);
			   leituraFaturadaAgua.setText( leituraAgua +"" );
			   diasDeConsumo.setText( qtdDiasAjustado +"" );
			   
			   ajustado = true;
			   
			} else {
			    Log.i("Leitura>> ", ">>" + consumoAgua.getLeituraAtual());
			    if(!ajustado){
				    leituraFaturadaAgua.setText( "" + (consumoAgua.getLeituraAtual() == Constantes.NULO_INT ? "" : consumoAgua.getLeituraAtual()) );
			    	diasDeConsumo.setText( consumoAgua.getDiasConsumo()+"" );
			    }
			}
		
		if(getImovelSelecionado().getMedidor(ControladorConta.LIGACAO_POCO) != null &&
				getImovelSelecionado().getMedidor(ControladorConta.LIGACAO_POCO)
				.getLeituraAtualFaturamento() != Constantes.NULO_INT){
			    
			    leituraEsgoto = getImovelSelecionado().getMedidor(ControladorConta.LIGACAO_POCO)
				.getLeituraAtualFaturamento(); 
			    
			    qtdDiasAjustado = getImovelSelecionado().getMedidor(ControladorConta.LIGACAO_POCO).getQtdDiasAjustado();
			    
			    leituraFaturadaPoco.setText( "" + (leituraEsgoto == Constantes.NULO_INT ? "" : leituraEsgoto) );
			    diasDeConsumo.setText( qtdDiasAjustado +"" );
			    
			    ajustado = true;
			    
			}else{
			    
			    if(!ajustado){
				    leituraFaturadaPoco.setText( "" + (consumoEsgoto.getLeituraAtual() == Constantes.NULO_INT ? "" : consumoEsgoto.getLeituraAtual()) );
					diasDeConsumo.setText( consumoAgua.getDiasConsumo()+"" );
			    }
			}
		
			if(getImovelSelecionado().getMedidor(ControladorConta.LIGACAO_AGUA) == null &&
				getImovelSelecionado().getMedidor(ControladorConta.LIGACAO_POCO) == null){
			    
			    leituraFaturadaAgua.setText( "" + (consumoAgua.getLeituraAtual() == Constantes.NULO_INT ? "" : consumoAgua.getLeituraAtual()) );
			    leituraFaturadaPoco.setText( "" + (consumoEsgoto.getLeituraAtual() == Constantes.NULO_INT ? "" : consumoEsgoto.getLeituraAtual()) );
			    diasDeConsumo.setText( consumoAgua.getDiasConsumo()+"" );	
			}
			
			this.consumoAgua.setText( consumoAgua.getConsumoCobradoMes()+"" );
			this.consumoTipoAgua.setText( consumoAgua.getTipoConsumo()+"" );
			this.anormalidadeConsumoAgua.setText( "" + (consumoAgua.getAnormalidadeConsumo() == Constantes.NULO_INT ? "" : consumoAgua.getAnormalidadeConsumo()) );
			this.valorDeAgua.setText( Util.formatarDoubleParaMoedaReal( getImovelSelecionado().getValorAgua() ) );
			this.consumoEsgoto.setText( consumoEsgoto.getConsumoCobradoMes()+"" );
			this.consumoTipoEsgoto.setText( consumoEsgoto.getTipoConsumo()+"" );
			this.anormalidadeConsumoEsgoto.setText( "" + (consumoEsgoto.getAnormalidadeConsumo() == Constantes.NULO_INT ? "" : consumoEsgoto.getAnormalidadeConsumo()) );
			this.valorDeEsgoto.setText( Util.formatarDoubleParaMoedaReal( getImovelSelecionado().getValorEsgoto() ) );
			this.valorDebitos.setText( Util.formatarDoubleParaMoedaReal( getImovelSelecionado().getValorDebitos() ) );
			this.valorCreditos.setText( Util.formatarDoubleParaMoedaReal( getImovelSelecionado().getValorCreditos() ) );
			this.valorTotal.setText( Util.formatarDoubleParaMoedaReal( getImovelSelecionado().getValorConta() ) );
			
		return view;
	}
	
	public Imovel getImovelSelecionado() {
		return ControladorImovel.getInstancia().getImovelSelecionado();
	}

}