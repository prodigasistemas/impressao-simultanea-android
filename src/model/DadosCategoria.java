package model;

import util.Util;

public class DadosCategoria {
	
	private int id;
    private int codigoCategoria;
    private String descricaoCategoria;
    private String codigoSubcategoria;
    private String descricaoSubcategoria;
    private int qtdEconomiasSubcategoria;
    private DadosTarifa tarifa;
    private DadosFaturamento faturamentoAgua;
    private DadosFaturamento faturamentoEsgoto;
    private DadosFaturamento faturamentoAguaProporcional;
    private DadosFaturamento faturamentoEsgotoProporcional;
    private String descricaoAbreviadaCategoria;
    private String descricaoAbreviadaSubcategoria;
    private String fatorEconomiaCategoria;
    
    
  //CATEGORIAS
	public final static int RESIDENCIAL = 1;
	public final static int COMERCIAL = 2;
	public final static int INDUSTRIAL = 3;
	public final static int PUBLICO = 4;
    
	public DadosCategoria(){
	}
	
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public int getCodigoCategoria() {
        return codigoCategoria;
    }
    
    public void setCodigoCategoria(String codigoCategoria) {
		this.codigoCategoria = Util.verificarNuloInt( codigoCategoria );
	}

	public void setDescricaoCategoria(String descricaoCategoria) {
		this.descricaoCategoria = Util.verificarNuloString( descricaoCategoria );
	}

	public void setCodigoSubcategoria(String codigoSubcategoria) {
		this.codigoSubcategoria = Util.verificarNuloString( codigoSubcategoria );
	}

	public void setDescricaoSubcategoria(String descricaoSubcategoria) {
		this.descricaoSubcategoria = Util.verificarNuloString( descricaoSubcategoria );
	}

	public void setQtdEconomiasSubcategoria(String qtdEconomiasSubcategoria) {
		this.qtdEconomiasSubcategoria = Util.verificarNuloInt( qtdEconomiasSubcategoria );
	}

	public void setDescricaoAbreviadaCategoria(String descricaoAbreviadaCategoria) {
		this.descricaoAbreviadaCategoria = Util.verificarNuloString( descricaoAbreviadaCategoria );
	}

	public void setDescricaoAbreviadaSubcategoria(
			String descricaoAbreviadaSubcategoria) {
		this.descricaoAbreviadaSubcategoria = Util.verificarNuloString( descricaoAbreviadaSubcategoria );
	}

	public int getQtdEconomiasSubcategoria() {
        return qtdEconomiasSubcategoria;
    }
    
    public DadosTarifa getTarifa() {
        return this.tarifa;
    }
    
    public void setTarifa(DadosTarifa tarifa) {
        this.tarifa = tarifa;
    }

    public void setFaturamentoAgua(DadosFaturamento faturamento) {
        this.faturamentoAgua = faturamento;
    }
    
    public void setFaturamentoEsgoto(DadosFaturamento faturamento) {
        this.faturamentoEsgoto = faturamento;
    }
    
    public DadosFaturamento getFaturamentoAgua() {
        return this.faturamentoAgua;
    }
    
    public DadosFaturamento getFaturamentoEsgoto() {
        return this.faturamentoEsgoto;
    }

    public String getCodigoSubcategoria() {
        return codigoSubcategoria;
    }

    public String getDescricaoCategoria() {
        return descricaoCategoria;
    }

    public String getDescricaoSubcategoria() {
        return descricaoSubcategoria;
    }

    public String getDescricaoAbreviadaCategoria(){
        return descricaoAbreviadaCategoria;
    }

    public String getDescricaoAbreviadaSubcategoria(){
        return descricaoAbreviadaSubcategoria;
    }

    /**
     * @return the fatorEconomiaCategoria
     */
    public String getFatorEconomiaCategoria() {
        return fatorEconomiaCategoria;
    }

    /**
     * @param fatorEconomiaCategoria the fatorEconomiaCategoria to set
     */
    public void setFatorEconomiaCategoria(String fatorEconomiaCategoria) {
        this.fatorEconomiaCategoria = Util.verificarNuloString( fatorEconomiaCategoria );
    }    
    
	public double valorTotalTarifaFaixa(){
    	double soma = 0d;
    	
    	for ( int i = 0; i < this.faturamentoAgua.getFaixas().size(); i++ ){
    		
    		DadosFaturamentoFaixa dadosFaturamento = ( DadosFaturamentoFaixa ) this.faturamentoAgua.getFaixas().get( i );    		
    		
    		soma += 
    			( dadosFaturamento.getValorFaturado() * this.qtdEconomiasSubcategoria ); 
    	}
    	
		soma += 
			faturamentoAgua.getValorTarifaMinima();
    	
    	
    	return soma;		
	}
	
	public double consumoTotalTarifaFaixa(){
    	double soma = 0d;
    	
    	for ( int i = 0; i < this.faturamentoAgua.getFaixas().size(); i++ ){
    		
    		DadosFaturamentoFaixa dadosFaturamento = ( DadosFaturamentoFaixa ) this.faturamentoAgua.getFaixas().get( i );
    		
    		soma += 
    			( dadosFaturamento.getConsumoFaturado() * this.qtdEconomiasSubcategoria );
    	}
    	
		soma += 
			faturamentoAgua.getConsumoMinimo();    	
    	
    	return soma;		
	}


	public DadosFaturamento getFaturamentoAguaProporcional() {
		return faturamentoAguaProporcional;
	}


	public void setFaturamentoAguaProporcional(
			DadosFaturamento faturamentoAguaProporcional) {
		this.faturamentoAguaProporcional = faturamentoAguaProporcional;
	}


	public DadosFaturamento getFaturamentoEsgotoProporcional() {
		return faturamentoEsgotoProporcional;
	}


	public void setFaturamentoEsgotoProporcional(
			DadosFaturamento faturamentoEsgotoProporcional) {
		this.faturamentoEsgotoProporcional = faturamentoEsgotoProporcional;
	}
	
}
