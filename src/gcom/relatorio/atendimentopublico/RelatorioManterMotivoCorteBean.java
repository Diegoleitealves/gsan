package gcom.relatorio.atendimentopublico;

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
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Rafael Corr�a
 * @version 1.0
 */

public class RelatorioManterMotivoCorteBean implements RelatorioBean {
	private String codigo;

	private String descricao;

	private String indicadorUso;

	/**
	 * Construtor da classe RelatorioManterMotivoCorteBean
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

	public RelatorioManterMotivoCorteBean(String codigo, String descricao,
			String indicadorUso) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.indicadorUso = indicadorUso;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getIndicadorUso() {
		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso) {
		this.indicadorUso = indicadorUso;
	}



}
