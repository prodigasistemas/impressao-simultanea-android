package background;

import java.util.List;

import model.Imovel;
import ui.ArquivoRetorno;
import util.Constantes;
import business.ControladorAcessoOnline;
import business.ControladorRota;

public class EnviarImoveisCondominioThread extends Thread {
	
	private List<Integer> idsImoveisCondominio;
	
	public EnviarImoveisCondominioThread(List<Integer> idsImoveisCondominio) {
		this.idsImoveisCondominio = idsImoveisCondominio;
	}
	
	public void run() {
		try {
			ArquivoRetorno.getInstancia().gerarDadosImoveisCondominioParaEnvio(idsImoveisCondominio);
			
			ControladorAcessoOnline.getInstancia().enviarCadastro(ArquivoRetorno.getInstancia().getConteudoArquivoRetorno().getBytes());
			
			if (ControladorAcessoOnline.getInstancia().isRequestOK()) {
				for (int id : idsImoveisCondominio) {
					Imovel imovel = ControladorRota.getInstancia().getDataManipulator().selectImovel("id = " + id, false);
					
					imovel.setIndcImovelEnviado(Constantes.SIM);
					ControladorRota.getInstancia().getDataManipulator().salvarImovel(imovel);
					System.out.println("Dentro IF");
				}
				
		    } else {
		    	System.out.println("Dentro ELSE");
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
