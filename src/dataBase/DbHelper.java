package dataBase;

import util.Constantes;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_IMOVEL_QUERY =
    	"CREATE TABLE imovel (id INTEGER PRIMARY KEY autoincrement, matricula TEXT not null, codigo_cliente TEXT, inscricao TEXT, rota TEXT, face TEXT, codigo_municipio TEXT, numero_iptu TEXT, numero_celpa TEXT, numero_pontos_uteis TEXT, " +
    	"logradouro_imovel TEXT, numero_imovel TEXT, complemento_imovel TEXT, bairro_imovel TEXT, cep_imovel TEXT, municipio_imovel TEXT, codigo_logradouro_imovel TEXT, " +
    	"sub_categoria_residencial_1 TEXT, sub_categoria_residencial_2 TEXT, sub_categoria_residencial_3 TEXT, sub_categoria_residencial_4 TEXT, " +
    	"sub_categoria_comercial_1 TEXT, sub_categoria_comercial_2 TEXT, sub_categoria_comercial_3 TEXT, sub_categoria_comercial_4 TEXT, " +
    	"sub_categoria_publica_1 TEXT, sub_categoria_publica_2 TEXT, sub_categoria_publica_3 TEXT, sub_categoria_publica_4 TEXT, " +
    	"sub_categoria_industrial_1 TEXT, sub_categoria_industrial_2 TEXT, sub_categoria_industrial_3 TEXT, sub_categoria_industrial_4 TEXT," +
    	"tipo_fonte_abastecimento TEXT, imovel_status TEXT, imovel_enviado TEXT, latitude TEXT, longitude TEXT, data TEXT)";

    private static final String DATABASE_DADOS_CATEGORIA_QUERY =
        	"CREATE TABLE dados_categoria (id INTEGER PRIMARY KEY autoincrement, id_imovel INTEGER)";

    private static final String DATABASE_LIGACAO_QUERY =
        	"CREATE TABLE ligacao (id INTEGER PRIMARY KEY autoincrement, id_imovel INTEGER)";

    private static final String DATABASE_DEBITO_QUERY =
    		"CREATE TABLE debito (id INTEGER PRIMARY KEY autoincrement, id_imovel INTEGER)";
    
    private static final String DATABASE_CREDITO_QUERY =
    		"CREATE TABLE credito (id INTEGER PRIMARY KEY autoincrement, id_imovel INTEGER)";
    
    private static final String DATABASE_IMPOSTO_QUERY =
        	"CREATE TABLE imposto (id INTEGER PRIMARY KEY autoincrement, id_imovel INTEGER)";

    private static final String DATABASE_TARIFACAO_MINIMA_QUERY =
        	"CREATE TABLE tarifacao_minima (id INTEGER PRIMARY KEY autoincrement, id_imovel INTEGER, codigo TEXT, data_vigencia DATE, codigo_categoria TEXT, codigo_subcategoria TEXT, consumo_minimo_subcategoria TEXT," +
        	"tarifa_minima_categoria TEXT)";

    private static final String DATABASE_TARIFACAO_COMPLEMENTAR_QUERY =
        	"CREATE TABLE tarifacao_complementar (id INTEGER PRIMARY KEY autoincrement, id_imovel TEXT, codigo TEXT, data_inicio_vigencia DATE, codigo_categoria TEXT, codigo_subcategoria TEXT, limite_inicial_faixa TEXT," +
        	"limite_final_faixa TEXT, valor_m3_faixa TEXT)";

    private static final String DATABASE_MEDIDOR_QUERY =
    	"CREATE TABLE medidor (id INTEGER PRIMARY KEY autoincrement, id_imovel INTEGER, matricula TEXT not null, possui_medidor TEXT, numero_hidrometro TEXT, marca TEXT, capacidade TEXT, tipo_caixa_protecao TEXT, latitude TEXT, longitude TEXT, data TEXT)";

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
        	"CREATE TABLE situacao_tipo (id INTEGER PRIMARY KEY autoincrement, id_imovel INTEGER)";
    
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
    	db.execSQL(DATABASE_LIGACAO_QUERY);
    	db.execSQL(DATABASE_DEBITO_QUERY);
    	db.execSQL(DATABASE_CREDITO_QUERY);
    	db.execSQL(DATABASE_IMPOSTO_QUERY);
    	db.execSQL(DATABASE_TARIFACAO_MINIMA_QUERY);
    	db.execSQL(DATABASE_TARIFACAO_COMPLEMENTAR_QUERY);
    	db.execSQL(DATABASE_MEDIDOR_QUERY);
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
