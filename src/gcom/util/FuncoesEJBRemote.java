package gcom.util;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * < <Descri��o da Interface>>
 * 
 * @author administrator
 */
public interface FuncoesEJBRemote extends EJBObject {
    /**
     * < <Descri��o do m�todo>>
     * 
     * @param contextoJNDI
     *            Descri��o do par�metro
     * @return Descri��o do retorno
     * @exception RemoteException
     *                Descri��o da exce��o
     */
    public java.sql.Connection alocarConexao(java.lang.String contextoJNDI)
            throws RemoteException;

    /**
     * < <Descri��o do m�todo>>
     * 
     * @return Descri��o do retorno
     * @exception RemoteException
     *                Descri��o da exce��o
     */
    public javax.transaction.UserTransaction alocarTransacao()
            throws RemoteException;

}
