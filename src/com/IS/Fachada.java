package com.IS;

import business.ControladorAcessoOnline;
import business.ControladorImovel;
import business.ControladorRota;

import util.Constantes;
import util.Criptografia;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Fachada extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.welcome);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    	
	    // get IMEI
    	ControladorAcessoOnline.getInstancia().setIMEI(((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());

        final Animation animation = new AlphaAnimation(1, (float)0.3);
        animation.setDuration(1000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);

        final Button startButton = (Button)findViewById(R.id.buttonStart);
        startButton.startAnimation(animation);

        startButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	v.clearAnimation();

                if (ControladorRota.getInstancia().databaseExists(getBaseContext()) && 
                	ControladorRota.getInstancia().isDatabaseRotaCarregadaOk() == Constantes.SIM){
                	
                	if (!ControladorRota.getInstancia().isPermissionGranted()){
                    	ControladorRota.getInstancia().initiateDataManipulator(getBaseContext());
                	}
                	onPasswordDialogButtonClick(findViewById(R.id.buttonStart));			

                }else{
        		    Intent myIntent = new Intent(getBaseContext(), ListaRotas.class);
        	        startActivityForResult(myIntent, 1);
                }
            }
        });
    }

	protected void onNewIntent(Intent intent) {
		
		super.onNewIntent(intent);
		setIntent(intent);//must store the new intent unless getIntent() will return the old one.

        final Animation animation = new AlphaAnimation(1, (float)0.3);
        animation.setDuration(1000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);

        final Button startButton = (Button)findViewById(R.id.buttonStart);
        startButton.startAnimation(animation);

        startButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	v.clearAnimation();

            	// Define URL GSAN
            	//Produção
            	ControladorAcessoOnline.getInstancia().setURL("http:/200.178.173.131:8080/gsan");
            	
            	//Treinamento
            	ControladorAcessoOnline.getInstancia().setURL("http:/200.178.173.136:8080/gsan");
            	
                if (ControladorRota.getInstancia().databaseExists(getBaseContext()) &&
                	ControladorRota.getInstancia().isDatabaseRotaCarregadaOk() == Constantes.SIM){
                	
                	if (!ControladorRota.getInstancia().isPermissionGranted()){
                    	ControladorRota.getInstancia().initiateDataManipulator(getBaseContext());
                	}
                	onPasswordDialogButtonClick(findViewById(R.id.buttonStart));			

                }else{
        		    Intent myIntent = new Intent(getBaseContext(), ListaRotas.class);
        	        startActivityForResult(myIntent, 1);
                }
            }
        });
	}

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ControladorRota.getInstancia().databaseExists(getBaseContext()) &&
        	ControladorRota.getInstancia().isDatabaseRotaCarregadaOk() == Constantes.SIM){
        	
        	if (!ControladorRota.getInstancia().isPermissionGranted()){
            	ControladorRota.getInstancia().initiateDataManipulator(getBaseContext());
        	}
        	onPasswordDialogButtonClick(findViewById(R.id.buttonStart));			

        }
    }
    
	public void carregaRotaDialogButtonClick(String fileName) {
		showDialog(Constantes.DIALOG_ID_CARREGAR_ROTA);
	}
	
	public void onPasswordDialogButtonClick(View v) {
	    if(!ControladorRota.getInstancia().isPermissionGranted()){
	    	ControladorRota.getInstancia().getDataManipulator().selectGeral();
	    	showDialog(Constantes.DIALOG_ID_PASSWORD);
	    	
	    }else{
		    Intent myIntent = new Intent(v.getContext(), MenuPrincipal.class);
		    startActivity(myIntent);
	    }
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	   
	    case Constantes.DIALOG_ID_PASSWORD:
	        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  
	        final View layout = inflater.inflate(R.layout.login, (ViewGroup) findViewById(R.id.root));
	        final EditText user = (EditText) layout.findViewById(R.id.EditText_User);
	        final EditText password = (EditText) layout.findViewById(R.id.EditText_Password);
	        final TextView error = (TextView) layout.findViewById(R.id.TextView_PwdProblem);
	  
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Autenticação");
	        builder.setView(layout);
	        
	        
	        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
	        	
	        	public void onClick(DialogInterface dialog, int whichButton) {
	        		removeDialog(Constantes.DIALOG_ID_PASSWORD);
	        		ControladorRota.getInstancia().setPermissionGranted(false);
	        	}
	        });
	        	 
	        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
	        	public void onClick(DialogInterface dialog, int which) {
	        	      String strUsr = user.getText().toString();
	        	      String strPass = password.getText().toString();
	        		    
	        	      if ( strUsr.equals(ControladorRota.getInstancia().getDadosGerais().getLogin()) && 
	        	    	(Criptografia.encode(strPass).equals(ControladorRota.getInstancia().getDadosGerais().getSenha())) ){
	        	    	  
	        	    	  ControladorRota.getInstancia().setPermissionGranted(true);
	        	    	  removeDialog(Constantes.DIALOG_ID_PASSWORD);
	        	    	  
	        	    	  Intent myIntent = new Intent(layout.getContext(), MenuPrincipal.class);
	        	    	  startActivity(myIntent);

	        	      }else {
	        	    	  error.setText(R.string.auth_problem);
	        	      }
	        	}
	        });
	        
	        AlertDialog passwordDialog = builder.create();
	        return passwordDialog;
	        
	    }
	    return null;
	}
	
    @Override
    public void onDestroy(){
    	
    	ControladorRota.getInstancia().finalizeDataManipulator();
    	super.onDestroy();
    	
    }
	
}