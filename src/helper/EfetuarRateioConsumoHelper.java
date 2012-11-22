package helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import business.ControladorRota;

import model.Consumo;
import model.DadosCategoria;

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
    private int matriculaMacro;
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
    int passos = 0;
    
    public int getMatriculaMacro() {
		return matriculaMacro;
	}

	public void setMatriculaMacro(int matriculaMacro) {
		this.matriculaMacro = matriculaMacro;
	}

	public List<Integer> getIdsAindaFaltamSerCalculados() {
		
		List<Integer> listaIdsCondominio = ControladorRota.getInstancia().getDataManipulator().getListaIdsCondominio(matriculaMacro);
		List<Integer> listaIdsNaoCalculados = new ArrayList<Integer>();
		
		for (int i=1; i<listaIdsCondominio.size(); i++){
			
			int matriculaImovelCondominial = ControladorRota.getInstancia().getDataManipulator().selectMatriculaImovel("id = " + listaIdsCondominio.get(i));
			Consumo consumoAgua = ControladorRota.getInstancia().getDataManipulator().selectConsumoImovelByTipoMedicao(matriculaImovelCondominial, Constantes.LIGACAO_AGUA);
			Consumo consumoEsgoto = ControladorRota.getInstancia().getDataManipulator().selectConsumoImovelByTipoMedicao(matriculaImovelCondominial, Constantes.LIGACAO_POCO);
			
			if (consumoAgua == null && consumoEsgoto == null){
				
				listaIdsNaoCalculados.add(listaIdsCondominio.get(i));
			}
		}
		
		return listaIdsNaoCalculados;
	}
	
	public boolean isCondominioRetido(){
		boolean result = false;
		ArrayList<Integer> listIndcGeracaoConta = ControladorRota.getInstancia().getDataManipulator().selectIndcgeracaoContasCondominio(matriculaMacro);
		
		for (int i=0; i<listIndcGeracaoConta.size(); i++){
			if (listIndcGeracaoConta.get(i) == Constantes.NAO){
				result = true;
				break;
			}
		}
		return result;
	}

	public int getIdUltimoImovelMicro(){
		List<Integer> listaIdsCondominio = ControladorRota.getInstancia().getDataManipulator().getListaIdsCondominio(matriculaMacro);
		return listaIdsCondominio.get(listaIdsCondominio.size()-1);
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

    public EfetuarRateioConsumoHelper( int matriculaMacro){ 
    	this.matriculaMacro = matriculaMacro;
    }
    
    public EfetuarRateioConsumoHelper(){ 
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
    
}
