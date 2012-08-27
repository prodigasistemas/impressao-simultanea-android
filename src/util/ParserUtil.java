package util;

/**
 * 
 * @author Daniel Zaccarias
 */
public class ParserUtil {

	private int contador = 0;
	private String fonte;

	private String conteudo;

	/**
	 * 
	 * @param fonte
	 */
	public ParserUtil(String fonte) {
		this.fonte = fonte;
	}

	/**
	 * 
	 * @param tamanho
	 * @return
	 */
	public String obterDadoParser(int tamanho) {
		String retorno = "";

		int posicaoInicial = contador;
		contador += tamanho;

		retorno = fonte.substring(posicaoInicial, contador);
		this.setConteudo(retorno);
		

		return retorno;

	}

	/**
	 * 
	 * @return
	 */
	public String getFonte() {
		return fonte;
	}

	/**
	 * 
	 * @param fonte
	 */
	public void setFonte(String fonte) {
		this.fonte = fonte;
	}

	/**
	 * 
	 * @return
	 */
	public int getContador() {
		return contador;
	}

	/**
	 * 
	 * @param contador
	 */
	public void setContador(int contador) {
		this.contador = contador;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

}
