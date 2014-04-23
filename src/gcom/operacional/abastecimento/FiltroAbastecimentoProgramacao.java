package gcom.operacional.abastecimento;

import gcom.util.filtro.Filtro;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 21/08/2006
 */
public class FiltroAbastecimentoProgramacao extends Filtro {
	private static final long serialVersionUID = 1L;
	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */
	public final static String ANOMESREFERENCIA = "anoMesReferencia";

	/**
	 * Description of the Field
	 */
	public final static String DATA_INICIO = "dataInicio";

	/**
	 * Description of the Field
	 */
	public final static String DATA_FIM = "dataFim";

	/**
	 * Description of the Field
	 */
	public final static String HORA_INICIO = "horaInicio";

	/**
	 * Description of the Field
	 */

	public final static String HORA_FIM = "horaFim";

	/**
	 * Description of the Field
	 */
	public final static String SISTEMAS_ABASTECIMENTO = "sistemaAbastecimento.id";

	public final static String SETOR_ABASTECIMENTO = "setorAbastecimento.id";

	public final static String DISTRITO_OPERACIONAL = "distritoOperacional.id";

	public final static String ZONA_ABASTECIMENTO = "zonaAbastecimento.id";

	public final static String BAIRRO_AREA = "bairroArea.id";

	public final static String BAIRRO = "bairro.id";

	public final static String MUNICIPIO = "municipio.id";

	/**
	 * 
	 * 
	 * Construtor de FiltroFuncionalidade
	 * 
	 */

	public FiltroAbastecimentoProgramacao() {

	}

	/**
	 * Construtor da classe FiltroFuncionalidade
	 * 
	 * @param campoOrderBy
	 *            Descri��o do par�metro
	 */
	public FiltroAbastecimentoProgramacao(String campoOrderBy) {
		this.campoOrderBy = campoOrderBy;
	}

}
