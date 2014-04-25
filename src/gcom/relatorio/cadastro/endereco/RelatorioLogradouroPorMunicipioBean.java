package gcom.relatorio.cadastro.endereco;

import gcom.relatorio.RelatorioBean;

/**
 * <p>
 * 
 * Title: GCOM
 * </p>
 * <p>
 * 
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * 
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * 
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Wallace Thierre
 * @version 1.0
 */

public class RelatorioLogradouroPorMunicipioBean implements RelatorioBean {
	private String descricaoTipo;

	private String descricaoBairro;

	private String descricaoTitulo;

	private String descricaoLogradouro;


	/**
	 * Construtor da classe RelatorioManterLogradouroBean
	 * 
	 * @param codigo
	 *            Descri��o do par�metro
	 * @param nome
	 *            Descri��o do par�metro
	 * @param titulo
	 *            Descri��o do par�metro
	 * @param tipo
	 *            Descri��o do par�metro
	 * @param municipio
	 *            Descri��o do par�metro
	 * @param indicadorUso
	 *            Descri��o do par�metro
	 */

	public RelatorioLogradouroPorMunicipioBean(String descricaoTipo, String descricaoBairro, String descricaoTitulo, String descricaoLogradouro) {
		this.descricaoTipo = descricaoTipo;
		this.descricaoBairro = descricaoBairro;
		this.descricaoTitulo = descricaoTitulo;
		this.descricaoLogradouro = descricaoLogradouro;        
	}


	public String getDescricaoTipo() {
		return descricaoTipo;
	}


	public void setDescricaoTipo(String descricaoTipo) {
		this.descricaoTipo = descricaoTipo;
	}


	public String getDescricaoBairro() {
		return descricaoBairro;
	}


	public void setDescricaoBairro(String descricaoBairro) {
		this.descricaoBairro = descricaoBairro;
	}


	public String getDescricaoTitulo() {
		return descricaoTitulo;
	}


	public void setDescricaoTitulo(String descricaoLogradouro) {
		this.descricaoTitulo = descricaoLogradouro;
	}


	public String getDescricaoLogradouro() {
		return descricaoLogradouro;
	}


	public void setDescricaoLogradouro(String descricaoLogradouro) {
		this.descricaoLogradouro = descricaoLogradouro;
	}



}
