package dataBase;

import util.Constantes;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CLIENTE_QUERY =
    	"CREATE TABLE cliente (id INTEGER PRIMARY KEY autoincrement, matricula TEXT not null, gerencia TEXT, tipo_endereco_proprietario TEXT, tipo_endereco_responsavel TEXT, usuario_proprietario TEXT, tipo_responsavel TEXT, " +
    	"nome_usuario TEXT, tipo_pessoa_usuario TEXT, cpf_cnpj_usuario TEXT, rg_usuario TEXT, uf_usuario TEXT, tipo_sexo_usuario TEXT, telefone_usuario TEXT, celular_usuario TEXT, email_usuario TEXT, " +
    	"nome_proprietario TEXT, tipo_pessoa_proprietario TEXT, cpf_cnpj_proprietario TEXT, rg_proprietario TEXT, uf_proprietario TEXT, tipo_sexo_proprietario TEXT, telefone_proprietario TEXT, celular_proprietario TEXT, email_proprietario TEXT, " +
     	"logradouro_proprietario TXT, numero_proprietario TEXT, complemento_proprietario TEXT, bairro_proprietario TEXT, cep_proprietario TEXT, municipio_proprietario TEXT, " +
    	"nome_responsavel TEXT, tipo_pessoa_responsavel TEXT, cpf_cnpj_responsavel TEXT, rg_responsavel TEXT, uf_responsavel TEXT, tipo_sexo_responsavel TEXT, telefone_responsavel TEXT, celular_responsavel TEXT, email_responsavel TEXT, " +
 		"logradouro_responsavel TXT, numero_responsavel TEXT, complemento_responsavel TEXT, bairro_responsavel TEXT, cep_responsavel TEXT, municipio_responsavel TEXT, latitude TEXT, longitude TEXT, data TEXT )";

    private static final String DATABASE_IMOVEL_QUERY =
    	"CREATE TABLE imovel (id INTEGER PRIMARY KEY autoincrement, matricula TEXT not null, codigo_cliente TEXT, inscricao TEXT, rota TEXT, face TEXT, codigo_municipio TEXT, numero_iptu TEXT, numero_celpa TEXT, numero_pontos_uteis TEXT, " +
    	"logradouro_imovel TEXT, numero_imovel TEXT, complemento_imovel TEXT, bairro_imovel TEXT, cep_imovel TEXT, municipio_imovel TEXT, codigo_logradouro_imovel TEXT, " +
    	"sub_categoria_residencial_1 TEXT, sub_categoria_residencial_2 TEXT, sub_categoria_residencial_3 TEXT, sub_categoria_residencial_4 TEXT, " +
    	"sub_categoria_comercial_1 TEXT, sub_categoria_comercial_2 TEXT, sub_categoria_comercial_3 TEXT, sub_categoria_comercial_4 TEXT, " +
    	"sub_categoria_publica_1 TEXT, sub_categoria_publica_2 TEXT, sub_categoria_publica_3 TEXT, sub_categoria_publica_4 TEXT, " +
    	"sub_categoria_industrial_1 TEXT, sub_categoria_industrial_2 TEXT, sub_categoria_industrial_3 TEXT, sub_categoria_industrial_4 TEXT," +
    	"tipo_fonte_abastecimento TEXT, imovel_status TEXT, imovel_enviado TEXT, latitude TEXT, longitude TEXT, data TEXT)";

    private static final String DATABASE_RAMO_ATIVIDADE_IMOVEL_QUERY =
    	"CREATE TABLE ramo_atividade_imovel (id INTEGER PRIMARY KEY autoincrement, matricula TEXT not null, id_imovel INTEGER, codigo INTEGER)";

    private static final String DATABASE_SERVICO_QUERY =
    	"CREATE TABLE servico (id INTEGER PRIMARY KEY autoincrement, matricula TEXT not null, tipo_ligacao_agua TEXT, tipo_ligacao_esgoto TEXT, local_instalacao_ramal TEXT, latitude TEXT, longitude TEXT, data TEXT)";

    private static final String DATABASE_MEDIDOR_QUERY =
    	"CREATE TABLE medidor (id INTEGER PRIMARY KEY autoincrement, matricula TEXT not null, possui_medidor TEXT, numero_hidrometro TEXT, marca TEXT, capacidade TEXT, tipo_caixa_protecao TEXT, latitude TEXT, longitude TEXT, data TEXT)";

    private static final String DATABASE_GERAL_QUERY =
    	"CREATE TABLE geral (id INTEGER PRIMARY KEY autoincrement, ano_mes_faturamento TEXT, codigo_febraban TEXT, telefone0800 TEXT, cnpj_empresa TEXT, " +
    	"inscricao_estadual_empresa TEXT, login TEXT, senha TXT, indicador_transmissao_offline TEXT, versao_celular TEXT, id_rota TEXT, data_inicio TEXT, data_fim TEXT, " +
    	"localidade TEXT, setor TEXT, rota TEXT, grupo_faturamento TEXT)";

    private static final String DATABASE_ANORMALIDADE_QUERY =
    	"CREATE TABLE anormalidade (id INTEGER PRIMARY KEY autoincrement, codigo INTEGER, descricao TEXT, data DATE)";

    private static final String DATABASE_ANORMALIDADE_IMOVEL_QUERY =
    	"CREATE TABLE anormalidade_imovel (id INTEGER PRIMARY KEY autoincrement, matricula TEXT, latitude TEXT, longitude TEXT, codigo_anormalidade TEXT, comentario TEXT, path_image_1 TEXT, path_image_2 TEXT, data TEXT)";

    private static final String DATABASE_RAMO_ATIVIDADE_QUERY =
    	"CREATE TABLE ramo_atividade (id INTEGER PRIMARY KEY autoincrement, codigo INTEGER, descricao TEXT)";

    private static final String DATABASE_SITUACAO_AGUA_QUERY =
    	"CREATE TABLE ligacao_agua (id INTEGER PRIMARY KEY autoincrement, codigo INTEGER, descricao TEXT)";

    private static final String DATABASE_SITUACAO_ESGOTO_QUERY =
    	"CREATE TABLE ligacao_esgoto (id INTEGER PRIMARY KEY autoincrement, codigo INTEGER, descricao TEXT)";

    private static final String DATABASE_PROTECAO_HIDROMETRO_QUERY =
    	"CREATE TABLE protecao_hidrometro (id INTEGER PRIMARY KEY autoincrement, codigo INTEGER, descricao TEXT)";

    private static final String DATABASE_FONTE_ABASTECIMENTO_QUERY =
    	"CREATE TABLE fonte_abastecimento (id INTEGER PRIMARY KEY autoincrement, codigo INTEGER, descricao TEXT)";

    private static final String DATABASE_MARCA_HIDROMETRO_QUERY =
    	"CREATE TABLE marca_hidrometro (id INTEGER PRIMARY KEY autoincrement, codigo INTEGER, descricao TEXT)";

    private static final String DATABASE_LOCAL_INSTALACAO_RAMAL_QUERY =
        "CREATE TABLE local_instalacao_ramal (id INTEGER PRIMARY KEY autoincrement, codigo INTEGER, descricao TEXT)";

    private static final String DATABASE_CONFIGURACAO_QUERY =
    	"CREATE TABLE configuracao (id INTEGER PRIMARY KEY autoincrement, rota_carregada INTEGER, posicao_cadastro_selecionado INTEGER)";

    public DbHelper(Context context) {
		super(context, Constantes.DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase db) {
    	db.execSQL(DATABASE_CLIENTE_QUERY);
    	db.execSQL(DATABASE_IMOVEL_QUERY);
     	db.execSQL(DATABASE_RAMO_ATIVIDADE_IMOVEL_QUERY);
    	db.execSQL(DATABASE_SERVICO_QUERY);
    	db.execSQL(DATABASE_MEDIDOR_QUERY);
     	db.execSQL(DATABASE_GERAL_QUERY);
     	db.execSQL(DATABASE_ANORMALIDADE_QUERY);
     	db.execSQL(DATABASE_ANORMALIDADE_IMOVEL_QUERY);
     	db.execSQL(DATABASE_RAMO_ATIVIDADE_QUERY);
     	db.execSQL(DATABASE_SITUACAO_AGUA_QUERY);
     	db.execSQL(DATABASE_SITUACAO_ESGOTO_QUERY);
     	db.execSQL(DATABASE_PROTECAO_HIDROMETRO_QUERY);
     	db.execSQL(DATABASE_FONTE_ABASTECIMENTO_QUERY);
     	db.execSQL(DATABASE_MARCA_HIDROMETRO_QUERY);
     	db.execSQL(DATABASE_LOCAL_INSTALACAO_RAMAL_QUERY);
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
