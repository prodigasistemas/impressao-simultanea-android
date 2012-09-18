package dataBase;

import helper.EfetuarRateioConsumoHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Anormalidade;
import model.Configuracao;
import model.Consumo;
import model.Conta;
import model.Credito;
import model.DadosCategoria;
import model.DadosGerais;
import model.Debito;
import model.HistoricoConsumo;
import model.Imovel;
import model.Imposto;
import model.Medidor;
import model.SituacaoTipo;
import model.TarifacaoComplementar;
import model.TarifacaoMinima;
import util.Constantes;
import util.ParserUtil;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import business.ControladorImovel;
import business.ControladorRota;

public class DataManipulator {
	private static Context context;
	private DbHelper openHelper;
	static SQLiteDatabase db;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public DataManipulator(Context context) {
		DataManipulator.context = context;
		openHelper = new DbHelper(DataManipulator.context);
	}

	public DataManipulator open() throws SQLException {
		db = openHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		openHelper.close();
	}

	public int getNumeroImoveis() {
		return (int) DatabaseUtils.queryNumEntries(db, Constantes.TABLE_IMOVEL);
	}

	public DadosGerais getDadosGerais() {
		return ControladorRota.getInstancia().getDadosGerais();
	}
	
	public List<DadosCategoria> getDadosCategoria() {
		return ControladorImovel.getInstancia().getImovelSelecionado().getDadosCategoria();
	}

//	public Medidor getMedidorSelecionado() {
//		return ControladorImovel.getInstancia().getImovelSelecionado().getMedidores().get(0);
//	}

//	public Conta getContaSelecionado() {
//		return ControladorImovel.getInstancia().getImovelSelecionado().getContas().get(0);
//	}

	public void deleteTable(String tableName) {
		db.delete(tableName, null, null);
	}

	public void deleteElementId(int rowId, String tableName) {
		db.delete(tableName, null, null);
	}

