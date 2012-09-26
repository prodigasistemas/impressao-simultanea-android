package com.IS;

import java.util.ArrayList;

import util.Constantes;

import business.ControladorRota;

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
    	
    	ArrayList<Integer> listStatus = (ArrayList<Integer>) ControladorRota.getInstancia().getDataManipulator().selectEstatisticaImoveis();
 
    	int numeroTotalImoveis = ControladorRota.getInstancia().getDataManipulator().getNumeroImoveis();
    	int numeroImoveisNaoInformativos = (numeroTotalImoveis - listStatus.get(Constantes.NUMERO_INFORMATIVO));
    	int numeroHidrometradosTotal = listStatus.get(Constantes.NUMERO_HIDROMETRADO);
    	int numeroNaoMedidoTotal = listStatus.get(Constantes.NUMERO_NAO_MEDIDO);
    			
    	((ProgressBar)findViewById(R.id.progressConcluidos)).setMax(numeroImoveisNaoInformativos);
    	((ProgressBar)findViewById(R.id.progressConcluidos)).setProgress(listStatus.get(Constantes.IMOVEL_STATUS_CONCLUIDO));
    	((TextView)findViewById(R.id.txtNumeroConcluidos)).setText(String.valueOf(listStatus.get(Constantes.IMOVEL_STATUS_CONCLUIDO)) + " / " + numeroImoveisNaoInformativos);
    	
    	((ProgressBar)findViewById(R.id.progressPendentes)).setMax(numeroImoveisNaoInformativos);
    	((ProgressBar)findViewById(R.id.progressPendentes)).setProgress(listStatus.get(Constantes.IMOVEL_STATUS_PENDENTE));
    	((TextView)findViewById(R.id.txtNumeroPendentes)).setText(String.valueOf(listStatus.get(Constantes.IMOVEL_STATUS_PENDENTE)) + " / " + numeroImoveisNaoInformativos);

		((ProgressBar)findViewById(R.id.progressTransmitidos)).setMax(numeroImoveisNaoInformativos);
		((ProgressBar)findViewById(R.id.progressTransmitidos)).setProgress(listStatus.get(Constantes.IMOVEL_TRANSMITIDO));
		((TextView)findViewById(R.id.txtNumeroTransmitidos)).setText(String.valueOf(listStatus.get(Constantes.IMOVEL_TRANSMITIDO)) + " / " + numeroImoveisNaoInformativos);
		
		((ProgressBar)findViewById(R.id.progressImpressos)).setMax(numeroImoveisNaoInformativos);
		((ProgressBar)findViewById(R.id.progressImpressos)).setProgress(listStatus.get(Constantes.IMOVEL_IMPRESSO));
		((TextView)findViewById(R.id.txtNumeroImpressos)).setText(String.valueOf(listStatus.get(Constantes.IMOVEL_IMPRESSO)) + " / " + numeroImoveisNaoInformativos);
		
    	((ProgressBar)findViewById(R.id.progressRetidos)).setMax(numeroImoveisNaoInformativos);
    	((ProgressBar)findViewById(R.id.progressRetidos)).setProgress(listStatus.get(Constantes.IMOVEL_RETIDO));
    	((TextView)findViewById(R.id.txtNumeroRetidos)).setText(String.valueOf(listStatus.get(Constantes.IMOVEL_RETIDO)) + " / " + numeroImoveisNaoInformativos);
    
    	((ProgressBar)findViewById(R.id.progressHidrometradosConcluidos)).setMax(numeroHidrometradosTotal);
    	((ProgressBar)findViewById(R.id.progressHidrometradosConcluidos)).setProgress(listStatus.get(Constantes.IMOVEL_HIDROMETRADO_CONCLUIDO));
    	((TextView)findViewById(R.id.txtNumeroHidrometradosConcluidos)).setText(String.valueOf(listStatus.get(Constantes.IMOVEL_HIDROMETRADO_CONCLUIDO)) + " / " + numeroHidrometradosTotal);
    
    	((ProgressBar)findViewById(R.id.progressFixosConcluidos)).setMax(numeroNaoMedidoTotal);
    	((ProgressBar)findViewById(R.id.progressFixosConcluidos)).setProgress(listStatus.get(Constantes.IMOVEL_NAO_MEDIDO_CONCLUIDO));
    	((TextView)findViewById(R.id.txtNumeroFixosConcluidos)).setText(String.valueOf(listStatus.get(Constantes.IMOVEL_NAO_MEDIDO_CONCLUIDO)) + " / " + numeroNaoMedidoTotal);
    
    	((ProgressBar)findViewById(R.id.progressTransmitidos)).setMax(numeroImoveisNaoInformativos);
    	((ProgressBar)findViewById(R.id.progressTransmitidos)).setProgress(listStatus.get(Constantes.IMOVEL_TRANSMITIDO));
    	((TextView)findViewById(R.id.txtNumeroTransmitidos)).setText(String.valueOf(listStatus.get(Constantes.IMOVEL_TRANSMITIDO)) + " / " + numeroImoveisNaoInformativos);
    
    }
}