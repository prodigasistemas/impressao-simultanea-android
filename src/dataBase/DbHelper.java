package dataBase;

import java.util.Date;

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

    private static final String DATABASE_CONTA_QUERY =
        	"CREATE TABLE conta (id INTEGER PRIMARY KEY autoincrement, id_imovel INTEGER, ano_mes_referencia_conta TEXT, valor_conta TEXT, data_vencimento_conta Date, valor_acresc_impontualidade TEXT )";

    private static final String DATABASE_TARIFA_MINIMA_QUERY =
    		"CREATE TABLE tarifa_minima (id INTEGER PRIMARY KEY autoincrement, id_imovel INTEGER)";

    private static final String DATABASE_TARIFA_COMPLEMENTAR_QUERY =
        	"CREATE TABLE tarifa_complementar (id INTEGER PRIMARY KEY autoincrement, id_imovel INTEGER)";

    private static final String DATABASE_MEDIDOR_QUERY =
    	"CREATE TABLE medidor (id INTEGER PRIMARY KEY autoincrement, id_imovel INTEGER not null, tipo_medicao TEXT, numero_hidrometro TEXT, data_instalacao_hidrometro TEXT, num_digitos_leitura_hidrometro TEXT, leitura_anterior_faturamento TEXT, " +  
    	"data_leitura_anterior_faturamento TEXT, codigo_situacao_leitura_anterior TEXT, leitura_esperada_inicial TEXT, leitura_esperada_final TEXT, consumo_medio TEXT, local_instalacao TEXT, leitura_anterior_informada TEXT,  TEXT," +
		"data_leitura_anterior_informada TEXT, data_ligacao_fornecimento TEXT, tipo_rateio TEXT, leitura_instalacao_hidrometro TEXT)";

    private static final String DATABASE_GERAL_QUERY =
    	"CREATE TABLE geral (id INTEGER PRIMARY KEY autoincrement, ano_mes_faturamento TEXT, codigo_febraban TEXT, telefone0800 TEXT, cnpj_empresa TEXT, " +
    	"inscricao_estadual_empresa TEXT, login TEXT, senha TXT, indicador_transmissao_offline TEXT, versao_celular TEXT, id_rota TEXT, data_inicio TEXT, data_fim TEXT, " +
    	"localidade TEXT, setor TEXT, rota TEXT, grupo_faturamento TEXT)";

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
    	"CREATE TABLE anormalidade (id INTEGER PRIMARY KEY autoincrement, codigo INTEGER, descricao TEXT, data DATE)";

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
    	db.execSQL(DATABASE_CONTA_QUERY);
    	db.execSQL(DATABASE_MEDIDOR_QUERY);
    	db.execSQL(DATABASE_TARIFA_MINIMA_QUERY);
    	db.execSQL(DATABASE_TARIFA_COMPLEMENTAR_QUERY);
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
