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

package business;

import helper.EfetuarRateioConsumoHelper;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import util.Constantes;
import util.Util;

import model.Consumo;
import model.ConsumoAnormalidadeAcao;
import model.DadosGerais;
import model.Debito;
import model.HistoricoConsumo;
import model.Imovel;
import model.Medidor;


public class ControladorConta {

    private static ControladorConta instancia = null;

// Daniel - Valor limite estipulado para cada Perfil
    // Valor limite para a conta
    public static final int VALOR_LIMITE_CONTA = 100000;
    public static final int VALOR_LIMITE_PERFIL_CORPORATIVO = 100000;
    public static final int VALOR_LIMITE_PERFIL_CONDOMINIAL = 100000;
    public static final int VALOR_LIMITE_PERFIL_GOVERNO_METROPOLITANO = 100000;
    public static final int VALOR_LIMITE_PERFIL_GOVERNO_INTERIOR = 100000;
    public static final int VALOR_LIMITE_PERFIL_GRANDE = 100000;
    public static final int VALOR_LIMITE_PERFIL_ESPECIAL = 100000;
    public static final int VALOR_LIMITE_PERFIL_NORMAL = 100000;
    public static final int VALOR_LIMITE_PERFIL_GRANDE_MES = 100000;
    public static final int VALOR_LIMITE_PERFIL_COLABORADOR = 100000;
    public static final int VALOR_LIMITE_PERFIL_BONUS_SOCIAL = 100000;

    /**
     * Medição Tipo
     */
    public static final int LIGACAO_AGUA = 1;
    public static final int LIGACAO_POCO = 2;

    public static final int SIM = 1;
    public static final int NAO = 2;

    public static final int CONSUMO_TIPO_INDEFINIDO = 0;
    public static final int CONSUMO_TIPO_REAL = 1;
    public static final int CONSUMO_TIPO_AJUSTADO = 2;
    public static final int CONSUMO_TIPO_MEDIA_HIDR = 3;
    public static final int CONSUMO_TIPO_INFORMADO = 4;
    public static final int CONSUMO_TIPO_NAO_MEDIDO = 5;
    public static final int CONSUMO_TIPO_ESTIMADO = 6;
    public static final int CONSUMO_TIPO_MINIMO_FIX = 7;
    public static final int CONSUMO_TIPO_SEM = 8;
    public static final int CONSUMO_TIPO_MEDIA_IMOV = 9;
    public static final int CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL = 10;

    public static final int LEITURA_SITU_INDEFINIDO = 0;
    public static final int LEITURA_SITU_REALIZADA = 1;
    public static final int LEITURA_SITU_NAO_REALIZ = 2;
    public static final int LEITURA_SITU_CONFIRMADA = 3;
    public static final int LEITURA_SITU_ALTERADA = 4;

    public static final int ANORM_HIDROMETRO_PARADO = 30;
    public static final int ANORM_HIDR_SEM_CONSUMO = 38;
    public static final int ANORM_HIDR_LEITURA_IMPEDIDA_CLIENTE = 4;
    public static final int ANORM_HIDR_PORTAO_FECHADO = 5;

    /*
     * Leitura Anormalidade Consumo
     */
    private static final int NAO_OCORRE = 0;
    private static final int MINIMO = 1;
    private static final int MEDIA = 2;
    private static final int NORMAL = 3;
    private static final int MAIOR_ENTRE_O_CONSUMO_MEDIO = 5;
    private static final int MENOR_ENTRE_O_CONSUMO_MEDIO = 6;
    public static final int FIXO = 7;
    public static final int NAO_MEDIDO = 8;

    /**
     * Leitura Anormalidade Leitura
     */
    private static final int ANTERIOR_MAIS_A_MEDIA = 0;
    private static final int ANTERIOR = 1;
    private static final int ANTERIOR_MAIS_O_CONSUMO = 2;
    private static final int INFORMADO = 3;

    private Consumo consumoAgua;
    private Consumo consumoEsgoto;


    public ControladorConta() {

    }

    public static ControladorConta getInstancia() {
	if (instancia == null) {
	    instancia = new ControladorConta();
	}
	return instancia;
    }

    /**
     * [UC0740] Calcular Consumo no Dispositivo Móvel
     */
    public void calcularConta() {}

    /**
     * [SB0000] - Calcular Consumo
     */
    private void calcularConsumo(int tipoMedicao) {}

    public void calcularValores() {}

    public void calcularValoresCondominio(Imovel imovelMicro) {}

    
    public void calcularValores(Consumo consumoAgua, Consumo consumoEsgoto) {

		this.setConsumoAgua(consumoAgua);
		this.setConsumoEsgoto(consumoEsgoto);
	
		calcularValores();
    }

    public void calcularValoresCondominio(Imovel imovelMicro, Consumo consumoAgua,
    	    Consumo consumoEsgoto) {

    	if (consumoAgua == null){
    		consumoAgua = new Consumo();
    	}
    	this.setConsumoAgua(consumoAgua);
    	
    	if (consumoEsgoto == null){
    		consumoEsgoto = new Consumo();
    	}
    	this.setConsumoEsgoto(consumoEsgoto);

    	calcularValoresCondominio(imovelMicro);
        }

    public Consumo getConsumoAgua() {
	return consumoAgua;
    }

    public Consumo getConsumoEsgoto() {
	return consumoEsgoto;
    }

    public void setConsumoAgua(Consumo consumoAgua) {
    	this.consumoAgua = consumoAgua;
    }

    public void setConsumoEsgoto(Consumo consumoEsgoto) {
    	this.consumoEsgoto = consumoEsgoto;
    }


    private int calcularConsumoAguaASerRateado(Imovel imovelMacro) { return 0;}
    
    private int calcularConsumoEsgotoASerRateado(Imovel imovelMacro) {return 0;}

  //Daniel - Novo método de calculo - condominio
    private double calcularContaAguaParaRateado(Imovel imovelMacro) {

    	EfetuarRateioConsumoHelper helper = imovelMacro
    		.getEfetuarRateioConsumoDispositivoMovelHelper();

    	boolean imovelComDebitoTipoCortado = false;
    	
    	Debito debito = getImovelSelecionado().getDebito( Debito.TARIFA_CORTADO_DEC_18_251_94 );
    	
    	if ( debito != null && debito.getIndcUso() == Constantes.SIM ){
    	    imovelComDebitoTipoCortado = true;
    	}

    	if ( ((imovelMacro.getIndcFaturamentoAgua() == SIM) ||  (imovelMacro.getIndcFaturamentoAgua() == NAO && imovelMacro.isImovelMicroCondominio()))&& 
    			imovelMacro.getIndicadorParalizarFaturamentoAgua() == NAO && !imovelComDebitoTipoCortado ) {
    		
    		ControladorImoveis.getInstancia().calcularValores(imovelMacro, 
    			    										  consumoAgua,
    			    										  ControladorConta.LIGACAO_AGUA);
    	}
    	System.out.println("Conta de água a ser rateada: " + imovelMacro.getValorAgua());
    	return imovelMacro.getValorAgua();
    }

  //Daniel - Novo método de calculo - condominio
    private double calcularContaEsgotoParaRateado(Imovel imovelMacro) {

    	EfetuarRateioConsumoHelper helper = imovelMacro
    		.getEfetuarRateioConsumoDispositivoMovelHelper();

    	if ( (getImovelSelecionado().getIndcFaturamentoEsgoto() == SIM) && getImovelSelecionado().getIndicadorParalizarFaturamentoEsgoto() == NAO) {
    		
    		ControladorImoveis.getInstancia().calcularValores(imovelMacro, 
    														  consumoEsgoto,
    														  ControladorConta.LIGACAO_POCO);
    	}
    	
    	System.out.println("Conta de esgoto a ser rateada: " + imovelMacro.getValorEsgoto());

    	return imovelMacro.getValorEsgoto();
	}

