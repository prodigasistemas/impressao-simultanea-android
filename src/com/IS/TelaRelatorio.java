package com.IS;

import java.util.ArrayList;

import util.Constantes;

import business.ControladorImovel;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TelaRelatorio extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.roteirorelatorio);
    	
    	int numeroCadastros = ControladorImovel.getInstancia().getDataManipulator().getNumeroImoveis();
    	ArrayList<Integer> listStatus = (ArrayList<Integer>) ControladorImovel.getInstancia().getDataManipulator().selectNumeroTodosStatusImoveis();
    	
    	((ProgressBar)findViewById(R.id.progressVisitados)).setMax(numeroCadastros);
    	((ProgressBar)findViewById(R.id.progressVisitados)).setProgress(listStatus.get(Constantes.IMOVEL_CONCLUIDO));
    	((TextView)findViewById(R.id.txtNumeroVisitados)).setText(String.valueOf(listStatus.get(Constantes.IMOVEL_CONCLUIDO)));
    	
    	((ProgressBar)findViewById(R.id.progressNaoVisitados)).setMax(numeroCadastros);
    	((ProgressBar)findViewById(R.id.progressNaoVisitados)).setProgress(listStatus.get(Constantes.IMOVEL_PENDENTE));
    	((TextView)findViewById(R.id.txtNumeroNaoVisitados)).setText(String.valueOf(listStatus.get(Constantes.IMOVEL_PENDENTE)));

    	((ProgressBar)findViewById(R.id.progressVisitadosAnormalidade)).setMax(numeroCadastros);
    	((ProgressBar)findViewById(R.id.progressVisitadosAnormalidade)).setProgress(listStatus.get(Constantes.IMOVEL_CONCLUIDO_COM_ANORMALIDADE));
    	((TextView)findViewById(R.id.txtNumeroVisitadosAnormalidade)).setText(String.valueOf(listStatus.get(Constantes.IMOVEL_CONCLUIDO_COM_ANORMALIDADE)));
    
    	((ProgressBar)findViewById(R.id.progressNovos)).setMax(numeroCadastros);
    	((ProgressBar)findViewById(R.id.progressNovos)).setProgress(listStatus.get(Constantes.IMOVEL_RETIDO));
    	((TextView)findViewById(R.id.txtNumeroNovos)).setText(String.valueOf(listStatus.get(Constantes.IMOVEL_RETIDO)));
    
    	((ProgressBar)findViewById(R.id.progressTransmitidos)).setMax(numeroCadastros);
    	((ProgressBar)findViewById(R.id.progressTransmitidos)).setProgress(listStatus.get(Constantes.IMOVEL_TRANSMITIDO));
    	((TextView)findViewById(R.id.txtNumeroTransmitidos)).setText(String.valueOf(listStatus.get(Constantes.IMOVEL_TRANSMITIDO)));
    
    	((ProgressBar)findViewById(R.id.progressNaoTransmitidos)).setMax(numeroCadastros);
    	((ProgressBar)findViewById(R.id.progressNaoTransmitidos)).setProgress(listStatus.get(Constantes.IMOVEL_NAO_TRANSMITIDO));
    	((TextView)findViewById(R.id.txtNumeroNaoTransmitidos)).setText(String.valueOf(listStatus.get(Constantes.IMOVEL_NAO_TRANSMITIDO)));
    
    }
}