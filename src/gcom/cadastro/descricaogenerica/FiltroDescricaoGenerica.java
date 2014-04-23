package gcom.cadastro.descricaogenerica;

import gcom.util.filtro.Filtro;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class FiltroDescricaoGenerica extends Filtro {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe FiltroCategoria
	 */
	public FiltroDescricaoGenerica() {
	}

	/**
	 * Description of the Field
	 */
	public final static String Dege_Id = "degeId";

	public final static String NOME_GENERICO = "nomeGenerico";

	/**
	 * Construtor da classe FiltroCategoria
	 * 
	 * @param campoOrderBy
	 *            Descri��o do par�metro
	 */
	public FiltroDescricaoGenerica(String campoOrderBy) {
		this.campoOrderBy = campoOrderBy;
	}

}
