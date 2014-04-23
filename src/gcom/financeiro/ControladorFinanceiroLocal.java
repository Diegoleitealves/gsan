package gcom.financeiro;

/**
 * Controlador de Financeiro
 * @author Raphael Rossiter
 * @since 09/01/2006
 */
public interface ControladorFinanceiroLocal extends javax.ejb.EJBLocalObject, IControladorFinanceiro {

	/**
	 * Remove os lan�amentos cont�beis e seus respectivos itens 
	 * de acordo com os par�metros informados. 
	 *
	 * @author Pedro Alexandre
	 * @date 26/06/2007
	 *
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param idLancamentoOrigem
	 * @throws ControladorException
	 */
	//public void removerLancamentoContabil(Integer anoMesReferenciaContabil, Integer idLocalidade, Integer idLancamentoOrigem) throws ControladorException;

}
