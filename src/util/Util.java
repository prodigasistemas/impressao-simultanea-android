package util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import ui.ArquivoRetorno;
import ui.MessageDispatcher;

import business.Controlador;
import business.ControladorAcessoOnline;

import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Util {

	static boolean isUpdatingCep;
	static boolean isUpdatingPhone;
	static boolean isUpdatingCpfCnpj;
	static boolean isCpfProprietarioOk = false;
	static boolean isCnpjProprietarioOk = false;
	static boolean isCpfUsuarioOk = false;
	static boolean isCnpjUsuarioOk = false;
	static boolean isCpfResponsavelOk = false;
	static boolean isCnpjResponsavelOk = false;
	static boolean isUpdatingConsulta = false;
	static TextWatcher consultaTextWatcher = null;
	
	
	public static boolean getCpfProprietarioOk(){
		return isCpfProprietarioOk;
	}

	public static boolean getCnpjProprietarioOk(){
		return isCnpjProprietarioOk;
	}

	public static boolean getCpfUsuarioOk(){
		return isCpfUsuarioOk;
	}

	public static boolean getCnpjUsuarioOk(){
		return isCnpjUsuarioOk;
	}

	public static boolean getCpfResponsavelOk(){
		return isCpfResponsavelOk;
	}

	public static boolean getCnpjResponsavelOk(){
		return isCnpjResponsavelOk;
	}

	// Define a variavel editText para tratar os eventos de textChanged considerando mascara para CEP.
	public static void addTextChangedListenerCepMask(final EditText edt){
    	edt.addTextChangedListener(new TextWatcher() {  
    	    
    		
    		public void beforeTextChanged(CharSequence s, int start, int count, int after) {  
    	    }  
    	      
    		
    	    public void onTextChanged(CharSequence s, int start, int before, int after) {  
    	      
    			// Quando o texto é alterado o onTextChange é chamado. Essa flag evita a chamada infinita desse método  
    			if (isUpdatingCep){
    				isUpdatingCep = false;  
    				return;  
    			}  
    	      
    			boolean hasMask = s.toString().indexOf('-') > -1;  
    	      
    			// Remove o '-' da String  
    			String str = s.toString().replaceAll("[-]", "");  
    	      
    			if (after > before) {  

    				// Se tem mais de 5 caracteres (sem máscara) coloca o '-'  
    				if (str.length() > 5) {  
    					str = str.substring(0,5) + '-' + str.substring(5);  
    				}  
    				
    				// Seta a flag pra evitar chamada infinita  
    				isUpdatingCep = true;  
    				
    				// seta o novo texto  
    				edt.setText(str);  
    				
    				// seta a posição do cursor  
    				if(start == 5){
        				edt.setSelection(start + 2);  
    				}else{
        				edt.setSelection(start + 1);  
    				}
    	      
    			} else {  
    				isUpdatingCep = true;  
    				
    				if (str.length() > 5){
    					str = str.substring(0,5) + '-' + str.substring(5);
    				}else{
        				edt.setText(str);  
    				}
    				
    				// Se estiver apagando posiciona o cursor no local correto. Isso trata a deleção dos caracteres da máscara.  
    				edt.setSelection(Math.max(0, Math.min(hasMask ? start + 1 - before : start, str.length() ) ) );  
    			}  
    		}  
    	         
    		
    	    public void afterTextChanged(Editable s) {  
    	    }  
        });
		
	}

	// Define a variavel editText para tratar os eventos de textChanged considerando mascara para Telefone.
	public static void addTextChangedListenerPhoneMask(final EditText edt){
    	edt.addTextChangedListener(new TextWatcher() {  
    	    
    		
    		public void beforeTextChanged(CharSequence s, int start, int count, int after) {  
    	    }  
    	      
    		
    	    public void onTextChanged(CharSequence s, int start, int before, int after) {  
    	      
    			// Quando o texto é alterado o onTextChange é chamado. Essa flag evita a chamada infinita desse método  
    			if (isUpdatingPhone){
    				isUpdatingPhone = false;  
    				return;  
    			}  
    	      
    			// Remove o '-' da String  
    			String str = s.toString().replaceAll("[-]", "").replaceAll("[(]", "").replaceAll("[)]", "");  
    	      
    			if (after > before) {  

    				str = '(' + str;  
    				
    				if (str.length() > 3) {  
    					str = str.substring(0,3) + ')' + str.substring(3);  
    				}  
    				
    				if (str.length() > 8) {  
    					str = str.substring(0,8) + '-' + str.substring(8);  
    				}  
    				
    				// Seta a flag pra evitar chamada infinita  
    				isUpdatingPhone = true;  
    				
    				// seta o novo texto  
    				edt.setText(str);  
    				
    				// seta a posição do cursor  
    				if(start == 0 || start == 3 || start == 8 ){
        				edt.setSelection(start + 2);  
    				}else{
        				edt.setSelection(start + 1);  
    				}
    	      
    			} else {  
    				isUpdatingPhone = true;  
    				
    				if(str.length() > 0){
        				str = '(' + str;
    				}
    				
    				if (str.length() > 3) {  
    					str = str.substring(0,3) + ')' + str.substring(3);  
    				}

    				if (str.length() > 8) {  
    					str = str.substring(0,8) + '-' + str.substring(8);  
    				}

    				edt.setText(str);  
    				
    				// Se estiver apagando posiciona o cursor no local correto. Isso trata a deleção dos caracteres da máscara.  
    				edt.setSelection(Math.max(0, Math.min(start + 1 - before, str.length() ) ) ); 
    			}  
    		}  
    	         
    		
    	    public void afterTextChanged(Editable s) {  
    	    }  
        });
	}
	
	// Define a variável editText para tratar os eventos de textChanged de Consulta.
	public static void addTextChangedListenerConsultaVerifierAndMask(final EditText edt, final int metodoBusca){
		
		if (consultaTextWatcher != null){
			edt.removeTextChangedListener(consultaTextWatcher);
		}
		
		consultaTextWatcher = new TextWatcher(){  
    	    
    		
    		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}  
    	      
    		
    	    public void onTextChanged(CharSequence s, int start, int before, int after) {  
    	         
    			// Quando o texto é alterado o onTextChange é chamado. Essa flag evita a chamada infinita desse método  
    			if (isUpdatingConsulta){
    				isUpdatingConsulta = false;  
    				return;  
    			}  
    	      
				// Se for CPF
    			if (metodoBusca == Constantes.METODO_BUSCA_CPF){
	    			
					// Remove o '-'  e o '.'da String  
	    			String str = s.toString().replaceAll("[-]", "").replaceAll("[.]", "");  
	    	      
	    			if (after > before && before < 12) {  

	    				if (str.length() > 3) {  
	    					str = str.substring(0,3) + '.' + str.substring(3);  
	    				}  
	    				
	    				if (str.length() > 7) {  
	    					str = str.substring(0,7) + '.' + str.substring(7);  
	    				}  
	    				
	    				if (str.length() > 11) {  
	    					str = str.substring(0,11) + '-' + str.substring(11);  
	    				}  
	    				
	    				if (str.length() > 14) {  
	    					str = str.substring(0,14);  
	    				}
	    				
	    				// Seta a flag pra evitar chamada infinita  
	    				isUpdatingConsulta = true;  
	    				
	    				// seta o novo texto  
	    				edt.setText(str);  
	    				
	    				// seta a posição do cursor  
	    				if(start == 3 || start == 7 || start == 11 ){
	        				edt.setSelection(start + 2);  
	    				}else if (start == 14){
	        				edt.setSelection(start);  
	    				}else{
	        				edt.setSelection(start + 1);  
	    				}
	    	      
	    			} else {  
	    				isUpdatingConsulta = true;  
	    				
	    				if (str.length() > 3) {  
	    					str = str.substring(0,3) + '.' + str.substring(3);  
	    				}

	    				if (str.length() > 7) {  
	    					str = str.substring(0,7) + '.' + str.substring(7);  
	    				}

	    				if (str.length() > 11) {  
	    					str = str.substring(0,11) + '-' + str.substring(11);  
	    				}

	    				if (str.length() > 18) {  
	    					str = str.substring(0,18);  
	    				}
	    				
	    				edt.setText(str);  
	    				
	    				// Se estiver apagando posiciona o cursor no local correto. Isso trata a deleção dos caracteres da máscara.  
	    				edt.setSelection(Math.max(0, Math.min(start + 1 - before, str.length() ) ) ); 
	    			}  
				// Se for CNPJ
    			}else if (metodoBusca == Constantes.METODO_BUSCA_CNPJ){
					
	    			// Remove o '-'  e '.'da String  
	    			String str = s.toString().replaceAll("[-]", "").replaceAll("[.]", "").replaceAll("[/]", "");  

	    			if (after > before && before < 18) {  

	    				if (str.length() > 2) {  
	    					str = str.substring(0,2) + '.' + str.substring(2);  
	    				}  
	    				
	    				if (str.length() > 6) {  
	    					str = str.substring(0,6) + '.' + str.substring(6);  
	    				}  
	    				
	    				if (str.length() > 10) {  
	    					str = str.substring(0,10) + '/' + str.substring(10);  
	    				}  
	    				
	    				if (str.length() > 15) {  
	    					str = str.substring(0,15) + '-' + str.substring(15);  
	    				}  
	    				
	    				// Seta a flag pra evitar chamada infinita  
	    				isUpdatingConsulta = true;  
	    				
	    				// seta o novo texto  
	    				edt.setText(str);  
	    				
	    				// seta a posição do cursor  
	    				if(start == 2 || start == 6 || start == 10 || start == 15 ){
	        				edt.setSelection(start + 2);  
	    				}else{
	        				edt.setSelection(start + 1);  
	    				}
	    	      
	    			} else {  
	    				isUpdatingConsulta = true;  
	    				
	    				if (str.length() > 2) {  
	    					str = str.substring(0,2) + '.' + str.substring(2);  
	    				}

	    				if (str.length() > 6) {  
	    					str = str.substring(0,6) + '.' + str.substring(6);  
	    				}

	    				if (str.length() > 10) {  
	    					str = str.substring(0,10) + '/' + str.substring(10);  
	    				}

	    				if (str.length() > 15) {  
	    					str = str.substring(0,15) + '-' + str.substring(15);  
	    				}

	    				edt.setText(str);  
	    				
	    				// Se estiver apagando posiciona o cursor no local correto. Isso trata a deleção dos caracteres da máscara.  
	    				edt.setSelection(Math.max(0, Math.min(start + 1 - before, str.length() ) ) ); 
	    			}  
				}
    		}

			
    	    public void afterTextChanged(Editable s) {}  
        };
		
		edt.addTextChangedListener(consultaTextWatcher);
	}
		
    /**
     * Verifica se o valor da String.trim() veio como null ou como
     * Constantes.NULO_DOUBLE, setando como Constantes.NULO_DOUBLE caso
     * verdadeiro
     * 
     * @param valor
     * @return
     */
    public static double verificarNuloDouble(String valor) {
		if (valor == null || valor.trim().equals(Constantes.NULO_STRING)) {
		    return Constantes.NULO_DOUBLE;
		} else {
		    return Double.parseDouble(valor.trim());
		}
    }

    /**
     * Verifica se o valor da String.trim() veio como null ou como
     * Constantes.NULO_STRING, setando como Constantes.NULO_STRING caso
     * verdadadeiro
     * 
     * @param valor
     * @return
     */
    public static String verificarNuloString(String valor) {
		if (valor == null || valor.trim().equals(Constantes.NULO_STRING) || valor.trim().equals("null")) {
		    return Constantes.NULO_STRING;
		} else {
		    return valor.trim();
		}
    }

    /**
     * Verifica se o valor da String.trim() veio como null ou como
     * Constantes.NULO_INT, setando como Constantes.NULO_INT caso
     * verdadadeiro
     * 
     * @param valor
     * @return
     */
    public static int verificarNuloInt(String valor) {
		if (valor == null || valor.trim().equals(Constantes.NULO_STRING)) {
		    return Constantes.NULO_INT;
		} else {
		    return Integer.parseInt(valor.trim());
		}
    }

    /**
     * Verifica se o valor da String.trim() veio como null ou como
     * Constantes.NULO_INT, setando como Constantes.NULO_INT caso
     * verdadadeiro
     * 
     * @param valor
     * @return
     */
    public static long verificarNuloLong(String valor) {
		if (valor == null || valor.trim().equals(Constantes.NULO_STRING)) {
		    return Constantes.NULO_INT;
		} else {
		    return Long.parseLong(valor.trim());
		}
    }

    /**
     * Verifica se o valor da String.trim() veio como null ou como
     * Constantes.NULO_STRING, setando como Constantes.NULO_INT caso
     * verdadadeiro
     * 
     * @param valor
     * @return
     */
    public static short verificarNuloShort(String valor) {
		if (valor == null || valor.trim().equals(Constantes.NULO_STRING)) {
		    return Constantes.NULO_SHORT;
		} else {
		    return Short.parseShort(valor.trim());
		}
    }

    public static Date getData(String data) {

    	if (data.equals(Constantes.NULO_STRING)) {
    	    return null;
    	} else {
    	    Calendar calendario = Calendar.getInstance();
    	    calendario.set(Calendar.YEAR, Integer.valueOf(data.substring(0, 4)).intValue());
    	    calendario.set(Calendar.MONTH, Integer.valueOf(data.substring(4, 6)).intValue() - 1);
    	    calendario.set(Calendar.DAY_OF_MONTH, Integer.valueOf(data.substring(6, 8)).intValue());
    	    calendario.set(Calendar.HOUR, 0);
    	    calendario.set(Calendar.HOUR_OF_DAY, 0);
    	    calendario.set(Calendar.MINUTE, 0);
    	    calendario.set(Calendar.SECOND, 0);
    	    calendario.set(Calendar.MILLISECOND, 0);

    	    return new Date(calendario.getTime().getTime());
    	}
	}

    /**
     * < <Descrição do método>>
     * 
     * @param data
     *            Descrição do parâmetro
     * @return Descrição do retorno
     */
    public static String formatarData(Date data) {
		StringBuffer dataBD = new StringBuffer();
	
		if (data != null) {
		    Calendar dataCalendar = Calendar.getInstance();
		    dataCalendar.setTime(data);
	
		    dataBD.append(dataCalendar.get(Calendar.YEAR) + "-");
	
		    // Obs.: Janeiro no Calendar é mês zero
		    if ((dataCalendar.get(Calendar.MONTH) + 1) > 9) {
				dataBD.append(dataCalendar.get(Calendar.MONTH) + 1 + "-");
		    } else {
		    	dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1) + "-");
		    }
	
		    if (dataCalendar.get(Calendar.DAY_OF_MONTH) > 9) {
		    	dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH));
		    } else {
		    	dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH));
		    }
	
		    dataBD.append(" ");
	
		    if (dataCalendar.get(Calendar.HOUR_OF_DAY) > 9) {
		    	dataBD.append(dataCalendar.get(Calendar.HOUR_OF_DAY));
		    } else {
		    	dataBD.append("0" + dataCalendar.get(Calendar.HOUR_OF_DAY));
		    }
	
		    dataBD.append(":");
	
		    if (dataCalendar.get(Calendar.MINUTE) > 9) {
		    	dataBD.append(dataCalendar.get(Calendar.MINUTE));
		    } else {
		    	dataBD.append("0" + dataCalendar.get(Calendar.MINUTE));
		    }
	
		    dataBD.append(":");
	
		    if (dataCalendar.get(Calendar.SECOND) > 9) {
		    	dataBD.append(dataCalendar.get(Calendar.SECOND));
		    } else {
		    	dataBD.append("0" + dataCalendar.get(Calendar.SECOND));
		    }
	
		    dataBD.append(".");
	
		    dataBD.append(Util.adicionarZerosEsquerdaNumero(6, dataCalendar.get(Calendar.MILLISECOND) + ""));
		}
	
		return dataBD.toString();
    }

    /**
     * Adiciona zeros a esqueda do número informado tamamho máximo campo 6
     * Número 16 retorna 000016
     * 
     * @param tamanhoMaximoCampo
     *            Descrição do parâmetro
     * @param numero
     *            Descrição do parâmetro
     * @return Descrição do retorno
     */
    public static String adicionarZerosEsquerdaNumero(int tamanhoMaximoCampo, String numero) {
		String zeros = "";
		String retorno = null;
	
		boolean ehNegativo = numero != null && !numero.equals(Constantes.NULO_STRING) && !numero.equals(Constantes.NULO_DOUBLE + "") && !numero.equals(Constantes.NULO_INT + "") && !numero.equals(Constantes.NULO_SHORT + "") && numero.charAt(0) == '-';
	
		if (ehNegativo) {
		    numero = numero.substring(1);
		}
	
		if (numero != null && !numero.equals("") && !numero.equals(Constantes.NULO_INT + "")) {
		    for (int a = 0; a < (tamanhoMaximoCampo - numero.length()); a++) {
		    	zeros = zeros.concat("0");
		    }
		    // concatena os zeros ao numero
		    // caso o numero seja diferente de nulo
		    retorno = zeros.concat(numero);
		} else {
		    for (int a = 0; a < tamanhoMaximoCampo; a++) {
		    	zeros = zeros.concat("0");
		    }
		    // retorna os zeros
		    // caso o numero seja nulo
		    retorno = zeros;
		}
	
		if (ehNegativo) {
		    retorno = "-" + retorno.substring(1);
		}
		return retorno;
    }

    /**
     * Adiciona zeros a direita do número informado tamamho máximo campo 6
     * Número 16 retorna 000016
     * 
     * @param tamanhoMaximoCampo
     *            Descrição do parâmetro
     * @param numero
     *            Descrição do parâmetro
     * @return Descrição do retorno
     */
    public static String adicionarZerosDireitaNumero(int tamanhoMaximoCampo, String numero) {
		String retorno = "";
		String zeros = "";
	
		for (int i = 0; i < (tamanhoMaximoCampo - numero.length()); i++) {
		    zeros += "0";
		}
	
		retorno += numero + zeros;
		return retorno;
    }

    /**
     * Adiciona char " " a esqueda da String informada número 16 retorna 000016
     * 
     * @param tamanhoMaximoCampo
     *            Descrição do parâmetro
     * @param numero
     *            Descrição do parâmetro
     * @return Descrição do retorno
     */
    public static String adicionarCharEsquerda(int tamanhoMaximoCampo, String string, char c) {
		String repetido = "";
		String retorno = null;
	
		if (string != null && !string.equals("") && !string.equals(Constantes.NULO_INT + "")) {
		    for (int a = 0; a < (tamanhoMaximoCampo - string.length()); a++) {
		    	repetido = repetido.concat(c + "");
		    }
		    // concatena os zeros ao numero
		    // caso o numero seja diferente de nulo
		    retorno = repetido.concat(string);
		} else {
		    for (int a = 0; a < tamanhoMaximoCampo; a++) {
		    	repetido = repetido.concat(c + "");
		    }
		    // retorna os zeros caso o numero seja nulo
		    retorno = repetido;
		}
		return retorno;
    }

    /**
     * Adiciona caracteres a direita da string informada tamamho máximo campo 1 = 15
     * Palavra  "apagar" retorna "apagar         "
     * 
     * @param tamanhoMaximoCampo
     *            Descrição do parâmetro
     * @param numero
     *            Descrição do parâmetro
     * @return Descrição do retorno
     */
    public static String adicionarCharDireita(int tamanhoMaximoCampo, String string, char c) {
		String retorno = new String();
	
		if (string != null && !string.equals("") && !string.equals(Constantes.NULO_INT + "")) {
			retorno = string;
			for (int a = 0; a < (tamanhoMaximoCampo - string.length()); a++) {
				retorno = retorno.concat(c + "");
		    }
		} else {
		    for (int a = 0; a < tamanhoMaximoCampo; a++) {
		    	retorno = retorno.concat(c + "");
		    }
		}
		return retorno;
    }

    public static String cleanStringForFileNameFormat(String fileName){
    	return fileName.trim().replace(' ', '_').replaceAll("[/]", "").replaceAll("[-]", "").replaceAll("[.]", "").replaceAll("[,]", "").replaceAll("[:]", "")
    	.replaceAll("[;]", "").replaceAll("[(]", "").replaceAll("[)]", "");
    }

    public static String getRetornoRotaDirectory(){
    	String diretorioRetornoRota = null;
    	
    	Controlador.getInstancia().getCadastroDataManipulator().selectGeral();

    	diretorioRetornoRota =  Controlador.getInstancia().getDadosGerais().getLocalidade() + "_";
    	diretorioRetornoRota += Controlador.getInstancia().getDadosGerais().getSetor() + "_";
    	diretorioRetornoRota += Controlador.getInstancia().getDadosGerais().getRota() + "_";
    	diretorioRetornoRota += Controlador.getInstancia().getDadosGerais().getAnoMesFaturamento();
    	
        File fileRotaDiretorio = new File(Environment.getExternalStorageDirectory() + Constantes.DIRETORIO_RETORNO, diretorioRetornoRota);
        if(!fileRotaDiretorio.exists()) {
        	fileRotaDiretorio.mkdirs();
        }

    	return fileRotaDiretorio.getAbsolutePath();
    }
 
    public static String getRotaFileName(){
    	String rotaFileName = null;
    	
    	Controlador.getInstancia().getCadastroDataManipulator().selectGeral();
    	
    	rotaFileName =  Controlador.getInstancia().getDadosGerais().getLocalidade() + "_";
    	rotaFileName += Controlador.getInstancia().getDadosGerais().getSetor() + "_";
    	rotaFileName += Controlador.getInstancia().getDadosGerais().getRota() + "_";
    	rotaFileName += Controlador.getInstancia().getDadosGerais().getAnoMesFaturamento() + ".txt";
    	
    	return rotaFileName;
    }
    
    /**
     * Método responsável por transformar um vetor de parâmetros em uma mensagem
     * um array de bytes.
     * 
     * @param parameters
     *            Vetor de parâmetros.
     * @return O array de bytes com os parâmetros empacotados.
     */
    public static byte[] empacotarParametros(Vector parametros) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
	
		byte[] resposta = null;
	
		parametros.trimToSize();
	
		try {

			// escreve os dados no OutputStream
		    if (parametros != null) {
				int tamanho = parametros.size();
				
				for (int i = 0; i < tamanho; i++) {
				    
					Object param = parametros.elementAt(i);
	
					if (param instanceof Byte) {
						dos.writeByte(((Byte) param).byteValue());
				    
					} else if (param instanceof Integer) {
						dos.writeInt(((Integer) param).intValue());
				    
					} else if (param instanceof Long) {
						dos.writeLong(((Long) param).longValue());
				    
					} else if (param instanceof String) {
						dos.writeUTF((String) param);
				    
					} else if (param instanceof byte[]) {
						dos.write((byte[]) param);
				    }
				}
		    }
	
		    // pega os dados enpacotados
		    resposta = baos.toByteArray();
	
		    if (dos != null) {
				dos.close();
				dos = null;
		    }

		    if (baos != null) {
				baos.close();
				baos = null;
		    }
	
		} catch (IOException e) {
		    e.printStackTrace();
		
		} catch (Exception e) {
		    e.printStackTrace();
		}
	
		// retorna o array de bytes
	return resposta;
    }

}
