package gcom.seguranca.acesso.usuario;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * 
 * @author Rhawi Dantas
 */
public class FiltroUsuarioTipo extends Filtro implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
     * Constructor 
     */
    public FiltroUsuarioTipo() {
    }

    /**
     * Constructor 
     * 
     * @param campoOrderBy
     *            Description of the Parameter
     */
    public FiltroUsuarioTipo (String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

    /**
     * C�digo 
     */
    public final static String ID = "id";
    
    /**
     * C�digo 
     */
    public final static String DESCRICAO = "descricao";
    
    /**
     * C�digo 
     */
    public final static String INDICADOR_USO = "indicadorUso";
    
    /**
     * C�digo 
     */
    public final static String INDICADOR_FUNCIONARIO = "indicadorFuncionario";


}
