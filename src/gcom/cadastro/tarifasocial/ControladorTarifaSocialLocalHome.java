package gcom.cadastro.tarifasocial;

import javax.ejb.CreateException;

/**
 * < <Descri��o da Interface>>
 * 
 * @author rodrigo
 */
public interface ControladorTarifaSocialLocalHome extends
        javax.ejb.EJBLocalHome {
    /**
     * < <Descri��o do m�todo>>
     * 
     * @return Descri��o do retorno
     * @exception CreateException
     *                Descri��o da exce��o
     */
    public ControladorTarifaSocialLocal create() throws CreateException;
}
