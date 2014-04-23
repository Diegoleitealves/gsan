package gcom.financeiro;

import javax.ejb.CreateException;

/**
 * < <Descri��o da Interface>>
 * 
 * @author Raphael Rossiter
 * @since 09/01/2006
 * 
 */
public interface ControladorFinanceiroLocalHome extends
        javax.ejb.EJBLocalHome {
    /**
     * < <Descri��o do m�todo>>
     * 
     * @return Descri��o do retorno
     * @exception CreateException
     *                Descri��o da exce��o
     */
    public ControladorFinanceiroLocal create() throws CreateException;
}
