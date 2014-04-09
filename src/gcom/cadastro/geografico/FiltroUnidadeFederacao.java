package gcom.cadastro.geografico;

import gcom.util.filtro.Filtro;

/**
 * O filtro serve para armazenar os crit�rios de busca de uma Unidade da
 * Federa��o
 * 
 * @author S�vio Luiz
 * @created 22 de Abril de 2005
 */

public class FiltroUnidadeFederacao extends Filtro {
	
	private static final long serialVersionUID = 1L;
    /**
     * Construtor da classe FiltroUnidadeFederacao
     */
    public FiltroUnidadeFederacao() {
    }

    /**
     * Construtor da classe FiltroUnidadeFederacao
     * 
     * @param campoOrderBy
     *            Descri��o do par�metro
     */
    public FiltroUnidadeFederacao(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
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
    public final static String SIGLA = "sigla";

}
