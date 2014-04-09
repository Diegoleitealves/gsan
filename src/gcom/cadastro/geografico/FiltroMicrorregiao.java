package gcom.cadastro.geografico;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * O filtro serve para armazenar os crit�rios de busca de uma municipio
 * 
 * @author S�vio Luiz
 * @created 22 de Abril de 2005
 */

public class FiltroMicrorregiao extends Filtro implements Serializable {
	
	private static final long serialVersionUID = 1L;
    /**
     * Constructor for the FiltroMicrorregiao object
     */
    public FiltroMicrorregiao() {
    }

    /**
     * Constructor for the FiltroCliente object
     * 
     * @param campoOrderBy
     *            Description of the Parameter
     */
    public FiltroMicrorregiao(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

    /**
     * C�digo da unidade de medi��o
     */
    public final static String ID = "id";
    
    
    /**
     * C�digo da unidade de medi��o
     */
    public final static String DESCRICAO ="nome";

    /**
     * Nome do municipio
     */
    public final static String REGIAO_ID = "regiao.id";

    /**
     * Description of the Field
     */
    public final static String INDICADOR_USO = "indicadorUso";

}
