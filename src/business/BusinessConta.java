/*
 * Copyright (C) 2007-2009 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Clêudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flêvio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Roberto Souza
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 * Daniel Canova Zaccarias
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implêcita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */
package business;

import model.Anormalidade;
import model.Consumo;
import model.Imovel;
import util.Constantes;
import util.Util;
import views.MedidorAguaTab;
import views.MedidorPocoTab;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class BusinessConta {

    private boolean leituraInvalida = false;
    private static final int ANORMALIDADE_CALCULO_MEDIA = 90;
    private static BusinessConta instancia;
    private static Context activityContext;
	AlertDialog dialog;


    /**
     * Metodo responsavel por chamar o calculo do consumo
     */
    public Consumo chamarCalculoConsumo(boolean salvarImovel) {

	if (getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) != null) {
		getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).setLeitura(MedidorAguaTab.getLeitura());
	    Anormalidade anormalidade = ControladorRota.getInstancia().getDataManipulator().selectAnormalidadeByCodigo(MedidorAguaTab.getCodigoAnormalidade(), true);

	    if(anormalidade != null){
	    	getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).setAnormalidade((anormalidade.getCodigo()));
	    }
	}

	if (getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) != null) {
		getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).setLeitura(MedidorPocoTab.getLeitura());
	    Anormalidade anormalidade = ControladorRota.getInstancia().getDataManipulator().selectAnormalidadeByCodigo(MedidorPocoTab.getCodigoAnormalidade(), true);
//	    Daniel
	    if(anormalidade != null){
	    	getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).setAnormalidade((anormalidade.getCodigo()));
	    }
	}

