package gcom.gui.seguranca.acesso;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 15/05/2006
 */
public class AtualizarFuncionalidadeActionForm extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String id;

	private String descricao;

	private String descricaoAbreviada;

	private String caminhoMenu;

	private String caminhoUrl;

	private String indicadorPontoEntrada;

	private String modulo;

	private String idFuncionalidade;

	private String numeroOrdemMenu;

	private String indicadorNovaJanela;

	private String indicadorOlap;

	private String idFuncionalidadeCategoria;

	private String nomeFuncionalidadeCategoria;

	public String getIdFuncionalidadeCategoria() {
		return idFuncionalidadeCategoria;
	}

	public void setIdFuncionalidadeCategoria(String idFuncionalidadeCategoria) {
		this.idFuncionalidadeCategoria = idFuncionalidadeCategoria;
	}

	public String getIndicadorNovaJanela() {
		return indicadorNovaJanela;
	}

	public void setIndicadorNovaJanela(String indicadorNovaJanela) {
		this.indicadorNovaJanela = indicadorNovaJanela;
	}

	public String getIndicadorOlap() {
		return indicadorOlap;
	}

	public void setIndicadorOlap(String indicadorOlap) {
		this.indicadorOlap = indicadorOlap;
	}

	public String getNomeFuncionalidadeCategoria() {
		return nomeFuncionalidadeCategoria;
	}

	public void setNomeFuncionalidadeCategoria(String nomeFuncionalidadeCategoria) {
		this.nomeFuncionalidadeCategoria = nomeFuncionalidadeCategoria;
	}

	public String getIdFuncionalidade() {
		return idFuncionalidade;
	}

	public void setIdFuncionalidade(String idFuncionalidade) {
		this.idFuncionalidade = idFuncionalidade;
	}

	/**
	 * @return Retorna o campo caminhoMenu.
	 */
	public String getCaminhoMenu() {
		return caminhoMenu;
	}

	/**
	 * @param caminhoMenu
	 *            O caminhoMenu a ser setado.
	 */
	public void setCaminhoMenu(String caminhoMenu) {
		this.caminhoMenu = caminhoMenu;
	}

	/**
	 * @return Retorna o campo caminhoUrl.
	 */
	public String getCaminhoUrl() {
		return caminhoUrl;
	}

	/**
	 * @param caminhoUrl
	 *            O caminhoUrl a ser setado.
	 */
	public void setCaminhoUrl(String caminhoUrl) {
		this.caminhoUrl = caminhoUrl;
	}

	/**
	 * @return Retorna o campo descricao.
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao
	 *            O descricao a ser setado.
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return Retorna o campo descricaoAbreviada.
	 */
	public String getDescricaoAbreviada() {
		return descricaoAbreviada;
	}

	/**
	 * @param descricaoAbreviada
	 *            O descricaoAbreviada a ser setado.
	 */
	public void setDescricaoAbreviada(String descricaoAbreviada) {
		this.descricaoAbreviada = descricaoAbreviada;
	}

	/**
	 * @return Retorna o campo indicadorPontoEntrada.
	 */
	public String getIndicadorPontoEntrada() {
		return indicadorPontoEntrada;
	}

	/**
	 * @param indicadorPontoEntrada
	 *            O indicadorPontoEntrada a ser setado.
	 */
	public void setIndicadorPontoEntrada(String indicadorPontoEntrada) {
		this.indicadorPontoEntrada = indicadorPontoEntrada;
	}

	/**
	 * @return Retorna o campo modulo.
	 */
	public String getModulo() {
		return modulo;
	}

	/**
	 * @param modulo
	 *            O modulo a ser setado.
	 */
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	/**
	 * @return Retorna o campo id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            O id a ser setado.
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getNumeroOrdemMenu() {
		return numeroOrdemMenu;
	}

	public void setNumeroOrdemMenu(String numeroOrdemMenu) {
		this.numeroOrdemMenu = numeroOrdemMenu;
	}

}