    public boolean verificarEstouroConsumo(Consumo consumo,
	    Medidor reg8) {

	int cMedio;

	// Verificamos se o consumo médio veio do
	// registro tipo 8 ou do imóvel
	if (reg8 != null) {
	    cMedio = reg8.getConsumoMedio();
	} else {
	    cMedio = getImovelSelecionado().getConsumoMedio();
	}

	boolean estouro = false;

	// [SB0000] 4.
	// [SB0006] - Verificar Estouro de Consumo
	// [SB0006] 1.
	int resultado = Util.arredondar(getImovelSelecionado().getFatorMultEstouro() * cMedio);

	if (consumo.getConsumoCobradoMes() > getImovelSelecionado().getConsumoEstouro()
		&& consumo.getConsumoCobradoMes() > resultado) {

	    int anormConsumo = Consumo.CONSUMO_ANORM_ESTOURO;

	    int idImovelPerfil = Util
		    .verificarNuloInt(ControladorImoveis.getInstancia().getImovelSelecionado().getCodigoPerfil());

	    int categoriaPrincipal = getImovelSelecionado().pesquisarPrincipalCategoria();

	    ConsumoAnormalidadeAcao consumoAnormalidadeAcao = ConsumoAnormalidadeAcao.getInstancia()
		    .getRegistro12(anormConsumo, categoriaPrincipal,
			    idImovelPerfil);

	    if (consumoAnormalidadeAcao != null) {

		int idLeituraAnormalidadeConsumo = Constantes.NULO_INT;
		double numerofatorConsumo = Constantes.NULO_DOUBLE;
		String mensagemContaPrimeiroMes = consumoAnormalidadeAcao
			.getMensagemContaPrimeiroMes();
		String mensagemContaSegundoMes = consumoAnormalidadeAcao
			.getMensagemContaSegundoMes();
		String mensagemContaTerceiroMes = consumoAnormalidadeAcao
			.getMensagemContaTerceiroMes();

		// [SB0006] 1.1.
		/*
		 * Calendar c = Calendar.getInstance();
		 * c.setTime(reg8.getDataLeitura());
		 */

		/*
		 * int anoLeitura = c.get(Calendar.YEAR); int mesLeitura =
		 * c.get(Calendar.MONTH); int dia = c.get( Calendar.DAY_OF_MONTH
		 * );
		 */

		int anoMes = Util.subtrairMesDoAnoMes(Util
			.verificarNuloInt(DadosGerais.getInstancia().getAnoMesFaturamento()), 1);
		// int anoMes =
		// Util.subtrairMesDoAnoMes(Util.getAnoMes(c.getTime()), 1);

		HistoricoConsumo reg3MesAnterior = getImovelSelecionado().getRegistro3(anoMes,
			anormConsumo);

		if (reg3MesAnterior == null || reg3MesAnterior.equals("")) {
		    idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao
			    .getIdLeituraAnormalidadeConsumoPrimeiroMes();

		    numerofatorConsumo = consumoAnormalidadeAcao
			    .getFatorConsumoPrimeiroMes();

		    if (mensagemContaPrimeiroMes != null) {

			String[] mensagem = Util.dividirString(
				mensagemContaPrimeiroMes, 40);

			switch (mensagem.length) {
			case 3:
			    getImovelSelecionado().setMensagemEstouroConsumo3(mensagem[2]);
			case 2:
			    getImovelSelecionado().setMensagemEstouroConsumo2(mensagem[1]);
			case 1:
			    getImovelSelecionado().setMensagemEstouroConsumo1(mensagem[0]);
			    break;
			}

			/*
			 * getImovelSelecionado().setMensagemConta1(mensagemContaPrimeiroMes
			 * .substring(0, 40));
			 * getImovelSelecionado().setMensagemConta2(mensagemContaPrimeiroMes
			 * .substring(40, 80));5
			 * getImovelSelecionado().setMensagemConta3(mensagemContaPrimeiroMes
			 * .substring(80, mensagemContaPrimeiroMes .length()));
			 */
			Repositorio.salvarObjeto(getImovelSelecionado());
		    }

		} else {
		    anoMes = Util.subtrairMesDoAnoMes(Util
			    .verificarNuloInt(getImovelSelecionado().getAnoMesConta()), 2);

		    HistoricoConsumo reg3SegundoMesAnterior = getImovelSelecionado().getRegistro3(
			    anoMes, anormConsumo);

		    if (reg3SegundoMesAnterior == null
			    || reg3SegundoMesAnterior.equals("")) {
			idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao
				.getIdLeituraAnormalidadeConsumoSegundoMes();

			numerofatorConsumo = consumoAnormalidadeAcao
				.getFatorConsumoSegundoMes();

			if (mensagemContaSegundoMes != null) {

			    String[] mensagem = Util.dividirString(
				    mensagemContaSegundoMes, 40);

			    switch (mensagem.length) {
			    case 3:
				getImovelSelecionado().setMensagemEstouroConsumo3(mensagem[2]);
			    case 2:
				getImovelSelecionado().setMensagemEstouroConsumo2(mensagem[1]);
			    case 1:
				getImovelSelecionado().setMensagemEstouroConsumo1(mensagem[0]);
				break;
			    }

			    Repositorio.salvarObjeto(getImovelSelecionado());
			}

		    } else {
			idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao
				.getIdLeituraAnormalidadeConsumoTerceiroMes();

			numerofatorConsumo = consumoAnormalidadeAcao
				.getFatorConsumoTerceiroMes();

			if (mensagemContaTerceiroMes != null) {

			    String[] mensagem = Util.dividirString(
				    mensagemContaTerceiroMes, 40);

			    switch (mensagem.length) {
			    case 3:
				getImovelSelecionado().setMensagemEstouroConsumo3(mensagem[2]);
			    case 2:
				getImovelSelecionado().setMensagemEstouroConsumo2(mensagem[1]);
			    case 1:
				getImovelSelecionado().setMensagemEstouroConsumo1(mensagem[0]);
				break;
			    }

			    Repositorio.salvarObjeto(getImovelSelecionado());
			}

		    }
		}

		// 3.1.1.1. O sistema gera a Anormalidade de Consumo com o valor
		// correspondente a estouro de consumo da tabela
		// CONSUMO_ANORMALIDADE
		consumo.setAnormalidadeConsumo(anormConsumo);

		if (idLeituraAnormalidadeConsumo == NAO_OCORRE) {

		    consumo.setConsumoCobradoMes(getImovelSelecionado().getConsumoMedio());
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);

		} else if (idLeituraAnormalidadeConsumo == MINIMO) {

		    // O Consumo a Ser Cobrado no Mês será o valor retornado
		    // por [UC0105 – Obter Consumo Mínimo da Ligação
		    consumo.setConsumoCobradoMes(getImovelSelecionado()
			    .getConsumoMinimoImovel());
		    // Seta o tipo de consumo
		    consumo.setTipoConsumo(CONSUMO_TIPO_MINIMO_FIX);

		} else if (idLeituraAnormalidadeConsumo == MEDIA) {

		    // Consumo a ser cobrado no mês será o consumo médio do
		    // hidrômetro
		    consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);

		} else if (idLeituraAnormalidadeConsumo == NORMAL) {

		    // Fazer nada já calculado

		} else if (idLeituraAnormalidadeConsumo == MAIOR_ENTRE_O_CONSUMO_MEDIO) {

		    if (cMedio > consumo.getConsumoCobradoMes()) {
			consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
			consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    }

		} else if (idLeituraAnormalidadeConsumo == MENOR_ENTRE_O_CONSUMO_MEDIO) {
		    if (cMedio < consumo.getConsumoCobradoMes()) {
			consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
			consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    }

		}

		// 3.1.4. O consumo a Ser Cobrado no Mês será igual
		// ao Consumo a Ser Cobrado no Mês multiplicado pelo
		// fator de multiplicação da quantidade de vezes a média
		// (CSAA_NNFATORCONSUMOMES(1,2 ou 3), dependendo do mês
		// calculado anteriormente
		if (numerofatorConsumo != Constantes.NULO_DOUBLE) {
		    double consumofaturadoMes = consumo.getConsumoCobradoMes();
		    consumofaturadoMes = consumofaturadoMes
			    * numerofatorConsumo;
		    int consumofaturadoMesInt = Util
			    .arredondar(consumofaturadoMes);
		    consumo.setConsumoCobradoMes(consumofaturadoMesInt);
		}

	    } else {
	        // [SB0006] 1.1.
	        //Calendar c = Calendar.getInstance();
	        //c.setTime( getImovelSelecionado().getdata );

	        int anoMes = Util.subtrairMesDoAnoMes( Integer.parseInt( DadosGerais.getInstancia().getAnoMesFaturamento() ) , 1);
	        HistoricoConsumo reg3 = getImovelSelecionado().getRegistro3(anoMes);


		int anormConsumoAnterior = Constantes.NULO_INT;
		if (reg3 != null) {
		    anormConsumoAnterior = reg3.getAnormalidadeConsumo();
		}

	        boolean cond1 = anormConsumoAnterior != Constantes.NULO_INT
	            && anormConsumoAnterior != Consumo.CONSUMO_ANORM_ESTOURO
	            && anormConsumoAnterior != Consumo.CONSUMO_ANORM_ESTOURO_MEDIA;

	        // [SB0006] 1.1. (continuação)
	        if (cond1 || 
	            consumo.getConsumoCobradoMes() > getImovelSelecionado().getConsumoMaximo() || 
	            anormConsumoAnterior == Constantes.NULO_INT) {
	            // [SB0006] 1.1.1.
	            consumo.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_ESTOURO_MEDIA);

	            // [SB0006] 1.1.2.
	            consumo.setConsumoCobradoMes(cMedio);

	            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
	            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);

