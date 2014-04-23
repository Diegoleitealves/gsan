package gcom.cadastro.geografico;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * O filtro serve para armazenar os crit�rios de busca de uma regi�o
 * 
 * @author S�vio Luiz
 * @created 22 de Abril de 2005
 */

public class FiltroRegiao extends Filtro implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * Constructor for the FiltroRegiao object
     */
    public FiltroRegiao() {
    }

    /**
     * Constructor for the FiltroCliente object
     * 
     * @param campoOrderBy
     *            Description of the Parameter
     */
    public FiltroRegiao(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

    /**
     * C�digo da unidade de medi��o
     */
    public final static String CODIGO = "codigo";
    /**
     * C�digo da unidade de medi��o
     */
    public final static String ID = "id";
    
    /**
     * C�digo da unidade de medi��o
     */
    public final static String DESCRICAO = "nome";

    /**
     * Description of the Field
     */
    public final static String INDICADOR_USO = "indicadorUso";

}
