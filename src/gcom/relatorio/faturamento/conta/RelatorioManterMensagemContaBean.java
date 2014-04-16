package gcom.relatorio.faturamento.conta;

import gcom.relatorio.RelatorioBean;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * @author Rafael Corr�a
 * @version 1.0
 */

public class RelatorioManterMensagemContaBean implements RelatorioBean {

	private String referencia;

	private String mensagem;

	private String grupo;

	private String gerenciaRegional;

	private String localidade;

	private String setorComercial;

	/**
	 * Construtor da classe RelatorioManterLogradouroBean
	 * 
	 * @param codigo
	 *            Descri��o do par�metro
	 * @param gerenciaRegional
	 *            Descri��o do par�metro
	 * @param nome
	 *            Descri��o do par�metro
	 * @param indicadorUso
	 *            Descri��o do par�metro
	 */

	/**
	 * @param referencia
	 * @param mensagem
	 * @param grupo
	 * @param gerenciaRegional
	 * @param localidade
	 * @param setorComercial
	 */
	public RelatorioManterMensagemContaBean(String referencia, String mensagem,
			String grupo, String gerenciaRegional, String localidade,
			String setorComercial) {
		this.referencia = referencia;
		this.mensagem = mensagem;
		this.grupo = grupo;
		this.gerenciaRegional = gerenciaRegional;
		this.localidade = localidade;
		this.setorComercial = setorComercial;
	}

	public String getGerenciaRegional() {
		return gerenciaRegional;
	}

	public void setGerenciaRegional(String gerenciaRegional) {
		this.gerenciaRegional = gerenciaRegional;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getSetorComercial() {
		return setorComercial;
	}

	public void setSetorComercial(String setorComercial) {
		this.setorComercial = setorComercial;
	}

}
