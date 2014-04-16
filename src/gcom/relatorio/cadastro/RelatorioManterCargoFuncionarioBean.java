package gcom.relatorio.cadastro;

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
 * @author Arthur Carvalho
 * @version 1.0
 */

public class RelatorioManterCargoFuncionarioBean implements RelatorioBean {
	
	private String codigo;

	private String descricao;
	
	private String descricaoAbreviada;
	
	private String indicadorUso;

	/**
	 * Construtor da classe RelatorioManterBaciaBean
	 * 
	 * @param id
	 *            Descri��o do par�metro
	 * @param descricao
	 *            Descri��o do par�metro
	 * @param sistemaEsgoto
	 *            Descri��o do par�metro
	 */

	public RelatorioManterCargoFuncionarioBean(String codigo,
			String descricao, String descricaoAbreviada, String indicadorUso) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.indicadorUso = indicadorUso;
	}

	/**
	 * Retorna o valor do id
	 * 
	 * @return O valor do id
	 */

	public String getCodigo() {
		return codigo;
	}

	/**
	 * Retorna o valor da descricao
	 * 
	 * @return O valor da descricao
	 */

	public String getDescricao() {
		return descricao;
	}

	/**
	 * Seta o valor do id
	 * 
	 * @param id
	 *            O novo valor do id
	 */

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	/**
	 * Seta o valor de sistemaEsgoto
	 * 
	 * @param nome
	 *            O novo valor de sistemaEsgoto
	 */

	public String getDescricaoAbreviada() {
		return descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada) {
		this.descricaoAbreviada = descricaoAbreviada;
	}


	public String getIndicadorUso() {
		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso) {
		this.indicadorUso = indicadorUso;
	}

	
	
	

	
}
