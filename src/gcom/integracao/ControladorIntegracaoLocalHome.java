package gcom.integracao;

import javax.ejb.CreateException;

public interface ControladorIntegracaoLocalHome extends javax.ejb.EJBLocalHome {
    /**
     * < <Descri��o do m�todo>>
     * 
     * @return Descri��o do retorno
     * @exception CreateException
     *                Descri��o da exce��o
     */
    public ControladorIntegracaoLocal create() throws CreateException;
}
