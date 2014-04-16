package gcom.seguranca.acesso;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * O filtro serve para armazenar os crit�rios de busca de uma municipio
 * 
 * @author Thiago Ten�rio
 * @created 27 de Mar�o de 2005
 */

public class FiltroPesquisarOperacao extends Filtro implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
     * Constructor for the FiltroBairro object
     */
    public FiltroPesquisarOperacao() {
    }

    /**
     * Constructor for the FiltroCliente object
     * 
     * @param campoOrderBy
     *            Description of the Parameter
     */
    public FiltroPesquisarOperacao(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

    /**
     * Id do Unidade
     */
    public final static String ID = "id";

    /**
     * Descri��o Abreviada da Unidade
     */
    public final static String DESCRICAO_ABREVIADA = "descricaoAbreviada";

    /**
     * Id Unidade Nivel
     */
    public final static String OPERACAO_TIPO = "operacaoTipo.id";

    /**
     * Nome do Municipio
     */
    public final static String FUNCIONALIDADE = "funcionalidade.id";
    
    /**
     * Descri��o da Unidade
     */
    public final static String DESCRICAO = "descricao";

}
