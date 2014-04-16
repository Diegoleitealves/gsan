package gcom.relatorio.seguranca;

import gcom.relatorio.RelatorioBean;

/**
 * 
 * @author Vinicius Medeiros
 * @version 1.0
 */

public class RelatorioManterAlteracaoTipoBean implements RelatorioBean {
	private String codigo;
	private String descricao;
	private String descricaoAbreviada;

	/**
	 * Construtor da classe RelatorioManterAlteracaoTipoBean
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

	public RelatorioManterAlteracaoTipoBean(String codigo, String descricao, String descricaoAbreviada) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
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
	
	public String getDescricaoAbreviada() {
		return descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada) {
		this.descricaoAbreviada = descricaoAbreviada;
	}
}
