package model;

import java.util.Date;

import util.Util;

public class DadosGerais{

    private String anoMesFaturamento;
    private String codigoEmpresaFebraban;
    private String telefone0800;
    private String cnpjEmpresa;
    private String inscricaoEstadualEmpresa;
    private String login;
    private String senha;
    private int indicadorTransmissaoOffline;
    private String versaoMovel;
    private int idRota;
    private Date dataInicio;
    private Date dataFim;
    private String localidade;
    private String setor;
    private String rota;
    private String grupoFaturamento;

    private static DadosGerais instancia;

    public DadosGerais() {}

	public void setLocalidade(String localidade) {
		this.localidade = Util.verificarNuloString(localidade);
	}

	public String getLocalidade() {
		return this.localidade;
	}

	public void setSetor(String setor) {
		this.setor = Util.verificarNuloString(setor);
	}

	public String getSetor() {
		return this.setor;
	}

	public void setRota(String rota) {
		this.rota = Util.verificarNuloString(rota);
	}

	public String getRota() {
		return this.rota;
	}

	public void setGrupoFaturamento(String grupoFaturamento) {
		this.grupoFaturamento = Util.verificarNuloString(grupoFaturamento);
	}

	public String getGrupoFaturamento() {
		return this.grupoFaturamento;
	}

    public String getAnoMesFaturamento() {
    	return anoMesFaturamento;
        }

    public void setAnoMesFaturamento(String anoMesFaturamento) {
    	this.anoMesFaturamento = Util.verificarNuloString(anoMesFaturamento);
    }

    public String getCodigoEmpresaFebraban() {
    	return codigoEmpresaFebraban;
    }

    public void setCodigoEmpresaFebraban(String codigoEmpresaFebraban) {
    	this.codigoEmpresaFebraban = codigoEmpresaFebraban;
    }

    public String getTelefone0800() {
    	return telefone0800;
    }

    public void setTelefone0800(String telefone0800) {
    	this.telefone0800 = telefone0800;
    }

    public String getCnpjEmpresa() {
    	return cnpjEmpresa;
    }

    public void setCnpjEmpresa(String cnpjEmpresa) {
    	this.cnpjEmpresa = cnpjEmpresa;
    }

    public String getInscricaoEstadualEmpresa() {
    	return inscricaoEstadualEmpresa;
    }

    public void setInscricaoEstadualEmpresa(String inscricaoEstadualEmpresa) {
    	this.inscricaoEstadualEmpresa = inscricaoEstadualEmpresa;
    }

    public void adicionaDadosGerais(DadosGerais dadosGerais) {
    	DadosGerais.instancia = dadosGerais;

    }

    public DadosGerais getDadosGerais() {
    	return DadosGerais.instancia;

    }

    public static DadosGerais getInstancia() {

    	return DadosGerais.instancia;
    }

    public String getLogin() {
	return login;
    }

    public void setLogin(String login) {
	this.login = login.trim();
    }

    public String getSenha() {
	return senha;
    }

    public void setSenha(String senha) {
	if (senha != null)
	    this.senha = senha.trim();
	else
	    this.senha = senha;
    }

    public int getIndicadorTransmissaoOffline() {
	return indicadorTransmissaoOffline;
    }

    public void setIndicadorTransmissaoOffline(
	    String indicadorTransmissaoOffline) {
	this.indicadorTransmissaoOffline = Util
		.verificarNuloInt(indicadorTransmissaoOffline);
    }

    public String getVersaoCelular() {
	return versaoMovel;
    }

    public void setVersaoCelular(String versaoCelular) {
	this.versaoMovel = Util.verificarNuloString(versaoCelular);
    }

    // Daniel - get Id de Rota
    public int getIdRota() {
        return idRota;
    }

    // Daniel - set Id de Rota
    public void setIdRota(String idRota) {
	this.idRota = Util.verificarNuloInt(idRota);
    }

    public Date getDataInicio() {
	return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
	this.dataInicio = Util.getData(Util.verificarNuloString(dataInicio));
    }
	
    public Date getDataFim() {
	return dataFim;
    }

    public void setDataFim(String dataFim) {
	this.dataFim = Util.getData(Util.verificarNuloString(dataFim));
    }

}
