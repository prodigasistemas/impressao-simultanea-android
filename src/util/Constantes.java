package util;

public class Constantes {

	public static final int NULO_INT = Integer.MIN_VALUE;
    public static final short NULO_SHORT = Short.MIN_VALUE;
    public static final String NULO_STRING = "";
    public static final double NULO_DOUBLE = Double.MIN_VALUE;

    public static final int DIALOG_ID_PASSWORD = 0;
    public static final int DIALOG_ID_CARREGAR_ROTA = 1;
    public static final int DIALOG_ID_CLEAN_DB = 2;
    public static final int DIALOG_ID_GERAR_ARQUIVO_COMPLETO = 3;
    public static final int DIALOG_ID_SUCESSO = 4;
    public static final int DIALOG_ID_AVISO = 5;
    public static final int DIALOG_ID_ERRO = 6;
    public static final int DIALOG_ID_ERRO_GPS_DESLIGADO = 7;
    public static final int DIALOG_ID_CONFIRM_BACK = 8;
       
    public static final String DIRETORIO_ROTAS = "/external_sd/GcomMovel/Roteiros";
    public static final String DIRETORIO_RETORNO = "/external_sd/GcomMovel/Retorno";
    
    /**
     * Medição Tipo
     */
    public static final int LIGACAO_AGUA = 1;
    public static final int LIGACAO_POCO = 2;

    public static final String AGUA = "Água";
    public static final String POCO = "Poço";

    public static final int REGISTRO_TIPO_IMOVEL = 1;
    public static final int REGISTRO_TIPO_DADOS_CATEGORIA = 2;
    public static final int REGISTRO_TIPO_LIGACAO = 3;
    public static final int REGISTRO_TIPO_DEBITO = 4;
    public static final int REGISTRO_TIPO_CREDITO = 5;
    public static final int REGISTRO_TIPO_IMPOSTO = 6;
    public static final int REGISTRO_TIPO_CONTA = 7;
    public static final int REGISTRO_TIPO_MEDIDOR = 8;
    public static final int REGISTRO_TIPO_TARIFACAO_MINIMA = 9;
    public static final int REGISTRO_TIPO_TARIFACAO_COMPLEMENTAR = 10;
    public static final int REGISTRO_TIPO_GERAL = 11;
    public static final int REGISTRO_TIPO_ANORMALIDADE = 14;
    
    public static final int SIM = 1;
    public static final int NAO = 2;
   
    public static final int IMOVEL_PROPRIETARIO_RESIDENCIAL = 1;
    public static final int IMOVEL_PROPRIETARIO_COMERCIAL = 2;
    
    public static final int IMOVEL_RESPONSAVEL_RESIDENCIAL = 1;
    public static final int IMOVEL_RESPONSAVEL_COMERCIAL = 2;
    
    public static final int FONTE_ABASTECIMENTO_COSANPA = 0;
    public static final int FONTE_ABASTECIMENTO_PROPRIO = 1;
    public static final int FONTE_ABASTECIMENTO_MISTO = 2;
    public static final int FONTE_ABASTECIMENTO_OUTRO = 3;
    
    public static final int METODO_BUSCA_TODOS = 0;
    public static final int METODO_BUSCA_MATRICULA = 1;
    public static final int METODO_BUSCA_CPF = 2;
    public static final int METODO_BUSCA_CNPJ = 3;
    
    public static final int FILTRO_BUSCA_TODOS = 0;
    public static final int FILTRO_BUSCA_VISITADOS_SUCESSO = 1;
    public static final int FILTRO_BUSCA_VISITADOS_ANORMALIDADE = 2;
    public static final int FILTRO_BUSCA_NAO_VISITADOS = 3;
    public static final int FILTRO_BUSCA_NOVOS = 4;
    public static final int FILTRO_BUSCA_TRANSMITIDOS = 5;
    public static final int FILTRO_BUSCA_NAO_TRANSMITIDOS = 6;
    
    public static final String TABLE_IMOVEL = "imovel";
    public static final String TABLE_DADOS_CATEGORIA = "dados_categoria";
    public static final String TABLE_LIGACAO = "ligacao";
    public static final String TABLE_IMPOSTO = "imposto";
    public static final String TABLE_CONTA = "conta";
    public static final String TABLE_MEDIDOR = "medidor";
    public static final String TABLE_TARIFACAO_MINIMA = "tarifacao_minima";
    public static final String TABLE_TARIFACAO_COMPLEMENTAR = "tarifacao_complementar";
    public static final String TABLE_GERAL = "geral";
    public static final String TABLE_ANORMALIDADE = "anormalidade";
    public static final String TABLE_CONSUMO_IMOVEL = "consumo_imovel";
    public static final String TABLE_FATURAMENTO_IMOVEL = "faturamento_imovel";
    public static final String TABLE_CONFIGURACAO = "configuracao";
    public static final String TABLE_ANORMALIDADE_IMOVEL = "anormalidade_imovel";
    
    public static final String DATABASE_NAME = "ImpressaoSimultanea.db";
	public static final String DATABASE_PATH = "/data/data/com.IS/databases/";
	
    public static final int IMOVEL_CONCLUIDO = 0;
    public static final int IMOVEL_PENDENTE = 1;
    public static final int IMOVEL_CONCLUIDO_COM_ANORMALIDADE = 2;
    public static final int IMOVEL_RETIDO = 3;
    public static final int IMOVEL_TRANSMITIDO = 4;
    public static final int IMOVEL_NAO_TRANSMITIDO = 5;

}
