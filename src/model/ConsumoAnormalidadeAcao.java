package model;

import java.util.Vector;

import util.Util;

public class ConsumoAnormalidadeAcao {

	private String tipoRegistro;
	private int idConsumoAnormalidade;
	private int idCategoria;
	private int idPerfil;
	private int idLeituraAnormalidadeConsumoPrimeiroMes;
	private int idLeituraAnormalidadeConsumoSegundoMes;
	private int idLeituraAnormalidadeConsumoTerceiroMes;
	private double fatorConsumoPrimeiroMes;
	private double fatorConsumoSegundoMes;
	private double fatorConsumoTerceiroMes;
	private int indcGeracaoCartaPrimeiroMes;
	private int indcGeracaoCartaSegundoMes;
	private int indcGeracaoCartaTerceiroMes;
	private String mensagemContaPrimeiroMes;
	private String mensagemContaSegundoMes;
	private String mensagemContaTerceiroMes;

	private static ConsumoAnormalidadeAcao instancia;

	private Vector registros12;

	private long id;

	public ConsumoAnormalidadeAcao() {
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public ConsumoAnormalidadeAcao getRegistro12() {

		return ConsumoAnormalidadeAcao.instancia;

	}

	public static ConsumoAnormalidadeAcao getInstancia() {

		if (ConsumoAnormalidadeAcao.instancia == null) {
			ConsumoAnormalidadeAcao.instancia = new ConsumoAnormalidadeAcao();
		}

		return ConsumoAnormalidadeAcao.instancia;
	}

	public String getTipoRegistro() {
		return tipoRegistro;
	}

	public void setTipoRegistro(String tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public int getIdConsumoAnormalidade() {
		return idConsumoAnormalidade;
	}

	public void setIdConsumoAnormalidade(String idConsumoAnormalidade) {
		this.idConsumoAnormalidade = Util.verificarNuloInt(idConsumoAnormalidade);
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(String idCategoria) {
		this.idCategoria = Util.verificarNuloInt(idCategoria);
	}

	public int getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(String idPerfil) {
		this.idPerfil = Util.verificarNuloInt(idPerfil);

	}

	public int getIdLeituraAnormalidadeConsumoPrimeiroMes() {
		return idLeituraAnormalidadeConsumoPrimeiroMes;
	}

	public void setIdLeituraAnormalidadeConsumoPrimeiroMes(String idLeituraAnormalidadeConsumoPrimeiroMes) {
		this.idLeituraAnormalidadeConsumoPrimeiroMes = Util.verificarNuloInt(idLeituraAnormalidadeConsumoPrimeiroMes);
	}

	public int getIdLeituraAnormalidadeConsumoSegundoMes() {
		return idLeituraAnormalidadeConsumoSegundoMes;
	}

	public void setIdLeituraAnormalidadeConsumoSegundoMes(String idLeituraAnormalidadeConsumoSegundoMes) {
		this.idLeituraAnormalidadeConsumoSegundoMes = Util.verificarNuloInt(idLeituraAnormalidadeConsumoSegundoMes);
	}

	public int getIdLeituraAnormalidadeConsumoTerceiroMes() {
		return idLeituraAnormalidadeConsumoTerceiroMes;
	}

	public void setIdLeituraAnormalidadeConsumoTerceiroMes(String idLeituraAnormalidadeConsumoTerceiroMes) {
		this.idLeituraAnormalidadeConsumoTerceiroMes = Util.verificarNuloInt(idLeituraAnormalidadeConsumoTerceiroMes);
	}

	public double getFatorConsumoPrimeiroMes() {
		return fatorConsumoPrimeiroMes;
	}

	public void setFatorConsumoPrimeiroMes(String fatorConsumoPrimeiroMes) {
		this.fatorConsumoPrimeiroMes = Util.verificarNuloDouble(fatorConsumoPrimeiroMes);
	}

	public double getFatorConsumoSegundoMes() {
		return fatorConsumoSegundoMes;
	}

	public void setFatorConsumoSegundoMes(String fatorConsumoSegundoMes) {
		this.fatorConsumoSegundoMes = Util.verificarNuloDouble(fatorConsumoSegundoMes);
	}

	public double getFatorConsumoTerceiroMes() {
		return fatorConsumoTerceiroMes;
	}

	public void setFatorConsumoTerceiroMes(String fatorConsumoTerceiroMes) {
		this.fatorConsumoTerceiroMes = Util.verificarNuloDouble(fatorConsumoTerceiroMes);
	}

	public int getIndcGeracaoCartaPrimeiroMes() {
		return indcGeracaoCartaPrimeiroMes;
	}

	public void setIndcGeracaoCartaPrimeiroMes(String indcGeracaoCartaPrimeiroMes) {
		this.indcGeracaoCartaPrimeiroMes = Util.verificarNuloInt(indcGeracaoCartaPrimeiroMes);
	}

	public int getIndcGeracaoCartaSegundoMes() {
		return indcGeracaoCartaSegundoMes;
	}

	public void setIndcGeracaoCartaSegundoMes(String indcGeracaoCartaSegundoMes) {
		this.indcGeracaoCartaSegundoMes = Util.verificarNuloInt(indcGeracaoCartaSegundoMes);
	}

	public int getIndcGeracaoCartaTerceiroMes() {
		return indcGeracaoCartaTerceiroMes;
	}

	public void setIndcGeracaoCartaTerceiroMes(String indcGeracaoCartaTerceiroMes) {
		this.indcGeracaoCartaTerceiroMes = Util.verificarNuloInt(indcGeracaoCartaTerceiroMes);
	}

	public void adicionaRegistro12(ConsumoAnormalidadeAcao reg12) {
		if (this.registros12 == null) {
			this.registros12 = new Vector();
		}

		this.registros12.addElement(reg12);

		// Repositorio.salvarObjeto(ImovelReg12.getInstancia());
//		Repositorio.salvarObjetos(this.registros12);
	}

	public Vector getRegistros12() {
		return registros12;
	}

	/**
	 * 
	 * @param idAnormalidadeConsumo
	 * @param idCategoria
	 * @param idPerfilImovel
	 * @return registro 12 encontrado através da pesquisa
	 */
	public ConsumoAnormalidadeAcao getRegistro12(int idAnormalidadeConsumo,int idCategoria, int idPerfilImovel) {
		
		ConsumoAnormalidadeAcao registro12 = null;
		int tamanho = 0;

		if (this.registros12 != null && !this.registros12.isEmpty()) {
			tamanho = registros12.size();
		}

		for (int i = 0; i < tamanho; i++) {

			ConsumoAnormalidadeAcao reg12 = (ConsumoAnormalidadeAcao) registros12.elementAt(i);
			int idReg12 = reg12.getIdConsumoAnormalidade();
			int idCategoriaSelecionada = reg12.getIdCategoria();
			int idPerfilSelecionado = reg12.getIdPerfil();

			if (idAnormalidadeConsumo == idReg12) {

				registro12 = (ConsumoAnormalidadeAcao) registros12.elementAt(i);

				if (idCategoria == idCategoriaSelecionada) {
					registro12 = (ConsumoAnormalidadeAcao) registros12.elementAt(i);
				}
				if (idCategoria == idCategoriaSelecionada
						&& idPerfilImovel == idPerfilSelecionado) {
					registro12 = (ConsumoAnormalidadeAcao) registros12.elementAt(i);
				}
			}

		}
		return registro12;
	}

	public void carregarRegistros12(Vector registros) {

//		this.registros12 = new Vector();
//		int tamanho = registros.size();
//
//		for (int i = 1; i <= tamanho; i++) {
//			ConsumoAnormalidadeAcao imovelReg2 = new ConsumoAnormalidadeAcao();
//			Repositorio.carregarObjeto(imovelReg2, i);
//			this.registros12.addElement(imovelReg2);
//		}
	}

	public String getMensagemContaPrimeiroMes() {
		return mensagemContaPrimeiroMes;
	}

	public void setMensagemContaPrimeiroMes(String mensagemContaPrimeiroMes) {
		this.mensagemContaPrimeiroMes = mensagemContaPrimeiroMes;
	}

	public String getMensagemContaSegundoMes() {
		return mensagemContaSegundoMes;
	}

	public void setMensagemContaSegundoMes(String mensagemContaSegundoMes) {
		this.mensagemContaSegundoMes = mensagemContaSegundoMes;
	}

	public String getMensagemContaTerceiroMes() {
		return mensagemContaTerceiroMes;
	}

	public void setMensagemContaTerceiroMes(String mensagemContaTerceiroMes) {
		this.mensagemContaTerceiroMes = mensagemContaTerceiroMes;
	}

}
