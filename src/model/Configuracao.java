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

import java.util.Vector;

import business.ControladorImovel;
import business.ControladorRota;

import util.Constantes;
import util.Util;

public class Configuracao {

	private short tipoPapel = Constantes.PAPEL_REGISPEL;

	private String nomeArquivoImoveis = null;
    private String bluetoothAddress = null;
    private int idImovelSelecionado = 0;
    private int indiceImovelCondominio = 0;
    private Boolean sucessoCarregamento = false;
//    private short indcRotaMarcacaoAtivada;
//    private int sequencialAtualRotaMarcacao = 0;
    private int qtdImoveis;
    private String nomeArquivoRetorno = null;
    /**
     * Contador de imóveis visitados.
     */
    private int contadorVisitados = 0;
    private int contadorVisitadosAnormalidade = 0;
    private int contadorVisitadosSemAnormalidade = 0;
    
    private Vector matriculasRevisitar = new Vector();

    public int getIndiceImovelCondominio() {
    	return indiceImovelCondominio;
    }

    public void setIndiceImovelCondominio(int indiceImovelCondominio) {
    	this.indiceImovelCondominio = indiceImovelCondominio;
    }

    public String getNomeArquivoRetorno() {
    	return nomeArquivoRetorno;
    }

    public void setNomeArquivoRetorno(String nomeArquRetorno) {
    	nomeArquivoRetorno = nomeArquRetorno;
    }

    public static Configuracao instancia;

    /**
     * Retorna o contador de imóveis visitados.
     * 
     * @return Vetor de imóveis.
     */
    public int getContadorVisitados() {
    	return this.contadorVisitados;
    }

    public void setContadorVisitados(int contadorVisitados) {
		this.contadorVisitados = contadorVisitados;
		// System.out.println("Contador Visitados: "+this.contadorVisitados);
    }

    public void incrementarContadorVisitados() {

		if (ControladorImovel.getInstancia().getImovelSelecionado()
			.getMedidor(Constantes.LIGACAO_AGUA) != null) {
	
		    int anormalidade = ControladorImovel.getInstancia()
			    .getImovelSelecionado().getMedidor(
				    Constantes.LIGACAO_AGUA).getAnormalidade();
	
		    if (anormalidade == 0) {
		    	this.setContadorVisitadosSemAnormalidade(Configuracao.getInstancia().getContadorVisitadosSemAnormalidade() + 1);
		    
		    } else {
		    	this.setContadorVisitadosAnormalidade(Configuracao.getInstancia().getContadorVisitadosAnormalidade() + 1);
		    }
		
		} else if (ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO) != null) {
	
		    int anormalidade = ControladorImovel.getInstancia().getImovelSelecionado().getMedidor(Constantes.LIGACAO_POCO).getAnormalidade();
	
		    if (anormalidade == 0) {
		    	this.setContadorVisitadosSemAnormalidade(Configuracao.getInstancia().getContadorVisitadosSemAnormalidade() + 1);
			
		    } else {
		    	this.setContadorVisitadosAnormalidade(Configuracao.getInstancia().getContadorVisitadosAnormalidade() + 1);
		    }
		}
	
		this.setContadorVisitados(Configuracao.getInstancia().getContadorVisitados() + 1);
		ControladorRota.getInstancia().getDataManipulator().updateConfiguracao(Configuracao.getInstancia());
//		Repositorio.salvarObjeto(Configuracao.getInstancia());
		System.out.println("Visitados: " + this.getContadorVisitados());
    }

    public int getQtdImoveis() {
    	return qtdImoveis;
    }

    public void setQtdImoveis(int qtdImoveis) {
    	this.qtdImoveis = qtdImoveis;
    }

    public int getIdImovelSelecionado() {
    	return idImovelSelecionado;
    }

    public void setIdImovelSelecionado(int idImovelSelecionado) {
    	this.idImovelSelecionado = idImovelSelecionado;
    }

    public String getBluetoothAddress() {
    	return Util.verificarNuloString(bluetoothAddress);
    }

    public void setBluetoothAddress(String bluetoothAddress) {
    	this.bluetoothAddress = bluetoothAddress;
    }

    public String getNomeArquivoImoveis() {
    	return Util.verificarNuloString(nomeArquivoImoveis);
    }

    public void setNomeArquivoImoveis(String nomeArquivoImoveis) {
    	this.nomeArquivoImoveis = nomeArquivoImoveis;
    }

    public int getContadorVisitadosAnormalidade() {
    	return contadorVisitadosAnormalidade;
    }

    public void setContadorVisitadosAnormalidade(int contadorVisitadosAnormalidade) {
    	this.contadorVisitadosAnormalidade = contadorVisitadosAnormalidade;
    }

    public int getContadorVisitadosSemAnormalidade() {
    	return contadorVisitadosSemAnormalidade;
    }

    public void setContadorVisitadosSemAnormalidade(int contadorVisitadosSemAnormalidade) {
    	this.contadorVisitadosSemAnormalidade = contadorVisitadosSemAnormalidade;
    }

    public static Configuracao getInstancia() {

	    if (Configuracao.instancia == null) {
	    	
	    	instancia = new Configuracao();
	    	ControladorRota.getInstancia().getDataManipulator().selectConfiguracao();
		}
	
		return Configuracao.instancia;
    }

    public Boolean getSucessoCarregamento() {
    	return sucessoCarregamento;
    }

    public void setSucessoCarregamento(int sucessoCarregamento) {
    	if (sucessoCarregamento == Constantes.SIM){
    		this.sucessoCarregamento = true;
    		
    	}else{
    		this.sucessoCarregamento = false;
    		
    	}
    }

    public void setTipoPapel(short tipoPapel) {
    	this.tipoPapel = tipoPapel;
    }

    public short getTipoPapel() {
    	return tipoPapel;
    }

//    public void setIndcRotaMarcacaoAtivada(short indcRotaMarcacaoAtivada) {
//    	this.indcRotaMarcacaoAtivada = indcRotaMarcacaoAtivada;
//    }
//
//    public short getIndcRotaMarcacaoAtivada() {
//    	return indcRotaMarcacaoAtivada;
//    }
//
//    public int getSequencialAtualRotaMarcacao() {
//		++sequencialAtualRotaMarcacao;	
//		ControladorImoveis.getInstancia().getDataManipulator().salvarConfiguracao(this);
////		Repositorio.salvarObjeto( this );
//		return sequencialAtualRotaMarcacao;
//    }

//    public Vector getIdsNaoLidos() {
//    	return idsNaoLidos;
//    }

    
    
    public void setMatriculasRevisitar( String idsRevisitar) {
	
    }

    public Vector getMatriculasRevisitar() {
    	return matriculasRevisitar;
    }


}
