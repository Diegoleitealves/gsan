package gcom.micromedicao;

import javax.ejb.CreateException;

/**
 * < <Descri��o da Interface>>
 * 
 * @author rodrigo
 */
public interface ControladorMicromedicaoLocalHome extends
        javax.ejb.EJBLocalHome {
    /**
     * < <Descri��o do m�todo>>
     * 
     * @return Descri��o do retorno
     * @exception CreateException
     *                Descri��o da exce��o
     */
    public ControladorMicromedicaoLocal create() throws CreateException;
}
