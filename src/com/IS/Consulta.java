package com.IS;

import java.util.ArrayList;

import util.Constantes;
import util.Util;

import business.ControladorImoveis;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Consulta extends ListActivity {
	
	MySimpleArrayAdapter enderecoList;
	ArrayList<String> listStatusImoveis;
	Spinner spinnerMetodoBusca;
	Spinner spinnerFiltro;
	String filterCondition = null;
	String searchCondition = null;
	static int metodoBusca = Constantes.METODO_BUSCA_TODOS;
	static int filtroBusca = Constantes.FILTRO_BUSCA_TODOS;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.consulta);
 
    	metodoBusca = 0;
    	filtroBusca = 0;
    	
    }
    
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);//must store the new intent unless getIntent() will return the old one.
//		loadEnderecoImoveis();
	}

    private void loadEnderecoImoveis(){
    }
    
	@Override
	protected void onListItemClick(ListView l, View view, int position, long id) {
		// user clicked a list item, make it "selected"
		enderecoList.setSelectedPosition(position);

		ControladorImoveis.getInstancia().setImovelSelecionadoByListPositionInConsulta(position, filterCondition);
//		Intent myIntent = new Intent(getApplicationContext(), MainTab.class);
//		startActivityForResult(myIntent, 0);
	}
	
	
	public class MySimpleArrayAdapter extends ArrayAdapter<String> {
		private final Activity context;
		private final ArrayList<String> names;

		// used to keep selected position in ListView
		private int selectedPos = -1;

		public MySimpleArrayAdapter(Activity context, ArrayList<String> names) {
			super(context, R.layout.rowimovel, names);
			this.context = context;
			this.names = names;
		}

		public void setSelectedPosition(int pos){
			selectedPos = pos;
			// inform the view of this change
			notifyDataSetChanged();
		}

		public int getSelectedPosition(){
			return selectedPos;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = context.getLayoutInflater();
			View rowView = inflater.inflate(R.layout.rowimovel, null, true);

	        // change the row color based on selected state
	        if(selectedPos == position){
	        	rowView.setBackgroundColor(Color.GRAY);
	        }else{
	        	rowView.setBackgroundColor(Color.TRANSPARENT);
	        }
	        
	        ((TextView)rowView.findViewById(R.id.nomerota)).setText(names.get(position));

			ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
			
			if ( Integer.parseInt(listStatusImoveis.get(position)) == Constantes.IMOVEL_PENDENTE ){
				imageView.setImageResource(R.drawable.todo);
			
			} else if ( Integer.parseInt(listStatusImoveis.get(position)) == Constantes.IMOVEL_CONCLUIDO){
				imageView.setImageResource(R.drawable.done);
			
			} else if ( Integer.parseInt(listStatusImoveis.get(position)) == Constantes.IMOVEL_CONCLUIDO_COM_ANORMALIDADE ){
				imageView.setImageResource(R.drawable.done_anormal);
			}

			return rowView;
		}
		
		public String getListElementName(int element){
			return names.get(element);
		}
	}
	
	public void metodoBuscaOnItemSelectedListener (Spinner spinnerMetodoBusca){

		spinnerMetodoBusca.setOnItemSelectedListener(new OnItemSelectedListener () {
        	
    		public void onItemSelected(AdapterView parent, View v, int position, long id){
        		metodoBusca = position;
        		
        	}
    		
    		public void onNothingSelected(AdapterView<?> arg0) {}
    	});
	}	
	
	public void filtroBuscaOnItemSelectedListener (Spinner spinnerFiltro){

		spinnerFiltro.setOnItemSelectedListener(new OnItemSelectedListener () {
        	
    		public void onItemSelected(AdapterView parent, View v, int position, long id){
        		filtroBusca = position;
        	}
    		
    		public void onNothingSelected(AdapterView<?> arg0) {}
    	});
	}	
	
}