package gcom.cadastro.tarifasocial;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class FiltroRendaTipo extends Filtro implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe FiltroRendaTipo
	 */
	public FiltroRendaTipo() {
	}

	/**
	 * Construtor da classe FiltroRendaTipo
	 * 
	 * @param campoOrderBy
	 *            Descri��o do par�metro
	 */
	public FiltroRendaTipo(String campoOrderBy) {
		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String DESCRICAO = "descricao";

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */
	public final static String INDICADOR_USO = "indicadorUso";
}
