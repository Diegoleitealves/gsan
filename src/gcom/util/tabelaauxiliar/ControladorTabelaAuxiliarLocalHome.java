package gcom.util.tabelaauxiliar;

import javax.ejb.CreateException;

/**
 * < <Descri��o da Interface>>
 * 
 * @author rodrigo
 */
public interface ControladorTabelaAuxiliarLocalHome extends
        javax.ejb.EJBLocalHome {
    /**
     * < <Descri��o do m�todo>>
     * 
     * @return Descri��o do retorno
     * @exception CreateException
     *                Descri��o da exce��o
     */
    public ControladorTabelaAuxiliarLocal create() throws CreateException;
}
