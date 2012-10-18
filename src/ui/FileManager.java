package ui;import java.io.BufferedReader;import java.io.ByteArrayInputStream;import java.io.ByteArrayOutputStream;import java.io.File;import java.io.FileInputStream;import java.io.IOException;import java.io.InputStream;import java.io.InputStreamReader;import java.util.ArrayList;import java.util.List;import java.util.Vector;import model.Anormalidade;import util.Constantes;import util.GZIP;import android.os.Environment;import android.util.Log;import business.ControladorRota;public class FileManager {    private static FileManager instancia;    private InputStream inputStreamArquivo;        private static BufferedReader inputBuffer;    private static FileInputStream fInputStream;    private static File fileSelected;    private String pathDiretorioRotas;    private static Vector anormalidadesIndicadorUso1;    private static Vector anormalidadesSemIndicadorUso1;    private static int LEITURA_CONFIRMADA = 99;        /**     * Valor decimal do código ascii para o Carriage-Return (enter). OBS.: O     * valor hexadecimal é 0D     */    private final static byte ENTER = 13;    /**     * Valor decimal do código ascii para o Line-Feed (pula linha).     */    private final static byte LINE = 10;    /**     * Valor retornado quando o final do arquivo é atingido.     */    private final static byte EOF = -1;    private static int indiceArquivoImoveis;    /**     * Modo de abertura de arquivo que nao abre se nao existir.     */    public static final int NAO_CRIAR = 1;    /**     * Caminho do cartão de memória.     */    public static String INPUT_FILE_PATH;    /**     * Modo de abertura de arquivo que cria o arquivo caso não exista.     */    public static final int CRIAR_SE_NAO_EXISTIR = 0;    /**     * Modo de abertura de arquivo que apaga antes de criar.     */    public static final int APAGAR_E_CRIAR = 2;    /**     * Modo de abertura de arquivo que apaga antes de criar.     */    public static final int APAGAR = 3;    /**     * Caminhos dos recursos. Raiz = /res/     */    private static final String RESOURCE_PATH = "/";        private static final int NUMERO_LINHAS_LEITURA = 0;     /**     * Retorna um vetor com a lista de arquivos no diretório do GComMovel.     *      * @return Vetor com os nomes dos arquivos no diretório.     */    private FileManager() {		try{			descobrirRootsDispositivo();		} catch (SecurityException se){}    }    public static FileManager getInstancia() {    	if (instancia == null) {    		instancia = new FileManager();    	}    	return instancia;	}    public static int getFileLineNumber(String fileName) throws IOException {		int numeroLinhas = Constantes.NULO_INT;    	String line = "";       	inputBuffer = null;        fileSelected = new File(Environment.getExternalStorageDirectory(), Constantes.DIRETORIO_ROTAS + "/" + fileName );                if (fileSelected.canRead()){        			try {				if (fileName.endsWith(".txt")) {					fInputStream = new FileInputStream(fileSelected);										int tamanho = (int) fileSelected.length();				    byte[] firstLine = new byte[100];							            ByteArrayOutputStream baos = new ByteArrayOutputStream();		            int nrb = fInputStream.read(firstLine, 0, 100);	                baos.write(firstLine, 0, nrb);		            		            firstLine = baos.toByteArray();		            			    	ByteArrayInputStream byteArray = new ByteArrayInputStream(firstLine);			    						InputStreamReader iSReader = new InputStreamReader(byteArray, "UTF-8");					inputBuffer = new BufferedReader(iSReader);					if((line = inputBuffer.readLine()) != null) {                         						numeroLinhas = Integer.parseInt(line);				    }										fInputStream.close();				} else {					readCompressedFile(fileName);											numeroLinhas = Integer.parseInt(inputBuffer.readLine());											fInputStream.close();				}											} catch (IOException e) {				e.printStackTrace();			}        }        return numeroLinhas;    }    /**     * Abre um arquivo ou diretório do sistema de arquivos do dispositivo.     *      * @param caminho     *            Caminho do arquivo ou diretório.     * @param diretorio     *            Identifica se é um diretório ou não.     * @param modo     *            Modo de abertura.     * @return Arquivo.     */    public static BufferedReader readFile(String fileName) {    	inputBuffer = null;        fileSelected = new File(Environment.getExternalStorageDirectory(), Constantes.DIRETORIO_ROTAS + "/" + fileName );                if (fileSelected.canRead()){        			try {				fInputStream = new FileInputStream(fileSelected);					InputStreamReader iSReader = new InputStreamReader(fInputStream, "UTF-8");				inputBuffer = new BufferedReader(iSReader);				} catch (IOException e) {				e.printStackTrace();			}        }        return inputBuffer;    }    /**     * Abre um arquivo ou diretório do sistema de arquivos do dispositivo.     *      * @param caminho     *            Caminho do arquivo ou diretório.     * @param diretorio     *            Identifica se é um diretório ou não.     * @param modo     *            Modo de abertura.     * @return Arquivo.     */    public static BufferedReader readCompressedFile(String fileName) {    	inputBuffer = null;        fileSelected = new File(Environment.getExternalStorageDirectory(), Constantes.DIRETORIO_ROTAS + "/" + fileName );                if (fileSelected.canRead()){        			try {				fInputStream = new FileInputStream(fileSelected);				    byte[] arrayArquivoCompactado = new byte[(int)fileSelected.length()];					            ByteArrayOutputStream baos = new ByteArrayOutputStream();	            int nrb = fInputStream.read(arrayArquivoCompactado, 0, (int)fileSelected.length());	            	            while (nrb != -1) {	                baos.write(arrayArquivoCompactado, 0, nrb);	                nrb = fInputStream.read(arrayArquivoCompactado, 0, (int)fileSelected.length());	            }	            arrayArquivoCompactado = baos.toByteArray();	            arrayArquivoCompactado = GZIP.inflate(arrayArquivoCompactado);		    	ByteArrayInputStream byteArray = new ByteArrayInputStream(arrayArquivoCompactado);		    					InputStreamReader iSReader = new InputStreamReader(byteArray, "UTF-8");				inputBuffer = new BufferedReader(iSReader);	//	            fInputStream.close();							} catch (IOException e) {				e.printStackTrace();			}			        }        return inputBuffer;    }    /**     * Separa um arquivo texto por linhas.     *      * @param arquivo     *            Dados do arquivo texto.     * @param maxLinhas     *            Número máximo de linhas. Caso seja zero, retorna todas as     *            linhas     * @return Vetor de linhas do arquivo texto.     */    public Vector carregaVetorLinhas(InputStream arquivo, int maxLinhas) throws IOException {		Vector vetor = new Vector();		vetor.removeAllElements();		StringBuffer buffer = new StringBuffer();				int i = 0;		while (i != EOF && (maxLinhas == 0 || vetor.size() < maxLinhas)) {		    i = arquivo.read();			String line = buffer.toString();		    // se for enter (0D ou 13)...		    if (i == ENTER) {			// ...pula para o próximo caractere			    i = arquivo.read();						    }		    // se for quebra linha (0A ou 10)...		    if (i == LINE || i == EOF) {			// ...salva o registro				if ("".equals(line) || line == null  || line.length()==0) {					continue;				}				vetor.addElement(line);				buffer.delete(0, buffer.length());		    } else {		    	buffer.append((char) i);		    }		}			indiceArquivoImoveis += maxLinhas;		buffer = null;		return vetor;    }    public Vector lerArquivoImoveis( InputStream arquivo ) throws IOException {	inputStreamArquivo = arquivo;	boolean response = (inputStreamArquivo != null);	Vector linhas = null;	if (response) {	    linhas = carregaVetorLinhas(inputStreamArquivo, NUMERO_LINHAS_LEITURA);	    if (inputStreamArquivo != null && linhas.size() == 0) {		inputStreamArquivo.close();		inputStreamArquivo = null;	    }	}	return linhas;    }        public void descobrirRootsDispositivo() throws SecurityException {    	    	String state = Environment.getExternalStorageState();    	if (Environment.MEDIA_MOUNTED.equals(state)) {    	    // We can read and write the media            File path = Environment.getExternalStorageDirectory();            path.getAbsolutePath();                        Log.i("ExternalStorage", "ExternalStorage :" + path.getAbsolutePath());            pathDiretorioRotas = path.getAbsolutePath() + Constantes.DIRETORIO_ROTAS;    	    	}else{    		pathDiretorioRotas = null;    	}    }    public Vector carregarTabela(int tipoTabela) throws IOException {  	    Vector tabela = null; // 	    switch (tipoTabela){//	    case Constantes.TABELA_TIPO_CARREGAMENTOS:// 		    if (Controlador.getInstancia().getCarregamentos() != null){// 				tabela = Controlador.getInstancia().getCarregamentos();	//  		    }// 	    	break;// 	    }    	return tabela;    }    /**     * Carrega os navios direto do arquivo     *      * @return vetor com os navios     */    public static Vector getTabela(int tabelaTipo){		Vector tabela = new Vector();	try {		tabela = FileManager.getInstancia().carregarTabela(tabelaTipo);	} catch (IOException e) {	    e.printStackTrace();	}	return tabela;    }    public static boolean verificarExistenciaArquivo(String nomeArquivo) {	// abre o diretório do GCom, criando ele caso ainda não exista	boolean result = false;	return result;    }        /**     * Retorna a anormalidade com o código pesquisado     *      * @param codigo     *            codigo da anormalidade     * @return se achar, retorna a anormalidade, senão nulo     */    public static Anormalidade getAnormalidade(int codigo) throws IOException {	Anormalidade anor = new Anormalidade();	anor.setCodigo(""+codigo);	Vector anormalidades = getAnormalidades(false);	int indice = anormalidades.indexOf(anor);	if (indice != -1) {	    return (Anormalidade) anormalidades.elementAt(indice);	} else {	    return null;	}    }        /**     * Carrega as anormalidades direto do arquivo     *      * @return vetor com as anormalidades     */    public static Vector getAnormalidades(boolean apenasComIndicadorUso1){		Vector anormalidades = new Vector();	try {	    if (ControladorRota.getInstancia().getDadosGerais().getCodigoEmpresaFebraban().equals(Constantes.CODIGO_FEBRABAN_COSANPA)) {		anormalidades = FileManager.getInstancia().carregarAnormalidades(apenasComIndicadorUso1);		}	} catch (IOException e) {	    e.printStackTrace();//	    Util.mostrarErro("Erro ao carregar arquivo de anormalidade");	}	return anormalidades;    }        /**     * Carrega as anormalidades no controlador conta     */    public Vector carregarAnormalidades(boolean apenasComIndicadorUso1) throws IOException {		if ( apenasComIndicadorUso1 && anormalidadesIndicadorUso1 != null ){	    return anormalidadesIndicadorUso1;	} else if ( !apenasComIndicadorUso1 && anormalidadesSemIndicadorUso1 != null ){	    return anormalidadesSemIndicadorUso1;	} else {		    	    Vector anorm = null;	    // lê arquivo de anormalidades	    	    List anorms = ControladorRota.getInstancia().getDataManipulator().selectAnormalidades(apenasComIndicadorUso1);// Daniel - check if it's null first.	    if (anorms != null){			List anormalidades = anorms;				// carrega os id's de cada anormalidade			Anormalidade[] anors = this.carregarVetorAnormalidades(anormalidades, apenasComIndicadorUso1);			// define lista de anormalidades//			anorm = AbaHidrometroAgua.getInstancia().setAnormalidadesAgua(anors);			anorm = new Vector();			for (int i = 0; i < anors.length; i++) {				anorm.addElement(anors[i]);			}			    if (apenasComIndicadorUso1) {			anormalidadesIndicadorUso1 = anorm;		    } else {			anormalidadesSemIndicadorUso1 = anorm;		    }	    }	    return anorm;	    	}    }        /**     * Carrega o array que mapeia o indice da anormalidade no identificador.     *      * @param anormalidades     *            Vetor de anormalidades.     */    public Anormalidade[] carregarVetorAnormalidades(List anormalidades, boolean apenasComIndicadorUso1) {	int len = anormalidades.size();	// Daniel- corrigindo vetor de anormalidades		Anormalidade[] retorno = new Anormalidade[anormalidades.size()+1];	Anormalidade anor = new Anormalidade();	anor.setCodigo(""+00);	anor.setDescricao("");	anor.setIndicadorLeitura(""+1);	anor.setIdConsumoACobrarComLeitura(""+1);	anor.setIdConsumoACobrarSemLeitura(""+0);	anor.setIdLeituraFaturarComLeitura(""+1);	anor.setIdLeituraFaturarSemLeitura(""+0);	anor.setIndcUso(""+Constantes.SIM);	anor.setNumeroFatorSemLeitura(""+Constantes.NULO_DOUBLE);	anor.setNumeroFatorComLeitura(""+Constantes.NULO_DOUBLE);		retorno[0] = anor;		int contador = 1;	// Daniel- corrigindo vetor de anormalidades		for (int i = 1; i < len+1 ; i++) {	    Anormalidade reg14 = (Anormalidade) anormalidades.get(i-1);// Daniel - Descarta Anormalidade LEITURA_CONFIRMADA - Usuario não pode usar tal opção.	    if (reg14.getCodigo() == LEITURA_CONFIRMADA){	    	len = len -1;	    	continue;	    }		anor = new Anormalidade();	    anor.setCodigo(""+reg14.getCodigo());	    anor.setDescricao(reg14.getDescricao());	    anor.setIndicadorLeitura(""+reg14.getIndicadorLeitura());	    anor.setIdConsumoACobrarComLeitura(""+reg14.getIdConsumoACobrarComLeitura());	    anor.setIdConsumoACobrarSemLeitura(""+reg14.getIdConsumoACobrarSemLeitura());	    anor.setIdLeituraFaturarSemLeitura(""+reg14.getIdLeituraFaturarSemLeitura());	    anor.setIdLeituraFaturarComLeitura(""+reg14.getIdLeituraFaturarComLeitura());	    anor.setIndcUso(""+reg14.getIndcUso());	    anor.setNumeroFatorSemLeitura(""+reg14.getNumeroFatorSemLeitura());		anor.setNumeroFatorComLeitura(""+reg14.getNumeroFatorComLeitura());	   	    int uso = reg14.getIndcUso();	    retorno[i] = anor;	    //retorno[i + 1] = anor;	    if (uso != 2) {		contador++;	    }	}	if (apenasComIndicadorUso1) {	    Anormalidade[] retornoIndicadorUsoSim = new Anormalidade[contador];	    contador = 0;	 // Daniel- corrigindo vetor de anormalidades		    for (int i = 0; i < len+1; i++) {		if (retorno[i].getIndcUso() == Constantes.SIM) {		    retornoIndicadorUsoSim[contador] = retorno[i];		    contador++;		}	    }	    return retornoIndicadorUsoSim;	} else {	    return retorno;	}    }}