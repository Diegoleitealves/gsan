package gcom.cadastro.geografico;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * O filtro serve para armazenar os crit�rios de busca de uma regi�o
 * 
 * @author S�vio Luiz
 * @created 22 de Abril de 2005
 */

public class FiltroRegiaoDesenvolvimento extends Filtro implements Serializable {
	
	private static final long serialVersionUID = 1L;
    /**
     * Constructor for the FiltroRegiaoDesenvolvimento object
     */
    public FiltroRegiaoDesenvolvimento() {
    }

    /**
     * Constructor for the FiltroCliente object
     * 
     * @param campoOrderBy
     *            Description of the Parameter
     */
    public FiltroRegiaoDesenvolvimento(String campoOrderBy) {
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
    public final static String DESCRICAO = "descricao";

    /**
     * Description of the Field
     */
    public final static String INDICADOR_USO = "indicadorUso";

}
