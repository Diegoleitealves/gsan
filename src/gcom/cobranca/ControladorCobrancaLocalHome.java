package gcom.cobranca;

import javax.ejb.CreateException;

/**
 * < <Descri��o da Interface>>
 * 
 * @author Rafael Santos
 * @since 02/01/2006
 * 
 */
public interface ControladorCobrancaLocalHome extends
        javax.ejb.EJBLocalHome {
    /**
     * < <Descri��o do m�todo>>
     * 
     * @return Descri��o do retorno
     * @exception CreateException
     *                Descri��o da exce��o
     */
    public ControladorCobrancaLocal create() throws CreateException;
}
