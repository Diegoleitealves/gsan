package gcom.cadastro.cliente;

import gcom.util.filtro.Filtro;

/*
 *  O filtro serve para armazenar os crit�rios de busca de um tipo de telefone
 *
 *  @author     Luis Oct�vio
 *  @created    02 de Maio de 2005
 */
/**
 * < <Descri��o da Classe>>
 * 
 * @author Luis Oct�vio
 */
public class FiltroFoneTipo extends Filtro {
	
	private static final long serialVersionUID = 1L;
    /**
     * Constructor for the FiltroFoneTipo object
     */
    public FiltroFoneTipo() {
    }

    /**
     * Constructor for the FiltroFone object
     * 
     * @param campoOrderBy
     *            Description of the Parameter
     */
    public FiltroFoneTipo(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

    /**
     * C�digo do tipo de telefone
     */
    public final static String ID = "id";

    /**
     * Descri��o do tipo de telefone
     */
    public final static String DESCRICAO = "descricao";

    /**
     * C�digo identificador de uso do tipo de telefone 1 - Ativo 2 - Inativo
     */
    public final static String INDICADOR_USO = "indicadorUso";

}
