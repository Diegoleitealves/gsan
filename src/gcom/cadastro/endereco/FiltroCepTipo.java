package gcom.cadastro.endereco;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class FiltroCepTipo extends Filtro implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe FiltroCepTipo
	 */
	public FiltroCepTipo() {
	}

	/**
	 * Construtor da classe FiltroCepTipo
	 * 
	 * @param campoOrderBy
	 *            Descri��o do par�metro
	 */
	public FiltroCepTipo(String campoOrderBy) {
		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */
	public final static String INDICADOR_USO = "indicadorUso";

	/**
	 * Description of the Field
	 */
	public final static String DESCRICAO = "descricao";

}
