package gcom.seguranca.transacao;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * O filtro serve para armazenar os crit�rios de busca da SgbdTabelaColuna 
 * 
 * @author Thiago Toscano
 */
public class FiltroSgbdTabelaColuna extends Filtro implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
     * Constructor 
     */
    public FiltroSgbdTabelaColuna() {
    }

    /**
     * Constructor que recebe o parametro de ordenacao
     * 
     * @param campoOrderBy
     *            Description of the Parameter
     */
    public FiltroSgbdTabelaColuna(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

    /**
     * C�digo da unidade de medi��o
     */
    public final static String ID = "id";
}
