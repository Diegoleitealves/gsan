package gcom.cadastro.imovel;

import gcom.util.filtro.Filtro;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class FiltroCategoriaTipo extends Filtro {
	
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
    public final static String DESCRICAO_ABREVIADA = "descricaoAbreviada";

    /**
     * Description of the Field
     */
    public final static String ULTIMA_ALTERACAO = "indicadorUso";

    /**
     * Construtor da classe FiltroCategoriaTipo
     */
    public FiltroCategoriaTipo() {
    }

    /**
     * Construtor da classe FiltroCategoriaTipo
     * 
     * @param campoOrderBy
     *            Descri��o do par�metro
     */
    public FiltroCategoriaTipo(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }
}
