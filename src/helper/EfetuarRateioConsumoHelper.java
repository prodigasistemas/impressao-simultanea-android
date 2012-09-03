package helper;

import java.util.Vector;

import util.Constantes;

/**
 * [UC0970] Efetuar Rateio de Consumo no Dispositivo Movel
 * 
 * Metodo responsavel em efeturar a divisão da diferença entre o consumo
 * coletado no hidrometro macro e a soma dos hidrometros micro
 * 
 * Helper responsavel pela passagem de parametros entre os métodos do UC supra
 * citado.
 * 
 * @date 26/11/2009
 * @author Bruno Barros
 * 
 */
public class EfetuarRateioConsumoHelper {
    
    private long id;

    private int matriculaUltimoImovelMicro;

    int quantidadeEconomiasAguaTotal = 0;
    int consumoLigacaoAguaTotal = 0;

    int quantidadeEconomiasEsgotoTotal = 0;
    int consumoLigacaoEsgotoTotal = 0;

    // Geral
    int consumoMinimoTotal = 0;
    int consumoParaRateioAgua = 0;
    double contaParaRateioAgua = 0;

    int consumoParaRateioEsgoto = 0;
    double contaParaRateioEsgoto = 0;
    boolean reterImpressaoContas = false;
    int passos = 0;
    
    private Vector idsAindaFaltamSerCalculador;    
    
    public Vector getIdsAindaFaltamSerCalculador() {
        return idsAindaFaltamSerCalculador;
    }

    public int getConsumoParaRateioEsgoto() {
        return consumoParaRateioEsgoto;
    }

    public void setConsumoParaRateioEsgoto(int consumoParaRateioEsgoto) {
        this.consumoParaRateioEsgoto = consumoParaRateioEsgoto;
    }

    public double getContaParaRateioEsgoto() {
        return contaParaRateioEsgoto;
    }

    public void setContaParaRateioEsgoto(double contaParaRateioEsgoto) {
        this.contaParaRateioEsgoto = contaParaRateioEsgoto;
    }

    public int getPassos() {
	return passos;
    }

    public void setPassos(int passos) {
	this.passos = passos;
    }

    public int getConsumoParaRateioAgua() {
	return consumoParaRateioAgua;
    }

    public void setConsumoParaRateioAgua(int consumoParaRateioAgua) {
	this.consumoParaRateioAgua = consumoParaRateioAgua;
    }

    public double getContaParaRateioAgua() {
    	return contaParaRateioAgua;
	}

	public void setContaParaRateioAgua(double contaParaRateioAgua) {
    	this.contaParaRateioAgua = contaParaRateioAgua;
	}

	public int getQuantidadeEconomiasAguaTotal() {
		return quantidadeEconomiasAguaTotal;
    }

    public int getConsumoLigacaoAguaTotal() {
	return consumoLigacaoAguaTotal;
    }

    public int getQuantidadeEconomiasEsgotoTotal() {
    	return quantidadeEconomiasEsgotoTotal;
    }

    public int getConsumoLigacaoEsgotoTotal() {
	return consumoLigacaoEsgotoTotal;
    }

    public int getConsumoMinimoTotal() {
	return consumoMinimoTotal;
    }

    public long getMatriculaUltimoImovelMicro() {
	return matriculaUltimoImovelMicro;
    }

    public EfetuarRateioConsumoHelper( int matriculaMacro, int matriculaUltimoImovelMicro ){ 

    	// Daniel - cuidar do caso onde existe imovel cortado no condominio. Este deve ser removido da lista de IDs
		this.matriculaUltimoImovelMicro = matriculaUltimoImovelMicro;
		this.idsAindaFaltamSerCalculador = new Vector( (matriculaUltimoImovelMicro+1) - matriculaMacro );
		
		for ( int i = matriculaMacro; i <= matriculaUltimoImovelMicro; i++ ){
		    this.idsAindaFaltamSerCalculador.addElement( new Integer( i ) );
		}
    }
    
    public boolean getReterImpressaoConta(){
    	return this.reterImpressaoContas;
    }

    public void setQuantidadeEconomiasAguaTotal(int quantidadeEconomiasAguaTotal) {
    	this.quantidadeEconomiasAguaTotal = quantidadeEconomiasAguaTotal;
    }

    public void setConsumoLigacaoAguaTotal(int consumoLigacaoAguaTotal) {
        this.consumoLigacaoAguaTotal = consumoLigacaoAguaTotal;
    }

	public void setQuantidadeEconomiasEsgotoTotal(int quantidadeEconomiasEsgotoTotal) {
		this.quantidadeEconomiasEsgotoTotal = quantidadeEconomiasEsgotoTotal;
	}

    public void setConsumoLigacaoEsgotoTotal(int consumoLigacaoEsgotoTotal) {
        this.consumoLigacaoEsgotoTotal = consumoLigacaoEsgotoTotal;
    }

    public void setConsumoMinimoTotal(int consumoMinimoTotal) {
        this.consumoMinimoTotal = consumoMinimoTotal;
    }

    public void setId(int arg0) {
    	this.id = arg0;
    }
    
    public long getId(){
    	return id;
    }
    
    public void setReterImpressaoConta(int reter){
		if (reter == Constantes.SIM){
			this.reterImpressaoContas = true;
		
		}else{
			this.reterImpressaoContas = false;
		}
    }
    
}
