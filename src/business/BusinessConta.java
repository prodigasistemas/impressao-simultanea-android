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

import java.util.List;

import model.Anormalidade;
import model.Consumo;
import model.DadosCategoria;
import model.DadosFaturamentoFaixa;
import model.Imovel;
import util.Constantes;
import views.MedidorAguaTab;
import views.MedidorPocoTab;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class BusinessConta {

    private static boolean leituraInvalida = false;
    private static final int ANORMALIDADE_CALCULO_MEDIA = 90;
    private static BusinessConta instancia;
    private static Context activityContext;
	AlertDialog dialog;
    private String mensagemPermiteImpressao = null;

    public String getMensagemPermiteImpressao(){
    	return mensagemPermiteImpressao;
    }
    
    /**
     * Metodo responsavel por chamar o calculo do consumo
     */
    @SuppressWarnings("unchecked")
    public static Consumo chamarCalculoConsumo() {

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
	
		consumoAguaRetorno = null;	
		consumoEsgotoRetorno = null;
		consumos = null;	
	
		if(getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA) != null){
			getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).setLeituraRelatorio(getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).getLeitura());
			getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).setAnormalidadeRelatorio(getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA).getAnormalidade());
	
			// Update DB - Medidor água
			ControladorRota.getInstancia().getDataManipulator().updateMedidor(getImovelSelecionado().getMatricula(), getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA));
		}
		
		if(getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) != null){
			getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).setLeituraRelatorio(getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).getLeitura());
			getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).setAnormalidadeRelatorio(getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).getAnormalidade());
	
			// Update DB - Medidor poço
			ControladorRota.getInstancia().getDataManipulator().updateMedidor(getImovelSelecionado().getMatricula(), getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO));
		}
	
		ControladorRota.getInstancia().getDataManipulator().salvarImovel(getImovelSelecionado());
		
		if (getImovelSelecionado().getConsumoAgua() != null){
			ControladorRota.getInstancia().getDataManipulator().salvarConsumoAgua(getImovelSelecionado().getConsumoAgua(), getImovelSelecionado().getMatricula());
		}
		
		if(getImovelSelecionado().getConsumoEsgoto() != null){
			ControladorRota.getInstancia().getDataManipulator().salvarConsumoEsgoto(getImovelSelecionado().getConsumoEsgoto(), getImovelSelecionado().getMatricula());
		}
		
		if(getImovelSelecionado().getEfetuarRateioConsumoHelper() != null){
			ControladorRota.getInstancia().getDataManipulator().salvarRateioCondominioHelper(getImovelSelecionado().getEfetuarRateioConsumoHelper());
		}
		
		if (getImovelSelecionado().getDadosCategoria().size() > 0) {
			for (DadosCategoria dc : getImovelSelecionado().getDadosCategoria()) {
				if (dc.getFaturamentoAgua() != null) {
					int idFaturamento = Math.abs(Long.valueOf(ControladorRota.getInstancia().getDataManipulator().saveDadosFaturamento(dc.getFaturamentoAgua())).intValue());
					List<DadosFaturamentoFaixa> dadosFaturamentoFaixas = ControladorImovel.getInstancia().getImovelSelecionado().getDadosCategoria().get(ControladorImovel.getInstancia().getImovelSelecionado().getDadosCategoria().indexOf(dc)).getFaturamentoAgua().getFaixas();
					for (DadosFaturamentoFaixa dadosFaturamentoFaixa : dadosFaturamentoFaixas) {
						dadosFaturamentoFaixa.setIdDadosFaturamento(idFaturamento);
						
						ControladorRota.getInstancia().getDataManipulator().saveDadosFaturamentoFaixa(dadosFaturamentoFaixa);
					}
					
				} else if (dc.getFaturamentoEsgoto() != null) {
					int idFaturamento = Math.abs(Long.valueOf(ControladorRota.getInstancia().getDataManipulator().saveDadosFaturamento(dc.getFaturamentoEsgoto())).intValue());
					
					List<DadosFaturamentoFaixa> dadosFaturamentoFaixas = ControladorImovel.getInstancia().getImovelSelecionado().getDadosCategoria().get(dc.getId()-1).getFaturamentoEsgoto().getFaixas();
					for (DadosFaturamentoFaixa dadosFaturamentoFaixa : dadosFaturamentoFaixas) {
						dadosFaturamentoFaixa.setIdDadosFaturamento(idFaturamento);
						ControladorRota.getInstancia().getDataManipulator().saveDadosFaturamentoFaixa(dadosFaturamentoFaixa);
					}
				}
			}
		}

		return retorno;
	}

    /**
     * Metodo responsavel por chamar o calculo do consumo pelo consumo médio.
     */
    public static Consumo chamarCalculoConsumoMedio() {

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

			// Update DB - Medidor água
			ControladorRota.getInstancia().getDataManipulator().updateMedidor(getImovelSelecionado().getMatricula(), getImovelSelecionado().getMedidor(Constantes.LIGACAO_AGUA));
		}
		
		if(getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) != null){
		
			getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).setLeituraRelatorio(getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).getLeitura());
			getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).setAnormalidadeRelatorio(getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).getAnormalidade());

			// Update DB - Medidor poço
			ControladorRota.getInstancia().getDataManipulator().updateMedidor(getImovelSelecionado().getMatricula(), getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO));
		}
		
		ControladorRota.getInstancia().getDataManipulator().salvarImovel(getImovelSelecionado());
		
		if (getImovelSelecionado().getConsumoAgua() != null){
			ControladorRota.getInstancia().getDataManipulator().salvarConsumoAgua(getImovelSelecionado().getConsumoAgua(), getImovelSelecionado().getMatricula());
		}
		
		if(getImovelSelecionado().getConsumoEsgoto() != null){
			ControladorRota.getInstancia().getDataManipulator().salvarConsumoEsgoto(getImovelSelecionado().getConsumoEsgoto(), getImovelSelecionado().getMatricula());
		}
		
		if(getImovelSelecionado().getEfetuarRateioConsumoHelper() != null){
			ControladorRota.getInstancia().getDataManipulator().salvarRateioCondominioHelper(getImovelSelecionado().getEfetuarRateioConsumoHelper());
		}
			
		consumoAguaRetorno = null;	
		consumoEsgotoRetorno = null;
		consumos = null;
	return retorno;
    }
    
    /**
     * Metodo responsavel fazer verificaçoes da leitura e anmormalidade de leitura
     * 
     * @return leituraInválida
     */
    public boolean isLeituraInvalida(boolean descartaLeitura) {

		leituraInvalida = false;
		
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
		return leituraInvalida;
    }
    
    /**
     * Metodo responsavel por calcular e imprimir a conta
     * 
     * @return confirmacao
     */
    public Consumo imprimirCalculo(boolean descartaLeitura) {

		Consumo validacao = null;
		
		if (!isLeituraInvalida(descartaLeitura)) {
	
			if (!descartaLeitura){
			
				if(ControladorRota.getInstancia().getDadosGerais().getIdCalculoMedia() == Constantes.SIM){
					validacao = chamarCalculoConsumoMedio();			
				
				}else{
					validacao = chamarCalculoConsumo();			
				}
			}
			return validacao;

		}else{
			
			AlertDialog.Builder a = new AlertDialog.Builder(activityContext);
			a.setTitle("Aviso");
			a.setMessage(ValidacaoLeitura.getInstancia().getMensagemValidacaoLeitura());
			
			a.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface arg0, int arg1) {}
			});

			a.show();
			return null;
		}
    }

	public boolean isImpressaoPermitida(){
		
		getImovelSelecionado().setIndcGeracaoConta(Constantes.SIM);
		
		// Caso o valor da conta seja menor que o valor permitido para ser impresso, não imprimir a conta.
		boolean valorAcimaDoMinimo = true;
		boolean valorContaMaiorPermitido = false;
		boolean emiteConta = true; 
		boolean reterConta = false; 
		boolean permiteImpressao = true;
		
		double valorConta = getImovelSelecionado().getValorConta();			
		valorAcimaDoMinimo = getImovelSelecionado().isValorContaAcimaDoMinimo();
		valorContaMaiorPermitido = getImovelSelecionado().isValorContaMaiorPermitido();
		
		if (getImovelSelecionado().getIndcEmissaoConta() == 2 && leituraInvalida == false) {
			
			// A conta já não seria impressa. Mas nos casos abaixo, deve reter a conta, isto é, não deve ser faturado no Gsan.
			if ( Integer.parseInt(getImovelSelecionado().getCodigoPerfil()) == Imovel.PERFIL_GOVERNO_METROPOLITANO){
				
				reterConta = true;
				
			}else{
				// Conta centralizada nao permite impressao. E não é retido.
				emiteConta = false;
			}
			
			// Verificando Consumo de agua e Anormalidades de Consumo e Anormalidades de Leitura para imoveis CORPORATIVOS e CONDOMINIAIS
		}else if (getImovelSelecionado().getConsumoAgua() != null){
			
			if ( (getImovelSelecionado().getConsumoAgua().getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_ALTO_CONSUMO ||
					getImovelSelecionado().getConsumoAgua().getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_ESTOURO_MEDIA ||
					getImovelSelecionado().getConsumoAgua().getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_ESTOURO ||
					getImovelSelecionado().getConsumoAgua().getAnormalidadeConsumo() == Consumo.CONSUMO_ANORM_HIDR_SUBST_INFO)
					||
					( (Integer.parseInt(getImovelSelecionado().getCodigoPerfil()) == Imovel.PERFIL_CORPORATIVO ||
					Integer.parseInt(getImovelSelecionado().getCodigoPerfil()) == Imovel.PERFIL_CONDOMINIAL) 
					&&
					(getImovelSelecionado().getConsumoAgua().getAnormalidadeLeituraFaturada() == ControladorConta.ANORM_HIDR_LEITURA_IMPEDIDA_CLIENTE ||
					getImovelSelecionado().getConsumoAgua().getAnormalidadeLeituraFaturada() == ControladorConta.ANORM_HIDR_PORTAO_FECHADO) ) ){
				
				reterConta = true;
			}
		}
		
		if (!leituraInvalida && !emiteConta){
			
			mensagemPermiteImpressao = "Conta do imóvel nao pode ser emitida!";
//			Toast.makeText(this, "Conta do imóvel nao pode ser emitida!", Toast.LENGTH_LONG).show();
			permiteImpressao = false;
			
		} else if (!leituraInvalida && (valorContaMaiorPermitido || reterConta)){
			
			getImovelSelecionado().setIndcGeracaoConta(Constantes.NAO);
    		mensagemPermiteImpressao = "Conta retida, entrega posterior!";
//			Toast.makeText(this, "Conta retida, entrega posterior!", Toast.LENGTH_LONG).show();
			permiteImpressao = false;
			
		} else if (!leituraInvalida && !valorAcimaDoMinimo) {
			
    		mensagemPermiteImpressao = "Valor da conta menor que o permitido!";
//			Toast.makeText(this, "Valor da conta menor que o permitido!", Toast.LENGTH_LONG).show();
			
			// Imovel com conta abaixo do minimo nao deve ser impresso, mas não deve fazer parte dos imoveis com conta a imprimir no Gsan. 
			getImovelSelecionado().setIndcImovelImpresso(Constantes.SIM);
			permiteImpressao = false;
			
		} else if (!leituraInvalida && 
				(getImovelSelecionado().getIndicadorParalizarFaturamentoAgua() == Constantes.SIM || 
				getImovelSelecionado().getIndicadorParalizarFaturamentoEsgoto() == Constantes.SIM)){
			
			getImovelSelecionado().setIndcGeracaoConta(Constantes.NAO);
    		mensagemPermiteImpressao = "Não é permitido a impressão de conta deste imóvel.";
//			Toast.makeText(this, "Não é permitido a impressão de conta deste imóvel.", Toast.LENGTH_LONG).show();
			permiteImpressao = false;
			
		} else if ( !leituraInvalida && valorConta == 0d && getImovelSelecionado().getValorResidualCredito() == 0d) {
    		mensagemPermiteImpressao = "Conta com valor zerado e sem crédito. Não imprimir!";
//			Toast.makeText(this, "Conta com valor zerado e sem crédito. Não imprimir!", Toast.LENGTH_LONG).show();
			permiteImpressao = false;
			
			// Daniel - Imovel com Endereço alternativo
			// caso nao haja erro de leitura e imovel contém endereço alternativo
		} else if ( !leituraInvalida && getImovelSelecionado().getEnderecoEntrega().length() > 0 ){
			
    		mensagemPermiteImpressao = "Conta do imóvel não pode ser emitida! Entrega  posterior!";
//			Toast.makeText(this, "Conta do imóvel não pode ser emitida! Entrega  posterior!", Toast.LENGTH_LONG).show();
			permiteImpressao = false;
		}
		
		return permiteImpressao;
	}

	   /**
     * Metodo responsavel por retornar a instancia do objeto BussinessConta
     * 
     * @return instancia
     */
    public static BusinessConta getInstancia(Context context) {

    	if (instancia == null) {
    		instancia = new BusinessConta();
		}

    	activityContext = context;
		return instancia;
    }

    /**
     * Metodo responsavel por retornar a instancia do objeto BussinessConta
     * 
     * @return instancia
     */
    public static BusinessConta getInstancia() {

    	if (instancia == null) {
    		instancia = new BusinessConta();
		}

		return instancia;
    }

    private static Imovel getImovelSelecionado(){
    	return ControladorImovel.getInstancia().getImovelSelecionado();
    }
}
