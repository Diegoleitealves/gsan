package gcom.spcserasa;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * O filtro serve para armazenar os crit�rios de busca de um cliente
 * 
 * @author S�vio Luiz
 */
public class FiltroNegativacaoComando extends Filtro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor for the FiltroCliente object
	 */
	public FiltroNegativacaoComando() {
	}

	/**
	 * Constructor for the FiltroCliente object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroNegativacaoComando(String campoOrderBy) {
		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * C�digo da unidade de medi��o
	 */
	public final static String ID = "id";

	
}
