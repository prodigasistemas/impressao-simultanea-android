package model;

import java.util.Date;

import util.Util;

public class Conta {

    private String anoMesReferenciaConta;
    private double valorConta;
    private Date dataVencimentoConta;
    private double valorAcrescimosImpontualidade;
    
	public Conta(){
	}    
	
    public String getAnoMes() {
        return anoMesReferenciaConta;
    }

    public Date getDataVencimento() {
        return dataVencimentoConta;
    }

    public double getValor() {
        return valorConta;
    }

    public double getValorAcrescimosImpontualidade() {
        return valorAcrescimosImpontualidade;
    }

    public void setAnoMes(String anoMes) {
        this.anoMesReferenciaConta = anoMes;
    }

    public void setDataVencimento(String dataVencimentoConta) {
        this.dataVencimentoConta = Util.getData( Util.verificarNuloString( dataVencimentoConta ) );
    }

    public void setValor(String valor) {
        this.valorConta = Util.verificarNuloDouble( valor );
    }

    public void setValorAcrescimosImpontualidade(String valorAcrescimosImpontualidade) {
        this.valorAcrescimosImpontualidade = Util.verificarNuloDouble( valorAcrescimosImpontualidade );
    }

}
