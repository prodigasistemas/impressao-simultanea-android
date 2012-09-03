package dataBase;

import util.Constantes;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_IMOVEL_QUERY = "CREATE TABLE imovel (id INTEGER PRIMARY KEY, matricula INTEGER, nome_gerencia_regional TEXT, nome_escritorio TEXT, nome_usuario TEXT, data_vencimento DATE, data_validade_conta DATE, inscricao TEXT, localidade TEXT, setor TEXT, quadra TEXT, lote TEXT, sublote TEXT," +
			"endereco TEXT, ano_mes_conta TEXT, digito_verificador_conta TEXT, codigo_responsavel TEXT, nome_responsavel TEXT, endereco_entrega TEXT, situacao_lig_agua TEXT, situacao_lig_esgoto TEXT, descricao_banco TEXT, codigo_agencia TEXT, matricula_condominio TEXT, indc_condominio TEXT, " +
			"codigo_perfil TEXT, consumo_medio TEXT, indc_faturamento_agua TEXT, indc_faturamento_esgoto TEXT, indc_emissao_conta TEXT, consumo_min_agua TEXT, consumo_min_esgoto TEXT, percent_coleta_esgoto TEXT, percent_cobranca_esgoto TEXT, tipo_poco TEXT, codigo_tarifa TEXT, consumo_estouro TEXT, " +
			"alto_consumo TEXT, baixo_consumo TEXT, fator_mult_estouro TEXT, fator_mult_media_alto_consumo TEXT, percent_baixo_consumo TEXT, consumo_maximo TEXT, grupo_faturamento TEXT, codigo_rota TEXT, numero_conta TEXT, tipo_calculo_tarifa TEXT, endereco_atendimento TEXT, telefone_localidade_ddd TEXT," +
			" sequencial_rota TEXT, mensagem_conta1 TEXT, mensagem_conta2 TEXT, mensagem_conta3 TEXT, turbidez_padrao TEXT, ph_padrao TEXT, cor_padrao TEXT, cloro_padrao TEXT, fluor_padrao TEXT, ferro_padrao TEXT, coliformes_totais_padrao TEXT, coliformes_fecais_padrao TEXT, nitrato_padrao TEXT, " +
			"coliformes_termo_tolerantes_padrao TEXT, am_referencia_qualidade_agua TEXT, numero_cloro_residual TEXT, numero_turbidez TEXT, numero_ph TEXT, numero_cor TEXT, numero_fluor TEXT, numero_ferro TEXT, numero_coliformes_totais TEXT, numero_coliformes_fecais TEXT, numero_nitrato TEXT, " +
			"numero_coliformes_termo_tolerantes TEXT, descricao_fonte_capacitacao TEXT, quantidade_turbidez_exigidas TEXT, quantidade_cor_exigidas TEXT, quantidade_cloro_exigidas TEXT, quantidade_fluor_exigidas TEXT, quantidade_coliformes_totais_exigidas TEXT, quantidade_coliformes_fecais_exigidas TEXT, " +
			"quantidade_coliformes_termo_tolerantes_exigidas TEXT, quantidade_turbidez_analisadas TEXT, quantidade_cor_analisadas TEXT, quantidade_cloro_analisadas TEXT, quantidade_fluor_analisadas TEXT, quantidade_coliformes_totais_analisadas TEXT, quantidade_coliformes_fecais_analisadas TEXT, " +
			"quantidade_coliformes_termo_tolerantes_analisadas TEXT, quantidade_turbidez_conforme TEXT, quantidade_cor_conforme TEXT, quantidade_cloro_conforme TEXT, quantidade_fluor_conforme TEXT, quantidade_coliformes_totais_conforme TEXT, quantidade_coliformes_fecais_conforme TEXT, " +
			"quantidade_coliformes_termo_tolerantes_conforme TEXT, consumo_minimo_imovel TEXT, consumo_minimo_imovel_nao_medido TEXT, numero_documento_notificacao_debito TEXT, numero_codigo_barra_notificacao_debito TEXT, cpf_cnpj_cliente TEXT, data_leitura_anterior_nao_medido DATE, indicador_abastecimento_agua TEXT," +
			" indicador_imovel_sazonal TEXT, indicador_paralizar_faturamento_agua TEXT, indicador_paralizar_faturamento_esgoto TEXT, opcao_debito_automatico TEXT, percentual_alternativo_esgoto TEXT, consumo_percentual_alternativo_esgoto TEXT, data_emissao_documento DATE, quantidade_contas_impressas TEXT, contagem_validacao_agua TEXT," +
			" contagem_validacao_poco TEXT, leitura_gravada_anterior TEXT, anormalidade_gravada_anterior TEXT, data_impressao_nao_medido DATE, valor_residual_credito TEXT, quantidade_imoveis_condominio TEXT, indc_adicionou_dados_iniciais_helper_rateio TEXT, valor_rateio_agua TEXT, valor_rateio_esgoto TEXT, consumo_rateio_agua TEXT, " +
			"consumo_rateio_esgoto TEXT)";
	
	
	
    private static final String DATABASE_DADOS_CATEGORIA_QUERY =
        "CREATE TABLE dados_categoria (id INTEGER PRIMARY KEY autoincrement, id_imovel INTEGER)";

    private static final String DATABASE_HISTORICO_CONSUMO_QUERY =
        "CREATE TABLE historico_consumo (id INTEGER PRIMARY KEY autoincrement, matricula INTEGER not null, tipo_ligacao TEXT, ano_mes_referencia TEXT, consumo TEXT, anormalidade_leitura TEXT, anormalidade_consumo TEXT)";

    private static final String DATABASE_DEBITO_QUERY =
    	"CREATE TABLE debito (id INTEGER PRIMARY KEY autoincrement, matricula INTEGER not null, descricao TEXT, valor TEXT, codigo TEXT, indc_uso TEXT)";
    
    private static final String DATABASE_CREDITO_QUERY =
    	"CREATE TABLE credito (id INTEGER PRIMARY KEY autoincrement, matricula INTEGER not null, descricao TEXT, valor TEXT, codigo TEXT, indc_uso TEXT)";
    
    private static final String DATABASE_IMPOSTO_QUERY =
        "CREATE TABLE imposto (id INTEGER PRIMARY KEY autoincrement, id_imovel INTEGER)";

    private static final String DATABASE_CONTA_QUERY =
        "CREATE TABLE conta (id INTEGER PRIMARY KEY autoincrement, matricula INTEGER, ano_mes_referencia_conta TEXT, valor_conta TEXT, data_vencimento_conta Date, valor_acresc_impontualidade TEXT )";

    private static final String DATABASE_TARIFACAO_MINIMA_QUERY =
        "CREATE TABLE tarifacao_minima (id INTEGER PRIMARY KEY autoincrement, matricula INTEGER not null, codigo TEXT, data_vigencia DATE, codigo_categoria TEXT, codigo_subcategoria TEXT, consumo_minimo_subcategoria TEXT," +
        "tarifa_minima_categoria TEXT)";

    private static final String DATABASE_TARIFACAO_COMPLEMENTAR_QUERY =
        "CREATE TABLE tarifacao_complementar (id INTEGER PRIMARY KEY autoincrement, matricula INTEGER not null, codigo TEXT, data_inicio_vigencia DATE, codigo_categoria TEXT, codigo_subcategoria TEXT, limite_inicial_faixa TEXT," +
        "limite_final_faixa TEXT, valor_m3_faixa TEXT)";
    
    private static final String DATABASE_MEDIDOR_QUERY =
    	"CREATE TABLE medidor (id INTEGER PRIMARY KEY autoincrement, matricula INTEGER not null, tipo_medicao TEXT, numero_hidrometro TEXT, data_instalacao_hidrometro TEXT, num_digitos_leitura_hidrometro TEXT, leitura_anterior_faturamento TEXT, " +  
    	"data_leitura_anterior_faturamento TEXT, codigo_situacao_leitura_anterior TEXT, leitura_esperada_inicial TEXT, leitura_esperada_final TEXT, consumo_medio TEXT, local_instalacao TEXT, leitura_anterior_informada TEXT,  TEXT," +
		"data_leitura_anterior_informada TEXT, data_ligacao_fornecimento TEXT, tipo_rateio TEXT, leitura_instalacao_hidrometro TEXT)";

    private static final String DATABASE_GERAL_QUERY =
        "CREATE TABLE geral (id INTEGER PRIMARY KEY autoincrement, data_referencia_arrecadacao TEXT, ano_mes_faturamento TEXT, codigo_empresa_febraban TEXT, telefone0800 TEXT, " +
        "cnpj_empresa TEXT, inscricao_estadual_empresa TEXT, valor_minim_emissao_conta TXT, percent_tolerancia_rateio TEXT, decremento_maximo_consumo_economia TEXT, incremento_maximo_consumo_economia TEXT, indc_tarifa_catgoria TEXT, login TEXT, " +
        "senha TEXT, data_ajuste_leitura TEXT, indicador_ajuste_consumo TEXT, indicador_transmissao_offline TEXT, versao_celular TEXT, indc_atualizar_sequencia_rota TEXT, indc_bloquear_reemissao_conta TEXT, qtd_dias_ajuste_consumo TEXT, indicador_rota_dividida TEXT," +
        "id_rota TEXT, modulo_verificador_codigo_barras TEXT, data_inicio TEXT, data_fim TEXT, data_referencia_arrecadada TEXT)";

    private static final String DATABASE_CONSUMO_QUERY =
        "CREATE TABLE consumo (id INTEGER PRIMARY KEY autoincrement, id_imovel INTEGER)";
    
    private static final String DATABASE_DADOS_FATURAMENTO_QUERY =
        "CREATE TABLE dados_faturamento (id INTEGER PRIMARY KEY autoincrement, id_imovel INTEGER)";
    
    private static final String DATABASE_DADOS_FATURAMENTO_FAIXA_QUERY =
        "CREATE TABLE dados_faturamento_faixa (id INTEGER PRIMARY KEY autoincrement, id_imovel INTEGER)";
    
    private static final String DATABASE_DADOS_RELATORIO_QUERY =
        "CREATE TABLE dados_relatorio (id INTEGER PRIMARY KEY autoincrement, id_imovel INTEGER)";
    
    private static final String DATABASE_SITUACAO_TIPO_QUERY =
        "CREATE TABLE situacao_tipo (id INTEGER PRIMARY KEY autoincrement, matricula INTEGER not null, tipo_situacao_especial_feturamento TEXT, id_anormalidade_consumo_sem_leitura TEXT, id_anormalidade_consumo_com_leitura TEXT, id_anormalidade_leitura_sem_leitura TEXT, " +
        "id_anormalidade_leitura_com_leitura TEXT, consumo_agua_medido_historico_faturamento TEXT, consumo_agua_nao_medido_historico_faturamento TEXT, volume_esgoto_medido_historico_faturamento TEXT, volume_esgoto_nao_medido_historico_faturamento TEXT, indc_valida_agua TEXT," +
        " indc_valida_esgoto TEXT)";
    
    private static final String DATABASE_ANORMALIDADE_QUERY =
        "CREATE TABLE anormalidade (id INTEGER PRIMARY KEY autoincrement, codigo INTEGER, descricao TEXT, indicador_leitura INTEGER, id_consumo_a_cobrar_sem_leitura INTEGER, " +
        "id_consumo_a_cobrar_com_leitura INTEGER, id_leitura_faturar_sem_leitura INTEGER, id_leitura_faturar_com_leitura INTEGER, indc_uso INTEGER, numero_fator_sem_leitura INTEGER," +
        "numero_fator_com_leitura INTEGER)";

    private static final String DATABASE_CONFIGURACAO_QUERY =
    	"CREATE TABLE configuracao (id INTEGER PRIMARY KEY autoincrement, rota_carregada INTEGER, posicao_cadastro_selecionado INTEGER)";

    public DbHelper(Context context) {
		super(context, Constantes.DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase db) {
    	db.execSQL(DATABASE_IMOVEL_QUERY);
    	db.execSQL(DATABASE_DADOS_CATEGORIA_QUERY);
    	db.execSQL(DATABASE_HISTORICO_CONSUMO_QUERY);
    	db.execSQL(DATABASE_DEBITO_QUERY);
    	db.execSQL(DATABASE_CREDITO_QUERY);
    	db.execSQL(DATABASE_IMPOSTO_QUERY);
    	db.execSQL(DATABASE_CONTA_QUERY);
    	db.execSQL(DATABASE_MEDIDOR_QUERY);
    	db.execSQL(DATABASE_TARIFACAO_MINIMA_QUERY);
    	db.execSQL(DATABASE_TARIFACAO_COMPLEMENTAR_QUERY);
     	db.execSQL(DATABASE_GERAL_QUERY);
     	db.execSQL(DATABASE_ANORMALIDADE_QUERY);

     	db.execSQL(DATABASE_CONSUMO_QUERY);
     	db.execSQL(DATABASE_DADOS_FATURAMENTO_QUERY);
     	db.execSQL(DATABASE_DADOS_FATURAMENTO_FAIXA_QUERY);
     	db.execSQL(DATABASE_DADOS_RELATORIO_QUERY);
     	db.execSQL(DATABASE_SITUACAO_TIPO_QUERY);
     	db.execSQL(DATABASE_CONFIGURACAO_QUERY);
    }

	// Method is called during an upgrade of the database, e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
		Log.w(DbHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
         db.execSQL("DROP TABLE IF EXISTS " + Constantes.TABLE_IMOVEL);
         onCreate(db);
    }
}
