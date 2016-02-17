package business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import model.Consumo;
import model.DadosCategoria;
import model.DadosFaturamento;
import model.DadosFaturamentoFaixa;
import model.DadosTarifa;
import model.Imovel;
import model.Tarifa;
import model.TarifacaoComplementar;
import model.TarifacaoMinima;
import util.Constantes;
import util.Util;
import views.MedidorAguaTab;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ControladorImovel {

    public static ControladorImovel instancia;

    private static Imovel imovelSelecionado = new Imovel();
    private static long idImovelSelecionado = 0;
    private static int imovelListPosition = -1;
    
    public static ControladorImovel getInstancia() {

    	if (ControladorImovel.instancia == null) {
			ControladorImovel.instancia = new ControladorImovel();

		}
		return ControladorImovel.instancia;
    }

    public Imovel getImovelSelecionado(){
    	return ControladorImovel.imovelSelecionado;
    }
    
    public void setImovelSelecionado(Imovel imovelSelecionado){
    	ControladorImovel.imovelSelecionado = imovelSelecionado;
    }
     
    public void setImovelSelecionadoByListPosition(int listPosition){
    	setImovelListPosition(listPosition);
    	idImovelSelecionado = getIdImovelSelecionado(listPosition, null);
    	instancia.setImovelSelecionado(ControladorRota.getInstancia().getDataManipulator().selectImovel("id = " + idImovelSelecionado, true));
    }
    
    public void setImovelSelecionadoByListPositionInConsulta(int listPositionInConsulta, String condition){
    	idImovelSelecionado = getIdImovelSelecionado(listPositionInConsulta, condition);
    	setImovelListPosition(getImovelListPositionById(idImovelSelecionado));

    	instancia.setImovelSelecionado(ControladorRota.getInstancia().getDataManipulator().selectImovel("id = " + idImovelSelecionado, true));
    }
    
    public void setImovelSelecionado(long id){
    	idImovelSelecionado = id;

    	instancia.setImovelSelecionado(ControladorRota.getInstancia().getDataManipulator().selectImovel("id = " + idImovelSelecionado, true));
    }
    
    public int getIdImovelSelecionado(int listPosition, String condition){
    	// se for cadastro novo
    	if (listPosition == -1){
    		return 0;
 
    	}else{
        	return Integer.parseInt(ControladorRota.getInstancia().getDataManipulator().selectIdImoveis(condition).get(listPosition));
     	}
    }
    
    public int getImovelListPositionById(long id){
    	int position = 0;
    	ArrayList<String> listIds = (ArrayList<String>) ControladorRota.getInstancia().getDataManipulator().selectIdImoveis(null);
    	
    	for(int i = 0; i < listIds.size(); i++){
    		if (id == Long.parseLong(listIds.get(i))){
    			position = i;
    			break;
    		}
      	}
    	return position;
    }
    
    public boolean isImovelAlterado(){
    	boolean result = false;
    	
    	// guarda instancia de cliente, imovel, medidor e servico 
//    	Cliente clienteEditado = clienteSelecionado;
//    	Imovel imovelEditado = imovelSelecionado;
//    	Servicos servicoEditado = servicosSelecionado;
//    	Medidor medidorEditado = medidorSelecionado;
//    	AnormalidadeImovel anormalidadeImovelEditado = anormalidadeImovelSelecionado;
//    	
//    	// atualiza as instancias clienteSelecionado, imovelSelecionado, servicoSelecionado e medidorSelecionado com os valores do banco de dados.
//    	dmImovel.selectCliente(idImovelSelecionado);
//    	dmImovel.selectImovel(idImovelSelecionado);
//    	dmImovel.selectServico(idImovelSelecionado);
//    	dmImovel.selectMedidor(idImovelSelecionado);
//    	dmImovel.selectAnormalidadeImovel(idImovelSelecionado);
    	
//    	if (clienteEditado != clienteSelecionado){
//    		result = true;
//    	
//    	}else if (imovelEditado != imovelSelecionado){
//    		result = true;
//    		
//    	}else if (servicoEditado != servicosSelecionado){
//    		result = true;
//    		
//    	}else if (medidorEditado != medidorSelecionado){
//    		result = true;
//    		
//    	}else if (anormalidadeImovelEditado != anormalidadeImovelSelecionado){
//    		result = true;
//    		
//    	}
    	
    	// Restaura os valores editados nas instancias selecionadas
    	if(result){
//        	clienteSelecionado = clienteEditado;
//        	imovelSelecionado = imovelEditado;
//        	servicosSelecionado = servicoEditado;
//        	medidorSelecionado  = medidorEditado;
//        	anormalidadeImovelSelecionado  = anormalidadeImovelEditado;
    	}
    	return result;
    }
    
    // Retorna o Id do imovel selecionado
    public long getIdImovelSelecionado(){
    	return idImovelSelecionado;
    }
    
    // Retorna a posição do imovel selecionado na lista de imoveis ordenada por inscrição
    public int getImovelListPosition(){
    	return imovelListPosition;
    }
    
    // Guarda a posição do imovel selecionado na lista de imoveis ordenada por inscrição
    public void setImovelListPosition(int position){
    	imovelListPosition = position;
//    	dataManipulator.updateConfiguracao("posicao_cadastro_selecionado", position);
    }
    
    public boolean tipoTarifaPorCategoria(Imovel imovel) {

		for (int i = 0; i < imovel.getTarifacoesMinimas().size(); i++) {
		    TarifacaoMinima tarifacaoMinima = (TarifacaoMinima) imovel.getTarifacoesMinimas().get(i);
	
		    if (tarifacaoMinima.getCodigoSubcategoria() == Constantes.NULO_INT) {
		    	return true;
		    }
		}
	
		return false;
    }

    /**
     * Calcula o consumo minimo do imovel. Inicialmente tentamos pesquisar por
     * subcategoria, e caso nao consigamos, pesquisamos por categorias.
     * 
     * @param imovel
     * @return consumo minimo do imovel
     */
    public int calcularConsumoMinimoImovel(Imovel imovel, Date dataInicioVigencia) {

		// Retorno
		int consumoMinimoImovel = 0;
	
		
		for (int i = 0; i < imovel.getDadosCategoria().size(); i++) {
			DadosCategoria dadosCategoria = (DadosCategoria) imovel.getDadosCategoria().get(i);
	
		    int codigoSubcategoria = 0;
		    
		    if (dadosCategoria.getCodigoSubcategoria() != null && !dadosCategoria.getCodigoSubcategoria().equals("")) {
		    	codigoSubcategoria = Integer.parseInt(dadosCategoria.getCodigoSubcategoria());
		    }
		    
		    boolean calculoPorSubcategoria = false;
		    
		    // Calculamos por subcategoria
		    for (int j = 0; j < imovel.getTarifacoesMinimas().size(); j++) {
	
		    	TarifacaoMinima tarifacaoMinima = (TarifacaoMinima) imovel.getTarifacoesMinimas().get(j);
	
				if (Util.compararData(dataInicioVigencia, tarifacaoMinima.getDataVigencia()) == 0
					&& imovel.getCodigoTarifa() == tarifacaoMinima.getCodigo()
					&& dadosCategoria.getCodigoCategoria() == tarifacaoMinima.getCodigoCategoria()
					&& codigoSubcategoria == tarifacaoMinima.getCodigoSubcategoria()) {
					
				    consumoMinimoImovel += tarifacaoMinima.getConsumoMinimoSubcategoria() * dadosCategoria.getQtdEconomiasSubcategoria();
				    calculoPorSubcategoria = true;
				    break;
				}
		    }
		    if(!calculoPorSubcategoria){
			    // Calculamos por categoria
			    for (int j = 0; j < imovel.getTarifacoesMinimas().size(); j++) {
		
			    	TarifacaoMinima tarifacaoMinima = (TarifacaoMinima) imovel.getTarifacoesMinimas().get(j);
		
				    if (Util.compararData(dataInicioVigencia, tarifacaoMinima.getDataVigencia()) == 0
					    && imovel.getCodigoTarifa() == tarifacaoMinima.getCodigo()
					    && dadosCategoria.getCodigoCategoria() == tarifacaoMinima.getCodigoCategoria()
					    && (tarifacaoMinima.getCodigoSubcategoria() == Constantes.NULO_INT || tarifacaoMinima.getCodigoSubcategoria() == 0)) {
						
				    	consumoMinimoImovel += tarifacaoMinima.getConsumoMinimoSubcategoria()* dadosCategoria.getQtdEconomiasSubcategoria();
						break;
				    }
				}
		    }
		}

	  return consumoMinimoImovel;
    }


    /**
     * [UC0743] Calcular Valores de Água/Esgoto
     */
    public void calcularValores(Imovel imovel, Consumo consumo,int tipoMedicao) {
    	Object[] dadosCalculo = this.deveAplicarCalculoSimples(imovel);
    	boolean calculoSimples = ((Boolean) dadosCalculo[0]).booleanValue();
    	Date[] dataInicioVigencia = (Date[]) dadosCalculo[1];
    	if (calculoSimples) {
    	    this.calculoSimples(imovel, consumo, tipoMedicao,
    		    dataInicioVigencia[0]);
    	} else {
    	    this.calculoProporcionalMaisUmaTarifa(imovel, consumo, tipoMedicao);
    	}

    }
    
    public Object[] deveAplicarCalculoSimples(Imovel imovel) {

		Object[] retorno = new Object[2];
		Boolean calculoSimples = Boolean.TRUE;
		int tamanho = 0;
		if (imovel.getTarifacoesMinimas() != null){
			tamanho = imovel.getTarifacoesMinimas().size();
	    }
		
		Date[] dataInicioVigencia = new Date[tamanho];
		
		int indiceData = 0;
		// TarifacaoMinima registro = ( TarifacaoMinima ) this.registrosTipo9.elementAt( i
		// );
		Date data0 = ((TarifacaoMinima) imovel.getTarifacoesMinimas().get(0))
			.getDataVigencia();
	
		dataInicioVigencia[indiceData] = data0;
	
		Date data1 = null;
		for (int i = 1; i < tamanho; i++) {
		    data1 = ((TarifacaoMinima) imovel.getTarifacoesMinimas().get(i))
			    .getDataVigencia();
	
		    if (Util.compararData(data0, data1) != 0) {
			calculoSimples = Boolean.FALSE;
			indiceData++;
			data0 = data1;
			dataInicioVigencia[indiceData] = data0;
		    }
		}
		retorno[0] = calculoSimples;
		retorno[1] = dataInicioVigencia;
		return retorno;
    }
    
    /**
     * [SB0001] - Cálculo Simples para uma Única Tarifa.
     */
    @SuppressWarnings("unchecked")
	private void calculoSimples(Imovel imovel, Consumo consumo,int tipoMedicao, Date dataInicioVigencia) {
    	
    	DadosFaturamentoFaixa faixaParaInclusao = null;
    	
		// 1. Verificamos se o tipo de calculo é por categoria ou por
		// subcategoria
		boolean tipoTarifaPorCategoria = ControladorImovel.getInstancia()
			.tipoTarifaPorCategoria(imovel);
	
		// Consumo minimo do imovel
		int consumoMinimoImovel = 0;
		int excessoImovel = 0;
	
		// 3. Caso o tipo de calculo da tarifa seja igual a 3
		if (imovel.getTipoCalculoTarifa() == Tarifa.TIPO_CALCULO_TARIFA_3) {
		    // 3.1.1 Calculamos o consumo minimo do imovel com a data de
		    // vigencia do primeiro
		    // dado tarifa que encontramos
	
		    //Novo método de calculo - condominio
		    if (imovel.getIndcCondominio() == Constantes.SIM && imovel.getMatriculaCondominio() == Constantes.NULO_INT){
		    	consumoMinimoImovel = 0;
		    }else{
		    	consumoMinimoImovel = ControladorImovel.getInstancia().calcularConsumoMinimoImovel(imovel, dataInicioVigencia);
		    }
	
		    // 3.1.2 Calculamos o consumo em excesso, onde
		    // retiramos o consumo a ser cobrado do mes o consumo
		    // minimo do imovel
		    Double consumoCobradoMes;
	
		    //Novo método de calculo - condominio
		    if (imovel.getIndcCondominio() == Constantes.SIM && imovel.getMatriculaCondominio() == Constantes.NULO_INT){
			    consumoCobradoMes = new Double(imovel.getEfetuarRateioConsumoHelper().getConsumoParaRateioAgua());
		    }else{
			    consumoCobradoMes = new Double(consumo.getConsumoCobradoMes());
		    }
		    excessoImovel = consumoCobradoMes.intValue() - consumoMinimoImovel;
		}
	
		// 4. Calculamos o consumo por economia
		int consumoPorEconomia;
	
		//Novo método de calculo - condominio
	    if (imovel.getIndcCondominio() == Constantes.SIM && imovel.getMatriculaCondominio() == Constantes.NULO_INT){
	    	consumoPorEconomia = imovel.getEfetuarRateioConsumoHelper().getConsumoParaRateioAgua() 
	    	/ imovel.getQuantidadeEconomiasTotal();;
	
	    }else{
	    	consumoPorEconomia = consumo.getConsumoCobradoMes()
			/ imovel.getQuantidadeEconomiasTotal();
	    }
		
		int consumoEconomiaCategoriaOuSubcategoria = 0;
		int consumoFaturadoCategoriaOuSubcategoria = 0;
	
		// 5. Para cada registro de Dados de Categoria
		for (int i = 0; i < imovel.getDadosCategoria().size(); i++) {
	
		    // Pegamos o regitro tipo 2 atual
		    DadosCategoria dadosEconomiasSubcategorias = (DadosCategoria) imovel
			    .getDadosCategoria().get(i);
	
		    // 5.1 Quantidade de economias para cada categoria ou subcategoria
		    int quantidadeEconomiasCategoriaSubCategoria;
	
		    if (!dadosEconomiasSubcategorias.getFatorEconomiaCategoria().trim()
			    .equals(Constantes.NULO_STRING)) {
			quantidadeEconomiasCategoriaSubCategoria = Integer
				.parseInt(dadosEconomiasSubcategorias
					.getFatorEconomiaCategoria().trim());
		    } else {
			if (tipoTarifaPorCategoria) {
			    quantidadeEconomiasCategoriaSubCategoria = imovel
				    .getQuantidadeEconomias(dadosEconomiasSubcategorias
					    .getCodigoCategoria(),
					    dadosEconomiasSubcategorias
						    .getCodigoSubcategoria());
			} else {
			    quantidadeEconomiasCategoriaSubCategoria = dadosEconomiasSubcategorias
				    .getQtdEconomiasSubcategoria();
			}
	
		    }
	
		    // 5.2 Seleciona as tarifas de consumo para cada categoria ou
		    // subcategoria do imovel
		    TarifacaoMinima dadosTarifa = null;
	
		    if (tipoTarifaPorCategoria) {
			dadosTarifa = imovel.pesquisarDadosTarifaImovel(
				tipoTarifaPorCategoria, dadosEconomiasSubcategorias
					.getCodigoCategoria()
					+ "", dadosEconomiasSubcategorias
					.getCodigoSubcategoria(), imovel
					.getCodigoTarifa(), dataInicioVigencia);
		    } else {
			dadosTarifa = imovel.pesquisarDadosTarifaImovel(
				tipoTarifaPorCategoria, dadosEconomiasSubcategorias
					.getCodigoCategoria()
					+ "", dadosEconomiasSubcategorias
					.getCodigoSubcategoria(), imovel
					.getCodigoTarifa(), dataInicioVigencia);
	
			if (dadosTarifa == null) {
			    dadosTarifa = imovel.pesquisarDadosTarifaImovel(true,
				    dadosEconomiasSubcategorias.getCodigoCategoria()
					    + "", dadosEconomiasSubcategorias
					    .getCodigoSubcategoria(), imovel
					    .getCodigoTarifa(), dataInicioVigencia);
			}
		    }
	
		    dadosEconomiasSubcategorias.setTarifa(new DadosTarifa(dadosTarifa));
	
		    double valorTarifaMinima =0;
		    int consumoMinimo = 0;
		    double valorPorEconomia = 0;
		    	
		    //Novo método de calculo - condominio
		    if (imovel.getIndcCondominio() == Constantes.SIM && imovel.getMatriculaCondominio() == Constantes.NULO_INT && consumoPorEconomia < 10){
		    	if (consumoPorEconomia > 0){
		    		
		    		double faixaInicial = ((TarifacaoComplementar)imovel.getTarifacoesComplementares().get(0)).getValorM3Faixa();
			    	valorTarifaMinima = faixaInicial / consumoPorEconomia;
			    	consumoMinimo = consumoPorEconomia;
		    	}
		    }else{
		    
			    // 5.3 Calcula os seguintes valores, da categoria ou subcategoria
			    // pesquisada.
			    // 5.3.1
			    valorTarifaMinima = dadosTarifa
				    .getTarifaMinimaCategoria()
				    * quantidadeEconomiasCategoriaSubCategoria;
		
			    // 5.3.2
			    consumoMinimo = dadosTarifa.getConsumoMinimoSubcategoria()
				    * quantidadeEconomiasCategoriaSubCategoria;
		
			    // 5.3.3
			    valorPorEconomia = dadosTarifa
				    .getTarifaMinimaCategoria();
		
			    // 5.3.4
			    if (consumoPorEconomia > dadosTarifa
				    .getConsumoMinimoSubcategoria()) {
				consumoEconomiaCategoriaOuSubcategoria = dadosTarifa
					.getConsumoMinimoSubcategoria();
			    } else {
				consumoEconomiaCategoriaOuSubcategoria = consumoPorEconomia;
			    }
		    }
		    int consumoExcedente = 0;
	
		    // 5.3.5
		    if (imovel.getTipoCalculoTarifa() == Tarifa.TIPO_CALCULO_TARIFA_3) {
			// 5.3.5.1
			if (excessoImovel > 0) {
			    // 5.3.5.1.1
			    consumoExcedente = (int) (excessoImovel * consumoMinimo)
				    / consumoMinimoImovel;
	
			    consumoExcedente = consumoExcedente
				    / quantidadeEconomiasCategoriaSubCategoria;
			}
		    } else {
			consumoExcedente = consumoPorEconomia
				- dadosTarifa.getConsumoMinimoSubcategoria();
	
		    }
	
		    List<DadosFaturamentoFaixa> faixasParaInclusao = new ArrayList<DadosFaturamentoFaixa>();
	
		    // 5.4
		        if (consumoExcedente > 0) {
		        // 5.4.2
		        	List<TarifacaoComplementar> faixas = null;
	
		        if (tipoTarifaPorCategoria) {
		            faixas = imovel.selecionarFaixasCalculoValorFaturado(
		                tipoTarifaPorCategoria, dadosEconomiasSubcategorias
		                    .getCodigoCategoria()
		                    + "",dadosEconomiasSubcategorias
		                    .getCodigoSubcategoria(), imovel.getCodigoTarifa(),
		                dataInicioVigencia);
		        } else {
		            faixas = imovel.selecionarFaixasCalculoValorFaturado(
		                tipoTarifaPorCategoria, dadosEconomiasSubcategorias.getCodigoCategoria()
		                + "", dadosEconomiasSubcategorias
		                .getCodigoSubcategoria(), imovel
		                    .getCodigoTarifa(), dataInicioVigencia);
	
		            if (faixas == null || faixas.isEmpty()) {
		            faixas = imovel.selecionarFaixasCalculoValorFaturado(
		                true, dadosEconomiasSubcategorias
		                    .getCodigoCategoria()
		                    + "",dadosEconomiasSubcategorias
		                    .getCodigoSubcategoria(),imovel.getCodigoTarifa(),
		                dataInicioVigencia);
		            }
		        }
	
			int consumoFaturadoFaixa = 0;
			double valorFaturadoFaixa = 0d;
			int limiteInicialConsumoFaixa = 0;
			int limiteFinalConsumoFaixa = 0;
			double valorTarifaFaixa = 0d;
			int limiteFaixaFimAnterior = 0;
			if (dadosTarifa.getConsumoMinimoSubcategoria() != Constantes.NULO_INT) {
			    limiteFaixaFimAnterior = dadosTarifa
				    .getConsumoMinimoSubcategoria();
			}
			// double valorEconomia = 0;
			// int consumoEconomia = 0;
	
			// 5.4.3
			if (imovel.getTipoCalculoTarifa() == Tarifa.TIPO_CALCULO_TARIFA_2) {
			    // 5.4.3.1
			    for (int j = 0; j < faixas.size(); j++) {
				TarifacaoComplementar faixa = (TarifacaoComplementar) faixas.get(j);
	
				if (consumoExcedente <= faixa
					.getLimiteFinalFaixa()
					&& consumoExcedente >= faixa
						.getLimiteInicialFaixa()) {
	
				    valorFaturadoFaixa = this
					    .calcularValorFaturadoFaixa(
						    consumoPorEconomia,
						    valorTarifaMinima, faixa
							    .getValorM3Faixa());
	
				    limiteInicialConsumoFaixa = faixa
					    .getLimiteInicialFaixa();
				    limiteFinalConsumoFaixa = faixa
					    .getLimiteFinalFaixa();
	
				    consumoFaturadoFaixa = faixa
					    .getLimiteFinalFaixa()
					    - limiteFaixaFimAnterior;
	
				     faixaParaInclusao = new DadosFaturamentoFaixa(
					    consumoFaturadoFaixa, valorFaturadoFaixa,
					    limiteInicialConsumoFaixa,
					    limiteFinalConsumoFaixa, faixa
						    .getValorM3Faixa());
				    
				    faixaParaInclusao.setTipoFaturamentoFaixa(tipoMedicao);
				    
	
				    faixasParaInclusao.add(faixaParaInclusao);
				    valorPorEconomia = valorFaturadoFaixa;
				    consumoEconomiaCategoriaOuSubcategoria = consumoFaturadoFaixa;
	
				    
				    break;
				}
			    }
			} else {
			    // 5.4.4
			    for (int j = 0; j < faixas.size() && consumoExcedente > 0; j++) {
				TarifacaoComplementar faixa = (TarifacaoComplementar) faixas.get(j);
	
				// 5.4.4.1.1
				consumoFaturadoFaixa = (faixa
					.getLimiteFinalFaixa() - limiteFaixaFimAnterior);
	
				// 5.4.4.1.2
				if (consumoExcedente < consumoFaturadoFaixa) {
				    consumoFaturadoFaixa = consumoExcedente;
				}
	
				// 5.4.4.1.3
				valorFaturadoFaixa = consumoFaturadoFaixa
					* faixa.getValorM3Faixa();
	
				// 5.4.4.1.4
				valorPorEconomia += valorFaturadoFaixa;
	
				// valorFaturadoFaixa = valorFaturadoFaixa *
				// quantidadeEconomiasCategoriaSubCategoria;
	
				// 5.4.4.1.5
	
				consumoEconomiaCategoriaOuSubcategoria = consumoEconomiaCategoriaOuSubcategoria
					+ consumoFaturadoFaixa;
	
				// 5.4.4.1.6
				limiteInicialConsumoFaixa = faixa
					.getLimiteInicialFaixa();
	
				// 5.4.4.1.7
				limiteFinalConsumoFaixa = faixa
					.getLimiteFinalFaixa();
	
				// 5.4.4.1.8
				valorTarifaFaixa = faixa.getValorM3Faixa();
	
				// 5.4.4.1.9
				consumoExcedente -= consumoFaturadoFaixa;
	
				faixaParaInclusao = new DadosFaturamentoFaixa(
					consumoFaturadoFaixa, valorFaturadoFaixa,
					limiteInicialConsumoFaixa,
					limiteFinalConsumoFaixa, valorTarifaFaixa);
				
				faixaParaInclusao.setTipoFaturamentoFaixa(tipoMedicao);
	
				faixasParaInclusao.add(faixaParaInclusao);
				// Recupera a faixa final da faixa anterior
				limiteFaixaFimAnterior = faixa
					.getLimiteFinalFaixa();
			    }
			}
		    }
	
		    // 5.5
		    double valorFaturado = 0d;
		    if (imovel.getTipoCalculoTarifa() == Tarifa.TIPO_CALCULO_TARIFA_2) {
			valorFaturado = valorPorEconomia;
	
		    } else {
			valorFaturado = valorPorEconomia
				* quantidadeEconomiasCategoriaSubCategoria;
		    }
	
		    // 5.6
		    if (imovel.getTipoCalculoTarifa() == Tarifa.TIPO_CALCULO_TARIFA_2) {
			consumoFaturadoCategoriaOuSubcategoria = consumoEconomiaCategoriaOuSubcategoria;
	
		    } else {
			consumoFaturadoCategoriaOuSubcategoria = consumoEconomiaCategoriaOuSubcategoria
				* quantidadeEconomiasCategoriaSubCategoria;
		    }
	
		    if (tipoMedicao == Constantes.LIGACAO_POCO) {
			valorFaturado = Util.arredondar(valorFaturado
				* (imovel.getPercentCobrancaEsgoto() / 100),2);
			valorTarifaMinima = Util.arredondar(valorTarifaMinima
				* (imovel.getPercentCobrancaEsgoto() / 100),2);
		    }
	
		    DadosFaturamento faturamento = new DadosFaturamento(valorFaturado,
			    consumoFaturadoCategoriaOuSubcategoria, valorTarifaMinima,
			    consumoMinimo, faixasParaInclusao);
		    
		 // =======================
		    faturamento.setIdDadosCategoria(dadosEconomiasSubcategorias.getId());
	    	faturamento.setTipoFaturamento(tipoMedicao);
	    	
	    	int posicao = imovel.getDadosCategoria().indexOf(dadosEconomiasSubcategorias);
	    	if (tipoMedicao == Constantes.TIPO_FATURAMENTO_AGUA) {
	    		imovel.getDadosCategoria().get(posicao).setFaturamentoAgua(faturamento);
	    	} else if (tipoMedicao == Constantes.TIPO_FATURAMENTO_ESGOTO) {
	    		imovel.getDadosCategoria().get(posicao).setFaturamentoEsgoto(faturamento);
	    	}
		    
	    	// ================
		    
		    if (tipoMedicao == ControladorConta.LIGACAO_AGUA) {
			dadosEconomiasSubcategorias.setFaturamentoAgua(faturamento);
		    } else {
			dadosEconomiasSubcategorias.setFaturamentoEsgoto(faturamento);
		    }
		}
    }
    
    /**
     * Calculamos o valor a ser faturado na faixa
     * 
     * @param consumoFaturado
     *            consumo que foi faturafo
     * @param valorTarifaMinimaCategoria
     *            Tarifa minima da categoria
     * @param valorTarifaFaixa
     *            Tarifa na faixa
     * @return
     */
    private double calcularValorFaturadoFaixa(int consumoFaturado, double valorTarifaMinimaCategoria, double valorTarifaFaixa) {

		// Legenda: x = consumoFaturado; NI = valorTarifaMinima
	
		double retorno = 0;
	
		int CONSUMO_SUPERIOR = 201;
	
		// Consumidores Taxados
		if (consumoFaturado < CONSUMO_SUPERIOR) {
	
		    double divisor = 10000;
		    int multiplicadorExp = 7;
	
		    // NI
		    if (consumoFaturado <= 10) {
		    	retorno = valorTarifaMinimaCategoria;
		    }
		    // NI(7x² + valorTarifaFaixa * x) / 10000
		    else {
	
				double parcial = consumoFaturado * consumoFaturado;
				parcial = parcial * multiplicadorExp;
		
				double parcial2 = valorTarifaFaixa * consumoFaturado;
		
				double parcialFinal = parcial + parcial2;
		
				parcialFinal = valorTarifaMinimaCategoria * parcialFinal;
		
				retorno = parcialFinal / divisor;
		    }
		}
		// Consumo Superior = NI(valorTarifaFaixa * x - 11,2)
		else {
	
		    double valor1 = 11.2;
	
		    double parcial = valorTarifaFaixa * consumoFaturado;
	
		    parcial = parcial - valor1;
	
		    retorno = valorTarifaMinimaCategoria * parcial;
		}
	
		return retorno;
    }

    /**
     * [UC0743] Calcular Valores de Água/Esgoto no Dispositivo Móvel [SB0002 –
     * Cálculo Proporcional Para Mais de Uma Tarifa]
     * 
     * @author Bruno Barros
     * @date 16/10/2009
     */
    public void calculoProporcionalMaisUmaTarifa(Imovel imovel, Consumo consumo, int tipoMedicao) {
		/*
		 * 1.O sistema seleciona as tarifas vigentes para o imóvel no período de
		 * leitura da seguinte forma: 1.1.Seleciona todas as ocorrências de
		 * registro 9 com código da tarifa igual ao código da tarifa do imóvel e
		 * com data de início da vigência entre as datas de leitura anterior e a
		 * data corrente, inclusive; 1.2.Caso não seja selecionada nenhuma
		 * ocorrência no item 1.1 ou caso nenhuma ocorrência selecionada tenha
		 * data de início da vigência=data de leitura anterior, selecionar
		 * também a ocorrência de registro 9 com código da tarifa igual ao
		 * código da tarifa do imóvel e com a maior data de início da vigência
		 * que seja menor que a data de leitura anterior. 1.3.As tarifas
		 * vigentes para o período de leitura serão as ocorrências selecionadas
		 * nos passos 1.1 e 1.2 e devem estar classificadas pela data de início
		 * da vigência. TODOS OS REGISTROS TIPO 9 PRESENTES NO IMOVEL JA
		 * RESPEITAM AS CONDIÇÕES SUPRECITADAS, LOGO APENAS SELECIONAMOS
		 */
    	ArrayList<List<TarifacaoMinima>> tarifacoesMinimasPorCategoria = imovel.getTarifacoesMinimasPorCategoria();
	
		// Selecionamos a data de leitura anterior dando prioridade ao ligação de água	
		Date dataLeituraAnterior = null;
		if (imovel.getMedidor(Constantes.LIGACAO_AGUA) != null && !imovel.getMedidor(Constantes.LIGACAO_AGUA).equals("")) {
			dataLeituraAnterior = imovel.getMedidor(Constantes.LIGACAO_AGUA).getDataLeituraAnteriorFaturada();
		} else {
			if (imovel.getMedidor(Constantes.LIGACAO_POCO) != null && !imovel.getMedidor(Constantes.LIGACAO_POCO).equals("")) {
			dataLeituraAnterior = imovel.getMedidor(Constantes.LIGACAO_POCO).getDataLeituraAnteriorFaturada();
			}
		}
	
		if (dataLeituraAnterior == null) {
			dataLeituraAnterior = imovel.getDataLeituraAnteriorNaoMedido();
		}

		Date dataLeituraAtual = Util.getDataSemHora(MedidorAguaTab.getCurrentDateByGPS());

		// 2.Calcula a quantidade de dias entre as leituras = data corrente - data de leitura anterior;
		long qtdDiasEntreLeituras = Util.obterModuloDiferencasDatasDias(dataLeituraAtual, dataLeituraAnterior);
		qtdDiasEntreLeituras += 1;
	
		DadosFaturamento dadosFaturamento;			
		// cria o objteto de faturamento proporcional para somar os valores por categoria de cada data de vigencia
		DadosFaturamento dadosFaturamentoProporcional;
		
		for (List<TarifacaoMinima> tarifacoesMinimas : tarifacoesMinimasPorCategoria) {
			
			// 3.Data da vigência inicial = data da leitura anterior
			Date dataVigenciaInicial = dataLeituraAnterior;
			
			// 4.Para cada tarifa vigente para o período de leitura, obtida no passo 1.3, ordenando por data de início da
			// vigência, o sistema efetua os seguintes procedimentos
			for (int i = 0; i < tarifacoesMinimas.size(); i++) {
				// 4.1.[SB0001 – Cálculo Simples Para Uma Única Tarifa];
				TarifacaoMinima reg9 = (TarifacaoMinima) tarifacoesMinimas.get(i);
				this.calculoSimples(imovel, consumo, tipoMedicao, reg9.getDataVigencia());
				
				// 4.2.Caso exista próxima tarifa vigente então data da vigência final = data de início da vigência da próxima
				// tarifa vigente menos um dia, caso contrário, data da vigência final = data corrente;
				Date dataVigenciaFinal = null;
				
				if (i < tarifacoesMinimas.size() - 1) {
					TarifacaoMinima proxReg9 = (TarifacaoMinima) tarifacoesMinimas.get(i + 1);
					System.out.println("Data REG 9" + Util.formatarData(proxReg9.getDataVigencia()));
					
					if (proxReg9.getDataVigencia().before(dataLeituraAtual) || proxReg9.getDataVigencia().equals(dataLeituraAtual)) {
						dataVigenciaFinal = Util.adicionarNumeroDiasDeUmaData(proxReg9.getDataVigencia(), -1);
					} else {
						dataVigenciaFinal = dataLeituraAtual;
					}
				} else {
					if (reg9.getDataVigencia().before(dataLeituraAtual) || reg9.getDataVigencia().equals(dataLeituraAtual)) {
						dataVigenciaFinal = dataLeituraAtual;
					} else {
						dataVigenciaFinal = reg9.getDataVigencia();
					}
				}
				
				// 4.3.Caso seja a primeira tarifa, a quantidade de dias de vigência da tarifa dentro do período
				// de leitura = data da vigência final – data da vigência inicial
				long qtdDiasVigenciaTarifaDentroPeriodoLeitura = Util.obterModuloDiferencasDatasDias(dataVigenciaFinal, dataVigenciaInicial);
				
				if (i == 0) {
					if (dataVigenciaFinal.after(dataVigenciaInicial) || dataVigenciaFinal.equals(dataVigenciaInicial)) {
						qtdDiasVigenciaTarifaDentroPeriodoLeitura += 1;
					} else {
						qtdDiasVigenciaTarifaDentroPeriodoLeitura = 0;
					}
					
					// 4.4.Caso contrário a quantidade de dias de vigência da tarifa dentro do período de
					// leitura = data da vigência final – data da vigência inicial + 1 dia;
				} else {
					if (dataVigenciaFinal.before(dataLeituraAtual) || dataVigenciaFinal.equals(dataLeituraAtual)) {
						qtdDiasVigenciaTarifaDentroPeriodoLeitura += 1;
					} else {
						qtdDiasVigenciaTarifaDentroPeriodoLeitura = 0;
					}
				}
				
				// 4.5.Calcula o fator de vigência da tarifa = quantidade de dias de vigência da tarifa no período de
				// leitura / quantidade de dias entre as leituras;
				double fatorVigenciaTarifa = Util.arredondar((double) qtdDiasVigenciaTarifaDentroPeriodoLeitura / (double) qtdDiasEntreLeituras, 4);
				
				/*
				 * 1.1.Para cada Categoria, aplica o fator de vigência da tarifa
				 * sobre os seguintes atributos obtidos nos cálculos efetuados no
				 * passo 4.1, arredondando para duas casas decimais: 1.1.1.Valor
				 * faturado; 1.1.2.Valor da tarifa mínima; 1.1.3.Para cada faixa da
				 * tarifa de consumo: 1.1.3.1.Valor faturado na faixa; 1.1.3.2.Valor
				 * da tarifa na faixa.
				 */
				for (int j = 0; j < imovel.getDadosCategoria().size(); j++) {
					
					DadosCategoria reg2 = (DadosCategoria) imovel.getDadosCategoria().get(j);
					
					if (tipoMedicao == Constantes.LIGACAO_AGUA) {
						dadosFaturamento = reg2.getFaturamentoAgua();
						dadosFaturamentoProporcional = reg2.getFaturamentoAguaProporcional();
					} else {
						dadosFaturamento = reg2.getFaturamentoEsgoto();
						dadosFaturamentoProporcional = reg2.getFaturamentoEsgotoProporcional();
					}
					
					if (dadosFaturamentoProporcional == null
							|| dadosFaturamentoProporcional.equals("") || i == 0) {
						dadosFaturamentoProporcional = new DadosFaturamento();
						dadosFaturamentoProporcional.setValorFaturado(0d);
						dadosFaturamentoProporcional.setValorTarifaMinima(0d);
					}
					
					double valorFaturadoPorFator = dadosFaturamentoProporcional.getValorFaturado();
					valorFaturadoPorFator = valorFaturadoPorFator + Util.arredondar(dadosFaturamento.getValorFaturado() * fatorVigenciaTarifa, 2);
					
					double valorTarifaMinimaPorFator = dadosFaturamentoProporcional.getValorTarifaMinima();
					valorTarifaMinimaPorFator = valorTarifaMinimaPorFator + Util.arredondar(dadosFaturamento.getValorTarifaMinima() * fatorVigenciaTarifa, 2);
					
					// seta os valores adicionados para os dados de faturamento proporcional
					dadosFaturamentoProporcional.setValorFaturado(valorFaturadoPorFator);
					dadosFaturamentoProporcional.setValorTarifaMinima(valorTarifaMinimaPorFator);
					
					// seta os dados do faturamento proporcional no dado de faturamento
					dadosFaturamento.setValorFaturado(valorFaturadoPorFator);
					dadosFaturamento.setValorTarifaMinima(valorTarifaMinimaPorFator);
					
					Vector faixasProporcional = new Vector();
					
					for (int k = 0; k < dadosFaturamento.getFaixas().size(); k++) {
						DadosFaturamentoFaixa faixa = (DadosFaturamentoFaixa) dadosFaturamento.getFaixas().get(k);
						DadosFaturamentoFaixa faixaProporcional = null;
						
						if (dadosFaturamentoProporcional.getFaixas() == null || dadosFaturamentoProporcional.getFaixas().equals("") || i == 0) {
							faixaProporcional = new DadosFaturamentoFaixa();
							faixaProporcional.setValorFaturado(0d);
							faixaProporcional.setValorTarifa(0d);
						} else {
							faixaProporcional = (DadosFaturamentoFaixa) dadosFaturamentoProporcional.getFaixas().get(k);
						}
						
						double valorFaturadoPorFatorNaFaixa = faixaProporcional.getValorFaturado();
						valorFaturadoPorFatorNaFaixa = valorFaturadoPorFatorNaFaixa + Util.arredondar(faixa.getValorFaturado() * fatorVigenciaTarifa, 2);
						
						double valorTarifaPorFatorNaFaixa = faixaProporcional.getValorTarifa();
						valorTarifaPorFatorNaFaixa = valorTarifaPorFatorNaFaixa + Util.arredondar(faixa.getValorTarifa() * fatorVigenciaTarifa, 2);
						
						// seta os valores adicionados para os dados de faturamento proporcional
						faixaProporcional.setValorFaturado(valorFaturadoPorFatorNaFaixa);
						faixaProporcional.setValorTarifa(valorTarifaPorFatorNaFaixa);
						faixasProporcional.addElement(faixaProporcional);
						
						// seta os dados do faturamento proporcional no dado de faturamento
						faixa.setValorFaturado(valorFaturadoPorFatorNaFaixa);
						faixa.setValorTarifa(valorTarifaPorFatorNaFaixa);
					}
					
					// seta as faixas proporcionais no dado de faturamento proporcional
					dadosFaturamentoProporcional.setFaixas(faixasProporcional);
					if (tipoMedicao == Constantes.LIGACAO_AGUA) {
						reg2.setFaturamentoAguaProporcional(dadosFaturamentoProporcional);
					} else {
						reg2.setFaturamentoEsgotoProporcional(dadosFaturamentoProporcional);
					}
				}
				
				// 4.8.Calcula data da vigência inicial = data da vigência final + 1 dia.
				dataVigenciaFinal = Util.adicionarNumeroDiasDeUmaData(dataVigenciaFinal, +1);
				
				dataVigenciaInicial = dataVigenciaFinal;
			}
		}
		
	}

    public boolean isPrintingAllowed(){
	    boolean habilitaOpcaoImpressao = true;
	    
	    // Se não for imovel condominial com leitura individualizada ou informativo
	    if (getImovelSelecionado().isImovelCondominio() || getImovelSelecionado().isImovelInformativo()) {

	    	habilitaOpcaoImpressao = false;
	    }

    	return habilitaOpcaoImpressao;
    }
    
    public boolean isUltimoImovelCondominio(){
	    boolean ultimoImovelCondominio = false;
	    

    	return ultimoImovelCondominio;
    }

    /**
     * 
     * Verifica se o imóvel é do tipo informativo.
     * 
     * @author Daniel Zaccarias
     * @date 03/07/2011
     * 
     * @param  
     * @return
     */
    public boolean isImovelInformativo(int indcParalizarFaturamentoAgua, 
    								   int indcParalizarFaturamentoEsgoto,
    								   int numeroConta,
    								   String situacaoLigacaoAgua){
    	boolean informativo = true;
    	boolean paralizarFaturamento = false;
    	
    	if (indcParalizarFaturamentoAgua == Constantes.SIM  || 
    		indcParalizarFaturamentoEsgoto == Constantes.SIM){
    		
    		paralizarFaturamento = true;
    	}
    	
    	if( (numeroConta != Constantes.NULO_INT) || 
    		(numeroConta == Constantes.NULO_INT && paralizarFaturamento && situacaoLigacaoAgua.equals(Constantes.LIGADO)) ){
  
     		informativo = false;
     	}
     	
    	return informativo;
    }
    
	public void setupDataAfterPrinting(int impressaoTipo){
		
		if (impressaoTipo == Constantes.IMPRESSAO_FATURA || impressaoTipo == Constantes.IMPRESSAO_FATURA_E_NOTIFICACAO ){
			getImovelSelecionado().setIndcImovelImpresso(Constantes.SIM);

	    	getImovelSelecionado().setQuantidadeContasImpressas(1+(getImovelSelecionado().getQuantidadeContasImpressas()));
//	    	getImovelSelecionado().getValoresContasImpressas().addElement(String.valueOf(.getImovelSelecionado().getValorConta()));
	    	
	    	System.out.println("Quantidade de vezes impressas: " + getImovelSelecionado().getQuantidadeContasImpressas());

	    	// Guarda a data da impressao da conta de imovel nao-medido. Já que não possui data de leitura.
	    	if (getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) == null && 
	    		getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) == null){
		    	
	    		getImovelSelecionado().setDataImpressaoNaoMedido(Util.dateToAnoMesDiaString(MedidorAguaTab.getCurrentDateByGPS()));
	    	}
	    	Log.i(" Imovel Selecionado", String.valueOf(getImovelSelecionado().getMatricula()));
		}
    	
    	// Define imovel como concluido
    	getImovelSelecionado().setImovelStatus(Constantes.IMOVEL_STATUS_CONCLUIDO);
    	ControladorRota.getInstancia().getDataManipulator().salvarImovel(getImovelSelecionado());
	}

	public int getImpressaoTipo(Context context){

		int impressaoTipo = Constantes.IMPRESSAO_NAO_PERMITIDA;
		
		if (BusinessConta.getInstancia(context).isImpressaoPermitida()){
			
//			if (getImovelSelecionado().getContas() != null && getImovelSelecionado().getContas().size() > 0 ){
//				impressaoTipo = Constantes.IMPRESSAO_FATURA_E_NOTIFICACAO;
//			}else{
				impressaoTipo = Constantes.IMPRESSAO_FATURA;				
//			}
			
		} else{
			
//			if (getImovelSelecionado().getContas() != null && getImovelSelecionado().getContas().size() > 0){
//				impressaoTipo = Constantes.IMPRESSAO_NOTIFICACAO_DEBITO;
//				
//			}else{
				impressaoTipo = Constantes.IMPRESSAO_NAO_PERMITIDA;
				Toast.makeText(context, BusinessConta.getInstancia().getMensagemPermiteImpressao(), Toast.LENGTH_LONG).show();
//			}
		}
		
		return impressaoTipo;
	}

}
