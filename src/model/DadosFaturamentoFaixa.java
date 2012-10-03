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
package model;

import util.Util;

public class DadosFaturamentoFaixa {

	private int idDadosFaturamento;
	private int consumoFaturado;
	private double valorFaturado;
	private int limiteInicialConsumo;
	private int limiteFinalConsumo;
	private double valorTarifa;
	private int tipoFaturamentoFaixa;
	
	public DadosFaturamentoFaixa(){
	}
	
	public boolean equals( Object obj ){
		DadosFaturamentoFaixa dadosFaturamentoFaixaPassado = ( DadosFaturamentoFaixa ) obj;
		
		return this.getLimiteInicialConsumo() == dadosFaturamentoFaixaPassado.getLimiteInicialConsumo();		
	}	
	
	public DadosFaturamentoFaixa(
			int consumoFaturado, 
			double valorFaturado,
			int limiteInicialConsumo, 
			int limiteFinalConsumo, 
			double valorTarifa ) {
		super();
		this.consumoFaturado = consumoFaturado;
		this.valorFaturado = valorFaturado;
		this.limiteInicialConsumo = limiteInicialConsumo;
		this.limiteFinalConsumo = limiteFinalConsumo;
		this.valorTarifa = valorTarifa;
	}
	
	public DadosFaturamentoFaixa(
			String limiteInicialConsumo, 
			String limiteFinalConsumo, 
			String valorTarifa ) {
		super();

		this.limiteInicialConsumo = Util.verificarNuloInt( limiteInicialConsumo );
		this.limiteFinalConsumo = Util.verificarNuloInt( limiteFinalConsumo ); 
		this.valorTarifa = Util.verificarNuloInt( valorTarifa );
	}
	
	public int getIdDadosFaturamento() {
		return idDadosFaturamento;
	}

	public void setIdDadosFaturamento(int idDadosFaturamento) {
		this.idDadosFaturamento = idDadosFaturamento;
	}

	public int getConsumoFaturado() {
		return consumoFaturado;
	}
	public void setConsumoFaturado(int consumoFaturado) {
		this.consumoFaturado = consumoFaturado;
	}
	public double getValorFaturado() {
		return valorFaturado;
	}
	public void setValorFaturado(double valorFaturado) {
		this.valorFaturado = valorFaturado;
	}
	public int getLimiteInicialConsumo() {
		return limiteInicialConsumo;
	}
	public void setLimiteInicialConsumo(int limiteInicialConsumo) {
		this.limiteInicialConsumo = limiteInicialConsumo;
	}
	public int getLimiteFinalConsumo() {
		return limiteFinalConsumo;
	}
	public void setLimiteFinalConsumo(int limiteFinalConsumo) {
		this.limiteFinalConsumo = limiteFinalConsumo;
	}
	public double getValorTarifa() {
		return valorTarifa;
	}
	public void setValorTarifa(double valorTarifa) {
		this.valorTarifa = valorTarifa;
	}

	public int getTipoFaturamento() {
		return tipoFaturamentoFaixa;
	}

	public void setTipoFaturamentoFaixa(int tipoFaturamento) {
		this.tipoFaturamentoFaixa = tipoFaturamento;
	}

	
    
}
