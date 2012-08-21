package ui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.IS.Fachada;

import android.util.Log;

/**
 * Classe reponsável por enviar as mensagens de requisição de serviço ao
 * servidor.
 * 
 * @author Daniel Canova Zaccarias
 */
public class MessageDispatcher {

    private static MessageDispatcher instancia;
    private InputStream respostaOnline;
    private int fileLength;
    private char tipoArquivo;
    private static String mensagemError = null;

    public static final String RESPOSTA_OK = "*";
    public static final String RESPOSTA_ERRO = "#";
    
    // Tipos de parametros que podem ser retornados
    public static final String PARAMETRO_IMOVEIS_PARA_REVISITAR = "imoveis=";
    public static final String PARAMETRO_MENSAGEM = "mensagem=";
    public static final String PARAMETRO_TIPO_ARQUIVO = "tipoArquivo=";
    public static final String PARAMETRO_ARQUIVO_ROTEIRO = "arquivoRoteiro=";
    
    public static final char CARACTER_FIM_PARAMETRO = '&';
       
    private static String respostaServidor = RESPOSTA_OK;

    // URL do servidor
    private String urlServidor;

    // Conexão
    private HttpURLConnection conexao;

    // Mensagem de requisição a ser enviada ao servidor.
    private byte[] mensagem;

    public static String getRespostaServidor() {
    	return respostaServidor;
    }

    /**
     * Define a mensagem de requisição a ser enviada ao servidor.
     * 
     * @param mensagem
     *            Mensagem empacotada.
     */
    public void setMensagem(byte[] mensagem) {
    	this.mensagem = mensagem;
    }
     
    // Inicia a execução na nova thread criada.
    public void enviarMensagem() {
	
		synchronized ( mensagem ) {
		    
		    InputStream resposta = null;
		
		    try {
		    	
				// Abrimos a conexão
			    conexao = (HttpURLConnection) new URL(getUrlServidor().concat(Fachada.IMPRESSAO_SIMULTANEA_ACTION_URL)).openConnection();
			    
				// Passamos os parametros, para a requisição
				conexao.setDoOutput(true);
				conexao.setDoInput(true);
				conexao.setUseCaches(false);
		
				conexao.setRequestProperty("Content-Type","application/octet-stream");
				conexao.setRequestProperty("Content-Length", Integer
						.toString(this.mensagem.length));
				conexao.setRequestMethod("POST");
		
				conexao.setRequestProperty("User-Agent",
					"Profile/Android Apache");
		
				// envia os dados, passados na mensagem informada
				DataOutputStream dos = new DataOutputStream(conexao.getOutputStream());
				dos.write(this.mensagem);
			    Log.e("MessageDispatcher", "Headers are written");
			    dos.flush();
			    dos.close();
			
				// lê a resposta do servidor
				int rc = conexao.getResponseCode();
		
				// Verificamos se a resposta da conexão foi ok
				if (rc == HttpURLConnection.HTTP_OK) {
		
				    // Agora, verificamos se o servidor nos enviou mensagem de processamento ok
				    resposta = conexao.getInputStream();
		
				    InputStreamReader isr = new InputStreamReader(resposta);
				    char c = (char) isr.read();
				    String valor = String.valueOf(c);
				    
				    StringBuffer buffer = new StringBuffer();
				    StringBuffer bufferValorParametro = new StringBuffer();		    
				    
				    // Se foi ok, caso o servidor tenha enviado o caracter esperado
				    if (valor.equals(RESPOSTA_OK)) {
					
						for (int i = 1; i <= conexao.getContentLength(); i++) {
						    
						    char caracter = (char) isr.read();
						    buffer.append( caracter );
						    
						    boolean ultimoCaracter = i == conexao.getContentLength();
						    
						    if ( controlarParametros(buffer, caracter, bufferValorParametro, ultimoCaracter) ){
							
								buffer = new StringBuffer();
								bufferValorParametro = new StringBuffer();
								
								continue;
						    }
						    
						    // Caso ache arquivos de retorno,
					            // não haverão parametros subsequentes				
						    if ( buffer.toString().indexOf( PARAMETRO_ARQUIVO_ROTEIRO ) > -1 ){				
								
						    	fileLength = (int)conexao.getContentLength() - (i+1);
								break;
						    }
						}
						
						buffer = null;
			
						respostaServidor = RESPOSTA_OK;
						
						// Setamos a resposta encontrada
						this.setRespostaOnline((InputStream) resposta);
						// Em qualquer outra situação
				    
				    } else {
						// Guardamos a resposta
						respostaServidor = RESPOSTA_ERRO;
			
						for (int i = 1; i <= conexao.getContentLength(); i++) {
						    
						    char caracter = (char) isr.read();
						    buffer.append( caracter );
						    
						    boolean ultimoCaracter = i == conexao.getContentLength();
						    
						    if ( controlarParametros(buffer, caracter, bufferValorParametro, ultimoCaracter) ){
								buffer = new StringBuffer();
								bufferValorParametro = new StringBuffer();
								continue;
						    }
						}
				    }
				} else {
				    // Caso não consiga a conexão, informa o erro
				    respostaServidor = RESPOSTA_ERRO;
				}
		    } catch (IOException e) {
				// Informamos que um erro ocorreu
				respostaServidor = RESPOSTA_ERRO;
		    } catch (SecurityException se) {
				// Informamos que um erro ocorreu
				respostaServidor = RESPOSTA_ERRO;
	//			Util.mostrarErro("Acesso a internet nao  permitido!");		    	
			}
		    
		    finally {
				if (conexao != null){
					conexao.disconnect();
			    }
		    }
		}
    }