	            // [SB0006] 1.1.3.
	            consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);

	            // [SB0006] 1.2.
	        } else {
	            consumo.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_ESTOURO);
	        }


	    }
	    estouro = true;
	}

	return estouro;
    }

    public void verificarAltoConsumo(Consumo consumo,
	    Medidor reg8) {

	int cMedio;

	// Verificamos se o consumo médio veio do
	// registro tipo 8 ou do imóvel
	if (reg8 != null) {
	    cMedio = reg8.getConsumoMedio();
	} else {
	    cMedio = getImovelSelecionado().getConsumoMedio();
	}

	// [SB0007] - Verificar Alto Consumo
	int resultado = Util.arredondar(getImovelSelecionado().getFatorMultMediaAltoConsumo()
		* cMedio);

	if (consumo.getConsumoCobradoMes() > getImovelSelecionado().getAltoConsumo()
		&& consumo.getConsumoCobradoMes() > resultado) {

	    int anormConsumo = Consumo.CONSUMO_ANORM_ALTO_CONSUMO;

	    int idImovelPerfil = Util
		    .verificarNuloInt(getImovelSelecionado().getCodigoPerfil());

	    int categoriaPrincipal = getImovelSelecionado().pesquisarPrincipalCategoria();

	    ConsumoAnormalidadeAcao consumoAnormalidadeAcao = ConsumoAnormalidadeAcao.getInstancia()
		    .getRegistro12(anormConsumo, categoriaPrincipal,
			    idImovelPerfil);

	    if (consumoAnormalidadeAcao != null) {

		String mensagemContaPrimeiroMes = consumoAnormalidadeAcao
			.getMensagemContaPrimeiroMes();
		String mensagemContaSegundoMes = consumoAnormalidadeAcao
			.getMensagemContaSegundoMes();
		String mensagemContaTerceiroMes = consumoAnormalidadeAcao
			.getMensagemContaTerceiroMes();

		int idLeituraAnormalidadeConsumo = Constantes.NULO_INT;
		double numerofatorConsumo = Constantes.NULO_DOUBLE;

		/*
		 * Calendar c = Calendar.getInstance();
		 * c.setTime(reg8.getDataLeitura());
		 */

		int anoMes = Util.subtrairMesDoAnoMes(Util
			.verificarNuloInt(getImovelSelecionado().getAnoMesConta()), 1);
		// int anoMes =
		// Util.subtrairMesDoAnoMes(Util.getAnoMes(c.getTime()), 1);

		HistoricoConsumo reg3MesAnterior = getImovelSelecionado().getRegistro3(anoMes,
			anormConsumo);

		if (reg3MesAnterior == null || reg3MesAnterior.equals("")) {
		    idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao
			    .getIdLeituraAnormalidadeConsumoPrimeiroMes();

		    numerofatorConsumo = consumoAnormalidadeAcao
			    .getFatorConsumoPrimeiroMes();

		    if (mensagemContaPrimeiroMes != null) {

			String[] mensagem = Util.dividirString(
				mensagemContaPrimeiroMes, 40);

			switch (mensagem.length) {
			case 3:
			    getImovelSelecionado().setMensagemEstouroConsumo3(mensagem[2]);
			case 2:
			    getImovelSelecionado().setMensagemEstouroConsumo2(mensagem[1]);
			case 1:
			    getImovelSelecionado().setMensagemEstouroConsumo1(mensagem[0]);
			    break;
			}

			Repositorio.salvarObjeto(getImovelSelecionado());
		    }

		} else {

		    anoMes = Util.subtrairMesDoAnoMes(Util
			    .verificarNuloInt(getImovelSelecionado().getAnoMesConta()), 2);
		    HistoricoConsumo reg3SegundoMesAnterior = getImovelSelecionado().getRegistro3(
			    anoMes, anormConsumo);

		    if (reg3SegundoMesAnterior == null
			    || reg3SegundoMesAnterior.equals("")) {
			idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao
				.getIdLeituraAnormalidadeConsumoSegundoMes();

			numerofatorConsumo = consumoAnormalidadeAcao
				.getFatorConsumoSegundoMes();

			if (mensagemContaSegundoMes != null) {

			    String[] mensagem = Util.dividirString(
				    mensagemContaSegundoMes, 40);

			    switch (mensagem.length) {
			    case 3:
				getImovelSelecionado().setMensagemEstouroConsumo3(mensagem[2]);
			    case 2:
				getImovelSelecionado().setMensagemEstouroConsumo2(mensagem[1]);
			    case 1:
				getImovelSelecionado().setMensagemEstouroConsumo1(mensagem[0]);
				break;
			    }

			    Repositorio.salvarObjeto(getImovelSelecionado());
			}

		    } else {
			idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao
				.getIdLeituraAnormalidadeConsumoTerceiroMes();

			numerofatorConsumo = consumoAnormalidadeAcao
				.getFatorConsumoTerceiroMes();

			if (mensagemContaTerceiroMes != null) {

			    String[] mensagem = Util.dividirString(
				    mensagemContaTerceiroMes, 40);

			    switch (mensagem.length) {
			    case 3:
				getImovelSelecionado().setMensagemEstouroConsumo3(mensagem[2]);
			    case 2:
				getImovelSelecionado().setMensagemEstouroConsumo2(mensagem[1]);
			    case 1:
				getImovelSelecionado().setMensagemEstouroConsumo1(mensagem[0]);
				break;
			    }

			    Repositorio.salvarObjeto(getImovelSelecionado());
			}

		    }
		}

		// 3.1.1.1. O sistema gera a Anormalidade de Consumo com o valor
		// correspondente a estouro de consumo da tabela
		// CONSUMO_ANORMALIDADE
		consumo.setAnormalidadeConsumo(anormConsumo);

		if (idLeituraAnormalidadeConsumo == NAO_OCORRE) {

		    consumo.setConsumoCobradoMes(cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);

		} else if (idLeituraAnormalidadeConsumo == MINIMO) {

		    // O Consumo a Ser Cobrado no Mês será o valor retornado
		    // por [UC0105 – Obter Consumo Mínimo da Ligação
		    consumo.setConsumoCobradoMes(getImovelSelecionado()
			    .getConsumoMinimoImovel());
		    // Seta o tipo de consumo
		    consumo.setTipoConsumo(CONSUMO_TIPO_MINIMO_FIX);

		} else if (idLeituraAnormalidadeConsumo == MEDIA) {

		    // Consumo a ser cobrado no mês será o consumo médio do
		    // hidrômetro
		    consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);

		} else if (idLeituraAnormalidadeConsumo == NORMAL) {

		    // Fazer nada já calculado

		} else if (idLeituraAnormalidadeConsumo == MAIOR_ENTRE_O_CONSUMO_MEDIO) {

		    if (cMedio > consumo.getConsumoCobradoMes()) {
			consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
			consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    }

		} else if (idLeituraAnormalidadeConsumo == MENOR_ENTRE_O_CONSUMO_MEDIO) {
		    if (cMedio < consumo.getConsumoCobradoMes()) {
			consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
			consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    }

		}

		// 3.1.4. O consumo a Ser Cobrado no Mês será igual
		// ao Consumo a Ser Cobrado no Mês multiplicado pelo
		// fator de multiplicação da quantidade de vezes a média
		// (CSAA_NNFATORCONSUMOMES(1,2 ou 3), dependendo do mês
		// calculado anteriormente
		if (numerofatorConsumo != Constantes.NULO_DOUBLE) {
		    double consumofaturadoMes = consumo.getConsumoCobradoMes();
		    consumofaturadoMes = consumofaturadoMes
			    * numerofatorConsumo;
		    int consumofaturadoMesInt = Util
			    .arredondar(consumofaturadoMes);
		    consumo.setConsumoCobradoMes(consumofaturadoMesInt);
		}

	    } else {
	    	consumo.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_ALTO_CONSUMO);
	    }

	}

    }

    public void verificarBaixoConsumo(Consumo consumo,
	    Medidor reg8) {

	int cMedio;

	// Verificamos se o consumo médio veio do
	// registro tipo 8 ou do imóvel
	if (reg8 != null) {
	    cMedio = reg8.getConsumoMedio();
	} else {
	    cMedio = getImovelSelecionado().getConsumoMedio();
	}

	// [SB0008] - Verificar Baixo Consumo
	double percentual = getImovelSelecionado().getPercentBaixoConsumo() / 100;
	double consumoMedioPercent = cMedio * percentual;

	if (cMedio > getImovelSelecionado().getBaixoConsumo()
		&& consumo.getConsumoCobradoMes() < consumoMedioPercent) {

	    int anormConsumo = Consumo.CONSUMO_ANORM_BAIXO_CONSUMO;

	    int idImovelPerfil = Util
		    .verificarNuloInt(getImovelSelecionado().getCodigoPerfil());

	    int categoriaPrincipal = getImovelSelecionado().pesquisarPrincipalCategoria();

	    ConsumoAnormalidadeAcao consumoAnormalidadeAcao = ConsumoAnormalidadeAcao.getInstancia()
		    .getRegistro12(anormConsumo, categoriaPrincipal,
			    idImovelPerfil);

	    if (consumoAnormalidadeAcao != null) {

		int idLeituraAnormalidadeConsumo = Constantes.NULO_INT;
		double numerofatorConsumo = Constantes.NULO_DOUBLE;

		/*
		 * Calendar c = Calendar.getInstance();
		 * c.setTime(reg8.getDataLeitura());
		 */

		int anoMes = Util.subtrairMesDoAnoMes(Util
			.verificarNuloInt(DadosGerais.getInstancia().getAnoMesFaturamento()), 1);
		// int anoMes =
		// Util.subtrairMesDoAnoMes(Util.getAnoMes(c.getTime()), 1);
		HistoricoConsumo reg3MesAnterior = getImovelSelecionado().getRegistro3(anoMes,
			anormConsumo);

		String mensagemContaPrimeiroMes = consumoAnormalidadeAcao
			.getMensagemContaPrimeiroMes();
		String mensagemContaSegundoMes = consumoAnormalidadeAcao
			.getMensagemContaSegundoMes();
		String mensagemContaTerceiroMes = consumoAnormalidadeAcao
			.getMensagemContaTerceiroMes();

		if (reg3MesAnterior == null || reg3MesAnterior.equals("")) {
		    idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao
			    .getIdLeituraAnormalidadeConsumoPrimeiroMes();

		    numerofatorConsumo = consumoAnormalidadeAcao
			    .getFatorConsumoPrimeiroMes();

		    if (mensagemContaPrimeiroMes != null) {

			String[] mensagem = Util.dividirString(
				mensagemContaPrimeiroMes, 40);

			switch (mensagem.length) {
			case 3:
			    getImovelSelecionado().setMensagemEstouroConsumo3(mensagem[2]);
			case 2:
			    getImovelSelecionado().setMensagemEstouroConsumo2(mensagem[1]);
			case 1:
			    getImovelSelecionado().setMensagemEstouroConsumo1(mensagem[0]);
			    break;
			}

			/*
			 * getImovelSelecionado().setMensagemConta1(mensagemContaPrimeiroMes
			 * .substring(0, 40));
			 * getImovelSelecionado().setMensagemConta2(mensagemContaPrimeiroMes
			 * .substring(40, 80));
			 * getImovelSelecionado().setMensagemConta3(mensagemContaPrimeiroMes
			 * .substring(80, mensagemContaPrimeiroMes .length()));
			 */

			Repositorio.salvarObjeto(getImovelSelecionado());

		    }

		} else {

		    anoMes = Util.subtrairMesDoAnoMes(Util
			    .verificarNuloInt(getImovelSelecionado().getAnoMesConta()), 2);
		    HistoricoConsumo reg3SegundoMesAnterior = getImovelSelecionado().getRegistro3(
			    anoMes, anormConsumo);

		    if (reg3SegundoMesAnterior == null
			    || reg3SegundoMesAnterior.equals("")) {
			idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao
				.getIdLeituraAnormalidadeConsumoSegundoMes();

			numerofatorConsumo = consumoAnormalidadeAcao
				.getFatorConsumoSegundoMes();

			if (mensagemContaSegundoMes != null) {

			    String[] mensagem = Util.dividirString(
				    mensagemContaSegundoMes, 40);

			    switch (mensagem.length) {
			    case 3:
				getImovelSelecionado().setMensagemEstouroConsumo3(mensagem[2]);
			    case 2:
				getImovelSelecionado().setMensagemEstouroConsumo2(mensagem[1]);
			    case 1:
				getImovelSelecionado().setMensagemEstouroConsumo1(mensagem[0]);
				break;
			    }
			    Repositorio.salvarObjeto(getImovelSelecionado());
			}

		    } else {
			idLeituraAnormalidadeConsumo = consumoAnormalidadeAcao
				.getIdLeituraAnormalidadeConsumoTerceiroMes();

			numerofatorConsumo = consumoAnormalidadeAcao
				.getFatorConsumoTerceiroMes();

			if (mensagemContaTerceiroMes != null) {

			    String[] mensagem = Util.dividirString(
				    mensagemContaTerceiroMes, 40);

			    switch (mensagem.length) {
			    case 3:
				getImovelSelecionado().setMensagemEstouroConsumo3(mensagem[2]);
			    case 2:
				getImovelSelecionado().setMensagemEstouroConsumo2(mensagem[1]);
			    case 1:
				getImovelSelecionado().setMensagemEstouroConsumo1(mensagem[0]);
				break;
			    }
			    Repositorio.salvarObjeto(getImovelSelecionado());
			}
		    }
		}

		// 3.1.1.1. O sistema gera a Anormalidade de Consumo com o valor
		// correspondente a estouro de consumo da tabela
		// CONSUMO_ANORMALIDADE
		consumo.setAnormalidadeConsumo(anormConsumo);

		if (idLeituraAnormalidadeConsumo == NAO_OCORRE) {

		    consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);

		} else if (idLeituraAnormalidadeConsumo == MINIMO) {

		    // O Consumo a Ser Cobrado no Mês será o valor retornado
		    // por [UC0105 – Obter Consumo Mínimo da Ligação
		    consumo.setConsumoCobradoMes(getImovelSelecionado()
			    .getConsumoMinimoImovel());
		    // Seta o tipo de consumo
		    consumo.setTipoConsumo(CONSUMO_TIPO_MINIMO_FIX);

		} else if (idLeituraAnormalidadeConsumo == MEDIA) {

		    // Consumo a ser cobrado no mês será o consumo médio do
		    // hidrômetro
		    consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);

		} else if (idLeituraAnormalidadeConsumo == NORMAL) {

		    // Fazer nada já calculado

		} else if (idLeituraAnormalidadeConsumo == MAIOR_ENTRE_O_CONSUMO_MEDIO) {

		    if (cMedio > consumo.getConsumoCobradoMes()) {
			consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
			consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    }

		} else if (idLeituraAnormalidadeConsumo == MENOR_ENTRE_O_CONSUMO_MEDIO) {
		    if (cMedio < consumo.getConsumoCobradoMes()) {
			consumo.setConsumoCobradoMes(cMedio);
            consumo.setLeituraAtual(reg8.getLeituraAnterior() + cMedio);
            reg8.setLeituraAtualFaturamento(reg8.getLeituraAnterior() + cMedio);
			consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    }

		}

		// 3.1.4. O consumo a Ser Cobrado no Mês será igual
		// ao Consumo a Ser Cobrado no Mês multiplicado pelo
		// fator de multiplicação da quantidade de vezes a média
		// (CSAA_NNFATORCONSUMOMES(1,2 ou 3), dependendo do mês
		// calculado anteriormente
		if (numerofatorConsumo != Constantes.NULO_DOUBLE) {
		    double consumofaturadoMes = consumo.getConsumoCobradoMes();
		    consumofaturadoMes = consumofaturadoMes
			    * numerofatorConsumo;
		    int consumofaturadoMesInt = Util
			    .arredondar(consumofaturadoMes);
		    consumo.setConsumoCobradoMes(consumofaturadoMesInt);
		}

	    } else {
   
			consumo.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_BAIXO_CONSUMO);

	    }

	}

    }

    /**
     * [UC0101] - Consistir Leituras e Calcular Consumos [SF0012] - Obter
     * Leitura Anterior
     * 
     * @author: Breno Santos
     */
    protected int obterLeituraAnterior(Medidor reg8) {
	int retorno = 0;

	if (reg8 != null) {

	    if (reg8.getLeituraAnteriorInformada() != Constantes.NULO_INT
		    && reg8.getLeitura() != Constantes.NULO_INT) {

		if (reg8.getLeituraAnteriorInformada() == reg8.getLeitura()) {
		    retorno = reg8.getLeituraAnteriorInformada();
		} else {
		    retorno = reg8.getLeituraAnteriorFaturamento();
		}

	    } else {

		retorno = reg8.getLeituraAnteriorFaturamento();
	    }

	}
	return retorno;
    }

    private void dadosFaturamentoEspecialNaoMedido(Consumo consumo, int ligacaoTipo) {

	Medidor reg8 = getImovelSelecionado().getRegistro8(ligacaoTipo);

	int cMedio;

	// Verificamos se o consumo médio veio do
	// registro tipo 8 ou do imóvel
	if (reg8 != null) {
	    cMedio = reg8.getConsumoMedio();
	} else {
	    cMedio = getImovelSelecionado().getConsumoMedio();
	}

	if (getImovelSelecionado().getSituacaoTipo() != null) {
	    if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoSemLeitura() == NAO_OCORRE) {
		consumo.setConsumoCobradoMes(cMedio);
		consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_IMOV);
	    } else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoSemLeitura() == MINIMO) {
	    	consumo.setConsumoCobradoMes(getImovelSelecionado().getConsumoMinimoImovel());
		consumo.setTipoConsumo(CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);
	    } else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoSemLeitura() == MEDIA) {
		consumo.setConsumoCobradoMes(cMedio);
		consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
// Daniel - Situaçao especial de faturamento - "FATURAR CONSUMO/VOLUME INFORMADO"
	    } else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoSemLeitura() == FIXO) {
			consumo.setTipoConsumo(CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);

			if (ligacaoTipo == Constantes.LIGACAO_AGUA) {
			    if (getImovelSelecionado().getSituacaoTipo().getConsumoAguaNaoMedidoHistoricoFaturamento() != Constantes.NULO_INT) {
				    consumo.setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo().getConsumoAguaNaoMedidoHistoricoFaturamento());
			    }
			} else if (getImovelSelecionado().getSituacaoTipo().getVolumeEsgotoNaoMedidoHistoricoFaturamento() != Constantes.NULO_INT) {
				consumo.setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo().getVolumeEsgotoNaoMedidoHistoricoFaturamento());
			}

	    } else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeLeituraSemLeitura() == FIXO) {
		consumo.setTipoConsumo(CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);

		if (ligacaoTipo == Constantes.LIGACAO_AGUA) {

		    if (getImovelSelecionado().getSituacaoTipo()
			    .getConsumoAguaNaoMedidoHistoricoFaturamento() != Constantes.NULO_INT) {

			/*
			 * Caso o consumo calculado seja MENOR que o consumo
			 * fixo, colocar o consumo calculado; caso contrário,
			 * colocar o consumo fixo.
			 */
			if (consumo.getConsumoCobradoMes() > getImovelSelecionado().getSituacaoTipo()
				.getConsumoAguaNaoMedidoHistoricoFaturamento()) {

			    consumo
				    .setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo()
					    .getConsumoAguaNaoMedidoHistoricoFaturamento());
			}
		    }

		} else if (getImovelSelecionado().getSituacaoTipo()
			.getVolumeEsgotoNaoMedidoHistoricoFaturamento() != Constantes.NULO_INT) {
		    /*
		     * Caso o volume calculado seja MENOR que o volume fixo,
		     * colocar o volume calculado; caso contrário, colocar o
		     * volume fixo.
		     */
		    if (consumo.getConsumoCobradoMes() > getImovelSelecionado().getSituacaoTipo()
			    .getVolumeEsgotoNaoMedidoHistoricoFaturamento()) {

			consumo
				.setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo()
					.getVolumeEsgotoNaoMedidoHistoricoFaturamento());
		    }
		}

	    } else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeLeituraSemLeitura() == NAO_MEDIDO) {
		// Seta o tipo de consumo
		consumo.setTipoConsumo(CONSUMO_TIPO_ESTIMADO);

		consumo.setConsumoCobradoMes(getImovelSelecionado().getConsumoMinimoImovel());
	    }
	}
    }

    private void dadosFaturamentoEspecialMedido(Consumo consumo, int ligacaoTipo) {

	Medidor imReg8 = getImovelSelecionado().getRegistro8(ligacaoTipo);

	int cMedio;

	// Verificamos se o consumo médio veio do
	// registro tipo 8 ou do imóvel
	if (imReg8 != null) {
	    cMedio = imReg8.getConsumoMedio();
	} else {
	    cMedio = getImovelSelecionado().getConsumoMedio();
	}

	int leituraAnterior = obterLeituraAnterior(imReg8);

	if (getImovelSelecionado().getSituacaoTipo() != null) {
	    if (imReg8 != null && imReg8.getLeitura() == Constantes.NULO_INT) {

		if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoSemLeitura() == NAO_OCORRE) {
		    consumo.setConsumoCobradoMes(cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_IMOV);
		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoSemLeitura() == MINIMO) {
			consumo.setConsumoCobradoMes(getImovelSelecionado().getConsumoMinimoImovel());
			consumo.setTipoConsumo(CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);
		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoSemLeitura() == MEDIA) {
		    consumo.setConsumoCobradoMes(cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		 // Daniel - Situaçao especial de faturamento - "FATURAR CONSUMO/VOLUME INFORMADO"
	    } else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoSemLeitura() == FIXO) {
			consumo.setTipoConsumo(CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);

			if (ligacaoTipo == Constantes.LIGACAO_AGUA) {
			    if (getImovelSelecionado().getSituacaoTipo().getConsumoAguaMedidoHistoricoFaturamento() != Constantes.NULO_INT) {
					consumo.setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo().getConsumoAguaMedidoHistoricoFaturamento());
			    }
			} else if (getImovelSelecionado().getSituacaoTipo().getVolumeEsgotoMedidoHistoricoFaturamento() != Constantes.NULO_INT) {
			    consumo.setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo().getVolumeEsgotoMedidoHistoricoFaturamento());
			}

		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeLeituraSemLeitura() == FIXO) {
		    consumo.setTipoConsumo(CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);

		    if (ligacaoTipo == Constantes.LIGACAO_AGUA) {

			if (getImovelSelecionado().getSituacaoTipo()
				.getConsumoAguaMedidoHistoricoFaturamento() != Constantes.NULO_INT) {

			    /*
			     * Caso o consumo calculado seja MENOR que o consumo
			     * fixo, colocar o consumo calculado; caso
			     * contrário, colocar o consumo fixo.
			     */
			    if (consumo.getConsumoCobradoMes() > getImovelSelecionado().getSituacaoTipo()
				    .getConsumoAguaMedidoHistoricoFaturamento()) {

				consumo
					.setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo()
						.getConsumoAguaMedidoHistoricoFaturamento());
			    }
			}

		    } else if (getImovelSelecionado().getSituacaoTipo()
			    .getVolumeEsgotoMedidoHistoricoFaturamento() != Constantes.NULO_INT) {
			/*
			 * Caso o volume calculado seja MENOR que o volume fixo,
			 * colocar o volume calculado; caso contrário, colocar o
			 * volume fixo.
			 */
			if (consumo.getConsumoCobradoMes() > getImovelSelecionado().getSituacaoTipo()
				.getVolumeEsgotoMedidoHistoricoFaturamento()) {

			    consumo
				    .setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo()
					    .getVolumeEsgotoMedidoHistoricoFaturamento());
			}
		    }

		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeLeituraSemLeitura() == NAO_MEDIDO) {
		    // Seta o tipo de consumo
		    consumo.setTipoConsumo(CONSUMO_TIPO_ESTIMADO);

		    consumo.setConsumoCobradoMes(getImovelSelecionado()
			    .getConsumoMinimoImovel());
		}

		// Caso leitura atual informada diferente de zero
	    } else if (imReg8 != null
		    && imReg8.getLeitura() != Constantes.NULO_INT) {
		// Caso a leitura anormalidade leitura com leitura seja igual a
		// leitura anormalidade consumo não ocorre
		if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoComLeitura() == NAO_OCORRE) {
		    // O consumo a ser cobrado no mes será o consumo médio do
		    // hidrômetro
		    consumo.setConsumoCobradoMes(cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		}
		// Caso a leitura anormalidade leitura com leitura seja igual a
		// leitura anormalidade consumo mínimo
		else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoComLeitura() == MINIMO) {

		    consumo.setConsumoCobradoMes(getImovelSelecionado().getConsumoMinimoImovel());
		    consumo.setTipoConsumo(CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);

		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoComLeitura() == MEDIA) {
		    // O consumo a ser cobrado no mes será o consumo médio do
		    // hidrômetro
		    consumo.setConsumoCobradoMes(cMedio);
		    consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    // Caso a leitura anormalidade leitura com leitura seja
		    // igual a
		    // leitura anormalidade consumo medido
		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoComLeitura() == MAIOR_ENTRE_O_CONSUMO_MEDIO) {
		    // Caso o consumo médio hidrômetro seja maior que o consumo
		    // medido
		    if (cMedio > consumo.getConsumoCobradoMes()) {
			// Consumo a ser cobrado no mês será o já calculado
			consumo.setConsumoCobradoMes(cMedio);
			// Seta o tipo de consumo
			consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    } else {
			consumo.setConsumoCobradoMes(consumo
				.getConsumoCobradoMes());
		    }

		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoComLeitura() == MENOR_ENTRE_O_CONSUMO_MEDIO) {
		    // Caso o consumo médio hidrômetro seja maior que o consumo
		    // medido
		    if (cMedio < consumo.getConsumoCobradoMes()) {
			// Consumo a ser cobrado no mês será o já calculado
			consumo.setConsumoCobradoMes(cMedio);
			// Seta o tipo de consumo
			consumo.setTipoConsumo(CONSUMO_TIPO_MEDIA_HIDR);
		    } else {
			consumo.setConsumoCobradoMes(consumo
				.getConsumoCobradoMes());
		    }

		}

		/*
		 * Colocado por Raphael Rossiter em 12/08/2008 - Analista:
		 * Rosana Carvalho OBJ: Verificar a situação especial de
		 * faturamento quando o consumo de água e/ou volume de esgoto
		 * está fixo.
		 */
		else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoComLeitura() == FIXO) {

		    // Seta o tipo de consumo
		    consumo.setTipoConsumo(CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL);

		    // Consumo a ser cobrado no mês será o consumo fixado no
		    // histórico da situação especial
		    if (ligacaoTipo == Constantes.LIGACAO_AGUA) {

			if (getImovelSelecionado().getSituacaoTipo()
				.getConsumoAguaMedidoHistoricoFaturamento() != Constantes.NULO_INT) {

			    consumo
				    .setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo()
					    .getConsumoAguaMedidoHistoricoFaturamento());

			}

		    } else if (getImovelSelecionado().getSituacaoTipo()
			    .getVolumeEsgotoMedidoHistoricoFaturamento() != Constantes.NULO_INT) {

			consumo.setConsumoCobradoMes(getImovelSelecionado().getSituacaoTipo()
				.getVolumeEsgotoMedidoHistoricoFaturamento());

		    }
		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeConsumoComLeitura() == NAO_MEDIDO) {

		    // Seta o tipo de consumo
		    consumo.setTipoConsumo(CONSUMO_TIPO_ESTIMADO);

		    consumo.setConsumoCobradoMes(getImovelSelecionado()
			    .getConsumoMinimoImovel());

		}

		// Caso a leitura anormalidade leitura com leitura
		// seja igual a leitura anormaliade leitura ->
		// <<anterior mais média>>
		if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeLeituraComLeitura() == ANTERIOR_MAIS_A_MEDIA) {
		    // Seta a leitura atual de faturamento
		    consumo.setLeituraAtual(leituraAnterior + cMedio);
            imReg8.setLeituraAtualFaturamento(leituraAnterior + cMedio);
		    // <<anterior>>
		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeLeituraComLeitura() == ANTERIOR) {
		    // Seta a leitura atual de faturamento
		    consumo.setLeituraAtual(leituraAnterior);
            imReg8.setLeituraAtualFaturamento(leituraAnterior);
		    // <<anterior mais consumo>>
		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeLeituraComLeitura() == ANTERIOR_MAIS_O_CONSUMO) {
		    // Seta a leitura atual de faturamento
		    consumo.setLeituraAtual(leituraAnterior + consumo.getConsumoCobradoMes());
            imReg8.setLeituraAtualFaturamento(leituraAnterior + consumo.getConsumoCobradoMes());
		    // <<leitura informada>>
		} else if (getImovelSelecionado().getSituacaoTipo().getIdAnormalidadeLeituraComLeitura() == INFORMADO) {
		    consumo.setLeituraAtual(consumo.getLeituraAtual());
            imReg8.setLeituraAtualFaturamento(consumo.getLeituraAtual());
		}
	    }
	}
    }


    /**
     * Consistir Leituras
     */
    protected void ajusteLeitura( Medidor reg8,
	    int tipoMedicao, Consumo consumo, DadosGerais reg11) {
    	
    }
    
    /**
     * [UC0101] - Consistir Leituras e Calcular Consumos [SF0017] - Ajuste
     * Mensal de Consumo
     */
    protected void ajusteMensalConsumo( Medidor reg8,
	    int tipoMedicao, Consumo consumo) {

	Date dataLeituraAnteriorFaturamento = null;
	Date dataLeituraLeituraAtualFaturamento = null;
	int leituraAjustada = reg8.getLeitura();
	
	dataLeituraAnteriorFaturamento = reg8.getDataLeituraAnteriorFaturada();
	dataLeituraLeituraAtualFaturamento = reg8.getDataLeitura();

	System.out.println("Leitura Anterior Faturamento: "
		+ dataLeituraAnteriorFaturamento);
	System.out.println("Leitura Atual Faturamento: "
		+ dataLeituraLeituraAtualFaturamento);
	
	int quantidadeDiasConsumoAjustado = 0;

	// Obtém a quantidade de dias de consumo
	int quantidadeDiasConsumo = 0;

	//    Daniel - Imovel antes fixo e agora hidrometrado.
	if (isImovelFixoComHidrometroInstalado(tipoMedicao)){

		quantidadeDiasConsumo = (int) Util.obterModuloDiferencasDatasDias(
				getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), reg8.getDataLeitura());

	}else{
		quantidadeDiasConsumo = (int) Util.obterModuloDiferencasDatasDias(
		dataLeituraAnteriorFaturamento, dataLeituraLeituraAtualFaturamento);
		
	}

	/*
	 * Caso a quantidade de dias não seja maior do que zero, retornar
	 * para correspondente no subfluxo que chamou.
	 * 
	 * Data: 05/06/2011 Desenvolvedor:Daniel Zaccarias
	 */

	if (quantidadeDiasConsumo > 0) {

		Date dataLeituraNaoMedidoAtual;
		int diasConsumoLido  = (int) Util.obterModuloDiferencasDatasDias(reg8.getDataLeitura(), reg8.getDataInstalacao());
		
//	    Daniel - Imovel com nova instalacao de fornecimento hidrometrado.
		if (isImovelNovaInstalacaoHidrometro(tipoMedicao)){

			// Obtém a quantidade de dias de consumo ajustado
			if(DadosGerais.getInstancia().getQtdDiasAjusteConsumo() != Constantes.NULO_INT){
				// Seta a data com a data de referencia da rota/grupo atual
			    dataLeituraNaoMedidoAtual = Util.adicionarNumeroDiasDeUmaData(getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), (long)DadosGerais.getInstancia().getQtdDiasAjusteConsumo());

			}else{
			    dataLeituraNaoMedidoAtual = Util.adicionarNumeroDiasDeUmaData(getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), (long)30);
			}

			// Numero de dias ajustado é diferença entre a data de referencia nao-medido do mes atual e a data da instalaçao do hidrometro  		    
			quantidadeDiasConsumoAjustado = (int) Util.obterModuloDiferencasDatasDias(reg8.getDataInstalacao(), dataLeituraNaoMedidoAtual);
		
		
