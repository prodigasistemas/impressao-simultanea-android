package dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import business.Controlador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.Conta;
import model.DadosGerais;
import model.Medidor;
import model.SituacaoTipo;

import util.Constantes;
import util.ParserUtil;
import util.Util;

public class DataManipulator {
	private static Context context;
	private DbHelper openHelper;
	static SQLiteDatabase db;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

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

	public int getNumeroCadastros() {
		return (int) DatabaseUtils.queryNumEntries(db, Constantes.TABLE_IMOVEL);
	}

	public DadosGerais getDadosGerais() {
		return Controlador.getInstancia().getDadosGerais();
	}

	public Medidor getMedidorSelecionado() {
		return Controlador.getInstancia().getMedidorSelecionado();
	}

	public Conta getContaSelecionado() {
		return Controlador.getInstancia().getContaSelecionado();
	}

	public void deleteTable(String tableName) {
		db.delete(tableName, null, null);
	}

	public void deleteElementId(int rowId, String tableName) {
		db.delete(tableName, null, null);
	}

	public List<String> selectEnderecoImoveis(String condition) {

		ArrayList<String> list = new ArrayList<String>();
		Cursor cursor;

		cursor = db.query(Constantes.TABLE_IMOVEL, new String[] { "id",
				"matricula", "logradouro_imovel", "numero_imovel",
				"complemento_imovel", "bairro_imovel", "cep_imovel",
				"municipio_imovel" }, condition, null, null, null,
				"inscricao asc");

		int x = 0;
		if (cursor.moveToFirst()) {
			do {
				String b1 = "(" + (x + 1) + ") "
						+ String.valueOf(Integer.parseInt(cursor.getString(1)))
						+ " - " + cursor.getString(2).trim() + ", n°"
						+ cursor.getString(3).trim() + " "
						+ cursor.getString(4).trim() + " "
						+ cursor.getString(5).trim() + " "
						+ cursor.getString(6).trim() + " "
						+ cursor.getString(7).trim();
				list.add(b1);
				x = x + 1;
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		cursor.close();
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

		int x = 0;
		if (cursor.moveToFirst()) {
			do {
				String b1 = cursor.getString(0);
				list.add(b1);
				x = x + 1;
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		cursor.close();
		return list;
	}

	public List<String> selectStatusImoveis(String condition) {

		ArrayList<String> list = new ArrayList<String>();
		Cursor cursor;

		if (condition == Constantes.NULO_STRING || condition == null) {

			cursor = db.query(Constantes.TABLE_IMOVEL,
					new String[] { "imovel_status" }, null, null, null, null,
					"inscricao asc");

		} else {

			cursor = db.query(Constantes.TABLE_IMOVEL,
					new String[] { "imovel_status" }, condition, null, null,
					null, "inscricao asc");
		}

		int x = 0;
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(0));
				x = x + 1;
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		cursor.close();
		return list;
	}
	
	public List<Integer> selectNumeroTodosStatusImoveis() {

		ArrayList<Integer> list = new ArrayList<Integer>();
		int visitados = 0;
		int naoVisitados = 0;
		int visitadosAnormalidade = 0;
		int novos = 0;
		int transmitidos = 0;
		int naoTransmitidos = 0;

		Cursor cursor = db.query(Constantes.TABLE_IMOVEL, new String[] {
				"imovel_status", "imovel_enviado", "matricula" }, null, null,
				null, null, "inscricao asc");
		if (cursor.moveToFirst()) {
			do {

				// Contabiliza imoveis novos
				if (Integer.parseInt(cursor.getString(2)) == 0) {
					novos++;
				}

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

			} while (cursor.moveToNext());

			list.add(visitados);
			list.add(naoVisitados);
			list.add(visitadosAnormalidade);
			list.add(novos);
			list.add(transmitidos);
			list.add(naoTransmitidos);

		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		cursor.close();
		return list;
	}

	public void SelectConta(long idImovel) {

		Cursor cursor = db.query(Constantes.TABLE_CONTA, null, "id_imovel = "
				+ idImovel, null, null, null, "id asc");

		if (cursor.moveToFirst()) {
			getContaSelecionado().setAnoMes(cursor.getString(1));
			getContaSelecionado().setValor(cursor.getString(2));
			getContaSelecionado().setDataVencimento(cursor.getString(3));
			getContaSelecionado().setValorAcrescimosImpontualidade(
					cursor.getString(4));
		}

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		cursor.close();
	}

	public void selectMedidor(long id) {

		Cursor cursor = db.query(Constantes.TABLE_MEDIDOR, new String[] {
				"tipo_medicao", "numero_hidrometro",
				"data_instalacao_hidrometro", "num_digitos_leitura_hidrometro",
				"leitura_anterior_faturamento",
				"data_leitura_anterior_faturamento",
				"codigo_situacao_leitura_anterior", "leitura_esperada_inicial",
				"leitura_esperada_final", "consumo_medio", "local_instalacao",
				"leitura_anterior_informada",
				"data_leitura_anterior_informada", "data_ligacao_fornecimento",
				"tipo_rateio", "leitura_instalacao_hidrometro" }, "id = " + id,
				null, null, null, "id asc");

		if (cursor.moveToFirst()) {
			getMedidorSelecionado().setTipoMedicao(cursor.getString(1));
			getMedidorSelecionado().setNumeroHidrometro(cursor.getString(2));
			getMedidorSelecionado().setDataInstalacaoHidrometro(
					cursor.getString(3));
			getMedidorSelecionado().setNumDigitosLeituraHidrometro(
					cursor.getString(4));
			getMedidorSelecionado().setLeituraAnteriorFaturamento(
					cursor.getString(5));
			getMedidorSelecionado().setDataLeituraAnteriorFaturado(
					cursor.getString(6));
			getMedidorSelecionado().setCodigoSituacaoLeituraAnterior(
					cursor.getString(7));
			getMedidorSelecionado().setLeituraEsperadaInicial(
					cursor.getString(8));
			getMedidorSelecionado()
					.setLeituraEsperadaFinal(cursor.getString(9));
			getMedidorSelecionado().setConsumoMedio(cursor.getString(10));
			getMedidorSelecionado().setLocalInstalacao(cursor.getString(11));
			getMedidorSelecionado().setLeituraAnteriorInformada(
					cursor.getString(12));
			getMedidorSelecionado().setDataLeituraAnteriorInformada(
					cursor.getString(13));
			getMedidorSelecionado().setDataLigacaoFornecimento(
					cursor.getString(14));
			getMedidorSelecionado().setTipoRateio(cursor.getString(15));
			getMedidorSelecionado().setLeituraInstalacaoHidrometro(
					cursor.getString(16));
		}

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		cursor.close();
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
		cursor.close();
	}

	public int selectConfiguracaoElement(String element) {

		int elementValue = 0;

		Cursor cursor = db.query(Constantes.TABLE_CONFIGURACAO,
				new String[] { element }, null, null, null, null, "id asc");

		if (cursor.moveToFirst()) {
			elementValue = Integer.parseInt(cursor.getString(0));
		}

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		cursor.close();

		return elementValue;
	}

	public List<String> selectInformacoesRota() {

		ArrayList<String> list = new ArrayList<String>();
		Cursor cursor = db.query(Constantes.TABLE_GERAL, new String[] {
				"grupo_faturamento", "localidade", "setor", "rota",
				"ano_mes_faturamento", "login" }, null, null, null, null,
				"grupo_faturamento asc");

		if (cursor.moveToFirst()) {
			list.add(cursor.getString(0));
			list.add(cursor.getString(1));
			list.add(cursor.getString(2));
			list.add(cursor.getString(3));
			list.add(cursor.getString(4));
			list.add(cursor.getString(5));
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		cursor.close();
		return list;
	}

	public List<String> selectAnormalidades() {

		ArrayList<String> list = new ArrayList<String>();
		Cursor cursor = db.query(Constantes.TABLE_ANORMALIDADE, new String[] {
				"codigo", "descricao" }, null, null, null, null, "codigo asc");
		int x = 0;
		if (cursor.moveToFirst()) {
			do {
				String b1 = cursor.getString(1);
				list.add(b1);
				x = x + 1;
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		cursor.close();
		return list;
	}

	public List<String> selectDescricoesFromTable(String table) {

		ArrayList<String> list = new ArrayList<String>();
		Cursor cursor = db.query(table, new String[] { "codigo", "descricao" },
				null, null, null, null, "descricao asc");
		int x = 0;
		if (cursor.moveToFirst()) {
			do {
				String b1 = cursor.getString(1);
				list.add(b1);
				x = x + 1;
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		cursor.close();
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
		cursor.close();
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
			cursor.close();
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

	public void updateConfiguracao(String parametroName, int value) {

		ContentValues initialValues = new ContentValues();
		initialValues.put(parametroName, value);

		if (DatabaseUtils.queryNumEntries(db, Constantes.TABLE_CONFIGURACAO) > 0) {
			db.update(Constantes.TABLE_CONFIGURACAO, initialValues, "id=?",
					new String[] { String.valueOf(1) });

		} else {
			db.insert(Constantes.TABLE_CONFIGURACAO, null, initialValues);
		}
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
		initialValues.put("inscricao", parser.obterDadoParser(17));
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

	public void insertAnormalidadeImovel(String linhaArquivo) {

		ParserUtil parser = new ParserUtil(linhaArquivo);
		parser.obterDadoParser(2);

		ContentValues initialValues = new ContentValues();

		initialValues.put("matricula", String.valueOf(Integer.parseInt(parser.obterDadoParser(9))));
		initialValues.put("latitude", String.valueOf(Integer.parseInt(parser.obterDadoParser(15))));
		initialValues.put("longitude", String.valueOf(Integer.parseInt(parser.obterDadoParser(15))));
		initialValues.put("codigo_anormalidade", "0");
		initialValues.put("comentario", "");
		initialValues.put("path_image_1", "");
		initialValues.put("path_image_2", "");
		initialValues.put("data", "");

		// Verifica se deve atualizar ou inserir um novo elemento na tabela
		if (Controlador.getInstancia().getIdCadastroSelecionado() > 0) {
			db.update(Constantes.TABLE_ANORMALIDADE_IMOVEL, initialValues,
					"id=?", new String[] { String.valueOf(Controlador
							.getInstancia().getIdCadastroSelecionado()) });

		} else {
			Controlador.getInstancia().setCadastroSelecionado(
					db.insert(Constantes.TABLE_ANORMALIDADE_IMOVEL, null,
							initialValues));
		}
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
		parser.obterDadoParser(11);
		ContentValues initialValues = new ContentValues();

		initialValues.put("matricula", SituacaoTipo.getInstancia().getMatricula());
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
//		parser.obterDadoParser(2);
		initialValues.put("descricao", parser.obterDadoParser(90));
		initialValues.put("valor", parser.obterDadoParser(14));
		initialValues.put("codigo", parser.obterDadoParser(6));
		initialValues.put("indc_uso", (short) Constantes.SIM );
		
		return db.insert(Constantes.TABLE_DEBITO, null, initialValues);
				
	}

	public void salvarConfiguracaoElement(String parametroName, int value) {

		ContentValues initialValues = new ContentValues();
		initialValues.put("rota_carregada", value);

		db.update(Constantes.TABLE_CONFIGURACAO, initialValues, "id=?",
				new String[] { String.valueOf(1) });
	}

}
