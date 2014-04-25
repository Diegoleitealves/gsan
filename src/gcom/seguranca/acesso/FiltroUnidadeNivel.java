package gcom.seguranca.acesso;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * O filtro serve para armazenar os crit�rios de busca do usuario alteracao
 * 
 * @author Thiago Ten�rio
 */
public class FiltroUnidadeNivel extends Filtro implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
     * Constructor 
     */
    public FiltroUnidadeNivel() {
    }

    /**
     * Constructor 
     * 
     * @param campoOrderBy
     *            Description of the Parameter
     */
    public FiltroUnidadeNivel(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

    /**
     * C�digo 
     */
    public final static String ID = "id";

}