	public List<String> selectEnderecoImoveis(String condition) {

		ArrayList<String> list = new ArrayList<String>();
		Cursor cursor;

		cursor = db.query(Constantes.TABLE_IMOVEL, new String[] { "matricula",	"endereco" }, condition, null, null, null,
				"inscricao asc");

		int x = 0;
		if (cursor.moveToFirst()) {
			do {
				
				String endereco = String.format("[%d] %s", cursor.getInt(0), cursor.getString(1));
				list.add(endereco);
				x = x + 1;
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return list;
	}

	public List<String> selectIdImoveis(String condition) {

		ArrayList<String> list = new ArrayList<String>();
		Cursor cursor;

		if (condition == Constantes.NULO_STRING || condition == null) {

			cursor = db.query(Constantes.TABLE_IMOVEL, new String[] { "id" },
							  null, null, null, null, "inscricao asc");

		} else {

			cursor = db.query(Constantes.TABLE_IMOVEL, new String[] { "id" },
							  condition, null, null, null, "inscricao asc");
		}

		if (cursor.moveToFirst()) {
			do {
				String b1 = cursor.getString(0);
				list.add(b1);
			} while (cursor.moveToNext());
		}

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return list;
	}

	public List<String> selectStatusImoveis(String condition) {

		ArrayList<String> list = new ArrayList<String>();
		Cursor cursor;

		if (condition == Constantes.NULO_STRING || condition == null) {

			cursor = db.query(Constantes.TABLE_IMOVEL, new String[] { "imovel_status" }, null, null, null, null,"inscricao asc");

		} else {

			cursor = db.query(Constantes.TABLE_IMOVEL, new String[] { "imovel_status" }, condition, null, null, null, "inscricao asc");
		}

		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return list;
	}
	
	public List<Integer> selectNumeroTodosStatusImoveis() {

		ArrayList<Integer> list = new ArrayList<Integer>();
		int visitados = 0;
		int naoVisitados = 0;
		int visitadosAnormalidade = 0;
		int naoImpressos = 0;
		int impressos = 0;
		int naoRetidos = 0;
		int retidos = 0;
		int transmitidos = 0;
		int naoTransmitidos = 0;

		Cursor cursor = db.query(Constantes.TABLE_IMOVEL, new String[] { "imovel_status", 
																		 "imovel_enviado", 
																		 "indc_imovel_impresso", 
																		 "indc_geracao" }, null, null, null, null, "inscricao asc");
		
		if (cursor.moveToFirst()) {
			do {

				// Verifica imovel_status
				if (Integer.parseInt(cursor.getString(0)) == Constantes.IMOVEL_PENDENTE) {
					naoVisitados++;

				} else if (Integer.parseInt(cursor.getString(0)) == Constantes.IMOVEL_CONCLUIDO) {
					visitados++;

				} else if (Integer.parseInt(cursor.getString(0)) == Constantes.IMOVEL_CONCLUIDO_COM_ANORMALIDADE) {
					visitadosAnormalidade++;
				}

				// Verifica imovel_enviado
				if (Integer.parseInt(cursor.getString(1)) == Constantes.SIM) {
					transmitidos++;

				} else if (Integer.parseInt(cursor.getString(1)) == Constantes.NAO) {
					naoTransmitidos++;
				}

				// Contabiliza imoveis impressos
				if (Integer.parseInt(cursor.getString(2)) == Constantes.SIM) {
					impressos++;
				}else{
					naoImpressos++;
				}
				
				// Contabiliza imoveis retidos
				if (Integer.parseInt(cursor.getString(3)) == Constantes.NAO) {
					retidos++;
				}else{
					naoRetidos++;
				}
				
			} while (cursor.moveToNext());

			list.add(visitados);
			list.add(naoVisitados);
			list.add(visitadosAnormalidade);
			list.add(transmitidos);
			list.add(naoTransmitidos);
			list.add(impressos);
			list.add(naoImpressos);
			list.add(retidos);
			list.add(naoRetidos);

		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return list;
	}

	public void selectGeral() {

		Cursor cursor = db.query(Constantes.TABLE_GERAL, new String[] {
				"data_referencia_arrecadacao", "ano_mes_faturamento",
				"codigo_empresa_febraban", "telefone0800", "cnpj_empresa",
				"inscricao_estadual_empresa", "valor_minim_emissao_conta",
				"percent_tolerancia_rateio",
				"decremento_maximo_consumo_economia",
				"incremento_maximo_consumo_economia", "indc_tarifa_catgoria",
				"login", "senha", "data_ajuste_leitura",
				"indicador_ajuste_consumo", "indicador_transmissao_offline",
				"versao_celular", "indc_atualizar_sequencia_rota",
				"indc_bloquear_reemissao_conta", "qtd_dias_ajuste_consumo",
				"indicador_rota_dividida", "id_rota",
				"modulo_verificador_codigo_barras", "data_inicio", "data_fim",
				"data_referencia_arrecadada" }, null, null, null, null,
				"id asc");

		if (cursor.moveToFirst()) {

			getDadosGerais().setDataReferenciaArrecadacao(cursor.getString(0));
			getDadosGerais().setAnoMesFaturamento(cursor.getString(1));
			getDadosGerais().setCodigoEmpresaFebraban(cursor.getString(2));
			getDadosGerais().setTelefone0800(cursor.getString(3));
			getDadosGerais().setCnpjEmpresa(cursor.getString(4));
			getDadosGerais().setInscricaoEstadualEmpresa(cursor.getString(5));
			getDadosGerais().setValorMinimEmissaoConta(cursor.getString(6));
			getDadosGerais().setPercentToleranciaRateio(cursor.getString(7));
			getDadosGerais().setDecrementoMaximoConsumoEconomia(
					cursor.getString(8));
			getDadosGerais().setIncrementoMaximoConsumoEconomia(
					cursor.getString(9));
			getDadosGerais().setIndcTarifaCatgoria(cursor.getString(10));
			getDadosGerais().setLogin(cursor.getString(11));
			getDadosGerais().setSenha(cursor.getString(12));

			getDadosGerais().setDataAjusteLeitura(cursor.getString(13));
			getDadosGerais().setIndicadorAjusteConsumo(cursor.getString(14));
			getDadosGerais().setIndicadorTransmissaoOffline(
					cursor.getString(15));
			getDadosGerais().setVersaoCelular(cursor.getString(16));
			getDadosGerais().setIndcAtualizarSequencialRota(
					cursor.getString(17));
			getDadosGerais()
					.setIndcBloquearReemissaoConta(cursor.getString(18));
			getDadosGerais().setQtdDiasAjusteConsumo(cursor.getString(19));
			getDadosGerais().setIndicadorRotaDividida(cursor.getString(20));
			getDadosGerais().setIdRota(cursor.getString(21));
			getDadosGerais().setModuloVerificadorCodigoBarras(
					cursor.getString(22));
			getDadosGerais().setDataInicio(cursor.getString(23));
			getDadosGerais().setDataFim(cursor.getString(24));
			// getDadosGerais().setDataReferenciaArrecadacao(cursor.getString(8));

		}

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
	}
	
	public Imovel selectImovel(String condition){
		
		Cursor cursor = db.query(Constantes.TABLE_IMOVEL, new String[] {"id",
																		"matricula",
																		"nome_gerencia_regional",
																		"nome_escritorio",
																		"nome_usuario",
																		"data_vencimento",
																		"data_validade_conta",
																		"inscricao",
																		"endereco",
																		"ano_mes_conta",
																		"digito_verificador_conta",
																		"codigo_responsavel",
																		"nome_responsavel",
																		"endereco_entrega",
																		"situacao_lig_agua",
																		"situacao_lig_esgoto",
																		"descricao_banco",
																		"codigo_agencia",
																		"matricula_condominio",
																		"indc_condominio",
																		"codigo_perfil",
																		"consumo_medio",
																		"indc_faturamento_agua",
																		"indc_faturamento_esgoto",
																		"indc_emissao_conta",
																		"consumo_min_agua",
																		"consumo_min_esgoto",
																		"percent_coleta_esgoto",
																		"percent_cobranca_esgoto",
																		"tipo_poco",
																		"codigo_tarifa",
																		"consumo_estouro",
																		"alto_consumo",
																		"baixo_consumo",
																		"fator_mult_estouro",
																		"fator_mult_media_alto_consumo",
																		"percent_baixo_consumo",
																		"consumo_maximo",
																		"grupo_faturamento",
																		"codigo_rota",
																		"numero_conta",
																		"tipo_calculo_tarifa",
																		"endereco_atendimento",
																		"telefone_localidade_ddd",
																		"sequencial_rota",
																		"mensagem_conta1",
																		"mensagem_conta2",
																		"mensagem_conta3",
																		"consumo_minimo_imovel",
																		"consumo_minimo_imovel_nao_medido",
																		"numero_documento_notificacao_debito",
																		"numero_codigo_barra_notificacao_debito",
																		"cpf_cnpj_cliente",
																		"data_leitura_anterior_nao_medido",
																		"indicador_abastecimento_agua",
																		"indicador_imovel_sazonal",
																		"indicador_paralizar_faturamento_agua",
																		"indicador_paralizar_faturamento_esgoto",
																		"opcao_debito_automatico",
																		"percentual_alternativo_esgoto",
																		"consumo_percentual_alternativo_esgoto",
																		"data_emissao_documento",
																		"quantidade_contas_impressas",
																		"contagem_validacao_agua",
																		"contagem_validacao_poco",
																		"leitura_gravada_anterior",
																		"anormalidade_gravada_anterior",
																		"data_impressao_nao_medido",
																		"valor_residual_credito",
																		"quantidade_imoveis_condominio",
																		"indc_adicionou_dados_iniciais_helper_rateio",
																		"valor_rateio_agua",
																		"valor_rateio_esgoto",
																		"consumo_rateio_agua",
																		"consumo_rateio_esgoto",
																		"mensagem_estouro_consumo_1",
																		"mensagem_estouro_consumo_2",
																		"mensagem_estouro_consumo_3",
																		"imovel_status",
																		"imovel_enviado",
																		"indc_imovel_impresso",
																		"indc_geracao"}, condition, null, null, null,  "inscricao asc");
		Imovel imovel = null;
		
		if (cursor.moveToFirst()) {
			
			imovel = new Imovel();
			
        	imovel.setId(cursor.getLong(0));
           	imovel.setMatricula(Integer.parseInt(cursor.getString(1)));
           	imovel.setNomeGerenciaRegional(cursor.getString(2));
           	imovel.setNomeEscritorio(cursor.getString(3));
           	imovel.setNomeUsuario(cursor.getString(4));
           	imovel.setDataVencimento(cursor.getString(5));
           	imovel.setDataValidadeConta(cursor.getString(6));
           	imovel.setInscricao(cursor.getString(7));
           	imovel.setEndereco(cursor.getString(8));
           	imovel.setAnoMesConta(cursor.getString(9));
        	imovel.setDigitoVerificadorConta(cursor.getString(10));
        	imovel.setCodigoResponsavel(cursor.getString(11));
        	imovel.setNomeResponsavel(cursor.getString(12));
          	imovel.setEnderecoEntrega(cursor.getString(13));
          	imovel.setSituacaoLigAgua(cursor.getString(14));
          	imovel.setSituacaoLigEsgoto(cursor.getString(15));
          	imovel.setDescricaoBanco(cursor.getString(16));
          	imovel.setCodigoAgencia(cursor.getString(17));
          	imovel.setMatriculaCondominio(cursor.getString(18));
          	imovel.setIndcCondominio(cursor.getString(19));
          	imovel.setCodigoPerfil(cursor.getString(20));
          	imovel.setConsumoMedio(cursor.getString(21));
          	imovel.setIndcFaturamentoAgua(cursor.getString(22));
          	imovel.setIndcFaturamentoEsgoto(cursor.getString(23));
          	imovel.setIndcEmissaoConta(cursor.getString(24));
          	imovel.setConsumoMinAgua(cursor.getString(25));
          	imovel.setConsumoMinEsgoto(cursor.getString(26));
          	imovel.setPercentColetaEsgoto(cursor.getString(27));
          	imovel.setPercentCobrancaEsgoto(cursor.getString(28));
          	imovel.setTipoPoco(cursor.getString(29));
          	imovel.setCodigoTarifa(cursor.getString(30));
          	imovel.setConsumoEstouro(cursor.getString(31));
          	imovel.setAltoConsumo(cursor.getString(32));
          	imovel.setBaixoConsumo(cursor.getString(33));
          	imovel.setFatorMultEstouro(cursor.getString(34));
          	imovel.setFatorMultMediaAltoConsumo(cursor.getString(35));
          	imovel.setPercentBaixoConsumo(cursor.getString(36));
          	imovel.setConsumoMaximo(cursor.getString(37));
          	imovel.setGrupoFaturamento(cursor.getString(38));
          	imovel.setCodigoRota(cursor.getString(39));
          	imovel.setNumeroConta(cursor.getString(40));
          	imovel.setTipoCalculoTarifa(cursor.getString(41));
          	imovel.setEnderecoAtendimento(cursor.getString(42));
          	imovel.setTelefoneLocalidadeDDD(cursor.getString(43));
          	imovel.setSequencialRota(cursor.getString(44));
          	imovel.setMensagemConta1(cursor.getString(45));
          	imovel.setMensagemConta2(cursor.getString(46));
          	imovel.setMensagemConta3(cursor.getString(47));
          	imovel.setConsumoMinimoImovel(cursor.getString(48));
          	imovel.setConsumoMinimoImovelNaoMedido(cursor.getString(49));
          	imovel.setNumeroDocumentoNotificacaoDebito(cursor.getString(50));
          	imovel.setNumeroCodigoBarraNotificacaoDebito(cursor.getString(51));
          	imovel.setCpfCnpjCliente(cursor.getString(52));
          	imovel.setDataLeituraAnteriorNaoMedido(cursor.getString(53));
          	imovel.setIndicadorAbastecimentoAgua(cursor.getString(54));
          	imovel.setIndicadorImovelSazonal(cursor.getString(55));
          	imovel.setIndicadorParalizarFaturamentoAgua(cursor.getString(56));
          	imovel.setIndicadorParalizarFaturamentoEsgoto(cursor.getString(57));
          	imovel.setOpcaoDebitoAutomatico(cursor.getString(58));
          	imovel.setPercentualAlternativoEsgoto(cursor.getString(59));
          	imovel.setConsumoPercentualAlternativoEsgoto(cursor.getString(60));
          	imovel.setDataEmissaoDocumento(cursor.getString(61));
          	imovel.setQuantidadeContasImpressas(Integer.parseInt(cursor.getString(62)));
          	imovel.setContagemValidacaoAgua(Integer.parseInt(cursor.getString(63)));
          	imovel.setContagemValidacaoPoco(Integer.parseInt(cursor.getString(64)));
          	imovel.setLeituraGravadaAnterior(Integer.parseInt(cursor.getString(65)));
          	imovel.setAnormalidadeGravadaAnterior(Integer.parseInt(cursor.getString(66)));
          	imovel.setDataImpressaoNaoMedido(cursor.getString(67));
          	imovel.setValorResidualCredito(Double.parseDouble(cursor.getString(68)));
          	imovel.setQuantidadeImoveisCondominio(Integer.parseInt(cursor.getString(69)));
          	imovel.setIndcAdicionouDadosIniciaisHelperRateio(Integer.parseInt(cursor.getString(70)));
          	imovel.setValorRateioAgua(Double.parseDouble(cursor.getString(71)));
          	imovel.setValorRateioEsgoto(Double.parseDouble(cursor.getString(72)));
          	imovel.setConsumoRateioAgua(Integer.parseInt(cursor.getString(73)));
          	imovel.setConsumoRateioEsgoto(Integer.parseInt(cursor.getString(74)));
          	imovel.setMensagemEstouroConsumo1(cursor.getString(75));
          	imovel.setMensagemEstouroConsumo2(cursor.getString(76));
          	imovel.setMensagemEstouroConsumo3(cursor.getString(77));
          	imovel.setImovelStatus(cursor.getString(78));
          	imovel.setImovelEnviado(cursor.getString(79));
          	imovel.setIndcImovelImpresso(Integer.parseInt(cursor.getString(80)));
          	imovel.setIndcGeracao(Integer.parseInt(cursor.getString(81)));
		}
		
		 if (cursor != null && !cursor.isClosed()) {
	           cursor.close();
	     }
		 
		 selectDependenciasImovel(imovel);
		 
		 return imovel;
	}
	
	public Imovel selectDependenciasImovel(Imovel imovel){
		
		if (imovel != null){
			
			selectDadosCategoria(imovel);
			selectHistoricosConsumo(imovel);
			selectDebitos(imovel);
			selectCreditos(imovel);
			selectImpostos(imovel);
			selectContas(imovel);
			selectMedidores(imovel);
			selectTarifacoesMinimas(imovel);
			selectTarifacoesMinimas(imovel);
		}
		
		return imovel;
	}

	public Imovel selectDadosCategoria(Imovel imovel) {
		Cursor cursor = db.query(Constantes.TABLE_DADOS_CATEGORIA, new String[] {"quantidade_econominas_subcategoria", 
																				"descricao_categoria"}, 
																				"matricula = " + imovel.getMatricula(), null, null, null, null);

		ControladorImovel.getInstancia().getImovelSelecionado().getDadosCategoria().clear();
			
		if (cursor.moveToFirst()) {
			do {
				
				DadosCategoria dc = new DadosCategoria();
				dc.setQtdEconomiasSubcategoria(cursor.getString(0));
				dc.setDescricaoCategoria(cursor.getString(1));
				
				imovel.getDadosCategoria().add(dc);
				
			} while (cursor.moveToNext());
		}
		
		if (cursor != null && !cursor.isClosed()) {
	           cursor.close();
	    }
		return imovel;
	}
	
	public Imovel selectHistoricosConsumo(Imovel imovel){
		
		Cursor cursor = db.query(Constantes.TABLE_HISTORICO_CONSUMO, new String[] {"tipo_ligacao","ano_mes_referencia",
																				"consumo","anormalidade_leitura", "anormalidade_consumo"}, 
																				"matricula = " + imovel.getMatricula(), null, null, null, null);
		
		HistoricoConsumo hc;
		
		ControladorImovel.getInstancia().getImovelSelecionado().getHistoricosConsumo().clear();
		
		if (cursor.moveToFirst()) {
			do {
				hc = new HistoricoConsumo();
				
				hc.setTipoLigacao(cursor.getString(0));
				hc.setAnoMesReferencia(cursor.getString(1));
				hc.setConsumo(cursor.getString(2));
				hc.setAnormalidadeLeitura(cursor.getString(3));
				hc.setAnormalidadeConsumo(cursor.getString(4));
				
				imovel.getHistoricosConsumo().add(hc);
			} while (cursor.moveToNext());
		}
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return imovel;
	}
	
	public Imovel selectDebitos(Imovel imovel) {
		
		Cursor cursor = db.query(Constantes.TABLE_DEBITO, new String[] {"descricao","valor","codigo","indc_uso"}, 
																		"matricula = " + imovel.getMatricula(), null, null, null, null);
		Debito debito;
		
		ControladorImovel.getInstancia().getImovelSelecionado().getDebitos().clear();
		
		if (cursor.moveToFirst()) {
			do {
				debito = new Debito();
				
				debito.setDescricao(cursor.getString(0));
				debito.setValor(cursor.getString(1));
				debito.setCodigo(cursor.getString(2));
				debito.setIndcUso(cursor.getShort(3));
				
				imovel.getDebitos().add(debito);
			} while (cursor.moveToNext());
		}
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return imovel;
	}
	
	public Imovel selectCreditos(Imovel imovel){
		Cursor cursor = db.query(Constantes.TABLE_CREDITO, new String[] {"descricao","valor","codigo","indc_uso"}, 
				"matricula = " + imovel.getMatricula(), null, null, null, null);
		
		Credito credito;
		
		ControladorImovel.getInstancia().getImovelSelecionado().getCreditos().clear();
		
		if (cursor.moveToFirst()) {
			do {
				credito = new Credito();
				
				credito.setDescricao(cursor.getString(0));
				credito.setValor(cursor.getString(1));
				credito.setCodigo(cursor.getString(2));
				credito.setIndcUso(cursor.getShort(3));
				
				imovel.getCreditos().add(credito);
			
			} while (cursor.moveToNext());
		}
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return imovel;
	}
	
	public Imovel selectImpostos(Imovel imovel){

		Cursor cursor = db.query(Constantes.TABLE_IMPOSTO, new String[] {"tipo_imposto" ,"descricao_imposto", "percentual_aliquota"},
																		 "matricula = " + imovel.getMatricula(), null, null, null, null);
		
		Imposto imposto;
		
		ControladorImovel.getInstancia().getImovelSelecionado().getImpostos().clear();
		
		if (cursor.moveToFirst()) {
			do {
				imposto = new Imposto();
				
				imposto.setTipoImposto(cursor.getString(0));
				imposto.setDescricaoImposto(cursor.getString(1));
				imposto.setPercentualAlicota(cursor.getString(2));
				
				imovel.getImpostos().add(imposto);
				
			} while (cursor.moveToNext());
		}
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return imovel;
	}
	
	public Imovel selectContas(Imovel imovel){
		
		Cursor cursor = db.query(Constantes.TABLE_CONTA, new String[] {"ano_mes_referencia_conta", "valor_conta", "data_vencimento_conta", 
																		"valor_acresc_impontualidade"},
																		"matricula = " + imovel.getMatricula(), null, null, null, null);

		Conta conta;
		
		ControladorImovel.getInstancia().getImovelSelecionado().getContas().clear();
		
		if (cursor.moveToFirst()) {
			do {
				conta = new Conta();
				
				conta.setAnoMes(cursor.getString(0));
				conta.setValor(cursor.getString(1));
				conta.setDataVencimento(cursor.getString(2));
				conta.setValorAcrescimosImpontualidade(cursor.getString(3));
				
				imovel.getContas().add(conta);
				
			} while (cursor.moveToNext());
		}
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return imovel;
	}

	public Imovel selectMedidores(Imovel imovel) {
		
		Log.i("Matricula medidor", ""+ imovel.getMatricula());

		Cursor cursor = db.query(Constantes.TABLE_MEDIDOR, new String[] {"tipo_medicao", 
																		 "numero_hidrometro",
																		 "data_instalacao_hidrometro", 
																		 "num_digitos_leitura_hidrometro",
																		 "leitura_anterior_faturamento",
																		 "data_leitura_anterior_faturamento",
																		 "codigo_situacao_leitura_anterior", 
																		 "leitura_esperada_inicial",
																		 "leitura_esperada_final", 
																		 "consumo_medio", 
																		 "local_instalacao",
																		 "leitura_anterior_informada",
																		 "data_leitura_anterior_informada", 
																		 "data_ligacao_fornecimento",
																		 "tipo_rateio", 
																		 "leitura_instalacao_hidrometro", 
																		 "matricula" }, "matricula = " + imovel.getMatricula(), null, null, null, "id asc");
		
		Medidor medidor = new Medidor();
		
		ControladorImovel.getInstancia().getImovelSelecionado().getMedidores().clear();

		if (cursor.moveToFirst()) {
			
			medidor.setTipoMedicao(cursor.getString(0));
			medidor.setNumeroHidrometro(cursor.getString(1));
			medidor.setDataInstalacaoHidrometro(cursor.getString(2));
			medidor.setNumDigitosLeituraHidrometro(cursor.getString(3));
			medidor.setLeituraAnteriorFaturamento(cursor.getString(4));
			medidor.setDataLeituraAnteriorFaturado(cursor.getString(5));
			medidor.setCodigoSituacaoLeituraAnterior(cursor.getString(6));
			medidor.setLeituraEsperadaInicial(cursor.getString(7));
			medidor.setLeituraEsperadaFinal(cursor.getString(8));
			medidor.setConsumoMedio(cursor.getString(9));
			medidor.setLocalInstalacao(cursor.getString(10));
			medidor.setLeituraAnteriorInformada(cursor.getString(11));
			medidor.setDataLeituraAnteriorInformada(cursor.getString(12));
			medidor.setDataLigacaoFornecimento(cursor.getString(13));
			medidor.setTipoRateio(cursor.getString(14));
			medidor.setLeituraInstalacaoHidrometro(cursor.getString(15));
			medidor.setMatricula(Integer.parseInt(cursor.getString(16)));

		} else {
			medidor.setNumeroHidrometro("");
			Log.i("Nenhum resgistro!", "Sem registro");
		}

		imovel.getMedidores().add(medidor);
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return imovel;
	}

	public Imovel selectTarifacoesMinimas(Imovel imovel){

		Cursor cursor = db.query(Constantes.TABLE_TARIFACAO_MINIMA, new String[] {"codigo", "data_vigencia", "codigo_categoria", "codigo_subcategoria",
																				  "consumo_minimo_subcategoria", "tarifa_minima_categoria"},
																		"matricula = " + imovel.getMatricula(), null, null, null, null);

		TarifacaoMinima tm;
		
		ControladorImovel.getInstancia().getImovelSelecionado().getTarifacoesMinimas().clear();
		
		if (cursor.moveToFirst()) {
			do {
				tm = new TarifacaoMinima();
				
				tm.setCodigo(cursor.getString(0));
				tm.setDataVigencia(cursor.getString(1));
				tm.setCodigoCategoria(cursor.getString(2));
				tm.setCodigoSubcategoria(cursor.getString(3));
				tm.setConsumoMinimoSubcategoria(cursor.getString(4));
				tm.setTarifaMinimaCategoria(cursor.getString(5));
				
				imovel.getTarifacoesMinimas().add(tm);
			
			} while (cursor.moveToNext());
		}
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return imovel;
	}

	public Imovel selectTarifacoesComplementares(Imovel imovel){

		Cursor cursor = db.query(Constantes.TABLE_TARIFACAO_COMPLEMENTAR, new String[] {"codigo", "data_inicio_vigencia", "codigo_categoria", 
																						"codigo_subcategoria", "limite_inicial_faixa",
																						"limite_final_faixa", "valor_m3_faixa"},
																						"matricula = " + imovel.getMatricula(), null, null, null, null);

		TarifacaoComplementar tc;
		
		ControladorImovel.getInstancia().getImovelSelecionado().getTarifacoesComplementares().clear();
		
		if (cursor.moveToFirst()) {
			do {
				try {
					
					tc = new TarifacaoComplementar();
					
					tc.setCodigo(cursor.getInt(0));
					tc.setDataInicioVigencia(dateFormat.parse(cursor.getString(1)));
					tc.setCodigoCategoria(cursor.getInt(2));
					tc.setCodigoSubcategoria(cursor.getInt(3));
					tc.setLimiteInicialFaixa(cursor.getInt(4));
					tc.setLimiteFinalFaixa(cursor.getInt(5));
					tc.setValorM3Faixa(cursor.getInt(6));
					
					imovel.getTarifacoesComplementares().add(tc);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			} while (cursor.moveToNext());
		}
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return imovel;
	}

	public String selectConfiguracaoElement(String element) {

		String elementValue = "";

		Cursor cursor = db.query(Constantes.TABLE_CONFIGURACAO,
				new String[] { element }, null, null, null, null, "id asc");

		if (cursor.moveToFirst()) {
			elementValue = cursor.getString(0);
		}

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return elementValue;
	}

	public List<String> selectInformacoesRota() {

		ArrayList<String> list = new ArrayList<String>();
		Cursor cursor = db.query(Constantes.TABLE_IMOVEL, new String[] {
				"grupo_faturamento", "localidade", "setor", "quadra" }, null, null, null, null,
				"grupo_faturamento asc");
		
		if (cursor.moveToFirst()) {
			list.add(cursor.getString(0));
			list.add(cursor.getString(1));
			list.add(cursor.getString(2));
			list.add(cursor.getString(3).substring(0, 2));
		}
		
		cursor = db.query(Constantes.TABLE_GERAL, new String[] {"ano_mes_faturamento", "login"}, null, null, null, null, null);
		
		if (cursor.moveToFirst()) {
			list.add(cursor.getString(0));
			list.add(cursor.getString(1));
		}
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return list;
	}

	public Anormalidade selectAnormalidadeByCodigo(String codigo, boolean apenasComIndicadorUso) {
		
		Cursor cursor = null; 
		
		if (apenasComIndicadorUso){
			cursor = db.query(Constantes.TABLE_ANORMALIDADE, null, "codigo = ? AND indc_uso = ?", new String []{codigo, String.valueOf(Constantes.SIM)}, null, null, "codigo asc");
			
		}else{
			cursor = db.query(Constantes.TABLE_ANORMALIDADE, null, "codigo = ?", new String []{codigo}, null, null, "codigo asc");
		}
		
		Anormalidade anormalidade = null;
		
		if (cursor.moveToFirst()) {
			anormalidade = new Anormalidade();

			do {
				anormalidade.setId(Long.parseLong(cursor.getString(0)));
				anormalidade.setCodigo(cursor.getString(1));
				anormalidade.setDescricao(cursor.getString(2));
				anormalidade.setIndicadorLeitura(cursor.getString(3));
				anormalidade.setIdConsumoACobrarComLeitura(cursor.getString(4));
				anormalidade.setIdConsumoACobrarSemLeitura(cursor.getString(5));
				anormalidade.setIdLeituraFaturarComLeitura(cursor.getString(6));
				anormalidade.setIdLeituraFaturarSemLeitura(cursor.getString(7));
				anormalidade.setIndcUso(cursor.getString(8));
				anormalidade.setNumeroFatorSemLeitura(cursor.getString(9));
				anormalidade.setNumeroFatorComLeitura(cursor.getString(10));

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return anormalidade;
	}
	
	public ArrayList<String> selectListaAnormalidades(boolean apenasComIndicadorUso) {

		Cursor cursor = null;
		ArrayList<String> list = new ArrayList<String>();
		
		if (apenasComIndicadorUso){
			cursor = db.query(Constantes.TABLE_ANORMALIDADE, null, "indc_uso = ?", new String []{String.valueOf(Constantes.SIM)}, null, null, "codigo asc");

		}else{
			cursor = db.query(Constantes.TABLE_ANORMALIDADE, null, null, null, null, null, "codigo asc");
		}

		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(2));
			} while (cursor.moveToNext());
		}
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return list;
	}
	
	public Consumo selectConsumoImovelByTipoMedicao(int matricula, int tipoMedicao) {

		Consumo consumo = null;
		Cursor cursor = null;
		
		if (tipoMedicao == Constantes.LIGACAO_AGUA) {
			cursor = db.query(Constantes.TABLE_CONSUMO_AGUA, null , "matricula = ?", 
					new String []{String.valueOf(matricula)}, null, null, "matricula asc");
		} else if (tipoMedicao == Constantes.LIGACAO_POCO) {
			cursor = db.query(Constantes.TABLE_CONSUMO_ESGOTO, null , "matricula = ?", 
					new String []{String.valueOf(matricula)}, null, null, "matricula asc");
		}
		if (cursor.moveToFirst()) {
			do {
				consumo = new Consumo();
				consumo.setConsumoMedidoMes(Integer.getInteger(cursor.getString(3)));
				consumo.setConsumoCobradoMes(Integer.getInteger(cursor.getString(4)));
				consumo.setConsumoCobradoMesImoveisMicro(Integer.getInteger(cursor.getString(5)));
				consumo.setConsumoCobradoMesOriginal(Integer.getInteger(cursor.getString(6)));
				consumo.setLeituraAtual(Integer.getInteger(cursor.getString(7)));
				consumo.setTipoConsumo(Integer.getInteger(cursor.getString(8)));
				consumo.setDiasConsumo(Integer.getInteger(cursor.getString(9)));
				consumo.setAnormalidadeConsumo(Integer.getInteger(cursor.getString(10)));
				consumo.setAnormalidadeLeituraFaturada(Integer.getInteger(cursor.getString(11)));

			} while (cursor.moveToNext());
		}
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return consumo;
	}
	
	public long saveRateioCondominio(EfetuarRateioConsumoHelper rateio) {
		
		Cursor cursor = db.query(Constantes.TABLE_RATEIO_CONDOMINIO, null , 
				"matricula_macro = ? ", new String []{String.valueOf(rateio.getMatriculaMacro())}, null, null, null);
		
		long retorno = -1;
		
		ContentValues values = new ContentValues();
		values.put("matricula_macro", rateio.getMatriculaMacro());
		values.put("matricula_ultimo_micro", rateio.getMatriculaUltimoImovelMicro());
		values.put("quantidade_economia_agua_total", rateio.getQuantidadeEconomiasAguaTotal());
		values.put("consumo_ligacao_agua_total", rateio.getConsumoLigacaoAguaTotal());
		values.put("quantidade_economia_esgoto_total", rateio.getQuantidadeEconomiasEsgotoTotal());
		values.put("consumo_ligacao_esgoto_total", rateio.getConsumoLigacaoEsgotoTotal());
		values.put("consumo_minimo_total", rateio.getConsumoMinimoTotal());
		values.put("consumo_para_rateio_agua", rateio.getConsumoParaRateioAgua());
		values.put("conta_para_rateio_agua", rateio.getContaParaRateioAgua());
		values.put("consumo_para_rateio_esgoto", rateio.getConsumoParaRateioEsgoto());
		values.put("conta_para_rateio_esgoto", rateio.getContaParaRateioEsgoto());
		values.put("reter_impressao_contas", rateio.getReterImpressaoConta());
		values.put("passos", rateio.getPassos());
		
		if (cursor.moveToFirst()) {
			retorno = db.update(Constantes.TABLE_RATEIO_CONDOMINIO, values, "matricula_macro = ?", new String[] {String.valueOf(rateio.getMatriculaMacro())});
		} else {
			retorno = db.insert(Constantes.TABLE_RATEIO_CONDOMINIO, null, values);
		}
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return retorno;
	}

	public EfetuarRateioConsumoHelper selectEfetuarRateioConsumoHelper(int matriculaMacro) {

		EfetuarRateioConsumoHelper helper = null;
		Cursor cursor = db.query(Constantes.TABLE_RATEIO_CONDOMINIO, null , "matricula_macro = ? ", new String []{String.valueOf(matriculaMacro)}, null, null, "matricula_macro asc");
		if (cursor.moveToFirst()) {
			do {
				
				helper = new EfetuarRateioConsumoHelper(matriculaMacro, cursor.getInt(2));
				helper.setQuantidadeEconomiasAguaTotal(cursor.getInt(3));
				helper.setConsumoLigacaoAguaTotal(cursor.getInt(4));
				helper.setQuantidadeEconomiasEsgotoTotal(cursor.getInt(5));
				helper.setConsumoLigacaoEsgotoTotal(cursor.getInt(6));
				helper.setConsumoMinimoTotal(cursor.getInt(7));
				helper.setContaParaRateioAgua(cursor.getInt(8));
				helper.setConsumoParaRateioAgua(cursor.getInt(9));
				helper.setContaParaRateioEsgoto(cursor.getInt(10));
				helper.setConsumoParaRateioEsgoto(cursor.getInt(11));
				helper.setReterImpressaoConta(cursor.getInt(12));
				helper.setPassos(cursor.getInt(13));
				
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return helper;
	}

	public int selectQuantidadeImoveisCondominio(int matriculaMacro) {

		int quantidadeImoveisCondominio = 0;

		Cursor cursor = db.query(Constantes.TABLE_IMOVEL, null , "matricula = ? OR matricula_condominio = ?", 
				new String []{String.valueOf(matriculaMacro), String.valueOf(matriculaMacro)}, null, null, "inscricao asc");
		
		if (cursor.moveToFirst()) {
		
			do {
				quantidadeImoveisCondominio++;
			
			} while (cursor.moveToNext());
		}
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return quantidadeImoveisCondominio;
	}

	public void selectConfiguracao() {

		Cursor cursor = db.query(Constantes.TABLE_CONFIGURACAO, null , null, null, null, null, "id asc");
		
		if (cursor.moveToFirst()) {
		
			do {
				Configuracao.getInstancia().setNomeArquivoImoveis(cursor.getString(1));
				Configuracao.getInstancia().setBluetoothAddress(cursor.getString(2));
				Configuracao.getInstancia().setIdImovelSelecionado(cursor.getInt(3));
				Configuracao.getInstancia().setIndiceImovelCondominio(cursor.getInt(4));
				Configuracao.getInstancia().setSucessoCarregamento(cursor.getInt(5));
				Configuracao.getInstancia().setQtdImoveis(cursor.getInt(6));
				Configuracao.getInstancia().setNomeArquivoRetorno(cursor.getString(7));
				
			} while (cursor.moveToNext());
		}
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
	}

	
	public List<String> selectDescricoesFromTable(String table) {

		ArrayList<String> list = new ArrayList<String>();
		Cursor cursor = db.query(table, new String[] { "codigo", "descricao" }, null, null, null, null, "codigo asc");

		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(1));
			} while (cursor.moveToNext());
		}
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return list;
	}

	public String selectCodigoByDescricaoFromTable(String table,
			String descricao) {

		String codigo = new String();
		Cursor cursor = db.query(table, new String[] { "codigo" },
				"descricao = " + "\"" + descricao + "\"", null, null, null,
				"descricao asc");
		if (cursor.moveToFirst()) {
			codigo = cursor.getString(0);
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return codigo;
	}

	public String selectDescricaoByCodigoFromTable(String table, String codigo) {

		String descricao = new String();
		Cursor cursor = db.query(table, new String[] { "descricao" },
				"codigo = " + "\"" + codigo + "\"", null, null, null,
				"codigo asc");
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				descricao = cursor.getString(0);
			}
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
			return descricao;

		} else {
			return null;
		}
	}

	public long insertDadosGerais(String linhaArquivo) {

		ParserUtil parser = new ParserUtil(linhaArquivo);
		parser.obterDadoParser(2);
		ContentValues initialValues = new ContentValues();

		initialValues.put("codigo_empresa_febraban", parser.obterDadoParser(4));
		initialValues.put("data_referencia_arrecadacao", parser.obterDadoParser(8));
		initialValues.put("ano_mes_faturamento", parser.obterDadoParser(6));
		initialValues.put("telefone0800", parser.obterDadoParser(12));
		initialValues.put("cnpj_empresa", parser.obterDadoParser(14));
		initialValues.put("inscricao_estadual_empresa",	parser.obterDadoParser(20));
		initialValues.put("valor_minim_emissao_conta",	parser.obterDadoParser(14));
		initialValues.put("percent_tolerancia_rateio",	parser.obterDadoParser(4));
		initialValues.put("decremento_maximo_consumo_economia",	parser.obterDadoParser(6));
		initialValues.put("incremento_maximo_consumo_economia",	parser.obterDadoParser(6));
		initialValues.put("indc_tarifa_catgoria", parser.obterDadoParser(1));
		initialValues.put("login", parser.obterDadoParser(11));
		initialValues.put("senha", parser.obterDadoParser(40));
		initialValues.put("data_ajuste_leitura", parser.obterDadoParser(8));
		initialValues.put("indicador_ajuste_consumo", parser.obterDadoParser(1));
		initialValues.put("indicador_transmissao_offline",	parser.obterDadoParser(1));
		initialValues.put("versao_celular", parser.obterDadoParser(10));
		initialValues.put("indc_bloquear_reemissao_conta",	parser.obterDadoParser(1));
		initialValues.put("indc_atualizar_sequencia_rota",	parser.obterDadoParser(1));
		initialValues.put("qtd_dias_ajuste_consumo", parser.obterDadoParser(2));
		initialValues.put("modulo_verificador_codigo_barras", parser.obterDadoParser(2));
		initialValues.put("data_inicio", parser.obterDadoParser(8));
		initialValues.put("data_fim", parser.obterDadoParser(8));
		initialValues.put("id_rota", parser.obterDadoParser(4));
		initialValues.put("indicador_rota_dividida", parser.obterDadoParser(2));

		return db.insert(Constantes.TABLE_GERAL, null, initialValues);
	}

		public long insertImovel(String linhaArquivo) {
		ParserUtil parser = new ParserUtil(linhaArquivo);
		parser.obterDadoParser(2);
		ContentValues initialValues = new ContentValues();
		SituacaoTipo situacaoTipo = SituacaoTipo.getInstancia();
		
		int matricula = Integer.parseInt(parser.obterDadoParser(9));
		
		initialValues.put("matricula", matricula);
		initialValues.put("nome_gerencia_regional", parser.obterDadoParser(25));
		initialValues.put("nome_escritorio", parser.obterDadoParser(25));
		initialValues.put("nome_usuario", parser.obterDadoParser(30));
		initialValues.put("data_vencimento", parser.obterDadoParser(8));
		initialValues.put("data_validade_conta", parser.obterDadoParser(8));
		
		String inscricao = parser.obterDadoParser(17);
		initialValues.put("inscricao", inscricao);
		initialValues.put("localidade", inscricao.substring(0, 3));
		initialValues.put("setor", inscricao.substring(3, 6));
		initialValues.put("quadra", inscricao.substring(6, 10));
		initialValues.put("lote", inscricao.substring(10, 14));
		initialValues.put("sublote", inscricao.substring(14, 17));
		
		initialValues.put("endereco", parser.obterDadoParser(70));
		initialValues.put("ano_mes_conta", parser.obterDadoParser(6));
		initialValues.put("digito_verificador_conta", parser.obterDadoParser(1));

		initialValues.put("codigo_responsavel", parser.obterDadoParser(9));
		initialValues.put("nome_responsavel", parser.obterDadoParser(25));
		initialValues.put("endereco_entrega", parser.obterDadoParser(75));
		initialValues.put("situacao_lig_agua", parser.obterDadoParser(1));
		initialValues.put("situacao_lig_esgoto", parser.obterDadoParser(1));

		initialValues.put("descricao_banco", parser.obterDadoParser(15));
		initialValues.put("codigo_agencia", parser.obterDadoParser(5));
		initialValues.put("matricula_condominio", parser.obterDadoParser(9));
		initialValues.put("indc_condominio", parser.obterDadoParser(1));
		initialValues.put("codigo_perfil", parser.obterDadoParser(2));

		initialValues.put("consumo_medio", parser.obterDadoParser(6));
		initialValues.put("indc_faturamento_agua", parser.obterDadoParser(1));
		initialValues.put("indc_faturamento_esgoto", parser.obterDadoParser(1));
		initialValues.put("indc_emissao_conta", parser.obterDadoParser(1));
		initialValues.put("consumo_min_agua", parser.obterDadoParser(6));

		initialValues.put("consumo_min_esgoto", parser.obterDadoParser(6));
		initialValues.put("percent_coleta_esgoto", parser.obterDadoParser(6));
		initialValues.put("percent_cobranca_esgoto", parser.obterDadoParser(6));
		initialValues.put("tipo_poco", parser.obterDadoParser(1));
		initialValues.put("codigo_tarifa", parser.obterDadoParser(2));

		initialValues.put("consumo_estouro", parser.obterDadoParser(6));
		initialValues.put("alto_consumo", parser.obterDadoParser(6));
		initialValues.put("baixo_consumo", parser.obterDadoParser(6));
		initialValues.put("fator_mult_estouro", parser.obterDadoParser(4));
		initialValues.put("fator_mult_media_alto_consumo", parser.obterDadoParser(4));

		initialValues.put("percent_baixo_consumo", parser.obterDadoParser(6));
		initialValues.put("consumo_maximo", parser.obterDadoParser(6));
		initialValues.put("grupo_faturamento", parser.obterDadoParser(3));
		initialValues.put("codigo_rota", parser.obterDadoParser(7));
		initialValues.put("numero_conta", parser.obterDadoParser(9));
		
		initialValues.put("tipo_calculo_tarifa", parser.obterDadoParser(2));
		initialValues.put("endereco_atendimento", parser.obterDadoParser(70));
		initialValues.put("telefone_localidade_ddd", parser.obterDadoParser(11));
		initialValues.put("sequencial_rota", parser.obterDadoParser(9));
		initialValues.put("mensagem_conta1", parser.obterDadoParser(100));

		initialValues.put("mensagem_conta2", parser.obterDadoParser(100));
		initialValues.put("mensagem_conta3", parser.obterDadoParser(100));
		initialValues.put("turbidez_padrao", parser.obterDadoParser(20));
		initialValues.put("ph_padrao", parser.obterDadoParser(20));
		initialValues.put("cor_padrao", parser.obterDadoParser(20));
		
		initialValues.put("cloro_padrao", parser.obterDadoParser(20));
		initialValues.put("fluor_padrao", parser.obterDadoParser(20));
		initialValues.put("ferro_padrao", parser.obterDadoParser(20));
		initialValues.put("coliformes_totais_padrao", parser.obterDadoParser(20));
		initialValues.put("coliformes_fecais_padrao", parser.obterDadoParser(20));
		
		initialValues.put("nitrato_padrao", parser.obterDadoParser(20));
		initialValues.put("coliformes_termo_tolerantes_padrao",	parser.obterDadoParser(20));
		initialValues.put("am_referencia_qualidade_agua", parser.obterDadoParser(6));
		initialValues.put("numero_cloro_residual", parser.obterDadoParser(5));
		
		initialValues.put("numero_turbidez", parser.obterDadoParser(5));
		initialValues.put("numero_ph", parser.obterDadoParser(5));
		initialValues.put("numero_cor", parser.obterDadoParser(5));
		initialValues.put("numero_fluor", parser.obterDadoParser(5));
		initialValues.put("numero_ferro", parser.obterDadoParser(5));
		
		initialValues.put("numero_coliformes_totais", parser.obterDadoParser(5));
		initialValues.put("numero_coliformes_fecais", parser.obterDadoParser(5));
		initialValues.put("numero_nitrato", parser.obterDadoParser(5));
		initialValues.put("numero_coliformes_termo_tolerantes",	parser.obterDadoParser(5));
		initialValues.put("descricao_fonte_capacitacao", parser.obterDadoParser(30));
		
		initialValues.put("quantidade_turbidez_exigidas",parser.obterDadoParser(6));
		initialValues.put("quantidade_cor_exigidas", parser.obterDadoParser(6));
		initialValues.put("quantidade_cloro_exigidas", parser.obterDadoParser(6));
		initialValues.put("quantidade_fluor_exigidas", parser.obterDadoParser(6));
		initialValues.put("quantidade_coliformes_totais_exigidas", parser.obterDadoParser(6));
		
		initialValues.put("quantidade_coliformes_fecais_exigidas", parser.obterDadoParser(6));
		initialValues.put("quantidade_coliformes_termo_tolerantes_exigidas", parser.obterDadoParser(6));
		initialValues.put("quantidade_turbidez_analisadas",	parser.obterDadoParser(6));

		initialValues.put("quantidade_cor_analisadas", parser.obterDadoParser(6));
		initialValues.put("quantidade_cloro_analisadas", parser.obterDadoParser(6));
		initialValues.put("quantidade_fluor_analisadas", parser.obterDadoParser(6));
		initialValues.put("quantidade_coliformes_totais_analisadas", parser.obterDadoParser(6));
		initialValues.put("quantidade_coliformes_fecais_analisadas", parser.obterDadoParser(6));

		initialValues.put("quantidade_coliformes_termo_tolerantes_analisadas",	parser.obterDadoParser(6));
		initialValues.put("quantidade_turbidez_conforme", parser.obterDadoParser(6));
		initialValues.put("quantidade_cor_conforme", parser.obterDadoParser(6));
		initialValues.put("quantidade_cloro_conforme", parser.obterDadoParser(6));
		initialValues.put("quantidade_fluor_conforme", parser.obterDadoParser(6));

		initialValues.put("quantidade_coliformes_totais_conforme",	parser.obterDadoParser(6));
		initialValues.put("quantidade_coliformes_fecais_conforme",	parser.obterDadoParser(6));
		initialValues.put("quantidade_coliformes_termo_tolerantes_conforme", parser.obterDadoParser(6));
		initialValues.put("consumo_minimo_imovel", parser.obterDadoParser(6));
		initialValues.put("consumo_minimo_imovel_nao_medido", parser.obterDadoParser(6));
		
		initialValues.put("numero_documento_notificacao_debito", parser.obterDadoParser(9));
		initialValues.put("numero_codigo_barra_notificacao_debito",	parser.obterDadoParser(48));
		initialValues.put("cpf_cnpj_cliente", parser.obterDadoParser(18));
		
		situacaoTipo.setMatricula(matricula);
		situacaoTipo.setTipoSituacaoEspecialFeturamento(parser.obterDadoParser(2));
		situacaoTipo.setIdAnormalidadeConsumoSemLeitura(parser.obterDadoParser(2));
		situacaoTipo.setIdAnormalidadeConsumoComLeitura(parser.obterDadoParser(2));
		situacaoTipo.setIdAnormalidadeLeituraSemLeitura(parser.obterDadoParser(2));
		situacaoTipo.setIdAnormalidadeLeituraComLeitura(parser.obterDadoParser(2));
		situacaoTipo.setConsumoAguaMedidoHistoricoFaturamento(parser.obterDadoParser(6));
		situacaoTipo.setConsumoAguaNaoMedidoHistoricoFaturamento(parser.obterDadoParser(6));
		situacaoTipo.setVolumeEsgotoMedidoHistoricoFaturamento(parser.obterDadoParser(6));
		situacaoTipo.setVolumeEsgotoNaoMedidoHistoricoFaturamento(parser.obterDadoParser(6));
		situacaoTipo.setIndcValidaAgua(parser.obterDadoParser(1));
		situacaoTipo.setIndcValidaEsgoto(parser.obterDadoParser(1));
		
		initialValues.put("data_leitura_anterior_nao_medido", parser.obterDadoParser(8));
		initialValues.put("indicador_abastecimento_agua", parser.obterDadoParser(1));
		initialValues.put("indicador_imovel_sazonal", parser.obterDadoParser(1));
		initialValues.put("indicador_paralizar_faturamento_agua", parser.obterDadoParser(1));
		initialValues.put("indicador_paralizar_faturamento_esgoto", parser.obterDadoParser(1));
		
		initialValues.put("opcao_debito_automatico", parser.obterDadoParser(9));
		initialValues.put("percentual_alternativo_esgoto", parser.obterDadoParser(6));
		initialValues.put("consumo_percentual_alternativo_esgoto", parser.obterDadoParser(6));
		initialValues.put("data_emissao_documento", parser.obterDadoParser(8));
		
		initialValues.put("quantidade_contas_impressas", "0");
		initialValues.put("contagem_validacao_agua", "0");
		initialValues.put("contagem_validacao_poco", "0");
		initialValues.put("leitura_gravada_anterior", "0");
		initialValues.put("anormalidade_gravada_anterior", "0");
		initialValues.put("valor_residual_credito", "0");
		initialValues.put("quantidade_imoveis_condominio", "0");
		initialValues.put("indc_adicionou_dados_iniciais_helper_rateio", String.valueOf(Constantes.NAO));
		initialValues.put("valor_rateio_agua", "0");
		initialValues.put("valor_rateio_esgoto", "0");
		initialValues.put("consumo_rateio_agua", "0");
		initialValues.put("consumo_rateio_esgoto", "0");

		initialValues.put("mensagem_estouro_consumo_1", Constantes.NULO_STRING);
		initialValues.put("mensagem_estouro_consumo_2", Constantes.NULO_STRING);
		initialValues.put("mensagem_estouro_consumo_3", Constantes.NULO_STRING);
		initialValues.put("imovel_status", String.valueOf(Constantes.IMOVEL_PENDENTE));
		initialValues.put("imovel_enviado", String.valueOf(Constantes.NAO));
		initialValues.put("indc_imovel_impresso", String.valueOf(Constantes.NAO));
		initialValues.put("indc_geracao", String.valueOf(Constantes.SIM));

		return db.insert(Constantes.TABLE_IMOVEL, null, initialValues);
	}
	
	public long insertSituacaoTipo(SituacaoTipo situacaoTipo) {
		
		ContentValues initialValues = new ContentValues();
		
		initialValues.put("matricula", situacaoTipo.getMatricula());
		initialValues.put("tipo_situacao_especial_feturamento", situacaoTipo.getTipoSituacaoEspecialFeturamento());
		initialValues.put("id_anormalidade_consumo_sem_leitura", situacaoTipo.getIdAnormalidadeConsumoSemLeitura());
		initialValues.put("id_anormalidade_consumo_com_leitura", situacaoTipo.getIdAnormalidadeConsumoComLeitura());
		initialValues.put("id_anormalidade_leitura_sem_leitura", situacaoTipo.getIdAnormalidadeLeituraSemLeitura());
		initialValues.put("id_anormalidade_leitura_com_leitura", situacaoTipo.getIdAnormalidadeLeituraComLeitura());
		initialValues.put("consumo_agua_medido_historico_faturamento", situacaoTipo.getConsumoAguaMedidoHistoricoFaturamento());
		initialValues.put("consumo_agua_nao_medido_historico_faturamento", situacaoTipo.getConsumoAguaNaoMedidoHistoricoFaturamento());
		initialValues.put("volume_esgoto_medido_historico_faturamento", situacaoTipo.getVolumeEsgotoMedidoHistoricoFaturamento());
		initialValues.put("volume_esgoto_nao_medido_historico_faturamento", situacaoTipo.getVolumeEsgotoNaoMedidoHistoricoFaturamento());
		initialValues.put("indc_valida_agua", situacaoTipo.getIndcValidaAgua());
		initialValues.put("indc_valida_esgoto", situacaoTipo.getIndcValidaEsgoto());
		
		return db.insert(Constantes.TABLE_SITUACAO_TIPO, null, initialValues);

	}

	public long insertAnormalidade(String linhaArquivo) {

		ParserUtil parser = new ParserUtil(linhaArquivo);
		parser.obterDadoParser(2);
		ContentValues initialValues = new ContentValues();

		initialValues.put("codigo", parser.obterDadoParser(3));
		initialValues.put("descricao", parser.obterDadoParser(25));
		initialValues.put("indicador_leitura", parser.obterDadoParser(1));
		initialValues.put("id_consumo_a_cobrar_com_leitura", parser.obterDadoParser(2));
		initialValues.put("id_consumo_a_cobrar_sem_leitura", parser.obterDadoParser(2));
		initialValues.put("id_leitura_faturar_com_leitura",	parser.obterDadoParser(2));
		initialValues.put("id_leitura_faturar_sem_leitura",	parser.obterDadoParser(2));
		initialValues.put("indc_uso", parser.obterDadoParser(1));
		initialValues.put("numero_fator_sem_leitura", parser.obterDadoParser(4));
		initialValues.put("numero_fator_com_leitura", parser.obterDadoParser(4));

		return db.insert(Constantes.TABLE_ANORMALIDADE, null, initialValues);
	}

	public long insertDadosCategoria(String linhaArquivo) {

		ParserUtil parser = new ParserUtil(linhaArquivo);
		parser.obterDadoParser(2);
		ContentValues initialValues = new ContentValues();

		initialValues.put("matricula", parser.obterDadoParser(9));
		initialValues.put("codigo_categoria", parser.obterDadoParser(1));
		initialValues.put("descricao_categoria", parser.obterDadoParser(15));
		initialValues.put("codigo_subcategoria", parser.obterDadoParser(3));
		initialValues.put("descricao_subcategoria", parser.obterDadoParser(50));
		initialValues.put("quantidade_econominas_subcategoria", parser.obterDadoParser(4));
		initialValues.put("descricao_abreviada_categoria", parser.obterDadoParser(3));
		initialValues.put("descricao_abreviada_subcategoria", parser.obterDadoParser(20));
		initialValues.put("fator_economia_categoria", parser.obterDadoParser(2));

		return db.insert(Constantes.TABLE_DADOS_CATEGORIA, null, initialValues);
	}

	public long insertImposto(String linhaArquivo) {

		ParserUtil parser = new ParserUtil(linhaArquivo);
		parser.obterDadoParser(2);
		ContentValues initialValues = new ContentValues();

		initialValues.put("matricula", SituacaoTipo.getInstancia().getMatricula());
		initialValues.put("tipo_imposto", parser.obterDadoParser(1));
		initialValues.put("descricao_imposto", parser.obterDadoParser(15));
		initialValues.put("percentual_aliquota", parser.obterDadoParser(6));

		return db.insert(Constantes.TABLE_IMPOSTO, null, initialValues);
	}

	public long insertConta(String linhaArquivo) {

		ParserUtil parser = new ParserUtil(linhaArquivo);
		parser.obterDadoParser(11);
		ContentValues initialValues = new ContentValues();

		initialValues.put("matricula", SituacaoTipo.getInstancia().getMatricula());
		initialValues.put("ano_mes_referencia_conta", parser.obterDadoParser(6));
		initialValues.put("valor_conta", parser.obterDadoParser(14));
		initialValues.put("data_vencimento_conta", parser.obterDadoParser(8));
		initialValues.put("valor_acresc_impontualidade", parser.obterDadoParser(8));

		return db.insert(Constantes.TABLE_CONTA, null, initialValues);
	}

	public long insertMedidor(String linhaArquivo) {

		ParserUtil parser = new ParserUtil(linhaArquivo);
		parser.obterDadoParser(2);
		ContentValues initialValues = new ContentValues();

		initialValues.put("matricula", parser.obterDadoParser(9));
		initialValues.put("tipo_medicao", parser.obterDadoParser(1));
		initialValues.put("numero_hidrometro", parser.obterDadoParser(11));
		initialValues.put("data_instalacao_hidrometro",	parser.obterDadoParser(8));
		initialValues.put("num_digitos_leitura_hidrometro",	parser.obterDadoParser(1));
		initialValues.put("leitura_anterior_faturamento", parser.obterDadoParser(7));
		initialValues.put("data_leitura_anterior_faturamento", parser.obterDadoParser(8));
		initialValues.put("codigo_situacao_leitura_anterior", parser.obterDadoParser(1));
		initialValues.put("leitura_esperada_inicial", parser.obterDadoParser(7));
		initialValues.put("leitura_esperada_final", parser.obterDadoParser(7));
		initialValues.put("consumo_medio", parser.obterDadoParser(6));
		initialValues.put("local_instalacao", parser.obterDadoParser(20));
		initialValues.put("leitura_anterior_informada",	parser.obterDadoParser(7));
		initialValues.put("data_leitura_anterior_informada", parser.obterDadoParser(8));
		initialValues.put("data_ligacao_fornecimento", parser.obterDadoParser(8));
		initialValues.put("tipo_rateio", parser.obterDadoParser(1));
		initialValues.put("leitura_instalacao_hidrometro", parser.obterDadoParser(7));
		

		return db.insert(Constantes.TABLE_MEDIDOR, null, initialValues);
	}
	
	public long updateMedidor(int matricula, Medidor novo) {

		Cursor cursor = db.query(Constantes.TABLE_MEDIDOR, null,
				 "matricula = " + matricula, null, null, null, null);
		ContentValues values = null;
		int retorno = -1;
		
		if (cursor.moveToFirst()) {
			values = new ContentValues();
			
			values.put("matricula", novo.getMatricula());
			values.put("tipo_medicao", novo.getTipoMedicao());
			values.put("numero_hidrometro", novo.getNumeroHidrometro());
			values.put("data_instalacao_hidrometro", ""+novo.getDataInstalacaoHidrometro());
			values.put("num_digitos_leitura_hidrometro", novo.getNumDigitosLeituraHidrometro());
			values.put("leitura_anterior_faturamento", novo.getLeituraAnteriorFaturamento());
			values.put("data_leitura_anterior_faturamento", ""+novo.getDataLeituraAnteriorFaturada());
			values.put("codigo_situacao_leitura_anterior", novo.getCodigoSituacaoLeituraAnterior());
			values.put("leitura_esperada_inicial", novo.getLeituraEsperadaInicial());
			values.put("leitura_esperada_final", novo.getLeituraEsperadaFinal());
			values.put("consumo_medio", novo.getConsumoMedio());
			values.put("local_instalacao", novo.getLocalInstalacao());
			values.put("leitura_anterior_informada", ""+novo.getLeituraAnteriorInformada());
			values.put("data_leitura_anterior_informada", ""+novo.getDataLeituraAnteriorInformada());
			values.put("data_ligacao_fornecimento", ""+novo.getDataLigacaoFornecimento());
			values.put("tipo_rateio", novo.getTipoRateio());
			values.put("leitura_instalacao_hidrometro", novo.getLeituraInstalacaoHidrometro());
			
			retorno = db.update(Constantes.TABLE_MEDIDOR, values, "matricula = ?", new String[] {String.valueOf(matricula)});
		}
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return retorno;
	}

	public long insertTarifacaoComplementar(String linhaArquivo) {
		ParserUtil parser = new ParserUtil(linhaArquivo);
		parser.obterDadoParser(2);
		ContentValues initialValues = new ContentValues();

		initialValues.put("matricula", SituacaoTipo.getInstancia().getMatricula());
		initialValues.put("codigo", parser.obterDadoParser(2));
		initialValues.put("data_inicio_vigencia", parser.obterDadoParser(8));
		initialValues.put("codigo_categoria", parser.obterDadoParser(1));
		initialValues.put("codigo_subcategoria", parser.obterDadoParser(3));
		initialValues.put("limite_inicial_faixa", parser.obterDadoParser(6));
		initialValues.put("limite_final_faixa", parser.obterDadoParser(6));
		initialValues.put("valor_m3_faixa", parser.obterDadoParser(14));

		return db.insert(Constantes.TABLE_TARIFACAO_COMPLEMENTAR, null,
				initialValues);
	}

	public long insertTarifacaoMinima(String linhaArquivo) {
		ParserUtil parser = new ParserUtil(linhaArquivo);
		parser.obterDadoParser(2);
		ContentValues initialValues = new ContentValues();

		initialValues.put("matricula", SituacaoTipo.getInstancia().getMatricula());
		initialValues.put("codigo", parser.obterDadoParser(2));
		initialValues.put("data_vigencia", parser.obterDadoParser(8));
		initialValues.put("codigo_categoria", parser.obterDadoParser(1));
		initialValues.put("codigo_subcategoria", parser.obterDadoParser(3));
		initialValues.put("consumo_minimo_subcategoria",
				parser.obterDadoParser(6));
		initialValues
				.put("tarifa_minima_categoria", parser.obterDadoParser(14));

		return db
				.insert(Constantes.TABLE_TARIFACAO_MINIMA, null, initialValues);
	}
	
	public long insertHistoricoConsumo(String linhaArquivo) {
		ParserUtil parser = new ParserUtil(linhaArquivo);
		parser.obterDadoParser(2);
		ContentValues initialValues = new ContentValues();
		
		initialValues.put("matricula", parser.obterDadoParser(9));
		initialValues.put("tipo_ligacao", parser.obterDadoParser(1));
		initialValues.put("ano_mes_referencia", parser.obterDadoParser(6));
		initialValues.put("consumo", parser.obterDadoParser(6));
		initialValues.put("anormalidade_leitura", parser.obterDadoParser(2));
		initialValues.put("anormalidade_consumo", parser.obterDadoParser(2));
		
		return db.insert(Constantes.TABLE_HISTORICO_CONSUMO, null, initialValues);
				
	}
	
	public long insertCredito(String linhaArquivo) {
		ParserUtil parser = new ParserUtil(linhaArquivo);
		parser.obterDadoParser(2);
		ContentValues initialValues = new ContentValues();
		
		initialValues.put("matricula", parser.obterDadoParser(9));
//		parser.obterDadoParser(2);
		initialValues.put("descricao", parser.obterDadoParser(90));
		initialValues.put("valor", parser.obterDadoParser(14));
		initialValues.put("codigo", parser.obterDadoParser(6));
		initialValues.put("indc_uso", (short) Constantes.SIM );
		
		return db.insert(Constantes.TABLE_CREDITO, null, initialValues);
				
	}
	
	public long insertDebito(String linhaArquivo) {
		
		ParserUtil parser = new ParserUtil(linhaArquivo);
		parser.obterDadoParser(2);
		ContentValues initialValues = new ContentValues();
		
		initialValues.put("matricula", parser.obterDadoParser(9));
		initialValues.put("descricao", parser.obterDadoParser(90));
		initialValues.put("valor", parser.obterDadoParser(14));
		initialValues.put("codigo", parser.obterDadoParser(6));
		initialValues.put("indc_uso", (short) Constantes.SIM );
		
		return db.insert(Constantes.TABLE_DEBITO, null, initialValues);
				
	}
	
	public long insertConsumoAnormalidadeImovel(String linhaArquivo) {
		ParserUtil parser = new ParserUtil(linhaArquivo);
		parser.obterDadoParser(2);
		ContentValues initialValues = new ContentValues();

		initialValues.put("id_consumo_anormalidade", parser.obterDadoParser(2));
		initialValues.put("id_categoria", parser.obterDadoParser(2));
		initialValues.put("id_perfil", parser.obterDadoParser(2));
		initialValues.put("id_leitura_anormalidade_consumo_primeiro_mes", parser.obterDadoParser(2));
		initialValues.put("id_leitura_anormalidade_consumo_segundo_mes", parser.obterDadoParser(2));
		initialValues.put("id_leitura_anormalidade_consumo_terceiro_mes", parser.obterDadoParser(2));
		initialValues.put("fator_consumo_primeiro_mes", parser.obterDadoParser(4));
		initialValues.put("fator_consumo_segundo_mes", parser.obterDadoParser(4));
		initialValues.put("fator_consumo_terceiro_mes", parser.obterDadoParser(4));
		initialValues.put("indc_geracao_conta_primeiro_mes", parser.obterDadoParser(1));
		initialValues.put("indc_geracao_conta_segundo_mes", parser.obterDadoParser(1));
		initialValues.put("indc_geracao_conta_terceiro_mes", parser.obterDadoParser(1));
		initialValues.put("mensagem_conta_primeiro_mes", parser.obterDadoParser(120));
		initialValues.put("mensagem_conta_segundo_mes", parser.obterDadoParser(120));
		initialValues.put("mensagem_conta_terceiro_mes", parser.obterDadoParser(120));
		
		return db.insert(Constantes.TABLE_CONSUMO_ANORMALIDADE_ACAO, null, initialValues);
	}
	
	public long saveConsumoAgua(Consumo consumo) {
		
		Cursor cursor = db.query(Constantes.TABLE_CONSUMO_AGUA, null,
				 "matricula = " + consumo.getMatricula(), null, null, null, null);
		
		long retorno = -1;
		
		ContentValues values = new ContentValues();
		values.put("consumo_medido_mes", consumo.getConsumoMedidoMes());
		values.put("consumo_cobrado_mes", consumo.getConsumoCobradoMes());
		values.put("consumo_cobrado_mes_imovel_micro", consumo.getConsumoCobradoMesImoveisMicro());
		values.put("consumo_cobrado_mes_original", consumo.getConsumoCobradoMesOriginal());
		values.put("leitura_atual", consumo.getLeituraAtual());
		values.put("tipo_consumo", consumo.getTipoConsumo());
		values.put("dias_consumo", consumo.getDiasConsumo());
		values.put("anormalidade_consumo", consumo.getAnormalidadeConsumo());
		values.put("anormalidade_leitura_faturada", consumo.getAnormalidadeLeituraFaturada());
		
		if (cursor.moveToFirst()) {
			retorno = db.update(Constantes.TABLE_CONSUMO_AGUA, values, "matricula = ?", new String[] {String.valueOf(consumo.getMatricula())});
		} else {
			retorno = db.insert(Constantes.TABLE_CONSUMO_AGUA, null, values);
		}
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return retorno;
		
	}
	
	public long saveConsumoEsgoto(Consumo consumo) {
		
		Cursor cursor = db.query(Constantes.TABLE_CONSUMO_ESGOTO, null,
				 "matricula = " + consumo.getMatricula(), null, null, null, null);
		
		long retorno = -1;
		
		ContentValues values = new ContentValues();
		values.put("consumo_medido_mes", consumo.getConsumoMedidoMes());
		values.put("consumo_cobrado_mes", consumo.getConsumoCobradoMes());
		values.put("consumo_cobrado_mes_imovel_micro", consumo.getConsumoCobradoMesImoveisMicro());
		values.put("consumo_cobrado_mes_original", consumo.getConsumoCobradoMesOriginal());
		values.put("leitura_atual", consumo.getLeituraAtual());
		values.put("tipo_consumo", consumo.getTipoConsumo());
		values.put("dias_consumo", consumo.getDiasConsumo());
		values.put("anormalidade_consumo", consumo.getAnormalidadeConsumo());
		values.put("anormalidade_leitura_faturada", consumo.getAnormalidadeLeituraFaturada());
		
		if (cursor.moveToFirst()) {
			retorno = db.update(Constantes.TABLE_CONSUMO_ESGOTO, values, "matricula = ?", new String[] {String.valueOf(consumo.getMatricula())});
		} else {
			retorno = db.insert(Constantes.TABLE_CONSUMO_ESGOTO, null, values);
		}
		
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return retorno;
		
	}
	
	public void updateConfiguracao(String parametroName, int value) {
		   
		ContentValues initialValues = new ContentValues();
		initialValues.put(parametroName, value);

		if (DatabaseUtils.queryNumEntries(db,Constantes.TABLE_CONFIGURACAO) > 0){
			db.update(Constantes.TABLE_CONFIGURACAO, initialValues, "id=?", new String []{String.valueOf(1)});
			
		}else{
			db.insert(Constantes.TABLE_CONFIGURACAO, null, initialValues);
		}
	}
	
	public void updateConfiguracao(String parametroName, String value) {
		   
		ContentValues initialValues = new ContentValues();
		initialValues.put(parametroName, value);

		if (DatabaseUtils.queryNumEntries(db,Constantes.TABLE_CONFIGURACAO) > 0){
			db.update(Constantes.TABLE_CONFIGURACAO, initialValues, "id=?", new String []{String.valueOf(1)});
			
		}else{
			db.insert(Constantes.TABLE_CONFIGURACAO, null, initialValues);
		}
	}
   
	public void updateConfiguracao(Configuracao configuracao) {

		ContentValues initialValues = new ContentValues();

		initialValues.put("nome_arquivo_imoveis", configuracao.getNomeArquivoImoveis());
		initialValues.put("bluetooth_address", configuracao.getBluetoothAddress());
		initialValues.put("id_imovel_selecionado", configuracao.getIdImovelSelecionado());
		initialValues.put("indice_imovel_condominio", configuracao.getIndiceImovelCondominio());
		initialValues.put("sucesso_carregamento", configuracao.getSucessoCarregamento());
//		initialValues.put("indc_rota_marcacao_ativada", configuracao.getIndcRotaMarcacaoAtivada());
//		initialValues.put("sequencial_atual_rota_marcacao", configuracao.getSequencialAtualRotaMarcacao());
		initialValues.put("quantidade_imoveis", configuracao.getQtdImoveis());
		initialValues.put("nome_arquivo_retorno", configuracao.getNomeArquivoRetorno());
		
		// verifica se Elemento já existe na tabela para atualizar ou inserir.
		if (DatabaseUtils.queryNumEntries(db, Constantes.TABLE_CONFIGURACAO) > 0) {
			db.update(Constantes.TABLE_CONFIGURACAO, initialValues, "id=?",new String[] { String.valueOf(1) });

		} else {
			db.insert(Constantes.TABLE_CONFIGURACAO, null, initialValues);
		}
	}
	
	public void salvarImovel(Imovel imovel){

		ContentValues initialValues = new ContentValues();

		initialValues.put("quantidade_contas_impressas", String.valueOf(imovel.getQuantidadeContasImpressas()));
		initialValues.put("contagem_validacao_agua", String.valueOf(imovel.getContagemValidacaoAgua()));
		initialValues.put("contagem_validacao_poco", String.valueOf(imovel.getContagemValidacaoPoco()));
		initialValues.put("leitura_gravada_anterior", String.valueOf(imovel.getLeituraGravadaAnterior()));
		initialValues.put("anormalidade_gravada_anterior", String.valueOf(imovel.getAnormalidadeGravadaAnterior()));
		initialValues.put("valor_residual_credito", String.valueOf(imovel.getValorResidualCredito()));
		initialValues.put("mensagem_estouro_consumo_1", imovel.getMensagemEstouroConsumo1());
		initialValues.put("mensagem_estouro_consumo_2", imovel.getMensagemEstouroConsumo2());
		initialValues.put("mensagem_estouro_consumo_3", imovel.getMensagemEstouroConsumo3());
		initialValues.put("imovel_status", String.valueOf(imovel.getImovelStatus()));
		initialValues.put("imovel_enviado", String.valueOf(imovel.getIndcImovelEnviado()));
		initialValues.put("indc_imovel_impresso", String.valueOf(imovel.getIndcImovelImpresso()));
		initialValues.put("indc_geracao", String.valueOf(imovel.getIndcGeracao()));

		db.update(Constantes.TABLE_IMOVEL, initialValues, "id=?", new String []{String.valueOf(imovel.getId())});
	}
}
