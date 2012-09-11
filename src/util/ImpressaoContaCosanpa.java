package util;

import business.ControladorRota;
import model.Imovel;


public class ImpressaoContaCosanpa {

    private static ImpressaoContaCosanpa instancia;
    private static Imovel imovel;

    protected static ImpressaoContaCosanpa getInstancia(int imovelId) {
		if (instancia == null) {
		    instancia = new ImpressaoContaCosanpa();
		}
		return instancia;
    }

    
}
