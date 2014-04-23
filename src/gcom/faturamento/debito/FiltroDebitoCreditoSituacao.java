package gcom.faturamento.debito;

import gcom.util.filtro.Filtro;

/**
 * objeto utilizado na pesquisa de d�bito cr�dito situ��o
 * 
 * @author Pedro Alexandre
 * @created 23 de mar�o de 2006
 */
public class FiltroDebitoCreditoSituacao extends Filtro {
	private static final long serialVersionUID = 1L;
    /**
     * Construtor da classe FiltroDebitoCreditoSituacao
     */
    public FiltroDebitoCreditoSituacao() {
    }

    /**
     * Description of the Field
     */
    public final static String ID = "id";

    /**
     * Description of the Field
     */
    public final static String DESCRICAO = "descricaoDebitoCreditoSituacao";
    
    
    /**
     * Construtor da classe FiltroDebitoCreditoSituacao
     * 
     * @param campoOrderBy
     *            Descri��o do par�metro
     */
    public FiltroDebitoCreditoSituacao(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }

}