    /**
     * Set URL de conexão com o sevidor
     **/
    public void setUrlServidor(String URL_SERVIDOR) {
			this.urlServidor = URL_SERVIDOR;
    }
    
    /**
     * Set URL de conexão com o sevidor
     **/
    public String getUrlServidor() {
		return this.urlServidor;
    }    

    public InputStream getRespostaOnline() {
    	return this.respostaOnline;
    }

    public void setRespostaOnline(InputStream respostaOnline) {
    	this.respostaOnline = respostaOnline;
    }

    public static MessageDispatcher getInstancia() {
    	
    	if (instancia == null) {
    		instancia = new MessageDispatcher();
    	}

    	return instancia;
    }

    protected MessageDispatcher() {
    	super();
    }

    public void setFileLength(int fileLength) {
    	this.fileLength = fileLength;
    }

    public int getFileLength() {
    	return fileLength;
    }

    public void setTipoArquivo(char tipoArquivo) {
    	this.tipoArquivo = tipoArquivo;
    }

    public char getTipoArquivo() {
    	return tipoArquivo;
    }

    // Só podemos pegar a mensagem de error uma vez
    public static String getMensagemError() {
		String temp = mensagemError;
		mensagemError = null;
		return temp;
    }

    public boolean controlarParametros( 
	    StringBuffer buffer, 
	    char caracter, 
	    StringBuffer bufferValorParametro,
	    boolean ultimoCaracter ){
	
	/* Caso se ja um dos parametros abaixo
	   só podemos comecar a receber o valor
	   quando estivermos lendo o primeiro caracter
	   pos identificador de parametros. Por isso
	   a comparacao com o tamanho do buffer */
	
	// Mensagem	
	if ( ( buffer.toString().indexOf( PARAMETRO_MENSAGEM ) > -1 &&
	       buffer.toString().length() > PARAMETRO_MENSAGEM.length() ) ||
	     // Imoveis a revisitar
	     ( buffer.toString().indexOf( PARAMETRO_IMOVEIS_PARA_REVISITAR ) > -1 &&
	       buffer.toString().length() > PARAMETRO_IMOVEIS_PARA_REVISITAR.length() ) ||
             // Tipo de arquivo carregado   
	     ( buffer.toString().indexOf( PARAMETRO_TIPO_ARQUIVO ) > -1 &&
               buffer.toString().length() > PARAMETRO_TIPO_ARQUIVO.length() ) ){
				
	    // Caso encontremos o caracter de fim de parametro
	    if ( caracter == CARACTER_FIM_PARAMETRO || ultimoCaracter ){	
			    
			// Mensagem
			if ( buffer.toString().indexOf( PARAMETRO_MENSAGEM ) > -1 ){				    
			    mensagemError =  bufferValorParametro.toString();		
			// Imoveis a revisitar					
			} else if ( buffer.toString().indexOf( PARAMETRO_IMOVEIS_PARA_REVISITAR ) > -1 ){
			    //this.setMatriculasImoveisRevisitar( bufferValorParametro.toString().getBytes() );
	//		    Configuracao.getInstancia().setMatriculasRevisitar( bufferValorParametro.toString() );
	//		    Repositorio.salvarObjeto( Configuracao.getInstancia() );
			// Tipo de arquivo carregado					
			} else if ( buffer.toString().indexOf( PARAMETRO_TIPO_ARQUIVO ) > -1 ){
			    this.setTipoArquivo( bufferValorParametro.toString().charAt( 0 ) );
			}
			
			return true;
	    
	    } else {
	    	bufferValorParametro.append( caracter );
	    }
	}
	
	return false;
    }
}
