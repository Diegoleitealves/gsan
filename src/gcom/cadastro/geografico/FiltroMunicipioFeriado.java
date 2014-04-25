package gcom.cadastro.geografico;

import gcom.util.filtro.Filtro;

/** 
 * O filtro serve para armazenar os crit�rios de busca de um Municipio Feriado
 * 
 * @author Kassia Albuquerque
 * @created 20/12/2006
 */

public class FiltroMunicipioFeriado extends Filtro {
	
	private static final long serialVersionUID = 1L;
    /**
     * Constructor for the FiltroMunicipio object
     */
    public FiltroMunicipioFeriado() {
    }

    /**
     * Constructor for the FiltroCliente object
     * 
     * @param campoOrderBy
     *            Description of the Parameter
     */
    public FiltroMunicipioFeriado(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

       
    /**
     * C�digo do Feriado
     */
    public final static String ID = "id";

    /**
     * Data do Feriado
     */
    public final static String DATA = "dataFeriado";

    /**
     * Descri��o do Feriado
     */
    public final static String NOME = "descricaoFeriado";
    
    /**
     * Description of the Field
     */
    public final static String ID_MUNICIPIO = "municipio.id";
    
  	
   
}