//	if (AbaAnormalidade.getInstancia().getAnormalidade() != null) {
//	    if (AbaAnormalidade.getInstancia().getAnormalidade().getSelectedIndex() != -1) {
//	    	Anormalidade anormalidade = (Anormalidade) AbaAnormalidade.getInstancia().getAnormalidade().getSelectedItem();
//	    	// Daniel
//	    	if(anormalidade != null){
//	    		getImovelSelecionado().setAnormalidadeSemHidrometro(anormalidade.getCodigo());
//	    	}
//	    }
//	}
//
	Consumo[] consumos = ControladorConta.getInstancia().calcularContaConsumo();
	Consumo consumoAguaRetorno = consumos[0];
	Consumo consumoEsgotoRetorno = consumos[1];
	Consumo retorno = null;

	getImovelSelecionado().setIndcImovelCalculado(Constantes.SIM);	
	getImovelSelecionado().atualizarResumoEfetuarRateio(consumoAguaRetorno, consumoEsgotoRetorno);

	if (consumoAguaRetorno != null) {
		getImovelSelecionado().setConsumoAgua(consumoAguaRetorno);
	    retorno = getImovelSelecionado().getConsumoAgua();
	}

	if (consumoEsgotoRetorno != null) {
		getImovelSelecionado().setConsumoEsgoto(consumoEsgotoRetorno);
	    if (consumoAguaRetorno == null) {
		retorno = getImovelSelecionado().getConsumoEsgoto();
	    }
	}

	consumoAguaRetorno = null;	
	consumoEsgotoRetorno = null;
	consumos = null;	

	if(getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) != null){
		getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).setLeituraRelatorio(getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).getLeitura());
		getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).setAnormalidadeRelatorio(getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).getAnormalidade());
	}
	
	if(getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) != null){
		getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).setLeituraRelatorio(getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).getLeitura());
		getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).setAnormalidadeRelatorio(getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).getAnormalidade());
	}
	if (salvarImovel){
		Repositorio.salvarObjeto(getImovelSelecionado());		
	}

	return retorno;
	}

    /**
     * Metodo responsavel por chamar o calculo do consumo desconsiderando a leitura e anormalidade adicionada pelo usuario
     */
    public Consumo CalculoConsumoDescartaLeitura() {
   
    	Consumo[] consumos = ControladorConta.getInstancia().calcularContaConsumo();
		Consumo consumoAguaRetorno = consumos[0];
		Consumo consumoEsgotoRetorno = consumos[1];
		Consumo retorno = null;
	
		getImovelSelecionado().setIndcImovelCalculado(Constantes.SIM);	
		getImovelSelecionado().atualizarResumoEfetuarRateio(consumoAguaRetorno, consumoEsgotoRetorno);
	
		if (consumoAguaRetorno != null) {
			getImovelSelecionado().setConsumoAgua(consumoAguaRetorno);
		    retorno = getImovelSelecionado().getConsumoAgua();
		}
	
		if (consumoEsgotoRetorno != null) {
			getImovelSelecionado().setConsumoEsgoto(consumoEsgotoRetorno);
		    if (consumoAguaRetorno == null) {
		    	retorno = getImovelSelecionado().getConsumoEsgoto();
		    }
		}
		
		if(getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) != null){
			getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).setLeituraRelatorio(getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).getLeitura());
			getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).setAnormalidadeRelatorio(getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).getAnormalidade());
		}
		
		if(getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) != null){
			getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).setLeituraRelatorio(getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).getLeitura());
			getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).setAnormalidadeRelatorio(getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).getAnormalidade());
		}
		
		Repositorio.salvarObjeto(getImovelSelecionado());		
	    
		consumoAguaRetorno = null;	
		consumoEsgotoRetorno = null;
		consumos = null;	
	
		return retorno;
    }

    /**
     * Metodo responsavel por chamar o calculo do consumo pelo consumo médio.
     */
    public Consumo chamarCalculoConsumoMedio(boolean salvarImovel) {

		if (getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) != null) {
			getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).setLeitura(MedidorAguaTab.getLeitura());

		    Anormalidade anormalidade = ControladorRota.getInstancia().getDataManipulator().selectAnormalidadeByCodigo(String.valueOf(ANORMALIDADE_CALCULO_MEDIA), true);

		    if(anormalidade != null){
		    	getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).setAnormalidade((anormalidade.getCodigo()));
		    }
		}
	
		if (getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) != null) {
			getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).setLeitura(MedidorPocoTab.getLeitura());

		    Anormalidade anormalidade = ControladorRota.getInstancia().getDataManipulator().selectAnormalidadeByCodigo(String.valueOf(ANORMALIDADE_CALCULO_MEDIA), true);

		    if(anormalidade != null){
		    	getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).setAnormalidade((anormalidade.getCodigo()));
		    }
		}
	
    	Consumo[] consumos = ControladorConta.getInstancia().calcularContaConsumo();
		Consumo consumoAguaRetorno = consumos[0];
		Consumo consumoEsgotoRetorno = consumos[1];
		Consumo retorno = null;
	
		getImovelSelecionado().setIndcImovelCalculado(Constantes.SIM);	
		getImovelSelecionado().atualizarResumoEfetuarRateio(consumoAguaRetorno, consumoEsgotoRetorno);
	
		if (consumoAguaRetorno != null) {
			getImovelSelecionado().setConsumoAgua(consumoAguaRetorno);
		    retorno = getImovelSelecionado().getConsumoAgua();
		}
	
		if (consumoEsgotoRetorno != null) {
			getImovelSelecionado().setConsumoEsgoto(consumoEsgotoRetorno);
		    if (consumoAguaRetorno == null) {
			retorno = getImovelSelecionado().getConsumoEsgoto();
		    }
		}
		
		if(getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) != null){
			getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).setLeituraRelatorio(getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).getLeitura());
			getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).setAnormalidadeRelatorio(getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).getAnormalidade());
		}
		
		if(getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) != null){
			getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).setLeituraRelatorio(getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).getLeitura());
			getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).setAnormalidadeRelatorio(getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).getAnormalidade());
		}
		if (salvarImovel){
			Repositorio.salvarObjeto(getImovelSelecionado());		
		}
	
		consumoAguaRetorno = null;	
		consumoEsgotoRetorno = null;
		consumos = null;
	return retorno;
    }

    /**
     * Metodo responsavel por calcular e imprimir a conta
     * 
     * @return confirmacao
     */
    public boolean imprimirCalculo(boolean salvarImovel, boolean descartaLeitura) {

		leituraInvalida = false;
		Consumo validacao = null;
		
		// Se nao for rota toda calculada pela média ou não descarta leitura e anormalidade informada pelo usário
		if (ControladorRota.getInstancia().getDadosGerais().getIdCalculoMedia() == Constantes.NAO && !descartaLeitura){
			
			if ( !getImovelSelecionado().getSituacaoLigAgua().equals(Constantes.CORTADO)){
			
				if (getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) != null && 
					getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) == null) {
				    
					String leituraTexto = MedidorAguaTab.getLeituraCampo();
				    Anormalidade anormalidade = ControladorRota.getInstancia().getDataManipulator().selectAnormalidadeByCodigo(MedidorAguaTab.getCodigoAnormalidade(), true);
				    leituraInvalida = ValidacaoLeitura.getInstancia().validarLeituraAnormalidade( leituraTexto, anormalidade, Constantes.LIGACAO_AGUA );

				} else if (getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) != null && 
						   getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) == null) {
				    
					String leituraTexto = MedidorPocoTab.getLeituraCampo();
			    	Anormalidade anormalidade = ControladorRota.getInstancia().getDataManipulator().selectAnormalidadeByCodigo(MedidorPocoTab.getCodigoAnormalidade(), true);
			    	leituraInvalida = ValidacaoLeitura.getInstancia().validarLeituraAnormalidade( leituraTexto, anormalidade, Constantes.LIGACAO_AGUA );

				} else if (getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) != null && 
						   getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) != null) {
				    
					String leituraTextoAgua = MedidorAguaTab.getLeituraCampo();
				    String leituraTextoPoco = MedidorPocoTab.getLeituraCampo();

				    Anormalidade anormalidadeAgua = ControladorRota.getInstancia().getDataManipulator().selectAnormalidadeByCodigo(MedidorAguaTab.getCodigoAnormalidade(), true);
			    	Anormalidade anormalidadePoco = ControladorRota.getInstancia().getDataManipulator().selectAnormalidadeByCodigo(MedidorPocoTab.getCodigoAnormalidade(), true);

				    leituraInvalida = ValidacaoLeitura.getInstancia().validarLeituraAnormalidade( leituraTextoAgua , anormalidadeAgua, Constantes.LIGACAO_AGUA);	    

				    if ( !leituraInvalida ){
				    	leituraInvalida = ValidacaoLeitura.getInstancia().validarLeituraAnormalidade( leituraTextoPoco , anormalidadePoco, Constantes.LIGACAO_POCO);
				    }
				}
			}
		}
	
		if (!leituraInvalida) {
	
			if (descartaLeitura){
				
				validacao = BusinessConta.instancia.CalculoConsumoDescartaLeitura();			
			
			}else if(ControladorRota.getInstancia().getDadosGerais().getIdCalculoMedia() == Constantes.SIM){
				
				validacao = BusinessConta.instancia.chamarCalculoConsumoMedio(salvarImovel);			
			
			}else{
				validacao = BusinessConta.instancia.chamarCalculoConsumo(salvarImovel);			
			}
		    
			double valorTotalConta = getImovelSelecionado().getValorConta();
	
			if(!descartaLeitura){
			    String mensagemConsumo = "";
			    mensagemConsumo = Util.validarAnormalidadeConsumo(validacao);
			    System.out.println("Consumo = " + mensagemConsumo);
			    if (mensagemConsumo != null && !mensagemConsumo.equals("")) {
					if (valorTotalConta != Constantes.NULO_DOUBLE) {
					    System.out.println("Valor = " + valorTotalConta);
					    BusinessConta.mensagemConsumo(mensagemConsumo, valorTotalConta);
					}
			    }
			    mensagemConsumo = null;	    
			}
		
		}else{
			
			AlertDialog.Builder a = new AlertDialog.Builder(instancia.activityContext);
			a.setTitle("Aviso");
			a.setMessage(ValidacaoLeitura.getInstancia().getReturnMessage());
			
			a.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {}
			});

			a.show();
		}
		
		// Daniel - setting variables to null	
		validacao = null;
		
		return leituraInvalida;
    }
    
    /**
     * Metodo responsavel por retornar a instancia do objeto BussinessConta
     * 
     * @return instancia
     */
    public static BusinessConta getInstancia(Context context) {

    	if (BusinessConta.instancia == null) {
    		BusinessConta.instancia = new BusinessConta();
		}

    	BusinessConta.instancia.activityContext = context;
		return BusinessConta.instancia;
    }

    public static void mensagemConsumo(String mensagem, double valor) {
//	Label mensagemConsumo = null;
//	Label valorConsumo = null;
//
//	mensagemConsumo = new Label(mensagem);
//	mensagemConsumo.getStyle().setMargin(0, 0, 0, 0);
//	mensagemConsumo.getStyle().setPadding(0, 0, 0, 0);
//
//	valorConsumo = new Label("Valor: " + valor);
//	valorConsumo.getStyle().setMargin(0, 0, 0, 0);
//	valorConsumo.getStyle().setPadding(0, 0, 0, 0);
//
//	voltar = new Command("Voltar");
//	confirmar = new Command("Confirmar");
//
//	dialogConsumo = new Dialog("Consumo");
//	dialogConsumo.setCommandListener(BusinessConta.getInstancia());
//	dialogConsumo.getTitleStyle().setBgTransparency(200);
//	dialogConsumo.getDialogStyle().setBgTransparency(200);
//	dialogConsumo.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
//	dialogConsumo.addComponent(mensagemConsumo);
//	dialogConsumo.addComponent(valorConsumo);
//	dialogConsumo.addCommand(voltar);
//	dialogConsumo.addCommand(confirmar);
//	dialogConsumo.show();
    }

//    public void actionPerformed(ActionEvent evt) {
//
//	if (evt.getCommand() == voltar) {
//	    getImovelSelecionado().setIndcImovelCalculado(Constantes.NAO);
//	    Repositorio.salvarObjeto(getImovelSelecionado());
//	    dialogConsumo.setTimeout(1);
//	    leituraInvalida = true;
//	} else if (evt.getCommand() == confirmar) {
//	    leituraInvalida = false;
//	}
//    }

    private Imovel getImovelSelecionado(){
    	return ControladorImovel.getInstancia().getImovelSelecionado();
    }
}
