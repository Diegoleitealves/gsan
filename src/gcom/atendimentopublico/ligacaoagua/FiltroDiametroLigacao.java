package gcom.atendimentopublico.ligacaoagua;

import gcom.util.filtro.Filtro;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Leandro Cavalcanti
 */
public class FiltroDiametroLigacao extends Filtro {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe FiltroOcupacaoTipo
	 */
	public FiltroDiametroLigacao() {
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */
	public final static String DESCRICAO = "descricao";

	/**
	 * Description of the Field
	 */
	public final static String INDICADOR_USO = "indicadorUso";

	public FiltroDiametroLigacao(String campoOrderBy) {
		this.campoOrderBy = campoOrderBy;
	}

}
