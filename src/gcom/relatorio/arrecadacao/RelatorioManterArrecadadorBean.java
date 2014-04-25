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

public class RelatorioManterArrecadadorBean implements RelatorioBean {
	
	private String codigoAgente;

	private String idCliente;
	
	private String nomeCliente;
	
	private String idImovel;
	
	private String inscricaoEstadual;

	/**
	 * Construtor da classe RelatorioManterAgenciaBancariaBean
	 * 
	 * @param codigoAgente
	 *            Descri��o do par�metro
	 * @param idCliente
	 *            Descri��o do par�metro
	 * @param nomeCliente
	 *            Descri��o do par�metro
	 * @param idImovel
	 *            Descri��o do par�metro
 	 * @param inscricaoEstadual
	 *            Descri��o do par�metro
	 */

	public RelatorioManterArrecadadorBean(String codigoAgente, String idCliente, String nomeCliente, String idImovel, String inscricaoEstadual) {
		
		this.codigoAgente = codigoAgente;
		this.idCliente = idCliente;
		this.nomeCliente = nomeCliente;
		this.idImovel = idImovel;
		this.inscricaoEstadual = inscricaoEstadual;
	
	}

	public String getCodigoAgente() {
		return codigoAgente;
	}

	public void setCodigoAgente(String codigoAgente) {
		this.codigoAgente = codigoAgente;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getIdImovel() {
		return idImovel;
	}

	public void setIdImovel(String idImovel) {
		this.idImovel = idImovel;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	
}
