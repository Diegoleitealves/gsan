package gcom.cadastro.tarifasocial;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class FiltroTarifaSocialExclusaoMotivo extends Filtro implements
		Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * Construtor da classe FiltroRendaTipo
	 */
	public FiltroTarifaSocialExclusaoMotivo() {
	}

	/**
	 * Construtor da classe FiltroRendaTipo
	 * 
	 * @param campoOrderBy
	 *            Descri��o do par�metro
	 */
	public FiltroTarifaSocialExclusaoMotivo(String campoOrderBy) {
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

	public final static String INDICADOR_USO = "indicadorUso";

}
