package util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import business.ControladorImovel;

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
    	
    	ControladorImovel.getInstancia().getCadastroDataManipulator().selectGeral();

//    	diretorioRetornoRota =  Controlador.getInstancia().getDadosGerais().getLocalidade() + "_";
//    	diretorioRetornoRota += Controlador.getInstancia().getDadosGerais().getSetor() + "_";
//    	diretorioRetornoRota += Controlador.getInstancia().getDadosGerais().getRota() + "_";
    	diretorioRetornoRota += ControladorImovel.getInstancia().getDadosGerais().getAnoMesFaturamento();
    	
        File fileRotaDiretorio = new File(Environment.getExternalStorageDirectory() + Constantes.DIRETORIO_RETORNO, diretorioRetornoRota);
        if(!fileRotaDiretorio.exists()) {
        	fileRotaDiretorio.mkdirs();
        }

    	return fileRotaDiretorio.getAbsolutePath();
    }
 
    public static String getRotaFileName(){
    	String rotaFileName = null;
    	
    	ControladorImovel.getInstancia().getCadastroDataManipulator().selectGeral();
    	
//    	rotaFileName =  Controlador.getInstancia().getDadosGerais().getLocalidade() + "_";
//    	rotaFileName += Controlador.getInstancia().getDadosGerais().getSetor() + "_";
//    	rotaFileName += Controlador.getInstancia().getDadosGerais().getRota() + "_";
    	rotaFileName += ControladorImovel.getInstancia().getDadosGerais().getAnoMesFaturamento() + ".txt";
    	
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

    public static void addTextChangedListenerCepMask(final EditText edt){
    	edt.addTextChangedListener(new TextWatcher() {  
    	    
    		
    		public void beforeTextChanged(CharSequence s, int start, int count, int after) {  
    	    }  
    		
    		public void afterTextChanged(Editable e){}
    	      
    		
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
    	});
    }

}
