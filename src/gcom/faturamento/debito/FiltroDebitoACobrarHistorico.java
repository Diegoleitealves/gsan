package gcom.faturamento.debito;

import java.io.Serializable;

import gcom.util.filtro.Filtro;

/**
 * O filtro serve para armazenar os crit�rios de busca de um debito a cobrar no hist�rico
 * @author Raphael Rossiter
 * @since 14/04/2008
 *
 */
public class FiltroDebitoACobrarHistorico extends Filtro implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor for the FiltroDebitoACobrar object
	 */
	public FiltroDebitoACobrarHistorico() {
	}

	/**
	 * Constructor for the FiltroDebitoACobrar object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroDebitoACobrarHistorico(String campoOrderBy) {
		this.campoOrderBy = campoOrderBy;
	}
	
	/**
	 * Description of the Field
	 */
	public final static String ID = "id";
	
	/**
	 * Description of the Field
	 */
	public final static String IMOVEL_ID = "imovel.id";
	
	
	public final static String DEBITO_TIPO_ID = "debitoTipo.id";
	
	
	public final static String DEBITO_CREDITO_SITUACAO_ATUAL_ID =  "debitoCreditoSituacaoAtual.id";
	
}