//	    Daniel - Imovel antes fixo e agora hidrometrado.
		}else if (isImovelFixoComHidrometroInstalado(tipoMedicao)){

			if(DadosGerais.getInstancia().getQtdDiasAjusteConsumo() != Constantes.NULO_INT){
			    quantidadeDiasConsumoAjustado = (int) DadosGerais.getInstancia().getQtdDiasAjusteConsumo();			

			}else{
			    quantidadeDiasConsumoAjustado = 30;			
				
			}
    	
	    }// Verifica se a data do ajuste é não nula
		else if (DadosGerais.getInstancia().getDataAjusteLeitura() != null) {
		// Obtém a quantidade de dias de consumo ajustado
			quantidadeDiasConsumoAjustado = (int) Util
				.obterModuloDiferencasDatasDias(
					dataLeituraAnteriorFaturamento, DadosGerais.getInstancia().getDataAjusteLeitura());

	    } else {

			// Obtém a quantidade de dias de consumo ajustado
			if(DadosGerais.getInstancia().getQtdDiasAjusteConsumo() != Constantes.NULO_INT){
			    quantidadeDiasConsumoAjustado = DadosGerais.getInstancia().getQtdDiasAjusteConsumo();

			}else{
				// Cria objeto
				Calendar data = Calendar.getInstance();
	
				// Seta a data com a data de leitura anterior faturamento
				data.setTime(dataLeituraAnteriorFaturamento);
	
				// Obtém a quantidade de dias
				int dias = Util.quantidadeDiasMes(data);
	
				quantidadeDiasConsumoAjustado = dias;
			}
		
	    }

	    reg8.setQtdDiasAjustado(quantidadeDiasConsumoAjustado);

	    // Obtém os dias de ajuste
	    int diasAjuste = quantidadeDiasConsumoAjustado
		    - quantidadeDiasConsumo;

	    if ( (diasAjuste < -3 || diasAjuste > 3) || isImovelHidrometroSubstituido(tipoMedicao) ) {

			// Cálculo para obter a leitura ajustada
			//Daniel - correçao bug: leitura faturada gerado incoerentemente em situacoes em que nao é registrado leitura e sim anormalidade.
		    //Daniel - Nao deve ajustar para anormalidade de consumo - leitura menor que anterior.

	    	double consumoDiario = 0;
	    	//Daniel - Hidrometro substituido.
			if (reg8.getLeitura() != Constantes.NULO_INT && 
				isImovelHidrometroSubstituido( tipoMedicao) &&
				consumo.getTipoConsumo() != CONSUMO_TIPO_MEDIA_HIDR){

				if (diasConsumoLido != 0){
					consumoDiario = Util.arredondar(((double) (reg8.getLeitura() - reg8.getLeituraInstalacaoHidrometro() )/ (double) diasConsumoLido), 3);
				}
				
				Date dataLeituraReferenciaAtual;
				
				if(DadosGerais.getInstancia().getQtdDiasAjusteConsumo() != Constantes.NULO_INT){
					// Seta a data com a data de referencia da rota/grupo atual
				    dataLeituraReferenciaAtual = Util.adicionarNumeroDiasDeUmaData(reg8.getDataLeituraAnteriorFaturada(), (long)DadosGerais.getInstancia().getQtdDiasAjusteConsumo());
				}else{
				    dataLeituraReferenciaAtual = Util.adicionarNumeroDiasDeUmaData(reg8.getDataLeituraAnteriorFaturada(), (long)30);
				}
				
				int diasConsumoLidoAjustado = (int) Util.obterModuloDiferencasDatasDias( dataLeituraReferenciaAtual, reg8.getDataInstalacao() );
					
				leituraAjustada = reg8.getLeituraInstalacaoHidrometro() + (int) Util.arredondar((consumoDiario * diasConsumoLidoAjustado), 0);
			
//		    Daniel - Imovel antes fixo e agora hidrometrado.
			}else if(reg8.getLeitura() != Constantes.NULO_INT &&
					isImovelFixoComHidrometroInstalado(tipoMedicao) &&
					consumo.getTipoConsumo() != CONSUMO_TIPO_MEDIA_HIDR){
			    	
				if (diasConsumoLido != 0){
					consumoDiario = Util.arredondar(((double) (reg8.getLeitura() - this.obterLeituraAnterior(reg8) )/ (double) diasConsumoLido), 3);
				}

				// Seta a data com a data de referencia da rota/grupo atual
			    dataLeituraNaoMedidoAtual = Util.adicionarNumeroDiasDeUmaData(getImovelSelecionado().getDataLeituraAnteriorNaoMedido(), (long)DadosGerais.getInstancia().getQtdDiasAjusteConsumo());
	
				int diasConsumoLidoAjustado = (int) Util.obterModuloDiferencasDatasDias( dataLeituraNaoMedidoAtual, reg8.getDataInstalacao() );
					
				leituraAjustada = this.obterLeituraAnterior(reg8) + (int) Util.arredondar((consumoDiario * diasConsumoLidoAjustado), 0);
			
			}else if (reg8.getLeitura() != Constantes.NULO_INT &&	consumo.getTipoConsumo() != CONSUMO_TIPO_MEDIA_HIDR){

		    	leituraAjustada = reg8.getLeitura()
					+ Util.divideDepoisMultiplica(consumo
							.getConsumoCobradoMes(), quantidadeDiasConsumo,	diasAjuste);
		    
		    }else if ((consumo.getTipoConsumo() == CONSUMO_TIPO_MEDIA_HIDR) ||
		    		(reg8.getLeitura() == Constantes.NULO_INT && consumo.getAnormalidadeLeituraFaturada() > 0)){
		    	
		    	leituraAjustada = consumo.getLeituraAtual();
		    
		    }else{
		    
		    	leituraAjustada = reg8.getLeitura();
		    }
		    
	
//Daniel - Consumo realizado pelo valor medio nao deve fazer reajuste de consumo
//pois o consumo médio já é baseado no numero de dias ajustado (28 - 31 dias).
			
			// Obtém o consumo a ser cobrado mês
			
			if ((leituraAjustada != Constantes.NULO_INT && 
				 reg8.getLeitura() != Constantes.NULO_INT && 
				 consumo.getTipoConsumo() != CONSUMO_TIPO_MEDIA_HIDR) &&
				(isImovelHidrometroSubstituido(reg8.getTipoMedicao()) || 
				 isImovelFixoComHidrometroInstalado(reg8.getTipoMedicao()))){
				
				if (diasConsumoLido > 9) {
				    consumo.setConsumoCobradoMes((int) Util.arredondar((consumoDiario * quantidadeDiasConsumoAjustado), 0));
				    consumo.setTipoConsumo(CONSUMO_TIPO_ESTIMADO);
				}else{
				    consumo.setConsumoCobradoMes(getImovelSelecionado().getconsumoMinimoImovelNaoMedido());
				    consumo.setTipoConsumo(CONSUMO_TIPO_MINIMO_FIX);
				}

			}else if (leituraAjustada != Constantes.NULO_INT && 
					  reg8.getLeitura() != Constantes.NULO_INT && 
					  consumo.getTipoConsumo() != CONSUMO_TIPO_MEDIA_HIDR){
				
				int consumoASerCobradoMes = Util.divideDepoisMultiplica(consumo
						.getConsumoCobradoMes(), quantidadeDiasConsumo,
						quantidadeDiasConsumoAjustado);
				
				// Seta o consumo a ser cobrado mês
				consumo.setConsumoCobradoMes(consumoASerCobradoMes);
			}
			// Adiciona ou subtrai de acordo com os dias de ajuste
			Date dataLeituraAtualFaturamento = Util.adicionarNumeroDiasDeUmaData(reg8.getDataLeitura(),	diasAjuste);
				
			// Seta a data da leitura atual de faturamento
			reg8.setDataLeituraAtualFaturamento(dataLeituraAtualFaturamento);
			
			if (consumo.getTipoConsumo() != CONSUMO_TIPO_MEDIA_HIDR &&
				consumo.getTipoConsumo() != CONSUMO_TIPO_MINIMO_FIX &&
				consumo.getTipoConsumo() != CONSUMO_TIPO_NAO_MEDIDO &&
				consumo.getTipoConsumo() != CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL){
				
					consumo.setTipoConsumo(CONSUMO_TIPO_AJUSTADO);
				
			}
	    }else{
			reg8.setDataLeituraAtualFaturamento(reg8.getDataLeitura());

	    	// Se nao foi necessario reajuste de leitura, mantem a leitura informada.
	    	if(reg8.getLeitura() != Constantes.NULO_INT){
		    	reg8.setLeituraAtualFaturamento(reg8.getLeitura());
			    consumo.setLeituraAtual(reg8.getLeituraAtualFaturamento());
	    	}
	    }
	}
	
	// Obtém 10 elevado ao numeroDigitosHidrometro
	int dezElevadoNumeroDigitos = (int) Util.pow(10,
			this.obterNumeroDigitosHidrometro(tipoMedicao));

	// Caso a leitura ajustada menor que zero
	//Daniel - correçao bug: leitura faturada gerado incoerente em situacoes em que nao é regitrado leitura e sim anormalidade.
	if (leituraAjustada == Constantes.NULO_INT){

		if ( consumo.getLeituraAtual() > (dezElevadoNumeroDigitos - 1) ) {

			reg8.setLeituraAtualFaturamento(consumo.getLeituraAtual() - (dezElevadoNumeroDigitos-1));
			leituraAjustada = leituraAjustada - (dezElevadoNumeroDigitos-1);
				
			// Daniel - caso devido ao valor da leitura ajustada ocasione em virada de hidrometro e nao houver nenhuma outra anormalidade já configurada.
			if(consumo.getAnormalidadeConsumo() == 0 || 
					consumo.getAnormalidadeConsumo() == Consumo.ANORMALIDADE_LEITURA){
				
				consumo.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_VIRADA_HIDROMETRO);
			}
				
		}else{
			reg8.setLeituraAtualFaturamento(consumo.getLeituraAtual());
			
		}
		    
	}else if (leituraAjustada < 0) {
			
		reg8.setLeituraAtualFaturamento(leituraAjustada + dezElevadoNumeroDigitos);

		// Caso a leitura ajustada maior que dez elevado ao número de dígitos menos um
	} else if (leituraAjustada > (dezElevadoNumeroDigitos - 1) ) {

		reg8.setLeituraAtualFaturamento(leituraAjustada - (dezElevadoNumeroDigitos-1));
		leituraAjustada = leituraAjustada - (dezElevadoNumeroDigitos-1);
			
		// Daniel - caso devido ao valor da leitura ajustada ocasione em virada de hidrometro e nao houver nenhuma outra anormalidade já configurada.
		if(consumo.getAnormalidadeConsumo() == 0 || 
				consumo.getAnormalidadeConsumo() == Consumo.ANORMALIDADE_LEITURA){
			
			consumo.setAnormalidadeConsumo(Consumo.CONSUMO_ANORM_VIRADA_HIDROMETRO);
		}
			
	    // Caso demais casos
	} else {
	    reg8.setLeituraAtualFaturamento(leituraAjustada);
	}
		
    consumo.setLeituraAtual(reg8.getLeituraAtualFaturamento());

	System.out.println("Data Leitura Atual Faturada!!!!!!!!: "
			+ reg8.getDataLeituraAtualFaturamento());
	System.out.println("leituraAjustada!!!!!!!!: " + leituraAjustada);
	
    }
    
    protected Imovel getImovelSelecionado(){
    	return ControladorImoveis.getInstancia().getImovelSelecionado();
    }
    
    public boolean recalcularContasCondominio(int idPrimeiroImovelMicro, int idUltimoImovelMicro, int consumoTipo){
    	return true;
    }

