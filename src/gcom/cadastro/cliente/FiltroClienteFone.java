package gcom.cadastro.cliente;

import gcom.util.filtro.Filtro;

/*
 *  O filtro serve para armazenar os crit�rios de busca de telefones de um
 *  cliente
 *
 *  @author     Luis Oct�vio
 *  @created    02 de Maio de 2005
 */
/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 * @created 14 de Outubro de 2005
 */
public class FiltroClienteFone extends Filtro {
	
	private static final long serialVersionUID = 1L;
    /**
     * Constructor for the FiltroClienteFone object
     */
    public FiltroClienteFone() {
    }

    /**
     * Constructor for the FiltroClienteFone object
     * 
     * @param campoOrderBy
     *            Description of the Parameter
     */
    public FiltroClienteFone(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

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

    /**
     * Description of the Field
     */
    public final static String DDD = "ddd";

    /**
     * Description of the Field
     */
    public final static String CLIENTE_ID = "cliente.id";
    
    public final static String CLIENTE = "cliente";
    
    public final static String FONE_TIPO = "foneTipo";

}
