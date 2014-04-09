package gcom.micromedicao.consumo;

import gcom.util.filtro.Filtro;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Bruno Barros
 */
public class FiltroConsumoTipo extends Filtro {
	
	private static final long serialVersionUID = 1L;

    /**
     * Description of the Field
     */
    public final static String CODIGO = "id";

    /**
     * Description of the Field
     */
    public final static String DESCRICAO = "descricao";

    /**
     * Description of the Field
     */
    public final static String INDICADOR_USO = "indicadorUso";
  

    /**
     * Construtor da classe FiltroCategoriaTipo
     */
    public FiltroConsumoTipo() {
    }

    /**
     * Construtor da classe FiltroCategoriaTipo
     * 
     * @param campoOrderBy
     *            Descri��o do par�metro
     */
    public FiltroConsumoTipo(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }
}
