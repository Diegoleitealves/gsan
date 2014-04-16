package gcom.seguranca.acesso.usuario;

import gcom.util.filtro.Filtro;


/**
 * Descri��o da classe 
 *
 * @author Thiago Ten�rio
 * @date 10/05/2006
 */
public class FiltroUsuarioSituacao extends Filtro {
	private static final long serialVersionUID = 1L;
    /**
     * Description of the Field
     */
    public final static String DESCRICAO = "descricaoUsuarioSituacao";

    /**
     * Construtor da classe FiltroCategoria
     */
    public final static String DESCRICAO_ABREVIADA = "descricaoAbreviada";
    /**
     * Description of the Field
     */
    public final static String INDICADOR_DE_USO_SISTEMA = "indicadorUsoSistema";
    
    /**
     * Description of the Field
     */
    public final static String INDICADOR_DE_USO = "indicadorUso";
    
    public final static String ID = "id";
    
    public FiltroUsuarioSituacao() {
    }

    /**
     * Construtor da classe FiltroCategoria
     * 
     * @param campoOrderBy
     *            Descri��o do par�metro
     */
    public FiltroUsuarioSituacao(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }
}
