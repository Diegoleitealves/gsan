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
 * @author Arthur Carvalho
 * @version 1.0
 */

public class RelatorioManterLigacaoEsgotoEsgotamentoBean implements RelatorioBean {
	
	private String id;

	private String descricao;
	
	private String faturamentoSituacaoTipo;
	
	private String faturamentoSituacaoMotivo;

	private String quantidadeMesesSituacaoEspecial;
	
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

	public RelatorioManterLigacaoEsgotoEsgotamentoBean(String id,
			String descricao, String faturamentoSituacaoTipo, String faturamentoSituacaoMotivo, String quantidadeMesesSituacaoEspecial, String indicadorUso) {
		this.id = id;
		this.descricao = descricao;
		this.faturamentoSituacaoTipo = faturamentoSituacaoTipo;
		this.faturamentoSituacaoMotivo = faturamentoSituacaoMotivo;
		this.quantidadeMesesSituacaoEspecial = quantidadeMesesSituacaoEspecial;
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
	public String getQuantidadeMesesSituacaoEspecial() {
		return quantidadeMesesSituacaoEspecial;
	}
	public void setQuantidadeMesesSituacaoEspecial(
			String quantidadeMesesSituacaoEspecial) {
		this.quantidadeMesesSituacaoEspecial = quantidadeMesesSituacaoEspecial;
	}


	public String getFaturamentoSituacaoMotivo() {
		return faturamentoSituacaoMotivo;
	}


	public void setFaturamentoSituacaoMotivo(String faturamentoSituacaoMotivo) {
		this.faturamentoSituacaoMotivo = faturamentoSituacaoMotivo;
	}


	public String getFaturamentoSituacaoTipo() {
		return faturamentoSituacaoTipo;
	}


	public void setFaturamentoSituacaoTipo(String faturamentoSituacaoTipo) {
		this.faturamentoSituacaoTipo = faturamentoSituacaoTipo;
	}
	
}
