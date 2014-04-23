package gcom.relatorio.cobranca;

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

public class RelatorioManterCobrancaSituacaoBean implements RelatorioBean {
	
	private String id;

	private String descricao;
	
	private String contaMotivoRevisao;
	
	private String indicadorExigenciaAdvogado;

	private String indicadorBloqueioParcelamento;
	
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

	public RelatorioManterCobrancaSituacaoBean(String id,
			String descricao, String contaMotivoRevisao, String indicadorExigenciaAdvogado, String indicadorBloqueioParcelamento, String indicadorUso) {
		this.id = id;
		this.descricao = descricao;
		this.contaMotivoRevisao = contaMotivoRevisao;
		this.indicadorExigenciaAdvogado = indicadorExigenciaAdvogado;
		this.indicadorBloqueioParcelamento = indicadorBloqueioParcelamento;
		this.indicadorUso = indicadorUso;
	}
	
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIndicadorUso() {
		return indicadorUso;
	}
	public void setIndicadorUso(String indicadorUso) {
		this.indicadorUso = indicadorUso;
	}

	public String getContaMotivoRevisao() {
		return contaMotivoRevisao;
	}

	public void setContaMotivoRevisao(String contaMotivoRevisao) {
		this.contaMotivoRevisao = contaMotivoRevisao;
	}

	public String getIndicadorBloqueioParcelamento() {
		return indicadorBloqueioParcelamento;
	}

	public void setIndicadorBloqueioParcelamento(
			String indicadorBloqueioParcelamento) {
		this.indicadorBloqueioParcelamento = indicadorBloqueioParcelamento;
	}

	public String getIndicadorExigenciaAdvogado() {
		return indicadorExigenciaAdvogado;
	}

	public void setIndicadorExigenciaAdvogado(String indicadorExigenciaAdvogado) {
		this.indicadorExigenciaAdvogado = indicadorExigenciaAdvogado;
	}
	
}
