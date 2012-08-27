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
import android.util.Log;
import android.widget.EditText;

public class Util {

	static TextWatcher consultaTextWatcher = null;
			
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

//    	diretorioRetornoRota =  Controlador.getInstancia().getDadosGerais().getLocalidade() + "_";
//    	diretorioRetornoRota += Controlador.getInstancia().getDadosGerais().getSetor() + "_";
//    	diretorioRetornoRota += Controlador.getInstancia().getDadosGerais().getRota() + "_";
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
    	
//    	rotaFileName =  Controlador.getInstancia().getDadosGerais().getLocalidade() + "_";
//    	rotaFileName += Controlador.getInstancia().getDadosGerais().getSetor() + "_";
//    	rotaFileName += Controlador.getInstancia().getDadosGerais().getRota() + "_";
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

    /**
     * Troca letrar por numeros, na seguinte sequencia: A - 1 B - 2 C - 3 D - 4
     * . . . Z - 26
     * 
     * @param string
     *            string a ser convertida
     * @return long com a representacao
     */
    public static double convertendoLetraParaNumeros(String string) {

		char[] hidrometro = string.toUpperCase().toCharArray();
		String retorno = "";
	
		for (int i = 0; i < hidrometro.length; i++) {
	
		    if (hidrometro[i] == 'A') {
			retorno += "1";
		    } else if (hidrometro[i] == 'B') {
			retorno += "2";
		    } else if (hidrometro[i] == 'C') {
			retorno += "3";
		    } else if (hidrometro[i] == 'D') {
			retorno += "4";
		    } else if (hidrometro[i] == 'E') {
			retorno += "5";
		    } else if (hidrometro[i] == 'F') {
			retorno += "6";
		    } else if (hidrometro[i] == 'G') {
			retorno += "7";
		    } else if (hidrometro[i] == 'H') {
			retorno += "9";
		    } else if (hidrometro[i] == 'I') {
			retorno += "9";
		    } else if (hidrometro[i] == 'J') {
			retorno += "10";
		    } else if (hidrometro[i] == 'K') {
			retorno += "11";
		    } else if (hidrometro[i] == 'L') {
			retorno += "12";
		    } else if (hidrometro[i] == 'M') {
			retorno += "13";
		    } else if (hidrometro[i] == 'N') {
			retorno += "14";
		    } else if (hidrometro[i] == 'O') {
			retorno += "15";
		    } else if (hidrometro[i] == 'P') {
			retorno += "16";
		    } else if (hidrometro[i] == 'Q') {
			retorno += "17";
		    } else if (hidrometro[i] == 'R') {
			retorno += "18";
		    } else if (hidrometro[i] == 'S') {
			retorno += "19";
		    } else if (hidrometro[i] == 'T') {
			retorno += "20";
		    } else if (hidrometro[i] == 'U') {
			retorno += "21";
		    } else if (hidrometro[i] == 'V') {
			retorno += "22";
		    } else if (hidrometro[i] == 'W') {
			retorno += "23";
		    } else if (hidrometro[i] == 'X') {
			retorno += "24";
		    } else if (hidrometro[i] == 'Y') {
			retorno += "25";
		    } else if (hidrometro[i] == 'Z') {
			retorno += "26";
		    } else if (hidrometro[i] == '\\') {
			retorno += "27";
		    } else if (hidrometro[i] == '/') {
			retorno += "28";
		    } else if (hidrometro[i] == ' ') {
			retorno += "29";
		    } else if (hidrometro[i] == '.') {
			retorno += "30";
		    } else if (hidrometro[i] == ',') {
			retorno += "31";
		    } else if (hidrometro[i] == '#') {
			retorno += "32";
		    } else if (hidrometro[i] == '-') {
			retorno += "33";
		    } else if (hidrometro[i] == ';'){
			retorno += "34";
		    } else if (hidrometro[i] == '`'){
			retorno += "35";		
		    } else if (hidrometro[i] == 'À'){
			retorno += "36";
		    } else if (hidrometro[i] == '='){
			retorno += "37";
		    } else {
			retorno += hidrometro[i];
		    }
		}
	
		double retornoLong = 0d;
		try {
		    if (retorno != null && !retorno.equals("")) {
			retornoLong = Double.parseDouble(retorno);
		    }
		} catch (NumberFormatException e) {
		    e.printStackTrace();
		    System.out.println("Hidrometro caractere: "+ retorno);
//		    Util.perguntarAcao( "Número do Hidrometro com problema: " + string + ". Visualizado ?", false, false );
		    throw e;
		}
	
		return retornoLong;
    }


}
