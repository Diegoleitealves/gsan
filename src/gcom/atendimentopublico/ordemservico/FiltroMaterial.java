package gcom.atendimentopublico.ordemservico;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 28/07/2006
 */
public class FiltroMaterial extends Filtro implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */
	public final static String CODIGO = "codigo";
	
	/**
	 * Description of the Field
	 */
	public final static String DESCRICAO = "descricao";

	/**
	 * Description of the Field
	 */
	public final static String DESCRICAO_ABREVIADA = "descricaoAbreviada";

	/**
	 * Description of the Field
	 */
	public final static String MATERIAL_UNIDADE_ID = "materialUnidade.id";
	
	/**
	 * Description of the Field
	 */
	public final static String INDICADOR_USO = "indicadorUso";

	/**
	 * 
	 * 
	 * Construtor de FiltroMaterial<ate
	 * 
	 */

	public FiltroMaterial() {

	}

	/**
	 * Construtor da classe FiltroMaterial
	 * 
	 * @param campoOrderBy
	 *            Descri��o do par�metro
	 */
	public FiltroMaterial(String campoOrderBy) {
		this.campoOrderBy = campoOrderBy;
	}

}
