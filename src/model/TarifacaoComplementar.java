package model;

import java.util.Date;

public class TarifacaoComplementar {

	private int id;
	
	private int matricula;

	private int codigo;
	
	private Date dataInicioVigencia;
	
	private int codigoCategoria;
	
	private int codigoSubcategoria;
	
	private int limiteInicialFaixa;
	
	private int limiteFinalFaixa;
	
	private double valorM3Faixa;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Date getDataInicioVigencia() {
		return dataInicioVigencia;
	}

	public void setDataInicioVigencia(Date dataInicioVigencia) {
		this.dataInicioVigencia = dataInicioVigencia;
	}

	public int getCodigoCategoria() {
		return codigoCategoria;
	}

	public void setCodigoCategoria(int codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}

	public int getCodigoSubcategoria() {
		return codigoSubcategoria;
	}

	public void setCodigoSubcategoria(int codigoSubcategoria) {
		this.codigoSubcategoria = codigoSubcategoria;
	}

	public int getLimiteInicialFaixa() {
		return limiteInicialFaixa;
	}

	public void setLimiteInicialFaixa(int limiteInicialFaixa) {
		this.limiteInicialFaixa = limiteInicialFaixa;
	}

	public int getLimiteFinalFaixa() {
		return limiteFinalFaixa;
	}

	public void setLimiteFinalFaixa(int limiteFinalFaixa) {
		this.limiteFinalFaixa = limiteFinalFaixa;
	}

	public double getValorM3Faixa() {
		return valorM3Faixa;
	}

	public void setValorM3Faixa(double valorM3Faixa) {
		this.valorM3Faixa = valorM3Faixa;
	}
	
	

}
