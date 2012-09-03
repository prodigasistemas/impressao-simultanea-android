package model;

import util.Util;

public class Imposto {

    private int tipoImposto;
    private String descricaoImposto;
    private double percentualAlicota;

	public Imposto(){
	}    

    public int getTipoImposto() {
		return tipoImposto;
	}

	public void setTipoImposto(String tipoImposto) {
		this.tipoImposto = Util.verificarNuloInt( tipoImposto );
	}

	public String getDescricaoImposto() {
		return descricaoImposto;
	}

	public void setDescricaoImposto(String descricaoImposto) {
		this.descricaoImposto =Util.verificarNuloString( descricaoImposto );
	}

	public double getPercentualAlicota() {
		return percentualAlicota;
	}

	public void setPercentualAlicota(String percentualAlicota) {
		this.percentualAlicota = Util.verificarNuloDouble( percentualAlicota );
	}
}
