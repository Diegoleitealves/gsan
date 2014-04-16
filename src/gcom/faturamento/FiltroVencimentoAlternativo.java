package gcom.faturamento;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * O filtro serve para armazenar os crit�rios de busca de uma municipio
 * 
 * @author S�vio Luiz
 * @created 22 de Abril de 2005
 */

public class FiltroVencimentoAlternativo extends Filtro implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor for the FiltroBairro object
	 */
	public FiltroVencimentoAlternativo() {
	}

	/**
	 * Constructor for the FiltroCliente object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroVencimentoAlternativo(String campoOrderBy) {
		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Id do bairro
	 */
	public final static String ID = "id";

	/**
	 * C�digo do bairro
	 */
	public final static String IMOVEL_ID = "imovel.id";

	/**
	 * Data Exclusao
	 */
	public final static String DATA_EXCLUSAO = "dateExclusao";
	
	/**
	 * Data Implantacao
	 */
	public final static String DATA_IMPLANTACAO = "dataImplantacao";

}