// Daniel - Verifica se o imovel era Nao_medido e agora recebeu um hidrometro.    
    public boolean isImovelFixoComHidrometroInstalado(int tipoMedicao){
		
    	boolean result = false;
    	// Imovel Cortado nao necessita verificar.
    	if (!(getImovelSelecionado().getSituacaoLigAgua()).equals(Constantes.CORTADO)){
		
    		//Se data de instalaçao do hidrometro IGUAL data de leitura anterior informada   E   
    		//Data de ligação de fornecimento ANTERIOR à data de instalaçao do Hidrometro
        	if (Util.compararData(getImovelSelecionado().getRegistro8(tipoMedicao).getDataInstalacaoHidrometro(), 
    				getImovelSelecionado().getRegistro8(tipoMedicao).getDataLeituraAnteriorInformada()) == 0 &&
    				Util.compararData(getImovelSelecionado().getRegistro8(tipoMedicao).getDataLigacaoFornecimento(), 
    						getImovelSelecionado().getRegistro8(tipoMedicao).getDataInstalacaoHidrometro()) < 0 ){
       		 
       		 	result = true;
        	}
		}
    	return result;
    }
    
 // Daniel - Verifica se o imovel teve hidrometro substituido.    
    public boolean isImovelHidrometroSubstituido(int tipoMedicao){
   
    	boolean result = false;
    	// Imovel Cortado nao necessita verificar.
    	if (!(getImovelSelecionado().getSituacaoLigAgua()).equals(Constantes.CORTADO)){

    		//Se data de instalaçao do hidrometro APOS OU IGUAL data de leitura anterior informada   E   
    		//Data de instalaçao do hidrometro ANTERIOR OU IGUAL que data de leitura atual informada E
    		//Houve leitura ou anormalidade no mes anterior (para diferenciar de nova instalação de hidrometro)
	    	 if ( ( Util.compararData(getImovelSelecionado().getRegistro8(tipoMedicao).getDataInstalacao(), 
	    			 	getImovelSelecionado().getRegistro8(tipoMedicao).getDataLeituraAnteriorInformada()) >= 0 ) && 
	    			(Util.compararData(getImovelSelecionado().getRegistro8(tipoMedicao).getDataInstalacao(), 
	    				getImovelSelecionado().getRegistro8(tipoMedicao).getDataLeitura()) <= 0 ) &&
	    			(houveLeituraOuAnormalidadeLeituraMesAnterior(tipoMedicao)) ) {
	    		 
	    		 result = true;
	    	 }
    	}
    	return result;    
    }
    
    
