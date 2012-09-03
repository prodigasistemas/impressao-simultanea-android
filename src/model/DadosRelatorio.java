package model;

import java.util.Vector;

import util.Constantes;

public class DadosRelatorio {

    public static DadosRelatorio instancia;

    private long id;

    public Vector idsLidosRelatorio =  new Vector();
    public Vector idsNaoLidosRelatorio =  new Vector();
    public Vector quadras = new Vector();

    public int quadraAnterior = Constantes.NULO_INT;
    public String valoresRelatorio = "{0000}[0000]";


    public DadosRelatorio() {
    }

    public void setId(long id) {
    	this.id = id;
    }

    public long getId() {
    	return id;
    }

    public static DadosRelatorio getInstancia() {

		if (DadosRelatorio.instancia == null) {
			DadosRelatorio.instancia = new DadosRelatorio();

		}
	
		return DadosRelatorio.instancia;
    }

}
