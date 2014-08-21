package com.IS;

import util.Constantes;
import views.MainTab;
import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;


public class NotifyAlertDialogFragment extends DialogFragment {
	
	public static NotifyAlertDialogFragment newInstance(int iconId, String title, String message, int messageType) {
		NotifyAlertDialogFragment frag = new NotifyAlertDialogFragment();
		Bundle args = new Bundle();
		args.putInt("iconId", iconId);
		args.putString("title", title);
		args.putString("message", message);
		args.putInt("messageType", messageType);
		frag.setArguments(args);
		return frag;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final String title = getArguments().getString("title");
		final String message = getArguments().getString("message");
		final int messageType = getArguments().getInt("messageType");
		final int iconId = getArguments().getInt("iconId");
		
		return new AlertDialog.Builder(getActivity())
		.setIcon(iconId)
		.setMessage(message)
		.setTitle(title)
		.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						
						switch (messageType){
						case Constantes.DIALOG_ID_SUCESSO:
						case Constantes.DIALOG_ID_ERRO:
							break;
						case Constantes.DIALOG_ID_ERRO_GPS_DESLIGADO:
							((MainTab)getActivity()).doGpsDesligado();							
							break;
						}
					}
				}).create();
	}
}