package gcom.relatorio.arrecadacao;

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
 * @author Fernando Fontelles
 * @version 1.0
 */

public class RelatorioManterAgenciaBancariaBean implements RelatorioBean {
	
	private Integer codBanco;

	private String codigoAgencia;
	
	private String nomeAgencia;

	/**
	 * Construtor da classe RelatorioManterAgenciaBancariaBean
	 * 
	 * @param codBanco
	 *            Descri��o do par�metro
	 * @param codigoAgencia
	 *            Descri��o do par�metro
	 * @param nomeAgencia
	 *            Descri��o do par�metro
	 */

	public RelatorioManterAgenciaBancariaBean(Integer codBanco,
			String codigoAgencia, String nomeAgencia) {
		
		this.codBanco = codBanco;
		this.codigoAgencia = codigoAgencia;
		this.nomeAgencia = nomeAgencia;
	
	}

	public Integer getCodBanco() {
		return codBanco;
	}

	public void setCodBanco(Integer codBanco) {
		this.codBanco = codBanco;
	}

	public String getCodigoAgencia() {
		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}

	public String getNomeAgencia() {
		return nomeAgencia;
	}

	public void setNomeAgencia(String nomeAgencia) {
		this.nomeAgencia = nomeAgencia;
	}
	
}
