package gcom.faturamento;

import javax.ejb.CreateException;

/**
 * < <Descri��o da Interface>>
 * 
 * @author Administrador
 */
public interface ControladorFaturamentoLocalHome extends javax.ejb.EJBLocalHome {

    /**
     * < <Descri��o do m�todo>>
     * 
     * @return Descri��o do retorno
     * @exception CreateException
     *                Descri��o da exce��o
     */
    public ControladorFaturamentoLocal create() throws CreateException;

}