//    Daniel - Verifica se é imovel com nova instalacao de fornecimento hidrometrado.
    public boolean isImovelNovaInstalacaoHidrometro(int tipoMedicao){

    	boolean result = false;

    	// Imovel Cortado nao necessita verificar.
    	if (!(getImovelSelecionado().getSituacaoLigAgua()).equals(Constantes.CORTADO)){
    		
    		//Se data de instalaçao do hidrometro IGUAL data de leitura anterior informada   E   
    		//Data de ligação de fornecimento IGUAL à data de instalaçao do Hidrometro.
    	    if (Util.compararData(getImovelSelecionado().getRegistro8(tipoMedicao).getDataInstalacao(), 
    	    		getImovelSelecionado().getRegistro8(tipoMedicao).getDataLeituraAnteriorInformada()) == 0 &&
    			Util.compararData(getImovelSelecionado().getRegistro8(tipoMedicao).getDataLigacaoFornecimento(), 
    					getImovelSelecionado().getRegistro8(tipoMedicao).getDataInstalacaoHidrometro()) == 0 ){
    	    	
    	    	result =  true;
    	    }
    	}
    	return result;
    }
    
    public boolean isForaDeFaixa(Medidor reg8){
		boolean foraDeFaixa = false;

//		Considerando a possibilidade de virada do hidrometro.
		if (reg8.getLeituraEsperadaInicial() > reg8.getLeituraEsperadaFinal()){
	
			if (reg8.getLeituraAtualFaturamento() < reg8.getLeituraEsperadaInicial() 
					&& reg8.getLeituraAtualFaturamento() > reg8.getLeituraEsperadaFinal()) {
			
				foraDeFaixa = true;
			}
		}else {
			if (reg8.getLeituraAtualFaturamento() < reg8.getLeituraEsperadaInicial() || 
					reg8.getLeituraAtualFaturamento() > reg8.getLeituraEsperadaFinal()) {
				
				foraDeFaixa = true;
			
			}
		}

		return foraDeFaixa;
    }
    
    public boolean houveLeituraOuAnormalidadeLeituraMesAnterior(int tipoMedicao){
    	boolean resultado = false;

    	if(getImovelSelecionado().getRegistro8(tipoMedicao).getLeituraAnteriorInformada() != Constantes.NULO_INT ){
    		resultado = true;
    	}
    	
    	if(!resultado){
	    	Vector regsTipo3 = getImovelSelecionado().getRegistros3();
		    if (regsTipo3 != null) {
		    	
		    	// Obtem o registro do mes anterior.
		    	HistoricoConsumo reg3 = (HistoricoConsumo)regsTipo3.elementAt(0);
		    	
		    	if (reg3.getAnormalidadeLeitura() != Constantes.NULO_INT &&
		    			reg3.getAnormalidadeLeitura() != 0){
		    		resultado = true;
		    	}
		    }
		    if(regsTipo3 != null){
			    regsTipo3 = null;	    	
		    }
    	}
    	return resultado;
    }
    
    public void chamaAjusteConsumo(int tipoMedicao){

    	// Caso esteja indicado o ajuste mensal do consumo
    	if (DadosGerais.getInstancia().getIndicadorAjusteConsumo() != Constantes.NULO_INT
    		&& DadosGerais.getInstancia().getIndicadorAjusteConsumo() == Constantes.SIM) {
    	    // [SF0017] - Ajuste Mensal do Consumo
    	    if (tipoMedicao == LIGACAO_AGUA) {
    		ajusteMensalConsumo(getImovelSelecionado().getRegistro8(tipoMedicao), tipoMedicao, consumoAgua);
    	    }
    	    if (tipoMedicao == LIGACAO_POCO) {
    		ajusteMensalConsumo(getImovelSelecionado().getRegistro8(tipoMedicao), tipoMedicao, consumoEsgoto);
    	    }
    	}
    	
    }
    
    public int obterNumeroDigitosHidrometro(int tipoMedicao){
    	int numeroDigitosHidrometro = 0;
    	
    	// Verifica qual o tipo de medição e obtém o número de dígitos
    	// do hidrômetro
    	if (tipoMedicao == LIGACAO_AGUA) {
    	    numeroDigitosHidrometro = getImovelSelecionado().getRegistro8(
    		    Constantes.LIGACAO_AGUA).getNumDigitosLeitura();

    	} else if (tipoMedicao == LIGACAO_POCO) {
    	    numeroDigitosHidrometro = getImovelSelecionado().getRegistro8(
    		    Constantes.LIGACAO_POCO).getNumDigitosLeitura();
    	}
    	return numeroDigitosHidrometro;

    }

    public String getTipoConsumoToPrint(int tipoConsumo){
    	
    	String resultado = "CONSUMO NAO MEDIDO(m3)";
    	
    	if (tipoConsumo == CONSUMO_TIPO_MEDIA_HIDR){
    		resultado =	"CONSUMO MEDIO(m3)";
    	
    	}else if (tipoConsumo == CONSUMO_TIPO_MINIMO_FIX){
    		resultado =	"CONSUMO MINIMO(m3)";

    	}else if (tipoConsumo == CONSUMO_TIPO_NAO_MEDIDO){
    		resultado =	"CONSUMO NAO MEDIDO(m3)";

    	}else if (tipoConsumo == CONSUMO_TIPO_REAL){
    		resultado =	"CONSUMO REAL(m3)";

    	}else if (tipoConsumo == CONSUMO_TIPO_AJUSTADO){
    		resultado =	"CONSUMO PROPOR. DIAS(m3)";

    	}else if (tipoConsumo == CONSUMO_TIPO_FIXO_SITUACAO_ESPECIAL){
    		resultado =	"CONSUMO SIT. ESPECIAL(m3)";

    	}
    	return resultado;
    }
}
