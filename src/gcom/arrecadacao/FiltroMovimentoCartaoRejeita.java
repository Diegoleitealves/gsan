package gcom.arrecadacao;

import gcom.util.filtro.Filtro;

/**
 * O filtro � respons�vel por armazenar os par�metros da pesquisa de movimentos de cart�o rejeitados 
 *
 * @author Raphael Rossiter
 * @date 07/06/2010
 */
public class FiltroMovimentoCartaoRejeita extends Filtro {
	
	private static final long serialVersionUID = 1L;
	
	public final static String ARRECADADOR_MOVIMENTO_ITEM_ID = "arrecadadorMovimentoItem.id";
	
	/**
     * Construtor de FiltroPagamento 
     * 
     */
    public FiltroMovimentoCartaoRejeita() {
    }

    /**
     * Construtor da classe FiltroPagamento
     * 
     * @param campoOrderBy
     *            Descri��o do par�metro
     */
    public FiltroMovimentoCartaoRejeita(String campoOrderBy) {
        this.campoOrderBy = campoOrderBy;
    }
}
