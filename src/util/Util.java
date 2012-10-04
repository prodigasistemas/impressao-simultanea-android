package util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import model.Consumo;
import model.DadosRelatorio;
import android.os.Environment;
import android.text.TextWatcher;
import android.util.Log;
import business.ControladorRota;

public class Util {

	static TextWatcher consultaTextWatcher = null;
	static boolean isUpdatingCep;
			
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
    	
    	List<String> info = ControladorRota.getInstancia().getDataManipulator().selectInformacoesRota();
    	
    	diretorioRetornoRota =  info.get(1) + "_";
    	diretorioRetornoRota +=info.get(2) + "_";
    	diretorioRetornoRota += info.get(3) + "_";
    	diretorioRetornoRota += info.get(4).substring(4, 6) + "/" + info.get(4).substring(0, 4);
    	
        File fileRotaDiretorio = new File(Environment.getExternalStorageDirectory() + Constantes.DIRETORIO_RETORNO, diretorioRetornoRota);
        if(!fileRotaDiretorio.exists()) {
        	fileRotaDiretorio.mkdirs();
        }

    	return fileRotaDiretorio.getAbsolutePath();
    }
 
    public static String getRotaFileName(){
    	String rotaFileName = null;
    	
    	List<String> info = ControladorRota.getInstancia().getDataManipulator().selectInformacoesRota();
    	
    	rotaFileName =  info.get(1) + "_";
    	rotaFileName +=info.get(2) + "_";
    	rotaFileName += info.get(3) + "_";
    	rotaFileName += info.get(4)+ ".txt";
    	
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
     * Author: Pedro Alexandre Data: 08/01/2006 Adiciona nº de dias para uma
     * data
     * 
     * @param numeroDias
     * @param data
     * @return data menos o nº de dias informado
     */
    public static Date adicionarNumeroDiasDeUmaData(Date data, long numeroDias) {
		Date dataAlterada = new Date(data.getTime());
	
		numeroDias = numeroDias * 86400000;
		// seta a data
		dataAlterada.setTime(dataAlterada.getTime() + (numeroDias));
	
		// retorna a nova data
		return dataAlterada;
    }

    public static String formatarCodigoBarras(String codigoBarras) {
		String retorno = "";
		// return "82600000010-7 63620006190-2 01531394000-7 08200920003-9";
		if (codigoBarras != null && codigoBarras.length() >= 48) {
		    retorno = codigoBarras.substring(0, 11).trim() + "-" + codigoBarras.substring(11, 12).trim() + " " + codigoBarras.substring(12, 23).trim() + "-" + codigoBarras.substring(23, 24).trim() + " " + codigoBarras.substring(24, 35).trim() + "-" + codigoBarras.substring(35, 36).trim() + " "
			    + codigoBarras.substring(36, 47).trim() + "-" + codigoBarras.substring(47, 48);
		}
	
		return retorno;
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

    public static double strToDouble(String str) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(str);
		buffer.insert(str.length() - 2, '.');
	
		return Double.valueOf(buffer.toString()).doubleValue();
    }
	
    public static int pow(int arg, int times) {
		int ret = 1;
		for (int i = 1; i <= times; i++) {
		    ret = ret * arg;
		}
		return ret;
    }

    /**
     * Arredonda as casas decimais.
     * 
     * @param valor
     *            O valor a ser arrendondado.
     * @param casasDecimais
     *            O número de casas decimais.
     * @return O valor arredondado. Ex.: arredondar(abcd.xyzw, 3) = abcd.xyz,
     *         para w <5 = abcd.xyk, para w >= 5 e k = z + 1
     */
    public static double arredondar(double valor, int casasDecimais) {

		int mult = pow(10, casasDecimais);
		valor = (double) valor * mult;
		int inteiro = (int) Math.floor(valor + 0.5);
		valor = (double) inteiro / mult;
		return valor;
    }
    
    /**
     * Recebe um número double que será arredondado para um inteiro, levando o
     * seguinte criterio: - Se a parte fracionada do numero for superior a 0,5
     * adicionamos 1 ao inteiro retornado, senão, retornamos apenas a parte
     * inteira sem o incremento
     * 
     * @author Bruno / Sávio
     * @date 09/12/2009
     * @param numero
     *            Número double para ser convertido para inteiro
     * @return
     */
    public static int arredondar(double numero) {
		int inteiro = (int) numero;
		double fracionado = numero - inteiro;
	
		if (fracionado >= .5) {
		    ++inteiro;
		}
	
		return inteiro;
    }

    
    /**
     * 
     * @author Daniel Zaccarias
     * @return Data atual
     */
    public static Date dataAtual() {
      	 return new Date( (new Date()).getTime());
    }

    public static String getDataHora() {
    	//recupera data e hora atual do sistema
    	Calendar calendario = Calendar.getInstance();
    	Date date = new Date((new Date()).getTime());
    	calendario.setTime(date);
    	String mes = String.valueOf(calendario.get(Calendar.MONTH) + 1);
    	String dia = String.valueOf(calendario.get(Calendar.DAY_OF_MONTH));
    	String ano = String.valueOf(calendario.get(Calendar.YEAR));
    	String horas = String.valueOf(calendario.get(Calendar.HOUR_OF_DAY));
    	String minutos = String.valueOf(calendario.get(Calendar.MINUTE));
    	String segundos = String.valueOf(calendario.get(Calendar.SECOND));
    	//
    	calendario = null;
    	date = null;
    	//
    	//formata a data de modo que o tamanho do resultado seja sempre fixo
    	//dia
    	if (dia.length() < 2) { dia = "0" + dia; } //mes
    	if (mes.length() < 2) { mes = "0" + mes; } //horas
    	if (horas.length() < 2) { horas = "0" + horas; } //minutos
    	if (minutos.length() < 2) { minutos = "0" + minutos; } //segundos
    	if (segundos.length() < 2) { segundos = "0" + segundos; } //
    	return dia + "/" + mes + "/" + ano + " " + horas + ":" + minutos + ":" + segundos;
    } 
    
    /**
     * Diferença entre datas em dias
     * 
     * @param data1
     * @param data2
     * @return
     */
    public static long obterModuloDiferencasDatasDias(Date data1, Date data2) {
		long dias;
		long umDia = 86400000; // um dia possui 86400000ms
		long date1, date2;
		date1 = data1.getTime();
		date2 = data2.getTime();
		dias = (date2 - date1) / umDia;
		return (dias < 0) ? dias * -1 : dias;
    }

    /**
     * Diferença entre datas em dias
     * 
     * @param data1
     * @param data2
     * @return
     */
    public static long obterDiferencasDatasDias(Date data1, Date data2) {
		long dias;
		long umDia = 86400000; // um dia possui 86400000ms
		long date1, date2;
		date1 = data1.getTime();
		date2 = data2.getTime();
		dias = (date2 - date1) / umDia;
		return dias;
    }

    /**
     * Subtrai a data no formato AAAAMM Exemplo 200508 retorna 200507 Author:
     * Sávio Luiz Data: 20/01/2006
     * 
     * @param data
     *            com a barra
     * @return Uma data no formato yyyyMM (sem a barra)
     */
    public static int subtrairMesDoAnoMes(int anoMes, int qtdMeses) {

		String dataFormatacao = "" + anoMes;
	
		int ano = Integer.parseInt(dataFormatacao.substring(0, 4));
		int mes = Integer.parseInt(dataFormatacao.substring(4, 6));
	
		int mesTemp = (mes - qtdMeses);
	
		if (mesTemp <= 0) {
		    mesTemp = (12 + mes) - qtdMeses;
		    ano = ano - 1;
		}
	
		String anoMesSubtraido = null;
		String tamanhoMes = "" + mesTemp;
	
		if (tamanhoMes.length() == 1) {
		    anoMesSubtraido = ano + "0" + mesTemp;
		
		} else {
		    anoMesSubtraido = ano + "" + mesTemp;
		}
		
		return Integer.parseInt(anoMesSubtraido);
    }

    public static int obterMes(int anoMes) {

		String dataFormatacao = "" + anoMes;
	
		int mes = Integer.parseInt(dataFormatacao.substring(4, 6));
	
		return mes;
    }

    public static int obterAno(int anoMes) {

		String dataFormatacao = "" + anoMes;
	
		int ano = Integer.parseInt(dataFormatacao.substring(0, 4));
	
		return ano;
    }

    /**
     * Método que recebe um java.util.Date e retorna uma String no formato
     * dia/mês/ano.
     * 
     * @param date
     *            Data a ser formatada.
     * @return String no formato dia/mês/ano.
     */
    public static String dateToString(Date date) {
		StringBuffer retorno = new StringBuffer();
	
		if (date != null && !date.equals("")) {
	
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(date);
		    // Dia
		    retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.DAY_OF_MONTH) + ""));
		    retorno.append("/");
		    // Mes
		    retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.MONTH) + 1 + ""));
		    // Ano
		    retorno.append("/");
		    retorno.append(calendar.get(Calendar.YEAR));
		}
	
		return retorno.toString();
    }

    /**
     * Método que recebe um java.util.Date e retorna uma String no formato
     * dia/mês/ano.
     * 
     * @param date
     *            Data a ser formatada.
     * @return String no formato dia/mês/ano.
     */
    public static String dateToAnoMesDiaString(Date date) {
		StringBuffer retorno = new StringBuffer();
	
		if (date != null && !date.equals("")) {
	
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(date);
		    // Ano
		    retorno.append(calendar.get(Calendar.YEAR));
		    // Mes
		    retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.MONTH) + 1 + ""));
		    // Dia
		    retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.DAY_OF_MONTH) + ""));
		}
	
		return retorno.toString();
    }

    /**
     * Método que recebe um java.util.Date e retorna uma String no formato
     * Hora:Minuto:Segundo.
     * 
     * @param date
     *            Data a ser formatada.
     * @return String no formato Hora:Minuto:Segundo.
     */
    public static String dateToHoraString(Date date) {
		StringBuffer retorno = new StringBuffer();
	
		if (date != null && !date.equals("")) {
	
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(date);
		    // Hora
		    retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.HOUR_OF_DAY) + ""));
		    retorno.append(":");
		    // Minuto
		    retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.MINUTE) + 1 + ""));
		    // Segundo
		    retorno.append(":");
		    retorno.append(Util.adicionarZerosEsquerdaNumero(2, calendar.get(Calendar.SECOND) + ""));
		}
	
		return retorno.toString();
    }

    /**
     * Compara duas datas sem verificar a hora.
     * 
     * @param data1
     *            A primeira data
     * @param data2
     *            A segunda data
     * @author Rafael Francisco Pinto
     * @return -1 se a data1 for menor que a data2, 0 se as datas forem iguais, 1
     *         se a data1 for maior que a data2.
     */
    public static int compararData(Date data1, Date data2) {

		Calendar calendar1;
		Calendar calendar2;
	
		int ano1;
		int ano2;
		int mes1;
		int mes2;
		int dia1;
		int dia2;
	
		int resultado;
	
		calendar1 = Calendar.getInstance();
		calendar1.setTime(data1);
	
		ano1 = calendar1.get(Calendar.YEAR);
		mes1 = calendar1.get(Calendar.MONTH);
		dia1 = calendar1.get(Calendar.DAY_OF_MONTH);
	
		calendar2 = Calendar.getInstance();
		calendar2.setTime(data2);
	
		ano2 = calendar2.get(Calendar.YEAR);
		mes2 = calendar2.get(Calendar.MONTH);
		dia2 = calendar2.get(Calendar.DAY_OF_MONTH);
	
		if (ano1 == ano2) {
	
		    if (mes1 == mes2) {
	
			if (dia1 == dia2) {
			    resultado = 0;
			} else if (dia1 < dia2) {
			    resultado = -1;
			} else {
			    resultado = 1;
			}
		    } else if (mes1 < mes2) {
			resultado = -1;
		    } else {
			resultado = 1;
		    }
		} else if (ano1 < ano2) {
		    resultado = -1;
		} else {
		    resultado = 1;
		}
		return resultado;
    }

    /**
     * Retorna a descrição abreviada do ano Mes
     * 
     * @param anoMes
     * @author Rafael Francisco Pinto
     */

    public static String retornaDescricaoAnoMes(String anoMes) {
    	
    	Log.i("ANoMes", anoMes);

		int mes = Integer.parseInt(anoMes.substring(4, 6));
		String ano = anoMes.substring(0, 4);
	
		String descricao = retornaDescricaoMes(mes) + "/" + ano;
	
		return descricao;
    }

    public static String retornaDescricaoMes(int mes) {

		String meses[] = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" };
	
		String mesPorExtenso = meses[mes - 1];// mes-1 pq o indice do array
		// começa no zero
	
		return mesPorExtenso;
    }

    /**
     * 
     * Divide uma String em várias partes, dependendo
     * do tamanho máximo permitido
     * 
     * @author Bruno Barros
     * @date 25/03/2010
     * @param mensagem Mensagem a ser quebrada
     * @param max máximo de caracteres por linha
     * @return String quebradas por linhas
     * 
     */
    public static String[] dividirString( String mensagem, int max ){
	
		// Encontramos em quantas strings precisaremos dividir
		short qtdLinhas = (short) ( mensagem.length() / max );
		
		// Verificamos se sobrou alguma coisa para a ultima linha
		if ( mensagem.length() % max != 0 ){
		    qtdLinhas++;
		}
		
		String[] retorno = new String[ qtdLinhas ];
		int limiteString = mensagem.length();
		
		for ( int i = 0; i < qtdLinhas; i++ ){
		    int inicio = max*i;
		    int fim = ( max*(i+1) );
		    
		    retorno[i] = mensagem.substring( inicio , ( fim > limiteString ? limiteString : fim )  );
		}
		
		return retorno;	
    }
    
    public static int divideDepoisMultiplica(int numerador, int denominador,int numeroMultiplicado) {

		double resultado = 0;
		double numeradorDouble = numerador;
		double denominadorDouble = denominador;
		
		numeradorDouble = numeradorDouble * 10000;
	
		denominadorDouble = denominadorDouble * 10000;
	
		 resultado = Util.arredondar(numeradorDouble / denominadorDouble, 4);
		
		resultado = resultado * numeroMultiplicado;
	
		return ( int )Util.arredondar(resultado,0);

    }
    
    public static int quantidadeDiasMes(Calendar data){
	
		int mes = data.get(Calendar.MONTH);
		int ano = data.get(Calendar.YEAR);
		int qtdDiasMes = 0;
		
		if(mes == Calendar.JANUARY){
		    qtdDiasMes = 31;
		}
		
		else if(mes == Calendar.FEBRUARY){
		    
		    if(verificarAnoBissexto(ano)){
			qtdDiasMes = 29;
		    }else{
			qtdDiasMes = 28;
		    }
		}
		
		else if(mes == Calendar.MARCH){
		    qtdDiasMes = 31;
		}
		
		else if(mes == Calendar.APRIL){
		    qtdDiasMes = 30;
		}
		
		else if(mes == Calendar.MAY){
		    qtdDiasMes = 31;
		}
		
		else if(mes == Calendar.JUNE){
		    qtdDiasMes = 30;
		}
		
		else if(mes == Calendar.JULY){
		    qtdDiasMes = 31;
		}
		
		else if(mes == Calendar.AUGUST){
		    qtdDiasMes = 30;
		}
		
		else if(mes == Calendar.SEPTEMBER){
		    qtdDiasMes = 31;
		}
		
		else if(mes == Calendar.OCTOBER){
		    qtdDiasMes = 30;
		}
		
		else if(mes == Calendar.NOVEMBER){
		    qtdDiasMes = 31;
		}
		
		else if(mes == Calendar.DECEMBER){
		    qtdDiasMes = 30;
		}
		
		return qtdDiasMes;
    }
    
    public static boolean verificarAnoBissexto(int ano) {

		boolean anoBissexto = false;
		String anoString = String.valueOf(ano);
		int ultimosDigitosAno = Integer.parseInt(anoString.substring(2,
			anoString.length()));
	
		if (ultimosDigitosAno != 00 && ano % 4 == 0) {
		    anoBissexto = true;
		}
	
		if (ultimosDigitosAno == 00 && ano % 400 == 0) {
		    anoBissexto = true;
		}
	
		return anoBissexto;
    }

    public static String validarAnormalidadeConsumo(Consumo consumo) {
    	String mensagemAnormalidade = null;

		if (consumo != null && consumo.getAnormalidadeConsumo() != Constantes.NULO_INT) {
		    int anormalidadeConsumo = consumo.getAnormalidadeConsumo();
		    switch (anormalidadeConsumo) {
		    case Consumo.CONSUMO_ANORM_BAIXO_CONSUMO:
			mensagemAnormalidade = "BAIXO CONSUMO";
			break;
		    case Consumo.CONSUMO_ANORM_ESTOURO:
			mensagemAnormalidade = "ESTOURO DO CONSUMO";
			break;
		    case Consumo.CONSUMO_ANORM_ALTO_CONSUMO:
			mensagemAnormalidade = "ALTO CONSUMO";
			break;
		    case Consumo.CONSUMO_ANORM_LEIT_MENOR_PROJ:
			mensagemAnormalidade = "LEITURA MENOR PROJETADA";
			break;
		    case Consumo.CONSUMO_ANORM_LEIT_MENOR_ANTE:
			mensagemAnormalidade = "LEITURA MENOR ANTERIOR";
			break;
		    case Consumo.CONSUMO_ANORM_HIDR_SUBST_INFO:
			mensagemAnormalidade = "HIDR. SUBSTITUIDO INFORM.";
			break;
		    case Consumo.CONSUMO_ANORM_LEITURA_N_INFO:
			mensagemAnormalidade = "LEITURA NAO INFORMADA";
			break;
		    case Consumo.CONSUMO_ANORM_ESTOURO_MEDIA:
			mensagemAnormalidade = "ESTOURO MEDIA";
			break;
		    case Consumo.CONSUMO_ANORM_FORA_DE_FAIXA:
			mensagemAnormalidade = "FORA DE FAIXA";
			break;
		    case Consumo.CONSUMO_ANORM_HIDR_SUBST_N_INFO:
			mensagemAnormalidade = "HIDR. SUBST. NAO INFORM.";
			break;
		    case Consumo.CONSUMO_ANORM_VIRADA_HIDROMETRO:
			mensagemAnormalidade = "VIRADA DE HIDROMETRO";
			break;
		    }
		}
	
		return mensagemAnormalidade;
    }


    public static void inserirValoresStringRelatorio(String quadra, boolean inseridoAnormalidade, boolean inseridoLeitura){
    	
//    	String valoresRelatorio =  DadosRelatorio.getInstancia().valoresRelatorio;
//    	String quadraAlterada =  null;
//    	int quadraInt = 0;
//    	int total = 0;
//    	int visitados = 0;
//    	int naoVisitados = 0;
//    	
//    	int indice = 0;
//    	if (valoresRelatorio.indexOf(quadra) != -1){
//    		indice = valoresRelatorio.indexOf(quadra);
//    		
//    	}
//    	String quadraAlteracao =  valoresRelatorio.substring(indice, indice + 18);
//    	//String lidosLeitura = valoresRelatorio.substring(valoresRelatorio.indexOf("[" + String.valueOf(valoresRelatorio.charAt(1)) + "]"));
//    	String lidosLeitura = valoresRelatorio.substring(1,5);
//    	//String lidosAnormalidade = valoresRelatorio.substring(valoresRelatorio.indexOf("[" + String.valueOf(valoresRelatorio.charAt(5)) + "]"));
//    	String lidosAnormalidade = valoresRelatorio.substring(7,11);
//    	
//    	if(inseridoAnormalidade){
//    	    int lidos = Integer.parseInt(lidosAnormalidade) + 1;
//    	    valoresRelatorio = replace(valoresRelatorio, "[" + 
//    		    Util.adicionarZerosEsquerdaNumero(4, lidosAnormalidade) + "]", "[" + 
//    		    Util.adicionarZerosEsquerdaNumero(4, String.valueOf(lidos)) + "]");
//    	}
//    	
//    	if(inseridoLeitura){
//    	    int lidos = Integer.parseInt(lidosLeitura) + 1;
//    	    valoresRelatorio = replace(valoresRelatorio, "{" + 
//    		    Util.adicionarZerosEsquerdaNumero(4, lidosLeitura) + "}", "{" + 
//    		    Util.adicionarZerosEsquerdaNumero(4, String.valueOf(lidos)) + "}");
//    	}
//
//    	   quadraInt = Integer.parseInt(valoresRelatorio.substring(indice + 1 ,indice + 5));
//    	   total = Integer.parseInt(valoresRelatorio.substring(indice + 6,indice + 10));
//    	   visitados = Integer.parseInt(valoresRelatorio.substring(indice + 10, indice + 14)) + 1;
//    	   naoVisitados = Integer.parseInt(valoresRelatorio.substring(indice + 14, indice + 18)) - 1;
//    	   
//    	   if(naoVisitados < 0 ){
//    	       naoVisitados = 0;
//    	   }
//    	   
//    	   if(visitados < 0){
//    	       visitados = 0;
//    	   }
//    	   
//    	   quadraAlterada = "(" + Util.adicionarZerosEsquerdaNumero(4, String.valueOf(quadraInt)) + ")" + 
//    	   	Util.adicionarZerosEsquerdaNumero(4, String.valueOf(total)) + 
//    	   	Util.adicionarZerosEsquerdaNumero(4, String.valueOf(visitados)) + 
//    	   	Util.adicionarZerosEsquerdaNumero(4, String.valueOf(naoVisitados));
//    	   
//    	   valoresRelatorio = replace(valoresRelatorio, quadraAlteracao, quadraAlterada);
//    	   
//    	   DadosRelatorio.getInstancia().valoresRelatorio =  valoresRelatorio;
	}
        
    public static void inserirValoresStringRelatorioCarregamento(String quadra, 
    															 String quadraAlteracao, 
    															 boolean inseridoAnormalidade, 
    															 boolean inseridoLeitura){
    	
    	String valoresRelatorio =  DadosRelatorio.getInstancia().valoresRelatorio;
    	String quadraAlterada =  null;
    	int quadraInt = 0;
    	int total = 0;
    	int visitados = 0;
    	int naoVisitados = 0;
    	
    	int indice = valoresRelatorio.indexOf(quadra);
    	quadraInt = Integer.parseInt(valoresRelatorio.substring(indice + 1 ,indice + 5));
    	total = Integer.parseInt(valoresRelatorio.substring(indice + 6,indice + 10)) + 1;	
    	visitados = Integer.parseInt(valoresRelatorio.substring(indice + 10, indice + 14));
    	naoVisitados = Integer.parseInt(valoresRelatorio.substring(indice + 14, indice + 18)) + 1;
 	   
 	   quadraAlterada = "(" + Util.adicionarZerosEsquerdaNumero(4, String.valueOf(quadraInt)) + ")" + 
 			   			Util.adicionarZerosEsquerdaNumero(4, String.valueOf(total)) + 
 			   			Util.adicionarZerosEsquerdaNumero(4, String.valueOf(visitados)) +
 			   			Util.adicionarZerosEsquerdaNumero(4, String.valueOf(naoVisitados));
 	   
 	   valoresRelatorio = replace(valoresRelatorio, quadraAlteracao, quadraAlterada);
 	   
 	   DadosRelatorio.getInstancia().valoresRelatorio = valoresRelatorio;
    }

    public static void inserirValoresStringRelatorioConsumoNulo(String quadra, 
    															boolean inseridoAnormalidade, 
    															boolean inseridoLeitura){
    	
    	String valoresRelatorio = DadosRelatorio.getInstancia().valoresRelatorio;
    	String quadraAlterada = null;
    	int quadraInt = 0;
    	int total = 0;
    	int visitados = 0;
    	int naoVisitados = 0;

    	int indice = valoresRelatorio.indexOf(quadra);
    	String quadraAlteracao = valoresRelatorio.substring(indice, indice + 18);
    	String lidosLeitura = valoresRelatorio.substring(1, 5);
    	String lidosAnormalidade = valoresRelatorio.substring(7, 11);

    	if (inseridoAnormalidade) {
 
    		int lidos = Integer.parseInt(lidosAnormalidade) - 1;
    	    valoresRelatorio = replace(valoresRelatorio, "["
    		    + Util.adicionarZerosEsquerdaNumero(4, lidosAnormalidade) + "]", "[" + 
    		    Util.adicionarZerosEsquerdaNumero(4, String.valueOf(lidos)) + "]");
    	}

    	if (inseridoLeitura) {
    	    
    		int lidos = Integer.parseInt(lidosLeitura) - 1;
    	    valoresRelatorio = replace(valoresRelatorio, "{" + Util.adicionarZerosEsquerdaNumero(4, lidosLeitura)
    		    + "}", "{" + Util.adicionarZerosEsquerdaNumero(4, String.valueOf(lidos)) + "}");
    	}

    	quadraInt = Integer.parseInt(valoresRelatorio.substring(indice + 1, indice + 5));
    	total = Integer.parseInt(valoresRelatorio.substring(indice + 6, indice + 10));
    	visitados = Integer.parseInt(valoresRelatorio.substring(indice + 10, indice + 14)) - 1;
    	naoVisitados = Integer.parseInt(valoresRelatorio.substring(indice + 14, indice + 18)) + 1;

    	if(naoVisitados < 0) {
    	    naoVisitados = 0;
    	}
    	
    	if(visitados < 0 ){
    	    visitados = 0;
    	}

    	quadraAlterada = "(" + Util.adicionarZerosEsquerdaNumero(4, String.valueOf(quadraInt)) + ")"
    		+ Util.adicionarZerosEsquerdaNumero(4, String.valueOf(total))
    		+ Util.adicionarZerosEsquerdaNumero(4, String.valueOf(visitados))
    		+ Util.adicionarZerosEsquerdaNumero(4, String.valueOf(naoVisitados));

    	valoresRelatorio = replace(valoresRelatorio, quadraAlteracao, quadraAlterada);

    	DadosRelatorio.getInstancia().valoresRelatorio = valoresRelatorio;
    }

    public static String replace(String text, String searchStr, String replacementStr){
    	
    	// String buffer to store str
    	StringBuffer sb = new StringBuffer();
		 
    	// Search for search
    	int searchStringPos = text.indexOf(searchStr);  
    	int startPos = 0;  
		int searchStringLength = searchStr.length();
		 
		// Iterate to add string
		while (searchStringPos != -1) {
		
			sb.append(text.substring(startPos, searchStringPos)).append(replacementStr);
			startPos = searchStringPos + searchStringLength;
			searchStringPos = text.indexOf(searchStr, startPos);
		}
		 
		 // Create string
		 sb.append(text.substring(startPos,text.length()));
	
		 return sb.toString();
	}
    
    /**
     * Converte um valor double para uma string formatada
     * 
     * @param valor
     *            valor a ser formatado
     * @return String formatada
     */
    public static String formatarDoubleParaMoedaReal(double d) {
	double valorArredondado = Util.arredondar(d, 2);
	int inteiro = (int) valorArredondado;
	double decimal = Util.arredondar(valorArredondado - (int) inteiro, 2);

	String inteiroString = inteiro + "";
	int contador = 0;
	String comPontoInvertido = "";

	// Agrupamos de 3 em 3
	for (int i = inteiroString.length() - 1; i >= 0; i--) {
	    contador++;
	    comPontoInvertido += inteiroString.charAt(i);

	    if (contador % 3 == 0 && i != 0) {
		comPontoInvertido += ".";
	    }
	}

	String comPonto = "";

	// Invertemos
	for (int i = comPontoInvertido.length() - 1; i >= 0; i--) {
	    contador++;
	    comPonto += comPontoInvertido.charAt(i);
	}

	String decimalString = decimal + "";
	decimalString = decimalString.substring(2, decimalString.length());

	// Colocamos a virgula
	return comPonto + ',' + adicionarZerosDireitaNumero(2, decimalString);
    }

    public static String getAnoBarraMesReferencia(int valor) {
    	if(valor != 0){
        	return String.valueOf(valor).substring(0, 4) + "/" + String.valueOf(valor).substring(4, 6);

    	}else{
    		return "";
    	}
    }
    
    /**
     * Retorna o valor de cnpjFormatado
     * 
     * @return O valor de cnpjFormatado
     */
    public static String formatarCnpj(String cnpj) {
	String cnpjFormatado = cnpj;
	String zeros = "";

	if (cnpjFormatado != null) {

	    for (int a = 0; a < (14 - cnpjFormatado.length()); a++) {
		zeros = zeros.concat("0");
	    }
	    // concatena os zeros ao numero
	    // caso o numero seja diferente de nulo
	    cnpjFormatado = zeros.concat(cnpjFormatado);

	    cnpjFormatado = cnpjFormatado.substring(0, 2) + "." + cnpjFormatado.substring(2, 5) + "." + cnpjFormatado.substring(5, 8) + "/" + cnpjFormatado.substring(8, 12) + "-" + cnpjFormatado.substring(12, 14);
	}

	return cnpjFormatado;
    }
    
    public static String formatarAnoMesParaMesAno(String anoMes) {

    	String anoMesFormatado = "";
    	String anoMesRecebido = anoMes;
    	if (anoMesRecebido.length() < 6) {
    	    anoMesFormatado = anoMesRecebido;
    	} else {
    	    String mes = anoMesRecebido.substring(4, 6);
    	    String ano = anoMesRecebido.substring(0, 4);
    	    anoMesFormatado = mes + "/" + ano;
    	}
    	return anoMesFormatado;
        }

    public static String formatarAnoMesParaMesAnoSemBarra(String anoMes) {

    	String anoMesFormatado = "";
    	String anoMesRecebido = anoMes;
    	if (anoMesRecebido.length() < 6) {
    	    anoMesFormatado = anoMesRecebido;
    	} else {
    	    String mes = anoMesRecebido.substring(4, 6);
    	    String ano = anoMesRecebido.substring(0, 4);
    	    anoMesFormatado = mes + ano;
    	}
    	return anoMesFormatado;
        }
    
    public static String obterRepresentacaoNumericaCodigoBarra(
    	    Integer tipoPagamento, 
    	    double valorCodigoBarra, 
    	    Integer idLocalidade, 
    	    Integer matriculaImovel, 
    	    String mesAnoReferenciaConta, 
    	    Integer digitoVerificadorRefContaModulo10, 
    	    Integer idTipoDebito, 
    	    String anoEmissaoGuiaPagamento,
    	    String sequencialDocumentoCobranca, 
    	    Integer idTipoDocumento, 
    	    Integer idCliente, 
    	    Integer seqFaturaClienteResponsavel) {

    	// [FS0001] Verificar compatibilidade dos campos informados com o tipo
    	// de pagamento
    	if (tipoPagamento == null) {
    	    return "atencao.parametros.incompletos.codigobarra";
    	} else {
    	    // Caso o tipo de pagamento seja referente a conta
    	    if (tipoPagamento.intValue() == 3) {

    		// Caso o código da localidade ou a matrícula do imóvel ou o
    		// mês/ano da referência da conta ou o dígito verificador da
    		// referência da conta no módulo 10
    		// não forem informados levanta uma exceção para o usuário
    		// indicando que os parâmetros para geração do código de barras
    		// está incompleto.
    		if (idLocalidade == null || matriculaImovel == null || mesAnoReferenciaConta == null || digitoVerificadorRefContaModulo10 == null) {
    		    return "atencao.parametros.incompletos.codigobarra";
    		}

    		// Caso o tipo de pagamento seja referente a guia de pagamento
    	    } else if (tipoPagamento.intValue() == 4) {

    		// Caso o código da localidade ou a matrícula do imóvel ou o
    		// tipo de débito ou o ano da emissão da guia de pagamento
    		// não forem informados levanta uma exceção para o usuário
    		// indicando que os parâmetros para geração do código de barras
    		// está incompleto.
    		if (idLocalidade == null || matriculaImovel == null || idTipoDebito == null || anoEmissaoGuiaPagamento == null) {
    		    return "atencao.parametros.incompletos.codigobarra";
    		}

    		// Caso a tipo de pagamento seja referente a documento de
    		// cobrança
    	    } else if (tipoPagamento.intValue() == 5) {

    		// Caso o código da localidade ou a matrícula do imóvel ou o
    		// sequencial do documento de cobrança ou o tipo de documento
    		// não forem informados levanta uma exceção para o usuário
    		// indicando que os parâmetros para geração do código de barras
    		// está incompleto.
    		if (idLocalidade == null || matriculaImovel == null || sequencialDocumentoCobranca == null || idTipoDocumento == null) {
    		    return "atencao.parametros.incompletos.codigobarra";
    		}

    		// Caso o tipo de pagamento seja referente a fatura do cliente
    		// responsável
    	    } else if (tipoPagamento.intValue() == 7) {
    		// Caso o código do cliente ou o mês/ano da referência da conta
    		// ou o sequencial da fatura do cliente responsável
    		// não forem informados levanta uma exceção para o usuário
    		// indicando que os parâmetros para geração do código de barras
    		// está incompleto.
    		if (idCliente == null || mesAnoReferenciaConta == null || seqFaturaClienteResponsavel == null) {
    		    return "atencao.parametros.incompletos.codigobarra";
    		}

    		// Caso a tipo de pagamento seja referente a guia de pagamento
    	    } else if (tipoPagamento.intValue() == 6) {
    		// Caso o código da localidade ou id do cliente ou o
    		// tipo de débito ou o ano da emissão da guia de pagamento
    		// não forem informados levanta uma exceção para o usuário
    		// indicando que os parâmetros para geração do código de barras
    		// está incompleto.
    		if (idLocalidade == null || idCliente == null || idTipoDebito == null || anoEmissaoGuiaPagamento == null) {
    		    return "atencao.parametros.incompletos.codigobarra";
    		}
    	    } else if (tipoPagamento.intValue() == 8) {

    		// Caso o código do cliente ou o
    		// sequencial do documento de cobrança ou o tipo de documento
    		// não forem informados levanta uma exceção para o usuário
    		// indicando que os parâmetros para geração do código de barras
    		// está incompleto.
    		if (idCliente == null || sequencialDocumentoCobranca == null || idTipoDocumento == null) {
    		    return "atencao.parametros.incompletos.codigobarra";
    		}
    	    }
    	}

    	// Cria a variável que vai armazenar a representação númerica do código
    	// de barras
    	String representacaoNumericaCodigoBarra = "";

    	// G.05.1 - Identificação do produto
    	String identificacaoProduto = "8";
    	representacaoNumericaCodigoBarra = representacaoNumericaCodigoBarra + identificacaoProduto;

    	// G.05.2 - Identificação do segmento
    	String identificacaoSegmento = "2";
    	representacaoNumericaCodigoBarra = representacaoNumericaCodigoBarra + identificacaoSegmento;

    	// G.05.3 - Identificação dovalor real ou referência

    	// MODULO 11
    	// String identificacaoValorRealOuReferencia = "8";

    	// MODULO 10
    	String identificacaoValorRealOuReferencia = "6";

    	representacaoNumericaCodigoBarra = representacaoNumericaCodigoBarra + identificacaoValorRealOuReferencia;

    	String valorContaString = Util.formatarDoubleParaMoedaReal(valorCodigoBarra);

    	valorContaString = replaceAll(valorContaString, ".", "");
    	valorContaString = replaceAll(valorContaString, ",", "");

    	// G.05.5 - Valor do código de barras
    	String valorCodigoBarraFormatado = Util.adicionarZerosEsquerdaNumero(11, valorContaString);
    	representacaoNumericaCodigoBarra = representacaoNumericaCodigoBarra + valorCodigoBarraFormatado;

    	// G.05.6 - Identificação da empresa
    	// Fixo por enquanto
    	String identificacaoEmpresa = ControladorRota.getInstancia().getDadosGerais().getCodigoEmpresaFebraban();
    	identificacaoEmpresa = Util.adicionarZerosEsquerdaNumero(4, identificacaoEmpresa);
    	representacaoNumericaCodigoBarra = representacaoNumericaCodigoBarra + identificacaoEmpresa;

    	// G.05.7 Identificação do pagamento
    	// [SB0001] Obter Identificação do Pagamento
    	String identificacaoPagamento = obterIdentificacaoPagamento(tipoPagamento, idLocalidade, matriculaImovel, mesAnoReferenciaConta, digitoVerificadorRefContaModulo10, idTipoDebito, anoEmissaoGuiaPagamento, sequencialDocumentoCobranca, idTipoDocumento, idCliente, seqFaturaClienteResponsavel);

    	representacaoNumericaCodigoBarra = representacaoNumericaCodigoBarra + identificacaoPagamento + tipoPagamento.toString();

    	// G.05.4 - Dígito verificador geral
    	// [SB0002] Obter Dígito verificador geral
    	String digitoVerificadorGeral = (Util.obterDigitoVerificadorGeral(representacaoNumericaCodigoBarra)).toString();

    	// Monta a representaçaõ númerica com todos os campos informados
    	representacaoNumericaCodigoBarra = identificacaoProduto + identificacaoSegmento + identificacaoValorRealOuReferencia + digitoVerificadorGeral + valorCodigoBarraFormatado + identificacaoEmpresa + identificacaoPagamento + tipoPagamento.toString();

    	// Cria as variáveis que vão armazenar o código de barra separado por
    	// campos
    	// e seus respectivos dígitos verificadores se existirem
    	String codigoBarraCampo1 = null;
    	String codigoBarraDigitoVerificadorCampo1 = null;
    	String codigoBarraCampo2 = null;
    	String codigoBarraDigitoVerificadorCampo2 = null;
    	String codigoBarraCampo3 = null;
    	String codigoBarraDigitoVerificadorCampo3 = null;
    	String codigoBarraCampo4 = null;
    	String codigoBarraDigitoVerificadorCampo4 = null;

    	// Separa as 44 posições do código de barras em 4 grupos de onze
    	// posições
    	// e para cada um dos grupos calcula o dígito verificador do módulo 11
    	codigoBarraCampo1 = representacaoNumericaCodigoBarra.substring(0, 11);
    	// codigoBarraDigitoVerificadorCampo1 =
    	// (obterDigitoVerificadorModulo11(new Long(
    	// codigoBarraCampo1))).toString();
    	codigoBarraDigitoVerificadorCampo1 = (Util.obterDigitoVerificadorModulo10(new Long(Long.parseLong(codigoBarraCampo1)))).toString();
    	codigoBarraCampo2 = representacaoNumericaCodigoBarra.substring(11, 22);
    	/*
    	 * codigoBarraDigitoVerificadorCampo2 =
    	 * (obterDigitoVerificadorModulo11(new Long(
    	 * codigoBarraCampo2))).toString();
    	 */
    	codigoBarraDigitoVerificadorCampo2 = (Util.obterDigitoVerificadorModulo10(new Long(Long.parseLong(codigoBarraCampo2)))).toString();
    	codigoBarraCampo3 = representacaoNumericaCodigoBarra.substring(22, 33);
    	/*
    	 * codigoBarraDigitoVerificadorCampo3 =
    	 * (obterDigitoVerificadorModulo11(new Long(
    	 * codigoBarraCampo3))).toString();
    	 */
    	codigoBarraDigitoVerificadorCampo3 = (Util.obterDigitoVerificadorModulo10(new Long(Long.parseLong(codigoBarraCampo3)))).toString();
    	codigoBarraCampo4 = representacaoNumericaCodigoBarra.substring(33, 44);
    	/*
    	 * codigoBarraDigitoVerificadorCampo4 =
    	 * (obterDigitoVerificadorModulo11(new Long(
    	 * codigoBarraCampo4))).toString();
    	 */
    	codigoBarraDigitoVerificadorCampo4 = (Util.obterDigitoVerificadorModulo10(new Long(Long.parseLong(codigoBarraCampo4)))).toString();

    	// Monta a representação númerica do código de barras com os dígitos
    	// verificadores
    	representacaoNumericaCodigoBarra = codigoBarraCampo1 + codigoBarraDigitoVerificadorCampo1 + codigoBarraCampo2 + codigoBarraDigitoVerificadorCampo2 + codigoBarraCampo3 + codigoBarraDigitoVerificadorCampo3 + codigoBarraCampo4 + codigoBarraDigitoVerificadorCampo4;

    	// Retorna a representação númerica do código de barras
    	return representacaoNumericaCodigoBarra;
        }
    
    public static Integer obterDigitoVerificadorModulo10(Long numero) {

    	long entrada = numero.longValue();

    	String entradaString = String.valueOf(entrada);

    	int sequencia = 2;
    	int contEntrada, digito, contAuxiliar, produto, contProduto;
    	String produtoString;
    	int somaDigitosProduto = 0;

    	contAuxiliar = 1;
    	for (contEntrada = 0; contEntrada < entradaString.length(); contEntrada++) {

    	    digito = Integer.parseInt(entradaString.substring(entradaString.length() - contAuxiliar, entradaString.length() - contEntrada));

    	    produto = digito * sequencia;
    	    produtoString = String.valueOf(produto);

    	    for (contProduto = 0; contProduto < produtoString.length(); contProduto++) {
    		somaDigitosProduto = somaDigitosProduto + Integer.parseInt(produtoString.substring(contProduto, contProduto + 1));
    	    }

    	    if (sequencia == 2) {
    		sequencia = 1;
    	    } else {
    		sequencia = 2;
    	    }

    	    contAuxiliar++;
    	}

    	int resto = (somaDigitosProduto % 10);

    	int dac;
    	if (resto == 0) {
    	    dac = 0;
    	} else {
    	    dac = 10 - resto;
    	}

    	return new Integer(dac);
        }
    
    public static Integer obterDigitoVerificadorGeral(String codigoBarraCom43Posicoes) {
    	// Recupera o dígito verificador do módulo 11 para o código de barra com
    	// 43 posições
    	// Passando uma string como parâmetro

    	// MUDOU PARA O MODULO 10
    	// Integer digitoVerificadorGeral =
    	// obterDigitoVerificadorModulo11(codigoBarraCom43Posicoes);

    	Integer digitoVerificadorGeral = obterDigitoVerificadorModulo10(codigoBarraCom43Posicoes);

    	// Retorna o dígito verificador calculado
    	return digitoVerificadorGeral;
        }
    
    
    public static Integer obterDigitoVerificadorModulo10(String numero) {

    	String entradaString = numero;

    	int sequencia = 2;
    	int contEntrada, digito, contAuxiliar, produto, contProduto;
    	String produtoString;
    	int somaDigitosProduto = 0;

    	contAuxiliar = 1;
    	for (contEntrada = 0; contEntrada < entradaString.length(); contEntrada++) {

    	    digito = Integer.parseInt(entradaString.substring(entradaString.length() - contAuxiliar, entradaString.length() - contEntrada));

    	    produto = digito * sequencia;
    	    produtoString = String.valueOf(produto);

    	    for (contProduto = 0; contProduto < produtoString.length(); contProduto++) {
    		somaDigitosProduto = somaDigitosProduto + Integer.parseInt(produtoString.substring(contProduto, contProduto + 1));
    	    }

    	    if (sequencia == 2) {
    		sequencia = 1;
    	    } else {
    		sequencia = 2;
    	    }

    	    contAuxiliar++;
    	}

    	int resto = (somaDigitosProduto % 10);

    	int dac;
    	if (resto == 0) {
    	    dac = 0;
    	} else {
    	    dac = 10 - resto;
    	}

    	return new Integer(dac);
        }

        public static String replaceAll(String text, String searchString, String replacementString) {

    	StringBuffer sBuffer = new StringBuffer();
    	int pos = 0;

    	while ((pos = text.indexOf(searchString)) != -1) {
    	    sBuffer.append(text.substring(0, pos) + replacementString);
    	    text = text.substring(pos + searchString.length());
    	}

    	sBuffer.append(text);

    	return sBuffer.toString();
        }
        
        public static String obterIdentificacaoPagamento(Integer tipoPagamento, Integer idLocalidade, Integer matriculaImovel, String mesAnoReferenciaConta, Integer digitoVerificadorRefContaModulo10, Integer idTipoDebito, String anoEmissaoGuiaPagamento, String sequencialDocumentoCobranca,
        	    Integer idTipoDocumento, Integer idCliente, Integer seqFaturaClienteResponsavel) {

        	// Cria a variável que vai armazenar o identificador do pagamento
        	// formatado
        	String identificacaoPagamento = "";

        	// Caso o tipo de pagamento seja referente a conta
        	if (tipoPagamento.intValue() == 3) {
        	    identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(3, "" + idLocalidade);
        	    identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(8, "" + matriculaImovel);
        	    identificacaoPagamento = identificacaoPagamento + "000";
        	    identificacaoPagamento = identificacaoPagamento + mesAnoReferenciaConta;
        	    identificacaoPagamento = identificacaoPagamento + digitoVerificadorRefContaModulo10;
        	    identificacaoPagamento = identificacaoPagamento + "000";

        	    // Caso o tipo de pagamento seja referente a guia de pagamento
        	    // (Imóvel)
        	} else if (tipoPagamento.intValue() == 4) {
        	    identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(3, "" + idLocalidade);
        	    identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(8, "" + matriculaImovel);
        	    identificacaoPagamento = identificacaoPagamento + "000";
        	    identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(3, idTipoDebito.toString()));
        	    identificacaoPagamento = identificacaoPagamento + anoEmissaoGuiaPagamento;
        	    identificacaoPagamento = identificacaoPagamento + "000";

        	    // Caso a tipo de pagamento seja referente a documento de cobrança
        	} else if (tipoPagamento.intValue() == 5) {
        	    identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(3, "" + idLocalidade);
        	    identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(8, "" + matriculaImovel);
        	    identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(9, sequencialDocumentoCobranca));
        	    identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(2, idTipoDocumento.toString()));
        	    identificacaoPagamento = identificacaoPagamento + "00";

        	    // Caso o tipo de pagamento seja referente a guia de pagamento
        	    // (Cliente)
        	} else if (tipoPagamento.intValue() == 6) {
        	    identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(3, "" + idLocalidade);
        	    identificacaoPagamento = identificacaoPagamento + Util.adicionarZerosEsquerdaNumero(8, "" + idCliente);
        	    identificacaoPagamento = identificacaoPagamento + "000";
        	    identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(3, idTipoDebito.toString()));
        	    identificacaoPagamento = identificacaoPagamento + anoEmissaoGuiaPagamento;
        	    identificacaoPagamento = identificacaoPagamento + "000";

        	    // Caso o tipo de pagamento seja referente a fatura do cliente
        	    // responsável
        	} else if (tipoPagamento.intValue() == 7) {
        	    identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(9, idCliente.toString()));
        	    identificacaoPagamento = identificacaoPagamento + "00";
        	    identificacaoPagamento = identificacaoPagamento + mesAnoReferenciaConta;
        	    identificacaoPagamento = identificacaoPagamento + digitoVerificadorRefContaModulo10;
        	    identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(6, seqFaturaClienteResponsavel.toString()));
        	    // Caso a tipo de pagamento seja referente a documento de cobrança
        	    // cliente
        	} else if (tipoPagamento.intValue() == 8) {
        	    identificacaoPagamento = identificacaoPagamento + "000";
        	    identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(8, idCliente.toString()));
        	    identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(9, sequencialDocumentoCobranca));
        	    identificacaoPagamento = identificacaoPagamento + (Util.adicionarZerosEsquerdaNumero(2, idTipoDocumento.toString()));
        	    identificacaoPagamento = identificacaoPagamento + "00";
        	}

        	// Retorna o identificador do pagamento formatado
        	return identificacaoPagamento;
            }
}
