package business;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Vector;

import ui.MessageDispatcher;
import util.Util;

public class ControladorAcessoOnline {


	public static boolean indcConfirmacaRecebimento = false;

	//Identificador da requisição Cliente->Servidor de confirmar recebimento do roteiro.
	public static final byte CS_CONFIRMAR_RECEBIMENTO = 3;

	// Identificadores das requisições
	private static final byte PACOTE_BAIXAR_ROTEIRO = 0;
	private static final byte PACOTE_ATUALIZAR_MOVIMENTO = 1;
	private static final byte PACOTE_FINALIZAR_CADASTRAMENTO = 2;
	private static final byte PACOTE_CONFIRMAR_ARQUIVO_RECEBIDO = 3;
	private static final byte BAIXAR_NOVA_VERSAO = 4;
	
	private boolean requestOK = false;

	// IMEI (International Mobile Equipment Identifier) do dispositivo.
	private long imei;

	/**
	 * Com base no padrão de Projeto chamado Singleton, OnlineAccess tem apenas
	 * uma única instância em todo o contexto da aplicação.
	 */
	private static ControladorAcessoOnline instance;

	//Despachante das mensagens de requisição de serviço para o servidor.
	private static MessageDispatcher dispatcher;

	// Cria uma instância do objeto OnlineAccess.
	private ControladorAcessoOnline() {
		ControladorAcessoOnline.dispatcher = MessageDispatcher.getInstancia();
	}

	/**
	 * Retorna a instância de Online Access.
	 * 
	 * @return A instância da fachada de rede.
	 */
	public static ControladorAcessoOnline getInstancia() {
		if (ControladorAcessoOnline.instance == null) {
			ControladorAcessoOnline.instance = new ControladorAcessoOnline();
		}
		return ControladorAcessoOnline.instance;
	}
	
    /**
     * Repassa as requisições ao servidor.
     * 
     * @param parametros
     *            Vetor de parâmetros da operação.
     * @param recebeResposta
     *            Boolean que diz se recebe ou não um InputStream do servidor
     */
    public void iniciarServicoRede(Vector parametros, boolean enviarIMEI) {

		byte[] serverMsg = null;
	
		if (enviarIMEI) {
		    // adiciona o IMEI como segundo parâmetro da mensagem
		    parametros.insertElementAt(new Long(this.getIMEI()), 1);
		}
	
		// transforma os parâmetros em array de bytes para enviar
		serverMsg = Util.empacotarParametros(parametros);
	
		// envia menssagem para o servidor
		dispatcher.setMensagem(serverMsg);
		dispatcher.enviarMensagem();
		requestOK = MessageDispatcher.getRespostaServidor() == MessageDispatcher.RESPOSTA_OK;
    }

    /**
     * Alterar a url do servidor
     **/
    public void setURL(String url) {
    	dispatcher.setUrlServidor(url);
    }

    /**
     * Retorna o IMEI (International Mobile Equipment Identifier) do
     * dispositivo.
     * 
     * @return O IMEI do dispositivo.
     */
    public long getIMEI() {
		return this.imei;
    }

    /**
     * Define o IMEI (International Mobile Equipment Identifier) do
     * dispositivo.
     * 
     * @return void.
     */
    public void setIMEI(String imei) {

		if (imei != null) {
			this.imei = Long.parseLong(imei);
	
		}else {
	    	this.imei = Long.parseLong("356837024186111");			
		}
    }

    /**
     * Solicita o envio dos dados dos ultimos imoveis com atualizacoes pendentes
     * para o servidor.
     * 
     * @param listener
     *            Objeto que aguarda a resposta da operacao.
     */
    public void confirmarRecebimentoArquivo() {

		// cria o vetor de parametros
		Vector param = new Vector();
	
		// adiciona os parametros no vetor (na ordem correta)
		param.addElement(new Byte(PACOTE_CONFIRMAR_ARQUIVO_RECEBIDO));
	
		param.trimToSize();
		this.iniciarServicoRede(param, true);
		requestOK = MessageDispatcher.getRespostaServidor() == MessageDispatcher.RESPOSTA_OK;

    }

    /**
     * Solicita o arquivo com os cadastros do roteiro ao servidor.
     * 
     * @param login
     *            Objeto que aguarda a resposta da operação.
     */
    public void baixarRoteiro() {

		// cria o vetor de parâmetros
		Vector param = new Vector();
	
		// adiciona os parâmetros no vetor (na ordem correta)
		param.addElement(new Byte(PACOTE_BAIXAR_ROTEIRO));
	
		param.trimToSize();
		this.iniciarServicoRede(param, true);
		requestOK = MessageDispatcher.getRespostaServidor() == MessageDispatcher.RESPOSTA_OK;
    }

    /**
     * Envia o arquivo gerado do cadastro para o servidor
     * 
     * @param Array
     *            de bytes com o arquivo
     */
    public void enviarCadastro(byte[] cadastro) throws IOException {

		// cria o vetor de parâmetros
		Vector param = new Vector();
		ByteArrayOutputStream bais = new ByteArrayOutputStream();
		bais.write(PACOTE_ATUALIZAR_MOVIMENTO);
		bais.write(cadastro);
		param.addElement(bais.toByteArray());
		param.trimToSize();
		this.iniciarServicoRede(param, false);
		requestOK = MessageDispatcher.getRespostaServidor() == MessageDispatcher.RESPOSTA_OK;
    }

    /**
     * Envia o arquivo de finalização do cadastramento para o servidor
     * 
     * @param Array
     *            de bytes com o arquivo
     */
    public void finalizarCadastramento(byte[] arquivoRetorno, short tipoFinalizacao) throws IOException {

		// cria o vetor de parâmetros
		Vector param = new Vector();
		param.addElement(new Byte(PACOTE_FINALIZAR_CADASTRAMENTO));
		param.addElement(arquivoRetorno);
		param.trimToSize();
		this.iniciarServicoRede(param, true);
		requestOK = MessageDispatcher.getRespostaServidor() == MessageDispatcher.RESPOSTA_OK;

    }

    public boolean isRequestOK() {
    	return requestOK;
    }
    
}