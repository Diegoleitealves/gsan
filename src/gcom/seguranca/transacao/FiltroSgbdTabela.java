package gcom.seguranca.transacao;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * O filtro serve para armazenar os crit�rios de busca da SgbdTabela 
 * 
 * @author Thiago Toscano
 */
public class FiltroSgbdTabela extends Filtro implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
     * Constructor 
     */
    public FiltroSgbdTabela() {
    }

    /**
     * Constructor que recebe o parametro de ordenacao
     * 
     * @param campoOrderBy
     *            Description of the Parameter
     */
    public FiltroSgbdTabela(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

    /**
     * C�digo da unidade de medi��o
     */
    public final static String ID = "id";
}
